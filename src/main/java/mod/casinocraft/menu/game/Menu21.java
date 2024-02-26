package mod.casinocraft.menu.game;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.casinocraft.menu.MenuCasino;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class Menu21 extends MenuCasino {  //  Black Jack  :  Baccarat
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor **/
	public Menu21(int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(CasinoKeeper.MENU_21.get(), windowID, playerInventory, board);
	}
	
	/** Forge Registry Constructor **/
	public Menu21(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		super(CasinoKeeper.MENU_21.get(), windowID, playerInventory, packetBuffer);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Override
	public int getID(){
		return 21;
	}
	
	
	
}
