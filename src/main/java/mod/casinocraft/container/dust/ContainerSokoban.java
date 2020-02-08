package mod.casinocraft.container.dust;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.container.ContainerBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerSokoban extends ContainerCasino {

    public static final ContainerType<ContainerSokoban> TYPE = (ContainerType<ContainerSokoban>) IForgeContainerType.create(ContainerSokoban::new).setRegistryName(CasinoCraft.MODID, "sokoban");

    public ContainerSokoban(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(TYPE, windowID, playerInventory, board);
    }

    public ContainerSokoban(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TYPE, windowID, playerInventory, packetBuffer);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public String getName(){
        return "a_sokoban";
    }
}
