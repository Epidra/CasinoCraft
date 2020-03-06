package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogicFreeCell extends LogicBase {

    public List<Card>[] cards_field   = new ArrayList[8];

    public List<Card>[] cards_finish   = new ArrayList[4];

    public Card[] cards_freecell   = new Card[4];

    public int compress = 4;
    public int timer = 0;



    //--------------------CONSTRUCTOR--------------------

    public LogicFreeCell(int table){
        super(false, table, "c_freecell");
    }



    //--------------------BASIC--------------------

    public void start2(){
        List<Card> deck = ShuffleDeck();

        TransferCards(cards_field[0], deck, 0, 7);
        TransferCards(cards_field[1], deck, 1, 7);
        TransferCards(cards_field[2], deck, 2, 7);
        TransferCards(cards_field[3], deck, 3, 7);
        TransferCards(cards_field[4], deck, 4, 6);
        TransferCards(cards_field[5], deck, 5, 6);
        TransferCards(cards_field[6], deck, 6, 6);
        TransferCards(cards_field[7], deck, 7, 6);

        cards_freecell[0] = new Card(-1, -1);
        cards_freecell[1] = new Card(-1, -1);
        cards_freecell[2] = new Card(-1, -1);
        cards_freecell[3] = new Card(-1, -1);

        selector = new Vector2(-1, -1);

        compress = 400;
        timer = -1;
    }

    public void actionTouch(int action){
        if(timer == -1){
            if(action == -1) Free_Cell(0);
            if(action == -2) Free_Cell(1);
            if(action == -3) Free_Cell(2);
            if(action == -4) Free_Cell(3);
            if(action == -5) TouchFinish(0);
            if(action == -6) TouchFinish(1);
            if(action == -7) TouchFinish(2);
            if(action == -8) TouchFinish(3);
            if(action == -9) timer = 1;
            if(action >=  0){ TouchField(action%8, action/8); }
        }
    }

    public void updateLogic(){
        if(timer == 0){
            timer--;
            boolean[] done = new boolean[4];
            done[0] = done[1] = done[2] = done[3] = false;
            for(int x1 = 0; x1 < 4; x1++){
                if(cards_freecell[x1].suit != -1){
                    for(int x2 = 0; x2 < 4; x2++){
                        boolean copy = false;
                        if(cards_finish[ + x2].size() == 0) { if(cards_freecell[x1].number == 0){
                            if(!done[x2]){ copy = true; } }
                        } else { if((cards_freecell[x1].number - 1 == cards_finish[x2].get((cards_finish[ + x2].size()) - 1).number) && cards_finish[x2].get((cards_finish[ + x2].size()) - 1).suit == cards_freecell[x1].suit) {
                            if(!done[x2]){ copy = true; } }
                        }
                        if(copy){
                            cards_freecell[x1].setShift(0, 16, 0);
                            cards_finish[x2].add(cards_freecell[x1]);
                            cards_freecell[x1] = new Card(-1, -1);
                            selector.set(-1,  -1);
                            timer = 16;
                            done[x2] = true;
                        }
                    }
                }
            }
            for(int x1 = 0; x1 < 8; x1++){
                for(int x2 = 0; x2 < 4; x2++){
                    if(cards_field[x1].size() > 0) {
                        boolean copy = false;
                        if(cards_finish[ + x2].size() == 0) {
                            if(cards_field[x1].get(cards_field[x1].size() - 1).number == 0){
                                if(!done[x2]){ copy = true;
                                }
                            }
                        } else {
                            if((cards_field[x1].get(cards_field[x1].size() - 1).number - 1 == cards_finish[x2].get(cards_finish[x2].size() - 1).number) && cards_finish[x2].get(cards_finish[x2].size() - 1).suit == cards_field[x1].get(cards_field[x1].size() - 1).suit) {
                                if(!done[x2]){
                                    copy = true;
                                }
                            }
                        }
                        if(copy){
                            cards_field[x1].get(cards_field[x1].size() - 1).setShift(0, 16, 0);
                            cards_finish[x2].add(cards_field[x1].get(cards_field[x1].size() - 1));
                            cards_field[x1].remove(cards_finish[x1].size() - 1);
                            selector.set(-1,  -1);
                            timer = 16;
                            done[x2] = true;
                        }
                    }
                }
            }
            Compress();
        } else if(timer > 0){
            timer--;
        }
        if(turnstate == 2){
            if(cards_finish[0].size() == 13 && cards_finish[1].size() == 13 && cards_finish[2].size() == 13 && cards_finish[3].size() == 13 && turnstate < 4) {
                scorePoint = 100;
                turnstate = 4;
            }
        }
    }

    public void updateMotion(){
        for(int x = 0; x < 8; x++){
            if(cards_field[x].size() > 0) for(Card c : cards_field[x]){
                c.update();
            }
        }
        for(int x = 0; x < 4; x++){
            if(cards_finish[ + x].size() > 0) for(Card c : cards_finish[x]){
                c.update();
            }
            cards_freecell[x].update();
        }
    }

    public void load2(CompoundNBT compound){
        cards_field[0].addAll(Arrays.asList(loadCard(compound, 0)));
        cards_field[1].addAll(Arrays.asList(loadCard(compound, 1)));
        cards_field[2].addAll(Arrays.asList(loadCard(compound, 2)));
        cards_field[3].addAll(Arrays.asList(loadCard(compound, 3)));
        cards_field[4].addAll(Arrays.asList(loadCard(compound, 4)));
        cards_field[5].addAll(Arrays.asList(loadCard(compound, 5)));
        cards_field[6].addAll(Arrays.asList(loadCard(compound, 6)));
        cards_field[7].addAll(Arrays.asList(loadCard(compound, 7)));

        cards_finish[0].addAll(Arrays.asList(loadCard(compound, 8)));
        cards_finish[1].addAll(Arrays.asList(loadCard(compound, 9)));
        cards_finish[2].addAll(Arrays.asList(loadCard(compound, 10)));
        cards_finish[3].addAll(Arrays.asList(loadCard(compound, 11)));
        cards_freecell = loadCard(compound, 12);
        compress = compound.getInt("compress");
        timer = compound.getInt("timer");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveCards(compound, 0, (Card[]) cards_field[0].toArray());
        saveCards(compound, 1, (Card[]) cards_field[1].toArray());
        saveCards(compound, 2, (Card[]) cards_field[2].toArray());
        saveCards(compound, 3, (Card[]) cards_field[3].toArray());
        saveCards(compound, 4, (Card[]) cards_field[4].toArray());
        saveCards(compound, 5, (Card[]) cards_field[5].toArray());
        saveCards(compound, 6, (Card[]) cards_field[6].toArray());
        saveCards(compound, 7, (Card[]) cards_field[7].toArray());

        saveCards(compound, 8, (Card[]) cards_finish[0].toArray());
        saveCards(compound, 9, (Card[]) cards_finish[1].toArray());
        saveCards(compound, 10, (Card[]) cards_finish[2].toArray());
        saveCards(compound, 11, (Card[]) cards_finish[3].toArray());

        saveCards(compound, 0, cards_freecell);
        compound.putInt("compress", compress);
        compound.putInt("timer", timer);
        return compound;
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
            if(!cards_freecell[cell].equals(-1, -1)) {
                selector.set(cell, -2);
            }
        } else if(selector.Y >= 0) {
            if(cards_freecell[cell].suit == -1) {
                if(selector.Y == cards_finish[selector.X].size() - 1) {
                    cards_field[selector.X].get(cards_field[selector.X].size() - 1).setShift(0, 16, 0);
                    cards_freecell[cell].set(cards_field[selector.X].get(cards_field[selector.X].size() - 1));
                    cards_field[selector.X].remove(cards_finish[selector.X].size() - 1);
                    selector.set(-1, -1);
                }
            }
        }
    }

    private void TouchFinish(int slot) {
        if(!selector.matches(-1, -1)) {
            if(selector.Y == -2) { // Cell-to-Finish
                boolean copy = false;
                if(cards_finish[slot].size() == 0) {
                    if(cards_freecell[selector.X].number == 0){
                        copy = true;
                    }
                } else {
                    if((cards_freecell[selector.X].number - 1 == cards_finish[slot].get(cards_finish[slot].size() - 1).number) && cards_finish[slot].get(cards_finish[slot].size() - 1).suit == cards_freecell[selector.X].suit) {
                        copy = true;
                    }
                }
                if(copy){
                    cards_freecell[selector.X].setShift(0, 16, 0);
                    cards_finish[slot].add(cards_freecell[selector.X]);
                    cards_freecell[selector.X] = new Card(-1, -1);
                    selector.set(-1,  -1);
                }
            } else { // Field-to-Finish
                if(selector.Y == cards_field[selector.X].size() - 1) {
                    boolean copy = false;
                    if(cards_finish[slot].size() == 0) {
                        if(cards_field[selector.X].get(cards_field[selector.X].size() - 1).number == 0){
                            copy = true;
                        }
                    } else {
                        if((cards_field[selector.X].get(selector.Y).number - 1 == cards_finish[slot].get(cards_finish[slot].size() - 1).number) && cards_finish[slot].get(cards_finish[slot].size() - 1).suit == cards_field[selector.X].get(selector.Y).suit) {
                            copy = true;
                        }
                    }
                    if(copy){
                        cards_field[selector.X].get(cards_field[selector.X].size() - 1).setShift(0, 16, 0);
                        cards_finish[slot].add(cards_field[selector.X].get(cards_field[selector.X].size() - 1));
                        cards_field[selector.X].remove(cards_field[selector.X].size() - 1);
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
        if(cards_field[x2].size() >= y2 - 1) {
            if(selector.matches(-1,  -1)) {
                y2 = cards_field[x2].size() <= y2 ? cards_field[x2].size() - 1 : y2;
                float tempCard = cards_field[x2].get(y2).number;
                float tempSuit = cards_field[x2].get(y2).suit;
                for(int i = y2; i < cards_field[x2].size(); i++) {
                    if(i != cards_field[x2].size() - 1) {
                        if(((cards_field[x2].get(i).number - 1 != cards_field[x2].get(i+1).number) && !(cards_field[x2].get(i).number == 1 && cards_field[x2].get(i+1).number == 13)) || !DifferentColors(cards_field[x2].get(i).suit, cards_field[x2].get(i+1).suit)) {
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
                TransferCards(cards_field[x2], cards_field[selector.X], selector.Y, cards_field[selector.X].size() - selector.Y, 0, 16);
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
