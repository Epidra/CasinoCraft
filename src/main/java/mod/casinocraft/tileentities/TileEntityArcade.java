package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.function.Supplier;


public class TileEntityArcade extends TileEntityBoard {

    public static TileEntityType<TileEntity> TYPE = TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityArcade::new).build(null);

    public TileEntityArcade(DyeColor color) {
        super(TYPE, color);
    }

    public TileEntityArcade() {
        this(DyeColor.BLACK);
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.arcade.name");
    }

    //@Override
    //@Nullable
    //public SPacketUpdateTileEntity getUpdatePacket(){
    //    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    //    write(nbtTagCompound);
    //    return new SPacketUpdateTileEntity(this.pos, CasinoKeeper.TILETYPE_ARCADE.hashCode(), nbtTagCompound);
    //}

}
