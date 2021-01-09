package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.card.ContainerCardBlack;
import mod.casinocraft.container.card.ContainerCardGray;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicCardGray;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiCardGray extends GuiCasino {   // Hold'em Poker

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiCardGray(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world){
        super(new ContainerCardGray(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardGray logic(){
        return (LogicCardGray) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
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

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            drawFontCenter("Waiting for Players", 128, 88);
        }
        if(logic().turnstate == 3){
            drawFontCenter("POT:  " + (logic().pot*bet), 128, 88);
        }
        if(CasinoKeeper.config_timeout - logic().timeout > 0)
            drawFontInvert("" + (CasinoKeeper.config_timeout - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        int playerPos = getPlayerPosition();
        if(logic().turnstate >= 2){

            drawCardBack( 48, 104, 7);
            drawCardBack( 80, 104, 7);
            drawCardBack(112, 104, 7);
            drawCardBack(144, 104, 7);
            drawCardBack(176, 104, 7);
            if(logic().comCards[0].idletimer == 0) drawCard( 48, 104, logic().comCards[0]);
            if(logic().comCards[1].idletimer == 0) drawCard( 80, 104, logic().comCards[1]);
            if(logic().comCards[2].idletimer == 0) drawCard(112, 104, logic().comCards[2]);
            if(logic().comCards[3].idletimer == 0) drawCard(144, 104, logic().comCards[3]);
            if(logic().comCards[4].idletimer == 0) drawCard(176, 104, logic().comCards[4]);

            if(tableID == 1){
                // Draw Player 1
                drawCard2( 96, 188, logic().cards[0][0], 0, playerPos, 0);
                drawCard2(128, 188, logic().cards[1][0], 0, playerPos, 0);

                // Draw Player 2
                drawCard2(20 + 48,  96, logic().cards[0][1], 90, playerPos, 1);
                drawCard2(20 + 48, 128, logic().cards[1][1], 90, playerPos, 1);

                // Draw Player 3
                drawCard2(128 + 32, 20 + 48, logic().cards[0][2], 180, playerPos, 2);
                drawCard2( 96 + 32, 20 + 48, logic().cards[1][2], 180, playerPos, 2);

                // Draw Player 4
                drawCard2(188, 128 + 32, logic().cards[0][3], 270, playerPos, 3);
                drawCard2(188,  96 + 32, logic().cards[1][3], 270, playerPos, 3);
            }
            if(tableID == 2){
                // Draw Player 1
                drawCard2( 96-88, 188, logic().cards[0][0], 0, playerPos, 0);
                drawCard2(128-88, 188, logic().cards[1][0], 0, playerPos, 0);

                // Draw Player 2
                drawCard2(20 + 48-88,  96, logic().cards[0][1], 90, playerPos, 1);
                drawCard2(20 + 48-88, 128, logic().cards[1][1], 90, playerPos, 1);

                // Draw Player 3
                drawCard2(128 + 32-88, 20 + 48, logic().cards[0][2], 180, playerPos, 2);
                drawCard2( 96 + 32-88, 20 + 48, logic().cards[1][2], 180, playerPos, 2);

                // Draw Player 4
                drawCard2(128 + 32+88, 20 + 48, logic().cards[0][3], 180, playerPos, 3);
                drawCard2( 96 + 32+88, 20 + 48, logic().cards[1][3], 180, playerPos, 3);

                // Draw Player 5
                drawCard2(188+88, 128 + 32, logic().cards[0][4], 270, playerPos, 4);
                drawCard2(188+88,  96 + 32, logic().cards[1][4], 270, playerPos, 4);

                // Draw Player 6
                drawCard2( 96+88, 188, logic().cards[0][5], 0, playerPos, 5);
                drawCard2(128+88, 188, logic().cards[1][5], 0, playerPos, 5);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 3){
            if(isActivePlayer()){
                if(logic().raisedPlayer == -1){
                    if(playerToken >= bet){
                        drawTexturedModalRect(guiLeft+11,  guiTop+237, 78, 22*4, 78, 22); // Raise
                    }
                }
                if(logic().raisedPlayer > -1){
                    if(playerToken >= bet){
                        drawTexturedModalRect(guiLeft+89,  guiTop+237, 78, 22*3, 78, 22); // Call
                    }
                } else {
                    drawTexturedModalRect(guiLeft+89,  guiTop+237, 78*2, 22*3, 78, 22); // Check
                }
                drawTexturedModalRect(guiLeft+167,  guiTop+237, 78*2, 22*4, 78, 22); // Fold
            }
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void drawCard2(int posX, int posY, Card card, float angle, int playerPos, int cardPos){
        if(card.suit == -1) return;
        if(card.idletimer > 0) return;
        boolean hidden = logic().turnstate > 3 ? false : playerPos != cardPos;
        if(hidden){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        } else {
            if(card.suit <= 1) this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROUGE);
            if(card.suit >= 2) this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_NOIR);
        }
        int texX = card.suit == -1 || hidden ? cardPos+1 : card.number % 8;
        int texY = card.suit == -1 || hidden ? 4 : (card.suit % 2) * 2 + card.number / 8;
        if(CasinoKeeper.config_animated_cards && !hidden){
            if(card.number >= 10){
                if(logic().frame == card.suit*12 + (card.number-10)*3){
                    texX += 3;
                }
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft + posX + card.shiftX, guiTop + posY + card.shiftY, 0);
        GL11.glRotatef(angle, 0, 0, 1);
        drawTexturedModalRect(0, 0, texX * 32, texY * 48, 32, 48-card.deathtimer);
        GL11.glPopMatrix();
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "holdem_poker";
    }



}
