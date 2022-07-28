package mod.casinocraft.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.Config;
import mod.casinocraft.CasinoCraft;
import mod.casinocraft.menu.MenuMachine;
import mod.casinocraft.network.MessageSettingServer;
import mod.casinocraft.network.MessageStateServer;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.lucky77.screen.ScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;

import static mod.casinocraft.util.KeyMap.*;

public class ScreenMachine extends ScreenBase<MenuMachine> {

    private static final ResourceLocation GUI_TEXTURE0 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory0.png");
    private static final ResourceLocation GUI_TEXTURE1 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory1.png");
    private static final ResourceLocation GUI_TEXTURE2 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory2.png");
    private static final ResourceLocation GUI_TEXTURE3 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory3.png");
    private static final ResourceLocation GUI_TEXTURE4 = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory4.png");

    private int page = 0;
    private int activeInput = 0;
    private String input = "";
    private boolean hasReset = false;
    private int highlightIndex = 0;
    private int highlightTimer = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMachine(MenuMachine container, Inventory player, Component name) {
        super(container, player, name, 176, 204);
    }





    //----------------------------------------INPUT----------------------------------------//

    /** Overwritten keyPressed function (only used for rerouting keyCode) */
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        keyTyped(keyCode);
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            this.onClose();
            return true;
        } else {
            boolean handled = this.checkHotbarKeyPressed(keyCode, scanCode);// Forge MC-146650: Needs to return true when the key is handled
            if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                if (this.minecraft.options.keyPickItem.isActiveAndMatches(mouseKey)) {
                    this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 0, ClickType.CLONE);
                    handled = true;
                } else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
                    this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
                    handled = true;
                }
            } else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
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
            if(mouseRect( 58 - 56, 0, 56, 24, mouseX, mouseY)) { commandChangePage(0); }
            if(mouseRect(116 - 56, 0, 56, 24, mouseX, mouseY)) { commandChangePage(1); }
            if(mouseRect(174 - 56, 0, 56, 24, mouseX, mouseY)) { commandChangePage(2); }

            // Page Body
            if(page == 0){ // PAGE: Settings
                if(mouseRect(60 - 56, 30, 12, 12, mouseX, mouseY)) { commandToggleSettings(0); } // SETTING 1
                if(mouseRect(60 - 56, 43, 12, 12, mouseX, mouseY)) { commandToggleSettings(1); } // SETTING 2
                if(mouseRect(60 - 56, 56, 12, 12, mouseX, mouseY)) { commandToggleSettings(2); } // SETTING 3
                if(mouseRect(60 - 56, 69, 12, 12, mouseX, mouseY)) { commandToggleSettings(3); } // SETTING 4
                if(mouseRect(60 - 56, 83, 12, 12, mouseX, mouseY)) { commandResetGameState();          } // SETTING RESET
                if(mouseRect(60 - 56, 96, 12, 12, mouseX, mouseY)) { commandToggleSettings(4); } // SETTING COLOR
            } else if(page == 1){ // PAGE: Betting
                if(mouseRect( 61 - 56,  31, 40, 20, mouseX, mouseY)) { commandTransfer(true, true); } // TRANSFER IN
                if(mouseRect(178 - 56,  31, 40, 20, mouseX, mouseY)) { commandTransfer(true, false); } // TRANSFER OUT
                if(mouseRect(193 - 56, 73, 16, 16, mouseX, mouseY)) { highlight(1); commandChangeBet(false, false); } // MINIMUM LOWER
                if(mouseRect(211 - 56, 73, 16, 16, mouseX, mouseY)) { highlight(2); commandChangeBet(true, false); } // MINIMUM UPPER
                if(mouseRect(193 - 56, 91, 16, 16, mouseX, mouseY)) { highlight(3); commandChangeBet(false, true); } // MAXIMUM LOWER
                if(mouseRect(211 - 56, 91, 16, 16, mouseX, mouseY)) { highlight(4); commandChangeBet(true, true); } // MAXIMUM UPPER
            } else if(page == 2){ // Page: Reward
                if(mouseRect( 61 - 56,  31, 40, 20, mouseX, mouseY)) { commandTransfer(false, true); } // TRANSFER IN
                if(mouseRect(178 - 56,  31, 40, 20, mouseX, mouseY)) { commandTransfer(false, false); } // TRANSFER OUT

                if(mouseRect(193 - 56, 55, 16, 16, mouseX, mouseY)) { highlight( 5); commandChangeReward(false, 0); } // REWARD 1 LOWER
                if(mouseRect(211 - 56, 55, 16, 16, mouseX, mouseY)) { highlight( 6); commandChangeReward(true, 0); } // REWARD 1 UPPER
                if(mouseRect(193 - 56, 73, 16, 16, mouseX, mouseY)) { highlight( 7); commandChangeReward(false, 1); } // REWARD 2 LOWER
                if(mouseRect(211 - 56, 73, 16, 16, mouseX, mouseY)) { highlight( 8); commandChangeReward(true, 1); } // REWARD 2 UPPER
                if(mouseRect(193 - 56, 91, 16, 16, mouseX, mouseY)) { highlight( 9); commandChangeReward(false, 2); } // REWARD 3 LOWER
                if(mouseRect(211 - 56, 91, 16, 16, mouseX, mouseY)) { highlight(10); commandChangeReward(true, 2); } // REWARD 3 UPPER

                if(mouseRect(79 - 56, 55, 16, 16, mouseX, mouseY)) { input = ""; activeInput = 1; } // SCORE 1 EDIT
                if(mouseRect(79 - 56, 73, 16, 16, mouseX, mouseY)) { input = ""; activeInput = 2; } // SCORE 2 EDIT
                if(mouseRect(79 - 56, 91, 16, 16, mouseX, mouseY)) { input = ""; activeInput = 3; } // SCORE 3 EDIT

                if(mouseRect(61 - 56, 55, 16, 16, mouseX, mouseY)) { commandTogglePrize(0); } // PRIZE 1 TOGGLE
                if(mouseRect(61 - 56, 73, 16, 16, mouseX, mouseY)) { commandTogglePrize(1); } // PRIZE 2 TOGGLE
                if(mouseRect(61 - 56, 91, 16, 16, mouseX, mouseY)) { commandTogglePrize(2); } // PRIZE 3 TOGGLE
            }
        }
        return false;
    }





    //----------------------------------------DRAW----------------------------------------//

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void renderLabels(PoseStack matrixstack, int mouseX, int mouseY){

        // ----- Page Body ----- //
        if(activeInput == 0){
            if(hasKey()){
                if(page == 1){ // PAGE: Betting
                    this.itemRenderer.renderGuiItem(menu.getItemToken(), 107-56, 33);
                    String text = menu.getStorageToken() == 0 ? "EMPTY" : menu.getSettingInfiniteToken() ? "x(INFINITE)" : "x " + menu.getStorageToken();
                    this.font.draw(matrixstack, text, 107+20-56-2, 33+4, 4210752);
                    this.font.draw(matrixstack, "" + menu.getBettingLow(), 162-56, 77, 4210752);
                    this.font.draw(matrixstack, "" + menu.getBettingHigh(), 162-56, 95, 4210752);
                } else if(page == 2){ // Page: Reward
                    this.itemRenderer.renderGuiItem(menu.getItemPrize(), 107-56, 33);
                    String text = menu.getStoragePrize() == 0 ? "EMPTY" : menu.getSettingInfinitePrize() ? "x(INFINITE)" : "x " + menu.getStoragePrize();
                    this.font.draw(matrixstack, text, 107+20-56-2, 33+4, 4210752);
                    this.font.draw(matrixstack, "" + menu.getPrizeScore1(),  106-56,  59, 4210752);
                    this.font.draw(matrixstack, "" + menu.getPrizeScore2(),  106-56,  77, 4210752);
                    this.font.draw(matrixstack, "" + menu.getPrizeScore3(),  106-56,  95, 4210752);
                    this.font.draw(matrixstack, "" + menu.getPrizeCount1(), 162-56,  59, 4210752);
                    this.font.draw(matrixstack, "" + menu.getPrizeCount2(), 162-56,  77, 4210752);
                    this.font.draw(matrixstack, "" + menu.getPrizeCount3(), 162-56,  95, 4210752);
                }
            }
        } else {
            this.font.draw(matrixstack, input.length() == 0 ? "0" : input, 110+3-56, 88+3, 4210752);
        }
    }

    /** Draws the background layer of this container (behind the items). */
    protected void renderBg(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getTexture());
        int i = (this.width  - this.imageWidth) / 2 - 56;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixstack, i, j, 0, 0, this.imageWidth+56, this.imageHeight);

        // ----- Page Body ----- //
        if(activeInput == 0 && hasKey()){
            if(page == 0){ // PAGE: Settings
                if(menu.getSettingInfiniteToken())       this.blit(matrixstack,  i + 60, j + 30, 108, 244, 12, 12);
                if(menu.getSettingInfinitePrize())      this.blit(matrixstack,  i + 60, j + 43, 108, 244, 12, 12);
                if(menu.getSettingDropOnBreak())    this.blit(matrixstack,  i + 60, j + 56, 108, 244, 12, 12);
                if(menu.getSettingIndestructable()) this.blit(matrixstack,  i + 60, j + 69, 108, 244, 12, 12);
                if(hasReset) this.blit(matrixstack,  i + 60, j + 83, 84, 244, 12, 12);
                this.blit(matrixstack,  i + 60, j + 96, menu.getSettingAlternateColor() * 12, 244, 12, 12);
            } else if(page == 1){ // PAGE: Betting
                if(menu.getTransferTokenIN())  this.blit(matrixstack,  i +  61, j + 31,  40, 236, 40, 20); // TOKEN IN
                if(menu.getTransferTokenOUT()) this.blit(matrixstack,  i + 187, j + 31, 120, 236, 40, 20); // TOKEN OUT
                if(highlightIndex == 1) this.blit(matrixstack,  i + 193, j + 73, 176, 240, 16, 16); // MINIMUM LOWER
                if(highlightIndex == 2) this.blit(matrixstack,  i + 211, j + 73, 208, 240, 16, 16); // MINIMUM UPPER
                if(highlightIndex == 3) this.blit(matrixstack,  i + 193, j + 91, 176, 240, 16, 16); // MAXIMUM LOWER
                if(highlightIndex == 4) this.blit(matrixstack,  i + 211, j + 91, 208, 240, 16, 16); // MAXIMUM UPPER
            } else if(page == 2){ // Page: Reward
                if(menu.getTransferPrizeIN())  this.blit(matrixstack,  i +  61, j + 31,  40, 236, 40, 20); // TOKEN IN
                if(menu.getTransferPrizeOUT()) this.blit(matrixstack,  i + 187, j + 31, 120, 236, 40, 20); // TOKEN OUT

                if(highlightIndex ==  5) this.blit(matrixstack,  i + 193, j + 55, 176, 240, 16, 16); // REWARD 1 LOWER
                if(highlightIndex ==  6) this.blit(matrixstack,  i + 211, j + 55, 208, 240, 16, 16); // REWARD 1 UPPER
                if(highlightIndex ==  7) this.blit(matrixstack,  i + 193, j + 73, 176, 240, 16, 16); // REWARD 2 LOWER
                if(highlightIndex ==  8) this.blit(matrixstack,  i + 211, j + 73, 208, 240, 16, 16); // REWARD 2 UPPER
                if(highlightIndex ==  9) this.blit(matrixstack,  i + 193, j + 91, 176, 240, 16, 16); // REWARD 3 LOWER
                if(highlightIndex == 10) this.blit(matrixstack,  i + 211, j + 91, 208, 240, 16, 16); // REWARD 3 UPPER
                if(highlightIndex == 11) this.blit(matrixstack,  i +  79, j + 55, 240, 240, 16, 16); // SCORE 1 EDIT
                if(highlightIndex == 12) this.blit(matrixstack,  i +  79, j + 73, 240, 240, 16, 16); // SCORE 2 EDIT
                if(highlightIndex == 13) this.blit(matrixstack,  i +  79, j + 91, 240, 240, 16, 16); // SCORE 3 EDIT
                if(menu.getPrizeMode1()) this.blit(matrixstack,  i +  61, j + 55, 176, 224, 16, 16); // PRIZE 1 MODE
                if(menu.getPrizeMode2()) this.blit(matrixstack,  i +  61, j + 73, 176, 224, 16, 16); // PRIZE 2 MODE
                if(menu.getPrizeMode3()) this.blit(matrixstack,  i +  61, j + 91, 176, 224, 16, 16); // PRIZE 3 MODE
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
            menu.setBettingHigh(menu.getBettingHigh() + 1);
            send = true;
        }
        if(!isHigher && isMaxBet){
            if(menu.getBettingHigh() > 0){
                menu.setBettingHigh(menu.getBettingHigh() - 1);
                if(menu.getBettingLow() > menu.getBettingHigh()){
                    menu.setBettingLow(menu.getBettingHigh());
                }
                send = true;
            }
        }
        if(isHigher && !isMaxBet){
            menu.setBettingLow(menu.getBettingLow() + 1);
            if(menu.getBettingHigh() < menu.getBettingLow()){
                menu.setBettingHigh(menu.getBettingLow());
            }
            send = true;
        }
        if(!isHigher && !isMaxBet){
            if(menu.getBettingLow() > 0){
                menu.setBettingLow(menu.getBettingLow() - 1);
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
        if(index == 0 &&  isHigher){                                       menu.setPrizeCount1(menu.getPrizeCount1() + 1); send = true; }
        if(index == 1 &&  isHigher){                                       menu.setPrizeCount2(menu.getPrizeCount2() + 1); send = true; }
        if(index == 2 &&  isHigher){                                       menu.setPrizeCount3(menu.getPrizeCount3() + 1); send = true; }
        if(index == 0 && !isHigher){ if(menu.getPrizeCount1() > 0){ menu.setPrizeCount1(menu.getPrizeCount1() - 1); send = true; } }
        if(index == 1 && !isHigher){ if(menu.getPrizeCount2() > 0){ menu.setPrizeCount2(menu.getPrizeCount2() - 1); send = true; } }
        if(index == 2 && !isHigher){ if(menu.getPrizeCount3() > 0){ menu.setPrizeCount3(menu.getPrizeCount3() - 1); send = true; } }
        if(send){ sendPacket(); }
    }

    /** ??? **/
    public void commandTransfer(boolean isToken, boolean isIN){
        if( isToken &&  isIN){
            menu.setTransferTokenIN(!menu.getTransferTokenIN());
            menu.setTransferTokenOUT(false);
            menu.setTransferPrizeIN(false);
            menu.setTransferPrizeOUT(false);
        }
        if( isToken && !isIN){
            menu.setTransferTokenIN(false);
            menu.setTransferTokenOUT(!menu.getTransferTokenOUT());
            menu.setTransferPrizeIN(false);
            menu.setTransferPrizeOUT(false);
        }
        if(!isToken &&  isIN){
            menu.setTransferTokenIN(false);
            menu.setTransferTokenOUT(false);
            menu.setTransferPrizeIN(!menu.getTransferPrizeIN());
            menu.setTransferPrizeOUT(false);
        }
        if(!isToken && !isIN){
            menu.setTransferTokenIN(false);
            menu.setTransferTokenOUT(false);
            menu.setTransferPrizeIN(false);
            menu.setTransferPrizeOUT(!menu.getTransferPrizeOUT());
        }
        sendPacket();
    }

    /** ??? **/
    public void commandToggleSettings(int settingID){
        if(settingID == 0){ if(Config.CONFIG.config_creative_token.get()){  menu.setSettingInfiniteToken(!menu.getSettingInfiniteToken());             } }
        if(settingID == 1){ if(Config.CONFIG.config_creative_reward.get()){ menu.setSettingInfinitePrize(!menu.getSettingInfinitePrize());           } }
        if(settingID == 2){                                                       menu.setSettingDropOnBreak(!menu.getSettingDropOnBreak());       }
        if(settingID == 3){                                                       menu.setSettingIndestructable(!menu.getSettingIndestructable()); }
        if(settingID == 4){                                                       menu.setSettingAlternateColor((menu.getSettingAlternateColor() + 1) % 6);  }
        sendPacket();
    }

    /** ??? **/
    public void commandChangePage(int index){
        if(menu.getTransferTokenIN() || menu.getTransferTokenOUT() || menu.getTransferPrizeIN() || menu.getTransferPrizeOUT()){
            menu.setTransferTokenIN(false);
            menu.setTransferTokenOUT(false);
            menu.setTransferPrizeIN(false);
            menu.setTransferPrizeOUT(false);
            sendPacket();
        }
        page = index;
    }

    /** ??? **/
    public void commandResetGameState(){
        CasinoPacketHandler.sendToServer(new MessageStateServer(true, -2, menu.pos()));
        hasReset = true;
    }

    /** ??? **/
    public void commandSetScore(int index, int score){
        if(index == 0){ menu.setPrizeScore1(score); }
        if(index == 1){ menu.setPrizeScore2(score); }
        if(index == 2){ menu.setPrizeScore3(score); }
        sendPacket();
    }

    /** ??? **/
    public void commandTogglePrize(int index){
        if(index == 0){ menu.setPrizeMode1(!menu.getPrizeMode1()); }
        if(index == 1){ menu.setPrizeMode2(!menu.getPrizeMode2()); }
        if(index == 2){ menu.setPrizeMode3(!menu.getPrizeMode3()); }
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

    protected void sendPacket(){
        CasinoPacketHandler.sendToServer(new MessageSettingServer(menu.pos(), new int[]{
                //menu.getStorageToken(),
                //menu.getStorageReward(),
                menu.getBettingLow(),
                menu.getBettingHigh(),
                menu.getPrizeScore1(),
                menu.getPrizeScore2(),
                menu.getPrizeScore3(),
                menu.getPrizeCount1(),
                menu.getPrizeCount2(),
                menu.getPrizeCount3(),
                menu.getPrizeMode1() ? 1 : 0,
                menu.getPrizeMode2() ? 1 : 0,
                menu.getPrizeMode3() ? 1 : 0,
                menu.getTransferTokenIN() ? 1 : 0,
                menu.getTransferTokenOUT() ? 1 : 0,
                menu.getTransferPrizeIN() ? 1 : 0,
                menu.getTransferPrizeOUT() ? 1 : 0,
                menu.getSettingInfiniteToken() ? 1 : 0,
                menu.getSettingInfinitePrize() ? 1 : 0,
                menu.getSettingDropOnBreak() ? 1 : 0,
                menu.getSettingIndestructable() ? 1 : 0,
                menu.getSettingAlternateColor()
        } ));
    }

    protected boolean hasKey(){
        return menu.hasKey();
    }



}
