package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.Container2048;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class Gui2048 extends GuiCasino {
	
	
	//--------------------CONSTRUCTOR--------------------
	
	 public Gui2048(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new Container2048(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_2048);
    }
    
	 
	 
	//--------------------BASIC--------------------
	 
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	if(!tc.getFlag(17)) {
        	if(keyCode == Keyboard.KEY_UP)    { tc.actionTouch(0); }
        	if(keyCode == Keyboard.KEY_DOWN)  { tc.actionTouch(1); }
        	if(keyCode == Keyboard.KEY_LEFT)  { tc.actionTouch(2); }
        	if(keyCode == Keyboard.KEY_RIGHT) { tc.actionTouch(3); }
        }
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.turnstate >= 2) {
    		this.fontRenderer.drawString("" + tc.scorePoints, 16+16, 16+200+16, 9999999);
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
    	
    	if(tc.turnstate >= 2){
    		if(tc.turnstate == 5) GlStateManager.color(0.25F, 0.25F, 0.25F, 1.0F);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_2048);
            for(int y = 0; y < 4; y++){
            	for(int x = 0; x < 4; x++){
            		//drawMino(tileCasino.getValue(y * 4 + x), x, y);
            		if(tc.getValue(y*4+x) != 0){
            			int shiftX = tc.getFlag(y*4+x) ? tc.getValue(17) == 4 ? tc.getValue(16) : tc.getValue(17) == 3 ? -tc.getValue(16) : 0 : 0;
            			int shiftY = tc.getFlag(y*4+x) ? tc.getValue(17) == 2 ? tc.getValue(16) : tc.getValue(17) == 1 ? -tc.getValue(16) : 0 : 0;
            			this.drawTexturedModalRect(guiLeft + 48*x+32 + shiftX, guiTop + 48*y+16 + shiftY, Get_Spritesheet(true, tc.getValue(y*4+x))*48, Get_Spritesheet(false, tc.getValue(y*4+x))*48, 48, 48);
            		}
            	}
            }
            if(tc.turnstate == 5) GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
    
    
    
  //--------------------CUSTOM--------------------
    
    private int Get_Spritesheet(boolean x, int id) {
        if(x) {
            if(id == 1) return 0;
            if(id == 2) return 1;
            if(id == 3) return 2;
            if(id == 4) return 3;
            if(id == 5) return 0;
            if(id == 6) return 1;
            if(id == 7) return 2;
            if(id == 8) return 3;
            if(id == 9) return 0;
            if(id == 10) return 1;
            if(id == 11) return 2;
            if(id == 12) return 3;
        } else {
            if(id == 1) return 0;
            if(id == 2) return 0;
            if(id == 3) return 0;
            if(id == 4) return 0;
            if(id == 5) return 1;
            if(id == 6) return 1;
            if(id == 7) return 1;
            if(id == 8) return 1;
            if(id == 9) return 2;
            if(id == 10) return 2;
            if(id == 11) return 2;
            if(id == 12) return 2;
        }
        return 0;
    }
}
