package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicModule;
import net.minecraft.nbt.CompoundNBT;
import static mod.casinocraft.util.KeyMap.*;
import static mod.casinocraft.util.SoundMap.SOUND_CHIP;

public class LogicMinoGreen extends LogicModule {   // Mystic Square

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoGreen(int tableID){
        super(tableID, 4, 4);
    }




    //----------------------------------------START/RESTART----------------------------------------//

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




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        move(action);
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){

    }

    public void updateMotion(){

    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){

    }

    public CompoundNBT save2(CompoundNBT compound){
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void move(int direction) {
        setJingle(SOUND_CHIP);
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if((direction == 0 || direction == KEY_UP)    && y > 0) if(grid[x][y - 1] == -1) grid[x][y] += 20; // UP
                if((direction == 1 || direction == KEY_DOWN)  && y < 3) if(grid[x][y + 1] == -1) grid[x][y] += 20; // DOWN
                if((direction == 2 || direction == KEY_LEFT)  && x > 0) if(grid[x - 1][y] == -1) grid[x][y] += 20; // LEFT
                if((direction == 3 || direction == KEY_RIGHT) && x < 3) if(grid[x + 1][y] == -1) grid[x][y] += 20; // RIGHT
            }
        }
        change(direction);
    }

    private void change(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if((direction == 0 || direction == KEY_UP)    && grid[x][y] >= 20) { grid[x][y - 1] = grid[x][y] % 20; grid[x][y] = -1; } // UP
                if((direction == 1 || direction == KEY_DOWN)  && grid[x][y] >= 20) { grid[x][y + 1] = grid[x][y] % 20; grid[x][y] = -1; } // DOWN
                if((direction == 2 || direction == KEY_LEFT)  && grid[x][y] >= 20) { grid[x - 1][y] = grid[x][y] % 20; grid[x][y] = -1; } // LEFT
                if((direction == 3 || direction == KEY_RIGHT) && grid[x][y] >= 20) { grid[x + 1][y] = grid[x][y] % 20; grid[x][y] = -1; } // RIGHT
            }
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
        return 37;
    }

}
