package mod.shared.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemItem extends Item {
	
	/** Default Constructor */
	public ItemItem(String name) {
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.MATERIALS);
	}
	
}
