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

public class LogicBlackJack extends LogicBase {

    public final int S_PLAYER1 = 0;
    public final int S_PLAYER2 = 1;
    public final int S_DEALER = 2;

    public final int I_PLAYER1 = 0;
    public final int I_PLAYER2 = 1;
    public final int I_DEALER = 2;
    public final int I_SPLIT = 3; // 0 - no split, 1 - left cards, 2 - right cards


    //--------------------CONSTRUCTOR--------------------

    public LogicBlackJack(){
        super(true, false, false, 1, 1, 3);
    }



    //--------------------BASIC--------------------

    public void start2(){

        clrSTK(S_PLAYER1);
        clrSTK(S_PLAYER2);
        clrSTK(S_DEALER);

        setINT(I_SPLIT, 0);

        addCRD(S_PLAYER1, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), -32, 0,    8, false));
        addCRD(S_PLAYER1, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), -48, 0,   32, true));
        addCRD(S_PLAYER1, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4),   0, -48,  8, false));
        addCRD(S_PLAYER1, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4),   0, -48, 32, false));

        setINT(I_PLAYER1, Add_Card(getSTK(S_PLAYER1), 0, 0, true));
        setINT(I_PLAYER2, 0);
        setINT(I_DEALER, Add_Card(getSTK(S_DEALER), 0, 0, true));
        if(getINT(I_PLAYER1) == 21) {
            result();
            hdnCRD(S_DEALER, 1);
        }
    }

    public void actionTouch(int action){
        if(action == 0){ // Hit
            if(getINT(I_SPLIT) < 2){
                setINT(I_PLAYER1, Add_Card(getSTK(S_PLAYER1), -48, 0, true));
                if(getINT(I_PLAYER1) > 21) {
                    if(getINT(I_SPLIT) == 0){
                        result();
                    } else {
                        setINT(I_SPLIT, 2);
                    }
                }
            } else {
                setINT(I_PLAYER2, Add_Card(getSTK(S_PLAYER2), -48, 0, true));
                if(getINT(I_PLAYER2) > 21) {
                    if(getINT(I_PLAYER1) > 21) {
                        result();
                    } else {
                        turnstate(3);
                        hdnCRD(S_DEALER, 1);
                    }
                }
            }
        }
        if(action == 1){ // Stand
            if(getINT(I_SPLIT) == 1){
                setINT(I_SPLIT, 2);
            } else {
                turnstate(3);
                hdnCRD(S_DEALER, 1);
            }
        }
        if(action == 2){ // Split
            setINT(I_SPLIT, 1);
            int cardX = getCRD(S_PLAYER1, 1).number;
            int cardY = getCRD(S_PLAYER1, 1).suit;
            rmvCRD(S_PLAYER1, 1);
            addCRD(S_PLAYER1, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), -48, 0,  32, false));
            addCRD(S_PLAYER2, new Card(cardX, cardY, -32, 0,   8, false));
            addCRD(S_PLAYER2, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), -48, 0,   8, false));

            setINT(I_PLAYER1, Add_Card(getSTK(S_PLAYER1), 0, 0, true));
            setINT(I_PLAYER1, Add_Card(getSTK(S_PLAYER2), 0, 0, true));
        }
    }

    public void updateMotion(){
        if(getSTK(S_PLAYER1).size() > 0) for(int i = 0; i < getSTK(S_PLAYER1).size(); i++){ getCRD(S_PLAYER1, i).update(); }
        if(getSTK(S_PLAYER2).size() > 0) for(int i = 0; i < getSTK(S_PLAYER2).size(); i++){ getCRD(S_PLAYER2, i).update(); }
        if(getSTK(S_DEALER) .size() > 0) for(int i = 0; i < getSTK(S_DEALER ).size(); i++){ getCRD(S_DEALER , i).update(); }
    }

    public void updateLogic(){
        if(turnstate() == 3){ // Execute only on playing Player
            if(getCRD(S_DEALER, (getSTK(S_DEALER).size() - 1)).shiftY >= -16){
                if(getINT(I_DEALER) > 16 || (getINT(I_DEALER) > getINT(I_PLAYER1) && getINT(I_DEALER) > getINT(I_PLAYER2))){
                    result();
                } else {
                    setINT(I_DEALER, Add_Card(getSTK(S_DEALER), 0, -48, false));
                }
            }
        }
    }



    //--------------------CUSTOM--------------------

    private void result(){
        turnstate(4);
        if(getINT(I_DEALER)  >  21                                                                            ) { hand("The House gone bust!");  reward(2);
        } else if(getINT(I_PLAYER1) >  21                                                                     ) { hand("The Player gone bust!"); reward(0);
        } else if(getINT(I_PLAYER1) == getINT(I_DEALER) && getSTK(S_PLAYER1).size() >  getSTK(S_DEALER).size()) { hand("The House wins!");       reward(0);
        } else if(getINT(I_PLAYER1) == getINT(I_DEALER) && getSTK(S_PLAYER1).size() == getSTK(S_DEALER).size()) { hand("DRAW");                  reward(1);
        } else if(getINT(I_PLAYER1) == 21           && getSTK(S_PLAYER1).size() == 2                          ) { hand("BLACK JACK");            reward(3);
        } else if(getINT(I_PLAYER1) == getINT(I_DEALER) && getSTK(S_PLAYER1).size() <  getSTK(S_DEALER).size()) { hand("The Player wins!");      reward(2);
        } else if(getINT(I_PLAYER1) >  getINT(I_DEALER)                                                       ) { hand("The Player wins!");      reward(2);
        } else                                                                                                  { hand("The House wins!");       reward(0);
        }
        if(getINT(I_SPLIT) > 0){
            if(getINT(I_DEALER)  >  21                                                                            ) { hand(hand() + " / The House gone bust!");  reward(reward() + 2);
            } else if(getINT(I_PLAYER2) >  21                                                                     ) { hand(hand() + " / The Player gone bust!"); reward(reward() + 0);
            } else if(getINT(I_PLAYER2) == getINT(I_DEALER) && getSTK(S_PLAYER2).size() >  getSTK(S_DEALER).size()) { hand(hand() + " / The House wins!");       reward(reward() + 0);
            } else if(getINT(I_PLAYER2) == getINT(I_DEALER) && getSTK(S_PLAYER2).size() == getSTK(S_DEALER).size()) { hand(hand() + " / DRAW");                  reward(reward() + 1);
            } else if(getINT(I_PLAYER2) == 21           && getSTK(S_PLAYER2).size() == 2                          ) { hand(hand() + " / BLACK JACK");            reward(reward() + 3);
            } else if(getINT(I_PLAYER2) == getINT(I_DEALER) && getSTK(S_PLAYER2).size() <  getSTK(S_DEALER).size()) { hand(hand() + " / The Player wins!");      reward(reward() + 2);
            } else if(getINT(I_PLAYER2) >  getINT(I_DEALER)                                                       ) { hand(hand() + " / The Player wins!");      reward(reward() + 2);
            } else                                                                                                  { hand(hand() + " / The House wins!");       reward(reward() + 0);
            }
        }
    }

    private int Add_Card(List<Card> cards, int shiftX, int shiftY, boolean noCard) {
        int value = 0;
        if(!noCard) cards.add(new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), shiftX, shiftY));
        int ace = 0;
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).number == 0) {
                ace++;
            } else if(cards.get(i).number <= 9) {
                value += cards.get(i).number + 1;
            } else {
                value += 10;
            }
        }
        if(ace > 0) {
            while(ace > 0) {
                if(value <= 10) {
                    value += 11;
                } else {
                    value += 1;
                }
                ace--;
            }
        }
        return value;
    }

}
