package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerTetris;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiTetris extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiTetris(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerTetris(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_TETRIS);
    }
    
	
	
	//--------------------BASIC--------------------
    
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	if(tc.turnstate == 2) {
    		if(keyCode == Keyboard.KEY_UP)    { tc.actionTouch(0); }
        	if(keyCode == Keyboard.KEY_DOWN)  { tc.actionTouch(1); }
        	if(keyCode == Keyboard.KEY_LEFT)  { tc.actionTouch(2); }
        	if(keyCode == Keyboard.KEY_RIGHT) { tc.actionTouch(3); }
        	if(keyCode == Keyboard.KEY_M)     { tc.actionTouch(4); }
        	if(keyCode == Keyboard.KEY_N)     { tc.actionTouch(5); }
        	if(keyCode == Keyboard.KEY_RETURN){ tc.actionTouch(6); }
    	}
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	if(tc.turnstate >= 2) {
    		this.fontRenderer.drawString("" + tc.scorePoints, 8+16*11 + 4-24, 8+16*0 + 4+8, 9999999);
        	this.fontRenderer.drawString("" + tc.scoreLives,  8+16*11 + 4-24, 8+16*1 + 4+8, 9999999);
        	this.fontRenderer.drawString("" + tc.scoreLevel,  8+16*11 + 4-24, 8+16*2 + 4+8, 9999999);
    	}
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	
    	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_TETRIS);
        this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
        
    	if(tc.turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 20; y++){
            	for(int x = 0; x < 10; x++){
            		if(tc.getValue(y * 10 + x) != -1) this.drawTexturedModalRect(guiLeft+16 + 8 + 12*x, guiTop + 8 + 12*y, tc.turnstate >= 4 ? 12*8 : 12*tc.getValue(y * 10 + x), 204, 12, 12);
            	}
            }
            
            this.drawTexturedModalRect(guiLeft+8+16 + 12*tc.getValue(200), guiTop+8 + 12*tc.getValue(201), tc.turnstate >= 4 ? 12*8 : 12*tc.getValue(210), 204, 12, 12);
        	this.drawTexturedModalRect(guiLeft+8+16 + 12*tc.getValue(202), guiTop+8 + 12*tc.getValue(203), tc.turnstate >= 4 ? 12*8 : 12*tc.getValue(210), 204, 12, 12);
        	this.drawTexturedModalRect(guiLeft+8+16 + 12*tc.getValue(204), guiTop+8 + 12*tc.getValue(205), tc.turnstate >= 4 ? 12*8 : 12*tc.getValue(210), 204, 12, 12);
        	this.drawTexturedModalRect(guiLeft+8+16 + 12*tc.getValue(206), guiTop+8 + 12*tc.getValue(207), tc.turnstate >= 4 ? 12*8 : 12*tc.getValue(210), 204, 12, 12);
            
        	
        	drawTetromino(tc.getValue(211), 8+16*11-24-4, 8+16*4+8);
        	drawTetromino(tc.getValue(212), 8+16*11-24-4, 8+16*9+8);
        	
            //this.drawTexturedModalRect(guiLeft+256-20, guiTop+256-20, 0, 32, 16, 16); // Restart Button
    	}
    }
    
    
    
  //--------------------CUSTOM--------------------
    
    private void drawTetromino(int mino, int x, int y) {
        if(mino == 0) { // I
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y +  0  , tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 96/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 1) { // O
        	this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 2) { // S
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 80/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 16/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 3) { // Z
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 16/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 80/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 4) { // L
        	this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 16/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 48/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 80/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 80/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 5) { // J
        	this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 16/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 48/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 80/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 80/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 6) { // T
        	this.drawTexturedModalRect(guiLeft+x + 16/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 80/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        	this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
    }
	
}
