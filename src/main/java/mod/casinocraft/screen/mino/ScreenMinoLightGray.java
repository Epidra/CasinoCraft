package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoLightGray;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoLightGray extends ScreenCasino {   // Minesweeper

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoLightGray(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoLightGray logic(){
        return (LogicMinoLightGray) menu.logic();
    }

    protected String getGameName() {
        return "mine_sweeper";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_MID_LEFT,  ButtonMap.CONTINUE, () -> isActivePlayer() && logic().turnstate == 3, () -> action(-1));
        buttonSet.addButton(ButtonMap.POS_MID_RIGHT, ButtonMap.GIVEUP,   () -> isActivePlayer() && logic().turnstate == 3, () -> action(-2));
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2){
            for(int y = 0; y < 14; y++){
                for(int x = 0; x < 26; x++){
                    if(mouseButton == 0 && mouseRect(-80 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26       ); }
                    if(mouseButton == 1 && mouseRect(-80 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26 + 1000); }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
        drawValueLeft(matrix, "POINTS", logic().scorePoint);
        drawValueRight(matrix, "BOMBS", logic().bombs);
    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 14; y++){
            for(int x = 0; x < 26; x++){
                int i = logic().grid[x][y];
                if(i >= 100){ // hidden
                    drawMinoSmall(matrix, -80 + 16*x,  + 16 + 16*y, i >= 200 ? 11 : 0, false);
                } else {
                    if(i ==  9){ drawMinoSmall(matrix, -80 + 16*x, 16 + 16*y, 12, false); } // Bomb
                    else if(i == 10){ drawMinoSmall(matrix, -80 + 16*x, 16 + 16*y, 13, false); } // Bomb (Exploded)
                    else if(i >   0){ drawMinoSmall(matrix, -80 + 16*x, 16 + 16*y, i+1, true); } // Number
                }
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
