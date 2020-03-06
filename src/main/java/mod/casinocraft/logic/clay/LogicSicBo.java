package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Dice;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

public class LogicSicBo extends LogicBase {

    public int grid[][] = new int[12][6];

    public Dice[] dice = new Dice[3];



    //--------------------CONSTRUCTOR--------------------

    public LogicSicBo(int table){
        super(false, table, "c_sicbo");
    }



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 6; y++) {
            for(int x = 0; x < 12; x++) {
                grid[x][y] = 0;
            }
        }
        selector = new Vector2(-1, -1);
        dice[0] = new Dice(0, 4);
        dice[1] = new Dice(0, 4);
        dice[2] = new Dice(0, 4);
    }

    public void actionTouch(int action){
        if(action == -2) {
            if(selector.X > -1){
                grid[selector.X][selector.Y] = 1;
                selector.set(-1, -1);
            }
            Spin();
        } else if(action == -1){
            if(selector.X > -1){
                grid[selector.X][selector.Y] = 1;
                selector.set(-1, -1);
            }
        } else {
            selector = new Vector2(action%12, action/12);
        }
    }

    public void updateLogic(){
        if(turnstate == 3) {
            for(int i = 0; i < 3; i++) {
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
            selector = new Vector2(-1, -1);
        }
    }

    public void updateMotion(){

    }

    public void load2(CompoundNBT compound){

        grid = loadGrid(compound, 12, 6);
        dice = loadDice(compound);
    }

    public CompoundNBT save2(CompoundNBT compound){

        saveGrid(compound, 12, 6, grid);
        saveDice(compound, dice);
        return compound;
    }



    //--------------------CUSTOM--------------------

    private void Spin() {
        if(turnstate == 2) {
            dice[0].Set_Up(200 + RANDOM.nextInt( 50),  50 + RANDOM.nextInt(200), RANDOM.nextInt(2) == 0);
            dice[1].Set_Up(100 + RANDOM.nextInt(100), 100 + RANDOM.nextInt(100), RANDOM.nextInt(2) == 0);
            dice[2].Set_Up( 50 + RANDOM.nextInt(200), 200 + RANDOM.nextInt( 50), RANDOM.nextInt(2) == 0);
            turnstate = 3;
        } else if(turnstate == 3) {
            if(dice[0].shiftX == 0 && dice[0].shiftX == 0 && dice[0].shiftX == 0) {
                Result();
            }
        }
    }

    private void Result() {
        hand = "" + (dice[0].number + 1) + "-" + (dice[1].number + 1) + "-" + (dice[2].number + 1);
        if(grid[ 0][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=  2; grid[ 0][0] = 2; } else { grid[ 0][0] = 3; } }
        if(grid[ 1][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=  2; grid[ 1][0] = 2; } else { grid[ 1][0] = 3; } }
        if(grid[ 2][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=  2; grid[ 2][0] = 2; } else { grid[ 2][0] = 3; } }
        if(grid[ 3][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=  2; grid[ 3][0] = 2; } else { grid[ 3][0] = 3; } }
        if(grid[ 4][0] == 1) { if(Result_AnyTriple()                         ) { reward += 31; grid[ 4][0] = 2; } else { grid[ 4][0] = 3; } }
        if(grid[ 5][0] == 1) { if(Result_AnyTriple()                         ) { reward += 31; grid[ 5][0] = 2; } else { grid[ 5][0] = 3; } }
        if(grid[ 6][0] == 1) { if(Result_AnyTriple()                         ) { reward += 31; grid[ 6][0] = 2; } else { grid[ 6][0] = 3; } }
        if(grid[ 7][0] == 1) { if(Result_AnyTriple()                         ) { reward += 31; grid[ 7][0] = 2; } else { grid[ 7][0] = 3; } }
        if(grid[ 8][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=  2; grid[ 8][0] = 2; } else { grid[ 8][0] = 3; } }
        if(grid[ 9][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=  2; grid[ 9][0] = 2; } else { grid[ 9][0] = 3; } }
        if(grid[10][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=  2; grid[10][0] = 2; } else { grid[10][0] = 3; } }
        if(grid[11][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=  2; grid[11][0] = 2; } else { grid[11][0] = 3; } }

        if(grid[ 0][1] == 1) { if(Result_Triple(0)                           ) { reward += 181; grid[ 0][1] = 2; } else { grid[ 0][1] = 3; } }
        if(grid[ 1][1] == 1) { if(Result_Triple(0)                           ) { reward += 181; grid[ 1][1] = 2; } else { grid[ 1][1] = 3; } }
        if(grid[ 2][1] == 1) { if(Result_Triple(1)                           ) { reward += 181; grid[ 2][1] = 2; } else { grid[ 2][1] = 3; } }
        if(grid[ 3][1] == 1) { if(Result_Triple(1)                           ) { reward += 181; grid[ 3][1] = 2; } else { grid[ 3][1] = 3; } }
        if(grid[ 4][1] == 1) { if(Result_Triple(2)                           ) { reward += 181; grid[ 4][1] = 2; } else { grid[ 4][1] = 3; } }
        if(grid[ 5][1] == 1) { if(Result_Triple(2)                           ) { reward += 181; grid[ 5][1] = 2; } else { grid[ 5][1] = 3; } }
        if(grid[ 6][1] == 1) { if(Result_Triple(3)                           ) { reward += 181; grid[ 6][1] = 2; } else { grid[ 6][1] = 3; } }
        if(grid[ 7][1] == 1) { if(Result_Triple(3)                           ) { reward += 181; grid[ 7][1] = 2; } else { grid[ 7][1] = 3; } }
        if(grid[ 8][1] == 1) { if(Result_Triple(4)                           ) { reward += 181; grid[ 8][1] = 2; } else { grid[ 8][1] = 3; } }
        if(grid[ 9][1] == 1) { if(Result_Triple(4)                           ) { reward += 181; grid[ 9][1] = 2; } else { grid[ 9][1] = 3; } }
        if(grid[10][1] == 1) { if(Result_Triple(5)                           ) { reward += 181; grid[10][1] = 2; } else { grid[10][1] = 3; } }
        if(grid[11][1] == 1) { if(Result_Triple(5)                           ) { reward += 181; grid[11][1] = 2; } else { grid[11][1] = 3; } }

        if(grid[ 0][2] == 1) { if(Result_Value() ==  4                       ) { reward += 61; grid[ 0][2] = 2; } else { grid[ 0][2] = 3; } }
        if(grid[ 1][2] == 1) { if(Result_Value() ==  5                       ) { reward += 21; grid[ 1][2] = 2; } else { grid[ 1][2] = 3; } }
        if(grid[ 2][2] == 1) { if(Result_Value() ==  6                       ) { reward += 19; grid[ 2][2] = 2; } else { grid[ 2][2] = 3; } }
        if(grid[ 3][2] == 1) { if(Result_Value() ==  7                       ) { reward += 13; grid[ 3][2] = 2; } else { grid[ 3][2] = 3; } }
        if(grid[ 4][2] == 1) { if(Result_Value() ==  8                       ) { reward +=  9; grid[ 4][2] = 2; } else { grid[ 4][2] = 3; } }
        if(grid[ 5][2] == 1) { if(Result_Double(0, 0)                        ) { reward += 12; grid[ 5][2] = 2; } else { grid[ 5][2] = 3; } }
        if(grid[ 6][2] == 1) { if(Result_Double(0, 1)                        ) { reward +=  7; grid[ 6][2] = 2; } else { grid[ 6][2] = 3; } }
        if(grid[ 7][2] == 1) { if(Result_Double(0, 2)                        ) { reward +=  7; grid[ 7][2] = 2; } else { grid[ 7][2] = 3; } }
        if(grid[ 8][2] == 1) { if(Result_Double(0, 3)                        ) { reward +=  7; grid[ 8][2] = 2; } else { grid[ 8][2] = 3; } }
        if(grid[ 9][2] == 1) { if(Result_Double(0, 4)                        ) { reward +=  7; grid[ 9][2] = 2; } else { grid[ 9][2] = 3; } }
        if(grid[10][2] == 1) { if(Result_Double(0, 5)                        ) { reward +=  7; grid[10][2] = 2; } else { grid[10][2] = 3; } }
        if(grid[11][2] == 1) { if(Result_Double(1, 1)                        ) { reward += 12; grid[11][2] = 2; } else { grid[11][2] = 3; } }

        if(grid[ 0][3] == 1) { if(Result_Value() ==  9                       ) { reward +=  7; grid[ 0][3] = 2; } else { grid[ 0][3] = 3; } }
        if(grid[ 1][3] == 1) { if(Result_Value() == 10                       ) { reward +=  7; grid[ 1][3] = 2; } else { grid[ 1][3] = 3; } }
        if(grid[ 2][3] == 1) { if(Result_Value() == 11                       ) { reward +=  7; grid[ 2][3] = 2; } else { grid[ 2][3] = 3; } }
        if(grid[ 3][3] == 1) { if(Result_Value() == 12                       ) { reward +=  7; grid[ 3][3] = 2; } else { grid[ 3][3] = 3; } }
        if(grid[ 4][3] == 1) { if(Result_Value() == 13                       ) { reward +=  7; grid[ 4][3] = 2; } else { grid[ 4][3] = 3; } }
        if(grid[ 5][3] == 1) { if(Result_Double(1, 2)                        ) { reward +=  7; grid[ 5][3] = 2; } else { grid[ 5][3] = 3; } }
        if(grid[ 6][3] == 1) { if(Result_Double(1, 3)                        ) { reward +=  7; grid[ 6][3] = 2; } else { grid[ 6][3] = 3; } }
        if(grid[ 7][3] == 1) { if(Result_Double(1, 4)                        ) { reward +=  7; grid[ 7][3] = 2; } else { grid[ 7][3] = 3; } }
        if(grid[ 8][3] == 1) { if(Result_Double(1, 5)                        ) { reward +=  7; grid[ 8][3] = 2; } else { grid[ 8][3] = 3; } }
        if(grid[ 9][3] == 1) { if(Result_Double(2, 2)                        ) { reward += 12; grid[ 9][3] = 2; } else { grid[ 9][3] = 3; } }
        if(grid[10][3] == 1) { if(Result_Double(2, 3)                        ) { reward +=  7; grid[10][3] = 2; } else { grid[10][3] = 3; } }
        if(grid[11][3] == 1) { if(Result_Double(2, 4)                        ) { reward +=  7; grid[11][3] = 2; } else { grid[11][3] = 3; } }

        if(grid[ 0][4] == 1) { if(Result_Value() == 14                       ) { reward +=  7; grid[ 0][4] = 2; } else { grid[ 0][4] = 3; } }
        if(grid[ 1][4] == 1) { if(Result_Value() == 15                       ) { reward +=  7; grid[ 1][4] = 2; } else { grid[ 1][4] = 3; } }
        if(grid[ 2][4] == 1) { if(Result_Value() == 16                       ) { reward +=  7; grid[ 2][4] = 2; } else { grid[ 2][4] = 3; } }
        if(grid[ 3][4] == 1) { if(Result_Value() == 17                       ) { reward +=  7; grid[ 3][4] = 2; } else { grid[ 3][4] = 3; } }
        if(grid[ 4][4] == 1) {                                                                                           grid[ 4][4] = 3;   }
        if(grid[ 5][4] == 1) { if(Result_Double(2, 5)                        ) { reward +=  7; grid[ 5][4] = 2; } else { grid[ 5][4] = 3; } }
        if(grid[ 6][4] == 1) { if(Result_Double(3, 3)                        ) { reward += 12; grid[ 6][4] = 2; } else { grid[ 6][4] = 3; } }
        if(grid[ 7][4] == 1) { if(Result_Double(3, 4)                        ) { reward +=  7; grid[ 7][4] = 2; } else { grid[ 7][4] = 3; } }
        if(grid[ 8][4] == 1) { if(Result_Double(3, 5)                        ) { reward +=  7; grid[ 8][4] = 2; } else { grid[ 8][4] = 3; } }
        if(grid[ 9][4] == 1) { if(Result_Double(4, 4)                        ) { reward += 12; grid[ 9][4] = 2; } else { grid[ 9][4] = 3; } }
        if(grid[10][4] == 1) { if(Result_Double(4, 5)                        ) { reward +=  7; grid[10][4] = 2; } else { grid[10][4] = 3; } }
        if(grid[11][4] == 1) { if(Result_Double(5, 5)                        ) { reward += 12; grid[11][4] = 2; } else { grid[11][4] = 3; } }

        if(grid[ 0][5] == 1) { if(Result_Triple(0)) { reward += 6; grid[ 0][5] = 2; } else if(Result_Double(0, 0)) { reward += 3; grid[ 0][5] = 2; } else if(Result_Single(0)) { reward += 2; grid[ 0][5] = 2; } else { grid[ 0][5] = 3; } }
        if(grid[ 1][5] == 1) { if(Result_Triple(0)) { reward += 6; grid[ 1][5] = 2; } else if(Result_Double(0, 0)) { reward += 3; grid[ 1][5] = 2; } else if(Result_Single(0)) { reward += 2; grid[ 1][5] = 2; } else { grid[ 1][5] = 3; } }
        if(grid[ 2][5] == 1) { if(Result_Triple(1)) { reward += 6; grid[ 2][5] = 2; } else if(Result_Double(1, 1)) { reward += 3; grid[ 2][5] = 2; } else if(Result_Single(1)) { reward += 2; grid[ 2][5] = 2; } else { grid[ 2][5] = 3; } }
        if(grid[ 3][5] == 1) { if(Result_Triple(1)) { reward += 6; grid[ 3][5] = 2; } else if(Result_Double(1, 1)) { reward += 3; grid[ 3][5] = 2; } else if(Result_Single(1)) { reward += 2; grid[ 3][5] = 2; } else { grid[ 3][5] = 3; } }
        if(grid[ 4][5] == 1) { if(Result_Triple(2)) { reward += 6; grid[ 4][5] = 2; } else if(Result_Double(2, 2)) { reward += 3; grid[ 4][5] = 2; } else if(Result_Single(2)) { reward += 2; grid[ 4][5] = 2; } else { grid[ 4][5] = 3; } }
        if(grid[ 5][5] == 1) { if(Result_Triple(2)) { reward += 6; grid[ 5][5] = 2; } else if(Result_Double(2, 2)) { reward += 3; grid[ 5][5] = 2; } else if(Result_Single(2)) { reward += 2; grid[ 5][5] = 2; } else { grid[ 5][5] = 3; } }
        if(grid[ 6][5] == 1) { if(Result_Triple(3)) { reward += 6; grid[ 6][5] = 2; } else if(Result_Double(3, 3)) { reward += 3; grid[ 6][5] = 2; } else if(Result_Single(3)) { reward += 2; grid[ 6][5] = 2; } else { grid[ 6][5] = 3; } }
        if(grid[ 7][5] == 1) { if(Result_Triple(3)) { reward += 6; grid[ 7][5] = 2; } else if(Result_Double(3, 3)) { reward += 3; grid[ 7][5] = 2; } else if(Result_Single(3)) { reward += 2; grid[ 7][5] = 2; } else { grid[ 7][5] = 3; } }
        if(grid[ 8][5] == 1) { if(Result_Triple(4)) { reward += 6; grid[ 8][5] = 2; } else if(Result_Double(4, 4)) { reward += 3; grid[ 8][5] = 2; } else if(Result_Single(4)) { reward += 2; grid[ 8][5] = 2; } else { grid[ 8][5] = 3; } }
        if(grid[ 9][5] == 1) { if(Result_Triple(4)) { reward += 6; grid[ 9][5] = 2; } else if(Result_Double(4, 4)) { reward += 3; grid[ 9][5] = 2; } else if(Result_Single(4)) { reward += 2; grid[ 9][5] = 2; } else { grid[ 9][5] = 3; } }
        if(grid[10][5] == 1) { if(Result_Triple(5)) { reward += 6; grid[10][5] = 2; } else if(Result_Double(5, 5)) { reward += 3; grid[10][5] = 2; } else if(Result_Single(5)) { reward += 2; grid[10][5] = 2; } else { grid[10][5] = 3; } }
        if(grid[11][5] == 1) { if(Result_Triple(5)) { reward += 6; grid[11][5] = 2; } else if(Result_Double(5, 5)) { reward += 3; grid[11][5] = 2; } else if(Result_Single(5)) { reward += 2; grid[11][5] = 2; } else { grid[11][5] = 3; } }

        turnstate = 4;
    }

    private int Result_Value() {
        return dice[0].number + dice[1].number + dice[2].number + 3;
    }

    private boolean Result_Single(int n) {
        if(dice[0].number == n) return true;
        if(dice[1].number == n) return true;
        return dice[2].number == n;
    }

    private boolean Result_Double(int n1, int n2) {
        return Result_Single(n1) && Result_Single(n2);
    }

    private boolean Result_Triple(int n) {
        return dice[0].number == dice[1].number && dice[0].number == dice[2].number && dice[0].number == n;
    }

    private boolean Result_AnyTriple() {
        return dice[0].number == dice[1].number && dice[0].number == dice[2].number;
    }

}
