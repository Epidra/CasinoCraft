package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicTriPeak;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiTriPeak extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiTriPeak(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicTriPeak logic(){
        return (LogicTriPeak) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 20; x++) {
                    if(mouseRect(-32 + 16*x, 0 + 20*y, 16, 20, mouseX, mouseY)){ action(x + y * 20); }
                }
            }
            if(mouseRect(0 + 16*5, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_stack.size() > 0) action(-1); }
            if(mouseRect(0 + 16*7, 192, 32, 48, mouseX, mouseY)){                                action(-2); }
            if(mouseRect(0 + 16*9, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_reserve.size() > 0) action(-3); }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            drawString("POINTS",             24, 24);
            drawString("" + logic().scorePoint,  34, 34);
            drawString("DRAWS",             204, 24);
            drawString("" + logic().scoreLives, 214, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){

            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            blit(guiLeft + 16*7 + 3+2, guiTop +2+ 20 * 10, 234, 0, 22, 22); // Button Stack

            drawCard(16 *  3-32, 20 * 2, logic().cards_field[ 0]);
            drawCard(16 *  9-32, 20 * 2, logic().cards_field[ 1]);
            drawCard(16 * 15-32, 20 * 2, logic().cards_field[ 2]);

            drawCard(16 *  2-32, 20 * 3, logic().cards_field[ 3]);
            drawCard(16 *  4-32, 20 * 3, logic().cards_field[ 4]);
            drawCard(16 *  8-32, 20 * 3, logic().cards_field[ 5]);
            drawCard(16 * 10-32, 20 * 3, logic().cards_field[ 6]);
            drawCard(16 * 14-32, 20 * 3, logic().cards_field[ 7]);
            drawCard(16 * 16-32, 20 * 3, logic().cards_field[ 8]);

            drawCard(16 *  1-32, 20 * 4, logic().cards_field[ 9]);
            drawCard(16 *  3-32, 20 * 4, logic().cards_field[10]);
            drawCard(16 *  5-32, 20 * 4, logic().cards_field[11]);
            drawCard(16 *  7-32, 20 * 4, logic().cards_field[12]);
            drawCard(16 *  9-32, 20 * 4, logic().cards_field[13]);
            drawCard(16 * 11-32, 20 * 4, logic().cards_field[14]);
            drawCard(16 * 13-32, 20 * 4, logic().cards_field[15]);
            drawCard(16 * 15-32, 20 * 4, logic().cards_field[16]);
            drawCard(16 * 17-32, 20 * 4, logic().cards_field[17]);

            drawCard(16 *  0-32, 20 * 5, logic().cards_field[18]);
            drawCard(16 *  2-32, 20 * 5, logic().cards_field[19]);
            drawCard(16 *  4-32, 20 * 5, logic().cards_field[20]);
            drawCard(16 *  6-32, 20 * 5, logic().cards_field[21]);
            drawCard(16 *  8-32, 20 * 5, logic().cards_field[22]);
            drawCard(16 * 10-32, 20 * 5, logic().cards_field[23]);
            drawCard(16 * 12-32, 20 * 5, logic().cards_field[24]);
            drawCard(16 * 14-32, 20 * 5, logic().cards_field[25]);
            drawCard(16 * 16-32, 20 * 5, logic().cards_field[26]);
            drawCard(16 * 18-32, 20 * 5, logic().cards_field[27]);

            drawCardBack(16 * 5, 20 * 10 - 10, 7);
            drawCardBack(16 * 9, 20 * 10 - 10, logic().scoreLives == 0 ? 8 : 10);

            if(logic().cards_reserve.size() > 1){ drawCard(16 * 9, 20 * 10 - 10, logic().cards_reserve.get(1)); }
            if(logic().cards_reserve.size() > 0){ drawCard(16 * 9, 20 * 10 - 10, logic().cards_reserve.get(0)); }
            if(logic().cards_stack.size() > 1){ drawCard(16 * 5, 20 * 10 - 10, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
            if(logic().cards_stack.size() > 0){ drawCard(16 * 5, 20 * 10 - 10, logic().cards_stack.get(logic().cards_stack.size() - 1)); }
        }
    }



    //--------------------CUSTOM--------------------

}