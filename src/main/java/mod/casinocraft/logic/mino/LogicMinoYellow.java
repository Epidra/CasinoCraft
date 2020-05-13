package mod.casinocraft.logic.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Dice;
import mod.casinocraft.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

public class LogicMinoYellow extends LogicBase {   // SicBo

    public Dice[] dice = new Dice[3];




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoYellow(int tableID){
        super(tableID, 12, 6);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        selector = new Vector2(5, 2);
        dice[0] = new Dice(0, 4);
        dice[1] = new Dice(0, 4);
        dice[2] = new Dice(0, 4);
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
            int x = action % 12;
            int y = action / 12;
            if(grid[x][y] == 0){
                selector.set(x, y);
            }
        }
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
            for(int i = 0; i < 3; i++) {
                if(dice[i].shiftX > 45) {
                    dice[i].Update(1, RANDOM.nextInt(6));
                } else if(dice[i].shiftX > 0) {
                    dice[i].shiftX = 0;
                    dice[i].shiftY = 0;
                }
            }
        }
        if(turnstate == 4) {
            selector = new Vector2(-1, -1);
        }
    }

    public void updateMotion(){

    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        dice = loadDice(compound);
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveDice(compound, dice);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void spin() {
        if(turnstate == 2) {
            activePlayer++;
            if(activePlayer >= getFirstFreePlayerSlot()){
                dice[0].setUp(200 + RANDOM.nextInt( 50),  50 + RANDOM.nextInt(200), RANDOM.nextInt(2) == 0);
                dice[1].setUp(100 + RANDOM.nextInt(100), 100 + RANDOM.nextInt(100), RANDOM.nextInt(2) == 0);
                dice[2].setUp( 50 + RANDOM.nextInt(200), 200 + RANDOM.nextInt( 50), RANDOM.nextInt(2) == 0);
                turnstate = 3;
            }

        } else if(turnstate == 3) {
            if(dice[0].shiftX == 0 && dice[1].shiftX == 0 && dice[2].shiftX == 0) {
                result();
            }
        }
    }

    private void result() {
        hand = "" + (dice[0].number + 1) + "-" + (dice[1].number + 1) + "-" + (dice[2].number + 1);
        if(grid[ 0][0] > 0) { if(resultValue() <= 10 && !resultAnyTriple())  { reward[grid[ 0][0]-1] +=  2; } else { grid[ 0][0] = 0; } }
        if(grid[ 1][0] > 0) { if(resultValue() <= 10 && !resultAnyTriple())  { reward[grid[ 1][0]-1] +=  2; } else { grid[ 1][0] = 0; } }
        if(grid[ 2][0] > 0) { if(resultValue() <= 10 && !resultAnyTriple())  { reward[grid[ 2][0]-1] +=  2; } else { grid[ 2][0] = 0; } }
        if(grid[ 3][0] > 0) { if(resultValue() <= 10 && !resultAnyTriple())  { reward[grid[ 3][0]-1] +=  2; } else { grid[ 3][0] = 0; } }
        if(grid[ 4][0] > 0) { if(resultAnyTriple()                         ) { reward[grid[ 4][0]-1] += 31; } else { grid[ 4][0] = 0; } }
        if(grid[ 5][0] > 0) { if(resultAnyTriple()                         ) { reward[grid[ 5][0]-1] += 31; } else { grid[ 5][0] = 0; } }
        if(grid[ 6][0] > 0) { if(resultAnyTriple()                         ) { reward[grid[ 6][0]-1] += 31; } else { grid[ 6][0] = 0; } }
        if(grid[ 7][0] > 0) { if(resultAnyTriple()                         ) { reward[grid[ 7][0]-1] += 31; } else { grid[ 7][0] = 0; } }
        if(grid[ 8][0] > 0) { if(resultValue() <= 10 && !resultAnyTriple())  { reward[grid[ 8][0]-1] +=  2; } else { grid[ 8][0] = 0; } }
        if(grid[ 9][0] > 0) { if(resultValue() <= 10 && !resultAnyTriple())  { reward[grid[ 9][0]-1] +=  2; } else { grid[ 9][0] = 0; } }
        if(grid[10][0] > 0) { if(resultValue() <= 10 && !resultAnyTriple())  { reward[grid[10][0]-1] +=  2; } else { grid[10][0] = 0; } }
        if(grid[11][0] > 0) { if(resultValue() <= 10 && !resultAnyTriple())  { reward[grid[11][0]-1] +=  2; } else { grid[11][0] = 0; } }

        if(grid[ 0][1] > 0) { if(resultTriple(0)                           ) { reward[grid[ 0][1]-1] += 181; } else { grid[ 0][1] = 0; } }
        if(grid[ 1][1] > 0) { if(resultTriple(0)                           ) { reward[grid[ 1][1]-1] += 181; } else { grid[ 1][1] = 0; } }
        if(grid[ 2][1] > 0) { if(resultTriple(1)                           ) { reward[grid[ 2][1]-1] += 181; } else { grid[ 2][1] = 0; } }
        if(grid[ 3][1] > 0) { if(resultTriple(1)                           ) { reward[grid[ 3][1]-1] += 181; } else { grid[ 3][1] = 0; } }
        if(grid[ 4][1] > 0) { if(resultTriple(2)                           ) { reward[grid[ 4][1]-1] += 181; } else { grid[ 4][1] = 0; } }
        if(grid[ 5][1] > 0) { if(resultTriple(2)                           ) { reward[grid[ 5][1]-1] += 181; } else { grid[ 5][1] = 0; } }
        if(grid[ 6][1] > 0) { if(resultTriple(3)                           ) { reward[grid[ 6][1]-1] += 181; } else { grid[ 6][1] = 0; } }
        if(grid[ 7][1] > 0) { if(resultTriple(3)                           ) { reward[grid[ 7][1]-1] += 181; } else { grid[ 7][1] = 0; } }
        if(grid[ 8][1] > 0) { if(resultTriple(4)                           ) { reward[grid[ 8][1]-1] += 181; } else { grid[ 8][1] = 0; } }
        if(grid[ 9][1] > 0) { if(resultTriple(4)                           ) { reward[grid[ 9][1]-1] += 181; } else { grid[ 9][1] = 0; } }
        if(grid[10][1] > 0) { if(resultTriple(5)                           ) { reward[grid[10][1]-1] += 181; } else { grid[10][1] = 0; } }
        if(grid[11][1] > 0) { if(resultTriple(5)                           ) { reward[grid[11][1]-1] += 181; } else { grid[11][1] = 0; } }

        if(grid[ 0][2] > 0) { if(resultValue() ==  4                       ) { reward[grid[ 0][2]-1] += 61; } else { grid[ 0][2] = 0; } }
        if(grid[ 1][2] > 0) { if(resultValue() ==  5                       ) { reward[grid[ 1][2]-1] += 21; } else { grid[ 1][2] = 0; } }
        if(grid[ 2][2] > 0) { if(resultValue() ==  6                       ) { reward[grid[ 2][2]-1] += 19; } else { grid[ 2][2] = 0; } }
        if(grid[ 3][2] > 0) { if(resultValue() ==  7                       ) { reward[grid[ 3][2]-1] += 13; } else { grid[ 3][2] = 0; } }
        if(grid[ 4][2] > 0) { if(resultValue() ==  8                       ) { reward[grid[ 4][2]-1] +=  9; } else { grid[ 4][2] = 0; } }
        if(grid[ 5][2] > 0) { if(resultDouble(0, 0)                        ) { reward[grid[ 5][2]-1] += 12; } else { grid[ 5][2] = 0; } }
        if(grid[ 6][2] > 0) { if(resultDouble(0, 1)                        ) { reward[grid[ 6][2]-1] +=  7; } else { grid[ 6][2] = 0; } }
        if(grid[ 7][2] > 0) { if(resultDouble(0, 2)                        ) { reward[grid[ 7][2]-1] +=  7; } else { grid[ 7][2] = 0; } }
        if(grid[ 8][2] > 0) { if(resultDouble(0, 3)                        ) { reward[grid[ 8][2]-1] +=  7; } else { grid[ 8][2] = 0; } }
        if(grid[ 9][2] > 0) { if(resultDouble(0, 4)                        ) { reward[grid[ 9][2]-1] +=  7; } else { grid[ 9][2] = 0; } }
        if(grid[10][2] > 0) { if(resultDouble(0, 5)                        ) { reward[grid[10][2]-1] +=  7; } else { grid[10][2] = 0; } }
        if(grid[11][2] > 0) { if(resultDouble(1, 1)                        ) { reward[grid[11][2]-1] += 12; } else { grid[11][2] = 0; } }

        if(grid[ 0][3] > 0) { if(resultValue() ==  9                       ) { reward[grid[ 0][3]-1] +=  7; } else { grid[ 0][3] = 0; } }
        if(grid[ 1][3] > 0) { if(resultValue() == 10                       ) { reward[grid[ 1][3]-1] +=  7; } else { grid[ 1][3] = 0; } }
        if(grid[ 2][3] > 0) { if(resultValue() == 11                       ) { reward[grid[ 2][3]-1] +=  7; } else { grid[ 2][3] = 0; } }
        if(grid[ 3][3] > 0) { if(resultValue() == 12                       ) { reward[grid[ 3][3]-1] +=  7; } else { grid[ 3][3] = 0; } }
        if(grid[ 4][3] > 0) { if(resultValue() == 13                       ) { reward[grid[ 4][3]-1] +=  7; } else { grid[ 4][3] = 0; } }
        if(grid[ 5][3] > 0) { if(resultDouble(1, 2)                        ) { reward[grid[ 5][3]-1] +=  7; } else { grid[ 5][3] = 0; } }
        if(grid[ 6][3] > 0) { if(resultDouble(1, 3)                        ) { reward[grid[ 6][3]-1] +=  7; } else { grid[ 6][3] = 0; } }
        if(grid[ 7][3] > 0) { if(resultDouble(1, 4)                        ) { reward[grid[ 7][3]-1] +=  7; } else { grid[ 7][3] = 0; } }
        if(grid[ 8][3] > 0) { if(resultDouble(1, 5)                        ) { reward[grid[ 8][3]-1] +=  7; } else { grid[ 8][3] = 0; } }
        if(grid[ 9][3] > 0) { if(resultDouble(2, 2)                        ) { reward[grid[ 9][3]-1] += 12; } else { grid[ 9][3] = 0; } }
        if(grid[10][3] > 0) { if(resultDouble(2, 3)                        ) { reward[grid[10][3]-1] +=  7; } else { grid[10][3] = 0; } }
        if(grid[11][3] > 0) { if(resultDouble(2, 4)                        ) { reward[grid[11][3]-1] +=  7; } else { grid[11][3] = 0; } }

        if(grid[ 0][4] > 0) { if(resultValue() == 14                       ) { reward[grid[ 0][4]-1] +=  7; } else { grid[ 0][4] = 0; } }
        if(grid[ 1][4] > 0) { if(resultValue() == 15                       ) { reward[grid[ 1][4]-1] +=  7; } else { grid[ 1][4] = 0; } }
        if(grid[ 2][4] > 0) { if(resultValue() == 16                       ) { reward[grid[ 2][4]-1] +=  7; } else { grid[ 2][4] = 0; } }
        if(grid[ 3][4] > 0) { if(resultValue() == 17                       ) { reward[grid[ 3][4]-1] +=  7; } else { grid[ 3][4] = 0; } }
        if(grid[ 4][4] > 0) {                                                                                        grid[ 4][4] = 0;   }
        if(grid[ 5][4] > 0) { if(resultDouble(2, 5)                        ) { reward[grid[ 5][4]-1] +=  7; } else { grid[ 5][4] = 0; } }
        if(grid[ 6][4] > 0) { if(resultDouble(3, 3)                        ) { reward[grid[ 6][4]-1] += 12; } else { grid[ 6][4] = 0; } }
        if(grid[ 7][4] > 0) { if(resultDouble(3, 4)                        ) { reward[grid[ 7][4]-1] +=  7; } else { grid[ 7][4] = 0; } }
        if(grid[ 8][4] > 0) { if(resultDouble(3, 5)                        ) { reward[grid[ 8][4]-1] +=  7; } else { grid[ 8][4] = 0; } }
        if(grid[ 9][4] > 0) { if(resultDouble(4, 4)                        ) { reward[grid[ 9][4]-1] += 12; } else { grid[ 9][4] = 0; } }
        if(grid[10][4] > 0) { if(resultDouble(4, 5)                        ) { reward[grid[10][4]-1] +=  7; } else { grid[10][4] = 0; } }
        if(grid[11][4] > 0) { if(resultDouble(5, 5)                        ) { reward[grid[11][4]-1] += 12; } else { grid[11][4] = 0; } }

        if(grid[ 0][5] > 0) { if(resultTriple(0)) { reward[grid[ 0][5]-1] += 6; } else if(resultDouble(0, 0)) { reward[grid[ 0][5]-1] += 3; } else if(resultSingle(0)) { reward[grid[ 0][5]-1] += 2; } else { grid[ 0][5] = 0; } }
        if(grid[ 1][5] > 0) { if(resultTriple(0)) { reward[grid[ 1][5]-1] += 6; } else if(resultDouble(0, 0)) { reward[grid[ 1][5]-1] += 3; } else if(resultSingle(0)) { reward[grid[ 1][5]-1] += 2; } else { grid[ 1][5] = 0; } }
        if(grid[ 2][5] > 0) { if(resultTriple(1)) { reward[grid[ 2][5]-1] += 6; } else if(resultDouble(1, 1)) { reward[grid[ 2][5]-1] += 3; } else if(resultSingle(1)) { reward[grid[ 2][5]-1] += 2; } else { grid[ 2][5] = 0; } }
        if(grid[ 3][5] > 0) { if(resultTriple(1)) { reward[grid[ 3][5]-1] += 6; } else if(resultDouble(1, 1)) { reward[grid[ 3][5]-1] += 3; } else if(resultSingle(1)) { reward[grid[ 3][5]-1] += 2; } else { grid[ 3][5] = 0; } }
        if(grid[ 4][5] > 0) { if(resultTriple(2)) { reward[grid[ 4][5]-1] += 6; } else if(resultDouble(2, 2)) { reward[grid[ 4][5]-1] += 3; } else if(resultSingle(2)) { reward[grid[ 4][5]-1] += 2; } else { grid[ 4][5] = 0; } }
        if(grid[ 5][5] > 0) { if(resultTriple(2)) { reward[grid[ 5][5]-1] += 6; } else if(resultDouble(2, 2)) { reward[grid[ 5][5]-1] += 3; } else if(resultSingle(2)) { reward[grid[ 5][5]-1] += 2; } else { grid[ 5][5] = 0; } }
        if(grid[ 6][5] > 0) { if(resultTriple(3)) { reward[grid[ 6][5]-1] += 6; } else if(resultDouble(3, 3)) { reward[grid[ 6][5]-1] += 3; } else if(resultSingle(3)) { reward[grid[ 6][5]-1] += 2; } else { grid[ 6][5] = 0; } }
        if(grid[ 7][5] > 0) { if(resultTriple(3)) { reward[grid[ 7][5]-1] += 6; } else if(resultDouble(3, 3)) { reward[grid[ 7][5]-1] += 3; } else if(resultSingle(3)) { reward[grid[ 7][5]-1] += 2; } else { grid[ 7][5] = 0; } }
        if(grid[ 8][5] > 0) { if(resultTriple(4)) { reward[grid[ 8][5]-1] += 6; } else if(resultDouble(4, 4)) { reward[grid[ 8][5]-1] += 3; } else if(resultSingle(4)) { reward[grid[ 8][5]-1] += 2; } else { grid[ 8][5] = 0; } }
        if(grid[ 9][5] > 0) { if(resultTriple(4)) { reward[grid[ 9][5]-1] += 6; } else if(resultDouble(4, 4)) { reward[grid[ 9][5]-1] += 3; } else if(resultSingle(4)) { reward[grid[ 9][5]-1] += 2; } else { grid[ 9][5] = 0; } }
        if(grid[10][5] > 0) { if(resultTriple(5)) { reward[grid[10][5]-1] += 6; } else if(resultDouble(5, 5)) { reward[grid[10][5]-1] += 3; } else if(resultSingle(5)) { reward[grid[10][5]-1] += 2; } else { grid[10][5] = 0; } }
        if(grid[11][5] > 0) { if(resultTriple(5)) { reward[grid[11][5]-1] += 6; } else if(resultDouble(5, 5)) { reward[grid[11][5]-1] += 3; } else if(resultSingle(5)) { reward[grid[11][5]-1] += 2; } else { grid[11][5] = 0; } }

        turnstate = 4;
    }

    private int resultValue() {
        return dice[0].number + dice[1].number + dice[2].number + 3;
    }

    private boolean resultSingle(int n) {
        if(dice[0].number == n) return true;
        if(dice[1].number == n) return true;
        return dice[2].number == n;
    }

    private boolean resultDouble(int n1, int n2) {
        if(n1 == n2) return resultDouble(n1);
        return resultSingle(n1) && resultSingle(n2);
    }

    private boolean resultDouble(int n){
        if(dice[0].number == n && dice[1].number == n) return true;
        if(dice[0].number == n && dice[2].number == n) return true;
        return (dice[1].number == n && dice[2].number == n);
    }

    private boolean resultTriple(int n) {
        return dice[0].number == dice[1].number && dice[0].number == dice[2].number && dice[0].number == n;
    }

    private boolean resultAnyTriple() {
        return dice[0].number == dice[1].number && dice[0].number == dice[2].number;
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return true;
    }

    public int getID(){
        return 47;
    }

}
