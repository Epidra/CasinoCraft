package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.chip.LogicChipOrange;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Ship;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenChipOrange extends ScreenCasino {   // Snake

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipOrange(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipOrange logic(){
        return (LogicChipOrange) menu.logic();
    }

    protected String getGameName() {
        return "snake";
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
        drawBackground(matrix, CasinoKeeper.TEXTURE_ARCADEDUMMY, CasinoKeeper.TEXTURE_ARCADE);
        drawShip(matrix, logic().point, 5);
        drawShip(matrix, logic().octanom_head, 0, -1, true);
        for(Ship tail : logic().octanom_tail){
            drawShip(matrix, tail, 4, 0, false);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
