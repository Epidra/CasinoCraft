package mod.casinocraft.menu.game;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.casinocraft.menu.MenuCasino;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class Menu11 extends MenuCasino {  //  Roulette
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public Menu11(int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(CasinoKeeper.MENU_11.get(), windowID, playerInventory, board);
	}
	
	/** Forge Registry Constructor **/
	public Menu11(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(CasinoKeeper.MENU_11.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public int getID(){
		return 11;
	}
	
	
	
}
