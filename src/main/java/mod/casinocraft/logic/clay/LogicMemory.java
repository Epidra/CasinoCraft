package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;
import mod.shared.util.Vector2;

public class LogicMemory extends LogicBase {

    public int[][] grid = new int[17][9];

    public boolean selectA = false;
    public boolean selectB = false;

    public Vector2 positionA = new Vector2(0, 0);
    public Vector2 positionB = new Vector2(0, 0);

    public int timer = 0;



    //--------------------CONSTRUCTOR--------------------

    public LogicMemory(int table){
        super(true, table, "memory");
    }



    //--------------------BASIC--------------------

    public void start2(){
        scoreLevel = 1;
        scoreLives = 8;
        selectA = false;
        selectB = false;
        timer = 0;
        positionA.set(-1, -1);
        positionB.set(-1, -1);
        Command_Create_Grid();
    }

    public void restart(){
        turnstate = 2;
        scoreLevel++;
        scoreLives += scoreLevel * 2;
        selectA = false;
        selectB = false;
        timer = 0;
        positionA.set(-1, -1);
        positionB.set(-1, -1);
        Command_Create_Grid();
    }

    public void actionTouch(int action){
        if(action == -1) {
            restart();
        } else if(action == -2) {
            turnstate = 4;
        } else {
            Click_Mino(action % 17, action / 17);
        }
    }

    public void updateLogic(){
        if(timer == -1){
            if(grid[positionA.X][positionA.Y] == grid[positionB.X][positionB.Y]){
                grid[positionA.X][positionA.Y] = -1;
                grid[positionB.X][positionB.Y] = -1;
            } else {
                scoreLives--;
                //ChangeName(selected_A_pos.X, selected_A_pos.Y, grid[selected_A_pos.X][selected_A_pos.Y]);
                //ChangeName(selected_B_pos.X, selected_B_pos.Y, grid[selected_B_pos.X][selected_B_pos.Y]);
            }
            boolean temp = false;
            for(int x = 0; x < 17; x++) {
                for(int y = 0; y < 9; y++) {
                    if(grid[x][y] != -1) temp = true;
                }
            }
            if(!temp) {
                turnstate = 3;
                scorePoint += scoreLevel * 4;
            } else {
                if(scoreLives <= 0) {
                    turnstate = 4;
                    scorePoint /= 2;
                }
            }
            selectA = false;
            selectB = false;
            positionA.set(-1, -1);
            positionB.set(-1, -1);
        }
    }

    public void updateMotion(){
        if(timer > 0){
            timer -= 10;
            if(timer <= 0)
                timer = -1;
        }
    }



    //--------------------CUSTOM--------------------

    public void Click_Mino(int x, int y) {
        if(!selectA) {
            if(grid[x][y] != -1) {
                selectA = true;
                positionA.set(x, y);
            }
        } else if(!selectB) {
            if(grid[x][y] != -1 && !positionA.matches(x, y)) {
                selectB = true;
                positionB.set(x, y);
                //ChangeName(x, y, grid[x][y]);
                timer = 400 - scoreLevel*10;
            }
        }
    }

    private void Command_Create_Grid() {
        for(int x = 0; x < 17; x++) {
            for(int y = 0; y < 9; y++) {
                grid[x][y] = -1;
            }
        }
        int max = table == 2 ? 9*9-1 : 17*9-1;
        int filler = scoreLevel*4 < max ? scoreLevel*4 : max;
        int filler2 = filler;

        int color[] = new int[] {0,0,0,0,0,0,0,0};
        while(filler > 0) {
            int z = RANDOM.nextInt(8);
            color[z] += 2;
            filler -= 2;
            //for(int i = 0; i < 8; i++) {
            //	if(filler > 0) { color[i] += 2; filler -= 2; }
            //}
        }

        while(filler2 > 0) {
            int x = RANDOM.nextInt(table == 2 ? 9 : 17) + (table == 2 ? 4 : 0);
            int y = RANDOM.nextInt(9);
            if(grid[x][y] == -1) {
                for(int i = 0; i < 8; i++) {
                    if(color[i] > 0) {
                        grid[x][y] = i;
                        color[i]--;
                        filler2--;
                        break;
                    }
                }
            }
        }
    }

}