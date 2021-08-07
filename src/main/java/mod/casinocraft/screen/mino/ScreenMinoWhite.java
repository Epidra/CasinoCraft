package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoWhite;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoWhite extends ScreenCasino {   // Sudoku

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoWhite(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoWhite logic(){
        return (LogicMinoWhite) menu.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 9; x++) {
                    if(mouseRect(20 + 24*x, 20 + 24*y, 24, 24, mouseX, mouseY)){
                        if(logic().grid[x][y] < 10) action(y*9 + x);
                    }
                }
            }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_SUDOKU);
        this.blit(matrixstack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);

        drawMino(matrixstack, 20 + 24*logic().selector.X, 20 + 24*logic().selector.Y, 8, 0);

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(logic().grid[x][y] > 0) {
                    if(logic().grid[x][y] > 10) RenderSystem.setShaderColor(0.5f, 0.5f, 0.5f, 1.0F);
                    drawMinoSmall(matrixstack, 20+4 + 24*x, 20+4 + 24*y, logic().grid[x][y] % 10, true);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "sudoku";
    }

}
