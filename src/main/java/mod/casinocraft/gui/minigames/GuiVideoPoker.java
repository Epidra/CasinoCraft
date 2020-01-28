package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerVideoPoker;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiVideoPoker extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiVideoPoker(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerVideoPoker(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_VIDEOPOKER);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if(tc.turnstate == 2){
    		if(mouseRect(  5,  24, 46, 26, mouseX, mouseY)){ tc.actionTouch(0); } // Hold 1
    		if(mouseRect( 55,  24, 46, 26, mouseX, mouseY)){ tc.actionTouch(1); } // Hold 2
    		if(mouseRect(105,  24, 46, 26, mouseX, mouseY)){ tc.actionTouch(2); } // Hold 3
    		if(mouseRect(155,  24, 46, 26, mouseX, mouseY)){ tc.actionTouch(3); } // Hold 4
    		if(mouseRect(205,  24, 46, 26, mouseX, mouseY)){ tc.actionTouch(4); } // Hold 5
    		if(mouseRect( 82, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(5); } // Finish
    	}
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.getString(0), 76, 151, 0);
    	if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.getString(0), 75, 150, 16777215);
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	if(tc.turnstate >= 2) {
    		drawCardBack(16,  72, 7); // Card 1
    		drawCardBack(64,  72, 7); // Card 2
    		drawCardBack(112, 72, 7); // Card 3
    		drawCardBack(160, 72, 7); // Card 4
    		drawCardBack(208, 72, 7); // Card 5
    		drawCard(16,  72, tc.getCard(0)); // Card 1
    		drawCard(64,  72, tc.getCard(1)); // Card 2
    		drawCard(112, 72, tc.getCard(2)); // Card 3
    		drawCard(160, 72, tc.getCard(3)); // Card 4
    		drawCard(208, 72, tc.getCard(4)); // Card 5
    	}
        
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
		if(tc.turnstate == 2){
			drawTexturedModalRect(guiLeft+82+7, guiTop+204+2, 78*2, 22, 78, 22); // Button Finish
			if(tc.getFlag(0)){drawTexturedModalRect(guiLeft+3+5,   guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+5,   guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 1
			if(tc.getFlag(1)){drawTexturedModalRect(guiLeft+3+55,  guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+55,  guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 2
			if(tc.getFlag(2)){drawTexturedModalRect(guiLeft+3+105, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+105, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 3
			if(tc.getFlag(3)){drawTexturedModalRect(guiLeft+3+155, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+155, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 4
			if(tc.getFlag(4)){drawTexturedModalRect(guiLeft+3+205, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+205, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 5
		}
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
