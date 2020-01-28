package mod.shared.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import mod.shared.blocks.MachinaFlamer;
import mod.shared.util.BurnTimes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public abstract class TileEntityFlamer  extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {

    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int burnTime;
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;

    protected final IIntArray furnaceData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0: return TileEntityFlamer.this.burnTime;
                case 1: return TileEntityFlamer.this.recipesUsed;
                case 2: return TileEntityFlamer.this.cookTime;
                case 3: return TileEntityFlamer.this.cookTimeTotal;
                default: return 0;
            }
        }
        public void set(int index, int value) {
            switch(index) {
                case 0: TileEntityFlamer.this.burnTime = value; break;
                case 1: TileEntityFlamer.this.recipesUsed = value; break;
                case 2: TileEntityFlamer.this.cookTime = value; break;
                case 3: TileEntityFlamer.this.cookTimeTotal = value;
            }
        }
        public int size() {
            return 4;
        }
    };

    private final Map<ResourceLocation, Integer> field_214022_n = Maps.newHashMap();
    protected final IRecipeType<? extends AbstractCookingRecipe> recipeType;

    protected TileEntityFlamer(TileEntityType<?> tileTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn) {
        super(tileTypeIn);
        this.recipeType = recipeTypeIn;
    }

    public TileEntityFlamer(TileEntityType<TileEntity> tileTypeIn) {
        super(tileTypeIn);
        recipeType = null;
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
        this.burnTime = compound.getInt("BurnTime");
        this.cookTime = compound.getInt("CookTime");
        this.cookTimeTotal = compound.getInt("CookTimeTotal");
        this.recipesUsed = this.getBurnTime(this.items.get(1));
        int i = compound.getShort("RecipesUsedSize");

        for(int j = 0; j < i; ++j) {
            ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + j));
            int k = compound.getInt("RecipeAmount" + j);
            this.field_214022_n.put(resourcelocation, k);
        }

    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ItemStackHelper.saveAllItems(compound, this.items);
        compound.putShort("RecipesUsedSize", (short)this.field_214022_n.size());
        int i = 0;

        for(Map.Entry<ResourceLocation, Integer> entry : this.field_214022_n.entrySet()) {
            compound.putString("RecipeLocation" + i, entry.getKey().toString());
            compound.putInt("RecipeAmount" + i, entry.getValue());
            ++i;
        }

        return compound;
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isRemote) {
            ItemStack itemstack = this.items.get(1);
            if (this.isBurning() || !itemstack.isEmpty() && !this.items.get(0).isEmpty()) {
                IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>)this.recipeType, this, this.world).orElse(null);
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    this.burnTime = this.getBurnTime(itemstack);
                    this.recipesUsed = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (itemstack.hasContainerItem())
                            this.items.set(1, itemstack.getContainerItem());
                        else
                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                this.items.set(1, itemstack.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.func_214005_h();
                        this.func_214007_c(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(MachinaFlamer.LIT, Boolean.valueOf(this.isBurning())), 3);
            }
        }

        if (flag1) {
            this.markDirty();
        }

    }

    protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
        if (!this.items.get(0).isEmpty() && recipeIn != null) {
            ItemStack itemstack = recipeIn.getRecipeOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void func_214007_c(@Nullable IRecipe<?> p_214007_1_) {
        if (p_214007_1_ != null && this.canSmelt(p_214007_1_)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = p_214007_1_.getRecipeOutput();
            ItemStack itemstack2 = this.items.get(2);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.world.isRemote) {
                this.setRecipeUsed(p_214007_1_);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }

    protected int getBurnTime(ItemStack p_213997_1_) {
        if (p_213997_1_.isEmpty()) {
            return 0;
        } else {
            Item item = p_213997_1_.getItem();
            int ret = p_213997_1_.getBurnTime();
            return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(p_213997_1_, ret == -1 ? BurnTimes.getBurnTimes().getOrDefault(item, 0) : ret);
        }
    }

    protected int func_214005_h() {
        return this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>)this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.cookTimeTotal = this.func_214005_h();
            this.cookTime = 0;
            this.markDirty();
        }

    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return BurnTimes.isFuel(stack) || stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
        }
    }

    public void clear() {
        this.items.clear();
    }

    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            this.field_214022_n.compute(recipe.getId(), (p_214004_0_, p_214004_1_) -> {
                return 1 + (p_214004_1_ == null ? 0 : p_214004_1_);
            });
        }

    }

    @Nullable
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    public void onCrafting(PlayerEntity player) {
    }

    public void func_213995_d(PlayerEntity p_213995_1_) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for(Map.Entry<ResourceLocation, Integer> entry : this.field_214022_n.entrySet()) {
            p_213995_1_.world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((p_213993_3_) -> {
                list.add(p_213993_3_);
                func_214003_a(p_213995_1_, entry.getValue(), ((AbstractCookingRecipe)p_213993_3_).getExperience());
            });
        }

        p_213995_1_.unlockRecipes(list);
        this.field_214022_n.clear();
    }

    private static void func_214003_a(PlayerEntity p_214003_0_, int p_214003_1_, float p_214003_2_) {
        if (p_214003_2_ == 0.0F) {
            p_214003_1_ = 0;
        } else if (p_214003_2_ < 1.0F) {
            int i = MathHelper.floor((float)p_214003_1_ * p_214003_2_);
            if (i < MathHelper.ceil((float)p_214003_1_ * p_214003_2_) && Math.random() < (double)((float)p_214003_1_ * p_214003_2_ - (float)i)) {
                ++i;
            }

            p_214003_1_ = i;
        }

        while(p_214003_1_ > 0) {
            int j = ExperienceOrbEntity.getXPSplit(p_214003_1_);
            p_214003_1_ -= j;
            p_214003_0_.world.addEntity(new ExperienceOrbEntity(p_214003_0_.world, p_214003_0_.posX, p_214003_0_.posY + 0.5D, p_214003_0_.posZ + 0.5D, j));
        }

    }

    public void fillStackedContents(RecipeItemHelper helper) {
        for(ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }

    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void remove() {
        super.remove();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }
}


