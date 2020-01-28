package mod.casinocraft.gui.minigames;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerCraps;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiCraps extends GuiCasino {
	
	int diceColor = 0;
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiCraps(InventoryPlayer playerInv, IInventory furnaceInv, int table){
		super(new ContainerCraps(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_CRAPS);
		Random rand = new Random();
		diceColor = rand.nextInt(8);
	}
   
	
	
	//--------------------BASIC--------------------
	
   protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
       if(tc.turnstate == 2 && mouseButton == 0) {
    	   for(int y = 0; y < 5; y++) {
               for(int x = 0; x < 21; x++) {
            	   if(mouseRect(-50 + 16*x, 49 + 32*y, 16, 30, mouseX, mouseY)){
            		   if(tc.selector.matches(x, y) && tc.getValue(x + y*21) == 0) {
            			   if(playerToken >= tc.getBetLow()) {
            				  tc.actionTouch(-1);
            				  CollectBet();
            				  playerToken = -1;
            			   } else {
            				  tc.actionTouch(-2); 
            			   }
            		   } else {
            			  tc.actionTouch(x + y*21);
            		   }
            	   }
               }
           }
       }
   }
   
   protected void keyTyped2(char typedChar, int keyCode) throws IOException{
	   if(keyCode == Keyboard.KEY_RETURN){ tc.actionTouch(-2); }
   }
   
   protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
	   if(tc.turnstate >= 2)    { this.fontRenderer.drawString(tc.getString(0),        21, 29, 0); }
	   if(tc.turnstate >= 2)    { this.fontRenderer.drawString(tc.getString(0),        20, 28, 16777215); }
	   if(tc.getValue(-1) > -1) { this.fontRenderer.drawString("" + tc.getValue(-1),  201, 29, 0); }
	   if(tc.getValue(-1) > -1) { this.fontRenderer.drawString("" + tc.getValue(-1),  200, 28, 16777215); }
	   if(tc.getValue(-2) > -1) { this.fontRenderer.drawString("" + tc.getValue(-2),  221, 29, 0); }
	   if(tc.getValue(-2) > -1) { this.fontRenderer.drawString("" + tc.getValue(-2),  220, 28, 16777215); }
	   if(tc.getValue(-3) > -1) { this.fontRenderer.drawString("" + tc.getValue(-3),  241, 29, 0); }
	   if(tc.getValue(-3) > -1) { this.fontRenderer.drawString("" + tc.getValue(-3),  240, 28, 16777215); }
   }
   
   protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
	   this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_LEFT);
       this.drawTexturedModalRect(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
       this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_RIGHT);
       this.drawTexturedModalRect(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
       
       this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);
       if(tc.turnstate >= 2){
    	   int color = 0;
    	   for(int y = 0; y < 5; y++){
    		   for(int x = 0; x < 21; x++){
    			   color = tc.getValue(x + y*21);
    			   if(color != 0) this.drawTexturedModalRect(guiLeft+-50 + 16*x, guiTop+49 + 32*y, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
    		   }
    	   }
       }
       if(tc.selector.X > -1) this.drawTexturedModalRect(guiLeft+-50 + 16*tc.selector.X, guiTop+49 + 32*tc.selector.Y, 192, 0, 32, 32);
       
       if(tc.turnstate == 3){
    	   this.drawTexturedModalRect(guiLeft + tc.getDice(0).posX, guiTop + tc.getDice(0).posY, tc.getDice(0).number*32, diceColor*32, 32, 32);
     	   this.drawTexturedModalRect(guiLeft + tc.getDice(1).posX, guiTop + tc.getDice(1).posY, tc.getDice(1).number*32, diceColor*32, 32, 32);
       }
   }
   
   
   
 //--------------------CUSTOM--------------------
   
}
