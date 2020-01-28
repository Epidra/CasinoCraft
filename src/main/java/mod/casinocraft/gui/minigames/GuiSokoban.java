package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerSokoban;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.util.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiSokoban extends GuiCasino {
	
	//--------------------CONSTRUCTOR--------------------
	
	public GuiSokoban(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerSokoban(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_SOKOBAN);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	if(keyCode == Keyboard.KEY_UP)    { tc.actionTouch(0); }
    	if(keyCode == Keyboard.KEY_DOWN)  { tc.actionTouch(1); }
    	if(keyCode == Keyboard.KEY_LEFT)  { tc.actionTouch(2); }
    	if(keyCode == Keyboard.KEY_RIGHT) { tc.actionTouch(3); }
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	
    	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADEDUMMY);
        this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
    	
    	if(tc.turnstate == 1) {
    		if(intro < 256 - 80) {
    			intro = 0;
    			tc.turnstate = 2;
    		}
    	}
    	
    	if(tc.turnstate >= 2) {
    		if(tc.turnstate == 5) GlStateManager.color(0.25F, 0.25F, 0.25F, 1.0F);
    		this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        	for(int x = 1; x < 15; x++){
        		for(int y = 0; y < 12; y++){
        			//if(tc.getValue(x + y*16) == 1) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 32, 64, 16, 16);
        			//if(tc.getValue(x + y*16) == 2) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 48, 64, 16, 16);
        			if(tc.getFlag (x + y*16)     ) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16,  16*7, 216, 16, 16);
        		}
        	}
        	for(Entity e : tc.getEntityList(0)){ this.drawTexturedModalRect(guiLeft + e.Get_Pos().X, guiTop + e.Get_Pos().Y,  16*14, 216, 16, 16); }
        	                                     this.drawTexturedModalRect(guiLeft + tc.getEntity(0).Get_Pos().X, guiTop + tc.getEntity(0).Get_Pos().Y, 0, tc.getEntity(0).Get_LookDirection()*16+66, 16, 16);
            for(Entity e : tc.getEntityList(1)){ this.drawTexturedModalRect(guiLeft + e.Get_Pos().X, guiTop + e.Get_Pos().Y, 16*10, 216, 16, 16); }
            if(tc.turnstate == 5) GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
