package mod.casinocraft.menu.other;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuDummy extends MenuCasino {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public MenuDummy(int windowID, Inventory playerInventory, BlockEntityMachine board) {
        super(CasinoKeeper.MENU_DUMMY.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public MenuDummy(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        super(CasinoKeeper.MENU_DUMMY.get(), windowID, playerInventory, packetBuffer);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public int getID(){
        return 48;
    }



}
