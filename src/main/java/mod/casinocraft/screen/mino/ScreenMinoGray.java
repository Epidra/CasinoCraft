package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoGray;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoGray extends ScreenCasino {   // Mino Flip

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoGray(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoGray logic(){
        return (LogicMinoGray) menu.logic();
    }

    protected String getGameName() {
        return "mino_flip";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_MID_LEFT,  ButtonMap.CONTINUE, () -> isActivePlayer() && logic().turnstate == 3, () -> action(-1));
        buttonSet.addButton(ButtonMap.POS_MID_RIGHT, ButtonMap.GIVEUP,   () -> isActivePlayer() && logic().turnstate == 3, () -> action(-2));
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    if(mouseButton == 0 && mouseRect(44 + 32*x, 44 + 32*y, 24, 24, mouseX, mouseY)){ action(x + y*5      ); }
                    if(mouseButton == 1 && mouseRect(44 + 32*x, 44 + 32*y, 24, 24, mouseX, mouseY)){ action(x + y*5 + 100); }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        drawValueLeft(matrix, "POINTS", logic().scorePoint);
        if(logic().turnstate == 2){
            for(int z = 0; z < 5; z++){
                drawFont(matrix, "" + logic().grid[5][z],       210, 46 + 32*z); // Right Values
                drawFont(matrix, "" + logic().grid[6][z],       210, 58 + 32*z); // Right Values
                drawFont(matrix, "" + logic().grid[z][5], 46 + 32*z,       210); // Bottom Values
                drawFont(matrix, "" + logic().grid[z][6], 46 + 32*z,       222); // Bottom Values
            }
        }
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        drawBackground(matrix, CasinoKeeper.TEXTURE_MINOFLIP, CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                int i = logic().grid[x][y];
                if(i >= 100){ // hidden
                    drawMino(matrix, 44 + 32*x,  44 + 32*y, i >= 200 ? 1 : 0, 0);
                } else {
                    if(i == 0){
                        drawMino(matrix, 44 + 32*x,  44 + 32*y, 9, 1);
                    } else {
                        drawMinoSmall(matrix, 48 + 32*x,  48 + 32*y, i % 100 + 1, true);
                    }
                }
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
