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

public class TileEntityArcade extends TileEntityMachine {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public TileEntityArcade(DyeColor color, int id) {
        super(CasinoKeeper.TILE_ARCADE_BASE.get(), color, id);
    }

    public TileEntityArcade() {
        this(DyeColor.BLACK, 0);
    }




    //----------------------------------------NETWORK----------------------------------------//

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT nbtTagCompound = new CompoundNBT();
        save(nbtTagCompound);
        return new SUpdateTileEntityPacket(this.worldPosition, CasinoKeeper.TILE_ARCADE_BASE.get().hashCode(), nbtTagCompound);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.arcade.name");
    }

}
