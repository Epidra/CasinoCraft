package mod.casinocraft.screen.card;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.Config;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardLightGray;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import mod.casinocraft.util.Card;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardLightGray extends ScreenCasino {   // Draw Poker

    int playerPos;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardLightGray(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardLightGray logic(){
        return (LogicCardLightGray) menu.logic();
    }

    protected String getGameName() {
        return "draw_poker";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_BOT_LEFT2,  ButtonMap.RAISE, () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer == -1 && logic().round != 1, () -> { if(playerToken >= bet){ collectBet(); action(1); } } );
        buttonSet.addButton(ButtonMap.POS_BOT_RIGHT2, ButtonMap.CALL,  () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer >  -1 && playerToken >= bet, () -> { if(playerToken >= bet){ collectBet(); action(0); } } );
        buttonSet.addButton(ButtonMap.POS_BOT_RIGHT2, ButtonMap.CHECK, () -> isActivePlayer() && logic().turnstate == 3 && logic().raisedPlayer == -1,                       () ->                                         action(2));
        buttonSet.addButton(ButtonMap.POS_BOT_MIDDLE, ButtonMap.FOLD,  () -> isActivePlayer() && logic().turnstate == 3,                                                     () ->                                         action(3));
        playerPos = -2;
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 3){
            if(logic().round == 1){
                int x = logic().getFirstFreePlayerSlot() < 5 ? 128 : 32;
                int playerPos = getPlayerPosition();
                for(int i = 0; i < logic().getCards(playerPos).size(); i++){
                    if(mouseRect(x - logic().getCards(playerPos).size()*16 + 32*i, 188, 32, 48, mouseX, mouseY)){ action(4+i); }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        if(logic().turnstate == 2                                           ){ drawFontCenter(matrix, "Waiting for Players",            128,  88); }
        if(logic().turnstate == 3 && !isActivePlayer()                      ){ drawFontCenter(matrix, "...",                            128,  88); }
        if(logic().turnstate == 3 &&  isActivePlayer() && logic().round != 1){ drawFontCenter(matrix, "Choose an option ...",           128,  88); }
        if(logic().turnstate == 3 &&  isActivePlayer() && logic().round == 1){ drawFontCenter(matrix, "You can change Cards now ...",   128,  88); }
        if(logic().turnstate == 3                                           ){ drawFontCenter(matrix, "POT:  " + (logic().pot*bet),      96, 160); }
        if(logic().turnstate == 3                                           ){ drawFontCenter(matrix, "ROUND:  " + (logic().round + 1), 160, 160); }
        if(logic().turnstate >= 4                                           ){ drawFontCenter(matrix, logic().hand,                     128,  88); }
        drawTimer(matrix);
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        if(playerPos >  -2 && logic().turnstate == 2) playerPos = -2;
        if(playerPos == -2 && logic().turnstate == 3) playerPos = getPlayerPosition();
        int  left = tableID == 1 ? 7 : -89;
        int right = tableID == 1 ? 217 : 313;
        switch (logic().playerCount){
            case 1:
                /* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos, 0);
                break;
            case 2:
                /* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
                /* UP     */ drawCard2(matrix,   128,   7, false, playerPos, (playerPos + 1) % 2);
                break;
            case 3:
                /* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
                /* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 3);
                /* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 2) % 3);
                break;
            case 4:
                /* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
                /* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 4);
                /* UP     */ drawCard2(matrix,   128,   7, false, playerPos, (playerPos + 2) % 4);
                /* RIGHT  */ drawCard2(matrix, right, 144, true,  playerPos, (playerPos + 3) % 4);
                break;
            case 5:
                /* DOWN   */ drawCard2(matrix,   128, 193, false, playerPos,  playerPos);
                /* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 5);
                /* UP_L   */ drawCard2(matrix,    48,   7, false, playerPos, (playerPos + 2) % 5);
                /* UP_R   */ drawCard2(matrix,   208,   7, false, playerPos, (playerPos + 3) % 5);
                /* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 4) % 5);
                break;
            case 6:
                /* DOWN_L */ drawCard2(matrix,    32, 193, false, playerPos,  playerPos);
                /* LEFT   */ drawCard2(matrix,  left, 128, true,  playerPos, (playerPos + 1) % 6);
                /* UP_L   */ drawCard2(matrix,    48,   7, false, playerPos, (playerPos + 2) % 6);
                /* UP_R   */ drawCard2(matrix,   208,   7, false, playerPos, (playerPos + 3) % 6);
                /* RIGHT  */ drawCard2(matrix, right, 128, true,  playerPos, (playerPos + 4) % 6);
                /* DOWN_R */ drawCard2(matrix,   208, 201, false, playerPos, (playerPos + 5) % 6);
                break;
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void drawCard2(GuiGraphics matrix, int posX, int posY, boolean sideways, int playerPos, int cardPos){
        if(playerPos < 0) return;
        int i = 0;
        for(Card card : logic().getCards(cardPos)){
            if(card.suit != -1 && card.idletimer == 0){
                boolean hidden = logic().turnstate > 3 ? false : card.hidden || playerPos != cardPos;
                // RenderSystem.setShaderTexture(0, getCardsTexture(hidden || card.suit >= 2));
                int texX = card.suit == -1 || hidden ? cardPos+1 : card.number % 8;
                int texY = card.suit == -1 || hidden ?         4 : (card.suit  % 2) * 2 + card.number / 8;
                if(Config.CONFIG.config_animated_cards.get() && !hidden){
                    if(card.number >= 10){
                        if(logic().frame == card.suit*12 + (card.number-10)*3){
                            texX += 3;
                        }
                    }
                }
                if(sideways){
                    matrix.blit(getCardsTexture(hidden || card.suit >= 2), leftPos + posX + card.shiftX, topPos + posY - logic().getCards(cardPos).size()*12 + i*20 + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
                } else {
                    matrix.blit(getCardsTexture(hidden || card.suit >= 2), leftPos + posX + card.shiftX - logic().getCards(cardPos).size()*16 + i*32, topPos + posY + card.shiftY, texX * 32, texY * 48, 32, 48-card.deathtimer);
                }
            }
            i++;
        }
    }



}