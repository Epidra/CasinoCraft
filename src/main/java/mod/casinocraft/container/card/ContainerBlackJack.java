package mod.casinocraft.container.card;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.container.ContainerBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerBlackJack extends ContainerCasino {

    public static final ContainerType<ContainerBlackJack> TYPE = (ContainerType<ContainerBlackJack>) IForgeContainerType.create(ContainerBlackJack::new).setRegistryName(CasinoCraft.MODID, "blackjack");

    public ContainerBlackJack(int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(TYPE, windowID, playerInventory, board);
    }

    public ContainerBlackJack(int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TYPE, windowID, playerInventory, packetBuffer);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public String getName(){
        return "c_black_jack";
    }
}
