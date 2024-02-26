package mod.casinocraft.screen.game;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.game.Logic61;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Ship;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen61 extends ScreenCasino {   //  Snake  :  PacMan
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen61(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic61 logic(){
		return (Logic61) menu.logic();
	}
	
	protected void createGameButtons(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
		drawFontCenter(matrix, "" + logic().scorePoint, 128, 230);
	}
	
	protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
		drawBackground(matrix, CasinoKeeper.TEXTURE_ARCADEDUMMY, CasinoKeeper.TEXTURE_ARCADE);
		drawShip(matrix, logic().point, 5);
		drawShip(matrix, logic().octanom_head, 0, -1, true);
		for(Ship tail : logic().octanom_tail){
			drawShip(matrix, tail, 4, 0, false);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}