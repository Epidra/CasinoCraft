package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.card.ContainerCardBlack;
import mod.casinocraft.container.card.ContainerCardOrange;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicCardOrange;
import mod.casinocraft.logic.other.LogicDummy;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiCardOrange extends GuiCasino {   // Baccarat

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiCardOrange(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world){
        super(new ContainerCardOrange(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardOrange logic(){
        return (LogicCardOrange) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(0); } else
        if(logic().turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        drawFont("PLAYER:  " + logic().value_player, 24, 24);
        drawFont("DEALER:  " + logic().value_dealer, 24, 40);

        if(logic().status == 1)     drawFont("Natural Draw!",    80, 170);
        if(logic().status == 2)     drawFont("continue drawing", 80, 170);
        if(logic().turnstate  >= 4) drawFont(logic().hand,            80, 190);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2){
            for(int z = 0; z < logic().cards_player.size(); z++){ if(logic().cards_player.get(z).idletimer == 0) drawCard( 24 + 16*z, 80 + 4*z, logic().cards_player.get(z)); }
            for(int z = 0; z < logic().cards_dealer.size(); z++){ if(logic().cards_dealer.get(z).idletimer == 0) drawCard(144 + 16*z, 24 + 4*z, logic().cards_dealer.get(z)); }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "baccarat";
    }

}
