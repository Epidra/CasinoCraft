package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.card.ContainerCardBlack;
import mod.casinocraft.container.card.ContainerCardRed;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicCardRed;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiCardRed extends GuiCasino {   // Rouge et Noir

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiCardRed(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world){
        super(new ContainerCardRed(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardRed logic(){
        return (LogicCardRed) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2) {
            if(mouseButton == 0){
                if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ action(0); }
                if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ action(1); }
            }
        }
    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 3) drawFont("" + logic().value_rouge, 36,  38-8);
        if(logic().turnstate >= 3) drawFont("" + logic().value_noir, 36, 134-8);
        if(logic().turnstate >= 4) drawFont(logic().hand, 65, 115-8);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        drawCardBack(32,  48-8, 1);
        drawCardBack(32, 144-8, 0);
        int i = 0; for(Card c : logic().cards_rouge){ drawCard(32+8 + 16*i,  48-8, c); i++; }
        i = 0; for(Card c : logic().cards_noir ){ drawCard(32+8 + 16*i, 144-8, c); i++; }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2, 0, 66, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 0, 88, 78, 22); // Button Stand

        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "rouge_etnoir";
    }

}
