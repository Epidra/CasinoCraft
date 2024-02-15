package mod.casinocraft.common.block.entity;

import mod.casinocraft.Register;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityCardTableWide extends BlockEntityMachine {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public BlockEntityCardTableWide(BlockPos blockpos, BlockState blockstate, DyeColor color, int id) {
		super(Register.TILE_CARDTABLE_WIDE.get(), blockpos, blockstate, color, id);
	}
	
	public BlockEntityCardTableWide(BlockPos blockpos, BlockState blockstate) {
		this(blockpos, blockstate, DyeColor.BLACK, 2);
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
