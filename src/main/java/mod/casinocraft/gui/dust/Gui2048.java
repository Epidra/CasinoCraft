package mod.casinocraft.gui.dust;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.dust.Logic2048;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class Gui2048 extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public Gui2048(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public Logic2048 logic(){
        return (Logic2048) CONTAINER.logic();
    }

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

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            this.font.drawString("" + logic().scorePoint, 16+16, 16+200+16, 9999999);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADEDUMMY);
        this.blit(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background

        if(logic().turnstate == 1) {
            if(intro < 256 - 80) {
                intro = 0;
                logic().turnstate = 2;
            }
        }

        if(logic().turnstate >= 2){
            if(logic().turnstate == 5) GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_2048);
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){
                    //drawMino(tileCasino.getValue(y * 4 + x), x, y);
                    if(logic().gridI[x][y] != 0){
                        int shiftX = logic().gridB[x][y] ? logic().direction == 4 ? logic().timer : logic().direction == 3 ? -logic().timer : 0 : 0;
                        int shiftY = logic().gridB[x][y] ? logic().direction == 2 ? logic().timer : logic().direction == 1 ? -logic().timer : 0 : 0;
                        this.blit(guiLeft + 48*x+32 + shiftX, guiTop + 48*y+16 + shiftY, ((logic().gridI[x][y]-1)%4)*48, ((logic().gridI[x][y]-1)/4)*48, 48, 48);
                    }
                }
            }
            if(logic().turnstate == 5) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

}
