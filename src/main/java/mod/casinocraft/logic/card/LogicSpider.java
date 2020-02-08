package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import mod.shared.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class LogicSpider extends LogicBase {

    public List<Card>[] cards_field   = new ArrayList[10];
    public List<Card>[] cards_reserve = new ArrayList[5];
    public List<Card>   cards_finish  = new ArrayList<Card>();

    public int compress = 4;



    //--------------------CONSTRUCTOR--------------------

    public LogicSpider(int table){
        super(false, table, "c_spider");
    }



    //--------------------BASIC--------------------

    public void start2(){
        List<Card> deck = ShuffleDeck();

        cards_field[0] = new ArrayList<Card>(); TransferCards(cards_field[0], deck, 0, 6);
        cards_field[1] = new ArrayList<Card>(); TransferCards(cards_field[1], deck, 0, 6);
        cards_field[2] = new ArrayList<Card>(); TransferCards(cards_field[2], deck, 0, 6);
        cards_field[3] = new ArrayList<Card>(); TransferCards(cards_field[3], deck, 0, 6);
        cards_field[4] = new ArrayList<Card>(); TransferCards(cards_field[4], deck, 0, 5);
        cards_field[5] = new ArrayList<Card>(); TransferCards(cards_field[5], deck, 0, 5);
        cards_field[6] = new ArrayList<Card>(); TransferCards(cards_field[6], deck, 0, 5);
        cards_field[7] = new ArrayList<Card>(); TransferCards(cards_field[7], deck, 0, 5);
        cards_field[8] = new ArrayList<Card>(); TransferCards(cards_field[8], deck, 0, 5);
        cards_field[9] = new ArrayList<Card>(); TransferCards(cards_field[9], deck, 0, 5);

        cards_reserve[0] = new ArrayList<Card>(); TransferCards(cards_reserve[0], deck, 0, 10);
        cards_reserve[1] = new ArrayList<Card>(); TransferCards(cards_reserve[1], deck, 0, 10);
        cards_reserve[2] = new ArrayList<Card>(); TransferCards(cards_reserve[2], deck, 0, 10);
        cards_reserve[3] = new ArrayList<Card>(); TransferCards(cards_reserve[3], deck, 0, 10);
        cards_reserve[4] = new ArrayList<Card>(); TransferCards(cards_reserve[4], deck, 0, 10);

        selector = new Vector2(-1, -1);

        compress = 2;

        for(int x = 0; x < 10; x++){
            int y = 0;
            for(Card c : cards_field[x]){
                c.setShift(0, -24*y, 70-10*y + x*3);
                y++;
            }
        }
    }

    public void actionTouch(int action){
        if(action == -1) DrawReserve();
        if(action >=  0) TouchField(action%10, action/10);
    }

    public void updateMotion(){

    }

    public void updateLogic(){
        for(int x = 0; x < 10; x++){
            if(cards_field[x].size() > 0) for(Card c : cards_field[x]){
                c.update();
            }
        }
        if(cards_finish.size() == 8 && turnstate < 4) {
            scorePoint = 100;
            turnstate = 4;
        }
    }



    //--------------------CUSTOM--------------------

    private void TransferCards(List<Card> cards_field2, List<Card> deck, int position, int count){
        for(int i = position; i < position + count; i++){
            cards_field2.add(deck.get(position));
            deck.remove(position);
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

    private void DrawReserve() {
        if(cards_reserve[0].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[0].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[0].get(x));
            }
            cards_reserve[0].clear();
        } else if(cards_reserve[1].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[1].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[1].get(x));
            }
            cards_reserve[1].clear();
        } else if(cards_reserve[2].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[2].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[2].get(x));
            }
            cards_reserve[2].clear();
        } else if(cards_reserve[3].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[3].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[3].get(x));
            }
            cards_reserve[3].clear();
        } else if(cards_reserve[4].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[4].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[4].get(x));
            }
            cards_reserve[4].clear();
        }
    }

    private void TouchField(int x, int y) {
        if(cards_field[x].size() >= y - 1) {
            if(selector.matches(-1, -1)) {
                int x2 = x;
                int y2 = cards_field[x].size() < y ? cards_field[x].size()-1 : y - 1;
                float tempCard = cards_field[x2].get(y2).number;
                for(int i = y2; i < cards_field[x2].size(); i++) {
                    if(i != cards_field[x2].size() - 1) {
                        if((cards_field[x2].get(i).number - 1 != cards_field[x2].get(i + 1).number) && !(cards_field[x2].get(i).number == 1 && cards_field[x2].get(i + 1).number == 13)) {
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
        if(cards_field[x2].size() == 0) {
            TransferCards(cards_field[x2], cards_field[selector.X], selector.Y, cards_field[selector.X].size() - selector.Y, 0, 16);
            selector.set(-1, -1);
            ClearRow(x2);
            return true;
        } else {
            if(cards_field[selector.X].get(selector.Y).number + 1 == cards_field[x2].get(y2).number) {
                TransferCards(cards_field[x2], cards_field[selector.X], selector.Y, cards_field[selector.X].size() - selector.Y, 0, 16);
                selector.set(-1, -1);
                ClearRow(x2);
                return true;
            }
        }
        return false;
    }

    private void ClearRow(int row) {
        if(cards_field[row].size() >= 13) {
            for(int i = 0; i < cards_field[row].size(); i++) {
                if(cards_field[row].get(i).number == 12) {
                    for(int j = 0; j < cards_field[row].size()-1; j++) {
                        if(12 - j == 0 && cards_field[row].get(i + j).number == 13 && cards_field[row].get(i).suit == cards_field[row].get(i + j).suit) {
                            cards_finish.add(cards_field[row].get(cards_field[row].size() - 1));
                            for(int z = cards_field[row].size() - 13; z < cards_field[row].size() - 13; z++) cards_field[row].remove(z);
                            return;
                        }
                        if(12 - j != cards_field[row].get(i + j).number || cards_field[row].get(i).suit != cards_field[row].get(i + j).suit) break;

                    }
                }
            }
        }
    }

    private void Compress() {
        int i = 0;
        for(int x = 0; x < 8; x++) {
            if(cards_field[x].size() > i) i = cards_field[x].size();
        }
        if(i > 8) {
            compress = i-5;
        } else {
            compress = 0;
        }
    }

}
