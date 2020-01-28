package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerAceyDeucey;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiAceyDeucey extends GuiCasino {
	
	
		//--------------------CONSTRUCTOR--------------------
	
	 public GuiAceyDeucey(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerAceyDeucey(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_ACEYDEUCEY);
    }
	
	//--------------------BASIC--------------------
	 
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if(tc.turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(AnotherBet() ? 0 : 1); } else
    	if(tc.turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(1); }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.getValue(0) > -1) this.fontRenderer.drawString("Spread: " + tc.getValue(0),  101, 126, 0);
    	if(tc.getValue(0) > -1) this.fontRenderer.drawString("Spread: " + tc.getValue(0),  100, 125, 16777215);
		this.fontRenderer.drawString(tc.getString(0),  76, 151, 0);
		this.fontRenderer.drawString(tc.getString(0),  75, 150, 16777215);
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	
    	                           drawCard(64,  72, tc.getCard( 0));
		if(tc.getCard(2) != null){ drawCard(112, 72, tc.getCard( 2)); }
		                           drawCard(160, 72, tc.getCard( 1));
    	
    	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
		if(tc.turnstate == 2){
			drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
			drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
		}
		
		
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
