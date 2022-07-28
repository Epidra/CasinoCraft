package mod.casinocraft.screen.card;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardCyan;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardCyan extends ScreenCasino {   // Spider

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardCyan(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardCyan logic(){
        return (LogicCardCyan) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < 10; x++){
                    if(mouseRect(-32 + 32*x, 16+4 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*10); }
                }
            }
            if(mouseRect(296, 24+4, 32, 196, mouseX, mouseY)){ action(-1); }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(matrixstack, -32 + x*32, 16+4 + y*(24-logic().compress), logic().cards_field[x].get(y));
            }
        }

        if(logic().selector.Y != -1) drawCardBack(matrixstack, logic().selector.X*32 - 32, 16+4 + logic().selector.Y*(24-logic().compress), 9);

        drawCardBack(matrixstack, 296, 24+4, 7);

        if(logic().cards_reserve[4].size() > 0) drawCardBack(matrixstack, 296, 24+4 + 0*24, 0);
        if(logic().cards_reserve[3].size() > 0) drawCardBack(matrixstack, 296, 24+4 + 1*24, 0);
        if(logic().cards_reserve[2].size() > 0) drawCardBack(matrixstack, 296, 24+4 + 2*24, 0);
        if(logic().cards_reserve[1].size() > 0) drawCardBack(matrixstack, 296, 24+4 + 3*24, 0);
        if(logic().cards_reserve[0].size() > 0) drawCardBack(matrixstack, 296, 24+4 + 4*24, 0);

        drawCardBack(matrixstack, -72, 24+4, 7);
        int i = 0;
        for(Card c : logic().cards_finish){
            drawCard(matrixstack, -72, 24+4 + i*24, c);
            i++;
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "spider";
    }



}
