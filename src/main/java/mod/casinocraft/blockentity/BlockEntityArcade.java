package mod.casinocraft.blockentity;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockEntityArcade extends BlockEntityMachine {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public BlockEntityArcade(BlockPos blockpos, BlockState blockstate, DyeColor color, int id) {
        super(CasinoKeeper.TILE_ARCADE_BASE.get(), blockpos, blockstate, color, id);
    }

    public BlockEntityArcade(BlockPos blockpos, BlockState blockstate) {
        this(blockpos, blockstate, DyeColor.BLACK, 0);
    }




    //----------------------------------------NETWORK----------------------------------------//

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        CompoundTag nbtTagCompound = new CompoundTag();
        save(nbtTagCompound);
        return new ClientboundBlockEntityDataPacket(this.worldPosition, CasinoKeeper.TILE_ARCADE_BASE.get().hashCode(), nbtTagCompound);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public TextComponent getName() {
        return new TextComponent("tile.arcade.name");
    }

}
