package mod.casinocraft.tileentities.minigames;

import java.util.ArrayList;
import java.util.List;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Card;
import net.minecraft.util.math.BlockPos;

public class TileEntityBlackJack extends TileEntityCasino {
	
	public List<Card> cards_player1 = new ArrayList<Card>();
	public List<Card> cards_player2 = new ArrayList<Card>();
    public List<Card> cards_dealer  = new ArrayList<Card>();
    public int value_player1;
    public int value_player2;
    public int value_dealer;
	
    int split; // 0 - no split, 1 - left cards, 2 - right cards
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityBlackJack(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		cards_player1.clear();
		cards_player2.clear();
        cards_dealer.clear();
        value_player1 = 0;
        value_player2 = 0;
        value_dealer  = 0;
        split = 0;
        
        cards_player1.add(new Card(rand.nextInt(13), rand.nextInt(4)));
        cards_player1.add(new Card(rand.nextInt(13), rand.nextInt(4)));
        cards_dealer.add(new Card(rand.nextInt(13), rand.nextInt(4)));
        cards_dealer.add(new Card(rand.nextInt(13), rand.nextInt(4)));
        cards_dealer.get(1).hidden = true;
        cards_player1.get(0).setShift(-32,   0,  8);
        cards_player1.get(1).setShift(-48,   0, 32);
        cards_dealer.get(0).setShift(  0, -48,  8);
        cards_dealer.get(1).setShift(  0, -48, 32);
        value_player1 = Add_Card(cards_player1, 0, 0, true);
        value_player2 = 0;
        value_dealer = Add_Card(cards_dealer, 0, 0, true);
        if(value_player1 == 21) {
            result();
            cards_dealer.get(1).hidden = false;
        }
	}
    
    public void actionTouch(int action){
    	if(action == 0){ // Hit
    		if(split < 2){
    			value_player1 = Add_Card(cards_player1, -48, 0, false);
            	if(value_player1 > 21) {
                	if(split == 0){
                		result();
                	} else {
                		split = 2;
                	}
                }	
    		} else {
    			value_player2 = Add_Card(cards_player2, -48, 0, false);
            	if(value_player2 > 21) {
            		if(value_player1 > 21) {
            			result();
            		} else {
            			turnstate = 3;
                		cards_dealer.get(1).hidden = false;
            		}
                }
    		}
    	}
    	if(action == 1){ // Stand
    		if(split == 1){
    			split = 2;
    		} else {
    			turnstate = 3;
        		cards_dealer.get(1).hidden = false;
    		}
    	}
    	if(action == 2){ // Split
    		split = 1;
    		cards_player2.add(cards_player1.get(1));
    		cards_player1.remove(1);
    		cards_player1.add(new Card(rand.nextInt(13), rand.nextInt(4)));
            cards_player2.add(new Card(rand.nextInt(13), rand.nextInt(4)));
            cards_player2.get(0).setShift(-32,   0,  8);
            cards_player2.get(1).setShift(-48,   0, 32);
            cards_player1.get(1).setShift(-48,   0, 32);
            value_player1 = Add_Card(cards_player1, 0, 0, true);
            value_player2 = Add_Card(cards_player2, 0, 0, true);
    	}
    }
    
    public void update(){
		if(cards_player1.size() > 0) for(int i = 0; i < cards_player1.size(); i++){ cards_player1.get(i).update(); }
		if(cards_player2.size() > 0) for(int i = 0; i < cards_player2.size(); i++){ cards_player2.get(i).update(); }
		if(cards_dealer .size() > 0) for(int i = 0; i < cards_dealer .size(); i++){ cards_dealer .get(i).update(); }
		if(turnstate == 3){
			if(cards_dealer.get(cards_dealer.size() - 1).shiftY >= -16){
				if(value_dealer > 16 || (value_dealer > value_player1 && value_dealer > value_player2)){
					result();
				} else {
					value_dealer = Add_Card(cards_dealer, 0, -48, false);
				}
			}
		}
	}
	
    
    
  //--------------------GETTER--------------------
    
	public List<Card> getCardStack(int index){
		if(index == 0) return cards_player1;
		if(index == 1) return cards_player2;
		if(index == 2) return cards_dealer;
		return null;
	}
	
	public int getValue(int index){
		if(index == 0) return value_player1;
		if(index == 1) return value_player2;
		if(index == 2) return value_dealer;
		return split;
	}
	
	public boolean getFlag(int index){
		return cards_player1.get(0).number == cards_player1.get(1).number;
	}
	
	
	
	//--------------------CUSTOM--------------------
	
	private void result(){
		turnstate = 4;
		       if(value_dealer  >  21                                                         ) { hand = "The House gone bust!";  reward = 2;
	 	} else if(value_player1 >  21                                                         ) { hand = "The Player gone bust!"; reward = 0;
	 	} else if(value_player1 == value_dealer && cards_player1.size() >  cards_dealer.size()) { hand = "The House wins!";       reward = 0;
	 	} else if(value_player1 == value_dealer && cards_player1.size() == cards_dealer.size()) { hand = "DRAW";                  reward = 1;
	 	} else if(value_player1 == 21           && cards_player1.size() == 2                  ) { hand = "BLACK JACK";            reward = 3;
	 	} else if(value_player1 == value_dealer && cards_player1.size() <  cards_dealer.size()) { hand = "The Player wins!";      reward = 2;
	 	} else if(value_player1 >  value_dealer                                               ) { hand = "The Player wins!";      reward = 2;
	 	} else                                                                                  { hand = "The House wins!";       reward = 0;
	 	}
       if(split > 0){
    	               if(value_dealer  >  21                                                         ) { hand = hand + " / The House gone bust!";  reward += 2;
    		 	} else if(value_player2 >  21                                                         ) { hand = hand + " / The Player gone bust!"; reward += 0;
    		 	} else if(value_player2 == value_dealer && cards_player2.size() >  cards_dealer.size()) { hand = hand + " / The House wins!";       reward += 0;
    		 	} else if(value_player2 == value_dealer && cards_player2.size() == cards_dealer.size()) { hand = hand + " / DRAW";                  reward += 1;
    		 	} else if(value_player2 == 21           && cards_player2.size() == 2                  ) { hand = hand + " / BLACK JACK";            reward += 3;
    		 	} else if(value_player2 == value_dealer && cards_player2.size() <  cards_dealer.size()) { hand = hand + " / The Player wins!";      reward += 2;
    		 	} else if(value_player2 >  value_dealer                                               ) { hand = hand + " / The Player wins!";      reward += 2;
    		 	} else                                                                                  { hand = hand + " / The House wins!";       reward += 0;
    		 	}
       }
	}
	
    private int Add_Card(List<Card> cards, int shiftX, int shiftY, boolean noCard) {
    	int value = 0;
    	if(!noCard) cards.add(new Card(rand.nextInt(13), rand.nextInt(4), shiftX, shiftY));
        int ace = 0;
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).number == 0) {
                ace++;
            } else if(cards.get(i).number <= 9) {
            	value += cards.get(i).number + 1;
            } else {
            	value += 10;
            }
        }
        if(ace > 0) {
            while(ace > 0) {
                if(value <= 10) {
                	value += 11;
                } else {
                	value += 1;
                }
                ace--;
            }
        }
        return value;
    }
	
}