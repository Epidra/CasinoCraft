package mod.casinocraft.screen.card;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoConfig;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardGray;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.opengl.GL11;

public class ScreenCardGray extends ScreenCasino {   // Hold'em Poker

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardGray(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardGray logic(){
        return (LogicCardGray) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 3){
            if(isActivePlayer()){
                if(mouseRect(11, 237, 78, 22, mouseX, mouseY)){ // Raise
                    if(logic().raisedPlayer == -1){
                        if(playerToken >= bet){
                            collectBet();
                            action(1);
                        }
                    }
                }
                if(mouseRect(89, 237, 78, 22, mouseX, mouseY)){ // Call/Check
                    if(logic().raisedPlayer > -1){
                        if(playerToken >= bet){
                            collectBet();
                            action(0);
                        }
                    } else {
                        action(2);
                    }
                }
                if(mouseRect(167, 237, 78, 22, mouseX, mouseY)){ // Fold
                    action(3);
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate == 2){
            drawFontCenter(matrixstack, "Waiting for Players", 128, 88);
        }
        if(logic().turnstate == 3){
            drawFontCenter(matrixstack, "POT:  " + (logic().pot*bet), 128, 88);
        }
        if(CasinoConfig.CONFIG.config_timeout.get() - logic().timeout > 0)
            drawFontInvert(matrixstack, "" + (CasinoConfig.CONFIG.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
        drawBalance(matrixstack);
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        int playerPos = getPlayerPosition();
        if(logic().turnstate >= 2){

            drawCardBack(matrixstack,  48, 104, 7);
            drawCardBack(matrixstack,  80, 104, 7);
            drawCardBack(matrixstack, 112, 104, 7);
            drawCardBack(matrixstack, 144, 104, 7);
            drawCardBack(matrixstack, 176, 104, 7);
            if(logic().comCards[0].idletimer == 0) drawCard(matrixstack,  48, 104, logic().comCards[0]);
            if(logic().comCards[1].idletimer == 0) drawCard(matrixstack,  80, 104, logic().comCards[1]);
            if(logic().comCards[2].idletimer == 0) drawCard(matrixstack, 112, 104, logic().comCards[2]);
            if(logic().comCards[3].idletimer == 0) drawCard(matrixstack, 144, 104, logic().comCards[3]);
            if(logic().comCards[4].idletimer == 0) drawCard(matrixstack, 176, 104, logic().comCards[4]);

            int  left = tableID == 1 ? 68 : 20 + 48-96;
            int right = tableID == 1 ? 188 : 188+96;
            switch (logic().getFirstFreePlayerSlot()){
                case 1:
                    /* DOWN   */ drawCard2(matrixstack, 128, 188,   0, playerPos, 0);
                    break;
                case 2:
                    /* DOWN   */ drawCard2(matrixstack, 128, 188,   0, playerPos, playerPos);
                    /* UP     */ drawCard2(matrixstack, 128,  68, 180, playerPos, (playerPos + 1) % 2);
                    break;
                case 3:
                    /* DOWN   */ drawCard2(matrixstack, 128, 188,   0, playerPos, playerPos);
                    /* LEFT   */ drawCard2(matrixstack,     left, 112,  90, playerPos, (playerPos + 1) % 3);
                    /* RIGHT  */ drawCard2(matrixstack,    right, 144, 270, playerPos, (playerPos + 2) % 3);
                    break;
                case 4:
                    /* DOWN   */ drawCard2(matrixstack, 128, 188,   0, playerPos, playerPos);
                    /* LEFT   */ drawCard2(matrixstack,     left, 112,  90, playerPos, (playerPos + 1) % 4);
                    /* UP     */ drawCard2(matrixstack, 128,  68, 180, playerPos, (playerPos + 2) % 4);
                    /* RIGHT  */ drawCard2(matrixstack,    right, 144, 270, playerPos, (playerPos + 3) % 4);
                    break;
                case 5:
                    /* DOWN   */ drawCard2(matrixstack, 128, 188,   0, playerPos, playerPos);
                    /* LEFT   */ drawCard2(matrixstack,     left, 112,  90, playerPos, (playerPos + 1) % 5);
                    /* UP_L   */ drawCard2(matrixstack,  48,  68, 180, playerPos, (playerPos + 2) % 5);
                    /* UP_R   */ drawCard2(matrixstack, 208,  68, 180, playerPos, (playerPos + 3) % 5);
                    /* RIGHT  */ drawCard2(matrixstack,    right, 144, 270, playerPos, (playerPos + 4) % 5);
                    break;
                case 6:
                    /* DOWN_L */ drawCard2(matrixstack,  32, 188,   0, playerPos, playerPos);
                    /* LEFT   */ drawCard2(matrixstack,     left, 112,  90, playerPos, (playerPos + 1) % 6);
                    /* UP_L   */ drawCard2(matrixstack,  48,  68, 180, playerPos, (playerPos + 2) % 6);
                    /* UP_R   */ drawCard2(matrixstack, 208,  68, 180, playerPos, (playerPos + 3) % 6);
                    /* RIGHT  */ drawCard2(matrixstack,    right, 144, 270, playerPos, (playerPos + 4) % 6);
                    /* DOWN_R */ drawCard2(matrixstack, 208, 188,   0, playerPos, (playerPos + 5) % 6);
                    break;
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 3){
            if(isActivePlayer()){
                if(logic().raisedPlayer == -1){
                    if(playerToken >= bet){
                        blit(matrixstack, leftPos+11,  topPos+237, 78, 22*4, 78, 22); // Raise
                    }
                }
                if(logic().raisedPlayer > -1){
                    if(playerToken >= bet){
                        blit(matrixstack, leftPos+89,  topPos+237, 78, 22*3, 78, 22); // Call
                    }
                } else {
                    blit(matrixstack, leftPos+89,  topPos+237, 78*2, 22*3, 78, 22); // Check
                }
                blit(matrixstack, leftPos+167,  topPos+237, 78*2, 22*4, 78, 22); // Fold
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void drawCard2(PoseStack matrixstack, int posX, int posY, float angle, int playerPos, int cardPos){
        int i = 0;
        for(Card card : logic().getCards(cardPos)){
            if(card.suit != -1 && card.idletimer == 0){
                boolean hidden = logic().turnstate > 3 ? false : playerPos != cardPos;
                RenderSystem.setShaderTexture(0, getCardsTexture(hidden || card.suit >= 2));
                int texX = card.suit == -1 || hidden ? cardPos+1 : card.number % 8;
                int texY = card.suit == -1 || hidden ?         4 : (card.suit  % 2) * 2 + card.number / 8;
                if(CasinoConfig.CONFIG.config_animated_cards.get() && !hidden){
                    if(card.number >= 10){
                        if(logic().frame == card.suit*12 + (card.number-10)*3){
                            texX += 3;
                        }
                    }
                }
                GL11.glPushMatrix();
                GL11.glTranslatef(leftPos + posX, topPos + posY, 0);
                GL11.glRotatef(angle, 0, 0, 1);
                blit(matrixstack, card.shiftX - logic().getCards(cardPos).size()*16 + i*32, card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
                GL11.glPopMatrix();
            }
            i++;
        }
    }





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "holdem_poker";
    }



}
