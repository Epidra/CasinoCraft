package mod.casinocraft.screen.card;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardPink;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardPink extends ScreenCasino {   // -----

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardPink(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardPink logic(){
        return (LogicCardPink) menu.logic();
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

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){

    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
