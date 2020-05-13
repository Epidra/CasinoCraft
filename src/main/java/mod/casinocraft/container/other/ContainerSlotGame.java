package mod.casinocraft.container.other;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerSlotGame extends ContainerCasino {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor **/
    public ContainerSlotGame(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(CasinoKeeper.CONTAINER_SLOTGAME.get(), windowID, playerInventory, board);
    }

    /** Forge Registry Constructor **/
    public ContainerSlotGame(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(CasinoKeeper.CONTAINER_SLOTGAME.get(), windowID, playerInventory, packetBuffer);
    }




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    @Override
    public int getID(){
        return 49;
    }

}
