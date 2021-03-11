package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.SoundMap;
import net.minecraft.nbt.CompoundNBT;

import static mod.casinocraft.util.SoundMap.SOUND_CARD_PLACE;

public class LogicCardYellow extends LogicBase {   // Acey Deucey

    public Card[] cards = new Card[3];
    public int spread = 0;
    public boolean doublebet = false;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicCardYellow(int table){
        super(table);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        spread = -1;
        hand = "";
        cards[0] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -30);
        cards[1] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50);
        cards[2] = new Card(-1, -1);
        turnstate = 3;
        doublebet = false;
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        doublebet = action == 0;
        cards[2] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50);
        turnstate = 3;
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {
        if(turnstate == 3) {
            if(cards[0].number == cards[1].number) {
                if(cards[2].number == -1) {
                    if(cards[0].shiftY == 0) {
                        cards[2] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -60);
                        setJingle(SOUND_CARD_PLACE);
                    }
                } else {
                    if(cards[2].shiftY == 0) {
                        if(cards[0].number == cards[2].number) {
                            hand = "Drilling!";
                            reward[0] = 11;
                            turnstate = 4;
                        } else {
                            hand = "Tie!";
                            reward[0] = 1;
                            turnstate = 4;
                        }
                        if(reward[0] >= 2){
                            jingle = SoundMap.SOUND_REWARD;
                        }
                    }
                }
            } else if(cards[0].sortedNumber() == cards[1].sortedNumber() + 1 || cards[0].sortedNumber() + 1 == cards[1].sortedNumber()) {
                if(cards[0].shiftY == 0) {
                    hand = "Tie!";
                    reward[0] = 1;
                    turnstate = 4;
                }
            } else {
                if(cards[2].number == -1) {
                    if(cards[1].shiftY == 0) {
                        spread = cards[0].sortedNumber() - cards[1].sortedNumber();
                        if(spread < 0) spread *= -1;
                        spread--;
                        turnstate = 2;
                        hand = "Double Your Bet..?";
                    }
                } else {
                    if(cards[2].shiftY == 0) {
                        if(cards[0].sortedNumber() < cards[2].sortedNumber() && cards[2].sortedNumber() < cards[1].sortedNumber()) {
                            hand = "In Between";
                            result();
                            turnstate = 4;
                        } else if(cards[0].sortedNumber() > cards[2].sortedNumber() && cards[2].sortedNumber() > cards[1].sortedNumber()) {
                            hand = "In Between";
                            result();
                            turnstate = 4;
                        } else {
                            hand = "Lost!";
                            reward[0] = 0;
                            turnstate = 4;
                        }
                    }
                }
            }
        }
    }

    public void updateMotion(){
        for(int i = 0; i < 3; i++) {
            cards[i].update();
        }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        cards = loadCardArray(compound, 0);
        spread = compound.getInt("spread");
        doublebet = compound.getBoolean("doublebet");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveCardArray(compound, 0, cards);
        compound.putInt("spread", spread);
        compound.putBoolean("doublebet", doublebet);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void result(){
        if(spread == 1) reward[0] = doublebet ? 12 : 6; // 1:5
        if(spread == 2) reward[0] = doublebet ? 10 : 5; // 1:4
        if(spread == 3) reward[0] = doublebet ?  6 : 3; // 1:2
        if(spread >= 4) reward[0] = doublebet ?  4 : 2; // 1:1
        if(reward[0] >= 2){
            jingle = SoundMap.SOUND_REWARD;
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
        return 15;
    }

}
