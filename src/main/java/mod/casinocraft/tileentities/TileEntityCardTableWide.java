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

public class TileEntityCardTableWide extends TileEntityBoard {

    public static TileEntityType<TileEntity> TYPE_BLACK     = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_BLACK    ).build(null).setRegistryName(MODID, "cardtable_wide_black");
    public static TileEntityType<TileEntity> TYPE_BLUE      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_BLUE     ).build(null).setRegistryName(MODID, "cardtable_wide_blue");
    public static TileEntityType<TileEntity> TYPE_BROWN     = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_BROWN    ).build(null).setRegistryName(MODID, "cardtable_wide_brown");
    public static TileEntityType<TileEntity> TYPE_CYAN      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_CYAN     ).build(null).setRegistryName(MODID, "cardtable_wide_cyan");
    public static TileEntityType<TileEntity> TYPE_GRAY      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_GRAY     ).build(null).setRegistryName(MODID, "cardtable_wide_gray");
    public static TileEntityType<TileEntity> TYPE_GREEN     = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_GREEN    ).build(null).setRegistryName(MODID, "cardtable_wide_green");
    public static TileEntityType<TileEntity> TYPE_LIGHTBLUE = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_LIGHTBLUE).build(null).setRegistryName(MODID, "cardtable_wide_lightblue");
    public static TileEntityType<TileEntity> TYPE_LIME      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_LIME     ).build(null).setRegistryName(MODID, "cardtable_wide_lime");
    public static TileEntityType<TileEntity> TYPE_MAGENTA   = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_MAGENTA  ).build(null).setRegistryName(MODID, "cardtable_wide_magenta");
    public static TileEntityType<TileEntity> TYPE_ORANGE    = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_ORANGE   ).build(null).setRegistryName(MODID, "cardtable_wide_orange");
    public static TileEntityType<TileEntity> TYPE_PINK      = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_PINK     ).build(null).setRegistryName(MODID, "cardtable_wide_pink");
    public static TileEntityType<TileEntity> TYPE_PURPLE    = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_PURPLE   ).build(null).setRegistryName(MODID, "cardtable_wide_purple");
    public static TileEntityType<TileEntity> TYPE_RED       = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_RED      ).build(null).setRegistryName(MODID, "cardtable_wide_red");
    public static TileEntityType<TileEntity> TYPE_SILVER    = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_SILVER   ).build(null).setRegistryName(MODID, "cardtable_wide_silver");
    public static TileEntityType<TileEntity> TYPE_WHITE     = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_WHITE    ).build(null).setRegistryName(MODID, "cardtable_wide_white");
    public static TileEntityType<TileEntity> TYPE_YELLOW    = (TileEntityType<TileEntity>) TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityCardTableWide::new, CasinoKeeper.CARDTABLE_WIDE_YELLOW   ).build(null).setRegistryName(MODID, "cardtable_wide_yellow");

    public TileEntityCardTableWide(DyeColor color, int id) {
        super(getType(color), color, id);
    }

    public TileEntityCardTableWide() {
        this(DyeColor.BLACK, 2);
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("tile.cardtablewide.name");
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
