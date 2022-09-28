package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.*;

public class LogicCardMagenta extends LogicModule {   // Pyramid

    public Card[] cards_field       = new Card[28];
    public List<Card> cards_stack   = new ArrayList<Card>();
    public List<Card> cards_reserve = new ArrayList<Card>();





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardMagenta(int table){
        super(table);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        scoreLives = 2;
        selector.set(-1, -1);

        List<Card> deck = shuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        } deck.subList(0, 28).clear();
        cards_reserve.addAll(deck);
        cards_stack.clear();

        cards_field[ 1].setShift(+16,  -20, 50);
        cards_field[ 2].setShift(-16,  -20, 50);

        cards_field[ 3].setShift( 32,  -40, 40);
        cards_field[ 4].setShift(  0,  -40, 40);
        cards_field[ 5].setShift(-32,  -40, 40);

        cards_field[ 6].setShift( 48,  -60, 30);
        cards_field[ 7].setShift( 16,  -60, 30);
        cards_field[ 8].setShift(-16,  -60, 30);
        cards_field[ 9].setShift(-48,  -60, 30);

        cards_field[10].setShift( 64,  -80, 20);
        cards_field[11].setShift( 32,  -80, 20);
        cards_field[12].setShift(  0,  -80, 20);
        cards_field[13].setShift(-32,  -80, 20);
        cards_field[14].setShift(-64,  -80, 20);

        cards_field[15].setShift( 80, -100, 10);
        cards_field[16].setShift( 48, -100, 10);
        cards_field[17].setShift( 16, -100, 10);
        cards_field[18].setShift(-16, -100, 10);
        cards_field[19].setShift(-48, -100, 10);
        cards_field[20].setShift(-80, -100, 10);

        cards_field[21].setShift( 96, -120, 0);
        cards_field[22].setShift( 64, -120, 0);
        cards_field[23].setShift( 32, -120, 0);
        cards_field[24].setShift(  0, -120, 0);
        cards_field[25].setShift(-32, -120, 0);
        cards_field[26].setShift(-64, -120, 0);
        cards_field[27].setShift(-96, -120, 0);

