package net.casinocraft.mod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemModules extends Item {
	
	int machine = 0;
	
	public ItemModules(String name, int slot) {
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.MATERIALS);
		machine = slot;
	}
	
	public int Get_Machine(){
		return machine;
	}
	
}
