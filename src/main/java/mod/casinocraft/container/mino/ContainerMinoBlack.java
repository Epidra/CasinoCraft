package mod.casinocraft.container.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ContainerMinoBlack extends ContainerCasino {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public ContainerMinoBlack(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(CasinoKeeper.CONTAINER_MINO_BLACK.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public ContainerMinoBlack(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(CasinoKeeper.CONTAINER_MINO_BLACK.get(), windowID, playerInventory, packetBuffer);
    }




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    @Override
    public int getID(){
        return 32;
    }

}
