package mod.casinocraft.gui.minigames;

import java.io.IOException;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerSpider;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiSpider extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiSpider(InventoryPlayer playerInv, IInventory furnaceInv, int table){
		super(new ContainerSpider(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_SPIDER);
	}
   
	
	
	//--------------------BASIC--------------------
	
   protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
	   if(tc.turnstate == 2 && mouseButton == 0){
		   for(int y = 1; y < 20; y++){
			   for(int x = 0; x < 10; x++){
				   if(mouseRect(-32 + 32*x, 16 + (24-tc.getValue(0))*y, 32, 24, mouseX, mouseY)){ tc.actionTouch(x + y*10); }
			   }
		   }
		   if(mouseRect(296, 24, 32, 196, mouseX, mouseY)){ tc.actionTouch(-1); }
	   }
   }
   
   protected void keyTyped2(char typedChar, int keyCode) throws IOException{
	   
   }
   
   protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
	   
   }
   
   protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
	   for(int x = 0; x < 10; x++){
		   for(int y = 0; y < tc.getCardStack(x).size(); y++){
			   drawCard(-32 + x*32, 16 + y*(24-tc.getValue(0)), tc.getCardStack(x).get(y));
		   }
	   }
	   
	   //if(tc.selector.Y != -1) drawCardBack(tc.selector.X*32 - 32, 16 + tc.selector.Y*24, 9);
	   if(tc.selector.Y != -1) drawCardBack(tc.selector.X*32 - 32, 16 + tc.selector.Y*(24-tc.getValue(0)), 9);
	   
	   drawCardBack(296, 24, 7);
	   
	   if(tc.getCardStack(14).size() > 0) drawCardBack(296, 24 + 0*24, 0);
	   if(tc.getCardStack(13).size() > 0) drawCardBack(296, 24 + 1*24, 0);
	   if(tc.getCardStack(12).size() > 0) drawCardBack(296, 24 + 2*24, 0);
	   if(tc.getCardStack(11).size() > 0) drawCardBack(296, 24 + 3*24, 0);
	   if(tc.getCardStack(10).size() > 0) drawCardBack(296, 24 + 4*24, 0);
	   
	   drawCardBack(-72, 24, 7);
	   int i = 0;
	   for(Card c : tc.getCardStack(15)){
		   drawCard(-72, 24 + i*24, tc.getCard(i));
	   }
   }
   
   
   
 //--------------------CUSTOM--------------------
   
}
