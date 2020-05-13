package mod.casinocraft.gui.chip;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.chip.ContainerChipBlack;
import mod.casinocraft.container.chip.ContainerChipBlue;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.chip.LogicChipBlue;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiChipBlue extends GuiCasino {   // Tetris

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiChipBlue(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerChipBlue(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipBlue logic(){
        return (LogicChipBlue) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {
        if(logic().turnstate == 2) {
            if(keyCode == Keyboard.KEY_UP)    { action(0); }
            if(keyCode == Keyboard.KEY_DOWN)  { action(1); }
            if(keyCode == Keyboard.KEY_LEFT)  { action(2); }
            if(keyCode == Keyboard.KEY_RIGHT) { action(3); }
            if(keyCode == Keyboard.KEY_M)     { action(4); }
            if(keyCode == Keyboard.KEY_N)     { action(5); }
            if(keyCode == Keyboard.KEY_RETURN) { action(6); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawFontInvert("" + logic().scorePoint,  216, 16);
            drawFontInvert("" + logic().scoreLives,  216, 36);
            drawFontInvert("" + logic().scoreLevel,  216, 56);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_TETRIS);
        if(logic().turnstate < 2){
            this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
        } else {
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        }

        if(logic().turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADE);
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < 10; x++){
                    if(logic().grid[x][y] != -1) drawDigiSmall(32 + 12*x, 8 + 12*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y));
                }
            }

            drawDigiSmall(32 + 12*logic().tetromino[0].X, 8 + 12*logic().tetromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
            drawDigiSmall(32 + 12*logic().tetromino[1].X, 8 + 12*logic().tetromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
            drawDigiSmall(32 + 12*logic().tetromino[2].X, 8 + 12*logic().tetromino[2].Y, logic().turnstate >= 4 ? 8 : logic().container_now);
            drawDigiSmall(32 + 12*logic().tetromino[3].X, 8 + 12*logic().tetromino[3].Y, logic().turnstate >= 4 ? 8 : logic().container_now);

            drawTetromino(logic().turnstate >= 4 ? 8 : logic().container_next, 172-16,  80);
            drawTetromino(logic().turnstate >= 4 ? 8 : logic().container_hold, 172-16, 164);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    private int tetroColor(int x, int y){
        return logic().inLine(y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
    }

    private void drawTetromino(int mino, int x, int y) {
        if(mino == 0) { drawTetromino(mino, x, y, 24,  0, 24, 16, 24, 32, 24, 48); } // I
        if(mino == 1) { drawTetromino(mino, x, y, 16, 16, 32, 16, 16, 32, 32, 32); } // O
        if(mino == 2) { drawTetromino(mino, x, y, 24, 16, 40, 16, 24, 32,  8, 32); } // S
        if(mino == 3) { drawTetromino(mino, x, y, 24, 16,  8, 16, 24, 32, 40, 32); } // Z
        if(mino == 4) { drawTetromino(mino, x, y, 16,  8, 16, 24, 16, 40, 32, 40); } // L
        if(mino == 5) { drawTetromino(mino, x, y, 32,  8, 32, 24, 32, 40, 16, 40); } // J
        if(mino == 6) { drawTetromino(mino, x, y,  8, 16, 24, 16, 40, 16, 24, 32); } // T
    }

    private void drawTetromino(int mino, int x, int y, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        drawDigi(x + x1, y + y1, mino, 0);
        drawDigi(x + x2, y + y2, mino, 0);
        drawDigi(x + x3, y + y3, mino, 0);
        drawDigi(x + x4, y + y4, mino, 0);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "tetris";
    }

}
