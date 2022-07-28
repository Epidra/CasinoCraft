package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoLightGray;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoLightGray extends ScreenCasino {   // Minesweeper

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoLightGray(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoLightGray logic(){
        return (LogicMinoLightGray) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 14; y++){
                for(int x = 0; x < 26; x++){
                    if(mouseRect(-80 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26); }
                }
            }
        }
        if(logic().turnstate == 2 && mouseButton == 1){
            for(int y = 0; y < 14; y++){
                for(int x = 0; x < 26; x++){
                    if(mouseRect(-80 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26 + 1000); }
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
        if(logic().tableID == 1) {
            drawFont(matrixstack, "POINTS",                24, 24);
            drawFont(matrixstack, "" + logic().scorePoint, 34, 34);
            drawFont(matrixstack, "BOMBS",                205, 24);
            drawFont(matrixstack, "" + logic().bombs,     214, 34);
        } else {
            drawFont(matrixstack, "POINTS",                24-76-16, 24);
            drawFont(matrixstack, "" + logic().scorePoint, 34-76-16, 34);
            drawFont(matrixstack, "BOMBS",                204+76+16, 24);
            drawFont(matrixstack, "" + logic().bombs,     214+76+16, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 14; y++){
            for(int x = 0; x < 26; x++){
                int i = logic().grid[x][y];
                if(i >= 100){ // hidden
                    drawMinoSmall(matrixstack, -96 + 16 + 16*x,  + 16 + 16*y, i >= 200 ? 11 : 0, false);
                } else {
                    if(i == 9) { // Bomb
                        drawMinoSmall(matrixstack, -96 + 16 + 16*x,  + 16 + 16*y, 12, false);
                    } else if(i == 10) { // Bomb (Exploded)
                        drawMinoSmall(matrixstack, -96 + 16 + 16*x,  + 16 + 16*y, 13, false);
                    } else if(i > 0){
                        drawMinoSmall(matrixstack, -96 + 16 + 16*x,  + 16 + 16*y, i+1, true);
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
        return "mine_sweeper";
    }



}
