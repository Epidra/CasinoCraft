package mod.casinocraft.screen.card;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardMagenta;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import mod.lucky77.util.Vector2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardMagenta extends ScreenCasino {   // Pyramid

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardMagenta(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardMagenta logic(){
        return (LogicCardMagenta) menu.logic();
    }

    protected String getGameName() {
        return "pyramid";
    }

    protected void createGameButtons(){
        buttonSet.addButton(new Vector2(168, 204), ButtonMap.ARROW_LEFT_OFF, ButtonMap.ARROW_LEFT_ON, 1, 32, () -> isActivePlayer() && logic().turnstate == 2, () -> action(-2));
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 15; x++) {
                if(mouseRect(16*x, 20*y, 16, 20, mouseX, mouseY)){ action(x + y * 20); }
            }
        }
        if(mouseRect( 92, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_stack.size()   > 0) action(-1); }
        if(mouseRect(132, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_reserve.size() > 0) action(-3); }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY){
        drawValueLeft( matrix, "POINTS", logic().scorePoint);
        drawValueRight(matrix, "DRAWS",  logic().scoreLives);
    }

    protected void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        drawCard(matrix, 112,  20, logic().cards_field[ 0]); if(logic().selector.X ==  0) drawCardBack(matrix, 112,  20, 9);

        drawCard(matrix,  96,  40, logic().cards_field[ 1]); if(logic().selector.X ==  1) drawCardBack(matrix,  96,  40, 9);
        drawCard(matrix, 128,  40, logic().cards_field[ 2]); if(logic().selector.X ==  2) drawCardBack(matrix, 128,  40, 9);

        drawCard(matrix,  80,  60, logic().cards_field[ 3]); if(logic().selector.X ==  3) drawCardBack(matrix,  80,  60, 9);
        drawCard(matrix, 112,  60, logic().cards_field[ 4]); if(logic().selector.X ==  4) drawCardBack(matrix, 112,  60, 9);
        drawCard(matrix, 144,  60, logic().cards_field[ 5]); if(logic().selector.X ==  5) drawCardBack(matrix, 144,  60, 9);

        drawCard(matrix,  64,  80, logic().cards_field[ 6]); if(logic().selector.X ==  6) drawCardBack(matrix,  64,  80, 9);
        drawCard(matrix,  96,  80, logic().cards_field[ 7]); if(logic().selector.X ==  7) drawCardBack(matrix,  96,  80, 9);
        drawCard(matrix, 128,  80, logic().cards_field[ 8]); if(logic().selector.X ==  8) drawCardBack(matrix, 128,  80, 9);
        drawCard(matrix, 160,  80, logic().cards_field[ 9]); if(logic().selector.X ==  9) drawCardBack(matrix, 160,  80, 9);

        drawCard(matrix,  48, 100, logic().cards_field[10]); if(logic().selector.X == 10) drawCardBack(matrix,  48, 100, 9);
        drawCard(matrix,  80, 100, logic().cards_field[11]); if(logic().selector.X == 11) drawCardBack(matrix,  80, 100, 9);
        drawCard(matrix, 112, 100, logic().cards_field[12]); if(logic().selector.X == 12) drawCardBack(matrix, 112, 100, 9);
        drawCard(matrix, 144, 100, logic().cards_field[13]); if(logic().selector.X == 13) drawCardBack(matrix, 144, 100, 9);
        drawCard(matrix, 176, 100, logic().cards_field[14]); if(logic().selector.X == 14) drawCardBack(matrix, 176, 100, 9);

        drawCard(matrix,  32, 120, logic().cards_field[15]); if(logic().selector.X == 15) drawCardBack(matrix,  32, 120, 9);
        drawCard(matrix,  64, 120, logic().cards_field[16]); if(logic().selector.X == 16) drawCardBack(matrix,  64, 120, 9);
        drawCard(matrix,  96, 120, logic().cards_field[17]); if(logic().selector.X == 17) drawCardBack(matrix,  96, 120, 9);
        drawCard(matrix, 128, 120, logic().cards_field[18]); if(logic().selector.X == 18) drawCardBack(matrix, 128, 120, 9);
        drawCard(matrix, 160, 120, logic().cards_field[19]); if(logic().selector.X == 19) drawCardBack(matrix, 160, 120, 9);
        drawCard(matrix, 192, 120, logic().cards_field[20]); if(logic().selector.X == 20) drawCardBack(matrix, 192, 120, 9);

        drawCard(matrix,  16, 140, logic().cards_field[21]); if(logic().selector.X == 21) drawCardBack(matrix,  16, 140, 9);
        drawCard(matrix,  48, 140, logic().cards_field[22]); if(logic().selector.X == 22) drawCardBack(matrix,  48, 140, 9);
        drawCard(matrix,  80, 140, logic().cards_field[23]); if(logic().selector.X == 23) drawCardBack(matrix,  80, 140, 9);
        drawCard(matrix, 112, 140, logic().cards_field[24]); if(logic().selector.X == 24) drawCardBack(matrix, 112, 140, 9);
        drawCard(matrix, 144, 140, logic().cards_field[25]); if(logic().selector.X == 25) drawCardBack(matrix, 144, 140, 9);
        drawCard(matrix, 176, 140, logic().cards_field[26]); if(logic().selector.X == 26) drawCardBack(matrix, 176, 140, 9);
        drawCard(matrix, 208, 140, logic().cards_field[27]); if(logic().selector.X == 27) drawCardBack(matrix, 208, 140, 9);

        drawCardBack(matrix,  92, 192, 7);
        drawCardBack(matrix, 132, 192, logic().scoreLives == 0 ? 8 : 10);

        if(logic().cards_reserve.size() > 1){ drawCard(matrix, 132, 192, logic().cards_reserve.get(1)); }
        if(logic().cards_reserve.size() > 0){ drawCard(matrix, 132, 192, logic().cards_reserve.get(0)); }
        if(logic().cards_stack.size()   > 1){ drawCard(matrix,  92, 192, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
        if(logic().cards_stack.size()   > 0){ drawCard(matrix,  92, 192, logic().cards_stack.get(logic().cards_stack.size() - 1)); }

        if(logic().selector.X == 28) drawCardBack(matrix,  92, 192, 9);
        if(logic().selector.X == 29) drawCardBack(matrix, 132, 192, 9);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
