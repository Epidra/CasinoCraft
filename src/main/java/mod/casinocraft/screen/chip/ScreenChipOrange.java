package mod.casinocraft.screen.chip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.chip.LogicChipOrange;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Ship;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenChipOrange extends ScreenCasino {   // Snake

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenChipOrange(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicChipOrange logic(){
        return (LogicChipOrange) menu.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawFontCenter(matrixstack, "" + logic().scorePoint, 128, 230);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_ARCADEDUMMY);
        this.blit(matrixstack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background

        if(logic().turnstate >= 2) {
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_ARCADE);
            drawShip(matrixstack, logic().point, 5);
            drawShip(matrixstack, logic().octanom_head, 0, -1, true);
            for(Ship tail : logic().octanom_tail){
                drawShip(matrixstack, tail, 4, 0, false);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "snake";
    }

}
