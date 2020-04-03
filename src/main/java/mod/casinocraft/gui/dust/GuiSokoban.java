package mod.casinocraft.gui.dust;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.dust.LogicSokoban;
import mod.casinocraft.util.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiSokoban extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSokoban(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicSokoban logic(){
        return (LogicSokoban) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_UP)    { action(0); }
        if(keyCode == KEY_DOWN)  { action(1); }
        if(keyCode == KEY_LEFT)  { action(2); }
        if(keyCode == KEY_RIGHT) { action(3); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

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
            for(int x = 1; x < 15; x++){
                for(int y = 0; y < 12; y++){
                    //if(tc.getValue(x + y*16) == 1) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 32, 64, 16, 16);
                    //if(tc.getValue(x + y*16) == 2) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 48, 64, 16, 16);
                    if(logic().grid[x][y] > 0) this.blit(guiLeft + x*16, guiTop + y*16,  16*7, 216-24, 16, 16);
                }
            }
            for(Entity e : logic().crate){ this.blit(guiLeft + e.Get_Pos().X, guiTop + e.Get_Pos().Y,  16*14, 216-24, 16, 16); }
            this.blit(guiLeft + logic().octanom.Get_Pos().X, guiTop + logic().octanom.Get_Pos().Y, 0, logic().octanom.Get_LookDirection()*16+112, 16, 16);
            for(Entity e : logic().cross){ this.blit(guiLeft + e.Get_Pos().X, guiTop + e.Get_Pos().Y, 16*10, 216-24, 16, 16); }
            if(logic().turnstate == 5) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }



    //--------------------CUSTOM--------------------

}
