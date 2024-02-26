package mod.casinocraft.menu.block;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuMachine;
import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuCardTable extends MenuMachine {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public MenuCardTable(int windowID, Inventory playerInventory, BlockEntityMachine board) {
        super(CasinoKeeper.MENU_CARDTABLE.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public MenuCardTable(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        super(CasinoKeeper.MENU_CARDTABLE.get(), windowID, playerInventory, packetBuffer);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public int getID(){
        return 101;
    }



}