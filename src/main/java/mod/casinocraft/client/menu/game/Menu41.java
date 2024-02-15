package mod.casinocraft.client.menu.game;

import mod.casinocraft.Register;
import mod.casinocraft.client.menu.MenuCasino;
import mod.casinocraft.common.block.entity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class Menu41 extends MenuCasino {  //  Minesweeper
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public Menu41(int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(Register.MENU_MINESWEEPER.get(), windowID, playerInventory, board);
	}
	
	/** Forge Registry Constructor **/
	public Menu41(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(Register.MENU_MINESWEEPER.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public int getID(){
		return 14;
	}
	
	
	
}
