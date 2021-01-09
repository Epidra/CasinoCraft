package mod.casinocraft.gui.chip;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.chip.ContainerChipBlack;
import mod.casinocraft.container.chip.ContainerChipOrange;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.chip.LogicChipOrange;
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

public class GuiChipOrange extends GuiCasino {   // Snake

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiChipOrange(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerChipOrange(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipOrange logic(){
        return (LogicChipOrange) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            if(keyCode == Keyboard.KEY_UP)    { action(1); }
            if(keyCode == Keyboard.KEY_DOWN)  { action(2); }
            if(keyCode == Keyboard.KEY_LEFT)  { action(3); }
            if(keyCode == Keyboard.KEY_RIGHT) { action(4); }
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

        if(logic().turnstate >= 2) {
            if(logic().turnstate == 5) GlStateManager.color(0.25F, 0.25F, 0.25F, 1.0F);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADE);
            drawShip(logic().point, 5);
            drawShip(logic().octanom_head, 0, true, true);
            for(Ship tail : logic().octanom_tail){
                drawShip(tail, 4, false, false);
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
        return "snake";
    }

}
