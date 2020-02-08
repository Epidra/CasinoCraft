package mod.casinocraft.container.clay;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.container.ContainerBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerMysticSquare extends ContainerCasino {

    public static final ContainerType<ContainerMysticSquare> TYPE = (ContainerType<ContainerMysticSquare>) IForgeContainerType.create(ContainerMysticSquare::new).setRegistryName(CasinoCraft.MODID, "mysticsquare");

    public ContainerMysticSquare(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(TYPE, windowID, playerInventory, board);
    }

    public ContainerMysticSquare(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TYPE, windowID, playerInventory, packetBuffer);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public String getName(){
        return "c_mystic_square";
    }
}
