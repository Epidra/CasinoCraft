package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoBrown;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenMinoBrown extends ScreenCasino {   // -----

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoBrown(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoBrown logic(){
        return (LogicMinoBrown) menu.logic();
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
