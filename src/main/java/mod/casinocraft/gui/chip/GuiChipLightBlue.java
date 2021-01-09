package mod.casinocraft.gui.chip;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.chip.ContainerChipBlack;
import mod.casinocraft.container.chip.ContainerChipLightBlue;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.chip.LogicChipLightBlue;
import mod.casinocraft.logic.other.LogicDummy;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiChipLightBlue extends GuiCasino {   // Mean Minos

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiChipLightBlue(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerChipLightBlue(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipLightBlue logic(){
        return (LogicChipLightBlue) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            if(keyCode == Keyboard.KEY_UP)    { action(0); }
            if(keyCode == Keyboard.KEY_DOWN)  { action(1); }
            if(keyCode == Keyboard.KEY_LEFT)  { action(2); }
            if(keyCode == Keyboard.KEY_RIGHT) { action(3); }
            if(keyCode == Keyboard.KEY_N)     { action(4); }
            if(keyCode == Keyboard.KEY_M)     { action(5); }
            if(keyCode == Keyboard.KEY_RETURN) { action(6); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2) {
            drawFontInvert("" + logic().scorePoint, 204, 16);
            drawFontInvert("" + logic().scoreLives, 204, 36);
            drawFontInvert("" + logic().scoreLevel, 204, 56);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MEANMINOS);
        if(logic().turnstate < 2){
            this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
        } else {
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        }

        if(logic().turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADE);
            for(int y = 0; y < 15; y++){
                for(int x = 0; x < 6; x++){
                    if(logic().grid[x][y] != -1) drawDigiSymbol(32 + 16*x, 8 + 16*y, logic().turnstate >= 4 ? tetroColor(x, y) + 8 : tetroColor(x, y));
                }
            }

            drawDigiSymbol(32 + 16*logic().domino[0].X, 8 + 16*logic().domino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_current[0]);
            drawDigiSymbol(32 + 16*logic().domino[1].X, 8 + 16*logic().domino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_current[1]);

            if(logic().turnstate < 4){
                                                   drawTetromino(logic().container_next[0], logic().container_next[1], 168, 100);
                if(logic().container_hold[0] > -1) drawTetromino(logic().container_hold[0], logic().container_hold[1], 168, 188);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    private int tetroColor(int x, int y){
        return logic().inLine(x, y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
    }

    private void drawTetromino(int mino0, int mino1, int x, int y) {
        drawDigiSymbol(x, y          , mino1);
        drawDigiSymbol(x, y + 16, mino0);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "meanminos";
    }

}
