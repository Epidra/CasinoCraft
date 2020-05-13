package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.NBTTagCompound;

public class LogicMinoCyan extends LogicBase {   // Halma

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoCyan(int tableID){
        super(tableID, 17, 9);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        if(tableID == 1) {
            selector.set(8, 2);
            grid[0][0] = -1; grid[1][0] = -1; grid[2][0] = -1; grid[3][0] = -1; grid[4][0] = -1; grid[5][0] = -1; grid[6][0] = 1; grid[7][0] =  1; grid[8][0] =  1; grid[9][0] =  1; grid[10][0] = 1; grid[11][0] = -1; grid[12][0] = -1; grid[13][0] = -1; grid[14][0] = -1; grid[15][0] = -1; grid[16][0] = -1;
            grid[0][1] = -1; grid[1][1] = -1; grid[2][1] = -1; grid[3][1] = -1; grid[4][1] = -1; grid[5][1] = -1; grid[6][1] = 1; grid[7][1] =  1; grid[8][1] =  1; grid[9][1] =  1; grid[10][1] = 1; grid[11][1] = -1; grid[12][1] = -1; grid[13][1] = -1; grid[14][1] = -1; grid[15][1] = -1; grid[16][1] = -1;
            grid[0][2] = -1; grid[1][2] = -1; grid[2][2] = -1; grid[3][2] = -1; grid[4][2] =  1; grid[5][2] =  1; grid[6][2] = 1; grid[7][2] =  1; grid[8][2] =  1; grid[9][2] =  1; grid[10][2] = 1; grid[11][2] =  1; grid[12][2] =  1; grid[13][2] = -1; grid[14][2] = -1; grid[15][2] = -1; grid[16][2] = -1;
            grid[0][3] = -1; grid[1][3] = -1; grid[2][3] = -1; grid[3][3] = -1; grid[4][3] =  1; grid[5][3] =  1; grid[6][3] = 1; grid[7][3] =  1; grid[8][3] =  1; grid[9][3] =  1; grid[10][3] = 1; grid[11][3] =  1; grid[12][3] =  1; grid[13][3] = -1; grid[14][3] = -1; grid[15][3] = -1; grid[16][3] = -1;
            grid[0][4] = -1; grid[1][4] = -1; grid[2][4] = -1; grid[3][4] = -1; grid[4][4] =  1; grid[5][4] =  1; grid[6][4] = 1; grid[7][4] =  1; grid[8][4] =  0; grid[9][4] =  1; grid[10][4] = 1; grid[11][4] =  1; grid[12][4] =  1; grid[13][4] = -1; grid[14][4] = -1; grid[15][4] = -1; grid[16][4] = -1;
            grid[0][5] = -1; grid[1][5] = -1; grid[2][5] = -1; grid[3][5] = -1; grid[4][5] =  1; grid[5][5] =  1; grid[6][5] = 1; grid[7][5] =  1; grid[8][5] =  1; grid[9][5] =  1; grid[10][5] = 1; grid[11][5] =  1; grid[12][5] =  1; grid[13][5] = -1; grid[14][5] = -1; grid[15][5] = -1; grid[16][5] = -1;
            grid[0][6] = -1; grid[1][6] = -1; grid[2][6] = -1; grid[3][6] = -1; grid[4][6] =  1; grid[5][6] =  1; grid[6][6] = 1; grid[7][6] =  1; grid[8][6] =  1; grid[9][6] =  1; grid[10][6] = 1; grid[11][6] =  1; grid[12][6] =  1; grid[13][6] = -1; grid[14][6] = -1; grid[15][6] = -1; grid[16][6] = -1;
            grid[0][7] = -1; grid[1][7] = -1; grid[2][7] = -1; grid[3][7] = -1; grid[4][7] = -1; grid[5][7] = -1; grid[6][7] = 1; grid[7][7] =  1; grid[8][7] =  1; grid[9][7] =  1; grid[10][7] = 1; grid[11][7] = -1; grid[12][7] = -1; grid[13][7] = -1; grid[14][7] = -1; grid[15][7] = -1; grid[16][7] = -1;
            grid[0][8] = -1; grid[1][8] = -1; grid[2][8] = -1; grid[3][8] = -1; grid[4][8] = -1; grid[5][8] = -1; grid[6][8] = 1; grid[7][8] =  1; grid[8][8] =  1; grid[9][8] =  1; grid[10][8] = 1; grid[11][8] = -1; grid[12][8] = -1; grid[13][8] = -1; grid[14][8] = -1; grid[15][8] = -1; grid[16][8] = -1;
        } else {
            selector.set(8, 4);
            grid[0][0] = -1; grid[1][0] = -1; grid[2][0] = -1; grid[3][0] =  1; grid[4][0] =  1; grid[5][0] =  1; grid[6][0] = 1; grid[7][0] = -1; grid[8][0] = -1; grid[9][0] = -1; grid[10][0] = 1; grid[11][0] =  1; grid[12][0] =  1; grid[13][0] =  1; grid[14][0] = -1; grid[15][0] = -1; grid[16][0] = -1;
            grid[0][1] = -1; grid[1][1] = -1; grid[2][1] = -1; grid[3][1] =  1; grid[4][1] =  1; grid[5][1] =  1; grid[6][1] = 1; grid[7][1] = -1; grid[8][1] = -1; grid[9][1] = -1; grid[10][1] = 1; grid[11][1] =  1; grid[12][1] =  1; grid[13][1] =  1; grid[14][1] = -1; grid[15][1] = -1; grid[16][1] = -1;
            grid[0][2] =  1; grid[1][2] =  1; grid[2][2] =  1; grid[3][2] =  1; grid[4][2] =  1; grid[5][2] =  1; grid[6][2] = 1; grid[7][2] =  1; grid[8][2] =  1; grid[9][2] =  1; grid[10][2] = 1; grid[11][2] =  1; grid[12][2] =  1; grid[13][2] =  1; grid[14][2] =  1; grid[15][2] =  1; grid[16][2] =  1;
            grid[0][3] =  1; grid[1][3] =  1; grid[2][3] =  1; grid[3][3] =  1; grid[4][3] =  1; grid[5][3] =  1; grid[6][3] = 1; grid[7][3] =  1; grid[8][3] =  1; grid[9][3] =  1; grid[10][3] = 1; grid[11][3] =  1; grid[12][3] =  1; grid[13][3] =  1; grid[14][3] =  1; grid[15][3] =  1; grid[16][3] =  1;
            grid[0][4] =  1; grid[1][4] =  1; grid[2][4] =  1; grid[3][4] =  1; grid[4][4] =  0; grid[5][4] =  0; grid[6][4] = 1; grid[7][4] =  1; grid[8][4] =  1; grid[9][4] =  1; grid[10][4] = 1; grid[11][4] =  0; grid[12][4] =  0; grid[13][4] =  1; grid[14][4] =  1; grid[15][4] =  1; grid[16][4] =  1;
            grid[0][5] =  1; grid[1][5] =  1; grid[2][5] =  1; grid[3][5] =  1; grid[4][5] =  1; grid[5][5] =  1; grid[6][5] = 1; grid[7][5] =  1; grid[8][5] =  1; grid[9][5] =  1; grid[10][5] = 1; grid[11][5] =  1; grid[12][5] =  1; grid[13][5] =  1; grid[14][5] =  1; grid[15][5] =  1; grid[16][5] =  1;
            grid[0][6] =  1; grid[1][6] =  1; grid[2][6] =  1; grid[3][6] =  1; grid[4][6] =  1; grid[5][6] =  1; grid[6][6] = 1; grid[7][6] =  1; grid[8][6] =  1; grid[9][6] =  1; grid[10][6] = 1; grid[11][6] =  1; grid[12][6] =  1; grid[13][6] =  1; grid[14][6] =  1; grid[15][6] =  1; grid[16][6] =  1;
            grid[0][7] = -1; grid[1][7] = -1; grid[2][7] = -1; grid[3][7] =  1; grid[4][7] =  1; grid[5][7] =  1; grid[6][7] = 1; grid[7][7] = -1; grid[8][7] = -1; grid[9][7] = -1; grid[10][7] = 1; grid[11][7] =  1; grid[12][7] =  1; grid[13][7] =  1; grid[14][7] = -1; grid[15][7] = -1; grid[16][7] = -1;
            grid[0][8] = -1; grid[1][8] = -1; grid[2][8] = -1; grid[3][8] =  1; grid[4][8] =  1; grid[5][8] =  1; grid[6][8] = 1; grid[7][8] = -1; grid[8][8] = -1; grid[9][8] = -1; grid[10][8] = 1; grid[11][8] =  1; grid[12][8] =  1; grid[13][8] =  1; grid[14][8] = -1; grid[15][8] = -1; grid[16][8] = -1;
        }
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        jump(action%17, action/17);
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion(){

    }

    public void updateLogic(){

    }



    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(NBTTagCompound compound){

    }

    public NBTTagCompound save2(NBTTagCompound compound){
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void jump(int x, int y) {
        if(grid[x][y] == 1) {
            selector.set(x, y);
            scoreLevel = 1;
        } else if(grid[x][y] == 0) {
            if(y + 2 == selector.Y && grid[selector.X][selector.Y - 1] == 1) { // Jump to UP
                grid[x][y    ] = 1;
                grid[x][y + 1] = 0;
                grid[x][y + 2] = 0;
                selector.set(x, y);
                scorePoint += scoreLevel;
                scoreLevel += 1;
            } else if(y - 2 == selector.Y && grid[selector.X][selector.Y + 1] == 1) { // Jump to DOWN
                grid[x][y    ] = 1;
                grid[x][y - 1] = 0;
                grid[x][y - 2] = 0;
                selector.set(x, y);
                scorePoint += scoreLevel;
                scoreLevel += 1;
            } else if(x + 2 == selector.X && grid[selector.X - 1][selector.Y] == 1) { // Jump to LEFT
                grid[x    ][y] = 1;
                grid[x + 1][y] = 0;
                grid[x + 2][y] = 0;
                selector.set(x, y);
                scorePoint += scoreLevel;
                scoreLevel += 1;
            } else if(x - 2 == selector.X && grid[selector.X + 1][selector.Y] == 1) { // Jump to RIGHT
                grid[x    ][y] = 1;
                grid[x - 1][y] = 0;
                grid[x - 2][y] = 0;
                selector.set(x, y);
                scorePoint += scoreLevel;
                scoreLevel += 1;
            }
            checkForGameOver();
        }
    }

    private void checkForGameOver() {
        boolean canJump = false;
        int counter = 0;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(grid[x][y] == 1) {
                    counter++;
                    if(y >=  2 && grid[x][y - 1] == 1 && grid[x][y - 2] == 0) canJump = true;
                    if(y <=  6 && grid[x][y + 1] == 1 && grid[x][y + 2] == 0) canJump = true;
                    if(x >=  2 && grid[x - 1][y] == 1 && grid[x - 2][y] == 0) canJump = true;
                    if(x <= 14 && grid[x + 1][y] == 1 && grid[x + 2][y] == 0) canJump = true;
                }
            }
        }
        if(counter == 1) turnstate = 4;
        if(!canJump) turnstate = 4;
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 35;
    }

}
