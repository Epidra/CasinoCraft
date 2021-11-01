package mod.casinocraft.logic.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Dice;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundTag;

import static mod.casinocraft.util.SoundMap.SOUND_CHIP;
import static mod.casinocraft.util.SoundMap.SOUND_DICE;

public class LogicMinoOrange extends LogicModule {   // Craps

    public Dice[] dice = new Dice[2];

    public int result = 0;
    public int point = 0;
    public int comepoint = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoOrange(int tableID){
        super(tableID, 8, 5);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        hand = "Place your Bets..";
        selector = new Vector2(3, 2);
        result = -1;
        point = -1;
        comepoint = -1;
        dice[0] = new Dice(0, 1);
        dice[1] = new Dice(0, 1);
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        timeout = 0;
        if(action == -2) {
            if(selector.X > -1){
                grid[selector.X][selector.Y] = activePlayer+1;
                selector.set(-1, -1);
            }
            spin();
        } else if(action == -1){
            if(selector.X > -1){
                grid[selector.X][selector.Y] = activePlayer+1;
                selector.set(-1, -1);
            }
        } else {
            int x = action % 8;
            int y = action / 8;
            if(grid[x][y] == 0){
                selector.set(x, y);
                setJingle(SOUND_CHIP);
            }
        }
    }

    private void reset(){
        turnstate = 2;
        activePlayer = 0;
        timeout = 0;
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){
        if(turnstate == 2){
            timeout++;
            if(timeout == CasinoKeeper.config_timeout.get()){
                spin();
            }
        }
        if(turnstate == 3) {
            for(int i = 0; i < 2; i++) {
                if(dice[i].shiftX > 45) {
                    dice[i].Update(1, RANDOM.nextInt(6));
                } else if(dice[i].shiftX > 0) {
                    dice[i].shiftX = 0;
                    dice[i].shiftY = 0;
                }
            }
        }
        if(turnstate == 4) {
            selector  = new Vector2(-1, -1);
        }
    }

    public void updateMotion(){

    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        dice = loadDice(compound);
        result = compound.getInt("result");
        point = compound.getInt("point");
        comepoint = compound.getInt("comepoint");
    }

