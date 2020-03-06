package mod.casinocraft.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.network.*;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.util.Card;
import mod.shared.container.ContainerBase;
import mod.shared.util.Inventory;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;
import java.util.function.Predicate;

public abstract class GuiCasino extends ContainerScreen<ContainerCasino> {

    /** The player inventory bound to this GUI. */
    private   final PlayerInventory PLAYER;
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

    public GuiCasino(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        PLAYER = player;
        CONTAINER = container;
        this.xSize = 256;
        this.ySize = 256;
        //BOARD.setupHighscore(BOARD.getModule());
        this.tableID = CONTAINER.tableID;
        //container.addListener(new IContainerListener() {
        //    @Override
        //    public void sendAllContents(Container container, NonNullList<ItemStack> nonNullList) {
        //        GuiCasino.this.CONTAINER.updateAllValues(container, nonNullList);
        //    }
//
        //    @Override
        //    public void sendSlotContents(Container container, int i, ItemStack itemStack) {
        //        GuiCasino.this.CONTAINER.updateSlotContents(container, i, itemStack);
        //    }
//
        //    @Override
        //    public void sendWindowProperty(Container container, int varToUpdate, int newValue) {
        //        GuiCasino.this.CONTAINER.updateSingleValue(container, varToUpdate, newValue);
        //    }
        //});
    }



    //--------------------BASIC--------------------

    private LogicBase logic(){
        return CONTAINER.logic();
    }

    /** Overwritten keyPressed function (only used for rerouting keyCode) */
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        keyTyped(p_keyPressed_1_);
        if (p_keyPressed_1_ == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        } else if (p_keyPressed_1_ == 258) {
            boolean flag = !hasShiftDown();
            if (!this.changeFocus(flag)) {
                this.changeFocus(flag);
            }

            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }

    /** Fired when a key is pressed */
    public boolean keyTyped(int keyCode){
        keyTyped2(keyCode);

        // Collect Token and start game (Arcade Version) / FROM: Start Screen
        if(CONTAINER.turnstate() == 0 && tableID == 0 && keyCode == KEY_ENTER) {
            if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBetLow()) {
                if(CONTAINER.hasToken()) CollectBet();
                start();
                shift = 2;
            }
        } else {
            // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
            if(CONTAINER.turnstate() == 7 && tableID == 0 && keyCode == KEY_ENTER) {
                if(CONTAINER.hasToken()) {
                    camera1 = 0;
                    camera0 = 0;
                    shift = 1;
                    intro = 256;
                    logic().turnstate = 0;
                } else {
                    start();
                    if(CONTAINER.turnstate() == 0) {
                        intro = 256;
                        shift = 1;
                    }
                }
            } else {
                // Collect Token and start game (Arcade Version) / FROM: GameOver Screen
                if(CONTAINER.turnstate() == 5 && tableID == 0 && keyCode == KEY_ENTER) {
                    if(logic().hasHighscore()) { // Show Highscore Screen
                        logic().turnstate = 7;
                        shift = 1;
                    } else {
                        if(CONTAINER.hasToken()) {
                            camera1 = 0;
                            camera0 = 0;
                            shift = 1;
                            intro = 256;
                            reset();
                        } else {
                            start();
                        }
                    }
                }
            }
        }

        // Toggle Pause Mode
        if(CONTAINER.turnstate() == 2 && tableID == 0 && keyCode == KEY_SPACE){
            CasinoPacketHandler.sendToServer(new ServerPauseMessage(CONTAINER.getPos()));
        }

