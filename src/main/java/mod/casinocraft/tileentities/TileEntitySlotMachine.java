package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Supplier;

public class TileEntitySlotMachine extends TileEntityBoard {

    public static TileEntityType<TileEntity> TYPE = TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new).build(null);

    public TileEntitySlotMachine(DyeColor color) {
        super(TYPE, color);
    }

    public TileEntitySlotMachine() {
        this(DyeColor.BLACK);
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.slotmachine.name");
    }

    //@Override
    //@Nullable
    //public SPacketUpdateTileEntity getUpdatePacket(){
    //    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    //    write(nbtTagCompound);
    //    return new SPacketUpdateTileEntity(this.pos, CasinoKeeper.TILETYPE_ARCADE.hashCode(), nbtTagCompound);
    //}

}
