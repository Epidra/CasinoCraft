package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Dice;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

public class LogicCraps extends LogicBase {

    public Dice[] dice = new Dice[2];

    public int[][] grid = new int[21][5];

    public int result = 0;
    public int point = 0;
    public int comepoint = 0;



    //--------------------CONSTRUCTOR--------------------

    public LogicCraps(int table){
        super(false, table, "c_craps");
    }



    //--------------------BASIC--------------------

    public void start2(){
        hand = "Place your Bets..";
        for(int y = 0; y < 5; y++) {
            for(int x = 0; x < 21; x++) {
                grid[x][y] = 0;
            }
        }
        selector = new Vector2(10, 2);
        result = -1;
        point = -1;
        comepoint = -1;
        dice[0] = new Dice(0, 1);
        dice[1] = new Dice(0, 1);
    }

    public void actionTouch(int action){
        if(action == -2) {
            if(selector.X > -1 && GridValid(selector)){
                grid[selector.X][selector.Y] = 1;
                selector.set(-1, -1);
            }
            Spin();
        }
        if(action == -1){
            if(selector.X > -1 && GridValid(selector)){
                grid[selector.X][selector.Y] = 1;
                selector.set(-1, -1);
            }
        } else {
            selector = new Vector2(action%21, action/21);
        }
    }

    public void updateMotion(){
        if(turnstate == 3) {
            for(int i = 0; i < 2; i++) {
                if(dice[i].shiftX > 45) {
                    dice[i].Update(1, RANDOM.nextInt(6));
                } else if(dice[i].shiftX > 0) {
                    dice[i].shiftX = 0;
                    dice[i].shiftY = 0;
                } else {

                }
            }
        }
        if(turnstate == 4) {
            selector  = new Vector2(-1, -1);
        }
    }

    public void updateLogic(){

    }

    public void load2(CompoundNBT compound){
        dice = loadDice(compound);
        grid = loadGrid(compound, 21, 5);
        result = compound.getInt("result");
        point = compound.getInt("point");
        comepoint = compound.getInt("comepoint");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveDice(compound, dice);
        saveGrid(compound, 21, 5, grid);
        compound.putInt("result", result);
        compound.putInt("point", point);
        compound.putInt("comepoint", comepoint);
        return compound;
    }



    //--------------------CUSTOM--------------------

    private boolean GridValid(Vector2 v) {
        if(v.matches( 1, 0)) return false;
        if(v.matches(19, 0)) return false;
        if(v.matches( 1, 4)) return false;
        if(v.matches(19, 4)) return false;
        if(v.Y >= 1 && v.Y <= 3) {
            return v.X % 3 != 1;
        }
        return true;
    }

    private void Spin() {
        if(turnstate == 2) {
            dice[0].Set_Up(200 + RANDOM.nextInt( 50),  50 + RANDOM.nextInt(200), RANDOM.nextInt(2) == 0);
            dice[1].Set_Up( 50 + RANDOM.nextInt(200), 200 + RANDOM.nextInt( 50), RANDOM.nextInt(2) == 0);
            if(selector.X > -1) {
                //if(point != -1 && gridI[(int)selector.X][(int)selector.Y] == 0) {
                //FM.coins       -= bet;
                //FM.coins_bonus -= bet;
                //}
                grid[selector.X][selector.Y] = 1;
                selector = new Vector2(-1, -1);
            }
            turnstate = 3;
        } else if(turnstate == 3) {
            if(dice[0].shiftX == 0 && dice[1].shiftX == 0) {
                Result();
            }
        }
    }

    private void Result() {
        dice[0].Reset();
        dice[1].Reset();
        boolean hasCome = false;
        for(int i = 0; i < 5; i++) {
            if(grid[ 0][i] == 1) hasCome = true;
            if(grid[20][i] == 1) hasCome = true;
        }
        if(point == -1) {
            point  = dice[0].number + 1 + dice[1].number + 1;
            result = dice[0].number + 1 + dice[1].number + 1;
            if(result == 7 || result == 11) { // PASS
                Result_Pass(true);
                Result_DontPass(false);
                hand = "Natura Roll!";
                turnstate = 4;
            } else if(result == 2 || result == 3 || result == 12) { // DON'T PASS
                Result_Pass(false);
                Result_DontPass(true);
                hand = "Crab...";
                turnstate = 4;
            } else {
                hand = "Roll again...";
                turnstate = 2;
            }
        } else {
            result = dice[0].number + 1 + dice[1].number + 1;
            Result_SingleOdds();
            turnstate = 2;
            if(hasCome) {
                if(comepoint == -1) {
                    comepoint = result;
                } else {
                    if(result == comepoint) {
                        Result_Come(true);
                        Result_DontCome(false);
                    } else if(comepoint == 7) {
                        Result_Come(false);
                        Result_DontCome(true);
                    }
                }
            }
            if(comepoint == 7) { // DON'T PASS
                Result_Pass(false);
                Result_PassOdds(false);
                Result_DontPass(true);
                Result_DontPassOdds(true);
                hand = "SEVEN";
                turnstate = 4;
            } else if(result == point) { // PASS
                Result_Pass(false);
                Result_PassOdds(false);
                Result_DontPass(true);
                Result_DontPassOdds(true);
                hand = "POINT";
                turnstate = 4;
            }
        }
    }

