package mod.casinocraft.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoCraft;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerMachine;
import mod.casinocraft.network.MessageSettingServer;
import mod.casinocraft.network.MessageStateServer;
import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static mod.casinocraft.util.KeyMap.*;

public class ScreenMachine extends ContainerScreen<ContainerMachine> {

    private static final ResourceLocation GUI_TEXTURE0 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory0.png");
    private static final ResourceLocation GUI_TEXTURE1 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory1.png");
    private static final ResourceLocation GUI_TEXTURE2 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory2.png");
    private static final ResourceLocation GUI_TEXTURE3 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory3.png");
    private static final ResourceLocation GUI_TEXTURE4 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory4.png");
    /** The player inventory bound to this GUI. */
    private   final PlayerInventory PLAYER;
    /** The TileEntity bound to this GUI. */
    protected final ContainerMachine CONTAINER;

    private int page = 0;
    private int activeInput = 0;
    private String input = "";
    private boolean hasReset = false;
    private int highlightIndex = 0;
    private int highlightTimer = 0;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMachine(ContainerMachine container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        PLAYER = player;
        CONTAINER = container;
        this.xSize = 176;
        this.ySize = 204;
    }




    //----------------------------------------INPUT----------------------------------------//

    /** Overwritten keyPressed function (only used for rerouting keyCode) */
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputMappings.Input mouseKey = InputMappings.getInputByCode(keyCode, scanCode);
        keyTyped(keyCode);
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey)) {
            this.closeScreen();
            return true;
        } else {
            boolean handled = this.itemStackMoved(keyCode, scanCode);// Forge MC-146650: Needs to return true when the key is handled
            if (this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
                if (this.minecraft.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
                    this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 0, ClickType.CLONE);
                    handled = true;
                } else if (this.minecraft.gameSettings.keyBindDrop.isActiveAndMatches(mouseKey)) {
                    this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, hasControlDown() ? 1 : 0, ClickType.THROW);
                    handled = true;
                }
            } else if (this.minecraft.gameSettings.keyBindDrop.isActiveAndMatches(mouseKey)) {
                handled = true; // Forge MC-146650: Emulate MC bug, so we don't drop from hotbar when pressing drop without hovering over a item.
            }

            return handled;
        }
    }

    /** Fired when a key is pressed */
    private void keyTyped(int keyCode){

        if(activeInput > 0){
            if(keyCode == KEY_ENTER){ // FINISH AND SEND INPUT STRING
                if(input.length() == 0){
                    input = "0";
                }
                int temp = Integer.parseInt(input);
                commandSetScore(activeInput - 1, temp);
                activeInput = 0;
            }
            if(keyCode == KEY_BACK){ // DELETE ONE DIGIT
                if(input.length() == 1){
                    input = "";
                } else if(input.length() > 1){
                    input = input.substring(0, input.length() - 1);
                }
            }
            if(keyCode == KEY_1_IN){ input = input + "1"; }
            if(keyCode == KEY_2_IN){ input = input + "2"; }
            if(keyCode == KEY_3_IN){ input = input + "3"; }
            if(keyCode == KEY_4_IN){ input = input + "4"; }
            if(keyCode == KEY_5_IN){ input = input + "5"; }
            if(keyCode == KEY_6_IN){ input = input + "6"; }
            if(keyCode == KEY_7_IN){ input = input + "7"; }
            if(keyCode == KEY_8_IN){ input = input + "8"; }
            if(keyCode == KEY_9_IN){ input = input + "9"; }
            if(keyCode == KEY_0_IN){ input = input + "0"; }
        }
    }

    /** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && hasKey() && activeInput == 0){

            // Page Header
            if(mouseRect( 58, 0, 56, 24, mouseX, mouseY)) { commandChangePage(0); }
            if(mouseRect(116, 0, 56, 24, mouseX, mouseY)) { commandChangePage(1); }
            if(mouseRect(174, 0, 56, 24, mouseX, mouseY)) { commandChangePage(2); }

            // Page Body
            if(page == 0){ // PAGE: Settings
                if(mouseRect(60, 30, 12, 12, mouseX, mouseY)) { commandToggleSettings(0); } // SETTING 1
                if(mouseRect(60, 43, 12, 12, mouseX, mouseY)) { commandToggleSettings(1); } // SETTING 2
                if(mouseRect(60, 56, 12, 12, mouseX, mouseY)) { commandToggleSettings(2); } // SETTING 3
                if(mouseRect(60, 69, 12, 12, mouseX, mouseY)) { commandToggleSettings(3); } // SETTING 4
                if(mouseRect(60, 83, 12, 12, mouseX, mouseY)) { commandResetGameState();          } // SETTING RESET
                if(mouseRect(60, 96, 12, 12, mouseX, mouseY)) { commandToggleSettings(4); } // SETTING COLOR
            } else if(page == 1){ // PAGE: Betting
                if(mouseRect( 61,  31, 40, 20, mouseX, mouseY)) { commandTransfer(true, true); } // TRANSFER IN
                if(mouseRect(178,  31, 40, 20, mouseX, mouseY)) { commandTransfer(true, false); } // TRANSFER OUT
                if(mouseRect(193, 73, 16, 16, mouseX, mouseY)) { highlight(1); commandChangeBet(false, false); } // MINIMUM LOWER
                if(mouseRect(211, 73, 16, 16, mouseX, mouseY)) { highlight(2); commandChangeBet(true, false); } // MINIMUM UPPER
                if(mouseRect(193, 91, 16, 16, mouseX, mouseY)) { highlight(3); commandChangeBet(false, true); } // MAXIMUM LOWER
                if(mouseRect(211, 91, 16, 16, mouseX, mouseY)) { highlight(4); commandChangeBet(true, true); } // MAXIMUM UPPER
            } else if(page == 2){ // Page: Reward
                if(mouseRect( 61,  31, 40, 20, mouseX, mouseY)) { commandTransfer(false, true); } // TRANSFER IN
                if(mouseRect(178,  31, 40, 20, mouseX, mouseY)) { commandTransfer(false, false); } // TRANSFER OUT

                if(mouseRect(193, 55, 16, 16, mouseX, mouseY)) { highlight( 5); commandChangeReward(false, 0); } // REWARD 1 LOWER
                if(mouseRect(211, 55, 16, 16, mouseX, mouseY)) { highlight( 6); commandChangeReward(true, 0); } // REWARD 1 UPPER
                if(mouseRect(193, 73, 16, 16, mouseX, mouseY)) { highlight( 7); commandChangeReward(false, 1); } // REWARD 2 LOWER
                if(mouseRect(211, 73, 16, 16, mouseX, mouseY)) { highlight( 8); commandChangeReward(true, 1); } // REWARD 2 UPPER
                if(mouseRect(193, 91, 16, 16, mouseX, mouseY)) { highlight( 9); commandChangeReward(false, 2); } // REWARD 3 LOWER
                if(mouseRect(211, 91, 16, 16, mouseX, mouseY)) { highlight(10); commandChangeReward(true, 2); } // REWARD 3 UPPER

                if(mouseRect(79, 55, 16, 16, mouseX, mouseY)) { input = ""; activeInput = 1; } // SCORE 1 EDIT
                if(mouseRect(79, 73, 16, 16, mouseX, mouseY)) { input = ""; activeInput = 2; } // SCORE 2 EDIT
                if(mouseRect(79, 91, 16, 16, mouseX, mouseY)) { input = ""; activeInput = 3; } // SCORE 3 EDIT

                if(mouseRect(61, 55, 16, 16, mouseX, mouseY)) { commandTogglePrize(0); } // PRIZE 1 TOGGLE
                if(mouseRect(61, 73, 16, 16, mouseX, mouseY)) { commandTogglePrize(1); } // PRIZE 2 TOGGLE
                if(mouseRect(61, 91, 16, 16, mouseX, mouseY)) { commandTogglePrize(2); } // PRIZE 3 TOGGLE
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
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixstack, int mouseX, int mouseY){

        // Page Body
        if(activeInput == 0){
            if(hasKey()){
                if(page == 1){ // PAGE: Betting
                    this.itemRenderer.renderItemIntoGUI(CONTAINER.getItemToken(), 107-56, 33);
                    String text = CONTAINER.getStorageToken() == 0 ? "EMPTY" : CONTAINER.getSettingInfiniteToken() ? "x(INFINITE)" : "x " + CONTAINER.getStorageToken();
                    this.font.drawString(matrixstack, text, 107+20-56-2, 33+4, 4210752);
                    this.font.drawString(matrixstack, "" + CONTAINER.getBettingLow(), 162-56, 77, 4210752);
                    this.font.drawString(matrixstack, "" + CONTAINER.getBettingHigh(), 162-56, 95, 4210752);
                } else if(page == 2){ // Page: Reward
                    this.itemRenderer.renderItemIntoGUI(CONTAINER.getItemPrize(), 107-56, 33);
                    String text = CONTAINER.getStoragePrize() == 0 ? "EMPTY" : CONTAINER.getSettingInfinitePrize() ? "x(INFINITE)" : "x " + CONTAINER.getStoragePrize();
                    this.font.drawString(matrixstack, text, 107+20-56-2, 33+4, 4210752);
                    this.font.drawString(matrixstack, "" + CONTAINER.getPrizeScore1(),  106-56,  59, 4210752);
                    this.font.drawString(matrixstack, "" + CONTAINER.getPrizeScore2(),  106-56,  77, 4210752);
                    this.font.drawString(matrixstack, "" + CONTAINER.getPrizeScore3(),  106-56,  95, 4210752);
                    this.font.drawString(matrixstack, "" + CONTAINER.getPrizeCount1(), 162-56,  59, 4210752);
                    this.font.drawString(matrixstack, "" + CONTAINER.getPrizeCount2(), 162-56,  77, 4210752);
                    this.font.drawString(matrixstack, "" + CONTAINER.getPrizeCount3(), 162-56,  95, 4210752);
                }
            }
        } else {
            this.font.drawString(matrixstack, input.length() == 0 ? "0" : input, 110+3-56, 88+3, 4210752);
        }
    }

    /** Draws the background layer of this container (behind the items). */
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(getTexture());
        int i = (this.width  - this.xSize) / 2 - 56;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixstack, i, j, 0, 0, this.xSize+56, this.ySize);

        // Page Body
        if(activeInput == 0 && hasKey()){
            if(page == 0){ // PAGE: Settings
                if(CONTAINER.getSettingInfiniteToken())       this.blit(matrixstack,  i + 60, j + 30, 108, 244, 12, 12);
                if(CONTAINER.getSettingInfinitePrize())      this.blit(matrixstack,  i + 60, j + 43, 108, 244, 12, 12);
                if(CONTAINER.getSettingDropOnBreak())    this.blit(matrixstack,  i + 60, j + 56, 108, 244, 12, 12);
                if(CONTAINER.getSettingIndestructable()) this.blit(matrixstack,  i + 60, j + 69, 108, 244, 12, 12);
                if(hasReset) this.blit(matrixstack,  i + 60, j + 83, 84, 244, 12, 12);
                this.blit(matrixstack,  i + 60, j + 96, CONTAINER.getSettingAlternateColor() * 12, 244, 12, 12);
            } else if(page == 1){ // PAGE: Betting
                if(CONTAINER.getTransferTokenIN())  this.blit(matrixstack,  i +  61, j + 31,  40, 236, 40, 20); // TOKEN IN
                if(CONTAINER.getTransferTokenOUT()) this.blit(matrixstack,  i + 187, j + 31, 120, 236, 40, 20); // TOKEN OUT
                if(highlightIndex == 1) this.blit(matrixstack,  i + 193, j + 73, 176, 240, 16, 16); // MINIMUM LOWER
                if(highlightIndex == 2) this.blit(matrixstack,  i + 211, j + 73, 208, 240, 16, 16); // MINIMUM UPPER
                if(highlightIndex == 3) this.blit(matrixstack,  i + 193, j + 91, 176, 240, 16, 16); // MAXIMUM LOWER
                if(highlightIndex == 4) this.blit(matrixstack,  i + 211, j + 91, 208, 240, 16, 16); // MAXIMUM UPPER
            } else if(page == 2){ // Page: Reward
                if(CONTAINER.getTransferPrizeIN())  this.blit(matrixstack,  i +  61, j + 31,  40, 236, 40, 20); // TOKEN IN
                if(CONTAINER.getTransferPrizeOUT()) this.blit(matrixstack,  i + 187, j + 31, 120, 236, 40, 20); // TOKEN OUT

                if(highlightIndex ==  5) this.blit(matrixstack,  i + 193, j + 55, 176, 240, 16, 16); // REWARD 1 LOWER
                if(highlightIndex ==  6) this.blit(matrixstack,  i + 211, j + 55, 208, 240, 16, 16); // REWARD 1 UPPER
                if(highlightIndex ==  7) this.blit(matrixstack,  i + 193, j + 73, 176, 240, 16, 16); // REWARD 2 LOWER
                if(highlightIndex ==  8) this.blit(matrixstack,  i + 211, j + 73, 208, 240, 16, 16); // REWARD 2 UPPER
                if(highlightIndex ==  9) this.blit(matrixstack,  i + 193, j + 91, 176, 240, 16, 16); // REWARD 3 LOWER
                if(highlightIndex == 10) this.blit(matrixstack,  i + 211, j + 91, 208, 240, 16, 16); // REWARD 3 UPPER
                if(highlightIndex == 11) this.blit(matrixstack,  i +  79, j + 55, 240, 240, 16, 16); // SCORE 1 EDIT
                if(highlightIndex == 12) this.blit(matrixstack,  i +  79, j + 73, 240, 240, 16, 16); // SCORE 2 EDIT
                if(highlightIndex == 13) this.blit(matrixstack,  i +  79, j + 91, 240, 240, 16, 16); // SCORE 3 EDIT
                if(CONTAINER.getPrizeMode1()) this.blit(matrixstack,  i +  61, j + 55, 176, 224, 16, 16); // PRIZE 1 MODE
                if(CONTAINER.getPrizeMode2()) this.blit(matrixstack,  i +  61, j + 73, 176, 224, 16, 16); // PRIZE 2 MODE
                if(CONTAINER.getPrizeMode3()) this.blit(matrixstack,  i +  61, j + 91, 176, 224, 16, 16); // PRIZE 3 MODE
            }
        }

        if(highlightTimer > 0){
            highlightTimer--;
            if(highlightTimer == 0){
                highlightIndex = 0;
            }
        }
    }




    //----------------------------------------COMMAND----------------------------------------//

    /** ??? **/
    public void commandChangeBet(boolean isHigher, boolean isMaxBet){
        boolean send = false;
        if(isHigher && isMaxBet){
            CONTAINER.setBettingHigh(CONTAINER.getBettingHigh() + 1);
            send = true;
        }
        if(!isHigher && isMaxBet){
            if(CONTAINER.getBettingHigh() > 0){
                CONTAINER.setBettingHigh(CONTAINER.getBettingHigh() - 1);
                if(CONTAINER.getBettingLow() > CONTAINER.getBettingHigh()){
                    CONTAINER.setBettingLow(CONTAINER.getBettingHigh());
                }
                send = true;
            }
        }
        if(isHigher && !isMaxBet){
            CONTAINER.setBettingLow(CONTAINER.getBettingLow() + 1);
            if(CONTAINER.getBettingHigh() < CONTAINER.getBettingLow()){
                CONTAINER.setBettingHigh(CONTAINER.getBettingLow());
            }
            send = true;
        }
        if(!isHigher && !isMaxBet){
            if(CONTAINER.getBettingLow() > 0){
                CONTAINER.setBettingLow(CONTAINER.getBettingLow() - 1);
                send = true;
            }
        }
        if(send){
            sendPacket();
        }
    }

    /** ??? **/
    public void commandChangeReward(boolean isHigher, int index){
        boolean send = false;
        if(index == 0 &&  isHigher){                                       CONTAINER.setPrizeCount1(CONTAINER.getPrizeCount1() + 1); send = true; }
        if(index == 1 &&  isHigher){                                       CONTAINER.setPrizeCount2(CONTAINER.getPrizeCount2() + 1); send = true; }
        if(index == 2 &&  isHigher){                                       CONTAINER.setPrizeCount3(CONTAINER.getPrizeCount3() + 1); send = true; }
        if(index == 0 && !isHigher){ if(CONTAINER.getPrizeCount1() > 0){ CONTAINER.setPrizeCount1(CONTAINER.getPrizeCount1() - 1); send = true; } }
        if(index == 1 && !isHigher){ if(CONTAINER.getPrizeCount2() > 0){ CONTAINER.setPrizeCount2(CONTAINER.getPrizeCount2() - 1); send = true; } }
        if(index == 2 && !isHigher){ if(CONTAINER.getPrizeCount3() > 0){ CONTAINER.setPrizeCount3(CONTAINER.getPrizeCount3() - 1); send = true; } }
        if(send){ sendPacket(); }
    }

    /** ??? **/
    public void commandTransfer(boolean isToken, boolean isIN){
        if( isToken &&  isIN){
            CONTAINER.setTransferTokenIN(!CONTAINER.getTransferTokenIN());
            CONTAINER.setTransferTokenOUT(false);
            CONTAINER.setTransferPrizeIN(false);
            CONTAINER.setTransferPrizeOUT(false);
        }
        if( isToken && !isIN){
            CONTAINER.setTransferTokenIN(false);
            CONTAINER.setTransferTokenOUT(!CONTAINER.getTransferTokenOUT());
            CONTAINER.setTransferPrizeIN(false);
            CONTAINER.setTransferPrizeOUT(false);
        }
        if(!isToken &&  isIN){
            CONTAINER.setTransferTokenIN(false);
            CONTAINER.setTransferTokenOUT(false);
            CONTAINER.setTransferPrizeIN(!CONTAINER.getTransferPrizeIN());
            CONTAINER.setTransferPrizeOUT(false);
        }
        if(!isToken && !isIN){
            CONTAINER.setTransferTokenIN(false);
            CONTAINER.setTransferTokenOUT(false);
            CONTAINER.setTransferPrizeIN(false);
            CONTAINER.setTransferPrizeOUT(!CONTAINER.getTransferPrizeOUT());
        }
        sendPacket();
    }

    /** ??? **/
    public void commandToggleSettings(int settingID){
        if(settingID == 0){ if(CasinoKeeper.config_creative_token.get()){  CONTAINER.setSettingInfiniteToken(!CONTAINER.getSettingInfiniteToken());             } }
        if(settingID == 1){ if(CasinoKeeper.config_creative_reward.get()){ CONTAINER.setSettingInfinitePrize(!CONTAINER.getSettingInfinitePrize());           } }
        if(settingID == 2){                                                CONTAINER.setSettingDropOnBreak(!CONTAINER.getSettingDropOnBreak());       }
        if(settingID == 3){                                                CONTAINER.setSettingIndestructable(!CONTAINER.getSettingIndestructable()); }
        if(settingID == 4){                                                CONTAINER.setSettingAlternateColor((CONTAINER.getSettingAlternateColor() + 1) % 6);  }
        sendPacket();
    }

    /** ??? **/
    public void commandChangePage(int index){
        if(CONTAINER.getTransferTokenIN() || CONTAINER.getTransferTokenOUT() || CONTAINER.getTransferPrizeIN() || CONTAINER.getTransferPrizeOUT()){
            CONTAINER.setTransferTokenIN(false);
            CONTAINER.setTransferTokenOUT(false);
            CONTAINER.setTransferPrizeIN(false);
            CONTAINER.setTransferPrizeOUT(false);
            sendPacket();
        }
        page = index;
    }

    /** ??? **/
    public void commandResetGameState(){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, -2, CONTAINER.pos()));
        hasReset = true;
    }

    /** ??? **/
    public void commandSetScore(int index, int score){
        if(index == 0){ CONTAINER.setPrizeScore1(score); }
        if(index == 1){ CONTAINER.setPrizeScore2(score); }
        if(index == 2){ CONTAINER.setPrizeScore3(score); }
        sendPacket();
    }

    /** ??? **/
    public void commandTogglePrize(int index){
        if(index == 0){ CONTAINER.setPrizeMode1(!CONTAINER.getPrizeMode1()); }
        if(index == 1){ CONTAINER.setPrizeMode2(!CONTAINER.getPrizeMode2()); }
        if(index == 2){ CONTAINER.setPrizeMode3(!CONTAINER.getPrizeMode3()); }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    private ResourceLocation getTexture(){
        if(!hasKey()) return GUI_TEXTURE0;
        if(activeInput != 0) return GUI_TEXTURE4;
        if(page == 2) return GUI_TEXTURE3;
        if(page == 1) return GUI_TEXTURE2;
        return GUI_TEXTURE1;
    }

    protected void highlight(int index){
        highlightTimer = 10;
        highlightIndex = index;
    }

    /** Checks if mouse is inside a rectangle **/
    protected boolean mouseRect(int x, int y, int width, int height, double mouseX, double mouseY){
        if(guiLeft + x - 56 < mouseX && mouseX < guiLeft + x + width - 56){
            return guiTop + y < mouseY && mouseY < guiTop + y + height;
        }
        return false;
    }

    protected void sendPacket(){
        CasinoPacketHandler.sendToServer(new MessageSettingServer(CONTAINER.pos(), new int[]{
                //CONTAINER.getStorageToken(),
                //CONTAINER.getStorageReward(),
                CONTAINER.getBettingLow(),
                CONTAINER.getBettingHigh(),
                CONTAINER.getPrizeScore1(),
                CONTAINER.getPrizeScore2(),
                CONTAINER.getPrizeScore3(),
                CONTAINER.getPrizeCount1(),
                CONTAINER.getPrizeCount2(),
                CONTAINER.getPrizeCount3(),
                CONTAINER.getPrizeMode1() ? 1 : 0,
                CONTAINER.getPrizeMode2() ? 1 : 0,
                CONTAINER.getPrizeMode3() ? 1 : 0,
                CONTAINER.getTransferTokenIN() ? 1 : 0,
                CONTAINER.getTransferTokenOUT() ? 1 : 0,
                CONTAINER.getTransferPrizeIN() ? 1 : 0,
                CONTAINER.getTransferPrizeOUT() ? 1 : 0,
                CONTAINER.getSettingInfiniteToken() ? 1 : 0,
                CONTAINER.getSettingInfinitePrize() ? 1 : 0,
                CONTAINER.getSettingDropOnBreak() ? 1 : 0,
                CONTAINER.getSettingIndestructable() ? 1 : 0,
                CONTAINER.getSettingAlternateColor()
        } ));
    }

    protected boolean hasKey(){
        return CONTAINER.hasKey();
    }

}
