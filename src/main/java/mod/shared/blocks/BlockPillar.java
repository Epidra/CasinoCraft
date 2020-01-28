package mod.shared.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPillar extends Block {
	
	/** Enum Value for Cubic Placement */
	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);
	
	
	
	
	//----------------------------------------CONSTRUCTOR----------------------------------------//
	
	/** Default Constructor */
    public BlockPillar(String name, Material material, float hardness, float resistance) {
		super(material);                                   // sets the material for the block which defines basic settings
		this.setUnlocalizedName(name);                     // sets the name of the block
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS); // sets the creative tab to display the block in
		this.setHardness(hardness);                        // sets how long it takes to break the block
		this.setResistance(resistance);                    // sets the block's resistance against explosions
		this.setSoundType(SoundType.GROUND);               // sets the step sound of a block
		this.setLightOpacity(16);                          // sets how much light is subtracted when going through this block		This is only used if isOpaqueCube() returns false
		this.setLightLevel(0);                             // sets how much light is emitted from the block
		this.setHarvestLevel("pickaxe", 2);                // sets the tool and the tool level to break a block. If you don't use this, the break level is defined by the material
		//this.setBlockUnbreakable();                      // makes the block unbreakable in survival
		this.setTickRandomly(false);                       // if true the block receives random update ticks. Used for example for the decaying of leaves. Important for Update() in TileEntity
		
		      if(material == Material.IRON) { this.setSoundType(SoundType.METAL);
		}else if(material == Material.GLASS){ this.setSoundType(SoundType.GLASS);
		}else if(material == Material.SAND) { this.setSoundType(SoundType.SAND);
		}else if(material == Material.ROCK) { this.setSoundType(SoundType.STONE);
		}else if(material == Material.WOOD) { this.setSoundType(SoundType.WOOD);
		}
	}
    
    
    
    
    //----------------------------------------RENDER----------------------------------------//
    
    
    
    
    
    //----------------------------------------PLACEMENT----------------------------------------//
    
    /** Rotates the Block towards its Axis */
    @Override
    public boolean rotateBlock(net.minecraft.world.World world, BlockPos pos, EnumFacing axis){
        net.minecraft.block.state.IBlockState state = world.getBlockState(pos);
        for (net.minecraft.block.properties.IProperty<?> prop : state.getProperties().keySet()){
            if (prop.getName().equals("axis")){
                world.setBlockState(pos, state.cycleProperty(prop));
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
    //----------------------------------------SUPPORT----------------------------------------//
    
    /** Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate. */
    public IBlockState withRotation(IBlockState state, Rotation rot){
        switch (rot){
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.getValue(AXIS)){
                    case X: return state.withProperty(AXIS, EnumFacing.Axis.Z);
                    case Z: return state.withProperty(AXIS, EnumFacing.Axis.X);
                    default: return state;
                }
            default: return state;
        }
    }
    
    /** Convert the given metadata into a BlockState for this Block */
    public IBlockState getStateFromMeta(int meta){
        EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
        int i = meta & 12;
        if (i == 4){
            enumfacing$axis = EnumFacing.Axis.X;
        } else if (i == 8){
            enumfacing$axis = EnumFacing.Axis.Z;
        }
        return this.getDefaultState().withProperty(AXIS, enumfacing$axis);
    }
    
    /** Convert the BlockState into the correct metadata value */
    public int getMetaFromState(IBlockState state){
        int i = 0;
        EnumFacing.Axis enumfacing$axis = state.getValue(AXIS);
        if (enumfacing$axis == EnumFacing.Axis.X){
            i |= 4;
        } else if (enumfacing$axis == EnumFacing.Axis.Z){
            i |= 8;
        }
        return i;
    }
    
    /** Creates the Block State Container */
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, AXIS);
    }
    
    /** Returns this Block for Silk Touch Effect */
    protected ItemStack getSilkTouchDrop(IBlockState state){
        return new ItemStack(Item.getItemFromBlock(this));
    }
    
    /** Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(AXIS, facing.getAxis());
    }
	
}
