package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.chip.LogicChipCyan;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenChipCyan extends ScreenCasino {   // Columns

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipCyan(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipCyan logic(){
        return (LogicChipCyan) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawFontInvert(matrixstack, "" + logic().scorePoint, 204, 16);
            drawFontInvert(matrixstack, "" + logic().scoreLives, 204, 36);
            drawFontInvert(matrixstack, "" + logic().scoreLevel, 204, 56);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_COLUMNS);
        this.blit(matrixstack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background

        if(logic().turnstate >= 2){
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_ARCADE);
            for(int y = 0; y < 15; y++){
                for(int x = 0; x < 6; x++){
                    if(logic().grid[x][y] != -1) drawDigiSymbol(matrixstack, 32 + 16*x, 8 + 16*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y));
                }
            }

            drawDigiSymbol(matrixstack, 32 + 16*logic().tromino[0].X, 8 + 16*logic().tromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_current[0]);
            drawDigiSymbol(matrixstack, 32 + 16*logic().tromino[1].X, 8 + 16*logic().tromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_current[1]);
            drawDigiSymbol(matrixstack, 32 + 16*logic().tromino[2].X, 8 + 16*logic().tromino[2].Y, logic().turnstate >= 4 ? 8 : logic().container_current[2]);

            if((logic().turnstate >= 4 ?  8 : logic().container_next[0]) > -1) drawTetromino(matrixstack, logic().turnstate >= 4 ?  8 : logic().container_next[0], logic().turnstate >= 4 ?  8 : logic().container_next[1], logic().turnstate >= 4 ?  8 : logic().container_next[2], 168,  92);
            if((logic().turnstate >= 4 ? -1 : logic().container_hold[0]) > -1) drawTetromino(matrixstack, logic().turnstate >= 4 ? -1 : logic().container_hold[0], logic().turnstate >= 4 ? -1 : logic().container_hold[1], logic().turnstate >= 4 ? -1 : logic().container_hold[2], 168, 180);
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    private int tetroColor(int x, int y){
        return logic().inLine(x, y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
    }

    private void drawTetromino(MatrixStack matrixstack, int mino0, int mino1, int mino2, int x, int y) {
        drawDigiSymbol(matrixstack, x, y     , logic().turnstate >= 4 ? 8 : mino0);
        drawDigiSymbol(matrixstack, x, y + 16, logic().turnstate >= 4 ? 8 : mino1);
        drawDigiSymbol(matrixstack, x, y + 32, logic().turnstate >= 4 ? 8 : mino2);
    }





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "columns";
    }



}