//public class ITileEntityFurnace extends TileEntity implements ITickable, ISidedInventory {
//
//    /** ??? */
//    private static final int[] SLOTS_TOP = new int[] {0};
//    /** ??? */
//    private static final int[] SLOTS_BOTTOM = new int[] {2, 1};
//    /** ??? */
//    private static final int[] SLOTS_SIDES = new int[] {1};
//    /** ??? */
//    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
//    /** ??? */
//    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
//    /** ??? */
//    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);
//    /** The ItemStacks that hold the items currently being used in the furnace */
//    protected NonNullList<ItemStack> inventory;
//    /** The number of ticks that the furnace will keep burning */
//    protected int furnaceBurnTime;
//    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
//    protected int currentItemBurnTime;
//    /** ??? */
//    protected int cookTime;
//    /** ??? */
//    protected int totalCookTime;
//    /** ??? */
//    protected String customName;
//
//
//
//
//    //----------------------------------------CONSTRUCTOR----------------------------------------//
//
//    public ITileEntityFurnace(){
//        inventory = NonNullList.withSize(3, ItemStack.EMPTY);
//    }
//
//    public ITileEntityFurnace(int size){
//        inventory = NonNullList.withSize(size, ItemStack.EMPTY);
//    }
//
//
//
//
//    //----------------------------------------UPDATE----------------------------------------//
//
//    /** Like the old updateEntity(), except more generic. */
//    public void update(){
//        boolean flag = this.isBurning();
//        boolean flag1 = false;
//        if (this.isBurning()){
//            --this.furnaceBurnTime;
//        }
//        if (!this.world.isRemote){
//            ItemStack itemstack = this.inventory.get(1);
//            if (this.isBurning() || !itemstack.isEmpty() && !this.inventory.get(0).isEmpty()){
//                if (!this.isBurning() && this.canSmelt()){
//                    this.furnaceBurnTime = getItemBurnTime(itemstack);
//                    this.currentItemBurnTime = this.furnaceBurnTime;
//                    if (this.isBurning()){
//                        flag1 = true;
//                        if (!itemstack.isEmpty()){
//                            Item item = itemstack.getItem();
//                            itemstack.shrink(1);
//                            if (itemstack.isEmpty()){
//                                ItemStack item1 = item.getContainerItem(itemstack);
//                                this.inventory.set(1, item1);
//                            }
//                        }
//                    }
//                }
//                if (this.isBurning() && this.canSmelt()){
//                    ++this.cookTime;
//                    if (this.cookTime == this.totalCookTime){
//                        this.cookTime = 0;
//                        this.totalCookTime = this.getCookTime(this.inventory.get(0));
//                        this.smeltItem();
//                        flag1 = true;
//                    }
//                } else {
//                    this.cookTime = 0;
//                }
//            } else if (!this.isBurning() && this.cookTime > 0){
//                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
//            }
//            if (flag != this.isBurning()){
//                flag1 = true;
//                BlockFurnace.setState(this.isBurning(), this.world, this.pos);
//            }
//        }
//        if (flag1){
//            this.markDirty();
//        }
//    }
//
//
//
//
//    //----------------------------------------SAVE/LOAD----------------------------------------//
//
//    /** ??? */
//    public void readFromNBT(NBTTagCompound compound){
//        super.readFromNBT(compound);
//        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
//        ItemStackHelper.loadAllItems(compound, this.inventory);
//        this.furnaceBurnTime = compound.getInteger("BurnTime");
//        this.cookTime = compound.getInteger("CookTime");
//        this.totalCookTime = compound.getInteger("CookTimeTotal");
//        this.currentItemBurnTime = getItemBurnTime(this.inventory.get(1));
//        if (compound.hasKey("CustomName", 8)){
//            this.customName = compound.getString("CustomName");
//        }
//    }
//
//    /** ??? */
//    public NBTTagCompound writeToNBT(NBTTagCompound compound){
//        super.writeToNBT(compound);
//        compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
//        compound.setInteger("CookTime", (short)this.cookTime);
//        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
//        ItemStackHelper.saveAllItems(compound, this.inventory);
//        if (this.hasCustomName()){
//            compound.setString("CustomName", this.customName);
//        }
//        return compound;
//    }
//
//
//
//    //----------------------------------------NETWORK----------------------------------------//
//
//    /** ??? */
//    public int getField(int id){
//        switch (id){
//            case  0: return this.furnaceBurnTime;
//            case  1: return this.currentItemBurnTime;
//            case  2: return this.cookTime;
//            case  3: return this.totalCookTime;
//            default: return 0;
//        }
//    }
//
//    /** ??? */
//    public void setField(int id, int value){
//        switch (id){
//            case 0: this.furnaceBurnTime     = value; break;
//            case 1: this.currentItemBurnTime = value; break;
//            case 2: this.cookTime            = value; break;
//            case 3: this.totalCookTime       = value; break;
//        }
//    }
//
//    /** ??? */
//    public int getFieldCount(){
//        return 4;
//    }
//
//
//
//
//    //----------------------------------------INVENTORY----------------------------------------//
//
//    /** Returns the number of slots in the inventory. */
//    public int getSizeInventory(){
//        return this.inventory.size();
//    }
//
//    /** ??? */
//    public boolean isEmpty(){
//        for (ItemStack itemstack : this.inventory){
//            if (!itemstack.isEmpty()){
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /** Returns the stack in the given slot. */
//    public ItemStack getStackInSlot(int index){
//        return this.inventory.get(index);
//    }
//
//    /** Removes up to a specified number of items from an inventory slot and returns them in a new stack. */
//    public ItemStack decrStackSize(int index, int count){
//        return ItemStackHelper.getAndSplit(this.inventory, index, count);
//    }
//
//    /** Removes a stack from the given slot and returns it. */
//    public ItemStack removeStackFromSlot(int index){
//        return ItemStackHelper.getAndRemove(this.inventory, index);
//    }
//
//    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
//    public void setInventorySlotContents(int index, ItemStack stack){
//        ItemStack itemstack = this.inventory.get(index);
//        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
//        this.inventory.set(index, stack);
//        if (stack.getCount() > this.getInventoryStackLimit()){
//            stack.setCount(this.getInventoryStackLimit());
//        }
//        if (index == 0 && !flag){
//            this.totalCookTime = this.getCookTime(stack);
//            this.cookTime = 0;
//            this.markDirty();
//        }
//    }
//
//    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. */
//    public int getInventoryStackLimit(){
//        return 64;
//    }
//
//    /** ??? */
//    public void openInventory(EntityPlayer player){ }
//
//    /** ??? */
//    public void closeInventory(EntityPlayer player){ }
//
//    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For guis use Slot.isItemValid */
//    public boolean isItemValidForSlot(int index, ItemStack stack){
//        if (index == 2){
//            return false;
//        } else if (index != 1){
//            return true;
//        } else {
//            ItemStack itemstack = this.inventory.get(1);
//            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
//        }
//    }
//
//    /** ??? */
//    public int[] getSlotsForFace(EnumFacing side){
//        if (side == EnumFacing.DOWN){
//            return SLOTS_BOTTOM;
//        } else {
//            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
//        }
//    }
//
//    /** Returns true if automation can insert the given item in the given slot from the given side. */
//    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction){
//        return this.isItemValidForSlot(index, itemStackIn);
//    }
//
//    /** Returns true if automation can extract the given item in the given slot from the given side. */
//    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
//        if (direction == EnumFacing.DOWN && index == 1){
//            Item item = stack.getItem();
//            return item == Items.WATER_BUCKET || item == Items.BUCKET;
//        }
//        return true;
//    }
//
//    /** ??? */
//    public void clear(){
//        this.inventory.clear();
//    }
//
//
//
//
//    //----------------------------------------SUPPORT----------------------------------------//
//
//    /** Get the name of this object. For players this returns their username */
//    public String getName(){
//        return this.hasCustomName() ? this.customName : "container.furnace";
//    }
//
//    /** Returns true if this thing is named */
//    public boolean hasCustomName(){
//        return this.customName != null && !this.customName.isEmpty();
//    }
//
//    /** ??? */
//    public void setCustomInventoryName(String s){
//        this.customName = s;
//    }
//
//    /** Furnace isBurning */
//    public boolean isBurning(){
//        return this.furnaceBurnTime > 0;
//    }
//
//    /** ??? */
//    @SideOnly(Side.CLIENT)
//    public static boolean isBurning(IInventory inventory){
//        return inventory.getField(0) > 0;
//    }
//
//    /** ??? */
//    public int getCookTime(ItemStack stack){
//        return 200;
//    }
//
//    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
//    private boolean canSmelt(){
//        if (this.inventory.get(0).isEmpty()){
//            return false;
//        } else {
//            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory.get(0));
//            if (itemstack.isEmpty()){
//                return false;
//            } else {
//                ItemStack itemstack1 = this.inventory.get(2);
//                if (itemstack1.isEmpty()){
//                    return true;
//                } else if (!itemstack1.isItemEqual(itemstack)){
//                    return false;
//                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()){  // Forge fix: make furnace respect stack sizes in furnace recipes
//                    return true;
//                } else {
//                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
//                }
//            }
//        }
//    }
//
//    /** Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack */
//    public void smeltItem(){
//        if (this.canSmelt()){
//            ItemStack itemstack = this.inventory.get(0);
//            ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
//            ItemStack itemstack2 = this.inventory.get(2);
//            if (itemstack2.isEmpty()){
//                this.inventory.set(2, itemstack1.copy());
//            } else if (itemstack2.getItem() == itemstack1.getItem()){
//                itemstack2.grow(itemstack1.getCount());
//            }
//            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !this.inventory.get(1).isEmpty() && this.inventory.get(1).getItem() == Items.BUCKET){
//                this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
//            }
//            itemstack.shrink(1);
//        }
//    }
//
//    /** Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't fuel */
//    public static int getItemBurnTime(ItemStack stack){
//        if (stack.isEmpty()){
//            return 0;
//        } else {
//            int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);
//            if (burnTime >= 0) return burnTime;
//            Item item = stack.getItem();
//            if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB)){ return 150; }
//            else if (item == Item.getItemFromBlock(Blocks.WOOL)){ return 100; }
//            else if (item == Item.getItemFromBlock(Blocks.CARPET)){ return 67; }
//            else if (item == Item.getItemFromBlock(Blocks.LADDER)){ return 300; }
//            else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON)){ return 100; }
//            else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD){ return 300; }
//            else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK)){ return 16000; }
//            else if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())){ return 200; }
//            else if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())){ return 200; }
//            else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())){ return 200; }
//            else if (item == Items.STICK){ return 100; }
//            else if (item != Items.BOW && item != Items.FISHING_ROD){
//                if (item == Items.SIGN){ return 200; }
//                else if (item == Items.COAL){ return 1600; }
//                else if (item == Items.LAVA_BUCKET){ return 20000; }
//                else if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL){
//                    if (item == Items.BLAZE_ROD){ return 2400; }
//                    else if (item instanceof ItemDoor && item != Items.IRON_DOOR){ return 200; }
//                    else { return item instanceof ItemBoat ? 400 : 0; }
//                } else { return 100; }
//            } else { return 300; }
//        }
//    }
//
//    /** ??? */
//    public static boolean isItemFuel(ItemStack stack){
//        return getItemBurnTime(stack) > 0;
//    }
//
//    /** Don't rename this method to canInteractWith due to conflicts with Container */
//    public boolean isUsableByPlayer(EntityPlayer player){
//        if (this.world.getTileEntity(this.pos) != this){
//            return false;
//        } else {
//            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
//        }
//    }
//
//    /** ??? */
//    public String getGuiID(){
//        return "minecraft:furnace";
//    }
//
//    /** ??? */
//    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
//        return new ContainerFurnace(playerInventory, this);
//    }
//
//    /** ??? */
//    @SuppressWarnings("unchecked")
//    @Override
//    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing){
//        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//            if (facing == EnumFacing.DOWN)
//                return (T) handlerBottom;
//            else if (facing == EnumFacing.UP)
//                return (T) handlerTop;
//            else
//                return (T) handlerSide;
//        return super.getCapability(capability, facing);
//    }
//
//}
