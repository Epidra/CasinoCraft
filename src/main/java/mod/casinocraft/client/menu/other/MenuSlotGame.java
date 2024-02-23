package mod.casinocraft.client.menu.other;

import mod.casinocraft.Register;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.common.block.entity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuSlotGame extends MenuCasino {  //  Slot Machine
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public MenuSlotGame(int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(Register.MENU_SLOTGAME.get(), windowID, playerInventory, board);
	}
	
	/** Forge Registry Constructor **/
	public MenuSlotGame(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(Register.MENU_SLOTGAME.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public int getID(){
		return 70;
	}
	
	
	
}