    public CompoundTag save2(CompoundTag compound){
        saveDice(compound, dice);
        compound.putInt("result", result);
        compound.putInt("point", point);
        compound.putInt("comepoint", comepoint);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void spin() {
        if(turnstate == 2) {
            activePlayer++;
            if(activePlayer >= getFirstFreePlayerSlot()){
                setJingle(SOUND_DICE);
                dice[0].setUp(200 + RANDOM.nextInt( 50),  50 + RANDOM.nextInt(200), RANDOM.nextInt(2) == 0);
                dice[1].setUp( 50 + RANDOM.nextInt(200), 200 + RANDOM.nextInt( 50), RANDOM.nextInt(2) == 0);
                if(selector.X > -1) {
                    grid[selector.X][selector.Y] = 1;
                    selector = new Vector2(-1, -1);
                }
                turnstate = 3;
            }

        } else if(turnstate == 3) {
            if(dice[0].shiftX == 0 && dice[1].shiftX == 0) {
                result();
            }
        }
    }

    private void result() {
        dice[0].Reset();
        dice[1].Reset();
        boolean hasCome = false;
        for(int i = 0; i < 5; i++) {
            if(grid[0][i] == 1) hasCome = true;
            if(grid[7][i] == 1) hasCome = true;
        }
        if(point == -1) {
            point  = dice[0].number + 1 + dice[1].number + 1;
            result = dice[0].number + 1 + dice[1].number + 1;
            if(result == 7 || result == 11) { // PASS
                resultPass(true);
                resultDontPass(false);
                hand = "Natura Roll!";
                turnstate = 4;
            } else if(result == 2 || result == 3 || result == 12) { // DON'T PASS
                resultPass(false);
                resultDontPass(true);
                hand = "Crab...";
                turnstate = 4;
            } else {
                hand = "Roll again...";
                reset();
            }
        } else {
            result = dice[0].number + 1 + dice[1].number + 1;
            resultSingleOdds();
            reset();
            if(hasCome) {
                if(comepoint == -1) {
                    comepoint = result;
                } else {
                    if(result == comepoint) {
                        resultCome(true);
                        resultDontCome(false);
                    } else if(comepoint == 7) {
                        resultCome(false);
                        resultDontCome(true);
                    }
                }
            }
            if(comepoint == 7) { // DON'T PASS
                resultPass(false);
                resultPassOdds(false);
                resultDontPass(true);
                resultDontPassOdds(true);
                hand = "SEVEN";
                turnstate = 4;
            } else if(result == point) { // PASS
                resultPass(false);
                resultPassOdds(false);
                resultDontPass(true);
                resultDontPassOdds(true);
                hand = "POINT";
                turnstate = 4;
            }
        }
    }

    private void resultCome(boolean won) {
        if(grid[0][0] > 0) { if(won) { reward[grid[0][0]-1] += 2; } else { grid[0][0] = 0; } }
        if(grid[0][1] > 0) { if(won) { reward[grid[0][1]-1] += 2; } else { grid[0][1] = 0; } }
        if(grid[0][2] > 0) { if(won) { reward[grid[0][2]-1] += 2; } else { grid[0][2] = 0; } }
        if(grid[0][3] > 0) { if(won) { reward[grid[0][3]-1] += 2; } else { grid[0][3] = 0; } }
        if(grid[0][4] > 0) { if(won) { reward[grid[0][4]-1] += 2; } else { grid[0][4] = 0; } }
    }

    private void resultDontCome(boolean won) {
        if(grid[7][0] == 1) { if(won) { reward[grid[7][0]-1] += 2; } else { grid[7][0] = 0; } }
        if(grid[7][1] == 1) { if(won) { reward[grid[7][1]-1] += 2; } else { grid[7][1] = 0; } }
        if(grid[7][2] == 1) { if(won) { reward[grid[7][2]-1] += 2; } else { grid[7][2] = 0; } }
        if(grid[7][3] == 1) { if(won) { reward[grid[7][3]-1] += 2; } else { grid[7][3] = 0; } }
        if(grid[7][4] == 1) { if(won) { reward[grid[7][4]-1] += 2; } else { grid[7][4] = 0; } }
    }

    private void resultPass(boolean won) {
        if(grid[1][0] == 1) { if(won) { reward[grid[1][0]-1] += 2; } else { grid[1][0] = 0; } }
        if(grid[2][0] == 1) { if(won) { reward[grid[2][0]-1] += 2; } else { grid[2][0] = 0; } }
        if(grid[3][0] == 1) { if(won) { reward[grid[3][0]-1] += 2; } else { grid[3][0] = 0; } }
        if(grid[4][0] == 1) { if(won) { reward[grid[4][0]-1] += 2; } else { grid[4][0] = 0; } }
        if(grid[5][0] == 1) { if(won) { reward[grid[5][0]-1] += 2; } else { grid[5][0] = 0; } }
        if(grid[6][0] == 1) { if(won) { reward[grid[6][0]-1] += 2; } else { grid[6][0] = 0; } }
    }

    private void resultDontPass(boolean won) {
        if(grid[1][4] == 1) { if(won) { reward[grid[1][4]-1] += 2; } else { grid[1][4] = 0; } }
        if(grid[2][4] == 1) { if(won) { reward[grid[2][4]-1] += 2; } else { grid[2][4] = 0; } }
        if(grid[3][4] == 1) { if(won) { reward[grid[3][4]-1] += 2; } else { grid[3][4] = 0; } }
        if(grid[4][4] == 1) { if(won) { reward[grid[4][4]-1] += 2; } else { grid[4][4] = 0; } }
        if(grid[5][4] == 1) { if(won) { reward[grid[5][4]-1] += 2; } else { grid[5][4] = 0; } }
        if(grid[6][4] == 1) { if(won) { reward[grid[6][4]-1] += 2; } else { grid[6][4] = 0; } }
    }

    private void resultPassOdds(boolean won) {
        if(grid[1][1] == 1) { if(won) { if(result ==  4) { reward[grid[1][1]-1] += 2; } else { grid[1][1] = 0; } } else { grid[1][1] = 0; } }
        if(grid[2][1] == 1) { if(won) { if(result ==  5) { reward[grid[2][1]-1] += 2; } else { grid[2][1] = 0; } } else { grid[2][1] = 0; } }
        if(grid[3][1] == 1) { if(won) { if(result ==  6) { reward[grid[3][1]-1] += 2; } else { grid[3][1] = 0; } } else { grid[3][1] = 0; } }
        if(grid[4][1] == 1) { if(won) { if(result ==  8) { reward[grid[4][1]-1] += 2; } else { grid[4][1] = 0; } } else { grid[4][1] = 0; } }
        if(grid[5][1] == 1) { if(won) { if(result ==  9) { reward[grid[5][1]-1] += 2; } else { grid[5][1] = 0; } } else { grid[5][1] = 0; } }
        if(grid[6][1] == 1) { if(won) { if(result == 10) { reward[grid[6][1]-1] += 2; } else { grid[6][1] = 0; } } else { grid[6][1] = 0; } }
    }

    private void resultDontPassOdds(boolean won) {
        if(grid[1][3] == 1) { if(won) { if(result ==  4) { reward[grid[1][3]-1] += 2; } else { grid[1][3] = 0; } } else { grid[1][3] = 0; } }
        if(grid[2][3] == 1) { if(won) { if(result ==  5) { reward[grid[2][3]-1] += 2; } else { grid[2][3] = 0; } } else { grid[2][3] = 0; } }
        if(grid[3][3] == 1) { if(won) { if(result ==  6) { reward[grid[3][3]-1] += 2; } else { grid[3][3] = 0; } } else { grid[3][3] = 0; } }
        if(grid[4][3] == 1) { if(won) { if(result ==  8) { reward[grid[4][3]-1] += 2; } else { grid[4][3] = 0; } } else { grid[4][3] = 0; } }
        if(grid[5][3] == 1) { if(won) { if(result ==  9) { reward[grid[5][3]-1] += 2; } else { grid[5][3] = 0; } } else { grid[5][3] = 0; } }
        if(grid[6][3] == 1) { if(won) { if(result == 10) { reward[grid[6][3]-1] += 2; } else { grid[6][3] = 0; } } else { grid[6][3] = 0; } }
    }

    private void resultSingleOdds() {
        if(grid[1][2] == 1) { if(result ==  4) {reward[grid[1][2]-1] += 10; } else { grid[1][2] = 0; } }
        if(grid[2][2] == 1) { if(result ==  5) {reward[grid[2][2]-1] +=  8; } else { grid[2][2] = 0; } }
        if(grid[3][2] == 1) { if(result ==  6) {reward[grid[3][2]-1] +=  6; } else { grid[3][2] = 0; } }
        if(grid[4][2] == 1) { if(result ==  8) {reward[grid[4][2]-1] +=  6; } else { grid[4][2] = 0; } }
        if(grid[5][2] == 1) { if(result ==  9) {reward[grid[5][2]-1] +=  8; } else { grid[5][2] = 0; } }
        if(grid[6][2] == 1) { if(result == 10) {reward[grid[6][2]-1] += 10; } else { grid[6][2] = 0; } }
    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return true;
    }

    public int getID(){
        return 42;
    }



}
