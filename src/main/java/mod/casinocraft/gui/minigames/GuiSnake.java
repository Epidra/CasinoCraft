package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerSnake;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.util.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiSnake extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	 public GuiSnake(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerSnake(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_SNAKE);
    }
    
	 
	 
	//--------------------BASIC--------------------
	 
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	if(keyCode == Keyboard.KEY_UP)    { tc.actionTouch(1); }
    	if(keyCode == Keyboard.KEY_DOWN)  { tc.actionTouch(2); }
    	if(keyCode == Keyboard.KEY_LEFT)  { tc.actionTouch(3); }
    	if(keyCode == Keyboard.KEY_RIGHT) { tc.actionTouch(4); }
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.turnstate >= 2) {
    		this.fontRenderer.drawString("POINTS: " + tc.scorePoints, 16+16, 16+200+16, 9999999);
    	}
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
        	this.drawTexturedModalRect(guiLeft+16 + tc.getVector(0).X*16, guiTop + tc.getVector(0).Y*16, 128, 0+66, 16, 16);
        	this.drawTexturedModalRect(guiLeft+16 + tc.getEntity(0).Get_Pos().X, guiTop + tc.getEntity(0).Get_Pos().Y, 0, tc.getEntity(0).Get_LookDirection()*16+66, 16, 16);
        	for(Entity tail : tc.getEntityList(0)){
        		this.drawTexturedModalRect(guiLeft+16 + tail.Get_Pos().X, guiTop + tail.Get_Pos().Y, 16, 66, 16, 16);
        	}
        	if(tc.turnstate == 5) GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
