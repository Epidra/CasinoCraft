package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.SoundMap;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class LogicCardBlack extends LogicBase {   // Black Jack

    public List<Card> cards_player1 = new ArrayList<Card>();
    public List<Card> cards_player2 = new ArrayList<Card>();
    public List<Card> cards_dealer = new ArrayList<Card>();
    public int value_player1 = 0;
    public int value_player2 = 0;
    public int value_dealer = 0;
    public int split = 0; // 0 - no split, 1 - left cards, 2 - right cards




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardBlack(int table){
        super(table);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){

        cards_player1.clear();
        cards_player2.clear();
        cards_dealer.clear();

        split = 0;

        cards_player1.add(new Card(RANDOM, -32, 0,    8, false));
        cards_player1.add(new Card(RANDOM, -48, 0,   32, false));
        cards_dealer.add(new Card(RANDOM,   0, -48,  8, false));
        cards_dealer.add(new Card(RANDOM,   0, -48, 32, true));
        setJingle(SoundMap.SOUND_CARD_SHOVE);
        value_player1 = Add_Card(cards_player1, 0, 0, true);
        value_player2 = 0;
        value_dealer  = Add_Card(cards_dealer, 0, 0, true);
        if(value_player1 == 21) {
            result();
            cards_dealer.get(1).hidden = false;
        }
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == 0){ // Hit
            if(split < 2){
                value_player1 = Add_Card(cards_player1, -48, 0, false);
                if(value_player1 > 21) {
                    if(split == 0){
                        result();
                    } else {
                        split = 2;
                    }
                }
            } else {
                value_player2 = Add_Card(cards_player2, -48, 0, false);
                if(value_player2 > 21) {
                    if(value_player1 > 21) {
                        result();
                    } else {
                        turnstate = 3;
                        cards_dealer.get(1).hidden = false;
                    }
                }
            }
        }
        if(action == 1){ // Stand
            if(split == 1){
                split = 2;
            } else {
                turnstate = 3;
                cards_dealer.get(1).hidden = false;
            }
        }
        if(action == 2){ // Split
            split = 1;
            int cardX = cards_player1.get(1).number;
            int cardY = cards_player1.get(1).suit;
            cards_player1.remove(1);
            cards_player1.add(new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), -48, 0,  0, false));
            cards_player2.add(new Card(cardX, cardY, -32, 0,   8, false));
            cards_player2.add(new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), -48, 0,   0, false));

            value_player1 = Add_Card(cards_player1, 0, 0, true);
            value_player2 = Add_Card(cards_player2, 0, 0, true);

            setJingle(SoundMap.SOUND_CARD_SHOVE);
        }
        if(action == 3){ // DoubleDown
            if(split < 2){
                value_player1 = Add_Card(cards_player1, -48, 0, false);
                if(split == 1){
                    split = 2;
                } else {
                    turnstate = 3;
                    cards_dealer.get(1).hidden = false;
                }
            } else {
                value_player2 = Add_Card(cards_player2, -48, 0, true);
                if(value_player1 > 21) {
                    result();
                } else {
                    turnstate = 3;
                    cards_dealer.get(1).hidden = false;
                }
            }
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){
        if(turnstate == 3){ // Execute only on playing Player
            if(cards_player1.get(cards_player1.size() - 1).shiftY >= -16){
                if(value_dealer > 16 || (value_dealer > value_player1 && value_dealer > value_player2)){
                    result();
                } else {
                    value_dealer = Add_Card(cards_dealer, 0, -48, false);
                }
            }
        }
    }

    public void updateMotion(){
        if(cards_player1.size() > 0) for (Card card : cards_player1) { card.update(); }
        if(cards_player2.size() > 0) for (Card card : cards_player2) { card.update(); }
        if(cards_dealer .size() > 0) for (Card card : cards_dealer)  { card.update(); }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        cards_player1 = loadCardList(compound, 0);
        cards_player2 = loadCardList(compound, 1);
        cards_dealer  = loadCardList(compound, 2);
        value_player1 = compound.getInt("valueplayer1");
        value_player2 = compound.getInt("valueplayer2");
        value_dealer = compound.getInt("valuedealer");
        split = compound.getInt("split");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveCardList(compound, 0, cards_player1);
        saveCardList(compound, 1, cards_player2);
        saveCardList(compound, 2, cards_dealer );
        compound.putInt("valueplayer1", value_player1);
        compound.putInt("valueplayer2", value_player2);
        compound.putInt("valuedealer", value_dealer);
        compound.putInt("split", split);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void result(){
        turnstate = 4;
        if(value_dealer  >  21                                                                ) { hand = "The House gone bust!";  reward[0] = 2;
        } else if(value_player1 >  21                                                         ) { hand = "The Player gone bust!"; reward[0] = 0;
        } else if(value_player1 == value_dealer && cards_player1.size() >  cards_dealer.size()) { hand = "The House wins!";       reward[0] = 0;
        } else if(value_player1 == value_dealer && cards_player1.size() == cards_dealer.size()) { hand = "DRAW";                  reward[0] = 1;
        } else if(value_player1 == 21           && cards_player1.size() == 2                  ) { hand = "BLACK JACK";            reward[0] = 3;
        } else if(value_player1 == value_dealer && cards_player1.size() <  cards_dealer.size()) { hand = "The Player wins!";      reward[0] = 2;
        } else if(value_player1 >  value_dealer                                               ) { hand = "The Player wins!";      reward[0] = 2;
        } else                                                                                  { hand = "The House wins!";       reward[0] = 0;
        }
        if(split > 0){
            if(value_dealer  >  21                                                                ) { hand = hand + " / The House gone bust!";  reward[0] += 2;
            } else if(value_player2 >  21                                                         ) { hand = hand + " / The Player gone bust!"; reward[0] += 0;
            } else if(value_player2 == value_dealer && cards_player2.size() >  cards_dealer.size()) { hand = hand + " / The House wins!";       reward[0] += 0;
            } else if(value_player2 == value_dealer && cards_player2.size() == cards_dealer.size()) { hand = hand + " / DRAW";                  reward[0] += 1;
            } else if(value_player2 == 21           && cards_player2.size() == 2                  ) { hand = hand + " / BLACK JACK";            reward[0] += 3;
            } else if(value_player2 == value_dealer && cards_player2.size() <  cards_dealer.size()) { hand = hand + " / The Player wins!";      reward[0] += 2;
            } else if(value_player2 >  value_dealer                                               ) { hand = hand + " / The Player wins!";      reward[0] += 2;
            } else                                                                                  { hand = hand + " / The House wins!";       reward[0] += 0;
            }
        }
        if(reward[0] >= 2){
            jingle = SoundMap.SOUND_REWARD;
        }
    }

    private int Add_Card(List<Card> cards, int shiftX, int shiftY, boolean noCard) {
        int value = 0;
        if(!noCard) cards.add(new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), shiftX, shiftY));
        int ace = 0;
        for (Card card : cards) {
            if (card.number == 0) {
                ace++;
            } else if (card.number <= 9) {
                value += card.number + 1;
            } else {
                value += 10;
            }
        }
        if(ace > 0) {
            while(ace > 0) {
                if(value <= 10) {
                    value += 11;
                } else {
                    value += 1;
                }
                ace--;
            }
        }
        jingle = SoundMap.SOUND_CARD_SHOVE;
        return value;
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 0;
    }

}
