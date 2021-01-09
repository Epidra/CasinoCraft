package mod.casinocraft.gui.chip;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.chip.ContainerChipBlack;
import mod.casinocraft.container.chip.ContainerChipPink;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.chip.LogicChipPink;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.util.Ship;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiChipPink extends GuiCasino {   // Sokoban

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiChipPink(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerChipPink(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipPink logic(){
        return (LogicChipPink) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            if(keyCode == Keyboard.KEY_UP)    { action( 0); }
            if(keyCode == Keyboard.KEY_DOWN)  { action( 1); }
            if(keyCode == Keyboard.KEY_LEFT)  { action( 2); }
            if(keyCode == Keyboard.KEY_RIGHT) { action( 3); }
            if(keyCode == Keyboard.KEY_RETURN) { action(-1); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2) {
            drawFontCenter("ENTER          for          RESET", 128, 230);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADEDUMMY);
        if(logic().turnstate < 2){
            this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
        } else {
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        }

        if(logic().turnstate >= 2) {
            if(logic().turnstate == 5) GlStateManager.color(0.25F, 0.25F, 0.25F, 1.0F);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADE);
            for(int x = 0; x < 12; x++){
                for(int y = 0; y < 12; y++){
                    //if(tc.getValue(x + y*16) == 1) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 32, 64, 16, 16);
                    //if(tc.getValue(x + y*16) == 2) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 48, 64, 16, 16);
                    if(logic().grid[x][y] > 0) drawDigi(32 + x*16, 8 + y*16, 0, 0);
                }
            }
            for(Ship e : logic().crate){ drawShip(e, 6, false, false); }
            drawShip(logic().octanom, 2, true, true);
            for(Ship e : logic().cross){ drawShip(e, 7, false, false); }
            if(logic().turnstate == 5) GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "sokoban";
    }

}
