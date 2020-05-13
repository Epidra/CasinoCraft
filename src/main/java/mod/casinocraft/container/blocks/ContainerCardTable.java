package mod.casinocraft.container.blocks;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerMachine;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ContainerCardTable extends ContainerMachine {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public ContainerCardTable(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(CasinoKeeper.CONTAINER_CARDTABLE.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public ContainerCardTable(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(CasinoKeeper.CONTAINER_CARDTABLE.get(), windowID, playerInventory, packetBuffer);
    }

}