    private void Result_Come(boolean won) {
        if(grid[0][0] == 1) { if(won) { grid[0][0] = 2; reward += 2; } else { grid[0][0] = 3; } }
        if(grid[0][1] == 1) { if(won) { grid[0][1] = 2; reward += 2; } else { grid[0][1] = 3; } }
        if(grid[0][2] == 1) { if(won) { grid[0][2] = 2; reward += 2; } else { grid[0][2] = 3; } }
        if(grid[0][3] == 1) { if(won) { grid[0][3] = 2; reward += 2; } else { grid[0][3] = 3; } }
        if(grid[0][4] == 1) { if(won) { grid[0][4] = 2; reward += 2; } else { grid[0][4] = 3; } }
    }

    private void Result_DontCome(boolean won) {
        if(grid[20][0] == 1) { if(won) { grid[20][0] = 2; reward += 2; } else { grid[20][0] = 3; } }
        if(grid[20][1] == 1) { if(won) { grid[20][1] = 2; reward += 2; } else { grid[20][1] = 3; } }
        if(grid[20][2] == 1) { if(won) { grid[20][2] = 2; reward += 2; } else { grid[20][2] = 3; } }
        if(grid[20][3] == 1) { if(won) { grid[20][3] = 2; reward += 2; } else { grid[20][3] = 3; } }
        if(grid[20][4] == 1) { if(won) { grid[20][4] = 2; reward += 2; } else { grid[20][4] = 3; } }
    }

    private void Result_Pass(boolean won) {
        if(grid[ 2][0] == 1) { if(won) { grid[ 2][0] = 2; reward += 2; } else { grid[ 2][0] = 3; } }
        if(grid[ 3][0] == 1) { if(won) { grid[ 3][0] = 2; reward += 2; } else { grid[ 3][0] = 3; } }
        if(grid[ 4][0] == 1) { if(won) { grid[ 4][0] = 2; reward += 2; } else { grid[ 4][0] = 3; } }
        if(grid[ 5][0] == 1) { if(won) { grid[ 5][0] = 2; reward += 2; } else { grid[ 5][0] = 3; } }
        if(grid[ 6][0] == 1) { if(won) { grid[ 6][0] = 2; reward += 2; } else { grid[ 6][0] = 3; } }
        if(grid[ 7][0] == 1) { if(won) { grid[ 7][0] = 2; reward += 2; } else { grid[ 7][0] = 3; } }
        if(grid[ 8][0] == 1) { if(won) { grid[ 8][0] = 2; reward += 2; } else { grid[ 8][0] = 3; } }
        if(grid[ 9][0] == 1) { if(won) { grid[ 9][0] = 2; reward += 2; } else { grid[ 9][0] = 3; } }
        if(grid[10][0] == 1) { if(won) { grid[10][0] = 2; reward += 2; } else { grid[10][0] = 3; } }
        if(grid[11][0] == 1) { if(won) { grid[11][0] = 2; reward += 2; } else { grid[11][0] = 3; } }
        if(grid[12][0] == 1) { if(won) { grid[12][0] = 2; reward += 2; } else { grid[12][0] = 3; } }
        if(grid[13][0] == 1) { if(won) { grid[13][0] = 2; reward += 2; } else { grid[13][0] = 3; } }
        if(grid[14][0] == 1) { if(won) { grid[14][0] = 2; reward += 2; } else { grid[14][0] = 3; } }
        if(grid[15][0] == 1) { if(won) { grid[15][0] = 2; reward += 2; } else { grid[15][0] = 3; } }
        if(grid[16][0] == 1) { if(won) { grid[16][0] = 2; reward += 2; } else { grid[16][0] = 3; } }
        if(grid[17][0] == 1) { if(won) { grid[17][0] = 2; reward += 2; } else { grid[17][0] = 3; } }
        if(grid[18][0] == 1) { if(won) { grid[18][0] = 2; reward += 2; } else { grid[18][0] = 3; } }
    }

