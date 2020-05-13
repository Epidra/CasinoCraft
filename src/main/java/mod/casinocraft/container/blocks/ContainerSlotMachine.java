package mod.casinocraft.container.blocks;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerMachine;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ContainerSlotMachine extends ContainerMachine {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public ContainerSlotMachine(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(CasinoKeeper.CONTAINER_SLOTMACHINE.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public ContainerSlotMachine(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(CasinoKeeper.CONTAINER_SLOTMACHINE.get(), windowID, playerInventory, packetBuffer);
    }

}
