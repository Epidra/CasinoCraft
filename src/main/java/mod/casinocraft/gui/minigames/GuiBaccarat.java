package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerBaccarat;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiBaccarat extends GuiCasino {
	
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiBaccarat(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerBaccarat(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_BACCARAT);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if(tc.turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(0); } else
    	if(tc.turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(1); }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	this.fontRenderer.drawString("PLAYER:  " + tc.getValue(0), 25, 25, 0);
    	this.fontRenderer.drawString("PLAYER:  " + tc.getValue(0), 24, 24, 16777215);
    	this.fontRenderer.drawString("DEALER:  " + tc.getValue(1), 25, 41, 0);
    	this.fontRenderer.drawString("DEALER:  " + tc.getValue(1), 24, 40, 16777215);
        
        if(tc.getValue(2) == 1) this.fontRenderer.drawString("Natural Draw!",    81, 171, 0);
        if(tc.getValue(2) == 1) this.fontRenderer.drawString("Natural Draw!",    80, 170, 16777215);
        if(tc.getValue(2) == 2) this.fontRenderer.drawString("continue drawing", 81, 171, 0);
        if(tc.getValue(2) == 2) this.fontRenderer.drawString("continue drawing", 80, 170, 16777215);
        if(tc.turnstate   >= 4) this.fontRenderer.drawString(tc.hand,            81, 191, 0);
        if(tc.turnstate   >= 4) this.fontRenderer.drawString(tc.hand,            80, 190, 16777215);
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	if(tc.turnstate >= 2){
        	for(int z = 0; z < tc.getCardStack(0).size(); z++){ if(tc.getCardStack(0).get(z).idletimer == 0) drawCard( 24 + 16*z, 80 + 4*z, tc.getCardStack(0).get(z)); }
            for(int z = 0; z < tc.getCardStack(1).size(); z++){ if(tc.getCardStack(1).get(z).idletimer == 0) drawCard(144 + 16*z, 24 + 4*z, tc.getCardStack(1).get(z)); }
        }
        
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
		if(tc.turnstate == 2){
			drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
			drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
		}
    }
	
    
    
  //--------------------CUSTOM--------------------
    
}
