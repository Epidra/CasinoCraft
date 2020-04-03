package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicBlackJack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiBlackJack extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiBlackJack(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicBlackJack logic(){
        return (LogicBlackJack) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(0); }
        if(logic().turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
        if(playerToken >= bet || !CONTAINER.hasToken())
            if(logic().turnstate == 2 && mouseRect( 24, 164, 92, 26, mouseX, mouseY)){ CollectBet(); action(2); }
        if(playerToken >= bet)
            if(logic().turnstate == 2 && mouseRect(140, 164, 92, 26, mouseX, mouseY)){ CollectBet(); action(3); }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

        if(logic().split == 0){
            drawString("PLAYER:  "   + logic().value_player1,  24, 24);
            if(logic().turnstate >= 4) drawString(logic().hand, 80, 190);
        } else {
            drawString("PLAYER L:  " + logic().value_player1,  24, 24);
            drawString("PLAYER R:  " + logic().value_player2,  24, 40);
            if(logic().turnstate >= 4) drawString(logic().hand, 18, 190);
        }
        if(logic().turnstate >= 3) drawString("DEALER:  " + logic().value_dealer, 24, 56);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        if(logic().turnstate >= 2){
            for(int z = 0; z < logic().cards_player1.size(); z++){ if(logic().cards_player1.get(z).idletimer == 0) drawCard( 24 + 16*z, 100 + 4*z, logic().cards_player1.get(z)); if(logic().split == 1) drawCardBack( 24 + 16*z, 100 + 4*z, 10); }
            for(int z = 0; z < logic().cards_player2.size(); z++){ if(logic().cards_player2.get(z).idletimer == 0) drawCard(144 + 16*z, 100 + 4*z, logic().cards_player2.get(z)); if(logic().split == 2) drawCardBack(144 + 16*z, 100 + 4*z, 10); }
            for(int z = 0; z < logic().cards_dealer.size();  z++){ if(logic().cards_dealer.get(z).idletimer == 0)  drawCard(144 + 16*z,  24 + 4*z, logic().cards_dealer.get(z)); }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(logic().turnstate == 2){
            if(playerToken == -1) ValidateBet();
            blit(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            blit(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
            if((logic().cards_player1.get(0).number >= 9 && logic().cards_player1.get(1).number >= 9) || (logic().cards_player1.get(0).number == logic().cards_player1.get(1).number)){
                if(playerToken >= bet || !CONTAINER.hasToken()){
                    blit(guiLeft+24+7, guiTop+204-40+2, 78*2, 0, 78, 22); // Button Split
                }
            }
            if(playerToken >= bet){
                blit(guiLeft+140+7, guiTop+204-40+2, 0, 44, 78, 22); // Button DoubleDown
            }

        }
    }



    //--------------------CUSTOM--------------------

}
