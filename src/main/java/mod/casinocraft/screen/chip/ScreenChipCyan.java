package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.chip.LogicChipCyan;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenChipCyan extends ScreenCasino {   // Columns

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipCyan(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipCyan logic(){
        return (LogicChipCyan) menu.logic();
    }

    protected String getGameName() {
        return "columns";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
        drawFontInvert(matrix, "" + logic().scorePoint, 204, 16);
        drawFontInvert(matrix, "" + logic().scoreLives, 204, 36);
        drawFontInvert(matrix, "" + logic().scoreLevel, 204, 56);
    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        drawBackground(matrix, CasinoKeeper.TEXTURE_COLUMNS, CasinoKeeper.TEXTURE_ARCADE);
        for(int y = 0; y < 15; y++){
            for(int x = 0; x < 6; x++){
                if(logic().grid[x][y] != -1) drawDigiSymbol(matrix, 32 + 16*x, 8 + 16*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y));
            }
        }

        drawDigiSymbol(matrix, 32 + 16*logic().tromino[0].X, 8 + 16*logic().tromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_current[0]);
        drawDigiSymbol(matrix, 32 + 16*logic().tromino[1].X, 8 + 16*logic().tromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_current[1]);
        drawDigiSymbol(matrix, 32 + 16*logic().tromino[2].X, 8 + 16*logic().tromino[2].Y, logic().turnstate >= 4 ? 8 : logic().container_current[2]);

        if((logic().turnstate >= 4 ?  8 : logic().container_next[0]) > -1) drawTetromino(matrix, logic().turnstate >= 4 ?  8 : logic().container_next[0], logic().turnstate >= 4 ?  8 : logic().container_next[1], logic().turnstate >= 4 ?  8 : logic().container_next[2], 168,  92);
        if((logic().turnstate >= 4 ? -1 : logic().container_hold[0]) > -1) drawTetromino(matrix, logic().turnstate >= 4 ? -1 : logic().container_hold[0], logic().turnstate >= 4 ? -1 : logic().container_hold[1], logic().turnstate >= 4 ? -1 : logic().container_hold[2], 168, 180);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private int tetroColor(int x, int y){
        return logic().inLine(x, y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
    }

    private void drawTetromino(PoseStack matrix, int mino0, int mino1, int mino2, int x, int y) {
        drawDigiSymbol(matrix, x, y     , logic().turnstate >= 4 ? 8 : mino0);
        drawDigiSymbol(matrix, x, y + 16, logic().turnstate >= 4 ? 8 : mino1);
        drawDigiSymbol(matrix, x, y + 32, logic().turnstate >= 4 ? 8 : mino2);
    }



}
