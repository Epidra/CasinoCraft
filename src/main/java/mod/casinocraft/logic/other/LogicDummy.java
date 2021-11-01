package mod.casinocraft.logic.other;

import mod.casinocraft.logic.LogicModule;
import net.minecraft.nbt.CompoundNBT;

public class LogicDummy extends LogicModule {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicDummy(int tableID) {
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

    public void load2(CompoundNBT compound){

    }

    public CompoundNBT save2(CompoundNBT compound){
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return true;
    }

    public int getID(){
        return 48;
    }



}
