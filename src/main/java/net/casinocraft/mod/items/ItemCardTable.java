package net.casinocraft.mod.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCardTable extends ItemBlock {
	
    public ItemCardTable(Block block){
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    /**
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
    public int getMetadata(int damage){
        return damage;
    }
    
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack){
    	switch(EnumDyeColor.byMetadata(stack.getMetadata())){
    		case BLACK:      return super.getUnlocalizedName() + "Black";
    		case RED:        return super.getUnlocalizedName() + "Red";
    		case GREEN:      return super.getUnlocalizedName() + "Green";
    		case BROWN:      return super.getUnlocalizedName() + "Brown";
    		case BLUE:       return super.getUnlocalizedName() + "Blue";
    		case PURPLE:     return super.getUnlocalizedName() + "Purple";
    		case CYAN:       return super.getUnlocalizedName() + "Cyan";
    		case SILVER:     return super.getUnlocalizedName() + "Silver";
    		case GRAY:       return super.getUnlocalizedName() + "Gray";
    		case PINK:       return super.getUnlocalizedName() + "Pink";
    		case LIME:       return super.getUnlocalizedName() + "Lime";
    		case YELLOW:     return super.getUnlocalizedName() + "Yellow";
    		case LIGHT_BLUE: return super.getUnlocalizedName() + "LightBlue";
    		case MAGENTA:    return super.getUnlocalizedName() + "Magenta";
    		case ORANGE:     return super.getUnlocalizedName() + "Orange";
    		case WHITE:      return super.getUnlocalizedName() + "White";
    	}
        return super.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
    }
}