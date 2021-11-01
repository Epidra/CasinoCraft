package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.chip.LogicChipBlue;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenChipBlue extends ScreenCasino {   // Tetris

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipBlue(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipBlue logic(){
        return (LogicChipBlue) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawFontInvert(matrixstack, "" + logic().scorePoint,  216, 16);
            drawFontInvert(matrixstack, "" + logic().scoreLives,  216, 36);
            drawFontInvert(matrixstack, "" + logic().scoreLevel,  216, 56);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_TETRIS);
        this.blit(matrixstack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background

        if(logic().turnstate >= 2){
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_ARCADE);
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < 10; x++){
                    if(logic().grid[x][y] != -1) drawDigiSmall(matrixstack, 32 + 12*x, 8 + 12*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y));
                }
            }

            drawDigiSmall(matrixstack, 32 + 12*logic().tetromino[0].X, 8 + 12*logic().tetromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
            drawDigiSmall(matrixstack, 32 + 12*logic().tetromino[1].X, 8 + 12*logic().tetromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
            drawDigiSmall(matrixstack, 32 + 12*logic().tetromino[2].X, 8 + 12*logic().tetromino[2].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
            drawDigiSmall(matrixstack, 32 + 12*logic().tetromino[3].X, 8 + 12*logic().tetromino[3].Y, logic().turnstate >= 4 ? 8 : logic().container_now);

            drawTetromino(matrixstack, logic().turnstate >= 4 ? 8 : logic().container_next, 172-16,  80);
            drawTetromino(matrixstack, logic().turnstate >= 4 ? 8 : logic().container_hold, 172-16, 164);
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    private int tetroColor(int x, int y){
        return logic().inLine(y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
    }

    private void drawTetromino(PoseStack matrixstack, int mino, int x, int y) {
        if(mino == 0) { drawTetromino(matrixstack, mino, x, y, 24,  0, 24, 16, 24, 32, 24, 48); } // I
        if(mino == 1) { drawTetromino(matrixstack, mino, x, y, 16, 16, 32, 16, 16, 32, 32, 32); } // O
        if(mino == 2) { drawTetromino(matrixstack, mino, x, y, 24, 16, 40, 16, 24, 32,  8, 32); } // S
        if(mino == 3) { drawTetromino(matrixstack, mino, x, y, 24, 16,  8, 16, 24, 32, 40, 32); } // Z
        if(mino == 4) { drawTetromino(matrixstack, mino, x, y, 16,  8, 16, 24, 16, 40, 32, 40); } // L
        if(mino == 5) { drawTetromino(matrixstack, mino, x, y, 32,  8, 32, 24, 32, 40, 16, 40); } // J
        if(mino == 6) { drawTetromino(matrixstack, mino, x, y,  8, 16, 24, 16, 40, 16, 24, 32); } // T
    }

    private void drawTetromino(PoseStack matrixstack, int mino, int x, int y, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        drawDigi(matrixstack, x + x1, y + y1, mino, 0);
        drawDigi(matrixstack, x + x2, y + y2, mino, 0);
        drawDigi(matrixstack, x + x3, y + y3, mino, 0);
        drawDigi(matrixstack, x + x4, y + y4, mino, 0);
    }





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "tetris";
    }



}
