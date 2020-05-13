package mod.casinocraft.screen;

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
    protected boolean colourUP = true;

    private final int grayscale = 16777215;

    protected int camera1 = 0;
    protected int camera0 = 0;
    protected int shift = 1;
    protected int intro = 256;




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
    private void keyTyped(int keyCode){
        if(isCurrentPlayer()) keyTyped2(keyCode);

        if(tableID == 0){
            // Collect Token and start game (Arcade Version) / FROM: Start Screen
            if(CONTAINER.turnstate() == 0 && tableID == 0 && keyCode == KEY_ENTER) {
                if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBetLow()) {
                    if(CONTAINER.hasToken()) collectBet();
                    start();
                    shift = 2;
                }
            } else {
                // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
                if(CONTAINER.turnstate() == 7 && tableID == 0 && keyCode == KEY_ENTER) {
                    camera1 = 0;
                    camera0 = 0;
                    shift = 1;
                    intro = 256;
                    reset();
                } else {
                    // Collect Token and start game (Arcade Version) / FROM: GameOver Screen
                    if(CONTAINER.turnstate() == 5 && tableID == 0 && keyCode == KEY_ENTER) {
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
            if(CONTAINER.turnstate() == 2 && tableID == 0 && keyCode == KEY_SPACE){
                CasinoPacketHandler.sendToServer(new MessageStateServer(true, -1, CONTAINER.getPos()));
            }
        } else if(tableID == 3){
            // Collect Token and start game (Arcade Version) / FROM: Start Screen
            if(CONTAINER.turnstate() == 0 && keyCode == KEY_ENTER) {
                if(playerToken >= CONTAINER.getBetLow()) {
                    collectBet();
                    start();
                }
            } else {
                // Collect Token and start game (Arcade Version) / FROM: Highscore Screen
                if(CONTAINER.turnstate() == 5 && keyCode == KEY_ENTER) {
                    reset();
                }
            }

            // Toggle Pause Mode
            if(CONTAINER.turnstate() == 2 && tableID == 0 && keyCode == KEY_SPACE){
                CasinoPacketHandler.sendToServer(new MessageStateServer(true, -1, CONTAINER.getPos()));
            }
        }



        // Close Screen Command
        if (keyCode == KEY_ESCAPE){
            //if(CONTAINER..world.getBlockState(CONTAINER.getPos()).getBlock() instanceof BlockArcade) {
            //    BlockArcade block = (BlockArcade) CONTAINER.getWorld().getBlockState(CONTAINER.getPos()).getBlock();
            //    block.setPowerState(CONTAINER.inventory.get(1).getItem(), CONTAINER.getPos());
            //}
            this.minecraft.player.closeScreen();
        }
    }

    /** Called when the mouse is clicked. */
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
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
        return false;
    }




    //----------------------------------------DRAW----------------------------------------//

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        //this.renderHoveredToolTip(mouseX, mouseY);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){

        // Debug Info (shown if Advanced Tooltips are enabled)
        if(this.minecraft.gameSettings.advancedItemTooltips){

            this.font.drawString("PLAYER1: " + logic().currentPlayer[0],         tableID == 2 ? 355 : 260,  15, 16777215);
            this.font.drawString("PLAYER2: " + logic().currentPlayer[1],         tableID == 2 ? 355 : 260,  25, 16777215);
            this.font.drawString("PLAYER3: " + logic().currentPlayer[2],         tableID == 2 ? 355 : 260,  35, 16777215);
            this.font.drawString("PLAYER4: " + logic().currentPlayer[3],         tableID == 2 ? 355 : 260,  45, 16777215);
            this.font.drawString("PLAYER5: " + logic().currentPlayer[4],         tableID == 2 ? 355 : 260,  55, 16777215);
            this.font.drawString("PLAYER6: " + logic().currentPlayer[5],         tableID == 2 ? 355 : 260,  65, 16777215);
            this.font.drawString("TIMEOUT: " + logic().timeout,                  tableID == 2 ? 355 : 260,  75, 16777215);
            this.font.drawString("STATE:   " + logic().turnstate,                tableID == 2 ? 355 : 260,  85, 16777215);
            this.font.drawString("PLAYERS: " + logic().getFirstFreePlayerSlot(), tableID == 2 ? 355 : 260,  95, 16777215);
            this.font.drawString("ACTIVE:  " + logic().activePlayer,             tableID == 2 ? 355 : 260, 105, 16777215);

        //    this.font.drawString("turnstate:     " + logic().turnstate,                             tableID == 2 ? 355 : 260,  15, 16777215);
        //    this.font.drawString("table:         " + logic().tableID,                               tableID == 2 ? 355 : 260,  25, 16777215);
        //    this.font.drawString("points:        " + logic().scorePoint,                            tableID == 2 ? 355 : 260,  35, 16777215);
        //    this.font.drawString("points:        " + this.container.getBetHigh(),                   tableID == 2 ? 355 : 260,  35, 16777215);
        //    this.font.drawString("level:         " + logic().scoreLevel,                            tableID == 2 ? 355 : 260,  45, 16777215);
        //    this.font.drawString("lives:         " + logic().scoreLives,                            tableID == 2 ? 355 : 260,  55, 16777215);
        //    this.font.drawString("hand:          " + logic().hand,                                  tableID == 2 ? 355 : 260,  65, 16777215);
        //    this.font.drawString("reward:        " + logic().reward,                                tableID == 2 ? 355 : 260,  75, 16777215);
        //    this.font.drawString("selector:      " + logic().selector.X + ":" + logic().selector.Y, tableID == 2 ? 355 : 260,  85, 16777215);
        //    this.font.drawString("tileentity:    " + CONTAINER.inventory.toString().substring(27),  tableID == 2 ? 355 : 260,  95, 16777215);
        //    this.font.drawString("playertoken:   " + playerToken,                                   tableID == 2 ? 355 : 260, 105, 16777215);
        //    this.font.drawString("boardToken:    " + CONTAINER.getBetStorage(),                     tableID == 2 ? 355 : 260, 115, 16777215);
        //    this.font.drawString("bet low:       " + CONTAINER.getBetLow(),                         tableID == 2 ? 355 : 260, 125, 16777215);
        //    this.font.drawString("bet high:      " + CONTAINER.getBetHigh(),                        tableID == 2 ? 355 : 260, 135, 16777215);
        //    this.font.drawString("bet player:    " + bet,                                           tableID == 2 ? 355 : 260, 145, 16777215);
        //    this.font.drawString("is creative:   " + CONTAINER.isCreative(),                        tableID == 2 ? 355 : 260, 155, 16777215);
        //    this.font.drawString("logic:         " + CONTAINER.logic().toString().substring(27),    tableID == 2 ? 355 : 260, 165, 16777215);
        //    this.font.drawString("score last:    " + logic().scoreLast,                             tableID == 2 ? 355 : 260, 175, 16777215);
        //    this.font.drawString("has highscore: " + logic().hasHighscore(),                        tableID == 2 ? 355 : 260, 185, 16777215);
        //    this.font.drawString("has token:     " + CONTAINER.hasToken(),                          tableID == 2 ? 355 : 260, 195, 16777215);
        //    this.font.drawString("board:         " + CONTAINER.toString().substring(27),            tableID == 2 ? 355 : 260, 205, 16777215);
        //    this.font.drawString("Player:        " + CONTAINER.getCurrentPlayer(),                  tableID == 2 ? 355 : 260, 215, 16777215);
        //    this.font.drawString("Table:         " + CONTAINER.tableID,                             tableID == 2 ? 355 : 260, 235, 16777215);
        }

        if(logic() instanceof LogicDummy) return;

        // Search for tokens in PlayerInventory
        if(playerToken == -1 && logic().turnstate < 4) validateBet();

        // Multiplayer Additional Player Join Button
        if(CONTAINER.logic().isMultiplayer() && CONTAINER.turnstate() == 2 && !isCurrentPlayer()){
            if(CONTAINER.logic().hasFreePlayerSlots()){
                drawFont("BET:", 158, 246-2);
                this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 158+20, 242-2);
                if(CONTAINER.getBetLow() > 1) drawFont("x" + CONTAINER.getBetLow(), 158+50, 246-2);
            }
        }

        // Draw Start Screen Information (Card Table)
        if(CONTAINER.turnstate() == 0 && tableID != 0){
            if(tableID < 3){
                if(CONTAINER.hasToken() && CONTAINER.getBetHigh() > 0) {
                    if(CONTAINER.getBetLow() == CONTAINER.getBetHigh()) {
                        drawFont("The bet is:", 30, 100);
                        this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 100, 96);
                        if(CONTAINER.getBetLow() > 1) drawFont("x" + CONTAINER.getBetLow(), 124, 100);
                    } else {
                        drawFont("The bets are:", 30, 100);
                        this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 100, 96);
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
                    this.font.drawString("INSERT ", 128, 210, 16777215);
                    this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 160, 206);
                    if(CONTAINER.getBetLow() > 1) this.font.drawString("x" + CONTAINER.getBetLow(), 180, 210, 16777215);
                    this.font.drawString("Press ENTER", 128, 225, 16777215);
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
                drawFont(           logic().scoreName[i],  40, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
                drawFontInvert("" + logic().scoreHigh[i], 216, 25 + 10*i, logic().scoreLast == i ? grayscale/2 : grayscale);
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

        if(CONTAINER.getID() != logic().getID()){
            this.minecraft.player.closeScreen();
        }
    }

    /** Draws the background layer of this container (behind the items). */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(tableID == 0) { // Arcade Background
            if(logic() instanceof LogicDummy){
                Random RANDOM = new Random();
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_STATIC);
                for(int y = 0; y < 8; y++){
                    for(int x = 0; x < 6; x++){
                        this.blit(guiLeft + 32 + 32*x, guiTop + 32*y, 32*RANDOM.nextInt(8), 32*RANDOM.nextInt(8), 32, 32);
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
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTMACHINE);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
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
            if(isCurrentPlayer()) drawGuiContainerBackgroundLayer3(partialTicks, mouseX, mouseY);
            if(logic().pause) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        // If NOT Ingame
        if((CONTAINER.turnstate() == 5 || CONTAINER.turnstate() == 0 || CONTAINER.turnstate() == 7) && (tableID == 1 || tableID == 2) && !(logic() instanceof LogicDummy)){
            if(CONTAINER.turnstate() == 5 && logic().hasHighscore()){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                blit(guiLeft+89, guiTop+206, 0, 22, 78, 22); // Button Highscore
            } else
            if(CONTAINER.turnstate() == 5){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                blit(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(CONTAINER.turnstate() == 7){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                blit(guiLeft+89, guiTop+206, 78*2, 22, 78, 22); // Button Finish
            } else
            if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBetLow()){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                blit(guiLeft+89, guiTop+206, 78, 22, 78, 22); // Button New Game
            }
            if(CONTAINER.turnstate() == 0 && logic().hasHighscore()){
                blit(guiLeft+89, guiTop+166, 0, 22, 78, 22); // Button Highscore
            }
            if(CONTAINER.turnstate() == 0 && playerToken >= CONTAINER.getBetLow()) {
                if(bet > CONTAINER.getBetLow())  blit(guiLeft+82-26+2, guiTop+204+2, 234, 22, 22, 22); // Button Minus
                if(bet < CONTAINER.getBetHigh()) blit(guiLeft+82+92+2, guiTop+204+2, 234, 44, 22, 22); // Button Plus
            }
        }

        // Multiplayer Additional Player Join Button
        if(CONTAINER.logic().isMultiplayer() && CONTAINER.turnstate() == 2 && !isCurrentPlayer()){
            if(CONTAINER.logic().hasFreePlayerSlots()){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
                this.blit(guiLeft + 153, guiTop + 237, 78, 220, 78, 22);
                if(!CONTAINER.hasToken() || playerToken >= CONTAINER.getBetLow()){
                    blit(guiLeft + 26, guiTop + 237, 0, 220, 78, 22);
                }
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
                this.blit(guiLeft + 128-16, guiTop + 232, 192, 32 + 32*CONTAINER.logic().getFirstFreePlayerSlot(), 32, 32);
            }
        }

        // Draws Arcade Border
        if(tableID == 0) {
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_GROUND_ARCADE);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);

            if(CONTAINER.turnstate() != 5) camera1 = (camera1 + shift)   % 256;
            if(CONTAINER.turnstate() != 5) camera0 = (camera0 + shift*2) % 256;
        }

        // Multiplayer Status
        if((logic().turnstate == 2 || logic().turnstate == 3) && logic().isMultiplayer()){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
            for(int i = 0; i < logic().getFirstFreePlayerSlot(); i++){
                this.blit(guiLeft+(tableID == 2 ? 355-15 : 260-15), guiTop+32 + 36*i, 224, 32 + 32 * i, 32, 32);
            }
            if(logic().activePlayer < logic().getFirstFreePlayerSlot()){
                this.blit(guiLeft+(tableID == 2 ? 355-15 : 260-15), guiTop+32 + 36*logic().activePlayer, 224-32, 32 + 32 * logic().activePlayer, 32, 32);
            }
        }

    }




    //----------------------------------------SUPPORT----------------------------------------//

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
                CasinoPacketHandler.sendToServer(new MessageScoreServer(PLAYER.player.getName().getString(), logic().scorePoint, CONTAINER.getPos()));
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
            InventoryUtil.decreaseInventory(PLAYER, CONTAINER.getToken(), bet);
            PLAYER.clearMatchingItems(Predicate.isEqual(CONTAINER.getToken()), bet);
            CasinoPacketHandler.sendToServer(new MessagePlayerServer(CONTAINER.getToken().getItem(), 0, -bet));
            if(!CONTAINER.isCreative()) {
                CONTAINER.setBetStorage(CONTAINER.getBetStorage() + bet);
                CasinoPacketHandler.sendToServer(new MessageBlockServer(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));
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
                CasinoPacketHandler.sendToServer(new MessageBlockServer(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));

                PLAYER.addItemStackToInventory(new ItemStack(item, count2));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, 0, count2));
            } else {
                Item item = CONTAINER.getToken().getItem();
                int count = bet * multi; // multi == 1 -> Return bet
                PLAYER.addItemStackToInventory(new ItemStack(item, count));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, 0, count));
            }
        }
    }

    protected void action(int action){
        CasinoPacketHandler.sendToServer(new MessageStateServer(false, action, CONTAINER.getPos()));
    }

    protected void start(){
        Random r = new Random();
        CasinoPacketHandler.sendToServer(new MessageStartServer(PLAYER.player.getName().getString(), r.nextInt(1000000), CONTAINER.getPos()));
    }

    private void reset(){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, 0, CONTAINER.getPos()));
    }

    protected void turnstate(int state){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, state, CONTAINER.getPos()));
    }

    private void addNewPlayer(){
        CasinoPacketHandler.sendToServer(new MessageStartServer(PLAYER.player.getName().getString(), -1, CONTAINER.getPos()));
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
    protected void drawFont(String text, int x, int y){
        drawFont(text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFont(String text, int x, int y, int color){
        this.font.drawString(text,  x+1, y+1, 0);
        this.font.drawString(text,  x+0, y+0, color);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontInvert(String text, int x, int y){
        drawFontInvert(text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontInvert(String text, int x, int y, int color){
        int w = this.font.getStringWidth(text);
        this.font.drawString(text,  x+1 - w, y+1, 0);
        this.font.drawString(text,  x+0 - w, y+0, color);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontCenter(String text, int x, int y){
        drawFontCenter(text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontCenter(String text, int x, int y, int color){
        int w = this.font.getStringWidth(text);
        this.font.drawString(text,  x+1 - w/2, y+1, 0);
        this.font.drawString(text,  x+0 - w/2, y+0, color);
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
        if(CasinoKeeper.config_animated_cards.get() && !card.hidden){
            if(card.number >= 10){
                if(logic().frame == card.suit*12 + (card.number-10)*3){
                    texX += 3;
                }
            }
        }
        blit(guiLeft + posX + card.shiftX, guiTop + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
    }

    /** Draws the Backside of a Card (also used for highlighter) **/
    public void drawCardBack(int posX, int posY, int color){
        if(color <= 6) this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        else           this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
        blit(guiLeft + posX, guiTop + posY, (color%7) * 32, 4 * 48, 32, 48);
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
        String[] logo = getGameName().split("_");
        if(tableID <= 2){
            if(tableID == 0){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_ARCADE);
                sizeX = 16;
            } else if(tableID == 1 || tableID == 2){
                this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
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
        this.blit(guiLeft + posX, guiTop + posY, 24 * idX, 24 * idY, 24, 24);
    }

    protected void drawMino(int posX, int posY){
        drawMino(posX, posY, 0, 0);
    }

    protected void drawMinoSmall(int posX, int posY, int id, boolean alternate){
        if(alternate){
            this.blit(guiLeft + posX, guiTop + posY, 240, 16 * id, 16, 16);
        } else {
            this.blit(guiLeft + posX, guiTop + posY, 16 * id, 240, 16, 16);
        }
    }

    protected void drawMinoSmall(int posX, int posY){
        drawMinoSmall(posX, posY, 0, false);
    }

    protected void drawDigi(int posX, int posY, int idX, int idY){
        this.blit(guiLeft + posX, guiTop + posY, 16 * idX, 16 + 16 * idY, 16, 16);
    }

    protected void drawDigi(int posX, int posY){
        drawDigi(posX, posY, 0, 0);
    }

    protected void drawDigiSmall(int posX, int posY, int id){
        this.blit(guiLeft + posX, guiTop + posY, 2 + 16 * id, 2, 12, 12);
    }

    protected void drawDigiSmall(int posX, int posY){
        drawDigiSmall(posX, posY, 0);
    }

    protected void drawButton(int posX, int posY, int id){

    }

    protected void drawShip(Ship ship, int shipID, boolean hasLookDirection, boolean animate){
        int frame = logic().turnstate < 4 && animate ? (logic().frame % 12) / 2 : 0;
        if(frame == 4) frame = 2;
        if(frame == 5) frame = 1;
        int direction = hasLookDirection ? ship.Get_LookDirection() : 0;
        this.blit(guiLeft + 32 + ship.Get_Pos().X, guiTop + 8 + ship.Get_Pos().Y, 64*(shipID%4) + 16*frame, 128 + direction*16 + (shipID/4)*64, 16, 16);
    }

    protected void drawShip(Vector2 vec, int shipID){
        int frame = logic().turnstate < 4 ? (logic().frame % 12) / 2 : 0;
        if(frame == 4) frame = 2;
        if(frame == 5) frame = 1;
        this.blit(guiLeft + 32 + vec.X*16, guiTop + 8 + vec.Y*16, 64*(shipID%4) + 16*frame, 128 + (shipID/4)*64, 16, 16);
    }




    //----------------------------------------OVERRIDES----------------------------------------//

    protected abstract void keyTyped2(int keyCode);
    protected abstract void mouseClicked2(double mouseX, double mouseY, int mouseButton);
    protected abstract void drawGuiContainerForegroundLayer2(int mouseX, int mouseY);
    protected abstract void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY);
    protected abstract void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY);
    protected abstract String getGameName();

}

