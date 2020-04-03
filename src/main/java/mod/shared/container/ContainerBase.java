package mod.shared.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;

import javax.annotation.Nullable;

public abstract class ContainerBase extends Container {

    /** Default Constructor **/
    public ContainerBase(@Nullable ContainerType<?> type, int windowID) {
        super(type, windowID);
    }

    /** Adds Slots from Player Inventory at a specific Position **/
    protected void addPlayerSlots(PlayerInventory playerInventory, int inX, int inY) {
        // Slots for the hotbar
        for (int row = 0; row < 9; ++ row) {
            int x = inX + row * 18;
            int y = inY + 86;
            addSlot(new Slot(playerInventory, row, x, y));
        }
        // Slots for the main inventory
        for (int row = 1; row < 4; ++ row) {
            for (int col = 0; col < 9; ++ col) {
                int x = inX + col * 18;
                int y = row * 18 + (inY + 10);
                addSlot(new Slot(playerInventory, col + row * 9, x, y));
            }
        }
    }

    /** Adds Slots from Player Inventory at the default Position **/
    protected void addPlayerSlots(PlayerInventory playerInventory) {
        addPlayerSlots(playerInventory, 8, 56);
    }

    //public abstract void updateAllValues(Container container, NonNullList<ItemStack> nonNullList);

    //public abstract void updateSlotContents(Container container, int i, ItemStack itemStack);

    //public abstract void updateSingleValue(Container container, int varToUpdate, int newValue);

}
