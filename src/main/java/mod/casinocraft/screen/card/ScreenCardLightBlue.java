package mod.casinocraft.screen.card;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardLightBlue;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.KeyMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardLightBlue extends ScreenCasino {   // Klondike

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardLightBlue(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardLightBlue logic(){
        return (LogicCardLightBlue) menu.logic();
    }

    protected String getGameName() {
        return tableID == 1 ? "klon_dike" : "klondike";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        int offset = tableID == 1 ? -16 : 0;
        if(logic().turnstate == 2 && mouseButton == 1){
            action(KeyMap.KEY_ENTER);
        }
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 20; y++){
                for(int x = tableID == 1 ? 1 : 0; x < 8; x++){
                    if(mouseRect(offset + 32*x, 68 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*8); }
                }
            }
            if(mouseRect(-offset,       20, 32, 48, mouseX, mouseY)){ action(-1); }
            if(mouseRect(-offset +  32, 20, 32, 48, mouseX, mouseY)){ action(-2); }
            if(mouseRect( offset + 128, 20, 32, 48, mouseX, mouseY)){ action(-5); }
            if(mouseRect( offset + 160, 20, 32, 48, mouseX, mouseY)){ action(-6); }
            if(mouseRect( offset + 192, 20, 32, 48, mouseX, mouseY)){ action(-7); }
            if(mouseRect( offset + 224, 20, 32, 48, mouseX, mouseY)){ action(-8); }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        drawValueLeft(matrix, "POINTS", logic().scorePoint);
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        int offset = tableID == 1 ? -16 : 0;
        drawCardBack(matrix, -offset,       20, logic().scoreLives == 0 ? 8 : 10);
        drawCardBack(matrix, -offset +  32, 20, 7);
        drawCardBack(matrix,  offset + 128, 20, 7);
        drawCardBack(matrix,  offset + 160, 20, 7);
        drawCardBack(matrix,  offset + 192, 20, 7);
        drawCardBack(matrix,  offset + 224, 20, 7);

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(matrix, offset + 32*x, 68 + (24-logic().compress)*y, logic().cards_field[x].get(y));
            }
        }

        if(logic().cards_stack.size()   > 1) drawCard(    matrix, -offset + 32, 20, logic().cards_stack.get(logic().cards_stack.size() - 2));
        if(logic().cards_stack.size()   > 0) drawCard(    matrix, -offset + 32, 20, logic().cards_stack.get(logic().cards_stack.size() - 1));
        if(logic().cards_reserve.size() > 0) drawCardBack(matrix, -offset,      20, 0);

        if(logic().cards_finish[0].size() > 1) drawCard(matrix, offset + 128, 20, (logic().cards_finish[0].get((logic().cards_finish[0].size() - 2))));
        if(logic().cards_finish[0].size() > 0) drawCard(matrix, offset + 128, 20, (logic().cards_finish[0].get((logic().cards_finish[0].size() - 1))));
        if(logic().cards_finish[1].size() > 1) drawCard(matrix, offset + 160, 20, (logic().cards_finish[1].get((logic().cards_finish[1].size() - 2))));
        if(logic().cards_finish[1].size() > 0) drawCard(matrix, offset + 160, 20, (logic().cards_finish[1].get((logic().cards_finish[1].size() - 1))));
        if(logic().cards_finish[2].size() > 1) drawCard(matrix, offset + 192, 20, (logic().cards_finish[2].get((logic().cards_finish[2].size() - 2))));
        if(logic().cards_finish[2].size() > 0) drawCard(matrix, offset + 192, 20, (logic().cards_finish[2].get((logic().cards_finish[2].size() - 1))));
        if(logic().cards_finish[3].size() > 1) drawCard(matrix, offset + 224, 20, (logic().cards_finish[3].get((logic().cards_finish[3].size() - 2))));
        if(logic().cards_finish[3].size() > 0) drawCard(matrix, offset + 224, 20, (logic().cards_finish[3].get((logic().cards_finish[3].size() - 1))));

        if(logic().selector.Y == -2){
            drawCardBack(matrix, -offset + 32, 20, 9);
        } else if(logic().selector.Y >= 0){
            drawCardBack(matrix, offset + logic().selector.X*32, 20 + logic().selector.Y*(24-logic().compress), 9);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
