package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerHalma;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiHalma extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	 public GuiHalma(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerHalma(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_HALMA);
    }
    
	 
	 
	//--------------------BASIC--------------------
	 
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if (mouseButton == 0){
    		for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                	if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ tc.actionTouch(y*17 + x); }
                }
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
    	} else {
    		this.fontRenderer.drawString("POINTS",            24-76-16+1, 25, 0);
    		this.fontRenderer.drawString("POINTS",            24-76-16  , 24, 16777215);
    		this.fontRenderer.drawString("" + tc.scorePoints, 34-76-16+1, 35, 0);
    		this.fontRenderer.drawString("" + tc.scorePoints, 34-76-16  , 34, 16777215);
    	}
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
    	for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(tc.getValue(y*17+x) == 0) this.drawTexturedModalRect(guiLeft-4-24*3 + 24*x, guiTop-4+24 + 24*y, 24*9, 232, 24, 24);
                if(tc.getValue(y*17+x) == 1) this.drawTexturedModalRect(guiLeft-4-24*3 + 24*x, guiTop-4+24 + 24*y,    0, 232, 24, 24);
            }
        }
    	this.drawTexturedModalRect(guiLeft-4-24*3 + 24*tc.selector.X, guiTop-4+24 + 24*tc.selector.Y, 24, 232, 24, 24);
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
