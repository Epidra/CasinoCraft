package mod.shared.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockBlock extends Block {

    //...



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Contructor */
    public BlockBlock(String modid, String name, Material material, MaterialColor materialcolor, float hardness, float resistance, SoundType sound, int light) {
        super(Properties.create(material, materialcolor).hardnessAndResistance(hardness, resistance).sound(sound).lightValue(light));
        this.setRegistryName(modid, name);
    }

    /** Contructor with predefined BlockProperty */
    public BlockBlock(String modid, String name, Block block) {
        super(Properties.from(block));
        this.setRegistryName(modid, name);
    }

    /** Contructor with doesNotBlockMovement */
    public BlockBlock(String modid, String name, Material material, MaterialColor materialcolor, float hardness, float resistance, SoundType sound, int light, boolean blockMovement) {
        super(Properties.create(material, materialcolor).hardnessAndResistance(hardness, resistance).sound(sound).lightValue(light).doesNotBlockMovement());
        this.setRegistryName(modid, name);
    }



    //----------------------------------------FUNCTION----------------------------------------//

    /** returns Harvest Level */
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

}
