package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.chip.LogicChipWhite;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenChipWhite extends ScreenCasino {   // -----

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipWhite(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipWhite logic(){
        return (LogicChipWhite) menu.logic();
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

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){

    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
