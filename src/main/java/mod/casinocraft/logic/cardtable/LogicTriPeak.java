package mod.casinocraft.logic.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Card;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LogicTriPeak extends LogicBase {
    
    public static int I_COMBO = 0;
    
    public static int S_FIELD = 0; // fixed: 28 Cards
    public static int S_STACK = 1;
    public static int S_RESERVE = 2;



    //--------------------CONSTRUCTOR--------------------

    public LogicTriPeak(){
        super(true, false, false, 1, 1, 3);
    }



    //--------------------BASIC--------------------

    public void start2(){
        scoreLives(5 - difficulty);
        
        setINT(I_COMBO, 0);

        List<Card> deck = ShuffleDeck();


        deck.get( 3).setShift(+16*1, -20*1, 20);
        deck.get( 4).setShift(-16*1, -20*1, 20);
        deck.get( 9).setShift(+16*2, -20*2, 10);
        deck.get(10).setShift(    0, -20*2, 10);
        deck.get(11).setShift(-16*2, -20*2, 10);
        deck.get(18).setShift(+16*3, -20*3,  0);
        deck.get(19).setShift(+16*1, -20*3,  0);
        deck.get(20).setShift(-16*1, -20*3,  0);

        deck.get( 5).setShift(+16*1, -20*1, 20);
        deck.get( 6).setShift(-16*1, -20*1, 20);
        deck.get(12).setShift(+16*2, -20*2, 10);
        deck.get(13).setShift(    0, -20*2, 10);
        deck.get(14).setShift(-16*2, -20*2, 10);
        deck.get(21).setShift(+16*3, -20*3,  0);
        deck.get(22).setShift(+16*1, -20*3,  0);
        deck.get(23).setShift(-16*1, -20*3,  0);
        deck.get(24).setShift(-16*3, -20*3,  0);

        deck.get( 7).setShift(+16*1, -20*1, 20);
        deck.get( 8).setShift(-16*1, -20*1, 20);
        deck.get(15).setShift(+16*2, -20*2, 10);
        deck.get(16).setShift(    0, -20*2, 10);
        deck.get(17).setShift(-16*2, -20*2, 10);
        deck.get(25).setShift(+16*1, -20*3,  0);
        deck.get(26).setShift(-16*1, -20*3,  0);
        deck.get(27).setShift(-16*3, -20*3,  0);
        
        for(int i = 0; i < 28; i++) {
            setCRD(S_FIELD, i, deck.get(i));
        }
        selector = new Vector2(9, 12);
        for(int i = 0; i < 28; i++){
            deck.remove(0);
        }
        setSTK(S_RESERVE, deck);
        clrSTK(S_STACK);
        DrawReserve();
    }

    public void Restart() {

        List<Card> deck = ShuffleDeck();


        deck.get( 3).setShift(+16*1, -20*1, 20);
        deck.get( 4).setShift(-16*1, -20*1, 20);
        deck.get( 9).setShift(+16*2, -20*2, 10);
        deck.get(10).setShift(    0, -20*2, 10);
        deck.get(11).setShift(-16*2, -20*2, 10);
        deck.get(18).setShift(+16*3, -20*3,  0);
        deck.get(19).setShift(+16*1, -20*3,  0);
        deck.get(20).setShift(-16*1, -20*3,  0);

        deck.get( 5).setShift(+16*1, -20*1, 20);
        deck.get( 6).setShift(-16*1, -20*1, 20);
        deck.get(12).setShift(+16*2, -20*2, 10);
        deck.get(13).setShift(    0, -20*2, 10);
        deck.get(14).setShift(-16*2, -20*2, 10);
        deck.get(21).setShift(+16*3, -20*3,  0);
        deck.get(22).setShift(+16*1, -20*3,  0);
        deck.get(23).setShift(-16*1, -20*3,  0);
        deck.get(24).setShift(-16*3, -20*3,  0);

        deck.get( 7).setShift(+16*1, -20*1, 20);
        deck.get( 8).setShift(-16*1, -20*1, 20);
        deck.get(15).setShift(+16*2, -20*2, 10);
        deck.get(16).setShift(    0, -20*2, 10);
        deck.get(17).setShift(-16*2, -20*2, 10);
        deck.get(25).setShift(+16*1, -20*3,  0);
        deck.get(26).setShift(-16*1, -20*3,  0);
        deck.get(27).setShift(-16*3, -20*3,  0);

        for(int i = 0; i < 28; i++) {
            setCRD(S_FIELD, i, deck.get(i));
        }
        selector = new Vector2(9, 12);
        for(int i = 0; i < 28; i++){
            deck.remove(0);
        }
        setSTK(S_RESERVE, deck);
        clrSTK(S_STACK);
        DrawReserve();
    }

    public void actionTouch(int action){
        //	 if(action == -1) CompareCards(28);
        if(action == -2) DrawReserve();
            //else if(action == -3) CompareCards(29);
        else TouchField(action % 20, action / 20);
    }

    public void updateMotion(){
        if(turnstate() >= 2){
            if(getSTK(S_STACK).size() > 0) for(int i = 0; i < getSTK(S_STACK).size(); i++){
                getSTK(S_STACK).get(i).update();
            }
            if(getSTK(S_RESERVE).size() > 0) for(int i = 0; i < getSTK(S_RESERVE).size(); i++){
                getSTK(S_RESERVE).get(i).update();
            }
            if(getSTK(S_FIELD).size() > 0) for(int i = 0; i < getSTK(S_FIELD).size(); i++){
                getSTK(S_FIELD).get(i).update();
            }
        }
    }
    
    public void updateLogic(){
        
    }



    //--------------------CUSTOM--------------------

    private List<Card> ShuffleDeck() {
        List<Card> stack = new ArrayList<Card>();
        List<Card> deck = new ArrayList<Card>();

        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 13; x++) {
                stack.add(new Card(x, y));
            }
        }
        while(stack.size() > 0) {
            int r = stack.size() == 1 ? 0 : RANDOM.nextInt(stack.size() - 1);
            deck.add(stack.get(r));
            stack.remove(r);
        }

        return deck;
    }

    public void DrawReserve() {
        setINT(I_COMBO, 0);
        if(getSTK(S_RESERVE).size() > 0) {
            getSTK(S_RESERVE).get(0).shiftX = 64;
            addCRD(S_STACK, getCRD(S_RESERVE, 0));
            rmvCRD(S_RESERVE, 0);
        } else {
            if(scoreLives() == 0){
                turnstate(4);
            } else {
                scoreLives(-1);
                Restart();
            }
        }
    }

    public void TouchField(int x, int y) {
        if(x ==  3 && y == 2) if(IsCardSelectable( 0)) { CompareCards( 0); }
        if(x ==  4 && y == 2) if(IsCardSelectable( 0)) { CompareCards( 0); }
        if(x ==  9 && y == 2) if(IsCardSelectable( 1)) { CompareCards( 1); }
        if(x == 10 && y == 2) if(IsCardSelectable( 1)) { CompareCards( 1); }
        if(x == 15 && y == 2) if(IsCardSelectable( 2)) { CompareCards( 2); }
        if(x == 16 && y == 2) if(IsCardSelectable( 2)) { CompareCards( 2); }

        if(x ==  2 && y == 3) if(IsCardSelectable( 3)) { CompareCards( 3); }
        if(x ==  3 && y == 3) if(IsCardSelectable( 3)) { CompareCards( 3); } else if(IsCardSelectable( 0)) { CompareCards( 0); }
        if(x ==  4 && y == 3) if(IsCardSelectable( 4)) { CompareCards( 4); } else if(IsCardSelectable( 0)) { CompareCards( 0); }
        if(x ==  5 && y == 3) if(IsCardSelectable( 4)) { CompareCards( 4); }
        if(x ==  8 && y == 3) if(IsCardSelectable( 5)) { CompareCards( 5); }
        if(x ==  9 && y == 3) if(IsCardSelectable( 5)) { CompareCards( 5); } else if(IsCardSelectable( 1)) { CompareCards( 1); }
        if(x == 10 && y == 3) if(IsCardSelectable( 6)) { CompareCards( 6); } else if(IsCardSelectable( 1)) { CompareCards( 1); }
        if(x == 11 && y == 3) if(IsCardSelectable( 6)) { CompareCards( 6); }
        if(x == 14 && y == 3) if(IsCardSelectable( 7)) { CompareCards( 7); }
        if(x == 15 && y == 3) if(IsCardSelectable( 7)) { CompareCards( 7); } else if(IsCardSelectable( 2)) { CompareCards( 2); }
        if(x == 16 && y == 3) if(IsCardSelectable( 8)) { CompareCards( 8); } else if(IsCardSelectable( 2)) { CompareCards( 2); }
        if(x == 17 && y == 3) if(IsCardSelectable( 8)) { CompareCards( 8); }

        if(x ==  1 && y == 4) if(IsCardSelectable( 9)) { CompareCards( 9); }
        if(x ==  2 && y == 4) if(IsCardSelectable( 9)) { CompareCards( 9); } else if(IsCardSelectable( 3)) { CompareCards( 3); }
        if(x ==  3 && y == 4) if(IsCardSelectable(10)) { CompareCards(10); } else if(IsCardSelectable( 3)) { CompareCards( 3); }
        if(x ==  4 && y == 4) if(IsCardSelectable(10)) { CompareCards(10); } else if(IsCardSelectable( 4)) { CompareCards( 4); }
        if(x ==  5 && y == 4) if(IsCardSelectable(11)) { CompareCards(11); } else if(IsCardSelectable( 4)) { CompareCards( 4); }
        if(x ==  6 && y == 4) if(IsCardSelectable(11)) { CompareCards(11); }
        if(x ==  7 && y == 4) if(IsCardSelectable(12)) { CompareCards(12); }
        if(x ==  8 && y == 4) if(IsCardSelectable(12)) { CompareCards(12); } else if(IsCardSelectable( 5)) { CompareCards( 5); }
        if(x ==  9 && y == 4) if(IsCardSelectable(13)) { CompareCards(13); } else if(IsCardSelectable( 5)) { CompareCards( 5); }
        if(x == 10 && y == 4) if(IsCardSelectable(13)) { CompareCards(13); } else if(IsCardSelectable( 6)) { CompareCards( 6); }
        if(x == 11 && y == 4) if(IsCardSelectable(14)) { CompareCards(14); } else if(IsCardSelectable( 6)) { CompareCards( 6); }
        if(x == 12 && y == 4) if(IsCardSelectable(14)) { CompareCards(14); }
        if(x == 13 && y == 4) if(IsCardSelectable(15)) { CompareCards(15); }
        if(x == 14 && y == 4) if(IsCardSelectable(15)) { CompareCards(15); } else if(IsCardSelectable( 7)) { CompareCards( 7); }
        if(x == 15 && y == 4) if(IsCardSelectable(16)) { CompareCards(16); } else if(IsCardSelectable( 7)) { CompareCards( 7); }
        if(x == 16 && y == 4) if(IsCardSelectable(16)) { CompareCards(16); } else if(IsCardSelectable( 8)) { CompareCards( 8); }
        if(x == 17 && y == 4) if(IsCardSelectable(17)) { CompareCards(17); } else if(IsCardSelectable( 8)) { CompareCards( 8); }
        if(x == 18 && y == 4) if(IsCardSelectable(17)) { CompareCards(17); }

        if(x ==  0 && y == 5) if(IsCardSelectable(18)) { CompareCards(18); }
        if(x ==  1 && y == 5) if(IsCardSelectable(18)) { CompareCards(18); } else if(IsCardSelectable( 9)) { CompareCards( 9); }
        if(x ==  2 && y == 5) if(IsCardSelectable(19)) { CompareCards(19); } else if(IsCardSelectable( 9)) { CompareCards( 9); }
        if(x ==  3 && y == 5) if(IsCardSelectable(19)) { CompareCards(19); } else if(IsCardSelectable(10)) { CompareCards(10); }
        if(x ==  4 && y == 5) if(IsCardSelectable(20)) { CompareCards(20); } else if(IsCardSelectable(10)) { CompareCards(10); }
        if(x ==  5 && y == 5) if(IsCardSelectable(20)) { CompareCards(20); } else if(IsCardSelectable(11)) { CompareCards(11); }
        if(x ==  6 && y == 5) if(IsCardSelectable(21)) { CompareCards(21); } else if(IsCardSelectable(11)) { CompareCards(11); }
        if(x ==  7 && y == 5) if(IsCardSelectable(21)) { CompareCards(21); } else if(IsCardSelectable(12)) { CompareCards(12); }
        if(x ==  8 && y == 5) if(IsCardSelectable(22)) { CompareCards(22); } else if(IsCardSelectable(12)) { CompareCards(12); }
        if(x ==  9 && y == 5) if(IsCardSelectable(22)) { CompareCards(22); } else if(IsCardSelectable(13)) { CompareCards(13); }
        if(x == 10 && y == 5) if(IsCardSelectable(23)) { CompareCards(23); } else if(IsCardSelectable(13)) { CompareCards(13); }
        if(x == 11 && y == 5) if(IsCardSelectable(23)) { CompareCards(23); } else if(IsCardSelectable(14)) { CompareCards(14); }
        if(x == 12 && y == 5) if(IsCardSelectable(24)) { CompareCards(24); } else if(IsCardSelectable(14)) { CompareCards(14); }
        if(x == 13 && y == 5) if(IsCardSelectable(24)) { CompareCards(24); } else if(IsCardSelectable(15)) { CompareCards(15); }
        if(x == 14 && y == 5) if(IsCardSelectable(25)) { CompareCards(25); } else if(IsCardSelectable(15)) { CompareCards(15); }
        if(x == 15 && y == 5) if(IsCardSelectable(25)) { CompareCards(25); } else if(IsCardSelectable(16)) { CompareCards(16); }
        if(x == 16 && y == 5) if(IsCardSelectable(26)) { CompareCards(26); } else if(IsCardSelectable(16)) { CompareCards(16); }
        if(x == 17 && y == 5) if(IsCardSelectable(26)) { CompareCards(26); } else if(IsCardSelectable(17)) { CompareCards(17); }
        if(x == 18 && y == 5) if(IsCardSelectable(27)) { CompareCards(27); } else if(IsCardSelectable(17)) { CompareCards(17); }
        if(x == 19 && y == 5) if(IsCardSelectable(27)) { CompareCards(27); }

        if(x ==  0 && y == 6) if(IsCardSelectable(18)) { CompareCards(18); }
        if(x ==  1 && y == 6) if(IsCardSelectable(18)) { CompareCards(18); }
        if(x ==  2 && y == 6) if(IsCardSelectable(19)) { CompareCards(19); }
        if(x ==  3 && y == 6) if(IsCardSelectable(19)) { CompareCards(19); }
        if(x ==  4 && y == 6) if(IsCardSelectable(20)) { CompareCards(20); }
        if(x ==  5 && y == 6) if(IsCardSelectable(20)) { CompareCards(20); }
        if(x ==  6 && y == 6) if(IsCardSelectable(21)) { CompareCards(21); }
        if(x ==  7 && y == 6) if(IsCardSelectable(21)) { CompareCards(21); }
        if(x ==  8 && y == 6) if(IsCardSelectable(22)) { CompareCards(22); }
        if(x ==  9 && y == 6) if(IsCardSelectable(22)) { CompareCards(22); }
        if(x == 10 && y == 6) if(IsCardSelectable(23)) { CompareCards(23); }
        if(x == 11 && y == 6) if(IsCardSelectable(23)) { CompareCards(23); }
        if(x == 12 && y == 6) if(IsCardSelectable(24)) { CompareCards(24); }
        if(x == 13 && y == 6) if(IsCardSelectable(24)) { CompareCards(24); }
        if(x == 14 && y == 6) if(IsCardSelectable(25)) { CompareCards(25); }
        if(x == 15 && y == 6) if(IsCardSelectable(25)) { CompareCards(25); }
        if(x == 16 && y == 6) if(IsCardSelectable(26)) { CompareCards(26); }
        if(x == 17 && y == 6) if(IsCardSelectable(26)) { CompareCards(26); }
        if(x == 18 && y == 6) if(IsCardSelectable(27)) { CompareCards(27); }
        if(x == 19 && y == 6) if(IsCardSelectable(27)) { CompareCards(27); }
    }

    private boolean IsCardSelectable(int id) {
        switch(id) {
            case  0: if(getCRD(S_FIELD,  0).suit != -1 && getCRD(S_FIELD,  3).suit == -1 && getCRD(S_FIELD,  4).suit == -1) return true; break;
            case  1: if(getCRD(S_FIELD,  1).suit != -1 && getCRD(S_FIELD,  5).suit == -1 && getCRD(S_FIELD,  6).suit == -1) return true; break;
            case  2: if(getCRD(S_FIELD,  2).suit != -1 && getCRD(S_FIELD,  7).suit == -1 && getCRD(S_FIELD,  8).suit == -1) return true; break;

            case  3: if(getCRD(S_FIELD,  3).suit != -1 && getCRD(S_FIELD,  9).suit == -1 && getCRD(S_FIELD, 10).suit == -1) return true; break;
            case  4: if(getCRD(S_FIELD,  4).suit != -1 && getCRD(S_FIELD, 10).suit == -1 && getCRD(S_FIELD, 11).suit == -1) return true; break;
            case  5: if(getCRD(S_FIELD,  5).suit != -1 && getCRD(S_FIELD, 12).suit == -1 && getCRD(S_FIELD, 13).suit == -1) return true; break;
            case  6: if(getCRD(S_FIELD,  6).suit != -1 && getCRD(S_FIELD, 13).suit == -1 && getCRD(S_FIELD, 14).suit == -1) return true; break;
            case  7: if(getCRD(S_FIELD,  7).suit != -1 && getCRD(S_FIELD, 15).suit == -1 && getCRD(S_FIELD, 16).suit == -1) return true; break;
            case  8: if(getCRD(S_FIELD,  8).suit != -1 && getCRD(S_FIELD, 16).suit == -1 && getCRD(S_FIELD, 17).suit == -1) return true; break;

            case  9: if(getCRD(S_FIELD,  9).suit != -1 && getCRD(S_FIELD, 18).suit == -1 && getCRD(S_FIELD, 19).suit == -1) return true; break;
            case 10: if(getCRD(S_FIELD, 10).suit != -1 && getCRD(S_FIELD, 19).suit == -1 && getCRD(S_FIELD, 20).suit == -1) return true; break;
            case 11: if(getCRD(S_FIELD, 11).suit != -1 && getCRD(S_FIELD, 20).suit == -1 && getCRD(S_FIELD, 21).suit == -1) return true; break;
            case 12: if(getCRD(S_FIELD, 12).suit != -1 && getCRD(S_FIELD, 21).suit == -1 && getCRD(S_FIELD, 22).suit == -1) return true; break;
            case 13: if(getCRD(S_FIELD, 13).suit != -1 && getCRD(S_FIELD, 22).suit == -1 && getCRD(S_FIELD, 23).suit == -1) return true; break;
            case 14: if(getCRD(S_FIELD, 14).suit != -1 && getCRD(S_FIELD, 23).suit == -1 && getCRD(S_FIELD, 24).suit == -1) return true; break;
            case 15: if(getCRD(S_FIELD, 15).suit != -1 && getCRD(S_FIELD, 24).suit == -1 && getCRD(S_FIELD, 25).suit == -1) return true; break;
            case 16: if(getCRD(S_FIELD, 16).suit != -1 && getCRD(S_FIELD, 25).suit == -1 && getCRD(S_FIELD, 26).suit == -1) return true; break;
            case 17: if(getCRD(S_FIELD, 17).suit != -1 && getCRD(S_FIELD, 26).suit == -1 && getCRD(S_FIELD, 27).suit == -1) return true; break;

            case 18: if(getCRD(S_FIELD, 18).suit != -1) return true; break;
            case 19: if(getCRD(S_FIELD, 19).suit != -1) return true; break;
            case 20: if(getCRD(S_FIELD, 20).suit != -1) return true; break;
            case 21: if(getCRD(S_FIELD, 21).suit != -1) return true; break;
            case 22: if(getCRD(S_FIELD, 22).suit != -1) return true; break;
            case 23: if(getCRD(S_FIELD, 23).suit != -1) return true; break;
            case 24: if(getCRD(S_FIELD, 24).suit != -1) return true; break;
            case 25: if(getCRD(S_FIELD, 25).suit != -1) return true; break;
            case 26: if(getCRD(S_FIELD, 26).suit != -1) return true; break;
            case 27: if(getCRD(S_FIELD, 27).suit != -1) return true; break;
        }
        return false;
    }

    public void CompareCards(int id) {

        if(getCRD(S_FIELD, id).number + 1 == getCRD(S_STACK, (getSTK(S_STACK).size() - 1)).number || getCRD(S_FIELD, id).number - 1 == getCRD(S_STACK, (getSTK(S_STACK).size() - 1)).number || (getCRD(S_FIELD, id).number == 0 && getCRD(S_STACK, (getSTK(S_STACK).size() - 1)).number == 12) || (getCRD(S_FIELD, id).number == 12 && getCRD(S_STACK, (getSTK(S_STACK).size() - 1)).number == 0)) {
            updINT(I_COMBO, 1);
            scoreScore(50 * getINT(I_COMBO));
            getCRD(S_FIELD, id).shiftX = 0;
            getCRD(S_FIELD, id).shiftY = +24;
            addCRD(S_STACK, getCRD(S_FIELD, id));
            setCRD(S_FIELD, id, new Card(-1, -1));
            if(getCRD(S_FIELD, 0).suit == -1 && getCRD(S_FIELD, 1).suit == -1 && getCRD(S_FIELD, 2).suit == -1) Restart();
        }
    }

}
