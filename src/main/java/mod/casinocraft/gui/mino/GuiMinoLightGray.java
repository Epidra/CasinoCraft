package mod.casinocraft.gui.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.mino.ContainerMinoLightGray;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.mino.LogicMinoLightGray;
import mod.casinocraft.logic.other.LogicDummy;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiMinoLightGray extends GuiCasino {   // Minesweeper

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiMinoLightGray(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerMinoLightGray(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoLightGray logic(){
        return (LogicMinoLightGray) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 14; y++){
                for(int x = 0; x < 26; x++){
                    if(mouseRect(16-96 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26); }
                }
            }
        }
        if(logic().turnstate == 3 && mouseButton == 0){
            if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(-1); }
            if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(-2); }
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
            drawFont("BOMBS",            205, 24);
            drawFont("" + logic().bombs, 214, 34);
        } else {
            drawFont("POINTS",           24-76-16, 24);
            drawFont("" + logic().scorePoint, 34-76-16, 34);
            drawFont("BOMBS",            204+76+16, 24);
            drawFont("" + logic().bombs, 214+76+16, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 14; y++){
            for(int x = 0; x < 26; x++){
                int i = logic().grid[x][y];
                if(i >= 20){ // hidden
                    drawMinoSmall(-96 + 16 + 16*x,  + 16 + 16*y, 0, false);
                } else {
                    if(i == 9) { // Bomb
                        drawMinoSmall(-96 + 16 + 16*x,  + 16 + 16*y, 12, false);
                    } else if(i == 10) { // Bomb (Exploded)
                        drawMinoSmall(-96 + 16 + 16*x,  + 16 + 16*y, 13, false);
                    } else if(i > 0){
                        drawMinoSmall(-96 + 16 + 16*x,  + 16 + 16*y, i+1, true);
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
        return "minesweeper";
    }

}
