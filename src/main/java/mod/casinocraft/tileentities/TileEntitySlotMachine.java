package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static mod.casinocraft.CasinoCraft.MODID;

public class TileEntitySlotMachine extends TileEntityBoard {

    public static TileEntityType<TileEntity> TYPE_BLACK     = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_BLACK    ).build(null).setRegistryName(MODID, "slotmachine_black");
    public static TileEntityType<TileEntity> TYPE_BLUE      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_BLUE     ).build(null).setRegistryName(MODID, "slotmachine_blue");
    public static TileEntityType<TileEntity> TYPE_BROWN     = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_BROWN    ).build(null).setRegistryName(MODID, "slotmachine_brown");
    public static TileEntityType<TileEntity> TYPE_CYAN      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_CYAN     ).build(null).setRegistryName(MODID, "slotmachine_cyan");
    public static TileEntityType<TileEntity> TYPE_GRAY      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_GRAY     ).build(null).setRegistryName(MODID, "slotmachine_gray");
    public static TileEntityType<TileEntity> TYPE_GREEN     = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_GREEN    ).build(null).setRegistryName(MODID, "slotmachine_green");
    public static TileEntityType<TileEntity> TYPE_LIGHTBLUE = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_LIGHTBLUE).build(null).setRegistryName(MODID, "slotmachine_lightblue");
    public static TileEntityType<TileEntity> TYPE_LIME      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_LIME     ).build(null).setRegistryName(MODID, "slotmachine_lime");
    public static TileEntityType<TileEntity> TYPE_MAGENTA   = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_MAGENTA  ).build(null).setRegistryName(MODID, "slotmachine_magenta");
    public static TileEntityType<TileEntity> TYPE_ORANGE    = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_ORANGE   ).build(null).setRegistryName(MODID, "slotmachine_orange");
    public static TileEntityType<TileEntity> TYPE_PINK      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_PINK     ).build(null).setRegistryName(MODID, "slotmachine_pink");
    public static TileEntityType<TileEntity> TYPE_PURPLE    = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_PURPLE   ).build(null).setRegistryName(MODID, "slotmachine_purple");
    public static TileEntityType<TileEntity> TYPE_RED       = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_RED      ).build(null).setRegistryName(MODID, "slotmachine_red");
    public static TileEntityType<TileEntity> TYPE_SILVER    = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_SILVER   ).build(null).setRegistryName(MODID, "slotmachine_silver");
    public static TileEntityType<TileEntity> TYPE_WHITE     = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_WHITE    ).build(null).setRegistryName(MODID, "slotmachine_white");
    public static TileEntityType<TileEntity> TYPE_YELLOW    = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntitySlotMachine::new, CasinoKeeper.ARCADE_BASE_YELLOW   ).build(null).setRegistryName(MODID, "slotmachine_yellow");

    public TileEntitySlotMachine(DyeColor color, int id) {
        super(getType(color), color, id);
    }

    public TileEntitySlotMachine() {
        this(DyeColor.BLACK, 3);
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.slotmachine.name");
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return new SUpdateTileEntityPacket(this.pos, getType(color).hashCode(), nbtTagCompound);
    }

    public static TileEntityType<TileEntity> getType(DyeColor color){
        switch(color){
            case BLACK     : return TYPE_BLACK    ;
            case BLUE      : return TYPE_BLUE     ;
            case BROWN     : return TYPE_BROWN    ;
            case CYAN      : return TYPE_CYAN     ;
            case GRAY      : return TYPE_GRAY     ;
            case GREEN     : return TYPE_GREEN    ;
            case LIGHT_BLUE: return TYPE_LIGHTBLUE;
            case LIME      : return TYPE_LIME     ;
            case MAGENTA   : return TYPE_MAGENTA  ;
            case ORANGE    : return TYPE_ORANGE   ;
            case PINK      : return TYPE_PINK     ;
            case PURPLE    : return TYPE_PURPLE   ;
            case RED       : return TYPE_RED      ;
            case LIGHT_GRAY: return TYPE_SILVER   ;
            case WHITE     : return TYPE_WHITE    ;
            case YELLOW    : return TYPE_YELLOW   ;
        }
        return TYPE_BLACK;
    }

}
