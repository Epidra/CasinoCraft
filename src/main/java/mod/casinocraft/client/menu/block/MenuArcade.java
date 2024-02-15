package mod.casinocraft.client.menu.block;

import mod.casinocraft.Register;
import mod.casinocraft.client.menu.MenuMachine;
import mod.casinocraft.common.block.entity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuArcade extends MenuMachine {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public MenuArcade(int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(Register.MENU_ARCADE.get(), windowID, playerInventory, board);
	}
	
	/** Forge Registry Constructor **/
	public MenuArcade(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(Register.MENU_ARCADE.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// @Override
	public int getID(){
		return 100;
	}
	
	
	
}