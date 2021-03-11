package mod.casinocraft.screen.card;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardBlue;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardBlue extends ScreenCasino {   // FreeCell

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardBlue(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardBlue logic(){
        return (LogicCardBlue) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int x = 0; x < 8; x++){
                for(int y = 0; y < 20; y++){
                    if(mouseRect(32*x, 64+4 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*8); }
                }
                if(mouseRect(32*x, 16+4, 32, 48, mouseX, mouseY)){ action((x+1) * -1); }
            }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(matrixstack, x*32, 64+4 + y*(24-logic().compress), logic().cards_field[x].get(y));
            }
        }
        drawCardBack(matrixstack, 32*0, 16+4, 12);
        drawCardBack(matrixstack, 32*1, 16+4, 12);
        drawCardBack(matrixstack, 32*2, 16+4, 12);
        drawCardBack(matrixstack, 32*3, 16+4, 12);
        drawCardBack(matrixstack, 32*4, 16+4,  7);
        drawCardBack(matrixstack, 32*5, 16+4,  7);
        drawCardBack(matrixstack, 32*6, 16+4,  7);
        drawCardBack(matrixstack, 32*7, 16+4,  7);

        drawCard(matrixstack, 0*32, 16+4, logic().cards_freecell[0]);
        drawCard(matrixstack, 1*32, 16+4, logic().cards_freecell[1]);
        drawCard(matrixstack, 2*32, 16+4, logic().cards_freecell[2]);
        drawCard(matrixstack, 3*32, 16+4, logic().cards_freecell[3]);

        if(logic().cards_finish[0].size() > 1) drawCard(matrixstack, 4*32, 16+4, logic().cards_finish[0].get(logic().cards_finish[0].size() - 2));
        if(logic().cards_finish[0].size() > 0) drawCard(matrixstack, 4*32, 16+4, logic().cards_finish[0].get(logic().cards_finish[0].size() - 1));
        if(logic().cards_finish[1].size() > 1) drawCard(matrixstack, 5*32, 16+4, logic().cards_finish[1].get(logic().cards_finish[1].size() - 2));
        if(logic().cards_finish[1].size() > 0) drawCard(matrixstack, 5*32, 16+4, logic().cards_finish[1].get(logic().cards_finish[1].size() - 1));
        if(logic().cards_finish[2].size() > 1) drawCard(matrixstack, 6*32, 16+4, logic().cards_finish[2].get(logic().cards_finish[2].size() - 2));
        if(logic().cards_finish[2].size() > 0) drawCard(matrixstack, 6*32, 16+4, logic().cards_finish[2].get(logic().cards_finish[2].size() - 1));
        if(logic().cards_finish[3].size() > 1) drawCard(matrixstack, 7*32, 16+4, logic().cards_finish[3].get(logic().cards_finish[3].size() - 2));
        if(logic().cards_finish[3].size() > 0) drawCard(matrixstack, 7*32, 16+4, logic().cards_finish[3].get(logic().cards_finish[3].size() - 1));

        if(logic().selector.Y == -2){
            drawCardBack(matrixstack, logic().selector.X*32, 16+4, 9);
        } else if(logic().selector.Y >= 0){
            drawCardBack(matrixstack, logic().selector.X*32, 64+4 + logic().selector.Y*(24-logic().compress), 9);
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "freecell";
    }

}
