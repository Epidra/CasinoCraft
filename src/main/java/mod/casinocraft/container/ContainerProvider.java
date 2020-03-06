package mod.casinocraft.container;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.card.*;
import mod.casinocraft.container.clay.*;
import mod.casinocraft.container.dust.*;
import mod.casinocraft.container.other.ContainerField;
import mod.casinocraft.container.other.ContainerNoise;
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
    public TileEntityBoard board;

    public ContainerProvider(@Nonnull TileEntityBoard tile) {
        this.board = tile;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        Item heldItem = playerInventory.mainInventory.get(playerInventory.currentItem).getItem();
        Item key = this.board.getKey();
        Item module = this.board.getModule();
        if(this.board instanceof TileEntityArcade){
            if(heldItem == key) return new ContainerArcade(windowId, playerInventory, this.board);
            if(key == Blocks.AIR.asItem()) return new ContainerArcade(windowId, playerInventory, this.board);
            //new BeaconContainer(p_createMenu_1_, p_createMenu_2_, this.int_array, IWorldPosCallable.of(this.world, this.getPos()))
            if(module == CasinoKeeper.MODULE_DUST_WHITE)     return new Container2048(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_ORANGE)    return new ContainerNoise(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_DUST_MAGENTA)   return new ContainerSokoban(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_DUST_LIGHTBLUE) return new ContainerMeanMinos(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_YELLOW)    return new ContainerNoise(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_LIME)      return new ContainerNoise(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_PINK)      return new ContainerNoise(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_GRAY)      return new ContainerNoise(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_SILVER)    return new ContainerNoise(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_DUST_CYAN)      return new ContainerColumns(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_PURPLE)    return new ContainerNoise(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_DUST_BLUE)      return new ContainerTetris(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_BROWN)     return new ContainerNoise(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_GREEN)     return new ContainerNoise(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_DUST_RED)       return new ContainerSnake(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_DUST_BLACK)     return new ContainerNoise(windowId, playerInventory, this.board);
            return new ContainerNoise(windowId, playerInventory, this.board);
        }
        else if(this.board instanceof TileEntityCardTableBase || this.board instanceof TileEntityCardTableWide){
            if(heldItem == key) return new ContainerCardTable(windowId, playerInventory, this.board);
            if(key == Blocks.AIR.asItem()) return new ContainerCardTable(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_WHITE)     return new ContainerAceyDeucey(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CARD_ORANGE)    return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_MAGENTA)   return new ContainerTriPeak(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CARD_LIGHTBLUE) return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_YELLOW)    return new ContainerFreeCell(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CARD_LIME)      return new ContainerField(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CARD_PINK)      return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_GRAY)      return new ContainerBaccarat(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_SILVER)    return new ContainerVideoPoker(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_CYAN)      return new ContainerKlondike(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_PURPLE)    return new ContainerPyramid(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CARD_BLUE)      return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_BROWN)     return new ContainerSpider(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CARD_GREEN)     return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_RED)       return new ContainerRougeEtNoir(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CARD_BLACK)     return new ContainerBlackJack(windowId, playerInventory, this.board);

            if(module == CasinoKeeper.MODULE_CLAY_WHITE)     return new ContainerSudoku(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_ORANGE)    return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CLAY_MAGENTA)   return new ContainerCraps(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_LIGHTBLUE) return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CLAY_YELLOW)    return new ContainerMysticSquare(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_LIME)      return new ContainerField(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_PINK)      return new ContainerField(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_GRAY)      return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CLAY_SILVER)    return new ContainerMinesweeper(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_CYAN)      return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CLAY_PURPLE)    return new ContainerSicBo(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_BLUE)      return new ContainerField(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_BROWN)     return new ContainerField(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CLAY_GREEN)     return new ContainerMemory(windowId, playerInventory, this.board);
            if(module == CasinoKeeper.MODULE_CLAY_RED)       return new ContainerRoulette(windowId, playerInventory, this.board);
            //if(module == CasinoKeeper.MODULE_CLAY_BLACK)     return new ContainerField(windowId, playerInventory, this.board);
            return new ContainerField(windowId, playerInventory, this.board);
        } else if(this.board instanceof TileEntitySlotMachine) {
            if (heldItem == key) return new ContainerSlotMachine(windowId, playerInventory, this.board);
            if (key == Blocks.AIR.asItem()) return new ContainerSlotMachine(windowId, playerInventory, this.board);
            return new ContainerSlotGame(windowId, playerInventory, this.board);
        }
        return new ContainerField(windowId, playerInventory, this.board);
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.board.getName();
    }
}

