package mod.casinocraft.util;

public class Card {
	
	/** ??? */
	public int number;
	/** 0 - Herz, 3 - Kreuz */
	public int suit;
	/** ??? */
	public int shiftX;
	/** ??? */
	public int shiftY;
	/** ??? */
	public int deathtimer;
	/** ??? */
	public boolean dead;
	
	public int idletimer;
	public boolean hidden;
	
	/** ??? */
	public Card(int number, int suit){
		this.number = number;
		this.suit = suit;
		shiftX = 0;
		shiftY = 0;
		deathtimer = 0;
		dead = false;
	}
	
	/** ??? */
	public Card(int number, int suit, int shiftX, int shiftY){
		this.number = number;
		this.suit = suit;
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		deathtimer = 0;
		idletimer = 0;
		dead = false;
		hidden = false;
	}
	
	/** ??? */
	public Card(int number, int suit, int shiftX, int shiftY, int idletimer, boolean hidden){
		this.number = number;
		this.suit = suit;
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		deathtimer = 0;
		this.idletimer = idletimer;
		dead = false;
		this.hidden = hidden;
	}
	
	public Card(Card card) {
		this.number = card.number;
		this.suit = card.suit;
		this.shiftX = card.shiftX;
		this.shiftY = card.shiftY;
		deathtimer = card.deathtimer;
		this.idletimer = card.idletimer;
		dead = card.dead;
		this.hidden = card.hidden;
	}

	public void setShift(int shiftX, int shiftY, int idletimer){
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.idletimer = idletimer;
	}
	
	/** ??? */
	public void update(){
		if(idletimer > 0){
			idletimer--;
		} else {
			if(shiftX < 0) shiftX++;
			if(shiftX > 0) shiftX--;
			if(shiftY < 0) shiftY++;
			if(shiftY > 0) shiftY--;
			if(dead) deathtimer++;
		}
	}

	public int SortedNumber() {
		return number;
	}

	public boolean equals(int x, int y) {
        return number == x && suit == y;
    }

	public void set(int x, int y) {
		number = x;
		suit = y;
		shiftX = 0;
		shiftY = 0;
		idletimer = 0;
	}
	
}
