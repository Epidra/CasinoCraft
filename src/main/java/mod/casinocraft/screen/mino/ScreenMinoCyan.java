package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoCyan;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoCyan extends ScreenCasino {   // Halma

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoCyan(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoCyan logic(){
        return (LogicMinoCyan) menu.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ action(y*17 + x); }
                }
            }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().tableID == 1) {
            drawFont(matrixstack, "POINTS",                24, 24);
            drawFont(matrixstack, "" + logic().scorePoint, 34, 34);
        } else {
            drawFont(matrixstack, "POINTS",                24-76-16  , 24);
            drawFont(matrixstack, "" + logic().scorePoint, 34-76-16  , 34);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(logic().grid[x][y] == 0) drawMino(matrixstack, -4-24*3 + 24*x, -4+24 + 24*y, 9, 0);
                if(logic().grid[x][y] == 1) drawMino(matrixstack, -4-24*3 + 24*x, -4+24 + 24*y, 0, 0);
            }
        }
        drawMino(matrixstack, -4-24*3 + 24*logic().selector.X, -4+24 + 24*logic().selector.Y, 3, 0);
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "halma";
    }

}
