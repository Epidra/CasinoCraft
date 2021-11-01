package mod.casinocraft.screen.card;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardMagenta;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardMagenta extends ScreenCasino {   // Pyramid

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardMagenta(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardMagenta logic(){
        return (LogicCardMagenta) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 15; x++) {
                    if(mouseRect(16*x, 20*y, 16, 20, mouseX, mouseY)){ action(x + y * 20); }
                }
            }
            if(mouseRect(16*5, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_stack.size() > 0)   action(-1); }
            if(mouseRect(16*7, 192, 32, 48, mouseX, mouseY)){           highlight(3);        action(-2); }
            if(mouseRect(16*9, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_reserve.size() > 0) action(-3); }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            drawFont(matrixstack, "POINTS",                24, 24);
            drawFont(matrixstack, "" + logic().scorePoint, 34, 34);
            drawFont(matrixstack, "DRAWS",                204, 24);
            drawFont(matrixstack, "" + logic().scoreLives,214, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){

            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
            blit(matrixstack, leftPos + 16*7 + 3+2, topPos +2+ 20 * 10, 234, 176 + (highlightIndex == 3 ? 22 : 0), 22, 22); // Button Stack

            drawCard(matrixstack, 16 *  7, 20 * 1, logic().cards_field[ 0]); if(logic().selector.X ==  0) drawCardBack(matrixstack, 16 *  7, 20 * 1, 9);

            drawCard(matrixstack, 16 *  6, 20 * 2, logic().cards_field[ 1]); if(logic().selector.X ==  1) drawCardBack(matrixstack, 16 *  6, 20 * 2, 9);
            drawCard(matrixstack, 16 *  8, 20 * 2, logic().cards_field[ 2]); if(logic().selector.X ==  2) drawCardBack(matrixstack, 16 *  8, 20 * 2, 9);

            drawCard(matrixstack, 16 *  5, 20 * 3, logic().cards_field[ 3]); if(logic().selector.X ==  3) drawCardBack(matrixstack, 16 *  5, 20 * 3, 9);
            drawCard(matrixstack, 16 *  7, 20 * 3, logic().cards_field[ 4]); if(logic().selector.X ==  4) drawCardBack(matrixstack, 16 *  7, 20 * 3, 9);
            drawCard(matrixstack, 16 *  9, 20 * 3, logic().cards_field[ 5]); if(logic().selector.X ==  5) drawCardBack(matrixstack, 16 *  9, 20 * 3, 9);

            drawCard(matrixstack, 16 *  4, 20 * 4, logic().cards_field[ 6]); if(logic().selector.X ==  6) drawCardBack(matrixstack, 16 *  4, 20 * 4, 9);
            drawCard(matrixstack, 16 *  6, 20 * 4, logic().cards_field[ 7]); if(logic().selector.X ==  7) drawCardBack(matrixstack, 16 *  6, 20 * 4, 9);
            drawCard(matrixstack, 16 *  8, 20 * 4, logic().cards_field[ 8]); if(logic().selector.X ==  8) drawCardBack(matrixstack, 16 *  8, 20 * 4, 9);
            drawCard(matrixstack, 16 * 10, 20 * 4, logic().cards_field[ 9]); if(logic().selector.X ==  9) drawCardBack(matrixstack, 16 * 10, 20 * 4, 9);

            drawCard(matrixstack, 16 *  3, 20 * 5, logic().cards_field[10]); if(logic().selector.X == 10) drawCardBack(matrixstack, 16 *  3, 20 * 5, 9);
            drawCard(matrixstack, 16 *  5, 20 * 5, logic().cards_field[11]); if(logic().selector.X == 11) drawCardBack(matrixstack, 16 *  5, 20 * 5, 9);
            drawCard(matrixstack, 16 *  7, 20 * 5, logic().cards_field[12]); if(logic().selector.X == 12) drawCardBack(matrixstack, 16 *  7, 20 * 5, 9);
            drawCard(matrixstack, 16 *  9, 20 * 5, logic().cards_field[13]); if(logic().selector.X == 13) drawCardBack(matrixstack, 16 *  9, 20 * 5, 9);
            drawCard(matrixstack, 16 * 11, 20 * 5, logic().cards_field[14]); if(logic().selector.X == 14) drawCardBack(matrixstack, 16 * 11, 20 * 5, 9);

            drawCard(matrixstack, 16 *  2, 20 * 6, logic().cards_field[15]); if(logic().selector.X == 15) drawCardBack(matrixstack, 16 *  2, 20 * 6, 9);
            drawCard(matrixstack, 16 *  4, 20 * 6, logic().cards_field[16]); if(logic().selector.X == 16) drawCardBack(matrixstack, 16 *  4, 20 * 6, 9);
            drawCard(matrixstack, 16 *  6, 20 * 6, logic().cards_field[17]); if(logic().selector.X == 17) drawCardBack(matrixstack, 16 *  6, 20 * 6, 9);
            drawCard(matrixstack, 16 *  8, 20 * 6, logic().cards_field[18]); if(logic().selector.X == 18) drawCardBack(matrixstack, 16 *  8, 20 * 6, 9);
            drawCard(matrixstack, 16 * 10, 20 * 6, logic().cards_field[19]); if(logic().selector.X == 19) drawCardBack(matrixstack, 16 * 10, 20 * 6, 9);
            drawCard(matrixstack, 16 * 12, 20 * 6, logic().cards_field[20]); if(logic().selector.X == 20) drawCardBack(matrixstack, 16 * 12, 20 * 6, 9);

            drawCard(matrixstack, 16 *  1, 20 * 7, logic().cards_field[21]); if(logic().selector.X == 21) drawCardBack(matrixstack, 16 *  1, 20 * 7, 9);
            drawCard(matrixstack, 16 *  3, 20 * 7, logic().cards_field[22]); if(logic().selector.X == 22) drawCardBack(matrixstack, 16 *  3, 20 * 7, 9);
            drawCard(matrixstack, 16 *  5, 20 * 7, logic().cards_field[23]); if(logic().selector.X == 23) drawCardBack(matrixstack, 16 *  5, 20 * 7, 9);
            drawCard(matrixstack, 16 *  7, 20 * 7, logic().cards_field[24]); if(logic().selector.X == 24) drawCardBack(matrixstack, 16 *  7, 20 * 7, 9);
            drawCard(matrixstack, 16 *  9, 20 * 7, logic().cards_field[25]); if(logic().selector.X == 25) drawCardBack(matrixstack, 16 *  9, 20 * 7, 9);
            drawCard(matrixstack, 16 * 11, 20 * 7, logic().cards_field[26]); if(logic().selector.X == 26) drawCardBack(matrixstack, 16 * 11, 20 * 7, 9);
            drawCard(matrixstack, 16 * 13, 20 * 7, logic().cards_field[27]); if(logic().selector.X == 27) drawCardBack(matrixstack, 16 * 13, 20 * 7, 9);

            drawCardBack(matrixstack, 16 * 5, 20 * 10 - 10, 7);
            drawCardBack(matrixstack, 16 * 9, 20 * 10 - 10, logic().scoreLives == 0 ? 8 : 10);

            if(logic().cards_reserve.size() > 1){ drawCard(matrixstack, 16 * 9, 20 * 10 - 10, logic().cards_reserve.get(1)); }
            if(logic().cards_reserve.size() > 0){ drawCard(matrixstack, 16 * 9, 20 * 10 - 10, logic().cards_reserve.get(0)); }
            if(logic().cards_stack.size()   > 1){ drawCard(matrixstack, 16 * 5, 20 * 10 - 10, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
            if(logic().cards_stack.size()   > 0){ drawCard(matrixstack, 16 * 5, 20 * 10 - 10, logic().cards_stack.get(logic().cards_stack.size() - 1)); }

            if(logic().selector.X == 28) drawCardBack(matrixstack, 16 * 5, 20 * 10 - 10, 9);
            if(logic().selector.X == 29) drawCardBack(matrixstack, 16 * 9, 20 * 10 - 10, 9);
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "pyramid";
    }



}
