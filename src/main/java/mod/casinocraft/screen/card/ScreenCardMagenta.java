package mod.casinocraft.screen.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardMagenta;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardMagenta extends ScreenCasino {   // Pyramid

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardMagenta(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardMagenta logic(){
        return (LogicCardMagenta) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 15; x++) {
                    if(mouseRect(0 + 16*x, 0 + 20*y, 16, 20, mouseX, mouseY)){ action(x + y * 20); }
                }
            }
            if(mouseRect(0 + 16*5, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_stack.size() > 0) action(-1); }
            if(mouseRect(0 + 16*7, 192, 32, 48, mouseX, mouseY)){                                action(-2); }
            if(mouseRect(0 + 16*9, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_reserve.size() > 0) action(-3); }
        }
    }

    protected void keyTyped2(int keyCode){

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            drawFont("POINTS",             24, 24);
            drawFont("" + logic().scorePoint,  34, 34);
            drawFont("DRAWS",             204, 24);
            drawFont("" + logic().scoreLives, 214, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){

            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
            blit(guiLeft + 16*7 + 3+2, guiTop +2+ 20 * 10, 234, 0, 22, 22); // Button Stack

            drawCard(16 *  7, 20 * 1, logic().cards_field[ 0]); if(logic().selector.X ==  0) drawCardBack(16 *  7, 20 * 1, 9);

            drawCard(16 *  6, 20 * 2, logic().cards_field[ 1]); if(logic().selector.X ==  1) drawCardBack(16 *  6, 20 * 2, 9);
            drawCard(16 *  8, 20 * 2, logic().cards_field[ 2]); if(logic().selector.X ==  2) drawCardBack(16 *  8, 20 * 2, 9);

            drawCard(16 *  5, 20 * 3, logic().cards_field[ 3]); if(logic().selector.X ==  3) drawCardBack(16 *  5, 20 * 3, 9);
            drawCard(16 *  7, 20 * 3, logic().cards_field[ 4]); if(logic().selector.X ==  4) drawCardBack(16 *  7, 20 * 3, 9);
            drawCard(16 *  9, 20 * 3, logic().cards_field[ 5]); if(logic().selector.X ==  5) drawCardBack(16 *  9, 20 * 3, 9);

            drawCard(16 *  4, 20 * 4, logic().cards_field[ 6]); if(logic().selector.X ==  6) drawCardBack(16 *  4, 20 * 4, 9);
            drawCard(16 *  6, 20 * 4, logic().cards_field[ 7]); if(logic().selector.X ==  7) drawCardBack(16 *  6, 20 * 4, 9);
            drawCard(16 *  8, 20 * 4, logic().cards_field[ 8]); if(logic().selector.X ==  8) drawCardBack(16 *  8, 20 * 4, 9);
            drawCard(16 * 10, 20 * 4, logic().cards_field[ 9]); if(logic().selector.X ==  9) drawCardBack(16 * 10, 20 * 4, 9);

            drawCard(16 *  3, 20 * 5, logic().cards_field[10]); if(logic().selector.X == 10) drawCardBack(16 *  3, 20 * 5, 9);
            drawCard(16 *  5, 20 * 5, logic().cards_field[11]); if(logic().selector.X == 11) drawCardBack(16 *  5, 20 * 5, 9);
            drawCard(16 *  7, 20 * 5, logic().cards_field[12]); if(logic().selector.X == 12) drawCardBack(16 *  7, 20 * 5, 9);
            drawCard(16 *  9, 20 * 5, logic().cards_field[13]); if(logic().selector.X == 13) drawCardBack(16 *  9, 20 * 5, 9);
            drawCard(16 * 11, 20 * 5, logic().cards_field[14]); if(logic().selector.X == 14) drawCardBack(16 * 11, 20 * 5, 9);

            drawCard(16 *  2, 20 * 6, logic().cards_field[15]); if(logic().selector.X == 15) drawCardBack(16 *  2, 20 * 6, 9);
            drawCard(16 *  4, 20 * 6, logic().cards_field[16]); if(logic().selector.X == 16) drawCardBack(16 *  4, 20 * 6, 9);
            drawCard(16 *  6, 20 * 6, logic().cards_field[17]); if(logic().selector.X == 17) drawCardBack(16 *  6, 20 * 6, 9);
            drawCard(16 *  8, 20 * 6, logic().cards_field[18]); if(logic().selector.X == 18) drawCardBack(16 *  8, 20 * 6, 9);
            drawCard(16 * 10, 20 * 6, logic().cards_field[19]); if(logic().selector.X == 19) drawCardBack(16 * 10, 20 * 6, 9);
            drawCard(16 * 12, 20 * 6, logic().cards_field[20]); if(logic().selector.X == 20) drawCardBack(16 * 12, 20 * 6, 9);

            drawCard(16 *  1, 20 * 7, logic().cards_field[21]); if(logic().selector.X == 21) drawCardBack(16 *  1, 20 * 7, 9);
            drawCard(16 *  3, 20 * 7, logic().cards_field[22]); if(logic().selector.X == 22) drawCardBack(16 *  3, 20 * 7, 9);
            drawCard(16 *  5, 20 * 7, logic().cards_field[23]); if(logic().selector.X == 23) drawCardBack(16 *  5, 20 * 7, 9);
            drawCard(16 *  7, 20 * 7, logic().cards_field[24]); if(logic().selector.X == 24) drawCardBack(16 *  7, 20 * 7, 9);
            drawCard(16 *  9, 20 * 7, logic().cards_field[25]); if(logic().selector.X == 25) drawCardBack(16 *  9, 20 * 7, 9);
            drawCard(16 * 11, 20 * 7, logic().cards_field[26]); if(logic().selector.X == 26) drawCardBack(16 * 11, 20 * 7, 9);
            drawCard(16 * 13, 20 * 7, logic().cards_field[27]); if(logic().selector.X == 27) drawCardBack(16 * 13, 20 * 7, 9);

            drawCardBack(16 * 5, 20 * 10 - 10, 7);
            drawCardBack(16 * 9, 20 * 10 - 10, logic().scoreLives == 0 ? 8 : 10);

            if(logic().cards_reserve.size() > 1){ drawCard(16 * 9, 20 * 10 - 10, logic().cards_reserve.get(1)); }
            if(logic().cards_reserve.size() > 0){ drawCard(16 * 9, 20 * 10 - 10, logic().cards_reserve.get(0)); }
            if(logic().cards_stack.size() > 1){ drawCard(16 * 5, 20 * 10 - 10, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
            if(logic().cards_stack.size() > 0){ drawCard(16 * 5, 20 * 10 - 10, logic().cards_stack.get(logic().cards_stack.size() - 1)); }

            if(logic().selector.X == 28) drawCardBack(16 * 5, 20 * 10 - 10, 9);
            if(logic().selector.X == 29) drawCardBack(16 * 9, 20 * 10 - 10, 9);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "pyramid";
    }

}
