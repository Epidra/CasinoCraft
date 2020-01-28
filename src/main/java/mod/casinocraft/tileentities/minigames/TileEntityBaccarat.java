package mod.casinocraft.tileentities.minigames;

import java.util.ArrayList;
import java.util.List;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Card;
import net.minecraft.util.math.BlockPos;

public class TileEntityBaccarat extends TileEntityCasino {
	
	public List<Card> cards_player = new ArrayList<Card>();
	public List<Card> cards_dealer = new ArrayList<Card>();
	public int value_player;
	public int value_dealer;
	public int status;
	
	
	
	//--------------------CONSTRUCTOR--------------------
	
	public TileEntityBaccarat(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		cards_player.clear();
        cards_dealer.clear();
        value_player = 0;
        value_dealer = 0;
        status = 0;
        
        cards_player.add(new Card(rand.nextInt(13), rand.nextInt(4)));
        cards_player.add(new Card(rand.nextInt(13), rand.nextInt(4)));
        cards_dealer.add(new Card(rand.nextInt(13), rand.nextInt(4)));
        cards_dealer.add(new Card(rand.nextInt(13), rand.nextInt(4)));
        cards_player.get(0).setShift(-32,   0,  8);
        cards_player.get(1).setShift(-48,   0, 32);
        cards_dealer.get(0).setShift(  0, -48,  8);
        cards_dealer.get(1).setShift(  0, -48, 32);
        value_player = 0;
        for(int i = 0; i < cards_player.size(); i++){
            if(cards_player.get(i).number == 0) {
                value_player += 1;
            } else if(cards_player.get(i).number <= 9) {
                value_player = value_player + cards_player.get(i).number + 1;
            }
        }
        value_dealer = 0;
        for(int i = 0; i < cards_dealer.size(); i++){
            if(cards_dealer.get(i).number == 0) {
                value_dealer += 1;
            } else if(cards_dealer.get(i).number <= 9) {
                value_dealer = value_dealer + cards_dealer.get(i).number + 1;
            }
        }
        while(value_player >= 10) { value_player -= 10; }
        while(value_dealer >= 10) { value_dealer -= 10; }
        if(value_player >= 8 || value_dealer >= 8) {
            status = 1;
            Result();
        } else {
            status = 2;
        }
	}
    
    public void actionTouch(int action){
    	if(action == 0) Add_Card(true);  // HIT
    	if(action == 1) Add_Card(false); // STAND
    }
	
	public void update(){
		if(cards_player.size() > 0) for(int i = 0; i < cards_player.size(); i++){
			cards_player.get(i).update();
		}
		if(cards_dealer.size() > 0) for(int i = 0; i < cards_dealer.size(); i++){
			cards_dealer.get(i).update();
		}
	}
    
	
	
	//--------------------GETTER--------------------
	
	public List<Card> getCardStack(int index){
		if(index == 0) return cards_player;
		if(index == 1) return cards_dealer;
		return null;
	}
	
	public int getValue(int index){
		if(index == 0) return value_player;
		if(index == 1) return value_dealer;
		if(index == 2) return status;
		return 0;
	}
	
	
	
	//--------------------CUSTOM--------------------
	
    private void Add_Card(boolean player) {
        if(player) {
            value_player = 0;
            cards_player.add(new Card(rand.nextInt(13), rand.nextInt(4), -48, 0));
            for(int i = 0; i < cards_player.size(); i++){
                if(cards_player.get(i).number == 0) {
                    value_player += 1;
                } else if(cards_player.get(i).number <= 9) {
                    value_player = value_player + cards_player.get(i).number + 1;
                }
            }
            while(value_player >= 10) { value_player -= 10; }
        }

        boolean temp_draw = false;

        if(cards_player.size() == 2 || value_dealer <= 3) { temp_draw = true; } else if(value_dealer == 4 && value_player <= 7) { temp_draw = true; } else if(value_dealer == 5 && value_player >= 4 && value_player <= 7) { temp_draw = true; } else if(value_dealer == 6 && value_player >= 6 && value_player <= 7) { temp_draw = true; }

        if(temp_draw) {
            cards_dealer.add(new Card(rand.nextInt(13), rand.nextInt(4), 0, -48));
            value_dealer = 0;
            for(int i = 0; i < cards_dealer.size(); i++){
                if(cards_dealer.get(i).number == 0) {
                    value_dealer += 1;
                } else if(cards_dealer.get(i).number <= 9) {
                    value_dealer = value_dealer + cards_dealer.get(i).number + 1;
                }
            }
            while(value_dealer >= 10) { value_dealer -= 10; }
        }
        Result();
    }

    private void Result() {
        turnstate = 4;
        if(status == 2) status = 3;
        if(value_dealer <  value_player){ hand = "The Player Wins!"; reward = 2; }
        if(value_dealer >  value_player){ hand = "The House Wins!";  reward = 0; }
        if(value_dealer == value_player){ hand = "DRAW!";            reward = 1; }
    }
	
}
