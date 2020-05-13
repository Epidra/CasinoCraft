package mod.casinocraft.screen.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.card.LogicCardLightGray;
import mod.casinocraft.logic.card.LogicCardWhite;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

public class ScreenCardLightGray extends ScreenCasino {   // Draw Poker

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardLightGray(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardLightGray logic(){
        return (LogicCardLightGray) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 3){
            if(isActivePlayer()){
                if(logic().round != 1){
                    if(mouseRect(11, 237, 78, 22, mouseX, mouseY)){ // Raise
                        if(logic().raisedPlayer == -1){
                            if(playerToken >= bet){
                                collectBet();
                                action(1);
                            }
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
                if(logic().round == 1){
                    if(getPlayerPosition() == 0){
                        if(mouseRect(tableID == 1 ?  48 :  48-80, 188, 32, 48, mouseX, mouseY)){ action(4+3); }
                        if(mouseRect(tableID == 1 ?  80 :  80-80, 188, 32, 48, mouseX, mouseY)){ action(4+1); }
                        if(mouseRect(tableID == 1 ? 112 : 112-80, 188, 32, 48, mouseX, mouseY)){ action(4+0); }
                        if(mouseRect(tableID == 1 ? 144 : 144-80, 188, 32, 48, mouseX, mouseY)){ action(4+2); }
                        if(mouseRect(tableID == 1 ? 176 : 176-80, 188, 32, 48, mouseX, mouseY)){ action(4+4); }
                    }
                    if(getPlayerPosition() == 1){
                        if(mouseRect(tableID == 1 ? 20 : 20-96,  48, 48, 32, mouseX, mouseY)){ action(4+3); }
                        if(mouseRect(tableID == 1 ? 20 : 20-96,  80, 48, 32, mouseX, mouseY)){ action(4+1); }
                        if(mouseRect(tableID == 1 ? 20 : 20-96, 112, 48, 32, mouseX, mouseY)){ action(4+0); }
                        if(mouseRect(tableID == 1 ? 20 : 20-96, 144, 48, 32, mouseX, mouseY)){ action(4+2); }
                        if(mouseRect(tableID == 1 ? 20 : 20-96, 176, 48, 32, mouseX, mouseY)){ action(4+4); }
                    }
                    if(getPlayerPosition() == 2){
                        if(mouseRect(tableID == 1 ? 176 : 176-80, 20, 32, 48, mouseX, mouseY)){ action(4+3); }
                        if(mouseRect(tableID == 1 ? 144 : 144-80, 20, 32, 48, mouseX, mouseY)){ action(4+1); }
                        if(mouseRect(tableID == 1 ? 112 : 112-80, 20, 32, 48, mouseX, mouseY)){ action(4+0); }
                        if(mouseRect(tableID == 1 ?  80 :  80-80, 20, 32, 48, mouseX, mouseY)){ action(4+2); }
                        if(mouseRect(tableID == 1 ?  48 :  48-80, 20, 32, 48, mouseX, mouseY)){ action(4+4); }
                    }
                    if(getPlayerPosition() == 3){
                        if(mouseRect(tableID == 1 ? 188 : 176, tableID == 1 ? 176 : 20, tableID == 1 ? 48 : 32, tableID == 1 ? 32 : 48, mouseX, mouseY)){ action(4+3); }
                        if(mouseRect(tableID == 1 ? 188 : 144, tableID == 1 ? 144 : 20, tableID == 1 ? 48 : 32, tableID == 1 ? 32 : 48, mouseX, mouseY)){ action(4+1); }
                        if(mouseRect(tableID == 1 ? 188 : 112, tableID == 1 ? 112 : 20, tableID == 1 ? 48 : 32, tableID == 1 ? 32 : 48, mouseX, mouseY)){ action(4+0); }
                        if(mouseRect(tableID == 1 ? 188 :  80, tableID == 1 ?  80 : 20, tableID == 1 ? 48 : 32, tableID == 1 ? 32 : 48, mouseX, mouseY)){ action(4+2); }
                        if(mouseRect(tableID == 1 ? 188 :  48, tableID == 1 ?  48 : 20, tableID == 1 ? 48 : 32, tableID == 1 ? 32 : 48, mouseX, mouseY)){ action(4+4); }
                    }
                    if(getPlayerPosition() == 4){
                        if(mouseRect(188, 176, 48, 32, mouseX, mouseY)){ action(4+3); }
                        if(mouseRect(188, 144, 48, 32, mouseX, mouseY)){ action(4+1); }
                        if(mouseRect(188, 112, 48, 32, mouseX, mouseY)){ action(4+0); }
                        if(mouseRect(188,  80, 48, 32, mouseX, mouseY)){ action(4+2); }
                        if(mouseRect(188,  48, 48, 32, mouseX, mouseY)){ action(4+4); }
                    }
                    if(getPlayerPosition() == 5){
                        if(mouseRect( 48+80, 188, 32, 48, mouseX, mouseY)){ action(4+3); }
                        if(mouseRect( 80+80, 188, 32, 48, mouseX, mouseY)){ action(4+1); }
                        if(mouseRect(112+80, 188, 32, 48, mouseX, mouseY)){ action(4+0); }
                        if(mouseRect(144+80, 188, 32, 48, mouseX, mouseY)){ action(4+2); }
                        if(mouseRect(176+80, 188, 32, 48, mouseX, mouseY)){ action(4+4); }
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate == 2){
            drawFontCenter("Waiting for Players", 128, 88);
        }
        if(logic().turnstate == 3){
            drawFontCenter("POT:  " + (logic().pot*bet), 128, 88);
            drawFontCenter("ROUND:  " + (logic().round + 1), 128, 108);
            if(logic().round == 1){
                drawFontCenter("You can change Cards now..", 128, 128);
            }
        }
        if(CasinoKeeper.config_timeout.get() - logic().timeout > 0)
            drawFontInvert("" + (CasinoKeeper.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        int playerPos = getPlayerPosition();
        if(logic().turnstate >= 2){

            if(tableID == 1){
                // Draw Player 1
                drawCard2( 48, 188 + (logic().activePlayer == 0 && logic().dropped[3] ? -24 : 0), logic().cards[3][0], 0, playerPos, 0);
                drawCard2( 80, 188 + (logic().activePlayer == 0 && logic().dropped[1] ? -24 : 0), logic().cards[1][0], 0, playerPos, 0);
                drawCard2(112, 188 + (logic().activePlayer == 0 && logic().dropped[0] ? -24 : 0), logic().cards[0][0], 0, playerPos, 0);
                drawCard2(144, 188 + (logic().activePlayer == 0 && logic().dropped[2] ? -24 : 0), logic().cards[2][0], 0, playerPos, 0);
                drawCard2(176, 188 + (logic().activePlayer == 0 && logic().dropped[4] ? -24 : 0), logic().cards[4][0], 0, playerPos, 0);

                // Draw Player 2
                drawCard2(20 + 48 + (logic().activePlayer == 1 && logic().dropped[3] ? 24 : 0),  48, logic().cards[3][1], 90, playerPos, 1);
                drawCard2(20 + 48 + (logic().activePlayer == 1 && logic().dropped[1] ? 24 : 0),  80, logic().cards[1][1], 90, playerPos, 1);
                drawCard2(20 + 48 + (logic().activePlayer == 1 && logic().dropped[0] ? 24 : 0), 112, logic().cards[0][1], 90, playerPos, 1);
                drawCard2(20 + 48 + (logic().activePlayer == 1 && logic().dropped[2] ? 24 : 0), 144, logic().cards[2][1], 90, playerPos, 1);
                drawCard2(20 + 48 + (logic().activePlayer == 1 && logic().dropped[4] ? 24 : 0), 176, logic().cards[4][1], 90, playerPos, 1);

                // Draw Player 3
                drawCard2(176 + 32, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[3] ? 24 : 0), logic().cards[3][2], 180, playerPos, 2);
                drawCard2(144 + 32, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[1] ? 24 : 0), logic().cards[1][2], 180, playerPos, 2);
                drawCard2(112 + 32, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[0] ? 24 : 0), logic().cards[0][2], 180, playerPos, 2);
                drawCard2( 80 + 32, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[2] ? 24 : 0), logic().cards[2][2], 180, playerPos, 2);
                drawCard2( 48 + 32, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[4] ? 24 : 0), logic().cards[4][2], 180, playerPos, 2);

                // Draw Player 4
                drawCard2(188 + (logic().activePlayer == 3 && logic().dropped[3] ? -24 : 0), 176 + 32, logic().cards[3][3], 270, playerPos, 3);
                drawCard2(188 + (logic().activePlayer == 3 && logic().dropped[1] ? -24 : 0), 144 + 32, logic().cards[1][3], 270, playerPos, 3);
                drawCard2(188 + (logic().activePlayer == 3 && logic().dropped[0] ? -24 : 0), 112 + 32, logic().cards[0][3], 270, playerPos, 3);
                drawCard2(188 + (logic().activePlayer == 3 && logic().dropped[2] ? -24 : 0),  80 + 32, logic().cards[2][3], 270, playerPos, 3);
                drawCard2(188 + (logic().activePlayer == 3 && logic().dropped[4] ? -24 : 0),  48 + 32, logic().cards[4][3], 270, playerPos, 3);
            }
            if(tableID == 2){
                // Draw Player 1
                drawCard2( 48-80, 188 + (logic().activePlayer == 0 && logic().dropped[3] ? -24 : 0), logic().cards[3][0], 0, playerPos, 0);
                drawCard2( 80-80, 188 + (logic().activePlayer == 0 && logic().dropped[1] ? -24 : 0), logic().cards[1][0], 0, playerPos, 0);
                drawCard2(112-80, 188 + (logic().activePlayer == 0 && logic().dropped[0] ? -24 : 0), logic().cards[0][0], 0, playerPos, 0);
                drawCard2(144-80, 188 + (logic().activePlayer == 0 && logic().dropped[2] ? -24 : 0), logic().cards[2][0], 0, playerPos, 0);
                drawCard2(176-80, 188 + (logic().activePlayer == 0 && logic().dropped[4] ? -24 : 0), logic().cards[4][0], 0, playerPos, 0);

                // Draw Player 2
                drawCard2(20 + 48-96 + (logic().activePlayer == 1 && logic().dropped[3] ? 24 : 0),  48, logic().cards[3][1], 90, playerPos, 1);
                drawCard2(20 + 48-96 + (logic().activePlayer == 1 && logic().dropped[1] ? 24 : 0),  80, logic().cards[1][1], 90, playerPos, 1);
                drawCard2(20 + 48-96 + (logic().activePlayer == 1 && logic().dropped[0] ? 24 : 0), 112, logic().cards[0][1], 90, playerPos, 1);
                drawCard2(20 + 48-96 + (logic().activePlayer == 1 && logic().dropped[2] ? 24 : 0), 144, logic().cards[2][1], 90, playerPos, 1);
                drawCard2(20 + 48-96 + (logic().activePlayer == 1 && logic().dropped[4] ? 24 : 0), 176, logic().cards[4][1], 90, playerPos, 1);

                // Draw Player 3
                drawCard2(176 + 32-80, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[3] ? 24 : 0), logic().cards[3][2], 180, playerPos, 2);
                drawCard2(144 + 32-80, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[1] ? 24 : 0), logic().cards[1][2], 180, playerPos, 2);
                drawCard2(112 + 32-80, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[0] ? 24 : 0), logic().cards[0][2], 180, playerPos, 2);
                drawCard2( 80 + 32-80, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[2] ? 24 : 0), logic().cards[2][2], 180, playerPos, 2);
                drawCard2( 48 + 32-80, 20 + 48 + (logic().activePlayer == 2 && logic().dropped[4] ? 24 : 0), logic().cards[4][2], 180, playerPos, 2);

                // Draw Player 4
                drawCard2(176 + 32+80, 20 + 48 + (logic().activePlayer == 3 && logic().dropped[3] ? 24 : 0), logic().cards[3][3], 180, playerPos, 3);
                drawCard2(144 + 32+80, 20 + 48 + (logic().activePlayer == 3 && logic().dropped[1] ? 24 : 0), logic().cards[1][3], 180, playerPos, 3);
                drawCard2(112 + 32+80, 20 + 48 + (logic().activePlayer == 3 && logic().dropped[0] ? 24 : 0), logic().cards[0][3], 180, playerPos, 3);
                drawCard2( 80 + 32+80, 20 + 48 + (logic().activePlayer == 3 && logic().dropped[2] ? 24 : 0), logic().cards[2][3], 180, playerPos, 3);
                drawCard2( 48 + 32+80, 20 + 48 + (logic().activePlayer == 3 && logic().dropped[4] ? 24 : 0), logic().cards[4][3], 180, playerPos, 3);

                // Draw Player 5
                drawCard2(188+96 + (logic().activePlayer == 4 && logic().dropped[3] ? -24 : 0), 176 + 32, logic().cards[3][4], 270, playerPos, 4);
                drawCard2(188+96 + (logic().activePlayer == 4 && logic().dropped[1] ? -24 : 0), 144 + 32, logic().cards[1][4], 270, playerPos, 4);
                drawCard2(188+96 + (logic().activePlayer == 4 && logic().dropped[0] ? -24 : 0), 112 + 32, logic().cards[0][4], 270, playerPos, 4);
                drawCard2(188+96 + (logic().activePlayer == 4 && logic().dropped[2] ? -24 : 0),  80 + 32, logic().cards[2][4], 270, playerPos, 4);
                drawCard2(188+96 + (logic().activePlayer == 4 && logic().dropped[4] ? -24 : 0),  48 + 32, logic().cards[4][4], 270, playerPos, 4);

                // Draw Player 6
                drawCard2( 48+80, 188 + (logic().activePlayer == 5 && logic().dropped[3] ? -24 : 0), logic().cards[3][5], 0, playerPos, 5);
                drawCard2( 80+80, 188 + (logic().activePlayer == 5 && logic().dropped[1] ? -24 : 0), logic().cards[1][5], 0, playerPos, 5);
                drawCard2(112+80, 188 + (logic().activePlayer == 5 && logic().dropped[0] ? -24 : 0), logic().cards[0][5], 0, playerPos, 5);
                drawCard2(144+80, 188 + (logic().activePlayer == 5 && logic().dropped[2] ? -24 : 0), logic().cards[2][5], 0, playerPos, 5);
                drawCard2(176+80, 188 + (logic().activePlayer == 5 && logic().dropped[4] ? -24 : 0), logic().cards[4][5], 0, playerPos, 5);
            }
        }

    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 3){
            if(isActivePlayer()){
                if(logic().raisedPlayer == -1 && logic().round != 1){
                    if(playerToken >= bet){
                        blit(guiLeft+11,  guiTop+237, 78, 22*4, 78, 22); // Raise
                    }
                }
                if(logic().raisedPlayer > -1){
                    if(playerToken >= bet){
                        blit(guiLeft+89,  guiTop+237, 78, 22*3, 78, 22); // Call
                    }
                } else {
                    blit(guiLeft+89,  guiTop+237, 78*2, 22*3, 78, 22); // Check
                }
                blit(guiLeft+167,  guiTop+237, 78*2, 22*4, 78, 22); // Fold
            }
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void drawCard2(int posX, int posY, Card card, float angle, int playerPos, int cardPos){
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
        blit(0, 0, texX * 32, texY * 48, 32, 48-card.deathtimer);
        GL11.glPopMatrix();
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "draw_poker";
    }



}