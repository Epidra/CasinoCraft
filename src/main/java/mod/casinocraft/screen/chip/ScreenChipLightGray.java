package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.chip.LogicChipLightGray;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenChipLightGray extends ScreenCasino {   // 2048

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipLightGray(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipLightGray logic(){
        return (LogicChipLightGray) menu.logic();
    }

    protected String getGameName() {
        return "2048";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY){
        drawFontCenter(matrix, "" + logic().scorePoint, 128, 230);
    }

    protected void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        drawBackground(matrix, CasinoKeeper.TEXTURE_2048_GROUND, CasinoKeeper.TEXTURE_2048);
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                if(logic().grid[x][y] != 0){
                    int shiftX = logic().grid[x][y] >= 100 ? logic().direction == 4 ? logic().timer : logic().direction == 3 ? -logic().timer : 0 : 0;
                    int shiftY = logic().grid[x][y] >= 100 ? logic().direction == 2 ? logic().timer : logic().direction == 1 ? -logic().timer : 0 : 0;
                    int color = (logic().grid[x][y] % 100) - 1;
                    this.blit(matrix, leftPos + 48*x+32 + shiftX, topPos + 48*y+8 + shiftY, (color % 4)*48, (color / 4)*48, 48, 48);
                }
            }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
