package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerMeanMinos;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiMeanMinos extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiMeanMinos(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerMeanMinos(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_MEANMINOS);
    }
    
	
	
	//--------------------BASIC--------------------
    
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	if(keyCode == Keyboard.KEY_UP)    { tc.actionTouch(0); }
    	if(keyCode == Keyboard.KEY_DOWN)  { tc.actionTouch(1); }
    	if(keyCode == Keyboard.KEY_LEFT)  { tc.actionTouch(2); }
    	if(keyCode == Keyboard.KEY_RIGHT) { tc.actionTouch(3); }
    	if(keyCode == Keyboard.KEY_M)     { tc.actionTouch(4); }
    	if(keyCode == Keyboard.KEY_N)     { tc.actionTouch(5); }
    	if(keyCode == Keyboard.KEY_RETURN){ tc.actionTouch(6); }
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.turnstate >= 2) {
    		this.fontRenderer.drawString("" + tc.scorePoints, 8+16*11 + 4-24, 8+16*0 + 4+8, 9999999);
        	this.fontRenderer.drawString("" + tc.scoreLives,  8+16*11 + 4-24, 8+16*1 + 4+8, 9999999);
        	this.fontRenderer.drawString("" + tc.scoreLevel,  8+16*11 + 4-24, 8+16*2 + 4+8, 9999999);
    	}
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	
    	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MEANMINOS);
        this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
    	
    	if(tc.turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 15; y++){
            	for(int x = 0; x < 6; x++){
            		//drawMino(tc.getValue(y * 10 + x), x, y);
            		if(tc.getValue(y * 6 + x) != -1) this.drawTexturedModalRect(guiLeft+16 + 8+16 + 16*x, guiTop + 8 + 16*y, tc.turnstate >= 4 ? 16*8 : 16*tc.getValue(y * 6 + x), 216, 16, 16);
            	}
            }
            
            this.drawTexturedModalRect(guiLeft+8+16+16 + 16*tc.getValue(200), guiTop+8 + 16*tc.getValue(201), tc.turnstate >= 4 ? 16*8 : 16*tc.getValue(210), 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+8+16+16 + 16*tc.getValue(202), guiTop+8 + 16*tc.getValue(203), tc.turnstate >= 4 ? 16*8 : 16*tc.getValue(211), 216, 16, 16);
        	
        	if(tc.getValue(212) > -1) drawTetromino(tc.getValue(212), tc.getValue(213), 8+16*11-24-4, 8+16*4+8);
        	if(tc.getValue(214) > -1) drawTetromino(tc.getValue(214), tc.getValue(215), 8+16*11-24-4, 8+16*9+8);
        	
        	//this.drawTexturedModalRect(guiLeft+256-20, guiTop+256-20, 0, 32, 16, 16);
            
            //if(tc.turnstate == 2) tc.update();
    	}
    }
    
    
    
  //--------------------CUSTOM--------------------
    
    private void drawTetromino(int mino0, int mino1, int x, int y) {
    	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino1, 216, 16, 16);
    	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino0, 216, 16, 16);
    }
	
}
