package mod.casinocraft.gui.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.mino.ContainerMinoWhite;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.mino.LogicMinoWhite;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiMinoWhite extends GuiCasino {   // Sudoku

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiMinoWhite(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerMinoWhite(playerInv, furnaceInv, pos, world), playerInv);
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

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {
        if(keyCode == Keyboard.KEY_1) { action(1); }
        if(keyCode == Keyboard.KEY_2) { action(2); }
        if(keyCode == Keyboard.KEY_3) { action(3); }
        if(keyCode == Keyboard.KEY_4) { action(4); }
        if(keyCode == Keyboard.KEY_5) { action(5); }
        if(keyCode == Keyboard.KEY_6) { action(6); }
        if(keyCode == Keyboard.KEY_7) { action(7); }
        if(keyCode == Keyboard.KEY_8) { action(8); }
        if(keyCode == Keyboard.KEY_9) { action(9); }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SUDOKU);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);

        drawMino(20 + 24*logic().selector.X, 20 + 24*logic().selector.Y, 8, 0);

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(logic().grid[x][y] > 0) {
                    if(logic().grid[x][y] > 10) GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0F);
                    drawMinoSmall(20+4 + 24*x, 20+4 + 24*y, logic().grid[x][y] % 10, true);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "sudoku";
    }

}
