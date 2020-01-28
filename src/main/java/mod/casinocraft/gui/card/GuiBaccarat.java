package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicBaccarat;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiBaccarat extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiBaccarat(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicBaccarat logic(){
        return (LogicBaccarat) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(0); } else
        if(logic().turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
    }

    protected void keyTyped2(char typedChar, int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        drawString("PLAYER:  " + logic().value_player, 24, 24);
        drawString("DEALER:  " + logic().value_dealer, 24, 40);

        if(logic().status == 1) drawString("Natural Draw!",    80, 170);
        if(logic().status == 2) drawString("continue drawing", 80, 170);
        if(logic().turnstate  >= 4) drawString(logic().hand,            80, 190);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            for(int z = 0; z < logic().cards_player.size(); z++){ if(logic().cards_player.get(z).idletimer == 0) drawCard( 24 + 16*z, 80 + 4*z, logic().cards_player.get(z)); }
            for(int z = 0; z < logic().cards_dealer.size(); z++){ if(logic().cards_dealer.get(z).idletimer == 0) drawCard(144 + 16*z, 24 + 4*z, logic().cards_dealer.get(z)); }
        }

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(logic().turnstate == 2){
            blit(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            blit(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }
    }



    //--------------------CUSTOM--------------------

}
