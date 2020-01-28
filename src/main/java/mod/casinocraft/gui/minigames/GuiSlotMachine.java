package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerSlotMachine;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiSlotMachine extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiSlotMachine(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerSlotMachine(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_TETRIS);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
 	   if(keyCode == Keyboard.KEY_RETURN){ tc.actionTouch(-1); }
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTWHEEL);
    	if(tc.turnstate == 2){
    		this.drawTexturedModalRect(guiLeft +  25, guiTop+ 25, 0, 0, 50, 50);
    		this.drawTexturedModalRect(guiLeft +  25, guiTop+100, 0, 0, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 100, guiTop+ 25, 0, 0, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 100, guiTop+100, 0, 0, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 175, guiTop+ 25, 0, 0, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 175, guiTop+100, 0, 0, 50, 50);
    	}
    	if(tc.turnstate >= 3){
    		this.drawTexturedModalRect(guiLeft +  35, guiTop +   3 - (tc.getValue(-1) % 50)+25, tc.getValue((tc.getValue(-1) / 50 + 0) % 9 + 0*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft +  35, guiTop +  53 - (tc.getValue(-1) % 50)+25, tc.getValue((tc.getValue(-1) / 50 + 1) % 9 + 0*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft +  35, guiTop + 103 - (tc.getValue(-1) % 50)+25, tc.getValue((tc.getValue(-1) / 50 + 2) % 9 + 0*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft +  35, guiTop + 153 - (tc.getValue(-1) % 50)+25, tc.getValue((tc.getValue(-1) / 50 + 3) % 9 + 0*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft +  35, guiTop + 203 - (tc.getValue(-1) % 50)+25, tc.getValue((tc.getValue(-1) / 50 + 4) % 9 + 0*9)*50, 50, 50, 50);
    		
    		this.drawTexturedModalRect(guiLeft + 100, guiTop +   3 - (tc.getValue(-2) % 50)+25, tc.getValue((tc.getValue(-2) / 50 + 0) % 9 + 1*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 100, guiTop +  53 - (tc.getValue(-2) % 50)+25, tc.getValue((tc.getValue(-2) / 50 + 1) % 9 + 1*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 100, guiTop + 103 - (tc.getValue(-2) % 50)+25, tc.getValue((tc.getValue(-2) / 50 + 2) % 9 + 1*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 100, guiTop + 153 - (tc.getValue(-2) % 50)+25, tc.getValue((tc.getValue(-2) / 50 + 3) % 9 + 1*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 100, guiTop + 203 - (tc.getValue(-2) % 50)+25, tc.getValue((tc.getValue(-2) / 50 + 4) % 9 + 1*9)*50, 50, 50, 50);
    		
    		this.drawTexturedModalRect(guiLeft + 165, guiTop +   3 - (tc.getValue(-3) % 50)+25, tc.getValue((tc.getValue(-3) / 50 + 0) % 9 + 2*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 165, guiTop +  53 - (tc.getValue(-3) % 50)+25, tc.getValue((tc.getValue(-3) / 50 + 1) % 9 + 2*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 165, guiTop + 103 - (tc.getValue(-3) % 50)+25, tc.getValue((tc.getValue(-3) / 50 + 2) % 9 + 2*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 165, guiTop + 153 - (tc.getValue(-3) % 50)+25, tc.getValue((tc.getValue(-3) / 50 + 3) % 9 + 2*9)*50, 50, 50, 50);
    		this.drawTexturedModalRect(guiLeft + 165, guiTop + 203 - (tc.getValue(-3) % 50)+25, tc.getValue((tc.getValue(-3) / 50 + 4) % 9 + 2*9)*50, 50, 50, 50);
    	}
 	   this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTMACHINE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
