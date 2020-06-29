package mod.casinocraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockBlock extends Block {

    // ...



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Contructor */
    public BlockBlock(Material material, MaterialColor materialcolor, float hardness, float resistance, SoundType sound, int light) {
        super(Properties.create(material, materialcolor).hardnessAndResistance(hardness, resistance).sound(sound));
    }

    /** Contructor with predefined BlockProperty */
    public BlockBlock(Block block) {
        super(Properties.from(block));
    }

    /** Contructor with doesNotBlockMovement */
    public BlockBlock(Material material, MaterialColor materialcolor, float hardness, float resistance, SoundType sound, int light, boolean blockMovement) {
        super(Properties.create(material, materialcolor).hardnessAndResistance(hardness, resistance).sound(sound).doesNotBlockMovement());
    }




    //----------------------------------------PLACEMENT----------------------------------------//

    // ...




    //----------------------------------------INTERACTION----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    // ...




    //----------------------------------------HELPER----------------------------------------//

    // ...

}
