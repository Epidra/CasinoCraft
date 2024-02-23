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
	
	protected void createGameButtons(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		if(logic().turnstate == 2){
			for(int y = 0; y < 8; y++){
				for(int x = 0; x < 12; x++){
					if(mouseRect(-16 + 24*x, 44 + 24*y, 24, 24, mouseX, mouseY)){ action(x + y*12); }
				}
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		drawValueLeft(matrix, "POINTS", logic().scorePoint);
		drawValueRight(matrix, "LEFT", logic().reserve.size());
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
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
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}