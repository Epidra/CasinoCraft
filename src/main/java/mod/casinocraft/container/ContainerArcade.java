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

public class ContainerArcade extends ContainerMachine {

    public static final ContainerType<ContainerArcade> TYPE = (ContainerType<ContainerArcade>) IForgeContainerType.create(ContainerArcade::new).setRegistryName(CasinoCraft.MODID, "arcade");

    public ContainerArcade(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(TYPE, windowID, playerInventory, board);
    }

    // For Forge Registry
    public ContainerArcade(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TYPE, windowID, playerInventory, packetBuffer);
    }



    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public String getName() {
        return "arcade";
    }
}