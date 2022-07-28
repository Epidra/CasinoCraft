package mod.casinocraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.Config;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.network.*;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.Ship;
import mod.lucky77.screen.ScreenBase;
import mod.lucky77.system.SystemInventory;
import mod.lucky77.util.Vector2;
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

import static mod.casinocraft.util.KeyMap.*;

public abstract class ScreenCasino extends ScreenBase<MenuCasino> {

    /** Determines the Background of the game. */
    protected int tableID; // 0 - Arcade, 1 - Table Normal, 2 - Table Wide, 3 - Slot Machine
    /** Amount of tokens in the PlayerInventory */
    protected int playerToken = -1;
    /** The bet set up in the opening screen */
    protected int bet = 0;
    /** the current shown bet balance */
    private int balance = 0;

    private int colour = 0;
    private int colourize = +65793;
    private final int grayscale = 16777215;

    private int camera1 = 0;
    private int camera0 = 0;

    protected boolean showDebug   = false;
    protected boolean showForfeit = false;

    protected int highlightIndex = 0;
    private   int highlightTimer = 0;

    /** The Player Inventory **/
    private final Inventory inventory;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCasino(MenuCasino container, Inventory player, Component name) {
        super(container, player, name, 256, 256);
        this.tableID = container.tableID;
        inventory = player;
    }





    //----------------------------------------LOGIC----------------------------------------//

