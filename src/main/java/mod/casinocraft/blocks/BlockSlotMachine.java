package mod.casinocraft.blocks;

import mod.shared.blocks.IMachinaBasic;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSlotMachine extends IMachinaBasic {
	
	public BlockSlotMachine(String name) {
		super(name, Material.ANVIL);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if (world.isRemote){
            return true;
        } else {
            return true;
        }
    }
	
	public TileEntity createNewTileEntity(World worldIn, int meta){
        return null;
    }
	
}
