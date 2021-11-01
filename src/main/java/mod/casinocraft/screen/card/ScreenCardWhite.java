package mod.casinocraft.screen.card;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardWhite;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardWhite extends ScreenCasino {   // Single Poker

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardWhite(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardWhite logic(){
        return (LogicCardWhite) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2){
            if(mouseRect(16+4*1+40*0,  24, 46, 26, mouseX, mouseY)){ action(0); } // Hold 1
            if(mouseRect(16+4*2+40*1,  24, 46, 26, mouseX, mouseY)){ action(1); } // Hold 2
            if(mouseRect(16+4*3+40*2,  24, 46, 26, mouseX, mouseY)){ action(2); } // Hold 3
            if(mouseRect(16+4*4+40*3,  24, 46, 26, mouseX, mouseY)){ action(3); } // Hold 4
            if(mouseRect(16+4*5+40*4,  24, 46, 26, mouseX, mouseY)){ action(4); } // Hold 5
            if(mouseRect(         82, 204, 92, 26, mouseX, mouseY)){ action(5); } // Finish
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 4) drawFont(matrixstack, logic().hand, 75, 150);
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawCardBack(matrixstack, 16+11*1+32*0, 72, 7); // Card 1
            drawCardBack(matrixstack, 16+11*2+32*1, 72, 7); // Card 2
            drawCardBack(matrixstack, 16+11*3+32*2, 72, 7); // Card 3
            drawCardBack(matrixstack, 16+11*4+32*3, 72, 7); // Card 4
            drawCardBack(matrixstack, 16+11*5+32*4, 72, 7); // Card 5
            drawCard(matrixstack, 16+11*1+32*0, 72, logic().cards_field[0]); // Card 1
            drawCard(matrixstack, 16+11*2+32*1, 72, logic().cards_field[1]); // Card 2
            drawCard(matrixstack, 16+11*3+32*2, 72, logic().cards_field[2]); // Card 3
            drawCard(matrixstack, 16+11*4+32*3, 72, logic().cards_field[3]); // Card 4
            drawCard(matrixstack, 16+11*5+32*4, 72, logic().cards_field[4]); // Card 5
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            blit(matrixstack, leftPos+82+7, topPos+204+2, 78*2, 22, 78, 22); // Button Finish
            if(logic().hold[0]){blit(matrixstack, leftPos+16+4*1+40*0, topPos+2+24, 78*2, 44, 39, 22);} else {blit(matrixstack, leftPos+16+4*1+40*0, topPos+2+24, 78*2+39, 44, 39, 22);} // Button Hold 1
            if(logic().hold[1]){blit(matrixstack, leftPos+16+4*2+40*1, topPos+2+24, 78*2, 44, 39, 22);} else {blit(matrixstack, leftPos+16+4*2+40*1, topPos+2+24, 78*2+39, 44, 39, 22);} // Button Hold 2
            if(logic().hold[2]){blit(matrixstack, leftPos+16+4*3+40*2, topPos+2+24, 78*2, 44, 39, 22);} else {blit(matrixstack, leftPos+16+4*3+40*2, topPos+2+24, 78*2+39, 44, 39, 22);} // Button Hold 3
            if(logic().hold[3]){blit(matrixstack, leftPos+16+4*4+40*3, topPos+2+24, 78*2, 44, 39, 22);} else {blit(matrixstack, leftPos+16+4*4+40*3, topPos+2+24, 78*2+39, 44, 39, 22);} // Button Hold 4
            if(logic().hold[4]){blit(matrixstack, leftPos+16+4*5+40*4, topPos+2+24, 78*2, 44, 39, 22);} else {blit(matrixstack, leftPos+16+4*5+40*4, topPos+2+24, 78*2+39, 44, 39, 22);} // Button Hold 5
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "single_poker";
    }



}
