package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerRougeEtNoir;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiRougeEtNoir extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	 public GuiRougeEtNoir(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerRougeEtNoir(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_ROUGEETNOIR);
    }
    
	 
	 
	//--------------------BASIC--------------------
	 
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if(tc.turnstate == 2) {
    		if(mouseButton == 0){
    			if(mouseRect(32,  48, 32, 48, mouseX, mouseY)){ tc.actionTouch(0); }
    			if(mouseRect(32, 144, 32, 48, mouseX, mouseY)){ tc.actionTouch(1); }
    		}
        }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.turnstate >= 3) this.fontRenderer.drawString("" + tc.getValue(0), 37,  39, 0);
    	if(tc.turnstate >= 3) this.fontRenderer.drawString("" + tc.getValue(0), 36,  38, 16777215);
        if(tc.turnstate >= 3) this.fontRenderer.drawString("" + tc.getValue(1), 37, 135, 0);
        if(tc.turnstate >= 3) this.fontRenderer.drawString("" + tc.getValue(1), 36, 134, 16777215);
        if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 66, 116, 0);
        if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 65, 115, 16777215);
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	drawCardBack(32,  48, 1);
    	drawCardBack(32, 144, 0);
    	int i = 0; for(Card c : tc.getCardStack(0)){ drawCard(32+8 + 16*i,  48, c); i++; }
    	    i = 0; for(Card c : tc.getCardStack(1)){ drawCard(32+8 + 16*i, 144, c); i++; }
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
