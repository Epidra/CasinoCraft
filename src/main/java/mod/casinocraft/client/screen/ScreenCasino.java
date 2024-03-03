package mod.casinocraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.casinocraft.Configuration;
import mod.casinocraft.Register;
import mod.casinocraft.client.logic.LogicModule;
import mod.casinocraft.client.logic.other.LogicDummy;
import mod.casinocraft.client.logic.other.LogicSlotGame;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.network.*;
import mod.casinocraft.system.PacketHandler;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.Ship;
import mod.lucky77.screen.ScreenBase;
import mod.lucky77.util.button.ButtonSet;
import mod.lucky77.util.Vector2;
import mod.lucky77.util.system.SystemPlayer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;
import java.util.function.Predicate;

import static mod.casinocraft.util.mapping.ButtonMap.*;
import static mod.lucky77.util.KeyMap.*;

public abstract class ScreenCasino extends ScreenBase<MenuCasino> {
	
	/** Determines the Background of the game. **/
	protected int tableID; // 0 - Arcade, 1 - Table Normal, 2 - Table Wide, 3 - Slot Machine
	/** Amount of tokens in the PlayerInventory **/
	protected int playerToken = -1;
	/** The bet set up in the opening screen **/
	protected int bet = 0;
	
	private int colour          =        0;
	private int colourize       =   +65793;
	private final int grayscale = 16777215;
	
	private int camera1 = 0;
	private int camera0 = 0;
	
	protected boolean showDebug   = false;
	protected boolean showForfeit = false;
	
	/** The Player Inventory **/
	private final Inventory inventory;
	
