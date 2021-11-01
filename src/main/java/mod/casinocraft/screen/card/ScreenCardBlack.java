package mod.casinocraft.screen.card;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardBlack;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardBlack extends ScreenCasino {   // Black Jack

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardBlack(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardBlack logic(){
        return (LogicCardBlack) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(0); }
        if(logic().turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
        if(playerToken >= bet || !menu.hasToken())
            if(logic().turnstate == 2 && mouseRect( 24, 164, 92, 26, mouseX, mouseY)){ collectBet(); action(2); }
        if(playerToken >= bet)
            if(logic().turnstate == 2 && mouseRect(140, 164, 92, 26, mouseX, mouseY)){ collectBet(); action(3); }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){
        if(logic().split == 0){
            drawFont(matrixstack, "PLAYER:  "   + logic().value_player1,  24, 24);
            if(logic().turnstate >= 4) drawFont(matrixstack, logic().hand, 80, 190);
        } else {
            drawFont(matrixstack, "PLAYER L:  " + logic().value_player1,  24, 24);
            drawFont(matrixstack, "PLAYER R:  " + logic().value_player2,  24, 40);
            if(logic().turnstate >= 4) drawFont(matrixstack, logic().hand, 18, 190);
        }
        if(logic().turnstate >= 3) drawFont(matrixstack, "DEALER:  " + logic().value_dealer, 24, 56);
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            for(int z = 0; z < logic().cards_player1.size(); z++){ if(logic().cards_player1.get(z).idletimer == 0) drawCard(matrixstack,  24 + 16*z, 100 + 4*z, logic().cards_player1.get(z)); if(logic().split == 1) drawCardBack(matrixstack,  24 + 16*z, 100 + 4*z, 10); }
            for(int z = 0; z < logic().cards_player2.size(); z++){ if(logic().cards_player2.get(z).idletimer == 0) drawCard(matrixstack, 144 + 16*z, 100 + 4*z, logic().cards_player2.get(z)); if(logic().split == 2) drawCardBack(matrixstack, 144 + 16*z, 100 + 4*z, 10); }
            for(int z = 0; z < logic().cards_dealer.size();  z++){ if(logic().cards_dealer.get(z).idletimer == 0)  drawCard(matrixstack, 144 + 16*z,  24 + 4*z, logic().cards_dealer.get(z)); }
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            if(playerToken == -1) validateBet();
            blit(matrixstack, leftPos+24+7,  topPos+204+2,  0, 0, 78, 22); // Button Hit
            blit(matrixstack, leftPos+140+7, topPos+204+2, 78, 0, 78, 22); // Button Stand
            if((logic().cards_player1.get(0).number >= 9 && logic().cards_player1.get(1).number >= 9) || (logic().cards_player1.get(0).number == logic().cards_player1.get(1).number)){
                if(playerToken >= bet || !menu.hasToken()){
                    blit(matrixstack, leftPos+24+7, topPos+204-40+2, 78*2, 0, 78, 22); // Button Split
                }
            }
            if(playerToken >= bet){
                blit(matrixstack, leftPos+140+7, topPos+204-40+2, 0, 44, 78, 22); // Button DoubleDown
            }

        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "black_jack";
    }



}
