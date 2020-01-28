package mod.shared.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;

public class BlockPillar extends RotatedPillarBlock {

    //...



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Contructor */
    public BlockPillar(String modid, String name, Material material, MaterialColor materialcolor, float hardness, float resistance, SoundType sound) {
        super(Properties.create(material, materialcolor).hardnessAndResistance(hardness, resistance).sound(sound));
        this.setRegistryName(modid, name);
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y));
    }

    /** Contructor with predefined BlockProperty */
    public BlockPillar(String modid, String name, Block block) {
        super(Properties.from(block));
        this.setRegistryName(modid, name);
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y));
    }
}
