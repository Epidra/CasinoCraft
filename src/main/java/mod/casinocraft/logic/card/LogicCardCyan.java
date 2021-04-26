package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CARD_PLACE;
import static mod.casinocraft.util.SoundMap.SOUND_CARD_SHOVE;

public class LogicCardCyan extends LogicModule {   // Spider

    public List<Card>[] cards_field   = new ArrayList[10];
    public List<Card>[] cards_reserve = new ArrayList[5];
    public List<Card>   cards_finish  = new ArrayList<Card>();
    public int compress = 4;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardCyan(int table){
        super(table);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        List<Card> deck = shuffleDeck();

        cards_field[0] = new ArrayList<Card>(); transferCards(cards_field[0], deck, 0, 6);
        cards_field[1] = new ArrayList<Card>(); transferCards(cards_field[1], deck, 0, 6);
        cards_field[2] = new ArrayList<Card>(); transferCards(cards_field[2], deck, 0, 6);
        cards_field[3] = new ArrayList<Card>(); transferCards(cards_field[3], deck, 0, 6);
        cards_field[4] = new ArrayList<Card>(); transferCards(cards_field[4], deck, 0, 5);
        cards_field[5] = new ArrayList<Card>(); transferCards(cards_field[5], deck, 0, 5);
        cards_field[6] = new ArrayList<Card>(); transferCards(cards_field[6], deck, 0, 5);
        cards_field[7] = new ArrayList<Card>(); transferCards(cards_field[7], deck, 0, 5);
        cards_field[8] = new ArrayList<Card>(); transferCards(cards_field[8], deck, 0, 5);
        cards_field[9] = new ArrayList<Card>(); transferCards(cards_field[9], deck, 0, 5);

        cards_reserve[0] = new ArrayList<Card>(); transferCards(cards_reserve[0], deck, 0, 10);
        cards_reserve[1] = new ArrayList<Card>(); transferCards(cards_reserve[1], deck, 0, 10);
        cards_reserve[2] = new ArrayList<Card>(); transferCards(cards_reserve[2], deck, 0, 10);
        cards_reserve[3] = new ArrayList<Card>(); transferCards(cards_reserve[3], deck, 0, 10);
        cards_reserve[4] = new ArrayList<Card>(); transferCards(cards_reserve[4], deck, 0, 10);

        selector = new Vector2(-1, -1);

        compress = 2;

        for(int x = 0; x < 10; x++){
            int y = 0;
            for(Card c : cards_field[x]){
                c.setShift(0, -24*y, 70-10*y + x*3);
                y++;
            }
        }
        setJingle(SOUND_CARD_SHOVE);
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == -1) drawReserve();
        if(action >=  0) touchField(action%10, action/10);
    }




    //----------------------------------------UPDATE----------------------------------------//

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

    public void updateMotion(){

    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        cards_field[0] = loadCardList(compound, 0);
        cards_field[1] = loadCardList(compound, 1);
        cards_field[2] = loadCardList(compound, 2);
        cards_field[3] = loadCardList(compound, 3);
        cards_field[4] = loadCardList(compound, 4);
        cards_field[5] = loadCardList(compound, 5);
        cards_field[6] = loadCardList(compound, 6);
        cards_field[7] = loadCardList(compound, 7);
        cards_field[8] = loadCardList(compound, 8);
        cards_field[9] = loadCardList(compound, 9);

        cards_reserve[0] = loadCardList(compound, 10);
        cards_reserve[1] = loadCardList(compound, 11);
        cards_reserve[2] = loadCardList(compound, 12);
        cards_reserve[3] = loadCardList(compound, 13);
        cards_reserve[4] = loadCardList(compound, 14);

        cards_finish.addAll(loadCardList(compound, 15));
        compress = compound.getInt("compress");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveCardList(compound, 0, cards_field[0]);
        saveCardList(compound, 1, cards_field[1]);
        saveCardList(compound, 2, cards_field[2]);
        saveCardList(compound, 3, cards_field[3]);
        saveCardList(compound, 4, cards_field[4]);
        saveCardList(compound, 5, cards_field[5]);
        saveCardList(compound, 6, cards_field[6]);
        saveCardList(compound, 7, cards_field[7]);
        saveCardList(compound, 8, cards_field[8]);
        saveCardList(compound, 9, cards_field[9]);

        saveCardList(compound, 10, cards_reserve[0]);
        saveCardList(compound, 11, cards_reserve[1]);
        saveCardList(compound, 12, cards_reserve[2]);
        saveCardList(compound, 13, cards_reserve[3]);
        saveCardList(compound, 14, cards_reserve[4]);

        saveCardList(compound, 15, cards_finish);
        compound.putInt("compress", compress);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void transferCards(List<Card> cards_field2, List<Card> deck, int position, int count){
        for(int i = position; i < position + count; i++){
            cards_field2.add(deck.get(position));
            deck.remove(position);
            setJingle(SOUND_CARD_PLACE);
        }
    }

    private void transferCards(List<Card> cards_field2, List<Card> deck, int position, int count, int shiftX, int shiftY){
        for(int i = position; i < position + count; i++){
            deck.get(position).setShift(shiftX, shiftY, 0);
            cards_field2.add(deck.get(position));
            deck.remove(position);
            setJingle(SOUND_CARD_PLACE);
        }
    }

    private List<Card> shuffleDeck() {
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

    private void drawReserve() {
        if(cards_reserve[0].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[0].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[0].get(x));
                setJingle(SOUND_CARD_SHOVE);
            }
            cards_reserve[0].clear();
        } else if(cards_reserve[1].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[1].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[1].get(x));
                setJingle(SOUND_CARD_SHOVE);
            }
            cards_reserve[1].clear();
        } else if(cards_reserve[2].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[2].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[2].get(x));
                setJingle(SOUND_CARD_SHOVE);
            }
            cards_reserve[2].clear();
        } else if(cards_reserve[3].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[3].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[3].get(x));
                setJingle(SOUND_CARD_SHOVE);
            }
            cards_reserve[3].clear();
        } else if(cards_reserve[4].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[4].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[4].get(x));
                setJingle(SOUND_CARD_SHOVE);
            }
            cards_reserve[4].clear();
        }
    }

    private void touchField(int x, int y) {
        if(cards_field[x].size() >= y - 1) {
            if(selector.matches(-1, -1)) {
                int x2 = x;
                int y2 = cards_field[x].size() <= y ? cards_field[x].size()-1 : y;
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
                if(!moveStack(x, y)) {
                    selector.set(-1, -1);
                }
            }
        }
        compress();
    }

    private boolean moveStack(int x, int y) {
        int x2 = x;
        int y2 = cards_field[x2].size() - 1;
        if(cards_field[x2].size() == 0) {
            transferCards(cards_field[x2], cards_field[selector.X], selector.Y, cards_field[selector.X].size() - selector.Y, 0, 16);
            selector.set(-1, -1);
            clearRow(x2);
            setJingle(SOUND_CARD_PLACE);
            return true;
        } else {
            if(cards_field[selector.X].get(selector.Y).number + 1 == cards_field[x2].get(y2).number) {
                transferCards(cards_field[x2], cards_field[selector.X], selector.Y, cards_field[selector.X].size() - selector.Y, 0, 16);
                selector.set(-1, -1);
                clearRow(x2);
                setJingle(SOUND_CARD_PLACE);
                return true;
            }
        }
        return false;
    }

    private void clearRow(int row) {
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

    private void compress() {
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




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 3;
    }

}
