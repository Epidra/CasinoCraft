package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerKlondike;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiKlondike extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiKlondike(InventoryPlayer playerInv, IInventory furnaceInv, int table){
		super(new ContainerKlondike(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_KLONDIKE);
	}
   
	
	
	//--------------------BASIC--------------------
	
   protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
	   if(tc.turnstate == 2 && mouseButton == 2){
		   tc.actionTouch(-9);
	   }
	   if(tc.turnstate == 2 && mouseButton == 0){
		   for(int y = 0; y < 20; y++){
			   for(int x = 0; x < 8; x++){
				   if(mouseRect(0 + 32*x, 64 + (24-tc.getValue(0))*y, 32, 24, mouseX, mouseY)){ tc.actionTouch(x + y*8); }
			   }
		   }
		   if(mouseRect(0 + 32*0, 16, 32, 48, mouseX, mouseY)){ tc.actionTouch(-1); }
		   if(mouseRect(0 + 32*1, 16, 32, 48, mouseX, mouseY)){ tc.actionTouch(-2); }
		   
		   if(mouseRect(0 + 32*4, 16, 32, 48, mouseX, mouseY)){ tc.actionTouch(-5); }
		   if(mouseRect(0 + 32*5, 16, 32, 48, mouseX, mouseY)){ tc.actionTouch(-6); }
		   if(mouseRect(0 + 32*6, 16, 32, 48, mouseX, mouseY)){ tc.actionTouch(-7); }
		   if(mouseRect(0 + 32*7, 16, 32, 48, mouseX, mouseY)){ tc.actionTouch(-8); }
	   }
   }
   
   protected void keyTyped2(char typedChar, int keyCode) throws IOException{
	   if(tc.turnstate == 2 && keyCode == Keyboard.KEY_RETURN){ tc.actionTouch(-9); }
   }
   
   protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
	   this.fontRenderer.drawString("POINTS",            76, 26, 0);
	   this.fontRenderer.drawString("POINTS",            75, 25, 16777215);
	   this.fontRenderer.drawString("" + tc.scorePoints, 86, 36, 0);
	   this.fontRenderer.drawString("" + tc.scorePoints, 85, 35, 16777215);
   }
   
   protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
	   drawCardBack(32*0, 16, tc.scoreLives == 0 ? 8 : 10);
	   drawCardBack(32*1, 16, 7);
	   drawCardBack(32*4, 16, 7);
	   drawCardBack(32*5, 16, 7);
	   drawCardBack(32*6, 16, 7);
	   drawCardBack(32*7, 16, 7);
	   
	   for(int x = 0; x < 8; x++){
		   for(int y = 0; y < tc.getCardStack(x).size(); y++){
			   drawCard(0 + 32*x, 64 + (24-tc.getValue(0))*y, tc.getCardStack(x).get(y));
		   }
	   }
	   
	   if(tc.getCardStack(-1).size() > 1) drawCard(32, 16, tc.getCardStack(-1).get(tc.getCardStack(-1).size() - 2));
	   if(tc.getCardStack(-1).size() > 0) drawCard(32, 16, tc.getCardStack(-1).get(tc.getCardStack(-1).size() - 1));
	   if(tc.getCardStack(-2).size() > 0) drawCardBack(32*0, 16, 0);
	   
	   if(tc.getCardStack( 8).size() > 1) drawCard(4*32, 16, tc.getCardStack( 8).get(tc.getCardStack( 8).size() - 2));
	   if(tc.getCardStack( 8).size() > 0) drawCard(4*32, 16, tc.getCardStack( 8).get(tc.getCardStack( 8).size() - 1));
	   if(tc.getCardStack( 9).size() > 1) drawCard(5*32, 16, tc.getCardStack( 9).get(tc.getCardStack( 9).size() - 2));
	   if(tc.getCardStack( 9).size() > 0) drawCard(5*32, 16, tc.getCardStack( 9).get(tc.getCardStack( 9).size() - 1));
	   if(tc.getCardStack(10).size() > 1) drawCard(6*32, 16, tc.getCardStack(10).get(tc.getCardStack(10).size() - 2));
	   if(tc.getCardStack(10).size() > 0) drawCard(6*32, 16, tc.getCardStack(10).get(tc.getCardStack(10).size() - 1));
	   if(tc.getCardStack(11).size() > 1) drawCard(7*32, 16, tc.getCardStack(11).get(tc.getCardStack(11).size() - 2));
	   if(tc.getCardStack(11).size() > 0) drawCard(7*32, 16, tc.getCardStack(11).get(tc.getCardStack(11).size() - 1));
	   
	   if(tc.selector.Y == -2){
		   drawCardBack(32, 16, 9);
	   } else if(tc.selector.Y >= 0){
		   drawCardBack(tc.selector.X*32, 64 + tc.selector.Y*(24-tc.getValue(0)), 9);
	   }
   }
   
   
   
 //--------------------CUSTOM--------------------
   
}
