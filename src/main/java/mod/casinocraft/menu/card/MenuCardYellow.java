package mod.casinocraft.menu.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuCardYellow extends MenuCasino {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public MenuCardYellow(int windowID, Inventory playerInventory, BlockEntityMachine board) {
        super(CasinoKeeper.CONTAINER_CARD_YELLOW.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public MenuCardYellow(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        super(CasinoKeeper.CONTAINER_CARD_YELLOW.get(), windowID, playerInventory, packetBuffer);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public int getID(){
        return 15;
    }



}