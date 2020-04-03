package mod.casinocraft.container.card;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerAceyDeucey extends ContainerCasino {

    public static final ContainerType<ContainerAceyDeucey> TYPE = (ContainerType<ContainerAceyDeucey>) IForgeContainerType.create(ContainerAceyDeucey::new).setRegistryName(CasinoCraft.MODID, "aceydeucey");

    public ContainerAceyDeucey(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(TYPE, windowID, playerInventory, board);
    }

    public ContainerAceyDeucey(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TYPE, windowID, playerInventory, packetBuffer);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public String getName(){
        return "c_acey_deucey";
    }

}