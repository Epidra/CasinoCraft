package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.chip.LogicChipBlack;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenChipBlack extends ScreenCasino {   // -----

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipBlack(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicChipBlack logic(){
        return (LogicChipBlack) menu.logic();
    }

    protected String getGameName() {
        return "testkit";
    }

    protected void createGameButtons(){

    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){

    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        drawBackground(matrix, CasinoKeeper.TEXTURE_ARCADEDUMMY, CasinoKeeper.TEXTURE_ARCADE);
        drawDigiSymbol(matrix, logic().position, 112);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
