package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerMinesweeper;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiMinesweeper extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiMinesweeper(InventoryPlayer playerInv, IInventory furnaceInv, int table){
		super(new ContainerMinesweeper(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_MINESWEEPER);
	}
   
	
	
	//--------------------BASIC--------------------
	
   protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
	   if(tc.turnstate == 2 && mouseButton == 0){
		   for(int y = 0; y < 14; y++){
			   for(int x = 0; x < 26; x++){
				   if(mouseRect(16-96 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ tc.actionTouch(x + y*26); }
			   }
		   }
	   }
	   if(tc.turnstate == 3 && mouseButton == 0){
		   if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(-1); }
		   if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ tc.actionTouch(-2); }
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
   			this.fontRenderer.drawString("BOMBS",             205, 25, 0);
   			this.fontRenderer.drawString("BOMBS",             205, 24, 16777215);
        	this.fontRenderer.drawString("" + tc.scoreLevel*tc.difficulty, 215, 35, 0);
        	this.fontRenderer.drawString("" + tc.scoreLevel*tc.difficulty, 214, 34, 16777215);
   		} else {
   			this.fontRenderer.drawString("POINTS",            24-76-15, 25, 0);
   			this.fontRenderer.drawString("POINTS",            24-76-16, 24, 16777215);
   			this.fontRenderer.drawString("" + tc.scorePoints, 34-76-15, 35, 0);
   			this.fontRenderer.drawString("" + tc.scorePoints, 34-76-16, 34, 16777215);
   			this.fontRenderer.drawString("BOMBS",             204+76+17, 25, 0);
   			this.fontRenderer.drawString("BOMBS",             204+76+16, 24, 16777215);
        	this.fontRenderer.drawString("" + tc.scoreLevel*tc.difficulty, 214+76+17, 35, 0);
        	this.fontRenderer.drawString("" + tc.scoreLevel*tc.difficulty, 214+76+16, 34, 16777215);
   		}
   }
   
   protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
	   this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
	   for(int y = 0; y < 14; y++){
		   for(int x = 0; x < 26; x++){
			   int i = tc.getValue(x + y*26);
			   if(tc.getFlag(x + y*26)){
				   this.drawTexturedModalRect(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 112, 216, 16, 16);
			   } else {
				   if(i == 9) {
					   this.drawTexturedModalRect(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 192, 216, 16, 16);
				   } else if(i == 10) {
					   this.drawTexturedModalRect(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 208, 216, 16, 16);
				   } else if(i > 0){
					   GlStateManager.color(1.0F-(i/10f), 1.0F, 1.0F, 0.5F);
					   this.drawTexturedModalRect(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 240, 80-16+16*i, 16, 16);
					   GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				   }
			   }
		   }
	   }
	   
	   if(tc.turnstate == 3){
			drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
			drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
		}
   }
   
   
   
 //--------------------CUSTOM--------------------
   
}
