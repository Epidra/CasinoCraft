package mod.casinocraft.screen.card;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardGreen;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.opengl.GL11;

public class ScreenCardGreen extends ScreenCasino {   // Mau-Mau

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardGreen(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardGreen logic(){
        return (LogicCardGreen) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 3){
            if(isActivePlayer()){

                if(logic().forcedAction == 0){
                    int x = logic().getFirstFreePlayerSlot() < 5 ? 128 : 128-80;
                    int playerPos = getPlayerPosition();
                    for(int i = 0; i < logic().getCards(playerPos).size(); i++){
                        if(mouseRect(x - logic().getCards(playerPos).size()*16 + 32*i, 188, 32, 48, mouseX, mouseY)){ action(i); }
                    }
                }
                if(logic().forcedAction == 3){
                    if(mouseRect(89-39, 237, 39, 22, mouseX, mouseY)){ // RED
                        action(-5);
                    }
                    if(mouseRect(89, 237, 39, 22, mouseX, mouseY)){ // YELLOW
                        action(-4);
                    }
                    if(mouseRect(89+39, 237, 39, 22, mouseX, mouseY)){ // BLUE
                        action(-3);
                    }
                    if(mouseRect(89+39+39, 237, 39, 22, mouseX, mouseY)){ // GREEN
                        action(-2);
                    }
                } else {
                    if(mouseRect(89, 237, 78, 22, mouseX, mouseY)){ // ACTION
                        action(-1);
                    }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate == 2){
            drawFontCenter(matrixstack, "Waiting for Players", 128, 88);
        }
        if(CasinoKeeper.config_timeout.get() - logic().timeout > 0)
            drawFontInvert(matrixstack, "" + (CasinoKeeper.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        int playerPos = getPlayerPosition();
        if(logic().turnstate >= 2){

            drawCard(matrixstack,  112, 104, logic().placed[1]);
            drawCard(matrixstack,  112, 104, logic().placed[0]);

            switch (logic().chosenColor){
                case 0: drawCardBack(matrixstack,  112, 104, 1+6+1); break;
                case 1: drawCardBack(matrixstack,  112, 104, 3+6+1); break;
                case 2: drawCardBack(matrixstack,  112, 104, 2+6+3); break;
                case 3: drawCardBack(matrixstack,  112, 104, 4+6-1); break;
            }

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

                if(logic().forcedAction == 3){
                    blit(matrixstack, leftPos+89-39,  topPos+237, 0, 22*5, 78+78, 22); // COLOR
                } else if(logic().forcedAction == 1){
                    blit(matrixstack, leftPos+89,  topPos+237, 78, 22*6, 78, 22); // WAIT
                } else {
                    blit(matrixstack, leftPos+89,  topPos+237, 0, 22*6, 78, 22); // DRAW
                }
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
                if(CasinoKeeper.config_animated_cards.get() && !hidden){
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
        return "maumau";
    }



}
