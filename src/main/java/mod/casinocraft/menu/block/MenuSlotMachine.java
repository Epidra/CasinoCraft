package mod.casinocraft.menu.block;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuMachine;
import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuSlotMachine extends MenuMachine {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public MenuSlotMachine(int windowID, Inventory playerInventory, BlockEntityMachine board) {
        super(CasinoKeeper.MENU_SLOTMACHINE.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public MenuSlotMachine(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        super(CasinoKeeper.MENU_SLOTMACHINE.get(), windowID, playerInventory, packetBuffer);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public int getID(){
        return 102;
    }



}
