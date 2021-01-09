package mod.casinocraft.gui.chip;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.chip.ContainerChipBlack;
import mod.casinocraft.container.chip.ContainerChipLightGray;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.chip.LogicChipLightGray;
import mod.casinocraft.logic.other.LogicDummy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiChipLightGray extends GuiCasino {   // 2048

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiChipLightGray(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerChipLightGray(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipLightGray logic(){
        return (LogicChipLightGray) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(!logic().timerActive) {
            if(keyCode == Keyboard.KEY_UP)    { action(0); }
            if(keyCode == Keyboard.KEY_DOWN)  { action(1); }
            if(keyCode == Keyboard.KEY_LEFT)  { action(2); }
            if(keyCode == Keyboard.KEY_RIGHT) { action(3); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2) {
            drawFontCenter("" + logic().scorePoint, 128, 230);
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

        if(logic().turnstate >= 2){
            if(logic().turnstate == 5) GlStateManager.color(0.25F, 0.25F, 0.25F, 1.0F);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_2048);
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){
                    //drawMino(tileCasino.getValue(y * 4 + x), x, y);
                    if(logic().grid[x][y] != 0){
                        int shiftX = logic().grid[x][y] >= 100 ? logic().direction == 4 ? logic().timer : logic().direction == 3 ? -logic().timer : 0 : 0;
                        int shiftY = logic().grid[x][y] >= 100 ? logic().direction == 2 ? logic().timer : logic().direction == 1 ? -logic().timer : 0 : 0;
                        int color = (logic().grid[x][y] % 100) - 1;
                        this.drawTexturedModalRect(guiLeft + 48*x+32 + shiftX, guiTop + 48*y+8 + shiftY, (color % 4)*48, (color / 4)*48, 48, 48);
                    }
                }
            }
            if(logic().turnstate == 5) GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "2048";
    }

}
