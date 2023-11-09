package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoGreen;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoGreen extends ScreenCasino {   // Mystic Square

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoGreen(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoGreen logic(){
        return (LogicMinoGreen) menu.logic();
    }

    protected String getGameName() {
        return "mystic_square";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){
                    if(mouseRect(32 + 48*x, 32 + 48*y, 48, 48, mouseX, mouseY)){
                        if(y > 0) if(logic().grid[x    ][y - 1] == -1) action(0);
                        if(y < 3) if(logic().grid[x    ][y + 1] == -1) action(1);
                        if(x > 0) if(logic().grid[x - 1][y    ] == -1) action(2);
                        if(x < 3) if(logic().grid[x + 1][y    ] == -1) action(3);
                    }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){

    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        // RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MYSTICSQUARE);
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                int i = logic().grid[x][y] % 20;
                if(i != -1) {
                    matrix.blit(CasinoKeeper.TEXTURE_MYSTICSQUARE, leftPos + 32 + 48*x, topPos + 32 + 48*y, (i % 4)*48, (i / 4)*48, 48, 48);
                }
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
