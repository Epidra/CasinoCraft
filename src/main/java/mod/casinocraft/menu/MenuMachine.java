package mod.casinocraft.menu;

import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.lucky77.blockentity.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class MenuMachine extends MenuBase {




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MenuMachine(MenuType<?> type, int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.of(packetBuffer.readLong()));
    }

    public MenuMachine(MenuType<?> type, int windowID, Inventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (BlockEntityMachine) playerInventory.player.getCommandSenderWorld().getBlockEntity(pos));
        this.pos = pos;
    }

    public MenuMachine(MenuType<?> type, int windowID, Inventory playerInventory, BlockEntityMachine machine) {
        super(type, windowID, playerInventory, machine);

    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected void createInventory(BlockEntityBase tile, Inventory playerInventory){
        this.addSlot(new Slot(tile, 0, -24, 31)); // Key Card
        this.addSlot(new Slot(tile, 1, -24, 61)); // Game Module
        this.addSlot(new Slot(tile, 2, -24, 91)); // Token IN/OUT
        //this.addSlot(new Slot(board, 3, 132, 16)); // Token OUT
        addPlayerSlots(playerInventory, 8, 122-28);
    }

    public ItemStack quickMoveStack(Player player, int index) {
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
                //if (this.canSmelt(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
                    return ItemStack.EMPTY;
                }
                //} else if (this.isFuel(itemstack1)) {
                //    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                //        return ItemStack.EMPTY;
                //    }
                //} else
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
