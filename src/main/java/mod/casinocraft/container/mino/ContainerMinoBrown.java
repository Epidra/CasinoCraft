package mod.casinocraft.container.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ContainerMinoBrown extends ContainerCasino {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public ContainerMinoBrown(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(CasinoKeeper.CONTAINER_MINO_BROWN.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public ContainerMinoBrown(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(CasinoKeeper.CONTAINER_MINO_BROWN.get(), windowID, playerInventory, packetBuffer);
    }




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    @Override
    public int getID(){
        return 34;
    }

}
