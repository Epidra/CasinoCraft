package mod.casinocraft.client.logic.game;

import mod.casinocraft.client.logic.LogicModule;
import mod.casinocraft.util.Card;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_CARD_PLACE;
import static mod.casinocraft.util.mapping.SoundMap.SOUND_CARD_SHOVE;

public class Logic32 extends LogicModule {   //  Pyramid
	
	// --------------------------------------------------
	
	public Card[] cards_field       = new Card[70];
	public List<Card> cards_stack   = new ArrayList<Card>();
	public List<Card> cards_reserve = new ArrayList<Card>();
	
	private int combo = 0;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic32(int tableID) {
		super(tableID);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		scoreLives = ruleLives();
		restart();
	}
	
	public void restart(){
		selector.set(-1, -1);
		combo = 0;
		
		List<Card> deck = shuffleDeck();
		
		for(int i = 0; i < cards_field.length; i++){
			cards_field[i] = new Card(-1, -1);
		}
		
		int i = 0;
		if(rulePyramids() == 1){
			cards_field[ 5     ] = deck.get(i++);
			
			cards_field[ 4 + 10] = deck.get(i++);
			cards_field[ 5 + 10] = deck.get(i++);
			
			cards_field[ 4 + 20] = deck.get(i++);
			cards_field[ 5 + 20] = deck.get(i++);
			cards_field[ 6 + 20] = deck.get(i++);
			
			cards_field[ 3 + 30] = deck.get(i++);
			cards_field[ 4 + 30] = deck.get(i++);
			cards_field[ 5 + 30] = deck.get(i++);
			cards_field[ 6 + 30] = deck.get(i++);
			
			cards_field[ 3 + 40] = deck.get(i++);
			cards_field[ 4 + 40] = deck.get(i++);
			cards_field[ 5 + 40] = deck.get(i++);
			cards_field[ 6 + 40] = deck.get(i++);
			cards_field[ 7 + 40] = deck.get(i++);
			
			cards_field[ 2 + 50] = deck.get(i++);
			cards_field[ 3 + 50] = deck.get(i++);
			cards_field[ 4 + 50] = deck.get(i++);
			cards_field[ 5 + 50] = deck.get(i++);
			cards_field[ 6 + 50] = deck.get(i++);
			cards_field[ 7 + 50] = deck.get(i++);
			
			cards_field[ 2 + 60] = deck.get(i++);
			cards_field[ 3 + 60] = deck.get(i++);
			cards_field[ 4 + 60] = deck.get(i++);
			cards_field[ 5 + 60] = deck.get(i++);
			cards_field[ 6 + 60] = deck.get(i++);
			cards_field[ 7 + 60] = deck.get(i++);
			cards_field[ 8 + 60] = deck.get(i++);
		}
		
		if(rulePyramids() == 2 && tableID == 1){
			cards_field[ 3     ] = deck.get(i++);
			cards_field[ 7     ] = deck.get(i++);
			
			cards_field[ 2 + 10] = deck.get(i++);
			cards_field[ 3 + 10] = deck.get(i++);
			cards_field[ 6 + 10] = deck.get(i++);
			cards_field[ 7 + 10] = deck.get(i++);
			
			cards_field[ 2 + 20] = deck.get(i++);
			cards_field[ 3 + 20] = deck.get(i++);
			cards_field[ 4 + 20] = deck.get(i++);
			cards_field[ 6 + 20] = deck.get(i++);
			cards_field[ 7 + 20] = deck.get(i++);
			cards_field[ 8 + 20] = deck.get(i++);
			
			cards_field[ 2 + 30] = deck.get(i++);
			cards_field[ 3 + 30] = deck.get(i++);
			cards_field[ 4 + 30] = deck.get(i++);
			cards_field[ 5 + 30] = deck.get(i++);
			cards_field[ 6 + 30] = deck.get(i++);
			cards_field[ 7 + 30] = deck.get(i++);
			
			cards_field[ 2 + 40] = deck.get(i++);
			cards_field[ 3 + 40] = deck.get(i++);
			cards_field[ 4 + 40] = deck.get(i++);
			cards_field[ 6 + 40] = deck.get(i++);
			cards_field[ 7 + 40] = deck.get(i++);
			cards_field[ 8 + 40] = deck.get(i++);
			
			cards_field[ 2 + 50] = deck.get(i++);
			cards_field[ 3 + 50] = deck.get(i++);
			cards_field[ 6 + 50] = deck.get(i++);
			cards_field[ 7 + 50] = deck.get(i++);
		}
		
		if(rulePyramids() == 2 && tableID == 2){
			cards_field[ 3     ] = deck.get(i++);
			cards_field[ 7     ] = deck.get(i++);
			
			cards_field[ 2 + 10] = deck.get(i++);
			cards_field[ 3 + 10] = deck.get(i++);
			cards_field[ 6 + 10] = deck.get(i++);
			cards_field[ 7 + 10] = deck.get(i++);
			
			cards_field[ 2 + 20] = deck.get(i++);
			cards_field[ 3 + 20] = deck.get(i++);
			cards_field[ 4 + 20] = deck.get(i++);
			cards_field[ 6 + 20] = deck.get(i++);
			cards_field[ 7 + 20] = deck.get(i++);
			cards_field[ 8 + 20] = deck.get(i++);
			
			cards_field[ 1 + 30] = deck.get(i++);
			cards_field[ 2 + 30] = deck.get(i++);
			cards_field[ 3 + 30] = deck.get(i++);
			cards_field[ 4 + 30] = deck.get(i++);
			cards_field[ 5 + 30] = deck.get(i++);
			cards_field[ 6 + 30] = deck.get(i++);
			cards_field[ 7 + 30] = deck.get(i++);
			cards_field[ 8 + 30] = deck.get(i++);
			
			cards_field[ 1 + 40] = deck.get(i++);
			cards_field[ 2 + 40] = deck.get(i++);
			cards_field[ 3 + 40] = deck.get(i++);
			cards_field[ 4 + 40] = deck.get(i++);
			cards_field[ 6 + 40] = deck.get(i++);
			cards_field[ 7 + 40] = deck.get(i++);
			cards_field[ 8 + 40] = deck.get(i++);
			cards_field[ 9 + 40] = deck.get(i++);
		}
		
		if(rulePyramids() == 3 && tableID == 2){
			cards_field[ 2     ] = deck.get(i++);
			cards_field[ 5     ] = deck.get(i++);
			cards_field[ 8     ] = deck.get(i++);
			
			cards_field[ 1 + 10] = deck.get(i++);
			cards_field[ 2 + 10] = deck.get(i++);
			cards_field[ 4 + 10] = deck.get(i++);
			cards_field[ 5 + 10] = deck.get(i++);
			cards_field[ 7 + 10] = deck.get(i++);
			cards_field[ 8 + 10] = deck.get(i++);
			
			cards_field[ 1 + 20] = deck.get(i++);
			cards_field[ 2 + 20] = deck.get(i++);
			cards_field[ 3 + 20] = deck.get(i++);
			cards_field[ 4 + 20] = deck.get(i++);
			cards_field[ 5 + 20] = deck.get(i++);
			cards_field[ 6 + 20] = deck.get(i++);
			cards_field[ 7 + 20] = deck.get(i++);
			cards_field[ 8 + 20] = deck.get(i++);
			cards_field[ 9 + 20] = deck.get(i++);
			
			cards_field[ 0 + 30] = deck.get(i++);
			cards_field[ 1 + 30] = deck.get(i++);
			cards_field[ 2 + 30] = deck.get(i++);
			cards_field[ 3 + 30] = deck.get(i++);
			cards_field[ 4 + 30] = deck.get(i++);
			cards_field[ 5 + 30] = deck.get(i++);
			cards_field[ 6 + 30] = deck.get(i++);
			cards_field[ 7 + 30] = deck.get(i++);
			cards_field[ 8 + 30] = deck.get(i++);
			cards_field[ 9 + 30] = deck.get(i++);
		}
		
		if(rulePyramids() == 3 && tableID == 1){
			cards_field[ 3     ] = deck.get(i++);
			cards_field[ 5     ] = deck.get(i++);
			cards_field[ 7     ] = deck.get(i++);
			
			cards_field[ 2 + 10] = deck.get(i++);
			cards_field[ 3 + 10] = deck.get(i++);
			cards_field[ 4 + 10] = deck.get(i++);
			cards_field[ 5 + 10] = deck.get(i++);
			cards_field[ 6 + 10] = deck.get(i++);
			cards_field[ 7 + 10] = deck.get(i++);
			
			
			cards_field[ 2 + 20] = deck.get(i++);
			cards_field[ 3 + 20] = deck.get(i++);
			cards_field[ 4 + 20] = deck.get(i++);
			cards_field[ 5 + 20] = deck.get(i++);
			cards_field[ 6 + 20] = deck.get(i++);
			cards_field[ 7 + 20] = deck.get(i++);
			cards_field[ 8 + 20] = deck.get(i++);
			
			cards_field[ 2 + 30] = deck.get(i++);
			cards_field[ 3 + 30] = deck.get(i++);
			cards_field[ 4 + 30] = deck.get(i++);
			cards_field[ 5 + 30] = deck.get(i++);
			cards_field[ 6 + 30] = deck.get(i++);
			cards_field[ 7 + 30] = deck.get(i++);
			
			cards_field[ 3 + 40] = deck.get(i++);
			cards_field[ 4 + 40] = deck.get(i++);
			cards_field[ 6 + 40] = deck.get(i++);
			cards_field[ 7 + 40] = deck.get(i++);
			
			cards_field[ 3 + 50] = deck.get(i++);
			cards_field[ 6 + 50] = deck.get(i++);
		}
		
		deck.subList(0, 28).clear();
		
		cards_reserve.addAll(deck);
		cards_stack.clear();
		
		setJingle(SOUND_CARD_SHOVE);
		
		drawReserve();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		     if(action == -1) compareCards(100);
		else if(action == -2) compareCards(200);
		else if(action == -3) drawReserve();
		else touchField(action % 10, action / 10);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
		if(turnstate >= 2){
			if(cards_stack.size() > 0) for (Card card : cards_stack) {
				card.update();
			}
			if(cards_reserve.size() > 0) for (Card card : cards_reserve) {
				card.update();
			}
			for(int i = 0; i < cards_field.length; i++){
				cards_field[i].update();
				if(cards_field[i].deathtimer >= 48){
					cards_field[i] = new Card(-1, -1);
					checkForClearedLine(i);
				}
			}
			if(cards_stack  .size() > 0) if(cards_stack  .get(cards_stack.size() - 1).deathtimer >= 48) cards_stack  .remove(cards_stack.get(cards_stack.size() - 1));
			if(cards_reserve.size() > 0) if(cards_reserve.get(0                     ).deathtimer >= 48) cards_reserve.remove(0);
		}
	}
	
