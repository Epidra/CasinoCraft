package mod.shared.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemItem extends Item {

    //...



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor */
    public ItemItem(String modid, String name, ItemGroup group){
        super(new Properties().group(group));
        this.setRegistryName(modid, name);
    }
}
