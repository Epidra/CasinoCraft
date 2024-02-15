package mod.casinocraft.client.screen.other;

import mod.casinocraft.client.logic.LogicModule;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenDummy extends ScreenCasino {   //  Dummy
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public ScreenDummy(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public LogicModule logic(){
		return menu.logic();
	}
	
	protected String getGameName() {
		return "";
	}
	
	protected void createGameButtons(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
	
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}
