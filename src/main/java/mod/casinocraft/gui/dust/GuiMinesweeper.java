package mod.casinocraft.gui.dust;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.dust.LogicMinesweeper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiMinesweeper extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiMinesweeper(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicMinesweeper logic(){
        return (LogicMinesweeper) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
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

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().table == 1) {
            drawString("POINTS",           24, 24);
            drawString("" + logic().scorePoint, 34, 34);
            drawString("BOMBS",            205, 24);
            drawString("" + logic().scoreLevel*logic().table, 214, 34);
        } else {
            drawString("POINTS",           24-76-16, 24);
            drawString("" + logic().scorePoint, 34-76-16, 34);
            drawString("BOMBS",            204+76+16, 24);
            drawString("" + logic().scoreLevel*logic().table, 214+76+16, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        for(int y = 0; y < 14; y++){
            for(int x = 0; x < 26; x++){
                int i = logic().gridI[x][y];
                if(logic().gridB[x][y]){
                    this.blit(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 112, 216, 16, 16);
                } else {
                    if(i == 9) {
                        this.blit(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 192, 216, 16, 16);
                    } else if(i == 10) {
                        this.blit(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 208, 216, 16, 16);
                    } else if(i > 0){
                        GlStateManager.color4f(1.0F-(i/10f), 1.0F, 1.0F, 0.5F);
                        this.blit(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 240, 80-16+16*i, 16, 16);
                        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
            }
        }

        if(logic().turnstate == 3){
            blit(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            blit(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }
    }



    //--------------------CUSTOM--------------------

}
