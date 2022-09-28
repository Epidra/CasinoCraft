package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoCyan;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenMinoCyan extends ScreenCasino {   // Halma

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoCyan(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoCyan logic(){
        return (LogicMinoCyan) menu.logic();
    }

    protected String getGameName() {
        return "halma";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-76 + x*24, 20 + y*24, 24, 24, mouseX, mouseY)){ action(y*17 + x); }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY){
        drawValueLeft(matrix, "POINTS", logic().scorePoint);
    }

    protected void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_MINOS);
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(logic().grid[x][y] == 0) drawMino(matrix, -76 + 24*x, 20 + 24*y, 9, 0);
                if(logic().grid[x][y] == 1) drawMino(matrix, -76 + 24*x, 20 + 24*y, 0, 0);
            }
        }
        drawMino(matrix, -76 + 24*logic().selector.X, 20 + 24*logic().selector.Y, 3, 0);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
