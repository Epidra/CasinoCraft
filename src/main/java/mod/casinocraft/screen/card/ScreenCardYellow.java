package mod.casinocraft.screen.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardYellow;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardYellow extends ScreenCasino {   // Acey Deucey

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardYellow(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardYellow logic(){
        return (LogicCardYellow) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(anotherBet() ? 0 : 1); } else
        if(logic().turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
    }

    protected void keyTyped2(int keyCode){

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().spread > -1) drawFont("Spread: " + logic().spread,  100, 125);
        drawFont(logic().hand,  75, 150);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        drawCard(64,  72, logic().cards[0]);
        if(logic().cards[2] != null){ drawCard(112, 72, logic().cards[2]); }
        drawCard(160, 72, logic().cards[1]);
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            blit(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            blit(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "acey_deucey";
    }

}
