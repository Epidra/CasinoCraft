package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicAceyDeucey;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiAceyDeucey extends GuiCasino {



    //--------------------CONSTRUCTOR--------------------

    public GuiAceyDeucey(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }

    //--------------------BASIC--------------------

    public LogicAceyDeucey logic(){
        return (LogicAceyDeucey) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(AnotherBet() ? 0 : 1); } else
        if(logic().turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().spread > -1) drawString("Spread: " + logic().spread,  100, 125);
        drawString(logic().hand,  75, 150);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        drawCard(64,  72, logic().cards[0]);
        if(logic().cards[2] != null){ drawCard(112, 72, logic().cards[2]); }
        drawCard(160, 72, logic().cards[1]);

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(logic().turnstate == 2){
            blit(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            blit(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }


    }

}
