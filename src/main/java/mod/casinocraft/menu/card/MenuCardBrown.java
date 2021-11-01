package mod.casinocraft.menu.card;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MenuCardBrown extends MenuCasino {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public MenuCardBrown(int windowID, Inventory playerInventory, BlockEntityMachine board) {
        super(CasinoKeeper.CONTAINER_CARD_BROWN.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public MenuCardBrown(int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        super(CasinoKeeper.CONTAINER_CARD_BROWN.get(), windowID, playerInventory, packetBuffer);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public int getID(){
        return 2;
    }



}