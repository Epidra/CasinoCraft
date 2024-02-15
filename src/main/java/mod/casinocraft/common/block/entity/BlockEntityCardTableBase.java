package mod.casinocraft.common.block.entity;

import mod.casinocraft.Register;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityCardTableBase extends BlockEntityMachine {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public BlockEntityCardTableBase(BlockPos blockpos, BlockState blockstate, DyeColor color, int id) {
		super(Register.TILE_CARDTABLE_BASE.get(), blockpos, blockstate, color, id);
	}
	
	public BlockEntityCardTableBase(BlockPos blockpos, BlockState blockstate) {
		this(blockpos, blockstate, DyeColor.BLACK, 1);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  NETWORK  ---------- ---------- ---------- ---------- //
	
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	public CompoundTag getUpdateTag() {
		CompoundTag tag = this.saveWithoutMetadata();
		saveAdditional(tag);
		return tag;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}
