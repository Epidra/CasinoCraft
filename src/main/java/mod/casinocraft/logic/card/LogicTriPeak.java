package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import mod.shared.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class LogicTriPeak extends LogicBase {

    private int combo = 0;

    public Card[] cards_field = new Card[28]; // fixed: 28 Cards
    public List<Card> cards_stack   = new ArrayList<Card>();
    public List<Card>   cards_reserve   = new ArrayList<Card>();



    //--------------------CONSTRUCTOR--------------------

    public LogicTriPeak(int table){
        super(true, table, "tripeak");
    }



    //--------------------BASIC--------------------

    public void start2(){
        scoreLives = 5;

        combo = 0;

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
            cards_field[i] = deck.get(i);
        }
        selector = new Vector2(9, 12);
        for(int i = 0; i < 28; i++){
            deck.remove(0);
        }
        cards_reserve.addAll(deck);
        cards_stack.clear();
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
            cards_field[i] = deck.get(i);
        }
        selector = new Vector2(9, 12);
        for(int i = 0; i < 28; i++){
            deck.remove(0);
        }
        cards_reserve.addAll(deck);
        cards_stack.clear();
        DrawReserve();
    }

    public void actionTouch(int action){
        //	 if(action == -1) CompareCards(28);
        if(action == -2) DrawReserve();
            //else if(action == -3) CompareCards(29);
        else TouchField(action % 20, action / 20);
    }

    public void updateMotion(){
        if(turnstate >= 2){
            if(cards_stack.size() > 0) for(int i = 0; i < cards_stack.size(); i++){
                cards_stack.get(i).update();
            }
            if(cards_reserve.size() > 0) for(int i = 0; i < cards_reserve.size(); i++){
                cards_reserve.get(i).update();
            }
            for(int i = 0; i < 28; i++) {
                cards_field[i].update();
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

    private void DrawReserve() {
        combo = 0;
        if(cards_reserve.size() > 0) {
            cards_reserve.get(0).shiftX = 64;
            cards_stack.add(cards_reserve.get(0));
            cards_reserve.remove(0);
        } else {
            if(scoreLives == 0){
                turnstate = 4;
            } else {
                scoreLives = -1;
                Restart();
            }
        }
    }

    private void TouchField(int x, int y) {
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
            case  0: if(cards_field[ 0].suit != -1 && cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1) return true; break;
            case  1: if(cards_field[ 1].suit != -1 && cards_field[ 5].suit == -1 && cards_field[ 6].suit == -1) return true; break;
            case  2: if(cards_field[ 2].suit != -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1) return true; break;

            case  3: if(cards_field[ 3].suit != -1 && cards_field[ 9].suit == -1 && cards_field[10].suit == -1) return true; break;
            case  4: if(cards_field[ 4].suit != -1 && cards_field[10].suit == -1 && cards_field[11].suit == -1) return true; break;
            case  5: if(cards_field[ 5].suit != -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1) return true; break;
            case  6: if(cards_field[ 6].suit != -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) return true; break;
            case  7: if(cards_field[ 7].suit != -1 && cards_field[15].suit == -1 && cards_field[16].suit == -1) return true; break;
            case  8: if(cards_field[ 8].suit != -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1) return true; break;

            case  9: if(cards_field[ 9].suit != -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1) return true; break;
            case 10: if(cards_field[10].suit != -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) return true; break;
            case 11: if(cards_field[11].suit != -1 && cards_field[20].suit == -1 && cards_field[21].suit == -1) return true; break;
            case 12: if(cards_field[12].suit != -1 && cards_field[21].suit == -1 && cards_field[22].suit == -1) return true; break;
            case 13: if(cards_field[13].suit != -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1) return true; break;
            case 14: if(cards_field[14].suit != -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1) return true; break;
            case 15: if(cards_field[15].suit != -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1) return true; break;
            case 16: if(cards_field[16].suit != -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1) return true; break;
            case 17: if(cards_field[17].suit != -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) return true; break;

            case 18: if(cards_field[18].suit != -1) return true; break;
            case 19: if(cards_field[19].suit != -1) return true; break;
            case 20: if(cards_field[20].suit != -1) return true; break;
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

    private void CompareCards(int id) {

        if(cards_field[id].number + 1 == cards_stack.get(cards_stack.size() - 1).number || cards_field[id].number - 1 == cards_stack.get(cards_stack.size() - 1).number || (cards_field[id].number == 0 && cards_stack.get(cards_stack.size() - 1).number == 12) || (cards_field[id].number == 12 && cards_stack.get(cards_stack.size() - 1).number == 0)) {
            combo++;
            scorePoint = 50 * combo;
            cards_field[id].shiftX = 0;
            cards_field[id].shiftY = +24;
            cards_stack.add(cards_field[id]);
            cards_field[id] = new Card(-1, -1);
            if(cards_field[0].suit == -1 && cards_field[1].suit == -1 && cards_field[2].suit == -1) Restart();
        }
    }

}
