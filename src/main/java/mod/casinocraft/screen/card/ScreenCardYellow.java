package mod.casinocraft.screen.card;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardYellow;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardYellow extends ScreenCasino {   // Acey Deucey

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardYellow(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardYellow logic(){
        return (LogicCardYellow) menu.logic();
    }

    protected String getGameName() {
        return "acey_deucey";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_MID_LEFT,  ButtonMap.ANOTHER, () -> isActivePlayer() && logic().turnstate == 2 && playerToken >= bet, () -> { action(0); collectBet(); });
        buttonSet.addButton(ButtonMap.POS_MID_RIGHT, ButtonMap.WAIT,    () -> isActivePlayer() && logic().turnstate == 2,                       () -> { action(1);               });
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        if(logic().spread    > -1                     ){ drawFontCenter(matrix, "Spread: " + logic().spread, 96, 176); }
        if(logic().turnstate == 2 &&  isActivePlayer()){ drawFontCenter(matrix, "Add another Bet ...?",     128, 192); }
        if(logic().turnstate == 2 && !isActivePlayer()){ drawFontCenter(matrix, "...",                      128, 192); }
        if(logic().turnstate == 3                     ){ drawFontCenter(matrix, "...",                      128, 192); }
        if(logic().turnstate >= 4                     ){ drawFontCenter(matrix, logic().hand,               128, 192); }
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        drawCard(matrix, 64,  72, logic().cards[0]);
        if(logic().cards[2] != null){ drawCard(matrix, 112, 72, logic().cards[2]); }
        drawCard(matrix, 160, 72, logic().cards[1]);
        if(logic().turnstate == 3){
            // RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
            matrix.blit(CasinoKeeper.TEXTURE_BUTTONS, leftPos + 89, topPos + 212, 0, 176, 78, 22);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
