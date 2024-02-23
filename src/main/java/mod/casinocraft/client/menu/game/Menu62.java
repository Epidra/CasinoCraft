package mod.casinocraft.client.menu.game;

import mod.casinocraft.Register;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.common.block.entity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class Menu62 extends MenuCasino {  //  Sokoban
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public Menu62(int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(Register.MENU_SOKOBAN.get(), windowID, playerInventory, board);
	}
	
	/** Forge Registry Constructor **/
	public Menu62(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(Register.MENU_SOKOBAN.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public int getID(){
		return 62;
	}
	
	
	
}
