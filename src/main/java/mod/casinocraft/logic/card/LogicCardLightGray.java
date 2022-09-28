package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CARD_PLACE;

public class LogicCardLightGray extends LogicModule {   // Draw Poker

    public List<Card> cardsP1 = new ArrayList<>();
    public List<Card> cardsP2 = new ArrayList<>();
    public List<Card> cardsP3 = new ArrayList<>();
    public List<Card> cardsP4 = new ArrayList<>();
    public List<Card> cardsP5 = new ArrayList<>();
    public List<Card> cardsP6 = new ArrayList<>();
    public boolean[] folded = new boolean[6];
    public int pot = 0;
    public int raisedPlayer = -1;
    public int round = 0;
    public int playerCount = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardLightGray(int tableID) {
        super(tableID);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {
        for(int i = 0; i < 6; i++){
            getCards(i).clear();
        }
        for(int i = 0; i < 6; i++){
            folded[i] = false;
        }
        pot = 0;
        raisedPlayer = -1;
        round = 0;
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {
        if(action == 0){ call();  }
        if(action == 1){ raise(); }
        if(action == 2){ check(); }
        if(action == 3){ fold();  }
        if(action >= 4){
            if(action - 4 < getCards(activePlayer).size()){
                getCards(activePlayer).get(action - 4).hidden = !getCards(activePlayer).get(action - 4).hidden;
            }
        }
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {
        timeout++;
        if(turnstate == 2 && timeout >= timeoutMAX || getFirstFreePlayerSlot() == (tableID == 1 ? 4 : 6)){
            draw();
        }
        if(turnstate == 3){
            if(folded[activePlayer]){
                drawAnother();
            }
            if(timeout == timeoutMAX){
                fold();
            }
            if(lastStanding() != -1){
                result();
            }
        }
    }

    public void updateMotion() {
        for(int i = 0; i < 6; i++){
            for(Card c : getCards(i)){
                c.update();
            }
        }
    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        cardsP1  = loadCardList(compound,  0);
        cardsP2  = loadCardList(compound,  1);
        cardsP3  = loadCardList(compound,  2);
        cardsP4  = loadCardList(compound,  3);
        cardsP5  = loadCardList(compound,  4);
        cardsP6  = loadCardList(compound,  5);
        folded[0] = compound.getBoolean("folded0");
        folded[1] = compound.getBoolean("folded1");
        folded[2] = compound.getBoolean("folded2");
        folded[3] = compound.getBoolean("folded3");
        folded[4] = compound.getBoolean("folded4");
        folded[5] = compound.getBoolean("folded5");
        pot = compound.getInt("pot");
        raisedPlayer = compound.getInt("raisedplayer");
        round = compound.getInt("round");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveCardList(compound,  0, cardsP1);
        saveCardList(compound,  1, cardsP2);
        saveCardList(compound,  2, cardsP3);
        saveCardList(compound,  3, cardsP4);
        saveCardList(compound,  4, cardsP5);
        saveCardList(compound,  5, cardsP6);
        compound.putBoolean("folded0", folded[0]);
        compound.putBoolean("folded1", folded[1]);
        compound.putBoolean("folded2", folded[2]);
        compound.putBoolean("folded3", folded[3]);
        compound.putBoolean("folded4", folded[4]);
        compound.putBoolean("folded5", folded[5]);
        compound.putInt("pot", pot);
        compound.putInt("raisedplayer", raisedPlayer);
        compound.putInt("round", round);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public List<Card> getCards(int index){
        switch (index){
            case 0: return cardsP1;
            case 1: return cardsP2;
            case 2: return cardsP3;
            case 3: return cardsP4;
            case 4: return cardsP5;
            case 5: return cardsP6;
        }
        return cardsP1;
    }

    private void draw(){
        turnstate = 3;
        timeout = 0;
        playerCount = pot = getFirstFreePlayerSlot();
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < pot; x++){
                getCards(x).add(new Card(RANDOM, 0, 24,  8*x + 8*4*y, false));
            }
        }
        for(int i = 0; i < 6; i++){
            if(i >= getFirstFreePlayerSlot()){
                folded[i] = true;
            }
        }
        setJingle(SOUND_CARD_PLACE);
    }

    private void drawAnother(){
        for(int y = 0; y < 5; y++){
            if(getCards(activePlayer).get(y).hidden){
                getCards(activePlayer).remove(y);
                getCards(activePlayer).add(new Card(RANDOM, 0, 24,  0, false));
                y--;
            }
        }
        timeout = 0;
        activePlayer = (activePlayer + 1) % getFirstFreePlayerSlot();
        if(activePlayer == 0){
            if(raisedPlayer == -1){
                round++;
                if(round == 3){
                    result();
                }
            }
        }
        if(activePlayer == raisedPlayer){
            raisedPlayer = -1;
        }
        setJingle(SOUND_CARD_PLACE);
    }

    private int lastStanding(){
        int count = 0;
        int pos = -1;
        for(int i = 0; i < 6; i++){
            if(!folded[i]){
                count++;
                pos = i;
            }
        }
        return count == 1 ? pos : -1;
    }

    private void call(){
        pot++;
        drawAnother();
    }

    private void raise(){
        pot++;
        raisedPlayer = activePlayer;
        drawAnother();
    }

    private void check(){
        drawAnother();
    }

    private void fold(){
        folded[activePlayer] = true;
        drawAnother();
    }

    private void result(){
        turnstate = 4;
        int lastPlayer = lastStanding();
        if(lastPlayer != -1){
            reward[lastPlayer] = pot;
        } else {
            int[] finalHand = new int[]{0, 0, 0, 0, 0, 0};
            for(int i = 0; i < 6; i++){
                if(getCards(i).size() < 5){
                    folded[i] = true;
                }
                finalHand[i] = folded[i] ? 0 : sortAndClear(getCards(i).get(0), getCards(i).get(1), getCards(i).get(2), getCards(i).get(3), getCards(i).get(4));
            }
            int winner = 0;
            for(int i = 0; i < 6; i++){
                if(finalHand[i] > finalHand[winner]){
                    winner = i;
                }
            }
            reward[winner] = pot;
            hand = currentPlayer[winner] + " has won the Game!";
        }
    }

    private int sortAndClear(Card card0, Card card1, Card card2, Card card3, Card card4){
        boolean sorted = false;
        Card[] tempCards = new Card[]{new Card(card0), new Card(card1), new Card(card2), new Card(card3), new Card(card4)};
        Card temp = new Card(-1, -1);
        while(!sorted){
            sorted = true;
            for(int i = 0; i < 3; i++){
                if(tempCards[i].sortedNumber() > tempCards[i+1].sortedNumber()){
                    temp.set(tempCards[i]);
                    tempCards[i].set(tempCards[i+1]);
                    tempCards[i+1].set(temp);
                    sorted = false;
                }
            }
        }
        return clear(tempCards);
    }

    private int clear(Card[] c){
        if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number == c[3].number && c[0].number == c[4].number) { return 100000 + c[0].sortedNumber(); } // 5 of a Kind

        if(c[0].number == 9 && c[1].number == 10 && c[2].number == 11 && c[3].number == 12 && c[4].number == 0 && c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 90000 + c[0].suit; } // Royal Flush

        if(c[0].number <= 9 && c[0].number + 1 == c[1].number && c[0].number + 2 == c[2].number && c[0].number + 3 == c[3].number && c[0].number + 4 == c[4].number && c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 80000 + c[0].sortedNumber(); } // Straight Flush

        if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number == c[3].number && c[0].number != c[4].number) { return 70000 + c[0].sortedNumber(); } // 4 of a Kind
        if(c[1].number == c[2].number && c[1].number == c[3].number && c[1].number == c[4].number && c[1].number != c[0].number) { return 70000 + c[1].sortedNumber(); } // 4 of a Kind

        if(c[0].number == c[1].number && c[0].number == c[2].number &&     c[0].number != c[3].number     && c[3].number == c[4].number) { return 60000 + c[3].sortedNumber()*100 + c[0].sortedNumber(); } // Full House  ###++
        if(c[2].number == c[3].number && c[2].number == c[4].number &&     c[0].number != c[3].number     && c[0].number == c[1].number) { return 60000 + c[0].sortedNumber()*100 + c[3].sortedNumber(); } // Full House  ++###

        if(c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 50000 + c[0].sortedNumber(); } // Flush

        if(c[0].number <= 9 && c[0].number + 1 == c[1].number && c[0].number + 2 == c[2].number && c[0].number + 3 == c[3].number && c[0].number + 4 == c[4].number) { return 40000 + c[0].sortedNumber(); } // Straight

        if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number != c[3].number && c[0].number != c[4].number) {  return 30000 + c[1].sortedNumber()*100 + c[4].sortedNumber(); } // 3 of a Kind
        if(c[1].number == c[2].number && c[1].number == c[3].number && c[1].number != c[0].number && c[1].number != c[4].number) {  return 30000 + c[2].sortedNumber()*100 + c[4].sortedNumber(); } // 3 of a Kind
        if(c[2].number == c[3].number && c[2].number == c[4].number && c[2].number != c[0].number && c[2].number != c[1].number) {  return 30000 + c[3].sortedNumber()*100 + c[1].sortedNumber(); } // 3 of a Kind

