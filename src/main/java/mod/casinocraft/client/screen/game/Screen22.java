package mod.casinocraft.client.screen.game;

import mod.casinocraft.Register;
import mod.casinocraft.client.logic.game.Logic22;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import mod.casinocraft.util.mapping.ButtonMap;
import mod.lucky77.util.Vector2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen22 extends ScreenCasino {   //  Poker
	
	// int playerPos;
	//
	// // ---
	//
	
	int playerPos;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen22(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic22 logic(){
		return (Logic22) menu.logic();
	}
	
	protected String getGameName() {
		return "poker";
	}
	
	protected void createGameButtons(){
		// buttonSet.addButton(ButtonMap.POS_BOT_LEFT2,  ButtonMap.RAISE, () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer == -1 && playerToken >= bet, () -> { if(playerToken >= bet){ collectBet(); action(1); } } );
		// buttonSet.addButton(ButtonMap.POS_BOT_RIGHT2, ButtonMap.CALL,  () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer >  -1 && playerToken >= bet, () -> { if(playerToken >= bet){ collectBet(); action(0); } } );
		// buttonSet.addButton(ButtonMap.POS_BOT_RIGHT2, ButtonMap.CHECK, () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer == -1,                       () ->                                         action(2));
		// buttonSet.addButton(ButtonMap.POS_BOT_MIDDLE, ButtonMap.FOLD,  () -> isActivePlayer() && logic().turnstate == 3,                                                     () ->                                         action(3));
		// playerPos = -2;
		//
		// // ---
		//
		// buttonSet.addButton(ButtonMap.POS_BOT_LEFT2,  ButtonMap.RAISE, () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer == -1 && logic().round != 1, () -> { if(playerToken >= bet){ collectBet(); action(1); } } );
		// buttonSet.addButton(ButtonMap.POS_BOT_RIGHT2, ButtonMap.CALL,  () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer >  -1 && playerToken >= bet, () -> { if(playerToken >= bet){ collectBet(); action(0); } } );
		// buttonSet.addButton(ButtonMap.POS_BOT_RIGHT2, ButtonMap.CHECK, () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer == -1,                       () ->                                         action(2));
		// buttonSet.addButton(ButtonMap.POS_BOT_MIDDLE, ButtonMap.FOLD,  () -> isActivePlayer() && logic().turnstate == 3,                                                     () ->                                         action(3));
		// playerPos = -2;
		//
		// // ---
		//
		
		buttonSet.addButton(0, new Vector2( 20, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(0));
		buttonSet.addButton(0, new Vector2( 64, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(1));
		buttonSet.addButton(0, new Vector2(108, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(2));
		buttonSet.addButton(0, new Vector2(152, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(3));
		buttonSet.addButton(0, new Vector2(196, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(4));
		buttonSet.addButton(0, ButtonMap.POS_MID_MIDDLE,                 ButtonMap.DRAW,    ButtonMap.DRAW, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1,      () -> isActivePlayer() && logic().turnstate == 2, () -> action(5));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		// if(logic().turnstate == 3){
		// 	if(logic().round == 1){
		// 		int x = logic().getFirstFreePlayerSlot() < 5 ? 128 : 32;
		// 		int playerPos = getPlayerPosition();
		// 		for(int i = 0; i < logic().getCards(playerPos).size(); i++){
		// 			if(mouseRect(x - logic().getCards(playerPos).size()*16 + 32*i, 188, 32, 48, mouseX, mouseY)){ action(4+i); }
		// 		}
		// 	}
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		// if(logic().turnstate == 2                     ){ drawFontCenter(matrix, "Waiting for Players",       128,  88); }
		// if(logic().turnstate == 3 && !isActivePlayer()){ drawFontCenter(matrix, "...",                       128,  88); }
		// if(logic().turnstate == 3 &&  isActivePlayer()){ drawFontCenter(matrix, "Choose an option ...",      128,  88); }
		// if(logic().turnstate == 3                     ){ drawFontCenter(matrix, "POT:  " + (logic().pot*bet), 96, 160); }
		// if(logic().turnstate >= 4                     ){ drawFontCenter(matrix, logic().hand,                128,  88); }
		// drawTimer(matrix);
		//
		// // ---
		//
		// if(logic().turnstate == 2                                           ){ drawFontCenter(matrix, "Waiting for Players",            128,  88); }
		// if(logic().turnstate == 3 && !isActivePlayer()                      ){ drawFontCenter(matrix, "...",                            128,  88); }
		// if(logic().turnstate == 3 &&  isActivePlayer() && logic().round != 1){ drawFontCenter(matrix, "Choose an option ...",           128,  88); }
		// if(logic().turnstate == 3 &&  isActivePlayer() && logic().round == 1){ drawFontCenter(matrix, "You can change Cards now ...",   128,  88); }
		// if(logic().turnstate == 3                                           ){ drawFontCenter(matrix, "POT:  " + (logic().pot*bet),      96, 160); }
		// if(logic().turnstate == 3                                           ){ drawFontCenter(matrix, "ROUND:  " + (logic().round + 1), 160, 160); }
		// if(logic().turnstate >= 4                                           ){ drawFontCenter(matrix, logic().hand,                     128,  88); }
		// drawTimer(matrix);
		//
		// // ---
		//
		
		if(logic().turnstate == 2 &&  isActivePlayer()){ drawFontCenter(matrix, "choose cards to keep ...", 128, 192); }
		if(logic().turnstate == 2 && !isActivePlayer()){ drawFontCenter(matrix, "...",                      128, 192); }
		if(logic().turnstate == 3                     ){ drawFontCenter(matrix, "...",                      128, 192); }
		if(logic().turnstate >= 4                     ){ drawFontCenter(matrix, logic().hand,               128, 192); }
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		// if(playerPos >  -2 && logic().turnstate == 2) playerPos = -2;
		// if(playerPos == -2 && logic().turnstate == 3) playerPos = getPlayerPosition();
		// drawCardBack(matrix,  48, 104, 7);
		// drawCardBack(matrix,  80, 104, 7);
		// drawCardBack(matrix, 112, 104, 7);
		// drawCardBack(matrix, 144, 104, 7);
		// drawCardBack(matrix, 176, 104, 7);
		// if(logic().comCards[0].idletimer == 0) drawCard(matrix,  48, 104, logic().comCards[0]);
		// if(logic().comCards[1].idletimer == 0) drawCard(matrix,  80, 104, logic().comCards[1]);
		// if(logic().comCards[2].idletimer == 0) drawCard(matrix, 112, 104, logic().comCards[2]);
		// if(logic().comCards[3].idletimer == 0) drawCard(matrix, 144, 104, logic().comCards[3]);
		// if(logic().comCards[4].idletimer == 0) drawCard(matrix, 176, 104, logic().comCards[4]);
		//
		// int  left = tableID == 1 ? 7 : -89;
		// int right = tableID == 1 ? 217 : 313;
		// switch (logic().playerCount){
		// 	case 1:
		// 		/* DOWN   */ drawCard2(matrix, 128, 193, false, playerPos, 0);
		// 		break;
		// 	case 2:
		// 		/* DOWN   */ drawCard2(matrix, 128, 193, false, playerPos,  playerPos);
		// 		/* UP     */ drawCard2(matrix, 128,   7, false, playerPos, (playerPos + 1) % 2);
		// 		break;
		// 	case 3:
		// 		/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
		// 		/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 3);
		// 		/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 2) % 3);
		// 		break;
		// 	case 4:
		// 		/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
		// 		/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 4);
		// 		/* UP     */ drawCard2(matrix,   128,   7, false, playerPos, (playerPos + 2) % 4);
		// 		/* RIGHT  */ drawCard2(matrix, right, 144, true,  playerPos, (playerPos + 3) % 4);
		// 		break;
		// 	case 5:
		// 		/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
		// 		/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 5);
		// 		/* UP_L   */ drawCard2(matrix,    48,   7, false, playerPos, (playerPos + 2) % 5);
		// 		/* UP_R   */ drawCard2(matrix,   208,   7, false, playerPos, (playerPos + 3) % 5);
		// 		/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 4) % 5);
		// 		break;
		// 	case 6:
		// 		/* DOWN_L */ drawCard2(matrix,    32, 193, false, playerPos,  playerPos);
		// 		/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 6);
		// 		/* UP_L   */ drawCard2(matrix,    48,   7, false, playerPos, (playerPos + 2) % 6);
		// 		/* UP_R   */ drawCard2(matrix,   208,   7, false, playerPos, (playerPos + 3) % 6);
		// 		/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 4) % 6);
		// 		/* DOWN_R */ drawCard2(matrix,   208, 201, false, playerPos, (playerPos + 5) % 6);
		// 		break;
		// }
		//
		// // ---
		//
		// if(playerPos >  -2 && logic().turnstate == 2) playerPos = -2;
		// if(playerPos == -2 && logic().turnstate == 3) playerPos = getPlayerPosition();
		// int  left = tableID == 1 ? 7 : -89;
		// int right = tableID == 1 ? 217 : 313;
		// switch (logic().playerCount){
		// 	case 1:
		// 		/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos, 0);
		// 		break;
		// 	case 2:
		// 		/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
		// 		/* UP     */ drawCard2(matrix,   128,   7, false, playerPos, (playerPos + 1) % 2);
		// 		break;
		// 	case 3:
		// 		/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
		// 		/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 3);
		// 		/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 2) % 3);
		// 		break;
		// 	case 4:
		// 		/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
		// 		/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 4);
		// 		/* UP     */ drawCard2(matrix,   128,   7, false, playerPos, (playerPos + 2) % 4);
		// 		/* RIGHT  */ drawCard2(matrix, right, 144, true,  playerPos, (playerPos + 3) % 4);
		// 		break;
		// 	case 5:
		// 		/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
		// 		/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 5);
		// 		/* UP_L   */ drawCard2(matrix,    48,   7, false, playerPos, (playerPos + 2) % 5);
		// 		/* UP_R   */ drawCard2(matrix,   208,   7, false, playerPos, (playerPos + 3) % 5);
		// 		/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 4) % 5);
		// 		break;
		// 	case 6:
		// 		/* DOWN_L */ drawCard2(matrix,    32, 193, false, playerPos,  playerPos);
		// 		/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 6);
		// 		/* UP_L   */ drawCard2(matrix,    48,   7, false, playerPos, (playerPos + 2) % 6);
		// 		/* UP_R   */ drawCard2(matrix,   208,   7, false, playerPos, (playerPos + 3) % 6);
		// 		/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 4) % 6);
		// 		/* DOWN_R */ drawCard2(matrix,   208, 201, false, playerPos, (playerPos + 5) % 6);
		// 		break;
		// }
		//
		// // ---
		//
		
		drawCardBack(matrix,  27, 72, 7); // Card 1
		drawCardBack(matrix,  70, 72, 7); // Card 2
		drawCardBack(matrix, 113, 72, 7); // Card 3
		drawCardBack(matrix, 156, 72, 7); // Card 4
		drawCardBack(matrix, 199, 72, 7); // Card 5
		drawCard(matrix,  27, 72, logic().cards_field[0]); // Card 1
		drawCard(matrix,  70, 72, logic().cards_field[1]); // Card 2
		drawCard(matrix, 113, 72, logic().cards_field[2]); // Card 3
		drawCard(matrix, 156, 72, logic().cards_field[3]); // Card 4
		drawCard(matrix, 199, 72, logic().cards_field[4]); // Card 5
		if(logic().turnstate == 3){
			// RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
			matrix.blit(Register.TEXTURE_BUTTONS, leftPos + 89, topPos + 212, 0, 176, 78, 22);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// private void drawCard2(GuiGraphics matrix, int posX, int posY, boolean sideways, int playerPos, int cardPos){
	// 	if(playerPos < 0) return;
	// 	int i = 0;
	// 	for(Card card : logic().getCards(cardPos)){
	// 		if(card.suit != -1 && card.idletimer == 0){
	// 			boolean hidden = logic().turnstate > 3 ? false : playerPos != cardPos;
	// 			// RenderSystem.setShaderTexture(0, getCardsTexture(hidden || card.suit >= 2));
	// 			int texX = card.suit == -1 || hidden ? cardPos+1 : card.number % 8;
	// 			int texY = card.suit == -1 || hidden ?         4 : (card.suit  % 2) * 2 + card.number / 8;
	// 			if(Configuration.CASINO.config_animated_cards.get() && !hidden){
	// 				if(card.number >= 10){
	// 					if(logic().frame == card.suit*12 + (card.number-10)*3){
	// 						texX += 3;
	// 					}
	// 				}
	// 			}
	// 			if(sideways){
	// 				matrix.blit(getCardsTexture(hidden || card.suit >= 2), leftPos + posX + card.shiftX, topPos + posY - logic().getCards(cardPos).size()*12 + i*20 + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
	// 			} else {
	// 				matrix.blit(getCardsTexture(hidden || card.suit >= 2), leftPos + posX + card.shiftX - logic().getCards(cardPos).size()*16 + i*32, topPos + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
	// 			}
	// 		}
	// 		i++;
	// 	}
	// }
	//
	// private void drawCard2(GuiGraphics matrix, int posX, int posY, boolean sideways, int playerPos, int cardPos){
	// 	if(playerPos < 0) return;
	// 	int i = 0;
	// 	for(Card card : logic().getCards(cardPos)){
	// 		if(card.suit != -1 && card.idletimer == 0){
	// 			boolean hidden = logic().turnstate > 3 ? false : card.hidden || playerPos != cardPos;
	// 			// RenderSystem.setShaderTexture(0, getCardsTexture(hidden || card.suit >= 2));
	// 			int texX = card.suit == -1 || hidden ? cardPos+1 : card.number % 8;
	// 			int texY = card.suit == -1 || hidden ?         4 : (card.suit  % 2) * 2 + card.number / 8;
	// 			if(Configuration.CASINO.config_animated_cards.get() && !hidden){
	// 				if(card.number >= 10){
	// 					if(logic().frame == card.suit*12 + (card.number-10)*3){
	// 						texX += 3;
	// 					}
	// 				}
	// 			}
	// 			if(sideways){
	// 				matrix.blit(getCardsTexture(hidden || card.suit >= 2), leftPos + posX + card.shiftX, topPos + posY - logic().getCards(cardPos).size()*12 + i*20 + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
	// 			} else {
	// 				matrix.blit(getCardsTexture(hidden || card.suit >= 2), leftPos + posX + card.shiftX - logic().getCards(cardPos).size()*16 + i*32, topPos + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
	// 			}
	// 		}
	// 		i++;
	// 	}
	// }
	
	
	
}