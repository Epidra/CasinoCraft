package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;

public class LogicAceyDeucey extends LogicBase {

    public Card[] cards = new Card[3];

    public int spread = 0;

    public boolean doublebet = false;



    //--------------------CONSTRUCTOR--------------------

    public LogicAceyDeucey(int table){
        super(false, table, "c_acey_deucey");
    }



    //--------------------BASIC--------------------

    public void start2(){
        spread = -1;
        hand = "";
        cards[0] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -25);
        cards[1] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50);
        cards[2] = new Card(-1, -1);
        turnstate = 3;
        doublebet = false;
    }

    public void actionTouch(int action){
        doublebet = action == 0;
        cards[2] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -50);
        turnstate = 3;
    }

    @Override
    public int size() {
        return 0;
    }

    public void updateMotion(){
        for(int i = 0; i < 3; i++) {
            cards[i].update();
        }
    }

    public void updateLogic() {
        if(turnstate == 2) {

        }
        if(turnstate == 3) {
            if(cards[0].number == cards[1].number) {
                if(cards[2].number == -1) {
                    if(cards[0].shiftY == 0) {
                        cards[2] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), 0, -75);
                    }
                } else {
                    if(cards[2].shiftY == 0) {
                        if(cards[0].number == cards[2].number) {
                            hand = "Drilling!";
                            reward = 11;
                            turnstate = 4;
                        } else {
                            hand = "Tie!";
                            reward = 1;
                            turnstate = 4;
                        }
                    }
                }
            } else if(cards[0].SortedNumber() == cards[1].SortedNumber() + 1 || cards[0].SortedNumber() + 1 == cards[1].SortedNumber()) {
                if(cards[0].shiftY == 0) {
                    hand = "Tie!";
                    reward = 1;
                    turnstate = 4;
                }
            } else {
                if(cards[2].number == -1) {
                    if(cards[1].shiftY == 0) {
                        spread = cards[0].SortedNumber() - cards[1].SortedNumber();
                        if(spread < 0) spread *= -1;
                        spread--;
                        turnstate = 2;
                        hand = "Double Your Bet..?";
                    }
                } else {
                    if(cards[2].shiftY == 0) {
                        if(cards[0].SortedNumber() < cards[2].SortedNumber() && cards[2].SortedNumber() < cards[1].SortedNumber()) {
                            hand = "In Between";
                            result();
                            turnstate = 4;
                        } else if(cards[0].SortedNumber() > cards[2].SortedNumber() && cards[2].SortedNumber() > cards[1].SortedNumber()) {
                            hand = "In Between";
                            result();
                            turnstate = 4;
                        } else {
                            hand = "Lost!";
                            reward = 0;
                            turnstate = 4;
                        }
                    }
                }
            }
        }
    }



    //--------------------CUSTOM--------------------

    private void result(){
        if(spread == 1) reward = doublebet ? 12 : 6; // 1:5
        if(spread == 2) reward = doublebet ? 10 : 5; // 1:4
        if(spread == 3) reward = doublebet ?  6 : 3; // 1:2
        if(spread >= 4) reward = doublebet ?  4 : 2; // 1:1
    }

}
