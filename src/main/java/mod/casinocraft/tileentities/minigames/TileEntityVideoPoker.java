package mod.casinocraft.tileentities.minigames;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Card;
import net.minecraft.util.math.BlockPos;

public class TileEntityVideoPoker extends TileEntityCasino {
	
	public boolean end;
    public Card[] card = new Card[5];
    public boolean[] hold = new boolean[5];
    private int ticker;
    private int movestate;
	
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityVideoPoker(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		for(int i = 0; i < 5; i++){
			card[i] = new Card(rand.nextInt(13), rand.nextInt(4), 0, 20 + 5*i);
			hold[i] = false;
		}
        end = false;
        ticker = 0;
        movestate = 0;
	}
	
    public void actionTouch(int action){
    	if(action == 5 && turnstate == 2){
    		turnstate = 3;
    		movestate = 1;
    		for(int i = 0; i < 5; i++){
    			if(!hold[i]) card[i].hidden = true;
    		}
    	} else {
    		hold[action] = !hold[action];
    	}
    }
	
    public void update(){
    	if(turnstate == 2) {
    		for(int i = 0; i < 5; i++){
    			card[i].update();
    		}
    	}
    	switch(movestate){
    	case 0: // NULL
    		break;
    	case 1: // Cards Move up
    		ticker++;
    		for(int i = 0; i < 5; i++){
    			if(!hold[i]) card[i].shiftY--;
    		}
    		if(ticker >= 30){
    			if(!hold[0]){ card[0].number = rand.nextInt(13); card[0].suit = rand.nextInt(4); card[0].hidden = false; }
                if(!hold[1]){ card[1].number = rand.nextInt(13); card[1].suit = rand.nextInt(4); card[1].hidden = false; }
                if(!hold[2]){ card[2].number = rand.nextInt(13); card[2].suit = rand.nextInt(4); card[2].hidden = false; }
                if(!hold[3]){ card[3].number = rand.nextInt(13); card[3].suit = rand.nextInt(4); card[3].hidden = false; }
                if(!hold[4]){ card[4].number = rand.nextInt(13); card[4].suit = rand.nextInt(4); card[4].hidden = false; }
                movestate = 2;
    		}
    		break;
    	case 2: // Cards Move down
    		ticker--;
    		for(int i = 0; i < 5; i++){
    			if(!hold[i]) card[i].shiftY++;
    		}
    		if(ticker == 0){
    			movestate = 3;
    		}
    		break;
    	case 3: // Cards Move Together
    		ticker++;
    		card[0].shiftX+=2;
    		card[1].shiftX+=1;
    		card[3].shiftX-=1;
    		card[4].shiftX-=2;
    		if(ticker == 48){
    			Sort();
    			card[0].shiftX =  48*2;
        		card[1].shiftX =  48;
        		card[2].shiftX =   0;
        		card[3].shiftX = -48;
        		card[4].shiftX = -48*2;
    			movestate = 4;
    		}
    		break;
    	case 4: // Cards Move apart
    		ticker--;
    		card[0].shiftX-=2;
    		card[1].shiftX-=1;
    		card[3].shiftX+=1;
    		card[4].shiftX+=2;
    		if(ticker == 0){
    			Result();
    			turnstate = 4;
    			movestate = 0;
    		}
    		break;
    	}
    }
    
    
    
  //--------------------GETTER--------------------
    
	public Card getCard(int index){
		return card[index];
	}
	
	public boolean getFlag(int index){
		return hold[index];
	}
	
	public String getString(int index){
		return hand;
	}
	
	
	
	//--------------------CUSTOM--------------------
	
	public void Click_Hold(int i) {
        hold[i] = !hold[i];
	}
    
    private void Sort() {
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
    }

