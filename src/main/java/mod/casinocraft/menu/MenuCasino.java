package mod.casinocraft.menu;

import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.lucky77.blockentity.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public abstract class MenuCasino extends MenuBase {

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MenuCasino(MenuType<?> type, int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.of(packetBuffer.readLong()));
    }

    public MenuCasino(MenuType<?> type, int windowID, Inventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (BlockEntityMachine) playerInventory.player.getCommandSenderWorld().getBlockEntity(pos));
        this.pos = pos;
    }

    public MenuCasino(MenuType<?> type, int windowID, Inventory playerInventory, BlockEntityMachine board) {
        super(type, windowID, playerInventory, board);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected void createInventory(BlockEntityBase tile, Inventory playerInventory){

    }

}