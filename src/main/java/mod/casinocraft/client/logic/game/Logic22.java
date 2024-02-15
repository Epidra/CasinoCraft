package mod.casinocraft.client.logic.game;

import mod.casinocraft.client.logic.LogicModule;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundTag;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_CARD_SHOVE;
import static mod.casinocraft.util.mapping.SoundMap.SOUND_REWARD;

public class Logic22 extends LogicModule {   //  Poker
	
	// GAME MODE -- Single Player, Multi Player
	// RULE 1 -- River (YES, NO)
	// RULE 2 -- Cards in Hand (1, 2, 3, 4, 5)
	// RULE 3 -- Can Trade Cards
	// COLOR VARIATION -- Card Design
	
	// --------------------------------------------------
	
	// public List<Card> cardsP1 = new ArrayList<>();
	// public List<Card> cardsP2 = new ArrayList<>();
	// public List<Card> cardsP3 = new ArrayList<>();
	// public List<Card> cardsP4 = new ArrayList<>();
	// public List<Card> cardsP5 = new ArrayList<>();
	// public List<Card> cardsP6 = new ArrayList<>();
	// public Card[] comCards = new Card[5];
	// public boolean[] folded = new boolean[6];
	// public int pot = 0;
	// public int raisedPlayer = -1;
	// public int playerCount = 0;
	//
	// // ---
	//
	// public List<Card> cardsP1 = new ArrayList<>();
	// public List<Card> cardsP2 = new ArrayList<>();
	// public List<Card> cardsP3 = new ArrayList<>();
	// public List<Card> cardsP4 = new ArrayList<>();
	// public List<Card> cardsP5 = new ArrayList<>();
	// public List<Card> cardsP6 = new ArrayList<>();
	// public boolean[] folded = new boolean[6];
	// public int pot = 0;
	// public int raisedPlayer = -1;
	// public int round = 0;
	// public int playerCount = 0;
	//
	// // ---
	//
	
