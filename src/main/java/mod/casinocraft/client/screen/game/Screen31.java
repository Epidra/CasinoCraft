package mod.casinocraft.client.screen.game;

import mod.casinocraft.client.logic.game.Logic31;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen31 extends ScreenCasino {   //  Solitaire
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen31(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic31 logic(){
		return (Logic31) menu.logic();
	}
	
	protected String getGameName() {
		return "solitaire";
	}
	
	protected void createGameButtons(){
		// buttonSet.addButton(ButtonMap.POS_BOT_RIGHT,  ButtonMap.DRAW, () -> isActivePlayer() && logic().turnstate == 2 && logic().reserve < logic().cards_reserve.length, () -> { action(-1); } );
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		int offset = tableID == 1 ? -16 : 0;
		if(logic().turnstate == 2 && mouseButton == 0){
			for(int x = tableID == 1 ? 1 : 0; x < 8; x++){
				for(int y = 0; y < 20; y++){
					if(mouseRect(offset + 32*x, 64+4 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*8); }
				}
				if(mouseRect(offset + 32*x, 16+4, 32, 48, mouseX, mouseY)){ action((x+1) * -1); }
			}
		}
		
		//
		// // ---
		//
		// int offset = tableID == 1 ? 16 : -32;
		// if(logic().turnstate == 2 && mouseButton == 0){
		// 	for(int y = 0; y < 20; y++){
		// 		for(int x = 0; x < (tableID == 1 ? 7 : 10); x++){
		// 			if(mouseRect(offset + 32*x, 20 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*10); }
		// 		}
		// 	}
		// 	if(tableID == 2 && mouseRect(296, 28, 32, 196, mouseX, mouseY)){ action(-1); }
		// }
		//
		// // ---
		//
		// int offset = tableID == 1 ? -16 : 0;
		// if(logic().turnstate == 2 && mouseButton == 1){
		// 	action(KeyMap.KEY_ENTER);
		// }
		// if(logic().turnstate == 2 && mouseButton == 0){
		// 	for(int y = 0; y < 20; y++){
		// 		for(int x = tableID == 1 ? 1 : 0; x < 8; x++){
		// 			if(mouseRect(offset + 32*x, 68 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*8); }
		// 		}
		// 	}
		// 	if(mouseRect(-offset,       20, 32, 48, mouseX, mouseY)){ action(-1); }
		// 	if(mouseRect(-offset +  32, 20, 32, 48, mouseX, mouseY)){ action(-2); }
		// 	if(mouseRect( offset + 128, 20, 32, 48, mouseX, mouseY)){ action(-5); }
		// 	if(mouseRect( offset + 160, 20, 32, 48, mouseX, mouseY)){ action(-6); }
		// 	if(mouseRect( offset + 192, 20, 32, 48, mouseX, mouseY)){ action(-7); }
		// 	if(mouseRect( offset + 224, 20, 32, 48, mouseX, mouseY)){ action(-8); }
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		// drawValueRight(matrix, "RESERVE", logic().cards_reserve.length - logic().reserve);
		//
		// // ---
		//
		// drawValueLeft(matrix, "POINTS", logic().scorePoint);
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		int offset = tableID == 1 ? -16 : 0;
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < logic().cards_field[x].size(); y++){
				drawCard(matrix, offset + x*32, 64+4 + y*(24-logic().compress), logic().cards_field[x].get(y));
			}
		}
		if(tableID == 2)
			drawCardBack(matrix, offset,   20, 12);
		drawCardBack(matrix, offset +  32, 20, 12);
		drawCardBack(matrix, offset +  64, 20, 12);
		drawCardBack(matrix, offset +  96, 20, 12);
		drawCardBack(matrix, offset + 128, 20,  7);
		drawCardBack(matrix, offset + 160, 20,  7);
		drawCardBack(matrix, offset + 192, 20,  7);
		drawCardBack(matrix, offset + 224, 20,  7);
						drawCard(matrix, offset,      20, logic().cards_freecell[0]);
		drawCard(matrix, offset + 32, 20, logic().cards_freecell[1]);
		drawCard(matrix, offset + 64, 20, logic().cards_freecell[2]);
		drawCard(matrix, offset + 96, 20, logic().cards_freecell[3]);
				if(logic().cards_finish[0].size() > 1) drawCard(matrix, offset + 128, 20, logic().cards_finish[0].get(logic().cards_finish[0].size() - 2));
		if(logic().cards_finish[0].size() > 0) drawCard(matrix, offset + 128, 20, logic().cards_finish[0].get(logic().cards_finish[0].size() - 1));
		if(logic().cards_finish[1].size() > 1) drawCard(matrix, offset + 160, 20, logic().cards_finish[1].get(logic().cards_finish[1].size() - 2));
		if(logic().cards_finish[1].size() > 0) drawCard(matrix, offset + 160, 20, logic().cards_finish[1].get(logic().cards_finish[1].size() - 1));
		if(logic().cards_finish[2].size() > 1) drawCard(matrix, offset + 192, 20, logic().cards_finish[2].get(logic().cards_finish[2].size() - 2));
		if(logic().cards_finish[2].size() > 0) drawCard(matrix, offset + 192, 20, logic().cards_finish[2].get(logic().cards_finish[2].size() - 1));
		if(logic().cards_finish[3].size() > 1) drawCard(matrix, offset + 224, 20, logic().cards_finish[3].get(logic().cards_finish[3].size() - 2));
		if(logic().cards_finish[3].size() > 0) drawCard(matrix, offset + 224, 20, logic().cards_finish[3].get(logic().cards_finish[3].size() - 1));
				if(logic().selector.Y == -2){
			drawCardBack(matrix, offset + logic().selector.X*32, 20, 9);
		} else if(logic().selector.Y >= 0){
			drawCardBack(matrix, offset + logic().selector.X*32, 68 + logic().selector.Y*(24-logic().compress), 9);
		}
				//
		// // ---
		//
		// int offset = tableID == 1 ? 16 : -32;
		// for(int x = 0; x < 10; x++){
		// 	for(int y = 0; y < logic().cards_field[x].size(); y++){
		// 		drawCard(matrix, offset + x*32, 20 + y*(24-logic().compress), logic().cards_field[x].get(y));
		// 	}
		// }
		//
		// if(logic().selector.Y != -1) drawCardBack(matrix, offset + logic().selector.X*32 , 20 + logic().selector.Y*(24-logic().compress), 9);
		//
		// if(tableID == 2){
		// 	drawCardBack(matrix, 296, 28, 7);
		//
		// 	if(logic().cards_reserve[4].size() > 0) drawCardBack(matrix, 296,  28, 0);
		// 	if(logic().cards_reserve[3].size() > 0) drawCardBack(matrix, 296,  52, 0);
		// 	if(logic().cards_reserve[2].size() > 0) drawCardBack(matrix, 296,  76, 0);
		// 	if(logic().cards_reserve[1].size() > 0) drawCardBack(matrix, 296, 100, 0);
		// 	if(logic().cards_reserve[0].size() > 0) drawCardBack(matrix, 296, 124, 0);
		//
		// 	drawCardBack(matrix, -72, 28, 7);
		// 	int i = 0;
		// 	for(Card c : logic().cards_finish){
		// 		drawCard(matrix, -72, 28 + i*24, c);
		// 		i++;
		// 	}
		// }
		//
		// // ---
		//
		// int offset = tableID == 1 ? -16 : 0;
		// drawCardBack(matrix, -offset,       20, logic().scoreLives == 0 ? 8 : 10);
		// drawCardBack(matrix, -offset +  32, 20, 7);
		// drawCardBack(matrix,  offset + 128, 20, 7);
		// drawCardBack(matrix,  offset + 160, 20, 7);
		// drawCardBack(matrix,  offset + 192, 20, 7);
		// drawCardBack(matrix,  offset + 224, 20, 7);
		//
		// for(int x = 0; x < 8; x++){
		// 	for(int y = 0; y < logic().cards_field[x].size(); y++){
		// 		drawCard(matrix, offset + 32*x, 68 + (24-logic().compress)*y, logic().cards_field[x].get(y));
		// 	}
		// }
		//
		// if(logic().cards_stack.size()   > 1) drawCard(    matrix, -offset + 32, 20, logic().cards_stack.get(logic().cards_stack.size() - 2));
		// if(logic().cards_stack.size()   > 0) drawCard(    matrix, -offset + 32, 20, logic().cards_stack.get(logic().cards_stack.size() - 1));
		// if(logic().cards_reserve.size() > 0) drawCardBack(matrix, -offset,      20, 0);
		//
		// if(logic().cards_finish[0].size() > 1) drawCard(matrix, offset + 128, 20, (logic().cards_finish[0].get((logic().cards_finish[0].size() - 2))));
		// if(logic().cards_finish[0].size() > 0) drawCard(matrix, offset + 128, 20, (logic().cards_finish[0].get((logic().cards_finish[0].size() - 1))));
		// if(logic().cards_finish[1].size() > 1) drawCard(matrix, offset + 160, 20, (logic().cards_finish[1].get((logic().cards_finish[1].size() - 2))));
		// if(logic().cards_finish[1].size() > 0) drawCard(matrix, offset + 160, 20, (logic().cards_finish[1].get((logic().cards_finish[1].size() - 1))));
		// if(logic().cards_finish[2].size() > 1) drawCard(matrix, offset + 192, 20, (logic().cards_finish[2].get((logic().cards_finish[2].size() - 2))));
		// if(logic().cards_finish[2].size() > 0) drawCard(matrix, offset + 192, 20, (logic().cards_finish[2].get((logic().cards_finish[2].size() - 1))));
		// if(logic().cards_finish[3].size() > 1) drawCard(matrix, offset + 224, 20, (logic().cards_finish[3].get((logic().cards_finish[3].size() - 2))));
		// if(logic().cards_finish[3].size() > 0) drawCard(matrix, offset + 224, 20, (logic().cards_finish[3].get((logic().cards_finish[3].size() - 1))));
		//
		// if(logic().selector.Y == -2){
		// 	drawCardBack(matrix, -offset + 32, 20, 9);
		// } else if(logic().selector.Y >= 0){
		// 	drawCardBack(matrix, offset + logic().selector.X*32, 20 + logic().selector.Y*(24-logic().compress), 9);
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}