package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.chip.LogicChipLightGray;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static mod.casinocraft.util.KeyMap.*;

public class ScreenChipLightGray extends ScreenCasino {   // 2048

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipLightGray(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipLightGray logic(){
        return (LogicChipLightGray) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(!logic().timerActive) {
            if(keyCode == KEY_UP)    { action(0); }
            if(keyCode == KEY_DOWN)  { action(1); }
            if(keyCode == KEY_LEFT)  { action(2); }
            if(keyCode == KEY_RIGHT) { action(3); }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(MatrixStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawFontCenter(matrixstack, "" + logic().scorePoint, 128, 230);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADEDUMMY);
        if(logic().turnstate < 2){
            this.blit(matrixstack, guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
        } else {
            this.blit(matrixstack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        }

        if(logic().turnstate >= 2){
            if(logic().turnstate == 5) GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_2048);
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){
                    //drawMino(tileCasino.getValue(y * 4 + x), x, y);
                    if(logic().grid[x][y] != 0){
                        int shiftX = logic().grid[x][y] >= 100 ? logic().direction == 4 ? logic().timer : logic().direction == 3 ? -logic().timer : 0 : 0;
                        int shiftY = logic().grid[x][y] >= 100 ? logic().direction == 2 ? logic().timer : logic().direction == 1 ? -logic().timer : 0 : 0;
                        int color = (logic().grid[x][y] % 100) - 1;
                        this.blit(matrixstack, guiLeft + 48*x+32 + shiftX, guiTop + 48*y+8 + shiftY, (color % 4)*48, (color / 4)*48, 48, 48);
                    }
                }
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
        return "2048";
    }

}
