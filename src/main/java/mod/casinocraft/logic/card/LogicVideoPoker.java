package mod.casinocraft.logic.card;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;

public class LogicVideoPoker extends LogicBase {

    public boolean[] hold = new boolean[5];

    public Card[] cards_field = new Card[5];

    private int ticker = 0;
    private int movestate = 0;

    //public boolean end;
    //public Card[] card = new Card[5];
    //public boolean[] hold = new boolean[5];
    //private int ticker;
    //private int movestate;

    //--------------------CONSTRUCTOR--------------------

    public LogicVideoPoker(int table){
        super(false, table, "videopoker");
    }



    //--------------------BASIC--------------------

    public void start2(){
        for(int i = 0; i < 5; i++){
            cards_field[i] = new Card(RANDOM, 0, 20 + 5*i);
            hold[i] = false;
        }
        ticker = 0;
        movestate = 0;
    }

    public void actionTouch(int action){
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

    public void updateMotion(){
        if(turnstate == 2) {
            for(int i = 0; i < 5; i++){
                cards_field[i].update();
            }
        }
    }

    public void updateLogic(){
        switch(movestate){
            case 0: // NULL
                break;
            case 1: // Cards Move up
                ticker++;
                for(int i = 0; i < 5; i++){
                    if(!hold[i]) cards_field[i].shiftY--;
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
            case 2: // Cards Move down
                ticker--;
                for(int i = 0; i < 5; i++){
                    if(!hold[i]) cards_field[i].shiftY++;
                }
                if(ticker == 0){
                    movestate = 3;
                }
                break;
            case 3: // Cards Move Together
                ticker++;
                cards_field[0].shiftX+=2;
                cards_field[1].shiftX+=1;
                cards_field[3].shiftX-=1;
                cards_field[4].shiftX-=2;
                if(ticker == 48){
                    Sort();
                    movestate = 4;
                }
                break;
            case 4: // Cards Move apart
                ticker--;
                cards_field[0].shiftX-=2;
                cards_field[1].shiftX-=1;
                cards_field[3].shiftX+=1;
                cards_field[4].shiftX+=2;
                if(ticker == 0){
                    Result();
                    turnstate =4;
                    movestate = 0;
                }
                break;
        }
    }



    //--------------------CUSTOM--------------------

    private void Sort() {
        Card[] card = new Card[5];
        card[0] = cards_field[0];
        card[1] = cards_field[1];
        card[2] = cards_field[2];
        card[3] = cards_field[3];
        card[4] = cards_field[4];
        if(card[0].number > card[4].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[0].number > card[3].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[0].number > card[2].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2]                                       = z; }
        if(card[0].number > card[1].number) { Card z = card[0]; card[0] = card[1]; card[1]                                                          = z; }
        if(card[1].number > card[4].number) { Card z =                    card[1]; card[1] = card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[1].number > card[3].number) { Card z =                    card[1]; card[1] = card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[1].number > card[2].number) { Card z =                    card[1]; card[1] = card[2]; card[2]                                       = z; }
        if(card[2].number > card[4].number) { Card z =                                       card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[2].number > card[3].number) { Card z =                                       card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[3].number > card[4].number) { Card z =                                                          card[3]; card[3] = card[4]; card[4] = z; }

        card[0].shiftX =  48*2;
        card[1].shiftX =  48;
        card[2].shiftX =   0;
        card[3].shiftX = -48;
        card[4].shiftX = -48*2;

        cards_field[0].set(card[0]);
        cards_field[1].set(card[1]);
        cards_field[2].set(card[2]);
        cards_field[3].set(card[3]);
        cards_field[4].set(card[4]);

    }

    private void Result() {
        if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number == cards_field[3].number && cards_field[0].number == cards_field[4].number) {
            hand = "5 of a Kind";
            reward = 20;
        } else if(cards_field[0].number == 9 && cards_field[1].number == 10 && cards_field[2].number == 11 && cards_field[3].number == 12 && cards_field[4].number == 0 && cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
            hand = "ROYAL FLUSH!!";
            reward = 16;
        } else if(cards_field[0].number <= 9 && cards_field[0].number + 1 == cards_field[1].number && cards_field[0].number + 2 == cards_field[2].number && cards_field[0].number + 3 == cards_field[3].number && cards_field[0].number + 4 == cards_field[4].number && cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
            hand = "Straight Flush";
            reward = 12;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number == cards_field[3].number && cards_field[0].number != cards_field[4].number) {
            hand = "4 of a Kind";
            reward = 10;
        } else if(cards_field[1].number == cards_field[2].number && cards_field[1].number == cards_field[3].number && cards_field[1].number == cards_field[4].number && cards_field[1].number != cards_field[0].number) {
            hand = "4 of a Kind";
            reward = 10;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number != cards_field[3].number && cards_field[3].number == cards_field[4].number) {
            hand = "Full House";
            reward = 8;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[0].number != cards_field[2].number && cards_field[2].number == cards_field[3].number && cards_field[2].number == cards_field[4].number) {
            hand = "Full House";
            reward = 8;
        } else if(cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
            hand = "Flush";
            reward = 7;
        } else if(cards_field[0].number <= 9 && cards_field[0].number + 1 == cards_field[1].number && cards_field[0].number + 2 == cards_field[2].number && cards_field[0].number + 3 == cards_field[3].number && cards_field[0].number + 4 == cards_field[4].number) {
            hand = "Straight";
            reward = 6;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number != cards_field[3].number && cards_field[0].number != cards_field[4].number) {
            hand = "3 of a Kind";
            reward = 4;
        } else if(cards_field[1].number == cards_field[2].number && cards_field[1].number == cards_field[3].number && cards_field[1].number != cards_field[0].number && cards_field[1].number != cards_field[4].number) {
            hand = "3 of a Kind";
            reward = 4;
        } else if(cards_field[2].number == cards_field[3].number && cards_field[2].number == cards_field[4].number && cards_field[2].number != cards_field[0].number && cards_field[2].number != cards_field[1].number) {
            hand = "3 of a Kind";
            reward = 4;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[2].number == cards_field[3].number) {
            hand = "Two Pair";
            reward = 2;
        } else if(cards_field[0].number == cards_field[1].number && cards_field[3].number == cards_field[4].number) {
            hand = "Two Pair";
            reward = 2;
        } else if(cards_field[1].number == cards_field[2].number && cards_field[3].number == cards_field[4].number) {
            hand = "Two Pair";
            reward = 2;
        } else if((cards_field[0].number >= 10 || cards_field[0].number == 0) && cards_field[0].number == cards_field[1].number) {
            hand = "Jacks or Better";
            reward = 1;
        } else if((cards_field[1].number >= 10 || cards_field[1].number == 0) && cards_field[1].number == cards_field[2].number) {
            hand = "Jacks or Better";
            reward = 1;
        } else if((cards_field[2].number >= 10 || cards_field[2].number == 0) && cards_field[2].number == cards_field[3].number) {
            hand = "Jacks or Better";
            reward = 1;
        } else if((cards_field[3].number >= 10 || cards_field[3].number == 0) && cards_field[3].number == cards_field[4].number) {
            hand = "Jacks or Better";
            reward = 1;
        }
    }

}
