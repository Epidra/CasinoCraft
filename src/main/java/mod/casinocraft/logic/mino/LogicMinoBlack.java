package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicModule;
import net.minecraft.nbt.CompoundTag;

public class LogicMinoBlack extends LogicModule {   // -----

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoBlack(int tableID) {
        super(tableID);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {

    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {

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





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 32;
    }



}
