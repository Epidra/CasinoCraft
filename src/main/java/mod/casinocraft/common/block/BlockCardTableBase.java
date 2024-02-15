package mod.casinocraft.common.block;

import mod.casinocraft.Register;
import mod.casinocraft.common.block.entity.BlockEntityCardTableBase;
import mod.casinocraft.common.block.entity.BlockEntityMachine;
import mod.casinocraft.system.MenuProvider;
import mod.lucky77.block.base.MachinaBase;
import mod.lucky77.block.entity.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockCardTableBase extends MachinaBase implements EntityBlock {
	
	private static final VoxelShape AABB = Block.box(1, 0, 1, 15, 16, 15);
	
	private final DyeColor color;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Contructor with predefined BlockProperty */
	public BlockCardTableBase(Block block, DyeColor color) {
		super(block);
		this.color = color;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  PLACEMENT  ---------- ---------- ---------- ---------- //
	
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		BlockEntityMachine tile = (BlockEntityMachine) world.getBlockEntity(pos);
		if(tile != null && tile.settingDropItemsOnBreak) {
			tile.setItem(3, new ItemStack(tile.getItem(3).getItem(), tile.storageToken));
			tile.setItem(4, new ItemStack(tile.getItem(4).getItem(), tile.storageToken));
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INTERACTION  ---------- ---------- ---------- ---------- //
	
	@Override
	public void interact(Level world, BlockPos pos, Player player, BlockEntityBase tile) {
		NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider((BlockEntityMachine)tile), buf -> buf.writeBlockPos(pos));
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return AABB;
	}
	
	public float getDestroyProgress(BlockState state, Player player, BlockGetter worldIn, BlockPos pos) {
		BlockEntityMachine tileEntity = (BlockEntityMachine) worldIn.getBlockEntity(pos);
		float f = state.getDestroySpeed(worldIn, pos);
		if(tileEntity.settingIndestructableBlock) f *= 1000;
		if (f == -1.0F) {
			return 0.0F;
		} else {
			int i = net.minecraftforge.common.ForgeHooks.isCorrectToolForDrops(state, player) ? 30 : 100;
			return player.getDigSpeed(state, pos) / f / (float)i;
		}
	}
	
	public float getExplosionResistance(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion){
		BlockEntityMachine tileEntity = (BlockEntityMachine) world.getBlockEntity(pos);
		return this.asBlock().getExplosionResistance() * (tileEntity.settingIndestructableBlock ? 1000 : 1);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BLOCK ENTITY  ---------- ---------- ---------- ---------- //
	
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BlockEntityCardTableBase(pos, state, color, 1);
	}
	
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTicker(level, type, (BlockEntityType<? extends BlockEntityCardTableBase>) Register.TILE_CARDTABLE_BASE.get());
	}
	
	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends BlockEntityCardTableBase> typeCustom) {
		return createTickerHelper(type, typeCustom, BlockEntityCardTableBase::serverTick);
	}
	
	
	
}
