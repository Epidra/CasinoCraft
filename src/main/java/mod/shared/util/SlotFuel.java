package mod.shared.util;

import mod.acecraft.container.ContainerBlastFurnace;
import mod.shared.container.ContainerFlamer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SlotFuel extends Slot {

    private ContainerFlamer container;

    public SlotFuel(ContainerFlamer container, IInventory inventory, int a, int b, int c) {
        super(inventory, a, b, c);
        this.container = container;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack) {
        return BurnTimes.isFuel(stack) || isBucket(stack);
    }

    public int getItemStackLimit(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }

}
