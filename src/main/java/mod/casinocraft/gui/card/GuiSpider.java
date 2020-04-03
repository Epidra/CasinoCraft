package mod.casinocraft.gui.card;

import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicSpider;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiSpider extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSpider(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicSpider logic(){
        return (LogicSpider) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < 10; x++){
                    if(mouseRect(-32 + 32*x, 16+4 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*10); }
                }
            }
            if(mouseRect(296, 24+4, 32, 196, mouseX, mouseY)){ action(-1); }
        }
    }

    protected void keyTyped2(char typedChar, int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(-32 + x*32, 16+4 + y*(24-logic().compress), logic().cards_field[x].get(y));
            }
        }

        if(logic().selector.Y != -1) drawCardBack(logic().selector.X*32 - 32, 16+4 + logic().selector.Y*(24-logic().compress), 9);

        drawCardBack(296, 24+4, 7);

        if(logic().cards_reserve[4].size() > 0) drawCardBack(296, 24+4 + 0*24, 0);
        if(logic().cards_reserve[3].size() > 0) drawCardBack(296, 24+4 + 1*24, 0);
        if(logic().cards_reserve[2].size() > 0) drawCardBack(296, 24+4 + 2*24, 0);
        if(logic().cards_reserve[1].size() > 0) drawCardBack(296, 24+4 + 3*24, 0);
        if(logic().cards_reserve[0].size() > 0) drawCardBack(296, 24+4 + 4*24, 0);

        drawCardBack(-72, 24+4, 7);
        int i = 0;
        for(Card c : logic().cards_finish){
            drawCard(-72, 24+4 + i*24, c);
            i++;
        }
    }



    //--------------------CUSTOM--------------------

}
