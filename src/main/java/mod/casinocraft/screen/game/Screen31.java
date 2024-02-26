package mod.casinocraft.screen.game;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.logic.game.Logic31;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.screen.ScreenCasino;
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
	
	protected void createGameButtons(){
	
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
		
		if(logic().turnstate == 2 && mouseButton == 1){
			action(-9);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
		drawValueLeft(matrix,  "POINTS",  logic().scorePoint);
		if(logic().ruleReserveOnecard()){
			drawValueRight(matrix, "RESERVE", logic().cards_reserve.size());
		}
		if(logic().timer > 0 && logic().timer % 4 > 1){
			drawFontCenter(matrix, "AUTO PLAY", 128, 7, 11119017);
		}
	}
	
	protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
		int offset = tableID == 1 ? -16 : 0;
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < logic().cards_field[x].size(); y++){
				drawCard(matrix, offset + x*32, 64+4 + y*(24-logic().compress), logic().cards_field[x].get(y));
				if(mouseRect(offset + 32*x, 64+4 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ drawCardBack(matrix, offset + 32*x, 64+4 + (24-logic().compress)*y, 10); }
			}
		}
		
		if(logic().ruleReserveOnecard()){
			if(logic().cards_reserve.size() > 1) drawCard(    matrix, 24-8 + 32, 20, logic().cards_reserve.get(logic().cards_reserve.size() - 2));
			if(logic().cards_reserve.size() > 0) drawCard(    matrix, 24-8 + 32, 20, logic().cards_reserve.get(logic().cards_reserve.size() - 1));
			if(logic().cards_reserve.size() > 1) drawCardBack(matrix, 24-8,      20, 0);
			if(logic().cards_reserve.size() < 2) drawCardBack(matrix, 24-8,      20, 8);
			if(logic().cards_reserve.size() > 0 && mouseRect(24+  -8  , 20, 32, 48, mouseX, mouseY)){ drawCardBack(matrix, 24 -8     , 20, 10); }
			if(logic().cards_reserve.size() > 0 && mouseRect(24+32-8  , 20, 32, 48, mouseX, mouseY)){ drawCardBack(matrix, 24 -8 + 32, 20, 10); }
			if(logic().cards_reserve.size() > 0 && logic().selector.Y == -2                        ){ drawCardBack(matrix, 24 -8 + 32, 20,  9); }
		}
		
		if(logic().ruleSuits() >= 1) drawCardBack(matrix, offset + 128, 20,  7);
		if(logic().ruleSuits() >= 2) drawCardBack(matrix, offset + 160, 20,  7);
		if(logic().ruleSuits() >= 3) drawCardBack(matrix, offset + 192, 20,  7);
		if(logic().ruleSuits() >= 4) drawCardBack(matrix, offset + 224, 20,  7);
		if(logic().ruleReserveFreecell() && logic().cards_reserve.size() >= 4){
			if(tableID == 2) drawCardBack(matrix, offset,   20, 12);
			drawCardBack(matrix, offset +  32, 20, 12);
			drawCardBack(matrix, offset +  64, 20, 12);
			drawCardBack(matrix, offset +  96, 20, 12);
			drawCard(matrix, offset,      20, logic().cards_reserve.get(0)); if(mouseRect(offset,      20, 32, 48, mouseX, mouseY) && tableID == 2){ drawCardBack(matrix, offset,      20, 10); }
			drawCard(matrix, offset + 32, 20, logic().cards_reserve.get(1)); if(mouseRect(offset + 32, 20, 32, 48, mouseX, mouseY)){ drawCardBack(matrix, offset + 32, 20, 10); }
			drawCard(matrix, offset + 64, 20, logic().cards_reserve.get(2)); if(mouseRect(offset + 64, 20, 32, 48, mouseX, mouseY)){ drawCardBack(matrix, offset + 64, 20, 10); }
			drawCard(matrix, offset + 96, 20, logic().cards_reserve.get(3)); if(mouseRect(offset + 96, 20, 32, 48, mouseX, mouseY)){ drawCardBack(matrix, offset + 96, 20, 10); }
		}
		if(logic().cards_finish[0].size() > 1) drawCard(matrix, offset + 128, 20, logic().cards_finish[0].get(logic().cards_finish[0].size() - 2));
		if(logic().cards_finish[0].size() > 0) drawCard(matrix, offset + 128, 20, logic().cards_finish[0].get(logic().cards_finish[0].size() - 1));
		if(logic().cards_finish[1].size() > 1) drawCard(matrix, offset + 160, 20, logic().cards_finish[1].get(logic().cards_finish[1].size() - 2));
		if(logic().cards_finish[1].size() > 0) drawCard(matrix, offset + 160, 20, logic().cards_finish[1].get(logic().cards_finish[1].size() - 1));
		if(logic().cards_finish[2].size() > 1) drawCard(matrix, offset + 192, 20, logic().cards_finish[2].get(logic().cards_finish[2].size() - 2));
		if(logic().cards_finish[2].size() > 0) drawCard(matrix, offset + 192, 20, logic().cards_finish[2].get(logic().cards_finish[2].size() - 1));
		if(logic().cards_finish[3].size() > 1) drawCard(matrix, offset + 224, 20, logic().cards_finish[3].get(logic().cards_finish[3].size() - 2));
		if(logic().cards_finish[3].size() > 0) drawCard(matrix, offset + 224, 20, logic().cards_finish[3].get(logic().cards_finish[3].size() - 1));
		if(mouseRect(offset + 128, 20, 32, 48, mouseX, mouseY) && logic().ruleSuits() >= 1 && logic().selector.Y >= 0){ drawCardBack(matrix, offset + 128, 20, 10); }
		if(mouseRect(offset + 160, 20, 32, 48, mouseX, mouseY) && logic().ruleSuits() >= 2 && logic().selector.Y >= 0){ drawCardBack(matrix, offset + 160, 20, 10); }
		if(mouseRect(offset + 192, 20, 32, 48, mouseX, mouseY) && logic().ruleSuits() >= 3 && logic().selector.Y >= 0){ drawCardBack(matrix, offset + 192, 20, 10); }
		if(mouseRect(offset + 224, 20, 32, 48, mouseX, mouseY) && logic().ruleSuits() >= 4 && logic().selector.Y >= 0){ drawCardBack(matrix, offset + 224, 20, 10); }
		if(logic().selector.Y == -2 && logic().ruleReserveFreecell()){
			drawCardBack(matrix, offset + logic().selector.X*32, 20, 9);
		} else if(logic().selector.Y >= 0){
			drawCardBack(matrix, offset + logic().selector.X*32, 68 + logic().selector.Y*(24-logic().compress), 9);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}