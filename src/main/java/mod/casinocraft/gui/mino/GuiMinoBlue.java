package mod.casinocraft.gui.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.mino.ContainerMinoBlue;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.mino.LogicMinoBlue;
import mod.casinocraft.logic.other.LogicDummy;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiMinoBlue extends GuiCasino {   // Memory

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiMinoBlue(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerMinoBlue(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoBlue logic(){
        return (LogicMinoBlue) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ action(y*17 + x); }
                }
            }
            if(logic().turnstate == 3 && mouseButton == 0){
                if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(-1); }
                if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(-2); }
            }
        }
    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().tableID == 1) {
            drawFont("POINTS",           24, 24);
            drawFont("" + logic().scorePoint, 34, 34);
            drawFont("LIVES",            204, 24);
            drawFont("" + logic().scoreLives, 214, 34);
        } else {
            drawFont("POINTS",           24-76-16, 24);
            drawFont("" + logic().scorePoint, 34-76-16, 34);
            drawFont("LIVES",            204+76+16, 24);
            drawFont("" + logic().scoreLives, 214+76+16, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
            for(int y = 0; y < 9; y++){
                for(int x = 0; x < 17; x++){
                    if(logic().grid[x][y] != -1){
                        if(logic().positionA.matches(x, y)){
                            drawMino(-76 + 24*x, 20 + 24*y, logic().grid[x][y]+1, 0);
                        } else
                        if(logic().positionB.matches(x, y)){
                            drawMino(-76 + 24*x, 20 + 24*y, logic().grid[x][y]+1, 0);
                        } else {
                            drawMino(-76 + 24*x, 20 + 24*y, 0, 0);
                        }
                    }
                }
            }

        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 3){
            drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "memory";
    }

}
