package mod.casinocraft.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.network.ServerBlockMessage;
import mod.casinocraft.network.ServerPlayerMessage;
import mod.casinocraft.network.ServerScoreMessage;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Card;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiCasino extends GuiContainer {
	
	/** The player inventory bound to this GUI. */
    private   final InventoryPlayer playerInventory;
    protected final TileEntityCasino tc;
    
    protected int table; // 0 - Arcade, 1 - Table Normal, 2 - Table Wide
    protected int playerToken = -1;
    protected int bet = 0;
    
    protected int colour = 0;
    protected boolean colourUP = true;
    
    protected int camera1 = 0;
    protected int camera0 = 0;
    protected int shift = 1;
    protected int intro = 256;
    
    //private Item logo;
    
    
    //--------------------CONSTRUCTOR--------------------
    
    /** Basic Constructor **/
    public GuiCasino(InventoryPlayer playerInv, IInventory furnaceInv, Item logo){
        super(new ContainerCasino(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tc = (TileEntityCasino) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
		this.table = 0;
		//this.logo = logo;
		tc.board.setupHighscore(tc.board.inventory.get(1).getItem());
    }
    
    /** Custom Constructor **/
    public GuiCasino(ContainerCasino container, InventoryPlayer playerInv, IInventory furnaceInv, int table, Item logo){
        super(new ContainerCasino(furnaceInv));
        this.playerInventory = playerInv;
        this.tc = (TileEntityCasino) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
		this.table = table;
		//this.logo = logo;
		tc.board.setupHighscore(tc.board.inventory.get(1).getItem());
    }
    
    
    
    //--------------------BASIC--------------------
    
    /** Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code) */
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
    	keyTyped2(typedChar, keyCode);
    	
    	if(tc.turnstate == 0 && table == 0 && keyCode == Keyboard.KEY_RETURN) {
    		if(!tc.hasToken() || playerToken >= tc.getBetLow()) {
    			if(tc.hasToken()) CollectBet();
    			tc.actionStart(2);
    			shift = 2;
    			tc.turnstate = 1;
    		}
    	} else
		if(tc.turnstate == 7 && table == 0 && keyCode == Keyboard.KEY_RETURN) {
    		if(tc.hasToken()) {
    			camera1 = 0;
    			camera0 = 0;
    		    shift = 1;
    		    intro = 256;
    		    tc.turnstate = 0;
    		} else {
    			tc.actionStart(2);
    			if(tc.turnstate == 0) {
    				intro = 256;
    				shift = 1;
    			}
    		}
    	} else
    	if(tc.turnstate == 5 && table == 0 && keyCode == Keyboard.KEY_RETURN) {
    		if(hasHighscore()) {
    			tc.turnstate = 7;
    			shift = 1;
    		} else {
    			if(tc.hasToken()) {
        			camera1 = 0;
        			camera0 = 0;
        		    shift = 1;
        		    intro = 256;
        		    tc.turnstate = 0;
        		} else {
        			tc.actionStart(2);
        		}
    		}
    	}
    	
    	if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)){
    		if(tc.board.getWorld().getBlockState(tc.board.getPos()).getBlock() instanceof BlockArcade) {
    			BlockArcade block = (BlockArcade) tc.board.getWorld().getBlockState(tc.board.getPos()).getBlock();
    			block.setPowerState(tc.board.inventory.get(1).getItem(), tc.board.getPos());
    		}
            this.mc.player.closeScreen();
        }
        this.checkHotbarKeys(keyCode);
    }
    
	/** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0){
        	if(tc.turnstate == 0) {
        		if(mouseRect(82-26, 204, 26, 26, mouseX, mouseY)) { if(bet > tc.getBetLow() ) bet--; } // BET MINUS
        		if(mouseRect(82+92, 204, 26, 26, mouseX, mouseY)) { if(bet < tc.getBetHigh()) bet++; } // BET PLUS
        		if(mouseRect(82, 204, 92, 26, mouseX, mouseY)) {
        			if(!tc.hasToken()){
            			tc.actionStart(table+1);
            		} else {
            			if(playerToken >= bet){
            				CollectBet();
            				playerToken = -1;
            				tc.actionStart(table+1);
            			}
            		}
        		}
        	} else
        	if(tc.turnstate == 5) {
        		if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
    				if(hasHighscore()) {
    					tc.turnstate = 7;
    				} else {
    					if(tc.hasToken()){
        					tc.actionStart(table+1);
        				} else {
        					tc.turnstate = 0;
        				}
    				}
    			}
        	} else
        	if(tc.turnstate == 7) {
        		if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
    				if(tc.hasToken()){
    					tc.actionStart(table+1);
    				} else {
    					tc.turnstate = 0;
    				}
    			}
        	} else {
        		mouseClicked2(mouseX, mouseY, mouseButton);
        	}
        }
    }
    
    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	if(this.mc.gameSettings.advancedItemTooltips){
    		this.fontRenderer.drawString("turnstate: "  + tc.turnstate,   table == 2 ? 355 : 260, 15, 16777215);
    		this.fontRenderer.drawString("difficulty: " + tc.difficulty,  table == 2 ? 355 : 260, 25, 16777215);
    		this.fontRenderer.drawString("points: "     + tc.scorePoints, table == 2 ? 355 : 260, 35, 16777215);
    		this.fontRenderer.drawString("level: "      + tc.scoreLevel,  table == 2 ? 355 : 260, 45, 16777215);
    		this.fontRenderer.drawString("lives: "      + tc.scoreLives,  table == 2 ? 355 : 260, 55, 16777215);
    		this.fontRenderer.drawString("hand: "       + tc.hand,        table == 2 ? 355 : 260, 65, 16777215);
    		this.fontRenderer.drawString("reward: "     + tc.reward,      table == 2 ? 355 : 260, 75, 16777215);
    		this.fontRenderer.drawString("selector: "   + tc.selector.X + ":" + tc.selector.Y, table == 2 ? 355 : 260, 85, 16777215);
    		this.fontRenderer.drawString("tileentity: " + tc.toString().substring(33), table == 2 ? 355 : 260, 95, 16777215);
    		this.fontRenderer.drawString("playertoken: " + playerToken,        table == 2 ? 355 : 260, 105, 16777215);
    		this.fontRenderer.drawString("boardToken: "  + tc.getBetStorage(), table == 2 ? 355 : 260, 115, 16777215);
    		this.fontRenderer.drawString("bet low: "     + tc.getBetLow(),     table == 2 ? 355 : 260, 125, 16777215);
    		this.fontRenderer.drawString("bet high: "    + tc.getBetHigh(),    table == 2 ? 355 : 260, 135, 16777215);
    		this.fontRenderer.drawString("bet player: "  + bet,                table == 2 ? 355 : 260, 145, 16777215);
    		this.fontRenderer.drawString("is creative: " + tc.board.isCreative,table == 2 ? 355 : 260, 155, 16777215);
    		this.fontRenderer.drawString("score token: " + tc.board.getScoreToken().getUnlocalizedName(),table == 2 ? 355 : 260, 165, 16777215);
    		this.fontRenderer.drawString("score last: " + getScoreLast(),table == 2 ? 355 : 260, 175, 16777215);
    		this.fontRenderer.drawString("has highscore: " + hasHighscore(),table == 2 ? 355 : 260, 185, 16777215);
    		this.fontRenderer.drawString("has token: " + tc.hasToken(),table == 2 ? 355 : 260, 195, 16777215);
    		this.fontRenderer.drawString("board: " + tc.board.toString().substring(33), table == 2 ? 355 : 260, 205, 16777215);
    	}
    	
    	if(playerToken == -1) ValidateBet();
    	
    	if(tc.turnstate == 0 && table != 0){
    		if(tc.hasToken() && tc.getBetHigh() > 0) {
    			if(tc.getBetLow() == tc.getBetHigh()) {
    				this.fontRenderer.drawString("The current bet is:", 31, 101, 0);
    				this.fontRenderer.drawString("The current bet is:", 30, 100, 16777215);
    				this.itemRender.renderItemIntoGUI(tc.getTokenStack(), 130+12, 96);
        			if(tc.getBetLow() > 1) this.fontRenderer.drawString("x" + tc.getBetLow(), 145+16, 101, 0);
        			if(tc.getBetLow() > 1) this.fontRenderer.drawString("x" + tc.getBetLow(), 145+15, 100, 16777215);
    			} else {
    				this.fontRenderer.drawString("The bets are:", 31, 101, 0);
    				this.fontRenderer.drawString("The bets are:", 30, 100, 16777215);
    				this.itemRender.renderItemIntoGUI(tc.getTokenStack(), 130+12, 96);
        			this.fontRenderer.drawString("x" + tc.getBetLow() + "  to  x" + tc.getBetHigh(), 145+11, 101, 0);
        			this.fontRenderer.drawString("x" + tc.getBetLow() + "  to  x" + tc.getBetHigh(), 145+15, 100, 16777215);
    			}
    			
    			if(playerToken < tc.getBetLow()) {
    				this.fontRenderer.drawString("You don't have enough Token to play...", 31, 121, 0);
    				this.fontRenderer.drawString("You don't have enough Token to play...", 30, 120, 16777215);
    			} else {
    				this.fontRenderer.drawString("Do you wish to play?", 31, 121, 0);
    				this.fontRenderer.drawString("Do you wish to play?", 30, 120, 16777215);
    			}
    			if(tc.getBetHigh() != tc.getBetLow()) this.fontRenderer.drawString("Your Bet:  " + bet, 31, 141, 0);
    			if(tc.getBetHigh() != tc.getBetLow()) this.fontRenderer.drawString("Your Bet:  " + bet, 30, 140, 16777215);
    		} else {
    			//this.fontRenderer.drawString("Free to play", 80, 170, 16777215);
    		}
    		
    	} else if(tc.turnstate == 7 && table != 0){
    		for(int i = 0; i < 18; i++) {
    			this.fontRenderer.drawString(     getScoreName(i)  ,  41, 26 + 10*i, 0);
    			this.fontRenderer.drawString(     getScoreName(i)  ,  40, 25 + 10*i, getScoreLast() == i ? 16777215/2 : 16777215);
    			this.fontRenderer.drawString("" + getScorePoints(i), 201, 26 + 10*i, 0);
    			this.fontRenderer.drawString("" + getScorePoints(i), 200, 25 + 10*i, getScoreLast() == i ? 16777215/2 : 16777215);
    		}
    		
    	} else if(tc.turnstate == 0 && table == 0){
    		if(tc.hasToken() && tc.getBetHigh() > 0) {
    			this.fontRenderer.drawString("INSERT ", 90, 180, 16777215);
				this.itemRender.renderItemIntoGUI(new ItemStack(tc.getToken()), 126, 176);
    			if(tc.getBetLow() > 1) this.fontRenderer.drawString("x" + tc.getBetLow(), 145, 180, 16777215);
    			if(playerToken < tc.getBetLow()) {
    				this.fontRenderer.drawString("NOT ENOUGH TOKEN", 80, 210, colour);
    			} else {
    				this.fontRenderer.drawString("Press ENTER", 95, 210, colour);
    			}
    		} else {
    			this.fontRenderer.drawString("Press ENTER", 95, 210, colour);
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
    	} else if(tc.turnstate == 7 && table == 0){
    		
    		for(int i = 0; i < 18; i++) {
    			this.fontRenderer.drawString(     getScoreName(i)  ,  40, 25 + 10*i, getScoreLast() == i ? 16777215/2 : 16777215);
    			this.fontRenderer.drawString("" + getScorePoints(i), 200, 25 + 10*i, getScoreLast() == i ? 16777215/2 : 16777215);
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
    		
        	if(tc.turnstate == 4){
        		tc.turnstate = 5;
        		gameOver();
        		playerToken = -1;
        	}
    	}
    }
    
    /** Draws the background layer of this container (behind the items). */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	if(table == 0) { // Arcade Background
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_STARFIELD1);
			this.drawTexturedModalRect(guiLeft, guiTop, 0, shift == 0 ? 0 : camera1, 256, 256);
			this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_STARFIELD0);
			this.drawTexturedModalRect(guiLeft, guiTop, 0, shift == 0 ? 0 : camera0, 256, 256);
    	} else { // Card Table Background
    		this.mc.getTextureManager().bindTexture(table == 0 ? CasinoKeeper.TEXTURE_GROUND_ARCADE : getBackground());
            if(table == 2){
            	this.drawTexturedModalRect(guiLeft-128+32, guiTop,  0, 0, this.xSize-32, this.ySize); // Background Left
         	   this.drawTexturedModalRect(guiLeft+128   , guiTop, 32, 0, this.xSize-32, this.ySize); // Background Right
            } else {
            	this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
            }
    	}
    	
    	if(tc.turnstate <= 1) {
    		drawLogo();
    	}
        
    	if(tc.turnstate == 1) {
    		intro--;
    		if(intro == 0) {
    			tc.turnstate = 2;
    		}
    	}
    	
        if(tc.turnstate >= 1 && tc.turnstate < 6) drawGuiContainerBackgroundLayer2(partialTicks, mouseX, mouseY);
        
		if((tc.turnstate == 5 || tc.turnstate == 0 || tc.turnstate == 7) && table > 0){
			if(tc.turnstate == 5 && hasHighscore()){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
				drawTexturedModalRect(guiLeft+89, guiTop+206, 0, 22, 78, 22); // Button Highcore
			} else
			if(tc.turnstate == 5){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
				drawTexturedModalRect(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
			} else
			if(tc.turnstate == 7){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
				drawTexturedModalRect(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
			} else
			if(!tc.hasToken() || playerToken >= tc.getBetLow()){
				this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
				drawTexturedModalRect(guiLeft+89, guiTop+206, 78, 22, 78, 22); // Button New Game
			}
			if(tc.turnstate == 0 && playerToken >= tc.getBetLow()) {
        		if(bet > tc.getBetLow())  drawTexturedModalRect(guiLeft+82-26+2, guiTop+204+2, 234, 22, 22, 22); // Button Minus
        		if(bet < tc.getBetHigh()) drawTexturedModalRect(guiLeft+82+92+2, guiTop+204+2, 234, 44, 22, 22); // Button Plus
			}
		}
        
		if(table == 0) {
			this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_ARCADE);
    		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    		
    		if(tc.turnstate != 5) camera1 = (camera1 + shift)   % 256;
    		if(tc.turnstate != 5) camera0 = (camera0 + shift*2) % 256;
		}
		
		if(tc.turnstate >= 2) tc.update();
		
    }
    
    protected void gameOver(){
    	payBet(tc.reward);
    	if(hasHighscore()) {
    		tc.board.addScore(playerInventory.player.getName(), tc.scorePoints);
    		CasinoPacketHandler.INSTANCE.sendToServer(new ServerScoreMessage(tc.board.getScoreToken(), tc.board.scoreName, tc.board.scorePoints, tc.getPos()));
    	}
    }
    
    
    
    //--------------------EMPTY--------------------
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{ }
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException { }
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){ }
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){ }
    
    
    
    //--------------------HELPER--------------------
    
    private ResourceLocation getBackground(){
    	if(tc.color == EnumDyeColor.BLACK)      return CasinoKeeper.TEXTURE_GROUND_BLACK;
    	if(tc.color == EnumDyeColor.BLUE)       return CasinoKeeper.TEXTURE_GROUND_BLUE;
    	if(tc.color == EnumDyeColor.BROWN)      return CasinoKeeper.TEXTURE_GROUND_BROWN;
    	if(tc.color == EnumDyeColor.CYAN)       return CasinoKeeper.TEXTURE_GROUND_CYAN;
    	if(tc.color == EnumDyeColor.GRAY)       return CasinoKeeper.TEXTURE_GROUND_GRAY;
    	if(tc.color == EnumDyeColor.GREEN)      return CasinoKeeper.TEXTURE_GROUND_GREEN;
    	if(tc.color == EnumDyeColor.LIGHT_BLUE) return CasinoKeeper.TEXTURE_GROUND_LIGHTBLUE;
    	if(tc.color == EnumDyeColor.LIME)       return CasinoKeeper.TEXTURE_GROUND_LIME;
    	if(tc.color == EnumDyeColor.MAGENTA)    return CasinoKeeper.TEXTURE_GROUND_MAGENTA;
    	if(tc.color == EnumDyeColor.ORANGE)     return CasinoKeeper.TEXTURE_GROUND_ORANGE;
    	if(tc.color == EnumDyeColor.PINK)       return CasinoKeeper.TEXTURE_GROUND_PINK;
    	if(tc.color == EnumDyeColor.PURPLE)     return CasinoKeeper.TEXTURE_GROUND_PURPLE;
    	if(tc.color == EnumDyeColor.RED)        return CasinoKeeper.TEXTURE_GROUND_RED;
    	if(tc.color == EnumDyeColor.SILVER)     return CasinoKeeper.TEXTURE_GROUND_SILVER;
    	if(tc.color == EnumDyeColor.WHITE)      return CasinoKeeper.TEXTURE_GROUND_WHITE;
    	if(tc.color == EnumDyeColor.YELLOW)     return CasinoKeeper.TEXTURE_GROUND_YELLOW;
    	
    	return CasinoKeeper.TEXTURE_GROUND_GRAY;
    }
    
    /** Checks if mouse is inside a rectangle **/
    protected boolean mouseRect(int x, int y, int width, int height, int mouseX, int mouseY){
    	if(guiLeft + x < mouseX && mouseX < guiLeft + x + width){
            return guiTop + y < mouseY && mouseY < guiTop + y + height;
    	}
    	return false;
    }
    
    private boolean ChangeTexture(Card v){
	   	
	   	return true;
   }
    
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
    	drawTexturedModalRect(guiLeft + posX + card.shiftX, guiTop + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
    }
	
    public void drawCardBack(int posX, int posY, int color){
	   	if(color <= 6) this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
	   	else           this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
    	drawTexturedModalRect(guiLeft + posX, guiTop + posY, (color%7) * 32, 4 * 48, 32, 48);
    }
    
    public void drawMino(int mino, int x, int y){
    	if(mino != -1) this.drawTexturedModalRect(guiLeft + 8 + 16*x, guiTop + 8 + 16*y, MinoPos(tc.turnstate >= 4 ? 9 : mino, true), MinoPos(tc.turnstate >= 4 ? 9 : mino, false), 16, 16);
    }
    
    public int MinoPos(int mino, boolean xy){
    	if(mino == 0){ if(xy) return 16; else return  0; }
    	if(mino == 1){ if(xy) return 32; else return  0; }
    	if(mino == 2){ if(xy) return 48; else return  0; }
    	if(mino == 3){ if(xy) return 64; else return  0; }
    	if(mino == 4){ if(xy) return 80; else return  0; }
    	if(mino == 5){ if(xy) return  0; else return 64; }
    	if(mino == 6){ if(xy) return 16; else return 64; }
    	if(mino == 7){ if(xy) return 32; else return 64; }
    	if(mino == 8){ if(xy) return 48; else return 64; }
    	if(mino == 9){ if(xy) return 64; else return 64; }
    	return 0;
    }
    
    protected void ValidateBet(){
    	if(bet < tc.getBetLow ()) bet = tc.getBetLow();
    	if(bet > tc.getBetHigh()) bet = tc.getBetHigh();
    	if(tc.hasToken()){
    		Item item = tc.getTokenStack().getItem();
    		int meta = tc.getTokenStack().getMetadata();
    		int count = 0;
    		for(int i = 0; i < 36; i++){
    			if(item == playerInventory.getStackInSlot(i).getItem() && meta == playerInventory.getStackInSlot(i).getMetadata()) count += playerInventory.getStackInSlot(i).getCount();
    		}
    		playerToken = count;
    	}
    }
    
    protected boolean AnotherBet(){
    	ValidateBet();
    	if(playerToken >= bet){
    		CollectBet();
    		return true;
    	}
    	return false;
    }
    
    protected void CollectBet(){
    	if(tc.hasToken()){
    		playerInventory.clearMatchingItems(tc.getTokenStack().getItem(), tc.getTokenStack().getMetadata(), bet, null);
    		CasinoPacketHandler.INSTANCE.sendToServer(new ServerPlayerMessage(tc.getToken(), tc.getTokenStack().getMetadata(), -bet));
    		//CasinoPacketHandler.INSTANCE.sendToAll(new ClientPlayerMessage(tc.getToken(), tc.getTokenStack().getMetadata(), -bet));
    		if(!tc.board.isCreative) {
    			tc.board.bet_storage+=bet;
    			CasinoPacketHandler.INSTANCE.sendToServer(new ServerBlockMessage(tc.board.inventory.get(0), tc.board.inventory.get(1), tc.board.inventory.get(4), tc.board.bet_storage, tc.getPos()));
    		}
    	}
    }
    
    protected void payBet(int multi){
    	if(multi <= 0) return;
    	if(tc.hasToken()){
    		if(!tc.board.isCreative) {
    			Item item = tc.getTokenStack().getItem();
    			int meta = tc.getTokenStack().getMetadata();
        		int count = bet * multi; // multi == 1 -> Return bet
        		int count2 = 0;
        		
        		if(tc.getBetStorage() >= count) {
        			count2 = count;
        		} else {
        			count2 = tc.getBetStorage();
        		}
        		
        		tc.board.bet_storage-=count;
        		
        		if(tc.getBetStorage() <= 0) {
    				tc.board.bet_storage = 0;
    				tc.board.setToken(new ItemStack(Blocks.AIR));
    			}
        		
        		CasinoPacketHandler.INSTANCE.sendToServer(new ServerBlockMessage(tc.board.inventory.get(0), tc.board.inventory.get(1), tc.board.inventory.get(4), tc.board.bet_storage, tc.getPos()));
        		
        		playerInventory.addItemStackToInventory(new ItemStack(item, count2, meta));
        		CasinoPacketHandler.INSTANCE.sendToServer(new ServerPlayerMessage(item, meta, count2));
    		} else {
    			Item item = tc.getTokenStack().getItem();
    			int meta = tc.getTokenStack().getMetadata();
        		int count = bet * multi; // multi == 1 -> Return bet
        		playerInventory.addItemStackToInventory(new ItemStack(item, count, meta));
        		CasinoPacketHandler.INSTANCE.sendToServer(new ServerPlayerMessage(item, meta, count));
    		}
    	}
    }
    
    private Item getModule() {
    	return tc.board.getModule();
    }
    
    private void drawLogo() {
    	int move = 256 - intro; // Move logo up
    	int vanish = move < 32 ? 0 : move-32 > 34 ? 34 : move - 32;
    	if(move >= 32) {
    		int i = 0;
    	}
    	
    	if(getModule() == CasinoKeeper.MODULE_TETRIS) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
    		//GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    		this.drawTexturedModalRect(guiLeft + 56 + 0*24, guiTop + 32 - move + vanish, 1*40, 3*34 + vanish, 40, 34 - vanish); // T
    		this.drawTexturedModalRect(guiLeft + 56 + 1*24, guiTop + 32 - move + vanish, 4*40, 0*34 + vanish, 40, 34 - vanish); // E
    		this.drawTexturedModalRect(guiLeft + 56 + 2*24, guiTop + 32 - move + vanish, 1*40, 3*34 + vanish, 40, 34 - vanish); // T
    		this.drawTexturedModalRect(guiLeft + 56 + 3*24, guiTop + 32 - move + vanish, 5*40, 2*34 + vanish, 40, 34 - vanish); // R
    		this.drawTexturedModalRect(guiLeft + 56 + 4*24, guiTop + 32 - move + vanish, 2*40, 1*34 + vanish, 40, 34 - vanish); // I
    		this.drawTexturedModalRect(guiLeft + 56 + 5*24, guiTop + 32 - move + vanish, 0*40, 3*34 + vanish, 40, 34 - vanish); // S
    		//GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	}
    	if(getModule() == CasinoKeeper.MODULE_COLUMNS) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
    		this.drawTexturedModalRect(guiLeft + 39 + 0*24, guiTop + 32 - move + vanish, 2*40, 0*34 + vanish, 40, 34 - vanish); // C
    		this.drawTexturedModalRect(guiLeft + 39 + 1*24, guiTop + 32 - move + vanish, 2*40, 2*34 + vanish, 40, 34 - vanish); // O
    		this.drawTexturedModalRect(guiLeft + 39 + 2*24, guiTop + 32 - move + vanish, 5*40, 1*34 + vanish, 40, 34 - vanish); // L
    		this.drawTexturedModalRect(guiLeft + 39 + 3*24, guiTop + 32 - move + vanish, 2*40, 3*34 + vanish, 40, 34 - vanish); // U
    		this.drawTexturedModalRect(guiLeft + 39 + 4*24, guiTop + 32 - move + vanish, 0*40, 2*34 + vanish, 40, 34 - vanish); // M
    		this.drawTexturedModalRect(guiLeft + 50 + 5*24, guiTop + 32 - move + vanish, 1*40, 2*34 + vanish, 40, 34 - vanish); // N
    		this.drawTexturedModalRect(guiLeft + 50 + 6*24, guiTop + 32 - move + vanish, 0*40, 3*34 + vanish, 40, 34 - vanish); // S
    	}
    	if(getModule() == CasinoKeeper.MODULE_MEANMINOS) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
    		this.drawTexturedModalRect(guiLeft + 12 + 0*24, guiTop + 32 - move + vanish, 0*40, 2*34 + vanish, 40, 34 - vanish); // M
    		this.drawTexturedModalRect(guiLeft + 23 + 1*24, guiTop + 32 - move + vanish, 4*40, 0*34 + vanish, 40, 34 - vanish); // E
    		this.drawTexturedModalRect(guiLeft + 23 + 2*24, guiTop + 32 - move + vanish, 0*40, 0*34 + vanish, 40, 34 - vanish); // A
    		this.drawTexturedModalRect(guiLeft + 23 + 3*24, guiTop + 32 - move + vanish, 1*40, 2*34 + vanish, 40, 34 - vanish); // N
    		this.drawTexturedModalRect(guiLeft + 23 + 4*24, guiTop + 32 - move + vanish, 0*40, 2*34 + vanish, 40, 34 - vanish); // M
    		this.drawTexturedModalRect(guiLeft + 34 + 5*24, guiTop + 32 - move + vanish, 2*40, 1*34 + vanish, 40, 34 - vanish); // I
    		this.drawTexturedModalRect(guiLeft + 34 + 6*24, guiTop + 32 - move + vanish, 1*40, 2*34 + vanish, 40, 34 - vanish); // N
    		this.drawTexturedModalRect(guiLeft + 34 + 7*24, guiTop + 32 - move + vanish, 2*40, 2*34 + vanish, 40, 34 - vanish); // O
    		this.drawTexturedModalRect(guiLeft + 34 + 8*24, guiTop + 32 - move + vanish, 0*40, 3*34 + vanish, 40, 34 - vanish); // S
    	}
    	if(getModule() == CasinoKeeper.MODULE_SNAKE) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
    		this.drawTexturedModalRect(guiLeft + 68 + 0*24, guiTop + 32 - move + vanish, 0*40, 3*34 + vanish, 40, 34 - vanish); // S
    		this.drawTexturedModalRect(guiLeft + 68 + 1*24, guiTop + 32 - move + vanish, 1*40, 2*34 + vanish, 40, 34 - vanish); // N
    		this.drawTexturedModalRect(guiLeft + 68 + 2*24, guiTop + 32 - move + vanish, 0*40, 0*34 + vanish, 40, 34 - vanish); // A
    		this.drawTexturedModalRect(guiLeft + 68 + 3*24, guiTop + 32 - move + vanish, 4*40, 1*34 + vanish, 40, 34 - vanish); // K
    		this.drawTexturedModalRect(guiLeft + 68 + 4*24, guiTop + 32 - move + vanish, 4*40, 0*34 + vanish, 40, 34 - vanish); // E
    	}
    	if(getModule() == CasinoKeeper.MODULE_SOKOBAN) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
    		this.drawTexturedModalRect(guiLeft + 44 + 0*24, guiTop + 32 - move + vanish, 0*40, 3*34 + vanish, 40, 34 - vanish); // S
    		this.drawTexturedModalRect(guiLeft + 44 + 1*24, guiTop + 32 - move + vanish, 2*40, 2*34 + vanish, 40, 34 - vanish); // O
    		this.drawTexturedModalRect(guiLeft + 44 + 2*24, guiTop + 32 - move + vanish, 4*40, 1*34 + vanish, 40, 34 - vanish); // K
    		this.drawTexturedModalRect(guiLeft + 44 + 3*24, guiTop + 32 - move + vanish, 2*40, 2*34 + vanish, 40, 34 - vanish); // O
    		this.drawTexturedModalRect(guiLeft + 44 + 4*24, guiTop + 32 - move + vanish, 1*40, 0*34 + vanish, 40, 34 - vanish); // B
    		this.drawTexturedModalRect(guiLeft + 44 + 5*24, guiTop + 32 - move + vanish, 0*40, 0*34 + vanish, 40, 34 - vanish); // A
    		this.drawTexturedModalRect(guiLeft + 44 + 6*24, guiTop + 32 - move + vanish, 1*40, 2*34 + vanish, 40, 34 - vanish); // N
    	}
    	if(getModule() == CasinoKeeper.MODULE_2048) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
    		this.drawTexturedModalRect(guiLeft + 80 + 0*24, guiTop + 32 - move + vanish, 5*40, 4*34 + vanish, 40, 34 - vanish); // 2
    		this.drawTexturedModalRect(guiLeft + 80 + 1*24, guiTop + 32 - move + vanish, 1*40, 6*34 + vanish, 40, 34 - vanish); // 0
    		this.drawTexturedModalRect(guiLeft + 80 + 2*24, guiTop + 32 - move + vanish, 1*40, 5*34 + vanish, 40, 34 - vanish); // 4
    		this.drawTexturedModalRect(guiLeft + 80 + 3*24, guiTop + 32 - move + vanish, 5*40, 5*34 + vanish, 40, 34 - vanish); // 8
    	}
    	
    	if(getModule() == CasinoKeeper.MODULE_BLACKJACK) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 1*32, 0*32, 32, 32); // B
    		this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 7*32, 0*32, 32, 32); // L
    		this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 2*32, 1*32, 32, 32); // K
    		
    		this.drawTexturedModalRect(guiLeft + 64 + 0*32, guiTop + 56, 3*32, 1*32, 32, 32); // J
    		this.drawTexturedModalRect(guiLeft + 64 + 1*32, guiTop + 56, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft + 64 + 2*32, guiTop + 56, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft + 64 + 3*32, guiTop + 56, 2*32, 1*32, 32, 32); // K
    	}
    	if(getModule() == CasinoKeeper.MODULE_BACCARAT) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft +  0 + 0*32, guiTop + 24, 1*32, 0*32, 32, 32); // B
    		this.drawTexturedModalRect(guiLeft +  0 + 1*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft +  0 + 2*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft +  0 + 3*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft +  0 + 4*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft +  0 + 5*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft +  0 + 6*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft +  0 + 7*32, guiTop + 24, 3*32, 2*32, 32, 32); // T
    	}
    	if(getModule() == CasinoKeeper.MODULE_VIDEOPOKER) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 5*32, 2*32, 32, 32); // V
    		this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 3*32, 0*32, 32, 32); // D
    		this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
    		
    		this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 56, 7*32, 1*32, 32, 32); // P
    		this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 56, 6*32, 1*32, 32, 32); // O
    		this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 56, 2*32, 1*32, 32, 32); // K
    		this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 56, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 56, 1*32, 2*32, 32, 32); // R
    	}
    	if(getModule() == CasinoKeeper.MODULE_ACEYDEUCEY) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 64 + 0*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft + 64 + 1*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft + 64 + 2*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 64 + 3*32, guiTop + 24, 0*32, 3*32, 32, 32); // Y
    		
    		this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 56, 3*32, 0*32, 32, 32); // D
    		this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 56, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 56, 4*32, 2*32, 32, 32); // U
    		this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 56, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 56, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 56, 0*32, 3*32, 32, 32); // Y
    	}
    	if(getModule() == CasinoKeeper.MODULE_ROUGEETNOIR) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
    		this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 4*32, 2*32, 32, 32); // U
    		this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 0*32, 1*32, 32, 32); // G
    		this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		
    		this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 56, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 56, 3*32, 2*32, 32, 32); // T
    		this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 56, 5*32, 1*32, 32, 32); // N
    		this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 56, 6*32, 1*32, 32, 32); // O
    		this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 56, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 56, 1*32, 2*32, 32, 32); // R
    	}
    	if(getModule() == CasinoKeeper.MODULE_CRAPS) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 7*32, 1*32, 32, 32); // P
    		this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
    	}
    	if(getModule() == CasinoKeeper.MODULE_SICBO) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
    		this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 1*32, 0*32, 32, 32); // B
    		this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
    	}
    	if(getModule() == CasinoKeeper.MODULE_ROULETTE) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft +  0 + 0*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft +  0 + 1*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
    		this.drawTexturedModalRect(guiLeft +  0 + 2*32, guiTop + 24, 4*32, 2*32, 32, 32); // U
    		this.drawTexturedModalRect(guiLeft +  0 + 3*32, guiTop + 24, 7*32, 0*32, 32, 32); // L
    		this.drawTexturedModalRect(guiLeft +  0 + 4*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft +  0 + 5*32, guiTop + 24, 3*32, 2*32, 32, 32); // T
    		this.drawTexturedModalRect(guiLeft +  0 + 6*32, guiTop + 24, 3*32, 2*32, 32, 32); // T
    		this.drawTexturedModalRect(guiLeft +  0 + 7*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    	}
    	if(getModule() == CasinoKeeper.MODULE_PYRAMID) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 16 + 0*32, guiTop + 24, 7*32, 1*32, 32, 32); // P
    		this.drawTexturedModalRect(guiLeft + 16 + 1*32, guiTop + 24, 0*32, 3*32, 32, 32); // Y
    		this.drawTexturedModalRect(guiLeft + 16 + 2*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft + 16 + 3*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft + 16 + 4*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
    		this.drawTexturedModalRect(guiLeft + 16 + 5*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft + 16 + 6*32, guiTop + 24, 3*32, 0*32, 32, 32); // D
    	}
    	if(getModule() == CasinoKeeper.MODULE_TRIPEAK) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 16 + 0*32, guiTop + 24, 3*32, 2*32, 32, 32); // T
    		this.drawTexturedModalRect(guiLeft + 16 + 1*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft + 16 + 2*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft + 16 + 3*32, guiTop + 24, 7*32, 1*32, 32, 32); // P
    		this.drawTexturedModalRect(guiLeft + 16 + 4*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 16 + 5*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft + 16 + 6*32, guiTop + 24, 2*32, 1*32, 32, 32); // K
    	}
    	if(getModule() == CasinoKeeper.MODULE_FREECELL) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft +  0 + 0*32, guiTop + 24, 5*32, 0*32, 32, 32); // F
    		this.drawTexturedModalRect(guiLeft +  0 + 1*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft +  0 + 2*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft +  0 + 3*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft +  0 + 4*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
    		this.drawTexturedModalRect(guiLeft +  0 + 5*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft +  0 + 6*32, guiTop + 24, 7*32, 0*32, 32, 32); // L
    		this.drawTexturedModalRect(guiLeft +  0 + 7*32, guiTop + 24, 7*32, 0*32, 32, 32); // L
    	}
    	if(getModule() == CasinoKeeper.MODULE_KLONDIKE) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft +  0 + 0*32, guiTop + 24, 2*32, 1*32, 32, 32); // K
    		this.drawTexturedModalRect(guiLeft +  0 + 1*32, guiTop + 24, 7*32, 0*32, 32, 32); // L
    		this.drawTexturedModalRect(guiLeft +  0 + 2*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
    		this.drawTexturedModalRect(guiLeft +  0 + 3*32, guiTop + 24, 5*32, 1*32, 32, 32); // N
    		this.drawTexturedModalRect(guiLeft +  0 + 4*32, guiTop + 24, 3*32, 0*32, 32, 32); // D
    		this.drawTexturedModalRect(guiLeft +  0 + 5*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft +  0 + 6*32, guiTop + 24, 2*32, 1*32, 32, 32); // K
    		this.drawTexturedModalRect(guiLeft +  0 + 7*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    	}
    	if(getModule() == CasinoKeeper.MODULE_SPIDER) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
    		this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 24, 7*32, 1*32, 32, 32); // P
    		this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 24, 3*32, 0*32, 32, 32); // D
    		this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    	}
    	if(getModule() == CasinoKeeper.MODULE_MEMORY) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
    		this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
    		this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
    		this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 24, 0*32, 3*32, 32, 32); // Y
    	}
    	if(getModule() == CasinoKeeper.MODULE_MYSTICSQUARE) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
    		this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 24, 0*32, 3*32, 32, 32); // Y
    		this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
    		this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 24, 3*32, 2*32, 32, 32); // T
    		this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
    		
    		this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 56, 2*32, 2*32, 32, 32); // S
    		this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 56, 0*32, 2*32, 32, 32); // Q
    		this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 56, 4*32, 2*32, 32, 32); // U
    		this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 56, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 56, 1*32, 2*32, 32, 32); // R
    		this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 56, 4*32, 0*32, 32, 32); // E
    	}
    	if(getModule() == CasinoKeeper.MODULE_SUDOKU) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
    		this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 24, 4*32, 2*32, 32, 32); // U
    		this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 24, 3*32, 0*32, 32, 32); // D
    		this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
    		this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 24, 2*32, 1*32, 32, 32); // K
    		this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 24, 4*32, 2*32, 32, 32); // U
    	}
    	if(getModule() == CasinoKeeper.MODULE_HALMA) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 1*32, 1*32, 32, 32); // H
    		this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    		this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 7*32, 0*32, 32, 32); // L
    		this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
    		this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
    	}
    	if(getModule() == CasinoKeeper.MODULE_MINESWEEPER) {
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
    		this.drawTexturedModalRect(guiLeft + 64 + 0*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
    		this.drawTexturedModalRect(guiLeft + 64 + 1*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
    		this.drawTexturedModalRect(guiLeft + 64 + 2*32, guiTop + 24, 5*32, 1*32, 32, 32); // N
    		this.drawTexturedModalRect(guiLeft + 64 + 3*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
    		
    		this.drawTexturedModalRect(guiLeft + 16 + 0*32, guiTop + 56, 2*32, 2*32, 32, 32); // S
    		this.drawTexturedModalRect(guiLeft + 16 + 1*32, guiTop + 56, 6*32, 2*32, 32, 32); // W
    		this.drawTexturedModalRect(guiLeft + 16 + 2*32, guiTop + 56, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 16 + 3*32, guiTop + 56, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 16 + 4*32, guiTop + 56, 7*32, 1*32, 32, 32); // P
    		this.drawTexturedModalRect(guiLeft + 16 + 5*32, guiTop + 56, 4*32, 0*32, 32, 32); // E
    		this.drawTexturedModalRect(guiLeft + 16 + 6*32, guiTop + 56, 1*32, 2*32, 32, 32); // R
    	}
    }
    
    public boolean hasHighscore() {
    	return tc.board.hasHighscore;
    }
    
    public String getScoreName(int i) {
    	return tc.board.scoreName[i];
    }
    
    public int getScorePoints(int i) {
    	return tc.board.scorePoints[i];
    }
    
    public int getScoreLast() {
    	return tc.board.scoreLast;
    }
    
}
