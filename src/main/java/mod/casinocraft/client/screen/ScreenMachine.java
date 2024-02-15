package mod.casinocraft.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import mod.casinocraft.Configuration;
import mod.casinocraft.Register;
import mod.casinocraft.client.menu.MenuMachine;
import mod.casinocraft.client.network.MessageSettingServer;
import mod.casinocraft.client.network.MessageStateServer;
import mod.casinocraft.common.item.ItemRulebook;
import mod.casinocraft.system.PacketHandler;
import mod.lucky77.screen.ScreenBase;
import mod.lucky77.util.button.ButtonSet;
import mod.lucky77.util.Vector2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;

import static mod.casinocraft.CasinoCraft.MODID;
import static mod.lucky77.util.KeyMap.*;

public class ScreenMachine extends ScreenBase<MenuMachine> {
	
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(MODID + ":" + "textures/gui/inventory.png");
	
	private int page           = 0;
	private int activeInput    = 0;
	private String input       = "";
	private boolean hasReset   = false;
	private int highlightIndex = 0;
	private int highlightTimer = 0;
	private int blinking       = 0;
	
	private Vector2 pos_button_left_1 = new Vector2(3, 4+2+3);
	private Vector2 pos_button_left_2 = new Vector2(3, 4+2+3+16);
	private Vector2 pos_button_left_3 = new Vector2(3, 4+2+3+16+16);
	private Vector2 pos_button_left_4 = new Vector2(3, 4+2+3+16+16+16);
	private Vector2 pos_button_left_5 = new Vector2(3, 4+2+3+16+16+16+16);
	
	private Vector2 pos_button_right_1 = new Vector2(157, 4+2+3);
	private Vector2 pos_button_right_2 = new Vector2(157, 4+2+3+16);
	private Vector2 pos_button_right_3 = new Vector2(157, 4+2+3+16+16);
	private Vector2 pos_button_right_4 = new Vector2(157, 4+2+3+16+16+16);
	private Vector2 pos_button_right_5 = new Vector2(157, 4+2+3+16+16+16+16);
	
