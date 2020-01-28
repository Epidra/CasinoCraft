package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerBlackJack;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiBlackJack extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiBlackJack(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerBlackJack(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_BLACKJACK);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if(tc.turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(0); }
		if(tc.turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(1); }
		if(playerToken >= bet || !tc.hasToken())
			if(tc.turnstate == 2 && mouseRect( 24, 204-40, 92, 26, mouseX, mouseY)){ CollectBet(); tc.actionTouch(2); }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	
    	if(tc.getValue(3) == 0){
    		this.fontRenderer.drawString("PLAYER:  "   + tc.getValue(0),  25, 25, 0);
    		this.fontRenderer.drawString("PLAYER:  "   + tc.getValue(0),  24, 24, 16777215);
    		if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 81, 191, 0);
    		if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 80, 190, 16777215);
    	} else {
    		this.fontRenderer.drawString("PLAYER L:  " + tc.getValue(0),  25, 25, 0);
    		this.fontRenderer.drawString("PLAYER L:  " + tc.getValue(0),  24, 24, 16777215);
    		this.fontRenderer.drawString("PLAYER R:  " + tc.getValue(1),  25, 41, 0);
    		this.fontRenderer.drawString("PLAYER R:  " + tc.getValue(1),  24, 40, 16777215);
    		if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 19, 191, 0);
    		if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 18, 190, 16777215);
    	}
    	if(tc.turnstate >= 3) this.fontRenderer.drawString("DEALER:  " + tc.getValue(2), 25, 57, 0);
    	if(tc.turnstate >= 3) this.fontRenderer.drawString("DEALER:  " + tc.getValue(2), 24, 56, 16777215);
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        
        if(tc.turnstate >= 2){
        	for(int z = 0; z < tc.getCardStack(0).size(); z++){ if(tc.getCardStack(0).get(z).idletimer == 0) drawCard( 24 + 16*z, 100 + 4*z, tc.getCardStack(0).get(z)); if(tc.getValue(3) == 1) drawCardBack( 24 + 16*z, 100 + 4*z, 10); }
        	for(int z = 0; z < tc.getCardStack(1).size(); z++){ if(tc.getCardStack(1).get(z).idletimer == 0) drawCard(144 + 16*z, 100 + 4*z, tc.getCardStack(1).get(z)); if(tc.getValue(3) == 2) drawCardBack(144 + 16*z, 100 + 4*z, 10); }
            for(int z = 0; z < tc.getCardStack(2).size(); z++){ if(tc.getCardStack(2).get(z).idletimer == 0) drawCard(144 + 16*z,  24 + 4*z, tc.getCardStack(2).get(z)); }
        }
        
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
		if(tc.turnstate == 2){
			drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
			drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
			if(tc.getFlag(0)){
				if(playerToken == -1) ValidateBet();
				if(playerToken >= bet){
					drawTexturedModalRect(guiLeft+24+7, guiTop+204-40+2, 78*2, 0, 78, 22); // Button Split
				}
			}
		}
        
    }
	
    
    
  //--------------------CUSTOM--------------------
    
}
