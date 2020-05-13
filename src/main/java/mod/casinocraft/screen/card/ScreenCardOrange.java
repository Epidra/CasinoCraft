package mod.casinocraft.screen.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardOrange;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardOrange extends ScreenCasino {   // Baccarat

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardOrange(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardOrange logic(){
        return (LogicCardOrange) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(0); } else
        if(logic().turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
    }

    protected void keyTyped2(int keyCode){

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        drawFont("PLAYER:  " + logic().value_player, 24, 24);
        drawFont("DEALER:  " + logic().value_dealer, 24, 40);

        if(logic().status == 1)     drawFont("Natural Draw!",    80, 170);
        if(logic().status == 2)     drawFont("continue drawing", 80, 170);
        if(logic().turnstate  >= 4) drawFont(logic().hand,            80, 190);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            for(int z = 0; z < logic().cards_player.size(); z++){ if(logic().cards_player.get(z).idletimer == 0) drawCard( 24 + 16*z, 80 + 4*z, logic().cards_player.get(z)); }
            for(int z = 0; z < logic().cards_dealer.size(); z++){ if(logic().cards_dealer.get(z).idletimer == 0) drawCard(144 + 16*z, 24 + 4*z, logic().cards_dealer.get(z)); }
        }
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
        return "baccarat";
    }

}
