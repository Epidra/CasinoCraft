package mod.casinocraft.logic.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CARD_PLACE;

public class LogicCardGreen extends LogicModule {   // Mau-Mau

    public List<Card> cardsP1 = new ArrayList<>();
    public List<Card> cardsP2 = new ArrayList<>();
    public List<Card> cardsP3 = new ArrayList<>();
    public List<Card> cardsP4 = new ArrayList<>();
    public List<Card> cardsP5 = new ArrayList<>();
    public List<Card> cardsP6 = new ArrayList<>();
    public Card[] placed = new Card[]{new Card(-1, -1),new Card(-1, -1)};
    public boolean[] folded = new boolean[6];
    public int chosenColor = -1;
    public int forcedAction = 0; // 0 - none, 1 - wait, 2 - draw2, 3 - choose color




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardGreen(int tableID) {
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
        placed[0] = new Card(-1, -1);
        placed[1] = new Card(-1, -1);
        chosenColor = -1;
        forcedAction = 0;
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {
        if(action == -1){
            drawCard();
        } else if(action < -1){ // CHOOSE COLOR
            chooseColor(action);
        } else {
            if(action < getCards(activePlayer).size()){
                playCard(action);
            }
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {
        timeout++;
        if(turnstate == 2 && timeout >= CasinoKeeper.config_timeout.get() || getFirstFreePlayerSlot() == (tableID == 1 ? 4 : 6)){
            draw();
        }
        if(turnstate == 3){
            if(timeout == CasinoKeeper.config_timeout.get()){
                if(forcedAction == 3){
                    chooseColor(4);
                } else {
                    drawCard();
                }
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
        placed[0].update();
        placed[1].update();
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        cardsP1  = loadCardList(compound,  0);
        cardsP2  = loadCardList(compound,  1);
        cardsP3  = loadCardList(compound,  2);
        cardsP4  = loadCardList(compound,  3);
        cardsP5  = loadCardList(compound,  4);
        cardsP6  = loadCardList(compound,  5);
        placed   = loadCardArray(compound, 6);
        folded[0] = compound.getBoolean("folded0");
        folded[1] = compound.getBoolean("folded1");
        folded[2] = compound.getBoolean("folded2");
        folded[3] = compound.getBoolean("folded3");
        folded[4] = compound.getBoolean("folded4");
        folded[5] = compound.getBoolean("folded5");
        chosenColor = compound.getInt("chosencolor");
        forcedAction = compound.getInt("forcedaction");
    }

    public CompoundTag save2(CompoundTag compound){
        saveCardList(compound,  0, cardsP1);
        saveCardList(compound,  1, cardsP2);
        saveCardList(compound,  2, cardsP3);
        saveCardList(compound,  3, cardsP4);
        saveCardList(compound,  4, cardsP5);
        saveCardList(compound,  5, cardsP6);
        saveCardArray(compound, 6, placed);
        compound.putBoolean("folded0", folded[0]);
        compound.putBoolean("folded1", folded[1]);
        compound.putBoolean("folded2", folded[2]);
        compound.putBoolean("folded3", folded[3]);
        compound.putBoolean("folded4", folded[4]);
        compound.putBoolean("folded5", folded[5]);
        compound.putInt("chosencolor", chosenColor);
        compound.putInt("forcedaction", forcedAction);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

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
        int playerCount = getFirstFreePlayerSlot();
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < playerCount; x++){
                getCards(x).add(new Card(RANDOM, 0, 24,  8*x + 8*4*y, false));
            }
        }
        for(int i = 0; i < 6; i++){
            if(i >= getFirstFreePlayerSlot()){
                folded[i] = true;
            }
        }
        placed[0].set(new Card(RANDOM, 0, 24,  0, false));
        placed[1].set(new Card(RANDOM, 0,  0,  0, true));
        setJingle(SOUND_CARD_PLACE);
    }

    private void drawCard(){
        if(forcedAction == 0){ // NONE
            getCards(activePlayer).add(new Card(RANDOM, 0, 24,  0, false));
        } else
        if(forcedAction == 1){ // WAIT

        } else
        if(forcedAction == 2){ // DRAW2
            getCards(activePlayer).add(new Card(RANDOM, 0, 24,  0, false));
            getCards(activePlayer).add(new Card(RANDOM, 0, 24,  8, false));
        } else
        if(forcedAction == 3){ // CHOOSE COLOR

        }
        forcedAction = 0;
        timeout = 0;
        activePlayer = (activePlayer + 1) % getFirstFreePlayerSlot();
        setJingle(SOUND_CARD_PLACE);
    }

    private void playCard(int action){
        if(canStack(action)){
            chosenColor = -1;
            placed[1].set(placed[0]);
            placed[0].set(getCards(activePlayer).get(action));
            placed[0].setShift(0, 24, 0);
            getCards(activePlayer).remove(action);
            if(placed[0].number == 10) { forcedAction = 3; } // CHOOSE
            if(placed[0].number == 11) { forcedAction = 1; } // WAIT
            if(placed[0].number == 12) { forcedAction = 2; } // DRAW2
            if(forcedAction != 3){
                timeout = 0;
                activePlayer = (activePlayer + 1) % getFirstFreePlayerSlot();
            }
            setJingle(SOUND_CARD_PLACE);
        }
    }

    private void chooseColor(int index){
        forcedAction = 0;
        chosenColor = index+5;
        timeout = 0;
        activePlayer = (activePlayer + 1) % getFirstFreePlayerSlot();
    }

    private int lastStanding(){
        for(int i = 0; i < 6; i++){
            if(!folded[i]){
                if(getCards(i).size() == 0){
                    return i;
                }
            }
        }
        return -1;
    }

    private void result(){
        turnstate = 4;
    }

    private boolean canStack(int pos){
        if(getCards(activePlayer).get(pos).number == 10){
            return true;
        }
        if(chosenColor == -1){
            if(getCards(activePlayer).get(pos).number == placed[0].number || getCards(activePlayer).get(pos).suit == placed[0].suit){
                return true;
            }
        } else {
            if(getCards(activePlayer).get(pos).suit == chosenColor){
                return true;
            }
        }
        return false;
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return true;
    }

    public int getID(){
        return 5;
    }

}
