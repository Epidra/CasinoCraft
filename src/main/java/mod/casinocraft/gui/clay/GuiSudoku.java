package mod.casinocraft.gui.clay;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.clay.LogicSudoku;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiSudoku extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSudoku(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicSudoku logic(){
        return (LogicSudoku) CONTAINER.logic();
    }

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

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SUDOKU);
        this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);

        this.blit(guiLeft+20 + 24*logic().selector.X, guiTop+20 + 24*logic().selector.Y, 0, 232, 24, 24);

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(logic().grid[x][y] > 0) {
                    if(logic().grid[x][y] > 10) GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
                    this.blit(guiLeft+20+4 + 24*x, guiTop+20+4 + 24*y, 240, 64 + 16*(logic().grid[x][y] % 10), 16, 16);
                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }



    //--------------------CUSTOM--------------------

}
