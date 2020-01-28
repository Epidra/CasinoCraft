package mod.shared.recipes;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class RecipeHandler {
	public static void RegisterRecipes() {
		
	}

	public static void removeRecipes() {
		 ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>)ForgeRegistries.RECIPES;
	        ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValues());
	        
	        for (IRecipe r : recipes)
            {
                ItemStack output = r.getRecipeOutput();
                if (output.getItem() == Item.getItemFromBlock(Blocks.PLANKS))
                {
                    recipeRegistry.remove(r.getRegistryName());
                    recipeRegistry.register(DummyRecipe.from(r));
                }
            }
	}
}
