package mod.casinocraft.screen.card;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.card.LogicCardGray;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

public class ScreenCardGray extends ScreenCasino {   // Hold'em Poker

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardGray(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardGray logic(){
        return (LogicCardGray) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
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

    protected void keyTyped2(int keyCode){

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(MatrixStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate == 2){
            drawFontCenter(matrixstack, "Waiting for Players", 128, 88);
        }
        if(logic().turnstate == 3){
            drawFontCenter(matrixstack, "POT:  " + (logic().pot*bet), 128, 88);
        }
        if(CasinoKeeper.config_timeout.get() - logic().timeout > 0)
            drawFontInvert(matrixstack, "" + (CasinoKeeper.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
    }

    protected void drawGuiContainerBackgroundLayer2(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
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

            if(tableID == 1){
                // Draw Player 1
                drawCard2(matrixstack,  96, 188, logic().cards[0][0], 0, playerPos, 0);
                drawCard2(matrixstack, 128, 188, logic().cards[1][0], 0, playerPos, 0);

                // Draw Player 2
                drawCard2(matrixstack, 20 + 48,  96, logic().cards[0][1], 90, playerPos, 1);
                drawCard2(matrixstack, 20 + 48, 128, logic().cards[1][1], 90, playerPos, 1);

                // Draw Player 3
                drawCard2(matrixstack, 128 + 32, 20 + 48, logic().cards[0][2], 180, playerPos, 2);
                drawCard2(matrixstack,  96 + 32, 20 + 48, logic().cards[1][2], 180, playerPos, 2);

                // Draw Player 4
                drawCard2(matrixstack, 188, 128 + 32, logic().cards[0][3], 270, playerPos, 3);
                drawCard2(matrixstack, 188,  96 + 32, logic().cards[1][3], 270, playerPos, 3);
            }
            if(tableID == 2){
                // Draw Player 1
                drawCard2(matrixstack,  96-88, 188, logic().cards[0][0], 0, playerPos, 0);
                drawCard2(matrixstack, 128-88, 188, logic().cards[1][0], 0, playerPos, 0);

                // Draw Player 2
                drawCard2(matrixstack, 20 + 48-88,  96, logic().cards[0][1], 90, playerPos, 1);
                drawCard2(matrixstack, 20 + 48-88, 128, logic().cards[1][1], 90, playerPos, 1);

                // Draw Player 3
                drawCard2(matrixstack, 128 + 32-88, 20 + 48, logic().cards[0][2], 180, playerPos, 2);
                drawCard2(matrixstack,  96 + 32-88, 20 + 48, logic().cards[1][2], 180, playerPos, 2);

                // Draw Player 4
                drawCard2(matrixstack, 128 + 32+88, 20 + 48, logic().cards[0][3], 180, playerPos, 3);
                drawCard2(matrixstack,  96 + 32+88, 20 + 48, logic().cards[1][3], 180, playerPos, 3);

                // Draw Player 5
                drawCard2(matrixstack, 188+88, 128 + 32, logic().cards[0][4], 270, playerPos, 4);
                drawCard2(matrixstack, 188+88,  96 + 32, logic().cards[1][4], 270, playerPos, 4);

                // Draw Player 6
                drawCard2(matrixstack,  96+88, 188, logic().cards[0][5], 0, playerPos, 5);
                drawCard2(matrixstack, 128+88, 188, logic().cards[1][5], 0, playerPos, 5);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 3){
            if(isActivePlayer()){
                if(logic().raisedPlayer == -1){
                    if(playerToken >= bet){
                        blit(matrixstack, guiLeft+11,  guiTop+237, 78, 22*4, 78, 22); // Raise
                    }
                }
                if(logic().raisedPlayer > -1){
                    if(playerToken >= bet){
                        blit(matrixstack, guiLeft+89,  guiTop+237, 78, 22*3, 78, 22); // Call
                    }
                } else {
                    blit(matrixstack, guiLeft+89,  guiTop+237, 78*2, 22*3, 78, 22); // Check
                }
                blit(matrixstack, guiLeft+167,  guiTop+237, 78*2, 22*4, 78, 22); // Fold
            }
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void drawCard2(MatrixStack matrixstack, int posX, int posY, Card card, float angle, int playerPos, int cardPos){
        if(card.suit == -1) return;
        if(card.idletimer > 0) return;
        boolean hidden = logic().turnstate > 3 ? false : playerPos != cardPos;
        if(hidden){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        } else {
            if(card.suit <= 1) this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
            if(card.suit >= 2) this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        }
        int texX = card.suit == -1 || hidden ? cardPos+1 : card.number % 8;
        int texY = card.suit == -1 || hidden ? 4 : (card.suit % 2) * 2 + card.number / 8;
        if(CasinoKeeper.config_animated_cards.get() && !hidden){
            if(card.number >= 10){
                if(logic().frame == card.suit*12 + (card.number-10)*3){
                    texX += 3;
                }
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft + posX + card.shiftX, guiTop + posY + card.shiftY, 0);
        GL11.glRotatef(angle, 0, 0, 1);
        blit(matrixstack, 0, 0, texX * 32, texY * 48, 32, 48-card.deathtimer);
        GL11.glPopMatrix();
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "holdem_poker";
    }



}
