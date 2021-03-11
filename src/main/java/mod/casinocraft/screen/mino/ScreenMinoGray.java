package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoGray;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenMinoGray extends ScreenCasino {   // Mino Flip

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoGray(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoGray logic(){
        return (LogicMinoGray) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    if(mouseRect(44 + 32*x, 44 + 32*y, 24, 24, mouseX, mouseY)){ action(x + y*5); }
                }
            }
        }
        if(logic().turnstate == 2 && mouseButton == 1){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    if(mouseRect(44 + 32*x, 44 + 32*y, 24, 24, mouseX, mouseY)){ action(x + y*5 + 100); }
                }
            }
        }
        if(logic().turnstate == 3 && mouseButton == 0){
            if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(-1); }
            if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(-2); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){
        drawFont(matrixstack, "POINTS",                24, 24);
        drawFont(matrixstack, "" + logic().scorePoint, 34, 34);
        if(logic().turnstate == 2){
            for(int y = 0; y < 5; y++){
                drawFont(matrixstack, "" + logic().grid[5][y], 210, 46 + 32*y);
                drawFont(matrixstack, "" + logic().grid[6][y], 210, 58 + 32*y);
            }
            for(int x = 0; x < 5; x++){
                drawFont(matrixstack, "" + logic().grid[x][5], 46 + 32*x, 210);
                drawFont(matrixstack, "" + logic().grid[x][6], 46 + 32*x, 222);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOFLIP);
        this.blit(matrixstack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                int i = logic().grid[x][y];
                if(i >= 100){ // hidden
                    drawMino(matrixstack, 44 + 32*x,  44 + 32*y, i >= 200 ? 8 : 0, 0);
                } else {
                    drawMino(matrixstack, 44 + 32*x,  44 + 32*y, 9, i % 100);
                }
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 3){
            blit(matrixstack, guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            blit(matrixstack, guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "mino_flip";
    }

}
