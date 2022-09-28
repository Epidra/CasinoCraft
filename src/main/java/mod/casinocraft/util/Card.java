package mod.casinocraft.util;

import java.util.Random;

public class Card {

    public int number;
    public int suit;
    public int shiftX;
    public int shiftY;
    public int deathtimer;
    public boolean dead;
    public int idletimer;
    public boolean hidden;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public Card(Random random){
        this(random.nextInt(13), random.nextInt(4), 0, 0, 0, false);
    }

    public Card(int number, int suit){
        this(number, suit, 0, 0, 0, false);
    }

    public Card(int number, int suit, boolean hidden){
        this(number, suit, 0, 0, 0, hidden);
    }

    public Card(Random random, int shiftX, int shiftY){
        this(random.nextInt(13), random.nextInt(4), shiftX, shiftY, 0, false);
    }

    public Card(int number, int suit, int shiftX, int shiftY){
        this(number, suit, shiftX, shiftY, 0, false);
    }

    public Card(Random random, int shiftX, int shiftY, int idletimer, boolean hidden){
        this(random.nextInt(13), random.nextInt(4), shiftX, shiftY, idletimer, false);
    }

    public Card(int number, int suit, int shiftX, int shiftY, int idletimer, boolean hidden){
        this.number = number;
        this.suit   = suit;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        deathtimer     = 0;
        this.idletimer = idletimer;
        dead           = false;
        this.hidden    = hidden;
    }

    public Card(Card card) {
        this.number = card.number;
        this.suit   = card.suit;
        this.shiftX = card.shiftX;
        this.shiftY = card.shiftY;
        deathtimer     = card.deathtimer;
        this.idletimer = card.idletimer;
        dead           = card.dead;
        this.hidden    = card.hidden;
    }





    //----------------------------------------UPDATE----------------------------------------//

    /** Update Function, RETURN value tells if the card is appering NOW on screen **/
    public boolean update(){
        if(idletimer > 0){
            idletimer-=2;
            return idletimer >= 0;
        } else {
            if(shiftX < 0) shiftX+=2;
            if(shiftX > 0) shiftX-=2;
            if(shiftY < 0) shiftY+=2;
            if(shiftY > 0) shiftY-=2;
            if(dead) deathtimer+=2;
        } return false;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public void setShift(int shiftX, int shiftY, int idletimer){
        this.shiftX    = shiftX;
        this.shiftY    = shiftY;
        this.idletimer = idletimer;
    }

    public int sortedNumber() {
        return number == 0 ? 13 : number;
    }

    public void set(int x, int y) {
        number = x;
        suit   = y;
        shiftX = 0;
        shiftY = 0;
        idletimer = 0;
    }

    public void set(Card c) {
        number = c.number;
        suit   = c.suit;
        shiftX = c.shiftX;
        shiftY = c.shiftY;
        idletimer = c.idletimer;
        hidden    = c.hidden;
    }

    public boolean equals(int x, int y) {
        return number == x && suit == y;
    }



}
