package mod.casinocraft.gui.other;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.dust.Logic2048;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiSlotGame extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSlotGame(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicBase logic(){
        return (LogicBase) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

    }

}
