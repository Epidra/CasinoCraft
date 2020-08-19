package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoWhite;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static mod.casinocraft.util.KeyMap.*;

public class ScreenMinoWhite extends ScreenCasino {   // Sudoku

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoWhite(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoWhite logic(){
        return (LogicMinoWhite) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 9; x++) {
                    if(mouseRect(20 + 24*x, 20 + 24*y, 24, 24, mouseX, mouseY)){
                        if(logic().grid[x][y] < 10) action(100 + y*9 + x);
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_1) { action(1); }
        if(keyCode == KEY_2) { action(2); }
        if(keyCode == KEY_3) { action(3); }
        if(keyCode == KEY_4) { action(4); }
        if(keyCode == KEY_5) { action(5); }
        if(keyCode == KEY_6) { action(6); }
        if(keyCode == KEY_7) { action(7); }
        if(keyCode == KEY_8) { action(8); }
        if(keyCode == KEY_9) { action(9); }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(MatrixStack matrixstack, int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SUDOKU);
        this.blit(matrixstack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);

        drawMino(matrixstack, 20 + 24*logic().selector.X, 20 + 24*logic().selector.Y, 8, 0);

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(logic().grid[x][y] > 0) {
                    if(logic().grid[x][y] > 10) GlStateManager.color4f(0.5f, 0.5f, 0.5f, 1.0F);
                    drawMinoSmall(matrixstack, 20+4 + 24*x, 20+4 + 24*y, logic().grid[x][y] % 10, true);
                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "sudoku";
    }

}
