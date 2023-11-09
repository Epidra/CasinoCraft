package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoBlue;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoBlue extends ScreenCasino {   // Memory

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoBlue(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoBlue logic(){
        return (LogicMinoBlue) menu.logic();
    }

    protected String getGameName() {
        return "memory";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_MID_LEFT,  ButtonMap.CONTINUE, () -> isActivePlayer() && logic().turnstate == 3, () -> action(-1));
        buttonSet.addButton(ButtonMap.POS_MID_RIGHT, ButtonMap.GIVEUP,   () -> isActivePlayer() && logic().turnstate == 3, () -> action(-2));
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if (logic().turnstate == 2){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-76 + x*24, 20 + y*24, 24, 24, mouseX, mouseY)){ action(y*17 + x); }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        drawValueLeft(matrix, "POINTS", logic().scorePoint);
        drawValueRight(matrix, "LIVES", logic().scoreLives);
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        // RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 9; y++){
            for(int x = 0; x < 17; x++){
                if(logic().grid[x][y] != -1){
                    if(logic().positionA.matches(x, y)){
                        drawMino(matrix, -76 + 24*x, 20 + 24*y, logic().grid[x][y]+1, logic().grid[x][y]+1);
                    } else
                    if(logic().positionB.matches(x, y)){
                        drawMino(matrix, -76 + 24*x, 20 + 24*y, logic().grid[x][y]+1, logic().grid[x][y]+1);
                    } else {
                        drawMino(matrix, -76 + 24*x, 20 + 24*y, 0, 0);
                    }
                }
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
