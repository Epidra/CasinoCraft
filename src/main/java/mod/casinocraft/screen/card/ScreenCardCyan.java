package mod.casinocraft.screen.card;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardCyan;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardCyan extends ScreenCasino {   // Spider

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardCyan(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardCyan logic(){
        return (LogicCardCyan) CONTAINER.logic();
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

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
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

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "spider";
    }

}
