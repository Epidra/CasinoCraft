package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoLightGray;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenMinoLightGray extends ScreenCasino {   // Minesweeper

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoLightGray(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoLightGray logic(){
        return (LogicMinoLightGray) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 14; y++){
                for(int x = 0; x < 26; x++){
                    if(mouseRect(-80 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26); }
                }
            }
        }
        if(logic().turnstate == 2 && mouseButton == 1){
            for(int y = 0; y < 14; y++){
                for(int x = 0; x < 26; x++){
                    if(mouseRect(-80 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26 + 1000); }
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
        if(logic().tableID == 1) {
            drawFont(matrixstack, "POINTS",                24, 24);
            drawFont(matrixstack, "" + logic().scorePoint, 34, 34);
            drawFont(matrixstack, "BOMBS",                205, 24);
            drawFont(matrixstack, "" + logic().bombs,     214, 34);
        } else {
            drawFont(matrixstack, "POINTS",                24-76-16, 24);
            drawFont(matrixstack, "" + logic().scorePoint, 34-76-16, 34);
            drawFont(matrixstack, "BOMBS",                204+76+16, 24);
            drawFont(matrixstack, "" + logic().bombs,     214+76+16, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 14; y++){
            for(int x = 0; x < 26; x++){
                int i = logic().grid[x][y];
                if(i >= 100){ // hidden
                    drawMinoSmall(matrixstack, -96 + 16 + 16*x,  + 16 + 16*y, i >= 200 ? 11 : 0, false);
                } else {
                    if(i == 9) { // Bomb
                        drawMinoSmall(matrixstack, -96 + 16 + 16*x,  + 16 + 16*y, 12, false);
                    } else if(i == 10) { // Bomb (Exploded)
                        drawMinoSmall(matrixstack, -96 + 16 + 16*x,  + 16 + 16*y, 13, false);
                    } else if(i > 0){
                        drawMinoSmall(matrixstack, -96 + 16 + 16*x,  + 16 + 16*y, i+1, true);
                    }
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
        return "mine_sweeper";
    }

}
