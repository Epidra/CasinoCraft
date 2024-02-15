package mod.casinocraft.system;

import mod.casinocraft.Register;
import mod.casinocraft.client.menu.block.MenuArcade;
import mod.casinocraft.client.menu.block.MenuCardTable;
import mod.casinocraft.client.menu.block.MenuSlotMachine;
import mod.casinocraft.client.menu.game.*;
import mod.casinocraft.client.menu.other.MenuDummy;
import mod.casinocraft.client.menu.other.MenuSlotGame;
import mod.casinocraft.common.block.entity.*;
import mod.lucky77.block.entity.BlockEntityBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MenuProvider implements net.minecraft.world.MenuProvider {
	
	public BlockEntityBase tile;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public MenuProvider(@Nonnull BlockEntityBase tile) {
		this.tile = tile;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CREATE MENU  ---------- ---------- ---------- ---------- //
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
		Item module = this.tile.getItem(1).getItem();
		boolean gainAccess = this.tile.getItem(0).getItem() == Blocks.AIR.asItem() || (this.tile.getItem(0).getItem() == playerInventory.getItem(playerInventory.selected).getItem() && playerInventory.getItem(playerInventory.selected).getHoverName().getString().matches(this.tile.getItem(0).getHoverName().getString()));
		if(this.tile instanceof BlockEntityArcade){
			if(gainAccess)                                      return new MenuArcade(       windowId, playerInventory, (BlockEntityMachine) this.tile);
			
			// if(module == Register.BOOK_RULE_BLACKJACK.get()) return new MenuBlackJack(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_POKER.get()) return new MenuPoker(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_ROULETTE.get()) return new MenuRoulette(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_SOLITAIRE.get()) return new MenuSolitaire(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_PYRAMID.get()) return new MenuPyramid(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_MAUMAU.get()) return new MenuMauMau(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_MINEFLIP.get()) return new MenuMinesweeper(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_HALMA.get()) return new MenuIshido(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_6_1.get()) return new Menu61(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_5_1.get()) return new Menu51(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_6_2.get()) return new Menu62(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_5_2.get()) return new Menu52(windowId, playerInventory, (BlockEntityMachine) this.tile);
			
			// if(module == Register.MODULE_CHIP_WHITE.get())      return new MenuChipWhite(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_ORANGE.get())     return new MenuChipOrange(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_MAGENTA.get())    return new MenuChipMagenta(  windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_LIGHT_BLUE.get()) return new MenuChipLightBlue(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_YELLOW.get())     return new MenuChipYellow(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_LIME.get())       return new MenuChipLime(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_PINK.get())       return new MenuChipPink(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_GRAY.get())       return new MenuChipGray(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_LIGHT_GRAY.get()) return new MenuChipLightGray(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_CYAN.get())       return new MenuChipCyan(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_PURPLE.get())     return new MenuChipPurple(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_BLUE.get())       return new MenuChipBlue(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_BROWN.get())      return new MenuChipBrown(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_GREEN.get())      return new MenuChipGreen(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_RED.get())        return new MenuChipRed(      windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CHIP_BLACK.get())      return new MenuChipBlack(    windowId, playerInventory, (BlockEntityMachine) this.tile);
		}
		else if(this.tile instanceof BlockEntityCardTableBase || this.tile instanceof BlockEntityCardTableWide){
			if(gainAccess)                                      return new MenuCardTable(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			
			if(module == Register.RULEBOOK_2_1.get()) return new Menu21(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_2_2.get()) return new Menu22(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_1_1.get()) return new Menu11(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_3_1.get()) return new Menu31(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_3_2.get()) return new Menu32(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_3_3.get()) return new Menu33(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_4_1.get()) return new Menu41(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_4_2.get()) return new Menu42(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_SNAKE.get()) return new MenuSnake(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_TETRIS.get()) return new MenuTetris(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_SOKOBAN.get()) return new MenuSokoban(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.BOOK_RULE_2048.get()) return new Menu2048(windowId, playerInventory, (BlockEntityMachine) this.tile);
			
			// if(module == Register.MODULE_CARD_WHITE.get())      return new MenuCardWhite(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_ORANGE.get())     return new MenuCardOrange(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_MAGENTA.get())    return new MenuCardMagenta(  windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_LIGHT_BLUE.get()) return new MenuCardLightBlue(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_YELLOW.get())     return new MenuCardYellow(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_LIME.get())       return new MenuCardLime(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_PINK.get())       return new MenuCardPink(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_GRAY.get())       return new MenuCardGray(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_LIGHT_GRAY.get()) return new MenuCardLightGray(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_CYAN.get())       return new MenuCardCyan(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_PURPLE.get())     return new MenuCardPurple(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_BLUE.get())       return new MenuCardBlue(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_BROWN.get())      return new MenuCardBrown(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_GREEN.get())      return new MenuCardGreen(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_RED.get())        return new MenuCardRed(      windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_CARD_BLACK.get())      return new MenuCardBlack(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			
			// if(module == Register.MODULE_MINO_WHITE.get())      return new MenuMinoWhite(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_ORANGE.get())     return new MenuMinoOrange(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_MAGENTA.get())    return new MenuMinoMagenta(  windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_LIGHT_BLUE.get()) return new MenuMinoLightBlue(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_YELLOW.get())     return new MenuMinoYellow(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_LIME.get())       return new MenuMinoLime(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_PINK.get())       return new MenuMinoPink(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_GRAY.get())       return new MenuMinoGray(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_LIGHT_GRAY.get()) return new MenuMinoLightGray(windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_CYAN.get())       return new MenuMinoCyan(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_PURPLE.get())     return new MenuMinoPurple(   windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_BLUE.get())       return new MenuMinoBlue(     windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_BROWN.get())      return new MenuMinoBrown(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_GREEN.get())      return new MenuMinoGreen(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_RED.get())        return new MenuMinoRed(      windowId, playerInventory, (BlockEntityMachine) this.tile);
			// if(module == Register.MODULE_MINO_BLACK.get())      return new MenuMinoBlack(    windowId, playerInventory, (BlockEntityMachine) this.tile);
		} else if(this.tile instanceof BlockEntitySlotMachine) {
			if(gainAccess){                                         return new MenuSlotMachine(  windowId, playerInventory, (BlockEntityMachine) this.tile); }
			return new MenuSlotGame(     windowId, playerInventory, (BlockEntityMachine) this.tile);
		}
		return new MenuDummy(windowId, playerInventory, (BlockEntityMachine) this.tile);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public Component getDisplayName() {
		return Component.empty();
	}
	
}