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

public class LogicAceyDeucey extends LogicBase {

    final int C_CARD_0 = 0;
    final int C_CARD_1 = 1;
    final int C_CARD_2 = 2;

    final int I_SPREAD = 0;

    final int B_DOUBLEBET = 0;



    //--------------------CONSTRUCTOR--------------------

    public LogicAceyDeucey(){
        super(true, false, false, 1, 1, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        setINT(I_SPREAD, -1);
        hand("");
        setCRD(0, C_CARD_0, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -25));
        setCRD(0, C_CARD_1, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50));
        setCRD(0, C_CARD_2, new Card(-1, -1));
        setINT(I_SPREAD, -1);
        turnstate(3);
        setBOL(B_DOUBLEBET, false);
    }

    public void actionTouch(int action){
        setBOL(B_DOUBLEBET, action == 0);
        setCRD(0, C_CARD_2, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50));
        turnstate(3);
    }

    public void updateMotion(){
        for(int i = 0; i < 3; i++) {
            getCRD(0, C_CARD_0 + i).update();
        }
    }

    public void updateLogic() {
        if(turnstate() == 2) {

        }
        if(turnstate() == 3) {
            if(getCRD(0, C_CARD_0).number == getCRD(0, C_CARD_1).number) {
                if(getCRD(0, C_CARD_2).number == -1) {
                    if(getCRD(0, C_CARD_0).shiftY == 0) {
                        setCRD(0, C_CARD_2, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -75));
                    }
                } else {
                    if(getCRD(0, C_CARD_2).shiftY == 0) {
                        if(getCRD(0, C_CARD_0).number == getCRD(0, C_CARD_2).number) {
                            hand("Drilling!");
                            reward(11);
                            turnstate(4);
                        } else {
                            hand("Tie!");
                            reward(1);
                            turnstate(4);
                        }
                    }
                }
            } else if(getCRD(0, C_CARD_0).SortedNumber() == getCRD(0, C_CARD_1).SortedNumber() + 1 || getCRD(0, C_CARD_0).SortedNumber() + 1 == getCRD(0, C_CARD_1).SortedNumber()) {
                if(getCRD(0, C_CARD_0).shiftY == 0) {
                    hand("Tie!");
                    reward(1);
                    turnstate(4);
                }
            } else {
                if(getCRD(0, C_CARD_2).number == -1) {
                    if(getCRD(0, C_CARD_1).shiftY == 0) {
                        setINT(I_SPREAD, getCRD(0, C_CARD_0).SortedNumber() - getCRD(0, C_CARD_1).SortedNumber());
                        if(getINT(I_SPREAD) < 0) setINT(I_SPREAD, getINT(I_SPREAD) * -1);
                        updINT(I_SPREAD, -1);
                        turnstate(2);
                        hand("Double Your Bet..?");
                    }
                } else {
                    if(getCRD(0, C_CARD_2).shiftY == 0) {
                        if(getCRD(0, C_CARD_0).SortedNumber() < getCRD(0, C_CARD_2).SortedNumber() && getCRD(0, C_CARD_2).SortedNumber() < getCRD(0, C_CARD_1).SortedNumber()) {
                            hand("In Between");
                            result();
                            turnstate(4);
                        } else if(getCRD(0, C_CARD_0).SortedNumber() > getCRD(0, C_CARD_2).SortedNumber() && getCRD(0, C_CARD_2).SortedNumber() > getCRD(0, C_CARD_1).SortedNumber()) {
                            hand("In Between");
                            result();
                            turnstate(4);
                        } else {
                            hand("Lost!");
                            reward(0);
                            turnstate(4);
                        }
                    }
                }
            }
        }
    }



    //--------------------CUSTOM--------------------

    private void result(){
        if(getINT(I_SPREAD) == 1) reward(getBOL(B_DOUBLEBET) ? 12 : 6); // 1:5
        if(getINT(I_SPREAD) == 2) reward(getBOL(B_DOUBLEBET) ? 10 : 5); // 1:4
        if(getINT(I_SPREAD) == 3) reward(getBOL(B_DOUBLEBET) ?  6 : 3); // 1:2
        if(getINT(I_SPREAD) >= 4) reward(getBOL(B_DOUBLEBET) ?  4 : 2); // 1:1
    }

}
