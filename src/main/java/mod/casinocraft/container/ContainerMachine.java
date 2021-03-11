package mod.casinocraft.container;

import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ContainerMachine extends ContainerBase {




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.fromLong(packetBuffer.readLong()));
    }

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityMachine) playerInventory.player.getEntityWorld().getTileEntity(pos));
        this.pos = pos;
    }

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityMachine machine) {
        super(type, windowID, playerInventory, machine);
        this.addSlot(new Slot(machine, 0, -24, 31)); // Key Card
        this.addSlot(new Slot(machine, 1, -24, 61)); // Game Module
        this.addSlot(new Slot(machine, 2, -24, 91)); // Token IN/OUT
        //this.addSlot(new Slot(board, 3, 132, 16)); // Token OUT
        addPlayerSlots(playerInventory, 8, 122-28);
    }




    //----------------------------------------OTHER----------------------------------------//

    // ...

}
