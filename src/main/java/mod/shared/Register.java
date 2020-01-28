package mod.shared;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class Register {

    /** registers Block and corresponding ItemBlock to ForgeRegistries */
    public static void registerBlock(Block block, ItemGroup group){
        ForgeRegistries.BLOCKS.register(block);
        BlockItem itemblock = new BlockItem(block, (new Item.Properties()).group(group));
        itemblock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemblock);
    }

    /** registers Block and corresponding ItemBlock to ForgeRegistries */
    public static void registerBlock(Block block){
        ForgeRegistries.BLOCKS.register(block);
        BlockItem itemblock = new BlockItem(block, (new Item.Properties()));
        itemblock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemblock);
    }

    /** registers Block and predefined ItemBlock to ForgeRegistries */
    public static void registerBlock(Block block, BlockItem itemblock){
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(itemblock);
    }

    /** registers Item to ForgeRegistries */
    public static void registerItem(Item item){
        ForgeRegistries.ITEMS.register(item);
    }

    public static void registerTileEntite(TileEntity tileentity){
        ForgeRegistries.TILE_ENTITIES.register(tileentity.getType());
    }

    public static void registerEntity(Entity entity){
        ForgeRegistries.ENTITIES.register(entity.getType());
    }

}
