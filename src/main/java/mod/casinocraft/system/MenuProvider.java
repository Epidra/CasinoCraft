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
			if(gainAccess)                            return new MenuArcade(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_5_1.get()) return new Menu51(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_5_2.get()) return new Menu52(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_6_1.get()) return new Menu61(    windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_6_2.get()) return new Menu62(    windowId, playerInventory, (BlockEntityMachine) this.tile);
		}
		else if(this.tile instanceof BlockEntityCardTableBase || this.tile instanceof BlockEntityCardTableWide){
			if(gainAccess)                            return new MenuCardTable(windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_1_1.get()) return new Menu11(       windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_2_1.get()) return new Menu21(       windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_2_2.get()) return new Menu22(       windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_3_1.get()) return new Menu31(       windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_3_2.get()) return new Menu32(       windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_3_3.get()) return new Menu33(       windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_4_1.get()) return new Menu41(       windowId, playerInventory, (BlockEntityMachine) this.tile);
			if(module == Register.RULEBOOK_4_2.get()) return new Menu42(       windowId, playerInventory, (BlockEntityMachine) this.tile);
		} else if(this.tile instanceof BlockEntitySlotMachine) {
			if(gainAccess){return new MenuSlotMachine(windowId, playerInventory, (BlockEntityMachine) this.tile); }
			               return new MenuSlotGame(   windowId, playerInventory, (BlockEntityMachine) this.tile);
		}
		return new MenuDummy(windowId, playerInventory, (BlockEntityMachine) this.tile);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public Component getDisplayName() {
		return Component.empty();
	}
	
	
	
}