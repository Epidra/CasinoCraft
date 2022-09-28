package mod.casinocraft.screen.card;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardRed;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import mod.casinocraft.util.Card;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardRed extends ScreenCasino {   // Rouge et Noir

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardRed(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardRed logic(){
        return (LogicCardRed) menu.logic();
    }

    protected String getGameName() {
        return "rouge_etnoir";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_MID_LEFT,  ButtonMap.ROUGE, () -> isActivePlayer() && logic().turnstate == 2, () -> action(0));
        buttonSet.addButton(ButtonMap.POS_MID_RIGHT, ButtonMap.NOIR,  () -> isActivePlayer() && logic().turnstate == 2, () -> action(1));
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
        if(logic().turnstate == 2 &&  isActivePlayer()) drawFontCenter(matrix, "Choose the color to bet on ...",  128, 192);
        if(logic().turnstate == 2 && !isActivePlayer()) drawFontCenter(matrix, "...",                             128, 192);
        if(logic().turnstate == 3                     ) drawFontCenter(matrix, "...",                             128, 192);
        if(logic().turnstate >= 4                     ) drawFontCenter(matrix, logic().hand,                      128, 192);
        if(logic().turnstate >= 3                     ) drawFont(      matrix, "" + logic().value_rouge,           36,  30);
        if(logic().turnstate >= 3                     ) drawFont(      matrix, "" + logic().value_noir,            36, 126);
    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        drawCardBack(matrix, 32,  40, 1);
        drawCardBack(matrix, 32, 136, 0);
        int i = 0; for(Card c : logic().cards_rouge){ drawCard(matrix, 40 + 16*i,  40, c); i++; }
        i = 0; for(Card c : logic().cards_noir ){ drawCard(matrix, 40 + 16*i, 136, c); i++; }
        if(logic().turnstate == 3){
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
            blit(matrix, leftPos + 89, topPos + 212, 0, 176, 78, 22);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
