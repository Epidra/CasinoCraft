package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerArcade;
import mod.casinocraft.container.ContainerCardTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

import javax.annotation.Nullable;

public class TileEntityCardTable extends TileEntityBoard {

    public TileEntityCardTable(){
        super(false);
    }

    @Override
    public String getGuiID() {
        return CasinoKeeper.GUIID_CARDTABLE.toString();
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerCardTable(playerInventory, this);
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket(){
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        write(nbtTagCompound);
        return new SPacketUpdateTileEntity(this.pos, CasinoKeeper.TILETYPE_CARDTABLE.hashCode(), nbtTagCompound);
    }
}
