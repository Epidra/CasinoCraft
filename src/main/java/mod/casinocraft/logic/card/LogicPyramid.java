package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;

import java.util.ArrayList;
import java.util.List;

public class LogicPyramid extends LogicBase {

    //public int selector_card = -1;
    //public Vector2 selector_pos;

    public Card[] cards_field = new Card[28];
    public List<Card> cards_stack = new ArrayList<Card>();
    public List<Card> cards_reserve = new ArrayList<Card>();

    public int timer = 0;



    //--------------------CONSTRUCTOR--------------------

    public LogicPyramid(int table){
        super(true, table, "pyramid");
    }



    //--------------------BASIC--------------------

    public void start2(){
        scoreLives = 5;
        timer = 0;
        selector.set(-1, -1);

        List<Card> deck = ShuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        }
        for(int i = 0; i < 28; i++) deck.remove(0);
        cards_reserve.addAll(deck);
        cards_stack.clear();

        cards_field[ 1].setShift(+16*1, -20*1, 50);
        cards_field[ 2].setShift(-16*1, -20*1, 50);

        cards_field[ 3].setShift(+16*2, -20*2, 40);
        cards_field[ 4].setShift(    0, -20*2, 40);
        cards_field[ 5].setShift(-16*2, -20*2, 40);

        cards_field[ 6].setShift(+16*3, -20*3, 30);
        cards_field[ 7].setShift(+16*1, -20*3, 30);
        cards_field[ 8].setShift(-16*1, -20*3, 30);
        cards_field[ 9].setShift(-16*3, -20*3, 30);

        cards_field[10].setShift(+16*4, -20*4, 20);
        cards_field[11].setShift(+16*2, -20*4, 20);
        cards_field[12].setShift(    0, -20*4, 20);
        cards_field[13].setShift(-16*2, -20*4, 20);
        cards_field[14].setShift(-16*4, -20*4, 20);

        cards_field[15].setShift(+16*5, -20*5, 10);
        cards_field[16].setShift(+16*3, -20*5, 10);
        cards_field[17].setShift(+16*1, -20*5, 10);
        cards_field[18].setShift(-16*1, -20*5, 10);
        cards_field[19].setShift(-16*3, -20*5, 10);
        cards_field[20].setShift(-16*5, -20*5, 10);

