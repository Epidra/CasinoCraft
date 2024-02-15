package mod.casinocraft.client.menu.other;

import mod.casinocraft.Register;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.common.block.entity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuDummy extends MenuCasino {  //  -----
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public MenuDummy(int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(Register.MENU_DUMMY.get(), windowID, playerInventory, board);
	}
	
	/** Forge Registry Constructor **/
	public MenuDummy(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(Register.MENU_DUMMY.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public int getID(){
		return 48;
	}
	
	
	
}