    private void Result_DontPass(boolean won) {
        if(grid[ 2][4] == 1) { if(won) { grid[ 2][4] = 2; reward += 2; } else { grid[ 2][4] = 3; } }
        if(grid[ 3][4] == 1) { if(won) { grid[ 3][4] = 2; reward += 2; } else { grid[ 3][4] = 3; } }
        if(grid[ 4][4] == 1) { if(won) { grid[ 4][4] = 2; reward += 2; } else { grid[ 4][4] = 3; } }
        if(grid[ 5][4] == 1) { if(won) { grid[ 5][4] = 2; reward += 2; } else { grid[ 5][4] = 3; } }
        if(grid[ 6][4] == 1) { if(won) { grid[ 6][4] = 2; reward += 2; } else { grid[ 6][4] = 3; } }
        if(grid[ 7][4] == 1) { if(won) { grid[ 7][4] = 2; reward += 2; } else { grid[ 7][4] = 3; } }
        if(grid[ 8][4] == 1) { if(won) { grid[ 8][4] = 2; reward += 2; } else { grid[ 8][4] = 3; } }
        if(grid[ 9][4] == 1) { if(won) { grid[ 9][4] = 2; reward += 2; } else { grid[ 9][4] = 3; } }
        if(grid[10][4] == 1) { if(won) { grid[10][4] = 2; reward += 2; } else { grid[10][4] = 3; } }
        if(grid[11][4] == 1) { if(won) { grid[11][4] = 2; reward += 2; } else { grid[11][4] = 3; } }
        if(grid[12][4] == 1) { if(won) { grid[12][4] = 2; reward += 2; } else { grid[12][4] = 3; } }
        if(grid[13][4] == 1) { if(won) { grid[13][4] = 2; reward += 2; } else { grid[13][4] = 3; } }
        if(grid[14][4] == 1) { if(won) { grid[14][4] = 2; reward += 2; } else { grid[14][4] = 3; } }
        if(grid[15][4] == 1) { if(won) { grid[15][4] = 2; reward += 2; } else { grid[15][4] = 3; } }
        if(grid[16][4] == 1) { if(won) { grid[16][4] = 2; reward += 2; } else { grid[16][4] = 3; } }
        if(grid[17][4] == 1) { if(won) { grid[17][4] = 2; reward += 2; } else { grid[17][4] = 3; } }
        if(grid[18][4] == 1) { if(won) { grid[18][4] = 2; reward += 2; } else { grid[18][4] = 3; } }
    }

    private void Result_PassOdds(boolean won) {
        if(grid[ 2][1] == 1) { if(won) { if(result ==  4) { grid[ 2][1] = 2; reward += 2; } else { grid[ 2][1] = 3; } } else { grid[ 2][1] = 3; } }
        if(grid[ 3][1] == 1) { if(won) { if(result ==  4) { grid[ 3][1] = 2; reward += 2; } else { grid[ 3][1] = 3; } } else { grid[ 3][1] = 3; } }
        if(grid[ 5][1] == 1) { if(won) { if(result ==  5) { grid[ 5][1] = 2; reward += 2; } else { grid[ 5][1] = 3; } } else { grid[ 5][1] = 3; } }
        if(grid[ 6][1] == 1) { if(won) { if(result ==  5) { grid[ 6][1] = 2; reward += 2; } else { grid[ 6][1] = 3; } } else { grid[ 6][1] = 3; } }
        if(grid[ 8][1] == 1) { if(won) { if(result ==  6) { grid[ 8][1] = 2; reward += 2; } else { grid[ 8][1] = 3; } } else { grid[ 8][1] = 3; } }
        if(grid[ 9][1] == 1) { if(won) { if(result ==  6) { grid[ 9][1] = 2; reward += 2; } else { grid[ 9][1] = 3; } } else { grid[ 9][1] = 3; } }
        if(grid[11][1] == 1) { if(won) { if(result ==  8) { grid[11][1] = 2; reward += 2; } else { grid[11][1] = 3; } } else { grid[11][1] = 3; } }
        if(grid[12][1] == 1) { if(won) { if(result ==  8) { grid[12][1] = 2; reward += 2; } else { grid[12][1] = 3; } } else { grid[12][1] = 3; } }
        if(grid[14][1] == 1) { if(won) { if(result ==  9) { grid[14][1] = 2; reward += 2; } else { grid[14][1] = 3; } } else { grid[14][1] = 3; } }
        if(grid[15][1] == 1) { if(won) { if(result ==  9) { grid[15][1] = 2; reward += 2; } else { grid[15][1] = 3; } } else { grid[15][1] = 3; } }
        if(grid[17][1] == 1) { if(won) { if(result == 10) { grid[17][1] = 2; reward += 2; } else { grid[17][1] = 3; } } else { grid[17][1] = 3; } }
        if(grid[18][1] == 1) { if(won) { if(result == 10) { grid[18][1] = 2; reward += 2; } else { grid[18][1] = 3; } } else { grid[18][1] = 3; } }
    }