        // Close Screen Command
        if (keyCode == KEY_ESCAPE){
            //if(CONTAINER..world.getBlockState(CONTAINER.getPos()).getBlock() instanceof BlockArcade) {
            //    BlockArcade block = (BlockArcade) CONTAINER.getWorld().getBlockState(CONTAINER.getPos()).getBlock();
            //    block.setPowerState(CONTAINER.inventory.get(1).getItem(), CONTAINER.getPos());
            //}
            this.minecraft.player.closeScreen();
        }
        return false;
    }

    /** Called when the mouse is clicked. */
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0){
            if(CONTAINER.turnstate() == 0) { // Adjust Bet
                if(mouseRect(82-26, 204, 26, 26, mouseX, mouseY)) { if(bet > CONTAINER.getBetLow() ) bet--; } // BET MINUS
                if(mouseRect(82+92, 204, 26, 26, mouseX, mouseY)) { if(bet < CONTAINER.getBetHigh()) bet++; } // BET PLUS
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)) {
                    // Start Game
                    if(!CONTAINER.hasToken()){
                        start();
                    } else {
                        if(playerToken >= bet){
                            CollectBet();
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
                        if(CONTAINER.hasToken()){
                            start();
                        } else {
                            reset();
                        }
                    }
                }
            } else
            if(CONTAINER.turnstate() == 7) { // Highscore Screen
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
                    if(CONTAINER.hasToken()){ // Reset Game
                        start();
                    } else {
                        reset();
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
        if(this.minecraft.gameSettings.advancedItemTooltips){
            this.font.drawString("turnstate:     " + logic().turnstate,                           tableID == 2 ? 355 : 260, 15, 16777215);
            this.font.drawString("table:         " + logic().table,                                 tableID == 2 ? 355 : 260, 25, 16777215);
            //this.font.drawString("points:        " + logic().scorePoint,                          tableID == 2 ? 355 : 260, 35, 16777215);
            this.font.drawString("points:        " +
                            ((ContainerCasino)this.container).getBetHigh(),
                    tableID == 2 ? 355 : 260, 35, 16777215);
            this.font.drawString("level:         " + logic().scoreLevel,                          tableID == 2 ? 355 : 260, 45, 16777215);
            this.font.drawString("lives:         " + logic().scoreLives,                          tableID == 2 ? 355 : 260, 55, 16777215);
            this.font.drawString("hand:          " + logic().hand,                                tableID == 2 ? 355 : 260, 65, 16777215);
            this.font.drawString("reward:        " + logic().reward,                              tableID == 2 ? 355 : 260, 75, 16777215);
            this.font.drawString("selector:      " + logic().selector.X + ":" + logic().selector.Y, tableID == 2 ? 355 : 260, 85, 16777215);
            this.font.drawString("tileentity:    " + logic().toString().substring(33),            tableID == 2 ? 355 : 260, 95, 16777215);
            this.font.drawString("playertoken:   " + playerToken,                               tableID == 2 ? 355 : 260, 105, 16777215);
            this.font.drawString("boardToken:    " + CONTAINER.getBetStorage(),                     tableID == 2 ? 355 : 260, 115, 16777215);
            this.font.drawString("bet low:       " + CONTAINER.getBetLow(),                         tableID == 2 ? 355 : 260, 125, 16777215);
            this.font.drawString("bet high:      " + CONTAINER.getBetHigh(),                        tableID == 2 ? 355 : 260, 135, 16777215);
            this.font.drawString("bet player:    " + bet,                                       tableID == 2 ? 355 : 260, 145, 16777215);
            this.font.drawString("is creative:   " + CONTAINER.isCreative(),                          tableID == 2 ? 355 : 260, 155, 16777215);
            //this.font.drawString("score token:   " + CONTAINER.getScoreToken(),                     tableID == 2 ? 355 : 260, 165, 16777215);
            this.font.drawString("score last:    " + logic().scoreLast,                           tableID == 2 ? 355 : 260, 175, 16777215);
            this.font.drawString("has highscore: " + logic().hasHighscore(),                        tableID == 2 ? 355 : 260, 185, 16777215);
            this.font.drawString("has token:     " + CONTAINER.hasToken(),                          tableID == 2 ? 355 : 260, 195, 16777215);
            this.font.drawString("board:         " + CONTAINER.toString().substring(33),            tableID == 2 ? 355 : 260, 205, 16777215);
        }

        if(logic() instanceof LogicDummy) return;

        // Search for tokens in PlayerInventory
        if(playerToken == -1) ValidateBet();

        // Draw Start Screen Information (Card Table)
        if(CONTAINER.turnstate() == 0 && tableID != 0){
            if(CONTAINER.hasToken() && CONTAINER.getBetHigh() > 0) {
                if(CONTAINER.getBetLow() == CONTAINER.getBetHigh()) {
                    drawFontShadowed("The current bet is:", 30, 100);
                    this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 130+12, 96);
                    if(CONTAINER.getBetLow() > 1) drawFontShadowed("x" + CONTAINER.getBetLow(), 145+15, 100);
                } else {
                    drawFontShadowed("The bets are:", 30, 100);
                    this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 130+12, 96);
                    drawFontShadowed("x" + CONTAINER.getBetLow() + "  to  x" + CONTAINER.getBetHigh(), 145+10, 100);
                }

                if(playerToken < CONTAINER.getBetLow()) {
                    drawFontShadowed("You don't have enough Token to play...", 30, 120);
                } else {
                    drawFontShadowed("Do you wish to play?", 30, 120);
                }
                if(CONTAINER.getBetHigh() != CONTAINER.getBetLow()) drawFontShadowed("Your Bet:  " + bet, 30, 140);
            } else {
                //this.fontRenderer.drawString("Free to play", 80, 170, 16777215);
            }

            // Draw Highscore (Card Table)
        } else if(CONTAINER.turnstate() == 7 && tableID != 0){
            for(int i = 0; i < 18; i++) {
                drawFontShadowed(     logic().scoreName[i]  ,  40, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
                drawFontShadowed("" + logic().scoreHigh[i], 200, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
            }

            // Draw Start Screen Information (Arcade)
        } else if(CONTAINER.turnstate() == 0 && tableID == 0){
            if(CONTAINER.hasToken() && CONTAINER.getBetHigh() > 0) {
                this.font.drawString("INSERT ", 90, 180, 16777215);
                this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 126, 176);
                if(CONTAINER.getBetLow() > 1) this.font.drawString("x" + CONTAINER.getBetLow(), 145, 180, 16777215);
                if(playerToken < CONTAINER.getBetLow()) {
                    this.font.drawString("NOT ENOUGH TOKEN", 80, 210, colour);
                } else {
                    this.font.drawString("Press ENTER", 95, 210, colour);
                }
            } else {
                this.font.drawString("Press ENTER", 95, 210, colour);
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
                this.font.drawString(     logic().scoreName[i]  ,  40, 25 + 10*i, logic().scoreLast == i ? 16777215/2 : 16777215);
                this.font.drawString("" + logic().scoreHigh[i], 200, 25 + 10*i, logic().scoreLast == i ? 16777215/2 : 16777215);
            }

            this.font.drawString("Press ENTER", 95, 220, colour);

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

            if(logic().turnstate == 4){ // ???
                gameOver();
            }
        }
        if(!CONTAINER.getName().matches(logic().getName())){
            this.minecraft.player.closeScreen();
        }
    }

    /** Draws the background layer of this container (behind the items). */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(tableID == 0) { // Arcade Background
            if(logic() instanceof LogicDummy){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_STATIC);
                Random r = new Random();
                for(int y = 0; y < 8; y++){
                    for(int x = 0; x < 8; x++){
                        this.blit(guiLeft + 16 + 28*x, guiTop + 32*y, 28*r.nextInt(8), 32*r.nextInt(8), 28, 32);
                    }
                }
            } else {
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_STARFIELD1);
                this.blit(guiLeft, guiTop, 0, shift == 0 ? 0 : camera1, 256, 256);
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_STARFIELD0);
                this.blit(guiLeft, guiTop, 0, shift == 0 ? 0 : camera0, 256, 256);
            }
        } else if(tableID < 3){ // Card Table Background
            this.minecraft.getTextureManager().bindTexture(tableID == 0 ? CasinoKeeper.TEXTURE_GROUND_ARCADE : getBackground());
            if(tableID == 2){
                this.blit(guiLeft-128+32, guiTop,  0, 0, this.xSize-32, this.ySize); // Background Left
                this.blit(guiLeft+128   , guiTop, 32, 0, this.xSize-32, this.ySize); // Background Right
            } else {
                this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
            }
        } else { // Slot Machine Background
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_ARCADE);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        }

        // Draws Logo from ItemModule
        if(CONTAINER.turnstate() <= 1) {
            drawLogo();
        }

        // Intro Animation (Only on Arcade)
        if(CONTAINER.turnstate() == 1) {
            intro--;
            if(intro == 0) {
                turnstate(2);
            }
        }

        // MiniGame BackgroundDrawer
        if(CONTAINER.turnstate() >= 1 && CONTAINER.turnstate() < 6){
            if(logic().pause) GlStateManager.color4f(0.35F, 0.35F, 0.35F, 1.0F);
            drawGuiContainerBackgroundLayer2(partialTicks, mouseX, mouseY);
            if(logic().pause) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        // If NOT Ingame
        if((CONTAINER.turnstate() == 5 || CONTAINER.turnstate() == 0 || CONTAINER.turnstate() == 7) && tableID > 0 && !(logic() instanceof LogicDummy)){
            if(CONTAINER.turnstate() == 5 && logic().hasHighscore()){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
                blit(guiLeft+89, guiTop+206, 0, 22, 78, 22); // Button Highcore
            } else
            if(CONTAINER.turnstate() == 5){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
                blit(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(CONTAINER.turnstate() == 7){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
                blit(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBetLow()){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
                blit(guiLeft+89, guiTop+206, 78, 22, 78, 22); // Button New Game
            }
            if(CONTAINER.turnstate() == 0 && playerToken >= CONTAINER.getBetLow()) {
                if(bet > CONTAINER.getBetLow())  blit(guiLeft+82-26+2, guiTop+204+2, 234, 22, 22, 22); // Button Minus
                if(bet < CONTAINER.getBetHigh()) blit(guiLeft+82+92+2, guiTop+204+2, 234, 44, 22, 22); // Button Plus
            }
        }

        // Draws Arcade Border
        if(tableID == 0) {
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_ARCADE);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);

            if(CONTAINER.turnstate() != 5) camera1 = (camera1 + shift)   % 256;
            if(CONTAINER.turnstate() != 5) camera0 = (camera0 + shift*2) % 256;
        }

    }

    protected void gameOver(){ // ???
        turnstate(5);
        payBet(logic().reward);
        if(logic().hasHighscore()) {
            logic().addScore(PLAYER.player.getName().getString(), logic().scorePoint);
            CasinoPacketHandler.sendToServer(new ServerScoreMessage(CONTAINER.getScoreToken().getItem(), logic().scoreName, logic().scoreHigh, CONTAINER.getPos()));
        }
        playerToken = -1;
    }



    //--------------------EMPTY--------------------

    protected void keyTyped2(int keyCode){ }
    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){ }
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){ }
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){ }



    //--------------------HELPER--------------------

    /** Draws Colored Card Table Background **/
    private ResourceLocation getBackground(){
        if(CONTAINER.color == DyeColor.BLACK)      return CasinoKeeper.TEXTURE_GROUND_BLACK;
        if(CONTAINER.color == DyeColor.BLUE)       return CasinoKeeper.TEXTURE_GROUND_BLUE;
        if(CONTAINER.color == DyeColor.BROWN)      return CasinoKeeper.TEXTURE_GROUND_BROWN;
        if(CONTAINER.color == DyeColor.CYAN)       return CasinoKeeper.TEXTURE_GROUND_CYAN;
        if(CONTAINER.color == DyeColor.GRAY)       return CasinoKeeper.TEXTURE_GROUND_GRAY;
        if(CONTAINER.color == DyeColor.GREEN)      return CasinoKeeper.TEXTURE_GROUND_GREEN;
        if(CONTAINER.color == DyeColor.LIGHT_BLUE) return CasinoKeeper.TEXTURE_GROUND_LIGHTBLUE;
        if(CONTAINER.color == DyeColor.LIME)       return CasinoKeeper.TEXTURE_GROUND_LIME;
        if(CONTAINER.color == DyeColor.MAGENTA)    return CasinoKeeper.TEXTURE_GROUND_MAGENTA;
        if(CONTAINER.color == DyeColor.ORANGE)     return CasinoKeeper.TEXTURE_GROUND_ORANGE;
        if(CONTAINER.color == DyeColor.PINK)       return CasinoKeeper.TEXTURE_GROUND_PINK;
        if(CONTAINER.color == DyeColor.PURPLE)     return CasinoKeeper.TEXTURE_GROUND_PURPLE;
        if(CONTAINER.color == DyeColor.RED)        return CasinoKeeper.TEXTURE_GROUND_RED;
        if(CONTAINER.color == DyeColor.LIGHT_GRAY) return CasinoKeeper.TEXTURE_GROUND_SILVER;
        if(CONTAINER.color == DyeColor.WHITE)      return CasinoKeeper.TEXTURE_GROUND_WHITE;
        if(CONTAINER.color == DyeColor.YELLOW)     return CasinoKeeper.TEXTURE_GROUND_YELLOW;

        return CasinoKeeper.TEXTURE_GROUND_GRAY;
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontShadowed(String text, int x, int y){
        drawFontShadowed(text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontShadowed(String text, int x, int y, int color){
        this.font.drawString(text,  x+1, y+1, 0);
        this.font.drawString(text,  x+0, y+0, color);
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
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        } else {
            if(card.suit <= 1) this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
            if(card.suit >= 2) this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        }
        int texX = card.suit == -1 || card.hidden ? 0 : card.number % 8;
        int texY = card.suit == -1 || card.hidden ? 4 : (card.suit % 2) * 2 + card.number / 8;
        blit(guiLeft + posX + card.shiftX, guiTop + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
    }

    /** Draws the Backside of a Card (also used for highlighter) **/
    public void drawCardBack(int posX, int posY, int color){
        if(color <= 6) this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        else           this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
        blit(guiLeft + posX, guiTop + posY, (color%7) * 32, 4 * 48, 32, 48);
    }

    /** Scans the PlayerInventory for Tokens **/
    protected void ValidateBet(){ // ???
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
    protected boolean AnotherBet(){
        ValidateBet();
        if(playerToken >= bet){
            CollectBet();
            return true;
        }
        return false;
    }

    /** Collects the bet from the Player **/
    protected void CollectBet(){ // ???
        if(CONTAINER.hasToken()){
            Inventory.decreaseInventory(PLAYER, CONTAINER.getToken(), bet);
            PLAYER.clearMatchingItems(Predicate.isEqual(CONTAINER.getToken()), bet);
            CasinoPacketHandler.sendToServer(new ServerPlayerMessage(CONTAINER.getToken().getItem(), 0, -bet));
            if(!CONTAINER.isCreative()) {
                CONTAINER.setBetStorage(CONTAINER.getBetStorage() + bet);
                CasinoPacketHandler.sendToServer(new ServerBlockMessage(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));
            }
        }
    }

    /** Pays the Reward to the Player **/
    protected void payBet(int multi){ // ???
        if(multi <= 0) return;
        if(CONTAINER.hasToken()){
            if(!CONTAINER.isCreative()) {
                Item item = CONTAINER.getToken().getItem();
                int count = bet * multi; // multi == 1 -> Return bet
                int count2 = 0;

                if(CONTAINER.getBetStorage() >= count) {
                    count2 = count;
                } else {
                    count2 = CONTAINER.getBetStorage();
                }

                CONTAINER.setBetStorage(CONTAINER.getBetStorage() - count);

                if(CONTAINER.getBetStorage() <= 0) {
                    CONTAINER.setBetStorage(0);
                    CONTAINER.setToken(new ItemStack(Blocks.AIR));
                }
                CasinoPacketHandler.sendToServer(new ServerBlockMessage(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));

                PLAYER.addItemStackToInventory(new ItemStack(item, count2));
                CasinoPacketHandler.sendToServer(new ServerPlayerMessage(item, 0, count2));
            } else {
                Item item = CONTAINER.getToken().getItem();
                int count = bet * multi; // multi == 1 -> Return bet
                PLAYER.addItemStackToInventory(new ItemStack(item, count));
                CasinoPacketHandler.sendToServer(new ServerPlayerMessage(item, 0, count));
            }
        }
    }

    private void drawLetter(char c, int posX, int posY, int sizeX, int sizeY, int vanish){
        if(c == 'a') blit(posX, posY + vanish, 0*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'b') blit(posX, posY + vanish, 1*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'c') blit(posX, posY + vanish, 2*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'd') blit(posX, posY + vanish, 3*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'e') blit(posX, posY + vanish, 4*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'f') blit(posX, posY + vanish, 5*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'g') blit(posX, posY + vanish, 6*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'h') blit(posX, posY + vanish, 7*sizeX, 0*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'i') blit(posX, posY + vanish, 0*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'j') blit(posX, posY + vanish, 1*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'k') blit(posX, posY + vanish, 2*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'l') blit(posX, posY + vanish, 3*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'm') blit(posX, posY + vanish, 4*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'n') blit(posX, posY + vanish, 5*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'o') blit(posX, posY + vanish, 6*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'p') blit(posX, posY + vanish, 7*sizeX, 1*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'q') blit(posX, posY + vanish, 0*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'r') blit(posX, posY + vanish, 1*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 's') blit(posX, posY + vanish, 2*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 't') blit(posX, posY + vanish, 3*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'u') blit(posX, posY + vanish, 4*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'v') blit(posX, posY + vanish, 5*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'w') blit(posX, posY + vanish, 6*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'x') blit(posX, posY + vanish, 7*sizeX, 2*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'y') blit(posX, posY + vanish, 0*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == 'z') blit(posX, posY + vanish, 1*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '0') blit(posX, posY + vanish, 2*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '1') blit(posX, posY + vanish, 3*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '2') blit(posX, posY + vanish, 4*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '3') blit(posX, posY + vanish, 5*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '4') blit(posX, posY + vanish, 6*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '5') blit(posX, posY + vanish, 7*sizeX, 3*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '6') blit(posX, posY + vanish, 0*sizeX, 4*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '7') blit(posX, posY + vanish, 1*sizeX, 4*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '8') blit(posX, posY + vanish, 2*sizeX, 4*sizeY + vanish, sizeX, sizeY - vanish);
        if(c == '9') blit(posX, posY + vanish, 3*sizeX, 4*sizeY + vanish, sizeX, sizeY - vanish);
    }

    /** Draws Logo from ItemModule **/
    private void drawLogo() {
        int move = 256 - intro; // Move logo up
        int vanish = move < 32 ? 0 : move-30 > 32 ? 32 : move - 30;
        if(move >= 30) {
            int i = 0;
        }

        int sizeX = 0;
        String s = CONTAINER.getName();
        String logo[] = s.split("_");
        if(logo[0].charAt(0) != 'x'){
            if(logo[0].charAt(0) == 'a'){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
                sizeX = 16;
            } else if(logo[0].charAt(0) == 'c'){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
                sizeX = 32;
                vanish = 0;
            }

            for(int i = 1; i < logo.length; i++){
                for(int k = 0; k < logo[i].length(); k++){
                    drawLetter(logo[i].charAt(k), guiLeft + 128 - logo[i].length()*16 + sizeX*k, guiTop + 32*i - move + vanish, 32, 32 - vanish, vanish);
                }
            }
        }
    }

    protected void action(int action){
        CasinoPacketHandler.sendToServer(new ServerActionMessage(action, CONTAINER.getPos()));
        //CONTAINER.logic().actionTouch(action);
    }

    protected void start(){
        Random r = new Random();
        CasinoPacketHandler.sendToServer(new ServerActionMessage(r.nextInt(), CONTAINER.getPos()));
    }

    protected void reset(){
        CasinoPacketHandler.sendToServer(new ServerTurnstateMessage(0, CONTAINER.getPos()));
    }

    protected void turnstate(int state){
        CasinoPacketHandler.sendToServer(new ServerTurnstateMessage(state, CONTAINER.getPos()));
    }

    protected void drawString(String text, int posX, int posY){
        this.font.drawString(text,  posX+1, posY+1, 0);
        this.font.drawString(text,  posX+0, posY+0, 16777215);
    }

}

