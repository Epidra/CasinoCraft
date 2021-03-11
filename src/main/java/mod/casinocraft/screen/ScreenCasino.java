package mod.casinocraft.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.network.*;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.Ship;
import mod.casinocraft.util.InventoryUtil;
import mod.casinocraft.util.Vector2;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;
import java.util.function.Predicate;

import static mod.casinocraft.util.KeyMap.*;

public abstract class ScreenCasino extends ContainerScreen<ContainerCasino> {

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
    protected int colourize = +65793;
    private final int grayscale = 16777215;

    protected int camera1 = 0;
    protected int camera0 = 0;

    protected boolean showDebug   = false;
    protected boolean showForfeit = false;

    protected int highlightIndex = 0;
    private int highlightTimer = 0;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCasino(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        PLAYER = player;
        CONTAINER = container;
        this.xSize = 256;
        this.ySize = 256;
        this.tableID = CONTAINER.tableID;
    }




    //----------------------------------------LOGIC----------------------------------------//

    private LogicBase logic(){
        return CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    /** Overwritten keyPressed function (only used for rerouting keyCode) */
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        keyTyped(keyCode);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    /** Fired when a key is pressed */
    private void keyTyped(int keyCode){
        if(keyCode == KEY_BACK){ showDebug = !showDebug; }
        if(isCurrentPlayer() && !logic().pause && (logic().turnstate == 2 || logic().turnstate == 3)){
            if(keyCode == KEY_UP)    { action(KEY_UP); }
            if(keyCode == KEY_DOWN)  { action(KEY_DOWN); }
            if(keyCode == KEY_LEFT)  { action(KEY_LEFT); }
            if(keyCode == KEY_RIGHT) { action(KEY_RIGHT); }
            if(keyCode == KEY_ENTER) { action(KEY_ENTER); }
            if(keyCode == KEY_0_IN) { action(KEY_0); }
            if(keyCode == KEY_1_IN) { action(KEY_1); }
            if(keyCode == KEY_2_IN) { action(KEY_2); }
            if(keyCode == KEY_3_IN) { action(KEY_3); }
            if(keyCode == KEY_4_IN) { action(KEY_4); }
            if(keyCode == KEY_5_IN) { action(KEY_5); }
            if(keyCode == KEY_6_IN) { action(KEY_6); }
            if(keyCode == KEY_7_IN) { action(KEY_7); }
            if(keyCode == KEY_8_IN) { action(KEY_8); }
            if(keyCode == KEY_9_IN) { action(KEY_9); }
        }

        if(tableID == 0){
            // Collect Token and start game (Arcade Version) / FROM: Start Screen
            if(CONTAINER.turnstate() == 0 && keyCode == KEY_ENTER) {
                if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBettingLow()) {
                    if(CONTAINER.hasToken()) collectBet();
                    start();
                }
            } else {
                // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
                if(CONTAINER.turnstate() == 7 && tableID == 0 && keyCode == KEY_ENTER) {
                    reset();
                } else {
                    // Collect Token and start game (Arcade Version) / FROM: GameOver Screen
                    if(CONTAINER.turnstate() == 5 && tableID == 0 && keyCode == KEY_ENTER) {
                        if(logic().hasHighscore()) { // Show Highscore Screen
                            turnstate(7);
                        } else {
                            reset();
                        }
                    }
                }
            }
            if((CONTAINER.turnstate() == 2 || CONTAINER.turnstate() == 3) &&                            tableID == 0 && keyCode == KEY_SPACE){ turnstate(-1); } // Toggle Pause Mode
            if((CONTAINER.turnstate() == 2 || CONTAINER.turnstate() == 3) && CONTAINER.logic().pause && tableID == 0 && keyCode == KEY_ENTER){ turnstate( 4); } // SET Game Over

        } else if(tableID == 3){ // Slot Machine Special Handling
            // Collect Token and start game (Arcade Version) / FROM: Start Screen
            if(CONTAINER.turnstate() == 0 && keyCode == KEY_ENTER) {
                if(playerToken >= CONTAINER.getBettingLow()) {
                    collectBet();
                    start();
                }
            } else {
                // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
                if(CONTAINER.turnstate() == 5 && keyCode == KEY_ENTER) {
                    reset();
                }
            }
            if(logic().turnstate == 2){
                if(keyCode == KEY_ENTER && logic().scoreLevel < 5){
                    if(playerToken >= bet){
                        collectBet();
                        playerToken = -1;
                        action(0);
                    }
                }
                if(keyCode == KEY_SPACE){
                    action(1);
                }
            } else {
                if(keyCode == KEY_SPACE){
                    action(1);
                }
            }
        }
    }