    private void Result_DontPassOdds(boolean won) {
        if(grid[ 2][3] == 1) { if(won) { if(result ==  4) { grid[ 2][3] = 2; reward += 2; } else { grid[ 2][3] = 3; } } else { grid[ 2][3] = 3; } }
        if(grid[ 3][3] == 1) { if(won) { if(result ==  4) { grid[ 3][3] = 2; reward += 2; } else { grid[ 3][3] = 3; } } else { grid[ 3][3] = 3; } }
        if(grid[ 5][3] == 1) { if(won) { if(result ==  5) { grid[ 5][3] = 2; reward += 2; } else { grid[ 5][3] = 3; } } else { grid[ 5][3] = 3; } }
        if(grid[ 6][3] == 1) { if(won) { if(result ==  5) { grid[ 6][3] = 2; reward += 2; } else { grid[ 6][3] = 3; } } else { grid[ 6][3] = 3; } }
        if(grid[ 8][3] == 1) { if(won) { if(result ==  6) { grid[ 8][3] = 2; reward += 2; } else { grid[ 8][3] = 3; } } else { grid[ 8][3] = 3; } }
        if(grid[ 9][3] == 1) { if(won) { if(result ==  6) { grid[ 9][3] = 2; reward += 2; } else { grid[ 9][3] = 3; } } else { grid[ 9][3] = 3; } }
        if(grid[11][3] == 1) { if(won) { if(result ==  8) { grid[11][3] = 2; reward += 2; } else { grid[11][3] = 3; } } else { grid[11][3] = 3; } }
        if(grid[12][3] == 1) { if(won) { if(result ==  8) { grid[12][3] = 2; reward += 2; } else { grid[12][3] = 3; } } else { grid[12][3] = 3; } }
        if(grid[14][3] == 1) { if(won) { if(result ==  9) { grid[14][3] = 2; reward += 2; } else { grid[14][3] = 3; } } else { grid[14][3] = 3; } }
        if(grid[15][3] == 1) { if(won) { if(result ==  9) { grid[15][3] = 2; reward += 2; } else { grid[15][3] = 3; } } else { grid[15][3] = 3; } }
        if(grid[17][3] == 1) { if(won) { if(result == 10) { grid[17][3] = 2; reward += 2; } else { grid[17][3] = 3; } } else { grid[17][3] = 3; } }
        if(grid[18][3] == 1) { if(won) { if(result == 10) { grid[18][3] = 2; reward += 2; } else { grid[18][3] = 3; } } else { grid[18][3] = 3; } }
    }

    private void Result_SingleOdds() {
        if(grid[ 2][2] == 1) { if(result ==  4) { grid[ 2][2] = 2; reward += 10; } else { grid[ 2][2] = 3; } }
        if(grid[ 3][2] == 1) { if(result ==  4) { grid[ 3][2] = 2; reward += 10; } else { grid[ 3][2] = 3; } }
        if(grid[ 5][2] == 1) { if(result ==  5) { grid[ 5][2] = 2; reward +=  8; } else { grid[ 5][2] = 3; } }
        if(grid[ 6][2] == 1) { if(result ==  5) { grid[ 6][2] = 2; reward +=  8; } else { grid[ 6][2] = 3; } }
        if(grid[ 8][2] == 1) { if(result ==  6) { grid[ 8][2] = 2; reward +=  6; } else { grid[ 8][2] = 3; } }
        if(grid[ 9][2] == 1) { if(result ==  6) { grid[ 9][2] = 2; reward +=  6; } else { grid[ 9][2] = 3; } }
        if(grid[11][2] == 1) { if(result ==  8) { grid[11][2] = 2; reward +=  6; } else { grid[11][2] = 3; } }
        if(grid[12][2] == 1) { if(result ==  8) { grid[12][2] = 2; reward +=  6; } else { grid[12][2] = 3; } }
        if(grid[14][2] == 1) { if(result ==  9) { grid[14][2] = 2; reward +=  8; } else { grid[14][2] = 3; } }
        if(grid[15][2] == 1) { if(result ==  9) { grid[15][2] = 2; reward +=  8; } else { grid[15][2] = 3; } }
        if(grid[17][2] == 1) { if(result == 10) { grid[17][2] = 2; reward += 10; } else { grid[17][2] = 3; } }
        if(grid[18][2] == 1) { if(result == 10) { grid[18][2] = 2; reward += 10; } else { grid[18][2] = 3; } }
    }

}
