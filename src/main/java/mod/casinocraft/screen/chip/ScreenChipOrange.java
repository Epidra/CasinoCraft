package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.chip.LogicChipOrange;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Ship;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static mod.casinocraft.util.KeyMap.*;

public class ScreenChipOrange extends ScreenCasino {   // Snake

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipOrange(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipOrange logic(){
        return (LogicChipOrange) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            if(keyCode == KEY_UP)    { action(1); }
            if(keyCode == KEY_DOWN)  { action(2); }
            if(keyCode == KEY_LEFT)  { action(3); }
            if(keyCode == KEY_RIGHT) { action(4); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(MatrixStack matrixstack, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2) {
            drawFontCenter(matrixstack, "" + logic().scorePoint, 128, 230);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADEDUMMY);
        if(logic().turnstate < 2){
            this.blit(matrixstack, guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
        } else {
            this.blit(matrixstack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        }

        if(logic().turnstate >= 2) {
            if(logic().turnstate == 5) GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADE);
            drawShip(matrixstack, logic().point, 5);
            drawShip(matrixstack, logic().octanom_head, 0, true, true);
            for(Ship tail : logic().octanom_tail){
                drawShip(matrixstack, tail, 4, false, false);
            }
            if(logic().turnstate == 5) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "snake";
    }

}
