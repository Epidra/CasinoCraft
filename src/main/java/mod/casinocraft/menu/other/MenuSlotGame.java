package mod.casinocraft.menu.other;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuSlotGame extends MenuCasino {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public MenuSlotGame(int windowID, Inventory playerInventory, BlockEntityMachine board) {
        super(CasinoKeeper.MENU_SLOTGAME.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public MenuSlotGame(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        super(CasinoKeeper.MENU_SLOTGAME.get(), windowID, playerInventory, packetBuffer);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public int getID(){
        return 49;
    }



}
