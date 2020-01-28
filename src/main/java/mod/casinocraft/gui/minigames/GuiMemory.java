package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerMemory;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiMemory extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiMemory(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerMemory(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_MEMORY);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if (mouseButton == 0){
    		for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                	if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ tc.actionTouch(y*17 + x); }
                }
            }
    		if(tc.turnstate == 3 && mouseButton == 0){
    			   if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(-1); }
    			   if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(-2); }
    		   }
        }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(table == 1) {
   			this.fontRenderer.drawString("POINTS",            25, 25, 0);
   			this.fontRenderer.drawString("POINTS",            24, 24, 16777215);
   			this.fontRenderer.drawString("" + tc.scorePoints, 35, 35, 0);
   			this.fontRenderer.drawString("" + tc.scorePoints, 34, 34, 16777215);
   			this.fontRenderer.drawString("LIVES",            205, 25, 0);
   			this.fontRenderer.drawString("LIVES",            204, 24, 16777215);
        	this.fontRenderer.drawString("" + tc.scoreLives, 215, 35, 0);
        	this.fontRenderer.drawString("" + tc.scoreLives, 214, 34, 16777215);
   		} else {
   			this.fontRenderer.drawString("POINTS",            24-76-15, 25, 0);
   			this.fontRenderer.drawString("POINTS",            24-76-16, 24, 16777215);
   			this.fontRenderer.drawString("" + tc.scorePoints, 34-76-15, 35, 0);
   			this.fontRenderer.drawString("" + tc.scorePoints, 34-76-16, 34, 16777215);
   			this.fontRenderer.drawString("LIVES",            204+76+17, 25, 0);
   			this.fontRenderer.drawString("LIVES",            204+76+16, 24, 16777215);
        	this.fontRenderer.drawString("" + tc.scoreLives, 214+76+17, 35, 0);
        	this.fontRenderer.drawString("" + tc.scoreLives, 214+76+16, 34, 16777215);
   		}
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tc.turnstate >= 2){
        	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 9; y++){
            	for(int x = 0; x < 17; x++){
            		if(tc.getValue(x + y*17) != -1){
            			if(tc.getValue(200) == x && tc.getValue(201) == y){
            				this.drawTexturedModalRect(guiLeft-76 + 24*x, guiTop+20 + 24*y, tc.getValue(x + y*17)*24+24, 232, 24, 24);
            			} else
            			if(tc.getValue(202) == x && tc.getValue(203) == y){
            				this.drawTexturedModalRect(guiLeft-76 + 24*x, guiTop+20 + 24*y, tc.getValue(x + y*17)*24+24, 232, 24, 24);
            			} else {
            				this.drawTexturedModalRect(guiLeft-76 + 24*x, guiTop+20 + 24*y, 0, 232, 24, 24);
            			}
            		}
            	}
            }
            if(tc.turnstate == 3){
    			drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
    			drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
    		}
        }
    }
	
    
    
  //--------------------CUSTOM--------------------
    
}
