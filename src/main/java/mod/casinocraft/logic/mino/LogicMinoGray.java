package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicModule;
import net.minecraft.nbt.CompoundTag;

import static mod.casinocraft.util.SoundMap.SOUND_CHIP;

public class LogicMinoGray extends LogicModule {   // Mino Flip

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoGray(int tableID) {
        super(tableID, 7, 7);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {
        scorePoint = 1;
        scoreLevel = 1;
        fillGrid();
    }

    public void restart(){
        turnstate = 2;
        scoreLevel++;
        fillGrid();
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {
        if(action == -1) {
            restart();
        } else if(action == -2) {
            turnstate = 4;
        } else if(action >= 100){
            setJingle(SOUND_CHIP);
            mark(action - 100);
        } else {
            setJingle(SOUND_CHIP);
            flip(action);
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {

    }

    public void updateMotion() {

    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){

    }

    public CompoundTag save2(CompoundTag compound){
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void fillGrid(){
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                grid[x][y] = 1;
            }
        }
        for(int i = 0; i < Math.min(scoreLevel + 8, 16); i++){
            int x = RANDOM.nextInt(5);
            int y = RANDOM.nextInt(5);
            if(grid[x][y] == 1){
                grid[x][y] = 0;
            } else {
                i--;
            }
        }
        for(int i = 0; i < Math.min(scoreLevel * 2 + 6, 32); i++){
            int x = RANDOM.nextInt(5);
            int y = RANDOM.nextInt(5);
            if(grid[x][y] > 0 && grid[x][y] <= 5){
                grid[x][y]++;
            } else {
                i--;
            }
        }
        for(int y = 0; y < 5; y++){
            grid[5][y] = 0;
            grid[6][y] = 0;
            for(int x = 0; x < 5; x++){
                if(grid[x][y] == 0){
                    grid[6][y]++;
                } else {
                    grid[5][y] += grid[x][y];
                }
            }
        }
        for(int x = 0; x < 5; x++){
            grid[x][5] = 0;
            grid[x][6] = 0;
            for(int y = 0; y < 5; y++){
                if(grid[x][y] == 0){
                    grid[x][6]++;
                } else {
                    grid[x][5] += grid[x][y];
                }
            }
        }
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                grid[x][y] += 100;
            }
        }
    }

    private void flip(int action){
        int x = action % 5;
        int y = action / 5;
        if(grid[x][y] >= 100){
            grid[x][y] = grid[x][y] % 100;
            if(grid[x][y] == 0){
                turnstate = 4;
                scorePoint /= 2;
            } else {
                scorePoint *= grid[x][y];
                checkGrid();
            }
        }
    }

    private void mark(int action){
        int x = action % 5;
        int y = action / 5;
        if(grid[x][y] >= 200){
            grid[x][y] -= 100;
        } else if(grid[x][y] >= 100){
            grid[x][y] += 100;
        }
    }

    private void checkGrid(){
        boolean allCleared = true;
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                if (grid[x][y] % 100 > 1 && grid[x][y] >= 100) {
                    allCleared = false;
                    break;
                }
            }
        }
        if(allCleared){
            turnstate = 3;
            uncoverAll();
        }
    }

    private void uncoverAll(){
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                grid[x][y] %= 100;
            }
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 36;
    }

}
