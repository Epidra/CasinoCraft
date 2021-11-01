package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.SoundMap;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CARD_PLACE;

public class LogicCardRed extends LogicModule {   // Rouge et Noir

    public List<Card> cards_rouge  = new ArrayList<Card>();
    public List<Card>   cards_noir = new ArrayList<Card>();

    public int value_rouge = 0;
    public int value_noir  = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardRed(int table){
        super(table);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        selector.set(-1, -1);
        cards_rouge.clear();
        cards_noir.clear();
        value_rouge = 0;
        value_noir = 0;
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        selector.X = action;
        turnstate = 3;
        cards_rouge.add(new Card(RANDOM, 0, -50));
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){
        if(turnstate == 3) {
            if(value_rouge < 31) {
                if(cards_rouge.get(cards_rouge.size() - 1).shiftY >= -10) {
                    value_rouge += cards_rouge.get(cards_rouge.size() - 1).number >= 9 ? 10 : (cards_rouge.get(cards_rouge.size() - 1).number + 1);
                    if(value_rouge >= 31) {
                        cards_noir.add(new Card(RANDOM, 0, -50));
                        setJingle(SOUND_CARD_PLACE);
                    } else {
                        cards_rouge.add(new Card(RANDOM, 0, -50));
                        setJingle(SOUND_CARD_PLACE);
                    }
                }
            } else {
                if(cards_noir.get(cards_noir.size() - 1).shiftY >= -10) {
                    value_noir += cards_noir.get(cards_noir.size() - 1).number >= 9 ? 10 : (cards_noir.get(cards_noir.size() - 1).number + 1);
                    if(value_noir >= 31) {
                        result();
                    } else {
                        cards_noir.add(new Card(RANDOM, 0, -50));
                        setJingle(SOUND_CARD_PLACE);
                    }
                }
            }
        }
    }

    public void updateMotion(){
        for(Card c : cards_rouge) { c.update(); }
        for(Card c : cards_noir ) { c.update(); }
    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        cards_rouge = loadCardList(compound, 0);
        cards_noir  = loadCardList(compound, 1);
        value_rouge = compound.getInt("valuerouge");
        value_noir  = compound.getInt("valuenoir");
    }

    public CompoundTag save2(CompoundTag compound){
        saveCardList(compound, 0, cards_rouge);
        saveCardList(compound, 1, cards_noir);
        compound.putInt("valuerouge", value_rouge);
        compound.putInt("valuenoir", value_noir);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public void result(){
        turnstate = 4;
        if(value_rouge <  value_noir) { hand = "Rouge Wins!"; if(selector.X == 0) { reward[0] = 2; } }
        if(value_rouge >  value_noir) { hand = "Noir Wins!";  if(selector.X == 1) { reward[0] = 2; } }
        if(value_rouge == value_noir) { hand = "Tie!";                              reward[0] = 1; }
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
        return 13;
    }



}
