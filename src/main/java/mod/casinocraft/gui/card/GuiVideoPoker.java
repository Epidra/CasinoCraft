package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicVideoPoker;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiVideoPoker extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiVideoPoker(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicVideoPoker logic(){
        return (LogicVideoPoker) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2){
            if(mouseRect(  5,  24, 46, 26, mouseX, mouseY)){ action(0); } // Hold 1
            if(mouseRect( 55,  24, 46, 26, mouseX, mouseY)){ action(1); } // Hold 2
            if(mouseRect(105,  24, 46, 26, mouseX, mouseY)){ action(2); } // Hold 3
            if(mouseRect(155,  24, 46, 26, mouseX, mouseY)){ action(3); } // Hold 4
            if(mouseRect(205,  24, 46, 26, mouseX, mouseY)){ action(4); } // Hold 5
            if(mouseRect( 82, 204, 92, 26, mouseX, mouseY)){ action(5); } // Finish
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 4) drawString(logic().hand, 75, 150);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawCardBack(16,  72, 7); // Card 1
            drawCardBack(64,  72, 7); // Card 2
            drawCardBack(112, 72, 7); // Card 3
            drawCardBack(160, 72, 7); // Card 4
            drawCardBack(208, 72, 7); // Card 5
            drawCard(16,  72, logic().cards_field[0]); // Card 1
            drawCard(64,  72, logic().cards_field[1]); // Card 2
            drawCard(112, 72, logic().cards_field[2]); // Card 3
            drawCard(160, 72, logic().cards_field[3]); // Card 4
            drawCard(208, 72, logic().cards_field[4]); // Card 5
        }

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(logic().turnstate == 2){
            blit(guiLeft+82+7, guiTop+204+2, 78*2, 22, 78, 22); // Button Finish
            if(logic().hold[0]){blit(guiLeft+3+5,   guiTop+2+24, 78*2, 44, 39, 22);} else {blit(guiLeft+3+5,   guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 1
            if(logic().hold[1]){blit(guiLeft+3+55,  guiTop+2+24, 78*2, 44, 39, 22);} else {blit(guiLeft+3+55,  guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 2
            if(logic().hold[2]){blit(guiLeft+3+105, guiTop+2+24, 78*2, 44, 39, 22);} else {blit(guiLeft+3+105, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 3
            if(logic().hold[3]){blit(guiLeft+3+155, guiTop+2+24, 78*2, 44, 39, 22);} else {blit(guiLeft+3+155, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 4
            if(logic().hold[4]){blit(guiLeft+3+205, guiTop+2+24, 78*2, 44, 39, 22);} else {blit(guiLeft+3+205, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 5
        }
    }



    //--------------------CUSTOM--------------------

}
