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

public class LogicRougeEtNoir extends LogicBase {

    public final int S_ROUGE = 0;
    public final int S_NOIR = 1;

    public static int I_ROUGE = 0;
    public static int I_NOIR = 1;



    //--------------------CONSTRUCTOR--------------------

    public LogicRougeEtNoir(){
        super(true, false, false, 1, 1, 2);
    }



    //--------------------BASIC--------------------

    public void start2(){
        selector.set(-1, -1);
        clrSTK(S_ROUGE);
        clrSTK(S_NOIR);
        setINT(I_ROUGE, 0);
        setINT(I_NOIR, 0);
    }

    public void actionTouch(int action){
        selector.X = action;
        turnstate(3);
        addCRD(S_ROUGE, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50));
    }

    public void updateMotion(){
        for(Card c : getSTK(S_ROUGE)) { c.update(); }
        for(Card c : getSTK(S_NOIR) ) { c.update(); }
    }

    public void updateLogic(){
        if(turnstate() == 3) {
            if(getINT(I_ROUGE) < 31) {
                if(getSTK(S_ROUGE).get(getSTK(S_ROUGE).size() - 1).shiftY >= -10) {
                    updINT(I_ROUGE, (getSTK(S_ROUGE).get(getSTK(S_ROUGE).size() - 1).number >= 9 ? 10 : (getSTK(S_ROUGE).get(getSTK(S_ROUGE).size() - 1).number + 1)));
                    if(getINT(I_ROUGE) >= 31) {
                        addCRD(S_NOIR, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50));
                    } else {
                        addCRD(S_ROUGE, (new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50)));
                    }
                }
            } else {
                if(getSTK(S_NOIR).get(getSTK(S_NOIR).size() - 1).shiftY >= -10) {
                    updINT(I_NOIR, getSTK(S_NOIR).get(getSTK(S_NOIR).size() - 1).number >= 9 ? 10 : (getSTK(S_NOIR).get(getSTK(S_NOIR).size() - 1).number + 1));
                    if(getINT(I_NOIR) >= 31) {
                        result();
                    } else {
                        addCRD(S_NOIR, (new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50)));
                    }
                }
            }
        }
    }



    //--------------------CUSTOM--------------------

    public void result(){
        turnstate(4);
        if(getINT(I_ROUGE) <  getINT(I_NOIR)) { hand("Rouge Wins!"); if(selector.X == 0) { reward(2); } }
        if(getINT(I_ROUGE) >  getINT(I_NOIR)) { hand("Noir Wins!");  if(selector.X == 1) { reward(2); } }
        if(getINT(I_ROUGE) == getINT(I_NOIR)) { hand("Tie!");                              reward(1); }
    }

}
