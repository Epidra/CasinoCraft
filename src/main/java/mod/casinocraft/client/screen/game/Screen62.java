package mod.casinocraft.client.screen.game;

import mod.casinocraft.Register;
import mod.casinocraft.client.logic.game.Logic62;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import mod.casinocraft.util.Ship;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class Screen62 extends ScreenCasino {   //  Sokoban
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen62(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic62 logic(){
		return (Logic62) menu.logic();
	}
	
	protected void createGameButtons(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
	
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		if(logic().turnstate == 2){
			drawBackground(matrix, Register.TEXTURE_SOKOBAN, Register.TEXTURE_FONT_ARCADE);
			for(int x = 0; x < 4; x++){
				for(int y = 0; y < 5; y++){
					int number = y * 4 + x + 1;
					drawNumber(matrix, Register.TEXTURE_FONT_ARCADE, leftPos+40 + 48 * x, topPos+14 + 34 * y, (number / 10), (number % 10), hasUnlocked(number), logic().mapID == number - 1);
				}
			}
		}
		if(logic().turnstate >= 3){
			drawBackground(matrix, Register.TEXTURE_ARCADEDUMMY, Register.TEXTURE_ARCADE);
			for(int x = 0; x < 12; x++){
				for(int y = 0; y < 15; y++){
					if(logic().grid[x][y] > 0) drawDigi(matrix, 32 + x*16, 8 + y*16, 0, 0);
				}
			}
			for(Ship e : logic().cross){ drawShip(matrix, e, 4, 2, false); }
			for(Ship e : logic().crate){ drawShip(matrix, e, 4, 1, false); }
			drawShip(matrix, logic().octanom, 2, -1, true);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private boolean hasUnlocked(int index){
		for(int i = 0; i < 20; i++){
			if(logic().scoreHigh[i] == index){
				return true;
			}
		} return false;
	}
	
	private void drawNumber(GuiGraphics matrix, ResourceLocation loc, int x, int y, int left, int right, boolean colored, boolean highlighted){
		int vOffset = 160 + (colored ? 48 : 0) + (highlighted ? 24 : 0);
		matrix.blit(loc, x,    y, 16 * left,  vOffset, 16, 24);
		matrix.blit(loc, x+16, y, 16 * right, vOffset, 16, 24);
	}
	
	
	
}