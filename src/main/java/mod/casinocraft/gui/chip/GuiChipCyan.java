package mod.casinocraft.gui.chip;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.chip.ContainerChipBlack;
import mod.casinocraft.container.chip.ContainerChipCyan;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.chip.LogicChipCyan;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiChipCyan extends GuiCasino {   // Columns

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiChipCyan(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerChipCyan(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipCyan logic(){
        return (LogicChipCyan) CONTAINER.logic();
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
            if(keyCode == Keyboard.KEY_N)     { action(4); }
            if(keyCode == Keyboard.KEY_M)     { action(5); }
            if(keyCode == Keyboard.KEY_RETURN) { action(6); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawFontInvert("" + logic().scorePoint, 204, 16);
            drawFontInvert("" + logic().scoreLives, 204, 36);
            drawFontInvert("" + logic().scoreLevel, 204, 56);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_COLUMNS);
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

            drawDigiSymbol(32 + 16*logic().tromino[0].X, 8 + 16*logic().tromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_current[0]);
            drawDigiSymbol(32 + 16*logic().tromino[1].X, 8 + 16*logic().tromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_current[1]);
            drawDigiSymbol(32 + 16*logic().tromino[2].X, 8 + 16*logic().tromino[2].Y, logic().turnstate >= 4 ? 8 : logic().container_current[2]);

            if(logic().turnstate < 4){
                                                   drawTetromino(logic().container_next[0], logic().container_next[1], logic().container_next[2], 168,  92);
                if(logic().container_hold[0] > -1) drawTetromino(logic().container_hold[0], logic().container_hold[1], logic().container_hold[2], 168, 180);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    private int tetroColor(int x, int y){
        return logic().inLine(x, y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
    }

    private void drawTetromino(int mino0, int mino1, int mino2, int x, int y) {
        drawDigiSymbol(x, y          , mino0);
        drawDigiSymbol(x, y + 16, mino1);
        drawDigiSymbol(x, y + 32, mino2);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "columns";
    }

}
