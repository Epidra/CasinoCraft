package mod.casinocraft.blocks;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.network.ServerPowerMessage;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.blocks.IMachinaDoubleTall;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockArcade extends IMachinaDoubleTall {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_15;

    /** Contructor with predefined BlockProperty */
    public BlockArcade(String modid, String name, Block block) {
        super(modid, name, block);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(OFFSET, Boolean.valueOf(true)).with(LEVEL, Integer.valueOf(0)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING, OFFSET, LEVEL);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        if(state.get(OFFSET)){
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        if(state.get(OFFSET)){
            return new TileEntityArcade();
        }
        return null;
    }

    @Nullable

    public IInteractionObject getContainer(IBlockState state, World worldIn, BlockPos pos)

    {

        TileEntity tileentity = worldIn.getTileEntity(pos);



        if (!(tileentity instanceof TileEntityArcade))

        {

            return null;

        }

        else

        {

            return (IInteractionObject) tileentity;

        }

    }

    @Deprecated
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        //if (world.isRemote){
        //    return true;
        //} else {
        //    IInteractionObject ilockablecontainer = this.getContainer(state, world, pos);
        //    if (ilockablecontainer != null){
        //        if (player instanceof EntityPlayerMP && !(player instanceof FakePlayer)){
        //            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        //            NetworkHooks.openGui(entityPlayerMP, ilockablecontainer, buf -> buf.writeBlockPos(pos));
        //        }
        //        player.addStat(StatList.OPEN_CHEST);
        //    }
        //    //return true;
        //}
        if (world.isRemote){
            return true;
        } else {
            boolean isPrimary = world.getBlockState(pos).get(OFFSET);
            final BlockPos pos2 = isPrimary ? pos : pos.down();
            Item item = Items.FLINT;
            if(world.getTileEntity(pos2) instanceof TileEntityBoard){
                TileEntityBoard te = (TileEntityBoard) world.getTileEntity(pos2);
                if (player instanceof EntityPlayerMP && !(player instanceof FakePlayer)){
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;

                    if(te.getStackInSlot(0).getItem() == Item.getItemFromBlock(Blocks.AIR) || (player.getHeldItem(hand) != null && te.getStackInSlot(0).getItem() == player.getHeldItem(hand).getItem() && te.getStackInSlot(0).getDisplayName().equals(player.getHeldItem(hand).getDisplayName()))){
                        NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(-1).writeBoolean(true));
                    } else if(te.inventory.get(1) != null){
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_TETRIS)    NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.TETRIS.ordinal()).writeBoolean(true));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_COLUMNS)   NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.COLUMNS.ordinal()).writeBoolean(true));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MEANMINOS) NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.MEANMINOS.ordinal()).writeBoolean(true));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SNAKE)     NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.SNAKE.ordinal()).writeBoolean(true));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SOKOBAN)   NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.SOKOBAN.ordinal()).writeBoolean(true));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_2048)      NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID._2048.ordinal()).writeBoolean(true));
                    }



                    return  true;
                }
                if(player.isSneaking()) {
                    setPowerState(te.inventory.get(1).getItem(), pos2);
                    //setPowerState(world, pos2, !isPrimary);
                    return true;
                }
                //if(te.getStackInSlot(0) == null || (player.getHeldItem(hand) != null && te.getStackInSlot(0).getItem() == player.getHeldItem(hand).getItem() && te.getStackInSlot(0).getDisplayName().matches(player.getHeldItem(hand).getDisplayName()))){
                //    player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.ARCADE.ordinal(), world, pos2.getX(), pos2.getY(), pos2.getZ());
                //    player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
                //} else if(te.inventory.get(1) != null){
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_TETRIS)   player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.TETRIS.ordinal(),    world, pos2.getX(), pos2.getY(), pos2.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_COLUMNS)  player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.COLUMNS.ordinal(),   world, pos2.getX(), pos2.getY(), pos2.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MEANMINOS)player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.MEANMINOS.ordinal(), world, pos2.getX(), pos2.getY(), pos2.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SOKOBAN)  player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SOKOBAN.ordinal(),   world, pos2.getX(), pos2.getY(), pos2.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SNAKE)    player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SNAKE.ordinal(),     world, pos2.getX(), pos2.getY(), pos2.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_2048)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID._2048.ordinal(),     world, pos2.getX(), pos2.getY(), pos2.getZ());
                //    player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
                //}

                te.markDirty();
            }
            //return true;
        }
        return false;
    }


    public void setPowerState(Item item, BlockPos pos) {
        CasinoPacketHandler.sendToServer(new ServerPowerMessage(EnumModule.byItem(item).meta, pos));
        //CasinoPacketHandler.INSTANCE.sendToAll(new PacketClientPowerMessage(EnumModule.byItem(item).meta, pos));
    }

    /** ??? */
    public static void setPowerState2(World world, BlockPos pos){
        IBlockState iblockstate = world.getBlockState(pos);
        TileEntityBoard tileentity = (TileEntityBoard) world.getTileEntity(pos);

        if (tileentity != null){
            //world.notifyBlockUpdate(pos, iblockstate, iblockstate.with(LEVEL, itemToInt(tileentity.inventory.get(1).getItem())), 3);
            world.destroyBlock(pos, false);
            world.destroyBlock(pos.up(), false);
            ///world.removeBlock(pos.up());
            world.setBlockState(pos,      iblockstate.with(LEVEL, itemToInt(tileentity.inventory.get(1).getItem())), 3);
            world.setBlockState(pos.up(), iblockstate.with(OFFSET, false).with(LEVEL, itemToInt(tileentity.inventory.get(1).getItem())), 3);
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }

    public static int itemToInt(Item item){
        if(item == CasinoKeeper.MODULE_TETRIS)    return 1;
        if(item == CasinoKeeper.MODULE_COLUMNS)   return 2;
        if(item == CasinoKeeper.MODULE_MEANMINOS) return 3;
        if(item == CasinoKeeper.MODULE_SNAKE)     return 4;
        if(item == CasinoKeeper.MODULE_SOKOBAN)   return 5;
        if(item == CasinoKeeper.MODULE_2048)      return 6;
        return 0;
    }

    public enum EnumModule implements IStringSerializable {
        EMPTY    (0, "empty"),
        TETRIS   (1, "tetris"),
        COLUMNS  (2, "columns"),
        MEANMINOS(3, "meanminos"),
        SNAKE    (4, "snake"),
        SOKOBAN  (5, "sokoban"),
        _2048    (6, "_2048");

        public final String name;
        public final int meta;

        EnumModule(int meta, String name){
            this.meta = meta;
            this.name = name;
        }

        public int getMetadata(){
            return this.meta;
        }

        public String toString(){
            return this.name;
        }

        public String getName(){
            return this.name;
        }

        public static EnumModule byMetadata(int meta){
            if(meta == 0) return EnumModule.EMPTY;
            if(meta == 1) return EnumModule.TETRIS;
            if(meta == 2) return EnumModule.COLUMNS;
            if(meta == 3) return EnumModule.MEANMINOS;
            if(meta == 4) return EnumModule.SNAKE;
            if(meta == 5) return EnumModule.SOKOBAN;
            if(meta == 6) return EnumModule._2048;
            return EnumModule.EMPTY;
        }

        public static EnumModule byItem(Item item){
            if(item == CasinoKeeper.MODULE_TETRIS)    return EnumModule.TETRIS;
            if(item == CasinoKeeper.MODULE_COLUMNS)   return EnumModule.COLUMNS;
            if(item == CasinoKeeper.MODULE_MEANMINOS) return EnumModule.MEANMINOS;
            if(item == CasinoKeeper.MODULE_SNAKE)     return EnumModule.SNAKE;
            if(item == CasinoKeeper.MODULE_SOKOBAN)   return EnumModule.SOKOBAN;
            if(item == CasinoKeeper.MODULE_2048)      return EnumModule._2048;
            return EnumModule.EMPTY;
        }

    }
}
