package mod.shared.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.EnumFacing;

public class BlockPillar extends BlockRotatedPillar {



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Contructor */
    public BlockPillar(String modid, String name, Material material, MaterialColor materialcolor, float hardness, float resistance, SoundType sound) {
        super(Block.Properties.create(material, materialcolor).hardnessAndResistance(hardness, resistance).sound(sound));
        this.setRegistryName(modid, name);
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, EnumFacing.Axis.Y));
    }

    /** Contructor with predefined BlockProperty */
    public BlockPillar(String modid, String name, Block block) {
        super(Block.Properties.from(block));
        this.setRegistryName(modid, name);
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, EnumFacing.Axis.Y));
    }
}
