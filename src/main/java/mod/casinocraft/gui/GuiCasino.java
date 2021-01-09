package mod.casinocraft.gui;

import java.io.IOException;
import java.util.Random;
import java.util.function.Predicate;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.network.*;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.Ship;
import mod.shared.util.InventoryUtil;
import mod.shared.util.Vector2;
import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class GuiCasino extends GuiContainer {

	/** The player inventory bound to this GUI. */
	private   final InventoryPlayer PLAYER;
	/** The TileEntity bound to this GUI. */
	protected final ContainerCasino CONTAINER;

	/** Determines the Background of the game. */
	protected int tableID; // 0 - Arcade, 1 - Table Normal, 2 - Table Wide, 3 - Slot Machine
	/** Amount of tokens in the PlayerInventory */
	protected int playerToken = -1;
	/** The bet set up in the opening screen */
	protected int bet = 0;

	protected int colour = 0;
	protected boolean colourUP = true;

	private final int grayscale = 16777215;

	protected int camera1 = 0;
	protected int camera0 = 0;
	protected int shift = 1;
	protected int intro = 256;

	protected boolean showDebug = false;
    
    
    //--------------------CONSTRUCTOR--------------------
    
    /** Basic Constructor **/
    public GuiCasino(ContainerCasino container, InventoryPlayer player){
        super(container);
        PLAYER = player;
        CONTAINER = container;
        this.xSize = 256;
		this.ySize = 256;
		this.tableID = CONTAINER.tableID;
    }

	private LogicBase logic(){
		return CONTAINER.logic();
	}
    
    
    
    //--------------------BASIC--------------------
    
    /** Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code) */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(isCurrentPlayer()) keyTyped2(typedChar, keyCode);

		if(keyCode == Keyboard.KEY_BACK){
			showDebug = !showDebug;
		}

		if(tableID == 0){
			// Collect Token and start game (Arcade Version) / FROM: Start Screen
			if(CONTAINER.turnstate() == 0 && tableID == 0 && keyCode == Keyboard.KEY_RETURN) {
				if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBetLow()) {
					if(CONTAINER.hasToken()) collectBet();
					start();
					shift = 2;
				}
			} else {
				// Collect Token and start game (Arcade Version) / FROM: Highscore Screen
				if(CONTAINER.turnstate() == 7 && tableID == 0 && keyCode == Keyboard.KEY_RETURN) {
					camera1 = 0;
					camera0 = 0;
					shift = 1;
					intro = 256;
					reset();
				} else {
					// Collect Token and start game (Arcade Version) / FROM: GameOver Screen
					if(CONTAINER.turnstate() == 5 && tableID == 0 && keyCode == Keyboard.KEY_RETURN) {
						if(logic().hasHighscore()) { // Show Highscore Screen
							turnstate(7);
							shift = 1;
						} else {
							//if(CONTAINER.hasToken()) {
							camera1 = 0;
							camera0 = 0;
							shift = 1;
							intro = 256;
							reset();
							//} else {
							//    start();
							//}
						}
					}
				}
			}

			// Toggle Pause Mode
			if(CONTAINER.turnstate() == 2 && tableID == 0 && keyCode == Keyboard.KEY_SPACE){
				CasinoPacketHandler.INSTANCE.sendToServer(new MessageStateServer(true, -1, CONTAINER.getPos()));
			}
		} else if(tableID == 3){
			// Collect Token and start game (Arcade Version) / FROM: Start Screen
			if(CONTAINER.turnstate() == 0 && keyCode == Keyboard.KEY_RETURN) {
				if(playerToken >= CONTAINER.getBetLow()) {
					collectBet();
					start();
				}
			} else {
				// Collect Token and start game (Arcade Version) / FROM: Highscore Screen
				if(CONTAINER.turnstate() == 5 && keyCode == Keyboard.KEY_RETURN) {
					reset();
				}
			}

			// Toggle Pause Mode
			if(CONTAINER.turnstate() == 2 && tableID == 0 && keyCode == Keyboard.KEY_SPACE){
				CasinoPacketHandler.INSTANCE.sendToServer(new MessageStateServer(true, -1, CONTAINER.getPos()));
			}
		}
    	
    	if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)){
    		//if(tc.board.getWorld().getBlockState(tc.board.getPos()).getBlock() instanceof BlockArcade) {
    		//	BlockArcade block = (BlockArcade) tc.board.getWorld().getBlockState(tc.board.getPos()).getBlock();
    		//	block.setPowerState(tc.board.inventory.get(1).getItem(), tc.board.getPos());
    		//}
            this.mc.player.closeScreen();
        }
        this.checkHotbarKeys(keyCode);
    }
    
	/** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0){
			if(CONTAINER.turnstate() == 0) { // Adjust Bet
				if(logic().hasHighscore())
					if(mouseRect(82, 164, 92, 26, mouseX, mouseY)) { turnstate(7); } // HIGHSCORE
				if(mouseRect(82-26, 204, 26, 26, mouseX, mouseY)) { if(bet > CONTAINER.getBetLow() ) bet--; } // BET MINUS
				if(mouseRect(82+92, 204, 26, 26, mouseX, mouseY)) { if(bet < CONTAINER.getBetHigh()) bet++; } // BET PLUS
				if(mouseRect(82, 204, 92, 26, mouseX, mouseY)) {
					// Start Game
					if(!CONTAINER.hasToken()){
						start();
					} else {
						if(playerToken >= bet){
							collectBet();
							playerToken = -1;
							start();
						}
					}
				}
			} else
			if(CONTAINER.turnstate() == 5) { // GameOver Screen
				if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
					if(logic().hasHighscore()) { // Show Highscore
						turnstate(7);
					} else { // Reset Game
						//if(CONTAINER.hasToken()){
						reset();
						//} else {
						//    start();
						//}
					}
				}
			} else
			if(CONTAINER.turnstate() == 7) { // Highscore Screen
				if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
					//if(CONTAINER.hasToken()){ // Reset Game
					reset();
					//} else {
					//    start();
					//}
				}
			} else if(CONTAINER.logic().isMultiplayer() && CONTAINER.turnstate() == 2 && !isCurrentPlayer()){ // Multiplayer Additional Player Join Button
				if(CONTAINER.logic().hasFreePlayerSlots()){
					if(mouseRect(26, 237, 78, 22, mouseX, mouseY)){
						if(!CONTAINER.hasToken()){
							addNewPlayer();
						} else {
							if(playerToken >= bet){
								collectBet();
								playerToken = -1;
								addNewPlayer();
							}
						}
					}
				}
			} else {
				if(isCurrentPlayer()) mouseClicked2(mouseX, mouseY, mouseButton);
			}
		}
    }
    
    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		// Debug Info (shown if Advanced Tooltips are enabled)
		if(showDebug){
			this.fontRenderer.drawString("PLAYER1: " + logic().currentPlayer[0],         tableID == 2 ? 355 : 260,  -40+15, 16777215);
			this.fontRenderer.drawString("PLAYER2: " + logic().currentPlayer[1],         tableID == 2 ? 355 : 260,  -40+25, 16777215);
			this.fontRenderer.drawString("PLAYER3: " + logic().currentPlayer[2],         tableID == 2 ? 355 : 260,  -40+35, 16777215);
			this.fontRenderer.drawString("PLAYER4: " + logic().currentPlayer[3],         tableID == 2 ? 355 : 260,  -40+45, 16777215);
			this.fontRenderer.drawString("PLAYER5: " + logic().currentPlayer[4],         tableID == 2 ? 355 : 260,  -40+55, 16777215);
			this.fontRenderer.drawString("PLAYER6: " + logic().currentPlayer[5],         tableID == 2 ? 355 : 260,  -40+65, 16777215);
			this.fontRenderer.drawString("TIMEOUT: " + logic().timeout,                  tableID == 2 ? 355 : 260,  -40+75, 16777215);
			this.fontRenderer.drawString("STATE:   " + logic().turnstate,                tableID == 2 ? 355 : 260,  -40+85, 16777215);
			this.fontRenderer.drawString("PLAYERS: " + logic().getFirstFreePlayerSlot(), tableID == 2 ? 355 : 260,  -40+95, 16777215);
			this.fontRenderer.drawString("ACTIVE:  " + logic().activePlayer,             tableID == 2 ? 355 : 260,  -40+105, 16777215);

			this.fontRenderer.drawString("table:         " + logic().tableID,                               tableID == 2 ? 355 : 260,  -40+115, 16777215);
			this.fontRenderer.drawString("points:        " + logic().scorePoint,                            tableID == 2 ? 355 : 260,  -40+125, 16777215);
			this.fontRenderer.drawString("level:         " + logic().scoreLevel,                            tableID == 2 ? 355 : 260,  -40+135, 16777215);
			this.fontRenderer.drawString("lives:         " + logic().scoreLives,                            tableID == 2 ? 355 : 260,  -40+145, 16777215);
			this.fontRenderer.drawString("hand:          " + logic().hand,                                  tableID == 2 ? 355 : 260,  -40+155, 16777215);
			this.fontRenderer.drawString("reward:        " + logic().reward.toString(),                     tableID == 2 ? 355 : 260,  -40+165, 16777215);
			this.fontRenderer.drawString("selector:      " + logic().selector.X + ":" + logic().selector.Y, tableID == 2 ? 355 : 260,  -40+175, 16777215);
			this.fontRenderer.drawString("tileentity:    " + CONTAINER.inventory.toString().substring(27),  tableID == 2 ? 355 : 260,  -40+185, 16777215);
			this.fontRenderer.drawString("playertoken:   " + playerToken,                                   tableID == 2 ? 355 : 260,  -40+195, 16777215);
			this.fontRenderer.drawString("boardToken:    " + CONTAINER.getBetStorage(),                     tableID == 2 ? 355 : 260,  -40+205, 16777215);
			this.fontRenderer.drawString("bet low:       " + CONTAINER.getBetLow(),                         tableID == 2 ? 355 : 260,  -40+215, 16777215);
			this.fontRenderer.drawString("bet high:      " + CONTAINER.getBetHigh(),                        tableID == 2 ? 355 : 260,  -40+225, 16777215);
			this.fontRenderer.drawString("bet player:    " + bet,                                           tableID == 2 ? 355 : 260,  -40+235, 16777215);
			this.fontRenderer.drawString("is creative:   " + CONTAINER.isCreative(),                        tableID == 2 ? 355 : 260,  -40+245, 16777215);
			this.fontRenderer.drawString("logic:         " + CONTAINER.logic().toString().substring(27),    tableID == 2 ? 355 : 260,  -40+255, 16777215);
			this.fontRenderer.drawString("score last:    " + logic().scoreLast,                             tableID == 2 ? 355 : 260,  -40+265, 16777215);
			this.fontRenderer.drawString("has highscore: " + logic().hasHighscore(),                        tableID == 2 ? 355 : 260,  -40+275, 16777215);
			this.fontRenderer.drawString("has token:     " + CONTAINER.hasToken(),                          tableID == 2 ? 355 : 260,  -40+285, 16777215);
			this.fontRenderer.drawString("board:  " + CONTAINER.toString().substring(27),                   tableID == 2 ? 355 : 260,  -40+295, 16777215);
			this.fontRenderer.drawString("module: " + CONTAINER.inventory.getModule().getUnlocalizedName(), tableID == 2 ? 355 : 260,  -40+305, 16777215);
		}

		if(logic() instanceof LogicDummy) return;

		// Search for tokens in PlayerInventory
		if(playerToken == -1 && logic().turnstate < 4) validateBet();

		// Multiplayer Additional Player Join Button
		if(CONTAINER.logic().isMultiplayer() && CONTAINER.turnstate() == 2 && !isCurrentPlayer()){
			if(CONTAINER.logic().hasFreePlayerSlots()){
				drawFont("BET:", 158, 246-2);
				this.itemRender.renderItemIntoGUI(CONTAINER.getToken(), 158+20, 242-2);
				if(CONTAINER.getBetLow() > 1) drawFont("x" + CONTAINER.getBetLow(), 158+50, 246-2);
			}
		}

		// Draw Start Screen Information (Card Table)
		if(CONTAINER.turnstate() == 0 && tableID != 0){
			if(tableID < 3){
				if(CONTAINER.hasToken() && CONTAINER.getBetHigh() > 0) {
					if(CONTAINER.getBetLow() == CONTAINER.getBetHigh()) {
						drawFont("The bet is:", 30, 100);
						this.itemRender.renderItemIntoGUI(CONTAINER.getToken(), 100, 96);
						if(CONTAINER.getBetLow() > 1) drawFont("x" + CONTAINER.getBetLow(), 124, 100);
					} else {
						drawFont("The bets are:", 30, 100);
						this.itemRender.renderItemIntoGUI(CONTAINER.getToken(), 100, 96);
						drawFont("x" + CONTAINER.getBetLow() + " to x" + CONTAINER.getBetHigh(), 124, 100);
					}

					if(playerToken < CONTAINER.getBetLow()) {
						drawFont("You don't have enough Token to play...", 30, 120);
					} else {
						drawFont("Do you wish to play?", 30, 120);
					}
					if(CONTAINER.getBetHigh() != CONTAINER.getBetLow()) drawFont("Your Bet:  " + bet, 30, 140);
				} else {
					//this.fontRenderer.drawString("Free to play", 80, 170, 16777215);
				}
			} else {
				if(CONTAINER.hasToken() && CONTAINER.getBetHigh() > 0){
					this.fontRenderer.drawString("INSERT ", 128, 210, 16777215);
					this.itemRender.renderItemIntoGUI(CONTAINER.getToken(), 160, 206);
					if(CONTAINER.getBetLow() > 1) this.fontRenderer.drawString("x" + CONTAINER.getBetLow(), 180, 210, 16777215);
					this.fontRenderer.drawString("Press ENTER", 128, 225, 16777215);
				}
			}

			// Draw Highscore (Card Table)
		} else if(CONTAINER.turnstate() == 7 && tableID != 0){
			for(int i = 0; i < 18; i++) {
				drawFont(           logic().scoreName[i],  40, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
				drawFontInvert("" + logic().scoreHigh[i], 216, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
			}


			// Draw Start Screen Information (Arcade)
		} else if(CONTAINER.turnstate() == 0 && tableID == 0){
			if(CONTAINER.hasToken() && CONTAINER.getBetHigh() > 0) {
				this.fontRenderer.drawString("INSERT ", 90, 180, 16777215);
				this.itemRender.renderItemIntoGUI(CONTAINER.getToken(), 126, 176);
				if(CONTAINER.getBetLow() > 1) this.fontRenderer.drawString("x" + CONTAINER.getBetLow(), 145, 180, 16777215);
				if(playerToken < CONTAINER.getBetLow()) {
					this.fontRenderer.drawString("NOT ENOUGH TOKEN", 80, 220, colour);
				} else {
					this.fontRenderer.drawString("Press ENTER", 95, 220, colour);
				}
			} else {
				this.fontRenderer.drawString("Press ENTER", 95, 220, colour);
			}

			if(colourUP){
				colour += 65793;
				if(colour >= 16777215){
					colour = 16777215;
					colourUP = false;
				}
			} else {
				colour -= 65793;
				if(colour <= 0){
					colour = 0;
					colourUP = true;
				}
			}

			// Draw Highscore Information (Arcade)
		} else if(CONTAINER.turnstate() == 7 && tableID == 0){

			for(int i = 0; i < 18; i++) {
				drawFont(           logic().scoreName[i],  40, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
				drawFontInvert("" + logic().scoreHigh[i], 216, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
			}

			this.fontRenderer.drawString("Press ENTER", 95, 220, colour);

			if(colourUP){
				colour += 65793;
				if(colour >= 16777215){
					colour = 16777215;
					colourUP = false;
				}
			} else {
				colour -= 65793;
				if(colour <= 0){
					colour = 0;
					colourUP = true;
				}
			}
		} else {

			drawGuiContainerForegroundLayer2(mouseX, mouseY);

			if(CONTAINER.turnstate() == 5 && tableID == 0){
				this.fontRenderer.drawString("GAME OVER", 103, 200, 16777215);
				this.fontRenderer.drawString("Press ENTER", 95, 220, colour);

				if(colourUP){
					colour += 65793;
					if(colour >= 16777215){
						colour = 16777215;
						colourUP = false;
					}
				} else {
					colour -= 65793;
					if(colour <= 0){
						colour = 0;
						colourUP = true;
					}
				}
			}

			if(logic().turnstate == 4){ // ???
				gameOver();
			}
		}

		if(CONTAINER.getID() != logic().getID()){
			this.mc.player.closeScreen();
		}
    }
    
    /** Draws the background layer of this container (behind the items). */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if(tableID == 0) { // Arcade Background
			if(logic() instanceof LogicDummy){
				Random RANDOM = new Random();
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_STATIC);
				for(int y = 0; y < 8; y++){
					for(int x = 0; x < 6; x++){
						this.drawTexturedModalRect(guiLeft + 32 + 32*x, guiTop + 32*y, 32*RANDOM.nextInt(8), 32*RANDOM.nextInt(8), 32, 32);
					}
				}
			} else {
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_STARFIELD1);
				this.drawTexturedModalRect(guiLeft, guiTop, 0, shift == 0 ? 0 : camera1, 256, 256);
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_STARFIELD0);
				this.drawTexturedModalRect(guiLeft, guiTop, 0, shift == 0 ? 0 : camera0, 256, 256);
			}
		} else if(tableID < 3){ // Card Table Background
			this.mc.getTextureManager().bindTexture(tableID == 0 ? CasinoKeeper.TEXTURE_GROUND_ARCADE : getBackground());
			if(tableID == 2){
				this.drawTexturedModalRect(guiLeft-128+32, guiTop,  0, 0, this.xSize-32, this.ySize); // Background Left
				this.drawTexturedModalRect(guiLeft+128   , guiTop, 32, 0, this.xSize-32, this.ySize); // Background Right
			} else {
				this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
			}
		} else { // Slot Machine Background
			this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTMACHINE);
			this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
		}

		// Draws Logo from ItemModule
		if(CONTAINER.turnstate() <= 1) {
			drawLogo();
		}

		// Intro Animation (Only on Arcade)
		if(CONTAINER.turnstate() == 1) {
			//intro--;
			//if(intro == 0) {
				turnstate(2);
			//}
		}

		// MiniGame BackgroundDrawer
		if(CONTAINER.turnstate() >= 1 && CONTAINER.turnstate() < 6){
			if(logic().pause) GlStateManager.color(0.35F, 0.35F, 0.35F, 1.0F);
			drawGuiContainerBackgroundLayer2(partialTicks, mouseX, mouseY);
			if(isCurrentPlayer()) drawGuiContainerBackgroundLayer3(partialTicks, mouseX, mouseY);
			if(logic().pause) GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}

		// If NOT Ingame
		if((CONTAINER.turnstate() == 5 || CONTAINER.turnstate() == 0 || CONTAINER.turnstate() == 7) && (tableID == 1 || tableID == 2) && !(logic() instanceof LogicDummy)){
			if(CONTAINER.turnstate() == 5 && logic().hasHighscore()){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
				drawTexturedModalRect(guiLeft+89, guiTop+206, 0, 22, 78, 22); // Button Highscore
			} else
			if(CONTAINER.turnstate() == 5){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
				drawTexturedModalRect(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
			} else
			if(CONTAINER.turnstate() == 7){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
				drawTexturedModalRect(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
			} else
			if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBetLow()){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
				drawTexturedModalRect(guiLeft+89, guiTop+206, 78, 22, 78, 22); // Button New Game
			}
			if(CONTAINER.turnstate() == 0 && logic().hasHighscore()){
				drawTexturedModalRect(guiLeft+89, guiTop+166, 0, 22, 78, 22); // Button Highscore
			}
			if(CONTAINER.turnstate() == 0 && playerToken >= CONTAINER.getBetLow()) {
				if(bet > CONTAINER.getBetLow())  drawTexturedModalRect(guiLeft+82-26+2, guiTop+204+2, 234, 22, 22, 22); // Button Minus
				if(bet < CONTAINER.getBetHigh()) drawTexturedModalRect(guiLeft+82+92+2, guiTop+204+2, 234, 44, 22, 22); // Button Plus
			}
		}

		// Multiplayer Additional Player Join Button
		if(CONTAINER.logic().isMultiplayer() && CONTAINER.turnstate() == 2 && !isCurrentPlayer()){
			if(CONTAINER.logic().hasFreePlayerSlots()){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
				this.drawTexturedModalRect(guiLeft + 153, guiTop + 237, 78, 220, 78, 22);
				if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBetLow()){
					drawTexturedModalRect(guiLeft + 26, guiTop + 237, 0, 220, 78, 22);
				}
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
				this.drawTexturedModalRect(guiLeft + 128-16, guiTop + 232, 192, 32 + 32*CONTAINER.logic().getFirstFreePlayerSlot(), 32, 32);
			}
		}

		// Draws Arcade Border
		if(tableID == 0) {
			this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_ARCADE);
			this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);

			if(CONTAINER.turnstate() != 5) camera1 = (camera1 + shift)   % 256;
			if(CONTAINER.turnstate() != 5) camera0 = (camera0 + shift*2) % 256;
		}

		// Multiplayer Status
		if((logic().turnstate == 2 || logic().turnstate == 3) && logic().isMultiplayer()){
			this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
			for(int i = 0; i < logic().getFirstFreePlayerSlot(); i++){
				this.drawTexturedModalRect(guiLeft+(tableID == 2 ? 355-15 : 260-15), guiTop+32 + 36*i, 224, 32 + 32 * i, 32, 32);
			}
			if(logic().activePlayer < logic().getFirstFreePlayerSlot()){
				this.drawTexturedModalRect(guiLeft+(tableID == 2 ? 355-15 : 260-15), guiTop+32 + 36*logic().activePlayer, 224-32, 32 + 32 * logic().activePlayer, 32, 32);
			}
		}
    }

	private void gameOver(){
		int pos = getPlayerPosition();
		if (pos > -1 && playerToken != -1) {
			payBet(logic().reward[pos]);
			turnstate(10 + pos);
			playerToken = -1;
		}
		if(allCleared()){
			turnstate(5);
			if(logic().hasHighscore()) {
				CasinoPacketHandler.INSTANCE.sendToServer(new MessageScoreServer(PLAYER.player.getName(), logic().scorePoint, CONTAINER.getPos()));
			} else {
				turnstate(-3);
			}
		}
	}

	private boolean allCleared(){
		for(int i = 0; i < 6; i++){
			if(logic().reward[i] > 0) return false;
		}
		return true;
	}

	/** Draws Colored Card Table Background **/
	private ResourceLocation getBackground(){
		if(CONTAINER.color == EnumDyeColor.BLACK)      return CasinoKeeper.TEXTURE_GROUND_BLACK;
		if(CONTAINER.color == EnumDyeColor.BLUE)       return CasinoKeeper.TEXTURE_GROUND_BLUE;
		if(CONTAINER.color == EnumDyeColor.BROWN)      return CasinoKeeper.TEXTURE_GROUND_BROWN;
		if(CONTAINER.color == EnumDyeColor.CYAN)       return CasinoKeeper.TEXTURE_GROUND_CYAN;
		if(CONTAINER.color == EnumDyeColor.GRAY)       return CasinoKeeper.TEXTURE_GROUND_GRAY;
		if(CONTAINER.color == EnumDyeColor.GREEN)      return CasinoKeeper.TEXTURE_GROUND_GREEN;
		if(CONTAINER.color == EnumDyeColor.LIGHT_BLUE) return CasinoKeeper.TEXTURE_GROUND_LIGHTBLUE;
		if(CONTAINER.color == EnumDyeColor.LIME)       return CasinoKeeper.TEXTURE_GROUND_LIME;
		if(CONTAINER.color == EnumDyeColor.MAGENTA)    return CasinoKeeper.TEXTURE_GROUND_MAGENTA;
		if(CONTAINER.color == EnumDyeColor.ORANGE)     return CasinoKeeper.TEXTURE_GROUND_ORANGE;
		if(CONTAINER.color == EnumDyeColor.PINK)       return CasinoKeeper.TEXTURE_GROUND_PINK;
		if(CONTAINER.color == EnumDyeColor.PURPLE)     return CasinoKeeper.TEXTURE_GROUND_PURPLE;
		if(CONTAINER.color == EnumDyeColor.RED)        return CasinoKeeper.TEXTURE_GROUND_RED;
		if(CONTAINER.color == EnumDyeColor.SILVER)     return CasinoKeeper.TEXTURE_GROUND_SILVER;
		if(CONTAINER.color == EnumDyeColor.WHITE)      return CasinoKeeper.TEXTURE_GROUND_WHITE;
		if(CONTAINER.color == EnumDyeColor.YELLOW)     return CasinoKeeper.TEXTURE_GROUND_YELLOW;

		return CasinoKeeper.TEXTURE_GROUND_GRAY;
	}

	/** Checks if mouse is inside a rectangle **/
	protected boolean mouseRect(int x, int y, int width, int height, double mouseX, double mouseY){
		if(guiLeft + x < mouseX && mouseX < guiLeft + x + width){
			return guiTop + y < mouseY && mouseY < guiTop + y + height;
		}
		return false;
	}

	/** Scans the PlayerInventory for Tokens **/
	protected void validateBet(){ // ???
		playerToken = -2;
		if(bet < CONTAINER.getBetLow ()) bet = CONTAINER.getBetLow();
		if(bet > CONTAINER.getBetHigh()) bet = CONTAINER.getBetHigh();
		if(CONTAINER.hasToken()){
			Item item = CONTAINER.getToken().getItem();
			int count = 0;
			for(int i = 0; i < 36; i++){
				if(item == PLAYER.getStackInSlot(i).getItem()) count += PLAYER.getStackInSlot(i).getCount();
			}
			playerToken = count;
		}
	}

	/** Checks if Player can pay another bet (and automatically collects it IF TRUE) **/
	protected boolean anotherBet(){
		validateBet();
		if(playerToken >= bet){
			collectBet();
			return true;
		}
		return false;
	}

	/** Collects the bet from the Player **/
	protected void collectBet(){ // ???
		if(CONTAINER.hasToken()){
			//InventoryUtil.decreaseInventory(PLAYER, CONTAINER.getToken(), bet);
			PLAYER.clearMatchingItems(CONTAINER.getToken().getItem(), CONTAINER.getToken().getMetadata(), bet, null);
			CasinoPacketHandler.INSTANCE.sendToServer(new MessagePlayerServer(CONTAINER.getToken(), -bet));
			if(!CONTAINER.isCreative()) {
				CONTAINER.setBetStorage(CONTAINER.getBetStorage() + bet);
				CasinoPacketHandler.INSTANCE.sendToServer(new MessageBlockServer(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));
			}
		}
	}

	/** Pays the Reward to the Player **/
	private void payBet(int multi){ // ???
		if(multi <= 0) return;
		if(CONTAINER.hasToken()){
			if(!CONTAINER.isCreative()) {
				Item item = CONTAINER.getToken().getItem();
				int count = bet * multi; // multi == 1 -> Return bet
				int count2 = 0;

				count2 = Math.min(CONTAINER.getBetStorage(), count);

				CONTAINER.setBetStorage(CONTAINER.getBetStorage() - count);

				if(CONTAINER.getBetStorage() <= 0) {
					CONTAINER.setBetStorage(0);
					CONTAINER.setToken(new ItemStack(Blocks.AIR));
				}
				CasinoPacketHandler.INSTANCE.sendToServer(new MessageBlockServer(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));

				PLAYER.addItemStackToInventory(new ItemStack(CONTAINER.getToken().getItem(), count2, CONTAINER.getToken().getMetadata()));
				CasinoPacketHandler.INSTANCE.sendToServer(new MessagePlayerServer(CONTAINER.getToken(), count2));
			} else {
				Item item = CONTAINER.getToken().getItem();
				int count = bet * multi; // multi == 1 -> Return bet
				PLAYER.addItemStackToInventory(new ItemStack(CONTAINER.getToken().getItem(), count, CONTAINER.getToken().getMetadata()));
				CasinoPacketHandler.INSTANCE.sendToServer(new MessagePlayerServer(CONTAINER.getToken(), count));
			}
		}
	}

	protected void action(int action){
		CasinoPacketHandler.INSTANCE.sendToServer(new MessageStateServer(false, action, CONTAINER.getPos()));
	}

	protected void start(){
		Random r = new Random();
		CasinoPacketHandler.INSTANCE.sendToServer(new MessageStartServer(PLAYER.player.getName(), r.nextInt(1000000), CONTAINER.getPos()));
	}

	private void reset(){
		CasinoPacketHandler.INSTANCE.sendToServer(new MessageStateServer(true, 0, CONTAINER.getPos()));
	}

	protected void turnstate(int state){
		CasinoPacketHandler.INSTANCE.sendToServer(new MessageStateServer(true, state, CONTAINER.getPos()));
	}

	private void addNewPlayer(){
		CasinoPacketHandler.INSTANCE.sendToServer(new MessageStartServer(PLAYER.player.getName(), -1, CONTAINER.getPos()));
	}

	protected boolean isCurrentPlayer(){
		if(CONTAINER.logic().isMultiplayer()){
			if(CONTAINER.getCurrentPlayer(0).matches("void")){
				return true;
			}
			for(int i = 0; i < 6; i++){
				if(CONTAINER.getCurrentPlayer(i).matches(PLAYER.player.getName())){
					return true;
				}
			}
		} else {

			return CONTAINER.getCurrentPlayer(0).matches("void") || CONTAINER.getCurrentPlayer(0).matches(PLAYER.player.getName());
		}
		return false;
	}

	protected boolean isActivePlayer(){
		if(CONTAINER.logic().isMultiplayer()){
			for(int i = 0; i < 6; i++){
				if(CONTAINER.getCurrentPlayer(i).matches(PLAYER.player.getName())){
					if(i == logic().activePlayer){
						return true;
					}
				}
			}
		} else {
			return CONTAINER.getCurrentPlayer(0).matches(PLAYER.player.getName());
		}
		return false;
	}

	protected int getPlayerPosition(){
		if(CONTAINER.logic().isMultiplayer()){
			for(int i = 0; i < 6; i++){
				if(CONTAINER.getCurrentPlayer(i).matches(PLAYER.player.getName())){
					return i;
				}
			}
		} else {
			return CONTAINER.getCurrentPlayer(0).matches(PLAYER.player.getName()) ? 0 : -1;
		}
		return -1;
	}

	/** Draws String on x,y position with shadow **/
	protected void drawFont(String text, int x, int y){
		drawFont(text, x, y, grayscale);
	}

	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFont(String text, int x, int y, int color){
		this.fontRenderer.drawString(text,  x+1, y+1, 0);
		this.fontRenderer.drawString(text,  x+0, y+0, color);
	}

	/** Draws String on x,y position with shadow **/
	protected void drawFontInvert(String text, int x, int y){
		drawFontInvert(text, x, y, grayscale);
	}

	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFontInvert(String text, int x, int y, int color){
		int w = this.fontRenderer.getStringWidth(text);
		this.fontRenderer.drawString(text,  x+1 - w, y+1, 0);
		this.fontRenderer.drawString(text,  x+0 - w, y+0, color);
	}

	/** Draws String on x,y position with shadow **/
	protected void drawFontCenter(String text, int x, int y){
		drawFontCenter(text, x, y, grayscale);
	}

	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFontCenter(String text, int x, int y, int color){
		int w = this.fontRenderer.getStringWidth(text);
		this.fontRenderer.drawString(text,  x+1 - w/2, y+1, 0);
		this.fontRenderer.drawString(text,  x+0 - w/2, y+0, color);
	}



	/** Draws a Card **/
	public void drawCard(int posX, int posY, Card card){
		if(card.suit == -1) return;
		if(card.hidden){
			this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
		} else {
			if(card.suit <= 1) this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
			if(card.suit >= 2) this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
		}
		int texX = card.suit == -1 || card.hidden ? 0 : card.number % 8;
		int texY = card.suit == -1 || card.hidden ? 4 : (card.suit % 2) * 2 + card.number / 8;
		if(CasinoKeeper.config_animated_cards && !card.hidden){
			if(card.number >= 10){
				if(logic().frame == card.suit*12 + (card.number-10)*3){
					texX += 3;
				}
			}
		}
		drawTexturedModalRect(guiLeft + posX + card.shiftX, guiTop + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
	}

	/** Draws the Backside of a Card (also used for highlighter) **/
	public void drawCardBack(int posX, int posY, int color){
		if(color <= 6) this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
		else           this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
		drawTexturedModalRect(guiLeft + posX, guiTop + posY, (color%7) * 32, 4 * 48, 32, 48);
	}

	private void drawLetter(char c, int posX, int posY, int sizeX, int sizeY, int vanish){
		if(c == 'a') drawTexturedModalRect(posX, posY + vanish, 0*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'b') drawTexturedModalRect(posX, posY + vanish, 1*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'c') drawTexturedModalRect(posX, posY + vanish, 2*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'd') drawTexturedModalRect(posX, posY + vanish, 3*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'e') drawTexturedModalRect(posX, posY + vanish, 4*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'f') drawTexturedModalRect(posX, posY + vanish, 5*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'g') drawTexturedModalRect(posX, posY + vanish, 6*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'h') drawTexturedModalRect(posX, posY + vanish, 7*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'i') drawTexturedModalRect(posX, posY + vanish, 0*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'j') drawTexturedModalRect(posX, posY + vanish, 1*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'k') drawTexturedModalRect(posX, posY + vanish, 2*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'l') drawTexturedModalRect(posX, posY + vanish, 3*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'm') drawTexturedModalRect(posX, posY + vanish, 4*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'n') drawTexturedModalRect(posX, posY + vanish, 5*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'o') drawTexturedModalRect(posX, posY + vanish, 6*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'p') drawTexturedModalRect(posX, posY + vanish, 7*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'q') drawTexturedModalRect(posX, posY + vanish, 0*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'r') drawTexturedModalRect(posX, posY + vanish, 1*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 's') drawTexturedModalRect(posX, posY + vanish, 2*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 't') drawTexturedModalRect(posX, posY + vanish, 3*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'u') drawTexturedModalRect(posX, posY + vanish, 4*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'v') drawTexturedModalRect(posX, posY + vanish, 5*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'w') drawTexturedModalRect(posX, posY + vanish, 6*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'x') drawTexturedModalRect(posX, posY + vanish, 7*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'y') drawTexturedModalRect(posX, posY + vanish, 0*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == 'z') drawTexturedModalRect(posX, posY + vanish, 1*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '0') drawTexturedModalRect(posX, posY + vanish, 2*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '1') drawTexturedModalRect(posX, posY + vanish, 3*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '2') drawTexturedModalRect(posX, posY + vanish, 4*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '3') drawTexturedModalRect(posX, posY + vanish, 5*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '4') drawTexturedModalRect(posX, posY + vanish, 6*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '5') drawTexturedModalRect(posX, posY + vanish, 7*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '6') drawTexturedModalRect(posX, posY + vanish, 0*sizeX, 4*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '7') drawTexturedModalRect(posX, posY + vanish, 1*sizeX, 4*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '8') drawTexturedModalRect(posX, posY + vanish, 2*sizeX, 4*sizeY + vanish, sizeX, sizeY - vanish);
		if(c == '9') drawTexturedModalRect(posX, posY + vanish, 3*sizeX, 4*sizeY + vanish, sizeX, sizeY - vanish);
	}

	/** Draws Logo from ItemModule **/
	private void drawLogo() {
		int move = 256 - intro; // Move logo up
		int vanish = move < 32 ? 0 : move-30 > 32 ? 32 : move - 30;
		if(move >= 30) {
			int i = 0;
		}

		int sizeX = 0;
		String[] logo = getGameName().split("_");
		if(tableID <= 2){
			if(tableID == 0){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
				sizeX = 16;
			} else if(tableID == 1 || tableID == 2){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
				sizeX = 32;
				vanish = 0;
			}

			for(int i = 0; i < logo.length; i++){
				for(int k = 0; k < logo[i].length(); k++){
					drawLetter(logo[i].charAt(k), guiLeft + 128 - logo[i].length()*(sizeX/2) + sizeX*k, guiTop + 32 + 32*i - move, 32, 32, vanish);
				}
			}
		}
	}

	protected void drawMino(int posX, int posY, int idX, int idY){
		this.drawTexturedModalRect(guiLeft + posX, guiTop + posY, 24 * idX, 24 * idY, 24, 24);
	}

	protected void drawMino(int posX, int posY){
		drawMino(posX, posY, 0, 0);
	}

	protected void drawMinoSmall(int posX, int posY, int id, boolean alternate){
		if(alternate){
			this.drawTexturedModalRect(guiLeft + posX, guiTop + posY, 240, 16 * id, 16, 16);
		} else {
			this.drawTexturedModalRect(guiLeft + posX, guiTop + posY, 16 * id, 240, 16, 16);
		}
	}

	protected void drawMinoSmall(int posX, int posY){
		drawMinoSmall(posX, posY, 0, false);
	}

	protected void drawDigi(int posX, int posY, int idX, int idY){
		this.drawTexturedModalRect(guiLeft + posX, guiTop + posY, 16 * idX, 16 + 16 * idY, 16, 16);
	}

	protected void drawDigi(int posX, int posY){
		drawDigi(posX, posY, 0, 0);
	}

	protected void drawDigiSmall(int posX, int posY, int id){
		this.drawTexturedModalRect(guiLeft + posX + 0, guiTop + posY + 0, 16 * id +  0, 16   , 6, 6);
		this.drawTexturedModalRect(guiLeft + posX + 6, guiTop + posY + 0, 16 * id + 10, 16   , 6, 6);
		this.drawTexturedModalRect(guiLeft + posX + 0, guiTop + posY + 6, 16 * id +  0, 16+10, 6, 6);
		this.drawTexturedModalRect(guiLeft + posX + 6, guiTop + posY + 6, 16 * id + 10, 16+10, 6, 6);

	}

	protected void drawDigiSmall(int posX, int posY){
		drawDigiSmall(posX, posY, 0);
	}

	protected void drawDigiSymbol(int posX, int posY, int id){
		this.drawTexturedModalRect(guiLeft + posX, guiTop + posY, 16 * id, 0, 16, 16);
	}

	protected void drawDigiSymbol(int posX, int posY){
		drawDigiSymbol(posX, posY, 0);
	}

	protected void drawButton(int posX, int posY, int id){

	}

	protected void drawShip(Ship ship, int shipID, boolean hasLookDirection, boolean animate){
		int frame = logic().turnstate < 4 && animate ? (logic().frame % 12) / 2 : 0;
		if(frame == 4) frame = 2;
		if(frame == 5) frame = 1;
		int direction = hasLookDirection ? ship.Get_LookDirection() : 0;
		this.drawTexturedModalRect(guiLeft + 32 + ship.Get_Pos().X, guiTop + 8 + ship.Get_Pos().Y, 64*(shipID%4) + 16*frame, 128 + direction*16 + (shipID/4)*64, 16, 16);
	}

	protected void drawShip(Vector2 vec, int shipID){
		int frame = logic().turnstate < 4 ? (logic().frame % 12) / 2 : 0;
		if(frame == 4) frame = 2;
		if(frame == 5) frame = 1;
		this.drawTexturedModalRect(guiLeft + 32 + vec.X*16, guiTop + 8 + vec.Y*16, 64*(shipID%4) + 16*frame, 128 + (shipID/4)*64, 16, 16);
	}




	//----------------------------------------OVERRIDES----------------------------------------//

	protected abstract void keyTyped2(char typedChar, int keyCode) throws IOException;
	protected abstract void mouseClicked2(double mouseX, double mouseY, int mouseButton);
	protected abstract void drawGuiContainerForegroundLayer2(int mouseX, int mouseY);
	protected abstract void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY);
	protected abstract void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY);
	protected abstract String getGameName();
    
}
