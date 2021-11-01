package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.SoundMap;
import net.minecraft.nbt.CompoundTag;

import static mod.casinocraft.util.SoundMap.SOUND_CARD_SHOVE;

public class LogicCardWhite extends LogicModule {   // Single Poker
    public boolean[] hold = new boolean[5];

    public Card[] cards_field = new Card[5];

    private int ticker = 0;
    private int movestate = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardWhite(int table){
        super(table);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        for(int i = 0; i < 5; i++){
            cards_field[i] = new Card(RANDOM, 0, 20 + 5*i);
            hold[i] = false;
        }
        ticker = 0;
        movestate = 0;
        setJingle(SOUND_CARD_SHOVE);
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == 5 && turnstate == 2){
            turnstate = 3;
            movestate = 1;
            for(int i = 0; i < 5; i++){
                if(!hold[i]) cards_field[i].hidden = !cards_field[i].hidden;
            }
        } else {
            hold[action] = !hold[action];
        }
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){
        switch(movestate){

            // ----- NULL -----
            case 0:
                break;

            // ----- Cards move up ----- //
            case 1:
                ticker+=2;
                for(int i = 0; i < 5; i++){
                    if(!hold[i]) cards_field[i].shiftY-=2;
                }
                if(ticker >= 30){
                    for(int i = 0; i < 5; i++){
                        if(!hold[i]){
                            int sX = cards_field[i].shiftX;
                            int sY = cards_field[i].shiftY;
                            cards_field[i] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), sX, sY);
                        }
                    }
                    movestate = 2;
                }
                break;

            // ----- Cards move down ----- //
            case 2:
                ticker-=2;
                for(int i = 0; i < 5; i++){
                    if(!hold[i]) cards_field[i].shiftY+=2;
                }
                if(ticker <= 0){
                    movestate = 3;
                }
                break;

            // ----- Cards move together ----- //
            case 3:
                ticker+=2;
                cards_field[0].shiftX+=4;
                cards_field[1].shiftX+=2;
                cards_field[3].shiftX-=2;
                cards_field[4].shiftX-=4;
                if(ticker == 44){
                    sort();
                    movestate = 4;
                }
                break;

            // ----- Cards move apart ----- //
            case 4:
                ticker-=2;
                cards_field[0].shiftX-=4;
                cards_field[1].shiftX-=2;
                cards_field[3].shiftX+=2;
                cards_field[4].shiftX+=4;
                if(ticker == 0){
                    result();
                    turnstate = 4;
                    movestate = 0;
                }
                break;
        }
    }

    public void updateMotion(){
        if(turnstate == 2) {
            for(int i = 0; i < 5; i++){
                cards_field[i].update();
            }
        }
    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        cards_field = loadCardArray(compound, 0);
        hold[0] = compound.getBoolean("hold0");
        hold[1] = compound.getBoolean("hold1");
        hold[2] = compound.getBoolean("hold2");
        hold[3] = compound.getBoolean("hold3");
        hold[4] = compound.getBoolean("hold4");
        ticker = compound.getInt("ticker");
        movestate = compound.getInt("movestate");
    }

    public CompoundTag save2(CompoundTag compound){
        saveCardArray(compound, 0, cards_field);
        compound.putBoolean("hold0", hold[0]);
        compound.putBoolean("hold1", hold[1]);
        compound.putBoolean("hold2", hold[2]);
        compound.putBoolean("hold3", hold[3]);
        compound.putBoolean("hold4", hold[4]);
        compound.putInt("ticker", ticker);
        compound.putInt("movestate", movestate);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void sort() {
        Card[] card = new Card[5];
        card[0] = cards_field[0];
        card[1] = cards_field[1];
        card[2] = cards_field[2];
        card[3] = cards_field[3];
        card[4] = cards_field[4];
        Card z = new Card(-1, -1);
        if(card[0].sortedNumber() > card[4].sortedNumber()) { z.set(card[0]); card[0].set(card[1]); card[1].set(card[2]); card[2].set(card[3]); card[3].set(card[4]); card[4].set(z); }
        if(card[0].sortedNumber() > card[3].sortedNumber()) { z.set(card[0]); card[0].set(card[1]); card[1].set(card[2]); card[2].set(card[3]); card[3].set(                      z); }
        if(card[0].sortedNumber() > card[2].sortedNumber()) { z.set(card[0]); card[0].set(card[1]); card[1].set(card[2]); card[2].set(                                            z); }
        if(card[0].sortedNumber() > card[1].sortedNumber()) { z.set(card[0]); card[0].set(card[1]); card[1].set(                                                                  z); }
        if(card[1].sortedNumber() > card[4].sortedNumber()) { z.set(                      card[1]); card[1].set(card[2]); card[2].set(card[3]); card[3].set(card[4]); card[4].set(z); }
        if(card[1].sortedNumber() > card[3].sortedNumber()) { z.set(                      card[1]); card[1].set(card[2]); card[2].set(card[3]); card[3].set(                      z); }
        if(card[1].sortedNumber() > card[2].sortedNumber()) { z.set(                      card[1]); card[1].set(card[2]); card[2].set(                                            z); }
        if(card[2].sortedNumber() > card[4].sortedNumber()) { z.set(                                            card[2]); card[2].set(card[3]); card[3].set(card[4]); card[4].set(z); }
        if(card[2].sortedNumber() > card[3].sortedNumber()) { z.set(                                            card[2]); card[2].set(card[3]); card[3].set(                      z); }
        if(card[3].sortedNumber() > card[4].sortedNumber()) { z.set(                                                                  card[3]); card[3].set(card[4]); card[4].set(z); }

        cards_field[0].set(card[0]);
        cards_field[1].set(card[1]);
        cards_field[2].set(card[2]);
        cards_field[3].set(card[3]);
        cards_field[4].set(card[4]);

        cards_field[0].shiftX =  44*2;
        cards_field[1].shiftX =  44;
        cards_field[2].shiftX =   0;
        cards_field[3].shiftX = -44;
        cards_field[4].shiftX = -44*2;

    }

    private void result() {
        if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number == cards_field[3].number && cards_field[0].number == cards_field[4].number) {
            hand = "5 of a Kind";
            reward[0] = 20;
        } else if(cards_field[0].number == 9 && cards_field[1].number == 10 && cards_field[2].number == 11 && cards_field[3].number == 12 && cards_field[4].number == 0 && cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
            hand = "ROYAL FLUSH!!";
            reward[0] = 16;
        } else if(cards_field[0].number <= 9 && cards_field[0].number + 1 == cards_field[1].number && cards_field[0].number + 2 == cards_field[2].number && cards_field[0].number + 3 == cards_field[3].number && cards_field[0].number + 4 == cards_field[4].number && cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
            hand = "Straight Flush";
            reward[0] = 12;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number == cards_field[3].number && cards_field[0].number != cards_field[4].number) {
            hand = "4 of a Kind";
            reward[0] = 10;
        } else if(cards_field[1].number == cards_field[2].number && cards_field[1].number == cards_field[3].number && cards_field[1].number == cards_field[4].number && cards_field[1].number != cards_field[0].number) {
            hand = "4 of a Kind";
            reward[0] = 10;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number != cards_field[3].number && cards_field[3].number == cards_field[4].number) {
            hand = "Full House";
            reward[0] = 8;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[0].number != cards_field[2].number && cards_field[2].number == cards_field[3].number && cards_field[2].number == cards_field[4].number) {
            hand = "Full House";
            reward[0] = 8;
        } else if(cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
            hand = "Flush";
            reward[0] = 7;
        } else if(cards_field[0].number <= 9 && cards_field[0].number + 1 == cards_field[1].number && cards_field[0].number + 2 == cards_field[2].number && cards_field[0].number + 3 == cards_field[3].number && cards_field[0].number + 4 == cards_field[4].number) {
            hand = "Straight";
            reward[0] = 6;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number != cards_field[3].number && cards_field[0].number != cards_field[4].number) {
            hand = "3 of a Kind";
            reward[0] = 4;
        } else if(cards_field[1].number == cards_field[2].number && cards_field[1].number == cards_field[3].number && cards_field[1].number != cards_field[0].number && cards_field[1].number != cards_field[4].number) {
            hand = "3 of a Kind";
            reward[0] = 4;
        } else if(cards_field[2].number == cards_field[3].number && cards_field[2].number == cards_field[4].number && cards_field[2].number != cards_field[0].number && cards_field[2].number != cards_field[1].number) {
            hand = "3 of a Kind";
            reward[0] = 4;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[2].number == cards_field[3].number) {
            hand = "Two Pair";
            reward[0] = 2;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[3].number == cards_field[4].number) {
            hand = "Two Pair";
            reward[0] = 2;
        } else if(cards_field[1].number == cards_field[2].number && cards_field[3].number == cards_field[4].number) {
            hand = "Two Pair";
            reward[0] = 2;
        } else if((cards_field[0].number >= 10 || cards_field[0].number == 0) && cards_field[0].number == cards_field[1].number) {
            hand = "Jacks or Better";
            reward[0] = 1;
        } else if((cards_field[1].number >= 10 || cards_field[1].number == 0) && cards_field[1].number == cards_field[2].number) {
            hand = "Jacks or Better";
            reward[0] = 1;
        } else if((cards_field[2].number >= 10 || cards_field[2].number == 0) && cards_field[2].number == cards_field[3].number) {
            hand = "Jacks or Better";
            reward[0] = 1;
        } else if((cards_field[3].number >= 10 || cards_field[3].number == 0) && cards_field[3].number == cards_field[4].number) {
            hand = "Jacks or Better";
            reward[0] = 1;
        }
        if(reward[0] >= 2){
            jingle = SoundMap.SOUND_REWARD;
        }
    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 14;
    }



}