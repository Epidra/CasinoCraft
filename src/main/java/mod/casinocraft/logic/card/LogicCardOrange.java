package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.SoundMap;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.*;

public class LogicCardOrange extends LogicModule {   // Baccarat

    public List<Card> cards_player = new ArrayList<Card>();
    public List<Card> cards_dealer = new ArrayList<Card>();

    public int value_player = 0;
    public int value_dealer = 0;

    public int status = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardOrange(int table){
        super(table);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        cards_player.clear();
        cards_dealer.clear();
        value_player = 0;
        value_dealer = 0;
        status = 0;

        cards_player.add(new Card(RANDOM));
        cards_player.add(new Card(RANDOM));
        cards_dealer.add(new Card(RANDOM));
        cards_dealer.add(new Card(RANDOM));

        cards_player.get(0).setShift(-32,   0,  8);
        cards_player.get(1).setShift(-48,   0, 32);
        cards_dealer.get(0).setShift(  0, -48,  8);
        cards_dealer.get(1).setShift(  0, -48, 32);

        for(int i = 0; i < cards_player.size(); i++){
            if(cards_player.get(i).number <= 9) {
                value_player += cards_player.get(i).number + 1;
            }
        }

        for(int i = 0; i < cards_dealer.size(); i++){
            if(cards_dealer.get(i).number <= 9) {
                value_dealer += cards_dealer.get(i).number + 1;
            }
        }

        value_player %= 10;
        value_dealer %= 10;

        if(value_player >= 8 || value_dealer >= 8){
            status = 1;
            result();
        } else {
            status = 2;
        }
        setJingle(SOUND_CARD_SHOVE);
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == 0) addCard(true);  // HIT
        if(action == 1) addCard(false); // STAND
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){

    }

    public void updateMotion(){
        if(cards_player.size() > 0) for (Card card : cards_player) { card.update(); }
        if(cards_dealer.size() > 0) for (Card card : cards_dealer) { card.update(); }
    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        cards_player = loadCardList(compound, 0);
        cards_dealer = loadCardList(compound, 1);
        value_player = compound.getInt("valueplayer");
        value_dealer = compound.getInt("valuedealer");
        status = compound.getInt("status");
    }

    public CompoundTag save2(CompoundTag compound){
        saveCardList(compound, 0, cards_player);
        saveCardList(compound, 1, cards_player);
        compound.putInt("valueplayer", value_player);
        compound.putInt("valuedealer", value_dealer);
        compound.putInt("status", status);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void addCard(boolean player) {
        if(player) {
            value_player = 0;
            cards_player.add(new Card(RANDOM, -48, 0));
            for(int i = 0; i < cards_player.size(); i++){
                if(cards_player.get(i).number <= 9) {
                    value_player += cards_player.get(i).number + 1;
                }
            }
            value_player %= 10;
        }

        boolean temp_draw = false;

        if(cards_player.size() == 2 || value_dealer <= 3) { temp_draw = true; } else if(value_dealer == 4 && value_player <= 7) { temp_draw = true; } else if(value_dealer == 5 && value_player >= 4 && value_player <= 7) { temp_draw = true; } else if(value_dealer == 6 && value_player >= 6 && value_player <= 7) { temp_draw = true; }

        if(temp_draw) {
            value_dealer = 0;
            cards_dealer.add(new Card(RANDOM, -48, 0));
            for(int i = 0; i < cards_dealer.size(); i++){
                if(cards_dealer.get(i).number <= 9) {
                    value_dealer += cards_dealer.get(i).number + 1;
                }
            }
            value_dealer %= 10;
        }
        setJingle(SOUND_CARD_PLACE);
        result();
    }

    private void result() {
        turnstate = 4;
        if(status == 2) status = 3;
        if(value_dealer <  value_player){ hand = "The Player Wins!"; reward[0] = 2; }
        if(value_dealer >  value_player){ hand = "The House Wins!";  reward[0] = 0; }
        if(value_dealer == value_player){ hand = "DRAW!";            reward[0] = 1; }
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
        return 10;
    }



}
