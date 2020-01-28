package mod.shared.tileentity;

public class TileEntityEnergy {

}

//public class ITileEntityEnergy extends TileEntity implements ITickable {
//
//    /** ??? */
//    public EnumFacing facing;
//    /** Energy Value */
//    public int energy = 0;
//    /** Render Value for energy based rotational movement */
//    public int rotation = 0;
//
//
//
//
//    //----------------------------------------CONSTRUCTOR----------------------------------------//
//
//
//
//
//    //----------------------------------------UPDATE----------------------------------------//
//
//    /** Update Function */
//    public void update(){
//        if(facing == null){
//            facing = world.getBlockState(pos).getValue(IMachinaEnergy.FACING).getOpposite();
//        } else {
//            if(world.getBlockState(pos.offset(facing)).getBlock() instanceof IMachinaEnergy){
//                ITileEntityEnergy entity = (ITileEntityEnergy) world.getTileEntity(pos.offset(facing));
//                energy = entity.energy;
//                this.markDirty();
//            } else {
//                energy = 0;
//            }
//        }
//        rotation = (rotation + energy) % 360;
//    }
//
//
//
//
//    //----------------------------------------SAVE/LOAD----------------------------------------//
//
//    /** This is where you save any data that you don't want to lose when the tile entity unloads In this case, we only need to store the gem colour.  For examples with other types of data, see MBE20 */
//    @Override
//    public NBTTagCompound writeToNBT(NBTTagCompound compound){
//        super.writeToNBT(compound); // The super call is required to save the tiles location
//        if(facing != null) compound.setInteger("facing", facing.getIndex());
//        else compound.setInteger("facing", -1);
//        compound.setInteger("energy", energy);
//        return compound;
//    }
//
//    /** This is where you load the data that you saved in writeToNBT */
//    @Override
//    public void readFromNBT(NBTTagCompound compound){
//        super.readFromNBT(compound); // The super call is required to load the tiles location
//        // important rule: never trust the data you read from NBT, make sure it can't cause a crash
//        final int NBT_INT_ID = 3;					// see NBTBase.createNewByType()
//        int f = compound.getInteger("facing");
//        if(f != -1) facing = EnumFacing.getFront(compound.getInteger("facing"));
//        energy = compound.getInteger("energy");
//    }
//
//
//
//
//    //----------------------------------------NETWORK----------------------------------------//
//
//    /** When the world loads from disk, the server needs to send the TileEntity information to the client it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and handleUpdateTag() to do this:
//     *  getUpdatePacket() and onDataPacket() are used for one-at-a-time TileEntity updates getUpdateTag() and handleUpdateTag() are used by vanilla to collate together into a single chunk update packet
//     * In this case, we need it for the gem colour.  There's no need to save the gem angular position because the player will never notice the difference and the client<-->server synchronisation lag will make it inaccurate anyway*/
//    @Override
//    @Nullable
//    public SPacketUpdateTileEntity getUpdatePacket(){
//        NBTTagCompound nbtTagCompound = new NBTTagCompound();
//        writeToNBT(nbtTagCompound);
//        int metadata = getBlockMetadata();
//        return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
//    }
//
//    /** ??? */
//    @Override
//    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
//        readFromNBT(pkt.getNbtCompound());
//    }
//
//    /** Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client */
//    @Override
//    public NBTTagCompound getUpdateTag(){
//        NBTTagCompound nbtTagCompound = new NBTTagCompound();
//        writeToNBT(nbtTagCompound);
//        return nbtTagCompound;
//    }
//
//    /** Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client */
//    @Override
//    public void handleUpdateTag(NBTTagCompound tag){
//        this.readFromNBT(tag);
//    }
//
//
//
//
//    //----------------------------------------SUPPORT----------------------------------------//
//
//    /** Don't render the gem if the player is too far away
//     * @return the maximum distance squared at which the TESR should render
//     */
//    @SideOnly(Side.CLIENT)
//    @Override
//    public double getMaxRenderDistanceSquared(){
//        final int MAXIMUM_DISTANCE_IN_BLOCKS = 32;
//        return MAXIMUM_DISTANCE_IN_BLOCKS * MAXIMUM_DISTANCE_IN_BLOCKS;
//    }
//
//    /** Return an appropriate bounding box enclosing the TESR
//     * This method is used to control whether the TESR should be rendered or not, depending on where the player is looking.
//     * The default is the AABB for the parent block, which might be too small if the TESR renders outside the borders of the parent block.
//     * If you get the boundary too small, the TESR may disappear when you aren't looking directly at it.
//     * @return an appropriately size AABB for the TileEntity
//     */
//    @SideOnly(Side.CLIENT)
//    @Override
//    public AxisAlignedBB getRenderBoundingBox(){
//        // if your render should always be performed regardless of where the player is looking, use infinite
//        //   Your should also change TileEntitySpecialRenderer.isGlobalRenderer().
//        AxisAlignedBB infiniteExample = INFINITE_EXTENT_AABB;
//        // our gem will stay above the block, up to 1 block higher, so our bounding box is from [x,y,z] to  [x+1, y+2, z+1]
//        AxisAlignedBB aabb = new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
//        return aabb;
//    }
//
//}
