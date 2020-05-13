package mod.casinocraft.container.chip;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ContainerChipPurple extends ContainerCasino {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public ContainerChipPurple(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(CasinoKeeper.CONTAINER_CHIP_PURPLE.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public ContainerChipPurple(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(CasinoKeeper.CONTAINER_CHIP_PURPLE.get(), windowID, playerInventory, packetBuffer);
    }




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    @Override
    public int getID(){
        return 28;
    }

}
