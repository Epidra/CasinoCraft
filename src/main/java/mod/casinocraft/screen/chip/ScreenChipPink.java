package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.chip.LogicChipPink;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Ship;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenChipPink extends ScreenCasino {   // Sokoban

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipPink(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipPink logic(){
        return (LogicChipPink) menu.logic();
    }

    protected String getGameName() {
        return "sokoban";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY){

    }

    protected void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate == 2){
            drawBackground(matrix, CasinoKeeper.TEXTURE_SOKOBAN, CasinoKeeper.TEXTURE_FONT_ARCADE);
            for(int x = 0; x < 4; x++){
                for(int y = 0; y < 5; y++){
                    int number = y * 4 + x + 1;
                    drawNumber(matrix, leftPos+40 + 48 * x, topPos+14 + 34 * y, (number / 10), (number % 10), hasUnlocked(number), logic().mapID == number - 1);
                }
            }
        }
        if(logic().turnstate >= 3){
            drawBackground(matrix, CasinoKeeper.TEXTURE_ARCADEDUMMY, CasinoKeeper.TEXTURE_ARCADE);
            for(int x = 0; x < 12; x++){
                for(int y = 0; y < 15; y++){
                    if(logic().grid[x][y] > 0) drawDigi(matrix, 32 + x*16, 8 + y*16, 0, 0);
                }
            }
            for(Ship e : logic().cross){ drawShip(matrix, e, 4, 2, false); }
            for(Ship e : logic().crate){ drawShip(matrix, e, 4, 1, false); }
            drawShip(matrix, logic().octanom, 2, -1, true);

        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private boolean hasUnlocked(int index){
        for(int i = 0; i < 20; i++){
            if(logic().scoreHigh[i] == index){
                return true;
            }
        } return false;
    }

    private void drawNumber(MatrixStack matrix, int x, int y, int left, int right, boolean colored, boolean highlighted){
        int vOffset = 160 + (colored ? 48 : 0) + (highlighted ? 24 : 0);
        blit(matrix, x,    y, 16 * left,  vOffset, 16, 24);
        blit(matrix, x+16, y, 16 * right, vOffset, 16, 24);
    }



}