    /** Called when the mouse is clicked. */
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0 || mouseButton == 1){
            if(CONTAINER.turnstate() == 0) { // Adjust Bet
                if(logic().hasHighscore())
                    if(mouseRect(82, 164, 92, 26, mouseX, mouseY)) { turnstate(7); } // HIGHSCORE
                if(mouseRect(82-26, 204, 26, 26, mouseX, mouseY)) { if(bet > CONTAINER.getBettingLow() ){ highlight(1); bet--; }  } // BET MINUS
                if(mouseRect(82+92, 204, 26, 26, mouseX, mouseY)) { if(bet < CONTAINER.getBettingHigh()){ highlight(2); bet++; } } // BET PLUS
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
                        reset();
                    }
                }
            } else
            if(CONTAINER.turnstate() == 7) { // Highscore Screen
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
                    reset();
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

                if(isCurrentPlayer()){
                    if(mouseRect(tableID == 1 ? 241 : 241 + 64+32, 241, 14, 14, mouseX, mouseY)){
                        if(showForfeit){
                            turnstate(4);
                        } else {
                            showForfeit = true;
                            highlightTimer = 100;
                        }
                    }
                    if(!(logic() instanceof LogicDummy))
                        mouseClickedSUB(mouseX, mouseY, mouseButton);
                }
            }
        }
        return false;
    }




    //----------------------------------------DRAW----------------------------------------//

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY){

        if(highlightTimer > 0){
            highlightTimer--;
            if(highlightTimer == 0){
                highlightIndex = 0;
                showForfeit = false;
            }
        }

        colour += colourize;
        if( colour <= 0 || colour >= 16777215){
            colourize = -colourize;
        }


        // Debug Info (shown if Advanced Tooltips are enabled)
        if(showDebug){
            this.font.drawString(matrixStack, "PLAYER1: " + logic().currentPlayer[0],         tableID == 2 ? 355 : 260,  15, 16777215);
            this.font.drawString(matrixStack, "PLAYER2: " + logic().currentPlayer[1],         tableID == 2 ? 355 : 260,  25, 16777215);
            this.font.drawString(matrixStack, "PLAYER3: " + logic().currentPlayer[2],         tableID == 2 ? 355 : 260,  35, 16777215);
            this.font.drawString(matrixStack, "PLAYER4: " + logic().currentPlayer[3],         tableID == 2 ? 355 : 260,  45, 16777215);
            this.font.drawString(matrixStack, "PLAYER5: " + logic().currentPlayer[4],         tableID == 2 ? 355 : 260,  55, 16777215);
            this.font.drawString(matrixStack, "PLAYER6: " + logic().currentPlayer[5],         tableID == 2 ? 355 : 260,  65, 16777215);
            this.font.drawString(matrixStack, "TIMEOUT: " + logic().timeout,                  tableID == 2 ? 355 : 260,  75, 16777215);
            this.font.drawString(matrixStack, "STATE:   " + logic().turnstate,                tableID == 2 ? 355 : 260,  85, 16777215);
            this.font.drawString(matrixStack, "PLAYERS: " + logic().getFirstFreePlayerSlot(), tableID == 2 ? 355 : 260,  95, 16777215);
            this.font.drawString(matrixStack, "ACTIVE:  " + logic().activePlayer,             tableID == 2 ? 355 : 260, 105, 16777215);
        }

        if(logic() instanceof LogicDummy) return;

        // Search for tokens in PlayerInventory
        if(playerToken == -1 && logic().turnstate < 4) validateBet();

        // Multiplayer Additional Player Join Button
        if(CONTAINER.logic().isMultiplayer() && CONTAINER.turnstate() == 2 && !isCurrentPlayer()){
            if(CONTAINER.logic().hasFreePlayerSlots()){
                drawFont(matrixStack, "BET:", 158, 246-2);
                this.itemRenderer.renderItemIntoGUI(CONTAINER.getItemToken(), 158+20, 242-2);
                if(CONTAINER.getBettingLow() > 1) drawFont(matrixStack, "x" + CONTAINER.getBettingLow(), 158+50, 246-2);
            }
        }

        // Draw Start Screen Information (Card Table)
        if(CONTAINER.turnstate() == 0 && tableID != 0){
            if(tableID < 3){
                if(CONTAINER.hasToken() && CONTAINER.getBettingHigh() > 0) {
                    if(CONTAINER.getBettingLow() == CONTAINER.getBettingHigh()) {
                        drawFont(matrixStack, "The bet is:", 30, 100);
                        this.itemRenderer.renderItemIntoGUI(CONTAINER.getItemToken(), 100, 96);
                        if(CONTAINER.getBettingLow() > 1) drawFont(matrixStack, "x" + CONTAINER.getBettingLow(), 124, 100);
                    } else {
                        drawFont(matrixStack, "The bets are:", 30, 100);
                        this.itemRenderer.renderItemIntoGUI(CONTAINER.getItemToken(), 100, 96);
                        drawFont(matrixStack, "x" + CONTAINER.getBettingLow() + " to x" + CONTAINER.getBettingHigh(), 124, 100);
                    }

                    if(playerToken < CONTAINER.getBettingLow()) {
                        drawFont(matrixStack, "You don't have enough Token to play...", 30, 120);
                    } else {
                        drawFont(matrixStack, "Do you wish to play?", 30, 120);
                    }
                    if(CONTAINER.getBettingHigh() != CONTAINER.getBettingLow()) drawFont(matrixStack, "Your Bet:  " + bet, 30, 140);
                }
            } else {
                if(CONTAINER.hasToken() && CONTAINER.getBettingHigh() > 0){
                    this.font.drawString(matrixStack, "INSERT ", 128, 210, 16777215);
                    this.itemRenderer.renderItemIntoGUI(CONTAINER.getItemToken(), 160, 206);
                    if(CONTAINER.getBettingLow() > 1) this.font.drawString(matrixStack, "x" + CONTAINER.getBettingLow(), 180, 210, 16777215);
                    this.font.drawString(matrixStack, "Press ENTER", 128, 225, 16777215);
                }
            }

            // Draw Highscore (Card Table)
        } else if(CONTAINER.turnstate() == 7 && tableID != 0){
            for(int i = 0; i < 18; i++) {
                drawFont( matrixStack,           logic().scoreName[i],  40, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
                drawFontInvert(matrixStack, "" + logic().scoreHigh[i], 216, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
            }


            // Draw Start Screen Information (Arcade)
        } else if(CONTAINER.turnstate() == 0 && tableID == 0){
            if(CONTAINER.hasToken() && CONTAINER.getBettingHigh() > 0) {
                this.font.drawString(matrixStack, "INSERT ", 90, 180, 16777215);
                this.itemRenderer.renderItemIntoGUI(CONTAINER.getItemToken(), 126, 176);
                if(CONTAINER.getBettingLow() > 1) this.font.drawString(matrixStack, "x" + CONTAINER.getBettingLow(), 145, 180, 16777215);
                if(playerToken < CONTAINER.getBettingLow()) {
                    this.font.drawString(matrixStack, "NOT ENOUGH TOKEN", 80, 220, colour);
                } else {
                    this.font.drawString(matrixStack, "Press ENTER", 95, 220, colour);
                }
            } else {
                this.font.drawString(matrixStack, "Press ENTER", 95, 220, colour);
            }

            // Draw Highscore Information (Arcade)
        } else if(CONTAINER.turnstate() == 7 && tableID == 0){

            for(int i = 0; i < 20; i++) {
                drawFont(     matrixStack,       logic().scoreName[i],  40, 15 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
                drawFontInvert(matrixStack, "" + logic().scoreHigh[i], 216, 15 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
            }
            this.font.drawString(matrixStack, "Press ENTER", 95, 220, colour);
        } else {

            drawGuiContainerForegroundLayerSUB(matrixStack, mouseX, mouseY);

            if((CONTAINER.turnstate() == 2 || CONTAINER.turnstate() == 3) && CONTAINER.logic().pause){
                this.font.drawString(matrixStack, "PAUSE", 103, 200, 16777215);
                this.font.drawString(matrixStack, "Press ENTER to FORFEIT", 65, 220, 16777215);
            }

            // Single Player Table GIVE UP Button
            if(showForfeit && showDebug){
                this.font.drawString(matrixStack, "" + highlightTimer, 128-16-8-12+2, 245, 0);
            }

            if(CONTAINER.turnstate() == 5 && tableID == 0){
                this.font.drawString(matrixStack, "GAME OVER", 103, 200, 16777215);
                this.font.drawString(matrixStack, "Press ENTER", 95, 220, colour);
            }

            if(logic().turnstate == 4){ // ???
                gameOver();
            }
        }

        if(CONTAINER.getID() != logic().getID()){
            this.minecraft.player.closeScreen();
        }
    }

    /** Draws the background layer of this container (behind the items). */
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(tableID == 0) { // Arcade Background


            // Dummy Arcade Screen
            if(logic() instanceof LogicDummy){
                Random RANDOM = new Random();
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_STATIC);
                for(int y = 0; y < 8; y++){
                    for(int x = 0; x < 6; x++){
                        this.blit(matrixStack, guiLeft + 32 + 32*x, guiTop + 32*y, 32*RANDOM.nextInt(8), 32*RANDOM.nextInt(8), 32, 32);
                    }
                }
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_ARCADE);
                this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
            } else {

                // Normal Arcade Background
                this.minecraft.getTextureManager().bindTexture(getParallaxTexture(true));
                this.blit(matrixStack, guiLeft, guiTop, 0, camera1, 256, 256);
                this.minecraft.getTextureManager().bindTexture(getParallaxTexture(false));
                this.blit(matrixStack, guiLeft, guiTop, 0, camera0, 256, 256);
            }
        } else if(tableID < 3){ // Card Table Background
            this.minecraft.getTextureManager().bindTexture(tableID == 0 ? CasinoKeeper.TEXTURE_GROUND_ARCADE : getBackground());
            if(tableID == 2){
                this.blit(matrixStack, guiLeft-128+32, guiTop,  0, 0, this.xSize-32, this.ySize); // Background Left
                this.blit(matrixStack, guiLeft+128   , guiTop, 32, 0, this.xSize-32, this.ySize); // Background Right
            } else {
                this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
            }
        } else { // Slot Machine Background
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTMACHINE);
            this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
        }


        // Draws Logo from ItemModule
        if(CONTAINER.turnstate() <= 1) { drawLogo(matrixStack); }


        // MiniGame Layer
        if(logic() instanceof LogicDummy) return;
        if(CONTAINER.turnstate() >= 1 && CONTAINER.turnstate() < 6){
            if(logic().pause) GlStateManager.color4f(0.35F, 0.35F, 0.35F, 1.0F);
            drawGuiContainerBackgroundLayerSUB(matrixStack, partialTicks, mouseX, mouseY);
            if(isCurrentPlayer()) drawGuiContainerBackgroundLayerGUI(matrixStack, partialTicks, mouseX, mouseY);
            if(logic().pause) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }


        // If NOT Ingame
        if((CONTAINER.turnstate() == 5 || CONTAINER.turnstate() == 0 || CONTAINER.turnstate() == 7) && (tableID == 1 || tableID == 2)){
            if(CONTAINER.turnstate() == 5 && logic().hasHighscore()){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                blit(matrixStack, guiLeft+89, guiTop+206, 0, 22, 78, 22); // Button Highscore
            } else
            if(CONTAINER.turnstate() == 5){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                blit(matrixStack, guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(CONTAINER.turnstate() == 7){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                blit(matrixStack, guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBettingLow()){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                blit(matrixStack, guiLeft+89, guiTop+206, 78, 22, 78, 22); // Button New Game
            }
            if(CONTAINER.turnstate() == 0 && logic().hasHighscore()){
                blit(matrixStack, guiLeft+89, guiTop+166, 0, 22, 78, 22); // Button Highscore
            }
            if(CONTAINER.turnstate() == 0 && playerToken >= CONTAINER.getBettingLow()) {
                blit(matrixStack, guiLeft +  58, guiTop + 206, 234, 88 + (highlightIndex == 1 ? 22 : 0) + (bet == CONTAINER.getBettingLow()  ? 44 : 0), 22, 22); // Button Minus
                blit(matrixStack, guiLeft + 176, guiTop + 206, 234,      (highlightIndex == 2 ? 22 : 0) + (bet == CONTAINER.getBettingHigh() ? 44 : 0), 22, 22); // Button Plus
            }
        }


        // Multiplayer Additional Player Join Button
        if(CONTAINER.logic().isMultiplayer() && CONTAINER.turnstate() == 2 && !isCurrentPlayer()){
            if(CONTAINER.logic().hasFreePlayerSlots()){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                this.blit(matrixStack, guiLeft + 153, guiTop + 237, 78, 220, 78, 22);
                if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBettingLow()){
                    blit(matrixStack, guiLeft + 26, guiTop + 237, 0, 220, 78, 22);
                }
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
                this.blit(matrixStack, guiLeft + 128-16, guiTop + 232, 192, 32 + 32*CONTAINER.logic().getFirstFreePlayerSlot(), 32, 32);
            }
        }


        // Single Player Table GIVE UP Button
        if(!CONTAINER.logic().isMultiplayer() && CONTAINER.turnstate() == 2 && (tableID == 1 || tableID == 2)){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
            this.blit(matrixStack, guiLeft + (tableID == 1 ? 241 : 241+64+32), guiTop + 241, showForfeit ? 14 : 0, 242, 14, 14);
            if(showForfeit){
                this.blit(matrixStack, guiLeft + 128-39, guiTop + 241, 28, 242, 78, 14);
            }
        }


        // Draws Arcade Border
        if(tableID == 0) {
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_ARCADE);
            this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
            int shift2 = CONTAINER.turnstate() == 2 || CONTAINER.turnstate() == 3 ? 2 : 1;
            if(CONTAINER.turnstate() != 5 && !CONTAINER.logic().pause) camera1 = (camera1 + shift2)   % 256;
            if(CONTAINER.turnstate() != 5 && !CONTAINER.logic().pause) camera0 = (camera0 + shift2*2) % 256;
        }


        // Multiplayer Status
        if((logic().turnstate == 2 || logic().turnstate == 3) && logic().isMultiplayer()){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
            for(int i = 0; i < logic().getFirstFreePlayerSlot(); i++){
                this.blit(matrixStack, guiLeft+(tableID == 2 ? 355-15 : 260-15), guiTop+32 + 36*i, 224, 32 + 32 * i, 32, 32);
            }
            if(logic().activePlayer < logic().getFirstFreePlayerSlot()){
                this.blit(matrixStack, guiLeft+(tableID == 2 ? 355-15 : 260-15), guiTop+32 + 36*logic().activePlayer, 224-32, 32 + 32 * logic().activePlayer, 32, 32);
            }
        }

    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected void highlight(int index){
        highlightTimer = 10;
        highlightIndex = index;
    }

    private void gameOver(){ // ???
        int pos = getPlayerPosition();
        if (pos > -1 && playerToken != -1) {
            payBet(logic().reward[pos]);
            turnstate(10 + pos);
            playerToken = -1;
        }
        if(allCleared()){
            turnstate(5);
            if(logic().hasHighscore()) {
                highscore();
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

    private void highscore(){
        CasinoPacketHandler.sendToServer(new MessageScoreServer(PLAYER.player.getName().getString(), logic().scorePoint, CONTAINER.pos()));
        int lastScore = 0;
        int prizeSET = 0;
        int prizeCON = 0;
        if(CONTAINER.getPrizeCount1() > 0){
            if(CONTAINER.getPrizeMode1()){
                if(CONTAINER.getPrizeScore1() <= CONTAINER.logic().scorePoint && CONTAINER.getPrizeScore1() > lastScore){
                    lastScore = CONTAINER.getPrizeScore1();
                    prizeSET  = CONTAINER.getPrizeCount1();
                }
            } else {
                if(CONTAINER.getPrizeScore1() > 0) prizeCON += CONTAINER.getPrizeCount1() * (CONTAINER.logic().scorePoint / CONTAINER.getPrizeScore1());
            }
        }

        if(CONTAINER.getPrizeCount2() > 0){
            if(CONTAINER.getPrizeMode2()){
                if(CONTAINER.getPrizeScore2() <= CONTAINER.logic().scorePoint && CONTAINER.getPrizeScore2() > lastScore){
                    lastScore = CONTAINER.getPrizeScore2();
                    prizeSET  = CONTAINER.getPrizeCount2();
                }
            } else {
                if(CONTAINER.getPrizeScore2() > 0) prizeCON += CONTAINER.getPrizeCount2() * (CONTAINER.logic().scorePoint / CONTAINER.getPrizeScore2());
            }
        }

        if(CONTAINER.getPrizeCount3() > 0){
            if(CONTAINER.getPrizeMode3()){
                if(CONTAINER.getPrizeScore3() <= CONTAINER.logic().scorePoint && CONTAINER.getPrizeScore3() > lastScore){
                    lastScore = CONTAINER.getPrizeScore3();
                    prizeSET  = CONTAINER.getPrizeCount3();
                }
            } else {
                if(CONTAINER.getPrizeScore3() > 0) prizeCON += CONTAINER.getPrizeCount3() * (CONTAINER.logic().scorePoint / CONTAINER.getPrizeScore3());
            }
        }

        payPrize(prizeSET + prizeCON);

    }

    /** Draws Colored Card Table Background **/
    private ResourceLocation getBackground(){
        if(CONTAINER.color == DyeColor.BLACK)      return CasinoKeeper.TEXTURE_GROUND_BLACK;
        if(CONTAINER.color == DyeColor.BLUE)       return CasinoKeeper.TEXTURE_GROUND_BLUE;
        if(CONTAINER.color == DyeColor.BROWN)      return CasinoKeeper.TEXTURE_GROUND_BROWN;
        if(CONTAINER.color == DyeColor.CYAN)       return CasinoKeeper.TEXTURE_GROUND_CYAN;
        if(CONTAINER.color == DyeColor.GRAY)       return CasinoKeeper.TEXTURE_GROUND_GRAY;
        if(CONTAINER.color == DyeColor.GREEN)      return CasinoKeeper.TEXTURE_GROUND_GREEN;
        if(CONTAINER.color == DyeColor.LIGHT_BLUE) return CasinoKeeper.TEXTURE_GROUND_LIGHT_BLUE;
        if(CONTAINER.color == DyeColor.LIME)       return CasinoKeeper.TEXTURE_GROUND_LIME;
        if(CONTAINER.color == DyeColor.MAGENTA)    return CasinoKeeper.TEXTURE_GROUND_MAGENTA;
        if(CONTAINER.color == DyeColor.ORANGE)     return CasinoKeeper.TEXTURE_GROUND_ORANGE;
        if(CONTAINER.color == DyeColor.PINK)       return CasinoKeeper.TEXTURE_GROUND_PINK;
        if(CONTAINER.color == DyeColor.PURPLE)     return CasinoKeeper.TEXTURE_GROUND_PURPLE;
        if(CONTAINER.color == DyeColor.RED)        return CasinoKeeper.TEXTURE_GROUND_RED;
        if(CONTAINER.color == DyeColor.LIGHT_GRAY) return CasinoKeeper.TEXTURE_GROUND_LIGHT_GRAY;
        if(CONTAINER.color == DyeColor.WHITE)      return CasinoKeeper.TEXTURE_GROUND_WHITE;
        if(CONTAINER.color == DyeColor.YELLOW)     return CasinoKeeper.TEXTURE_GROUND_YELLOW;

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
        if(bet < CONTAINER.getBettingLow() ) bet = CONTAINER.getBettingLow();
        if(bet > CONTAINER.getBettingHigh()) bet = CONTAINER.getBettingHigh();
        if(CONTAINER.hasToken()){
            Item item = CONTAINER.getItemToken().getItem();
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
            InventoryUtil.decreaseInventory(PLAYER, CONTAINER.getItemToken(), bet);
            {
                int i = 0;
                ItemStack itemStack = ItemStack.EMPTY;
                Predicate<ItemStack> p_195408_1_ = Predicate.isEqual(CONTAINER.getItemToken());
                int count = bet;

                for(int j = 0; j < PLAYER.getSizeInventory(); ++j) {
                    ItemStack itemstack = PLAYER.getStackInSlot(j);
                    if (!itemstack.isEmpty() && p_195408_1_.test(itemstack)) {
                        int k = count <= 0 ? itemstack.getCount() : Math.min(count - i, itemstack.getCount());
                        i += k;
                        if (count != 0) {
                            itemstack.shrink(k);
                            if (itemstack.isEmpty()) {
                                PLAYER.setInventorySlotContents(j, ItemStack.EMPTY);
                            }
                        }
                    }
                }

                if (!itemStack.isEmpty() && p_195408_1_.test(itemStack)) {
                    int l = count <= 0 ? itemStack.getCount() : Math.min(count - i, itemStack.getCount());
                    if (count != 0) { itemStack.shrink(l); }
                }
            }
            CasinoPacketHandler.sendToServer(new MessagePlayerServer(CONTAINER.getItemToken().getItem(), -bet));
            if(!CONTAINER.getSettingInfiniteToken()) {
                CONTAINER.setStorageToken(CONTAINER.getStorageToken() + bet);
                sendMessageBlock();
            }
        }
    }

    /** Pays the Reward to the Player **/
    private void payBet(int multi){ // ???
        if(multi <= 0) return;
        if(CONTAINER.hasToken()){
            if(!CONTAINER.getSettingInfiniteToken()) {
                Item item = CONTAINER.getItemToken().getItem();
                int count = bet * multi; // multi == 1 -> Return bet
                int count2 = 0;

                count2 = Math.min(CONTAINER.getStorageToken(), count);

                CONTAINER.setStorageToken(CONTAINER.getStorageToken() - count);

                if(CONTAINER.getStorageToken() <= 0) {
                    CONTAINER.setStorageToken(0);
                    CONTAINER.setItemToken(new ItemStack(Blocks.AIR));
                }
                sendMessageBlock();

                PLAYER.addItemStackToInventory(new ItemStack(item, count2));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count2));
            } else {
                Item item = CONTAINER.getItemToken().getItem();
                int count = bet * multi; // multi == 1 -> Return bet
                PLAYER.addItemStackToInventory(new ItemStack(item, count));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count));
            }
        }
    }

    /** Pays the Prize to the Player **/
    private void payPrize(int amount){ // ???
        if(amount <= 0) return;
        if(CONTAINER.hasReward()){
            if(!CONTAINER.getSettingInfinitePrize()) {
                Item item = CONTAINER.getItemPrize().getItem();
                int count = amount; // multi == 1 -> Return bet
                int count2 = 0;

                count2 = Math.min(CONTAINER.getStoragePrize(), count);

                CONTAINER.setStoragePrize(CONTAINER.getStoragePrize() - count);

                if(CONTAINER.getStoragePrize() <= 0) {
                    CONTAINER.setStoragePrize(0);
                    CONTAINER.setItemPrize(new ItemStack(Blocks.AIR));
                }
                sendMessageBlock();

                PLAYER.addItemStackToInventory(new ItemStack(item, count2));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count2));
            } else {
                Item item = CONTAINER.getItemPrize().getItem();
                int count = amount; // multi == 1 -> Return bet
                PLAYER.addItemStackToInventory(new ItemStack(item, count));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count));
            }
        }
    }

    protected void action(int action){
        CasinoPacketHandler.sendToServer(new MessageStateServer(false, action, CONTAINER.pos()));
    }

    protected void start(){
        Random r = new Random();
        CasinoPacketHandler.sendToServer(new MessageStartServer(PLAYER.player.getName().getString(), r.nextInt(1000000), CONTAINER.pos()));
    }

    private void reset(){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, 0, CONTAINER.pos()));
    }

    protected void turnstate(int state){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, state, CONTAINER.pos()));
    }

    private void addNewPlayer(){
        CasinoPacketHandler.sendToServer(new MessageStartServer(PLAYER.player.getName().getString(), -1, CONTAINER.pos()));
    }

    protected boolean isCurrentPlayer(){
        if(CONTAINER.logic().isMultiplayer()){
            if(CONTAINER.getCurrentPlayer(0).matches("void")){
                return true;
            }
            for(int i = 0; i < 6; i++){
                if(CONTAINER.getCurrentPlayer(i).matches(PLAYER.player.getName().getString())){
                    return true;
                }
            }
        } else {

            return CONTAINER.getCurrentPlayer(0).matches("void") || CONTAINER.getCurrentPlayer(0).matches(PLAYER.player.getName().getString());
        }
        return false;
    }

    protected boolean isActivePlayer(){
        if(CONTAINER.logic().isMultiplayer()){
            for(int i = 0; i < 6; i++){
                if(CONTAINER.getCurrentPlayer(i).matches(PLAYER.player.getName().getString())){
                    if(i == logic().activePlayer){
                        return true;
                    }
                }
            }
        } else {
            return CONTAINER.getCurrentPlayer(0).matches(PLAYER.player.getName().getString());
        }
        return false;
    }

    protected int getPlayerPosition(){
        if(CONTAINER.logic().isMultiplayer()){
            for(int i = 0; i < 6; i++){
                if(CONTAINER.getCurrentPlayer(i).matches(PLAYER.player.getName().getString())){
                    return i;
                }
            }
        } else {
            return CONTAINER.getCurrentPlayer(0).matches(PLAYER.player.getName().getString()) ? 0 : -1;
        }
        return -1;
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFont(MatrixStack matrixStack, String text, int x, int y){
        drawFont(matrixStack, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFont(MatrixStack matrixStack, String text, int x, int y, int color){
        this.font.drawString(matrixStack, text,  x+1, y+1, 0);
        this.font.drawString(matrixStack, text,  x+0, y+0, color);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontInvert(MatrixStack matrixStack, String text, int x, int y){
        drawFontInvert(matrixStack, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontInvert(MatrixStack matrixStack, String text, int x, int y, int color){
        int w = this.font.getStringWidth(text);
        this.font.drawString(matrixStack, text,  x+1 - w, y+1, 0);
        this.font.drawString(matrixStack, text,  x+0 - w, y+0, color);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontCenter(MatrixStack matrixStack, String text, int x, int y){
        drawFontCenter(matrixStack, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontCenter(MatrixStack matrixStack, String text, int x, int y, int color){
        int w = this.font.getStringWidth(text);
        this.font.drawString(matrixStack, text,  x+1 - w/2, y+1, 0);
        this.font.drawString(matrixStack, text,  x+0 - w/2, y+0, color);
    }

    /** Draws a Card **/
    public void drawCard(MatrixStack matrixStack, int posX, int posY, Card card){
        if(card.suit == -1) return;
        if(card.hidden){
            this.minecraft.getTextureManager().bindTexture(getCardsTexture(true));
        } else {
            if(card.suit <= 1) this.minecraft.getTextureManager().bindTexture(getCardsTexture(false));
            if(card.suit >= 2) this.minecraft.getTextureManager().bindTexture(getCardsTexture(true));
        }
        int texX = card.suit == -1 || card.hidden ? 0 : card.number % 8;
        int texY = card.suit == -1 || card.hidden ? 4 : (card.suit % 2) * 2 + card.number / 8;
        if(CasinoKeeper.config_animated_cards.get() && !card.hidden){
            if(card.number >= 10){
                if(logic().frame == card.suit*12 + (card.number-10)*3){
                    texX += 3;
                }
            }
        }
        blit(matrixStack, guiLeft + posX + card.shiftX, guiTop + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
    }

    /** Draws the Backside of a Card (also used for highlighter) **/
    public void drawCardBack(MatrixStack matrixStack, int posX, int posY, int color){
        if(color <= 6) this.minecraft.getTextureManager().bindTexture(getCardsTexture(true));
        else           this.minecraft.getTextureManager().bindTexture(getCardsTexture(false));
        blit(matrixStack, guiLeft + posX, guiTop + posY, (color%7) * 32, 4 * 48, 32, 48);
    }

    private void drawLetter(MatrixStack matrixStack, char c, int posX, int posY, int sizeX, int sizeY){
        if(c == 'a') blit(matrixStack, posX, posY,       0,       0, sizeX, sizeY);
        if(c == 'b') blit(matrixStack, posX, posY,   sizeX,       0, sizeX, sizeY);
        if(c == 'c') blit(matrixStack, posX, posY, 2*sizeX,       0, sizeX, sizeY);
        if(c == 'd') blit(matrixStack, posX, posY, 3*sizeX,       0, sizeX, sizeY);
        if(c == 'e') blit(matrixStack, posX, posY, 4*sizeX,       0, sizeX, sizeY);
        if(c == 'f') blit(matrixStack, posX, posY, 5*sizeX,       0, sizeX, sizeY);
        if(c == 'g') blit(matrixStack, posX, posY, 6*sizeX,       0, sizeX, sizeY);
        if(c == 'h') blit(matrixStack, posX, posY, 7*sizeX,       0, sizeX, sizeY);
        if(c == 'i') blit(matrixStack, posX, posY,       0,   sizeY, sizeX, sizeY);
        if(c == 'j') blit(matrixStack, posX, posY,   sizeX,   sizeY, sizeX, sizeY);
        if(c == 'k') blit(matrixStack, posX, posY, 2*sizeX,   sizeY, sizeX, sizeY);
        if(c == 'l') blit(matrixStack, posX, posY, 3*sizeX,   sizeY, sizeX, sizeY);
        if(c == 'm') blit(matrixStack, posX, posY, 4*sizeX,   sizeY, sizeX, sizeY);
        if(c == 'n') blit(matrixStack, posX, posY, 5*sizeX,   sizeY, sizeX, sizeY);
        if(c == 'o') blit(matrixStack, posX, posY, 6*sizeX,   sizeY, sizeX, sizeY);
        if(c == 'p') blit(matrixStack, posX, posY, 7*sizeX,   sizeY, sizeX, sizeY);
        if(c == 'q') blit(matrixStack, posX, posY,       0, 2*sizeY, sizeX, sizeY);
        if(c == 'r') blit(matrixStack, posX, posY,   sizeX, 2*sizeY, sizeX, sizeY);
        if(c == 's') blit(matrixStack, posX, posY, 2*sizeX, 2*sizeY, sizeX, sizeY);
        if(c == 't') blit(matrixStack, posX, posY, 3*sizeX, 2*sizeY, sizeX, sizeY);
        if(c == 'u') blit(matrixStack, posX, posY, 4*sizeX, 2*sizeY, sizeX, sizeY);
        if(c == 'v') blit(matrixStack, posX, posY, 5*sizeX, 2*sizeY, sizeX, sizeY);
        if(c == 'w') blit(matrixStack, posX, posY, 6*sizeX, 2*sizeY, sizeX, sizeY);
        if(c == 'x') blit(matrixStack, posX, posY, 7*sizeX, 2*sizeY, sizeX, sizeY);
        if(c == 'y') blit(matrixStack, posX, posY,       0, 3*sizeY, sizeX, sizeY);
        if(c == 'z') blit(matrixStack, posX, posY,   sizeX, 3*sizeY, sizeX, sizeY);
        if(c == '0') blit(matrixStack, posX, posY, 2*sizeX, 3*sizeY, sizeX, sizeY);
        if(c == '1') blit(matrixStack, posX, posY, 3*sizeX, 3*sizeY, sizeX, sizeY);
        if(c == '2') blit(matrixStack, posX, posY, 4*sizeX, 3*sizeY, sizeX, sizeY);
        if(c == '3') blit(matrixStack, posX, posY, 5*sizeX, 3*sizeY, sizeX, sizeY);
        if(c == '4') blit(matrixStack, posX, posY, 6*sizeX, 3*sizeY, sizeX, sizeY);
        if(c == '5') blit(matrixStack, posX, posY, 7*sizeX, 3*sizeY, sizeX, sizeY);
        if(c == '6') blit(matrixStack, posX, posY,       0, 4*sizeY, sizeX, sizeY);
        if(c == '7') blit(matrixStack, posX, posY,   sizeX, 4*sizeY, sizeX, sizeY);
        if(c == '8') blit(matrixStack, posX, posY, 2*sizeX, 4*sizeY, sizeX, sizeY);
        if(c == '9') blit(matrixStack, posX, posY, 3*sizeX, 4*sizeY, sizeX, sizeY);
    }

    /** Draws Logo from ItemModule **/
    private void drawLogo(MatrixStack matrixStack) {
        int sizeX = 0;
        String[] logo = getGameName().split("_");
        if(tableID <= 2){
            if(tableID == 0){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
                sizeX = 16;
            } else if(tableID == 1 || tableID == 2){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
                sizeX = 32;
            }

            for(int i = 0; i < logo.length; i++){
                for(int k = 0; k < logo[i].length(); k++){
                    drawLetter(matrixStack, logo[i].charAt(k), guiLeft + 128 - logo[i].length()*(sizeX/2) + sizeX*k, guiTop + 32 + 32*i, 32, 32);
                }
            }
        }
    }

    protected void drawMino(MatrixStack matrixStack, int posX, int posY, int idX, int idY){
        this.blit(matrixStack, guiLeft + posX, guiTop + posY, 24 * idX, 24 * idY, 24, 24);
    }

    protected void drawMino(MatrixStack matrixStack, int posX, int posY){
        drawMino(matrixStack, posX, posY, 0, 0);
    }

    protected void drawMinoSmall(MatrixStack matrixStack, int posX, int posY, int id, boolean alternate){
        if(alternate){
            this.blit(matrixStack, guiLeft + posX, guiTop + posY, 240, 16 * id, 16, 16);
        } else {
            this.blit(matrixStack, guiLeft + posX, guiTop + posY, 16 * id, 240, 16, 16);
        }
    }

    protected void drawMinoSmall(MatrixStack matrixStack, int posX, int posY){
        drawMinoSmall(matrixStack, posX, posY, 0, false);
    }

    protected void drawDigi(MatrixStack matrixStack, int posX, int posY, int idX, int idY){
        this.blit(matrixStack, guiLeft + posX, guiTop + posY, 16 * idX, 16 + 16 * idY, 16, 16);
    }

    protected void drawDigi(MatrixStack matrixStack, int posX, int posY){
        drawDigi(matrixStack, posX, posY, 0, 0);
    }

    protected void drawDigiSmall(MatrixStack matrixStack, int posX, int posY, int id){
        this.blit(matrixStack, guiLeft + posX    , guiTop + posY    , 16 * id     , 16   , 6, 6);
        this.blit(matrixStack, guiLeft + posX + 6, guiTop + posY    , 16 * id + 10, 16   , 6, 6);
        this.blit(matrixStack, guiLeft + posX    , guiTop + posY + 6, 16 * id     , 16+10, 6, 6);
        this.blit(matrixStack, guiLeft + posX + 6, guiTop + posY + 6, 16 * id + 10, 16+10, 6, 6);

    }

    protected void drawDigiSmall(MatrixStack matrixStack, int posX, int posY){
        drawDigiSmall(matrixStack, posX, posY, 0);
    }

    protected void drawDigiSymbol(MatrixStack matrixStack, int posX, int posY, int id){
        this.blit(matrixStack, guiLeft + posX, guiTop + posY, 16 * id, 0, 16, 16);
    }

    protected void drawDigiSymbol(MatrixStack matrixStack, int posX, int posY){
        drawDigiSymbol(matrixStack, posX, posY, 0);
    }

    protected void drawButton(MatrixStack matrixStack, int posX, int posY, int id){

    }

    protected void drawShip(MatrixStack matrixStack, Ship ship, int shipID, int lookDirection, boolean animate){
        int frame = logic().turnstate < 4 && animate ? (logic().frame % 12) / 2 : 0;
        if(frame == 4) frame = 2;
        if(frame == 5) frame = 1;
        int direction = lookDirection == -1 ? ship.getLookDirection() : lookDirection;
        this.blit(matrixStack, guiLeft + 32 + ship.getPos().X, guiTop + 8 + ship.getPos().Y, 64*(shipID%4) + 16*frame, 128 + direction*16 + (shipID/4)*64, 16, 16);
    }

    protected void drawShip(MatrixStack matrixStack, Vector2 vec, int shipID){
        int frame = logic().turnstate < 4 ? (logic().frame % 12) / 2 : 0;
        if(frame == 4) frame = 2;
        if(frame == 5) frame = 1;
        this.blit(matrixStack, guiLeft + 32 + vec.X*16, guiTop + 8 + vec.Y*16, 64*(shipID%4) + 16*frame, 128 + (shipID/4)*64, 16, 16);
    }

    private ResourceLocation getParallaxTexture(boolean lowTexture){
        switch(CONTAINER.getSettingAlternateColor()){
            case 0: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_0_LOW : CasinoKeeper.TEXTURE_PARALLAX_0_HIGH;
            case 1: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_1_LOW : CasinoKeeper.TEXTURE_PARALLAX_1_HIGH;
            case 2: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_2_LOW : CasinoKeeper.TEXTURE_PARALLAX_2_HIGH;
            case 3: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_3_LOW : CasinoKeeper.TEXTURE_PARALLAX_3_HIGH;
            case 4: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_4_LOW : CasinoKeeper.TEXTURE_PARALLAX_4_HIGH;
            case 5: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_5_LOW : CasinoKeeper.TEXTURE_PARALLAX_5_HIGH;
        }
        return CasinoKeeper.TEXTURE_STATIC;
    }

    protected ResourceLocation getCardsTexture(boolean noirTexture){
        switch(CONTAINER.getSettingAlternateColor()){
            case 0: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_0_NOIR : CasinoKeeper.TEXTURE_CARDS_0_ROUGE;
            case 1: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_1_NOIR : CasinoKeeper.TEXTURE_CARDS_1_ROUGE;
            case 2: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_2_NOIR : CasinoKeeper.TEXTURE_CARDS_2_ROUGE;
            case 3: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_3_NOIR : CasinoKeeper.TEXTURE_CARDS_3_ROUGE;
            case 4: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_4_NOIR : CasinoKeeper.TEXTURE_CARDS_4_ROUGE;
            case 5: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_5_NOIR : CasinoKeeper.TEXTURE_CARDS_5_ROUGE;
        }
        return CasinoKeeper.TEXTURE_STATIC;
    }

    private void sendMessageBlock(){
        NonNullList<ItemStack> inv = NonNullList.withSize(5, ItemStack.EMPTY);
        inv.set(0, CONTAINER.inventory.getStackInSlot(0));
        inv.set(1, CONTAINER.inventory.getStackInSlot(1));
        inv.set(2, CONTAINER.inventory.getStackInSlot(2));
        inv.set(3, CONTAINER.inventory.getStackInSlot(3));
        inv.set(4, CONTAINER.inventory.getStackInSlot(4));
        CasinoPacketHandler.sendToServer(new MessageInventoryServer(inv, CONTAINER.getStorageToken(), CONTAINER.getStoragePrize(), CONTAINER.pos()));
    }




    //----------------------------------------OVERRIDES----------------------------------------//

    protected abstract void mouseClickedSUB(double mouseX, double mouseY, int mouseButton);
    protected abstract void drawGuiContainerForegroundLayerSUB(MatrixStack matrixStack, int mouseX, int mouseY);
    protected abstract void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY);
    protected abstract void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY);
    protected abstract String getGameName();

}

