package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoLightBlue;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoLightBlue extends ScreenCasino {   // Ishido

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoLightBlue(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoLightBlue logic(){
        return (LogicMinoLightBlue) menu.logic();
    }

    protected String getGameName() {
        return "ishido";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2){
            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 12; x++){
                    if(mouseRect(-16 + 24*x, 44 + 24*y, 24, 24, mouseX, mouseY)){ action(x + y*12); }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
        drawValueLeft(matrix, "POINTS", logic().scorePoint);
        drawValueRight(matrix, "LEFT", logic().reserve.size());
    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        if(logic().reserve.size() > 0){
            drawMino(matrix, 116, 18, logic().reserve.get(0).number + 1, logic().reserve.get(0).suit + 1);
        }
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 12; x++){
                if(logic().grid[x][y] == -2){
                    drawMinoSmall(matrix, -12 + x*24, 48 + y*24, 0, true);
                    drawMino(matrix, -16 + x*24, 44 + y*24, 9, 0);
                } else if(logic().grid[x][y] == -1){
                    drawMino(matrix, -16 + x*24, 44 + y*24, 9, 0);
                } else if(logic().grid[x][y] >= 0){
                    drawMino(matrix, -16 + x*24, 44 + y*24, (logic().grid[x][y] % 6) + 1, (logic().grid[x][y] / 6) + 1);
                }
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
