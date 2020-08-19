package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.chip.LogicChipPink;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Ship;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static mod.casinocraft.util.KeyMap.*;

public class ScreenChipPink extends ScreenCasino {   // Sokoban

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipPink(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipPink logic(){
        return (LogicChipPink) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(logic().turnstate == 2){
            if(keyCode == KEY_UP)    { action( 0); }
            if(keyCode == KEY_DOWN)  { action( 1); }
            if(keyCode == KEY_LEFT)  { action( 2); }
            if(keyCode == KEY_RIGHT) { action( 3); }
            if(keyCode == KEY_ENTER) { action(-1); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(MatrixStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawFontCenter(matrixstack, "ENTER          for          RESET", 128, 230);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADEDUMMY);
        if(logic().turnstate < 2){
            this.blit(matrixstack, guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
        } else {
            this.blit(matrixstack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        }

        if(logic().turnstate >= 2) {
            if(logic().turnstate == 5) GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADE);
            for(int x = 0; x < 12; x++){
                for(int y = 0; y < 12; y++){
                    //if(tc.getValue(x + y*16) == 1) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 32, 64, 16, 16);
                    //if(tc.getValue(x + y*16) == 2) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 48, 64, 16, 16);
                    if(logic().grid[x][y] > 0) drawDigi(matrixstack, 32 + x*16, 8 + y*16, 0, 0);
                }
            }
            for(Ship e : logic().crate){ drawShip(matrixstack, e, 6, false, false); }
            drawShip(matrixstack, logic().octanom, 2, true, true);
            for(Ship e : logic().cross){ drawShip(matrixstack, e, 7, false, false); }
            if(logic().turnstate == 5) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "sokoban";
    }

}
