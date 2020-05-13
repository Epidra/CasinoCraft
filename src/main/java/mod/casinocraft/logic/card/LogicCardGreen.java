package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.CompoundNBT;

public class LogicCardGreen extends LogicBase {   // -----

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardGreen(int tableID) {
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




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 5;
    }

}
