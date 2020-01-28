package mod.shared.util;

import com.google.common.collect.Maps;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

import java.util.Map;

public class BurnTimes {

    public static Map<Item, Integer> getBurnTimes() {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        addItemBurnTime(map, Items.LAVA_BUCKET, 20000);
        addItemBurnTime(map, Blocks.COAL_BLOCK, 16000);
        addItemBurnTime(map, Items.BLAZE_ROD, 2400);
        addItemBurnTime(map, Items.COAL, 1600);
        addItemBurnTime(map, Items.CHARCOAL, 1600);
        addItemTagBurnTime(map, ItemTags.LOGS, 300);
        addItemTagBurnTime(map, ItemTags.PLANKS, 300);
        addItemTagBurnTime(map, ItemTags.WOODEN_STAIRS, 300);
        addItemTagBurnTime(map, ItemTags.WOODEN_SLABS, 150);
        addItemTagBurnTime(map, ItemTags.WOODEN_TRAPDOORS, 300);
        addItemTagBurnTime(map, ItemTags.WOODEN_PRESSURE_PLATES, 300);
        addItemBurnTime(map, Blocks.OAK_FENCE, 300);
        addItemBurnTime(map, Blocks.BIRCH_FENCE, 300);
        addItemBurnTime(map, Blocks.SPRUCE_FENCE, 300);
        addItemBurnTime(map, Blocks.JUNGLE_FENCE, 300);
        addItemBurnTime(map, Blocks.DARK_OAK_FENCE, 300);
        addItemBurnTime(map, Blocks.ACACIA_FENCE, 300);
        addItemBurnTime(map, Blocks.OAK_FENCE_GATE, 300);
        addItemBurnTime(map, Blocks.BIRCH_FENCE_GATE, 300);
        addItemBurnTime(map, Blocks.SPRUCE_FENCE_GATE, 300);
        addItemBurnTime(map, Blocks.JUNGLE_FENCE_GATE, 300);
        addItemBurnTime(map, Blocks.DARK_OAK_FENCE_GATE, 300);
        addItemBurnTime(map, Blocks.ACACIA_FENCE_GATE, 300);
        addItemBurnTime(map, Blocks.NOTE_BLOCK, 300);
        addItemBurnTime(map, Blocks.BOOKSHELF, 300);
        addItemBurnTime(map, Blocks.LECTERN, 300);
        addItemBurnTime(map, Blocks.JUKEBOX, 300);
        addItemBurnTime(map, Blocks.CHEST, 300);
        addItemBurnTime(map, Blocks.TRAPPED_CHEST, 300);
        addItemBurnTime(map, Blocks.CRAFTING_TABLE, 300);
        addItemBurnTime(map, Blocks.DAYLIGHT_DETECTOR, 300);
        addItemTagBurnTime(map, ItemTags.BANNERS, 300);
        addItemBurnTime(map, Items.BOW, 300);
        addItemBurnTime(map, Items.FISHING_ROD, 300);
        addItemBurnTime(map, Blocks.LADDER, 300);
        addItemTagBurnTime(map, ItemTags.SIGNS, 200);
        addItemBurnTime(map, Items.WOODEN_SHOVEL, 200);
        addItemBurnTime(map, Items.WOODEN_SWORD, 200);
        addItemBurnTime(map, Items.WOODEN_HOE, 200);
        addItemBurnTime(map, Items.WOODEN_AXE, 200);
        addItemBurnTime(map, Items.WOODEN_PICKAXE, 200);
        addItemTagBurnTime(map, ItemTags.WOODEN_DOORS, 200);
        addItemTagBurnTime(map, ItemTags.BOATS, 200);
        addItemTagBurnTime(map, ItemTags.WOOL, 100);
        addItemTagBurnTime(map, ItemTags.WOODEN_BUTTONS, 100);
        addItemBurnTime(map, Items.STICK, 100);
        addItemTagBurnTime(map, ItemTags.SAPLINGS, 100);
        addItemBurnTime(map, Items.BOWL, 100);
        addItemTagBurnTime(map, ItemTags.CARPETS, 67);
        addItemBurnTime(map, Blocks.DRIED_KELP_BLOCK, 4001);
        addItemBurnTime(map, Items.CROSSBOW, 300);
        addItemBurnTime(map, Blocks.BAMBOO, 50);
        addItemBurnTime(map, Blocks.DEAD_BUSH, 100);
        addItemBurnTime(map, Blocks.SCAFFOLDING, 50);
        addItemBurnTime(map, Blocks.LOOM, 300);
        addItemBurnTime(map, Blocks.BARREL, 300);
        addItemBurnTime(map, Blocks.CARTOGRAPHY_TABLE, 300);
        addItemBurnTime(map, Blocks.FLETCHING_TABLE, 300);
        addItemBurnTime(map, Blocks.SMITHING_TABLE, 300);
        addItemBurnTime(map, Blocks.COMPOSTER, 300);
        return map;
    }

    public static boolean isFuel(ItemStack stack) {
        int ret = stack.getBurnTime();
        return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? BurnTimes.getBurnTimes().getOrDefault(stack.getItem(), 0) : ret) > 0;
    }

    private static void addItemTagBurnTime(Map<Item, Integer> map, Tag<Item> itemTag, int burnTime) {
        for(Item item : itemTag.getAllElements()) {
            map.put(item, burnTime);
        }

    }

    private static void addItemBurnTime(Map<Item, Integer> map, IItemProvider itemProvider, int burnTimeIn) {
        map.put(itemProvider.asItem(), burnTimeIn);
    }

}
