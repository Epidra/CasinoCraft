package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.NBTTagCompound;

public class LogicMinoWhite extends LogicBase {   // Sudoku

    private boolean match;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoWhite(int tableID){
        super(tableID, 9, 9);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        match = false;

        int r = RANDOM.nextInt(9)+1;
        grid[4][4] = r + 10;
        generateSquare(3, 3, 0);
        generateSquare(3, 0, 3);
        generateSquare(3, 6, 3);
        generateSquare(3, 3, 6);

        generateSquare(3, 0, 0);
        generateSquare(3, 6, 0);
        generateSquare(3, 0, 6);
        generateSquare(3, 6, 6);
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action >= 100){
            int i = action - 100;
            selector.set(i%9, i/9);
        } else {
            grid[selector.X][selector.Y] = action;
            check();
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion(){

    }

    public void updateLogic(){
        if(match && turnstate < 4) {
            turnstate = 4;
        }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(NBTTagCompound compound){
    }

    public NBTTagCompound save2(NBTTagCompound compound){
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void generateSquare(int count, int xi, int yi) {
        int index = 0;
        while(index < count) {
            int x = RANDOM.nextInt(3);
            int y = RANDOM.nextInt(3);
            int i = RANDOM.nextInt(9)+1;
            if(grid[0][yi + y] != i && grid[1][yi + y] != i && grid[2][yi + y] != i && grid[3][yi + y] != i && grid[4][yi + y] != i && grid[5][yi + y] != i && grid[6][yi + y] != i && grid[7][yi + y] != i && grid[8][yi + y] != i) {
                if(grid[xi + x][0] != i && grid[xi + x][1] != i && grid[xi + x][2] != i && grid[xi + x][3] != i && grid[xi + x][4] != i && grid[xi + x][5] != i && grid[xi + x][6] != i && grid[xi + x][7] != i && grid[xi + x][8] != i) {
                    if(grid[xi + 0][yi + 0] != i && grid[xi + 1][yi + 0] != i && grid[xi + 2][yi + 0] != i && grid[xi + 0][yi + 1] != i && grid[xi + 1][yi + 1] != i && grid[xi + 2][yi + 1] != i && grid[xi + 0][yi + 2] != i && grid[xi + 1][yi + 2] != i && grid[xi + 2][yi + 2] != i) {
                        grid[xi + x][yi + y] = i + 10;
                        index++;
                    }
                }
            }
        }
    }

    private void check() {
        boolean[] match_vert = new boolean[9];
        boolean[] match_hori = new boolean[9];
        boolean[] match_cube = new boolean[9];
        int[] n = new int[10];
        for(int i = 0; i < 9; i++) { n[i] = 0; }

        for(int y = 0; y < 9; y++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int x = 0; x < 9; x++) {
                n[grid[x][y]]++;
            }
            match_vert[y] = n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1;
        }

        for(int x = 0; x < 9; x++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int y = 0; y < 9; y++) {
                n[grid[x][y]]++;
            }
            match_hori[x] = n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1;
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                for(int i = 0; i < 9; i++) { n[i] = 0; }
                for(int yi = y * 3; yi < y * 3 + 3; yi++) {
                    for(int xi = x * 3; xi < x * 3 + 3; xi++) {
                        n[grid[xi][yi]]++;
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




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 46;
    }

}