	public void updateMotion() {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		cards_reserve = loadCardList(compound, 0);
		cards_stack   = loadCardList(compound, 1);
		cards_field   = loadCardArray(compound, 2);
	}
	
	public CompoundTag save2(CompoundTag compound){
		saveCardList(compound,  0, cards_reserve);
		saveCardList(compound,  1, cards_stack);
		saveCardArray(compound, 2, cards_field);
		return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public boolean ruleGameMode(){
		return ruleSet[0] == 0;
	}
	
	public int rulePyramids(){
		return ruleSet[1] + 1;
	}
	
	public int ruleLives(){
		return ruleSet[2] + 1;
	}
	
	private List<Card> shuffleDeck() {
		List<Card> stack = new ArrayList<Card>();
		List<Card> deck  = new ArrayList<Card>();
			for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 13; x++) {
				stack.add(new Card(x, y));
			}
		}
		
		while(stack.size() > 0) {
			int r = stack.size() == 1 ? 0 : RANDOM.nextInt(stack.size() - 1);
			deck.add(stack.get(r));
			stack.remove(r);
		}
		return deck;
	}
	
	
	
	private void drawReserve() {
		selector.set(-1, -1);
		combo = 0;
		if(cards_reserve.size() > 0) {
			cards_reserve.get(0).shiftX = ruleGameMode() ? 36 : 36 + 8;
			cards_stack.add(cards_reserve.get(0));
			cards_reserve.remove(0);
			setJingle(SOUND_CARD_SHOVE);
		} else {
			cards_reserve.addAll(cards_stack);
			cards_stack.clear();
			if(scoreLives == 0){
				turnstate = 4;
			} else {
				scoreLives--;
			}
			setJingle(SOUND_CARD_SHOVE);
		}
	}
	
	private void touchField(int x, int y) {
		if(cards_field[x + y*10].suit != -1){
			if(y == 6){
				compareCards(x + y*10);
			} else {
				if(y % 2 == 0){
					if(cards_field[x + (y+1)*10].suit == -1 && cards_field[x - 1 + (y+1)*10].suit == -1)
						compareCards(x + y*10);
				} else {
					if(cards_field[x + (y+1)*10].suit == -1 && cards_field[x + 1 + (y+1)*10].suit == -1)
						compareCards(x + y*10);
				}
			}
		}
	}
	
	private int findNumber(int id){
		if(id == 100) if(cards_stack  .size() > 0) return cards_stack  .get(cards_stack.size() - 1).number; else return -1;
		if(id == 200) if(cards_reserve.size() > 0) return cards_reserve.get(0                     ).number; else return -1;
		return cards_field[id].number;
	}
	
	private void setDead(int id){
		       if(id == 100){ if(cards_stack  .size() > 0) cards_stack  .get(cards_stack.size() - 1).dead = true;
		} else if(id == 200){ if(cards_reserve.size() > 0) cards_reserve.get(0                     ).dead = true;
		} else cards_field[id].dead = true;
	}
	
	private void compareCards(int id) {
		
		// Pyramid Mode
		if(ruleGameMode()){
			if(selector.X == -1) { // No Card Selected
				if(findNumber(id) == 12){
					setDead(id);
					scorePoint += 50;
					setJingle(SOUND_CARD_PLACE);
				} else {
					selector.X = id;
				}
			} else { // A Card already selected
				if(findNumber(id) + findNumber(selector.X) == 11){
					setDead(id);
					setDead(selector.X);
					selector.X = -1;
					combo++;
					scorePoint += 50 * combo;
					setJingle(SOUND_CARD_PLACE);
				} else if(findNumber(id) == 12){ // Set new Selector
					setDead(id);
					scorePoint += 50;
					setJingle(SOUND_CARD_PLACE);
				} else {
					selector.X = id;
				}
			}
		}
		
		// TriPeak Mode
		else {
			if(cards_field[id].number + 1 == cards_stack.get(cards_stack.size() - 1).number || cards_field[id].number - 1 == cards_stack.get(cards_stack.size() - 1).number || (cards_field[id].number == 0 && cards_stack.get(cards_stack.size() - 1).number == 12) || (cards_field[id].number == 12 && cards_stack.get(cards_stack.size() - 1).number == 0)) {
				combo++;
				scorePoint += 50 * combo;
				cards_field[id].shiftX = 0;
				cards_field[id].shiftY = +24;
				cards_stack.add(cards_field[id]);
				cards_field[id] = new Card(-1, -1);
				checkForClearedLine(id);
				setJingle(SOUND_CARD_PLACE);
			}
		}
	}
	
	private void checkForClearedLine(int id) {
		if(id < 10){
			boolean finished = true;
			for(int i = 0; i < 10; i++){
				if(cards_field[i].suit > -1) {
					finished = false;
					break;
				}
			}
			if(finished){
				restart();
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 32;
	}
	
	public String getName(){
		return ruleSet[1] == 0 ? "pyramid" : ruleSet[1] == 1 ? (tableID == 1 ? "twin_peak" : "twinpeak") : "tripeak";
	}
	
	
	
}