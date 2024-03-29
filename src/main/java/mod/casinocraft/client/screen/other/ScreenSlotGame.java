package mod.casinocraft.client.screen.other;

import mod.casinocraft.Register;
import mod.casinocraft.client.logic.other.LogicSlotGame;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.client.screen.ScreenCasino;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ScreenSlotGame extends ScreenCasino {   //  Slot Machine
	
	int color1 = 11119017;
	int color2 = 14474460;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public ScreenSlotGame(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public LogicSlotGame logic(){
		return (LogicSlotGame) menu.logic();
	}
	
	protected void createGameButtons(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
		if(mouseRect(256, 48, 48, 200, mouseX, mouseY)){ action(1); }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
		if(logic().turnstate == 0 &&                    !menu.hasToken()                          ){ drawFontCenter(matrix, "PRESS ENTER TO START",  133, 215, color2); }
		if(logic().turnstate == 0 &&                     menu.hasToken()                          ){ drawFontCenter(matrix, "PRESS ENTER FOR TOKEN", 133, 215, color2); }
		if(logic().turnstate == 2 && isActivePlayer() && menu.hasToken() && logic().scoreLevel < 5){ drawFontCenter(matrix, "PRESS ENTER FOR TOKEN", 133, 215, color2); }
		if(logic().turnstate == 2 && isActivePlayer()                                             ){ drawFontCenter(matrix, "PRESS SPACE TO SPIN",   133, 198, color2); }
		if(logic().turnstate == 3 && isActivePlayer()                    && logic().wheelSTOP > -1){ drawFontCenter(matrix, "PRESS SPACE TO STOP",   133, 198, color2); }
		if(logic().turnstate == 5                                                                 ){ drawFontCenter(matrix, "PRESS ENTER TO RESET",  133, 215, color2); }
		
		// ----- TOKEN ----- //
		if(menu.hasToken() && menu.getBettingHigh() > 0){
			drawFontCenter(matrix, "TOKEN",                      219, 194, color1);
			matrix.renderFakeItem(menu.getItemToken(), 211, 202        );
			drawFontCenter(matrix, "x" + menu.getBettingLow(),   219, 218, color1);
		}
		
		// ----- WINNINGS ----- //
		drawFont(matrix, "x" + logic().multi[0], 46, 184, color1); // BAR
		drawFont(matrix, "x" + logic().multi[1], 46, 193, color1); // ICON 1
		drawFont(matrix, "x" + logic().multi[2], 46, 202, color1); // ICON 2
		drawFont(matrix, "x" + logic().multi[3], 46, 211, color1); // ICON 3
		drawFont(matrix, "x" + logic().multi[4], 46, 220, color1); // ICON 4
		drawFont(matrix, "x" + logic().multi[5], 46, 229, color1); // SEVEN
	}
	
	protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		loadTexture();
		
		// ----- WINNINGS ----- //
		matrix.blit(loadTexture(), leftPos + 37, topPos + 184,  24, 240, 8, 8); // BAR
		matrix.blit(loadTexture(), leftPos + 21, topPos + 193,  56, 240, 8, 8); // ICON 1
		matrix.blit(loadTexture(), leftPos + 29, topPos + 193,  56, 240, 8, 8); // ICON 1
		matrix.blit(loadTexture(), leftPos + 37, topPos + 193,  56, 240, 8, 8); // ICON 1
		matrix.blit(loadTexture(), leftPos + 21, topPos + 202,  88, 240, 8, 8); // ICON 2
		matrix.blit(loadTexture(), leftPos + 29, topPos + 202,  88, 240, 8, 8); // ICON 2
		matrix.blit(loadTexture(), leftPos + 37, topPos + 202,  88, 240, 8, 8); // ICON 2
		matrix.blit(loadTexture(), leftPos + 21, topPos + 211, 120, 240, 8, 8); // ICON 3
		matrix.blit(loadTexture(), leftPos + 29, topPos + 211, 120, 240, 8, 8); // ICON 3
		matrix.blit(loadTexture(), leftPos + 37, topPos + 211, 120, 240, 8, 8); // ICON 3
		matrix.blit(loadTexture(), leftPos + 21, topPos + 220, 152, 240, 8, 8); // ICON 4
		matrix.blit(loadTexture(), leftPos + 29, topPos + 220, 152, 240, 8, 8); // ICON 4
		matrix.blit(loadTexture(), leftPos + 37, topPos + 220, 152, 240, 8, 8); // ICON 4
		matrix.blit(loadTexture(), leftPos + 21, topPos + 229, 184, 240, 8, 8); // SEVEN
		matrix.blit(loadTexture(), leftPos + 29, topPos + 229, 184, 240, 8, 8); // SEVEN
		matrix.blit(loadTexture(), leftPos + 37, topPos + 229, 184, 240, 8, 8); // SEVEN
		
		// ----- Multiplier Lights ----- //
		int light = logic().turnstate <= 1 ? 0 : logic().lights[0];
		matrix.blit(loadTexture(), leftPos +  38, topPos +  73, 254, light == -1 ? 180 :       light*10, 2, 10); // Light 1 left
		matrix.blit(loadTexture(), leftPos + 219, topPos +  73, 254, light == -1 ? 180 :       light*10, 2, 10); // Light 1 right
		light = logic().turnstate <= 1 ? 0 : logic().lights[1];
		matrix.blit(loadTexture(), leftPos +  38, topPos +  46, 254, light == -1 ? 180 :  30 + light*10, 2, 10); // Light 2 left
		matrix.blit(loadTexture(), leftPos + 219, topPos +  46, 254, light == -1 ? 180 :  30 + light*10, 2, 10); // Light 2 right
		light = logic().turnstate <= 1 ? 0 : logic().lights[2];
		matrix.blit(loadTexture(), leftPos +  38, topPos + 100, 254, light == -1 ? 180 :  90 + light*10, 2, 10); // Light 3 left
		matrix.blit(loadTexture(), leftPos + 219, topPos + 100, 254, light == -1 ? 180 :  90 + light*10, 2, 10); // Light 3 right
		light = logic().turnstate <= 1 ? 0 : logic().lights[3];
		matrix.blit(loadTexture(), leftPos +  38, topPos +  19, 254, light == -1 ? 180 : 120 + light*10, 2, 10); // Light 4 left
		matrix.blit(loadTexture(), leftPos + 219, topPos + 127, 254, light == -1 ? 180 : 120 + light*10, 2, 10); // Light 4 right
		light = logic().turnstate <= 1 ? 0 : logic().lights[4];
		matrix.blit(loadTexture(), leftPos +  38, topPos + 127, 254, light == -1 ? 180 : 150 + light*10, 2, 10); // Light 5 left
		matrix.blit(loadTexture(), leftPos + 219, topPos +  19, 254, light == -1 ? 180 : 150 + light*10, 2, 10); // Light 5 right
		
		// ----- Wheel Lights ----- //
		light = logic().turnstate <= 1 ? 180 : (logic().turnstate == 3 && logic().wheelSTOP == 0) ? 216 : 204;
		matrix.blit(loadTexture(), leftPos +  52, topPos + 155, 240, light, 12, 2); // Light 1
		matrix.blit(loadTexture(), leftPos +  64, topPos + 155, 240, light, 16, 2); // Light 1
		matrix.blit(loadTexture(), leftPos +  80, topPos + 155, 240, light, 12, 2); // Light 1
		light = logic().turnstate <= 1 ? 180 : (logic().turnstate == 3 && logic().wheelSTOP == 1) ? 216 : 204;
		matrix.blit(loadTexture(), leftPos + 108, topPos + 155, 240, light, 12, 2); // Light 2
		matrix.blit(loadTexture(), leftPos + 120, topPos + 155, 240, light, 16, 2); // Light 2
		matrix.blit(loadTexture(), leftPos + 136, topPos + 155, 240, light, 12, 2); // Light 2
		light = logic().turnstate <= 1 ? 180 : (logic().turnstate == 3 && logic().wheelSTOP == 2) ? 216 : 204;
		matrix.blit(loadTexture(), leftPos + 164, topPos + 155, 240, light, 12, 2); // Light 3
		matrix.blit(loadTexture(), leftPos + 176, topPos + 155, 240, light, 16, 2); // Light 3
		matrix.blit(loadTexture(), leftPos + 192, topPos + 155, 240, light, 12, 2); // Light 3
		
		// ----- WHEEL ICONS ----- //
		drawIcon(matrix, leftPos +  52, topPos + 8, 0);
		drawIcon(matrix, leftPos + 108, topPos + 8, 1);
		drawIcon(matrix, leftPos + 164, topPos + 8, 2);
		
		// ----- HIGHLIGHT LINES ----- //
		if(logic().turnstate == 5){
			if(logic().lines[0]){
				matrix.blit(loadTexture(), leftPos +  48, topPos +  57,   0, 192, 48, 48); // Lines 1 left
				matrix.blit(loadTexture(), leftPos + 104, topPos +  57,   0, 192, 48, 48); // Lines 1 middle
				matrix.blit(loadTexture(), leftPos + 160, topPos +  57,   0, 192, 48, 48); // Lines 1 right
			} if(logic().lines[1]){
				matrix.blit(loadTexture(), leftPos +  48, topPos +   9,  48, 192, 48, 48); // Lines 2 left
				matrix.blit(loadTexture(), leftPos + 104, topPos +   9,  48, 192, 48, 48); // Lines 2 middle
				matrix.blit(loadTexture(), leftPos + 160, topPos +   9,  48, 192, 48, 48); // Lines 2 right
			} if(logic().lines[2]){
				matrix.blit(loadTexture(), leftPos +  48, topPos + 105,  96, 192, 48, 48); // Lines 3 left
				matrix.blit(loadTexture(), leftPos + 104, topPos + 105,  96, 192, 48, 48); // Lines 3 middle
				matrix.blit(loadTexture(), leftPos + 160, topPos + 105,  96, 192, 48, 48); // Lines 3 right
			} if(logic().lines[3]){
				matrix.blit(loadTexture(), leftPos +  48, topPos +  16, 144, 206, 48, 34); // Lines 4 left
				matrix.blit(loadTexture(), leftPos + 104, topPos +  56, 144, 192, 48, 48); // Lines 4 middle
				matrix.blit(loadTexture(), leftPos + 160, topPos + 109, 144, 192, 48, 35); // Lines 4 right
			} if(logic().lines[4]){
				matrix.blit(loadTexture(), leftPos +  48, topPos + 109, 192, 192, 48, 35); // Lines 5 left
				matrix.blit(loadTexture(), leftPos + 104, topPos +  56, 192, 192, 48, 48); // Lines 5 middle
				matrix.blit(loadTexture(), leftPos + 160, topPos +  16, 192, 206, 48, 34); // Lines 5 right
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private void drawIcon(GuiGraphics matrix, int posX, int posY, int index){
		for(int i = 0; i < 4; i++){
			int wheel  = (int)logic().wheelPos[index]/48;
			int offset = (int)logic().wheelPos[index]%48;
			
			int iconX = logic().grid[(wheel - 1 + i + 9) % 9][index];
			int iconY = (int)(logic().speed[index] / 5)*48;
			
			if(i == 0) matrix.blit(loadTexture(), posX, posY            + 8, iconX * 40, iconY + offset + 8, 40, 40       - offset );
			if(i == 1) matrix.blit(loadTexture(), posX, posY +  48 - offset, iconX * 40, iconY,              40, 48                );
			if(i == 2) matrix.blit(loadTexture(), posX, posY +  96 - offset, iconX * 40, iconY,              40, 48                );
			if(i == 3) matrix.blit(loadTexture(), posX, posY + 144 - offset, iconX * 40, iconY,              40, 40 - (48 - offset));
		}
	}
	
	private ResourceLocation loadTexture(){
		if(menu.getSettingAlternateColor() == 0) return Register.TEXTURE_SLOTGAME_0;
		if(menu.getSettingAlternateColor() == 1) return Register.TEXTURE_SLOTGAME_1;
		if(menu.getSettingAlternateColor() == 2) return Register.TEXTURE_SLOTGAME_2;
		if(menu.getSettingAlternateColor() == 3) return Register.TEXTURE_SLOTGAME_3;
		if(menu.getSettingAlternateColor() == 4) return Register.TEXTURE_SLOTGAME_4;
		if(menu.getSettingAlternateColor() == 5) return Register.TEXTURE_SLOTGAME_5;
		return Register.TEXTURE_SLOTGAME_5;
	}
	
	
	
}
