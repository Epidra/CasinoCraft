package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerSudoku;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiSudoku extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiSudoku(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerSudoku(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_SUDOKU);
    }
    
	
	
	//--------------------BASIC--------------------
	
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if (mouseButton == 0){
    		for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 9; x++) {
                	if(mouseRect(20 + 24*x, 20 + 24*y, 24, 24, mouseX, mouseY)){ tc.actionTouch(100 + y*9 + x); }
                }
            }
        }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	if(keyCode == Keyboard.KEY_1) { tc.actionTouch(1); }
    	if(keyCode == Keyboard.KEY_2) { tc.actionTouch(2); }
    	if(keyCode == Keyboard.KEY_3) { tc.actionTouch(3); }
    	if(keyCode == Keyboard.KEY_4) { tc.actionTouch(4); }
    	if(keyCode == Keyboard.KEY_5) { tc.actionTouch(5); }
    	if(keyCode == Keyboard.KEY_6) { tc.actionTouch(6); }
    	if(keyCode == Keyboard.KEY_7) { tc.actionTouch(7); }
    	if(keyCode == Keyboard.KEY_8) { tc.actionTouch(8); }
    	if(keyCode == Keyboard.KEY_9) { tc.actionTouch(9); }
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
    	
    	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SUDOKU);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        
        this.drawTexturedModalRect(guiLeft+20 + 24*tc.getValue(-1), guiTop+20 + 24*tc.getValue(-2), 0, 232, 24, 24);
        
    	for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(tc.getValue(y*9+x+100) != 0) {
                	if(tc.getValue(y*9+x) > 0) {
                		GlStateManager.color(0.25F, 0.25F, 0.25F, 1.0F);
                    	this.drawTexturedModalRect(guiLeft+20+4 + 24*x, guiTop+20+4 + 24*y, 240, 64 + 16*tc.getValue(y*9+x), 16, 16);
                    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                	}
                } else {
                	if(tc.getValue(y*9+x) > 0) {
                		this.drawTexturedModalRect(guiLeft+20+4 + 24*x, guiTop+20+4 + 24*y, 240, 64 + 16*tc.getValue(y*9+x), 16, 16);
                	}
                }
            }
        }
    }
    
    
    
  //--------------------CUSTOM--------------------
    
}
