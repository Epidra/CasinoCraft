package mod.casinocraft.screen.card;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardPurple;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import mod.lucky77.util.Vector2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardPurple extends ScreenCasino {   // TriPeak

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardPurple(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardPurple logic(){
        return (LogicCardPurple) menu.logic();
    }

    protected String getGameName() {
        return tableID == 1 ? "twin_peak" : "tripeak";
    }

    protected void createGameButtons(){
        buttonSet.addButton(new Vector2(200, 188), ButtonMap.ARROW_LEFT_OFF, ButtonMap.ARROW_LEFT_ON, 1, 32, () -> isActivePlayer() && logic().turnstate == 2, () -> action(-2));
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        int offset = tableID == 1 ? 48 : 0;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 20; x++) {
                if(mouseRect(offset + -32 + 16*x, 20*y, 16, 20, mouseX, mouseY)){ action(x + y * 20); }
            }
        }
        if(mouseRect(112, 176, 32, 48, mouseX, mouseY)){ if(logic().cards_stack.size()   > 0) action(-1); }
        if(mouseRect(160, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_reserve.size() > 0) action(-3); }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
        drawValueLeft(matrix, "POINTS", logic().scorePoint);
        drawValueRight(matrix, "DRAWS", logic().scoreLives);
    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        int offset = tableID == 1 ? 16 : -32;

        drawCard(matrix, offset +  48,  40, logic().cards_field[ 0]);
        drawCard(matrix, offset + 144,  40, logic().cards_field[ 1]);
        drawCard(matrix, offset + 240,  40, logic().cards_field[ 2]);
        drawCard(matrix, offset +  32,  60, logic().cards_field[ 3]);
        drawCard(matrix, offset +  64,  60, logic().cards_field[ 4]);
        drawCard(matrix, offset + 128,  60, logic().cards_field[ 5]);
        drawCard(matrix, offset + 160,  60, logic().cards_field[ 6]);
        drawCard(matrix, offset + 224,  60, logic().cards_field[ 7]);
        drawCard(matrix, offset + 256,  60, logic().cards_field[ 8]);
        drawCard(matrix, offset +  16,  80, logic().cards_field[ 9]);
        drawCard(matrix, offset +  48,  80, logic().cards_field[10]);
        drawCard(matrix, offset +  80,  80, logic().cards_field[11]);
        drawCard(matrix, offset + 112,  80, logic().cards_field[12]);
        drawCard(matrix, offset + 144,  80, logic().cards_field[13]);
        drawCard(matrix, offset + 176,  80, logic().cards_field[14]);
        drawCard(matrix, offset + 208,  80, logic().cards_field[15]);
        drawCard(matrix, offset + 240,  80, logic().cards_field[16]);
        drawCard(matrix, offset + 272,  80, logic().cards_field[17]);
        drawCard(matrix, offset,       100, logic().cards_field[18]);
        drawCard(matrix, offset +  32, 100, logic().cards_field[19]);
        drawCard(matrix, offset +  64, 100, logic().cards_field[20]);
        drawCard(matrix, offset +  96, 100, logic().cards_field[21]);
        drawCard(matrix, offset + 128, 100, logic().cards_field[22]);
        drawCard(matrix, offset + 160, 100, logic().cards_field[23]);
        drawCard(matrix, offset + 192, 100, logic().cards_field[24]);
        drawCard(matrix, offset + 224, 100, logic().cards_field[25]);
        drawCard(matrix, offset + 256, 100, logic().cards_field[26]);
        drawCard(matrix, offset + 288, 100, logic().cards_field[27]);

        drawCardBack(matrix, 112, 176, 7);
        drawCardBack(matrix, 152, 176, logic().scoreLives == 0 ? 8 : 10);

        if(logic().cards_reserve.size() > 1){ drawCard(matrix, 152, 176, logic().cards_reserve.get(1)); }
        if(logic().cards_reserve.size() > 0){ drawCard(matrix, 152, 176, logic().cards_reserve.get(0)); }
        if(logic().cards_stack.size()   > 1){ drawCard(matrix, 112, 176, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
        if(logic().cards_stack.size()   > 0){ drawCard(matrix, 112, 176, logic().cards_stack.get(logic().cards_stack.size() - 1)); }
        drawCardBack(matrix, 160, 176, 0);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
