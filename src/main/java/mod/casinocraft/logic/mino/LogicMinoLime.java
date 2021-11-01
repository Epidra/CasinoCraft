package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicModule;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CHIP;

public class LogicMinoLime extends LogicModule {   // Simon

    public int[] alpha = new int[4];
    public List<Integer> color_simon  = new ArrayList<>();
    public List<Integer> color_player = new ArrayList<>();
    public int alpha_pos = 0;
    public boolean[] alpha_player = new boolean[4];
    public boolean result;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoLime(int tableID) {
        super(tableID);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {
        color_simon.clear();
        color_simon.add(RANDOM.nextInt(4));
        color_simon.add(RANDOM.nextInt(4));
        color_simon.add(RANDOM.nextInt(4));
        color_player.clear();
        alpha[0] = 0;
        alpha[1] = 0;
        alpha[2] = 0;
        alpha[3] = 0;
        alpha_pos = 0;
        result = false;
        scoreLevel = 1;
        alpha_player[0] = false;
        alpha_player[1] = false;
        alpha_player[2] = false;
        alpha_player[3] = false;
        turnstate = 3;
        alpha[color_simon.get(0)] = getAlpha();
    }

    public void restart(){
        scoreLevel++;
        scorePoint += color_simon.size();
        color_simon.add(RANDOM.nextInt(4));
        color_simon.add(RANDOM.nextInt(4));
        color_player.clear();
        alpha[0] = 0;
        alpha[1] = 0;
        alpha[2] = 0;
        alpha[3] = 0;
        alpha_pos = 0;
        alpha_player[0] = false;
        alpha_player[1] = false;
        alpha_player[2] = false;
        alpha_player[3] = false;
        turnstate = 3;
        result = false;
        alpha[color_simon.get(0)] = getAlpha();
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {

        color_player.add(action);
        alpha[action] = getAlpha();
        setJingle(SOUND_CHIP);
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {

        for(int i = 0; i < 4; i++){
            if(alpha[i] > 0){
                alpha[i]--;
                if(alpha[i] <= 0) alpha[i] = 0;
            }
        }
        if(turnstate == 2){
            if(color_simon.size() == color_player.size()){
                if(alpha[0] == 0 && alpha[1] == 0 && alpha[2] == 0 && alpha[3] == 0){
                    boolean match = true;
                    for(int i = 0; i < color_player.size(); i++){
                        if(color_player.get(i) != color_simon.get(i)){
                            match = false;
                        }
                    }
                    if(!match){
                        turnstate = 4;
                    } else {
                        restart();
                    }
                }
            }
        } else if(turnstate == 3){
            if(alpha[0] == 0 && alpha[1] == 0 && alpha[2] == 0 && alpha[3] == 0){
                alpha_pos++;
                if(alpha_pos == color_simon.size()){
                    turnstate = 2;
                } else {
                    alpha[color_simon.get(alpha_pos)] = getAlpha();
                }
            }
        }
    }

    public void updateMotion() {

    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        alpha[0] = compound.getInt("alpha0");
        alpha[1] = compound.getInt("alpha1");
        alpha[2] = compound.getInt("alpha2");
        alpha[3] = compound.getInt("alpha3");
        {
            int[] i = compound.getIntArray("colorsimon");
            color_simon.clear();
            for(int x : i){
                color_simon.add(x);
            }
        }
        {
            int[] i = compound.getIntArray("colorplayer");
            color_player.clear();
            for(int x : i){
                color_player.add(x);
            }
        }
        alpha_pos = compound.getInt("alphapos");
        alpha_player[0] = compound.getBoolean("alphaplayer0");
        alpha_player[1] = compound.getBoolean("alphaplayer1");
        alpha_player[2] = compound.getBoolean("alphaplayer2");
        alpha_player[3] = compound.getBoolean("alphaplayer3");
        result = compound.getBoolean("result");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putInt("alpha0", alpha[0]);
        compound.putInt("alpha1", alpha[1]);
        compound.putInt("alpha2", alpha[2]);
        compound.putInt("alpha3", alpha[3]);
        compound.putIntArray("colorsimon",  color_simon);
        compound.putIntArray("colorplayer", color_player);
        compound.putInt("alphapos", alpha_pos);
        compound.putBoolean("alphaplayer0", alpha_player[0]);
        compound.putBoolean("alphaplayer1", alpha_player[1]);
        compound.putBoolean("alphaplayer2", alpha_player[2]);
        compound.putBoolean("alphaplayer3", alpha_player[3]);
        compound.putBoolean("result", result);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public int getAlpha(){
        return Math.max(10 - scoreLevel, 2);
    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 40;
    }



}
