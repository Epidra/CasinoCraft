package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class TileEntityCardTableWide extends TileEntityMachine {

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
        save(nbtTagCompound);
        return new SUpdateTileEntityPacket(this.worldPosition, CasinoKeeper.TILE_CARDTABLE_WIDE.get().hashCode(), nbtTagCompound);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.cardtablewide.name");
    }

}