        if(c[0].number == c[1].number && c[2].number == c[3].number) { return 20000 + c[2].sortedNumber()*100 + c[0].sortedNumber(); } // 2 Pair  ##++_
        if(c[0].number == c[1].number && c[3].number == c[4].number) { return 20000 + c[3].sortedNumber()*100 + c[0].sortedNumber(); } // 2 Pair  ##_++
        if(c[1].number == c[2].number && c[3].number == c[4].number) { return 20000 + c[3].sortedNumber()*100 + c[1].sortedNumber(); } // 2 Pair  _##++

        if(c[0].number == c[1].number) {  return 10000 + c[0].sortedNumber() + c[4].sortedNumber(); } // 1 Pair
        if(c[1].number == c[2].number) {  return 10000 + c[1].sortedNumber() + c[4].sortedNumber(); } // 1 Pair
        if(c[2].number == c[3].number) {  return 10000 + c[2].sortedNumber() + c[4].sortedNumber(); } // 1 Pair
        if(c[3].number == c[4].number) {  return 10000 + c[3].sortedNumber() + c[2].sortedNumber(); } // 1 Pair

        int highestNumber = 0;
        for(int i = 0; i < 5; i++){
            if(c[i].sortedNumber() > highestNumber){
                highestNumber = c[i].sortedNumber();
            }
        }
        return highestNumber;
    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return true;
    }

    public int getID(){
        return 7;
    }



}