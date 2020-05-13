package mod.casinocraft.logic.other;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.CompoundNBT;

public class LogicSlotGame extends LogicBase {

    public int multiplier;
    public int wheel; // What wheel will be halted next
    public int[] wheelPos = new int[3];
    public boolean[] lines = new boolean[5];




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicSlotGame(int tableID) {
        super(tableID, 9, 3);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {
        fillGrid();
        wheelPos[0] = 0;
        wheelPos[1] = 0;
        wheelPos[2] = 0;
        multiplier = 1;
        wheel = 0;
        lines[0] = false;
        lines[1] = false;
        lines[2] = false;
        lines[3] = false;
        lines[4] = false;
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {
        if(action == 0){
            if(multiplier < 5) multiplier++;
        } else {
            if(turnstate == 2){
                turnstate = 3;
            } else if(turnstate == 3){
                spin();
            }
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion() {

    }

    public void updateLogic() {
        int max = 48*9;
        if(turnstate == 3) {
            for(int i = 0; i < 3; i++) {
                if(wheel <= i) {
                    wheelPos[i] = (wheelPos[i] + 6 + i * 6) % max;
                } else if(wheelPos[i] % 48 != 0) {
                    wheelPos[i] = (wheelPos[i] + 1) % max;
                }
            }
            if(wheel == 3){
                if(wheelPos[0] % 48 == 0 && wheelPos[1] % 48 == 0 && wheelPos[2] % 48 == 0){
                    result();
                }
            }
        }

    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        multiplier = compound.getInt("multiplier");
        wheel = compound.getInt("wheel");
        wheelPos[0] = compound.getInt("wheelpos0");
        wheelPos[1] = compound.getInt("wheelpos1");
        wheelPos[2] = compound.getInt("wheelpos2");
        lines[0] = compound.getBoolean("lines0");
        lines[1] = compound.getBoolean("lines1");
        lines[2] = compound.getBoolean("lines2");
        lines[3] = compound.getBoolean("lines3");
        lines[4] = compound.getBoolean("lines4");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putInt("multiplier", multiplier);
        compound.putInt("wheel", wheel);
        compound.putInt("wheelpos0", wheelPos[0]);
        compound.putInt("wheelpos1", wheelPos[1]);
        compound.putInt("wheelpos2", wheelPos[2]);
        compound.putBoolean("lines0", lines[0]);
        compound.putBoolean("lines1", lines[1]);
        compound.putBoolean("lines2", lines[2]);
        compound.putBoolean("lines3", lines[3]);
        compound.putBoolean("lines4", lines[4]);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void fillGrid() {
        for(int y = 0; y < 3; y++) {
            int[] gridtemp = getGrid((y) % 9);
            for(int x = 0; x < 9; x++) {
                grid[x][y] = gridtemp[x];
            }
        }
    }

    private int[] getGrid(int id) {
        switch(id) {
            case 0: return new int[] { 1, 0, 5, 4, 3, 2, 0, 5, 4 };
            case 1: return new int[] { 1, 2, 0, 5, 4, 3, 2, 0, 5 };
            case 2: return new int[] { 1, 3, 2, 0, 5, 4, 3, 2, 0 };
        }
        return null;
    }

    private void spin() {
        if(wheel < 3) {
            wheel++;
        }
    }

    private void result() {
        int pos0 = wheelPos[0] / 48;
        int pos1 = wheelPos[1] / 48;
        int pos2 = wheelPos[2] / 48;
        if(multiplier >= 1) checkWheel(grid[(pos0 + 1) % 9][0], grid[(pos1 + 1) % 9][1], grid[(pos2 + 1) % 9][2], 0);
        if(multiplier >= 2) checkWheel(grid[(pos0 + 0) % 9][0], grid[(pos1 + 0) % 9][1], grid[(pos2 + 0) % 9][2], 1);
        if(multiplier >= 3) checkWheel(grid[(pos0 + 2) % 9][0], grid[(pos1 + 2) % 9][1], grid[(pos2 + 2) % 9][2], 2);
        if(multiplier >= 4) checkWheel(grid[(pos0 + 0) % 9][0], grid[(pos1 + 1) % 9][1], grid[(pos2 + 2) % 9][2], 3);
        if(multiplier >= 5) checkWheel(grid[(pos0 + 2) % 9][0], grid[(pos1 + 1) % 9][1], grid[(pos2 + 0) % 9][2], 4);
        turnstate = 4;
    }

    private void checkWheel(int wheel0, int wheel1, int wheel2, int index){
        if(wheel0 == 0){
            reward[0] += 1; // BAR
        }
        if(wheel0 == 1){
            reward[0] += 1; // BAR
        }
        if(wheel0 == 2){
            reward[0] += 1; // BAR
        }
        if(wheel0 == 2 && wheel1 == 2 && wheel2 == 2){
            reward[0] += 5; // KREUZ
            lines[index] = true;
        }
        if(wheel0 == 3 && wheel1 == 3 && wheel2 == 3){
            reward[0] += 10; // PIK
            lines[index] = true;
        }
        if(wheel0 == 4 && wheel1 == 4 && wheel2 == 4){
            reward[0] += 20; // KARO
            lines[index] = true;
        }
        if(wheel0 == 5 && wheel1 == 5 && wheel2 == 5){
            reward[0] += 35; // HERZ
            lines[index] = true;
        }
        if(wheel0 == 1 && wheel1 == 1 && wheel2 == 1){
            reward[0] += 77; // SEVEN
            lines[index] = true;
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return true;
    }

    public int getID(){
        return 49;
    }
}
