package mod.casinocraft.blocks;

import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockBlock extends Block {

    // ...



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Contructor */
    public BlockBlock(Material material, MaterialColor materialcolor, float hardness, float resistance, SoundType sound, int light) {
        super(Properties.create(material, materialcolor).hardnessAndResistance(hardness, resistance).sound(sound));
    }

    /** Contructor with predefined BlockProperty */
    public BlockBlock(Block block) {
        super(Properties.from(block));
    }

    /** Contructor with doesNotBlockMovement */
    public BlockBlock(Material material, MaterialColor materialcolor, float hardness, float resistance, SoundType sound, int light, boolean blockMovement) {
        super(Properties.create(material, materialcolor).hardnessAndResistance(hardness, resistance).sound(sound).doesNotBlockMovement());
    }




    //----------------------------------------PLACEMENT----------------------------------------//

    // ...




    //----------------------------------------INTERACTION----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    // ...




    //----------------------------------------HELPER----------------------------------------//

    protected BlockPos getTilePosition(BlockPos pos, boolean isPrimary, Direction facing){
        if(facing == Direction.DOWN || facing == Direction.UP){
            return isPrimary ? pos : pos.down();
        } else {
            if(!isPrimary) facing = facing.getOpposite();
            BlockPos pos2 = pos;
            if(!isPrimary){
                if(facing == Direction.EAST)  pos2 = pos.north();
                if(facing == Direction.WEST)  pos2 = pos.south();
                if(facing == Direction.NORTH) pos2 = pos.west();
                if(facing == Direction.SOUTH) pos2 = pos.east();
            }
            return pos2;
        }
    }

    protected void spawnInventory(World world, BlockPos pos, TileEntityMachine te){
        if(te != null && te.settingDropItemsOnBreak){
            te.inventory.set(3, new ItemStack(te.inventory.get(3).getItem(), te.storageToken));
            te.inventory.set(4, new ItemStack(te.inventory.get(4).getItem(), te.storageToken));
            InventoryHelper.dropInventoryItems(world, pos, te);
            world.updateComparatorOutputLevel(pos, this);
        }
    }

}
