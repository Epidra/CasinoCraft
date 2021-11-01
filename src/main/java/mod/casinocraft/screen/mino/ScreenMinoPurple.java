package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoPurple;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoPurple extends ScreenCasino {   // -----

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoPurple(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoPurple logic(){
        return (LogicMinoPurple) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "";
    }



}
