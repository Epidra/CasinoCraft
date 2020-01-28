package mod.casinocraft.gui.minigames;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerSicBo;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiSicBo extends GuiCasino {
	
	int diceColor = 0;
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiSicBo(InventoryPlayer playerInv, IInventory furnaceInv, int table){
		super(new ContainerSicBo(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_SICBO);
		Random rand = new Random();
		diceColor = rand.nextInt(8);
	}
   
	
	
	//--------------------BASIC--------------------
	
   protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
	   if(tc.turnstate == 2 && mouseButton == 0){
		   for(int x = 0; x < 12; x++){
			   if(mouseRect(-63 + 32*x,  13, 32, 30, mouseX, mouseY)){ action(x, 0); }
			   if(mouseRect(-63 + 32*x,  41, 32, 30, mouseX, mouseY)){ action(x, 1); }
			   if(mouseRect(-63 + 32*x,  73, 32, 46, mouseX, mouseY)){ action(x, 2); }
			   if(mouseRect(-63 + 32*x, 121, 32, 46, mouseX, mouseY)){ action(x, 3); }
			   if(mouseRect(-63 + 32*x, 169, 32, 46, mouseX, mouseY)){ action(x, 4); }
			   if(mouseRect(-63 + 32*x, 217, 32, 30, mouseX, mouseY)){ action(x, 5); }
		   }
	   }
   }
   
   private void action(int x, int y) {
	   if(tc.selector.matches(x, y) && tc.getValue(x + y*12) == 0) {
		   if(playerToken >= tc.getBetLow()) {
			  tc.actionTouch(-1);
			  CollectBet();
			  playerToken = -1;
		   } else {
			  tc.actionTouch(-2); 
		   }
	   } else {
		  tc.actionTouch(x + y*12);
	   }
   }
   
   protected void keyTyped2(char typedChar, int keyCode) throws IOException{
	   if(keyCode == Keyboard.KEY_RETURN){ tc.actionTouch(-2); }
   }
   
   protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
	   if(tc.turnstate >= 4){
		   this.fontRenderer.drawString(tc.getString(0), 26,  -9, 0);
		   this.fontRenderer.drawString(tc.getString(0), 25, -10, 16777215);
	   }
   }
   
   protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
	   this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_LEFT);
       this.drawTexturedModalRect(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
       this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_RIGHT);
       this.drawTexturedModalRect(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
       
       this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);
	   
	   if(tc.turnstate >= 2){
		   int color = 0;
		   for(int x = 0; x < 12; x++){
			   color = tc.getValue(x + 0*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  13, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
			   color = tc.getValue(x + 1*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  41, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
			   color = tc.getValue(x + 2*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  73, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
			   color = tc.getValue(x + 3*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 121, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
			   color = tc.getValue(x + 4*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 169, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
			   color = tc.getValue(x + 5*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 217, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
			   
			   if(tc.selector.matches(x, 0)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  13, 192, 0, 32, 32);
			   if(tc.selector.matches(x, 1)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  41, 192, 0, 32, 32);
			   if(tc.selector.matches(x, 2)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  73, 192, 0, 32, 32);
			   if(tc.selector.matches(x, 3)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 121, 192, 0, 32, 32);
			   if(tc.selector.matches(x, 4)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 169, 192, 0, 32, 32);
			   if(tc.selector.matches(x, 5)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 217, 192, 0, 32, 32);
		   }
	   }
	   
       if(tc.turnstate == 3){
    	  this.drawTexturedModalRect(guiLeft + tc.getDice(0).posX, guiTop + tc.getDice(0).posY, tc.getDice(0).number*32, diceColor*32, 32, 32);
    	  this.drawTexturedModalRect(guiLeft + tc.getDice(1).posX, guiTop + tc.getDice(1).posY, tc.getDice(1).number*32, diceColor*32, 32, 32);
    	  this.drawTexturedModalRect(guiLeft + tc.getDice(2).posX, guiTop + tc.getDice(2).posY, tc.getDice(2).number*32, diceColor*32, 32, 32);
       }
   }
   
   
   
 //--------------------CUSTOM--------------------
   
}
