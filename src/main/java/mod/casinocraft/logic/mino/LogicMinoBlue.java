package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import static mod.casinocraft.util.SoundMap.SOUND_CHIP;
import static mod.casinocraft.util.SoundMap.SOUND_IMPACT;

public class LogicMinoBlue extends LogicBase {   // Memory

    public boolean selectA = false;
    public boolean selectB = false;

    public Vector2 positionA = new Vector2(0, 0);
    public Vector2 positionB = new Vector2(0, 0);

    public int timer = 0;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoBlue(int tableID){
        super(tableID, 17, 9);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        scoreLevel = 1;
        scoreLives = 8;
        selectA = false;
        selectB = false;
        timer = 0;
        positionA.set(-1, -1);
        positionB.set(-1, -1);
        commandCreateGrid();
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
        commandCreateGrid();
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == -1) {
            restart();
        } else if(action == -2) {
            turnstate = 4;
        } else {
            clickMino(action % 17, action / 17);
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){
        if(timer == -1){
            if(grid[positionA.X][positionA.Y] == grid[positionB.X][positionB.Y]){
                grid[positionA.X][positionA.Y] = -1;
                grid[positionB.X][positionB.Y] = -1;
            } else {
                scoreLives--;
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
            timer = 0;
        }
    }

    public void updateMotion(){
        if(timer > 0){
            timer -= 10;
            if(timer <= 0)
                timer = -1;
        }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        selectA = compound.getBoolean("selecta");
        selectB = compound.getBoolean("selectb");
        positionA.set(compound.getInt("positionax"), compound.getInt("positionay"));
        positionA.set(compound.getInt("positionbx"), compound.getInt("positionby"));
        timer = compound.getInt("timer");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putBoolean("selecta", selectA);
        compound.putBoolean("selectb", selectB);
        compound.putInt("positionax", positionA.X);
        compound.putInt("positionay", positionA.Y);
        compound.putInt("positionbx", positionB.X);
        compound.putInt("positionby", positionB.Y);
        compound.putInt("timer", timer);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void clickMino(int x, int y) {
        if(!selectA) {
            if(grid[x][y] != -1) {
                selectA = true;
                positionA.set(x, y);
                setJingle(SOUND_CHIP);
            }
        } else if(!selectB) {
            if(grid[x][y] != -1 && !positionA.matches(x, y)) {
                selectB = true;
                positionB.set(x, y);
                //ChangeName(x, y, grid[x][y]);
                timer = 200 - scoreLevel*10;
                setJingle(SOUND_CHIP);
            }
        }
    }

    private void commandCreateGrid() {
        for(int x = 0; x < 17; x++) {
            for(int y = 0; y < 9; y++) {
                grid[x][y] = -1;
            }
        }
        int max = tableID == 1 ? 9*9-1 : 17*9-1;
        int filler = scoreLevel*4 < max ? scoreLevel*4 : max;
        int filler2 = filler;

        int color[] = new int[] {0,0,0,0,0,0,0,0};
        while(filler > 0) {
            int z = RANDOM.nextInt(8);
            color[z] += 2;
            filler -= 2;
        }

        while(filler2 > 0) {
            int x = RANDOM.nextInt(tableID == 1 ? 9 : 17) + (tableID == 1 ? 4 : 0);
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




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 33;
    }

}
