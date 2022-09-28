package mod.casinocraft.logic.chip;

import mod.casinocraft.logic.LogicModule;
import net.minecraft.nbt.CompoundNBT;

import static mod.casinocraft.util.KeyMap.*;

public class LogicChipBlack extends LogicModule {   // -----

    public int position = 0;
    public int positionMAX = 256;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicChipBlack(int tableID) {
        super(tableID);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {

    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {
        if(action == KEY_LEFT){
            if(position > 0) position-=4;
        } else if(action == KEY_RIGHT){
            if(position < positionMAX) position+=4;
        }
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
        return false;
    }

    public int getID(){
        return 16;
    }



}
