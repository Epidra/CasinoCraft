package mod.casinocraft.container;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityMachine;
import mod.casinocraft.util.LogicData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ContainerCasino extends ContainerBase {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.fromLong(packetBuffer.readLong()));
    }

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityMachine) playerInventory.player.getEntityWorld().getTileEntity(pos));
        this.pos = pos;
    }

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityMachine board) {
        super(type, windowID, playerInventory, board);
    }

    //----------------------------------------OTHER----------------------------------------//

    // ...

}