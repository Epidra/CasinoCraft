package mod.shared.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockColored extends ItemBlock {
	
	/** Default Constructor */
	public ItemBlockColored(Block block){
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    /** Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is placed as a Block (mostly used with ItemBlocks). */
    public int getMetadata(int damage){
        return damage;
    }
    
    /** Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have different names based on their damage or NBT. */
    public String getUnlocalizedName(ItemStack stack){
    	switch(EnumDyeColor.byMetadata(stack.getMetadata())){
    		case BLACK:      return super.getUnlocalizedName() + "_black";
    		case RED:        return super.getUnlocalizedName() + "_red";
    		case GREEN:      return super.getUnlocalizedName() + "_green";
    		case BROWN:      return super.getUnlocalizedName() + "_brown";
    		case BLUE:       return super.getUnlocalizedName() + "_blue";
    		case PURPLE:     return super.getUnlocalizedName() + "_purple";
    		case CYAN:       return super.getUnlocalizedName() + "_cyan";
    		case SILVER:     return super.getUnlocalizedName() + "_silver";
    		case GRAY:       return super.getUnlocalizedName() + "_gray";
    		case PINK:       return super.getUnlocalizedName() + "_pink";
    		case LIME:       return super.getUnlocalizedName() + "_lime";
    		case YELLOW:     return super.getUnlocalizedName() + "_yellow";
    		case LIGHT_BLUE: return super.getUnlocalizedName() + "_lightblue";
    		case MAGENTA:    return super.getUnlocalizedName() + "_magenta";
    		case ORANGE:     return super.getUnlocalizedName() + "_orange";
    		case WHITE:      return super.getUnlocalizedName() + "_white";
    	}
        return super.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
    }
	
}
