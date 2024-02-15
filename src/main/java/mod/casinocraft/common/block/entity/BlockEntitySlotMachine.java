package mod.casinocraft.common.block.entity;

import mod.casinocraft.Register;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntitySlotMachine extends BlockEntityMachine {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public BlockEntitySlotMachine(BlockPos blockpos, BlockState blockstate, DyeColor color, int id) {
		super(Register.TILE_ARCADE_SLOT.get(), blockpos, blockstate, color, id);
	}
	
	public BlockEntitySlotMachine(BlockPos blockpos, BlockState blockstate) {
		this(blockpos, blockstate, DyeColor.BLACK, 3);
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
