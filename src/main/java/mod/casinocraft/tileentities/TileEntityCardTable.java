package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Supplier;

public class TileEntityCardTable extends TileEntityBoard {

    public static TileEntityType<TileEntity> TYPE = TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTable::new).build(null);

    public TileEntityCardTable(DyeColor color) {
        super(TYPE, color);
    }

    public TileEntityCardTable() {
        this(DyeColor.BLACK);
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.cardtable.name");
    }

    //@Override
    //@Nullable
    //public SPacketUpdateTileEntity getUpdatePacket(){
    //    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    //    write(nbtTagCompound);
    //    return new SPacketUpdateTileEntity(this.pos, CasinoKeeper.TILETYPE_CARDTABLE.hashCode(), nbtTagCompound);
    //}

}
