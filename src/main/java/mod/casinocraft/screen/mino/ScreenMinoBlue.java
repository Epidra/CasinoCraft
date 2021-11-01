package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoBlue;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoBlue extends ScreenCasino {   // Memory

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoBlue(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoBlue logic(){
        return (LogicMinoBlue) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ action(y*17 + x); }
                }
            }
            if(logic().turnstate == 3){
                if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(-1); }
                if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(-2); }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().tableID == 1) {
            drawFont(matrixstack, "POINTS",                 24, 24);
            drawFont(matrixstack, "" + logic().scorePoint,  34, 34);
            drawFont(matrixstack, "LIVES",                 204, 24);
            drawFont(matrixstack, "" + logic().scoreLives, 214, 34);
        } else {
            drawFont(matrixstack, "POINTS",                 24-76-16, 24);
            drawFont(matrixstack, "" + logic().scorePoint,  34-76-16, 34);
            drawFont(matrixstack, "LIVES",                 204+76+16, 24);
            drawFont(matrixstack, "" + logic().scoreLives, 214+76+16, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
            for(int y = 0; y < 9; y++){
                for(int x = 0; x < 17; x++){
                    if(logic().grid[x][y] != -1){
                        if(logic().positionA.matches(x, y)){
                            drawMino(matrixstack, -76 + 24*x, 20 + 24*y, logic().grid[x][y]+1, 0);
                        } else
                        if(logic().positionB.matches(x, y)){
                            drawMino(matrixstack, -76 + 24*x, 20 + 24*y, logic().grid[x][y]+1, 0);
                        } else {
                            drawMino(matrixstack, -76 + 24*x, 20 + 24*y, 0, 0);
                        }
                    }
                }
            }

        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 3){
            blit(matrixstack, leftPos+24+7,  topPos+204+2,  0, 0, 78, 22); // Button Hit
            blit(matrixstack, leftPos+140+7, topPos+204+2, 78, 0, 78, 22); // Button Stand
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "memory";
    }



}