        cards_field[21].setShift(+16*6, -20*6, 0);
        cards_field[22].setShift(+16*4, -20*6, 0);
        cards_field[23].setShift(+16*2, -20*6, 0);
        cards_field[24].setShift(    0, -20*6, 0);
        cards_field[25].setShift(-16*2, -20*6, 0);
        cards_field[26].setShift(-16*4, -20*6, 0);
        cards_field[27].setShift(-16*6, -20*6, 0);
    }

    public void Restart() {
        selector.set(-1, -1);
        List<Card> deck = ShuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        }
        for(int i = 0; i < 28; i++) deck.remove(0);
        cards_reserve.addAll(deck);
        cards_stack.clear();

        cards_field[ 1].setShift(+16*1, -20*1, 50);
        cards_field[ 2].setShift(-16*1, -20*1, 50);

        cards_field[ 3].setShift(+16*2, -20*2, 40);
        cards_field[ 4].setShift(    0, -20*2, 40);
        cards_field[ 5].setShift(-16*2, -20*2, 40);

        cards_field[ 6].setShift(+16*3, -20*3, 30);
        cards_field[ 7].setShift(+16*1, -20*3, 30);
        cards_field[ 8].setShift(-16*1, -20*3, 30);
        cards_field[ 9].setShift(-16*3, -20*3, 30);

        cards_field[10].setShift(+16*4, -20*4, 20);
        cards_field[11].setShift(+16*2, -20*4, 20);
        cards_field[12].setShift(    0, -20*4, 20);
        cards_field[13].setShift(-16*2, -20*4, 20);
        cards_field[14].setShift(-16*4, -20*4, 20);

        cards_field[15].setShift(+16*5, -20*5, 10);
        cards_field[16].setShift(+16*3, -20*5, 10);
        cards_field[17].setShift(+16*1, -20*5, 10);
        cards_field[18].setShift(-16*1, -20*5, 10);
        cards_field[19].setShift(-16*3, -20*5, 10);
        cards_field[20].setShift(-16*5, -20*5, 10);

        cards_field[21].setShift(+16*6, -20*6, 0);
        cards_field[22].setShift(+16*4, -20*6, 0);
        cards_field[23].setShift(+16*2, -20*6, 0);
        cards_field[24].setShift(    0, -20*6, 0);
        cards_field[25].setShift(-16*2, -20*6, 0);
        cards_field[26].setShift(-16*4, -20*6, 0);
        cards_field[27].setShift(-16*6, -20*6, 0);
    }

    public void actionTouch(int action){
        if(action == -1) CompareCards(28);
        else if(action == -2) DrawReserve();
        else if(action == -3) CompareCards(29);
        else TouchField(action % 20, action / 20);
    }

    public void updateMotion(){

    }

    public void updateLogic(){
        if(turnstate >= 2){
            if(cards_stack.size() > 0) for(int i = 0; i < cards_stack.size(); i++){
                cards_stack.get(i).update();
            }
            if(cards_reserve.size() > 0) for(int i = 0; i < cards_reserve.size(); i++){
                cards_reserve.get(i).update();
            }
            for(int i = 0; i < 28; i++){
                cards_field[i].update();
            }
            if(timer > 0){
                timer--;
                if(timer == 0){
                    if(cards_stack  .size() > 0) if(cards_stack  .get(cards_stack.size() - 1).dead) cards_stack  .remove(cards_stack.get(cards_stack.size() - 1));
                    if(cards_reserve.size() > 0) if(cards_reserve.get(0                     ).dead) cards_reserve.remove(0);
                    for(int i = 0; i < 28; i++){
                        if(cards_field[i].dead){
                            cards_field[i] = new Card(-1, -1);
                            CheckForClearedLine(i);
                        }
                    }
                }
            }
        }
    }



    //--------------------CUSTOM--------------------

    private List<Card> ShuffleDeck() {
        List<Card> stack = new ArrayList<Card>();
        List<Card> deck  = new ArrayList<Card>();

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
        if(cards_reserve.size() > 0) {
            cards_reserve.get(0).shiftX = 64;
            cards_stack.add(cards_reserve.get(0));
            cards_reserve.remove(0);
        } else {
            cards_reserve.addAll(cards_stack);
            cards_stack.clear();
            if(scoreLives == 0){
                turnstate = 4;
            } else {
                scoreLives--;
            }
        }
    }

    public void TouchField(int x, int y) {
        if(x ==  7 && y == 1) if(IsCardSelectable( 0)){ CompareCards(0); }
        if(x ==  8 && y == 1) if(IsCardSelectable( 0)){ CompareCards(0); }

        if(x ==  6 && y == 2) if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x ==  7 && y == 2) if(IsCardSelectable( 1)){ CompareCards(1); } else if(IsCardSelectable( 0)){CompareCards(0); }
        if(x ==  8 && y == 2) if(IsCardSelectable( 2)){ CompareCards(2); } else if(IsCardSelectable( 0)){CompareCards(0); }
        if(x ==  9 && y == 2) if(IsCardSelectable( 2)){ CompareCards(2); }

        if(x ==  5 && y == 3) if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x ==  6 && y == 3) if(IsCardSelectable( 3)){ CompareCards(3); } else if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x ==  7 && y == 3) if(IsCardSelectable( 4)){ CompareCards(4); } else if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x ==  8 && y == 3) if(IsCardSelectable( 4)){ CompareCards(4); } else if(IsCardSelectable( 2)){ CompareCards(2); }
        if(x ==  9 && y == 3) if(IsCardSelectable( 5)){ CompareCards(5); } else if(IsCardSelectable( 2)){ CompareCards(2); }
        if(x == 10 && y == 3) if(IsCardSelectable( 5)){ CompareCards(5); }

        if(x ==  4 && y == 4) if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  5 && y == 4) if(IsCardSelectable( 6)){ CompareCards(6); } else if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x ==  6 && y == 4) if(IsCardSelectable( 7)){ CompareCards(7); } else if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x ==  7 && y == 4) if(IsCardSelectable( 7)){ CompareCards(7); } else if(IsCardSelectable( 4)){ CompareCards(4); }
        if(x ==  8 && y == 4) if(IsCardSelectable( 8)){ CompareCards(8); } else if(IsCardSelectable( 4)){ CompareCards(4); }
        if(x ==  9 && y == 4) if(IsCardSelectable( 8)){ CompareCards(8); } else if(IsCardSelectable( 5)){ CompareCards(5); }
        if(x == 10 && y == 4) if(IsCardSelectable( 9)){ CompareCards(9); } else if(IsCardSelectable( 5)){ CompareCards(5); }
        if(x == 11 && y == 4) if(IsCardSelectable( 9)){ CompareCards(9); }

        if(x ==  3 && y == 5) if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  4 && y == 5) if(IsCardSelectable(10)){ CompareCards(10); } else if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  5 && y == 5) if(IsCardSelectable(11)){ CompareCards(11); } else if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  6 && y == 5) if(IsCardSelectable(11)){ CompareCards(11); } else if(IsCardSelectable( 7)){ CompareCards(7); }
        if(x ==  7 && y == 5) if(IsCardSelectable(12)){ CompareCards(12); } else if(IsCardSelectable( 7)){ CompareCards(7); }
        if(x ==  8 && y == 5) if(IsCardSelectable(12)){ CompareCards(12); } else if(IsCardSelectable( 8)){ CompareCards(8); }
        if(x ==  9 && y == 5) if(IsCardSelectable(13)){ CompareCards(13); } else if(IsCardSelectable( 8)){ CompareCards(8); }
        if(x == 10 && y == 5) if(IsCardSelectable(13)){ CompareCards(13); } else if(IsCardSelectable( 9)){ CompareCards(9); }
        if(x == 11 && y == 5) if(IsCardSelectable(14)){ CompareCards(14); } else if(IsCardSelectable( 9)){ CompareCards(9); }
        if(x == 12 && y == 5) if(IsCardSelectable(14)){ CompareCards(14); }

        if(x ==  2 && y == 6) if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  3 && y == 6) if(IsCardSelectable(15)){ CompareCards(15); } else if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  4 && y == 6) if(IsCardSelectable(16)){ CompareCards(16); } else if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  5 && y == 6) if(IsCardSelectable(16)){ CompareCards(16); } else if(IsCardSelectable(11)){ CompareCards(11); }
        if(x ==  6 && y == 6) if(IsCardSelectable(17)){ CompareCards(17); } else if(IsCardSelectable(11)){ CompareCards(11); }
        if(x ==  7 && y == 6) if(IsCardSelectable(17)){ CompareCards(17); } else if(IsCardSelectable(12)){ CompareCards(12); }
        if(x ==  8 && y == 6) if(IsCardSelectable(18)){ CompareCards(18); } else if(IsCardSelectable(12)){ CompareCards(12); }
        if(x ==  9 && y == 6) if(IsCardSelectable(18)){ CompareCards(18); } else if(IsCardSelectable(13)){ CompareCards(13); }
        if(x == 10 && y == 6) if(IsCardSelectable(19)){ CompareCards(19); } else if(IsCardSelectable(13)){ CompareCards(13); }
        if(x == 11 && y == 6) if(IsCardSelectable(19)){ CompareCards(19); } else if(IsCardSelectable(14)){ CompareCards(14); }
        if(x == 12 && y == 6) if(IsCardSelectable(20)){ CompareCards(20); } else if(IsCardSelectable(14)){ CompareCards(14); }
        if(x == 13 && y == 6) if(IsCardSelectable(20)){ CompareCards(20); }

        if(x ==  1 && y == 7) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  2 && y == 7) if(IsCardSelectable(21)){ CompareCards(21); } else if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  3 && y == 7) if(IsCardSelectable(22)){ CompareCards(22); } else if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  4 && y == 7) if(IsCardSelectable(22)){ CompareCards(22); } else if(IsCardSelectable(16)){ CompareCards(16); }
        if(x ==  5 && y == 7) if(IsCardSelectable(23)){ CompareCards(23); } else if(IsCardSelectable(16)){ CompareCards(16); }
        if(x ==  6 && y == 7) if(IsCardSelectable(23)){ CompareCards(23); } else if(IsCardSelectable(17)){ CompareCards(17); }
        if(x ==  7 && y == 7) if(IsCardSelectable(24)){ CompareCards(24); } else if(IsCardSelectable(17)){ CompareCards(17); }
        if(x ==  8 && y == 7) if(IsCardSelectable(24)){ CompareCards(24); } else if(IsCardSelectable(18)){ CompareCards(18); }
        if(x ==  9 && y == 7) if(IsCardSelectable(25)){ CompareCards(25); } else if(IsCardSelectable(18)){ CompareCards(18); }
        if(x == 10 && y == 7) if(IsCardSelectable(25)){ CompareCards(25); } else if(IsCardSelectable(19)){ CompareCards(19); }
        if(x == 11 && y == 7) if(IsCardSelectable(26)){ CompareCards(26); } else if(IsCardSelectable(19)){ CompareCards(19); }
        if(x == 12 && y == 7) if(IsCardSelectable(26)){ CompareCards(26); } else if(IsCardSelectable(20)){ CompareCards(20); }
        if(x == 13 && y == 7) if(IsCardSelectable(27)){ CompareCards(27); } else if(IsCardSelectable(20)){ CompareCards(20); }
        if(x == 14 && y == 7) if(IsCardSelectable(27)){ CompareCards(27); }

        if(x ==  1 && y == 8) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  2 && y == 8) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  3 && y == 8) if(IsCardSelectable(22)){ CompareCards(22); }
        if(x ==  4 && y == 8) if(IsCardSelectable(22)){ CompareCards(22); }
        if(x ==  5 && y == 8) if(IsCardSelectable(23)){ CompareCards(23); }
        if(x ==  6 && y == 8) if(IsCardSelectable(23)){ CompareCards(23); }
        if(x ==  7 && y == 8) if(IsCardSelectable(24)){ CompareCards(24); }
        if(x ==  8 && y == 8) if(IsCardSelectable(24)){ CompareCards(24); }
        if(x ==  9 && y == 8) if(IsCardSelectable(25)){ CompareCards(25); }
        if(x == 10 && y == 8) if(IsCardSelectable(25)){ CompareCards(25); }
        if(x == 11 && y == 8) if(IsCardSelectable(26)){ CompareCards(26); }
        if(x == 12 && y == 8) if(IsCardSelectable(26)){ CompareCards(26); }
        if(x == 13 && y == 8) if(IsCardSelectable(27)){ CompareCards(27); }
        if(x == 14 && y == 8) if(IsCardSelectable(27)){ CompareCards(27); }
    }

    private boolean IsCardSelectable(int id) {
        switch(id) {
            case  0: if(cards_field[ 0].suit != -1 && cards_field[ 1].suit == -1 && cards_field[ 2].suit == -1) return true; break;

            case  1: if(cards_field[ 1].suit != -1 && cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1) return true; break;
            case  2: if(cards_field[ 2].suit != -1 && cards_field[ 4].suit == -1 && cards_field[ 5].suit == -1) return true; break;

            case  3: if(cards_field[ 3].suit != -1 && cards_field[ 6].suit == -1 && cards_field[ 7].suit == -1) return true; break;
            case  4: if(cards_field[ 4].suit != -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1) return true; break;
            case  5: if(cards_field[ 5].suit != -1 && cards_field[ 8].suit == -1 && cards_field[ 9].suit == -1) return true; break;

            case  6: if(cards_field[ 6].suit != -1 && cards_field[10].suit == -1 && cards_field[11].suit == -1) return true; break;
            case  7: if(cards_field[ 7].suit != -1 && cards_field[11].suit == -1 && cards_field[12].suit == -1) return true; break;
            case  8: if(cards_field[ 8].suit != -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1) return true; break;
            case  9: if(cards_field[ 9].suit != -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) return true; break;

            case 10: if(cards_field[10].suit != -1 && cards_field[15].suit == -1 && cards_field[16].suit == -1) return true; break;
            case 11: if(cards_field[11].suit != -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1) return true; break;
            case 12: if(cards_field[12].suit != -1 && cards_field[17].suit == -1 && cards_field[18].suit == -1) return true; break;
            case 13: if(cards_field[13].suit != -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1) return true; break;
            case 14: if(cards_field[14].suit != -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) return true; break;

            case 15: if(cards_field[15].suit != -1 && cards_field[21].suit == -1 && cards_field[22].suit == -1) return true; break;
            case 16: if(cards_field[16].suit != -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1) return true; break;
            case 17: if(cards_field[17].suit != -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1) return true; break;
            case 18: if(cards_field[18].suit != -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1) return true; break;
            case 19: if(cards_field[19].suit != -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1) return true; break;
            case 20: if(cards_field[20].suit != -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) return true; break;

            case 21: if(cards_field[21].suit != -1) return true; break;
            case 22: if(cards_field[22].suit != -1) return true; break;
            case 23: if(cards_field[23].suit != -1) return true; break;
            case 24: if(cards_field[24].suit != -1) return true; break;
            case 25: if(cards_field[25].suit != -1) return true; break;
            case 26: if(cards_field[26].suit != -1) return true; break;
            case 27: if(cards_field[27].suit != -1) return true; break;
        }
        return false;
    }

    private int findNumber(int id){
        if(id == 28) if(cards_stack  .size() > 0) return cards_stack  .get(cards_stack.size() - 1).number; else return -1;
        if(id == 29) if(cards_reserve.size() > 0) return cards_reserve.get(0                     ).number; else return -1;
        return cards_field[id].number;
    }

    private void setDead(int id){
        if(id == 28){ if(cards_stack  .size() > 0) cards_stack  .get(cards_stack.size() - 1).dead = true;
        } else if(id == 29){ if(cards_reserve.size() > 0) cards_reserve.get(0                     ).dead = true;
        } else cards_field[id].dead = true;
    }

    public void CompareCards(int id) {
        if(selector.X == -1) { // No Card Selected
            if(findNumber(id) == 12){
                setDead(id);
                timer = 48;
                scorePoint += 50;
            } else {
                selector.X = id;
            }
        } else { // A Card already selected
            if(findNumber(id) + findNumber(selector.X) == 11){
                setDead(id);
                setDead(selector.X);
                selector.X = -1;
                timer = 48;
                scorePoint += 50;

            } else if(findNumber(id) == 12){ // Set new Selector
                setDead(id);
                timer = 48;
                scorePoint += 50;
            } else {
                selector.X = id;
            }
        }
    }

    private void CheckForClearedLine(int id) {
        if(id ==  0) Restart();
        if(id >=  1 && id <  2) if(cards_field[ 1].suit == -1 && cards_field[ 2].suit == -1) scorePoint += 1000;
        if(id >=  3 && id <  5) if(cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1 && cards_field[ 5].suit == -1) scorePoint += 500;
        if(id >=  6 && id <  9) if(cards_field[ 6].suit == -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1 && cards_field[ 9].suit == -1) scorePoint += 400;
        if(id >= 10 && id < 14) if(cards_field[10].suit == -1 && cards_field[11].suit == -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) scorePoint += 300;
        if(id >= 15 && id < 20) if(cards_field[15].suit == -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) scorePoint += 200;
        if(id >= 21 && id < 27) if(cards_field[21].suit == -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) scorePoint += 100;
    }

}
