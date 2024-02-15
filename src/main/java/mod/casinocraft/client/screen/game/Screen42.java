package mod.casinocraft.client.screen.game;

import mod.casinocraft.client.logic.game.Logic42;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen42 extends ScreenCasino {   //  Ishido
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen42(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic42 logic(){
		return (Logic42) menu.logic();
	}
	
	protected String getGameName() {
		return "halma";
	}
	
	protected void createGameButtons(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		// if (mouseButton == 0){
		// 	for(int y = 0; y < 9; y++) {
		// 		for(int x = 0; x < 17; x++) {
		// 			if(mouseRect(-76 + x*24, 20 + y*24, 24, 24, mouseX, mouseY)){ action(y*17 + x); }
		// 		}
		// 	}
		// }
		
		//
		// // ---
		//
		
		if(logic().turnstate == 2){
			for(int y = 0; y < 8; y++){
				for(int x = 0; x < 12; x++){
					if(mouseRect(-16 + 24*x, 44 + 24*y, 24, 24, mouseX, mouseY)){ action(x + y*12); }
				}
			}
		}
		
		//
		// // ---
		//
		// if(logic().turnstate == 2){
		// 	if(logic().color_player.size() < logic().color_simon.size()){
		// 		for(int y = 0; y < 2; y++){
		// 			for(int x = 0; x < 2; x++){
		// 				if(mouseRect(64 + 64*x, 64 + 64*y, 64, 64, mouseX, mouseY)) {
		// 					action(y*2 + x);
		// 				}
		// 			}
		// 		}
		// 	}
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		// drawValueLeft(matrix, "POINTS", logic().scorePoint);
		
		//
		// // ---
		//
		
		drawValueLeft(matrix, "POINTS", logic().scorePoint);
		drawValueRight(matrix, "LEFT", logic().reserve.size());
		
		//
		// // ---
		//
		// drawValueLeft(matrix, "POINTS", logic().scorePoint);
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		// RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
		// for(int y = 0; y < 9; y++) {
		// 	for(int x = 0; x < 17; x++) {
		// 		if(logic().grid[x][y] == 0) drawMino(matrix, -76 + 24*x, 20 + 24*y, 9, 0);
		// 		if(logic().grid[x][y] == 1) drawMino(matrix, -76 + 24*x, 20 + 24*y, 0, 0);
		// 	}
		// }
		// drawMino(matrix, -76 + 24*logic().selector.X, 20 + 24*logic().selector.Y, 3, 0);
		
		//
		// // ---
		//
		
		// // RenderSystem.setShaderTexture(0, Register.TEXTURE_MINOS);
		if(logic().reserve.size() > 0){
			drawMino(matrix, 116, 18, logic().reserve.get(0).number + 1, logic().reserve.get(0).suit + 1);
		}
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 12; x++){
				if(logic().grid[x][y] == -2){
					drawMinoSmall(matrix, -12 + x*24, 48 + y*24, 0, true);
					drawMino(matrix, -16 + x*24, 44 + y*24, 9, 0);
				} else if(logic().grid[x][y] == -1){
					drawMino(matrix, -16 + x*24, 44 + y*24, 9, 0);
				} else if(logic().grid[x][y] >= 0){
					drawMino(matrix, -16 + x*24, 44 + y*24, (logic().grid[x][y] % 6) + 1, (logic().grid[x][y] / 6) + 1);
				}
			}
		}
		
		//
		// // ---
		//
		
		// // RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_SIMON);
		// matrix.blit(Register.TEXTURE_SIMON, leftPos +  64, topPos +  64,   0, logic().alpha[0] > 1 ? 64 : 128, 64, 64);
		// matrix.blit(Register.TEXTURE_SIMON, leftPos + 128, topPos +  64,  64, logic().alpha[1] > 1 ? 64 : 128, 64, 64);
		// matrix.blit(Register.TEXTURE_SIMON, leftPos +  64, topPos + 128, 128, logic().alpha[2] > 1 ? 64 : 128, 64, 64);
		// matrix.blit(Register.TEXTURE_SIMON, leftPos + 128, topPos + 128, 192, logic().alpha[3] > 1 ? 64 : 128, 64, 64);
		// // RenderSystem.setShaderTexture(0, Register.TEXTURE_MINOS);
		// for(int i = 0; i < logic().color_player.size(); i++){
		// 	drawMinoSmall(matrix,  16 + (i%2)*16, 24 + (i/2)*16, logic().color_player.get(i) + 1, false);
		// }
		// for(int i = 0; i < (logic().turnstate ==3 ? logic().alpha_pos + 1 : logic().color_simon.size()); i++){
		// 	drawMinoSmall(matrix, 208 + (i%2)*16, 24 + (i/2)*16, logic().turnstate <= 3 ? 14 : (logic().color_simon.get(i) + 1), logic().turnstate <= 3);
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}