package mod.casinocraft.client.screen.game;

import mod.casinocraft.client.logic.game.Logic41;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import mod.casinocraft.util.mapping.ButtonMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen41 extends ScreenCasino {   //  Minesweeper
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen41(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic41 logic(){
		return (Logic41) menu.logic();
	}
	
	protected void createGameButtons(){
		buttonSet.addButton(0, ButtonMap.POS_MID_LEFT,  ButtonMap.CONTINUE, ButtonMap.CONTINUE, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1, () -> isActivePlayer() && logic().turnstate == 3, () -> action(-1));
		buttonSet.addButton(0, ButtonMap.POS_MID_RIGHT, ButtonMap.GIVEUP,   ButtonMap.GIVEUP, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1, () -> isActivePlayer() && logic().turnstate == 3, () -> action(-2));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		if(logic().turnstate == 2){
			for(int y = 0; y < 14; y++){
				for(int x = 0; x < 26; x++){
					if(mouseButton == 0 && mouseRect(-80 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26       ); }
					if(mouseButton == 1 && mouseRect(-80 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ action(x + y*26 + 1000); }
				}
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		drawValueLeft(matrix, "POINTS", logic().scorePoint);
		drawValueRight(matrix, "BOMBS", logic().bombs);
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		for(int y = 0; y < 14; y++){
			for(int x = 0; x < 26; x++){
				int i = logic().grid[x][y];
				if(i >= 100){ // hidden
					drawMinoSmall(matrix, -80 + 16*x,  + 16 + 16*y, i >= 200 ? 11 : 0, false);
				} else {
					if(i ==  9){ drawMinoSmall(matrix, -80 + 16*x, 16 + 16*y, 12, false); } // Bomb
					else if(i == 10){ drawMinoSmall(matrix, -80 + 16*x, 16 + 16*y, 13, false); } // Bomb (Exploded)
					else if(i >   0){ drawMinoSmall(matrix, -80 + 16*x, 16 + 16*y, i+1, true); } // Number
				}
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}