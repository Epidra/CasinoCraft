package mod.casinocraft.client.screen.game;

import mod.casinocraft.Register;
import mod.casinocraft.client.logic.game.Logic11;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import mod.casinocraft.util.mapping.ButtonMap;
import mod.lucky77.util.Vector2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen11 extends ScreenCasino {   //  Roulette
	
	int colorRED   = 10886686;
	int colorBLACK =  5131854;
	int colorGREEN =    46080;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen11(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic11 logic(){
		return (Logic11) menu.logic();
	}
	
	protected void createGameButtons(){
		buttonSet.addButton(0, ButtonMap.POS_BOT_MIDDLE, ButtonMap.PLACE,   ButtonMap.PLACE, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1, () -> isActivePlayer() && logic().turnstate == 2 && !logic().hasPlaced,                       () -> { action(-1);               } );
		buttonSet.addButton(0, ButtonMap.POS_BOT_LEFT,   ButtonMap.ANOTHER, ButtonMap.ANOTHER, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1, () -> isActivePlayer() && logic().turnstate == 2 &&  logic().hasPlaced && playerToken >= bet, () -> { action(-2); collectBet(); } );
		buttonSet.addButton(0, ButtonMap.POS_BOT_RIGHT,  ButtonMap.WAIT,    ButtonMap.WAIT, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1, () -> isActivePlayer() && logic().turnstate == 2 &&  logic().hasPlaced,                       () -> { action(-3);               } );
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		if(logic().turnstate == 2 && !logic().hasPlaced){
			for(int y = 0; y < 7; y++){
				for(int x = 0; x < 25; x++){
					int posX = tableID == 1 ? 16+8 + 8*x : -72 + 16*x;
					int posY = y == 6 ? 200 : y == 5 ? 168 : 32 + 24*y;
					int sizeX = tableID == 1 ? 8 : 16;
					int sizeY = y > 4 ? 32 : 24;
					if(mouseRect(posX, posY, sizeX, sizeY, mouseX, mouseY)){
						action(x + y*25);
					}
				}
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		if(logic().turnstate == 2){ drawTimer(matrix);                                                             }
		if(logic().turnstate == 5){ drawFontCenter(matrix, "" + logic().result, 128, 7, getColor(logic().result)); }
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		if(tableID == 1){
			drawBackground(matrix, Register.TEXTURE_ROULETTE_MIDDLE);
		} else {
			drawBackground(matrix, Register.TEXTURE_ROULETTE_LEFT, -128);
			drawBackground(matrix, Register.TEXTURE_ROULETTE_RIGHT, 128);
		}
		for(int y = 0; y < 7; y++){
			for(int x = 0; x < 25; x++){
				int color = logic().grid[x][y];
				int posX = tableID == 1 ? 16 + 8*x : -72 + 16*x;
				int posY = y == 6 ? 200 : y == 5 ? 168 : 32 + 24*y;
				if(color == -1                   ){ matrix.blit(Register.TEXTURE_DICE, leftPos+posX, topPos+posY, 224,               224, 32, 32); }
				if(color  >  0                   ){ matrix.blit(Register.TEXTURE_DICE, leftPos+posX, topPos+posY, 192, 32 * (color % 10), 32, 32); }
				if(logic().selector.matches(x, y)){ matrix.blit(Register.TEXTURE_DICE, leftPos+posX, topPos+posY, 224,                 0, 32, 32); }
			}
		}
		if(logic().turnstate == 3){
			matrix.blit(Register.TEXTURE_ROULETTE_WHEEL, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
			Vector2 v = logic().vectorWheel();
			drawMinoSmall(matrix, v.X, v.Y, 15, false);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private int getColor(int value){
		if(value ==  0) return colorGREEN;
		if(value ==  1) return colorRED;
		if(value ==  3) return colorRED;
		if(value ==  5) return colorRED;
		if(value ==  7) return colorRED;
		if(value ==  9) return colorRED;
		if(value == 12) return colorRED;
		if(value == 14) return colorRED;
		if(value == 16) return colorRED;
		if(value == 18) return colorRED;
		if(value == 19) return colorRED;
		if(value == 21) return colorRED;
		if(value == 23) return colorRED;
		if(value == 25) return colorRED;
		if(value == 27) return colorRED;
		if(value == 30) return colorRED;
		if(value == 32) return colorRED;
		if(value == 34) return colorRED;
		if(value == 36) return colorRED;
		return colorBLACK;
	}
	
	
	
}