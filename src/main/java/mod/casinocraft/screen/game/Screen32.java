package mod.casinocraft.screen.game;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.logic.game.Logic32;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.mapping.ButtonMap;
import mod.lucky77.util.Vector2;
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
	
	protected void createGameButtons(){
		buttonSet.addButton(0, new Vector2(132+40, 192+13), ButtonMap.ARROW_LEFT_OFF, ButtonMap.ARROW_LEFT_ON, ButtonMap.LIGHT_SMALL, ButtonMap.SIZE_SMALL, 10, () -> isActivePlayer() && logic().turnstate == 2, () -> action(-3));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		if(mouseButton == 0){
			int iCommand = -9;
			
			for(int i = 0; i < logic().cards_field.length; i++){
				int offset = i % 20 >= 10 ? 0 : -16;
				if(logic().cards_field[i].suit != -1 && mouseRect(offset - 32 + 32 * (i%10),  20 + 20*(i/10), 32, 48, mouseX, mouseY)){
					iCommand = i;
				}
			}
			if(iCommand >= 0){
				action(iCommand);
			}
			
			if(mouseRect( 92, 192, 32, 48, mouseX, mouseY) && logic().ruleGameMode() && logic().cards_stack.size()   > 0){ action(-1); }
			if(mouseRect(132, 192, 32, 48, mouseX, mouseY) && logic().ruleGameMode() && logic().cards_reserve.size() > 0){ action(-2); }
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
		drawValueLeft(matrix, "POINTS", logic().scorePoint);
		drawValueRight(matrix, "DRAWS", logic().scoreLives);
	}
	
	protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
		int iHighlight = -1;
		for(int i = 0; i < logic().cards_field.length; i++){
			int offset = i % 20 >= 10 ? 0 : -16;
			if(logic().cards_field[i].suit != -1){
				if(mouseRect(offset - 32 + 32 * (i%10),  20 + 20*(i/10), 32, 48, mouseX, mouseY)){
					iHighlight = i;
				}
			}
		}
		for(int i = 0; i < logic().cards_field.length; i++){
			int offset = i % 20 >= 10 ? 0 : -16;
			if(logic().cards_field[i].suit != -1){
				drawCard(matrix, offset - 32 + 32 * (i%10),  20 + 20*(i/10), logic().cards_field[i]);
				if(iHighlight == i){
					drawCardBack(matrix, offset - 32 + 32 * (i%10),  20 + 20*(i/10), 10);
				}
				if(logic().selector.X == i){
					drawCardBack(matrix, offset - 32 + 32 * (i%10),  20 + 20*(i/10), 9);
				}
				
			}
		}
		if(logic().ruleGameMode()){
			drawCardBack(matrix,  92, 192, 7);
			drawCardBack(matrix, 132, 192, logic().scoreLives == 0 ? 8 : 11);
			if(logic().cards_reserve.size() > 1){ drawCard(    matrix, 132, 192, logic().cards_reserve.get(1)); }
			if(logic().cards_reserve.size() > 0){ drawCard(    matrix, 132, 192, logic().cards_reserve.get(0)); }
			if(logic().cards_stack.size()   > 1){ drawCard(    matrix,  92, 192, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
			if(logic().cards_stack.size()   > 0){ drawCard(    matrix,  92, 192, logic().cards_stack.get(logic().cards_stack.size() - 1)); }
			if(mouseRect( 92, 192, 32, 48, mouseX, mouseY)) drawCardBack(matrix,  92, 192, 10);
			if(mouseRect(132, 192, 32, 48, mouseX, mouseY)) drawCardBack(matrix, 132, 192, 10);
			if( logic().selector.X == 100) drawCardBack(matrix,  92, 192, 9);
			if( logic().selector.X == 200) drawCardBack(matrix, 132, 192, 9);
		} else {
			drawCardBack(matrix,  92+20, 192, 7);
			drawCardBack(matrix, 132+28, 192, logic().scoreLives == 0 ? 8 : 11);
			if(logic().cards_reserve.size() > 1){ drawCard(    matrix, 132+28, 192, logic().cards_reserve.get(1)); }
			if(logic().cards_reserve.size() > 0){ drawCard(    matrix, 132+28, 192, logic().cards_reserve.get(0)); }
			if(logic().cards_stack.size()   > 1){ drawCard(    matrix,  92+20, 192, logic().cards_stack.get(logic().cards_stack.size() - 2)); }
			if(logic().cards_stack.size()   > 0){ drawCard(    matrix,  92+20, 192, logic().cards_stack.get(logic().cards_stack.size() - 1)); }
			drawCardBack(matrix, 135+32, 192, 0);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}