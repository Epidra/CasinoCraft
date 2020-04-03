package mod.casinocraft.container;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerCardTable extends ContainerMachine {

    public static final ContainerType<ContainerCardTable> TYPE = (ContainerType<ContainerCardTable>) IForgeContainerType.create(ContainerCardTable::new).setRegistryName(CasinoCraft.MODID, "cardtable");

    public ContainerCardTable(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(TYPE, windowID, playerInventory, board);
    }

    // For Forge Registry
    public ContainerCardTable(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TYPE, windowID, playerInventory, packetBuffer);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public String getName() {
        return "cardtable";
    }
}