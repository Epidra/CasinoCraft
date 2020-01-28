package mod.casinocraft.container;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.container.ContainerBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ContainerMachine extends ContainerBase {

    //public static final ContainerType<ContainerCasino> TYPE = (ContainerType<ContainerCasino>) IForgeContainerType.create(ContainerCasino::new).setRegistryName(CasinoCraft.MODID, "casino");

    private final IInventory tileCasino;
    private final World world;


    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, (TileEntityBoard) playerInventory.player.getEntityWorld().getTileEntity(BlockPos.fromLong(packetBuffer.readLong())));
    }

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(type, windowID);
        this.tileCasino = board;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(board, 0, 256, -4)); // Key Card
        this.addSlot(new Slot(board, 1, 256, -4)); // Game Module
        this.addSlot(new Slot(board, 2, 256, -4)); // Token IN
        this.addSlot(new Slot(board, 3, 256, -4)); // Token OUT
        addPlayerSlots(playerInventory);
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return tileCasino.isUsableByPlayer(playerIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */

    // needed ???
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            //} else if (index != 1 && index != 0) {
            //    if (this.func_217057_a(itemstack1)) {
            //        if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
            //            return ItemStack.EMPTY;
            //        }
            //    } else if (this.isFuel(itemstack1)) {
            //        if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
            //            return ItemStack.EMPTY;
            //        }
            //    } else if (index >= 3 && index < 30) {
            //        if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
            //            return ItemStack.EMPTY;
            //        }
            //    } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
            //        return ItemStack.EMPTY;
            //    }
            //} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    //    public void addListener(IContainerListener listener);
    //    public void detectAndSendChanges();
    //    public ItemStack transferStackInSlot(EntityPlayer player, int index);
}
