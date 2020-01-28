package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerTriPeak;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiTriPeak extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiTriPeak(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerTriPeak(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_TRIPEAK);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if (mouseButton == 0){
    		for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 20; x++) {
                	if(mouseRect(-32 + 16*x, 0 + 20*y, 16, 20, mouseX, mouseY)){ tc.actionTouch(x + y * 20); }
                }
            }
    		if(mouseRect(0 + 16*5, 192, 32, 48, mouseX, mouseY)){ if(tc.getCardStack(1).size() > 0) tc.actionTouch(-1); }
        	if(mouseRect(0 + 16*7, 192, 32, 48, mouseX, mouseY)){                                   tc.actionTouch(-2); }
        	if(mouseRect(0 + 16*9, 192, 32, 48, mouseX, mouseY)){ if(tc.getCardStack(2).size() > 0) tc.actionTouch(-3); }
        }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.turnstate >= 2){
    		this.fontRenderer.drawString("POINTS",             25, 25, 0);
    		this.fontRenderer.drawString("POINTS",             24, 24, 16777215);
    		this.fontRenderer.drawString("" + tc.getValue(0),  35, 35, 0);
    		this.fontRenderer.drawString("" + tc.getValue(0),  34, 34, 16777215);
        	this.fontRenderer.drawString("DRAWS",             205, 25, 0);
        	this.fontRenderer.drawString("DRAWS",             204, 24, 16777215);
        	this.fontRenderer.drawString("" + tc.getValue(1), 215, 35, 0);
        	this.fontRenderer.drawString("" + tc.getValue(1), 214, 34, 16777215);
    	}
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	if(tc.turnstate >= 2){
    		
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
    		drawTexturedModalRect(guiLeft + 16*7 + 3+2, guiTop +2+ 20 * 10, 234, 0, 22, 22); // Button Stack
    		
    		drawCard(16 *  3-32, 20 * 2, tc.getCard( 0));
        	drawCard(16 *  9-32, 20 * 2, tc.getCard( 1));
        	drawCard(16 * 15-32, 20 * 2, tc.getCard( 2));
        	
        	drawCard(16 *  2-32, 20 * 3, tc.getCard( 3));
        	drawCard(16 *  4-32, 20 * 3, tc.getCard( 4));
        	drawCard(16 *  8-32, 20 * 3, tc.getCard( 5));
        	drawCard(16 * 10-32, 20 * 3, tc.getCard( 6));
        	drawCard(16 * 14-32, 20 * 3, tc.getCard( 7));
        	drawCard(16 * 16-32, 20 * 3, tc.getCard( 8));
        	
        	drawCard(16 *  1-32, 20 * 4, tc.getCard( 9));
        	drawCard(16 *  3-32, 20 * 4, tc.getCard(10));
        	drawCard(16 *  5-32, 20 * 4, tc.getCard(11));
        	drawCard(16 *  7-32, 20 * 4, tc.getCard(12));
        	drawCard(16 *  9-32, 20 * 4, tc.getCard(13));
        	drawCard(16 * 11-32, 20 * 4, tc.getCard(14));
        	drawCard(16 * 13-32, 20 * 4, tc.getCard(15));
        	drawCard(16 * 15-32, 20 * 4, tc.getCard(16));
        	drawCard(16 * 17-32, 20 * 4, tc.getCard(17));
        	
        	drawCard(16 *  0-32, 20 * 5, tc.getCard(18));
        	drawCard(16 *  2-32, 20 * 5, tc.getCard(19));
        	drawCard(16 *  4-32, 20 * 5, tc.getCard(20));
        	drawCard(16 *  6-32, 20 * 5, tc.getCard(21));
        	drawCard(16 *  8-32, 20 * 5, tc.getCard(22));
        	drawCard(16 * 10-32, 20 * 5, tc.getCard(23));
        	drawCard(16 * 12-32, 20 * 5, tc.getCard(24));
        	drawCard(16 * 14-32, 20 * 5, tc.getCard(25));
        	drawCard(16 * 16-32, 20 * 5, tc.getCard(26));
        	drawCard(16 * 18-32, 20 * 5, tc.getCard(27));
        	
        	drawCardBack(16 * 5, 20 * 10 - 10, 7);
    		drawCardBack(16 * 9, 20 * 10 - 10, tc.scoreLives == 0 ? 8 : 10);
    		
    		if(tc.getCardStack(2).size() > 1){ drawCard(16 * 9, 20 * 10 - 10, tc.getCardStack(2).get(1)); }
    		if(tc.getCardStack(2).size() > 0){ drawCard(16 * 9, 20 * 10 - 10, tc.getCardStack(2).get(0)); }
    		if(tc.getCardStack(1).size() > 1){ drawCard(16 * 5, 20 * 10 - 10, tc.getCardStack(1).get(tc.getCardStack(1).size() - 2)); }
    		if(tc.getCardStack(1).size() > 0){ drawCard(16 * 5, 20 * 10 - 10, tc.getCardStack(1).get(tc.getCardStack(1).size() - 1)); }
    	}
    }
    
    
    
  //--------------------CUSTOM--------------------
	
}
