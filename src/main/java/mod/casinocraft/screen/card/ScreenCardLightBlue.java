package mod.casinocraft.screen.card;

import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardLightBlue;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static mod.casinocraft.util.KeyMap.KEY_ENTER;

public class ScreenCardLightBlue extends ScreenCasino {   // Klondike

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardLightBlue(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardLightBlue logic(){
        return (LogicCardLightBlue) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2 && mouseButton == 2){
            action(-9);
        }
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < 8; x++){
                    if(mouseRect(0 + 32*x, 64+4 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*8); }
                }
            }
            if(mouseRect(0 + 32*0, 16+4, 32, 48, mouseX, mouseY)){ action(-1); }
            if(mouseRect(0 + 32*1, 16+4, 32, 48, mouseX, mouseY)){ action(-2); }

            if(mouseRect(0 + 32*4, 16+4, 32, 48, mouseX, mouseY)){ action(-5); }
            if(mouseRect(0 + 32*5, 16+4, 32, 48, mouseX, mouseY)){ action(-6); }
            if(mouseRect(0 + 32*6, 16+4, 32, 48, mouseX, mouseY)){ action(-7); }
            if(mouseRect(0 + 32*7, 16+4, 32, 48, mouseX, mouseY)){ action(-8); }
        }
    }

    protected void keyTyped2(int keyCode){
        if(logic().turnstate == 2 && keyCode == KEY_ENTER){ action(-9); }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        drawFont("POINTS",           75, 25);
        drawFont("" + logic().scorePoint, 85, 35);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        drawCardBack(32*0, 16+4, logic().scoreLives == 0 ? 8 : 10);
        drawCardBack(32*1, 16+4, 7);
        drawCardBack(32*4, 16+4, 7);
        drawCardBack(32*5, 16+4, 7);
        drawCardBack(32*6, 16+4, 7);
        drawCardBack(32*7, 16+4, 7);

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(0 + 32*x, 64+4 + (24-logic().compress)*y, logic().cards_field[x].get(y));
            }
        }

        if(logic().cards_stack.size() > 1) drawCard(32, 16+4, logic().cards_stack.get(logic().cards_stack.size() - 2));
        if(logic().cards_stack.size() > 0) drawCard(32, 16+4, logic().cards_stack.get(logic().cards_stack.size() - 1));
        if(logic().cards_reserve.size() > 0) drawCardBack(32*0, 16+4, 0);

        if(logic().cards_finish[0].size() > 1) drawCard(4*32, 16+4, (logic().cards_finish[0].get((logic().cards_finish[0].size() - 2))));
        if(logic().cards_finish[0].size() > 0) drawCard(4*32, 16+4, (logic().cards_finish[0].get((logic().cards_finish[0].size() - 1))));
        if(logic().cards_finish[1].size() > 1) drawCard(5*32, 16+4, (logic().cards_finish[1].get((logic().cards_finish[1].size() - 2))));
        if(logic().cards_finish[1].size() > 0) drawCard(5*32, 16+4, (logic().cards_finish[1].get((logic().cards_finish[1].size() - 1))));
        if(logic().cards_finish[2].size() > 1) drawCard(6*32, 16+4, (logic().cards_finish[2].get((logic().cards_finish[2].size() - 2))));
        if(logic().cards_finish[2].size() > 0) drawCard(6*32, 16+4, (logic().cards_finish[2].get((logic().cards_finish[2].size() - 1))));
        if(logic().cards_finish[3].size() > 1) drawCard(7*32, 16+4, (logic().cards_finish[3].get((logic().cards_finish[3].size() - 2))));
        if(logic().cards_finish[3].size() > 0) drawCard(7*32, 16+4, (logic().cards_finish[3].get((logic().cards_finish[3].size() - 1))));

        if(logic().selector.Y == -2){
            drawCardBack(32, 16+4, 9);
        } else if(logic().selector.Y >= 0){
            drawCardBack(logic().selector.X*32, 64+4 + logic().selector.Y*(24-logic().compress), 9);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "klondike";
    }

}
