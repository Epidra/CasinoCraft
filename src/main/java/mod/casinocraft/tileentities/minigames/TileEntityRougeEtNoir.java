package mod.casinocraft.tileentities.minigames;

import java.util.ArrayList;
import java.util.List;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Card;
import net.minecraft.util.math.BlockPos;

public class TileEntityRougeEtNoir extends TileEntityCasino {
	
	List<Card> cards_rouge = new ArrayList<Card>();
    List<Card> cards_noir  = new ArrayList<Card>();

    int value_rouge;
    int value_noir;
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityRougeEtNoir(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		Set_Up();
	}
    
	public void Set_Up() {
		selector.set(-1, -1);
        cards_rouge.clear();
        cards_noir.clear();
        value_rouge = 0;
        value_noir = 0;
    }
	
    public void actionTouch(int action){
    	selector.X = action;
    	turnstate = 3;
    	cards_rouge.add(new Card(rand.nextInt(13), rand.nextInt(4), 0, -50));
    }
    
    public void update(){
        if(turnstate == 3) {
            if(value_rouge < 31) {
                if(cards_rouge.get(cards_rouge.size() - 1).shiftY >= -10) {
                    value_rouge += (cards_rouge.get(cards_rouge.size() - 1).number >= 9 ? 10 : (cards_rouge.get(cards_rouge.size() - 1).number + 1));
                    if(value_rouge >= 31) {
                        cards_noir.add(new Card(rand.nextInt(13), rand.nextInt(4), 0, -50));
                    } else {
                        cards_rouge.add(new Card(rand.nextInt(13), rand.nextInt(4), 0, -50));
                    }
                }
            } else {
                if(cards_noir.get(cards_noir.size() - 1).shiftY >= -10) {
                    value_noir += (cards_noir.get(cards_noir.size() - 1).number >= 9 ? 10 : (cards_noir.get(cards_noir.size() - 1).number + 1));
                    if(value_noir >= 31) {
                        result();
                    } else {
                        cards_noir.add(new Card(rand.nextInt(13), rand.nextInt(4), 0, -50));
                    }
                }
            }
        }
        for(Card c : cards_rouge) { c.update(); }
        for(Card c : cards_noir ) { c.update(); }
	}
	
    
    
  //--------------------GETTER--------------------
    
    public int getValue(int index){
		if(index == 0) return value_rouge;
		if(index == 1) return value_noir;
		return selector.X;
	}
    
    public List<Card> getCardStack(int index){
		if(index == 0) return cards_rouge;
		return cards_noir;
	}
    
    
    
  //--------------------CUSTOM--------------------
    
    public void result(){
    	turnstate = 4;
    	if(value_rouge <  value_noir) { hand = "Rouge Wins!"; if(selector.X == 0) { reward = 2; } }
        if(value_rouge >  value_noir) { hand = "Noir Wins!";  if(selector.X == 1) { reward = 2; } }
        if(value_rouge == value_noir) { hand = "Tie!";                              reward = 1; }
    }
    
}