package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.chip.LogicChipBlue;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenChipBlue extends ScreenCasino {   // Tetris

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipBlue(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipBlue logic(){
        return (LogicChipBlue) menu.logic();
    }

    protected String getGameName() {
        return "tetris";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        drawFontInvert(matrix, "" + logic().scorePoint,  216, 16);
        drawFontInvert(matrix, "" + logic().scoreLives,  216, 36);
        drawFontInvert(matrix, "" + logic().scoreLevel,  216, 56);
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        drawBackground(matrix, CasinoKeeper.TEXTURE_TETRIS, CasinoKeeper.TEXTURE_ARCADE);
        for(int y = 0; y < 20; y++){
            for(int x = 0; x < 10; x++){
                if(logic().grid[x][y] != -1) drawDigiSmall(matrix, 32 + 12*x, 8 + 12*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y));
            }
        }

        drawDigiSmall(matrix, 32 + 12*logic().tetromino[0].X, 8 + 12*logic().tetromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
        drawDigiSmall(matrix, 32 + 12*logic().tetromino[1].X, 8 + 12*logic().tetromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
        drawDigiSmall(matrix, 32 + 12*logic().tetromino[2].X, 8 + 12*logic().tetromino[2].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
        drawDigiSmall(matrix, 32 + 12*logic().tetromino[3].X, 8 + 12*logic().tetromino[3].Y, logic().turnstate >= 4 ? 8 : logic().container_now);

        drawTetromino(matrix, logic().turnstate >= 4 ? 8 : logic().container_next, 156,  80);
        drawTetromino(matrix, logic().turnstate >= 4 ? 8 : logic().container_hold, 156, 164);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private int tetroColor(int x, int y){
        return logic().inLine(y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
    }

    private void drawTetromino(GuiGraphics matrix, int mino, int x, int y) {
        if(mino == 0) { drawTetromino(matrix, mino, x, y, 24,  0, 24, 16, 24, 32, 24, 48); } // I
        if(mino == 1) { drawTetromino(matrix, mino, x, y, 16, 16, 32, 16, 16, 32, 32, 32); } // O
        if(mino == 2) { drawTetromino(matrix, mino, x, y, 24, 16, 40, 16, 24, 32,  8, 32); } // S
        if(mino == 3) { drawTetromino(matrix, mino, x, y, 24, 16,  8, 16, 24, 32, 40, 32); } // Z
        if(mino == 4) { drawTetromino(matrix, mino, x, y, 16,  8, 16, 24, 16, 40, 32, 40); } // L
        if(mino == 5) { drawTetromino(matrix, mino, x, y, 32,  8, 32, 24, 32, 40, 16, 40); } // J
        if(mino == 6) { drawTetromino(matrix, mino, x, y,  8, 16, 24, 16, 40, 16, 24, 32); } // T
    }

    private void drawTetromino(GuiGraphics matrix, int mino, int x, int y, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        drawDigi(matrix, x + x1, y + y1, mino, 0);
        drawDigi(matrix, x + x2, y + y2, mino, 0);
        drawDigi(matrix, x + x3, y + y3, mino, 0);
        drawDigi(matrix, x + x4, y + y4, mino, 0);
    }



}
