package mod.casinocraft.gui.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.card.ContainerCardBlack;
import mod.casinocraft.container.card.ContainerCardWhite;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicCardWhite;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiCardWhite extends GuiCasino {   // Single Poker

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiCardWhite(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world){
        super(new ContainerCardWhite(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardWhite logic(){
        return (LogicCardWhite) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2){
            if(mouseRect(16+4*1+40*0,  24, 46, 26, mouseX, mouseY)){ action(0); } // Hold 1
            if(mouseRect(16+4*2+40*1,  24, 46, 26, mouseX, mouseY)){ action(1); } // Hold 2
            if(mouseRect(16+4*3+40*2,  24, 46, 26, mouseX, mouseY)){ action(2); } // Hold 3
            if(mouseRect(16+4*4+40*3,  24, 46, 26, mouseX, mouseY)){ action(3); } // Hold 4
            if(mouseRect(16+4*5+40*4,  24, 46, 26, mouseX, mouseY)){ action(4); } // Hold 5
            if(mouseRect(         82, 204, 92, 26, mouseX, mouseY)){ action(5); } // Finish
        }
    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 4) drawFont(logic().hand, 75, 150);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            drawCardBack(16+11*1+32*0, 72, 7); // Card 1
            drawCardBack(16+11*2+32*1, 72, 7); // Card 2
            drawCardBack(16+11*3+32*2, 72, 7); // Card 3
            drawCardBack(16+11*4+32*3, 72, 7); // Card 4
            drawCardBack(16+11*5+32*4, 72, 7); // Card 5
            drawCard(16+11*1+32*0, 72, logic().cards_field[0]); // Card 1
            drawCard(16+11*2+32*1, 72, logic().cards_field[1]); // Card 2
            drawCard(16+11*3+32*2, 72, logic().cards_field[2]); // Card 3
            drawCard(16+11*4+32*3, 72, logic().cards_field[3]); // Card 4
            drawCard(16+11*5+32*4, 72, logic().cards_field[4]); // Card 5
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            drawTexturedModalRect(guiLeft+82+7, guiTop+204+2, 78*2, 22, 78, 22); // Button Finish
            if(logic().hold[0]){drawTexturedModalRect(guiLeft+16+4*1+40*0, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+16+4*1+40*0, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 1
            if(logic().hold[1]){drawTexturedModalRect(guiLeft+16+4*2+40*1, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+16+4*2+40*1, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 2
            if(logic().hold[2]){drawTexturedModalRect(guiLeft+16+4*3+40*2, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+16+4*3+40*2, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 3
            if(logic().hold[3]){drawTexturedModalRect(guiLeft+16+4*4+40*3, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+16+4*4+40*3, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 4
            if(logic().hold[4]){drawTexturedModalRect(guiLeft+16+4*5+40*4, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+16+4*5+40*4, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 5
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "single_poker";
    }

}