    private LogicModule logic(){
        return menu.logic();
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
            if(menu.turnstate() == 0 && keyCode == KEY_ENTER) {
                if(!menu.hasToken() || playerToken >= menu.getBettingLow()) {
                    if(menu.hasToken()) collectBet();
                    start();
                }
            } else {
                // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
                if(menu.turnstate() == 7 && tableID == 0 && keyCode == KEY_ENTER) {
                    reset();
                } else {
                    // Collect Token and start game (Arcade Version) / FROM: GameOver Screen
                    if(menu.turnstate() == 5 && tableID == 0 && keyCode == KEY_ENTER) {
                        if(logic().hasHighscore()) { // Show Highscore Screen
                            turnstate(7);
                        } else {
                            reset();
                        }
                    }
                }
            }
            if((menu.turnstate() == 2 || menu.turnstate() == 3) &&                       tableID == 0 && keyCode == KEY_SPACE){ turnstate(-1); } // Toggle Pause Mode
            if((menu.turnstate() == 2 || menu.turnstate() == 3) && menu.logic().pause && tableID == 0 && keyCode == KEY_ENTER){ turnstate( 4); } // SET Game Over

        } else if(tableID == 3){ // Slot Machine Special Handling
            // Collect Token and start game (Arcade Version) / FROM: Start Screen
            if(menu.turnstate() == 0 && keyCode == KEY_ENTER) {
                if(playerToken >= menu.getBettingLow()) {
                    collectBet();
                    start();
                }
            } else {
                // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
                if(menu.turnstate() == 5 && keyCode == KEY_ENTER) {
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
            if(menu.turnstate() == 0) { // Adjust Bet
                if(logic().hasHighscore())
                    if(mouseRect(82, 164, 92, 26, mouseX, mouseY)) { turnstate(7); } // HIGHSCORE
                if(mouseRect(82-26, 204, 26, 26, mouseX, mouseY)) { if(bet > menu.getBettingLow() ){ highlight(1); bet--; }  } // BET MINUS
                if(mouseRect(82+92, 204, 26, 26, mouseX, mouseY)) { if(bet < menu.getBettingHigh()){ highlight(2); bet++; } } // BET PLUS
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)) {
                    // Start Game
                    if(!menu.hasToken()){
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
            if(menu.turnstate() == 5) { // GameOver Screen
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
                    if(logic().hasHighscore()) { // Show Highscore
                        turnstate(7);
                    } else { // Reset Game
                        reset();
                    }
                }
            } else
            if(menu.turnstate() == 7) { // Highscore Screen
                if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
                    reset();
                }
            } else if(menu.logic().isMultiplayer() && menu.turnstate() == 2 && !isCurrentPlayer()){ // Multiplayer Additional Player Join Button
                    if(menu.logic().hasFreePlayerSlots()){
                        if(mouseRect(26, 237, 78, 22, mouseX, mouseY)){
                            if(!menu.hasToken()){
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

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY){

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
            this.font.draw(matrixStack, "PLAYER1: " + logic().currentPlayer[0],         tableID == 2 ? 355 : 260,  15, 16777215);
            this.font.draw(matrixStack, "PLAYER2: " + logic().currentPlayer[1],         tableID == 2 ? 355 : 260,  25, 16777215);
            this.font.draw(matrixStack, "PLAYER3: " + logic().currentPlayer[2],         tableID == 2 ? 355 : 260,  35, 16777215);
            this.font.draw(matrixStack, "PLAYER4: " + logic().currentPlayer[3],         tableID == 2 ? 355 : 260,  45, 16777215);
            this.font.draw(matrixStack, "PLAYER5: " + logic().currentPlayer[4],         tableID == 2 ? 355 : 260,  55, 16777215);
            this.font.draw(matrixStack, "PLAYER6: " + logic().currentPlayer[5],         tableID == 2 ? 355 : 260,  65, 16777215);
            this.font.draw(matrixStack, "TIMEOUT: " + logic().timeout,                  tableID == 2 ? 355 : 260,  75, 16777215);
            this.font.draw(matrixStack, "STATE:   " + logic().turnstate,                tableID == 2 ? 355 : 260,  85, 16777215);
            this.font.draw(matrixStack, "PLAYERS: " + logic().getFirstFreePlayerSlot(), tableID == 2 ? 355 : 260,  95, 16777215);
            this.font.draw(matrixStack, "ACTIVE:  " + logic().activePlayer,             tableID == 2 ? 355 : 260, 105, 16777215);
        }

        if(logic() instanceof LogicDummy) return;

        // Search for tokens in PlayerInventory
        if(playerToken == -1 && logic().turnstate < 4) validateBet();

        // Multiplayer Additional Player Join Button
        if(menu.logic().isMultiplayer() && menu.turnstate() == 2 && !isCurrentPlayer()){
            if(menu.logic().hasFreePlayerSlots()){
                drawFont(matrixStack, "BET:", 158, 246-2);
                this.itemRenderer.renderGuiItem(menu.getItemToken(), 158+20, 242-2);
                if(menu.getBettingLow() > 1) drawFont(matrixStack, "x" + menu.getBettingLow(), 158+50, 246-2);
            }
        }

        // Draw Start Screen Information (Card Table)
        if(menu.turnstate() == 0 && tableID != 0){
            if(tableID < 3){
                if(menu.hasToken() && menu.getBettingHigh() > 0) {
                    if(menu.getBettingLow() == menu.getBettingHigh()) {
                        drawFont(matrixStack, "The bet is:", 30, 100);
                        this.itemRenderer.renderGuiItem(menu.getItemToken(), 100, 96);
                        if(menu.getBettingLow() > 1) drawFont(matrixStack, "x" + menu.getBettingLow(), 124, 100);
                    } else {
                        drawFont(matrixStack, "The bets are:", 30, 100);
                        this.itemRenderer.renderGuiItem(menu.getItemToken(), 100, 96);
                        drawFont(matrixStack, "x" + menu.getBettingLow() + " to x" + menu.getBettingHigh(), 124, 100);
                    }

                    if(playerToken < menu.getBettingLow()) {
                        drawFont(matrixStack, "You don't have enough Token to play...", 30, 120);
                    } else {
                        drawFont(matrixStack, "Do you wish to play?", 30, 120);
                    }
                    if(menu.getBettingHigh() != menu.getBettingLow()) drawFont(matrixStack, "Your Bet:  " + bet, 30, 140);
                }
            } else {
                if(menu.hasToken() && menu.getBettingHigh() > 0){
                    this.font.draw(matrixStack, "INSERT ", 128, 210, 16777215);
                    this.itemRenderer.renderGuiItem(menu.getItemToken(), 160, 206);
                    if(menu.getBettingLow() > 1) this.font.draw(matrixStack, "x" + menu.getBettingLow(), 180, 210, 16777215);
                    this.font.draw(matrixStack, "Press ENTER", 128, 225, 16777215);
                }
            }

            // Draw Highscore (Card Table)
        } else if(menu.turnstate() == 7 && tableID != 0){
            for(int i = 0; i < 18; i++) {
                drawFont( matrixStack,           logic().scoreName[i],  40, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
                drawFontInvert(matrixStack, "" + logic().scoreHigh[i], 216, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
            }


            // Draw Start Screen Information (Arcade)
        } else if(menu.turnstate() == 0 && tableID == 0){
            if(menu.hasToken() && menu.getBettingHigh() > 0) {
                this.font.draw(matrixStack, "INSERT ", 90, 180, 16777215);
                this.itemRenderer.renderGuiItem(menu.getItemToken(), 126, 176);
                if(menu.getBettingLow() > 1) this.font.draw(matrixStack, "x" + menu.getBettingLow(), 145, 180, 16777215);
                if(playerToken < menu.getBettingLow()) {
                    this.font.draw(matrixStack, "NOT ENOUGH TOKEN", 80, 220, colour);
                } else {
                    this.font.draw(matrixStack, "Press ENTER", 95, 220, colour);
                }
            } else {
                this.font.draw(matrixStack, "Press ENTER", 95, 220, colour);
            }

            // Draw Highscore Information (Arcade)
        } else if(menu.turnstate() == 7 && tableID == 0){

            for(int i = 0; i < 20; i++) {
                drawFont(     matrixStack,       logic().scoreName[i],  40, 15 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
                drawFontInvert(matrixStack, "" + logic().scoreHigh[i], 216, 15 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
            }
            this.font.draw(matrixStack, "Press ENTER", 95, 220, colour);
        } else {

            drawGuiContainerForegroundLayerSUB(matrixStack, mouseX, mouseY);

            if((menu.turnstate() == 2 || menu.turnstate() == 3) && menu.logic().pause){
                this.font.draw(matrixStack, "PAUSE", 103, 200, 16777215);
                this.font.draw(matrixStack, "Press ENTER to FORFEIT", 65, 220, 16777215);
            }

            // Single Player Table GIVE UP Button
            if(showForfeit && showDebug){
                this.font.draw(matrixStack, "" + highlightTimer, 128-16-8-12+2, 245, 0);
            }

            if(menu.turnstate() == 5 && tableID == 0){
                this.font.draw(matrixStack, "GAME OVER", 103, 200, 16777215);
                this.font.draw(matrixStack, "Press ENTER", 95, 220, colour);
            }

            if(logic().turnstate == 4){ // ???
                gameOver();
            }
        }

        if(menu.getID() != logic().getID()){
            this.minecraft.player.closeContainer();
        }
    }

    /** Draws the background layer of this container (behind the items). */
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if(tableID == 0) { // Arcade Background


            // Dummy Arcade Screen
            if(logic() instanceof LogicDummy){
                Random RANDOM = new Random();
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_STATIC);
                for(int y = 0; y < 8; y++){
                    for(int x = 0; x < 6; x++){
                        this.blit(matrixStack, leftPos + 32 + 32*x, topPos + 32*y, 32*RANDOM.nextInt(8), 32*RANDOM.nextInt(8), 32, 32);
                    }
                }
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_GROUND_ARCADE);
                this.blit(matrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
            } else {

                // Normal Arcade Background
                RenderSystem.setShaderTexture(0, getParallaxTexture(true));
                this.blit(matrixStack, leftPos, topPos, 0, camera1, 256, 256);
                RenderSystem.setShaderTexture(0, getParallaxTexture(false));
                this.blit(matrixStack, leftPos, topPos, 0, camera0, 256, 256);
            }
        } else if(tableID < 3){ // Card Table Background
            RenderSystem.setShaderTexture(0, getBackground());
            if(tableID == 2){
                this.blit(matrixStack, leftPos-128+32, topPos,  0, 0, this.imageWidth-32, this.imageHeight); // Background Left
                this.blit(matrixStack, leftPos+128   , topPos, 32, 0, this.imageWidth-32, this.imageHeight); // Background Right
            } else {
                this.blit(matrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background
            }
        } else { // Slot Machine Background
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_SLOTMACHINE);
            this.blit(matrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background SMALL
        }


        // Draws Logo from ItemModule
        if(menu.turnstate() <= 1) { drawLogo(matrixStack); }


        // MiniGame Layer
        if(logic() instanceof LogicDummy) return;
        if(menu.turnstate() >= 1 && menu.turnstate() < 6){
            if(logic().pause) RenderSystem.setShaderColor(0.35F, 0.35F, 0.35F, 1.0F);
            drawGuiContainerBackgroundLayerSUB(matrixStack, partialTicks, mouseX, mouseY);
            if(isCurrentPlayer()) drawGuiContainerBackgroundLayerGUI(matrixStack, partialTicks, mouseX, mouseY);
            if(logic().pause) RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }


        // If NOT Ingame
        if((menu.turnstate() == 5 || menu.turnstate() == 0 || menu.turnstate() == 7) && (tableID == 1 || tableID == 2)){
            if(menu.turnstate() == 5 && logic().hasHighscore()){
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
                blit(matrixStack, leftPos+89, topPos+206, 0, 22, 78, 22); // Button Highscore
            } else
            if(menu.turnstate() == 5){
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
                blit(matrixStack, leftPos+89, topPos+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(menu.turnstate() == 7){
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
                blit(matrixStack, leftPos+89, topPos+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(!menu.hasToken() || playerToken >= menu.getBettingLow()){
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
                blit(matrixStack, leftPos+89, topPos+206, 78, 22, 78, 22); // Button New Game
            }
            if(menu.turnstate() == 0 && logic().hasHighscore()){
                blit(matrixStack, leftPos+89, topPos+166, 0, 22, 78, 22); // Button Highscore
            }
            if(menu.turnstate() == 0 && playerToken >= menu.getBettingLow()) {
                blit(matrixStack, leftPos +  58, topPos + 206, 234, 88 + (highlightIndex == 1 ? 22 : 0) + (bet == menu.getBettingLow()  ? 44 : 0), 22, 22); // Button Minus
                blit(matrixStack, leftPos + 176, topPos + 206, 234,      (highlightIndex == 2 ? 22 : 0) + (bet == menu.getBettingHigh() ? 44 : 0), 22, 22); // Button Plus
            }
        }


        // Multiplayer Additional Player Join Button
        if(menu.logic().isMultiplayer() && menu.turnstate() == 2 && !isCurrentPlayer()){
            if(menu.logic().hasFreePlayerSlots()){
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
                this.blit(matrixStack, leftPos + 153, topPos + 237, 78, 220, 78, 22);
                if(!menu.hasToken() || playerToken >= menu.getBettingLow()){
                    blit(matrixStack, leftPos + 26, topPos + 237, 0, 220, 78, 22);
                }
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_DICE);
                this.blit(matrixStack, leftPos + 128-16, topPos + 232, 192, 32 + 32*menu.logic().getFirstFreePlayerSlot(), 32, 32);
            }
        }


        // Single Player Table GIVE UP Button
        if(!menu.logic().isMultiplayer() && menu.turnstate() == 2 && (tableID == 1 || tableID == 2)){
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
            this.blit(matrixStack, leftPos + (tableID == 1 ? 241 : 241+64+32), topPos + 241, showForfeit ? 14 : 0, 242, 14, 14);
            if(showForfeit){
                this.blit(matrixStack, leftPos + 128-39, topPos + 241, 28, 242, 78, 14);
            }
        }


        // Draws Arcade Border
        if(tableID == 0) {
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_GROUND_ARCADE);
            this.blit(matrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
            int shift2 = menu.turnstate() == 2 || menu.turnstate() == 3 ? 2 : 1;
            if(menu.turnstate() != 5 && !menu.logic().pause) camera1 = (camera1 + shift2)   % 256;
            if(menu.turnstate() != 5 && !menu.logic().pause) camera0 = (camera0 + shift2*2) % 256;
        }


        // Multiplayer Status
        if((logic().turnstate == 2 || logic().turnstate == 3) && logic().isMultiplayer()){
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_DICE);
            for(int i = 0; i < logic().getFirstFreePlayerSlot(); i++){
                this.blit(matrixStack, leftPos+(tableID == 2 ? 355-15 : 260-15), topPos+32 + 36*i, 224, 32 + 32 * i, 32, 32);
            }
            if(logic().activePlayer < logic().getFirstFreePlayerSlot()){
                this.blit(matrixStack, leftPos+(tableID == 2 ? 355-15 : 260-15), topPos+32 + 36*logic().activePlayer, 224-32, 32 + 32 * logic().activePlayer, 32, 32);
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
        CasinoPacketHandler.sendToServer(new MessageScoreServer(this.inventory.player.getName().getString(), logic().scorePoint, menu.pos()));
        int lastScore = 0;
        int prizeSET = 0;
        int prizeCON = 0;
        if(menu.getPrizeCount1() > 0){
            if(menu.getPrizeMode1()){
                if(menu.getPrizeScore1() <= menu.logic().scorePoint && menu.getPrizeScore1() > lastScore){
                    lastScore = menu.getPrizeScore1();
                    prizeSET  = menu.getPrizeCount1();
                }
            } else {
                if(menu.getPrizeScore1() > 0) prizeCON += menu.getPrizeCount1() * (menu.logic().scorePoint / menu.getPrizeScore1());
            }
        }

        if(menu.getPrizeCount2() > 0){
            if(menu.getPrizeMode2()){
                if(menu.getPrizeScore2() <= menu.logic().scorePoint && menu.getPrizeScore2() > lastScore){
                    lastScore = menu.getPrizeScore2();
                    prizeSET  = menu.getPrizeCount2();
                }
            } else {
                if(menu.getPrizeScore2() > 0) prizeCON += menu.getPrizeCount2() * (menu.logic().scorePoint / menu.getPrizeScore2());
            }
        }

        if(menu.getPrizeCount3() > 0){
            if(menu.getPrizeMode3()){
                if(menu.getPrizeScore3() <= menu.logic().scorePoint && menu.getPrizeScore3() > lastScore){
                    lastScore = menu.getPrizeScore3();
                    prizeSET  = menu.getPrizeCount3();
                }
            } else {
                if(menu.getPrizeScore3() > 0) prizeCON += menu.getPrizeCount3() * (menu.logic().scorePoint / menu.getPrizeScore3());
            }
        }

        payPrize(prizeSET + prizeCON);

    }

    /** Draws Colored Card Table Background **/
    private ResourceLocation getBackground(){
        if(menu.color == DyeColor.BLACK)      return CasinoKeeper.TEXTURE_GROUND_BLACK;
        if(menu.color == DyeColor.BLUE)       return CasinoKeeper.TEXTURE_GROUND_BLUE;
        if(menu.color == DyeColor.BROWN)      return CasinoKeeper.TEXTURE_GROUND_BROWN;
        if(menu.color == DyeColor.CYAN)       return CasinoKeeper.TEXTURE_GROUND_CYAN;
        if(menu.color == DyeColor.GRAY)       return CasinoKeeper.TEXTURE_GROUND_GRAY;
        if(menu.color == DyeColor.GREEN)      return CasinoKeeper.TEXTURE_GROUND_GREEN;
        if(menu.color == DyeColor.LIGHT_BLUE) return CasinoKeeper.TEXTURE_GROUND_LIGHT_BLUE;
        if(menu.color == DyeColor.LIME)       return CasinoKeeper.TEXTURE_GROUND_LIME;
        if(menu.color == DyeColor.MAGENTA)    return CasinoKeeper.TEXTURE_GROUND_MAGENTA;
        if(menu.color == DyeColor.ORANGE)     return CasinoKeeper.TEXTURE_GROUND_ORANGE;
        if(menu.color == DyeColor.PINK)       return CasinoKeeper.TEXTURE_GROUND_PINK;
        if(menu.color == DyeColor.PURPLE)     return CasinoKeeper.TEXTURE_GROUND_PURPLE;
        if(menu.color == DyeColor.RED)        return CasinoKeeper.TEXTURE_GROUND_RED;
        if(menu.color == DyeColor.LIGHT_GRAY) return CasinoKeeper.TEXTURE_GROUND_LIGHT_GRAY;
        if(menu.color == DyeColor.WHITE)      return CasinoKeeper.TEXTURE_GROUND_WHITE;
        if(menu.color == DyeColor.YELLOW)     return CasinoKeeper.TEXTURE_GROUND_YELLOW;

        return CasinoKeeper.TEXTURE_GROUND_GRAY;
    }

    /** Scans the PlayerInventory for Tokens **/
    protected void validateBet(){ // ???
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
        if(menu.hasToken()){
            balance -= bet;
            SystemInventory.decreaseInventory(inventory, menu.getItemToken(), bet);
            {
                int i = 0;
                ItemStack itemStack = ItemStack.EMPTY;
                Predicate<ItemStack> p_195408_1_ = Predicate.isEqual(menu.getItemToken());
                int count = bet;

                for(int j = 0; j < inventory.getContainerSize(); ++j) {
                    ItemStack itemstack = inventory.getItem(j);
                    if (!itemstack.isEmpty() && p_195408_1_.test(itemstack)) {
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

                if (!itemStack.isEmpty() && p_195408_1_.test(itemStack)) {
                    int l = count <= 0 ? itemStack.getCount() : Math.min(count - i, itemStack.getCount());
                    if (count != 0) { itemStack.shrink(l); }
                }
            }
            CasinoPacketHandler.sendToServer(new MessagePlayerServer(menu.getItemToken().getItem(), -bet));
            if(!menu.getSettingInfiniteToken()) {
                menu.setStorageToken(menu.getStorageToken() + bet);
                sendMessageBlock();
            }
        }
    }

    /** Pays the Reward to the Player **/
    private void payBet(int multi){ // ???
        if(multi <= 0) return;
        balance += bet * multi;
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
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count2));
            } else {
                Item item = menu.getItemToken().getItem();
                int count = bet * multi; // multi == 1 -> Return bet
                inventory.add(new ItemStack(item, count));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count));
            }
        }
    }

    /** Pays the Prize to the Player **/
    private void payPrize(int amount){ // ???
        if(amount <= 0) return;
        if(menu.hasReward()){
            if(!menu.getSettingInfinitePrize()) {
                Item item = menu.getItemPrize().getItem();
                int count = amount; // multi == 1 -> Return bet
                int count2 = 0;

                count2 = Math.min(menu.getStoragePrize(), count);

                menu.setStoragePrize(menu.getStoragePrize() - count);

                if(menu.getStoragePrize() <= 0) {
                    menu.setStoragePrize(0);
                    menu.setItemPrize(new ItemStack(Blocks.AIR));
                }
                sendMessageBlock();

                inventory.add(new ItemStack(item, count2));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count2));
            } else {
                Item item = menu.getItemPrize().getItem();
                int count = amount; // multi == 1 -> Return bet
                inventory.add(new ItemStack(item, count));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count));
            }
        }
    }

    protected void action(int action){
        CasinoPacketHandler.sendToServer(new MessageStateServer(false, action, menu.pos()));
    }

    protected void start(){
        Random r = new Random();
        CasinoPacketHandler.sendToServer(new MessageStartServer(inventory.player.getName().getString(), r.nextInt(1000000), menu.pos()));
    }

    private void reset(){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, 0, menu.pos()));
    }

    protected void turnstate(int state){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, state, menu.pos()));
    }

    private void addNewPlayer(){
        CasinoPacketHandler.sendToServer(new MessageStartServer(inventory.player.getName().getString(), -1, menu.pos()));
    }

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
        }
        return false;
    }

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
        }
        return false;
    }

    protected int getPlayerPosition(){
        if(menu.logic().isMultiplayer()){
            for(int i = 0; i < 6; i++){
                if(menu.getCurrentPlayer(i).matches(inventory.player.getName().getString())){
                    return i;
                }
            }
        } else {
            return menu.getCurrentPlayer(0).matches(inventory.player.getName().getString()) ? 0 : -1;
        }
        return -1;
    }

    protected void drawBalance(PoseStack matrixStack){
        //drawFont(matrixStack, "Balance: " + balance, tableID == 1 ? 0 : -64, 256 - 16, grayscale);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFont(PoseStack matrixStack, String text, int x, int y){
        drawFont(matrixStack, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFont(PoseStack matrixStack, String text, int x, int y, int color){
        this.font.draw(matrixStack, text,  x+1, y+1, 0);
        this.font.draw(matrixStack, text,  x+0, y+0, color);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontInvert(PoseStack matrixStack, String text, int x, int y){
        drawFontInvert(matrixStack, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontInvert(PoseStack matrixStack, String text, int x, int y, int color){
        int w = this.font.width(text);
        this.font.draw(matrixStack, text,  x+1 - w, y+1, 0);
        this.font.draw(matrixStack, text,  x+0 - w, y+0, color);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontCenter(PoseStack matrixStack, String text, int x, int y){
        drawFontCenter(matrixStack, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontCenter(PoseStack matrixStack, String text, int x, int y, int color){
        int w = this.font.width(text);
        this.font.draw(matrixStack, text,  x+1 - w/2, y+1, 0);
        this.font.draw(matrixStack, text,  x+0 - w/2, y+0, color);
    }

    /** Draws a Card **/
    public void drawCard(PoseStack matrixStack, int posX, int posY, Card card){
        if(card.suit == -1) return;
        if(card.hidden){
            RenderSystem.setShaderTexture(0, getCardsTexture(true));
        } else {
            if(card.suit <= 1) RenderSystem.setShaderTexture(0, getCardsTexture(false));
            if(card.suit >= 2) RenderSystem.setShaderTexture(0, getCardsTexture(true));
        }
        int texX = card.suit == -1 || card.hidden ? 0 : card.number % 8;
        int texY = card.suit == -1 || card.hidden ? 4 : (card.suit % 2) * 2 + card.number / 8;
        if(Config.CONFIG.config_animated_cards.get() && !card.hidden){
            if(card.number >= 10){
                if(logic().frame == card.suit*12 + (card.number-10)*3){
                    texX += 3;
                }
            }
        }
        blit(matrixStack, leftPos + posX + card.shiftX, topPos + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
    }

    /** Draws the Backside of a Card (also used for highlighter) **/
    public void drawCardBack(PoseStack matrixStack, int posX, int posY, int color){
        if(color <= 6) RenderSystem.setShaderTexture(0, getCardsTexture(true));
        else           RenderSystem.setShaderTexture(0, getCardsTexture(false));
        blit(matrixStack, leftPos + posX, topPos + posY, (color%7) * 32, 4 * 48, 32, 48);
    }

    private void drawLetter(PoseStack matrixStack, char c, int posX, int posY, int sizeX, int sizeY){
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
    private void drawLogo(PoseStack matrixStack) {
        int sizeX = 0;
        String[] logo = getGameName().split("_");
        if(tableID <= 2){
            if(tableID == 0){
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_FONT_ARCADE);
                sizeX = 16;
            } else if(tableID == 1 || tableID == 2){
                RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_FONT_CARDTABLE);
                sizeX = 32;
            }

            for(int i = 0; i < logo.length; i++){
                for(int k = 0; k < logo[i].length(); k++){
                    drawLetter(matrixStack, logo[i].charAt(k), leftPos + 128 - logo[i].length()*(sizeX/2) + sizeX*k, topPos + 32 + 32*i, 32, 32);
                }
            }
        }
    }

    protected void drawMino(PoseStack matrixStack, int posX, int posY, int idX, int idY){
        this.blit(matrixStack, leftPos + posX, topPos + posY, 24 * idX, 24 * idY, 24, 24);
    }

    protected void drawMino(PoseStack matrixStack, int posX, int posY){
        drawMino(matrixStack, posX, posY, 0, 0);
    }

    protected void drawMinoSmall(PoseStack matrixStack, int posX, int posY, int id, boolean alternate){
        if(alternate){
            this.blit(matrixStack, leftPos + posX, topPos + posY, 240, 16 * id, 16, 16);
        } else {
            this.blit(matrixStack, leftPos + posX, topPos + posY, 16 * id, 240, 16, 16);
        }
    }

    protected void drawMinoSmall(PoseStack matrixStack, int posX, int posY){
        drawMinoSmall(matrixStack, posX, posY, 0, false);
    }

    protected void drawDigi(PoseStack matrixStack, int posX, int posY, int idX, int idY){
        this.blit(matrixStack, leftPos + posX, topPos + posY, 16 * idX, 16 + 16 * idY, 16, 16);
    }

    protected void drawDigi(PoseStack matrixStack, int posX, int posY){
        drawDigi(matrixStack, posX, posY, 0, 0);
    }

    protected void drawDigiSmall(PoseStack matrixStack, int posX, int posY, int id){
        this.blit(matrixStack, leftPos + posX    , topPos + posY    , 16 * id     , 16   , 6, 6);
        this.blit(matrixStack, leftPos + posX + 6, topPos + posY    , 16 * id + 10, 16   , 6, 6);
        this.blit(matrixStack, leftPos + posX    , topPos + posY + 6, 16 * id     , 16+10, 6, 6);
        this.blit(matrixStack, leftPos + posX + 6, topPos + posY + 6, 16 * id + 10, 16+10, 6, 6);

    }

    protected void drawDigiSmall(PoseStack matrixStack, int posX, int posY){
        drawDigiSmall(matrixStack, posX, posY, 0);
    }

    protected void drawDigiSymbol(PoseStack matrixStack, int posX, int posY, int id){
        this.blit(matrixStack, leftPos + posX, topPos + posY, 16 * id, 0, 16, 16);
    }

    protected void drawDigiSymbol(PoseStack matrixStack, int posX, int posY){
        drawDigiSymbol(matrixStack, posX, posY, 0);
    }

    protected void drawButton(PoseStack matrixStack, int posX, int posY, int id){

    }

    protected void drawShip(PoseStack matrixStack, Ship ship, int shipID, int lookDirection, boolean animate){
        int frame = logic().turnstate < 4 && animate ? (logic().frame % 12) / 2 : 0;
        if(frame == 4) frame = 2;
        if(frame == 5) frame = 1;
        int direction = lookDirection == -1 ? ship.getLookDirection() : lookDirection;
        this.blit(matrixStack, leftPos + 32 + ship.getPos().X, topPos + 8 + ship.getPos().Y, 64*(shipID%4) + 16*frame, 128 + direction*16 + (shipID/4)*64, 16, 16);
    }

    protected void drawShip(PoseStack matrixStack, Vector2 vec, int shipID){
        int frame = logic().turnstate < 4 ? (logic().frame % 12) / 2 : 0;
        if(frame == 4) frame = 2;
        if(frame == 5) frame = 1;
        this.blit(matrixStack, leftPos + 32 + vec.X*16, topPos + 8 + vec.Y*16, 64*(shipID%4) + 16*frame, 128 + (shipID/4)*64, 16, 16);
    }

    private ResourceLocation getParallaxTexture(boolean lowTexture){
        switch(menu.getSettingAlternateColor()){
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
        switch(menu.getSettingAlternateColor()){
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
        inv.set(0, menu.container.getItem(0));
        inv.set(1, menu.container.getItem(1));
        inv.set(2, menu.container.getItem(2));
        inv.set(3, menu.container.getItem(3));
        inv.set(4, menu.container.getItem(4));
        CasinoPacketHandler.sendToServer(new MessageInventoryServer(inv, menu.getStorageToken(), menu.getStoragePrize(), menu.pos()));
    }





    //----------------------------------------ABSTRACT----------------------------------------//

    protected abstract void mouseClickedSUB(double mouseX, double mouseY, int mouseButton);
    protected abstract void drawGuiContainerForegroundLayerSUB(PoseStack matrixStack, int mouseX, int mouseY);
    protected abstract void drawGuiContainerBackgroundLayerSUB(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY);
    protected abstract void drawGuiContainerBackgroundLayerGUI(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY);
    protected abstract String getGameName();



}

