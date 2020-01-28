package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.shared.util.Vector2;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerRoulette;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiRoulette extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiRoulette(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerRoulette(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_ROULETTE);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if(tc.turnstate == 2 && mouseButton == 0){
 		   for(int y = 0; y < 5; y++){
 			   for(int x = 0; x < 25; x++){
 				   if(mouseRect(-128+49 + 16*x, 26 + 24*y, 16, 24, mouseX, mouseY)){
 					   if(tc.selector.matches(x, y) && tc.getValue(x + y*12) == 0) {
 						   if(playerToken >= tc.getBetLow()) {
 							  tc.actionTouch(-1);
 							  CollectBet();
 							  playerToken = -1;
 						   } else {
 							  tc.actionTouch(-2); 
 						   }
 					   } else {
 						  tc.actionTouch(x + y*25);
 					   }
				   }
 			   }
 		   }
 		  for(int x = 0; x < 25; x++){
			   if(mouseRect(-128+49 + 16*x, 161, 16, 24, mouseX, mouseY)){
				   if(tc.selector.matches(x, 5) && tc.getValue(x + 5*12) == 0) {
					   if(playerToken >= tc.getBetLow()) {
							  tc.actionTouch(-1);
							  CollectBet();
							  playerToken = -1;
						   } else {
							  tc.actionTouch(-2); 
						   }
					   } else {
						  tc.actionTouch(x + 5*25);
					   }
			   }
			   if(mouseRect(-128+49 + 16*x, 193, 16, 24, mouseX, mouseY)){
				   if(tc.selector.matches(x, 6) && tc.getValue(x + 6*12) == 0) {
					   if(playerToken >= tc.getBetLow()) {
							  tc.actionTouch(-1);
							  CollectBet();
							  playerToken = -1;
						   } else {
							  tc.actionTouch(-2); 
						   }
					   } else {
						  tc.actionTouch(x + 6*25);
					   }
			   }
		   }
 	   }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
  	   if(keyCode == Keyboard.KEY_RETURN){ tc.actionTouch(-2); }
     }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.turnstate >= 4){
    		this.fontRenderer.drawString("" + tc.getValue(-1),  226, -14, 0);
    		this.fontRenderer.drawString("" + tc.getValue(-1),  225, -15, 16777215);
    	}
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
 	    this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_LEFT);
        this.drawTexturedModalRect(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_RIGHT);
        this.drawTexturedModalRect(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
        
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);
    	
        if(tc.turnstate >= 2){
        	int color = 0;
        	for(int y = 0; y < 5; y++){
        		for(int x = 0; x < 25; x++){
        			color = tc.getValue(x + y*25);
        			if(color != 0)
        				this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+17+8 + 24*y, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
        			if(tc.selector.matches(x, y)) this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+17+8 + 24*y, 192,   0, 32, 32);
        		}
        	}
        	for(int x = 0; x < 25; x++){
        		color = tc.getValue(x + 5*25);
    			if(tc.getValue(x + 5*25) != 0) this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+161, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
    			color = tc.getValue(x + 6*25);
    			if(tc.getValue(x + 6*25) != 0) this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+193, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
    			if(tc.selector.matches(x, 5))   this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+161, 192,   0, 32, 32);
    			if(tc.selector.matches(x, 6))   this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+193, 192,   0, 32, 32);
    		}
        }
        
        if(tc.turnstate == 3){
        	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_WHEEL);
        	this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        	Vector2 v = tc.getVector(1);
        	this.drawTexturedModalRect(guiLeft + v.X, guiTop + v.Y, 128, 66, 16, 16);
        }
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
