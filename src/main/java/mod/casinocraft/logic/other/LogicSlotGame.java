package mod.casinocraft.logic.other;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.CompoundNBT;

public class LogicSlotGame extends LogicBase {

    public int multiplier;
    public int wheel; // What wheel will be halted next

    public int[] wheelPos = new int[3];

    public boolean[] lines = new boolean[5];

    public LogicSlotGame() {
        super(false, 3, "x_slotgame", 9, 3);
    }

    @Override
    public void actionTouch(int action) {
        if(action == 0){
            if(multiplier < 5) multiplier++;
        } else {
            if(turnstate == 2){
                turnstate = 3;
            } else if(turnstate == 3){
                Spin();
            }
        }
    }

    @Override
    public void updateMotion() {

    }

    @Override
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
                    Result();
                }
            }
        }

    }

    @Override
    public void start2() {
        Fill_Grid();
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

    @Override
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

    @Override
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

    private void Fill_Grid() {
        for(int y = 0; y < 3; y++) {
            int[] gridtemp = Get_Grid((y) % 9);
            for(int x = 0; x < 9; x++) {
                grid[x][y] = gridtemp[x];
            }
        }
    }

    private int[] Get_Grid(int id) {
        switch(id) {
            case 0: return new int[] { 1, 0, 5, 4, 3, 2, 0, 5, 4 };
            case 1: return new int[] { 1, 2, 0, 5, 4, 3, 2, 0, 5 };
            case 2: return new int[] { 1, 3, 2, 0, 5, 4, 3, 2, 0 };
        }
        return null;
    }

    private void Spin() {
        if(wheel < 3) {
            wheel++;
        }
    }

    public int Get_Value(int index) {
        return grid[index % 9][index / 9];
    }

    private void Result() {
        int pos0 = wheelPos[0] / 48;
        int pos1 = wheelPos[1] / 48;
        int pos2 = wheelPos[2] / 48;
        if(multiplier >= 1) CheckWheel(grid[(pos0 + 1) % 9][0], grid[(pos1 + 1) % 9][1], grid[(pos2 + 1) % 9][2], 0);
        if(multiplier >= 2) CheckWheel(grid[(pos0 + 0) % 9][0], grid[(pos1 + 0) % 9][1], grid[(pos2 + 0) % 9][2], 1);
        if(multiplier >= 3) CheckWheel(grid[(pos0 + 2) % 9][0], grid[(pos1 + 2) % 9][1], grid[(pos2 + 2) % 9][2], 2);
        if(multiplier >= 4) CheckWheel(grid[(pos0 + 0) % 9][0], grid[(pos1 + 1) % 9][1], grid[(pos2 + 2) % 9][2], 3);
        if(multiplier >= 5) CheckWheel(grid[(pos0 + 2) % 9][0], grid[(pos1 + 1) % 9][1], grid[(pos2 + 0) % 9][2], 4);
        turnstate = 4;
    }

    public void CheckWheel(int wheel0, int wheel1, int wheel2, int index){
        if(wheel0 == 0){
            reward += 1; // BAR
        }
        if(wheel0 == 1){
            reward += 1; // BAR
        }
        if(wheel0 == 2){
            reward += 1; // BAR
        }
        if(wheel0 == 2 && wheel1 == 2 && wheel2 == 2){
            reward += 5; // KREUZ
            lines[index] = true;
        }
        if(wheel0 == 3 && wheel1 == 3 && wheel2 == 3){
            reward += 10; // PIK
            lines[index] = true;
        }
        if(wheel0 == 4 && wheel1 == 4 && wheel2 == 4){
            reward += 20; // KARO
            lines[index] = true;
        }
        if(wheel0 == 5 && wheel1 == 5 && wheel2 == 5){
            reward += 35; // HERZ
            lines[index] = true;
        }
        if(wheel0 == 1 && wheel1 == 1 && wheel2 == 1){
            reward += 77; // SEVEN
            lines[index] = true;
        }
    }
}
