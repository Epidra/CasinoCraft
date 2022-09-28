package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.chip.LogicChipPurple;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenChipPurple extends ScreenCasino {   // -----

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipPurple(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipPurple logic(){
        return (LogicChipPurple) menu.logic();
    }

    protected String getGameName() {
        return "";
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

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
