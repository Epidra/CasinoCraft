package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;

public class LogicSudoku extends LogicBase {

    public     int [][] gridI = new     int[9][9];
    public boolean [][] gridB = new boolean[9][9];

    private boolean match;



    //--------------------CONSTRUCTOR--------------------

    public LogicSudoku(int table){
        super(false, table, "sudoku");
    }



    //--------------------BASIC--------------------

    public void start2(){
        match = false;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                gridI[x][y] = 0;
            }
        }

        int r = RANDOM.nextInt(9)+1;
        gridI[4][4] = r;
        gridB[4][4] = true;
        Generate_Square(3, 3, 0);
        Generate_Square(3, 0, 3);
        Generate_Square(3, 6, 3);
        Generate_Square(3, 3, 6);

        Generate_Square(3, 0, 0);
        Generate_Square(3, 6, 0);
        Generate_Square(3, 0, 6);
        Generate_Square(3, 6, 6);
    }

    public void actionTouch(int action){
        if(action >= 100){
            int i = action - 100;
            selector.set(i%9, i/9);
        } else {
            gridI[selector.X][selector.Y] = action;
            Check();
        }
    }

    public void updateMotion(){

    }

    public void updateLogic(){
        if(match && turnstate < 4) {
            turnstate = 4;
        }
    }



    //--------------------CUSTOM--------------------

    private void Generate_Square(int count, int xi, int yi) {
        int index = 0;
        while(index < count) {
            int x = RANDOM.nextInt(3);
            int y = RANDOM.nextInt(3);
            int i = RANDOM.nextInt(9)+1;
            if(gridI[0][yi + y] != i && gridI[1][yi + y] != i && gridI[2][yi + y] != i && gridI[3][yi + y] != i && gridI[4][yi + y] != i && gridI[5][yi + y] != i && gridI[6][yi + y] != i && gridI[7][yi + y] != i && gridI[8][yi + y] != i) {
                if(gridI[xi + x][0] != i && gridI[xi + x][1] != i && gridI[xi + x][2] != i && gridI[xi + x][3] != i && gridI[xi + x][4] != i && gridI[xi + x][5] != i && gridI[xi + x][6] != i && gridI[xi + x][7] != i && gridI[xi + x][8] != i) {
                    if(gridI[xi + 0][yi + 0] != i && gridI[xi + 1][yi + 0] != i && gridI[xi + 2][yi + 0] != i && gridI[xi + 0][yi + 1] != i && gridI[xi + 1][yi + 1] != i && gridI[xi + 2][yi + 1] != i && gridI[xi + 0][yi + 2] != i && gridI[xi + 1][yi + 2] != i && gridI[xi + 2][yi + 2] != i) {
                        gridI[xi + x][yi + y] = i;
                        gridB[xi + x][yi + y] = true;
                        index++;
                    }
                }
            }
        }
    }

    private void Check() {
        boolean[] match_vert = new boolean[9];
        boolean[] match_hori = new boolean[9];
        boolean[] match_cube = new boolean[9];
        int[] n = new int[10];
        for(int i = 0; i < 9; i++) { n[i] = 0; }

        for(int y = 0; y < 9; y++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int x = 0; x < 9; x++) {
                n[gridI[x][y]]++;
            }
            match_vert[y] = n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1;
        }

        for(int x = 0; x < 9; x++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int y = 0; y < 9; y++) {
                n[gridI[x][y]]++;
            }
            match_hori[x] = n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1;
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                for(int i = 0; i < 9; i++) { n[i] = 0; }
                for(int yi = y * 3; yi < y * 3 + 3; yi++) {
                    for(int xi = x * 3; xi < x * 3 + 3; xi++) {
                        n[gridI[xi][yi]]++;
                    }
                }
                match_cube[y * 3 + x] = false;
                if(n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1) match_hori[y * 3 + x] = true;
            }
        }

        match = true;
        for(int i = 0; i < 9; i++) {
            if(!match_vert[i]) match = false;
            if(!match_hori[i]) match = false;
            if(!match_cube[i]) match = false;
        }
    }

}
