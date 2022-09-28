package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoLime;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoLime extends ScreenCasino {   // Simon

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoLime(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoLime logic(){
        return (LogicMinoLime) menu.logic();
    }

    protected String getGameName() {
        return "simon";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2){
            if(logic().color_player.size() < logic().color_simon.size()){
                for(int y = 0; y < 2; y++){
                    for(int x = 0; x < 2; x++){
                        if(mouseRect(64 + 64*x, 64 + 64*y, 64, 64, mouseX, mouseY)) {
                            action(y*2 + x);
                        }
                    }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
        drawValueLeft(matrix, "POINTS", logic().scorePoint);
    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_SIMON);
        this.blit(matrix, leftPos +  64, topPos +  64,   0, logic().alpha[0] > 1 ? 64 : 128, 64, 64);
        this.blit(matrix, leftPos + 128, topPos +  64,  64, logic().alpha[1] > 1 ? 64 : 128, 64, 64);
        this.blit(matrix, leftPos +  64, topPos + 128, 128, logic().alpha[2] > 1 ? 64 : 128, 64, 64);
        this.blit(matrix, leftPos + 128, topPos + 128, 192, logic().alpha[3] > 1 ? 64 : 128, 64, 64);
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        for(int i = 0; i < logic().color_player.size(); i++){
            drawMinoSmall(matrix,  16 + (i%2)*16, 24 + (i/2)*16, logic().color_player.get(i) + 1, false);
        }
        for(int i = 0; i < (logic().turnstate ==3 ? logic().alpha_pos + 1 : logic().color_simon.size()); i++){
            drawMinoSmall(matrix, 208 + (i%2)*16, 24 + (i/2)*16, logic().turnstate <= 3 ? 14 : (logic().color_simon.get(i) + 1), logic().turnstate <= 3);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
