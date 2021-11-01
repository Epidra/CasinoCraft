package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class TileEntityCardTableBase extends TileEntityMachine {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public TileEntityCardTableBase(DyeColor color, int id) {
        super(CasinoKeeper.TILE_CARDTABLE_BASE.get(), color, id);
    }

    public TileEntityCardTableBase() {
        this(DyeColor.BLACK, 1);
    }





    //----------------------------------------NETWORK----------------------------------------//

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT nbtTagCompound = new CompoundNBT();
        save(nbtTagCompound);
        return new SUpdateTileEntityPacket(this.worldPosition, CasinoKeeper.TILE_CARDTABLE_BASE.get().hashCode(), nbtTagCompound);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.cardtablebase.name");
    }



}
