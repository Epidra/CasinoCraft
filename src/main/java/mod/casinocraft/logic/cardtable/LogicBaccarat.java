package mod.casinocraft.logic.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LogicBaccarat extends LogicBase {

    public final int S_PLAYER = 0;
    public final int S_DEALER = 1;

    public final int I_PLAYER = 0;
    public final int I_DEALER = 1;

    public final int I_STATUS = 2;



    //--------------------CONSTRUCTOR--------------------

    public LogicBaccarat(){
        super(true, false, false, 1, 1, 2);
    }



    //--------------------BASIC--------------------

    public void start2(){
        clrSTK(S_PLAYER);
        clrSTK(S_DEALER);
        setINT(I_PLAYER, 0);
        setINT(I_DEALER, 0);
        setINT(I_STATUS, 0);

        addCRD(S_PLAYER, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4)));
        addCRD(S_PLAYER, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4)));
        addCRD(S_DEALER, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4)));
        addCRD(S_DEALER, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4)));

        getCRD(S_PLAYER, 0).setShift(-32,   0,  8);
        getCRD(S_PLAYER, 0).setShift(-48,   0, 32);
        getCRD(S_PLAYER, 0).setShift(  0, -48,  8);
        getCRD(S_PLAYER, 0).setShift(  0, -48, 32);

        setINT(I_PLAYER, 0);

        for(int i = 0; i < getSTK(S_PLAYER).size(); i++){
            if(getCRD(S_PLAYER, i).number == 0) {
                updINT(I_PLAYER, +1);
            } else if(getCRD(S_PLAYER, i).number <= 9) {
                updINT(I_PLAYER, getCRD(S_PLAYER, i).number + 1);
            }
        }

        setINT(I_DEALER, 0);

        for(int i = 0; i < getSTK(S_DEALER).size(); i++){
            if(getCRD(S_DEALER, i).number == 0) {
                updINT(I_DEALER, +1);
            } else if(getCRD(S_DEALER, i).number <= 9) {
                updINT(I_DEALER, getCRD(S_DEALER, i).number + 1);
            }
        }

        setINT(I_PLAYER, getINT(I_PLAYER) % 10);
        setINT(I_DEALER, getINT(I_DEALER) % 10);

        if(getINT(I_PLAYER) >= 8 || getINT(I_DEALER) >= 8){
            setINT(I_STATUS, 1);
            Result();
        } else {
            setINT(I_STATUS, 2);
        }
    }

    public void actionTouch(int action){
        if(action == 0) Add_Card(true);  // HIT
        if(action == 1) Add_Card(false); // STAND
    }

    public void updateMotion(){
        if(getSTK(S_PLAYER).size() > 0) for(int i = 0; i < getSTK(S_PLAYER).size(); i++){
            getCRD(S_DEALER, i).update();
        }
        if(getSTK(S_DEALER).size() > 0) for(int i = 0; i < getSTK(S_DEALER).size(); i++){
            getCRD(S_DEALER, i).update();
        }
    }

    public void updateLogic(){

    }



    //--------------------CUSTOM--------------------

    private void Add_Card(boolean player) {
        if(player) {
            setINT(I_PLAYER, 0);
            addCRD(S_PLAYER, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), -48, 0));
            for(int i = 0; i < getSTK(S_PLAYER).size(); i++){
                if(getCRD(S_PLAYER, i).number == 0) {
                    updINT(I_PLAYER, +1);
                } else if(getCRD(S_PLAYER, i).number <= 9) {
                    updINT(I_PLAYER, getCRD(S_PLAYER, i).number + 1);
                }
            }
            setINT(I_PLAYER, getINT(I_PLAYER) % 10);
        }

        boolean temp_draw = false;

        if(getSTK(S_PLAYER).size() == 2 || getINT(I_DEALER) <= 3) { temp_draw = true; } else if(getINT(I_DEALER) == 4 && getINT(I_PLAYER) <= 7) { temp_draw = true; } else if(getINT(I_DEALER) == 5 && getINT(I_PLAYER) >= 4 && getINT(I_PLAYER) <= 7) { temp_draw = true; } else if(getINT(I_DEALER) == 6 && getINT(I_PLAYER) >= 6 && getINT(I_PLAYER) <= 7) { temp_draw = true; }

        if(temp_draw) {
            setINT(I_DEALER, 0);
            addCRD(S_DEALER, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -48));
            for(int i = 0; i < getSTK(S_DEALER).size(); i++){
                if(getCRD(S_DEALER, i).number == 0) {
                    updINT(I_DEALER, +1);
                } else if(getCRD(S_DEALER, i).number <= 9) {
                    updINT(I_DEALER, getCRD(S_DEALER, i).number + 1);
                }
            }
            setINT(I_DEALER, getINT(I_DEALER) % 10);
        }
        Result();
    }

    private void Result() {
        turnstate(4);
        if(getINT(I_STATUS) == 2) setINT(I_STATUS, 3);
        if(getINT(I_DEALER) <  getINT(I_PLAYER)){ hand("The Player Wins!"); reward(2); }
        if(getINT(I_DEALER) >  getINT(I_PLAYER)){ hand("The House Wins!");  reward(0); }
        if(getINT(I_DEALER) == getINT(I_PLAYER)){ hand("DRAW!");            reward(1); }
    }

}
