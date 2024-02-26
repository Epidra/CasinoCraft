package mod.casinocraft.menu;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.block.MenuArcade;
import mod.casinocraft.menu.block.MenuCardTable;
import mod.casinocraft.menu.block.MenuSlotMachine;
import mod.casinocraft.menu.game.*;
import mod.casinocraft.menu.other.MenuDummy;
import mod.casinocraft.menu.other.MenuSlotGame;
import mod.casinocraft.blockentity.*;
import net.minecraft.network.chat.Component;
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
            if(gainAccess)                                return new MenuArcade(windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_5_1.get()) return new Menu51(    windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_5_2.get()) return new Menu52(    windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_6_1.get()) return new Menu61(    windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_6_2.get()) return new Menu62(    windowId, playerInventory, (BlockEntityMachine) this.board);
        }
        else if(this.board instanceof BlockEntityCardTableBase || this.board instanceof BlockEntityCardTableWide){
            if(gainAccess)                                return new MenuCardTable(windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_1_1.get()) return new Menu11(       windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_2_1.get()) return new Menu21(       windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_2_2.get()) return new Menu22(       windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_3_1.get()) return new Menu31(       windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_3_2.get()) return new Menu32(       windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_3_3.get()) return new Menu33(       windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_4_1.get()) return new Menu41(       windowId, playerInventory, (BlockEntityMachine) this.board);
            if(module == CasinoKeeper.RULEBOOK_4_2.get()) return new Menu42(       windowId, playerInventory, (BlockEntityMachine) this.board);
        } else if(this.board instanceof BlockEntitySlotMachine) {
            if(gainAccess){                                         return new MenuSlotMachine(  windowId, playerInventory, this.board); }
            return new MenuSlotGame(     windowId, playerInventory, this.board);
        }
        return new MenuDummy(windowId, playerInventory, this.board);
    }



    //----------------------------------------SUPPORT----------------------------------------//

    //@Override
    //public TextComponent getDisplayName() {
    //    return this.board.getName();
    //}

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }



}