	protected ButtonSet buttonSet = new ButtonSet();
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public ScreenCasino(MenuCasino container, Inventory player, Component name) {
		super(container, player, name, 256, 256);
		this.tableID = container.tableID;
		inventory = player;
		if(this.tableID == 1 || this.tableID == 2){
			createButtons();
			createGameButtons();
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  LOGIC  ---------- ---------- ---------- ---------- //
	
	private LogicModule logic(){
		return menu.logic();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CREATE  ---------- ---------- ---------- ---------- //
	
	private void createButtons(){
		buttonSet.addButton(0, POS_TOP_MIDDLE,        SCORE, SCORE, LIGHT_LARGE, SIZE_LARGE,                 10,                              () -> logic().turnstate == 0 && logic().hasHighscore(),                         this::commandReset);    // Show Highscore
		buttonSet.addButton(1, POS_MID_MIDDLE,        FINISH, FINISH, LIGHT_LARGE, SIZE_LARGE,                10,                              () -> logic().turnstate == 5,                                                   this::commandReset);    // Show Highscore OR return to START
		buttonSet.addButton(2, POS_MID_MIDDLE,        BACK, BACK, LIGHT_LARGE, SIZE_LARGE,                     10,                           () -> logic().turnstate == 7,                                                   this::commandReset);    // Return to START
		buttonSet.addButton(3, POS_MID_MIDDLE,        START, START, LIGHT_LARGE, SIZE_LARGE,                    10,                           () -> logic().turnstate == 0 && hasBet(),                                       this::commandStart);    // Start MiniGame
		buttonSet.addButton(4, new Vector2( 56, 212), ARROW_DOWN_OFF, ARROW_DOWN_ON, LIGHT_SMALL, SIZE_SMALL,    10,            () -> logic().turnstate == 0 && menu.hasToken() && bet > menu.getBettingLow(),  this::commandBetDown);  // Lower Bet
		buttonSet.addButton(5, new Vector2(174, 212), ARROW_UP_OFF,   ARROW_UP_ON,   LIGHT_SMALL, SIZE_SMALL,     10,           () -> logic().turnstate == 0 && menu.hasToken() && bet < menu.getBettingHigh(), this::commandBetUp);    // Raise Bet
		buttonSet.addButton(6, POS_MID_MIDDLE,        JOIN, JOIN,    LIGHT_LARGE, SIZE_LARGE,                       10,                                () -> logic().turnstate == 2 &&  logic().isMultiplayer() && !isCurrentPlayer(), this::commandJoinGame); // Join Multiplayer game
		buttonSet.addButton(7, new Vector2(tableID == 1 ? 240 : 336, 240), FORFEIT_OFF, FORFEIT_ON, LIGHT_MICRO, SIZE_MICRO, -1, () -> logic().turnstate == 2 && !logic().isMultiplayer(),                       this::commandForfeit);  // Forfeit
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	/** Overwritten keyPressed function (only used for rerouting keyCode) */
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		keyTyped(keyCode);
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	/** Fired when a key is pressed */
	private void keyTyped(int keyCode){
		if(keyCode == KEY_BACK){ showDebug = !showDebug; }
		if(tableID < 3 && isCurrentPlayer() && !logic().pause && (logic().turnstate == 2 || logic().turnstate == 3)){
			if(keyCode == KEY_UP)   { action(KEY_UP   ); }
			if(keyCode == KEY_DOWN) { action(KEY_DOWN ); }
			if(keyCode == KEY_LEFT) { action(KEY_LEFT ); }
			if(keyCode == KEY_RIGHT){ action(KEY_RIGHT); }
			if(keyCode == KEY_ENTER){ action(KEY_ENTER); }
			if(keyCode == KEY_0_IN) { action(KEY_0    ); }
			if(keyCode == KEY_1_IN) { action(KEY_1    ); }
			if(keyCode == KEY_2_IN) { action(KEY_2    ); }
			if(keyCode == KEY_3_IN) { action(KEY_3    ); }
			if(keyCode == KEY_4_IN) { action(KEY_4    ); }
			if(keyCode == KEY_5_IN) { action(KEY_5    ); }
			if(keyCode == KEY_6_IN) { action(KEY_6    ); }
			if(keyCode == KEY_7_IN) { action(KEY_7    ); }
			if(keyCode == KEY_8_IN) { action(KEY_8    ); }
			if(keyCode == KEY_9_IN) { action(KEY_9    ); }
		}
		
		if(tableID == 0){
			if( logic().turnstate == 0                                                  && keyCode == KEY_ENTER){ commandStart(); } // Collect Token and start game
			if( logic().turnstate >= 5                                                  && keyCode == KEY_ENTER){ commandReset(); } // Show Highscore and return to Title Screen
			if((logic().turnstate == 2 || logic().turnstate == 3)                       && keyCode == KEY_SPACE){ turnstate(-1);  } // Toggle Pause Mode
			if((logic().turnstate == 2 || logic().turnstate == 3) && menu.logic().pause && keyCode == KEY_ENTER){ turnstate( 4);  } // SET Game Over
		}
		if(tableID == 3 && logic().scoreLives > 0){ // Slot Machine Special Handling
			if(logic().turnstate == 0 && keyCode == KEY_ENTER) { commandStart(); } // Collect Token and start game (Arcade Version) / FROM: Start Screen
			if(logic().turnstate == 5 && keyCode == KEY_ENTER) { commandReset(); } // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
			if(keyCode == KEY_SPACE){
				// action(1);
				PacketHandler.sendToServer(new MessageSlotsServer(((LogicSlotGame)logic()).wheelPos[0], ((LogicSlotGame)logic()).wheelPos[1], ((LogicSlotGame)logic()).wheelPos[2], menu.pos()));
			}
			if(logic().turnstate == 2){
				if(keyCode == KEY_ENTER && logic().scoreLevel < 5){ if(playerToken >= bet){ collectBet(); playerToken = -1; action(0); } }
			}
		}
	}
	
	/** Called when the mouse is clicked. */
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if(mouseButton == 0 || mouseButton == 1){
			if( logic().turnstate  < 2 ||  logic().turnstate  > 3                    ) { buttonSet.interact(leftPos, topPos, mouseX, mouseY); }
			if( logic().turnstate == 2 && !isActivePlayer() && logic().isMultiplayer()){ buttonSet.interact(leftPos, topPos, mouseX, mouseY); }
			if((logic().turnstate == 2 ||  logic().turnstate == 3) && isActivePlayer()){ buttonSet.interact(leftPos, topPos, mouseX, mouseY);
				if(!(logic() instanceof LogicDummy)){ interact(mouseX, mouseY, mouseButton); }
			}
		} return false;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER - OVERLAY  ---------- ---------- ---------- ---------- //
	
	/** Draw the foreground layer for the GuiContainer (everything in front of the items) */
	protected void renderLabels(GuiGraphics matrix, int mouseX, int mouseY){
		if(logic() instanceof LogicDummy) return;
		
		// ----- Animate the "PRESS ENTER" Text ----- //
		if(tableID == 0){
			colour += colourize;
			if( colour <= 0 || colour >= 16777215){
				colourize = -colourize;
			}
		}
		
		// ----- Debug Info (shown if Advanced Tooltips are enabled) ----- //
		if(showDebug){
			matrix.drawString(font,  "PLAYER1: " + logic().currentPlayer[0],         tableID == 2 ? 355 : 260,  15, 16777215);
			matrix.drawString(font,  "PLAYER2: " + logic().currentPlayer[1],         tableID == 2 ? 355 : 260,  25, 16777215);
			matrix.drawString(font,  "PLAYER3: " + logic().currentPlayer[2],         tableID == 2 ? 355 : 260,  35, 16777215);
			matrix.drawString(font,  "PLAYER4: " + logic().currentPlayer[3],         tableID == 2 ? 355 : 260,  45, 16777215);
			matrix.drawString(font,  "PLAYER5: " + logic().currentPlayer[4],         tableID == 2 ? 355 : 260,  55, 16777215);
			matrix.drawString(font,  "PLAYER6: " + logic().currentPlayer[5],         tableID == 2 ? 355 : 260,  65, 16777215);
			matrix.drawString(font,  "TIMEOUT: " + logic().timeout,                  tableID == 2 ? 355 : 260,  75, 16777215);
			matrix.drawString(font,  "STATE:   " + logic().turnstate,                tableID == 2 ? 355 : 260,  85, 16777215);
			matrix.drawString(font,  "PLAYERS: " + logic().getFirstFreePlayerSlot(), tableID == 2 ? 355 : 260,  95, 16777215);
			matrix.drawString(font,  "ACTIVE:  " + logic().activePlayer,             tableID == 2 ? 355 : 260, 105, 16777215);
		}
		
		// ----- Search for tokens in PlayerInventory ----- //
		if(playerToken == -1 && logic().turnstate < 4) validateTokens();
		
		// ----- Multiplayer Additional Player Join Button ----- //
		if(menu.logic().isMultiplayer() && logic().turnstate == 2 && !isCurrentPlayer()){
			if(menu.logic().hasFreePlayerSlots()){
				drawFont(matrix, "BET:",                      96, 196);
				matrix.renderFakeItem(menu.getItemToken(),                      180, 192);
				if(menu.getBettingLow() > 1) drawFont(matrix, "x" + menu.getBettingLow(), 138, 196);
			}
		}
		
		// ----- Start Screen Information (Card Table) ----- //
		if(logic().turnstate == 0 && (tableID == 1 || tableID == 2)){
			if(menu.hasToken() && menu.getBettingHigh() > 0) {
				drawFontCenter(      matrix, "The entry fee is:",    128, 112);
				matrix.renderFakeItem( menu.getItemToken(), 120, 124);
				if(bet > 1) drawFont(matrix, "x" + bet,              140, 128);
				if(menu.getBettingHigh() != menu.getBettingLow()){ drawFontCenter(matrix, "You can change the amount to pay.",      128, 144); }
				if(playerToken <  bet                           ){ drawFontCenter(matrix, "You don't have enough Token to play...", 128, 160); }
				if(playerToken >= bet                           ){ drawFontCenter(matrix, "Do you wish to play?",                   128, 160); }
			}
		}
		
		// ----- Start Screen Information (Arcade) ----- //
		if(logic().turnstate == 0 && tableID == 0){
			if(menu.hasToken() && menu.getBettingHigh() > 0) {
				drawFontCenter(      matrix, "INSERT TOKEN",             128, 180, 16777215);
				matrix.renderFakeItem( menu.getItemToken(),     120, 192          );
				if(bet > 1) drawFont(matrix, "x" + menu.getBettingLow(), 140, 196, 16777215);
				if(playerToken < menu.getBettingLow()) {
					drawFontCenter(matrix, "NOT ENOUGH TOKEN", 128, 228, 16777215);
				} else {
					drawFontCenter(matrix, "Press ENTER", 128, 228, colour);
				}
			} else {
				drawFontCenter(matrix, "Press ENTER", 128, 228, colour);
			}
		}
		
		// ----- Highscore (All) ----- //
		if(logic().turnstate == 7){
			if(tableID == 0) drawFontCenter(matrix, "Press ENTER", 128, 228, colour);
			int max = tableID == 0 ? 20 : 18; // CardTable needs the space for buttons
			for(int i = 0; i < max; i++) {
				drawFont(      matrix,      logic().scoreName[i],  40, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
				drawFontInvert(matrix, "" + logic().scoreHigh[i], 216, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
			}
		}
		
		// ----- MiniGame Information (Arcade/Table) ----- //
		if(tableID < 3 && logic().turnstate >= 2 && logic().turnstate <= 5){
			drawForegroundLayer(matrix, mouseX, mouseY);
		}
		
		// ----- MiniGame Information (Slot Machine) ----- //
		if(tableID == 3 && logic().scoreLives > 0){
			drawForegroundLayer(matrix, mouseX, mouseY);
		}
		
		// ----- Pause Message (Arcade) ----- //
		if(logic().turnstate == 2 && menu.logic().pause){
			drawFontCenter(matrix, "PAUSE",                  128, 196, 16777215);
			drawFontCenter(matrix, "Press ENTER to FORFEIT", 128, 228, colour  );
		}
		
		// ----- GameOver Message (Arcade) ----- //
		if(logic().turnstate == 5 && tableID == 0){
			drawFontCenter(matrix, "GAME OVER",   128, 196, 16777215);
			drawFontCenter(matrix, "Press ENTER", 128, 228, colour  );
		}
		
		// ----- GameOver Conditions ----- //
		if(logic().turnstate == 4         ){ gameOver();     }
		if(menu.getID() != logic().getID()){ this.onClose(); }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER - BACKGROUND  ---------- ---------- ---------- ---------- //
	
	/** Draws the background layer of this container (behind the items). */
	protected void renderBg(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		buttonSet.update(leftPos, topPos, mouseX, mouseY);
		if((tableID == 1 || tableID == 2) && colour > 0){
			colour--;
			if(colour == 0){
				showForfeit = false;
			}
		}
		
		// ----- Arcade Background (dummy) ----- //
		if(tableID == 0 && (logic() instanceof LogicDummy)) {
			matrix.blit(Register.TEXTURE_STATIC, leftPos + 32 + 0, topPos + 0, 32*6, 32*8, 32*6, 32*8);
			matrix.blit(Register.TEXTURE_GROUND_ARCADE, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
		}
		
		// ----- Arcade Background (game) ----- //
		if(tableID == 0 && !(logic() instanceof LogicDummy)) {
			matrix.blit(getParallaxTexture(true), leftPos, topPos, 0, camera1, 256, 256);
			matrix.blit(getParallaxTexture(false), leftPos, topPos, 0, camera0, 256, 256);
		}
		
		// ----- Card Table Background (small) ----- //
		if(tableID == 1){
			matrix.blit(getBackgroundCardTable(), leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background
		}
		
		// ----- Card Table Background (wide) ----- //
		if(tableID == 2){
			matrix.blit(getBackgroundCardTable(), leftPos -  96, topPos,  0, 0, this.imageWidth-32, this.imageHeight); // Background Left
			matrix.blit(getBackgroundCardTable(), leftPos + 128, topPos, 32, 0, this.imageWidth-32, this.imageHeight); // Background Right
		}
		
		// ----- Slot Machine Background ----- //
		if(tableID == 3){
			matrix.blit(getBackgroundSlotMachine(), leftPos +  31, topPos -   1,   0,   0, 194, 162); // Upper Part
			matrix.blit(getBackgroundSlotMachine(), leftPos      , topPos + 163,   0, 162, 256,  94); // Lower Part
			matrix.blit(getBackgroundSlotMachine(), leftPos + 256, topPos + 178, 224,  64,  32,  64); // Crank
			
			matrix.blit(getBackgroundSlotMachine(), leftPos +  38, topPos + 161, 195, 129,  60,   2); // MiddleLine
			matrix.blit(getBackgroundSlotMachine(), leftPos +  98, topPos + 161, 195, 129,  60,   2); // MiddleLine
			matrix.blit(getBackgroundSlotMachine(), leftPos + 158, topPos + 161, 195, 129,  60,   2); // MiddleLine
			
			matrix.blit(getBackgroundSlotMachine(), leftPos +  10, topPos + 257, 195, 129,  52,  30); // Underline
			matrix.blit(getBackgroundSlotMachine(), leftPos +  62, topPos + 257, 203, 129,  44,  30); // Underline
			matrix.blit(getBackgroundSlotMachine(), leftPos + 106, topPos + 257, 203, 129,  44,  30); // Underline
			matrix.blit(getBackgroundSlotMachine(), leftPos + 150, topPos + 257, 203, 129,  44,  30); // Underline
			matrix.blit(getBackgroundSlotMachine(), leftPos + 194, topPos + 257, 203, 129,  52,  30); // Underline
			
			// ----- LEVER ----- //
			if(logic().scoreLives == 2){ // Lever Down
				matrix.blit(getBackgroundSlotMachine(), leftPos + 261, topPos + 220, 197, 64, 22,  8); // Lever
				matrix.blit(getBackgroundSlotMachine(), leftPos + 249, topPos + 224, 201,  9, 46, 46); // Knob
			} else {                     // Lever Up
				matrix.blit(getBackgroundSlotMachine(), leftPos + 261, topPos + 142, 197, 68, 24, 60); // Lever (Lower Part)
				matrix.blit(getBackgroundSlotMachine(), leftPos + 261, topPos + 100, 197, 68, 22, 42); // Lever (Upper Part)
				matrix.blit(getBackgroundSlotMachine(), leftPos + 249, topPos +  60, 201,  9, 46, 46); // Knob
			}
		}
		
		// ----- Draws Logo from ItemModule ----- //
		if(logic().turnstate <= 1) { drawLogo(matrix); }
		
		// ----- LOGIC Break ----- //
		if(logic() instanceof LogicDummy) return;
		
		// ----- MiniGame Layer (Arcade/Table) ----- //
		if(tableID < 3 && logic().turnstate >= 2 && logic().turnstate < 6){
			if(logic().pause) RenderSystem.setShaderColor(0.35F, 0.35F, 0.35F, 1.0F);
			drawBackgroundLayer(matrix, partialTicks, mouseX, mouseY);
			if(logic().pause) RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		}
		
		// ----- MiniGame Layer (Slot Machine) ----- //
		if(tableID == 3 && logic().scoreLives > 0){
			drawBackgroundLayer(matrix, partialTicks, mouseX, mouseY);
		}
		
		// ----- Buttons ----- //
		if(tableID == 1 || tableID == 2){
			while (buttonSet.next()){
				if(buttonSet.isVisible()    ){ matrix.blit(Register.TEXTURE_BUTTONS, leftPos + buttonSet.pos().X, topPos + buttonSet.pos().Y, buttonSet.map().X,       buttonSet.map().Y,       buttonSet.sizeX(), buttonSet.sizeY()); }
				if(buttonSet.isHighlighted()){ matrix.blit(Register.TEXTURE_BUTTONS, leftPos + buttonSet.pos().X, topPos + buttonSet.pos().Y, buttonSet.highlight().X, buttonSet.highlight().Y, buttonSet.sizeX(), buttonSet.sizeY()); }
			}
			if(colour > 0){
				matrix.blit(Register.TEXTURE_BUTTONS, leftPos + 89, topPos + 240, 28, 242, 78, 14);
			}
		}
		
		// ----- Arcade Border ----- //
		if(tableID == 0) {
			matrix.blit(Register.TEXTURE_GROUND_ARCADE, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
			int shift2 = logic().turnstate == 2 || logic().turnstate == 3 ? 2 : 1;
			if(logic().turnstate != 5 && !menu.logic().pause) camera1 = (camera1 + shift2  ) % 256;
			if(logic().turnstate != 5 && !menu.logic().pause) camera0 = (camera0 + shift2*2) % 256;
		}
		
		// ----- Multiplayer Status ----- //
		if((logic().turnstate == 2 || logic().turnstate == 3) && logic().isMultiplayer()){
			for(int i = 0;        i < logic().getFirstFreePlayerSlot(); i++){ matrix.blit(Register.TEXTURE_DICE, leftPos+(tableID == 2 ? 340 : 245), topPos + 32 + 36*i,                    224, 32 + 32*i,                    32, 32); }
			if(logic().activePlayer < logic().getFirstFreePlayerSlot()     ){ matrix.blit(Register.TEXTURE_DICE, leftPos+(tableID == 2 ? 340 : 245), topPos + 32 + 36*logic().activePlayer, 192, 32 + 32*logic().activePlayer, 32, 32); }
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	protected void commandStart(){
		if(!menu.hasToken() || playerToken >= menu.getBettingLow()){
			if(menu.hasToken()) collectBet();
			Random r = new Random();
			PacketHandler.sendToServer(new MessageStartServer(inventory.player.getName().getString(), r.nextInt(1000000), menu.pos()));
		}
	}
	
	private void commandJoinGame(){
		if(menu.logic().hasFreePlayerSlots()){
			if(!menu.hasToken()){
				addNewPlayer();
			} else {
				if(playerToken >= bet){
					collectBet();
					addNewPlayer();
				}
			}
		}
	}
	
	private void commandForfeit(){
		if(showForfeit){ turnstate(4); colour = 0; } else { showForfeit = true; colour = 96; }
	}
	
	private void commandReset(){
		if(logic().hasHighscore() && logic().turnstate < 7) turnstate(7); else turnstate(0);
	}
	
	private void commandBetDown(){
		if(bet > menu.getBettingLow() ){ bet--; }
	}
	
	private void commandBetUp(){
		if(bet < menu.getBettingHigh()){ bet++; }
	}
	
	protected void action(int action){
		PacketHandler.sendToServer(new MessageStateServer(false, action, menu.pos()));
	}
	
	protected void turnstate(int state){
		PacketHandler.sendToServer(new MessageStateServer(true, state, menu.pos()));
	}
	
	private void addNewPlayer(){
		PacketHandler.sendToServer(new MessageStartServer(inventory.player.getName().getString(), -1, menu.pos()));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	/** Final Function before a game ends, takes care of payment. **/
	private void gameOver(){
		buttonSet.releaseToggle();
		int pos = getPlayerPosition();
		if (pos > -1 && playerToken != -1) {
			payBet(logic().reward[pos]);
			turnstate(10 + pos);
			playerToken = -1;
		}
		if(allCleared()){
			turnstate(5);
			if(logic().hasHighscore() && isActivePlayer()) {
				highscore();
			} else {
				turnstate(-3);
			}
		}
	}
	
	/** Checks if everyone has collected their bet before resetting the game **/
	private boolean allCleared(){
		for(int i = 0; i < 6; i++){
			if(logic().reward[i] > 0) return false;
		} return true;
	}
	
	/** generates the highscore for upload and gives out all PRIZES that can be collected from that score **/
	private void highscore(){
		PacketHandler.sendToServer(new MessageScoreServer(this.inventory.player.getName().getString(), logic().scorePoint, menu.pos()));
		int lastScore = 0;
		int prizeSET = 0;
		int prizeCON = 0;
		for(int i = 0; i < 3; i++){
			if(menu.getPrizeCount(i) > 0){
				if(menu.getPrizeMode(i)){
					if(menu.getPrizeScore(i) <= menu.logic().scorePoint && menu.getPrizeScore(i) > lastScore){
						lastScore = menu.getPrizeScore(i);
						prizeSET  = menu.getPrizeCount(i);
					}
				} else {
					if(menu.getPrizeScore(i) > 0) prizeCON += menu.getPrizeCount(i) * (menu.logic().scorePoint / menu.getPrizeScore(i));
				}
			}
		}
		payPrize(prizeSET + prizeCON);
	}
	
	/** Upload the current inventory of this block for syncing **/
	private void sendMessageBlock(){
		NonNullList<ItemStack> inv = NonNullList.withSize(5, ItemStack.EMPTY);
		inv.set(0, menu.container.getItem(0));
		inv.set(1, menu.container.getItem(1));
		inv.set(2, menu.container.getItem(2));
		inv.set(3, menu.container.getItem(3));
		inv.set(4, menu.container.getItem(4));
		PacketHandler.sendToServer(new MessageInventoryServer(inv, menu.getStorageToken(), menu.getStoragePrize(), menu.pos()));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - TOKEN  ---------- ---------- ---------- ---------- //
	
	private boolean hasBet(){
		return !menu.hasToken() || playerToken >= bet;
	}
	
	/** Scans the PlayerInventory for Tokens **/
	protected void validateTokens(){
		playerToken = -2;
		if(bet < menu.getBettingLow() ) bet = menu.getBettingLow();
		if(bet > menu.getBettingHigh()) bet = menu.getBettingHigh();
		if(menu.hasToken()){
			Item item = menu.getItemToken().getItem();
			int count = 0;
			for(int i = 0; i < 36; i++){
				if(item == inventory.getItem(i).getItem()) count += inventory.getItem(i).getCount();
			}
			playerToken = count;
		}
	}
	
	/** Collects the bet from the Player **/
	protected void collectBet(){
		if(menu.hasToken()){
			SystemPlayer.decreaseInventory(inventory, menu.getItemToken(), bet);
			{
				int i = 0;
				ItemStack itemStack = ItemStack.EMPTY;
				Predicate<ItemStack> stack = Predicate.isEqual(menu.getItemToken());
				int count = bet;
				
				for(int j = 0; j < inventory.getContainerSize(); ++j) {
					ItemStack itemstack = inventory.getItem(j);
					if (!itemstack.isEmpty() && stack.test(itemstack)) {
						int k = count <= 0 ? itemstack.getCount() : Math.min(count - i, itemstack.getCount());
						i += k;
						if (count != 0) {
							itemstack.shrink(k);
							if (itemstack.isEmpty()) {
								inventory.setItem(j, ItemStack.EMPTY);
							}
						}
					}
				}
				
				if (!itemStack.isEmpty() && stack.test(itemStack)) {
					int l = count <= 0 ? itemStack.getCount() : Math.min(count - i, itemStack.getCount());
					if (count != 0) { itemStack.shrink(l); }
				}
			}
			PacketHandler.sendToServer(new MessagePlayerServer(menu.getItemToken().getItem(), -bet));
			if(!menu.getSettingInfiniteToken()) {
				menu.setStorageToken(menu.getStorageToken() + bet);
				sendMessageBlock();
			}
			playerToken = -1;
		}
	}
	
	/** Pays the won Bet to the Player **/
	private void payBet(int multi){
		if(multi <= 0) return;
		if(menu.hasToken()){
			if(!menu.getSettingInfiniteToken()) {
				Item item = menu.getItemToken().getItem();
				int count = bet * multi; // multi == 1 -> Return bet
				int count2 = 0;
				count2 = Math.min(menu.getStorageToken(), count);
				menu.setStorageToken(menu.getStorageToken() - count);
				if(menu.getStorageToken() <= 0) {
					menu.setStorageToken(0);
					menu.setItemToken(new ItemStack(Blocks.AIR));
				}
				sendMessageBlock();
				inventory.add(new ItemStack(item, count2));
				PacketHandler.sendToServer(new MessagePlayerServer(item, count2));
			} else {
				Item item = menu.getItemToken().getItem();
				int count = bet * multi; // multi == 1 -> Return bet
				inventory.add(new ItemStack(item, count));
				PacketHandler.sendToServer(new MessagePlayerServer(item, count));
			}
		}
	}
	
	/** Pays the Prize to the Player **/
	private void payPrize(int amount){
		if(amount <= 0) return;
		if(menu.hasReward()){
			if(!menu.getSettingInfinitePrize()) {
				Item item = menu.getItemPrize().getItem();
				int count = 0;
				count = Math.min(menu.getStoragePrize(), amount);
				menu.setStoragePrize(menu.getStoragePrize() - amount);
				if(menu.getStoragePrize() <= 0) {
					menu.setStoragePrize(0);
					menu.setItemPrize(new ItemStack(Blocks.AIR));
				}
				sendMessageBlock();
				inventory.add(new ItemStack(item, count));
				PacketHandler.sendToServer(new MessagePlayerServer(item, count));
			} else {
				Item item = menu.getItemPrize().getItem();
				inventory.add(new ItemStack(item, amount));
				PacketHandler.sendToServer(new MessagePlayerServer(item, amount));
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - PLAYER  ---------- ---------- ---------- ---------- //
	
	/** Checks this CLIENT is one of the current players **/
	protected boolean isCurrentPlayer(){
		if(menu.logic().isMultiplayer()){
			if(menu.getCurrentPlayer(0).matches("void")){
				return true;
			}
			for(int i = 0; i < 6; i++){
				if(menu.getCurrentPlayer(i).matches(inventory.player.getName().getString())){
					return true;
				}
			}
		} else {
			return menu.getCurrentPlayer(0).matches("void") || menu.getCurrentPlayer(0).matches(inventory.player.getName().getString());
		} return false;
	}
	
	/** Checks if this CLIENT is the active player **/
	protected boolean isActivePlayer(){
		if(menu.logic().isMultiplayer()){
			for(int i = 0; i < 6; i++){
				if(menu.getCurrentPlayer(i).matches(inventory.player.getName().getString())){
					if(i == logic().activePlayer){
						return true;
					}
				}
			}
		} else {
			return menu.getCurrentPlayer(0).matches(inventory.player.getName().getString());
		} return false;
	}
	
	/** Gives out where in line this Client is **/
	protected int getPlayerPosition(){
		if(menu.logic().isMultiplayer()){
			for(int i = 0; i < 6; i++){
				if(menu.getCurrentPlayer(i).matches(inventory.player.getName().getString())){
					return i;
				}
			}
		} else {
			return menu.getCurrentPlayer(0).matches(inventory.player.getName().getString()) ? 0 : -1;
		} return -1;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT - RENDER  ---------- ---------- ---------- ---------- //
	
	/** Draw a Background Texture on the field **/
	protected void drawBackground(GuiGraphics matrix, ResourceLocation rl){
		matrix.blit(rl, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
	}
	
	/** Draw a Background Texture on the field and move it along the x-Axis **/
	protected void drawBackground(GuiGraphics matrix, ResourceLocation rl, int offset){
		matrix.blit(rl, leftPos + offset, topPos, 0, 0, this.imageWidth, this.imageHeight);
	}
	
	/** Draw a Background Texture on the field and reset the shader to another texture afterwards **/
	protected void drawBackground(GuiGraphics matrix, ResourceLocation rl, ResourceLocation rl2){
		matrix.blit(rl, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
	}
	
	/** Draws a timeout timer **/
	protected void drawTimer(GuiGraphics matrix){
		if(logic().turnstate == 2 || logic().turnstate == 3){
			if(Configuration.CASINO.config_timeout.get() - logic().timeout > 0){
				drawFontInvert(matrix, "" + (Configuration.CASINO.config_timeout.get() - logic().timeout), tableID == 1 ? 238 : 336, 7);
			}
		}
	}
	
	/** Draws a value and its name on the left side of the Table **/
	protected void drawValueLeft(GuiGraphics matrix, String name, int value){
		drawFontCenter(matrix,       name, 32, 7, 11119017);
		drawFontCenter(matrix, "" + value, 64, 7          );
	}
	
	/** Draws a value and its name on the left side of the Table **/
	protected void drawValueRight(GuiGraphics matrix, String name, int value){
		drawFontCenter(matrix,       name, 224, 7, 11119017);
		drawFontCenter(matrix, "" + value, 192, 7          );
	}
	
	/** Draws String on x,y position with shadow **/
	protected void drawFont(GuiGraphics matrix, String text, int x, int y){
		drawFont(matrix, text, x, y, grayscale);
	}
	
	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFont(GuiGraphics matrix, String text, int x, int y, int color){
		matrix.drawString(font,  text,  x+1, y+1, 0    , false);
		matrix.drawString(font,  text,  x  , y  , color, false);
	}
	
	/** Draws String on x,y position with shadow **/
	protected void drawFontInvert(GuiGraphics matrix, String text, int x, int y){
		drawFontInvert(matrix, text, x, y, grayscale);
	}
	
	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFontInvert(GuiGraphics matrix, String text, int x, int y, int color){
		int w = this.font.width(text);
		matrix.drawString(font,  text,  x+1 - w, y+1, 0    , false);
		matrix.drawString(font,  text,  x   - w, y  , color, false);
	}
	
	/** Draws String on x,y position with shadow **/
	protected void drawFontCenter(GuiGraphics matrix, String text, int x, int y){
		drawFontCenter(matrix, text, x, y, grayscale);
	}
	
	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFontCenter(GuiGraphics matrix, String text, int x, int y, int color){
		int w = this.font.width(text) / 2;
		matrix.drawString(font,  text,  x+1 - w, y+1, 0    , false);
		matrix.drawString(font,  text,  x   - w, y  , color, false);
	}
	
	private ResourceLocation cardTexture(Card card){
		if(card.hidden){
			return getCardsTexture(true);
		} else {
			if(card.suit <= 1) return getCardsTexture(false);
			if(card.suit >= 2) return getCardsTexture(true );
		}
		return getCardsTexture(false);
	}
	
	private ResourceLocation cardTexture(int color){
		if(color <= 6) return getCardsTexture(true );
		else           return getCardsTexture(false);
	}
	
	/** Draws a Card **/
	public void drawCard(GuiGraphics matrix, int posX, int posY, Card card){
		if(card.suit == -1) return;
		int texX = card.suit == -1 || card.hidden ? 0 : card.number % 8;
		int texY = card.suit == -1 || card.hidden ? 4 : (card.suit % 2) * 2 + card.number / 8;
		if(Configuration.CASINO.config_animated_cards.get() && !card.hidden){
			if(card.number >= 10){
				if(logic().frame == card.suit*12 + (card.number-10)*3){
					texX += 3;
				}
			}
		}
		matrix.blit(cardTexture(card), leftPos + posX + card.shiftX, topPos + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
	}
	
	/** Draws the Backside of a Card (also used for highlighter) **/
	public void drawCardBack(GuiGraphics matrix, int posX, int posY, int color){
		matrix.blit(cardTexture(color), leftPos + posX, topPos + posY, (color%7) * 32, 192, 32, 48);
	}
	
	private void drawLetter(GuiGraphics matrix, ResourceLocation loc, char c, int posX, int posY){
		if(c == 'a') matrix.blit(loc, posX, posY,   0,   0, 32, 32);
		if(c == 'b') matrix.blit(loc, posX, posY,  32,   0, 32, 32);
		if(c == 'c') matrix.blit(loc, posX, posY,  64,   0, 32, 32);
		if(c == 'd') matrix.blit(loc, posX, posY,  96,   0, 32, 32);
		if(c == 'e') matrix.blit(loc, posX, posY, 128,   0, 32, 32);
		if(c == 'f') matrix.blit(loc, posX, posY, 160,   0, 32, 32);
		if(c == 'g') matrix.blit(loc, posX, posY, 192,   0, 32, 32);
		if(c == 'h') matrix.blit(loc, posX, posY, 224,   0, 32, 32);
		if(c == 'i') matrix.blit(loc, posX, posY,   0,  32, 32, 32);
		if(c == 'j') matrix.blit(loc, posX, posY,  32,  32, 32, 32);
		if(c == 'k') matrix.blit(loc, posX, posY,  64,  32, 32, 32);
		if(c == 'l') matrix.blit(loc, posX, posY,  96,  32, 32, 32);
		if(c == 'm') matrix.blit(loc, posX, posY, 128,  32, 32, 32);
		if(c == 'n') matrix.blit(loc, posX, posY, 160,  32, 32, 32);
		if(c == 'o') matrix.blit(loc, posX, posY, 192,  32, 32, 32);
		if(c == 'p') matrix.blit(loc, posX, posY, 224,  32, 32, 32);
		if(c == 'q') matrix.blit(loc, posX, posY,   0,  64, 32, 32);
		if(c == 'r') matrix.blit(loc, posX, posY,  32,  64, 32, 32);
		if(c == 's') matrix.blit(loc, posX, posY,  64,  64, 32, 32);
		if(c == 't') matrix.blit(loc, posX, posY,  96,  64, 32, 32);
		if(c == 'u') matrix.blit(loc, posX, posY, 128,  64, 32, 32);
		if(c == 'v') matrix.blit(loc, posX, posY, 160,  64, 32, 32);
		if(c == 'w') matrix.blit(loc, posX, posY, 192,  64, 32, 32);
		if(c == 'x') matrix.blit(loc, posX, posY, 224,  64, 32, 32);
		if(c == 'y') matrix.blit(loc, posX, posY,   0,  96, 32, 32);
		if(c == 'z') matrix.blit(loc, posX, posY,  32,  96, 32, 32);
		if(c == '0') matrix.blit(loc, posX, posY,  64,  96, 32, 32);
		if(c == '1') matrix.blit(loc, posX, posY,  96,  96, 32, 32);
		if(c == '2') matrix.blit(loc, posX, posY, 128,  96, 32, 32);
		if(c == '3') matrix.blit(loc, posX, posY, 160,  96, 32, 32);
		if(c == '4') matrix.blit(loc, posX, posY, 192,  96, 32, 32);
		if(c == '5') matrix.blit(loc, posX, posY, 224,  96, 32, 32);
		if(c == '6') matrix.blit(loc, posX, posY,   0, 128, 32, 32);
		if(c == '7') matrix.blit(loc, posX, posY,  32, 128, 32, 32);
		if(c == '8') matrix.blit(loc, posX, posY,  64, 128, 32, 32);
		if(c == '9') matrix.blit(loc, posX, posY,  96, 128, 32, 32);
	}
	
	/** Draws Colored Card Table Background **/
	private ResourceLocation getBackgroundCardTable(){
		if(menu.color == DyeColor.BLACK     ) return Register.TEXTURE_GROUND_BLACK;
		if(menu.color == DyeColor.BLUE      ) return Register.TEXTURE_GROUND_BLUE;
		if(menu.color == DyeColor.BROWN     ) return Register.TEXTURE_GROUND_BROWN;
		if(menu.color == DyeColor.CYAN      ) return Register.TEXTURE_GROUND_CYAN;
		if(menu.color == DyeColor.GRAY      ) return Register.TEXTURE_GROUND_GRAY;
		if(menu.color == DyeColor.GREEN     ) return Register.TEXTURE_GROUND_GREEN;
		if(menu.color == DyeColor.LIGHT_BLUE) return Register.TEXTURE_GROUND_LIGHT_BLUE;
		if(menu.color == DyeColor.LIME      ) return Register.TEXTURE_GROUND_LIME;
		if(menu.color == DyeColor.MAGENTA   ) return Register.TEXTURE_GROUND_MAGENTA;
		if(menu.color == DyeColor.ORANGE    ) return Register.TEXTURE_GROUND_ORANGE;
		if(menu.color == DyeColor.PINK      ) return Register.TEXTURE_GROUND_PINK;
		if(menu.color == DyeColor.PURPLE    ) return Register.TEXTURE_GROUND_PURPLE;
		if(menu.color == DyeColor.RED       ) return Register.TEXTURE_GROUND_RED;
		if(menu.color == DyeColor.LIGHT_GRAY) return Register.TEXTURE_GROUND_LIGHT_GRAY;
		if(menu.color == DyeColor.WHITE     ) return Register.TEXTURE_GROUND_WHITE;
		if(menu.color == DyeColor.YELLOW    ) return Register.TEXTURE_GROUND_YELLOW;
		return Register.TEXTURE_GROUND_GRAY;
	}
	
	/** Draws Colored Slot Machine Background **/
	private ResourceLocation getBackgroundSlotMachine(){
		if(menu.color == DyeColor.BLACK     ) return Register.TEXTURE_SLOTS_BLACK;
		if(menu.color == DyeColor.BLUE      ) return Register.TEXTURE_SLOTS_BLUE;
		if(menu.color == DyeColor.BROWN     ) return Register.TEXTURE_SLOTS_BROWN;
		if(menu.color == DyeColor.CYAN      ) return Register.TEXTURE_SLOTS_CYAN;
		if(menu.color == DyeColor.GRAY      ) return Register.TEXTURE_SLOTS_GRAY;
		if(menu.color == DyeColor.GREEN     ) return Register.TEXTURE_SLOTS_GREEN;
		if(menu.color == DyeColor.LIGHT_BLUE) return Register.TEXTURE_SLOTS_LIGHT_BLUE;
		if(menu.color == DyeColor.LIME      ) return Register.TEXTURE_SLOTS_LIME;
		if(menu.color == DyeColor.MAGENTA   ) return Register.TEXTURE_SLOTS_MAGENTA;
		if(menu.color == DyeColor.ORANGE    ) return Register.TEXTURE_SLOTS_ORANGE;
		if(menu.color == DyeColor.PINK      ) return Register.TEXTURE_SLOTS_PINK;
		if(menu.color == DyeColor.PURPLE    ) return Register.TEXTURE_SLOTS_PURPLE;
		if(menu.color == DyeColor.RED       ) return Register.TEXTURE_SLOTS_RED;
		if(menu.color == DyeColor.LIGHT_GRAY) return Register.TEXTURE_SLOTS_LIGHT_GRAY;
		if(menu.color == DyeColor.WHITE     ) return Register.TEXTURE_SLOTS_WHITE;
		if(menu.color == DyeColor.YELLOW    ) return Register.TEXTURE_SLOTS_YELLOW;
		return Register.TEXTURE_SLOTS_GRAY;
	}
	
	private ResourceLocation getlogo(){
		if(tableID == 0){
			return Register.TEXTURE_FONT_ARCADE;
		} else if(tableID == 1 || tableID == 2){
			return Register.TEXTURE_FONT_CARDTABLE;
		}
		return Register.TEXTURE_FONT_CARDTABLE;
	}
	
	/** Draws Logo from ItemModule **/
	private void drawLogo(GuiGraphics matrix) {
		int sizeX = 0;
		String[] logo = logic().getName().split("_");
		if(tableID <= 2){
			if(tableID == 0){
				sizeX = 16;
			} else if(tableID == 1 || tableID == 2){
				sizeX = 32;
			}
			
			for(int i = 0; i < logo.length; i++){
				for(int k = 0; k < logo[i].length(); k++){
					drawLetter(matrix, getlogo(), logo[i].charAt(k), leftPos + 128 - logo[i].length()*(sizeX/2) + sizeX*k, topPos + 32 + 32*i);
				}
			}
		}
	}
	
	protected void drawMino(GuiGraphics matrix, int posX, int posY, int idX, int idY){
		matrix.blit(Register.TEXTURE_MINOS, leftPos + posX, topPos + posY, 24 * idX, 24 * idY, 24, 24);
	}
	
	protected void drawMino(GuiGraphics matrix, int posX, int posY){
		drawMino(matrix, posX, posY, 0, 0);
	}
	
	protected void drawMinoSmall(GuiGraphics matrix, int posX, int posY, int id, boolean alternate){
		if(alternate){
			matrix.blit(Register.TEXTURE_MINOS, leftPos + posX, topPos + posY, 240, 16 * id, 16, 16);
		} else {
			matrix.blit(Register.TEXTURE_MINOS, leftPos + posX, topPos + posY, 16 * id, 240, 16, 16);
		}
	}
	
	protected void drawMinoSmall(GuiGraphics matrix, int posX, int posY){
		drawMinoSmall(matrix, posX, posY, 0, false);
	}
	
	protected void drawDigi(GuiGraphics matrix, int posX, int posY, int idX, int idY){
		matrix.blit(Register.TEXTURE_ARCADE, leftPos + posX, topPos + posY, 16 * idX, 16 + 16 * idY, 16, 16);
	}
	
	protected void drawDigi(GuiGraphics matrix, int posX, int posY){
		drawDigi(matrix, posX, posY, 0, 0);
	}
	
	protected void drawDigiSmall(GuiGraphics matrix, int posX, int posY, int idX){
		matrix.blit(Register.TEXTURE_ARCADE, leftPos + posX    , topPos + posY    , 16 * idX     , 16   , 6, 6);
		matrix.blit(Register.TEXTURE_ARCADE, leftPos + posX + 6, topPos + posY    , 16 * idX + 10, 16   , 6, 6);
		matrix.blit(Register.TEXTURE_ARCADE, leftPos + posX    , topPos + posY + 6, 16 * idX     , 16+10, 6, 6);
		matrix.blit(Register.TEXTURE_ARCADE, leftPos + posX + 6, topPos + posY + 6, 16 * idX + 10, 16+10, 6, 6);
		
	}
	
	protected void drawDigiSmall(GuiGraphics matrix, int posX, int posY){
		drawDigiSmall(matrix, posX, posY, 0);
	}
	
	protected void drawDigiSymbol(GuiGraphics matrix, int posX, int posY, int id){
		matrix.blit(Register.TEXTURE_ARCADE, leftPos + posX, topPos + posY, 16 * id, 0, 16, 16);
	}
	
	protected void drawDigiSymbol(GuiGraphics matrix, int posX, int posY){
		drawDigiSymbol(matrix, posX, posY, 0);
	}
	
	protected void drawShip(GuiGraphics matrix, Ship ship, int shipID, int lookDirection, boolean animate){
		int frame = logic().turnstate < 4 && animate ? (logic().frame % 12) / 2 : 0;
		if(frame == 4) frame = 2;
		if(frame == 5) frame = 1;
		int direction = lookDirection == -1 ? ship.getLookDirection() : lookDirection;
		matrix.blit(Register.TEXTURE_ARCADE, leftPos + 32 + ship.getPos().X, topPos + 8 + ship.getPos().Y, 64*(shipID%4) + 16*frame, 128 + direction*16 + (shipID/4)*64, 16, 16);
	}
	
	protected void drawShip(GuiGraphics matrix, Vector2 vec, int shipID){
		int frame = logic().turnstate < 4 ? (logic().frame % 12) / 2 : 0;
		if(frame == 4) frame = 2;
		if(frame == 5) frame = 1;
		matrix.blit(Register.TEXTURE_ARCADE, leftPos + 32 + vec.X*16, topPos + 8 + vec.Y*16, 64*(shipID%4) + 16*frame, 128 + (shipID/4)*64, 16, 16);
	}
	
	private ResourceLocation getParallaxTexture(boolean lowTexture){
		return switch (menu.getSettingAlternateColor()) {
			case 0 -> lowTexture ? Register.TEXTURE_PARALLAX_0_LOW : Register.TEXTURE_PARALLAX_0_HIGH;
			case 1 -> lowTexture ? Register.TEXTURE_PARALLAX_1_LOW : Register.TEXTURE_PARALLAX_1_HIGH;
			case 2 -> lowTexture ? Register.TEXTURE_PARALLAX_2_LOW : Register.TEXTURE_PARALLAX_2_HIGH;
			case 3 -> lowTexture ? Register.TEXTURE_PARALLAX_3_LOW : Register.TEXTURE_PARALLAX_3_HIGH;
			case 4 -> lowTexture ? Register.TEXTURE_PARALLAX_4_LOW : Register.TEXTURE_PARALLAX_4_HIGH;
			case 5 -> lowTexture ? Register.TEXTURE_PARALLAX_5_LOW : Register.TEXTURE_PARALLAX_5_HIGH;
			default -> Register.TEXTURE_STATIC;
		};
	}
	
	protected ResourceLocation getCardsTexture(boolean noirTexture){
		return switch (menu.getSettingAlternateColor()) {
			case 0 -> noirTexture ? Register.TEXTURE_CARDS_0_NOIR : Register.TEXTURE_CARDS_0_ROUGE;
			case 1 -> noirTexture ? Register.TEXTURE_CARDS_1_NOIR : Register.TEXTURE_CARDS_1_ROUGE;
			case 2 -> noirTexture ? Register.TEXTURE_CARDS_2_NOIR : Register.TEXTURE_CARDS_2_ROUGE;
			case 3 -> noirTexture ? Register.TEXTURE_CARDS_3_NOIR : Register.TEXTURE_CARDS_3_ROUGE;
			case 4 -> noirTexture ? Register.TEXTURE_CARDS_4_NOIR : Register.TEXTURE_CARDS_4_ROUGE;
			case 5 -> noirTexture ? Register.TEXTURE_CARDS_5_NOIR : Register.TEXTURE_CARDS_5_ROUGE;
			default -> Register.TEXTURE_STATIC;
		};
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ABSTRACT  ---------- ---------- ---------- ---------- //
	
	protected abstract void createGameButtons();
	protected abstract void interact(double mouseX, double mouseY, int mouseButton);
	protected abstract void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY);
	protected abstract void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY);
	
	
	
}

