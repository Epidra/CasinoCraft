package mod.casinocraft.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
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

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory.png");

    private int page           = 0;
    private int activeInput    = 0;
    private String input       = "";
    private boolean hasReset   = false;
    private int highlightIndex = 0;
    private int highlightTimer = 0;
    private int blinking       = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMachine(MenuMachine container, Inventory player, Component name) {
        super(container, player, name, 176, 178);
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
                page = 2;
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

            // ----- Page Header ----- //
            if(mouseRect(  2, -19, 56, 24, mouseX, mouseY)) {                     highlight(18); commandChangePage(0);   }
            if(mouseRect( 58, -19, 56, 24, mouseX, mouseY)) { if(menu.hasKey() ){ highlight(19); commandChangePage(1); } }
            if(mouseRect(114, -19, 56, 24, mouseX, mouseY)) { if(hasHighscore()){ highlight(20); commandChangePage(2); } }

            // ----- Page 1 - Settings ----- //
            if(page == 0){
                if(mouseRect(4, 10, 12, 12, mouseX, mouseY)) { commandToggleSettings(0); } // SETTING 1
                if(mouseRect(4, 23, 12, 12, mouseX, mouseY)) { commandToggleSettings(1); } // SETTING 2
                if(mouseRect(4, 36, 12, 12, mouseX, mouseY)) { commandToggleSettings(2); } // SETTING 3
                if(mouseRect(4, 49, 12, 12, mouseX, mouseY)) { commandToggleSettings(3); } // SETTING 4
                if(mouseRect(4, 63, 12, 12, mouseX, mouseY)) { commandResetGameState( ); } // SETTING RESET
                if(mouseRect(4, 76, 12, 12, mouseX, mouseY)) { commandToggleSettings(4); } // SETTING COLOR
            }

            // ----- Page 2 - Token ----- //
            if(  page == 1){
                if(mouseRect(  5, 11, 40, 20, mouseX, mouseY)) { highlight(14); commandTransfer(true, true ); } // TRANSFER IN
                if(mouseRect(122, 11, 40, 20, mouseX, mouseY)) { highlight(15); commandTransfer(true, false); } // TRANSFER OUT
            } if(page == 1 && menu.hasToken()){
                if(mouseRect(137, 53, 16, 16, mouseX, mouseY)) { highlight( 1); commandChangeBet(-1, false); } // MINIMUM LOWER
                if(mouseRect(155, 53, 16, 16, mouseX, mouseY)) { highlight( 2); commandChangeBet( 1, false); } // MINIMUM UPPER
                if(mouseRect(137, 71, 16, 16, mouseX, mouseY)) { highlight( 3); commandChangeBet(-1, true ); } // MAXIMUM LOWER
                if(mouseRect(155, 71, 16, 16, mouseX, mouseY)) { highlight( 4); commandChangeBet( 1, true ); } // MAXIMUM UPPER
            }

            // ----- Page 3 - Reward ----- //
            if(  page == 2){
                if(mouseRect(  5, 11, 40, 20, mouseX, mouseY)) { highlight(14); commandTransfer(false,  true); } // TRANSFER IN
                if(mouseRect(122, 11, 40, 20, mouseX, mouseY)) { highlight(15); commandTransfer(false, false); } // TRANSFER OUT
            } if(page == 2 && menu.hasReward()){
                if(mouseRect(137, 35, 16, 16, mouseX, mouseY)) { highlight( 5); commandChangeReward(-1, 0); } // REWARD 1 LOWER
                if(mouseRect(155, 35, 16, 16, mouseX, mouseY)) { highlight( 6); commandChangeReward( 1, 0); } // REWARD 1 UPPER
                if(mouseRect(137, 53, 16, 16, mouseX, mouseY)) { highlight( 7); commandChangeReward(-1, 1); } // REWARD 2 LOWER
                if(mouseRect(155, 53, 16, 16, mouseX, mouseY)) { highlight( 8); commandChangeReward( 1, 1); } // REWARD 2 UPPER
                if(mouseRect(137, 71, 16, 16, mouseX, mouseY)) { highlight( 9); commandChangeReward(-1, 2); } // REWARD 3 LOWER
                if(mouseRect(155, 71, 16, 16, mouseX, mouseY)) { highlight(10); commandChangeReward( 1, 2); } // REWARD 3 UPPER
                if(mouseRect( 23, 35, 16, 16, mouseX, mouseY)) { page = 3; input = ""; activeInput = 1;     } // SCORE 1 EDIT
                if(mouseRect( 23, 53, 16, 16, mouseX, mouseY)) { page = 3; input = ""; activeInput = 2;     } // SCORE 2 EDIT
                if(mouseRect( 23, 71, 16, 16, mouseX, mouseY)) { page = 3; input = ""; activeInput = 3;     } // SCORE 3 EDIT
                if(mouseRect(  5, 35, 16, 16, mouseX, mouseY)) { commandTogglePrize(0);                     } // PRIZE 1 TOGGLE
                if(mouseRect(  5, 53, 16, 16, mouseX, mouseY)) { commandTogglePrize(1);                     } // PRIZE 2 TOGGLE
                if(mouseRect(  5, 71, 16, 16, mouseX, mouseY)) { commandTogglePrize(2);                     } // PRIZE 3 TOGGLE
            }
        } return false;
    }





    //----------------------------------------DRAW---OVERLAY----------------------------------------//

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void renderLabels(PoseStack matrix, int mouseX, int mouseY){
        if(page == 2 && (!menu.hasKey() || !hasHighscore()))   page = 0;
        if(page == 1 && (!menu.hasKey() || !menu.hasModule())) page = 0;
        blinking = (blinking + 1) % 32;

        // ----- Page 1 - Settings ----- //
        if(  page == 0 && !menu.hasKey()){
            drawFontCenter(matrix, "VISIT MODPAGE FOR INSTRUCTIONS", 88, 16);
            if(blinking / 16 == 1) drawFontCenter(matrix, "MISSING  KEY  ITEM  ",           88, 74);
        } if(page == 0 && menu.hasKey() && Config.CONFIG.config_creative_token.get() && isOP()){
            drawFont(matrix,       "INFINITE TOKEN STORAGE",         18, 13);
        } if(page == 0 && menu.hasKey() && Config.CONFIG.config_creative_reward.get() && isOP()){
            drawFont(matrix,       "INFINITE REWARD STORAGE",        18, 26);
        } if(page == 0 && menu.hasKey()){
            drawFont(matrix,       "DROP ITEMS ON BREAK",            18, 39);
            drawFont(matrix,       "INDESTRUCTABLE BLOCK",           18, 52);
        } if(page == 0 && menu.hasKey() && !menu.hasModule()){
            if(blinking / 16 == 1) drawFontCenter(matrix, "MISSING  MODULE  ITEM  ",        88, 74);
        } if(page == 0 && menu.hasKey() && menu.hasModule()){
            drawFont(matrix,       "RESET GAME",                     18, 66);
            drawFont(matrix,       "ALTERNATE COLOR VARIANT",        18, 79);
        }

        // ----- Page 2 - Token ----- //
        if(  page == 1){
            String text = menu.getStorageToken() == 0 ? "EMPTY" : menu.getSettingInfiniteToken() ? "x(INFINITE)" : "x " + menu.getStorageToken();
            this.itemRenderer.renderGuiItem(matrix, menu.getItemToken(),  51, 13         );
            this.font.draw(matrix, text,                          69, 17, 4210752);
        } if(page == 1 && menu.hasToken() && !hasGambling()){
            drawFontInvert(matrix, "AMOUNT",                     102, 57         );
            this.font.draw(matrix, "" + menu.getBettingLow(),    108, 57, 4210752);
        } if(page == 1 && menu.hasToken() && hasGambling()){
            drawFontInvert(matrix, "MINIMUM",                    102, 57         );
            drawFontInvert(matrix, "MAXIMUM",                    102, 75         );
            this.font.draw(matrix, "" + menu.getBettingLow(),    108, 57, 4210752);
            this.font.draw(matrix, "" + menu.getBettingHigh(),   108, 75, 4210752);
        }

        // ----- Page 3 - Reward ----- //
        if(  page == 2){
            String text = menu.getStoragePrize() == 0 ? "EMPTY" : menu.getSettingInfinitePrize() ? "x(INFINITE)" : "x " + menu.getStoragePrize();
            this.itemRenderer.renderGuiItem(matrix, menu.getItemPrize(), 51, 13         );
            this.font.draw(matrix, text,                         69, 17, 4210752);
        } if(page == 2 && menu.hasReward()){
            this.font.draw(matrix, "" + menu.getPrizeScore1(),   52, 39, 4210752);
            this.font.draw(matrix, "" + menu.getPrizeScore2(),   52, 57, 4210752);
            this.font.draw(matrix, "" + menu.getPrizeScore3(),   52, 75, 4210752);
            this.font.draw(matrix, "" + menu.getPrizeCount1(),  108, 39, 4210752);
            this.font.draw(matrix, "" + menu.getPrizeCount2(),  108, 57, 4210752);
            this.font.draw(matrix, "" + menu.getPrizeCount3(),  108, 75, 4210752);
        }

        // ----- Page 4 - Input ----- //
        if(  page == 3){
            String text = input.length() == 0 ? "0" : input;
            int w = this.font.width(text) / 2;
            drawFontCenter(matrix, "INPUT NUMBER",          88,   16         );
            drawFontCenter(matrix, "PRESS ENTER TO SUBMIT", 88,   32         );
            this.font.draw(matrix,  text,                   88-w, 71, 4210752);
        }
    }





    //----------------------------------------DRAW---BACKGROUND----------------------------------------//

    /** Draws the background layer of this container (behind the items). */
    protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int posX = (this.width  - this.imageWidth      ) / 2;
        int posY = (this.height - this.imageHeight + 12) / 2;

        // ----- Background ----- //
        this.blit(matrix, posX,       posY,        0,   0, 176, 178); // Background
        this.blit(matrix, posX -  56, posY -  1,   0, 180,  54,  28); // Slot 1
        this.blit(matrix, posX -  56, posY + 29,   0, 180,  54,  28); // Slot 2
        this.blit(matrix, posX -  56, posY + 59,   0, 180,  54,  28); // Slot 3
        this.blit(matrix, posX +   2, posY - 26, 200, 216,  56,  24); // Header 1
        this.blit(matrix, posX +  60, posY - 26, 200, 216,  56,  24); // Header 2
        this.blit(matrix, posX + 118, posY - 26, 200, 216,  56,  24); // Header 3

        // ----- Slots ----- //
        if(page < 3 && activeInput == 0                 ) this.blit(matrix, posX - 56, posY -  1,   0, 208, 54, 28); // Slot 1
        if(page < 3 && menu.hasKey()                    ) this.blit(matrix, posX - 56, posY + 29,  54, 208, 54, 28); // Slot 2
        if(page < 3 && menu.hasKey() && menu.hasModule()) this.blit(matrix, posX - 56, posY + 59, 108, 208, 54, 28); // Slot 3

        // ----- Header ----- //
        if(page < 3 && menu.hasKey() && page == 0                    ) this.blit(matrix, posX +   2, posY - 26, 200, highlightIndex == 18 ?  48 :  24, 56, 24); // Header 1
        if(page < 3 && menu.hasKey() && page != 0                    ) this.blit(matrix, posX +   2, posY - 26, 200,                                0, 56, 24); // Header 1
        if(page < 3 && menu.hasKey() && page == 1 && menu.hasModule()) this.blit(matrix, posX +  60, posY - 26, 200, highlightIndex == 19 ? 120 :  96, 56, 24); // Header 2
        if(page < 3 && menu.hasKey() && page != 1 && menu.hasModule()) this.blit(matrix, posX +  60, posY - 26, 200,                               72, 56, 24); // Header 2
        if(page < 3 && menu.hasKey() && page == 2 && hasHighscore()  ) this.blit(matrix, posX + 118, posY - 26, 200, highlightIndex == 20 ? 192 : 168, 56, 24); // Header 3
        if(page < 3 && menu.hasKey() && page != 2 && hasHighscore()  ) this.blit(matrix, posX + 118, posY - 26, 200,                              144, 56, 24); // Header 3

        // ----- Page 1 - Settings ----- //
        if(  page == 0 && menu.hasKey()){
            this.blit(matrix,  posX + 4, posY +  4, 188,                                        72, 12, 12); // Empty Button
            this.blit(matrix,  posX + 4, posY + 17, 188,                                        72, 12, 12); // Empty Button
            this.blit(matrix,  posX + 4, posY + 30, 188, menu.getSettingDropOnBreak()    ? 60 : 24, 12, 12); // Drop Items on Break
            this.blit(matrix,  posX + 4, posY + 43, 188, menu.getSettingIndestructable() ? 60 : 24, 12, 12); // Indestructible Block
        } if(page == 0 && menu.hasKey() && Config.CONFIG.config_creative_token.get() && isOP()){
            this.blit(matrix,  posX + 4, posY +  4, 188, menu.getSettingInfiniteToken()  ? 60 : 24, 12, 12); // Infinite Tokens
        } if(page == 0 && menu.hasKey() && Config.CONFIG.config_creative_reward.get() && isOP()){
            this.blit(matrix,  posX + 4, posY + 17, 188, menu.getSettingInfinitePrize()  ? 60 : 24, 12, 12); // Infinite Rewards
        } if(page == 0 && menu.hasKey() && menu.hasModule()){
            this.blit(matrix,  posX + 4, posY + 57, 188, hasReset ? 12 : 0,                         12, 12); // Reset MiniGame
            this.blit(matrix,  posX + 4, posY + 70, 176, menu.getSettingAlternateColor() * 12,      12, 12); // Color Palette
        }

        // ----- Page 2 - Token ----- //
        if(  page == 1) {
            this.blit(matrix, posX +  49, posY +  5,                               55, 187, 78, 20); // FIELD ITEMS
            this.blit(matrix, posX +   5, posY +  5, highlightIndex == 14 ?  40 :   0, 236, 40, 20); // TOKEN IN
            this.blit(matrix, posX + 131, posY +  5, highlightIndex == 15 ? 120 :  80, 236, 40, 20); // TOKEN OUT
        } if(page == 1 && menu.hasToken()){
            this.blit(matrix, posX + 104, posY + 49,                              135, 181, 24, 12); // FIELD MINIMUM
            this.blit(matrix, posX + 137, posY + 47, highlightIndex ==  1 ? 176 : 160, 240, 16, 16); // MINIMUM LOWER
            this.blit(matrix, posX + 155, posY + 47, highlightIndex ==  2 ? 208 : 192, 240, 16, 16); // MINIMUM UPPER
        } if(page == 1 && menu.hasToken() && hasGambling()){
            this.blit(matrix, posX + 104, posY + 67,                              135, 181, 24, 12); // FIELD MAXIMUM
            this.blit(matrix, posX + 137, posY + 65, highlightIndex ==  3 ? 176 : 160, 240, 16, 16); // MAXIMUM LOWER
            this.blit(matrix, posX + 155, posY + 65, highlightIndex ==  4 ? 208 : 192, 240, 16, 16); // MAXIMUM UPPER
        }

        // ----- Page 3 - Reward ----- //
        if(  page == 2){
            this.blit(matrix, posX +  49, posY +  5,                               55, 187, 78, 20); // FIELD ITEMS
            this.blit(matrix, posX +   5, posY +  5, highlightIndex == 14 ?  40 :   0, 236, 40, 20); // TOKEN IN
            this.blit(matrix, posX + 131, posY +  5, highlightIndex == 15 ? 120 :  80, 236, 40, 20); // TOKEN OUT
        } if(page == 2 && menu.hasReward()){
            this.blit(matrix, posX +  48, posY + 31,                              135, 195, 48, 12); // FIELD BIG 1
            this.blit(matrix, posX +  48, posY + 49,                              135, 195, 48, 12); // FIELD BIG 2
            this.blit(matrix, posX +  48, posY + 67,                              135, 195, 48, 12); // FIELD BIG 3
            this.blit(matrix, posX + 104, posY + 31,                              135, 181, 24, 12); // FIELD SMALL 1
            this.blit(matrix, posX + 104, posY + 49,                              135, 181, 24, 12); // FIELD SMALL 2
            this.blit(matrix, posX + 104, posY + 67,                              135, 181, 24, 12); // FIELD SMALL 3

            this.blit(matrix, posX + 137, posY + 29, highlightIndex ==  5 ? 176 : 160, 240, 16, 16); // REWARD 1 LOWER
            this.blit(matrix, posX + 155, posY + 29, highlightIndex ==  6 ? 208 : 192, 240, 16, 16); // REWARD 1 UPPER
            this.blit(matrix, posX + 137, posY + 47, highlightIndex ==  7 ? 176 : 160, 240, 16, 16); // REWARD 2 LOWER
            this.blit(matrix, posX + 155, posY + 47, highlightIndex ==  8 ? 208 : 192, 240, 16, 16); // REWARD 2 UPPER
            this.blit(matrix, posX + 137, posY + 65, highlightIndex ==  9 ? 176 : 160, 240, 16, 16); // REWARD 3 LOWER
            this.blit(matrix, posX + 155, posY + 65, highlightIndex == 10 ? 208 : 192, 240, 16, 16); // REWARD 3 UPPER
            this.blit(matrix, posX +  23, posY + 29, highlightIndex == 11 ? 240 : 224, 240, 16, 16); // SCORE 1 EDIT
            this.blit(matrix, posX +  23, posY + 47, highlightIndex == 12 ? 240 : 224, 240, 16, 16); // SCORE 2 EDIT
            this.blit(matrix, posX +  23, posY + 65, highlightIndex == 13 ? 240 : 224, 240, 16, 16); // SCORE 3 EDIT
            this.blit(matrix, posX +   5, posY + 29, menu.getPrizeMode1() ? 178 : 162, 208, 16, 16); // PRIZE 1 MODE
            this.blit(matrix, posX +   5, posY + 47, menu.getPrizeMode2() ? 178 : 162, 208, 16, 16); // PRIZE 2 MODE
            this.blit(matrix, posX +   5, posY + 65, menu.getPrizeMode3() ? 178 : 162, 208, 16, 16); // PRIZE 3 MODE
        }

        // ----- Page 4 - Input ----- //
        if(page == 3){
            this.blit(matrix, posX + 49, posY + 57, 55, 187, 78, 20); // FIELD INPUT
        }

        if(highlightTimer > 0){
            highlightTimer--;
            if(highlightTimer == 0){
                if(highlightIndex == 11 || highlightIndex == 12){
                    commandTranferOFF();
                } highlightIndex = 0;
            }
        }
    }





    //----------------------------------------COMMAND----------------------------------------//

    /** ??? **/
    public void commandChangeBet(int value, boolean isMaxBet){
        boolean send = false;
        if(isMaxBet){
            menu.setBettingHigh(menu.getBettingHigh() + value);
            if(menu.getBettingLow() > menu.getBettingHigh()){
                menu.setBettingLow(menu.getBettingHigh());
            }
            send = true;
        } else {
            if(value > 0 || menu.getBettingLow() > 0){
                menu.setBettingLow(menu.getBettingLow() + value);
                if(menu.getBettingHigh() < menu.getBettingLow() || !hasGambling()){
                    menu.setBettingHigh(menu.getBettingLow());
                }
                send = true;
            }
        }
        if(send){
            sendPacket();
        }
    }

    /** ??? **/
    public void commandChangeReward(int value, int index){
        boolean send = false;
        if(index == 0){ if(value > 0 || menu.getPrizeCount1() > 0){ menu.setPrizeCount1(menu.getPrizeCount1() + value); send = true; } }
        if(index == 1){ if(value > 0 || menu.getPrizeCount2() > 0){ menu.setPrizeCount2(menu.getPrizeCount2() + value); send = true; } }
        if(index == 2){ if(value > 0 || menu.getPrizeCount3() > 0){ menu.setPrizeCount3(menu.getPrizeCount3() + value); send = true; } }
        if(send){ sendPacket(); }
    }

    /** ??? **/
    public void commandTransfer(boolean isToken, boolean isIN){
        if( isToken &&  isIN){
            menu.setTransferTokenIN(true);
            menu.setTransferTokenOUT(false);
            menu.setTransferPrizeIN(false);
            menu.setTransferPrizeOUT(false);
        }
        if( isToken && !isIN){
            menu.setTransferTokenIN(false);
            menu.setTransferTokenOUT(true);
            menu.setTransferPrizeIN(false);
            menu.setTransferPrizeOUT(false);
        }
        if(!isToken &&  isIN){
            menu.setTransferTokenIN(false);
            menu.setTransferTokenOUT(false);
            menu.setTransferPrizeIN(true);
            menu.setTransferPrizeOUT(false);
        }
        if(!isToken && !isIN){
            menu.setTransferTokenIN(false);
            menu.setTransferTokenOUT(false);
            menu.setTransferPrizeIN(false);
            menu.setTransferPrizeOUT(true);
        }
        sendPacket();
    }

    private void commandTranferOFF(){
        menu.setTransferTokenIN(false);
        menu.setTransferTokenOUT(false);
        menu.setTransferPrizeIN(false);
        menu.setTransferPrizeOUT(false);
        sendPacket();
    }

    /** ??? **/
    public void commandToggleSettings(int settingID){
        if(settingID == 0){ if(Config.CONFIG.config_creative_token.get() && isOP()){  menu.setSettingInfiniteToken( !menu.getSettingInfiniteToken());          } }
        if(settingID == 1){ if(Config.CONFIG.config_creative_reward.get() && isOP()){ menu.setSettingInfinitePrize( !menu.getSettingInfinitePrize());          } }
        if(settingID == 2){                                                 menu.setSettingDropOnBreak(   !menu.getSettingDropOnBreak());              }
        if(settingID == 3){                                                 menu.setSettingIndestructable(!menu.getSettingIndestructable());           }
        if(settingID == 4){                                                 menu.setSettingAlternateColor((menu.getSettingAlternateColor() + 1) % 6);  }
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

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFont(PoseStack matrix, String text, int x, int y){
        this.font.draw(matrix, text,  x, y, 0);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontInvert(PoseStack matrix, String text, int x, int y){
        int w = this.font.width(text);
        this.font.draw(matrix, text,  x - w, y, 0);
    }

    /** Draws String on x,y position with shadow and custom color **/
    protected void drawFontCenter(PoseStack matrix, String text, int x, int y){
        int w = this.font.width(text) / 2;
        this.font.draw(matrix, text,  x - w, y, 0);
    }

    protected void highlight(int index){
        highlightTimer = 10;
        highlightIndex = index;
    }

    private boolean isOP(){
        return !Config.CONFIG.config_creative_oponly.get() || this.minecraft.player.getPermissionLevel() > 0;
    }

    protected void sendPacket(){
        CasinoPacketHandler.sendToServer(new MessageSettingServer(menu.pos(), new int[]{
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
                menu.getTransferTokenIN()  ? 1 : 0,
                menu.getTransferTokenOUT() ? 1 : 0,
                menu.getTransferPrizeIN()  ? 1 : 0,
                menu.getTransferPrizeOUT() ? 1 : 0,
                menu.getSettingInfiniteToken()  ? 1 : 0,
                menu.getSettingInfinitePrize()  ? 1 : 0,
                menu.getSettingDropOnBreak()    ? 1 : 0,
                menu.getSettingIndestructable() ? 1 : 0,
                menu.getSettingAlternateColor()
        } ));
    }

    private boolean hasHighscore(){
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_LIME.get()      ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_LIGHT_GRAY.get()) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_LIGHT_BLUE.get()) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_GRAY.get()      ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_CYAN.get()      ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_BLUE.get()      ) return true;

        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CHIP_PINK.get()      ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CHIP_ORANGE.get()    ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CHIP_LIGHT_GRAY.get()) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CHIP_LIGHT_BLUE.get()) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CHIP_CYAN.get()      ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CHIP_BLUE.get()      ) return true;

        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_PURPLE.get()    ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_MAGENTA.get()   ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_LIGHT_BLUE.get()) return true;

        return false;
    }

    private boolean hasGambling(){
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_YELLOW.get()) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_RED.get()   ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_PINK.get()  ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_MINO_ORANGE.get()) return true;

        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_YELLOW.get()    ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_WHITE.get()     ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_RED.get()       ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_ORANGE.get()    ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_LIGHT_GRAY.get()) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_GRAY.get()      ) return true;
        if(menu.getItems().get(1).getItem() == CasinoKeeper.MODULE_CARD_BLACK.get()     ) return true;

        return false;
    }

    protected boolean hasKey(){
        return menu.hasKey();
    }



}
