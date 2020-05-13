package mod.casinocraft.logic.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundNBT;

public class LogicCardLightGray extends LogicBase {   // Draw Poker

    public Card[][] cards = new Card[5][6];
    public boolean[] folded = new boolean[6];
    public int pot = 0;
    public int raisedPlayer = -1;
    public boolean[] dropped = new boolean[5];
    public int round = 0;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardLightGray(int tableID) {
        super(tableID);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {
        for(int x = 0; x < 6; x++){
            for(int y = 0; y < 5; y++){
                cards[y][x] = new Card(-1, -1);
            }
        }
        for(int i = 0; i < 5; i++){
            dropped[i] = false;
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
        if(action == 0){ // CALL
            call();
        }
        if(action == 1){ // RAISE
            raise();
        }
        if(action == 2){ // CHECK
            check();
        }
        if(action == 3){ // FOLD
            fold();
        }
        if(action >= 4){
            dropped[action - 4] = !dropped[action - 4];
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {
        timeout++;
        if(turnstate == 2 && timeout >= CasinoKeeper.config_timeout.get() || getFirstFreePlayerSlot() == (tableID == 1 ? 4 : 6)){
            draw();
        }
        if(turnstate == 3){
            if(folded[activePlayer]){
                drawAnother();
            }
            if(timeout == CasinoKeeper.config_timeout.get()){
                fold();
            }
            if(lastStanding() != -1){
                result();
            }
        }
    }

    public void updateMotion() {
        for(int x = 0; x < 6; x++){
            for(int y = 0; y < 5; y++){
                cards[y][x].update();
            }
        }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        cards[0] = loadCardArray(compound, 0);
        cards[1] = loadCardArray(compound, 1);
        cards[2] = loadCardArray(compound, 2);
        cards[3] = loadCardArray(compound, 3);
        cards[4] = loadCardArray(compound, 4);
        folded[0] = compound.getBoolean("folded0");
        folded[1] = compound.getBoolean("folded1");
        folded[2] = compound.getBoolean("folded2");
        folded[3] = compound.getBoolean("folded3");
        folded[4] = compound.getBoolean("folded4");
        dropped[0] = compound.getBoolean("dropped0");
        dropped[1] = compound.getBoolean("dropped1");
        dropped[2] = compound.getBoolean("dropped2");
        dropped[3] = compound.getBoolean("dropped3");
        dropped[4] = compound.getBoolean("dropped4");
        pot = compound.getInt("pot");
        raisedPlayer = compound.getInt("raisedplayer");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveCardArray(compound, 0, cards[0]);
        saveCardArray(compound, 1, cards[1]);
        saveCardArray(compound, 2, cards[2]);
        saveCardArray(compound, 3, cards[3]);
        saveCardArray(compound, 4, cards[4]);
        compound.putBoolean("folded0", folded[0]);
        compound.putBoolean("folded1", folded[1]);
        compound.putBoolean("folded2", folded[2]);
        compound.putBoolean("folded3", folded[3]);
        compound.putBoolean("folded4", folded[4]);
        compound.putBoolean("folded0", folded[0]);
        compound.putBoolean("dropped1", dropped[1]);
        compound.putBoolean("dropped2", dropped[2]);
        compound.putBoolean("dropped3", dropped[3]);
        compound.putBoolean("dropped4", dropped[4]);
        compound.putInt("pot", pot);
        compound.putInt("raisedplayer", raisedPlayer);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void draw(){
        turnstate = 3;
        timeout = 0;
        pot = getFirstFreePlayerSlot();
        if(tableID == 1){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < pot; x++){
                    cards[y][x] = new Card(RANDOM, x == 1 ? 24 : x == 3 ? -24 : 0, x == 0 ? -24 : x == 2 ? 24 : 0,  8*x + 8*4*y, false);
                }
            }
        } else {
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < pot; x++){
                    cards[y][x] = new Card(RANDOM, x == 1 ? 24 : x == 4 ? -24 : 0, x == 0 || x == 5 ? -24 : x == 2 || x == 3 ? 24 : 0,  8*x+8*6*y, false);
                }
            }
        }
        for(int i = 0; i < 6; i++){
            if(i >= getFirstFreePlayerSlot()){
                folded[i] = true;
            }
        }
    }

    private void drawAnother(){
        for(int y = 0; y < 5; y++){
            if(dropped[y]){
                if(tableID == 1)
                    cards[y][activePlayer] = new Card(RANDOM, activePlayer == 1 ? 24 : activePlayer == 3 ? -24 : 0, activePlayer == 0 ? -24 : activePlayer == 2 ? 24 : 0,  0, false);
                if(tableID == 2)
                    cards[y][activePlayer] = new Card(RANDOM, activePlayer == 1 ? 24 : activePlayer == 4 ? -24 : 0, activePlayer == 0 || activePlayer == 5 ? -24 : activePlayer == 2 || activePlayer == 3 ? 24 : 0,  0, false);
                dropped[y] = false;
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
                finalHand[i] = folded[i] ? 0 : sortAndClear(cards[0][i], cards[1][i], cards[2][i], cards[3][i], cards[4][i]);
            }
            int winner = 0;
            for(int i = 0; i < 6; i++){
                if(finalHand[i] > finalHand[winner]){
                    winner = i;
                }
            }
            reward[winner] = pot;
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




    //----------------------------------------SUPPORT----------------------------------------//

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