	protected ButtonSet buttonSet = new ButtonSet();
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public ScreenMachine(MenuMachine container, Inventory player, Component name) {
		super(container, player, name, 176, 178);
		createButtons();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CREATE  ---------- ---------- ---------- ---------- //
	
	private void createButtons(){
		buttonSet.addButton(0, new Vector2(176 + 1 + 2 + 3, 2 - 2 + 4 + 5     ), new Vector2(208, 0      ), new Vector2(208, 16      ), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.hasKey() && this.page != 5                     , () -> this.commandSetPage(0)); // SYSTEM
		buttonSet.addButton(1, new Vector2(176 + 1 + 2 + 3, 2 - 1 + 3 + 5 + 16), new Vector2(208, 0 +  32), new Vector2(208, 16 +  32), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.hasKey() && this.page != 5 && this.hasModule(), () -> this.commandSetPage(1)); // COLORS
		buttonSet.addButton(2, new Vector2(176 + 1 + 2 + 3, 2 + 0 + 2 + 5 + 32), new Vector2(208, 0 +  64), new Vector2(208, 16 +  64), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.hasKey() && this.page != 5 && this.hasColoring(), () -> this.commandSetPage(2)); // TOKEN
		buttonSet.addButton(3, new Vector2(176 + 1 + 2 + 3, 2 + 1 + 1 + 5 + 48), new Vector2(208, 0 +  96), new Vector2(208, 16 +  96), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.hasKey() && this.page != 5 && this.hasHighscore(), () -> this.commandSetPage(3)); // REWARD
		buttonSet.addButton(4, new Vector2(176 + 1 + 2 + 3, 2 + 2 + 5 + 64    ), new Vector2(208, 0 + 128), new Vector2(208, 16 + 128), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.hasKey() && this.page != 5 && this.hasHighscore(), () -> this.commandSetPage(4)); // RULES
		// buttonSet.addButton(5, new Vector2(176 + 1 + 2 + 3, 2 + 5 + 64+16 ), new Vector2(208, 0 + 160), new Vector2(208, 16 + 160), new Vector2(160, 192), new Vector2(48, 16), -1, () -> this.hasKey() && this.page != 5 && this.hasHighscore(), () -> this.commandSetPage(5)); // RULES
		
		// SETTING
		buttonSet.addButton(6, pos_button_left_1, new Vector2(176, 112), new Vector2(192, 128), new Vector2(192, 0), new Vector2(16, 16), -1, () -> this.page == 0 && this.hasKey() && (Configuration.CASINO.config_creative_token.get() && isOP()), () -> this.commandToggleSettings(0)); // ???
		buttonSet.addButton(7, pos_button_left_2, new Vector2(176, 112), new Vector2(192, 128), new Vector2(192, 0), new Vector2(16, 16), -1, () -> this.page == 0 && this.hasKey() && (Configuration.CASINO.config_creative_reward.get() && isOP()), () -> this.commandToggleSettings(1)); // ???
		buttonSet.addButton(8, pos_button_left_3, new Vector2(176, 112), new Vector2(192, 128), new Vector2(192, 0), new Vector2(16, 16), -1, () -> this.page == 0 && this.hasKey(), () -> this.commandToggleSettings(2)); // ???
		buttonSet.addButton(9, pos_button_left_4, new Vector2(176, 112), new Vector2(192, 128), new Vector2(192, 0), new Vector2(16, 16), -1, () -> this.page == 0 && this.hasKey(), () -> this.commandToggleSettings(3)); // ???
		buttonSet.addButton(10, pos_button_left_5, new Vector2(176, 112), new Vector2(192, 128), new Vector2(192, 0), new Vector2(16, 16), -1, () -> this.page == 0 && this.hasKey() && this.hasHighscore(), () -> this.commandToggleSettings(4)); // ???
		
		// RULES LEFT
		buttonSet.addButton(11, pos_button_left_1, new Vector2(176, 64), new Vector2(192, 64), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(0) && this.getRuleCount(0) > 1, () -> this.commandChangeRule(0, -1)); // ???
		buttonSet.addButton(12, pos_button_left_2, new Vector2(176, 64), new Vector2(192, 64), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(1) && this.getRuleCount(1) > 1, () -> this.commandChangeRule(1, -1)); // ???
		buttonSet.addButton(13, pos_button_left_3, new Vector2(176, 64), new Vector2(192, 64), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(2) && this.getRuleCount(2) > 1, () -> this.commandChangeRule(2, -1)); // ???
		buttonSet.addButton(14, pos_button_left_4, new Vector2(176, 64), new Vector2(192, 64), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(3) && this.getRuleCount(3) > 1, () -> this.commandChangeRule(3, -1)); // ???
		buttonSet.addButton(15, pos_button_left_5, new Vector2(176, 64), new Vector2(192, 64), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(4) && this.getRuleCount(4) > 1, () -> this.commandChangeRule(4, -1)); // ???
		
		// RULES RIGHT
		buttonSet.addButton(16, pos_button_right_1, new Vector2(176, 80), new Vector2(192, 80), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(0) && this.getRuleCount(0) > 1, () -> this.commandChangeRule(0, +1)); // ???
		buttonSet.addButton(17, pos_button_right_2, new Vector2(176, 80), new Vector2(192, 80), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(1) && this.getRuleCount(1) > 1, () -> this.commandChangeRule(1, +1)); // ???
		buttonSet.addButton(18, pos_button_right_3, new Vector2(176, 80), new Vector2(192, 80), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(2) && this.getRuleCount(2) > 1, () -> this.commandChangeRule(2, +1)); // ???
		buttonSet.addButton(19, pos_button_right_4, new Vector2(176, 80), new Vector2(192, 80), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(3) && this.getRuleCount(3) > 1, () -> this.commandChangeRule(3, +1)); // ???
		buttonSet.addButton(20, pos_button_right_5, new Vector2(176, 80), new Vector2(192, 80), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 1 && this.hasRules(4) && this.getRuleCount(4) > 1, () -> this.commandChangeRule(4, +1)); // ???
		
		// COLORS
		buttonSet.addButton(21, pos_button_left_5,  new Vector2(176, 64), new Vector2(192, 64), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 2, () -> this.commandChangeColor(-1)); // ???
		buttonSet.addButton(22, pos_button_right_5, new Vector2(176, 80), new Vector2(192, 80), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 2, () -> this.commandChangeColor(+1)); // ???
		
		// TOKEN
		buttonSet.addButton(23, pos_button_left_1, new Vector2(208, 192), new Vector2(208, 208), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.page == 3 && this.canTakeIN(true), () -> this.commandTransfer(true, true)); // ???
		buttonSet.addButton(24, pos_button_left_2, new Vector2(208, 160), new Vector2(208, 176), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.page == 3 && this.canTakeOUT(true), () -> this.commandTransfer(true, false)); // ???
		
		buttonSet.addButton(25, new Vector2(pos_button_left_4.X  + 32, pos_button_left_4.Y), new Vector2(176, 48), new Vector2(192, 32), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 3,                       () -> this.commandChangeBet(-1, false)); // ???
		buttonSet.addButton(26, new Vector2(pos_button_right_4.X - 32, pos_button_left_4.Y), new Vector2(176, 32), new Vector2(192, 48), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 3,                       () -> this.commandChangeBet(+1, false)); // ???
		buttonSet.addButton(27, new Vector2(pos_button_left_5.X  + 32, pos_button_left_5.Y), new Vector2(176, 48), new Vector2(192, 32), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 3 && this.hasGambling(), () -> this.commandChangeBet(-1, true)); // ???
		buttonSet.addButton(28, new Vector2(pos_button_right_5.X - 32 ,pos_button_left_5.Y), new Vector2(176, 32), new Vector2(192, 48), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 3 && this.hasGambling(), () -> this.commandChangeBet(+1, true)); // ???
		
		// REWARD
		buttonSet.addButton(29, pos_button_left_1, new Vector2(208, 192), new Vector2(208, 208), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.page == 4 && this.canTakeIN(false), () -> this.commandTransfer(false, true)); // ???
		buttonSet.addButton(30, pos_button_left_2, new Vector2(208, 160), new Vector2(208, 176), new Vector2(208, 240), new Vector2(48, 16), -1, () -> this.page == 4 && this.canTakeOUT(false), () -> this.commandTransfer(false, false)); // ???
		
		buttonSet.addButton(31, new Vector2(pos_button_left_3.X + 4      , pos_button_left_3.Y), new Vector2(176, 160), new Vector2(192, 144), new Vector2(192, 0), new Vector2(16, 16), -1, () -> this.page == 4, () -> this.commandTogglePrize(0)); // ???
		buttonSet.addButton(32, new Vector2(pos_button_left_3.X + 4  + 16, pos_button_left_3.Y), new Vector2(176, 96), new Vector2(192, 96), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> { page = 5; input = ""; activeInput = 1; }); // ???
		buttonSet.addButton(33, new Vector2(pos_button_left_4.X + 4      , pos_button_left_4.Y), new Vector2(176, 160), new Vector2(192, 144), new Vector2(192, 0), new Vector2(16, 16), -1, () -> this.page == 4, () -> this.commandTogglePrize(1)); // ???
		buttonSet.addButton(34, new Vector2(pos_button_left_4.X + 4  + 16, pos_button_left_4.Y), new Vector2(176, 96), new Vector2(192, 96), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> { page = 5; input = ""; activeInput = 2; }); // ???
		buttonSet.addButton(35, new Vector2(pos_button_left_5.X + 4      , pos_button_left_5.Y), new Vector2(176, 160), new Vector2(192, 144), new Vector2(192, 0), new Vector2(16, 16), -1, () -> this.page == 4, () -> this.commandTogglePrize(2)); // ???
		buttonSet.addButton(36, new Vector2(pos_button_left_5.X + 4  + 16, pos_button_left_5.Y), new Vector2(176, 96), new Vector2(192, 96), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> { page = 5; input = ""; activeInput = 3; }); // ???
		
		buttonSet.addButton(37, new Vector2(pos_button_right_3.X - 4  - 16, pos_button_right_3.Y), new Vector2(176, 48), new Vector2(192, 32), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> this.commandChangeReward(-1, 0)); // ???
		buttonSet.addButton(38, new Vector2(pos_button_right_3.X - 4      , pos_button_right_3.Y), new Vector2(176, 32), new Vector2(192, 48), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> this.commandChangeReward(+1, 0)); // ???
		buttonSet.addButton(39, new Vector2(pos_button_right_4.X - 4  - 16, pos_button_right_4.Y), new Vector2(176, 48), new Vector2(192, 32), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> this.commandChangeReward(-1, 1)); // ???
		buttonSet.addButton(40, new Vector2(pos_button_right_4.X - 4      , pos_button_right_4.Y), new Vector2(176, 32), new Vector2(192, 48), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> this.commandChangeReward(+1, 1)); // ???
		buttonSet.addButton(41, new Vector2(pos_button_right_5.X - 4  - 16, pos_button_right_5.Y), new Vector2(176, 48), new Vector2(192, 32), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> this.commandChangeReward(-1, 2)); // ???
		buttonSet.addButton(42, new Vector2(pos_button_right_5.X - 4      , pos_button_right_5.Y), new Vector2(176, 32), new Vector2(192, 48), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 4, () -> this.commandChangeReward(+1, 2)); // ???
		
		// HIGHSCORE
		// buttonSet.addButton(43, pos_button_left_5,  new Vector2(176, 16), new Vector2(192, 16), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 5, () -> this.commandToggleSettings(0)); // ???
		// buttonSet.addButton(44, pos_button_right_5, new Vector2(176, 16), new Vector2(192, 16), new Vector2(192, 0), new Vector2(16, 16), 10, () -> this.page == 5, () -> this.commandToggleSettings(0)); // ???
		
		//--
		buttonSet.setToggle( 0, true);
		buttonSet.setToggle( 6, menu.getSettingInfiniteToken());
		buttonSet.setToggle( 7, menu.getSettingInfinitePrize());
		buttonSet.setToggle( 8, menu.getSettingDropOnBreak());
		buttonSet.setToggle( 9, menu.getSettingIndestructable());
		buttonSet.setToggle(10, menu.getSettingAlternateScore());
		buttonSet.setToggle(31, menu.getPrizeMode1());
		buttonSet.setToggle(33, menu.getPrizeMode2());
		buttonSet.setToggle(35, menu.getPrizeMode3());
		
		// buttonSet.addButton(POS_TOP_MIDDLE,        SCORE,                                               () -> logic().turnstate == 0 && logic().hasHighscore(),                         this::commandReset);    // Show Highscore
		// buttonSet.addButton(POS_MID_MIDDLE,        FINISH,                                              () -> logic().turnstate == 5,                                                   this::commandReset);    // Show Highscore OR return to START
		// buttonSet.addButton(POS_MID_MIDDLE,        BACK,                                                () -> logic().turnstate == 7,                                                   this::commandReset);    // Return to START
		// buttonSet.addButton(POS_MID_MIDDLE,        START,                                               () -> logic().turnstate == 0 && hasBet(),                                       this::commandStart);    // Start MiniGame
		// buttonSet.addButton(new Vector2( 56, 212), ARROW_DOWN_OFF, ARROW_DOWN_ON, 1, 32,                () -> logic().turnstate == 0 && menu.hasToken() && bet > menu.getBettingLow(),  this::commandBetDown);  // Lower Bet
		// buttonSet.addButton(new Vector2(174, 212), ARROW_UP_OFF,   ARROW_UP_ON,   1, 32,                () -> logic().turnstate == 0 && menu.hasToken() && bet < menu.getBettingHigh(), this::commandBetUp);    // Raise Bet
		// buttonSet.addButton(POS_MID_MIDDLE,        JOIN,                                                () -> logic().turnstate == 2 &&  logic().isMultiplayer() && !isCurrentPlayer(), this::commandJoinGame); // Join Multiplayer game
		// buttonSet.addButton(new Vector2(tableID == 1 ? 240 : 336, 240), FORFEIT_OFF, FORFEIT_ON, 0, 96, () -> logic().turnstate == 2 && !logic().isMultiplayer(),                       this::commandForfeit);  // Forfeit
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	/** Overwritten keyPressed function (only used for rerouting keyCode) */
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
		keyTyped(keyCode);
		if (super.keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		} else if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
			this.onClose();
			return true;
		} else {
			boolean handled = this.checkHotbarKeyPressed(keyCode, scanCode);// Forge MC-146650: Needs to return true when the key is handled
			if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
				if (this.minecraft.options.keyPickItem.isActiveAndMatches(mouseKey)) {
					this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 0, ClickType.CLONE);
					handled = true;
				} else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
					this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
					handled = true;
				}
			} else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
				handled = true; // Forge MC-146650: Emulate MC bug, so we don't drop from hotbar when pressing drop without hovering over a item.
			}
			
			return handled;
		}
	}
	
	/** Fired when a key is pressed */
	private void keyTyped(int keyCode){
		
		if(activeInput > 0){
			if(keyCode == KEY_ENTER){ // FINISH AND SEND INPUT STRING
				if(input.length() == 0){
					input = "0";
				}
				int temp = Integer.parseInt(input);
				commandSetScore(activeInput - 1, temp);
				activeInput = 0;
				page = 4;
			}
			if(keyCode == KEY_BACK){ // DELETE ONE DIGIT
				if(input.length() == 1){
					input = "";
				} else if(input.length() > 1){
					input = input.substring(0, input.length() - 1);
				}
			}
			if(keyCode == KEY_1_IN){ input = input + "1"; }
			if(keyCode == KEY_2_IN){ input = input + "2"; }
			if(keyCode == KEY_3_IN){ input = input + "3"; }
			if(keyCode == KEY_4_IN){ input = input + "4"; }
			if(keyCode == KEY_5_IN){ input = input + "5"; }
			if(keyCode == KEY_6_IN){ input = input + "6"; }
			if(keyCode == KEY_7_IN){ input = input + "7"; }
			if(keyCode == KEY_8_IN){ input = input + "8"; }
			if(keyCode == KEY_9_IN){ input = input + "9"; }
			if(keyCode == KEY_0_IN){ input = input + "0"; }
		}
	}
	
	/** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		buttonSet.interact(leftPos, topPos, mouseX, mouseY);
		
		// ----- STUFF ----- //
		// ...
		
		// - Return - //
		return false;
		
		// if (mouseButton == 0 && hasKey() && activeInput == 0){
		//
		// 	// ----- Page Header ----- //
		// 	if(mouseRect(  2, -19, 56, 24, mouseX, mouseY)) {                     highlight(18); commandChangePage(0);   }
		// 	if(mouseRect( 58, -19, 56, 24, mouseX, mouseY)) { if(menu.hasKey() ){ highlight(19); commandChangePage(1); } }
		// 	if(mouseRect(114, -19, 56, 24, mouseX, mouseY)) { if(hasHighscore()){ highlight(20); commandChangePage(2); } }
		//
		// 	// ----- Page 1 - Settings ----- //
		// 	if(page == 0){
		// 		if(mouseRect(4, 10, 12, 12, mouseX, mouseY)) { commandToggleSettings(0); } // SETTING 1
		// 		if(mouseRect(4, 23, 12, 12, mouseX, mouseY)) { commandToggleSettings(1); } // SETTING 2
		// 		if(mouseRect(4, 36, 12, 12, mouseX, mouseY)) { commandToggleSettings(2); } // SETTING 3
		// 		if(mouseRect(4, 49, 12, 12, mouseX, mouseY)) { commandToggleSettings(3); } // SETTING 4
		// 		if(mouseRect(4, 63, 12, 12, mouseX, mouseY)) { commandResetGameState( ); } // SETTING RESET
		// 		if(mouseRect(4, 76, 12, 12, mouseX, mouseY)) { commandToggleSettings(4); } // SETTING COLOR
		// 	}
		//
		// 	// ----- Page 2 - Token ----- //
		// 	if(  page == 1){
		// 		if(mouseRect(  5, 11, 40, 20, mouseX, mouseY)) { highlight(14); commandTransfer(true, true ); } // TRANSFER IN
		// 		if(mouseRect(122, 11, 40, 20, mouseX, mouseY)) { highlight(15); commandTransfer(true, false); } // TRANSFER OUT
		// 	} if(page == 1 && menu.hasToken()){
		// 		if(mouseRect(137, 53, 16, 16, mouseX, mouseY)) { highlight( 1); commandChangeBet(-1, false); } // MINIMUM LOWER
		// 		if(mouseRect(155, 53, 16, 16, mouseX, mouseY)) { highlight( 2); commandChangeBet( 1, false); } // MINIMUM UPPER
		// 		if(mouseRect(137, 71, 16, 16, mouseX, mouseY)) { highlight( 3); commandChangeBet(-1, true ); } // MAXIMUM LOWER
		// 		if(mouseRect(155, 71, 16, 16, mouseX, mouseY)) { highlight( 4); commandChangeBet( 1, true ); } // MAXIMUM UPPER
		// 	}
		//
		// 	// ----- Page 3 - Reward ----- //
		// 	if(  page == 2){
		// 		if(mouseRect(  5, 11, 40, 20, mouseX, mouseY)) { highlight(14); commandTransfer(false,  true); } // TRANSFER IN
		// 		if(mouseRect(122, 11, 40, 20, mouseX, mouseY)) { highlight(15); commandTransfer(false, false); } // TRANSFER OUT
		// 	} if(page == 2 && menu.hasReward()){
		// 		if(mouseRect(137, 35, 16, 16, mouseX, mouseY)) { highlight( 5); commandChangeReward(-1, 0); } // REWARD 1 LOWER
		// 		if(mouseRect(155, 35, 16, 16, mouseX, mouseY)) { highlight( 6); commandChangeReward( 1, 0); } // REWARD 1 UPPER
		// 		if(mouseRect(137, 53, 16, 16, mouseX, mouseY)) { highlight( 7); commandChangeReward(-1, 1); } // REWARD 2 LOWER
		// 		if(mouseRect(155, 53, 16, 16, mouseX, mouseY)) { highlight( 8); commandChangeReward( 1, 1); } // REWARD 2 UPPER
		// 		if(mouseRect(137, 71, 16, 16, mouseX, mouseY)) { highlight( 9); commandChangeReward(-1, 2); } // REWARD 3 LOWER
		// 		if(mouseRect(155, 71, 16, 16, mouseX, mouseY)) { highlight(10); commandChangeReward( 1, 2); } // REWARD 3 UPPER
		// 		if(mouseRect( 23, 35, 16, 16, mouseX, mouseY)) { page = 3; input = ""; activeInput = 1;     } // SCORE 1 EDIT
		// 		if(mouseRect( 23, 53, 16, 16, mouseX, mouseY)) { page = 3; input = ""; activeInput = 2;     } // SCORE 2 EDIT
		// 		if(mouseRect( 23, 71, 16, 16, mouseX, mouseY)) { page = 3; input = ""; activeInput = 3;     } // SCORE 3 EDIT
		// 		if(mouseRect(  5, 35, 16, 16, mouseX, mouseY)) { commandTogglePrize(0);                     } // PRIZE 1 TOGGLE
		// 		if(mouseRect(  5, 53, 16, 16, mouseX, mouseY)) { commandTogglePrize(1);                     } // PRIZE 2 TOGGLE
		// 		if(mouseRect(  5, 71, 16, 16, mouseX, mouseY)) { commandTogglePrize(2);                     } // PRIZE 3 TOGGLE
		// 	}
		// } return false;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER - OVERLAY  ---------- ---------- ---------- ---------- //
	
	/** Draw the foreground layer for the GuiContainer (everything in front of the items) */
	protected void renderLabels(GuiGraphics matrix, int mouseX, int mouseY){
		if(page == 5 && (!menu.hasKey() || !hasHighscore()))   page = 0;
		if(page >= 1 && (!menu.hasKey() || !menu.hasModule())) page = 0;
		
		if(!hasKey()){
			drawFontCenter(matrix, "NO KEY FOUND", 176/2, 8-1+6+16);
		}
		
		if(page == 0 && this.hasKey()){
			drawFont(matrix, I18n.get("setting_0"), 27-2-4, 8-1+6);
			drawFont(matrix, I18n.get("setting_1"), 27-2-4, 8-1+6+16);
			drawFont(matrix, I18n.get("setting_2"), 27-2-4, 8-1+6+16+16);
			drawFont(matrix, I18n.get("setting_3"), 27-2-4, 8-1+6+16+16+16);
			drawFont(matrix, I18n.get("setting_4"), 27-2-4, 8-1+6+16+16+16+16);
		}
		if(page == 1){
			if(menu.tableID == 3){
				drawFontCenter(matrix, "SLOT MACHINE", 176/2, 8-1+6);
			} else if(!hasRules(0)){
				drawFontCenter(matrix, "INCOMPATIBLE RULESET", 176/2, 8-1+6);
			} else {
				if(hasRules(0)) drawFontCenter(matrix, I18n.get("rulename_" + getModuleID() + "_0_" + getCurrentRule(0)), 176/2, 8-1+6);
				if(hasRules(1)) drawFontCenter(matrix, I18n.get("rulename_" + getModuleID() + "_1_" + getCurrentRule(1)), 176/2, 8-1+6+16);
				if(hasRules(2)) drawFontCenter(matrix, I18n.get("rulename_" + getModuleID() + "_2_" + getCurrentRule(2)), 176/2, 8-1+6+16+16);
				if(hasRules(3)) drawFontCenter(matrix, I18n.get("rulename_" + getModuleID() + "_3_" + getCurrentRule(3)), 176/2, 8-1+6+16+16+16);
				if(hasRules(4)) drawFontCenter(matrix, I18n.get("rulename_" + getModuleID() + "_4_" + getCurrentRule(4)), 176/2, 8-1+6+16+16+16+16);
			}
		}
		if(page == 2){
			if(menu.getSettingAlternateColor() == 0) drawFontCenter(matrix, I18n.get("rule_color_0"), 176/2, 8-1+6+16+16+16+16);
			if(menu.getSettingAlternateColor() == 1) drawFontCenter(matrix, I18n.get("rule_color_1"), 176/2, 8-1+6+16+16+16+16);
			if(menu.getSettingAlternateColor() == 2) drawFontCenter(matrix, I18n.get("rule_color_2"), 176/2, 8-1+6+16+16+16+16);
			if(menu.getSettingAlternateColor() == 3) drawFontCenter(matrix, I18n.get("rule_color_3"), 176/2, 8-1+6+16+16+16+16);
			if(menu.getSettingAlternateColor() == 4) drawFontCenter(matrix, I18n.get("rule_color_4"), 176/2, 8-1+6+16+16+16+16);
			if(menu.getSettingAlternateColor() == 5) drawFontCenter(matrix, I18n.get("rule_color_5"), 176/2, 8-1+6+16+16+16+16);
		}
		if(page == 3){
			
			// 	String text = menu.getStorageToken() == 0 ? "EMPTY" : menu.getSettingInfiniteToken() ? "x(INFINITE)" : "x " + menu.getStorageToken();
			// 	matrix.renderFakeItem(menu.getItemToken(),  51, 13         );
			// 	matrix.drawString(font, text,                          69, 17, 16777215);
			
			// matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X + 64 + 4, posY + pos_button_left_1.Y - 6 + 6, 1, 179, 78, 20);
			String text = menu.getStorageToken() == 0 ? "EMPTY" : menu.getSettingInfiniteToken() ? "x(INFINITE)" : "x " + menu.getStorageToken();
			matrix.renderFakeItem(menu.getItemToken(),  3+64+4+4 -2, 4+2+3+2+4 +2       );
			drawFontInvert(matrix, text, 3+64+4+4+78-4 -4+2, 4+2+3+2 +4  +4+2+4 );
			
			drawFontCenter(matrix, hasGambling() ? "Betting Range" : "Price to Play", 176/2, 8-1+6+16+16+2);
			
			drawFont(matrix, hasGambling() ? "MIN:" : "", 176/2 - 19, 8-1+6+16+16+16);
			drawFontInvert(matrix, hasGambling() ? "" + menu.getBettingLow() : menu.getBettingLow() == 0 ? "FREE" : "" + menu.getBettingLow(), 176/2 + 19, 8-1+6+16+16+16);
			
			if(hasGambling()) drawFont(matrix, "MAX:", 176/2 - 19, 8-1+6+16+16+16+16);
			if(hasGambling()) drawFontInvert(matrix, "" + menu.getBettingHigh(), 176/2 + 19, 8-1+6+16+16+16+16);
			
			// // ----- Page 2 - Token ----- //
			// if(  page == 1){
			// 	String text = menu.getStorageToken() == 0 ? "EMPTY" : menu.getSettingInfiniteToken() ? "x(INFINITE)" : "x " + menu.getStorageToken();
			// 	matrix.renderFakeItem(menu.getItemToken(),  51, 13         );
			// 	matrix.drawString(font, text,                          69, 17, 16777215);
			// } if(page == 1 && menu.hasToken() && !hasGambling()){
			// 	drawFontInvert(matrix, "AMOUNT",                     102, 57         );
			// 	matrix.drawString(font, "" + menu.getBettingLow(),    108, 57, 16777215);
			// } if(page == 1 && menu.hasToken() && hasGambling()){
			// 	drawFontInvert(matrix, "MINIMUM",                    102, 57         );
			// 	drawFontInvert(matrix, "MAXIMUM",                    102, 75         );
			// 	matrix.drawString(font, "" + menu.getBettingLow(),    108, 57, 16777215);
			// 	matrix.drawString(font, "" + menu.getBettingHigh(),   108, 75, 16777215);
			// }
			
		}
		if(page == 4){
			
			String text = menu.getStorageToken() == 0 ? "EMPTY" : menu.getSettingInfinitePrize() ? "x(INFINITE)" : "x " + menu.getStoragePrize();
			matrix.renderFakeItem(menu.getItemPrize(),  3+64+4+4 -2, 4+2+3+2+4 +2       );
			drawFontInvert(matrix, text, 3+64+4+4+78-4 -4+2, 4+2+3+2 +4  +4+2+4 );
			
			drawFont(matrix, "" + menu.getPrizeScore1(), 176/2 - 40, 8-1+6+16+16);
			drawFont(matrix, "" + menu.getPrizeScore2(), 176/2 - 40, 8-1+6+16+16+16);
			drawFont(matrix, "" + menu.getPrizeScore3(), 176/2 - 40, 8-1+6+16+16+16+16);
			
			drawFontInvert(matrix, "" + menu.getPrizeCount1(), 176/2 + 40, 8-1+6+16+16);
			drawFontInvert(matrix, "" + menu.getPrizeCount2(), 176/2 + 40, 8-1+6+16+16+16);
			drawFontInvert(matrix, "" + menu.getPrizeCount3(), 176/2 + 40, 8-1+6+16+16+16+16);
			
			// // ----- Page 3 - Reward ----- //
			// if(  page == 2){
			// 	String text = menu.getStoragePrize() == 0 ? "EMPTY" : menu.getSettingInfinitePrize() ? "x(INFINITE)" : "x " + menu.getStoragePrize();
			// 	matrix.renderFakeItem(menu.getItemPrize(), 51, 13         );
			// 	matrix.drawString(font, text,                         69, 17, 16777215);
			// } if(page == 2 && menu.hasReward()){
			// 	matrix.drawString(font, "" + menu.getPrizeScore1(),   52, 39, 16777215);
			// 	matrix.drawString(font, "" + menu.getPrizeScore2(),   52, 57, 16777215);
			// 	matrix.drawString(font, "" + menu.getPrizeScore3(),   52, 75, 16777215);
			// 	matrix.drawString(font, "" + menu.getPrizeCount1(),  108, 39, 16777215);
			// 	matrix.drawString(font, "" + menu.getPrizeCount2(),  108, 57, 16777215);
			// 	matrix.drawString(font, "" + menu.getPrizeCount3(),  108, 75, 16777215);
			// }
			
		}
		if(page == 5){
			String text = input.length() == 0 ? "0" : input;
			drawFontCenter(matrix, "INPUT NUMBER", 176/2, 8-1+6+16);
			drawFontInvert(matrix, text, 176/2 + 19, 8-1+6+16+16);
			drawFontCenter(matrix, "Press ENTER to Submit", 176/2, 8-1+6+16+16+16);
			
			
			
			// int w = this.font.width(text) / 2;
			// drawFontCenter(matrix, "INPUT NUMBER",          88,   16         );
			// drawFontCenter(matrix, "PRESS ENTER TO SUBMIT", 88,   32         );
			// matrix.drawString(font,  text,                   88-w, 71, 16777215);
			
			// drawFont(matrix, "1.", 6, 8-2+6); drawFontInvert(matrix, "1000", 44, 8-2+6);
			// drawFont(matrix, "2.", 6, 8-2+6+12); drawFontInvert(matrix, "750", 44, 8-2+6+12);
			// drawFont(matrix, "3.", 6, 8-2+6+12+12); drawFontInvert(matrix, "500", 44, 8-2+6+12+12);
			// drawFont(matrix, "4.", 6, 8-2+6+12+12+12); drawFontInvert(matrix, "250", 44, 8-2+6+12+12+12);
			// drawFont(matrix, "5.", 6, 8-2+6+12+12+12+12); drawFontInvert(matrix, "0", 44, 8-2+6+12+12+12+12);
		}
		
		
		// if(page == 2 && (!menu.hasKey() || !hasHighscore()))   page = 0;
		// if(page == 1 && (!menu.hasKey() || !menu.hasModule())) page = 0;
		// blinking = (blinking + 1) % 32;
		//
		// // ----- Page 1 - Settings ----- //
		// if(  page == 0 && !menu.hasKey()){
		// 	drawFontCenter(matrix, "VISIT MODPAGE FOR INSTRUCTIONS", 88, 16);
		// 	if(blinking / 16 == 1) drawFontCenter(matrix, "MISSING  KEY  ITEM  ",           88, 74);
		// } if(page == 0 && menu.hasKey() && Configuration.CASINO.config_creative_token.get() && isOP()){
		// 	drawFont(matrix,       "INFINITE TOKEN STORAGE",         18, 13);
		// } if(page == 0 && menu.hasKey() && Configuration.CASINO.config_creative_reward.get() && isOP()){
		// 	drawFont(matrix,       "INFINITE REWARD STORAGE",        18, 26);
		// } if(page == 0 && menu.hasKey()){
		// 	drawFont(matrix,       "DROP ITEMS ON BREAK",            18, 39);
		// 	drawFont(matrix,       "INDESTRUCTABLE BLOCK",           18, 52);
		// } if(page == 0 && menu.hasKey() && !menu.hasModule()){
		// 	if(blinking / 16 == 1) drawFontCenter(matrix, "MISSING  MODULE  ITEM  ",        88, 74);
		// } if(page == 0 && menu.hasKey() && menu.hasModule()){
		// 	drawFont(matrix,       "RESET GAME",                     18, 66);
		// 	drawFont(matrix,       "ALTERNATE COLOR VARIANT",        18, 79);
		// }
		//
		// // ----- Page 2 - Token ----- //
		// if(  page == 1){
		// 	String text = menu.getStorageToken() == 0 ? "EMPTY" : menu.getSettingInfiniteToken() ? "x(INFINITE)" : "x " + menu.getStorageToken();
		// 	matrix.renderFakeItem(menu.getItemToken(),  51, 13         );
		// 	matrix.drawString(font, text,                          69, 17, 16777215);
		// } if(page == 1 && menu.hasToken() && !hasGambling()){
		// 	drawFontInvert(matrix, "AMOUNT",                     102, 57         );
		// 	matrix.drawString(font, "" + menu.getBettingLow(),    108, 57, 16777215);
		// } if(page == 1 && menu.hasToken() && hasGambling()){
		// 	drawFontInvert(matrix, "MINIMUM",                    102, 57         );
		// 	drawFontInvert(matrix, "MAXIMUM",                    102, 75         );
		// 	matrix.drawString(font, "" + menu.getBettingLow(),    108, 57, 16777215);
		// 	matrix.drawString(font, "" + menu.getBettingHigh(),   108, 75, 16777215);
		// }
		//
		// // ----- Page 3 - Reward ----- //
		// if(  page == 2){
		// 	String text = menu.getStoragePrize() == 0 ? "EMPTY" : menu.getSettingInfinitePrize() ? "x(INFINITE)" : "x " + menu.getStoragePrize();
		// 	matrix.renderFakeItem(menu.getItemPrize(), 51, 13         );
		// 	matrix.drawString(font, text,                         69, 17, 16777215);
		// } if(page == 2 && menu.hasReward()){
		// 	matrix.drawString(font, "" + menu.getPrizeScore1(),   52, 39, 16777215);
		// 	matrix.drawString(font, "" + menu.getPrizeScore2(),   52, 57, 16777215);
		// 	matrix.drawString(font, "" + menu.getPrizeScore3(),   52, 75, 16777215);
		// 	matrix.drawString(font, "" + menu.getPrizeCount1(),  108, 39, 16777215);
		// 	matrix.drawString(font, "" + menu.getPrizeCount2(),  108, 57, 16777215);
		// 	matrix.drawString(font, "" + menu.getPrizeCount3(),  108, 75, 16777215);
		// }
		//
		// // ----- Page 4 - Input ----- //
		// if(  page == 3){
		// 	String text = input.length() == 0 ? "0" : input;
		// 	int w = this.font.width(text) / 2;
		// 	drawFontCenter(matrix, "INPUT NUMBER",          88,   16         );
		// 	drawFontCenter(matrix, "PRESS ENTER TO SUBMIT", 88,   32         );
		// 	matrix.drawString(font,  text,                   88-w, 71, 16777215);
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER - BACKGROUND  ---------- ---------- ---------- ---------- //
	
	/** Draws the background layer of this container (behind the items). */
	protected void renderBg(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
		buttonSet.update(leftPos, topPos, mouseX, mouseY);
		
		// RenderSystem.setShaderTexture(0, GUI_TEXTURE);
		int posX = (this.width  - this.imageWidth      ) / 2;
		int posY = (this.height - this.imageHeight + 12) / 2;
		
		// ----- Background ----- //
		matrix.blit(GUI_TEXTURE, posX,       posY,        0,   0, 176, 86); // Background 1
		matrix.blit(GUI_TEXTURE, posX,       posY+86+2+2,        0,   86+2, 176, 90); // Background 2
		matrix.blit(GUI_TEXTURE, posX -  56, posY - 0,        0, 200,  54,  28); // Slot 1
		matrix.blit(GUI_TEXTURE, posX -  56, posY + 29,  54, this.hasKey() ? 228 : 200,  54,  28); // Slot 2
		matrix.blit(GUI_TEXTURE, posX -  56, posY + 58 + 0,  this.hasModule() ? 0 : 54, 200,  54,  28); // Slot 3
		
		matrix.blit(GUI_TEXTURE, posX + 176 + 2+1,       posY, 0,   0, 27-1, 86+1); // Background - Pages
		matrix.blit(GUI_TEXTURE, posX + 176 + 2+1 + 27-1,  posY, 149-1, 0, 27-1, 86+1); // Background - Pages
		
		matrix.blit(GUI_TEXTURE, posX + 176 + 1 + 2 + 3, posY - 2 + 2 + 3, 160,   176, 48, 16); // Page Button 1
		matrix.blit(GUI_TEXTURE, posX + 176 + 1 + 2 + 3, posY - 1 + 1 + 3 + 16, 160,   192, 48, 16); // Page Button 2
		matrix.blit(GUI_TEXTURE, posX + 176 + 1 + 2 + 3, posY + 0 + 3 + 16 + 16, 160,   208, 48, 16); // Page Button 3
		matrix.blit(GUI_TEXTURE, posX + 176 + 1 + 2 + 3, posY + 1 - 1 + 3 + 16 + 16 + 16, 160,   224, 48, 16); // Page Button 4
		matrix.blit(GUI_TEXTURE, posX + 176 + 1 + 2 + 3, posY + 2 - 2 + 3 + 16 + 16 + 16 + 16, 160,   240, 48, 16); // Page Button 5
		
		if(this.hasKey()){
			if(page == 0){
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X, posY + pos_button_left_1.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_2.X, posY + pos_button_left_2.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_3.X, posY + pos_button_left_3.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_4.X, posY + pos_button_left_4.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_5.X, posY + pos_button_left_5.Y - 6, 176, 0, 16, 16);
			}
			if(page == 1){
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X, posY + pos_button_left_1.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_2.X, posY + pos_button_left_2.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_3.X, posY + pos_button_left_3.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_4.X, posY + pos_button_left_4.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_5.X, posY + pos_button_left_5.Y - 6, 176, 0, 16, 16);
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_right_1.X, posY + pos_button_right_1.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_right_2.X, posY + pos_button_right_2.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_right_3.X, posY + pos_button_right_3.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_right_4.X, posY + pos_button_right_4.Y - 6, 176, 0, 16, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_right_5.X, posY + pos_button_right_5.Y - 6, 176, 0, 16, 16);
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X + 19 + 6     , posY + pos_button_left_1.Y - 6 + 2, 110, 229, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X + 19 + 6 + 40, posY + pos_button_left_1.Y - 6 + 2, 111, 229, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X + 19 + 6 + 80, posY + pos_button_left_1.Y - 6 + 2, 112, 229, 40, 12);
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_2.X + 19 + 6     , posY + pos_button_left_2.Y - 6 + 2, 110, this.hasRules(1) ? 243 : 215, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_2.X + 19 + 6 + 40, posY + pos_button_left_2.Y - 6 + 2, 111, this.hasRules(1) ? 243 : 215, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_2.X + 19 + 6 + 80, posY + pos_button_left_2.Y - 6 + 2, 112, this.hasRules(1) ? 243 : 215, 40, 12);
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_3.X + 19 + 6     , posY + pos_button_left_3.Y - 6 + 2, 110, this.hasRules(2) ? 243 : 215, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_3.X + 19 + 6 + 40, posY + pos_button_left_3.Y - 6 + 2, 111, this.hasRules(2) ? 243 : 215, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_3.X + 19 + 6 + 80, posY + pos_button_left_3.Y - 6 + 2, 112, this.hasRules(2) ? 243 : 215, 40, 12);
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_4.X + 19 + 6     , posY + pos_button_left_4.Y - 6 + 2, 110, this.hasRules(3) ? 243 : 215, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_4.X + 19 + 6 + 40, posY + pos_button_left_4.Y - 6 + 2, 111, this.hasRules(3) ? 243 : 215, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_4.X + 19 + 6 + 80, posY + pos_button_left_4.Y - 6 + 2, 112, this.hasRules(3) ? 243 : 215, 40, 12);
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_5.X + 19 + 6     , posY + pos_button_left_5.Y - 6 + 2, 110, this.hasRules(4) ? 243 : 215, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_5.X + 19 + 6 + 40, posY + pos_button_left_5.Y - 6 + 2, 111, this.hasRules(4) ? 243 : 215, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_5.X + 19 + 6 + 80, posY + pos_button_left_5.Y - 6 + 2, 112, this.hasRules(4) ? 243 : 215, 40, 12);
			}
			if(page == 2){
				
				if(menu.tableID < 3 && getColoringType() == 0){ // Cards
					if(menu.getSettingAlternateColor() == 0){
						matrix.blit(Register.TEXTURE_CARDS_0_ROUGE, leftPos+8 + 112, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_0_NOIR,  leftPos+8 + 88, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_0_ROUGE, leftPos+8 + 64, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_0_NOIR,  leftPos+8 + 40, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_0_NOIR, leftPos +8+ 16, topPos + 16, 0 * 32, 4 * 48, 32, 48);
					}
					if(menu.getSettingAlternateColor() == 1){
						matrix.blit(Register.TEXTURE_CARDS_1_ROUGE, leftPos+8 + 112, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_1_NOIR,  leftPos+8 + 88, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_1_ROUGE, leftPos+8 + 64, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_1_NOIR,  leftPos+8 + 40, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_1_NOIR, leftPos +8+ 16, topPos + 16, 0 * 32, 4 * 48, 32, 48);
					}
					if(menu.getSettingAlternateColor() == 2){
						matrix.blit(Register.TEXTURE_CARDS_2_ROUGE, leftPos+8 + 112, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_2_NOIR,  leftPos+8 + 88, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_2_ROUGE, leftPos+8 + 64, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_2_NOIR,  leftPos+8 + 40, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_2_NOIR, leftPos +8+ 16, topPos + 16, 0 * 32, 4 * 48, 32, 48);
					}
					if(menu.getSettingAlternateColor() == 3){
						matrix.blit(Register.TEXTURE_CARDS_3_ROUGE, leftPos+8 + 112, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_3_NOIR,  leftPos+8 + 88, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_3_ROUGE, leftPos+8 + 64, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_3_NOIR,  leftPos+8 + 40, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_3_NOIR, leftPos +8+ 16, topPos + 16, 0 * 32, 4 * 48, 32, 48);
					}
					if(menu.getSettingAlternateColor() == 4){
						matrix.blit(Register.TEXTURE_CARDS_4_ROUGE, leftPos+8 + 112, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_4_NOIR,  leftPos+8 + 88, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_4_ROUGE, leftPos+8 + 64, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_4_NOIR,  leftPos+8 + 40, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_4_NOIR, leftPos +8+ 16, topPos + 16, 0 * 32, 4 * 48, 32, 48);
					}
					if(menu.getSettingAlternateColor() == 5){
						matrix.blit(Register.TEXTURE_CARDS_5_ROUGE, leftPos+8 + 112, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_5_NOIR,  leftPos+8 + 88, topPos + 16, 4 * 32, 1 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_5_ROUGE, leftPos+8 + 64, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_5_NOIR,  leftPos+8 + 40, topPos + 16, 4 * 32, 3 * 48, 32, 48);
						matrix.blit(Register.TEXTURE_CARDS_5_NOIR, leftPos +8+ 16, topPos + 16, 0 * 32, 4 * 48, 32, 48);
					}
					
					
					
					
				}
				if(menu.tableID < 3 && getColoringType() == 1){ // Dice
					matrix.blit(Register.TEXTURE_DICE, leftPos + 176/2 - 16 - 32 - 2, topPos + 8+2, 0 * 32, (menu.getSettingAlternateColor() + 1) * 32, 32, 32);
					matrix.blit(Register.TEXTURE_DICE, leftPos + 176/2 - 16, topPos + 8+2, 1 * 32, (menu.getSettingAlternateColor() + 1) * 32, 32, 32);
					matrix.blit(Register.TEXTURE_DICE, leftPos + 176/2 - 16 + 32 + 2, topPos + 8+2, 2 * 32, (menu.getSettingAlternateColor() + 1) * 32, 32, 32);
					matrix.blit(Register.TEXTURE_DICE, leftPos + 176/2 - 16 - 32 - 2, topPos + 48-4-2, 3 * 32, (menu.getSettingAlternateColor() + 1) * 32, 32, 32);
					matrix.blit(Register.TEXTURE_DICE, leftPos + 176/2 - 16, topPos + 48-4-2, 4 * 32, (menu.getSettingAlternateColor() + 1) * 32, 32, 32);
					matrix.blit(Register.TEXTURE_DICE, leftPos + 176/2 - 16 + 32 + 2, topPos + 48-4-2, 5 * 32, (menu.getSettingAlternateColor() + 1) * 32, 32, 32);
				}
				if(menu.tableID < 3 && getColoringType() == 2){ // Arcade
					
					if(menu.getSettingAlternateColor() == 0){
						matrix.blit(Register.TEXTURE_PARALLAX_0_LOW, leftPos + 18, topPos + 16-6, 58, 93, 140, 70-6);
					}
					if(menu.getSettingAlternateColor() == 1){
						matrix.blit(Register.TEXTURE_PARALLAX_1_LOW, leftPos + 18, topPos + 16-6, 58, 93, 140, 70-6);
					}
					if(menu.getSettingAlternateColor() == 2){
						matrix.blit(Register.TEXTURE_PARALLAX_2_LOW, leftPos + 18, topPos + 16-6, 58, 93, 140, 70-6);
					}
					if(menu.getSettingAlternateColor() == 3){
						matrix.blit(Register.TEXTURE_PARALLAX_3_LOW, leftPos + 18, topPos + 16-6, 58, 93, 140, 70-6);
					}
					if(menu.getSettingAlternateColor() == 4){
						matrix.blit(Register.TEXTURE_PARALLAX_4_LOW, leftPos + 18, topPos + 16-6, 58, 93, 140, 70-6);
					}
					if(menu.getSettingAlternateColor() == 5){
						matrix.blit(Register.TEXTURE_PARALLAX_5_LOW, leftPos + 18, topPos + 16-6, 58, 93, 140, 70-6);
					}
					
					matrix.blit(Register.TEXTURE_GROUND_ARCADE, leftPos + 18, topPos + 16-6, 24, 0, 70, 35);
					matrix.blit(Register.TEXTURE_GROUND_ARCADE, leftPos + 18+70, topPos + 16-6, 162, 0, 70, 35);
					matrix.blit(Register.TEXTURE_GROUND_ARCADE, leftPos + 18, topPos + 16+35-12, 24, 221, 70, 35);
					matrix.blit(Register.TEXTURE_GROUND_ARCADE, leftPos + 18+70, topPos + 16+35-12, 162, 221, 70, 35);
				}
				if(menu.tableID == 3){ // Slot Machine
					if(menu.getSettingAlternateColor() == 0){
						matrix.blit(Register.TEXTURE_SLOTGAME_0, leftPos + 176/2 - 60, topPos + 2, 0, 0, 120, 50);
						matrix.blit(Register.TEXTURE_SLOTGAME_0, leftPos + 176/2 - 60, topPos + 34, 120, 0, 120, 50);
					}
					if(menu.getSettingAlternateColor() == 1){
						matrix.blit(Register.TEXTURE_SLOTGAME_1, leftPos + 176/2 - 60, topPos + 2, 0, 0, 120, 50);
						matrix.blit(Register.TEXTURE_SLOTGAME_1, leftPos + 176/2 - 60, topPos + 34, 120, 0, 120, 50);
					}
					if(menu.getSettingAlternateColor() == 2){
						matrix.blit(Register.TEXTURE_SLOTGAME_2, leftPos + 176/2 - 60, topPos + 2, 0, 0, 120, 50);
						matrix.blit(Register.TEXTURE_SLOTGAME_2, leftPos + 176/2 - 60, topPos + 34, 120, 0, 120, 50);
					}
					if(menu.getSettingAlternateColor() == 3){
						matrix.blit(Register.TEXTURE_SLOTGAME_3, leftPos + 176/2 - 60, topPos + 2, 0, 0, 120, 50);
						matrix.blit(Register.TEXTURE_SLOTGAME_3, leftPos + 176/2 - 60, topPos + 34, 120, 0, 120, 50);
					}
					if(menu.getSettingAlternateColor() == 4){
						matrix.blit(Register.TEXTURE_SLOTGAME_4, leftPos + 176/2 - 60, topPos + 2, 0, 0, 120, 50);
						matrix.blit(Register.TEXTURE_SLOTGAME_4, leftPos + 176/2 - 60, topPos + 34, 120, 0, 120, 50);
					}
					if(menu.getSettingAlternateColor() == 5){
						matrix.blit(Register.TEXTURE_SLOTGAME_5, leftPos + 176/2 - 60, topPos + 2, 0, 0, 120, 50);
						matrix.blit(Register.TEXTURE_SLOTGAME_5, leftPos + 176/2 - 60, topPos + 34, 120, 0, 120, 50);
					}
				}
				
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_5.X + 19 + 6     , posY + pos_button_left_5.Y - 6 + 2, 110, 229, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_5.X + 19 + 6 + 40, posY + pos_button_left_5.Y - 6 + 2, 111, 229, 40, 12);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_5.X + 19 + 6 + 80, posY + pos_button_left_5.Y - 6 + 2, 112, 229, 40, 12);
			}
			if(page == 3){
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X, posY + pos_button_left_1.Y - 6, 208, 224, 48, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_2.X, posY + pos_button_left_2.Y - 6, 208, 224, 48, 16);
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X + 64+4, posY + pos_button_left_1.Y - 6 + 6, 1, 179, 78, 20);
				
				matrix.blit(GUI_TEXTURE, posX + 176/2-21, posY + pos_button_left_4.Y - 6 + 2, 110, 243, 42, 12);
				if(hasGambling()) matrix.blit(GUI_TEXTURE, posX + 176/2-21, posY + pos_button_left_5.Y - 6 + 2, 110, 243, 42, 12);
			}
			if(page == 4){
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X, posY + pos_button_left_1.Y - 6, 208, 224, 48, 16);
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_2.X, posY + pos_button_left_2.Y - 6, 208, 224, 48, 16);
				
				matrix.blit(GUI_TEXTURE, posX + pos_button_left_1.X + 64 + 4, posY + pos_button_left_1.Y - 6 + 6, 1, 179, 78, 20);
				
				matrix.blit(GUI_TEXTURE, posX + 176/2-43, posY + pos_button_left_3.Y - 6 + 2, 110, 243, 42, 12);
				matrix.blit(GUI_TEXTURE, posX + 176/2-43, posY + pos_button_left_4.Y - 6 + 2, 110, 243, 42, 12);
				matrix.blit(GUI_TEXTURE, posX + 176/2-43, posY + pos_button_left_5.Y - 6 + 2, 110, 243, 42, 12);
				
				matrix.blit(GUI_TEXTURE, posX + 176/2+1, posY + pos_button_left_3.Y - 6 + 2, 110, 243, 42, 12);
				matrix.blit(GUI_TEXTURE, posX + 176/2+1, posY + pos_button_left_4.Y - 6 + 2, 110, 243, 42, 12);
				matrix.blit(GUI_TEXTURE, posX + 176/2+1, posY + pos_button_left_5.Y - 6 + 2, 110, 243, 42, 12);
			}
			if(page == 5){
				matrix.blit(GUI_TEXTURE, posX + 176/2-21, posY + pos_button_left_3.Y - 6 + 2, 110, 243, 42, 12);
				
				// matrix.blit(GUI_TEXTURE, posX + 4,          posY + 4, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42,       posY + 4, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42,    posY + 4, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42+42, posY + 4, 98, 202, 42, 12);
				//
				// matrix.blit(GUI_TEXTURE, posX + 4,          posY + 4 + 12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42,       posY + 4 + 12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42,    posY + 4 + 12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42+42, posY + 4 + 12, 98, 202, 42, 12);
				//
				// matrix.blit(GUI_TEXTURE, posX + 4,          posY + 4 + 12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42,       posY + 4 + 12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42,    posY + 4 + 12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42+42, posY + 4 + 12+12, 98, 202, 42, 12);
				//
				// matrix.blit(GUI_TEXTURE, posX + 4,          posY + 4 + 12+12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42,       posY + 4 + 12+12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42,    posY + 4 + 12+12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42+42, posY + 4 + 12+12+12, 98, 202, 42, 12);
				//
				// matrix.blit(GUI_TEXTURE, posX + 4,          posY + 4 + 12+12+12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42,       posY + 4 + 12+12+12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42,    posY + 4 + 12+12+12+12, 98, 202, 42, 12);
				// matrix.blit(GUI_TEXTURE, posX + 4+42+42+42, posY + 4 + 12+12+12+12, 98, 202, 42, 12);
				
			}
		}
		
		// ----- Buttons ----- //
		while (buttonSet.next()){
			if(buttonSet.isVisible()    ){ matrix.blit(GUI_TEXTURE, leftPos + buttonSet.pos().X, topPos + buttonSet.pos().Y, buttonSet.map().X,       buttonSet.map().Y,       buttonSet.sizeX(), buttonSet.sizeY()); }
			if(buttonSet.isHighlighted()){ matrix.blit(GUI_TEXTURE, leftPos + buttonSet.pos().X, topPos + buttonSet.pos().Y, buttonSet.highlight().X, buttonSet.highlight().Y, buttonSet.sizeX(), buttonSet.sizeY()); }
		}
		
		// // ----- Background ----- //
		// matrix.blit(GUI_TEXTURE, posX,       posY,        0,   0, 176, 178); // Background
		// matrix.blit(GUI_TEXTURE, posX -  56, posY -  1,   0, 180,  54,  28); // Slot 1
		// matrix.blit(GUI_TEXTURE, posX -  56, posY + 29,   0, 180,  54,  28); // Slot 2
		// matrix.blit(GUI_TEXTURE, posX -  56, posY + 59,   0, 180,  54,  28); // Slot 3
		// matrix.blit(GUI_TEXTURE, posX +   2, posY - 26, 200, 216,  56,  24); // Header 1
		// matrix.blit(GUI_TEXTURE, posX +  60, posY - 26, 200, 216,  56,  24); // Header 2
		// matrix.blit(GUI_TEXTURE, posX + 118, posY - 26, 200, 216,  56,  24); // Header 3
		//
		// // ----- Slots ----- //
		// if(page < 3 && activeInput == 0                 ) matrix.blit(GUI_TEXTURE, posX - 56, posY -  1,   0, 208, 54, 28); // Slot 1
		// if(page < 3 && menu.hasKey()                    ) matrix.blit(GUI_TEXTURE, posX - 56, posY + 29,  54, 208, 54, 28); // Slot 2
		// if(page < 3 && menu.hasKey() && menu.hasModule()) matrix.blit(GUI_TEXTURE, posX - 56, posY + 59, 108, 208, 54, 28); // Slot 3
		//
		// // ----- Header ----- //
		// if(page < 3 && menu.hasKey() && page == 0                    ) matrix.blit(GUI_TEXTURE, posX +   2, posY - 26, 200, highlightIndex == 18 ?  48 :  24, 56, 24); // Header 1
		// if(page < 3 && menu.hasKey() && page != 0                    ) matrix.blit(GUI_TEXTURE, posX +   2, posY - 26, 200,                                0, 56, 24); // Header 1
		// if(page < 3 && menu.hasKey() && page == 1 && menu.hasModule()) matrix.blit(GUI_TEXTURE, posX +  60, posY - 26, 200, highlightIndex == 19 ? 120 :  96, 56, 24); // Header 2
		// if(page < 3 && menu.hasKey() && page != 1 && menu.hasModule()) matrix.blit(GUI_TEXTURE, posX +  60, posY - 26, 200,                               72, 56, 24); // Header 2
		// if(page < 3 && menu.hasKey() && page == 2 && hasHighscore()  ) matrix.blit(GUI_TEXTURE, posX + 118, posY - 26, 200, highlightIndex == 20 ? 192 : 168, 56, 24); // Header 3
		// if(page < 3 && menu.hasKey() && page != 2 && hasHighscore()  ) matrix.blit(GUI_TEXTURE, posX + 118, posY - 26, 200,                              144, 56, 24); // Header 3
		//
		// // ----- Page 1 - Settings ----- //
		// if(  page == 0 && menu.hasKey()){
		// 	matrix.blit(GUI_TEXTURE,  posX + 4, posY +  4, 188,                                        72, 12, 12); // Empty Button
		// 	matrix.blit(GUI_TEXTURE,  posX + 4, posY + 17, 188,                                        72, 12, 12); // Empty Button
		// 	matrix.blit(GUI_TEXTURE,  posX + 4, posY + 30, 188, menu.getSettingDropOnBreak()    ? 60 : 24, 12, 12); // Drop Items on Break
		// 	matrix.blit(GUI_TEXTURE,  posX + 4, posY + 43, 188, menu.getSettingIndestructable() ? 60 : 24, 12, 12); // Indestructible Block
		// } if(page == 0 && menu.hasKey() && Configuration.CASINO.config_creative_token.get() && isOP()){
		// 	matrix.blit(GUI_TEXTURE,  posX + 4, posY +  4, 188, menu.getSettingInfiniteToken()  ? 60 : 24, 12, 12); // Infinite Tokens
		// } if(page == 0 && menu.hasKey() && Configuration.CASINO.config_creative_reward.get() && isOP()){
		// 	matrix.blit(GUI_TEXTURE,  posX + 4, posY + 17, 188, menu.getSettingInfinitePrize()  ? 60 : 24, 12, 12); // Infinite Rewards
		// } if(page == 0 && menu.hasKey() && menu.hasModule()){
		// 	matrix.blit(GUI_TEXTURE,  posX + 4, posY + 57, 188, hasReset ? 12 : 0,                         12, 12); // Reset MiniGame
		// 	matrix.blit(GUI_TEXTURE,  posX + 4, posY + 70, 176, menu.getSettingAlternateColor() * 12,      12, 12); // Color Palette
		// }
		//
		// // ----- Page 2 - Token ----- //
		// if(  page == 1) {
		// 	matrix.blit(GUI_TEXTURE, posX +  49, posY +  5,                               55, 187, 78, 20); // FIELD ITEMS
		// 	matrix.blit(GUI_TEXTURE, posX +   5, posY +  5, highlightIndex == 14 ?  40 :   0, 236, 40, 20); // TOKEN IN
		// 	matrix.blit(GUI_TEXTURE, posX + 131, posY +  5, highlightIndex == 15 ? 120 :  80, 236, 40, 20); // TOKEN OUT
		// } if(page == 1 && menu.hasToken()){
		// 	matrix.blit(GUI_TEXTURE, posX + 104, posY + 49,                              135, 181, 24, 12); // FIELD MINIMUM
		// 	matrix.blit(GUI_TEXTURE, posX + 137, posY + 47, highlightIndex ==  1 ? 176 : 160, 240, 16, 16); // MINIMUM LOWER
		// 	matrix.blit(GUI_TEXTURE, posX + 155, posY + 47, highlightIndex ==  2 ? 208 : 192, 240, 16, 16); // MINIMUM UPPER
		// } if(page == 1 && menu.hasToken() && hasGambling()){
		// 	matrix.blit(GUI_TEXTURE, posX + 104, posY + 67,                              135, 181, 24, 12); // FIELD MAXIMUM
		// 	matrix.blit(GUI_TEXTURE, posX + 137, posY + 65, highlightIndex ==  3 ? 176 : 160, 240, 16, 16); // MAXIMUM LOWER
		// 	matrix.blit(GUI_TEXTURE, posX + 155, posY + 65, highlightIndex ==  4 ? 208 : 192, 240, 16, 16); // MAXIMUM UPPER
		// }
		//
		// // ----- Page 3 - Reward ----- //
		// if(  page == 2){
		// 	matrix.blit(GUI_TEXTURE, posX +  49, posY +  5,                               55, 187, 78, 20); // FIELD ITEMS
		// 	matrix.blit(GUI_TEXTURE, posX +   5, posY +  5, highlightIndex == 14 ?  40 :   0, 236, 40, 20); // TOKEN IN
		// 	matrix.blit(GUI_TEXTURE, posX + 131, posY +  5, highlightIndex == 15 ? 120 :  80, 236, 40, 20); // TOKEN OUT
		// } if(page == 2 && menu.hasReward()){
		// 	matrix.blit(GUI_TEXTURE, posX +  48, posY + 31,                              135, 195, 48, 12); // FIELD BIG 1
		// 	matrix.blit(GUI_TEXTURE, posX +  48, posY + 49,                              135, 195, 48, 12); // FIELD BIG 2
		// 	matrix.blit(GUI_TEXTURE, posX +  48, posY + 67,                              135, 195, 48, 12); // FIELD BIG 3
		// 	matrix.blit(GUI_TEXTURE, posX + 104, posY + 31,                              135, 181, 24, 12); // FIELD SMALL 1
		// 	matrix.blit(GUI_TEXTURE, posX + 104, posY + 49,                              135, 181, 24, 12); // FIELD SMALL 2
		// 	matrix.blit(GUI_TEXTURE, posX + 104, posY + 67,                              135, 181, 24, 12); // FIELD SMALL 3
		//
		// 	matrix.blit(GUI_TEXTURE, posX + 137, posY + 29, highlightIndex ==  5 ? 176 : 160, 240, 16, 16); // REWARD 1 LOWER
		// 	matrix.blit(GUI_TEXTURE, posX + 155, posY + 29, highlightIndex ==  6 ? 208 : 192, 240, 16, 16); // REWARD 1 UPPER
		// 	matrix.blit(GUI_TEXTURE, posX + 137, posY + 47, highlightIndex ==  7 ? 176 : 160, 240, 16, 16); // REWARD 2 LOWER
		// 	matrix.blit(GUI_TEXTURE, posX + 155, posY + 47, highlightIndex ==  8 ? 208 : 192, 240, 16, 16); // REWARD 2 UPPER
		// 	matrix.blit(GUI_TEXTURE, posX + 137, posY + 65, highlightIndex ==  9 ? 176 : 160, 240, 16, 16); // REWARD 3 LOWER
		// 	matrix.blit(GUI_TEXTURE, posX + 155, posY + 65, highlightIndex == 10 ? 208 : 192, 240, 16, 16); // REWARD 3 UPPER
		// 	matrix.blit(GUI_TEXTURE, posX +  23, posY + 29, highlightIndex == 11 ? 240 : 224, 240, 16, 16); // SCORE 1 EDIT
		// 	matrix.blit(GUI_TEXTURE, posX +  23, posY + 47, highlightIndex == 12 ? 240 : 224, 240, 16, 16); // SCORE 2 EDIT
		// 	matrix.blit(GUI_TEXTURE, posX +  23, posY + 65, highlightIndex == 13 ? 240 : 224, 240, 16, 16); // SCORE 3 EDIT
		// 	matrix.blit(GUI_TEXTURE, posX +   5, posY + 29, menu.getPrizeMode1() ? 178 : 162, 208, 16, 16); // PRIZE 1 MODE
		// 	matrix.blit(GUI_TEXTURE, posX +   5, posY + 47, menu.getPrizeMode2() ? 178 : 162, 208, 16, 16); // PRIZE 2 MODE
		// 	matrix.blit(GUI_TEXTURE, posX +   5, posY + 65, menu.getPrizeMode3() ? 178 : 162, 208, 16, 16); // PRIZE 3 MODE
		// }
		//
		// // ----- Page 4 - Input ----- //
		// if(page == 3){
		// 	matrix.blit(GUI_TEXTURE, posX + 49, posY + 57, 55, 187, 78, 20); // FIELD INPUT
		// }
		//
		// if(highlightTimer > 0){
		// 	highlightTimer--;
		// 	if(highlightTimer == 0){
		// 		if(highlightIndex == 11 || highlightIndex == 12){
		// 			commandTranferOFF();
		// 		} highlightIndex = 0;
		// 	}
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	/** ??? **/
	public void commandChangeBet(int value, boolean isMaxBet){
		boolean send = false;
		if(isMaxBet){
			menu.setBettingHigh(menu.getBettingHigh() + value);
			if(menu.getBettingLow() > menu.getBettingHigh()){
				menu.setBettingLow(menu.getBettingHigh());
			}
			send = true;
		} else {
			if(value > 0 || menu.getBettingLow() > 0){
				menu.setBettingLow(menu.getBettingLow() + value);
				if(menu.getBettingHigh() < menu.getBettingLow() || !hasGambling()){
					menu.setBettingHigh(menu.getBettingLow());
				}
				send = true;
			}
		}
		if(send){
			sendPacket();
		}
	}
	
	/** ??? **/
	public void commandChangeReward(int value, int index){
		boolean send = false;
		if(index == 0){ if(value > 0 || menu.getPrizeCount1() > 0){ menu.setPrizeCount1(menu.getPrizeCount1() + value); send = true; } }
		if(index == 1){ if(value > 0 || menu.getPrizeCount2() > 0){ menu.setPrizeCount2(menu.getPrizeCount2() + value); send = true; } }
		if(index == 2){ if(value > 0 || menu.getPrizeCount3() > 0){ menu.setPrizeCount3(menu.getPrizeCount3() + value); send = true; } }
		if(send){ sendPacket(); }
	}
	
	/** ??? **/
	public void commandTransfer(boolean isToken, boolean isIN){
		if( isToken &&  isIN){
			menu.setTransferTokenIN(true);
			menu.setTransferTokenOUT(false);
			menu.setTransferPrizeIN(false);
			menu.setTransferPrizeOUT(false);
		}
		if( isToken && !isIN){
			menu.setTransferTokenIN(false);
			menu.setTransferTokenOUT(true);
			menu.setTransferPrizeIN(false);
			menu.setTransferPrizeOUT(false);
		}
		if(!isToken &&  isIN){
			menu.setTransferTokenIN(false);
			menu.setTransferTokenOUT(false);
			menu.setTransferPrizeIN(true);
			menu.setTransferPrizeOUT(false);
		}
		if(!isToken && !isIN){
			menu.setTransferTokenIN(false);
			menu.setTransferTokenOUT(false);
			menu.setTransferPrizeIN(false);
			menu.setTransferPrizeOUT(true);
		}
		sendPacket();
	}
	
	private void commandTranferOFF(){
		menu.setTransferTokenIN(false);
		menu.setTransferTokenOUT(false);
		menu.setTransferPrizeIN(false);
		menu.setTransferPrizeOUT(false);
		sendPacket();
	}
	
	/** ??? **/
	public void commandToggleSettings(int settingID){
		if(settingID == 0){ if(Configuration.CASINO.config_creative_token.get() && isOP()){  buttonSet.setToggle(6, menu.setSettingInfiniteToken( !menu.getSettingInfiniteToken()));          } }
		if(settingID == 1){ if(Configuration.CASINO.config_creative_reward.get() && isOP()){ buttonSet.setToggle(7, menu.setSettingInfinitePrize( !menu.getSettingInfinitePrize()));          } }
		if(settingID == 2){                                                 buttonSet.setToggle(8, menu.setSettingDropOnBreak(   !menu.getSettingDropOnBreak()));              }
		if(settingID == 3){                                                 buttonSet.setToggle(9, menu.setSettingIndestructable(!menu.getSettingIndestructable()));           }
		if(settingID == 4){                                                 buttonSet.setToggle(10, menu.setSettingAlternateScore(!menu.getSettingAlternateScore()));  }
		sendPacket();
	}
	
	/** ??? **/
	public void commandChangePage(int index){
		if(menu.getTransferTokenIN() || menu.getTransferTokenOUT() || menu.getTransferPrizeIN() || menu.getTransferPrizeOUT()){
			menu.setTransferTokenIN(false);
			menu.setTransferTokenOUT(false);
			menu.setTransferPrizeIN(false);
			menu.setTransferPrizeOUT(false);
			sendPacket();
		}
		page = index;
	}
	
	/** ??? **/
	public void commandResetGameState(){
		PacketHandler.sendToServer(new MessageStateServer(true, -2, menu.pos()));
		hasReset = true;
	}
	
	/** ??? **/
	public void commandSetScore(int index, int score){
		if(index == 0){ menu.setPrizeScore1(score); }
		if(index == 1){ menu.setPrizeScore2(score); }
		if(index == 2){ menu.setPrizeScore3(score); }
		sendPacket();
	}
	
	/** ??? **/
	public void commandTogglePrize(int index){
		if(index == 0){ menu.setPrizeMode1(!menu.getPrizeMode1()); buttonSet.setToggle(31, menu.getPrizeMode1()); }
		if(index == 1){ menu.setPrizeMode2(!menu.getPrizeMode2()); buttonSet.setToggle(33, menu.getPrizeMode2()); }
		if(index == 2){ menu.setPrizeMode3(!menu.getPrizeMode3()); buttonSet.setToggle(35, menu.getPrizeMode3()); }
		sendPacket();
	}
	
	// public void commandSetPage1(){
	// 	resetPages();
	// 	this.page = 0;
	// }
	//
	// public void commandSetPage2(){
	// 	resetPages();
	// 	this.page = 1;
	// }
	//
	// public void commandSetPage3(){
	// 	resetPages();
	// 	this.page = 2;
	// }
	//
	// public void commandSetPage4(){
	// 	resetPages();
	// 	this.page = 3;
	// }
	//
	// public void commandSetPage5(){
	// 	resetPages();
	// 	this.page = 4;
	// }
	
	public void commandSetPage(int id){
		buttonSet.setToggle(0, id == 0);
		buttonSet.setToggle(1, id == 1);
		buttonSet.setToggle(2, id == 2);
		buttonSet.setToggle(3, id == 3);
		buttonSet.setToggle(4, id == 4);
		buttonSet.setToggle(5, id == 5);
		this.page = id;
		if(menu.getTransferTokenIN() || menu.getTransferTokenOUT() || menu.getTransferPrizeIN() || menu.getTransferPrizeOUT()){
			menu.setTransferTokenIN(false);
			menu.setTransferTokenOUT(false);
			menu.setTransferPrizeIN(false);
			menu.setTransferPrizeOUT(false);
			sendPacket();
		}
	}
	
	public void commandChangeColor(int value){
		menu.setSettingAlternateColor((menu.getSettingAlternateColor() + value + 6) % 6);
		sendPacket();
	}
	
	public void commandChangeRule(int id, int value){
		if(id == 0) menu.setSettingRule_1((menu.getSettingRule_1() + value + getRuleCount(id)) % getRuleCount(id));
		if(id == 1) menu.setSettingRule_2((menu.getSettingRule_2() + value + getRuleCount(id)) % getRuleCount(id));
		if(id == 2) menu.setSettingRule_3((menu.getSettingRule_3() + value + getRuleCount(id)) % getRuleCount(id));
		if(id == 3) menu.setSettingRule_4((menu.getSettingRule_4() + value + getRuleCount(id)) % getRuleCount(id));
		if(id == 4) menu.setSettingRule_5((menu.getSettingRule_5() + value + getRuleCount(id)) % getRuleCount(id));
		sendPacket();
	}
	
	
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFont(GuiGraphics matrix, String text, int x, int y){
		matrix.drawString(font, text,  x, y, 16777215);
	}
	
	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFontInvert(GuiGraphics matrix, String text, int x, int y){
		int w = this.font.width(text);
		matrix.drawString(font, text,  x - w, y, 16777215);
	}
	
	/** Draws String on x,y position with shadow and custom color **/
	protected void drawFontCenter(GuiGraphics matrix, String text, int x, int y){
		int w = this.font.width(text) / 2;
		matrix.drawString(font, text,  x - w, y, 16777215);
	}
	
	protected void highlight(int index){
		highlightTimer = 10;
		highlightIndex = index;
	}
	
	private boolean isOP(){
		return !Configuration.CASINO.config_creative_oponly.get() || this.minecraft.player.getPermissionLevel() > 0;
	}
	
	protected void sendPacket(){
		PacketHandler.sendToServer(new MessageSettingServer(menu.pos(), new int[]{
				menu.getBettingLow(),
				menu.getBettingHigh(),
				menu.getPrizeScore1(),
				menu.getPrizeScore2(),
				menu.getPrizeScore3(),
				menu.getPrizeCount1(),
				menu.getPrizeCount2(),
				menu.getPrizeCount3(),
				menu.getPrizeMode1() ? 1 : 0,
				menu.getPrizeMode2() ? 1 : 0,
				menu.getPrizeMode3() ? 1 : 0,
				menu.getTransferTokenIN()  ? 1 : 0,
				menu.getTransferTokenOUT() ? 1 : 0,
				menu.getTransferPrizeIN()  ? 1 : 0,
				menu.getTransferPrizeOUT() ? 1 : 0,
				menu.getSettingInfiniteToken()  ? 1 : 0,
				menu.getSettingInfinitePrize()  ? 1 : 0,
				menu.getSettingDropOnBreak()    ? 1 : 0,
				menu.getSettingIndestructable() ? 1 : 0,
				menu.getSettingAlternateScore() ? 1 : 0,
				menu.getSettingAlternateColor(),
				menu.getSettingRule_1(),
				menu.getSettingRule_2(),
				menu.getSettingRule_3(),
				menu.getSettingRule_4(),
				menu.getSettingRule_5()
		} ));
	}
	
	private boolean hasHighscore(){
		return menu.getModule() instanceof ItemRulebook && ((ItemRulebook) menu.getModule()).hasHighscore();
		
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_LIME.get()      ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_LIGHT_GRAY.get()) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_LIGHT_BLUE.get()) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_GRAY.get()      ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_CYAN.get()      ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_BLUE.get()      ) return true;
		
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CHIP_PINK.get()      ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CHIP_ORANGE.get()    ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CHIP_LIGHT_GRAY.get()) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CHIP_LIGHT_BLUE.get()) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CHIP_CYAN.get()      ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CHIP_BLUE.get()      ) return true;
		
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_PURPLE.get()    ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_MAGENTA.get()   ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_LIGHT_BLUE.get()) return true;
		
		// return false;
	}
	
	private boolean hasGambling(){
		return menu.getModule() instanceof ItemRulebook && ((ItemRulebook) menu.getModule()).hasGambling();
		
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_YELLOW.get()) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_RED.get()   ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_PINK.get()  ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_MINO_ORANGE.get()) return true;
		
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_YELLOW.get()    ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_WHITE.get()     ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_RED.get()       ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_ORANGE.get()    ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_LIGHT_GRAY.get()) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_GRAY.get()      ) return true;
		// if(menu.getItems().get(1).getItem() == Register.MODULE_CARD_BLACK.get()     ) return true;
		
		// return false;
	}
	
	
	
	protected boolean hasKey(){
		return menu.hasKey();
	}
	
	protected boolean hasModule(){
		return menu.getModule() instanceof ItemRulebook;
	}
	
	protected int getModuleID(){
		return menu.getModule() instanceof ItemRulebook ? ((ItemRulebook) menu.getModule()).getModuleID() : 0;
	}
	
	protected int getCurrentRule(int index){
		if(index == 0) return menu.getSettingRule_1();
		if(index == 1) return menu.getSettingRule_2();
		if(index == 2) return menu.getSettingRule_3();
		if(index == 3) return menu.getSettingRule_4();
		if(index == 4) return menu.getSettingRule_5();
		return 0;
	}
	
	protected boolean hasColoring(){
		return menu.getModule() instanceof ItemRulebook && ((ItemRulebook) menu.getModule()).getColoringType() > -1;
	}
	
	protected int getColoringType(){
		return menu.getModule() instanceof ItemRulebook ? ((ItemRulebook) menu.getModule()).getColoringType() : -1;
	}
	
	protected boolean hasRules(int ruleCount){
		if(menu.getModule() instanceof ItemRulebook &&  ((ItemRulebook) menu.getModule()).isArcadeGame() && menu.tableID > 0) return false;
		if(menu.getModule() instanceof ItemRulebook && !((ItemRulebook) menu.getModule()).isArcadeGame() && menu.tableID == 0) return false;
		if(menu.tableID == 3) return false;
		return menu.getModule() instanceof ItemRulebook && ((ItemRulebook) menu.getModule()).getRuleSet().length > ruleCount;
	}
	
	protected int getRuleCount(int index){
		return menu.getModule() instanceof ItemRulebook ? ((ItemRulebook) menu.getModule()).getRuleSet().length > index ? ((ItemRulebook) menu.getModule()).getRuleSet()[index] : 1 : 1;
	}
	
	protected boolean canTakeIN(boolean isTokenStorage){
		if(isTokenStorage){
			return !menu.getItems().get(2).isEmpty() && (menu.getItemToken() == menu.getItems().get(2) || menu.getItemToken().isEmpty());
		} else {
			return !menu.getItems().get(2).isEmpty() && (menu.getItemPrize() == menu.getItems().get(2) || menu.getItemPrize().isEmpty());
		}
	}
	
	protected boolean canTakeOUT(boolean isTokenStorage){
		if(isTokenStorage){
			return (menu.getItems().get(2).isEmpty() || menu.getItemToken() == menu.getItems().get(2)) && !menu.getItemToken().isEmpty();
		} else {
			return (menu.getItems().get(2).isEmpty() || menu.getItemPrize() == menu.getItems().get(2)) && !menu.getItemPrize().isEmpty();
		}
	}
	
	
	
}
