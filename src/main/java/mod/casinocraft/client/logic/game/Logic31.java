package mod.casinocraft.client.logic.game;

import mod.casinocraft.client.logic.LogicModule;
import mod.casinocraft.util.Card;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_CARD_PLACE;
import static mod.casinocraft.util.mapping.SoundMap.SOUND_CARD_SHOVE;
import static mod.lucky77.util.KeyMap.KEY_ENTER;

public class Logic31 extends LogicModule {   //  Solitaire
	
	// --------------------------------------------------
	
	public List<Card>[] cards_field   = new ArrayList[8];
	public List<Card>   cards_reserve = new ArrayList<Card>();
	public List<Card>[] cards_finish  = new ArrayList[4];
	
	public int compress = 1;
	
	public int timer = -1;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic31(int tableID) {
		super(tableID);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		cards_field[0] = new ArrayList<>();
		cards_field[1] = new ArrayList<>();
		cards_field[2] = new ArrayList<>();
		cards_field[3] = new ArrayList<>();
		cards_field[4] = new ArrayList<>();
		cards_field[5] = new ArrayList<>();
		cards_field[6] = new ArrayList<>();
		cards_field[7] = new ArrayList<>();
		
		cards_finish[0] = new ArrayList<>();
		cards_finish[1] = new ArrayList<>();
		cards_finish[2] = new ArrayList<>();
		cards_finish[3] = new ArrayList<>();
		
		List<Card> deck = shuffleDeck();
		
		int iStart = ruleSortingEven() || ruleSortingDescending() ? tableID == 1 ? 1 : 0 : 7;
		int iEnd   = ruleSortingEven() || ruleSortingAscending() ? 8 : tableID == 1 ? 2 : 1;
		int iReserve = (ruleReserveFreecell() || ruleReserveNone()) ? 0 : 7 * (ruleSuits() - 1);
		while(deck.size() > iReserve){
			for(int i = iStart; i < iEnd; i++){
				if(deck.size() > iReserve){
					cards_field[i].add(deck.get(0));
					deck.remove(0);
				}
			}
			if(ruleSortingDescending() && iEnd < 8) iEnd++;
			if(ruleSortingAscending() && iStart > (tableID == 1 ? 1 : 0)) iStart--;
		}
		
		cards_reserve.clear();
		cards_reserve.addAll(deck);
		
		selector = new Vector2(-1, -1);
		compress = 2;
		compress();
		setJingle(SOUND_CARD_SHOVE);
		
		if(ruleReserveFreecell()){
			cards_reserve.add(new Card(-1, -1));
			cards_reserve.add(new Card(-1, -1));
			cards_reserve.add(new Card(-1, -1));
			cards_reserve.add(new Card(-1, -1));
		}
		
		int z = tableID == 1 ? 32 : 0;
		for(int x = 1; x < 8; x++){
			int y = 0;
			for(Card c : cards_field[x]){
				c.setShift(-32*x + z, 48 - (48 + 24*y), y*10 + x);
				if(ruleReserveOnecard() && y < cards_field[x].size() - 1) c.hidden = true;
				y++;
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		if(timer == -1){
			if(action == -2 && ruleReserveOnecard() ) drawReserve();
			if(action == -3 && ruleReserveOnecard() ) touchStack();
			if(action == -1 && ruleReserveFreecell()) touchFreeCell(0);
			if(action == -2 && ruleReserveFreecell()) touchFreeCell(1);
			if(action == -3 && ruleReserveFreecell()) touchFreeCell(2);
			if(action == -4 && ruleReserveFreecell()) touchFreeCell(3);
			if(action == -5) touchFinish(0);
			if(action == -6) touchFinish(1);
			if(action == -7) touchFinish(2);
			if(action == -8) touchFinish(3);
			if(action == -9) activateAutoSort();
			if(action >=  0){ touchField(action%8, action/8); }
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
		if(timer > 0){
			timer--;
		}
		
		// --- auto-move Cards to finish --- //
		else if(timer == 0){
			timer = -1;
			boolean copy = false;
			
			// --- Move card to Finish Stack --- //
			for(int x1 = 0; x1 < 8; x1++){
				for(int x2 = 0; x2 < 4; x2++){
					if(!copy){
						if(cards_field[x1].size() > 0) {
							if(cards_finish[x2].size() == 0) {
								if(cards_field[x1].get(cards_field[x1].size() - 1).number == 0){
									copy = true;
								}
							} else {
								if((cards_field[x1].get(cards_field[x1].size() - 1).number - 1 == cards_finish[x2].get(cards_finish[x2].size() - 1).number) && cards_finish[x2].get(cards_finish[x2].size() - 1).suit == cards_field[x1].get(cards_field[x1].size() - 1).suit) {
									copy = true;
								}
							}
						}
						if(copy){
							cards_field[x1].get(cards_field[x1].size() - 1).setShift(0, 16, 0);
							cards_finish[x2].add(cards_field[x1].get(cards_field[x1].size() - 1));
							cards_field[x1].remove(cards_field[x1].size() - 1);
							timer = 12;
							uncover();
							scorePoint+=10;
						}
					}
				}
			}
			
			// --- Update Compression --- //
			compress();
		}
		
		// --- GameOver Condition --- //
		if(turnstate == 2){
			if(cards_finish[0].size() == 13
					&& (cards_finish[1].size() == 13 || ruleSuits() <= 1)
					&& (cards_finish[2].size() == 13 || ruleSuits() <= 2)
					&& (cards_finish[3].size() == 13 || ruleSuits() <= 3)){
				turnstate = 4;
			}
		}
	}
	
	public void updateMotion() {
		for(int x = 0; x < 8; x++){
			if(cards_field[x].size() > 0) for(Card c : cards_field[x]){
				c.update();
			}
		}
		for(int x = 0; x < 4; x++){
			if(cards_finish[ + x].size() > 0) for(Card c : cards_finish[x]){
				c.update();
			}
		}
		for(Card c: cards_reserve) {
			c.update();
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		cards_field[0] = loadCardList(compound, 0);
		cards_field[1] = loadCardList(compound, 1);
		cards_field[2] = loadCardList(compound, 2);
		cards_field[3] = loadCardList(compound, 3);
		cards_field[4] = loadCardList(compound, 4);
		cards_field[5] = loadCardList(compound, 5);
		cards_field[6] = loadCardList(compound, 6);
		cards_field[7] = loadCardList(compound, 7);
		cards_finish[0] = loadCardList(compound,  8);
		cards_finish[1] = loadCardList(compound,  9);
		cards_finish[2] = loadCardList(compound, 10);
		cards_finish[3] = loadCardList(compound, 11);
		cards_reserve.addAll(loadCardList(compound, 12));
		compress        = compound.getInt("compress");
		timer           = compound.getInt("timer");
	}
	
	public CompoundTag save2(CompoundTag compound){
		saveCardList(compound, 0, cards_field[0]);
		saveCardList(compound, 1, cards_field[1]);
		saveCardList(compound, 2, cards_field[2]);
		saveCardList(compound, 3, cards_field[3]);
		saveCardList(compound, 4, cards_field[4]);
		saveCardList(compound, 5, cards_field[5]);
		saveCardList(compound, 6, cards_field[6]);
		saveCardList(compound, 7, cards_field[7]);
		saveCardList(compound,  8, cards_finish[0]);
		saveCardList(compound,  9, cards_finish[1]);
		saveCardList(compound, 10, cards_finish[2]);
		saveCardList(compound, 11, cards_finish[3]);
		saveCardList(compound, 12, cards_reserve);
		compound.putInt("compress", compress);
		compound.putInt("timer", timer);
		return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public boolean ruleGameModeKlondike(){
		return ruleSet[0] == 0;
	}
	
	public int ruleSuits(){
		return ruleSet[1] + 1;
	}
	
	public boolean ruleSortingAscending(){
		return ruleSet[2] == 0;
	}
	
	public boolean ruleSortingDescending(){
		return ruleSet[2] == 1;
	}
	
	public boolean ruleSortingEven(){
		return ruleSet[2] == 2;
	}
	
	public boolean ruleReserveNone(){
		return ruleSet[3] == 0;
	}
	
	public boolean ruleReserveFreecell(){
		return ruleSet[3] == 1;
	}
	
	public boolean ruleReserveOnecard(){
		return ruleSet[3] == 2;
	}
	
	public boolean ruleReserveonestack(){
		return ruleSet[3] == 3;
	}
	
	public int rulePlaceOnColor(){
		return ruleSet[4];
	}
	
	private void transferCards(List<Card> cards_field2, List<Card> deck, int position, int count, int shiftX, int shiftY){
		for(int i = position; i < position + count; i++){
			deck.get(position).setShift(shiftX, shiftY, 0);
			cards_field2.add(deck.get(position));
			deck.remove(position);
		}
		setJingle(SOUND_CARD_PLACE);
	}
	
	private List<Card> shuffleDeck() {
		List<Card> stack = new ArrayList<Card>();
		List<Card> deck  = new ArrayList<Card>();
			for(int y = 1; y < ruleSuits()+1; y++) {
			for(int x = 0; x < 13; x++) {
				stack.add(new Card(x, y%4));
			}
		}
		while(stack.size() > 1) {
			int r = RANDOM.nextInt(stack.size() - 1);
			deck.add(stack.get(r));
			stack.remove(r);
		}
		deck.add(stack.get(0));
		return deck;
	}
	
	private void touchFreeCell(int cell) {
		if(selector.matches(-1, -1)) {
			if(!cards_reserve.get(cell).equals(-1, -1)) {
				selector.set(cell, -2);
			}
		} else if(selector.Y >= 0) { // Field-to-Cell
			if(cards_reserve.get(cell).suit == -1) {
				if(selector.Y == cards_field[selector.X].size() - 1) {
					cards_field[selector.X].get(cards_field[selector.X].size() - 1).setShift(0, 16, 0);
					cards_reserve.get(cell).set(cards_field[selector.X].get(cards_field[selector.X].size() - 1));
					cards_field[selector.X].remove(cards_field[selector.X].size() - 1);
					selector.set(-1, -1);
					uncover();
					scorePoint -= 1;
					if(scorePoint < 0) scorePoint = 0;
					setJingle(SOUND_CARD_PLACE);
				}
			}
		}
	}
	
	private void touchStack() {
		selector.set(cards_reserve.size() - 1, -2);
	}
	
	private void touchFinish(int slot) {
		if(!selector.matches(-1, -1)) {
			if(selector.Y == -2) { // Cell-to-Finish
				boolean copy = false;
				if(cards_finish[slot].size() == 0) {
					if(cards_reserve.get(selector.X).number == 0){
						copy = true;
					}
				} else {
					if((cards_reserve.get(selector.X).number - 1 == cards_finish[slot].get(cards_finish[slot].size() - 1).number) && cards_finish[slot].get(cards_finish[slot].size() - 1).suit == cards_reserve.get(selector.X).suit) {
						copy = true;
					}
				}
				if(copy){
					cards_reserve.get(selector.X).setShift(0, 16, 0);
					cards_finish[slot].add(cards_reserve.get(selector.X));
					cards_reserve.remove(selector.X);
					if(ruleReserveFreecell()){
						cards_reserve.add(selector.X, new Card(-1, -1));
					}
					selector.set(-1,  -1);
					uncover();
					scorePoint+=10;
					setJingle(SOUND_CARD_PLACE);
				}
			} else { // Field-to-Finish
				if(selector.Y == cards_field[selector.X].size() - 1) {
					boolean copy = false;
					if(cards_finish[slot].size() == 0) {
						if(cards_field[selector.X].get(cards_field[selector.X].size() - 1).number == 0){
							copy = true;
						}
					} else {
						if((cards_field[selector.X].get(selector.Y).number - 1 == cards_finish[slot].get(cards_finish[slot].size() - 1).number) && cards_finish[slot].get(cards_finish[slot].size() - 1).suit == cards_field[selector.X].get(selector.Y).suit) {
							copy = true;
						}
					}
					if(copy){
						cards_field[selector.X].get(cards_field[selector.X].size() - 1).setShift(0, 16, 0);
						cards_finish[slot].add(cards_field[selector.X].get(cards_field[selector.X].size() - 1));
						cards_field[selector.X].remove(cards_field[selector.X].size() - 1);
						selector.set(-1,  -1);
						uncover();
						scorePoint+=10;
						setJingle(SOUND_CARD_PLACE);
					}
				}
			}
		}
		compress();
	}
		private void touchField(int x, int y) {
		if(selector.Y == -2) {
			if(!moveStack(x)) {
				selector.set(-1,  -1);
			}
		} else
		if(cards_field[x].size() >= y - 1) {
			if(selector.matches(-1,  -1)) {
				y = cards_field[x].size() <= y ? cards_field[x].size() - 1 : y;
				for(int i = y; i < cards_field[x].size(); i++) {
					if(i != cards_field[x].size() - 1) {
						if(((cards_field[x].get(i).number - 1 != cards_field[x].get(i+1).number) && !(cards_field[x].get(i).number == 1 && cards_field[x].get(i+1).number == 13)) || !compareColors(cards_field[x].get(i).suit, cards_field[x].get(i+1).suit)) {
							return;
						}
					}
				}
				selector.set(x, y);
			} else {
				if(!moveStack(x)) {
					selector.set(-1, -1);
				}
			}
		}
		compress();
	}
		private boolean moveStack(int x) {
		int y = cards_field[x].size() - 1;
		if(selector.Y != -2) { // Field-to-Field
			if(cards_field[x].size() == 0 || ((cards_field[selector.X].get(selector.Y).number + 1 == cards_field[x].get(y).number) && compareColors(cards_field[x].get(y).suit, cards_field[selector.X].get(selector.Y).suit))) {
				transferCards(cards_field[x], cards_field[selector.X], selector.Y, cards_field[selector.X].size() - selector.Y, 0, 16);
				selector.set(-1, -1);
				uncover();
				return true;
			}
		} else { // Cell-to-Field
			if(ruleReserveFreecell()){
				if(cards_field[x].size() == 0 || ((cards_reserve.get(selector.X).number + 1 == cards_field[x].get(y).number) && compareColors(cards_field[x].get(y).suit, cards_reserve.get(selector.X).suit))) {
					cards_reserve.get(selector.X).setShift(0, 16, 0);
					cards_field[x].add(new Card(cards_reserve.get(selector.X)));
					cards_reserve.get(selector.X).set(-1, -1);
					selector.set(-1, -1);
					return true;
				}
			}
			if(ruleReserveOnecard()){
				if(cards_field[x].size() == 0 || ((cards_reserve.get(selector.X).number + 1 == cards_field[x].get(y).number) && compareColors(cards_field[x].get(y).suit, cards_reserve.get(selector.X).suit))) {
					cards_reserve.get(selector.X).setShift(0, 16, 0);
					cards_field[x].add(new Card(cards_reserve.get(selector.X)));
					cards_reserve.remove(selector.X);
					selector.set(-1, -1);
					return true;
				}
			}
		}
		return false;
	}
	
	private void compress() {
		int i = 0;
		for(int x = 0; x < 8; x++) {
			if(cards_field[x].size() > i) i = cards_field[x].size();
		}
		if(i > 6) {
			compress = i-3;
		} else {
			compress = 0;
		}
	}
	
	private void uncover(){
		for(int x = 0; x < 8; x++){
			if(cards_field[x].size() > 0)
				cards_field[x].get(cards_field[x].size() - 1).hidden = false;
		}
	}
	
	private void drawReserve() {
		if(cards_reserve.size() > 0) {
			cards_reserve.get(0).setShift(-32, 0, 0);
			cards_reserve.add(cards_reserve.get(0));
			cards_reserve.remove(0);
			scorePoint -= 1;
			if(scorePoint < 0) scorePoint = 0;
			setJingle(SOUND_CARD_SHOVE);
		}
	}
	
	private boolean compareColors(float a, float b) {
		if(rulePlaceOnColor() == 0){
			return true;
		} else if(rulePlaceOnColor() == 1){
			if( (a <= 1 && b <= 1) || (a >= 2 && b >= 2) ) return true;
		} else if(rulePlaceOnColor() == 2){
			if( (a == 0 || a == 1) && (b == 2 || b == 3) ) return true;
			if( (a == 2 || a == 3) && (b == 0 || b == 1) ) return true;
		}
		return false;
	}
	
	private void activateAutoSort(){
		timer = 0;
		selector.set(-1, -1);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 31;
	}
	
	public String getName(){
		return "klondike";
	}
	
	
	
}