	public boolean[] hold = new boolean[5];
	public Card[] cards_field = new Card[5];
	private int ticker = 0;
	private int movestate = 0;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic22(int tableID) {
		super(tableID);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		// for(int i = 0; i < 6; i++){
		// 	getCards(i).clear();
		// }
		// for(int i = 0; i < 5; i++){
		// 	comCards[i] = new Card(-1, -1);
		// }
		// for(int i = 0; i < 6; i++){
		// 	folded[i] = false;
		// }
		// pot = 0;
		// raisedPlayer = -1;
		//
		// // ---
		//
		// for(int i = 0; i < 6; i++){
		// 	getCards(i).clear();
		// }
		// for(int i = 0; i < 6; i++){
		// 	folded[i] = false;
		// }
		// pot = 0;
		// raisedPlayer = -1;
		// round = 0;
		//
		// // ---
		//
		
		for(int i = 0; i < 5; i++){
			cards_field[i] = new Card(RANDOM, 0, 20 + 5*i);
			hold[i] = false;
		}
		ticker = 0;
		movestate = 0;
		setJingle(SOUND_CARD_SHOVE);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		// if(action == 0){ call();  }
		// if(action == 1){ raise(); }
		// if(action == 2){ check(); }
		// if(action == 3){ fold();  }
		//
		// // ---
		//
		// if(action == 0){ call();  }
		// if(action == 1){ raise(); }
		// if(action == 2){ check(); }
		// if(action == 3){ fold();  }
		// if(action >= 4){
		// 	if(action - 4 < getCards(activePlayer).size()){
		// 		getCards(activePlayer).get(action - 4).hidden = !getCards(activePlayer).get(action - 4).hidden;
		// 	}
		// }
		//
		// // ---
		//
		
		if(action == 5 && turnstate == 2){
			turnstate = 3;
			movestate = 1;
			for(int i = 0; i < 5; i++){
				if(!hold[i]) cards_field[i].hidden = !cards_field[i].hidden;
			}
		} else {
			hold[action] = !hold[action];
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
		// timeout++;
		// if(turnstate == 2 && timeout >= timeoutMAX || getFirstFreePlayerSlot() == (tableID == 1 ? 4 : 6)){
		// 	draw();
		// }
		// if(turnstate == 3){
		// 	if(folded[activePlayer]){
		// 		drawAnother();
		// 	}
		// 	if(timeout == timeoutMAX){
		// 		fold();
		// 	}
		// 	if(lastStanding() != -1){
		// 		result();
		// 	}
		// }
		//
		// // ---
		//
		// timeout++;
		// if(turnstate == 2 && timeout >= timeoutMAX || getFirstFreePlayerSlot() == (tableID == 1 ? 4 : 6)){
		// 	draw();
		// }
		// if(turnstate == 3){
		// 	if(folded[activePlayer]){
		// 		drawAnother();
		// 	}
		// 	if(timeout == timeoutMAX){
		// 		fold();
		// 	}
		// 	if(lastStanding() != -1){
		// 		result();
		// 	}
		// }
		//
		// // ---
		
				switch(movestate){
					// ----- NULL -----
			case 0:
				break;
					// ----- Cards move up ----- //
			case 1:
				ticker+=2;
				for(int i = 0; i < 5; i++){
					if(!hold[i]) cards_field[i].shiftY-=2;
				}
				if(ticker >= 30){
					for(int i = 0; i < 5; i++){
						if(!hold[i]){
							int sX = cards_field[i].shiftX;
							int sY = cards_field[i].shiftY;
							cards_field[i] = new Card(RANDOM.nextInt(13), RANDOM.nextInt(4), sX, sY);
						}
					}
					movestate = 2;
				}
				break;
					// ----- Cards move down ----- //
			case 2:
				ticker-=2;
				for(int i = 0; i < 5; i++){
					if(!hold[i]) cards_field[i].shiftY+=2;
				}
				if(ticker <= 0){
					movestate = 3;
				}
				break;
					// ----- Cards move together ----- //
			case 3:
				ticker+=2;
				cards_field[0].shiftX+=4;
				cards_field[1].shiftX+=2;
				cards_field[3].shiftX-=2;
				cards_field[4].shiftX-=4;
				if(ticker == 44){
					sort();
					movestate = 4;
				}
				break;
					// ----- Cards move apart ----- //
			case 4:
				ticker-=2;
				cards_field[0].shiftX-=4;
				cards_field[1].shiftX-=2;
				cards_field[3].shiftX+=2;
				cards_field[4].shiftX+=4;
				if(ticker == 0){
					result();
					turnstate = 4;
					movestate = 0;
				}
				break;
		}
	}
	
	public void updateMotion() {
		// for(int i = 0; i < 6; i++){
		// 	for(Card c : getCards(i)){
		// 		c.update();
		// 	}
		// }
		// for(int i = 0; i < 5; i++){
		// 	comCards[i].update();
		// }
		//
		// // ---
		//
		// for(int i = 0; i < 6; i++){
		// 	for(Card c : getCards(i)){
		// 		c.update();
		// 	}
		// }
		//
		// // ---
		//
		
		if(turnstate == 2) {
			for(int i = 0; i < 5; i++){
				cards_field[i].update();
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		// cardsP1  = loadCardList(compound,  0);
		// cardsP2  = loadCardList(compound,  1);
		// cardsP3  = loadCardList(compound,  2);
		// cardsP4  = loadCardList(compound,  3);
		// cardsP5  = loadCardList(compound,  4);
		// cardsP6  = loadCardList(compound,  5);
		// comCards = loadCardArray(compound, 6);
		// folded[0] = compound.getBoolean("folded0");
		// folded[1] = compound.getBoolean("folded1");
		// folded[2] = compound.getBoolean("folded2");
		// folded[3] = compound.getBoolean("folded3");
		// folded[4] = compound.getBoolean("folded4");
		// folded[5] = compound.getBoolean("folded5");
		// pot = compound.getInt("pot");
		// raisedPlayer = compound.getInt("raisedplayer");
		//
		// // ---
		//
		// cardsP1  = loadCardList(compound,  0);
		// cardsP2  = loadCardList(compound,  1);
		// cardsP3  = loadCardList(compound,  2);
		// cardsP4  = loadCardList(compound,  3);
		// cardsP5  = loadCardList(compound,  4);
		// cardsP6  = loadCardList(compound,  5);
		// folded[0] = compound.getBoolean("folded0");
		// folded[1] = compound.getBoolean("folded1");
		// folded[2] = compound.getBoolean("folded2");
		// folded[3] = compound.getBoolean("folded3");
		// folded[4] = compound.getBoolean("folded4");
		// folded[5] = compound.getBoolean("folded5");
		// pot = compound.getInt("pot");
		// raisedPlayer = compound.getInt("raisedplayer");
		// round = compound.getInt("round");
		//
		// // ---
		//
		
		cards_field = loadCardArray(compound, 0);
		hold[0] = compound.getBoolean("hold0");
		hold[1] = compound.getBoolean("hold1");
		hold[2] = compound.getBoolean("hold2");
		hold[3] = compound.getBoolean("hold3");
		hold[4] = compound.getBoolean("hold4");
		ticker    = compound.getInt("ticker");
		movestate = compound.getInt("movestate");
	}
	
	public CompoundTag save2(CompoundTag compound){
		// return compound;
		
		// // ---
		//
		// saveCardList(compound,  0, cardsP1);
		// saveCardList(compound,  1, cardsP2);
		// saveCardList(compound,  2, cardsP3);
		// saveCardList(compound,  3, cardsP4);
		// saveCardList(compound,  4, cardsP5);
		// saveCardList(compound,  5, cardsP6);
		// saveCardArray(compound, 6, comCards);
		// compound.putBoolean("folded0", folded[0]);
		// compound.putBoolean("folded1", folded[1]);
		// compound.putBoolean("folded2", folded[2]);
		// compound.putBoolean("folded3", folded[3]);
		// compound.putBoolean("folded4", folded[4]);
		// compound.putBoolean("folded5", folded[5]);
		// compound.putInt("pot", pot);
		// compound.putInt("raisedplayer", raisedPlayer);
		// return compound;
		//
		// // ---
		//
		// saveCardList(compound,  0, cardsP1);
		// saveCardList(compound,  1, cardsP2);
		// saveCardList(compound,  2, cardsP3);
		// saveCardList(compound,  3, cardsP4);
		// saveCardList(compound,  4, cardsP5);
		// saveCardList(compound,  5, cardsP6);
		// compound.putBoolean("folded0", folded[0]);
		// compound.putBoolean("folded1", folded[1]);
		// compound.putBoolean("folded2", folded[2]);
		// compound.putBoolean("folded3", folded[3]);
		// compound.putBoolean("folded4", folded[4]);
		// compound.putBoolean("folded5", folded[5]);
		// compound.putInt("pot", pot);
		// compound.putInt("raisedplayer", raisedPlayer);
		// compound.putInt("round", round);
		// return compound;
		//
		// // ---
		//
		
		saveCardArray(compound, 0, cards_field);
		compound.putBoolean("hold0", hold[0]);
		compound.putBoolean("hold1", hold[1]);
		compound.putBoolean("hold2", hold[2]);
		compound.putBoolean("hold3", hold[3]);
		compound.putBoolean("hold4", hold[4]);
		compound.putInt("ticker",    ticker);
		compound.putInt("movestate", movestate);
		return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// private List<Card> getCards(int index){
	// 	switch (index){
	// 		case 0: return cardsP1;
	// 		case 1: return cardsP2;
	// 		case 2: return cardsP3;
	// 		case 3: return cardsP4;
	// 		case 4: return cardsP5;
	// 		case 5: return cardsP6;
	// 	} return cardsP1;
	// }
	//
	// private void draw(){
	// 	turnstate = 3;
	// 	timeout = 0;
	// 	playerCount = pot = getFirstFreePlayerSlot();
	// 	for(int y = 0; y < 2; y++){
	// 		for(int x = 0; x < pot; x++){
	// 			getCards(x).add(new Card(RANDOM, 0, 24,  8*x + 8*4*y, false));
	// 		}
	// 	}
	// 	for(int i = 0; i < 6; i++){
	// 		if(i >= getFirstFreePlayerSlot()){
	// 			folded[i] = true;
	// 		}
	// 	}
	// 	comCards[0] = new Card(RANDOM, 0, -16,      8*pot + 8*4*2, false);
	// 	comCards[1] = new Card(RANDOM, 0, -16,  8 + 8*pot + 8*4*2, false);
	// 	setJingle(SOUND_CARD_PLACE);
	// }
	//
	// private void drawAnother(){
	// 	timeout = 0;
	// 	activePlayer = (activePlayer + 1) % getFirstFreePlayerSlot();
	// 	if(activePlayer == 0){
	// 		if(raisedPlayer == -1){
	// 			for(int i = 0; i < 5; i++){
	// 				if(comCards[i].number == -1){
	// 					comCards[i] = new Card(RANDOM, 0, -24, 0, false);
	// 					break;
	// 				}
	// 			}
	// 			if(lastStanding() != -1 || comCards[4].number != -1){
	// 				result();
	// 			}
	// 		}
	// 	}
	// 	if(activePlayer == raisedPlayer){
	// 		raisedPlayer = -1;
	// 	}
	// 	setJingle(SOUND_CARD_PLACE);
	// }
	//
	// private int lastStanding(){
	// 	int count = 0;
	// 	int pos = -1;
	// 	for(int i = 0; i < 6; i++){
	// 		if(!folded[i]){
	// 			count++;
	// 			pos = i;
	// 		}
	// 	}
	// 	return count == 1 ? pos : -1;
	// }
	//
	// private void call(){
	// 	pot++;
	// 	drawAnother();
	// }
	//
	// private void raise(){
	// 	pot++;
	// 	raisedPlayer = activePlayer;
	// 	drawAnother();
	// }
	//
	// private void check(){
	// 	drawAnother();
	// }
	//
	// private void fold(){
	// 	folded[activePlayer] = true;
	// 	drawAnother();
	// }
	//
	// private void result(){
	// 	turnstate = 4;
	// 	int lastPlayer = lastStanding();
	// 	if(lastPlayer != -1){
	// 		reward[lastPlayer] = pot;
	// 	} else {
	// 		int[] finalHand = new int[]{0, 0, 0, 0, 0, 0};
	// 		for(int i = 0; i < 6; i++){
	// 			finalHand[i] = folded[i] ? 0 : sortAndClear(getCards(i).get(0), getCards(i).get(1), comCards[0], comCards[1], comCards[2], comCards[3], comCards[4]);
	// 		}
	// 		int winner = 0;
	// 		for(int i = 0; i < 6; i++){
	// 			if(finalHand[i] > finalHand[winner]){
	// 				winner = i;
	// 			}
	// 		}
	// 		reward[winner] = pot;
	// 		hand = currentPlayer[winner] + " has won the Game!";
	// 	}
	// }
	//
	// private int sortAndClear(Card card0, Card card1, Card card2, Card card3, Card card4, Card card5, Card card6){
	// 	boolean sorted = false;
	// 	Card[] tempCards = new Card[]{new Card(card0), new Card(card1), new Card(card2), new Card(card3), new Card(card4), new Card(card5), new Card(card6)};
	// 	Card temp = new Card(-1, -1);
	// 	while(!sorted){
	// 		sorted = true;
	// 		for(int i = 0; i < 5; i++){
	// 			if(tempCards[i].sortedNumber() > tempCards[i+1].sortedNumber()){
	// 				temp.set(tempCards[i]);
	// 				tempCards[i].set(tempCards[i+1]);
	// 				tempCards[i+1].set(temp);
	// 				sorted = false;
	// 			}
	// 		}
	// 	}
	// 	return clear(tempCards);
	// }
	//
	// private int clear(Card[] c){
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number == c[3].number && c[0].number == c[4].number) { return 100000 + c[0].sortedNumber(); } // 5 of a Kind
	// 	if(c[1].number == c[2].number && c[1].number == c[3].number && c[1].number == c[4].number && c[1].number == c[5].number) { return 100000 + c[1].sortedNumber(); } // 5 of a Kind
	// 	if(c[2].number == c[3].number && c[2].number == c[4].number && c[2].number == c[5].number && c[2].number == c[6].number) { return 100000 + c[2].sortedNumber(); } // 5 of a Kind
	//
	// 	if(c[0].number == 9 && c[1].number == 10 && c[2].number == 11 && c[3].number == 12 && c[4].number == 0 && c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 90000 + c[0].suit; } // Royal Flush
	// 	if(c[1].number == 9 && c[2].number == 10 && c[3].number == 11 && c[4].number == 12 && c[5].number == 0 && c[1].suit == c[2].suit && c[1].suit == c[3].suit && c[1].suit == c[4].suit && c[1].suit == c[5].suit) { return 90000 + c[1].suit; } // Royal Flush
	// 	if(c[2].number == 9 && c[3].number == 10 && c[4].number == 11 && c[5].number == 12 && c[6].number == 0 && c[2].suit == c[3].suit && c[2].suit == c[4].suit && c[2].suit == c[5].suit && c[2].suit == c[6].suit) { return 90000 + c[2].suit; } // Royal Flush
	//
	// 	if(c[0].number <= 9 && c[0].number + 1 == c[1].number && c[0].number + 2 == c[2].number && c[0].number + 3 == c[3].number && c[0].number + 4 == c[4].number && c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 80000 + c[0].sortedNumber(); } // Straight Flush
	// 	if(c[1].number <= 9 && c[1].number + 1 == c[2].number && c[1].number + 2 == c[3].number && c[1].number + 3 == c[4].number && c[1].number + 4 == c[5].number && c[1].suit == c[2].suit && c[1].suit == c[3].suit && c[1].suit == c[4].suit && c[1].suit == c[5].suit) { return 80000 + c[1].sortedNumber(); } // Straight Flush
	// 	if(c[2].number <= 9 && c[2].number + 1 == c[3].number && c[2].number + 2 == c[4].number && c[2].number + 3 == c[5].number && c[2].number + 4 == c[6].number && c[2].suit == c[3].suit && c[2].suit == c[4].suit && c[2].suit == c[5].suit && c[2].suit == c[6].suit) { return 80000 + c[2].sortedNumber(); } // Straight Flush
	//
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number == c[3].number && c[0].number != c[4].number) { return 70000 + c[0].sortedNumber(); } // 4 of a Kind
	// 	if(c[1].number == c[2].number && c[1].number == c[3].number && c[1].number == c[4].number && c[1].number != c[5].number) { return 70000 + c[1].sortedNumber(); } // 4 of a Kind
	// 	if(c[2].number == c[3].number && c[2].number == c[4].number && c[2].number == c[5].number && c[2].number != c[6].number) { return 70000 + c[2].sortedNumber(); } // 4 of a Kind
	// 	if(c[3].number == c[4].number && c[3].number == c[5].number && c[3].number == c[6].number && c[3].number != c[7].number) { return 70000 + c[3].sortedNumber(); } // 4 of a Kind
	//
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number &&     c[0].number != c[3].number     && c[3].number == c[4].number) { return 60000 + c[3].sortedNumber()*100 + c[0].sortedNumber(); } // Full House  ###++__
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number &&     c[0].number != c[4].number     && c[4].number == c[5].number) { return 60000 + c[4].sortedNumber()*100 + c[0].sortedNumber(); } // Full House  ###_++_
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number &&     c[0].number != c[5].number     && c[5].number == c[6].number) { return 60000 + c[5].sortedNumber()*100 + c[0].sortedNumber(); } // Full House  ###__++
	// 	if(c[1].number == c[2].number && c[1].number == c[3].number &&     c[1].number != c[4].number     && c[4].number == c[5].number) { return 60000 + c[4].sortedNumber()*100 + c[1].sortedNumber(); } // Full House  _###++_
	// 	if(c[1].number == c[2].number && c[1].number == c[3].number &&     c[1].number != c[5].number     && c[5].number == c[6].number) { return 60000 + c[5].sortedNumber()*100 + c[1].sortedNumber(); } // Full House  _###_++
	// 	if(c[2].number == c[3].number && c[2].number == c[4].number &&     c[2].number != c[5].number     && c[5].number == c[6].number) { return 60000 + c[5].sortedNumber()*100 + c[2].sortedNumber(); } // Full House  __###++
	// 	if(c[2].number == c[3].number && c[2].number == c[4].number &&     c[2].number != c[0].number     && c[0].number == c[1].number) { return 60000 + c[2].sortedNumber()*100 + c[0].sortedNumber(); } // Full House  ++###__
	// 	if(c[3].number == c[4].number && c[3].number == c[5].number &&     c[3].number != c[0].number     && c[0].number == c[1].number) { return 60000 + c[3].sortedNumber()*100 + c[0].sortedNumber(); } // Full House  ++_###_
	// 	if(c[3].number == c[4].number && c[3].number == c[5].number &&     c[3].number != c[1].number     && c[1].number == c[2].number) { return 60000 + c[3].sortedNumber()*100 + c[1].sortedNumber(); } // Full House  _++###_
	// 	if(c[4].number == c[5].number && c[4].number == c[6].number &&     c[4].number != c[0].number     && c[0].number == c[1].number) { return 60000 + c[4].sortedNumber()*100 + c[0].sortedNumber(); } // Full House  ++__###
	// 	if(c[4].number == c[5].number && c[4].number == c[6].number &&     c[4].number != c[1].number     && c[1].number == c[2].number) { return 60000 + c[4].sortedNumber()*100 + c[1].sortedNumber(); } // Full House  _++_###
	// 	if(c[4].number == c[5].number && c[4].number == c[6].number &&     c[4].number != c[2].number     && c[2].number == c[3].number) { return 60000 + c[4].sortedNumber()*100 + c[2].sortedNumber(); } // Full House  __++###
	//
	// 	if(c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 50000 + c[0].sortedNumber(); } // Flush
	// 	if(c[1].suit == c[2].suit && c[1].suit == c[3].suit && c[1].suit == c[4].suit && c[1].suit == c[5].suit) { return 50000 + c[1].sortedNumber(); } // Flush
	// 	if(c[2].suit == c[3].suit && c[2].suit == c[4].suit && c[2].suit == c[5].suit && c[2].suit == c[6].suit) { return 50000 + c[2].sortedNumber(); } // Flush
	//
	// 	if(c[0].number <= 9 && c[0].number + 1 == c[1].number && c[0].number + 2 == c[2].number && c[0].number + 3 == c[3].number && c[0].number + 4 == c[4].number) { return 40000 + c[0].sortedNumber(); } // Straight
	// 	if(c[1].number <= 9 && c[1].number + 1 == c[2].number && c[1].number + 2 == c[3].number && c[1].number + 3 == c[4].number && c[1].number + 4 == c[5].number) { return 40000 + c[1].sortedNumber(); } // Straight
	// 	if(c[2].number <= 9 && c[2].number + 1 == c[3].number && c[2].number + 2 == c[4].number && c[2].number + 3 == c[5].number && c[2].number + 4 == c[6].number) { return 40000 + c[2].sortedNumber(); } // Straight
	//
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number != c[3].number && c[0].number != c[4].number) {  return 30000 + c[1].sortedNumber()*100 + c[6].sortedNumber(); } // 3 of a Kind
	// 	if(c[1].number == c[2].number && c[1].number == c[3].number && c[1].number != c[4].number && c[1].number != c[5].number) {  return 30000 + c[2].sortedNumber()*100 + c[6].sortedNumber(); } // 3 of a Kind
	// 	if(c[2].number == c[3].number && c[2].number == c[4].number && c[2].number != c[5].number && c[2].number != c[6].number) {  return 30000 + c[3].sortedNumber()*100 + c[6].sortedNumber(); } // 3 of a Kind
	// 	if(c[3].number == c[4].number && c[3].number == c[5].number && c[3].number != c[6].number && c[3].number != c[0].number) {  return 30000 + c[4].sortedNumber()*100 + c[6].sortedNumber(); } // 3 of a Kind
	// 	if(c[4].number == c[5].number && c[4].number == c[6].number && c[4].number != c[0].number && c[4].number != c[1].number) {  return 30000 + c[5].sortedNumber()*100 + c[1].sortedNumber(); } // 3 of a Kind
	//
	// 	if(c[0].number == c[1].number && c[2].number == c[3].number) { return 20000 + c[2].sortedNumber()*100 + c[0].sortedNumber(); } // 2 Pair  ##++___
	// 	if(c[0].number == c[1].number && c[3].number == c[4].number) { return 20000 + c[3].sortedNumber()*100 + c[0].sortedNumber(); } // 2 Pair  ##_++__
	// 	if(c[0].number == c[1].number && c[4].number == c[5].number) { return 20000 + c[4].sortedNumber()*100 + c[0].sortedNumber(); } // 2 Pair  ##__++_
	// 	if(c[0].number == c[1].number && c[5].number == c[6].number) { return 20000 + c[5].sortedNumber()*100 + c[0].sortedNumber(); } // 2 Pair  ##___++
	// 	if(c[1].number == c[2].number && c[3].number == c[4].number) { return 20000 + c[3].sortedNumber()*100 + c[1].sortedNumber(); } // 2 Pair  _##++__
	// 	if(c[1].number == c[2].number && c[4].number == c[5].number) { return 20000 + c[4].sortedNumber()*100 + c[1].sortedNumber(); } // 2 Pair  _##_++_
	// 	if(c[1].number == c[2].number && c[5].number == c[6].number) { return 20000 + c[5].sortedNumber()*100 + c[1].sortedNumber(); } // 2 Pair  _##__++
	// 	if(c[2].number == c[3].number && c[4].number == c[5].number) { return 20000 + c[4].sortedNumber()*100 + c[2].sortedNumber(); } // 2 Pair  __##++_
	// 	if(c[2].number == c[3].number && c[5].number == c[6].number) { return 20000 + c[5].sortedNumber()*100 + c[2].sortedNumber(); } // 2 Pair  __##_++
	// 	if(c[3].number == c[4].number && c[5].number == c[6].number) { return 20000 + c[5].sortedNumber()*100 + c[3].sortedNumber(); } // 2 Pair  ___##++
	//
	// 	if(c[0].number == c[1].number) {  return 10000 + c[0].sortedNumber() + c[6].sortedNumber(); } // 1 Pair
	// 	if(c[1].number == c[2].number) {  return 10000 + c[1].sortedNumber() + c[6].sortedNumber(); } // 1 Pair
	// 	if(c[2].number == c[3].number) {  return 10000 + c[2].sortedNumber() + c[6].sortedNumber(); } // 1 Pair
	// 	if(c[3].number == c[4].number) {  return 10000 + c[3].sortedNumber() + c[6].sortedNumber(); } // 1 Pair
	// 	if(c[4].number == c[5].number) {  return 10000 + c[4].sortedNumber() + c[6].sortedNumber(); } // 1 Pair
	// 	if(c[5].number == c[6].number) {  return 10000 + c[5].sortedNumber() + c[4].sortedNumber(); } // 1 Pair
	//
	// 	int highestNumber = 0;
	// 	for(int i = 0; i < 7; i++){
	// 		if(c[i].sortedNumber() > highestNumber){
	// 			highestNumber = c[i].sortedNumber();
	// 		}
	// 	}
	// 	return highestNumber;
	// }
	//
	// public List<Card> getCards(int index){
	// 	switch (index){
	// 		case 0: return cardsP1;
	// 		case 1: return cardsP2;
	// 		case 2: return cardsP3;
	// 		case 3: return cardsP4;
	// 		case 4: return cardsP5;
	// 		case 5: return cardsP6;
	// 	}
	// 	return cardsP1;
	// }
	//
	// private void draw(){
	// 	turnstate = 3;
	// 	timeout = 0;
	// 	playerCount = pot = getFirstFreePlayerSlot();
	// 	for(int y = 0; y < 5; y++){
	// 		for(int x = 0; x < pot; x++){
	// 			getCards(x).add(new Card(RANDOM, 0, 24,  8*x + 8*4*y, false));
	// 		}
	// 	}
	// 	for(int i = 0; i < 6; i++){
	// 		if(i >= getFirstFreePlayerSlot()){
	// 			folded[i] = true;
	// 		}
	// 	}
	// 	setJingle(SOUND_CARD_PLACE);
	// }
	//
	// private void drawAnother(){
	// 	for(int y = 0; y < 5; y++){
	// 		if(getCards(activePlayer).get(y).hidden){
	// 			getCards(activePlayer).remove(y);
	// 			getCards(activePlayer).add(new Card(RANDOM, 0, 24,  0, false));
	// 			y--;
	// 		}
	// 	}
	// 	timeout = 0;
	// 	activePlayer = (activePlayer + 1) % getFirstFreePlayerSlot();
	// 	if(activePlayer == 0){
	// 		if(raisedPlayer == -1){
	// 			round++;
	// 			if(round == 3){
	// 				result();
	// 			}
	// 		}
	// 	}
	// 	if(activePlayer == raisedPlayer){
	// 		raisedPlayer = -1;
	// 	}
	// 	setJingle(SOUND_CARD_PLACE);
	// }
	//
	// private int lastStanding(){
	// 	int count = 0;
	// 	int pos = -1;
	// 	for(int i = 0; i < 6; i++){
	// 		if(!folded[i]){
	// 			count++;
	// 			pos = i;
	// 		}
	// 	}
	// 	return count == 1 ? pos : -1;
	// }
	//
	// private void call(){
	// 	pot++;
	// 	drawAnother();
	// }
	//
	// private void raise(){
	// 	pot++;
	// 	raisedPlayer = activePlayer;
	// 	drawAnother();
	// }
	//
	// private void check(){
	// 	drawAnother();
	// }
	//
	// private void fold(){
	// 	folded[activePlayer] = true;
	// 	drawAnother();
	// }
	//
	// private void result(){
	// 	turnstate = 4;
	// 	int lastPlayer = lastStanding();
	// 	if(lastPlayer != -1){
	// 		reward[lastPlayer] = pot;
	// 	} else {
	// 		int[] finalHand = new int[]{0, 0, 0, 0, 0, 0};
	// 		for(int i = 0; i < 6; i++){
	// 			if(getCards(i).size() < 5){
	// 				folded[i] = true;
	// 			}
	// 			finalHand[i] = folded[i] ? 0 : sortAndClear(getCards(i).get(0), getCards(i).get(1), getCards(i).get(2), getCards(i).get(3), getCards(i).get(4));
	// 		}
	// 		int winner = 0;
	// 		for(int i = 0; i < 6; i++){
	// 			if(finalHand[i] > finalHand[winner]){
	// 				winner = i;
	// 			}
	// 		}
	// 		reward[winner] = pot;
	// 		hand = currentPlayer[winner] + " has won the Game!";
	// 	}
	// }
	//
	// private int sortAndClear(Card card0, Card card1, Card card2, Card card3, Card card4){
	// 	boolean sorted = false;
	// 	Card[] tempCards = new Card[]{new Card(card0), new Card(card1), new Card(card2), new Card(card3), new Card(card4)};
	// 	Card temp = new Card(-1, -1);
	// 	while(!sorted){
	// 		sorted = true;
	// 		for(int i = 0; i < 3; i++){
	// 			if(tempCards[i].sortedNumber() > tempCards[i+1].sortedNumber()){
	// 				temp.set(tempCards[i]);
	// 				tempCards[i].set(tempCards[i+1]);
	// 				tempCards[i+1].set(temp);
	// 				sorted = false;
	// 			}
	// 		}
	// 	}
	// 	return clear(tempCards);
	// }
	//
	// private int clear(Card[] c){
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number == c[3].number && c[0].number == c[4].number) { return 100000 + c[0].sortedNumber(); } // 5 of a Kind
	//
	// 	if(c[0].number == 9 && c[1].number == 10 && c[2].number == 11 && c[3].number == 12 && c[4].number == 0 && c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 90000 + c[0].suit; } // Royal Flush
	//
	// 	if(c[0].number <= 9 && c[0].number + 1 == c[1].number && c[0].number + 2 == c[2].number && c[0].number + 3 == c[3].number && c[0].number + 4 == c[4].number && c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 80000 + c[0].sortedNumber(); } // Straight Flush
	//
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number == c[3].number && c[0].number != c[4].number) { return 70000 + c[0].sortedNumber(); } // 4 of a Kind
	// 	if(c[1].number == c[2].number && c[1].number == c[3].number && c[1].number == c[4].number && c[1].number != c[0].number) { return 70000 + c[1].sortedNumber(); } // 4 of a Kind
	//
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number &&     c[0].number != c[3].number     && c[3].number == c[4].number) { return 60000 + c[3].sortedNumber()*100 + c[0].sortedNumber(); } // Full House  ###++
	// 	if(c[2].number == c[3].number && c[2].number == c[4].number &&     c[0].number != c[3].number     && c[0].number == c[1].number) { return 60000 + c[0].sortedNumber()*100 + c[3].sortedNumber(); } // Full House  ++###
	//
	// 	if(c[0].suit == c[1].suit && c[0].suit == c[2].suit && c[0].suit == c[3].suit && c[0].suit == c[4].suit) { return 50000 + c[0].sortedNumber(); } // Flush
	//
	// 	if(c[0].number <= 9 && c[0].number + 1 == c[1].number && c[0].number + 2 == c[2].number && c[0].number + 3 == c[3].number && c[0].number + 4 == c[4].number) { return 40000 + c[0].sortedNumber(); } // Straight
	//
	// 	if(c[0].number == c[1].number && c[0].number == c[2].number && c[0].number != c[3].number && c[0].number != c[4].number) {  return 30000 + c[1].sortedNumber()*100 + c[4].sortedNumber(); } // 3 of a Kind
	// 	if(c[1].number == c[2].number && c[1].number == c[3].number && c[1].number != c[0].number && c[1].number != c[4].number) {  return 30000 + c[2].sortedNumber()*100 + c[4].sortedNumber(); } // 3 of a Kind
	// 	if(c[2].number == c[3].number && c[2].number == c[4].number && c[2].number != c[0].number && c[2].number != c[1].number) {  return 30000 + c[3].sortedNumber()*100 + c[1].sortedNumber(); } // 3 of a Kind
	//
	// 	if(c[0].number == c[1].number && c[2].number == c[3].number) { return 20000 + c[2].sortedNumber()*100 + c[0].sortedNumber(); } // 2 Pair  ##++_
	// 	if(c[0].number == c[1].number && c[3].number == c[4].number) { return 20000 + c[3].sortedNumber()*100 + c[0].sortedNumber(); } // 2 Pair  ##_++
	// 	if(c[1].number == c[2].number && c[3].number == c[4].number) { return 20000 + c[3].sortedNumber()*100 + c[1].sortedNumber(); } // 2 Pair  _##++
	//
	// 	if(c[0].number == c[1].number) {  return 10000 + c[0].sortedNumber() + c[4].sortedNumber(); } // 1 Pair
	// 	if(c[1].number == c[2].number) {  return 10000 + c[1].sortedNumber() + c[4].sortedNumber(); } // 1 Pair
	// 	if(c[2].number == c[3].number) {  return 10000 + c[2].sortedNumber() + c[4].sortedNumber(); } // 1 Pair
	// 	if(c[3].number == c[4].number) {  return 10000 + c[3].sortedNumber() + c[2].sortedNumber(); } // 1 Pair
	//
	// 	int highestNumber = 0;
	// 	for(int i = 0; i < 5; i++){
	// 		if(c[i].sortedNumber() > highestNumber){
	// 			highestNumber = c[i].sortedNumber();
	// 		}
	// 	}
	// 	return highestNumber;
	// }
	//
	
	private void sort() {
		Card[] card = new Card[5];
		card[0] = cards_field[0];
		card[1] = cards_field[1];
		card[2] = cards_field[2];
		card[3] = cards_field[3];
		card[4] = cards_field[4];
		Card z = new Card(-1, -1);
		if(card[0].sortedNumber() > card[4].sortedNumber()) { z.set(card[0]); card[0].set(card[1]); card[1].set(card[2]); card[2].set(card[3]); card[3].set(card[4]); card[4].set(z); }
		if(card[0].sortedNumber() > card[3].sortedNumber()) { z.set(card[0]); card[0].set(card[1]); card[1].set(card[2]); card[2].set(card[3]); card[3].set(                      z); }
		if(card[0].sortedNumber() > card[2].sortedNumber()) { z.set(card[0]); card[0].set(card[1]); card[1].set(card[2]); card[2].set(                                            z); }
		if(card[0].sortedNumber() > card[1].sortedNumber()) { z.set(card[0]); card[0].set(card[1]); card[1].set(                                                                  z); }
		if(card[1].sortedNumber() > card[4].sortedNumber()) { z.set(                      card[1]); card[1].set(card[2]); card[2].set(card[3]); card[3].set(card[4]); card[4].set(z); }
		if(card[1].sortedNumber() > card[3].sortedNumber()) { z.set(                      card[1]); card[1].set(card[2]); card[2].set(card[3]); card[3].set(                      z); }
		if(card[1].sortedNumber() > card[2].sortedNumber()) { z.set(                      card[1]); card[1].set(card[2]); card[2].set(                                            z); }
		if(card[2].sortedNumber() > card[4].sortedNumber()) { z.set(                                            card[2]); card[2].set(card[3]); card[3].set(card[4]); card[4].set(z); }
		if(card[2].sortedNumber() > card[3].sortedNumber()) { z.set(                                            card[2]); card[2].set(card[3]); card[3].set(                      z); }
		if(card[3].sortedNumber() > card[4].sortedNumber()) { z.set(                                                                  card[3]); card[3].set(card[4]); card[4].set(z); }
			cards_field[0].set(card[0]);
		cards_field[1].set(card[1]);
		cards_field[2].set(card[2]);
		cards_field[3].set(card[3]);
		cards_field[4].set(card[4]);
			cards_field[0].shiftX =  44*2;
		cards_field[1].shiftX =  44;
		cards_field[2].shiftX =   0;
		cards_field[3].shiftX = -44;
		cards_field[4].shiftX = -44*2;
	}
	
	private void result() {
		if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number == cards_field[3].number && cards_field[0].number == cards_field[4].number) {
			hand = "5 of a Kind";
			reward[0] = 20;
		} else if(cards_field[0].number == 9 && cards_field[1].number == 10 && cards_field[2].number == 11 && cards_field[3].number == 12 && cards_field[4].number == 0 && cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
			hand = "ROYAL FLUSH!!";
			reward[0] = 16;
		} else if(cards_field[0].number <= 9 && cards_field[0].number + 1 == cards_field[1].number && cards_field[0].number + 2 == cards_field[2].number && cards_field[0].number + 3 == cards_field[3].number && cards_field[0].number + 4 == cards_field[4].number && cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
			hand = "Straight Flush";
			reward[0] = 12;
		} else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number == cards_field[3].number && cards_field[0].number != cards_field[4].number) {
			hand = "4 of a Kind";
			reward[0] = 10;
		} else if(cards_field[1].number == cards_field[2].number && cards_field[1].number == cards_field[3].number && cards_field[1].number == cards_field[4].number && cards_field[1].number != cards_field[0].number) {
			hand = "4 of a Kind";
			reward[0] = 10;
		} else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number != cards_field[3].number && cards_field[3].number == cards_field[4].number) {
			hand = "Full House";
			reward[0] = 8;
		} else if(cards_field[0].number == cards_field[1].number && cards_field[0].number != cards_field[2].number && cards_field[2].number == cards_field[3].number && cards_field[2].number == cards_field[4].number) {
			hand = "Full House";
			reward[0] = 8;
		} else if(cards_field[0].suit == cards_field[1].suit && cards_field[0].suit == cards_field[2].suit && cards_field[0].suit == cards_field[3].suit && cards_field[0].suit == cards_field[4].suit) {
			hand = "Flush";
			reward[0] = 7;
		} else if(cards_field[0].number <= 9 && cards_field[0].number + 1 == cards_field[1].number && cards_field[0].number + 2 == cards_field[2].number && cards_field[0].number + 3 == cards_field[3].number && cards_field[0].number + 4 == cards_field[4].number) {
			hand = "Straight";
			reward[0] = 6;
		} else if(cards_field[0].number == cards_field[1].number && cards_field[0].number == cards_field[2].number && cards_field[0].number != cards_field[3].number && cards_field[0].number != cards_field[4].number) {
			hand = "3 of a Kind";
			reward[0] = 4;
		} else if(cards_field[1].number == cards_field[2].number && cards_field[1].number == cards_field[3].number && cards_field[1].number != cards_field[0].number && cards_field[1].number != cards_field[4].number) {
			hand = "3 of a Kind";
			reward[0] = 4;
		} else if(cards_field[2].number == cards_field[3].number && cards_field[2].number == cards_field[4].number && cards_field[2].number != cards_field[0].number && cards_field[2].number != cards_field[1].number) {
			hand = "3 of a Kind";
			reward[0] = 4;
		} else if(cards_field[0].number == cards_field[1].number && cards_field[2].number == cards_field[3].number) {
			hand = "Two Pair";
			reward[0] = 2;
		} else if(cards_field[0].number == cards_field[1].number && cards_field[3].number == cards_field[4].number) {
			hand = "Two Pair";
			reward[0] = 2;
		} else if(cards_field[1].number == cards_field[2].number && cards_field[3].number == cards_field[4].number) {
			hand = "Two Pair";
			reward[0] = 2;
		} else if((cards_field[0].number >= 10 || cards_field[0].number == 0) && cards_field[0].number == cards_field[1].number) {
			hand = "Jacks or Better";
			reward[0] = 1;
		} else if((cards_field[1].number >= 10 || cards_field[1].number == 0) && cards_field[1].number == cards_field[2].number) {
			hand = "Jacks or Better";
			reward[0] = 1;
		} else if((cards_field[2].number >= 10 || cards_field[2].number == 0) && cards_field[2].number == cards_field[3].number) {
			hand = "Jacks or Better";
			reward[0] = 1;
		} else if((cards_field[3].number >= 10 || cards_field[3].number == 0) && cards_field[3].number == cards_field[4].number) {
			hand = "Jacks or Better";
			reward[0] = 1;
		}
		if(reward[0] >= 2){
			jingle = SOUND_REWARD;
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return false;
	}
	
	public boolean isMultiplayer(){
		return true;
	}
	
	public int getID(){
		return 15;
	}
	
	public String getName(){
		return "poker";
	}
	
	
	
}