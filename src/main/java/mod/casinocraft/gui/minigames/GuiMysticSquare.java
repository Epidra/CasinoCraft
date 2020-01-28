package mod.casinocraft.gui.minigames;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.minigames.ContainerMysticSquare;
import mod.casinocraft.gui.GuiCasino;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class GuiMysticSquare extends GuiCasino {
	
		//--------------------CONSTRUCTOR--------------------
	
	public GuiMysticSquare(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(new ContainerMysticSquare(playerInv, furnaceInv), playerInv, furnaceInv, table, CasinoKeeper.MODULE_MYSTICSQUARE);
    }
    
	
	
	//--------------------BASIC--------------------
    
    protected void mouseClicked2(int mouseX, int mouseY, int mouseButton) throws IOException {
    	if (mouseButton == 0){
        	for(int y = 0; y < 4; y++){
        		for(int x = 0; x < 4; x++){
        			if(mouseRect(32 + 48*x, 32 + 48*y, 48, 48, mouseX, mouseY)){
        				if(y > 0) if(tc.getValue((x    ) + (y - 1)*4) == -1) tc.actionTouch(0);
                        if(y < 3) if(tc.getValue((x    ) + (y + 1)*4) == -1) tc.actionTouch(1);
                        if(x > 0) if(tc.getValue((x - 1) + (y    )*4) == -1) tc.actionTouch(2);
                        if(x < 3) if(tc.getValue((x + 1) + (y    )*4) == -1) tc.actionTouch(3);
        			}
        		}
        	}
        }
    }
    
    protected void keyTyped2(char typedChar, int keyCode) throws IOException{
    	if(keyCode == Keyboard.KEY_UP)   { tc.actionTouch(0); }
    	if(keyCode == Keyboard.KEY_DOWN) { tc.actionTouch(1); }
    	if(keyCode == Keyboard.KEY_LEFT) { tc.actionTouch(2); }
    	if(keyCode == Keyboard.KEY_RIGHT){ tc.actionTouch(3); }
    }
    
    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
    	
    }
    
    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tc.turnstate >= 2){
        	this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MYSTICSQUARE);
            for(int y = 0; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(tc.getValue(x + y*4) != -1) {
                    	this.drawTexturedModalRect(guiLeft + 32 + 48*x, guiTop + 32 + 48*y, Get_Spritesheet(true, tc.getValue(x + y*4))*48, Get_Spritesheet(false, tc.getValue(x + y*4))*48, 48, 48);
                    }
                }
            }
        }
    }
    
    
    
  //--------------------CUSTOM--------------------
    
    private int Get_Spritesheet(boolean x, int id) {
        if(x) {
            if(id == 0) return 0;
            if(id == 1) return 1;
            if(id == 2) return 2;
            if(id == 3) return 3;
            if(id == 4) return 0;
            if(id == 5) return 1;
            if(id == 6) return 2;
            if(id == 7) return 3;
            if(id == 8) return 0;
            if(id == 9) return 1;
            if(id == 10) return 2;
            if(id == 11) return 3;
            if(id == 12) return 0;
            if(id == 13) return 1;
            if(id == 14) return 2;
            if(id == -1) return 3;
        } else {
            if(id == 0) return 0;
            if(id == 1) return 0;
            if(id == 2) return 0;
            if(id == 3) return 0;
            if(id == 4) return 1;
            if(id == 5) return 1;
            if(id == 6) return 1;
            if(id == 7) return 1;
            if(id == 8) return 2;
            if(id == 9) return 2;
            if(id == 10) return 2;
            if(id == 11) return 2;
            if(id == 12) return 3;
            if(id == 13) return 3;
            if(id == 14) return 3;
            if(id == -1) return 3;
        }
        return 0;
    }
	
}
