package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static mod.casinocraft.CasinoCraft.MODID;

public class TileEntityCardTableWide extends TileEntityBoard {

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    public TileEntityCardTableWide(DyeColor color, int id) {
        super(CasinoKeeper.TILE_CARDTABLE_WIDE.get(), color, id);
    }

    public TileEntityCardTableWide() {
        this(DyeColor.BLACK, 2);
    }




    //----------------------------------------NETWORK----------------------------------------//

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return new SUpdateTileEntityPacket(this.pos, CasinoKeeper.TILE_CARDTABLE_WIDE.get().hashCode(), nbtTagCompound);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.cardtablewide.name");
    }

    @Override
    public IIntArray getIntArray() {
        return null;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

}
