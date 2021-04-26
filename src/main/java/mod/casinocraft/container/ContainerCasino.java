package mod.casinocraft.container;

import mod.casinocraft.tileentities.TileEntityMachine;
import mod.lucky77.tileentities.TileBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public abstract class ContainerCasino extends ContainerBase {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.of(packetBuffer.readLong()));
    }

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityMachine) playerInventory.player.getCommandSenderWorld().getBlockEntity(pos));
        this.pos = pos;
    }

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityMachine board) {
        super(type, windowID, playerInventory, board);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected void createInventory(TileBase tile, PlayerInventory playerInventory){

    }

}