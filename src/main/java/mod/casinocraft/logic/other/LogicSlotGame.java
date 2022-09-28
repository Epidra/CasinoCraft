package mod.casinocraft.logic.other;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.SoundMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

public class LogicSlotGame extends LogicModule {

    /** the index of the wheel that will be stopped next **/
    public int wheelSTOP;
    /** The position on the wheel, maximum: 9 * 48 **/
    public float[] wheelPos = new float[3];
    /** Indicates if a winning line should be animated **/
    public boolean[] lines = new boolean[5];
    /** the highlight value of each miltiplier light, to help choose the actual color value **/
    public int[] lights = new int[5];
    /** the speed at which the wheel rotates **/
    public float[] speed = new float[3];
    /** The amount the multiplier inceases when hitting a certain combo **/
    public final int[] multi = new int[6];

    private int lightUp = 0;
    private final float speedUP = 0.7f;
    private final float speedDOWN = 1.2f;
    private final float speedMAX = 16.0f;
    private int pullTimer = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicSlotGame(int tableID, Item module) {
        super(tableID, 9, 3);
        scoreLives = 1;
        createSlotWheels(moduleToInt(module) % 16, (moduleToInt(module) / 16) + 1);
        wheelPos[0] = 0.00f;
        wheelPos[1] = 0.00f;
        wheelPos[2] = 0.00f;
        multi[0] =  1;
        multi[1] = 10;
        multi[2] = 15;
        multi[3] = 20;
        multi[4] = 45;
        multi[5] = 77;
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {
        setJingle(SoundMap.SOUND_PAUSE_OFF);
        scoreLevel = 1; // multiplier
        scoreLives = 1;
        wheelSTOP = -1;
        lines[0] = false;
        lines[1] = false;
        lines[2] = false;
        lines[3] = false;
        lines[4] = false;

        lights[0] = 1;
        lights[1] = 0;
        lights[2] = 0;
        lights[3] = 0;
        lights[4] = 0;

        speed[0] = 0.00f;
        speed[1] = 0.00f;
        speed[2] = 0.00f;
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {
        if(action == 0){ // ENTER
            if(scoreLevel < 5){
                setJingle(SoundMap.SOUND_TETRIS);
                scoreLevel++;
                lights[1] = scoreLevel >= 2 ? 1 : 0;
                lights[2] = scoreLevel >= 3 ? 1 : 0;
                lights[3] = scoreLevel >= 4 ? 1 : 0;
                lights[4] = scoreLevel >= 5 ? 1 : 0;
            } else {
                setJingle(SoundMap.SOUND_MENU);
                lightUp = 15;
                lights[0] = 2;
                lights[3] = 2;
                lights[4] = 2;

            }
        }
        if(action == 1){ // SPACE
            if(turnstate == 2){
                turnstate = 3;
                setJingle(SoundMap.SOUND_PAUSE_ON);
                pull();
            } else if(turnstate == 3){
                spin();
                setJingle(SoundMap.SOUND_PAUSE_ON);
            }
        }
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {
        if(wheelSTOP == -1 && speed[2] >= speedMAX / 4){
            wheelSTOP = 0;
        }
        if(wheelSTOP == 3 && turnstate == 3){
            if(wheelPos[0] % 48 == 0 && wheelPos[1] % 48 == 0 && wheelPos[2] % 48 == 0){
                result();
            }
        }
    }

    public void updateMotion() {
        if(lightUp > 0){
            lightUp--;
            lights[0] = lights[0] == 1 ? 2 : 1;
            lights[1] = lights[1] == 1 ? 2 : 1;
            lights[2] = lights[2] == 1 ? 2 : 1;
            lights[3] = lights[3] == 1 ? 2 : 1;
            lights[4] = lights[4] == 1 ? 2 : 1;
            if(lightUp == 0){
                lights[0] = 1;
                lights[1] = 1;
                lights[2] = 1;
                lights[3] = 1;
                lights[4] = 1;
            }
        }
        if(turnstate == 3){
            if(wheelSTOP <= 0 && speed[0] < speedMAX                            ){ speed[0] += speedUP;   if(speed[0] >= speedMAX) speed[0] = speedMAX; }
            if(wheelSTOP >  0 && speed[0] >        1                            ){ speed[0] -= speedDOWN; if(speed[0] <=        1) speed[0] =        1; }
            if(wheelSTOP <  1 && speed[1] < speedMAX && speed[0] >= speedMAX / 4){ speed[1] += speedUP;   if(speed[1] >= speedMAX) speed[1] = speedMAX; }
            if(wheelSTOP >  1 && speed[1] >        1                            ){ speed[1] -= speedDOWN; if(speed[1] <=        1) speed[1] =        1; }
            if(wheelSTOP <  2 && speed[2] < speedMAX && speed[1] >= speedMAX / 4){ speed[2] += speedUP;   if(speed[2] >= speedMAX) speed[2] = speedMAX; }
            if(wheelSTOP >  2 && speed[2] >        1                            ){ speed[2] -= speedDOWN; if(speed[2] <=        1) speed[2] =        1; }
        }

        wheelPos[0] = (wheelPos[0] + (int)speed[0]) % (9*48);
        wheelPos[1] = (wheelPos[1] + (int)speed[1]) % (9*48);
        wheelPos[2] = (wheelPos[2] + (int)speed[2]) % (9*48);

        if(wheelSTOP > 0 && (int)wheelPos[0] % 48 == 0){ wheelPos[0] = (int)wheelPos[0]; speed[0] = 0; }
        if(wheelSTOP > 1 && (int)wheelPos[1] % 48 == 0){ wheelPos[1] = (int)wheelPos[1]; speed[1] = 0; }
        if(wheelSTOP > 2 && (int)wheelPos[2] % 48 == 0){ wheelPos[2] = (int)wheelPos[2]; speed[2] = 0; }

        if(pullTimer > 0){
            pullTimer--;
            if(pullTimer <= 0){
                scoreLives = 1;
            }
        }
    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        wheelSTOP   = compound.getInt("wheelstop");
        wheelPos[0] = compound.getInt("wheelpos0");
        wheelPos[1] = compound.getInt("wheelpos1");
        wheelPos[2] = compound.getInt("wheelpos2");
        lines[0] = compound.getBoolean("lines0");
        lines[1] = compound.getBoolean("lines1");
        lines[2] = compound.getBoolean("lines2");
        lines[3] = compound.getBoolean("lines3");
        lines[4] = compound.getBoolean("lines4");
        lights[0] = scoreLevel >= 1 ? 1 : 0;
        lights[1] = scoreLevel >= 2 ? 1 : 0;
        lights[2] = scoreLevel >= 3 ? 1 : 0;
        lights[3] = scoreLevel >= 4 ? 1 : 0;
        lights[4] = scoreLevel >= 5 ? 1 : 0;
    }

    public CompoundTag save2(CompoundTag compound){
        compound.putInt(  "wheelstop", wheelSTOP);
        compound.putFloat("wheelpos0", wheelPos[0]);
        compound.putFloat("wheelpos1", wheelPos[1]);
        compound.putFloat("wheelpos2", wheelPos[2]);
        compound.putBoolean("lines0", lines[0]);
        compound.putBoolean("lines1", lines[1]);
        compound.putBoolean("lines2", lines[2]);
        compound.putBoolean("lines3", lines[3]);
        compound.putBoolean("lines4", lines[4]);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void spin() {
        if(wheelSTOP >= 0 && wheelSTOP < 3) {
            wheelSTOP++;
            pull();
        }
    }

    private void pull(){
        pullTimer = 6;
        scoreLives = 2;
    }

    private void result() {
        lights[0] = 0;
        lights[1] = 0;
        lights[2] = 0;
        lights[3] = 0;
        lights[4] = 0;
        int pos0 = (int)wheelPos[0] / 48;
        int pos1 = (int)wheelPos[1] / 48;
        int pos2 = (int)wheelPos[2] / 48;
        if(scoreLevel >= 1) checkWheel(grid[(pos0 +  9) % 9][0], grid[(pos1 +  9) % 9][1], grid[(pos2 +  9) % 9][2], 0);
        if(scoreLevel >= 2) checkWheel(grid[(pos0 +  8) % 9][0], grid[(pos1 +  8) % 9][1], grid[(pos2 +  8) % 9][2], 1);
        if(scoreLevel >= 3) checkWheel(grid[(pos0 + 10) % 9][0], grid[(pos1 + 10) % 9][1], grid[(pos2 + 10) % 9][2], 2);
        if(scoreLevel >= 4) checkWheel(grid[(pos0 +  8) % 9][0], grid[(pos1 +  9) % 9][1], grid[(pos2 + 10) % 9][2], 3);
        if(scoreLevel >= 5) checkWheel(grid[(pos0 + 10) % 9][0], grid[(pos1 +  9) % 9][1], grid[(pos2 +  8) % 9][2], 4);
        turnstate = 4;
        if(reward[0] >= 2){
            jingle = SoundMap.SOUND_REWARD;
        }
    }

    private void checkWheel(int wheel0, int wheel1, int wheel2, int index){
        boolean found = false;
        if(wheel0 == 0                              ){ reward[0] += multi[0]; found = true; } // BAR (Wheel 1)
        if(               wheel1 == 0               ){ reward[0] += multi[0]; found = true; } // BAR (Wheel 2)
        if(                              wheel2 == 0){ reward[0] += multi[0]; found = true; } // BAR (Wheel 3)
        if(wheel0 == 2 && wheel1 == 2 && wheel2 == 2){ reward[0] += multi[1]; found = true; } // Color 1
        if(wheel0 == 3 && wheel1 == 3 && wheel2 == 3){ reward[0] += multi[2]; found = true; } // Color 2
        if(wheel0 == 4 && wheel1 == 4 && wheel2 == 4){ reward[0] += multi[3]; found = true; } // Color 3
        if(wheel0 == 5 && wheel1 == 5 && wheel2 == 5){ reward[0] += multi[4]; found = true; } // Color 4
        if(wheel0 == 1 && wheel1 == 1 && wheel2 == 1){ reward[0] += multi[5]; found = true; } // LUCKY 777
        if(found){
            lines[index] = true;
            lights[index] = 2;
        }
    }

    private void createSlotWheels(int index, int mod){
        for(int y = 0; y < 3; y++) {
            int mod2 = y == 1 ? mod*2 : y == 2 ? -(mod*2) : 0;
            int[] temp = getWheel((index + mod2 + 16) % 16);
            for(int x = 0; x < 9; x++) {
                grid[x][y] = temp[x];
            }
        }
    }

    private int[] getWheel(int index){
        scoreLives = 1;
        if(index ==  0) return new int[]{5,   4, 0, 1, 2,   3, 1, 2, 0};
        if(index ==  1) return new int[]{5,   0, 4, 1, 2,   1, 3, 2, 0};
        if(index ==  2) return new int[]{5,   0, 1, 4, 2,   1, 2, 3, 0};
        if(index ==  3) return new int[]{5,   0, 1, 2, 4,   1, 2, 0, 3};
        if(index ==  4) return new int[]{5,   3, 1, 2, 0,   4, 2, 0, 1};
        if(index ==  5) return new int[]{5,   1, 3, 2, 0,   2, 4, 0, 1};
        if(index ==  6) return new int[]{5,   1, 2, 3, 0,   2, 0, 4, 1};
        if(index ==  7) return new int[]{5,   1, 2, 0, 3,   2, 0, 1, 4};
        if(index ==  8) return new int[]{5,   3, 2, 0, 1,   4, 0, 1, 2};
        if(index ==  9) return new int[]{5,   2, 3, 0, 1,   0, 4, 1, 2};
        if(index == 10) return new int[]{5,   2, 0, 3, 1,   0, 1, 4, 2};
        if(index == 11) return new int[]{5,   2, 0, 1, 3,   0, 1, 2, 4};
        if(index == 12) return new int[]{5,   4, 0, 1, 2,   3, 1, 2, 0};
        if(index == 13) return new int[]{5,   0, 4, 1, 2,   1, 3, 2, 0};
        if(index == 14) return new int[]{5,   0, 1, 4, 2,   1, 2, 3, 0};
        if(index == 15) return new int[]{5,   0, 1, 2, 4,   1, 2, 0, 3};
        scoreLives = 0;
        return new int[]{0,   0, 0, 0, 0,   0, 0, 0, 0};
    }

    private int moduleToInt(Item module){
        if(module == CasinoKeeper.MODULE_CARD_WHITE.get()     ) return  0;
        if(module == CasinoKeeper.MODULE_CARD_ORANGE.get()    ) return  1;
        if(module == CasinoKeeper.MODULE_CARD_MAGENTA.get()   ) return  2;
        if(module == CasinoKeeper.MODULE_CARD_LIGHT_BLUE.get()) return  3;
        if(module == CasinoKeeper.MODULE_CARD_YELLOW.get()    ) return  4;
        if(module == CasinoKeeper.MODULE_CARD_LIME.get()      ) return  5;
        if(module == CasinoKeeper.MODULE_CARD_PINK.get()      ) return  6;
        if(module == CasinoKeeper.MODULE_CARD_GRAY.get()      ) return  7;
        if(module == CasinoKeeper.MODULE_CARD_LIGHT_GRAY.get()) return  8;
        if(module == CasinoKeeper.MODULE_CARD_CYAN.get()      ) return  9;
        if(module == CasinoKeeper.MODULE_CARD_PURPLE.get()    ) return 10;
        if(module == CasinoKeeper.MODULE_CARD_BLUE.get()      ) return 11;
        if(module == CasinoKeeper.MODULE_CARD_BROWN.get()     ) return 12;
        if(module == CasinoKeeper.MODULE_CARD_GREEN.get()     ) return 13;
        if(module == CasinoKeeper.MODULE_CARD_RED.get()       ) return 14;
        if(module == CasinoKeeper.MODULE_CARD_BLACK.get()     ) return 15;

        if(module == CasinoKeeper.MODULE_MINO_WHITE.get()     ) return 16;
        if(module == CasinoKeeper.MODULE_MINO_ORANGE.get()    ) return 17;
        if(module == CasinoKeeper.MODULE_MINO_MAGENTA.get()   ) return 18;
        if(module == CasinoKeeper.MODULE_MINO_LIGHT_BLUE.get()) return 19;
        if(module == CasinoKeeper.MODULE_MINO_YELLOW.get()    ) return 20;
        if(module == CasinoKeeper.MODULE_MINO_LIME.get()      ) return 21;
        if(module == CasinoKeeper.MODULE_MINO_PINK.get()      ) return 22;
        if(module == CasinoKeeper.MODULE_MINO_GRAY.get()      ) return 23;
        if(module == CasinoKeeper.MODULE_MINO_LIGHT_GRAY.get()) return 24;
        if(module == CasinoKeeper.MODULE_MINO_CYAN.get()      ) return 25;
        if(module == CasinoKeeper.MODULE_MINO_PURPLE.get()    ) return 26;
        if(module == CasinoKeeper.MODULE_MINO_BLUE.get()      ) return 27;
        if(module == CasinoKeeper.MODULE_MINO_BROWN.get()     ) return 28;
        if(module == CasinoKeeper.MODULE_MINO_GREEN.get()     ) return 29;
        if(module == CasinoKeeper.MODULE_MINO_RED.get()       ) return 30;
        if(module == CasinoKeeper.MODULE_MINO_BLACK.get()     ) return 31;

        if(module == CasinoKeeper.MODULE_CHIP_WHITE.get()     ) return 32;
        if(module == CasinoKeeper.MODULE_CHIP_ORANGE.get()    ) return 33;
        if(module == CasinoKeeper.MODULE_CHIP_MAGENTA.get()   ) return 34;
        if(module == CasinoKeeper.MODULE_CHIP_LIGHT_BLUE.get()) return 35;
        if(module == CasinoKeeper.MODULE_CHIP_YELLOW.get()    ) return 36;
        if(module == CasinoKeeper.MODULE_CHIP_LIME.get()      ) return 37;
        if(module == CasinoKeeper.MODULE_CHIP_PINK.get()      ) return 38;
        if(module == CasinoKeeper.MODULE_CHIP_GRAY.get()      ) return 39;
        if(module == CasinoKeeper.MODULE_CHIP_LIGHT_GRAY.get()) return 40;
        if(module == CasinoKeeper.MODULE_CHIP_CYAN.get()      ) return 41;
        if(module == CasinoKeeper.MODULE_CHIP_PURPLE.get()    ) return 42;
        if(module == CasinoKeeper.MODULE_CHIP_BLUE.get()      ) return 43;
        if(module == CasinoKeeper.MODULE_CHIP_BROWN.get()     ) return 44;
        if(module == CasinoKeeper.MODULE_CHIP_GREEN.get()     ) return 45;
        if(module == CasinoKeeper.MODULE_CHIP_RED.get()       ) return 46;
        if(module == CasinoKeeper.MODULE_CHIP_BLACK.get()     ) return 47;

        return 0;
    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 49;
    }



}
