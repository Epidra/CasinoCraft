package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class LogicMinoLime extends LogicBase {   // Simon

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
        //if(color_player.size() == color_simon.size()){
        //    turnstate = 3;
        //}
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion() {

    }

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




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(NBTTagCompound compound){
        alpha[0] = compound.getInteger("alpha0");
        alpha[1] = compound.getInteger("alpha1");
        alpha[2] = compound.getInteger("alpha2");
        alpha[3] = compound.getInteger("alpha3");
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
        alpha_pos = compound.getInteger("alphapos");
        alpha_player[0] = compound.getBoolean("alphaplayer0");
        alpha_player[1] = compound.getBoolean("alphaplayer1");
        alpha_player[2] = compound.getBoolean("alphaplayer2");
        alpha_player[3] = compound.getBoolean("alphaplayer3");
        result = compound.getBoolean("result");
    }

    public NBTTagCompound save2(NBTTagCompound compound){
        int[] array1 = new int[color_player.size()];
        for(int i = 0; i < array1.length; i++){
            array1[i] = color_player.get(i);
        }
        int[] array2 = new int[color_simon.size()];
        for(int i = 0; i < array2.length; i++){
            array2[i] = color_simon.get(i);
        }
        compound.setInteger("alpha0", alpha[0]);
        compound.setInteger("alpha1", alpha[1]);
        compound.setInteger("alpha2", alpha[2]);
        compound.setInteger("alpha3", alpha[3]);
        compound.setIntArray("colorsimon",  array2);
        compound.setIntArray("colorplayer", array1);
        compound.setInteger("alphapos", alpha_pos);
        compound.setBoolean("alphaplayer0", alpha_player[0]);
        compound.setBoolean("alphaplayer1", alpha_player[1]);
        compound.setBoolean("alphaplayer2", alpha_player[2]);
        compound.setBoolean("alphaplayer3", alpha_player[3]);
        compound.setBoolean("result", result);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    public int getAlpha(){
        return Math.max(10 - scoreLevel, 2);
    }




    //----------------------------------------SUPPORT----------------------------------------//

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
