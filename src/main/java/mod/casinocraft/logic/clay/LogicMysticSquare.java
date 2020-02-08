package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;

public class LogicMysticSquare extends LogicBase {

    public     int[][] gridI = new     int[4][4];
    public boolean[][] gridB = new boolean[4][4];

    //--------------------CONSTRUCTOR--------------------

    public LogicMysticSquare(int table){
        super(false, table, "c_mystic_square");
    }



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                gridI[x][y] = -1;
                gridB[x][y] = false;
            }
        }
        int i = 0;
        while(i < 15) {
            int x = RANDOM.nextInt(4);
            int y = RANDOM.nextInt(4);
            if(gridI[x][y] == -1) {
                gridI[x][y] = i;
                i++;
            }
        }
    }

    public void actionTouch(int action){
        Move(action);
    }

    public void updateMotion(){

    }

    public void updateLogic(){

    }



    //--------------------CUSTOM--------------------

    private void Move(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && y > 0) if(gridI[x][y - 1] == -1) gridB[x][y] = true; // UP
                if(direction == 1 && y < 3) if(gridI[x][y + 1] == -1) gridB[x][y] = true; // DOWN
                if(direction == 2 && x > 0) if(gridI[x - 1][y] == -1) gridB[x][y] = true; // LEFT
                if(direction == 3 && x < 3) if(gridI[x + 1][y] == -1) gridB[x][y] = true; // RIGHT
            }
        }
        Change(direction);
    }

    private void Change(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && gridB[x][y]) { gridI[x][y - 1] = gridI[x][y]; gridI[x][y] = -1; gridB[x][y] = false; } // UP
                if(direction == 1 && gridB[x][y]) { gridI[x][y + 1] = gridI[x][y]; gridI[x][y] = -1; gridB[x][y] = false; } // DOWN
                if(direction == 2 && gridB[x][y]) { gridI[x - 1][y] = gridI[x][y]; gridI[x][y] = -1; gridB[x][y] = false; } // LEFT
                if(direction == 3 && gridB[x][y]) { gridI[x + 1][y] = gridI[x][y]; gridI[x][y] = -1; gridB[x][y] = false; } // RIGHT
            }
        }
    }

}
