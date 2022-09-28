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





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardBlue logic(){
        return (LogicCardBlue) menu.logic();
    }

    protected String getGameName() {
        return tableID == 1 ? "free_cell" : "freecell";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        int offset = tableID == 1 ? -16 : 0;
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int x = tableID == 1 ? 1 : 0; x < 8; x++){
                for(int y = 0; y < 20; y++){
                    if(mouseRect(offset + 32*x, 64+4 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*8); }
                }
                if(mouseRect(offset + 32*x, 16+4, 32, 48, mouseX, mouseY)){ action((x+1) * -1); }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY){

    }

    protected void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        int offset = tableID == 1 ? -16 : 0;
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(matrix, offset + x*32, 64+4 + y*(24-logic().compress), logic().cards_field[x].get(y));
            }
        }
        if(tableID == 2)
            drawCardBack(matrix, offset,   20, 12);
        drawCardBack(matrix, offset +  32, 20, 12);
        drawCardBack(matrix, offset +  64, 20, 12);
        drawCardBack(matrix, offset +  96, 20, 12);
        drawCardBack(matrix, offset + 128, 20,  7);
        drawCardBack(matrix, offset + 160, 20,  7);
        drawCardBack(matrix, offset + 192, 20,  7);
        drawCardBack(matrix, offset + 224, 20,  7);


        drawCard(matrix, offset,      20, logic().cards_freecell[0]);
        drawCard(matrix, offset + 32, 20, logic().cards_freecell[1]);
        drawCard(matrix, offset + 64, 20, logic().cards_freecell[2]);
        drawCard(matrix, offset + 96, 20, logic().cards_freecell[3]);

        if(logic().cards_finish[0].size() > 1) drawCard(matrix, offset + 128, 20, logic().cards_finish[0].get(logic().cards_finish[0].size() - 2));
        if(logic().cards_finish[0].size() > 0) drawCard(matrix, offset + 128, 20, logic().cards_finish[0].get(logic().cards_finish[0].size() - 1));
        if(logic().cards_finish[1].size() > 1) drawCard(matrix, offset + 160, 20, logic().cards_finish[1].get(logic().cards_finish[1].size() - 2));
        if(logic().cards_finish[1].size() > 0) drawCard(matrix, offset + 160, 20, logic().cards_finish[1].get(logic().cards_finish[1].size() - 1));
        if(logic().cards_finish[2].size() > 1) drawCard(matrix, offset + 192, 20, logic().cards_finish[2].get(logic().cards_finish[2].size() - 2));
        if(logic().cards_finish[2].size() > 0) drawCard(matrix, offset + 192, 20, logic().cards_finish[2].get(logic().cards_finish[2].size() - 1));
        if(logic().cards_finish[3].size() > 1) drawCard(matrix, offset + 224, 20, logic().cards_finish[3].get(logic().cards_finish[3].size() - 2));
        if(logic().cards_finish[3].size() > 0) drawCard(matrix, offset + 224, 20, logic().cards_finish[3].get(logic().cards_finish[3].size() - 1));

        if(logic().selector.Y == -2){
            drawCardBack(matrix, offset + logic().selector.X*32, 20, 9);
        } else if(logic().selector.Y >= 0){
            drawCardBack(matrix, offset + logic().selector.X*32, 68 + logic().selector.Y*(24-logic().compress), 9);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
