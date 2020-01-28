package mod.casinocraft.gui;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.network.*;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Card;
import mod.shared.util.Inventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public class GuiCasino extends GuiContainer {

    /** The player inventory bound to this GUI. */
    private   final InventoryPlayer PLAYER;
    /** The TileEntity bound to this GUI. */
    protected final TileEntityBoard BOARD;
    /** The Game Logic bound to the TileEntity */
    protected final LogicBase LOGIC;

    /** Determines the Background of the game. */
    protected int tableID; // 0 - Arcade, 1 - Table Normal, 2 - Table Wide
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

    protected final int KEY_UP = 265;
    protected final int KEY_DOWN = 264;
    protected final int KEY_LEFT = 263;
    protected final int KEY_RIGHT = 262;
    protected final int KEY_ENTER = 257;
    protected final int KEY_SPACE = 32;
    protected final int KEY_ESCAPE = 256;
    protected final int KEY_M = 77;
    protected final int KEY_N = 78;
    protected final int KEY_1 = 49;
    protected final int KEY_2 = 50;
    protected final int KEY_3 = 51;
    protected final int KEY_4 = 52;
    protected final int KEY_5 = 53;
    protected final int KEY_6 = 54;
    protected final int KEY_7 = 55;
    protected final int KEY_8 = 56;
    protected final int KEY_9 = 57;
    protected final int KEY_0 = 58;


    //--------------------CONSTRUCTOR--------------------

    /** Basic Constructor **/
    public GuiCasino(InventoryPlayer playerInv, IInventory casinoInv, LogicBase logicBase, int tableID){
        super(new ContainerCasino(playerInv, casinoInv));
        this.PLAYER = playerInv;
        this.BOARD = (TileEntityBoard) casinoInv;
        this.LOGIC = logicBase;
        this.xSize = 256;
        this.ySize = 256;
        this.tableID = tableID;
        BOARD.setupHighscore(BOARD.getModule());
    }



    //--------------------BASIC--------------------

    /** Overwritten keyPressed function (only used for rerouting keyCode) */
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        keyTyped(p_keyPressed_1_);
        if (p_keyPressed_1_ == 256 && this.allowCloseWithEscape()) {
            this.close();
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }

    /** Fired when a key is pressed */
    public boolean keyTyped(int keyCode){
        keyTyped2(keyCode);

        // Collect Token and start game (Arcade Version) / FROM: Start Screen
        if(BOARD.turnstate == 0 && tableID == 0 && keyCode == KEY_ENTER) {
            if(!BOARD.hasToken() || playerToken >= BOARD.getBetLow()) {
                if(BOARD.hasToken()) CollectBet();
                LOGIC.start(2);
                shift = 2;
                BOARD.turnstate = 1;
            }
        } else
            // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
        if(BOARD.turnstate == 7 && tableID == 0 && keyCode == KEY_ENTER) {
            if(BOARD.hasToken()) {
                camera1 = 0;
                camera0 = 0;
                shift = 1;
                intro = 256;
                BOARD.turnstate = 0;
            } else {
                LOGIC.start(2);
                if(BOARD.turnstate == 0) {
                    intro = 256;
                    shift = 1;
                }
            }
        } else
            // Collect Token and start game (Arcade Version) / FROM: GameOver Screen
        if(BOARD.turnstate == 5 && tableID == 0 && keyCode == KEY_ENTER) {
            if(LOGIC.nbtHasHighscore) { // Show Highscore Screen
                BOARD.turnstate = 7;
                shift = 1;
            } else {
                if(BOARD.hasToken()) {
                    camera1 = 0;
                    camera0 = 0;
                    shift = 1;
                    intro = 256;
                    BOARD.turnstate = 0;
                } else {
                    LOGIC.start(2);
                }
            }
        }

        // Close Screen Command
        if (keyCode == KEY_ESCAPE){
            if(BOARD.getWorld().getBlockState(BOARD.getPos()).getBlock() instanceof BlockArcade) {
                BlockArcade block = (BlockArcade) BOARD.getWorld().getBlockState(BOARD.getPos()).getBlock();
                block.setPowerState(BOARD.inventory.get(1).getItem(), BOARD.getPos());
            }
            this.mc.player.closeScreen();
        }
        return false;
    }

    /** Called when the mouse is clicked. */
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0){
            if(BOARD.turnstate == 0) { // Adjust Bet
                if(mouseRect(82-26, 204, 26, 26, mouseX, mouseY)) { if(bet > BOARD.getBetLow() ) bet--; } // BET MINUS
                if(mouseRect(82+92, 204, 26, 26, mouseX, mouseY)) { if(bet < BOARD.getBetHigh()) bet++; } // BET PLUS
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)) {
                    // Start Game
                    if(!BOARD.hasToken()){
                        LOGIC.start(tableID+1);
                    } else {
                        if(playerToken >= bet){
                            CollectBet();
                            playerToken = -1;
                            LOGIC.start(tableID+1);
                        }
                    }
                }
            } else
            if(BOARD.turnstate == 5) { // GameOver Screen
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
                    if(LOGIC.nbtHasHighscore) { // Show Highscore
                        BOARD.turnstate = 7;
                    } else { // Reset Game
                        if(BOARD.hasToken()){
                            LOGIC.start(tableID+1);
                        } else {
                            BOARD.turnstate = 0;
                        }
                    }
                }
            } else
            if(BOARD.turnstate == 7) { // Highscore Screen
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
                    if(BOARD.hasToken()){ // Reset Game
                        LOGIC.start(tableID+1);
                    } else {
                        BOARD.turnstate = 0;
                    }
                }
            } else {
                mouseClicked2(mouseX, mouseY, mouseButton);
            }
        }
        return false;
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){

        // Debug Info (shown if Advanced Tooltips are enabled)
        if(this.mc.gameSettings.advancedItemTooltips){
            this.fontRenderer.drawString("turnstate:     " + BOARD.turnstate,                           tableID == 2 ? 355 : 260, 15, 16777215);
            this.fontRenderer.drawString("difficulty:    " + BOARD.difficulty,                          tableID == 2 ? 355 : 260, 25, 16777215);
            this.fontRenderer.drawString("points:        " + BOARD.scorePoint,                          tableID == 2 ? 355 : 260, 35, 16777215);
            this.fontRenderer.drawString("level:         " + BOARD.scoreLevel,                          tableID == 2 ? 355 : 260, 45, 16777215);
            this.fontRenderer.drawString("lives:         " + BOARD.scoreLives,                          tableID == 2 ? 355 : 260, 55, 16777215);
            this.fontRenderer.drawString("hand:          " + BOARD.hand,                                tableID == 2 ? 355 : 260, 65, 16777215);
            this.fontRenderer.drawString("reward:        " + BOARD.reward,                              tableID == 2 ? 355 : 260, 75, 16777215);
            this.fontRenderer.drawString("selector:      " + BOARD.selector.X + ":" + BOARD.selector.Y, tableID == 2 ? 355 : 260, 85, 16777215);
            this.fontRenderer.drawString("tileentity:    " + BOARD.toString().substring(33),            tableID == 2 ? 355 : 260, 95, 16777215);
            this.fontRenderer.drawString("playertoken:   " + playerToken,                               tableID == 2 ? 355 : 260, 105, 16777215);
            this.fontRenderer.drawString("boardToken:    " + BOARD.getBetStorage(),                     tableID == 2 ? 355 : 260, 115, 16777215);
            this.fontRenderer.drawString("bet low:       " + BOARD.getBetLow(),                         tableID == 2 ? 355 : 260, 125, 16777215);
            this.fontRenderer.drawString("bet high:      " + BOARD.getBetHigh(),                        tableID == 2 ? 355 : 260, 135, 16777215);
            this.fontRenderer.drawString("bet player:    " + bet,                                       tableID == 2 ? 355 : 260, 145, 16777215);
            this.fontRenderer.drawString("is creative:   " + BOARD.isCreative,                          tableID == 2 ? 355 : 260, 155, 16777215);
            this.fontRenderer.drawString("score token:   " + BOARD.getScoreToken(),                     tableID == 2 ? 355 : 260, 165, 16777215);
            this.fontRenderer.drawString("score last:    " + LOGIC.scoreLast,                           tableID == 2 ? 355 : 260, 175, 16777215);
            this.fontRenderer.drawString("has highscore: " + LOGIC.nbtHasHighscore,                        tableID == 2 ? 355 : 260, 185, 16777215);
            this.fontRenderer.drawString("has token:     " + BOARD.hasToken(),                          tableID == 2 ? 355 : 260, 195, 16777215);
            this.fontRenderer.drawString("board:         " + BOARD.toString().substring(33),            tableID == 2 ? 355 : 260, 205, 16777215);
        }

        // Search for tokens in PlayerInventory
        if(playerToken == -1) ValidateBet();

        // Draw Start Screen Information (Card Table)
        if(BOARD.turnstate == 0 && tableID != 0){
            if(BOARD.hasToken() && BOARD.getBetHigh() > 0) {
                if(BOARD.getBetLow() == BOARD.getBetHigh()) {
                    drawFontShadowed("The current bet is:", 30, 100);
                    this.itemRender.renderItemIntoGUI(BOARD.getTokenStack(), 130+12, 96);
                    if(BOARD.getBetLow() > 1) drawFontShadowed("x" + BOARD.getBetLow(), 145+15, 100);
                } else {
                    drawFontShadowed("The bets are:", 30, 100);
                    this.itemRender.renderItemIntoGUI(BOARD.getTokenStack(), 130+12, 96);
                    drawFontShadowed("x" + BOARD.getBetLow() + "  to  x" + BOARD.getBetHigh(), 145+10, 100);
                }

                if(playerToken < BOARD.getBetLow()) {
                    drawFontShadowed("You don't have enough Token to play...", 30, 120);
                } else {
                    drawFontShadowed("Do you wish to play?", 30, 120);
                }
                if(BOARD.getBetHigh() != BOARD.getBetLow()) drawFontShadowed("Your Bet:  " + bet, 30, 140);
            } else {
                //this.fontRenderer.drawString("Free to play", 80, 170, 16777215);
            }

        // Draw Highscore (Card Table)
        } else if(BOARD.turnstate == 7 && tableID != 0){
            for(int i = 0; i < 18; i++) {
                drawFontShadowed(     LOGIC.scoreName[i]  ,  40, 25 + 10*i, LOGIC.scoreLast == i ? grayscale/2 : grayscale);
                drawFontShadowed("" + LOGIC.scorePoints[i], 200, 25 + 10*i, LOGIC.scoreLast == i ? grayscale/2 : grayscale);
            }

        // Draw Start Screen Information (Arcade)
        } else if(BOARD.turnstate == 0 && tableID == 0){
            if(BOARD.hasToken() && BOARD.getBetHigh() > 0) {
                this.fontRenderer.drawString("INSERT ", 90, 180, 16777215);
                this.itemRender.renderItemIntoGUI(new ItemStack(BOARD.getToken()), 126, 176);
                if(BOARD.getBetLow() > 1) this.fontRenderer.drawString("x" + BOARD.getBetLow(), 145, 180, 16777215);
                if(playerToken < BOARD.getBetLow()) {
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

        // Draw Highscore Information (Arcade)
        } else if(BOARD.turnstate == 7 && tableID == 0){

            for(int i = 0; i < 18; i++) {
                this.fontRenderer.drawString(     LOGIC.scoreName[i]  ,  40, 25 + 10*i, LOGIC.scoreLast == i ? 16777215/2 : 16777215);
                this.fontRenderer.drawString("" + LOGIC.scorePoints[i], 200, 25 + 10*i, LOGIC.scoreLast == i ? 16777215/2 : 16777215);
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

            if(BOARD.turnstate == 4){
                BOARD.turnstate = 5;
                gameOver();
                playerToken = -1;
            }
        }
    }

    /** Draws the background layer of this container (behind the items). */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(tableID == 0) { // Arcade Background
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_STARFIELD1);
            this.drawTexturedModalRect(guiLeft, guiTop, 0, shift == 0 ? 0 : camera1, 256, 256);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_STARFIELD0);
            this.drawTexturedModalRect(guiLeft, guiTop, 0, shift == 0 ? 0 : camera0, 256, 256);
        } else { // Card Table Background
            this.mc.getTextureManager().bindTexture(tableID == 0 ? CasinoKeeper.TEXTURE_GROUND_ARCADE : getBackground());
            if(tableID == 2){
                this.drawTexturedModalRect(guiLeft-128+32, guiTop,  0, 0, this.xSize-32, this.ySize); // Background Left
                this.drawTexturedModalRect(guiLeft+128   , guiTop, 32, 0, this.xSize-32, this.ySize); // Background Right
            } else {
                this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
            }
        }

        // Draws Logo from ItemModule
        if(BOARD.turnstate <= 1) {
            drawLogo();
        }

        // Intro Animation (Only on Arcade)
        if(BOARD.turnstate == 1) {
            intro--;
            if(intro == 0) {
                BOARD.turnstate = 2;
            }
        }

        // MiniGame BackgroundDrawer
        if(BOARD.turnstate >= 1 && BOARD.turnstate < 6) drawGuiContainerBackgroundLayer2(partialTicks, mouseX, mouseY);

        // If NOT Ingame
        if((BOARD.turnstate == 5 || BOARD.turnstate == 0 || BOARD.turnstate == 7) && tableID > 0){
            if(BOARD.turnstate == 5 && LOGIC.nbtHasHighscore){
                this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
                drawTexturedModalRect(guiLeft+89, guiTop+206, 0, 22, 78, 22); // Button Highcore
            } else
            if(BOARD.turnstate == 5){
                this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
                drawTexturedModalRect(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(BOARD.turnstate == 7){
                this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
                drawTexturedModalRect(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(!BOARD.hasToken() || playerToken >= BOARD.getBetLow()){
                this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
                drawTexturedModalRect(guiLeft+89, guiTop+206, 78, 22, 78, 22); // Button New Game
            }
            if(BOARD.turnstate == 0 && playerToken >= BOARD.getBetLow()) {
                if(bet > BOARD.getBetLow())  drawTexturedModalRect(guiLeft+82-26+2, guiTop+204+2, 234, 22, 22, 22); // Button Minus
                if(bet < BOARD.getBetHigh()) drawTexturedModalRect(guiLeft+82+92+2, guiTop+204+2, 234, 44, 22, 22); // Button Plus
            }
        }

        // Draws Arcade Border
        if(tableID == 0) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_ARCADE);
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);

            if(BOARD.turnstate != 5) camera1 = (camera1 + shift)   % 256;
            if(BOARD.turnstate != 5) camera0 = (camera0 + shift*2) % 256;
        }

    }

    protected void gameOver(){
        payBet(BOARD.reward);
        if(LOGIC.nbtHasHighscore) {
            BOARD.addScore(PLAYER.player.getName().getString(), BOARD.scorePoint);
            CasinoPacketHandler.sendToServer(new ServerScoreMessage(BOARD.getScoreToken(), LOGIC.scoreName, LOGIC.scorePoints, BOARD.getPos()));
        }
    }



    //--------------------EMPTY--------------------

    protected void keyTyped2(int keyCode){ }
    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){ }
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){ }
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){ }



    //--------------------HELPER--------------------

    /** Draws Colored Card Table Background **/
    private ResourceLocation getBackground(){
        if(BOARD.color == EnumDyeColor.BLACK)      return CasinoKeeper.TEXTURE_GROUND_BLACK;
        if(BOARD.color == EnumDyeColor.BLUE)       return CasinoKeeper.TEXTURE_GROUND_BLUE;
        if(BOARD.color == EnumDyeColor.BROWN)      return CasinoKeeper.TEXTURE_GROUND_BROWN;
        if(BOARD.color == EnumDyeColor.CYAN)       return CasinoKeeper.TEXTURE_GROUND_CYAN;
        if(BOARD.color == EnumDyeColor.GRAY)       return CasinoKeeper.TEXTURE_GROUND_GRAY;
        if(BOARD.color == EnumDyeColor.GREEN)      return CasinoKeeper.TEXTURE_GROUND_GREEN;
        if(BOARD.color == EnumDyeColor.LIGHT_BLUE) return CasinoKeeper.TEXTURE_GROUND_LIGHTBLUE;
        if(BOARD.color == EnumDyeColor.LIME)       return CasinoKeeper.TEXTURE_GROUND_LIME;
        if(BOARD.color == EnumDyeColor.MAGENTA)    return CasinoKeeper.TEXTURE_GROUND_MAGENTA;
        if(BOARD.color == EnumDyeColor.ORANGE)     return CasinoKeeper.TEXTURE_GROUND_ORANGE;
        if(BOARD.color == EnumDyeColor.PINK)       return CasinoKeeper.TEXTURE_GROUND_PINK;
        if(BOARD.color == EnumDyeColor.PURPLE)     return CasinoKeeper.TEXTURE_GROUND_PURPLE;
        if(BOARD.color == EnumDyeColor.RED)        return CasinoKeeper.TEXTURE_GROUND_RED;
        if(BOARD.color == EnumDyeColor.LIGHT_GRAY) return CasinoKeeper.TEXTURE_GROUND_SILVER;
        if(BOARD.color == EnumDyeColor.WHITE)      return CasinoKeeper.TEXTURE_GROUND_WHITE;
        if(BOARD.color == EnumDyeColor.YELLOW)     return CasinoKeeper.TEXTURE_GROUND_YELLOW;

        return CasinoKeeper.TEXTURE_GROUND_GRAY;
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontShadowed(String text, int x, int y){
        drawFontShadowed(text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontShadowed(String text, int x, int y, int color){
        this.fontRenderer.drawString(text,  x+1, y+1, 0);
        this.fontRenderer.drawString(text,  x+0, y+0, color);
    }

    /** Checks if mouse is inside a rectangle **/
    protected boolean mouseRect(int x, int y, int width, int height, double mouseX, double mouseY){
        if(guiLeft + x < mouseX && mouseX < guiLeft + x + width){
            return guiTop + y < mouseY && mouseY < guiTop + y + height;
        }
        return false;
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
        drawTexturedModalRect(guiLeft + posX + card.shiftX, guiTop + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
    }

    /** Draws the Backside of a Card (also used for highlighter) **/
    public void drawCardBack(int posX, int posY, int color){
        if(color <= 6) this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        else           this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
        drawTexturedModalRect(guiLeft + posX, guiTop + posY, (color%7) * 32, 4 * 48, 32, 48);
    }

    /** Scans the PlayerInventory for Tokens **/
    protected void ValidateBet(){
        if(bet < BOARD.getBetLow ()) bet = BOARD.getBetLow();
        if(bet > BOARD.getBetHigh()) bet = BOARD.getBetHigh();
        if(BOARD.hasToken()){
            Item item = BOARD.getTokenStack().getItem();
            int count = 0;
            for(int i = 0; i < 36; i++){
                if(item == PLAYER.getStackInSlot(i).getItem()) count += PLAYER.getStackInSlot(i).getCount();
            }
            playerToken = count;
        }
    }

    /** Checks if Player can pay another bet (and automatically collects it IF TRUE) **/
    protected boolean AnotherBet(){
        ValidateBet();
        if(playerToken >= bet){
            CollectBet();
            return true;
        }
        return false;
    }

    /** Collects the bet from the Player **/
    protected void CollectBet(){
        if(BOARD.hasToken()){
            Inventory.decreaseInventory(PLAYER, BOARD.getTokenStack(), bet);
            PLAYER.clearMatchingItems(Predicate.isEqual(BOARD.getTokenStack()), bet);
            CasinoPacketHandler.sendToServer(new ServerPlayerMessage(BOARD.getToken(), 0, -bet));
            if(!BOARD.isCreative) {
                BOARD.bet_storage+=bet;
                CasinoPacketHandler.sendToServer(new ServerBlockMessage(BOARD.inventory.get(0), BOARD.inventory.get(1), BOARD.inventory.get(4), BOARD.bet_storage, BOARD.getPos()));
            }
        }
    }

    /** Pays the Reward to the Player **/
    protected void payBet(int multi){
        if(multi <= 0) return;
        if(BOARD.hasToken()){
            if(!BOARD.isCreative) {
                Item item = BOARD.getTokenStack().getItem();
                int count = bet * multi; // multi == 1 -> Return bet
                int count2 = 0;

                if(BOARD.getBetStorage() >= count) {
                    count2 = count;
                } else {
                    count2 = BOARD.getBetStorage();
                }

                BOARD.bet_storage-=count;

                if(BOARD.getBetStorage() <= 0) {
                    BOARD.bet_storage = 0;
                    BOARD.setToken(new ItemStack(Blocks.AIR));
                }
                CasinoPacketHandler.sendToServer(new ServerBlockMessage(BOARD.inventory.get(0), BOARD.inventory.get(1), BOARD.inventory.get(4), BOARD.bet_storage, BOARD.getPos()));

                PLAYER.addItemStackToInventory(new ItemStack(item, count2));
                CasinoPacketHandler.sendToServer(new ServerPlayerMessage(item, 0, count2));
            } else {
                Item item = BOARD.getTokenStack().getItem();
                int count = bet * multi; // multi == 1 -> Return bet
                PLAYER.addItemStackToInventory(new ItemStack(item, count));
                CasinoPacketHandler.sendToServer(new ServerPlayerMessage(item, 0, count));
            }
        }
    }

    /** Draws Logo from ItemModule **/
    private void drawLogo() {
        int move = 256 - intro; // Move logo up
        int vanish = move < 32 ? 0 : move-32 > 34 ? 34 : move - 32;
        if(move >= 32) {
            int i = 0;
        }

        if(BOARD.getModule() == CasinoKeeper.MODULE_TETRIS) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_COLUMNS) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
            this.drawTexturedModalRect(guiLeft + 39 + 0*24, guiTop + 32 - move + vanish, 2*40, 0*34 + vanish, 40, 34 - vanish); // C
            this.drawTexturedModalRect(guiLeft + 39 + 1*24, guiTop + 32 - move + vanish, 2*40, 2*34 + vanish, 40, 34 - vanish); // O
            this.drawTexturedModalRect(guiLeft + 39 + 2*24, guiTop + 32 - move + vanish, 5*40, 1*34 + vanish, 40, 34 - vanish); // L
            this.drawTexturedModalRect(guiLeft + 39 + 3*24, guiTop + 32 - move + vanish, 2*40, 3*34 + vanish, 40, 34 - vanish); // U
            this.drawTexturedModalRect(guiLeft + 39 + 4*24, guiTop + 32 - move + vanish, 0*40, 2*34 + vanish, 40, 34 - vanish); // M
            this.drawTexturedModalRect(guiLeft + 50 + 5*24, guiTop + 32 - move + vanish, 1*40, 2*34 + vanish, 40, 34 - vanish); // N
            this.drawTexturedModalRect(guiLeft + 50 + 6*24, guiTop + 32 - move + vanish, 0*40, 3*34 + vanish, 40, 34 - vanish); // S
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_MEANMINOS) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_SNAKE) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
            this.drawTexturedModalRect(guiLeft + 68 + 0*24, guiTop + 32 - move + vanish, 0*40, 3*34 + vanish, 40, 34 - vanish); // S
            this.drawTexturedModalRect(guiLeft + 68 + 1*24, guiTop + 32 - move + vanish, 1*40, 2*34 + vanish, 40, 34 - vanish); // N
            this.drawTexturedModalRect(guiLeft + 68 + 2*24, guiTop + 32 - move + vanish, 0*40, 0*34 + vanish, 40, 34 - vanish); // A
            this.drawTexturedModalRect(guiLeft + 68 + 3*24, guiTop + 32 - move + vanish, 4*40, 1*34 + vanish, 40, 34 - vanish); // K
            this.drawTexturedModalRect(guiLeft + 68 + 4*24, guiTop + 32 - move + vanish, 4*40, 0*34 + vanish, 40, 34 - vanish); // E
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_SOKOBAN) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
            this.drawTexturedModalRect(guiLeft + 44 + 0*24, guiTop + 32 - move + vanish, 0*40, 3*34 + vanish, 40, 34 - vanish); // S
            this.drawTexturedModalRect(guiLeft + 44 + 1*24, guiTop + 32 - move + vanish, 2*40, 2*34 + vanish, 40, 34 - vanish); // O
            this.drawTexturedModalRect(guiLeft + 44 + 2*24, guiTop + 32 - move + vanish, 4*40, 1*34 + vanish, 40, 34 - vanish); // K
            this.drawTexturedModalRect(guiLeft + 44 + 3*24, guiTop + 32 - move + vanish, 2*40, 2*34 + vanish, 40, 34 - vanish); // O
            this.drawTexturedModalRect(guiLeft + 44 + 4*24, guiTop + 32 - move + vanish, 1*40, 0*34 + vanish, 40, 34 - vanish); // B
            this.drawTexturedModalRect(guiLeft + 44 + 5*24, guiTop + 32 - move + vanish, 0*40, 0*34 + vanish, 40, 34 - vanish); // A
            this.drawTexturedModalRect(guiLeft + 44 + 6*24, guiTop + 32 - move + vanish, 1*40, 2*34 + vanish, 40, 34 - vanish); // N
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_2048) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
            this.drawTexturedModalRect(guiLeft + 80 + 0*24, guiTop + 32 - move + vanish, 5*40, 4*34 + vanish, 40, 34 - vanish); // 2
            this.drawTexturedModalRect(guiLeft + 80 + 1*24, guiTop + 32 - move + vanish, 1*40, 6*34 + vanish, 40, 34 - vanish); // 0
            this.drawTexturedModalRect(guiLeft + 80 + 2*24, guiTop + 32 - move + vanish, 1*40, 5*34 + vanish, 40, 34 - vanish); // 4
            this.drawTexturedModalRect(guiLeft + 80 + 3*24, guiTop + 32 - move + vanish, 5*40, 5*34 + vanish, 40, 34 - vanish); // 8
        }

        if(BOARD.getModule() == CasinoKeeper.MODULE_BLACKJACK) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_BACCARAT) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_VIDEOPOKER) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_ACEYDEUCEY) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_ROUGEETNOIR) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_CRAPS) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
            this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
            this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
            this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
            this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 7*32, 1*32, 32, 32); // P
            this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_SICBO) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
            this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
            this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
            this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 2*32, 0*32, 32, 32); // C
            this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 1*32, 0*32, 32, 32); // B
            this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_ROULETTE) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_PYRAMID) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
            this.drawTexturedModalRect(guiLeft + 16 + 0*32, guiTop + 24, 7*32, 1*32, 32, 32); // P
            this.drawTexturedModalRect(guiLeft + 16 + 1*32, guiTop + 24, 0*32, 3*32, 32, 32); // Y
            this.drawTexturedModalRect(guiLeft + 16 + 2*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
            this.drawTexturedModalRect(guiLeft + 16 + 3*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
            this.drawTexturedModalRect(guiLeft + 16 + 4*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
            this.drawTexturedModalRect(guiLeft + 16 + 5*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
            this.drawTexturedModalRect(guiLeft + 16 + 6*32, guiTop + 24, 3*32, 0*32, 32, 32); // D
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_TRIPEAK) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
            this.drawTexturedModalRect(guiLeft + 16 + 0*32, guiTop + 24, 3*32, 2*32, 32, 32); // T
            this.drawTexturedModalRect(guiLeft + 16 + 1*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
            this.drawTexturedModalRect(guiLeft + 16 + 2*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
            this.drawTexturedModalRect(guiLeft + 16 + 3*32, guiTop + 24, 7*32, 1*32, 32, 32); // P
            this.drawTexturedModalRect(guiLeft + 16 + 4*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
            this.drawTexturedModalRect(guiLeft + 16 + 5*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
            this.drawTexturedModalRect(guiLeft + 16 + 6*32, guiTop + 24, 2*32, 1*32, 32, 32); // K
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_FREECELL) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_KLONDIKE) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_SPIDER) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
            this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
            this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 24, 7*32, 1*32, 32, 32); // P
            this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 24, 6*32, 0*32, 32, 32); // I
            this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 24, 3*32, 0*32, 32, 32); // D
            this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
            this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_MEMORY) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
            this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
            this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 24, 4*32, 0*32, 32, 32); // E
            this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
            this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
            this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 24, 1*32, 2*32, 32, 32); // R
            this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 24, 0*32, 3*32, 32, 32); // Y
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_MYSTICSQUARE) {
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
        if(BOARD.getModule() == CasinoKeeper.MODULE_SUDOKU) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
            this.drawTexturedModalRect(guiLeft + 32 + 0*32, guiTop + 24, 2*32, 2*32, 32, 32); // S
            this.drawTexturedModalRect(guiLeft + 32 + 1*32, guiTop + 24, 4*32, 2*32, 32, 32); // U
            this.drawTexturedModalRect(guiLeft + 32 + 2*32, guiTop + 24, 3*32, 0*32, 32, 32); // D
            this.drawTexturedModalRect(guiLeft + 32 + 3*32, guiTop + 24, 6*32, 1*32, 32, 32); // O
            this.drawTexturedModalRect(guiLeft + 32 + 4*32, guiTop + 24, 2*32, 1*32, 32, 32); // K
            this.drawTexturedModalRect(guiLeft + 32 + 5*32, guiTop + 24, 4*32, 2*32, 32, 32); // U
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_HALMA) {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
            this.drawTexturedModalRect(guiLeft + 48 + 0*32, guiTop + 24, 1*32, 1*32, 32, 32); // H
            this.drawTexturedModalRect(guiLeft + 48 + 1*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
            this.drawTexturedModalRect(guiLeft + 48 + 2*32, guiTop + 24, 7*32, 0*32, 32, 32); // L
            this.drawTexturedModalRect(guiLeft + 48 + 3*32, guiTop + 24, 4*32, 1*32, 32, 32); // M
            this.drawTexturedModalRect(guiLeft + 48 + 4*32, guiTop + 24, 0*32, 0*32, 32, 32); // A
        }
        if(BOARD.getModule() == CasinoKeeper.MODULE_MINESWEEPER) {
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

}
