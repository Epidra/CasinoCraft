package mod.shared;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import mod.shared.items.ItemBlockColored;
import mod.shared.recipes.DummyRecipe;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class Register {
	
	/** ??? */
	public static void registerBlock(Block block, boolean pre, String modid){
		if(pre){
			ForgeRegistries.BLOCKS.register(block);
			ForgeRegistries.ITEMS.register(new ItemBlock(block).setUnlocalizedName(block.getUnlocalizedName().substring(5)).setRegistryName(block.getRegistryName()));
		}else{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
		}
	}
	
	/** ??? */
	public static void registerItem(Item item, boolean pre, String modid){
		if(pre){
			ForgeRegistries.ITEMS.register(item);
		}else{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		}
	}
	
	/** ??? */
	public static void registerBlockAndItem(Block block, Item item, boolean pre, String modid){
		if(pre){
			ForgeRegistries.BLOCKS.register(block);
			ForgeRegistries.ITEMS.register(item);
			ForgeRegistries.ITEMS.register(new ItemBlock(block).setUnlocalizedName(block.getUnlocalizedName().substring(5)).setRegistryName(block.getRegistryName()));
		}else{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		}
	}
	
	/** ??? */
	public static void registerColoredBlock(Block block, boolean pre, String modid){
		if(pre){
			ForgeRegistries.BLOCKS.register(block);
			ForgeRegistries.ITEMS.register(new ItemBlockColored(block).setUnlocalizedName(block.getUnlocalizedName().substring(5)).setRegistryName(block.getRegistryName()));
		}else{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 15, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),     "color=black"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 14, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),       "color=red"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 13, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),     "color=green"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 12, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),     "color=brown"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 11, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),      "color=blue"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 10, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),    "color=purple"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  9, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),      "color=cyan"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  8, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),    "color=silver"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  7, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),      "color=gray"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  6, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),      "color=pink"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  5, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),      "color=lime"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  4, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),    "color=yellow"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  3, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),"color=light_blue"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  2, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),   "color=magenta"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  1, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),    "color=orange"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5),     "color=white"));
		}
	}
	
	/** ??? */
	public static void registerEntity(Class<? extends Entity> entity, String name, Render<? extends Entity> renderer, Object instance, String modid){
		EntityRegistry.registerModEntity(new ResourceLocation(modid, name), entity, name, 0, instance, 64, 20, true); // missing Resource Location
	}
	
	public static void registerEntity(Class<? extends Entity> entity, String name, Object instance, String modid){
		EntityRegistry.registerModEntity(new ResourceLocation(modid, name), entity, name, 0, instance, 64, 20, true); // missing Resource Location
	}

	public static SoundEvent registerSound(String string, String modid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void removeRecipes(Block block) {
		 ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>)ForgeRegistries.RECIPES;
	        ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValues());
	        
	        for (IRecipe r : recipes)
           {
               ItemStack output = r.getRecipeOutput();
               if (output.getItem() == Item.getItemFromBlock(block))
               {
                   recipeRegistry.remove(r.getRegistryName());
                   recipeRegistry.register(DummyRecipe.from(r));
               }
           }
	}
	
}
