package mod.casinocraft.container;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.container.ContainerBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerSlotMachine extends ContainerMachine {

    public static final ContainerType<ContainerSlotMachine> TYPE = (ContainerType<ContainerSlotMachine>) IForgeContainerType.create(ContainerSlotMachine::new).setRegistryName(CasinoCraft.MODID, "slotmachine");

    public ContainerSlotMachine(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(TYPE, windowID, playerInventory, board);
    }

    public ContainerSlotMachine(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TYPE, windowID, playerInventory, packetBuffer);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