        setJingle(SOUND_CARD_SHOVE);
    }

    private void restart() {
        selector.set(-1, -1);
        List<Card> deck = shuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        } deck.subList(0, 28).clear();
        cards_reserve.addAll(deck);
        cards_stack.clear();

        cards_field[ 1].setShift( 16,  -20, 50);
        cards_field[ 2].setShift(-16,  -20, 50);

        cards_field[ 3].setShift( 32,  -40, 40);
        cards_field[ 4].setShift(  0,  -40, 40);
        cards_field[ 5].setShift(-32,  -40, 40);

        cards_field[ 6].setShift( 48,  -60, 30);
        cards_field[ 7].setShift( 16,  -60, 30);
        cards_field[ 8].setShift(-16,  -60, 30);
        cards_field[ 9].setShift(-48,  -60, 30);

        cards_field[10].setShift( 64,  -80, 20);
        cards_field[11].setShift( 32,  -80, 20);
        cards_field[12].setShift(  0,  -80, 20);
        cards_field[13].setShift(-32,  -80, 20);
        cards_field[14].setShift(-64,  -80, 20);

        cards_field[15].setShift( 80, -100, 10);
        cards_field[16].setShift( 48, -100, 10);
        cards_field[17].setShift( 16, -100, 10);
        cards_field[18].setShift(-16, -100, 10);
        cards_field[19].setShift(-48, -100, 10);
        cards_field[20].setShift(-80, -100, 10);

        cards_field[21].setShift( 96, -120, 0);
        cards_field[22].setShift( 64, -120, 0);
        cards_field[23].setShift( 32, -120, 0);
        cards_field[24].setShift(  0, -120, 0);
        cards_field[25].setShift(-32, -120, 0);
        cards_field[26].setShift(-64, -120, 0);
        cards_field[27].setShift(-96, -120, 0);

        setJingle(SOUND_CARD_SHOVE);
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
             if(action == -1) compareCards(28);
        else if(action == -2) drawReserve();
        else if(action == -3) compareCards(29);
        else touchField(action % 20, action / 20);
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){
        if(turnstate >= 2){
            if(cards_stack.size() > 0) for (Card card : cards_stack) {
                card.update();
            }
            if(cards_reserve.size() > 0) for (Card card : cards_reserve) {
                card.update();
            }
            for(int i = 0; i < 28; i++){
                cards_field[i].update();
                if(cards_field[i].deathtimer >= 48){
                    cards_field[i] = new Card(-1, -1);
                    checkForClearedLine(i);
                }
            }
            if(cards_stack  .size() > 0) if(cards_stack  .get(cards_stack.size() - 1).deathtimer >= 48) cards_stack  .remove(cards_stack.get(cards_stack.size() - 1));
            if(cards_reserve.size() > 0) if(cards_reserve.get(0                     ).deathtimer >= 48) cards_reserve.remove(0);
        }
    }

    public void updateMotion(){

    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        cards_reserve = loadCardList(compound, 0);
        cards_stack   = loadCardList(compound, 1);
        cards_field   = loadCardArray(compound, 2);
    }

    public CompoundTag save2(CompoundTag compound){
        saveCardList(compound,  0, cards_reserve);
        saveCardList(compound,  1, cards_stack);
        saveCardArray(compound, 2, cards_field);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private List<Card> shuffleDeck() {
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

    private void drawReserve() {
        if(cards_reserve.size() > 0) {
            cards_reserve.get(0).shiftX = 36;
            cards_stack.add(cards_reserve.get(0));
            cards_reserve.remove(0);
            setJingle(SOUND_CARD_SHOVE);
        } else {
            cards_reserve.addAll(cards_stack);
            cards_stack.clear();
            if(scoreLives == 0){
                turnstate = 4;
            } else {
                scoreLives--;
            }
            setJingle(SOUND_CARD_SHOVE);
        }
    }

    private void touchField(int x, int y) {
        if(x ==  7 && y == 1) if(isCardSelectable( 0)){ compareCards(0); }
        if(x ==  8 && y == 1) if(isCardSelectable( 0)){ compareCards(0); }

        if(x ==  6 && y == 2) if(isCardSelectable( 1)){ compareCards(1); }
        if(x ==  7 && y == 2) if(isCardSelectable( 1)){ compareCards(1); } else if(isCardSelectable( 0)){
            compareCards(0); }
        if(x ==  8 && y == 2) if(isCardSelectable( 2)){ compareCards(2); } else if(isCardSelectable( 0)){
            compareCards(0); }
        if(x ==  9 && y == 2) if(isCardSelectable( 2)){ compareCards(2); }

        if(x ==  5 && y == 3) if(isCardSelectable( 3)){ compareCards(3); }
        if(x ==  6 && y == 3) if(isCardSelectable( 3)){ compareCards(3); } else if(isCardSelectable( 1)){ compareCards(1); }
        if(x ==  7 && y == 3) if(isCardSelectable( 4)){ compareCards(4); } else if(isCardSelectable( 1)){ compareCards(1); }
        if(x ==  8 && y == 3) if(isCardSelectable( 4)){ compareCards(4); } else if(isCardSelectable( 2)){ compareCards(2); }
        if(x ==  9 && y == 3) if(isCardSelectable( 5)){ compareCards(5); } else if(isCardSelectable( 2)){ compareCards(2); }
        if(x == 10 && y == 3) if(isCardSelectable( 5)){ compareCards(5); }

        if(x ==  4 && y == 4) if(isCardSelectable( 6)){ compareCards(6); }
        if(x ==  5 && y == 4) if(isCardSelectable( 6)){ compareCards(6); } else if(isCardSelectable( 3)){ compareCards(3); }
        if(x ==  6 && y == 4) if(isCardSelectable( 7)){ compareCards(7); } else if(isCardSelectable( 3)){ compareCards(3); }
        if(x ==  7 && y == 4) if(isCardSelectable( 7)){ compareCards(7); } else if(isCardSelectable( 4)){ compareCards(4); }
        if(x ==  8 && y == 4) if(isCardSelectable( 8)){ compareCards(8); } else if(isCardSelectable( 4)){ compareCards(4); }
        if(x ==  9 && y == 4) if(isCardSelectable( 8)){ compareCards(8); } else if(isCardSelectable( 5)){ compareCards(5); }
        if(x == 10 && y == 4) if(isCardSelectable( 9)){ compareCards(9); } else if(isCardSelectable( 5)){ compareCards(5); }
        if(x == 11 && y == 4) if(isCardSelectable( 9)){ compareCards(9); }

        if(x ==  3 && y == 5) if(isCardSelectable(10)){ compareCards(10); }
        if(x ==  4 && y == 5) if(isCardSelectable(10)){ compareCards(10); } else if(isCardSelectable( 6)){ compareCards(6); }
        if(x ==  5 && y == 5) if(isCardSelectable(11)){ compareCards(11); } else if(isCardSelectable( 6)){ compareCards(6); }
        if(x ==  6 && y == 5) if(isCardSelectable(11)){ compareCards(11); } else if(isCardSelectable( 7)){ compareCards(7); }
        if(x ==  7 && y == 5) if(isCardSelectable(12)){ compareCards(12); } else if(isCardSelectable( 7)){ compareCards(7); }
        if(x ==  8 && y == 5) if(isCardSelectable(12)){ compareCards(12); } else if(isCardSelectable( 8)){ compareCards(8); }
        if(x ==  9 && y == 5) if(isCardSelectable(13)){ compareCards(13); } else if(isCardSelectable( 8)){ compareCards(8); }
        if(x == 10 && y == 5) if(isCardSelectable(13)){ compareCards(13); } else if(isCardSelectable( 9)){ compareCards(9); }
        if(x == 11 && y == 5) if(isCardSelectable(14)){ compareCards(14); } else if(isCardSelectable( 9)){ compareCards(9); }
        if(x == 12 && y == 5) if(isCardSelectable(14)){ compareCards(14); }

        if(x ==  2 && y == 6) if(isCardSelectable(15)){ compareCards(15); }
        if(x ==  3 && y == 6) if(isCardSelectable(15)){ compareCards(15); } else if(isCardSelectable(10)){ compareCards(10); }
        if(x ==  4 && y == 6) if(isCardSelectable(16)){ compareCards(16); } else if(isCardSelectable(10)){ compareCards(10); }
        if(x ==  5 && y == 6) if(isCardSelectable(16)){ compareCards(16); } else if(isCardSelectable(11)){ compareCards(11); }
        if(x ==  6 && y == 6) if(isCardSelectable(17)){ compareCards(17); } else if(isCardSelectable(11)){ compareCards(11); }
        if(x ==  7 && y == 6) if(isCardSelectable(17)){ compareCards(17); } else if(isCardSelectable(12)){ compareCards(12); }
        if(x ==  8 && y == 6) if(isCardSelectable(18)){ compareCards(18); } else if(isCardSelectable(12)){ compareCards(12); }
        if(x ==  9 && y == 6) if(isCardSelectable(18)){ compareCards(18); } else if(isCardSelectable(13)){ compareCards(13); }
        if(x == 10 && y == 6) if(isCardSelectable(19)){ compareCards(19); } else if(isCardSelectable(13)){ compareCards(13); }
        if(x == 11 && y == 6) if(isCardSelectable(19)){ compareCards(19); } else if(isCardSelectable(14)){ compareCards(14); }
        if(x == 12 && y == 6) if(isCardSelectable(20)){ compareCards(20); } else if(isCardSelectable(14)){ compareCards(14); }
        if(x == 13 && y == 6) if(isCardSelectable(20)){ compareCards(20); }

        if(x ==  1 && y == 7) if(isCardSelectable(21)){ compareCards(21); }
        if(x ==  2 && y == 7) if(isCardSelectable(21)){ compareCards(21); } else if(isCardSelectable(15)){ compareCards(15); }
        if(x ==  3 && y == 7) if(isCardSelectable(22)){ compareCards(22); } else if(isCardSelectable(15)){ compareCards(15); }
        if(x ==  4 && y == 7) if(isCardSelectable(22)){ compareCards(22); } else if(isCardSelectable(16)){ compareCards(16); }
        if(x ==  5 && y == 7) if(isCardSelectable(23)){ compareCards(23); } else if(isCardSelectable(16)){ compareCards(16); }
        if(x ==  6 && y == 7) if(isCardSelectable(23)){ compareCards(23); } else if(isCardSelectable(17)){ compareCards(17); }
        if(x ==  7 && y == 7) if(isCardSelectable(24)){ compareCards(24); } else if(isCardSelectable(17)){ compareCards(17); }
        if(x ==  8 && y == 7) if(isCardSelectable(24)){ compareCards(24); } else if(isCardSelectable(18)){ compareCards(18); }
        if(x ==  9 && y == 7) if(isCardSelectable(25)){ compareCards(25); } else if(isCardSelectable(18)){ compareCards(18); }
        if(x == 10 && y == 7) if(isCardSelectable(25)){ compareCards(25); } else if(isCardSelectable(19)){ compareCards(19); }
        if(x == 11 && y == 7) if(isCardSelectable(26)){ compareCards(26); } else if(isCardSelectable(19)){ compareCards(19); }
        if(x == 12 && y == 7) if(isCardSelectable(26)){ compareCards(26); } else if(isCardSelectable(20)){ compareCards(20); }
        if(x == 13 && y == 7) if(isCardSelectable(27)){ compareCards(27); } else if(isCardSelectable(20)){ compareCards(20); }
        if(x == 14 && y == 7) if(isCardSelectable(27)){ compareCards(27); }

        if(x ==  1 && y == 8) if(isCardSelectable(21)){ compareCards(21); }
        if(x ==  2 && y == 8) if(isCardSelectable(21)){ compareCards(21); }
        if(x ==  3 && y == 8) if(isCardSelectable(22)){ compareCards(22); }
        if(x ==  4 && y == 8) if(isCardSelectable(22)){ compareCards(22); }
        if(x ==  5 && y == 8) if(isCardSelectable(23)){ compareCards(23); }
        if(x ==  6 && y == 8) if(isCardSelectable(23)){ compareCards(23); }
        if(x ==  7 && y == 8) if(isCardSelectable(24)){ compareCards(24); }
        if(x ==  8 && y == 8) if(isCardSelectable(24)){ compareCards(24); }
        if(x ==  9 && y == 8) if(isCardSelectable(25)){ compareCards(25); }
        if(x == 10 && y == 8) if(isCardSelectable(25)){ compareCards(25); }
        if(x == 11 && y == 8) if(isCardSelectable(26)){ compareCards(26); }
        if(x == 12 && y == 8) if(isCardSelectable(26)){ compareCards(26); }
        if(x == 13 && y == 8) if(isCardSelectable(27)){ compareCards(27); }
        if(x == 14 && y == 8) if(isCardSelectable(27)){ compareCards(27); }
    }

    private boolean isCardSelectable(int id) {
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

    private void compareCards(int id) {
        if(selector.X == -1) { // No Card Selected
            if(findNumber(id) == 12){
                setDead(id);
                scorePoint += 50;
                setJingle(SOUND_CARD_PLACE);
            } else {
                selector.X = id;
            }
        } else { // A Card already selected
            if(findNumber(id) + findNumber(selector.X) == 11){
                setDead(id);
                setDead(selector.X);
                selector.X = -1;
                scorePoint += 50;
                setJingle(SOUND_CARD_PLACE);

            } else if(findNumber(id) == 12){ // Set new Selector
                setDead(id);
                scorePoint += 50;
                setJingle(SOUND_CARD_PLACE);
            } else {
                selector.X = id;
            }
        }
    }

    private void checkForClearedLine(int id) {
        if(id ==  0) restart();
        if(id >=  1 && id <=  2) if(cards_field[ 1].suit == -1 && cards_field[ 2].suit == -1) scorePoint += 1000;
        if(id >=  3 && id <=  5) if(cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1 && cards_field[ 5].suit == -1) scorePoint += 500;
        if(id >=  6 && id <=  9) if(cards_field[ 6].suit == -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1 && cards_field[ 9].suit == -1) scorePoint += 400;
        if(id >= 10 && id <= 14) if(cards_field[10].suit == -1 && cards_field[11].suit == -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) scorePoint += 300;
        if(id >= 15 && id <= 20) if(cards_field[15].suit == -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) scorePoint += 200;
        if(id >= 21 && id <= 27) if(cards_field[21].suit == -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) scorePoint += 100;
    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 9;
    }



}
