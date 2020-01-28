package mod.shared.util;

import net.minecraft.nbt.CompoundNBT;

public abstract class ContainerContent {

    public ContainerContent(){

    }

    public void read(CompoundNBT compound) {
    }

    public void write(CompoundNBT compound) {
    }

    public boolean add(String string) {
        return false;
    }
}
