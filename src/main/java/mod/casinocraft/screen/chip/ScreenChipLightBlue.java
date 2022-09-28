package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.chip.LogicChipLightBlue;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenChipLightBlue extends ScreenCasino {   // Puyo Puyo

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipLightBlue(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipLightBlue logic(){
        return (LogicChipLightBlue) menu.logic();
    }

    protected String getGameName() {
        return "puyopuyo";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY){
        drawFontInvert(matrix, "" + logic().scorePoint, 204, 16);
        drawFontInvert(matrix, "" + logic().scoreLives, 204, 36);
        drawFontInvert(matrix, "" + logic().scoreLevel, 204, 56);
    }

    protected void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        drawBackground(matrix, CasinoKeeper.TEXTURE_MEANMINOS, CasinoKeeper.TEXTURE_ARCADE);
        for(int y = 0; y < 15; y++){
            for(int x = 0; x < 6; x++){
                if(logic().grid[x][y] != -1) drawDigiSymbol(matrix, 32 + 16*x, 8 + 16*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y));
            }
        }

        drawDigiSymbol(matrix, 32 + 16*logic().domino[0].X, 8 + 16*logic().domino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_current[0]);
        drawDigiSymbol(matrix, 32 + 16*logic().domino[1].X, 8 + 16*logic().domino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_current[1]);

        if((logic().turnstate >= 4 ?  8 : logic().container_next[0]) > -1) drawTetromino(matrix, logic().turnstate >= 4 ?  8 : logic().container_next[0], logic().turnstate >= 4 ?  8 : logic().container_next[1], 168, 100);
        if((logic().turnstate >= 4 ? -1 : logic().container_hold[0]) > -1) drawTetromino(matrix, logic().turnstate >= 4 ? -1 : logic().container_hold[0], logic().turnstate >= 4 ? -1 : logic().container_hold[1], 168, 188);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private int tetroColor(int x, int y){
        return logic().inLine(x, y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
    }

    private void drawTetromino(MatrixStack matrix, int mino0, int mino1, int x, int y) {
        drawDigiSymbol(matrix, x, y     , logic().turnstate >= 4 ? 8 : mino1);
        drawDigiSymbol(matrix, x, y + 16, logic().turnstate >= 4 ? 8 : mino0);
    }



}
