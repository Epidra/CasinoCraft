package mod.casinocraft.menu.game;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.casinocraft.menu.MenuCasino;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class Menu42 extends MenuCasino {  //  Ishido
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public Menu42(int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(CasinoKeeper.MENU_42.get(), windowID, playerInventory, board);
	}
	
	/** Forge Registry Constructor **/
	public Menu42(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(CasinoKeeper.MENU_42.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public int getID(){
		return 42;
	}
	
	
	
}
