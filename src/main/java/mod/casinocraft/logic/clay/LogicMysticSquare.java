package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.CompoundNBT;

public class LogicMysticSquare extends LogicBase {

    //--------------------CONSTRUCTOR--------------------

    public LogicMysticSquare(int table){
        super(false, table, "c_mystic_square", 4, 4);
    }



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                grid[x][y] = -1;
            }
        }
        int i = 0;
        while(i < 15) {
            int x = RANDOM.nextInt(4);
            int y = RANDOM.nextInt(4);
            if(grid[x][y] == -1) {
                grid[x][y] = i;
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

    public void load2(CompoundNBT compound){

    }

    public CompoundNBT save2(CompoundNBT compound){
        return compound;
    }



    //--------------------CUSTOM--------------------

    private void Move(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && y > 0) if(grid[x][y - 1] == -1) grid[x][y] += 20; // UP
                if(direction == 1 && y < 3) if(grid[x][y + 1] == -1) grid[x][y] += 20; // DOWN
                if(direction == 2 && x > 0) if(grid[x - 1][y] == -1) grid[x][y] += 20; // LEFT
                if(direction == 3 && x < 3) if(grid[x + 1][y] == -1) grid[x][y] += 20; // RIGHT
            }
        }
        Change(direction);
    }

    private void Change(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && grid[x][y] >= 20) { grid[x][y - 1] = grid[x][y] % 20; grid[x][y] = -1; } // UP
                if(direction == 1 && grid[x][y] >= 20) { grid[x][y + 1] = grid[x][y] % 20; grid[x][y] = -1; } // DOWN
                if(direction == 2 && grid[x][y] >= 20) { grid[x - 1][y] = grid[x][y] % 20; grid[x][y] = -1; } // LEFT
                if(direction == 3 && grid[x][y] >= 20) { grid[x + 1][y] = grid[x][y] % 20; grid[x][y] = -1; } // RIGHT
            }
        }
    }

}