    private void Result() {
    	if(card[1-1].number == card[2-1].number && card[1-1].number == card[3-1].number && card[1-1].number == card[4-1].number && card[1-1].number == card[5-1].number) {
            hand = "5 of a Kind";
            reward = 20;
        } else if(card[1-1].number == 9 && card[2-1].number == 10 && card[3-1].number == 11 && card[4-1].number == 12 && card[5-1].number == 0 && card[1-1].suit == card[2-1].suit && card[1-1].suit == card[3-1].suit && card[1-1].suit == card[4-1].suit && card[1-1].suit == card[5-1].suit) {
            hand = "ROYAL FLUSH!!";
            reward = 16;
        } else if(card[1-1].number <= 9 && card[1-1].number + 1 == card[2-1].number && card[1-1].number + 2 == card[3-1].number && card[1-1].number + 3 == card[4-1].number && card[1-1].number + 4 == card[5-1].number && card[1-1].suit == card[2-1].suit && card[1-1].suit == card[3-1].suit && card[1-1].suit == card[4-1].suit && card[1-1].suit == card[5-1].suit) {
            hand = "Straight Flush";
            reward = 12;
        } else if(card[1-1].number == card[2-1].number && card[1-1].number == card[3-1].number && card[1-1].number == card[4-1].number && card[1-1].number != card[5-1].number) {
            hand = "4 of a Kind";
            reward = 10;
        } else if(card[2-1].number == card[3-1].number && card[2-1].number == card[4-1].number && card[2-1].number == card[5-1].number && card[2-1].number != card[1-1].number) {
            hand = "4 of a Kind";
            reward = 10;
        } else if(card[1-1].number == card[2-1].number && card[1-1].number == card[3-1].number && card[1-1].number != card[4-1].number && card[4-1].number == card[5-1].number) {
            hand = "Full House";
            reward = 8;
        } else if(card[1-1].number == card[2-1].number && card[1-1].number != card[3-1].number && card[3-1].number == card[4-1].number && card[3-1].number == card[5-1].number) {
            hand = "Full House";
            reward = 8;
        } else if(card[1-1].suit == card[2-1].suit && card[1-1].suit == card[3-1].suit && card[1-1].suit == card[4-1].suit && card[1-1].suit == card[5-1].suit) {
            hand = "Flush";
            reward = 7;
        } else if(card[1-1].number <= 9 && card[1-1].number + 1 == card[2-1].number && card[1-1].number + 2 == card[3-1].number && card[1-1].number + 3 == card[4-1].number && card[1-1].number + 4 == card[5-1].number) {
            hand = "Straight";
            reward = 6;
        } else if(card[1-1].number == card[2-1].number && card[1-1].number == card[3-1].number && card[1-1].number != card[4-1].number && card[1-1].number != card[5-1].number) {
            hand = "3 of a Kind";
            reward = 4;
        } else if(card[2-1].number == card[3-1].number && card[2-1].number == card[4-1].number && card[2-1].number != card[1-1].number && card[2-1].number != card[5-1].number) {
            hand = "3 of a Kind";
            reward = 4;
        } else if(card[3-1].number == card[4-1].number && card[3-1].number == card[5-1].number && card[3-1].number != card[1-1].number && card[3-1].number != card[2-1].number) {
            hand = "3 of a Kind";
            reward = 4;
        } else if(card[1-1].number == card[2-1].number && card[3-1].number == card[4-1].number) {
            hand = "Two Pair";
            reward = 2;
        } else if(card[1-1].number == card[2-1].number && card[4-1].number == card[5-1].number) {
            hand = "Two Pair";
            reward = 2;
        } else if(card[2-1].number == card[3-1].number && card[4-1].number == card[5-1].number) {
            hand = "Two Pair";
            reward = 2;
        } else if((card[1-1].number >= 10 || card[1-1].number == 0) && card[1-1].number == card[2-1].number) {
            hand = "Jacks or Better";
            reward = 1;
        } else if((card[2-1].number >= 10 || card[2-1].number == 0) && card[2-1].number == card[3-1].number) {
            hand = "Jacks or Better";
            reward = 1;
        } else if((card[3-1].number >= 10 || card[3-1].number == 0) && card[3-1].number == card[4-1].number) {
            hand = "Jacks or Better";
            reward = 1;
        } else if((card[4-1].number >= 10 || card[4-1].number == 0) && card[4-1].number == card[5-1].number) {
            hand = "Jacks or Better";
            reward = 1;
        } else {

        }
        end = true;
    }
	
}
