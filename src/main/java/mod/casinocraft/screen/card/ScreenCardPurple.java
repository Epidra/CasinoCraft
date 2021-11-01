package mod.casinocraft.screen.card;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardPurple;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardPurple extends ScreenCasino {   // TriPeak

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardPurple(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardPurple logic(){
        return (LogicCardPurple) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 20; x++) {
                    if(mouseRect(-32 + 16*x, 20*y, 16, 20, mouseX, mouseY)){ action(x + y * 20); }
                }
            }
            if(mouseRect(16*5, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_stack.size() > 0)   action(-1); }
            if(mouseRect(16*7, 192, 32, 48, mouseX, mouseY)){           highlight(3);        action(-2); }
            if(mouseRect(16*9, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_reserve.size() > 0) action(-3); }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            drawFont(matrixstack, "POINTS",                 24, 24-3);
            drawFont(matrixstack, "" + logic().scorePoint,  34, 34-3);
            drawFont(matrixstack, "DRAWS",                 204, 24-3);
            drawFont(matrixstack, "" + logic().scoreLives, 214, 34-3);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){

            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_BUTTONS);
            blit(matrixstack, leftPos + 16*7 + 3+2, topPos +2+ 20 * 10, 234, 176 + (highlightIndex == 3 ? 22 : 0), 22, 22); // Button Stack

            drawCard(matrixstack, 16 *  3-32, 20 * 2, logic().cards_field[ 0]);
            drawCard(matrixstack, 16 *  9-32, 20 * 2, logic().cards_field[ 1]);
            drawCard(matrixstack, 16 * 15-32, 20 * 2, logic().cards_field[ 2]);

            drawCard(matrixstack, 16 *  2-32, 20 * 3, logic().cards_field[ 3]);
            drawCard(matrixstack, 16 *  4-32, 20 * 3, logic().cards_field[ 4]);
            drawCard(matrixstack, 16 *  8-32, 20 * 3, logic().cards_field[ 5]);
            drawCard(matrixstack, 16 * 10-32, 20 * 3, logic().cards_field[ 6]);
            drawCard(matrixstack, 16 * 14-32, 20 * 3, logic().cards_field[ 7]);
            drawCard(matrixstack, 16 * 16-32, 20 * 3, logic().cards_field[ 8]);

            drawCard(matrixstack, 16 *  1-32, 20 * 4, logic().cards_field[ 9]);
            drawCard(matrixstack, 16 *  3-32, 20 * 4, logic().cards_field[10]);
            drawCard(matrixstack, 16 *  5-32, 20 * 4, logic().cards_field[11]);
            drawCard(matrixstack, 16 *  7-32, 20 * 4, logic().cards_field[12]);
            drawCard(matrixstack, 16 *  9-32, 20 * 4, logic().cards_field[13]);
            drawCard(matrixstack, 16 * 11-32, 20 * 4, logic().cards_field[14]);
            drawCard(matrixstack, 16 * 13-32, 20 * 4, logic().cards_field[15]);
            drawCard(matrixstack, 16 * 15-32, 20 * 4, logic().cards_field[16]);
            drawCard(matrixstack, 16 * 17-32, 20 * 4, logic().cards_field[17]);

            drawCard(matrixstack, 16 *  0-32, 20 * 5, logic().cards_field[18]);
            drawCard(matrixstack, 16 *  2-32, 20 * 5, logic().cards_field[19]);
            drawCard(matrixstack, 16 *  4-32, 20 * 5, logic().cards_field[20]);
            drawCard(matrixstack, 16 *  6-32, 20 * 5, logic().cards_field[21]);
            drawCard(matrixstack, 16 *  8-32, 20 * 5, logic().cards_field[22]);
            drawCard(matrixstack, 16 * 10-32, 20 * 5, logic().cards_field[23]);
            drawCard(matrixstack, 16 * 12-32, 20 * 5, logic().cards_field[24]);
            drawCard(matrixstack, 16 * 14-32, 20 * 5, logic().cards_field[25]);
            drawCard(matrixstack, 16 * 16-32, 20 * 5, logic().cards_field[26]);
            drawCard(matrixstack, 16 * 18-32, 20 * 5, logic().cards_field[27]);

            drawCardBack(matrixstack, 16 * 5, 20 * 10 - 10, 7);
            drawCardBack(matrixstack, 16 * 9, 20 * 10 - 10, logic().scoreLives == 0 ? 8 : 10);

            if(logic().cards_reserve.size() > 1){ drawCard(matrixstack, 16 * 9, 20 * 10 - 10, logic().cards_reserve.get(1)); }
            if(logic().cards_reserve.size() > 0){ drawCard(matrixstack, 16 * 9, 20 * 10 - 10, logic().cards_reserve.get(0)); }
            if(logic().cards_stack.size()   > 1){ drawCard(matrixstack, 16 * 5, 20 * 10 - 10, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
            if(logic().cards_stack.size()   > 0){ drawCard(matrixstack, 16 * 5, 20 * 10 - 10, logic().cards_stack.get(logic().cards_stack.size() - 1)); }
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "tripeak";
    }



}
