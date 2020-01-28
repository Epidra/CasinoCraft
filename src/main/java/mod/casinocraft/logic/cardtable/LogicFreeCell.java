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

public class LogicFreeCell extends LogicBase {

    public static int S_FIELD0 = 0;
    public static int S_FIELD1 = 1;
    public static int S_FIELD2 = 2;
    public static int S_FIELD3 = 3;

    public static int S_FIELD4 = 4;
    public static int S_FIELD5 = 5;
    public static int S_FIELD6 = 6;
    public static int S_FIELD7 = 7;

    public static int S_FINISH0 = 8;
    public static int S_FINISH1 = 9;
    public static int S_FINISH2 = 10;
    public static int S_FINISH3 = 11;

    public static int S_CELL = 12;

    public static int I_COMPRESS = 0;
    public static int I_TIMER = 1;



    //--------------------CONSTRUCTOR--------------------

    public LogicFreeCell(){
        super(true, false, false, 1, 1, 16);
    }



    //--------------------BASIC--------------------

    public void start2(){
        List<Card> deck = ShuffleDeck();

        TransferCards(S_FIELD0, deck, 0, 7);
        TransferCards(S_FIELD1, deck, 1, 7);
        TransferCards(S_FIELD2, deck, 2, 7);
        TransferCards(S_FIELD3, deck, 3, 7);
        TransferCards(S_FIELD4, deck, 4, 6);
        TransferCards(S_FIELD5, deck, 5, 6);
        TransferCards(S_FIELD6, deck, 6, 6);
        TransferCards(S_FIELD7, deck, 7, 6);

        addCRD(S_CELL, new Card(-1, -1));
        addCRD(S_CELL, new Card(-1, -1));
        addCRD(S_CELL, new Card(-1, -1));
        addCRD(S_CELL, new Card(-1, -1));

        selector = new Vector2(-1, -1);

        setINT(I_COMPRESS, 400);
        setINT(I_TIMER, -1);
    }

    public void actionTouch(int action){
        if(getINT(I_TIMER) == -1){
            if(action == -1) Free_Cell(0);
            if(action == -2) Free_Cell(1);
            if(action == -3) Free_Cell(2);
            if(action == -4) Free_Cell(3);
            if(action == -5) TouchFinish(0);
            if(action == -6) TouchFinish(1);
            if(action == -7) TouchFinish(2);
            if(action == -8) TouchFinish(3);
            if(action == -9) setINT(I_TIMER, 1);
            if(action >=  0){ TouchField(action%8, action/8); }
        }
    }

    public void updateLogic(){
        if(getINT(I_TIMER) == 0){
            updINT(I_TIMER, -1);
            boolean[] done = new boolean[4];
            done[0] = done[1] = done[2] = done[3] = false;
            for(int x1 = 0; x1 < 4; x1++){
                if(getCRD(S_CELL, x1).suit != -1){
                    for(int x2 = 0; x2 < 4; x2++){
                        boolean copy = false;
                        if(getSTK(S_FINISH0 + x2).size() == 0) { if(getCRD(S_CELL, x1).number == 0){
                                if(!done[x2]){ copy = true; } }
                        } else { if((getCRD(S_CELL, x1).number - 1 == getCRD(S_FINISH0 + x2, (getSTK(S_FINISH0 + x2).size()) - 1).number) && getCRD(S_FINISH0 + x2, (getSTK(S_FINISH0 + x2).size()) - 1).suit == getCRD(S_CELL, x1).suit) {
                                if(!done[x2]){ copy = true; } }
                        }
                        if(copy){
                            getCRD(S_CELL, x1).setShift(0, 16, 0);
                            addCRD(S_FINISH0 + x2, getCRD(S_CELL, x1));
                            setCRD(S_CELL, x1, new Card(-1, -1));
                            selector.set(-1,  -1);
                            setINT(I_TIMER, 16);
                            done[x2] = true;
                        }
                    }
                }
            }
            for(int x1 = 0; x1 < 8; x1++){
                for(int x2 = 0; x2 < 4; x2++){
                    if(getSTK(S_FIELD0 + x1).size() > 0) {
                        boolean copy = false;
                        if(getSTK(S_FINISH0 + x2).size() == 0) {
                            if(getCRD(S_FIELD0 + x1, getSTK(S_FIELD0 + x1).size() - 1).number == 0){
                                if(!done[x2]){ copy = true;
                                }
                            }
                        } else {
                            if((getCRD(S_FIELD0 + x1, getSTK(S_FIELD0 + x1).size() - 1).number - 1 == getCRD(S_FINISH0 + x2, getSTK(S_FINISH0 + x2).size() - 1).number) && getCRD(S_FINISH0 + x2, getSTK(S_FINISH0 + x2).size() - 1).suit == getCRD(S_FIELD0 + x1, getSTK(S_FIELD0 + x1).size() - 1).suit) {
                                if(!done[x2]){
                                    copy = true;
                                }
                            }
                        }
                        if(copy){
                            getCRD(S_FIELD0 + x1, getSTK(S_FINISH0 + x1).size() - 1).setShift(0, 16, 0);
                            addCRD(S_FINISH0 + x2, getCRD(S_CELL, x1));
                            rmvCRD(S_FIELD0 + x1, getSTK(S_FINISH0 + x1).size() - 1);
                            selector.set(-1,  -1);
                            setINT(I_TIMER, 16);
                            done[x2] = true;
                        }
                    }
                }
            }
            Compress();
        } else if(getINT(I_TIMER) > 0){
            updINT(I_TIMER, -1);
        }
        if(turnstate() == 2){
            if(getSTK(S_FINISH0).size() == 13 && getSTK(S_FINISH1).size() == 13 && getSTK(S_FINISH2).size() == 13 && getSTK(S_FINISH3).size() == 13 && turnstate() < 4) {
                scoreScore(100);
                turnstate(4);
            }
        }
        //if(compressDisplay > compress) {
        //    compressDisplay -= 0.25f;
        //    if(compressDisplay < compress) {
        //        compressDisplay = compress;
        //    }
        //}
        //if(compressDisplay < compress) {
        //    compressDisplay += 0.25f;
        //    if(compressDisplay > compress) {
        //        compressDisplay = compress;
        //    }
        //}
    }

