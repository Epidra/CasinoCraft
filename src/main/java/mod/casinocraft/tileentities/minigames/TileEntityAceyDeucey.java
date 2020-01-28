package mod.casinocraft.tileentities.minigames;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Card;
import net.minecraft.util.math.BlockPos;

public class TileEntityAceyDeucey extends TileEntityCasino {
	
	Card[] cards = new Card[3];

    int spread;
    
    boolean doublebet;
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityAceyDeucey(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		spread = -1;
        Set_Up();
        hand = "";
	}
    
	public void Set_Up() {
        cards[0] = new Card(rand.nextInt(13), rand.nextInt(4), 0, -25);
        cards[1] = new Card(rand.nextInt(13), rand.nextInt(4), 0, -50);
        cards[2] = null;
        spread = -1;
        turnstate = 3;
        doublebet = false;
    }
	
    public void actionTouch(int action){
    	doublebet = action == 0;
    	cards[2] = new Card(rand.nextInt(13), rand.nextInt(4), 0, -50);
    	turnstate = 3;
    }
    
    public void update(){
		if(turnstate == 2) {
        	
        }
        if(turnstate == 3) {
            if(cards[0].number == cards[1].number) {
                if(cards[2] == null) {
                    if(cards[0].shiftY == 0) {
                        cards[2] = new Card(rand.nextInt(13), rand.nextInt(4), 0, -75);
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
                if(cards[2] == null) {
                    if(cards[1].shiftY == 0) {
                        spread = cards[0].SortedNumber() - cards[1].SortedNumber();
                        if(spread < 0) spread *= -1;
                        spread -= 1;
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
        for(int i = 0; i < 3; i++) {
            if(cards[i] != null) cards[i].update();
        }
	}
    
    
    
  //--------------------GETTER--------------------
    
    public Card getCard(int index){
    	return cards[index];
	}
    
    public int getValue(int index){
		if(index == 0) return spread;
		if(index == 1) return reward;
    	return 0;
	}
	
	public boolean getFlag(int index){
		return false;
	}
	
	public String getString(int index){
		return hand;
	}
    
	
	
	//--------------------CUSTOM--------------------
	
	private void result(){
		if(spread == 1) reward = doublebet ? 12 : 6; // 1:5
        if(spread == 2) reward = doublebet ? 10 : 5; // 1:4
        if(spread == 3) reward = doublebet ?  6 : 3; // 1:2
        if(spread >= 4) reward = doublebet ?  4 : 2; // 1:1
    }
	
}