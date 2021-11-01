package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoGray;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoGray extends ScreenCasino {   // Mino Flip

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoGray(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoGray logic(){
        return (LogicMinoGray) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    if(mouseRect(44 + 32*x, 44 + 32*y, 24, 24, mouseX, mouseY)){ action(x + y*5); }
                }
            }
        }
        if(logic().turnstate == 2 && mouseButton == 1){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    if(mouseRect(44 + 32*x, 44 + 32*y, 24, 24, mouseX, mouseY)){ action(x + y*5 + 100); }
                }
            }
        }
        if(logic().turnstate == 3 && mouseButton == 0){
            if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(-1); }
            if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(-2); }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        drawFont(matrixstack, "POINTS",                24, 24);
        drawFont(matrixstack, "" + logic().scorePoint, 34, 34);
        if(logic().turnstate == 2){
            for(int y = 0; y < 5; y++){
                drawFont(matrixstack, "" + logic().grid[5][y], 210, 46 + 32*y);
                drawFont(matrixstack, "" + logic().grid[6][y], 210, 58 + 32*y);
            }
            for(int x = 0; x < 5; x++){
                drawFont(matrixstack, "" + logic().grid[x][5], 46 + 32*x, 210);
                drawFont(matrixstack, "" + logic().grid[x][6], 46 + 32*x, 222);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOFLIP);
        this.blit(matrixstack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background

        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                int i = logic().grid[x][y];
                if(i >= 100){ // hidden
                    drawMino(matrixstack, 44 + 32*x,  44 + 32*y, i >= 200 ? 8 : 0, 0);
                } else {
                    drawMino(matrixstack, 44 + 32*x,  44 + 32*y, 9, i % 100);
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
        return "mino_flip";
    }

}
