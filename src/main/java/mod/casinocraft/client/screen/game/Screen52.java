package mod.casinocraft.client.screen.game;

import mod.casinocraft.Register;
import mod.casinocraft.client.logic.game.Logic52;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen52 extends ScreenCasino {   //  2048  :  Maysic Square
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen52(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic52 logic(){
		return (Logic52) menu.logic();
	}
	
	protected String getGameName() {
		return "2048";
	}
	
	protected void createGameButtons(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		drawFontCenter(matrix, "" + logic().scorePoint, 128, 230);
		if(logic().canUndo){
			drawFontCenter(matrix, "UNDO", 64, 230);
		}
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		drawBackground(matrix, Register.TEXTURE_2048_GROUND, Register.TEXTURE_2048);
		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 4; x++){
				if(logic().grid[x][y] != 0){
					int shiftX = logic().grid[x][y] >= 100 ? logic().direction == 4 ? logic().timer : logic().direction == 3 ? -logic().timer : 0 : 0;
					int shiftY = logic().grid[x][y] >= 100 ? logic().direction == 2 ? logic().timer : logic().direction == 1 ? -logic().timer : 0 : 0;
					int color = (logic().grid[x][y] % 100) - 1;
					matrix.blit(Register.TEXTURE_2048, leftPos + 48*x+32 + shiftX, topPos + 48*y+8 + shiftY, (color % 4)*48, (color / 4)*48, 48, 48);
				}
			}
		}
		
		//
		// // ---
		//
		// // RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MYSTICSQUARE);
		// for(int y = 0; y < 4; y++) {
		// 	for(int x = 0; x < 4; x++) {
		// 		int i = logic().grid[x][y] % 20;
		// 		if(i != -1) {
		// 			matrix.blit(Register.TEXTURE_MYSTICSQUARE, leftPos + 32 + 48*x, topPos + 32 + 48*y, (i % 4)*48, (i / 4)*48, 48, 48);
		// 		}
		// 	}
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}