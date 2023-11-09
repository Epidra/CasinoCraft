package mod.casinocraft.screen.card;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardCyan;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import mod.casinocraft.util.Card;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardCyan extends ScreenCasino {   // Spider

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardCyan(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardCyan logic(){
        return (LogicCardCyan) menu.logic();
    }

    protected String getGameName() {
        return "spider";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_BOT_RIGHT,  ButtonMap.DRAW, () -> isActivePlayer() && logic().turnstate == 2 && logic().reserve < logic().cards_reserve.length, () -> { action(-1); } );
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        int offset = tableID == 1 ? 16 : -32;
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < (tableID == 1 ? 7 : 10); x++){
                    if(mouseRect(offset + 32*x, 20 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*10); }
                }
            }
            if(tableID == 2 && mouseRect(296, 28, 32, 196, mouseX, mouseY)){ action(-1); }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        drawValueRight(matrix, "RESERVE", logic().cards_reserve.length - logic().reserve);
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        int offset = tableID == 1 ? 16 : -32;
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(matrix, offset + x*32, 20 + y*(24-logic().compress), logic().cards_field[x].get(y));
            }
        }

        if(logic().selector.Y != -1) drawCardBack(matrix, offset + logic().selector.X*32 , 20 + logic().selector.Y*(24-logic().compress), 9);

        if(tableID == 2){
            drawCardBack(matrix, 296, 28, 7);

            if(logic().cards_reserve[4].size() > 0) drawCardBack(matrix, 296,  28, 0);
            if(logic().cards_reserve[3].size() > 0) drawCardBack(matrix, 296,  52, 0);
            if(logic().cards_reserve[2].size() > 0) drawCardBack(matrix, 296,  76, 0);
            if(logic().cards_reserve[1].size() > 0) drawCardBack(matrix, 296, 100, 0);
            if(logic().cards_reserve[0].size() > 0) drawCardBack(matrix, 296, 124, 0);

            drawCardBack(matrix, -72, 28, 7);
            int i = 0;
            for(Card c : logic().cards_finish){
                drawCard(matrix, -72, 28 + i*24, c);
                i++;
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
