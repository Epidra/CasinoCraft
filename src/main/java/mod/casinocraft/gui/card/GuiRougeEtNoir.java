package mod.casinocraft.gui.card;

import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicRougeEtNoir;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiRougeEtNoir extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiRougeEtNoir(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicRougeEtNoir logic(){
        return (LogicRougeEtNoir) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2) {
            if(mouseButton == 0){
                if(mouseRect(32,  48, 32, 48, mouseX, mouseY)){ action(0); }
                if(mouseRect(32, 144, 32, 48, mouseX, mouseY)){ action(1); }
            }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 3) drawString("" + logic().value_rouge, 36,  38);
        if(logic().turnstate >= 3) drawString("" + logic().value_noir, 36, 134);
        if(logic().turnstate >= 4) drawString(logic().hand, 65, 115);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        drawCardBack(32,  48, 1);
        drawCardBack(32, 144, 0);
        int i = 0; for(Card c : logic().cards_rouge){ drawCard(32+8 + 16*i,  48, c); i++; }
            i = 0; for(Card c : logic().cards_noir ){ drawCard(32+8 + 16*i, 144, c); i++; }
    }



    //--------------------CUSTOM--------------------

}
