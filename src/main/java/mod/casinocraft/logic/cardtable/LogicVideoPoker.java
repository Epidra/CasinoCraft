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

public class LogicVideoPoker extends LogicBase {
    
    public static int B_HOLD0 = 0;
    public static int B_HOLD1 = 1;
    public static int B_HOLD2 = 2;
    public static int B_HOLD3 = 3;
    public static int B_HOLD4 = 4;

    public static int S_CARDS = 0;

    public static int I_TICKER = 0;
    public static int I_MOVESTATE = 1;

    //public boolean end;
    //public Card[] card = new Card[5];
    //public boolean[] hold = new boolean[5];
    //private int ticker;
    //private int movestate;

    //--------------------CONSTRUCTOR--------------------

    public LogicVideoPoker(){
        super(true, false, false, 1, 1, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        for(int i = 0; i < 5; i++){
            addCRD(S_CARDS, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, 20 + 5*i));
            setBOL(B_HOLD0 + i, false);
        }
        setINT(I_TICKER, 0);
        setINT(I_MOVESTATE, 0);
    }

    public void actionTouch(int action){
        if(action == 5 && turnstate() == 2){
            turnstate(3);
            setINT(I_MOVESTATE, 1);
            for(int i = 0; i < 5; i++){
                if(!getBOL(B_HOLD0 + i)) hdnCRD(S_CARDS, i);
            }
        } else {
            tglBOL(B_HOLD0 + action);
        }
    }

    public void updateMotion(){
        if(turnstate() == 2) {
            for(int i = 0; i < 5; i++){
                getCRD(S_CARDS, i).update();
            }
        }
    }
    
