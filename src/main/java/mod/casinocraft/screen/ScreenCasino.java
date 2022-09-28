package mod.casinocraft.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.casinocraft.Config;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.network.*;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.util.ButtonSet;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.Ship;
import mod.lucky77.screen.ScreenBase;
import mod.lucky77.util.Vector2;
import net.minecraft.block.Blocks;
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
import static mod.casinocraft.util.ButtonMap.*;

public abstract class ScreenCasino extends ScreenBase<ContainerCasino> {

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
    private final PlayerInventory inventory;

    protected ButtonSet buttonSet = new ButtonSet();





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCasino(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name, 256, 256);
        this.tableID = container.tableID;
        inventory = player;
        if(this.tableID == 1 || this.tableID == 2){
            createButtons();
            createGameButtons();
        }
    }





    //----------------------------------------LOGIC----------------------------------------//

    private LogicModule logic(){
        return menu.logic();
    }





    //----------------------------------------CREATE----------------------------------------//

    private void createButtons(){
        buttonSet.addButton(POS_TOP_MIDDLE,        SCORE,                                               () -> logic().turnstate == 0 && logic().hasHighscore(),                         this::commandReset);    // Show Highscore
        buttonSet.addButton(POS_MID_MIDDLE,        FINISH,                                              () -> logic().turnstate == 5,                                                   this::commandReset);    // Show Highscore OR return to START
        buttonSet.addButton(POS_MID_MIDDLE,        BACK,                                                () -> logic().turnstate == 7,                                                   this::commandReset);    // Return to START
        buttonSet.addButton(POS_MID_MIDDLE,        START,                                               () -> logic().turnstate == 0 && hasBet(),                                       this::commandStart);    // Start MiniGame
        buttonSet.addButton(new Vector2( 56, 212), ARROW_DOWN_OFF, ARROW_DOWN_ON, 1, 32,                () -> logic().turnstate == 0 && menu.hasToken() && bet > menu.getBettingLow(),  this::commandBetDown);  // Lower Bet
        buttonSet.addButton(new Vector2(174, 212), ARROW_UP_OFF,   ARROW_UP_ON,   1, 32,                () -> logic().turnstate == 0 && menu.hasToken() && bet < menu.getBettingHigh(), this::commandBetUp);    // Raise Bet
        buttonSet.addButton(POS_MID_MIDDLE,        JOIN,                                                () -> logic().turnstate == 2 &&  logic().isMultiplayer() && !isCurrentPlayer(), this::commandJoinGame); // Join Multiplayer game
        buttonSet.addButton(new Vector2(tableID == 1 ? 240 : 336, 240), FORFEIT_OFF, FORFEIT_ON, 0, 96, () -> logic().turnstate == 2 && !logic().isMultiplayer(),                       this::commandForfeit);  // Forfeit
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
            if(keyCode == KEY_SPACE){ action(1); }
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





    //----------------------------------------DRAW---OVERLAY----------------------------------------//

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void renderLabels(MatrixStack matrix, int mouseX, int mouseY){
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
            this.font.draw(matrix, "PLAYER1: " + logic().currentPlayer[0],         tableID == 2 ? 355 : 260,  15, 16777215);
            this.font.draw(matrix, "PLAYER2: " + logic().currentPlayer[1],         tableID == 2 ? 355 : 260,  25, 16777215);
            this.font.draw(matrix, "PLAYER3: " + logic().currentPlayer[2],         tableID == 2 ? 355 : 260,  35, 16777215);
            this.font.draw(matrix, "PLAYER4: " + logic().currentPlayer[3],         tableID == 2 ? 355 : 260,  45, 16777215);
            this.font.draw(matrix, "PLAYER5: " + logic().currentPlayer[4],         tableID == 2 ? 355 : 260,  55, 16777215);
            this.font.draw(matrix, "PLAYER6: " + logic().currentPlayer[5],         tableID == 2 ? 355 : 260,  65, 16777215);
            this.font.draw(matrix, "TIMEOUT: " + logic().timeout,                  tableID == 2 ? 355 : 260,  75, 16777215);
            this.font.draw(matrix, "STATE:   " + logic().turnstate,                tableID == 2 ? 355 : 260,  85, 16777215);
            this.font.draw(matrix, "PLAYERS: " + logic().getFirstFreePlayerSlot(), tableID == 2 ? 355 : 260,  95, 16777215);
            this.font.draw(matrix, "ACTIVE:  " + logic().activePlayer,             tableID == 2 ? 355 : 260, 105, 16777215);
        }

        // ----- Search for tokens in PlayerInventory ----- //
        if(playerToken == -1 && logic().turnstate < 4) validateTokens();

        // ----- Multiplayer Additional Player Join Button ----- //
        if(menu.logic().isMultiplayer() && logic().turnstate == 2 && !isCurrentPlayer()){
            if(menu.logic().hasFreePlayerSlots()){
                drawFont(matrix, "BET:",                      96, 196);
                this.itemRenderer.renderGuiItem(menu.getItemToken(),                      180, 192);
                if(menu.getBettingLow() > 1) drawFont(matrix, "x" + menu.getBettingLow(), 138, 196);
            }
        }

        // ----- Start Screen Information (Card Table) ----- //
        if(logic().turnstate == 0 && (tableID == 1 || tableID == 2)){
            if(menu.hasToken() && menu.getBettingHigh() > 0) {
                drawFontCenter(      matrix, "The entry fee is:",    128, 112);
                this.itemRenderer.renderGuiItem(menu.getItemToken(), 120, 124);
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
                this.itemRenderer.renderGuiItem(menu.getItemToken(),     120, 192          );
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





    //----------------------------------------DRAW---BACKGROUND----------------------------------------//

    /** Draws the background layer of this container (behind the items). */
    protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        buttonSet.update(leftPos, topPos, mouseX, mouseY);
        if((tableID == 1 || tableID == 2) && colour > 0){
            colour--;
            if(colour == 0){
                showForfeit = false;
            }
        }

        // ----- Arcade Background (dummy) ----- //
        if(tableID == 0 && (logic() instanceof LogicDummy)) {
            Random RANDOM = new Random();
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_STATIC);
            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 6; x++){
                    this.blit(matrix, leftPos + 32 + 32*x, topPos + 32*y, 32*RANDOM.nextInt(8), 32*RANDOM.nextInt(8), 32, 32);
                }
            }
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_GROUND_ARCADE);
            this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        }

        // ----- Arcade Background (game) ----- //
        if(tableID == 0 && !(logic() instanceof LogicDummy)) {
            this.minecraft.getTextureManager().bind(getParallaxTexture(true));
            this.blit(matrix, leftPos, topPos, 0, camera1, 256, 256);
            this.minecraft.getTextureManager().bind(getParallaxTexture(false));
            this.blit(matrix, leftPos, topPos, 0, camera0, 256, 256);
        }

        // ----- Card Table Background (small) ----- //
        if(tableID == 1){
            this.minecraft.getTextureManager().bind(getBackgroundCardTable());
            this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background
        }

        // ----- Card Table Background (wide) ----- //
        if(tableID == 2){
            this.minecraft.getTextureManager().bind(getBackgroundCardTable());
            this.blit(matrix, leftPos -  96, topPos,  0, 0, this.imageWidth-32, this.imageHeight); // Background Left
            this.blit(matrix, leftPos + 128, topPos, 32, 0, this.imageWidth-32, this.imageHeight); // Background Right
        }

        // ----- Slot Machine Background ----- //
        if(tableID == 3){
            this.minecraft.getTextureManager().bind(getBackgroundSlotMachine());
            this.blit(matrix, leftPos +  31, topPos -   1,   0,   0, 194, 162); // Upper Part
            this.blit(matrix, leftPos      , topPos + 163,   0, 162, 256,  94); // Lower Part
            this.blit(matrix, leftPos + 256, topPos + 178, 224,  64,  32,  64); // Crank

            this.blit(matrix, leftPos +  38, topPos + 161, 195, 129,  60,   2); // MiddleLine
            this.blit(matrix, leftPos +  98, topPos + 161, 195, 129,  60,   2); // MiddleLine
            this.blit(matrix, leftPos + 158, topPos + 161, 195, 129,  60,   2); // MiddleLine

            this.blit(matrix, leftPos +  10, topPos + 257, 195, 129,  52,  30); // Underline
            this.blit(matrix, leftPos +  62, topPos + 257, 203, 129,  44,  30); // Underline
            this.blit(matrix, leftPos + 106, topPos + 257, 203, 129,  44,  30); // Underline
            this.blit(matrix, leftPos + 150, topPos + 257, 203, 129,  44,  30); // Underline
            this.blit(matrix, leftPos + 194, topPos + 257, 203, 129,  52,  30); // Underline

            // ----- LEVER ----- //
            if(logic().scoreLives == 2){ // Lever Down
                this.blit(matrix, leftPos + 261, topPos + 220, 197, 64, 22,  8); // Lever
                this.blit(matrix, leftPos + 249, topPos + 224, 201,  9, 46, 46); // Knob
            } else {                     // Lever Up
                this.blit(matrix, leftPos + 261, topPos + 142, 197, 68, 24, 60); // Lever (Lower Part)
                this.blit(matrix, leftPos + 261, topPos + 100, 197, 68, 22, 42); // Lever (Upper Part)
                this.blit(matrix, leftPos + 249, topPos +  60, 201,  9, 46, 46); // Knob
            }
        }

        // ----- Draws Logo from ItemModule ----- //
        if(logic().turnstate <= 1) { drawLogo(matrix); }

        // ----- LOGIC Break ----- //
        if(logic() instanceof LogicDummy) return;

        // ----- MiniGame Layer (Arcade/Table) ----- //
        if(tableID < 3 && logic().turnstate >= 2 && logic().turnstate < 6){
            if(logic().pause) RenderSystem.color4f(0.35F, 0.35F, 0.35F, 1.0F);
            drawBackgroundLayer(matrix, partialTicks, mouseX, mouseY);
            if(logic().pause) RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        // ----- MiniGame Layer (Slot Machine) ----- //
        if(tableID == 3 && logic().scoreLives > 0){
            drawBackgroundLayer(matrix, partialTicks, mouseX, mouseY);
        }

        // ----- Buttons ----- //
        if(tableID == 1 || tableID == 2){
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_BUTTONS);
            while (buttonSet.next()){
                if(buttonSet.visible()      ){ blit(matrix, leftPos + buttonSet.pos().X, topPos + buttonSet.pos().Y, buttonSet.map().X,       buttonSet.map().Y,       buttonSet.sizeX(), buttonSet.sizeY()); }
                if(buttonSet.isHighlighted()){ blit(matrix, leftPos + buttonSet.pos().X, topPos + buttonSet.pos().Y, buttonSet.highlight().X, buttonSet.highlight().Y, buttonSet.sizeX(), buttonSet.sizeY()); }
            }
            if(colour > 0){
                blit(matrix, leftPos + 89, topPos + 240, 28, 242, 78, 14);
            }
        }

        // ----- Arcade Border ----- //
        if(tableID == 0) {
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_GROUND_ARCADE);
            this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
            int shift2 = logic().turnstate == 2 || logic().turnstate == 3 ? 2 : 1;
            if(logic().turnstate != 5 && !menu.logic().pause) camera1 = (camera1 + shift2  ) % 256;
            if(logic().turnstate != 5 && !menu.logic().pause) camera0 = (camera0 + shift2*2) % 256;
        }

        // ----- Multiplayer Status ----- //
        if((logic().turnstate == 2 || logic().turnstate == 3) && logic().isMultiplayer()){
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_DICE);
            for(int i = 0;        i < logic().getFirstFreePlayerSlot(); i++){ this.blit(matrix, leftPos+(tableID == 2 ? 340 : 245), topPos + 32 + 36*i,                    224, 32 + 32*i,                    32, 32); }
            if(logic().activePlayer < logic().getFirstFreePlayerSlot()     ){ this.blit(matrix, leftPos+(tableID == 2 ? 340 : 245), topPos + 32 + 36*logic().activePlayer, 192, 32 + 32*logic().activePlayer, 32, 32); }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

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
        CasinoPacketHandler.sendToServer(new MessageScoreServer(this.inventory.player.getName().getString(), logic().scorePoint, menu.pos()));
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
        inv.set(0, menu.inventory.getItem(0));
        inv.set(1, menu.inventory.getItem(1));
        inv.set(2, menu.inventory.getItem(2));
        inv.set(3, menu.inventory.getItem(3));
        inv.set(4, menu.inventory.getItem(4));
        CasinoPacketHandler.sendToServer(new MessageInventoryServer(inv, menu.getStorageToken(), menu.getStoragePrize(), menu.pos()));
    }





    //----------------------------------------SUPPORT---TOKEN----------------------------------------//

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
            mod.lucky77.util.Inventory.decreaseInventory(inventory, menu.getItemToken(), bet);
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
            CasinoPacketHandler.sendToServer(new MessagePlayerServer(menu.getItemToken().getItem(), -bet));
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
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, count));
            } else {
                Item item = menu.getItemPrize().getItem();
                inventory.add(new ItemStack(item, amount));
                CasinoPacketHandler.sendToServer(new MessagePlayerServer(item, amount));
            }
        }
    }





    //----------------------------------------SUPPORT---PLAYER----------------------------------------//

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





    //----------------------------------------SUPPORT---DRAW----------------------------------------//

    /** Draw a Background Texture on the field **/
    protected void drawBackground(MatrixStack matrix, ResourceLocation rl){
        this.minecraft.getTextureManager().bind(rl);
        this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    /** Draw a Background Texture on the field and move it along the x-Axis **/
    protected void drawBackground(MatrixStack matrix, ResourceLocation rl, int offset){
        this.minecraft.getTextureManager().bind(rl);
        this.blit(matrix, leftPos + offset, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    /** Draw a Background Texture on the field and reset the shader to another texture afterwards **/
    protected void drawBackground(MatrixStack matrix, ResourceLocation rl, ResourceLocation rl2){
        this.minecraft.getTextureManager().bind(rl);
        this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.minecraft.getTextureManager().bind(rl2);
    }

    /** Draws a timeout timer **/
    protected void drawTimer(MatrixStack matrix){
        if(logic().turnstate == 2 || logic().turnstate == 3){
            if(Config.CONFIG.config_timeout.get() - logic().timeout > 0){
                drawFontInvert(matrix, "" + (Config.CONFIG.config_timeout.get() - logic().timeout), tableID == 1 ? 238 : 336, 7);
            }
        }
    }

    /** Draws a value and its name on the left side of the Table **/
    protected void drawValueLeft(MatrixStack matrix, String name, int value){
        drawFontCenter(matrix,       name, 32, 7, 11119017);
        drawFontCenter(matrix, "" + value, 64, 7          );
    }

    /** Draws a value and its name on the left side of the Table **/
    protected void drawValueRight(MatrixStack matrix, String name, int value){
        drawFontCenter(matrix,       name, 224, 7, 11119017);
        drawFontCenter(matrix, "" + value, 192, 7          );
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFont(MatrixStack matrix, String text, int x, int y){
        drawFont(matrix, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFont(MatrixStack matrix, String text, int x, int y, int color){
        this.font.draw(matrix, text,  x+1, y+1, 0    );
        this.font.draw(matrix, text,  x  , y  , color);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontInvert(MatrixStack matrix, String text, int x, int y){
        drawFontInvert(matrix, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontInvert(MatrixStack matrix, String text, int x, int y, int color){
        int w = this.font.width(text);
        this.font.draw(matrix, text,  x+1 - w, y+1, 0    );
        this.font.draw(matrix, text,  x   - w, y  , color);
    }

    /** Draws String on x,y position with shadow **/
    protected void drawFontCenter(MatrixStack matrix, String text, int x, int y){
        drawFontCenter(matrix, text, x, y, grayscale);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontCenter(MatrixStack matrix, String text, int x, int y, int color){
        int w = this.font.width(text) / 2;
        this.font.draw(matrix, text,  x+1 - w, y+1, 0    );
        this.font.draw(matrix, text,  x   - w, y  , color);
    }

    /** Draws a Card **/
    public void drawCard(MatrixStack matrix, int posX, int posY, Card card){
        if(card.suit == -1) return;
        if(card.hidden){
            this.minecraft.getTextureManager().bind(getCardsTexture(true));
        } else {
            if(card.suit <= 1) this.minecraft.getTextureManager().bind(getCardsTexture(false));
            if(card.suit >= 2) this.minecraft.getTextureManager().bind(getCardsTexture(true ));
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
        blit(matrix, leftPos + posX + card.shiftX, topPos + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
    }

    /** Draws the Backside of a Card (also used for highlighter) **/
    public void drawCardBack(MatrixStack matrix, int posX, int posY, int color){
        if(color <= 6) this.minecraft.getTextureManager().bind(getCardsTexture(true ));
        else           this.minecraft.getTextureManager().bind(getCardsTexture(false));
        blit(matrix, leftPos + posX, topPos + posY, (color%7) * 32, 192, 32, 48);
    }

    private void drawLetter(MatrixStack matrix, char c, int posX, int posY){
        if(c == 'a') blit(matrix, posX, posY,   0,   0, 32, 32);
        if(c == 'b') blit(matrix, posX, posY,  32,   0, 32, 32);
        if(c == 'c') blit(matrix, posX, posY,  64,   0, 32, 32);
        if(c == 'd') blit(matrix, posX, posY,  96,   0, 32, 32);
        if(c == 'e') blit(matrix, posX, posY, 128,   0, 32, 32);
        if(c == 'f') blit(matrix, posX, posY, 160,   0, 32, 32);
        if(c == 'g') blit(matrix, posX, posY, 192,   0, 32, 32);
        if(c == 'h') blit(matrix, posX, posY, 224,   0, 32, 32);
        if(c == 'i') blit(matrix, posX, posY,   0,  32, 32, 32);
        if(c == 'j') blit(matrix, posX, posY,  32,  32, 32, 32);
        if(c == 'k') blit(matrix, posX, posY,  64,  32, 32, 32);
        if(c == 'l') blit(matrix, posX, posY,  96,  32, 32, 32);
        if(c == 'm') blit(matrix, posX, posY, 128,  32, 32, 32);
        if(c == 'n') blit(matrix, posX, posY, 160,  32, 32, 32);
        if(c == 'o') blit(matrix, posX, posY, 192,  32, 32, 32);
        if(c == 'p') blit(matrix, posX, posY, 224,  32, 32, 32);
        if(c == 'q') blit(matrix, posX, posY,   0,  64, 32, 32);
        if(c == 'r') blit(matrix, posX, posY,  32,  64, 32, 32);
        if(c == 's') blit(matrix, posX, posY,  64,  64, 32, 32);
        if(c == 't') blit(matrix, posX, posY,  96,  64, 32, 32);
        if(c == 'u') blit(matrix, posX, posY, 128,  64, 32, 32);
        if(c == 'v') blit(matrix, posX, posY, 160,  64, 32, 32);
        if(c == 'w') blit(matrix, posX, posY, 192,  64, 32, 32);
        if(c == 'x') blit(matrix, posX, posY, 224,  64, 32, 32);
        if(c == 'y') blit(matrix, posX, posY,   0,  96, 32, 32);
        if(c == 'z') blit(matrix, posX, posY,  32,  96, 32, 32);
        if(c == '0') blit(matrix, posX, posY,  64,  96, 32, 32);
        if(c == '1') blit(matrix, posX, posY,  96,  96, 32, 32);
        if(c == '2') blit(matrix, posX, posY, 128,  96, 32, 32);
        if(c == '3') blit(matrix, posX, posY, 160,  96, 32, 32);
        if(c == '4') blit(matrix, posX, posY, 192,  96, 32, 32);
        if(c == '5') blit(matrix, posX, posY, 224,  96, 32, 32);
        if(c == '6') blit(matrix, posX, posY,   0, 128, 32, 32);
        if(c == '7') blit(matrix, posX, posY,  32, 128, 32, 32);
        if(c == '8') blit(matrix, posX, posY,  64, 128, 32, 32);
        if(c == '9') blit(matrix, posX, posY,  96, 128, 32, 32);
    }

    /** Draws Colored Card Table Background **/
    private ResourceLocation getBackgroundCardTable(){
        if(menu.color == DyeColor.BLACK     ) return CasinoKeeper.TEXTURE_GROUND_BLACK;
        if(menu.color == DyeColor.BLUE      ) return CasinoKeeper.TEXTURE_GROUND_BLUE;
        if(menu.color == DyeColor.BROWN     ) return CasinoKeeper.TEXTURE_GROUND_BROWN;
        if(menu.color == DyeColor.CYAN      ) return CasinoKeeper.TEXTURE_GROUND_CYAN;
        if(menu.color == DyeColor.GRAY      ) return CasinoKeeper.TEXTURE_GROUND_GRAY;
        if(menu.color == DyeColor.GREEN     ) return CasinoKeeper.TEXTURE_GROUND_GREEN;
        if(menu.color == DyeColor.LIGHT_BLUE) return CasinoKeeper.TEXTURE_GROUND_LIGHT_BLUE;
        if(menu.color == DyeColor.LIME      ) return CasinoKeeper.TEXTURE_GROUND_LIME;
        if(menu.color == DyeColor.MAGENTA   ) return CasinoKeeper.TEXTURE_GROUND_MAGENTA;
        if(menu.color == DyeColor.ORANGE    ) return CasinoKeeper.TEXTURE_GROUND_ORANGE;
        if(menu.color == DyeColor.PINK      ) return CasinoKeeper.TEXTURE_GROUND_PINK;
        if(menu.color == DyeColor.PURPLE    ) return CasinoKeeper.TEXTURE_GROUND_PURPLE;
        if(menu.color == DyeColor.RED       ) return CasinoKeeper.TEXTURE_GROUND_RED;
        if(menu.color == DyeColor.LIGHT_GRAY) return CasinoKeeper.TEXTURE_GROUND_LIGHT_GRAY;
        if(menu.color == DyeColor.WHITE     ) return CasinoKeeper.TEXTURE_GROUND_WHITE;
        if(menu.color == DyeColor.YELLOW    ) return CasinoKeeper.TEXTURE_GROUND_YELLOW;
        return CasinoKeeper.TEXTURE_GROUND_GRAY;
    }

    /** Draws Colored Slot Machine Background **/
    private ResourceLocation getBackgroundSlotMachine(){
        if(menu.color == DyeColor.BLACK     ) return CasinoKeeper.TEXTURE_SLOTS_BLACK;
        if(menu.color == DyeColor.BLUE      ) return CasinoKeeper.TEXTURE_SLOTS_BLUE;
        if(menu.color == DyeColor.BROWN     ) return CasinoKeeper.TEXTURE_SLOTS_BROWN;
        if(menu.color == DyeColor.CYAN      ) return CasinoKeeper.TEXTURE_SLOTS_CYAN;
        if(menu.color == DyeColor.GRAY      ) return CasinoKeeper.TEXTURE_SLOTS_GRAY;
        if(menu.color == DyeColor.GREEN     ) return CasinoKeeper.TEXTURE_SLOTS_GREEN;
        if(menu.color == DyeColor.LIGHT_BLUE) return CasinoKeeper.TEXTURE_SLOTS_LIGHT_BLUE;
        if(menu.color == DyeColor.LIME      ) return CasinoKeeper.TEXTURE_SLOTS_LIME;
        if(menu.color == DyeColor.MAGENTA   ) return CasinoKeeper.TEXTURE_SLOTS_MAGENTA;
        if(menu.color == DyeColor.ORANGE    ) return CasinoKeeper.TEXTURE_SLOTS_ORANGE;
        if(menu.color == DyeColor.PINK      ) return CasinoKeeper.TEXTURE_SLOTS_PINK;
        if(menu.color == DyeColor.PURPLE    ) return CasinoKeeper.TEXTURE_SLOTS_PURPLE;
        if(menu.color == DyeColor.RED       ) return CasinoKeeper.TEXTURE_SLOTS_RED;
        if(menu.color == DyeColor.LIGHT_GRAY) return CasinoKeeper.TEXTURE_SLOTS_LIGHT_GRAY;
        if(menu.color == DyeColor.WHITE     ) return CasinoKeeper.TEXTURE_SLOTS_WHITE;
        if(menu.color == DyeColor.YELLOW    ) return CasinoKeeper.TEXTURE_SLOTS_YELLOW;
        return CasinoKeeper.TEXTURE_SLOTS_GRAY;
    }

    /** Draws Logo from ItemModule **/
    private void drawLogo(MatrixStack matrix) {
        int sizeX = 0;
        String[] logo = getGameName().split("_");
        if(tableID <= 2){
            if(tableID == 0){
                this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_FONT_ARCADE);
                sizeX = 16;
            } else if(tableID == 1 || tableID == 2){
                this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_FONT_CARDTABLE);
                sizeX = 32;
            }

            for(int i = 0; i < logo.length; i++){
                for(int k = 0; k < logo[i].length(); k++){
                    drawLetter(matrix, logo[i].charAt(k), leftPos + 128 - logo[i].length()*(sizeX/2) + sizeX*k, topPos + 32 + 32*i);
                }
            }
        }
    }

    protected void drawMino(MatrixStack matrix, int posX, int posY, int idX, int idY){
        this.blit(matrix, leftPos + posX, topPos + posY, 24 * idX, 24 * idY, 24, 24);
    }

    protected void drawMino(MatrixStack matrix, int posX, int posY){
        drawMino(matrix, posX, posY, 0, 0);
    }

    protected void drawMinoSmall(MatrixStack matrix, int posX, int posY, int id, boolean alternate){
        if(alternate){
            this.blit(matrix, leftPos + posX, topPos + posY, 240, 16 * id, 16, 16);
        } else {
            this.blit(matrix, leftPos + posX, topPos + posY, 16 * id, 240, 16, 16);
        }
    }

    protected void drawMinoSmall(MatrixStack matrix, int posX, int posY){
        drawMinoSmall(matrix, posX, posY, 0, false);
    }

    protected void drawDigi(MatrixStack matrix, int posX, int posY, int idX, int idY){
        this.blit(matrix, leftPos + posX, topPos + posY, 16 * idX, 16 + 16 * idY, 16, 16);
    }

    protected void drawDigi(MatrixStack matrix, int posX, int posY){
        drawDigi(matrix, posX, posY, 0, 0);
    }

    protected void drawDigiSmall(MatrixStack matrix, int posX, int posY, int id){
        this.blit(matrix, leftPos + posX    , topPos + posY    , 16 * id     , 16   , 6, 6);
        this.blit(matrix, leftPos + posX + 6, topPos + posY    , 16 * id + 10, 16   , 6, 6);
        this.blit(matrix, leftPos + posX    , topPos + posY + 6, 16 * id     , 16+10, 6, 6);
        this.blit(matrix, leftPos + posX + 6, topPos + posY + 6, 16 * id + 10, 16+10, 6, 6);

    }

    protected void drawDigiSmall(MatrixStack matrix, int posX, int posY){
        drawDigiSmall(matrix, posX, posY, 0);
    }

    protected void drawDigiSymbol(MatrixStack matrix, int posX, int posY, int id){
        this.blit(matrix, leftPos + posX, topPos + posY, 16 * id, 0, 16, 16);
    }

    protected void drawDigiSymbol(MatrixStack matrix, int posX, int posY){
        drawDigiSymbol(matrix, posX, posY, 0);
    }

    protected void drawShip(MatrixStack matrix, Ship ship, int shipID, int lookDirection, boolean animate){
        int frame = logic().turnstate < 4 && animate ? (logic().frame % 12) / 2 : 0;
        if(frame == 4) frame = 2;
        if(frame == 5) frame = 1;
        int direction = lookDirection == -1 ? ship.getLookDirection() : lookDirection;
        this.blit(matrix, leftPos + 32 + ship.getPos().X, topPos + 8 + ship.getPos().Y, 64*(shipID%4) + 16*frame, 128 + direction*16 + (shipID/4)*64, 16, 16);
    }

    protected void drawShip(MatrixStack matrix, Vector2 vec, int shipID){
        int frame = logic().turnstate < 4 ? (logic().frame % 12) / 2 : 0;
        if(frame == 4) frame = 2;
        if(frame == 5) frame = 1;
        this.blit(matrix, leftPos + 32 + vec.X*16, topPos + 8 + vec.Y*16, 64*(shipID%4) + 16*frame, 128 + (shipID/4)*64, 16, 16);
    }

    private ResourceLocation getParallaxTexture(boolean lowTexture){
        switch (menu.getSettingAlternateColor()) {
            case 0: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_0_LOW : CasinoKeeper.TEXTURE_PARALLAX_0_HIGH;
            case 1: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_1_LOW : CasinoKeeper.TEXTURE_PARALLAX_1_HIGH;
            case 2: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_2_LOW : CasinoKeeper.TEXTURE_PARALLAX_2_HIGH;
            case 3: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_3_LOW : CasinoKeeper.TEXTURE_PARALLAX_3_HIGH;
            case 4: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_4_LOW : CasinoKeeper.TEXTURE_PARALLAX_4_HIGH;
            case 5: return lowTexture ? CasinoKeeper.TEXTURE_PARALLAX_5_LOW : CasinoKeeper.TEXTURE_PARALLAX_5_HIGH;
            default: return CasinoKeeper.TEXTURE_STATIC;
        }
    }

    protected ResourceLocation getCardsTexture(boolean noirTexture){
        switch (menu.getSettingAlternateColor()) {
            case 0: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_0_NOIR : CasinoKeeper.TEXTURE_CARDS_0_ROUGE;
            case 1: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_1_NOIR : CasinoKeeper.TEXTURE_CARDS_1_ROUGE;
            case 2: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_2_NOIR : CasinoKeeper.TEXTURE_CARDS_2_ROUGE;
            case 3: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_3_NOIR : CasinoKeeper.TEXTURE_CARDS_3_ROUGE;
            case 4: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_4_NOIR : CasinoKeeper.TEXTURE_CARDS_4_ROUGE;
            case 5: return noirTexture ? CasinoKeeper.TEXTURE_CARDS_5_NOIR : CasinoKeeper.TEXTURE_CARDS_5_ROUGE;
            default: return CasinoKeeper.TEXTURE_STATIC;
        }
    }





    //----------------------------------------COMMAND----------------------------------------//

    protected void commandStart(){
        if(!menu.hasToken() || playerToken >= menu.getBettingLow()){
            if(menu.hasToken()) collectBet();
            Random r = new Random();
            CasinoPacketHandler.sendToServer(new MessageStartServer(inventory.player.getName().getString(), r.nextInt(1000000), menu.pos()));
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
        CasinoPacketHandler.sendToServer(new MessageStateServer(false, action, menu.pos()));
    }

    protected void turnstate(int state){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, state, menu.pos()));
    }

    private void addNewPlayer(){
        CasinoPacketHandler.sendToServer(new MessageStartServer(inventory.player.getName().getString(), -1, menu.pos()));
    }





    //----------------------------------------ABSTRACT----------------------------------------//

    protected abstract void createGameButtons();
    protected abstract void interact(double mouseX, double mouseY, int mouseButton);
    protected abstract void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY);
    protected abstract void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY);
    protected abstract String getGameName();



}

