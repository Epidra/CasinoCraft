package mod.casinocraft.gui.card;

import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicFreeCell;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiFreeCell extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiFreeCell(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicFreeCell logic(){
        return (LogicFreeCell) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int x = 0; x < 8; x++){
                for(int y = 0; y < 20; y++){
                    if(mouseRect(0 + 32*x, 64 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*8); }
                }
                if(mouseRect(0 + 32*x, 16, 32, 48, mouseX, mouseY)){ action((x+1) * -1); }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(logic().turnstate == 2 && keyCode == KEY_ENTER){ action(-9); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(x*32, 64 + y*(24-logic().compress), logic().cards_field[x].get(y));
            }
        }
        drawCardBack(32*0, 16, 12);
        drawCardBack(32*1, 16, 12);
        drawCardBack(32*2, 16, 12);
        drawCardBack(32*3, 16, 12);
        drawCardBack(32*4, 16,  7);
        drawCardBack(32*5, 16,  7);
        drawCardBack(32*6, 16,  7);
        drawCardBack(32*7, 16,  7);

        drawCard(0*32, 16, logic().cards_freecell[0]);
        drawCard(1*32, 16, logic().cards_freecell[1]);
        drawCard(2*32, 16, logic().cards_freecell[2]);
        drawCard(3*32, 16, logic().cards_freecell[3]);

        if(logic().cards_finish[0].size() > 0) drawCard(4*32, 16, logic().cards_finish[0].get(logic().cards_finish[0].size() - 1));
        if(logic().cards_finish[1].size() > 0) drawCard(5*32, 16, logic().cards_finish[1].get(logic().cards_finish[1].size() - 1));
        if(logic().cards_finish[2].size() > 0) drawCard(6*32, 16, logic().cards_finish[2].get(logic().cards_finish[2].size() - 1));
        if(logic().cards_finish[3].size() > 0) drawCard(7*32, 16, logic().cards_finish[3].get(logic().cards_finish[3].size() - 1));

        if(logic().selector.Y == -2){
            drawCardBack(logic().selector.X*32, 16, 9);
        } else if(logic().selector.Y >= 0){
            drawCardBack(logic().selector.X*32, 64 + logic().selector.Y*(24-logic().compress), 9);
        }
    }



    //--------------------CUSTOM--------------------

}
