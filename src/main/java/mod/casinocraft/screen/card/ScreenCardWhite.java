package mod.casinocraft.screen.card;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardWhite;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import mod.lucky77.util.Vector2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardWhite extends ScreenCasino {   // Single Poker

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardWhite(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardWhite logic(){
        return (LogicCardWhite) menu.logic();
    }

    protected String getGameName() {
        return "single_poker";
    }

    protected void createGameButtons(){
        buttonSet.addButton(new Vector2( 20, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, 2, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(0));
        buttonSet.addButton(new Vector2( 64, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, 2, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(1));
        buttonSet.addButton(new Vector2(108, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, 2, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(2));
        buttonSet.addButton(new Vector2(152, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, 2, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(3));
        buttonSet.addButton(new Vector2(196, 28), ButtonMap.HOLD_OFF, ButtonMap.HOLD_ON, 2, 0, () -> isActivePlayer() && logic().turnstate == 2, () -> action(4));
        buttonSet.addButton(ButtonMap.POS_MID_MIDDLE,                 ButtonMap.DRAW,          () -> isActivePlayer() && logic().turnstate == 2, () -> action(5));
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        if(logic().turnstate == 2 &&  isActivePlayer()){ drawFontCenter(matrix, "choose cards to keep ...", 128, 192); }
        if(logic().turnstate == 2 && !isActivePlayer()){ drawFontCenter(matrix, "...",                      128, 192); }
        if(logic().turnstate == 3                     ){ drawFontCenter(matrix, "...",                      128, 192); }
        if(logic().turnstate >= 4                     ){ drawFontCenter(matrix, logic().hand,               128, 192); }
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        drawCardBack(matrix,  27, 72, 7); // Card 1
        drawCardBack(matrix,  70, 72, 7); // Card 2
        drawCardBack(matrix, 113, 72, 7); // Card 3
        drawCardBack(matrix, 156, 72, 7); // Card 4
        drawCardBack(matrix, 199, 72, 7); // Card 5
        drawCard(matrix,  27, 72, logic().cards_field[0]); // Card 1
        drawCard(matrix,  70, 72, logic().cards_field[1]); // Card 2
        drawCard(matrix, 113, 72, logic().cards_field[2]); // Card 3
        drawCard(matrix, 156, 72, logic().cards_field[3]); // Card 4
        drawCard(matrix, 199, 72, logic().cards_field[4]); // Card 5
        if(logic().turnstate == 3){
            // RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
            matrix.blit(CasinoKeeper.TEXTURE_BUTTONS, leftPos + 89, topPos + 212, 0, 176, 78, 22);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
