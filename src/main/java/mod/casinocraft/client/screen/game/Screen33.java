package mod.casinocraft.client.screen.game;

import mod.casinocraft.Configuration;
import mod.casinocraft.client.logic.game.Logic33;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.mapping.ButtonMap;
import mod.lucky77.util.Vector2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen33 extends ScreenCasino {   //  Mau Mau
	
	int playerPos;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen33(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic33 logic(){
		return (Logic33) menu.logic();
	}
	
	protected void createGameButtons(){
		buttonSet.addButton(0, ButtonMap.POS_BOT_MIDDLE, ButtonMap.DRAW, ButtonMap.DRAW, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1, () -> isActivePlayer() && logic().turnstate == 3 && (logic().forcedAction == 0 || logic().forcedAction == 2), () -> action(-1));
		buttonSet.addButton(0, ButtonMap.POS_BOT_MIDDLE, ButtonMap.WAIT, ButtonMap.WAIT, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1, () -> isActivePlayer() && logic().turnstate == 3 &&  logic().forcedAction == 1,                               () -> action(-1));
		buttonSet.addButton(0, new Vector2( 50, 237), ButtonMap.COLOR_RED, ButtonMap.COLOR_RED, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 10, () -> isActivePlayer() && logic().turnstate == 3 && logic().forcedAction == 3, () -> action(-5));
		buttonSet.addButton(0, new Vector2( 89, 237), ButtonMap.COLOR_YELLOW, ButtonMap.COLOR_YELLOW, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 10, () -> isActivePlayer() && logic().turnstate == 3 && logic().forcedAction == 3, () -> action(-4));
		buttonSet.addButton(0, new Vector2(128, 237), ButtonMap.COLOR_BLUE,   ButtonMap.COLOR_BLUE, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 10, () -> isActivePlayer() && logic().turnstate == 3 && logic().forcedAction == 3, () -> action(-3));
		buttonSet.addButton(0, new Vector2(167, 237), ButtonMap.COLOR_GREEN,  ButtonMap.COLOR_GREEN, ButtonMap.LIGHT_BASIC, ButtonMap.SIZE_BASIC, 10, () -> isActivePlayer() && logic().turnstate == 3 && logic().forcedAction == 3, () -> action(-2));
		playerPos = -2;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		if(logic().turnstate == 3){
			if(logic().forcedAction == 0){
				int x = logic().getFirstFreePlayerSlot() < 5 ? 128 : 128-80;
				int playerPos = getPlayerPosition();
				for(int i = 0; i < logic().getCards(playerPos).size(); i++){
					if(mouseRect(x - logic().getCards(playerPos).size()*16 + 32*i, 188, 32, 48, mouseX, mouseY)){ action(i); }
				}
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		if(logic().turnstate == 2                                                  ){ drawFontCenter(matrix, "Waiting for Players",        128, 88); }
		if(logic().turnstate == 3 && !isActivePlayer()                             ){ drawFontCenter(matrix, "...",                        128, 88); }
		if(logic().turnstate == 3 &&  isActivePlayer() && logic().forcedAction == 0){ drawFontCenter(matrix, "Choose a Card to play ...",  128, 88); }
		if(logic().turnstate == 3 &&  isActivePlayer() && logic().forcedAction == 1){ drawFontCenter(matrix, "You are forced to wait ...", 128, 88); }
		if(logic().turnstate == 3 &&  isActivePlayer() && logic().forcedAction == 2){ drawFontCenter(matrix, "Draw 2 Cards ...",           128, 88); }
		if(logic().turnstate == 3 &&  isActivePlayer() && logic().forcedAction == 3){ drawFontCenter(matrix, "Choose a color ...",         128, 88); }
		if(logic().turnstate >= 4                                                  ){ drawFontCenter(matrix, logic().hand,                 128, 88); }
		drawTimer(matrix);
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		if(playerPos >  -2 && logic().turnstate == 2) playerPos = -2;
		if(playerPos == -2 && logic().turnstate == 3) playerPos = getPlayerPosition();
		drawCard(matrix,  112, 104, logic().placed[1]);
		drawCard(matrix,  112, 104, logic().placed[0]);
				switch (logic().chosenColor){
			case 0: drawCardBack(matrix,  112, 104,  8); break; // RED
			case 1: drawCardBack(matrix,  112, 104, 10); break; // YELLOW
			case 2: drawCardBack(matrix,  112, 104,  9); break; // BLUE
			case 3: drawCardBack(matrix,  112, 104, 11); break; // GREEN
		}
				int  left = tableID == 1 ? 7 : -89;
		int right = tableID == 1 ? 217 : 313;
		switch (logic().playerCount){
			case 1:
				/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos, 0);
				break;
			case 2:
				/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
				/* UP     */ drawCard2(matrix,   128,   7, false, playerPos, (playerPos + 1) % 2);
				break;
			case 3:
				/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
				/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 3);
				/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 2) % 3);
				break;
			case 4:
				/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
				/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 4);
				/* UP     */ drawCard2(matrix,   128,   7, false, playerPos, (playerPos + 2) % 4);
				/* RIGHT  */ drawCard2(matrix, right, 144, true,  playerPos, (playerPos + 3) % 4);
				break;
			case 5:
				/* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
				/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 5);
				/* UP_L   */ drawCard2(matrix,    48,   7, false, playerPos, (playerPos + 2) % 5);
				/* UP_R   */ drawCard2(matrix,   208,   7, false, playerPos, (playerPos + 3) % 5);
				/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 4) % 5);
				break;
			case 6:
				/* DOWN_L */ drawCard2(matrix,    32, 193, false, playerPos, playerPos);
				/* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 6);
				/* UP_L   */ drawCard2(matrix,    48,   7, false, playerPos, (playerPos + 2) % 6);
				/* UP_R   */ drawCard2(matrix,   208,   7, false, playerPos, (playerPos + 3) % 6);
				/* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 4) % 6);
				/* DOWN_R */ drawCard2(matrix,   208, 201, false, playerPos, (playerPos + 5) % 6);
				break;
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private void drawCard2(GuiGraphics matrix, int posX, int posY, boolean sideways, int playerPos, int cardPos){
		if(playerPos < 0) return;
		int i = 0;
		for(Card card : logic().getCards(cardPos)){
			if(card.suit != -1 && card.idletimer == 0){
				boolean hidden = logic().turnstate > 3 ? false : playerPos != cardPos;
				int texX = card.suit == -1 || hidden ? cardPos+1 : card.number % 8;
				int texY = card.suit == -1 || hidden ?         4 : (card.suit  % 2) * 2 + card.number / 8;
				if(Configuration.CASINO.config_animated_cards.get() && !hidden){
					if(card.number >= 10){
						if(logic().frame == card.suit*12 + (card.number-10)*3){
							texX += 3;
						}
					}
				}
				if(sideways){
					matrix.blit(getCardsTexture(hidden || card.suit >= 2), leftPos + posX + card.shiftX, topPos + posY - logic().getCards(cardPos).size()*12 + i*20 + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
				} else {
					matrix.blit(getCardsTexture(hidden || card.suit >= 2), leftPos + posX + card.shiftX - logic().getCards(cardPos).size()*16 + i*32, topPos + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
				}
			}
			i++;
		}
	}
	
	
	
}