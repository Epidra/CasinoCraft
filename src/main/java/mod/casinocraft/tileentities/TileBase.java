package mod.casinocraft.tileentities;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;

public abstract class TileBase extends TileEntity implements ITickableTileEntity, IInventory {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public TileBase(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public abstract ITextComponent getName();
    
    public abstract IIntArray getIntArray();

    public abstract boolean isItemValid(ItemStack stack);
}
