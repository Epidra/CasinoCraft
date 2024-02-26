package mod.casinocraft.screen.game;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.logic.game.Logic21;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.mapping.ButtonMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen21 extends ScreenCasino {   //  Black Jack  :  Baccarat
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen21(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic21 logic(){
		return (Logic21) menu.logic();
	}
	
	protected void createGameButtons(){
		buttonSet.addButton(0, ButtonMap.POS_MID_LEFT,  ButtonMap.HIT, ButtonMap.HIT, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1,    () -> isActivePlayer() && logic().turnstate == 2,                                          () -> action(0));
		buttonSet.addButton(0, ButtonMap.POS_MID_RIGHT, ButtonMap.STAND, ButtonMap.STAND, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1,  () -> isActivePlayer() && logic().turnstate == 2,                                          () -> action(1));
		buttonSet.addButton(0, ButtonMap.POS_TOP_RIGHT, ButtonMap.SPLIT, ButtonMap.SPLIT, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1,  () -> isActivePlayer() && logic().turnstate == 2 && canSplit(),                            () -> action(2));
		buttonSet.addButton(0, ButtonMap.POS_TOP_LEFT,  ButtonMap.DOUBLE, ButtonMap.DOUBLE, ButtonMap.LIGHT_LARGE, ButtonMap.SIZE_LARGE, -1, () -> isActivePlayer() && logic().turnstate == 2 && menu.hasToken() && playerToken >= bet, () -> action(3));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
		if(logic().split == 0){
			drawFont(matrix, "PLAYER:  "   + logic().value_player1,  24, 24);
			if(logic().turnstate >= 4) drawFontCenter(matrix, logic().hand, 128, 190);
		} else {
			drawFont(matrix, "PLAYER L:  " + logic().value_player1,  24, 24);
			drawFont(matrix, "PLAYER R:  " + logic().value_player2,  24, 40);
			if(logic().turnstate >= 4) drawFontCenter(matrix, logic().hand, 128, 190);
		}
		if(logic().turnstate >= 3) drawFont(matrix, "DEALER:  " + logic().value_dealer, 24, 56);
	}
	
	protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
		for(int z = 0; z < logic().cards_player1.size(); z++){ if(logic().cards_player1.get(z).idletimer == 0) drawCard(matrix,  24 + 16*z, 100 + 4*z, logic().cards_player1.get(z)); if(logic().split == 1) drawCardBack(matrix,  24 + 16*z, 100 + 4*z, 10); }
		for(int z = 0; z < logic().cards_player2.size(); z++){ if(logic().cards_player2.get(z).idletimer == 0) drawCard(matrix, 144 + 16*z, 100 + 4*z, logic().cards_player2.get(z)); if(logic().split == 2) drawCardBack(matrix, 144 + 16*z, 100 + 4*z, 10); }
		for(int z = 0; z < logic().cards_dealer.size();  z++){ if(logic().cards_dealer.get(z).idletimer  == 0) drawCard(matrix, 144 + 16*z,  24 + 4*z, logic().cards_dealer.get(z)); }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private boolean canSplit(){
		return    ((logic().cards_player1.get(0).number >= 9 && logic().cards_player1.get(1).number >= 9)
				|| (logic().cards_player1.get(0).number      == logic().cards_player1.get(1).number))
				&& (playerToken >= bet || !menu.hasToken());
	}
	
	
	
}