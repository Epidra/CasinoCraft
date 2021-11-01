package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoLightBlue;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoLightBlue extends ScreenCasino {   // Ishido

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoLightBlue(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoLightBlue logic(){
        return (LogicMinoLightBlue) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 12; x++){
                    if(mouseRect(-16 + 24*x, 32+12 + 24*y, 24, 24, mouseX, mouseY)){ action(x + y*12); }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().tableID == 1) {
            drawFont(matrixstack, "POINTS",                    24, 24);
            drawFont(matrixstack, "" + logic().scorePoint,     34, 34);
            drawFont(matrixstack, "LEFT",                     205, 24);
            drawFont(matrixstack, "" + logic().reserve.size(),214, 34);
        } else {
            drawFont(matrixstack, "POINTS",                     24-76-16, 24);
            drawFont(matrixstack, "" + logic().scorePoint,      34-76-16, 34);
            drawFont(matrixstack, "LEFT",                      204+76+16, 24);
            drawFont(matrixstack, "" + logic().reserve.size(), 214+76+16, 34);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        if(logic().reserve.size() > 0){
            drawMino(matrixstack, 128-12, 16+2, logic().reserve.get(0).number + 1, logic().reserve.get(0).suit);
        }
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 12; x++){
                if(logic().grid[x][y] == -2){
                    //drawMino(matrixstack, -16 + x*24, 32+12 + y*24, logic().grid[x][y] % 6, logic().grid[x][y] / 6);
                } else if(logic().grid[x][y] == -1){
                    drawMino(matrixstack, -16 + x*24, 32+12 + y*24, 0, 6);
                } else {
                    drawMino(matrixstack, -16 + x*24, 32+12 + y*24, (logic().grid[x][y] % 6) + 1, logic().grid[x][y] / 6);
                }
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "ishido";
    }



}
