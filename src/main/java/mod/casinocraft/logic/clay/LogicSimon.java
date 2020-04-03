package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class LogicSimon extends LogicBase {

    public int[] alpha = new int[4];
    public List<Integer> color_simon  = new ArrayList<>();
    public List<Integer> color_player = new ArrayList<>();
    public int alpha_pos = 0;
    public boolean[] alpha_player = new boolean[4];
    public boolean result;

    public LogicSimon(int table) {
        super(true, table, "c_simon");
    }

    public int getAlpha(){
        return 10 - scoreLevel > 2 ? 10 - scoreLevel : 2;
    }


    @Override
    public void actionTouch(int action) {

        color_player.add(action);
        alpha[action] = getAlpha();
        //if(color_player.size() == color_simon.size()){
        //    turnstate = 3;
        //}
    }

    @Override
    public void updateMotion() {

    }

    @Override
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

//        for(int i = 0; i < 4; i++) {
//        if(alpha_player[i]) {
//        if(alpha[i] < 1) alpha[i] += Get_Speed();
//        if(alpha[i] >= 1) { alpha[i] = 1.00f; alpha_player[i] = false; }
//        } else {
//        if(alpha[i] > 0) alpha[i] -= Get_Speed();
//        if(alpha[i] <= 0) alpha[i] = 0.00f;
//        }
//        }
//
//        if(turnstate == Turnstate.TURN_COMPUTER) {
//        if(alpha_pos < color_simon.Count) {
//        if(alpha[0] == 0 && alpha[1] == 0 && alpha[2] == 0 && alpha[3] == 0) {
//        alpha_pos++;
//        int temp = 0;
//        foreach(int c in color_simon) {
//        temp++;
//        if(alpha_pos == temp) {
//        alpha[c] += Get_Speed();
//        alpha_player[c] = true;
//        break;
//        }
//        }
//        if(alpha_pos == color_simon.Count) turnstate = Turnstate.TURN_PLAYER;
//        }
//        }
//        }
//
//        if(turnstate < Turnstate.GAMEOVER) {
//        if(alpha[0] == 0 && alpha[1] == 0 && alpha[2] == 0 && alpha[3] == 0) {
//        if(result) {
//        Restart();
//        }
//        if(color_player.Count == color_simon.Count) {
//        bool error = false;
//        int index_simon = 0;
//        foreach(int c1 in color_simon) {
//        int index_player = 0;
//        foreach(int c2 in color_player) {
//        if(index_simon == index_player) {
//        if(c1 != c2) error = true;
//        }
//        index_player++;
//        }
//        index_simon++;
//        }
//        if(error) {
//        turnstate = Turnstate.GAMEOVER;
//        result = true;
//        } else {
//        result = true;
//        alpha[0] += Get_Speed(); alpha_player[0] = true;
//        alpha[1] += Get_Speed(); alpha_player[1] = true;
//        alpha[2] += Get_Speed(); alpha_player[2] = true;
//        alpha[3] += Get_Speed(); alpha_player[3] = true;
//        JK.Play_Sound(ENUM.Sound.CLEARD);
//        }
//        }
//        }
//        }
//        return ENUM.Command.VOID;
    }

    @Override
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

    @Override
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

    @Override
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

}




