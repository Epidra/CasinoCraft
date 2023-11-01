package mod.casinocraft.item;

import mod.lucky77.item.ItemBook;
import net.minecraft.world.item.CreativeModeTab;

import static mod.casinocraft.CasinoCraft.MODID;

public class ItemBookArcade extends ItemBook {

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor */
    public ItemBookArcade() {
        super(null, 2);
        this.addPage("book.arcade.header1", "book.arcade.body1", "", -1, MODID);
        this.addPage("book.arcade.header2", "book.arcade.body2", "", -1, MODID);
        this.addPage("book.arcade.header3", "", "textures/gui/spritesheet/cards_1_noir.png", 0, MODID);
        this.addPage("book.arcade.header4", "", "textures/gui/spritesheet/cards_1_noir.png", 1, MODID);
    }

}