    public void updateMotion(){
        for(int x = 0; x < 8; x++){
            if(getSTK(S_FIELD0 + x).size() > 0) for(Card c : getSTK(S_FIELD0 + x)){
                c.update();
            }
        }
        for(int x = 0; x < 4; x++){
            if(getSTK(S_FINISH0 + x).size() > 0) for(Card c : getSTK(S_FINISH0 + x)){
                c.update();
            }
        }
        for(Card c : getSTK(S_CELL)){
            c.update();
        }
    }



    //--------------------CUSTOM--------------------

    private void TransferCards(List<Card> cards_field2, List<Card> deck, int position, int count){
        for(int i = position; i < position + count; i++){
            cards_field2.add(deck.get(position));
            deck.remove(position);
        }
        for(int x = 0; x < 8; x++){
            int y = 0;
            for(Card c : cards_field[x]){
                c.setShift(0, -20*y, 60-10*y + x*3);
                y++;
            }
        }
    }

    private void TransferCards(List<Card> cards_field2, List<Card> deck, int position, int count, int shiftX, int shiftY){
        for(int i = position; i < position + count; i++){
            deck.get(position).setShift(shiftX, shiftY, 0);
            cards_field2.add(deck.get(position));
            deck.remove(position);
        }
    }

    private List<Card> ShuffleDeck() {
        List<Card> stack = new ArrayList<Card>();
        List<Card> deck  = new ArrayList<Card>();

        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 13; x++) {
                stack.add(new Card(x, y));
            }
        }

        while(stack.size() > 1) {
            int r = RANDOM.nextInt(stack.size() - 1);
            deck.add(stack.get(r));
            stack.remove(r);
        }
        deck.add(stack.get(0));

        return deck;
    }

    private void Free_Cell(int cell) {
        if(selector.matches(-1, -1)) {
            if(!getCRD(S_CELL, cell).equals(-1, -1)) {
                selector.set(cell, -2);
            }
        } else if(selector.Y >= 0) {
            if(getCRD(S_CELL, cell).suit == -1) {
                if(selector.Y == getSTK(S_FIELD0 + selector.X).size() - 1) {
                    getCRD(S_FIELD0 + selector.X, getSTK(S_FIELD0 + selector.X).size() - 1).setShift(0, 16, 0);
                    setCRD(S_CELL, cell, getCRD(S_FIELD0 + selector.X, getSTK(S_FIELD0 + selector.X).size() - 1));
                    rmvCRD(S_FIELD0 + selector.X, getSTK(S_FIELD0 + selector.X).size() - 1);
                    selector.set(-1, -1);
                }
            }
        }
    }

    private void TouchFinish(int slot) {
        if(!selector.matches(-1, -1)) {
            if(selector.Y == -2) { // Cell-to-Finish
                boolean copy = false;
                if(getSTK(S_FINISH0 + slot).size() == 0) {
                    if(getCRD(S_CELL, selector.X).number == 0){
                        copy = true;
                    }
                } else {
                    if((getCRD(S_CELL, selector.X).number - 1 == getCRD(S_FINISH0 + slot, getSTK(S_FINISH0 + slot).size() - 1).number) && getCRD(S_FINISH0 + slot, getSTK(S_FINISH0 + slot).size() - 1).suit == getCRD(S_CELL, selector.X).suit) {
                        copy = true;
                    }
                }
                if(copy){
                    getCRD(S_CELL, selector.X).setShift(0, 16, 0);
                    addCRD(S_FINISH0 + slot, getCRD(S_CELL, selector.X));
                    setCRD(S_CELL, selector.X, new Card(-1, -1));
                    selector.set(-1,  -1);
                }
            } else { // Field-to-Finish
                if(selector.Y == getSTK(S_FIELD0 + selector.X).size() - 1) {
                    boolean copy = false;
                    if(getSTK(S_FINISH0 + slot).size() == 0) {
                        if(getCRD(S_FIELD0 + selector.X, getSTK(S_FIELD0 + selector.X).size() - 1).number == 0){
                            copy = true;
                        }
                    } else {
                        if((getCRD(S_FIELD0 + selector.X, selector.Y).number - 1 == getCRD(S_FINISH0 + slot, getSTK(S_FINISH0 + slot).size() - 1).number) && getCRD(S_FINISH0 + slot, getSTK(S_FINISH0 + slot).size() - 1).suit == getCRD(S_FIELD0 + selector.X, selector.Y).suit) {
                            copy = true;
                        }
                    }
                    if(copy){
                        getCRD(S_FIELD0 + selector.X, getSTK(S_FIELD0 + selector.X).size() - 1).setShift(0, 16, 0);
                        addCRD(S_FINISH0 + slot, getCRD(S_FIELD0 + selector.X, getSTK(S_FIELD0 + selector.X).size() - 1));
                        rmvCRD(S_FIELD0 + selector.X, getSTK(S_FIELD0 + selector.X).size() - 1);
                        selector.set(-1,  -1);
                    }
                }
            }
        }
        Compress();
    }

    private void TouchField(int x, int y) {
        int x2 = x;
        int y2 = y;
        if(selector.Y == -2) {
            if(!MoveStack(x, y)) {
                selector.set(-1,  -1);
            }
        } else
        if(getSTK(S_FIELD0 + x2).size() >= y2 - 1) {
            if(selector.matches(-1,  -1)) {
                y2 = getSTK(S_FIELD0 + x2).size() <= y2 ? getSTK(S_FIELD0 + x2).size() - 1 : y2;
                float tempCard = getCRD(S_FIELD0 + x2, y2).number;
                float tempSuit = getCRD(S_FIELD0 + x2, y2).suit;
                for(int i = y2; i < getSTK(S_FIELD0 + x2).size(); i++) {
                    if(i != getSTK(S_FIELD0 + x2).size() - 1) {
                        if(((getCRD(S_FIELD0 + x2, i).number - 1 != getCRD(S_FIELD0 + x2, i + 1).number) && !(getCRD(S_FIELD0 + x2, i).number == 1 && getCRD(S_FIELD0 + x2, i + 1).number == 13)) || !DifferentColors(getCRD(S_FIELD0 + x2, i).suit, getCRD(S_FIELD0 + x2, i + 1).suit)) {
                            return;
                        }
                    }
                }
                selector.set(x2, y2);
            } else {
                if(!MoveStack(x, y)) {
                    selector.set(-1, -1);
                }
            }
        }
        Compress();
    }

    private boolean MoveStack(int x, int y) {
        int x2 = x;
        int y2 = cards_field[x2].size() - 1;
        if(selector.Y != -2) { // Field-to-Field
            if(cards_field[x2].size() == 0 || ((cards_field[selector.X].get(selector.Y).number + 1 == cards_field[x2].get(y2).number) && DifferentColors(cards_field[x2].get(y2).suit, cards_field[selector.X].get(selector.Y).suit))) {
                TransferCards(cards_field[x2], cards_field[selector.X], selector.Y, getSTK(S_FIELD0 + selector.X).size() - selector.Y, 0, 16);
                selector.set(-1, -1);
                return true;
            }
        } else { // Cell-to-Field
            if(cards_field[x2].size() == 0 || ((cards_freecell[selector.X].number + 1 == cards_field[x2].get(y2).number) && DifferentColors(cards_field[x2].get(y2).suit, cards_freecell[selector.X].suit))) {
                cards_freecell[selector.X].setShift(0, 16, 0);
                cards_field[x2].add(new Card(cards_freecell[selector.X]));
                cards_freecell[selector.X].set(-1, -1);
                selector.set(-1, -1);
                return true;
            }
        }
        return false;
    }

    private void Compress() {
        int i = 0;
        for(int x = 0; x < 8; x++) {
            if(cards_field[x].size() > i) i = cards_field[x].size();
        }
        if(i > 6) {
            compress = i-3;
        } else {
            compress = 0;
        }
    }

    private boolean DifferentColors(float a, float b) {
        if(a == 0 || a == 1) if(b == 2 || b == 3) return true;
        if(a == 2 || a == 3) return b == 0 || b == 1;
        return false;
    }

}
