package mod.casinocraft.container;

import mod.casinocraft.tileentities.TileEntityMachine;
import mod.lucky77.tileentities.TileBase;
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
        this(type, windowID, playerInventory, BlockPos.of(packetBuffer.readLong()));
    }

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityMachine) playerInventory.player.getCommandSenderWorld().getBlockEntity(pos));
        this.pos = pos;
    }

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityMachine machine) {
        super(type, windowID, playerInventory, machine);

    }





    //----------------------------------------SUPPORT----------------------------------------//

    protected void createInventory(TileBase tile, PlayerInventory playerInventory){
        this.addSlot(new Slot(tile, 0, -24, 11)); // Key Card
        this.addSlot(new Slot(tile, 1, -24, 41)); // Game Module
        this.addSlot(new Slot(tile, 2, -24, 71)); // Token IN/OUT
        addPlayerSlots(playerInventory,  8, 74);
    }

    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                    if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                if (index >= 3 && index < 30) {
                    if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }



}
