package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerFreeCell;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiFreeCell extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiFreeCell(InventoryPlayer playerInv, IInventory furnaceInv, int table){
		super(new ContainerFreeCell(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_FREECELL);
	}
   
	
	
	//--------------------BASIC--------------------
	
   protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
	   if(tc.turnstate == 2 && mouseButton == 0){
		   for(int x = 0; x < 8; x++){
			   for(int y = 0; y < 20; y++){
				   if(mouseRect(0 + 32*x, 64 + (24-tc.getValue(0))*y, 32, 24, mouseX, mouseY)){ tc.actionTouch(x + y*8); }
			   }
			   if(mouseRect(0 + 32*x, 16, 32, 48, mouseX, mouseY)){ tc.actionTouch((x+1) * -1); }
		   }
	   }
   }
   
   protected void keyTyped2(char typedChar, int keyCode) throws IOException{
	   if(tc.turnstate == 2 && keyCode == Keyboard.KEY_RETURN){ tc.actionTouch(-9); }
   }
   
   protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
	   
   }
   
   protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
	   for(int x = 0; x < 8; x++){
		   for(int y = 0; y < tc.getCardStack(x).size(); y++){
			   drawCard(x*32, 64 + y*(24-tc.getValue(0)), tc.getCardStack(x).get(y));
		   }
	   }
	   drawCardBack(32*0, 16, 12);
	   drawCardBack(32*1, 16, 12);
	   drawCardBack(32*2, 16, 12);
	   drawCardBack(32*3, 16, 12);
	   drawCardBack(32*4, 16,  7);
	   drawCardBack(32*5, 16,  7);
	   drawCardBack(32*6, 16,  7);
	   drawCardBack(32*7, 16,  7);
	   
	   drawCard(0*32, 16, tc.getCard(0));
	   drawCard(1*32, 16, tc.getCard(1));
	   drawCard(2*32, 16, tc.getCard(2));
	   drawCard(3*32, 16, tc.getCard(3));
	   
	   if(tc.getCardStack( 8).size() > 0) drawCard(4*32, 16, tc.getCardStack( 8).get(tc.getCardStack( 8).size() - 1));
	   if(tc.getCardStack( 9).size() > 0) drawCard(5*32, 16, tc.getCardStack( 9).get(tc.getCardStack( 9).size() - 1));
	   if(tc.getCardStack(10).size() > 0) drawCard(6*32, 16, tc.getCardStack(10).get(tc.getCardStack(10).size() - 1));
	   if(tc.getCardStack(11).size() > 0) drawCard(7*32, 16, tc.getCardStack(11).get(tc.getCardStack(11).size() - 1));
	   
	   if(tc.selector.Y == -2){
		   drawCardBack(tc.selector.X*32, 16, 9);
	   } else if(tc.selector.Y >= 0){
		   drawCardBack(tc.selector.X*32, 64 + tc.selector.Y*(24-tc.getValue(0)), 9);
	   }
   }
   
   
   
 //--------------------CUSTOM--------------------
   
}
