package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerPyramid;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiPyramid extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiPyramid(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerPyramid(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_PYRAMID);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if (mouseButton == 0){
        	for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 15; x++) {
                	if(mouseRect(0 + 16*x, 0 + 20*y, 16, 20, mouseX, mouseY)){ tc.actionTouch(x + y * 20); }
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
    		
    		drawCard(16 *  7, 20 * 1, tc.getCard( 0)); if(tc.selector.X ==  0) drawCardBack(16 *  7, 20 * 1, 9);
    		
    		drawCard(16 *  6, 20 * 2, tc.getCard( 1)); if(tc.selector.X ==  1) drawCardBack(16 *  6, 20 * 2, 9);
    		drawCard(16 *  8, 20 * 2, tc.getCard( 2)); if(tc.selector.X ==  2) drawCardBack(16 *  8, 20 * 2, 9);
    		
    		drawCard(16 *  5, 20 * 3, tc.getCard( 3)); if(tc.selector.X ==  3) drawCardBack(16 *  5, 20 * 3, 9);
    		drawCard(16 *  7, 20 * 3, tc.getCard( 4)); if(tc.selector.X ==  4) drawCardBack(16 *  7, 20 * 3, 9);
    		drawCard(16 *  9, 20 * 3, tc.getCard( 5)); if(tc.selector.X ==  5) drawCardBack(16 *  9, 20 * 3, 9);
    		
    		drawCard(16 *  4, 20 * 4, tc.getCard( 6)); if(tc.selector.X ==  6) drawCardBack(16 *  4, 20 * 4, 9);
    		drawCard(16 *  6, 20 * 4, tc.getCard( 7)); if(tc.selector.X ==  7) drawCardBack(16 *  6, 20 * 4, 9);
    		drawCard(16 *  8, 20 * 4, tc.getCard( 8)); if(tc.selector.X ==  8) drawCardBack(16 *  8, 20 * 4, 9);
    		drawCard(16 * 10, 20 * 4, tc.getCard( 9)); if(tc.selector.X ==  9) drawCardBack(16 * 10, 20 * 4, 9);
    		
    		drawCard(16 *  3, 20 * 5, tc.getCard(10)); if(tc.selector.X == 10) drawCardBack(16 *  3, 20 * 5, 9);
    		drawCard(16 *  5, 20 * 5, tc.getCard(11)); if(tc.selector.X == 11) drawCardBack(16 *  5, 20 * 5, 9);
    		drawCard(16 *  7, 20 * 5, tc.getCard(12)); if(tc.selector.X == 12) drawCardBack(16 *  7, 20 * 5, 9);
    		drawCard(16 *  9, 20 * 5, tc.getCard(13)); if(tc.selector.X == 13) drawCardBack(16 *  9, 20 * 5, 9);
    		drawCard(16 * 11, 20 * 5, tc.getCard(14)); if(tc.selector.X == 14) drawCardBack(16 * 11, 20 * 5, 9);
    		
    		drawCard(16 *  2, 20 * 6, tc.getCard(15)); if(tc.selector.X == 15) drawCardBack(16 *  2, 20 * 6, 9);
    		drawCard(16 *  4, 20 * 6, tc.getCard(16)); if(tc.selector.X == 16) drawCardBack(16 *  4, 20 * 6, 9);
    		drawCard(16 *  6, 20 * 6, tc.getCard(17)); if(tc.selector.X == 17) drawCardBack(16 *  6, 20 * 6, 9);
    		drawCard(16 *  8, 20 * 6, tc.getCard(18)); if(tc.selector.X == 18) drawCardBack(16 *  8, 20 * 6, 9);
    		drawCard(16 * 10, 20 * 6, tc.getCard(19)); if(tc.selector.X == 19) drawCardBack(16 * 10, 20 * 6, 9);
    		drawCard(16 * 12, 20 * 6, tc.getCard(20)); if(tc.selector.X == 20) drawCardBack(16 * 12, 20 * 6, 9);
    		
    		drawCard(16 *  1, 20 * 7, tc.getCard(21)); if(tc.selector.X == 21) drawCardBack(16 *  1, 20 * 7, 9);
    		drawCard(16 *  3, 20 * 7, tc.getCard(22)); if(tc.selector.X == 22) drawCardBack(16 *  3, 20 * 7, 9);
    		drawCard(16 *  5, 20 * 7, tc.getCard(23)); if(tc.selector.X == 23) drawCardBack(16 *  5, 20 * 7, 9);
    		drawCard(16 *  7, 20 * 7, tc.getCard(24)); if(tc.selector.X == 24) drawCardBack(16 *  7, 20 * 7, 9);
    		drawCard(16 *  9, 20 * 7, tc.getCard(25)); if(tc.selector.X == 25) drawCardBack(16 *  9, 20 * 7, 9);
    		drawCard(16 * 11, 20 * 7, tc.getCard(26)); if(tc.selector.X == 26) drawCardBack(16 * 11, 20 * 7, 9);
    		drawCard(16 * 13, 20 * 7, tc.getCard(27)); if(tc.selector.X == 27) drawCardBack(16 * 13, 20 * 7, 9);
    		
    		drawCardBack(16 * 5, 20 * 10 - 10, 7);
    		drawCardBack(16 * 9, 20 * 10 - 10, tc.scoreLives == 0 ? 8 : 10);
    		
    		if(tc.getCardStack(2).size() > 1){ drawCard(16 * 9, 20 * 10 - 10, tc.getCardStack(2).get(1)); }
    		if(tc.getCardStack(2).size() > 0){ drawCard(16 * 9, 20 * 10 - 10, tc.getCardStack(2).get(0)); }
    		if(tc.getCardStack(1).size() > 1){ drawCard(16 * 5, 20 * 10 - 10, tc.getCardStack(1).get(tc.getCardStack(1).size() - 2)); }
    		if(tc.getCardStack(1).size() > 0){ drawCard(16 * 5, 20 * 10 - 10, tc.getCardStack(1).get(tc.getCardStack(1).size() - 1)); }
    		
    		if(tc.getValue(2) == 28) drawCardBack(16 * 5, 20 * 10 - 10, 9);
    		if(tc.getValue(2) == 29) drawCardBack(16 * 9, 20 * 10 - 10, 9);
    	}
    }
    
    
    
  //--------------------CUSTOM--------------------
	
}
