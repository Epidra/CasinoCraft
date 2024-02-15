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
	
	// GAME MODE -- PYRAMID, TRIPEAK
	// RULE 1 -- Pyramid Count (1, 2, 3)
	// RULE 2 -- Live Count (1, 2, 3, 4, 5)
	// RULE 3 -- ??
	// COLOR VARIATION -- Card Design
	
	// --------------------------------------------------
	
	public Card[] cards_field       = new Card[28];
	public List<Card> cards_stack   = new ArrayList<Card>();
	public List<Card> cards_reserve = new ArrayList<Card>();
	
	//
	// // ---
	//
	// private int combo = 0;
	// public Card[] cards_field = new Card[28]; // fixed: 28 Cards
	// public List<Card> cards_stack   = new ArrayList<Card>();
	// public List<Card> cards_reserve = new ArrayList<Card>();
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic32(int tableID) {
		super(tableID);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		scoreLives = 2;
		selector.set(-1, -1);
				List<Card> deck = shuffleDeck();
				for(int i = 0; i < 28; i++) {
			cards_field[i] = deck.get(i);
		} deck.subList(0, 28).clear();
		cards_reserve.addAll(deck);
		cards_stack.clear();
				cards_field[ 1].setShift(+16,  -20, 50);
		cards_field[ 2].setShift(-16,  -20, 50);
				cards_field[ 3].setShift( 32,  -40, 40);
		cards_field[ 4].setShift(  0,  -40, 40);
		cards_field[ 5].setShift(-32,  -40, 40);
				cards_field[ 6].setShift( 48,  -60, 30);
		cards_field[ 7].setShift( 16,  -60, 30);
		cards_field[ 8].setShift(-16,  -60, 30);
		cards_field[ 9].setShift(-48,  -60, 30);
				cards_field[10].setShift( 64,  -80, 20);
		cards_field[11].setShift( 32,  -80, 20);
		cards_field[12].setShift(  0,  -80, 20);
		cards_field[13].setShift(-32,  -80, 20);
		cards_field[14].setShift(-64,  -80, 20);
				cards_field[15].setShift( 80, -100, 10);
		cards_field[16].setShift( 48, -100, 10);
		cards_field[17].setShift( 16, -100, 10);
		cards_field[18].setShift(-16, -100, 10);
		cards_field[19].setShift(-48, -100, 10);
		cards_field[20].setShift(-80, -100, 10);
				cards_field[21].setShift( 96, -120, 0);
		cards_field[22].setShift( 64, -120, 0);
		cards_field[23].setShift( 32, -120, 0);
		cards_field[24].setShift(  0, -120, 0);
		cards_field[25].setShift(-32, -120, 0);
		cards_field[26].setShift(-64, -120, 0);
		cards_field[27].setShift(-96, -120, 0);
				setJingle(SOUND_CARD_SHOVE);
		
				//
		// // ---
		//
		// scoreLives = 2;
		// combo = 0;
		// if(tableID == 1){
		// 	fillSmallField();
		// } else {
		// 	fillBigField();
		// }
	}
	
	public void restart(){
		selector.set(-1, -1);
		List<Card> deck = shuffleDeck();
				for(int i = 0; i < 28; i++) {
			cards_field[i] = deck.get(i);
		} deck.subList(0, 28).clear();
		cards_reserve.addAll(deck);
		cards_stack.clear();
				cards_field[ 1].setShift( 16,  -20, 50);
		cards_field[ 2].setShift(-16,  -20, 50);
				cards_field[ 3].setShift( 32,  -40, 40);
		cards_field[ 4].setShift(  0,  -40, 40);
		cards_field[ 5].setShift(-32,  -40, 40);
				cards_field[ 6].setShift( 48,  -60, 30);
		cards_field[ 7].setShift( 16,  -60, 30);
		cards_field[ 8].setShift(-16,  -60, 30);
		cards_field[ 9].setShift(-48,  -60, 30);
				cards_field[10].setShift( 64,  -80, 20);
		cards_field[11].setShift( 32,  -80, 20);
		cards_field[12].setShift(  0,  -80, 20);
		cards_field[13].setShift(-32,  -80, 20);
		cards_field[14].setShift(-64,  -80, 20);
				cards_field[15].setShift( 80, -100, 10);
		cards_field[16].setShift( 48, -100, 10);
		cards_field[17].setShift( 16, -100, 10);
		cards_field[18].setShift(-16, -100, 10);
		cards_field[19].setShift(-48, -100, 10);
		cards_field[20].setShift(-80, -100, 10);
				cards_field[21].setShift( 96, -120, 0);
		cards_field[22].setShift( 64, -120, 0);
		cards_field[23].setShift( 32, -120, 0);
		cards_field[24].setShift(  0, -120, 0);
		cards_field[25].setShift(-32, -120, 0);
		cards_field[26].setShift(-64, -120, 0);
		cards_field[27].setShift(-96, -120, 0);
				setJingle(SOUND_CARD_SHOVE);
		
		//
		// // ---
		//
		// if(tableID == 1){
		// 	fillSmallField();
		// } else {
		// 	fillBigField();
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		if(action == -1) compareCards(28);
		else if(action == -2) drawReserve();
		else if(action == -3) compareCards(29);
		else touchField(action % 20, action / 20);
		
		//
		// // ---
		//
		// if(action == -2) drawReserve();
		// else touchField(action % 20, action / 20);
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
			for(int i = 0; i < 28; i++){
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
		if(turnstate >= 2){
			if(cards_stack.size()   > 0) for (Card card : cards_stack) {   card.update(); }
			if(cards_reserve.size() > 0) for (Card card : cards_reserve) { card.update(); }
			for(int i = 0; i < 28; i++) { cards_field[i].update(); }
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		cards_reserve = loadCardList(compound, 0);
		cards_stack   = loadCardList(compound, 1);
		cards_field   = loadCardArray(compound, 2);
		
		//
		// // ---
		//
		// cards_field = loadCardArray( compound, 0);
		// cards_stack   = loadCardList(compound, 1);
		// cards_reserve = loadCardList(compound, 2);
	}
	
	public CompoundTag save2(CompoundTag compound){
		// return compound;
		
		// // ---
		//
		
		saveCardList(compound,  0, cards_reserve);
		saveCardList(compound,  1, cards_stack);
		saveCardArray(compound, 2, cards_field);
		return compound;
		
		//
		// // ---
		//
		// saveCardArray(compound, 0, cards_field);
		// saveCardList(compound,  1, cards_stack);
		// saveCardList(compound,  2, cards_reserve);
		// return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
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
		
		// private List<Card> shuffleDeck() {
		// 	List<Card> stack = new ArrayList<Card>();
		// 	List<Card> deck  = new ArrayList<Card>();
		// 	for(int y = 0; y < 4; y++) {
		// 		for(int x = 0; x < 13; x++) {
		// 			stack.add(new Card(x, y));
		// 		}
		// 	}
		// 	while(stack.size() > 0) {
		// 		int r = stack.size() == 1 ? 0 : RANDOM.nextInt(stack.size() - 1);
		// 		deck.add(stack.get(r));
		// 		stack.remove(r);
		// 	}
		// 	return deck;
		// }
	}
	
	
	
	private void drawReserve() {
		if(cards_reserve.size() > 0) {
			cards_reserve.get(0).shiftX = 36;
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
		
		// private void drawReserve() {
		// 	combo = 0;
		// 	if(cards_reserve.size() > 0) {
		// 		cards_reserve.get(0).shiftX = 40;
		// 		cards_stack.add(cards_reserve.get(0));
		// 		cards_reserve.remove(0);
		// 		setJingle(SOUND_CARD_SHOVE);
		// 	} else {
		// 		if(scoreLives == 0){
		// 			turnstate = 4;
		// 		} else {
		// 			scoreLives--;
		// 			restart();
		// 		}
		// 	}
		// }
	}
	
	private void touchField(int x, int y) {
		if(x ==  7 && y == 1) if(isCardSelectable( 0)){ compareCards(0); }
		if(x ==  8 && y == 1) if(isCardSelectable( 0)){ compareCards(0); }
			if(x ==  6 && y == 2) if(isCardSelectable( 1)){ compareCards(1); }
		if(x ==  7 && y == 2) if(isCardSelectable( 1)){ compareCards(1); } else if(isCardSelectable( 0)){
			compareCards(0); }
		if(x ==  8 && y == 2) if(isCardSelectable( 2)){ compareCards(2); } else if(isCardSelectable( 0)){
			compareCards(0); }
		if(x ==  9 && y == 2) if(isCardSelectable( 2)){ compareCards(2); }
			if(x ==  5 && y == 3) if(isCardSelectable( 3)){ compareCards(3); }
		if(x ==  6 && y == 3) if(isCardSelectable( 3)){ compareCards(3); } else if(isCardSelectable( 1)){ compareCards(1); }
		if(x ==  7 && y == 3) if(isCardSelectable( 4)){ compareCards(4); } else if(isCardSelectable( 1)){ compareCards(1); }
		if(x ==  8 && y == 3) if(isCardSelectable( 4)){ compareCards(4); } else if(isCardSelectable( 2)){ compareCards(2); }
		if(x ==  9 && y == 3) if(isCardSelectable( 5)){ compareCards(5); } else if(isCardSelectable( 2)){ compareCards(2); }
		if(x == 10 && y == 3) if(isCardSelectable( 5)){ compareCards(5); }
			if(x ==  4 && y == 4) if(isCardSelectable( 6)){ compareCards(6); }
		if(x ==  5 && y == 4) if(isCardSelectable( 6)){ compareCards(6); } else if(isCardSelectable( 3)){ compareCards(3); }
		if(x ==  6 && y == 4) if(isCardSelectable( 7)){ compareCards(7); } else if(isCardSelectable( 3)){ compareCards(3); }
		if(x ==  7 && y == 4) if(isCardSelectable( 7)){ compareCards(7); } else if(isCardSelectable( 4)){ compareCards(4); }
		if(x ==  8 && y == 4) if(isCardSelectable( 8)){ compareCards(8); } else if(isCardSelectable( 4)){ compareCards(4); }
		if(x ==  9 && y == 4) if(isCardSelectable( 8)){ compareCards(8); } else if(isCardSelectable( 5)){ compareCards(5); }
		if(x == 10 && y == 4) if(isCardSelectable( 9)){ compareCards(9); } else if(isCardSelectable( 5)){ compareCards(5); }
		if(x == 11 && y == 4) if(isCardSelectable( 9)){ compareCards(9); }
			if(x ==  3 && y == 5) if(isCardSelectable(10)){ compareCards(10); }
		if(x ==  4 && y == 5) if(isCardSelectable(10)){ compareCards(10); } else if(isCardSelectable( 6)){ compareCards(6); }
		if(x ==  5 && y == 5) if(isCardSelectable(11)){ compareCards(11); } else if(isCardSelectable( 6)){ compareCards(6); }
		if(x ==  6 && y == 5) if(isCardSelectable(11)){ compareCards(11); } else if(isCardSelectable( 7)){ compareCards(7); }
		if(x ==  7 && y == 5) if(isCardSelectable(12)){ compareCards(12); } else if(isCardSelectable( 7)){ compareCards(7); }
		if(x ==  8 && y == 5) if(isCardSelectable(12)){ compareCards(12); } else if(isCardSelectable( 8)){ compareCards(8); }
		if(x ==  9 && y == 5) if(isCardSelectable(13)){ compareCards(13); } else if(isCardSelectable( 8)){ compareCards(8); }
		if(x == 10 && y == 5) if(isCardSelectable(13)){ compareCards(13); } else if(isCardSelectable( 9)){ compareCards(9); }
		if(x == 11 && y == 5) if(isCardSelectable(14)){ compareCards(14); } else if(isCardSelectable( 9)){ compareCards(9); }
		if(x == 12 && y == 5) if(isCardSelectable(14)){ compareCards(14); }
			if(x ==  2 && y == 6) if(isCardSelectable(15)){ compareCards(15); }
		if(x ==  3 && y == 6) if(isCardSelectable(15)){ compareCards(15); } else if(isCardSelectable(10)){ compareCards(10); }
		if(x ==  4 && y == 6) if(isCardSelectable(16)){ compareCards(16); } else if(isCardSelectable(10)){ compareCards(10); }
		if(x ==  5 && y == 6) if(isCardSelectable(16)){ compareCards(16); } else if(isCardSelectable(11)){ compareCards(11); }
		if(x ==  6 && y == 6) if(isCardSelectable(17)){ compareCards(17); } else if(isCardSelectable(11)){ compareCards(11); }
		if(x ==  7 && y == 6) if(isCardSelectable(17)){ compareCards(17); } else if(isCardSelectable(12)){ compareCards(12); }
		if(x ==  8 && y == 6) if(isCardSelectable(18)){ compareCards(18); } else if(isCardSelectable(12)){ compareCards(12); }
		if(x ==  9 && y == 6) if(isCardSelectable(18)){ compareCards(18); } else if(isCardSelectable(13)){ compareCards(13); }
		if(x == 10 && y == 6) if(isCardSelectable(19)){ compareCards(19); } else if(isCardSelectable(13)){ compareCards(13); }
		if(x == 11 && y == 6) if(isCardSelectable(19)){ compareCards(19); } else if(isCardSelectable(14)){ compareCards(14); }
		if(x == 12 && y == 6) if(isCardSelectable(20)){ compareCards(20); } else if(isCardSelectable(14)){ compareCards(14); }
		if(x == 13 && y == 6) if(isCardSelectable(20)){ compareCards(20); }
			if(x ==  1 && y == 7) if(isCardSelectable(21)){ compareCards(21); }
		if(x ==  2 && y == 7) if(isCardSelectable(21)){ compareCards(21); } else if(isCardSelectable(15)){ compareCards(15); }
		if(x ==  3 && y == 7) if(isCardSelectable(22)){ compareCards(22); } else if(isCardSelectable(15)){ compareCards(15); }
		if(x ==  4 && y == 7) if(isCardSelectable(22)){ compareCards(22); } else if(isCardSelectable(16)){ compareCards(16); }
		if(x ==  5 && y == 7) if(isCardSelectable(23)){ compareCards(23); } else if(isCardSelectable(16)){ compareCards(16); }
		if(x ==  6 && y == 7) if(isCardSelectable(23)){ compareCards(23); } else if(isCardSelectable(17)){ compareCards(17); }
		if(x ==  7 && y == 7) if(isCardSelectable(24)){ compareCards(24); } else if(isCardSelectable(17)){ compareCards(17); }
		if(x ==  8 && y == 7) if(isCardSelectable(24)){ compareCards(24); } else if(isCardSelectable(18)){ compareCards(18); }
		if(x ==  9 && y == 7) if(isCardSelectable(25)){ compareCards(25); } else if(isCardSelectable(18)){ compareCards(18); }
		if(x == 10 && y == 7) if(isCardSelectable(25)){ compareCards(25); } else if(isCardSelectable(19)){ compareCards(19); }
		if(x == 11 && y == 7) if(isCardSelectable(26)){ compareCards(26); } else if(isCardSelectable(19)){ compareCards(19); }
		if(x == 12 && y == 7) if(isCardSelectable(26)){ compareCards(26); } else if(isCardSelectable(20)){ compareCards(20); }
		if(x == 13 && y == 7) if(isCardSelectable(27)){ compareCards(27); } else if(isCardSelectable(20)){ compareCards(20); }
		if(x == 14 && y == 7) if(isCardSelectable(27)){ compareCards(27); }
			if(x ==  1 && y == 8) if(isCardSelectable(21)){ compareCards(21); }
		if(x ==  2 && y == 8) if(isCardSelectable(21)){ compareCards(21); }
		if(x ==  3 && y == 8) if(isCardSelectable(22)){ compareCards(22); }
		if(x ==  4 && y == 8) if(isCardSelectable(22)){ compareCards(22); }
		if(x ==  5 && y == 8) if(isCardSelectable(23)){ compareCards(23); }
		if(x ==  6 && y == 8) if(isCardSelectable(23)){ compareCards(23); }
		if(x ==  7 && y == 8) if(isCardSelectable(24)){ compareCards(24); }
		if(x ==  8 && y == 8) if(isCardSelectable(24)){ compareCards(24); }
		if(x ==  9 && y == 8) if(isCardSelectable(25)){ compareCards(25); }
		if(x == 10 && y == 8) if(isCardSelectable(25)){ compareCards(25); }
		if(x == 11 && y == 8) if(isCardSelectable(26)){ compareCards(26); }
		if(x == 12 && y == 8) if(isCardSelectable(26)){ compareCards(26); }
		if(x == 13 && y == 8) if(isCardSelectable(27)){ compareCards(27); }
		if(x == 14 && y == 8) if(isCardSelectable(27)){ compareCards(27); }
		
		// private void touchField(int x, int y) {
		// 	if(x ==  3 && y == 2) if(isCardSelectable( 0)) { compareCards( 0); }
		// 	if(x ==  4 && y == 2) if(isCardSelectable( 0)) { compareCards( 0); }
		// 	if(x ==  9 && y == 2) if(isCardSelectable( 1)) { compareCards( 1); }
		// 	if(x == 10 && y == 2) if(isCardSelectable( 1)) { compareCards( 1); }
		// 	if(x == 15 && y == 2) if(isCardSelectable( 2)) { compareCards( 2); }
		// 	if(x == 16 && y == 2) if(isCardSelectable( 2)) { compareCards( 2); }
		// 		if(x ==  2 && y == 3) if(isCardSelectable( 3)) { compareCards( 3); }
		// 	if(x ==  3 && y == 3) if(isCardSelectable( 3)) { compareCards( 3); } else if(isCardSelectable( 0)) { compareCards( 0); }
		// 	if(x ==  4 && y == 3) if(isCardSelectable( 4)) { compareCards( 4); } else if(isCardSelectable( 0)) { compareCards( 0); }
		// 	if(x ==  5 && y == 3) if(isCardSelectable( 4)) { compareCards( 4); }
		// 	if(x ==  8 && y == 3) if(isCardSelectable( 5)) { compareCards( 5); }
		// 	if(x ==  9 && y == 3) if(isCardSelectable( 5)) { compareCards( 5); } else if(isCardSelectable( 1)) { compareCards( 1); }
		// 	if(x == 10 && y == 3) if(isCardSelectable( 6)) { compareCards( 6); } else if(isCardSelectable( 1)) { compareCards( 1); }
		// 	if(x == 11 && y == 3) if(isCardSelectable( 6)) { compareCards( 6); }
		// 	if(x == 14 && y == 3) if(isCardSelectable( 7)) { compareCards( 7); }
		// 	if(x == 15 && y == 3) if(isCardSelectable( 7)) { compareCards( 7); } else if(isCardSelectable( 2)) { compareCards( 2); }
		// 	if(x == 16 && y == 3) if(isCardSelectable( 8)) { compareCards( 8); } else if(isCardSelectable( 2)) { compareCards( 2); }
		// 	if(x == 17 && y == 3) if(isCardSelectable( 8)) { compareCards( 8); }
		// 		if(x ==  1 && y == 4) if(isCardSelectable( 9)) { compareCards( 9); }
		// 	if(x ==  2 && y == 4) if(isCardSelectable( 9)) { compareCards( 9); } else if(isCardSelectable( 3)) { compareCards( 3); }
		// 	if(x ==  3 && y == 4) if(isCardSelectable(10)) { compareCards(10); } else if(isCardSelectable( 3)) { compareCards( 3); }
		// 	if(x ==  4 && y == 4) if(isCardSelectable(10)) { compareCards(10); } else if(isCardSelectable( 4)) { compareCards( 4); }
		// 	if(x ==  5 && y == 4) if(isCardSelectable(11)) { compareCards(11); } else if(isCardSelectable( 4)) { compareCards( 4); }
		// 	if(x ==  6 && y == 4) if(isCardSelectable(11)) { compareCards(11); }
		// 	if(x ==  7 && y == 4) if(isCardSelectable(12)) { compareCards(12); }
		// 	if(x ==  8 && y == 4) if(isCardSelectable(12)) { compareCards(12); } else if(isCardSelectable( 5)) { compareCards( 5); }
		// 	if(x ==  9 && y == 4) if(isCardSelectable(13)) { compareCards(13); } else if(isCardSelectable( 5)) { compareCards( 5); }
		// 	if(x == 10 && y == 4) if(isCardSelectable(13)) { compareCards(13); } else if(isCardSelectable( 6)) { compareCards( 6); }
		// 	if(x == 11 && y == 4) if(isCardSelectable(14)) { compareCards(14); } else if(isCardSelectable( 6)) { compareCards( 6); }
		// 	if(x == 12 && y == 4) if(isCardSelectable(14)) { compareCards(14); }
		// 	if(x == 13 && y == 4) if(isCardSelectable(15)) { compareCards(15); }
		// 	if(x == 14 && y == 4) if(isCardSelectable(15)) { compareCards(15); } else if(isCardSelectable( 7)) { compareCards( 7); }
		// 	if(x == 15 && y == 4) if(isCardSelectable(16)) { compareCards(16); } else if(isCardSelectable( 7)) { compareCards( 7); }
		// 	if(x == 16 && y == 4) if(isCardSelectable(16)) { compareCards(16); } else if(isCardSelectable( 8)) { compareCards( 8); }
		// 	if(x == 17 && y == 4) if(isCardSelectable(17)) { compareCards(17); } else if(isCardSelectable( 8)) { compareCards( 8); }
		// 	if(x == 18 && y == 4) if(isCardSelectable(17)) { compareCards(17); }
		// 		if(x ==  0 && y == 5) if(isCardSelectable(18)) { compareCards(18); }
		// 	if(x ==  1 && y == 5) if(isCardSelectable(18)) { compareCards(18); } else if(isCardSelectable( 9)) { compareCards( 9); }
		// 	if(x ==  2 && y == 5) if(isCardSelectable(19)) { compareCards(19); } else if(isCardSelectable( 9)) { compareCards( 9); }
		// 	if(x ==  3 && y == 5) if(isCardSelectable(19)) { compareCards(19); } else if(isCardSelectable(10)) { compareCards(10); }
		// 	if(x ==  4 && y == 5) if(isCardSelectable(20)) { compareCards(20); } else if(isCardSelectable(10)) { compareCards(10); }
		// 	if(x ==  5 && y == 5) if(isCardSelectable(20)) { compareCards(20); } else if(isCardSelectable(11)) { compareCards(11); }
		// 	if(x ==  6 && y == 5) if(isCardSelectable(21)) { compareCards(21); } else if(isCardSelectable(11)) { compareCards(11); }
		// 	if(x ==  7 && y == 5) if(isCardSelectable(21)) { compareCards(21); } else if(isCardSelectable(12)) { compareCards(12); }
		// 	if(x ==  8 && y == 5) if(isCardSelectable(22)) { compareCards(22); } else if(isCardSelectable(12)) { compareCards(12); }
		// 	if(x ==  9 && y == 5) if(isCardSelectable(22)) { compareCards(22); } else if(isCardSelectable(13)) { compareCards(13); }
		// 	if(x == 10 && y == 5) if(isCardSelectable(23)) { compareCards(23); } else if(isCardSelectable(13)) { compareCards(13); }
		// 	if(x == 11 && y == 5) if(isCardSelectable(23)) { compareCards(23); } else if(isCardSelectable(14)) { compareCards(14); }
		// 	if(x == 12 && y == 5) if(isCardSelectable(24)) { compareCards(24); } else if(isCardSelectable(14)) { compareCards(14); }
		// 	if(x == 13 && y == 5) if(isCardSelectable(24)) { compareCards(24); } else if(isCardSelectable(15)) { compareCards(15); }
		// 	if(x == 14 && y == 5) if(isCardSelectable(25)) { compareCards(25); } else if(isCardSelectable(15)) { compareCards(15); }
		// 	if(x == 15 && y == 5) if(isCardSelectable(25)) { compareCards(25); } else if(isCardSelectable(16)) { compareCards(16); }
		// 	if(x == 16 && y == 5) if(isCardSelectable(26)) { compareCards(26); } else if(isCardSelectable(16)) { compareCards(16); }
		// 	if(x == 17 && y == 5) if(isCardSelectable(26)) { compareCards(26); } else if(isCardSelectable(17)) { compareCards(17); }
		// 	if(x == 18 && y == 5) if(isCardSelectable(27)) { compareCards(27); } else if(isCardSelectable(17)) { compareCards(17); }
		// 	if(x == 19 && y == 5) if(isCardSelectable(27)) { compareCards(27); }
		// 		if(x ==  0 && y == 6) if(isCardSelectable(18)) { compareCards(18); }
		// 	if(x ==  1 && y == 6) if(isCardSelectable(18)) { compareCards(18); }
		// 	if(x ==  2 && y == 6) if(isCardSelectable(19)) { compareCards(19); }
		// 	if(x ==  3 && y == 6) if(isCardSelectable(19)) { compareCards(19); }
		// 	if(x ==  4 && y == 6) if(isCardSelectable(20)) { compareCards(20); }
		// 	if(x ==  5 && y == 6) if(isCardSelectable(20)) { compareCards(20); }
		// 	if(x ==  6 && y == 6) if(isCardSelectable(21)) { compareCards(21); }
		// 	if(x ==  7 && y == 6) if(isCardSelectable(21)) { compareCards(21); }
		// 	if(x ==  8 && y == 6) if(isCardSelectable(22)) { compareCards(22); }
		// 	if(x ==  9 && y == 6) if(isCardSelectable(22)) { compareCards(22); }
		// 	if(x == 10 && y == 6) if(isCardSelectable(23)) { compareCards(23); }
		// 	if(x == 11 && y == 6) if(isCardSelectable(23)) { compareCards(23); }
		// 	if(x == 12 && y == 6) if(isCardSelectable(24)) { compareCards(24); }
		// 	if(x == 13 && y == 6) if(isCardSelectable(24)) { compareCards(24); }
		// 	if(x == 14 && y == 6) if(isCardSelectable(25)) { compareCards(25); }
		// 	if(x == 15 && y == 6) if(isCardSelectable(25)) { compareCards(25); }
		// 	if(x == 16 && y == 6) if(isCardSelectable(26)) { compareCards(26); }
		// 	if(x == 17 && y == 6) if(isCardSelectable(26)) { compareCards(26); }
		// 	if(x == 18 && y == 6) if(isCardSelectable(27)) { compareCards(27); }
		// 	if(x == 19 && y == 6) if(isCardSelectable(27)) { compareCards(27); }
		// }
	}
	
	private boolean isCardSelectable(int id) {
		switch(id) {
			case  0: if(cards_field[ 0].suit != -1 && cards_field[ 1].suit == -1 && cards_field[ 2].suit == -1) return true; break;
				case  1: if(cards_field[ 1].suit != -1 && cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1) return true; break;
			case  2: if(cards_field[ 2].suit != -1 && cards_field[ 4].suit == -1 && cards_field[ 5].suit == -1) return true; break;
				case  3: if(cards_field[ 3].suit != -1 && cards_field[ 6].suit == -1 && cards_field[ 7].suit == -1) return true; break;
			case  4: if(cards_field[ 4].suit != -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1) return true; break;
			case  5: if(cards_field[ 5].suit != -1 && cards_field[ 8].suit == -1 && cards_field[ 9].suit == -1) return true; break;
				case  6: if(cards_field[ 6].suit != -1 && cards_field[10].suit == -1 && cards_field[11].suit == -1) return true; break;
			case  7: if(cards_field[ 7].suit != -1 && cards_field[11].suit == -1 && cards_field[12].suit == -1) return true; break;
			case  8: if(cards_field[ 8].suit != -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1) return true; break;
			case  9: if(cards_field[ 9].suit != -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) return true; break;
				case 10: if(cards_field[10].suit != -1 && cards_field[15].suit == -1 && cards_field[16].suit == -1) return true; break;
			case 11: if(cards_field[11].suit != -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1) return true; break;
			case 12: if(cards_field[12].suit != -1 && cards_field[17].suit == -1 && cards_field[18].suit == -1) return true; break;
			case 13: if(cards_field[13].suit != -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1) return true; break;
			case 14: if(cards_field[14].suit != -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) return true; break;
				case 15: if(cards_field[15].suit != -1 && cards_field[21].suit == -1 && cards_field[22].suit == -1) return true; break;
			case 16: if(cards_field[16].suit != -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1) return true; break;
			case 17: if(cards_field[17].suit != -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1) return true; break;
			case 18: if(cards_field[18].suit != -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1) return true; break;
			case 19: if(cards_field[19].suit != -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1) return true; break;
			case 20: if(cards_field[20].suit != -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) return true; break;
				case 21: if(cards_field[21].suit != -1) return true; break;
			case 22: if(cards_field[22].suit != -1) return true; break;
			case 23: if(cards_field[23].suit != -1) return true; break;
			case 24: if(cards_field[24].suit != -1) return true; break;
			case 25: if(cards_field[25].suit != -1) return true; break;
			case 26: if(cards_field[26].suit != -1) return true; break;
			case 27: if(cards_field[27].suit != -1) return true; break;
		}
		return false;
		
		// private boolean isCardSelectable(int id) {
		// 	switch(id) {
		// 		case  0: if(cards_field[ 0].suit != -1 && cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1) return true; break;
		// 		case  1: if(cards_field[ 1].suit != -1 && cards_field[ 5].suit == -1 && cards_field[ 6].suit == -1) return true; break;
		// 		case  2: if(cards_field[ 2].suit != -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1) return true; break;
		// 			case  3: if(cards_field[ 3].suit != -1 && cards_field[ 9].suit == -1 && cards_field[10].suit == -1) return true; break;
		// 		case  4: if(cards_field[ 4].suit != -1 && cards_field[10].suit == -1 && cards_field[11].suit == -1) return true; break;
		// 		case  5: if(cards_field[ 5].suit != -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1) return true; break;
		// 		case  6: if(cards_field[ 6].suit != -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) return true; break;
		// 		case  7: if(cards_field[ 7].suit != -1 && cards_field[15].suit == -1 && cards_field[16].suit == -1) return true; break;
		// 		case  8: if(cards_field[ 8].suit != -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1) return true; break;
		// 			case  9: if(cards_field[ 9].suit != -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1) return true; break;
		// 		case 10: if(cards_field[10].suit != -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) return true; break;
		// 		case 11: if(cards_field[11].suit != -1 && cards_field[20].suit == -1 && cards_field[21].suit == -1) return true; break;
		// 		case 12: if(cards_field[12].suit != -1 && cards_field[21].suit == -1 && cards_field[22].suit == -1) return true; break;
		// 		case 13: if(cards_field[13].suit != -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1) return true; break;
		// 		case 14: if(cards_field[14].suit != -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1) return true; break;
		// 		case 15: if(cards_field[15].suit != -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1) return true; break;
		// 		case 16: if(cards_field[16].suit != -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1) return true; break;
		// 		case 17: if(cards_field[17].suit != -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) return true; break;
		// 			case 18: if(cards_field[18].suit != -1) return true; break;
		// 		case 19: if(cards_field[19].suit != -1) return true; break;
		// 		case 20: if(cards_field[20].suit != -1) return true; break;
		// 		case 21: if(cards_field[21].suit != -1) return true; break;
		// 		case 22: if(cards_field[22].suit != -1) return true; break;
		// 		case 23: if(cards_field[23].suit != -1) return true; break;
		// 		case 24: if(cards_field[24].suit != -1) return true; break;
		// 		case 25: if(cards_field[25].suit != -1) return true; break;
		// 		case 26: if(cards_field[26].suit != -1) return true; break;
		// 		case 27: if(cards_field[27].suit != -1) return true; break;
		// 	}
		// 	return false;
		// }
	}
	
	private int findNumber(int id){
		if(id == 28) if(cards_stack  .size() > 0) return cards_stack  .get(cards_stack.size() - 1).number; else return -1;
		if(id == 29) if(cards_reserve.size() > 0) return cards_reserve.get(0                     ).number; else return -1;
		return cards_field[id].number;
	}
	
	private void setDead(int id){
		if(id == 28){ if(cards_stack  .size() > 0) cards_stack  .get(cards_stack.size() - 1).dead = true;
		} else if(id == 29){ if(cards_reserve.size() > 0) cards_reserve.get(0                     ).dead = true;
		} else cards_field[id].dead = true;
	}
	
	private void compareCards(int id) {
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
				scorePoint += 50;
				setJingle(SOUND_CARD_PLACE);
				} else if(findNumber(id) == 12){ // Set new Selector
				setDead(id);
				scorePoint += 50;
				setJingle(SOUND_CARD_PLACE);
			} else {
				selector.X = id;
			}
		}
		
		// private void compareCards(int id) {
		// 		if(cards_field[id].number + 1 == cards_stack.get(cards_stack.size() - 1).number || cards_field[id].number - 1 == cards_stack.get(cards_stack.size() - 1).number || (cards_field[id].number == 0 && cards_stack.get(cards_stack.size() - 1).number == 12) || (cards_field[id].number == 12 && cards_stack.get(cards_stack.size() - 1).number == 0)) {
		// 		combo++;
		// 		scorePoint += 50 * combo;
		// 		cards_field[id].shiftX = 0;
		// 		cards_field[id].shiftY = +24;
		// 		cards_stack.add(cards_field[id]);
		// 		cards_field[id] = new Card(-1, -1);
		// 		if(cards_field[0].suit == -1 && cards_field[1].suit == -1 && cards_field[2].suit == -1) restart();
		// 		setJingle(SOUND_CARD_PLACE);
		// 	}
		// }
	}
	
	private void checkForClearedLine(int id) {
		if(id ==  0) restart();
		if(id >=  1 && id <=  2) if(cards_field[ 1].suit == -1 && cards_field[ 2].suit == -1) scorePoint += 1000;
		if(id >=  3 && id <=  5) if(cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1 && cards_field[ 5].suit == -1) scorePoint += 500;
		if(id >=  6 && id <=  9) if(cards_field[ 6].suit == -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1 && cards_field[ 9].suit == -1) scorePoint += 400;
		if(id >= 10 && id <= 14) if(cards_field[10].suit == -1 && cards_field[11].suit == -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) scorePoint += 300;
		if(id >= 15 && id <= 20) if(cards_field[15].suit == -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) scorePoint += 200;
		if(id >= 21 && id <= 27) if(cards_field[21].suit == -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) scorePoint += 100;
	}
	
	
	
	private void fillSmallField(){
		List<Card> deck = shuffleDeck();
			deck.get( 0).setShift( 0,  0,  0); cards_field[ 0] = deck.get( 0);
		deck.get( 1).setShift( 0,  0,  0); cards_field[ 1] = deck.get( 1);
		cards_field[ 2] = new Card(-1, -1);
			deck.get( 2).setShift(+16, -20, 20); cards_field[ 3] = deck.get( 2);
		deck.get( 3).setShift(-16, -20, 20); cards_field[ 4] = deck.get( 3);
		deck.get( 4).setShift(+16, -20, 20); cards_field[ 5] = deck.get( 4);
		deck.get( 5).setShift(-16, -20, 20); cards_field[ 6] = deck.get( 5);
		cards_field[ 7] = new Card(-1, -1);
		cards_field[ 8] = new Card(-1, -1);
			deck.get( 6).setShift(+32, -40, 20); cards_field[ 9] = deck.get( 6);
		deck.get( 7).setShift(  0, -40, 20); cards_field[10] = deck.get( 7);
		deck.get( 8).setShift(-32, -40, 20); cards_field[11] = deck.get( 8);
		deck.get( 9).setShift(+32, -40, 20); cards_field[12] = deck.get( 9);
		deck.get(10).setShift(  0, -40, 20); cards_field[13] = deck.get(10);
		deck.get(11).setShift(-32, -40, 20); cards_field[14] = deck.get(11);
		cards_field[15] = new Card(-1, -1);
		cards_field[16] = new Card(-1, -1);
		cards_field[17] = new Card(-1, -1);
			deck.get(12).setShift(+48, -60, 20); cards_field[18] = deck.get(12);
		deck.get(13).setShift(+16, -60, 20); cards_field[19] = deck.get(13);
		deck.get(14).setShift(-16, -60, 20); cards_field[20] = deck.get(14);
		deck.get(15).setShift(-48, -60, 20); cards_field[21] = deck.get(15);
		deck.get(16).setShift(+16, -60, 20); cards_field[22] = deck.get(16);
		deck.get(17).setShift(-16, -60, 20); cards_field[23] = deck.get(17);
		deck.get(18).setShift(-48, -60, 20); cards_field[24] = deck.get(18);
		cards_field[25] = new Card(-1, -1);
		cards_field[26] = new Card(-1, -1);
		cards_field[27] = new Card(-1, -1);
			selector = new Vector2(9, 12);
		deck.subList(0, 19).clear();
		cards_reserve.addAll(deck);
		cards_stack.clear();
		drawReserve();
			setJingle(SOUND_CARD_SHOVE);
	}
	
	private void fillBigField(){
		List<Card> deck = shuffleDeck();
			deck.get( 3).setShift( 16, -20, 20);
		deck.get( 4).setShift(-16, -20, 20);
		deck.get( 9).setShift( 32, -40, 10);
		deck.get(10).setShift(  0, -40, 10);
		deck.get(11).setShift(-32, -40, 10);
		deck.get(18).setShift( 48, -60,  0);
		deck.get(19).setShift( 16, -60,  0);
		deck.get(20).setShift(-16, -60,  0);
			deck.get( 5).setShift( 16, -20, 20);
		deck.get( 6).setShift(-16, -20, 20);
		deck.get(12).setShift( 32, -40, 10);
		deck.get(13).setShift(  0, -40, 10);
		deck.get(14).setShift(-32, -40, 10);
		deck.get(21).setShift( 48, -60,  0);
		deck.get(22).setShift( 16, -60,  0);
		deck.get(23).setShift(-16, -60,  0);
		deck.get(24).setShift(-48, -60,  0);
			deck.get( 7).setShift( 16, -20, 20);
		deck.get( 8).setShift(-16, -20, 20);
		deck.get(15).setShift( 32, -40, 10);
		deck.get(16).setShift(  0, -40, 10);
		deck.get(17).setShift(-32, -40, 10);
		deck.get(25).setShift( 16, -60,  0);
		deck.get(26).setShift(-16, -60,  0);
		deck.get(27).setShift(-48, -60,  0);
			for(int i = 0; i < 28; i++) {
			cards_field[i] = deck.get(i);
		}
		selector = new Vector2(9, 12);
		deck.subList(0, 28).clear();
		cards_reserve.addAll(deck);
		cards_stack.clear();
		drawReserve();
			setJingle(SOUND_CARD_SHOVE);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 16;
	}
	
	public String getName(){
		return ruleSet[0] == 0 ? "pyramid" : ruleSet[0] == 1 ? "twinpeak" : "tripeak";
	}
	
	
	
}