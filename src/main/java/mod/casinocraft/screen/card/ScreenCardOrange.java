package mod.casinocraft.screen.card;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.card.LogicCardOrange;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenCardOrange extends ScreenCasino {   // Baccarat

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenCardOrange(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardOrange logic(){
        return (LogicCardOrange) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(0); } else
        if(logic().turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        drawFont(matrixstack, "PLAYER:  " + logic().value_player, 24, 24);
        drawFont(matrixstack, "DEALER:  " + logic().value_dealer, 24, 40);

        if(logic().status == 1)     drawFont(matrixstack, "Natural Draw!",    80, 170);
        if(logic().status == 2)     drawFont(matrixstack, "continue drawing", 80, 170);
        if(logic().turnstate  >= 4) drawFont(matrixstack, logic().hand,            80, 190);
        drawBalance(matrixstack);
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            for(int z = 0; z < logic().cards_player.size(); z++){ if(logic().cards_player.get(z).idletimer == 0) drawCard(matrixstack,  24 + 16*z, 80 + 4*z, logic().cards_player.get(z)); }
            for(int z = 0; z < logic().cards_dealer.size(); z++){ if(logic().cards_dealer.get(z).idletimer == 0) drawCard(matrixstack, 144 + 16*z, 24 + 4*z, logic().cards_dealer.get(z)); }
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            blit(matrixstack, leftPos+24+7,  topPos+204+2,  0, 0, 78, 22); // Button Hit
            blit(matrixstack, leftPos+140+7, topPos+204+2, 78, 0, 78, 22); // Button Stand
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "baccarat";
    }



}
