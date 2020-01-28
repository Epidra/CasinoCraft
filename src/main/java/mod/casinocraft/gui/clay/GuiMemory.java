package mod.casinocraft.gui.clay;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.clay.LogicMemory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiMemory extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiMemory(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicMemory logic(){
        return (LogicMemory) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ action(y*17 + x); }
                }
            }
            if(logic().turnstate == 3 && mouseButton == 0){
                if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(-1); }
                if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(-2); }
            }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().table == 1) {
            drawString("POINTS",           24, 24);
            drawString("" + logic().scorePoint, 34, 34);
            drawString("LIVES",            204, 24);
            drawString("" + logic().scoreLives, 214, 34);
        } else {
            drawString("POINTS",           24-76-16, 24);
            drawString("" + logic().scorePoint, 34-76-16, 34);
            drawString("LIVES",            204+76+16, 24);
            drawString("" + logic().scoreLives, 214+76+16, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 9; y++){
                for(int x = 0; x < 17; x++){
                    if(logic().grid[x][y] != -1){
                        if(logic().positionA.matches(x, y)){
                            this.blit(guiLeft-76 + 24*x, guiTop+20 + 24*y, logic().grid[x][y]*24+24, 232, 24, 24);
                        } else
                        if(logic().positionB.matches(x, y)){
                            this.blit(guiLeft-76 + 24*x, guiTop+20 + 24*y, logic().grid[x][y]*24+24, 232, 24, 24);
                        } else {
                            this.blit(guiLeft-76 + 24*x, guiTop+20 + 24*y, 0, 232, 24, 24);
                        }
                    }
                }
            }
            if(logic().turnstate == 3){
                blit(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
                blit(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
            }
        }
    }



    //--------------------CUSTOM--------------------

}
