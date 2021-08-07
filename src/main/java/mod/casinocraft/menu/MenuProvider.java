package mod.casinocraft.menu;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.block.MenuArcade;
import mod.casinocraft.menu.block.MenuCardTable;
import mod.casinocraft.menu.block.MenuSlotMachine;
import mod.casinocraft.menu.card.*;
import mod.casinocraft.menu.chip.*;
import mod.casinocraft.menu.mino.*;
import mod.casinocraft.menu.other.MenuDummy;
import mod.casinocraft.menu.other.MenuSlotGame;
import mod.casinocraft.blockentity.*;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MenuProvider implements net.minecraft.world.MenuProvider {

    public BlockEntityMachine board;




    //----------------------------------------CONSTRUCTOR----------------------------------------//


    public MenuProvider(@Nonnull BlockEntityMachine tile) {
        this.board = tile;
    }




    //----------------------------------------MENU----------------------------------------//

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        Item module = this.board.getItem(1).getItem();
        boolean gainAccess = this.board.getItem(0).getItem() == Blocks.AIR.asItem() || (this.board.getItem(0).getItem() == playerInventory.getItem(playerInventory.selected).getItem() && playerInventory.getItem(playerInventory.selected).getHoverName().getString().matches(this.board.getItem(0).getHoverName().getString()));
        if(this.board instanceof BlockEntityArcade){
            if(gainAccess)                                          return new MenuArcade(       windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_WHITE.get())      return new MenuChipWhite(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_ORANGE.get())     return new MenuChipOrange(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_MAGENTA.get())    return new MenuChipMagenta(  windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_LIGHT_BLUE.get()) return new MenuChipLightBlue(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_YELLOW.get())     return new MenuChipYellow(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_LIME.get())       return new MenuChipLime(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_PINK.get())       return new MenuChipPink(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_GRAY.get())       return new MenuChipGray(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_LIGHT_GRAY.get()) return new MenuChipLightGray(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_CYAN.get())       return new MenuChipCyan(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_PURPLE.get())     return new MenuChipPurple(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_BLUE.get())       return new MenuChipBlue(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_BROWN.get())      return new MenuChipBrown(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_GREEN.get())      return new MenuChipGreen(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_RED.get())        return new MenuChipRed(      windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CHIP_BLACK.get())      return new MenuChipBlack(    windowId, playerInventory, this.board);
        }
        else if(this.board instanceof BlockEntityCardTableBase || this.board instanceof BlockEntityCardTableWide){
            if(gainAccess)                                          return new MenuCardTable(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_WHITE.get())      return new MenuCardWhite(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_ORANGE.get())     return new MenuCardOrange(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_MAGENTA.get())    return new MenuCardMagenta(  windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_LIGHT_BLUE.get()) return new MenuCardLightBlue(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_YELLOW.get())     return new MenuCardYellow(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_LIME.get())       return new MenuCardLime(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_PINK.get())       return new MenuCardPink(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_GRAY.get())       return new MenuCardGray(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_LIGHT_GRAY.get()) return new MenuCardLightGray(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_CYAN.get())       return new MenuCardCyan(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_PURPLE.get())     return new MenuCardPurple(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_BLUE.get())       return new MenuCardBlue(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_BROWN.get())      return new MenuCardBrown(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_GREEN.get())      return new MenuCardGreen(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_RED.get())        return new MenuCardRed(      windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_BLACK.get())      return new MenuCardBlack(    windowId, playerInventory, this.board);

            if(module == CasinoKeeper.MODULE_MINO_WHITE.get())      return new MenuMinoWhite(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_ORANGE.get())     return new MenuMinoOrange(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_MAGENTA.get())    return new MenuMinoMagenta(  windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_LIGHT_BLUE.get()) return new MenuMinoLightBlue(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_YELLOW.get())     return new MenuMinoYellow(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_LIME.get())       return new MenuMinoLime(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_PINK.get())       return new MenuMinoPink(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_GRAY.get())       return new MenuMinoGray(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_LIGHT_GRAY.get()) return new MenuMinoLightGray(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_CYAN.get())       return new MenuMinoCyan(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_PURPLE.get())     return new MenuMinoPurple(   windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_BLUE.get())       return new MenuMinoBlue(     windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_BROWN.get())      return new MenuMinoBrown(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_GREEN.get())      return new MenuMinoGreen(    windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_RED.get())        return new MenuMinoRed(      windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_MINO_BLACK.get())      return new MenuMinoBlack(    windowId, playerInventory, this.board);
        } else if(this.board instanceof BlockEntitySlotMachine) {
            if(gainAccess)                                          return new MenuSlotMachine(  windowId, playerInventory, this.board);
                                                                    return new MenuSlotGame(     windowId, playerInventory, this.board);
        }
        return new MenuDummy(windowId, playerInventory, this.board);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public TextComponent getDisplayName() {
        return this.board.getName();
    }

}

