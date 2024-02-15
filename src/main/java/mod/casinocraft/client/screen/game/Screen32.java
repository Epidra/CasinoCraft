package mod.casinocraft.client.screen.game;

import mod.casinocraft.client.logic.game.Logic32;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import mod.casinocraft.util.mapping.ButtonMap;
import mod.lucky77.util.Vector2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen32 extends ScreenCasino {   //  Pyramid
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen32(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic32 logic(){
		return (Logic32) menu.logic();
	}
	
	protected String getGameName() {
		return "pyramid";
	}
	
	protected void createGameButtons(){
		buttonSet.addButton(0, new Vector2(200, 188), ButtonMap.ARROW_LEFT_OFF, ButtonMap.ARROW_LEFT_ON, ButtonMap.LIGHT_SMALL, ButtonMap.SIZE_SMALL, 10, () -> isActivePlayer() && logic().turnstate == 2, () -> action(-2));
		
		//
		// // ---
		//
		// buttonSet.addButton(new Vector2(168, 204), ButtonMap.ARROW_LEFT_OFF, ButtonMap.ARROW_LEFT_ON, 1, 32, () -> isActivePlayer() && logic().turnstate == 2, () -> action(-2));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 20; x++) {
				if(isMouseOverGrid(-32 + 16*x, 20*y, mouseX, mouseY)){
					action(x + y * 20);
				}
				// if(mouseRect(offset + -32 + 16*x, 20*y, 16, 20, mouseX, mouseY)){ action(x + y * 20); }
			}
		}
		if(mouseRect(112, 176, 32, 48, mouseX, mouseY)){ if(logic().cards_stack.size()   > 0) action(-1); }
		if(mouseRect(160, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_reserve.size() > 0) action(-3); }
		
		//
		// // ---
		//
		// for(int y = 0; y < 9; y++) {
		// 	for(int x = 0; x < 15; x++) {
		// 		if(mouseRect(16*x, 20*y, 16, 20, mouseX, mouseY)){ action(x + y * 20); }
		// 	}
		// }
		// if(mouseRect( 92, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_stack.size()   > 0) action(-1); }
		// if(mouseRect(132, 192, 32, 48, mouseX, mouseY)){ if(logic().cards_reserve.size() > 0) action(-3); }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		drawValueLeft(matrix, "POINTS", logic().scorePoint);
		drawValueRight(matrix, "DRAWS", logic().scoreLives);
		
		//
		// // ---
		//
		// drawValueLeft( matrix, "POINTS", logic().scorePoint);
		// drawValueRight(matrix, "DRAWS",  logic().scoreLives);
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		// int offset = tableID == 1 ? 16 : -32;
		// 		drawCard(matrix, offset +  48,  40, logic().cards_field[ 0]);
		// drawCard(matrix, offset + 144,  40, logic().cards_field[ 1]);
		// drawCard(matrix, offset + 240,  40, logic().cards_field[ 2]);
		// drawCard(matrix, offset +  32,  60, logic().cards_field[ 3]);
		// drawCard(matrix, offset +  64,  60, logic().cards_field[ 4]);
		// drawCard(matrix, offset + 128,  60, logic().cards_field[ 5]);
		// drawCard(matrix, offset + 160,  60, logic().cards_field[ 6]);
		// drawCard(matrix, offset + 224,  60, logic().cards_field[ 7]);
		// drawCard(matrix, offset + 256,  60, logic().cards_field[ 8]);
		// drawCard(matrix, offset +  16,  80, logic().cards_field[ 9]);
		// drawCard(matrix, offset +  48,  80, logic().cards_field[10]);
		// drawCard(matrix, offset +  80,  80, logic().cards_field[11]);
		// drawCard(matrix, offset + 112,  80, logic().cards_field[12]);
		// drawCard(matrix, offset + 144,  80, logic().cards_field[13]);
		// drawCard(matrix, offset + 176,  80, logic().cards_field[14]);
		// drawCard(matrix, offset + 208,  80, logic().cards_field[15]);
		// drawCard(matrix, offset + 240,  80, logic().cards_field[16]);
		// drawCard(matrix, offset + 272,  80, logic().cards_field[17]);
		// drawCard(matrix, offset,       100, logic().cards_field[18]);
		// drawCard(matrix, offset +  32, 100, logic().cards_field[19]);
		// drawCard(matrix, offset +  64, 100, logic().cards_field[20]);
		// drawCard(matrix, offset +  96, 100, logic().cards_field[21]);
		// drawCard(matrix, offset + 128, 100, logic().cards_field[22]);
		// drawCard(matrix, offset + 160, 100, logic().cards_field[23]);
		// drawCard(matrix, offset + 192, 100, logic().cards_field[24]);
		// drawCard(matrix, offset + 224, 100, logic().cards_field[25]);
		// drawCard(matrix, offset + 256, 100, logic().cards_field[26]);
		// drawCard(matrix, offset + 288, 100, logic().cards_field[27]);
		// 		drawCardBack(matrix, 112, 176, 7);
		// drawCardBack(matrix, 152, 176, logic().scoreLives == 0 ? 8 : 10);
		// 		if(logic().cards_reserve.size() > 1){ drawCard(matrix, 152, 176, logic().cards_reserve.get(1)); }
		// if(logic().cards_reserve.size() > 0){ drawCard(matrix, 152, 176, logic().cards_reserve.get(0)); }
		// if(logic().cards_stack.size()   > 1){ drawCard(matrix, 112, 176, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
		// if(logic().cards_stack.size()   > 0){ drawCard(matrix, 112, 176, logic().cards_stack.get(logic().cards_stack.size() - 1)); }
		// drawCardBack(matrix, 160, 176, 0);
		
		//
		// // ---
		//
		
		drawCard(matrix, 112,  20, logic().cards_field[ 0]); if(logic().selector.X ==  0) drawCardBack(matrix, 112,  20, 9); if(isMouseOverGrid(112,  20, mouseX, mouseY)) drawCardBack(matrix, 112,  20, 10);
		drawCard(matrix,  96,  40, logic().cards_field[ 1]); if(logic().selector.X ==  1) drawCardBack(matrix,  96,  40, 9); if(isMouseOverGrid( 96,  40, mouseX, mouseY)) drawCardBack(matrix,  96,  40, 10);
		drawCard(matrix, 128,  40, logic().cards_field[ 2]); if(logic().selector.X ==  2) drawCardBack(matrix, 128,  40, 9); if(isMouseOverGrid(128,  40, mouseX, mouseY)) drawCardBack(matrix, 128,  40, 10);
		drawCard(matrix,  80,  60, logic().cards_field[ 3]); if(logic().selector.X ==  3) drawCardBack(matrix,  80,  60, 9); if(isMouseOverGrid( 80,  60, mouseX, mouseY)) drawCardBack(matrix,  80,  60, 10);
		drawCard(matrix, 112,  60, logic().cards_field[ 4]); if(logic().selector.X ==  4) drawCardBack(matrix, 112,  60, 9); if(isMouseOverGrid(112,  60, mouseX, mouseY)) drawCardBack(matrix, 112,  60, 10);
		drawCard(matrix, 144,  60, logic().cards_field[ 5]); if(logic().selector.X ==  5) drawCardBack(matrix, 144,  60, 9); if(isMouseOverGrid(144,  60, mouseX, mouseY)) drawCardBack(matrix, 144,  60, 10);
		drawCard(matrix,  64,  80, logic().cards_field[ 6]); if(logic().selector.X ==  6) drawCardBack(matrix,  64,  80, 9); if(isMouseOverGrid( 64,  80, mouseX, mouseY)) drawCardBack(matrix,  64,  80, 10);
		drawCard(matrix,  96,  80, logic().cards_field[ 7]); if(logic().selector.X ==  7) drawCardBack(matrix,  96,  80, 9); if(isMouseOverGrid( 96,  80, mouseX, mouseY)) drawCardBack(matrix,  96,  80, 10);
		drawCard(matrix, 128,  80, logic().cards_field[ 8]); if(logic().selector.X ==  8) drawCardBack(matrix, 128,  80, 9); if(isMouseOverGrid(128,  80, mouseX, mouseY)) drawCardBack(matrix, 128,  80, 10);
		drawCard(matrix, 160,  80, logic().cards_field[ 9]); if(logic().selector.X ==  9) drawCardBack(matrix, 160,  80, 9); if(isMouseOverGrid(160,  80, mouseX, mouseY)) drawCardBack(matrix, 160,  80, 10);
		drawCard(matrix,  48, 100, logic().cards_field[10]); if(logic().selector.X == 10) drawCardBack(matrix,  48, 100, 9); if(isMouseOverGrid( 48, 100, mouseX, mouseY)) drawCardBack(matrix,  48, 100, 10);
		drawCard(matrix,  80, 100, logic().cards_field[11]); if(logic().selector.X == 11) drawCardBack(matrix,  80, 100, 9); if(isMouseOverGrid( 80, 100, mouseX, mouseY)) drawCardBack(matrix,  80, 100, 10);
		drawCard(matrix, 112, 100, logic().cards_field[12]); if(logic().selector.X == 12) drawCardBack(matrix, 112, 100, 9); if(isMouseOverGrid(112, 100, mouseX, mouseY)) drawCardBack(matrix, 112, 100, 10);
		drawCard(matrix, 144, 100, logic().cards_field[13]); if(logic().selector.X == 13) drawCardBack(matrix, 144, 100, 9); if(isMouseOverGrid(144, 100, mouseX, mouseY)) drawCardBack(matrix, 144, 100, 10);
		drawCard(matrix, 176, 100, logic().cards_field[14]); if(logic().selector.X == 14) drawCardBack(matrix, 176, 100, 9); if(isMouseOverGrid(176, 100, mouseX, mouseY)) drawCardBack(matrix, 176, 100, 10);
		drawCard(matrix,  32, 120, logic().cards_field[15]); if(logic().selector.X == 15) drawCardBack(matrix,  32, 120, 9); if(isMouseOverGrid( 32, 120, mouseX, mouseY)) drawCardBack(matrix,  32, 120, 10);
		drawCard(matrix,  64, 120, logic().cards_field[16]); if(logic().selector.X == 16) drawCardBack(matrix,  64, 120, 9); if(isMouseOverGrid( 64, 120, mouseX, mouseY)) drawCardBack(matrix,  64, 120, 10);
		drawCard(matrix,  96, 120, logic().cards_field[17]); if(logic().selector.X == 17) drawCardBack(matrix,  96, 120, 9); if(isMouseOverGrid( 96, 120, mouseX, mouseY)) drawCardBack(matrix,  96, 120, 10);
		drawCard(matrix, 128, 120, logic().cards_field[18]); if(logic().selector.X == 18) drawCardBack(matrix, 128, 120, 9); if(isMouseOverGrid(128, 120, mouseX, mouseY)) drawCardBack(matrix, 128, 120, 10);
		drawCard(matrix, 160, 120, logic().cards_field[19]); if(logic().selector.X == 19) drawCardBack(matrix, 160, 120, 9); if(isMouseOverGrid(160, 120, mouseX, mouseY)) drawCardBack(matrix, 160, 120, 10);
		drawCard(matrix, 192, 120, logic().cards_field[20]); if(logic().selector.X == 20) drawCardBack(matrix, 192, 120, 9); if(isMouseOverGrid(192, 120, mouseX, mouseY)) drawCardBack(matrix, 192, 120, 10);
		drawCard(matrix,  16, 140, logic().cards_field[21]); if(logic().selector.X == 21) drawCardBack(matrix,  16, 140, 9); if(isMouseOverGrid( 16, 140, mouseX, mouseY)) drawCardBack(matrix,  16, 140, 10);
		drawCard(matrix,  48, 140, logic().cards_field[22]); if(logic().selector.X == 22) drawCardBack(matrix,  48, 140, 9); if(isMouseOverGrid( 48, 140, mouseX, mouseY)) drawCardBack(matrix,  48, 140, 10);
		drawCard(matrix,  80, 140, logic().cards_field[23]); if(logic().selector.X == 23) drawCardBack(matrix,  80, 140, 9); if(isMouseOverGrid( 80, 140, mouseX, mouseY)) drawCardBack(matrix,  80, 140, 10);
		drawCard(matrix, 112, 140, logic().cards_field[24]); if(logic().selector.X == 24) drawCardBack(matrix, 112, 140, 9); if(isMouseOverGrid(112, 140, mouseX, mouseY)) drawCardBack(matrix, 112, 140, 10);
		drawCard(matrix, 144, 140, logic().cards_field[25]); if(logic().selector.X == 25) drawCardBack(matrix, 144, 140, 9); if(isMouseOverGrid(144, 140, mouseX, mouseY)) drawCardBack(matrix, 144, 140, 10);
		drawCard(matrix, 176, 140, logic().cards_field[26]); if(logic().selector.X == 26) drawCardBack(matrix, 176, 140, 9); if(isMouseOverGrid(176, 140, mouseX, mouseY)) drawCardBack(matrix, 176, 140, 10);
		drawCard(matrix, 208, 140, logic().cards_field[27]); if(logic().selector.X == 27) drawCardBack(matrix, 208, 140, 9); if(isMouseOverGrid(208, 140, mouseX, mouseY)) drawCardBack(matrix, 208, 140, 10);
		
		drawCardBack(matrix,  92, 192, 7);
		drawCardBack(matrix, 132, 192, logic().scoreLives == 0 ? 8 : 10);
		
		if(logic().cards_reserve.size() > 1){ drawCard(matrix, 132, 192, logic().cards_reserve.get(1)); }
		if(logic().cards_reserve.size() > 0){ drawCard(matrix, 132, 192, logic().cards_reserve.get(0)); }
		if(logic().cards_stack.size()   > 1){ drawCard(matrix,  92, 192, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
		if(logic().cards_stack.size()   > 0){ drawCard(matrix,  92, 192, logic().cards_stack.get(logic().cards_stack.size() - 1)); }
		if(logic().selector.X == 28) drawCardBack(matrix,  92, 192, 9);
		if(logic().selector.X == 29) drawCardBack(matrix, 132, 192, 9);
		if(isMouseOverGrid(  92, 192, mouseX, mouseY, 1, 1)) drawCardBack(matrix,   92, 192, 10);
		if(isMouseOverGrid( 132, 192, mouseX, mouseY, 1, 1)) drawCardBack(matrix,  132, 192, 10);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private boolean isMouseOverGrid(int posX, int posY, double mouseX, double mouseY){
		return mouseRect(posX, posY, 16, 20, mouseX, mouseY);
	}
	
	private boolean isMouseOverGrid(int posX, int posY, double mouseX, double mouseY, int sizeX, int sizeY){
		return mouseRect(posX, posY, 16*2, 20*2, mouseX, mouseY);
	}
	
	
	
}