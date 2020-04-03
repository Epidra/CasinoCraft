package mod.casinocraft.gui.dust;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.dust.LogicSnake;
import mod.casinocraft.util.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiSnake extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSnake(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicSnake logic(){
        return (LogicSnake) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_UP)    { action(1); }
        if(keyCode == KEY_DOWN)  { action(2); }
        if(keyCode == KEY_LEFT)  { action(3); }
        if(keyCode == KEY_RIGHT) { action(4); }
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

        if(logic().turnstate >= 2) {
            if(logic().turnstate == 5) GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            this.blit(guiLeft+16 + logic().point.X*16, guiTop + logic().point.Y*16, 80, 112, 16, 16);
            this.blit(guiLeft+16 + logic().octanom_head.Get_Pos().X, guiTop + logic().octanom_head.Get_Pos().Y, 0, logic().octanom_head.Get_LookDirection()*16+112, 16, 16);
            for(Entity tail : logic().octanom_tail){
                this.blit(guiLeft+16 + tail.Get_Pos().X, guiTop + tail.Get_Pos().Y, 16, 112, 16, 16);
            }
            if(logic().turnstate == 5) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }



    //--------------------CUSTOM--------------------

}
