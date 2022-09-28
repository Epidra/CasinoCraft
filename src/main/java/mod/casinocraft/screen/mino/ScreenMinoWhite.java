package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoWhite;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoWhite extends ScreenCasino {   // Sudoku

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoWhite(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoWhite logic(){
        return (LogicMinoWhite) menu.logic();
    }

    protected String getGameName() {
        return "sudoku";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(mouseRect(20 + 24*x, 20 + 24*y, 24, 24, mouseX, mouseY)){
                    if(logic().grid[x][y] < 10) action(y*9 + x);
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){

    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        drawBackground(matrix, CasinoKeeper.TEXTURE_SUDOKU, CasinoKeeper.TEXTURE_MINOS);
        drawMino(matrix, 20 + 24*logic().selector.X, 20 + 24*logic().selector.Y, 0, 9);

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(logic().grid[x][y] > 0) {
                    if(logic().grid[x][y] > 10) RenderSystem.setShaderColor(0.5f, 0.5f, 0.5f, 1.0F);
                    drawMinoSmall(matrix, 20+4 + 24*x, 20+4 + 24*y, (logic().grid[x][y] % 10) + 1, true);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
