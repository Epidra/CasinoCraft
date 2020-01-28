package mod.casinocraft.logic.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

public class LogicMysticSquare extends LogicBase {



    //--------------------CONSTRUCTOR--------------------

    public LogicMysticSquare(){
        super(false, true, false, 4, 4, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                setGRD(x, y, -1);
                setFLG(x, y, false);
            }
        }
        int i = 0;
        while(i < 15) {
            int x = RANDOM.nextInt(4);
            int y = RANDOM.nextInt(4);
            if(getGRD(x, y) == -1) {
                setGRD(x, y, i);
                i++;
            }
        }
    }

    public void actionTouch(int action){
        Move(action);
    }

    public void updateMotion(){

    }

    public void updateLogic(){

    }



    //--------------------CUSTOM--------------------

    public void Move(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && y > 0) if(getGRD(x, y - 1) == -1) setFLG(x, y, true); // UP
                if(direction == 1 && y < 3) if(getGRD(x, y + 1) == -1) setFLG(x, y, true); // DOWN
                if(direction == 2 && x > 0) if(getGRD(x - 1, y) == -1) setFLG(x, y, true); // LEFT
                if(direction == 3 && x < 3) if(getGRD(x + 1, y) == -1) setFLG(x, y, true); // RIGHT
            }
        }
        Change(direction);
    }

    private void Change(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && getFLG(x, y)) { setGRD(x, y - 1, getGRD(x, y)); setGRD(x, y, -1); setFLG(x, y, false); } // UP
                if(direction == 1 && getFLG(x, y)) { setGRD(x, y + 1, getGRD(x, y)); setGRD(x, y, -1); setFLG(x, y, false); } // DOWN
                if(direction == 2 && getFLG(x, y)) { setGRD(x - 1, y, getGRD(x, y)); setGRD(x, y, -1); setFLG(x, y, false); } // LEFT
                if(direction == 3 && getFLG(x, y)) { setGRD(x + 1, y, getGRD(x, y)); setGRD(x, y, -1); setFLG(x, y, false); } // RIGHT
            }
        }
    }

}
