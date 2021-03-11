package mod.casinocraft.container;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.blocks.ContainerArcade;
import mod.casinocraft.container.blocks.ContainerCardTable;
import mod.casinocraft.container.blocks.ContainerSlotMachine;
import mod.casinocraft.container.card.*;
import mod.casinocraft.container.chip.*;
import mod.casinocraft.container.mino.*;
import mod.casinocraft.container.other.ContainerDummy;
import mod.casinocraft.container.other.ContainerSlotGame;
import mod.casinocraft.tileentities.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerProvider implements INamedContainerProvider {

    public TileEntityMachine board;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ContainerProvider(@Nonnull TileEntityMachine tile) {
        this.board = tile;
    }




    //----------------------------------------FUNCTION----------------------------------------//

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        Item module = this.board.getModule();
        boolean gainAccess = this.board.getKey() == Blocks.AIR.asItem() || (this.board.getKey() == playerInventory.mainInventory.get(playerInventory.currentItem).getItem() && playerInventory.mainInventory.get(playerInventory.currentItem).getDisplayName().getString().matches(this.board.inventory.get(0).getDisplayName().getString()));
        if(this.board instanceof TileEntityArcade){
            if(gainAccess)                                          return new ContainerArcade(       windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_WHITE.get())      return new ContainerChipWhite(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_ORANGE.get())     return new ContainerChipOrange(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_MAGENTA.get())    return new ContainerChipMagenta(  windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_LIGHT_BLUE.get()) return new ContainerChipLightBlue(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_YELLOW.get())     return new ContainerChipYellow(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_LIME.get())       return new ContainerChipLime(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_PINK.get())       return new ContainerChipPink(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_GRAY.get())       return new ContainerChipGray(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_LIGHT_GRAY.get()) return new ContainerChipLightGray(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_CYAN.get())       return new ContainerChipCyan(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_PURPLE.get())     return new ContainerChipPurple(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_BLUE.get())       return new ContainerChipBlue(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_BROWN.get())      return new ContainerChipBrown(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_GREEN.get())      return new ContainerChipGreen(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_RED.get())        return new ContainerChipRed(      windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_BLACK.get())      return new ContainerChipBlack(    windowId, playerInventory, this.board);
        }
        else if(this.board instanceof TileEntityCardTableBase || this.board instanceof TileEntityCardTableWide){
            if(gainAccess)                                          return new ContainerCardTable(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_WHITE.get())      return new ContainerCardWhite(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_ORANGE.get())     return new ContainerCardOrange(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_MAGENTA.get())    return new ContainerCardMagenta(  windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_LIGHT_BLUE.get()) return new ContainerCardLightBlue(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_YELLOW.get())     return new ContainerCardYellow(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_LIME.get())       return new ContainerCardLime(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_PINK.get())       return new ContainerCardPink(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_GRAY.get())       return new ContainerCardGray(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_LIGHT_GRAY.get()) return new ContainerCardLightGray(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_CYAN.get())       return new ContainerCardCyan(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_PURPLE.get())     return new ContainerCardPurple(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_BLUE.get())       return new ContainerCardBlue(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_BROWN.get())      return new ContainerCardBrown(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_GREEN.get())      return new ContainerCardGreen(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_RED.get())        return new ContainerCardRed(      windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_BLACK.get())      return new ContainerCardBlack(    windowId, playerInventory, this.board);

            if(module == CasinoKeeper.MODULE_MINO_WHITE.get())      return new ContainerMinoWhite(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_ORANGE.get())     return new ContainerMinoOrange(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_MAGENTA.get())    return new ContainerMinoMagenta(  windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_LIGHT_BLUE.get()) return new ContainerMinoLightBlue(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_YELLOW.get())     return new ContainerMinoYellow(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_LIME.get())       return new ContainerMinoLime(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_PINK.get())       return new ContainerMinoPink(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_GRAY.get())       return new ContainerMinoGray(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_LIGHT_GRAY.get()) return new ContainerMinoLightGray(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_CYAN.get())       return new ContainerMinoCyan(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_PURPLE.get())     return new ContainerMinoPurple(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_BLUE.get())       return new ContainerMinoBlue(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_BROWN.get())      return new ContainerMinoBrown(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_GREEN.get())      return new ContainerMinoGreen(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_RED.get())        return new ContainerMinoRed(      windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_BLACK.get())      return new ContainerMinoBlack(    windowId, playerInventory, this.board);
        } else if(this.board instanceof TileEntitySlotMachine) {
            if(gainAccess)                                          return new ContainerSlotMachine(  windowId, playerInventory, this.board);
                                                                    return new ContainerSlotGame(     windowId, playerInventory, this.board);
        }
        return new ContainerDummy(windowId, playerInventory, this.board);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public ITextComponent getDisplayName() {
        return this.board.getName();
    }

}

