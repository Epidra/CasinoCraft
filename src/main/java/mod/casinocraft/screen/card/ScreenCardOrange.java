package mod.casinocraft.screen.card;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.card.LogicCardOrange;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenCardOrange extends ScreenCasino {   // Baccarat

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardOrange(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicCardOrange logic(){
        return (LogicCardOrange) menu.logic();
    }

    protected String getGameName() {
        return tableID == 1 ? "bacca_rat" : "baccarat";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_MID_LEFT,  ButtonMap.ANOTHER, () -> isActivePlayer() && logic().turnstate == 2, () -> action(0));
        buttonSet.addButton(ButtonMap.POS_MID_RIGHT, ButtonMap.WAIT,    () -> isActivePlayer() && logic().turnstate == 2, () -> action(1));
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY){
        drawFont(matrix, "PLAYER:  " + logic().value_player, 24, 24);
        drawFont(matrix, "DEALER:  " + logic().value_dealer, 24, 40);
        if(logic().status    == 1                     ){ drawFontCenter(matrix, "Natural Draw!",          128, 176); }
        if(logic().turnstate == 2 &&  isActivePlayer()){ drawFontCenter(matrix, "Want another card ...?", 128, 192); }
        if(logic().turnstate == 2 && !isActivePlayer()){ drawFontCenter(matrix, "...",                    128, 192); }
        if(logic().turnstate == 3                     ){ drawFontCenter(matrix, "...",                    128, 192); }
        if(logic().turnstate >= 4                     ){ drawFontCenter(matrix, logic().hand,             128, 192); }
    }

    protected void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            for(int z = 0; z < logic().cards_player.size(); z++){ if(logic().cards_player.get(z).idletimer == 0) drawCard(matrix,  24 + 16*z, 80 + 4*z, logic().cards_player.get(z)); }
            for(int z = 0; z < logic().cards_dealer.size(); z++){ if(logic().cards_dealer.get(z).idletimer == 0) drawCard(matrix, 144 + 16*z, 24 + 4*z, logic().cards_dealer.get(z)); }
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