    public void updateLogic(){
        switch(getINT(I_MOVESTATE)){
            case 0: // NULL
                break;
            case 1: // Cards Move up
                updINT(I_TICKER, 1);
                for(int i = 0; i < 5; i++){
                    if(!getBOL(B_HOLD0  + i)) getCRD(S_CARDS, i).shiftY--;
                }
                if(getINT(I_TICKER) >= 30){
                    for(int i = 0; i < 5; i++){
                        if(!getBOL(B_HOLD0  + i)){
                            int sX = getCRD(S_CARDS, i).shiftX;
                            int sY = getCRD(S_CARDS, i).shiftY;
                            setCRD(S_CARDS, i, new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), sX, sY));
                        }
                    }
                    setINT(I_MOVESTATE, 2);
                }
                break;
            case 2: // Cards Move down
                updINT(I_TICKER, -1);
                for(int i = 0; i < 5; i++){
                    if(!getBOL(B_HOLD0  + i)) getCRD(S_CARDS, i).shiftY++;
                }
                if(getINT(I_TICKER) == 0){
                    setINT(I_MOVESTATE, 3);
                }
                break;
            case 3: // Cards Move Together
                updINT(I_TICKER, 1);
                getCRD(S_CARDS, 0).shiftX+=2;
                getCRD(S_CARDS, 1).shiftX+=1;
                getCRD(S_CARDS, 3).shiftX-=1;
                getCRD(S_CARDS, 4).shiftX-=2;
                if(getINT(I_TICKER) == 48){
                    Sort();
                    setINT(I_MOVESTATE, 4);
                }
                break;
            case 4: // Cards Move apart
                updINT(I_TICKER, -1);
                getCRD(S_CARDS, 0).shiftX-=2;
                getCRD(S_CARDS, 1).shiftX-=1;
                getCRD(S_CARDS, 3).shiftX+=1;
                getCRD(S_CARDS, 4).shiftX+=2;
                if(getINT(I_TICKER) == 0){
                    Result();
                    turnstate(4);
                    setINT(I_MOVESTATE, 0);
                }
                break;
        }
    }



    //--------------------CUSTOM--------------------

    private void Sort() {
        Card card[] = new Card[5];
        card[0] = getCRD(S_CARDS, 0);
        card[1] = getCRD(S_CARDS, 1);
        card[2] = getCRD(S_CARDS, 2);
        card[3] = getCRD(S_CARDS, 3);
        card[4] = getCRD(S_CARDS, 4);
        if(card[0].number > card[4].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[0].number > card[3].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[0].number > card[2].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2]                                       = z; }
        if(card[0].number > card[1].number) { Card z = card[0]; card[0] = card[1]; card[1]                                                          = z; }
        if(card[1].number > card[4].number) { Card z =                    card[1]; card[1] = card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[1].number > card[3].number) { Card z =                    card[1]; card[1] = card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[1].number > card[2].number) { Card z =                    card[1]; card[1] = card[2]; card[2]                                       = z; }
        if(card[2].number > card[4].number) { Card z =                                       card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[2].number > card[3].number) { Card z =                                       card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[3].number > card[4].number) { Card z =                                                          card[3]; card[3] = card[4]; card[4] = z; }

        card[0].shiftX =  48*2;
        card[1].shiftX =  48;
        card[2].shiftX =   0;
        card[3].shiftX = -48;
        card[4].shiftX = -48*2;
        
        setCRD(S_CARDS, 0, new Card(card[0]));
        setCRD(S_CARDS, 1, new Card(card[1]));
        setCRD(S_CARDS, 2, new Card(card[2]));
        setCRD(S_CARDS, 3, new Card(card[3]));
        setCRD(S_CARDS, 4, new Card(card[4]));
        
    }

    private void Result() {
        if(getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 4).number) {
            hand("5 of a Kind");
            reward(20);
        } else if(getCRD(S_CARDS, 0).number == 9 && getCRD(S_CARDS, 1).number == 10 && getCRD(S_CARDS, 2).number == 11 && getCRD(S_CARDS, 3).number == 12 && getCRD(S_CARDS, 4).number == 0 && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 1).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 2).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 3).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 4).suit) {
            hand("ROYAL FLUSH!!");
            reward(16);
        } else if(getCRD(S_CARDS, 0).number <= 9 && getCRD(S_CARDS, 0).number + 1 == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 0).number + 2 == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 0).number + 3 == getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 0).number + 4 == getCRD(S_CARDS, 4).number && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 1).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 2).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 3).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 4).suit) {
            hand("Straight Flush");
            reward(12);
        } else if(getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 0).number != getCRD(S_CARDS, 4).number) {
            hand("4 of a Kind");
            reward(10);
        } else if(getCRD(S_CARDS, 1).number == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 1).number == getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 1).number == getCRD(S_CARDS, 4).number && getCRD(S_CARDS, 1).number != getCRD(S_CARDS, 0).number) {
            hand("4 of a Kind");
            reward(10);
        } else if(getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 0).number != getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 3).number == getCRD(S_CARDS, 4).number) {
            hand("Full House");
            reward(8);
        } else if(getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 0).number != getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 2).number == getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 2).number == getCRD(S_CARDS, 4).number) {
            hand("Full House");
            reward(8);
        } else if(getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 1).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 2).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 3).suit && getCRD(S_CARDS, 0).suit == getCRD(S_CARDS, 4).suit) {
            hand("Flush");
            reward(7);
        } else if(getCRD(S_CARDS, 0).number <= 9 && getCRD(S_CARDS, 0).number + 1 == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 0).number + 2 == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 0).number + 3 == getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 0).number + 4 == getCRD(S_CARDS, 4).number) {
            hand("Straight");
            reward(6);
        } else if(getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 0).number != getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 0).number != getCRD(S_CARDS, 4).number) {
            hand("3 of a Kind");
            reward(4);
        } else if(getCRD(S_CARDS, 1).number == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 1).number == getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 1).number != getCRD(S_CARDS, 0).number && getCRD(S_CARDS, 1).number != getCRD(S_CARDS, 4).number) {
            hand("3 of a Kind");
            reward(4);
        } else if(getCRD(S_CARDS, 2).number == getCRD(S_CARDS, 3).number && getCRD(S_CARDS, 2).number == getCRD(S_CARDS, 4).number && getCRD(S_CARDS, 2).number != getCRD(S_CARDS, 0).number && getCRD(S_CARDS, 2).number != getCRD(S_CARDS, 1).number) {
            hand("3 of a Kind");
            reward(4);
        } else if(getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 2).number == getCRD(S_CARDS, 3).number) {
            hand("Two Pair");
            reward(2);
        } else if(getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 1).number && getCRD(S_CARDS, 3).number == getCRD(S_CARDS, 4).number) {
            hand("Two Pair");
            reward(2);
        } else if(getCRD(S_CARDS, 1).number == getCRD(S_CARDS, 2).number && getCRD(S_CARDS, 3).number == getCRD(S_CARDS, 4).number) {
            hand("Two Pair");
            reward(2);
        } else if((getCRD(S_CARDS, 0).number >= 10 || getCRD(S_CARDS, 0).number == 0) && getCRD(S_CARDS, 0).number == getCRD(S_CARDS, 1).number) {
            hand("Jacks or Better");
            reward(1);
        } else if((getCRD(S_CARDS, 1).number >= 10 || getCRD(S_CARDS, 1).number == 0) && getCRD(S_CARDS, 1).number == getCRD(S_CARDS, 2).number) {
            hand("Jacks or Better");
            reward(1);
        } else if((getCRD(S_CARDS, 2).number >= 10 || getCRD(S_CARDS, 2).number == 0) && getCRD(S_CARDS, 2).number == getCRD(S_CARDS, 3).number) {
            hand("Jacks or Better");
            reward(1);
        } else if((getCRD(S_CARDS, 3).number >= 10 || getCRD(S_CARDS, 3).number == 0) && getCRD(S_CARDS, 3).number == getCRD(S_CARDS, 4).number) {
            hand("Jacks or Better");
            reward(1);
        }
    }

}
