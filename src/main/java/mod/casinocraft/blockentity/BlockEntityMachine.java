package mod.casinocraft.blockentity;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.block.BlockArcade;
import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.logic.game.*;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.logic.other.LogicSlotGame;
import mod.casinocraft.network.MessageInventoryClient;
import mod.casinocraft.network.MessageModuleServer;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.lucky77.blockentity.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.sounds.SoundSource.NEUTRAL;

public abstract class BlockEntityMachine extends BlockEntityBase<LogicModule> {

    public boolean settingInfiniteToken       = false;
    public boolean settingInfiniteReward      = false;
    public boolean settingDropItemsOnBreak    = false;
    public boolean settingIndestructableBlock = false;
    public boolean settingAlternateScore      = false;
    public int     settingAlternateColor      = 0;

    public boolean transferTokenIN   = false;
    public boolean transferTokenOUT  = false;
    public boolean transferRewardIN  = false;
    public boolean transferRewardOUT = false;

    public int storageToken  = 0;
    public int storageReward = 0;
    public int bettingLow    = 0;
    public int bettingHigh   = 0;
    public int rewardScore1  = 0;
    public int rewardScore2  = 0;
    public int rewardScore3  = 0;
    public int rewardAmount1 = 0;
    public int rewardAmount2 = 0;
    public int rewardAmount3 = 0;

    public boolean prizeMode1 = false;
    public boolean prizeMode2 = false;
    public boolean prizeMode3 = false;
    
    public int settingRule1 = 0;
    public int settingRule2 = 0;
    public int settingRule3 = 0;
    public int settingRule4 = 0;
    public int settingRule5 = 0;

    private Item lastModule = Items.FLINT;
    public DyeColor color;
    public final int tableID;
    public final ContainerData casinoData = new ContainerData() {
        public int get(int index) {
            switch(index) {
                case  0: return BlockEntityMachine.this.storageToken;
                case  1: return BlockEntityMachine.this.storageReward;
                case  2: return BlockEntityMachine.this.bettingLow;
                case  3: return BlockEntityMachine.this.bettingHigh;
                case  4: return BlockEntityMachine.this.rewardScore1;
                case  5: return BlockEntityMachine.this.rewardScore2;
                case  6: return BlockEntityMachine.this.rewardScore3;
                case  7: return BlockEntityMachine.this.rewardAmount1;
                case  8: return BlockEntityMachine.this.rewardAmount2;
                case  9: return BlockEntityMachine.this.rewardAmount3;
                case 10: return BlockEntityMachine.this.prizeMode1                 ? 1 : 0;
                case 11: return BlockEntityMachine.this.prizeMode2                 ? 1 : 0;
                case 12: return BlockEntityMachine.this.prizeMode3                 ? 1 : 0;
                case 13: return BlockEntityMachine.this.transferTokenIN            ? 1 : 0;
                case 14: return BlockEntityMachine.this.transferTokenOUT           ? 1 : 0;
                case 15: return BlockEntityMachine.this.transferRewardIN           ? 1 : 0;
                case 16: return BlockEntityMachine.this.transferRewardOUT          ? 1 : 0;
                case 17: return BlockEntityMachine.this.settingInfiniteToken       ? 1 : 0;
                case 18: return BlockEntityMachine.this.settingInfiniteReward      ? 1 : 0;
                case 19: return BlockEntityMachine.this.settingDropItemsOnBreak    ? 1 : 0;
                case 20: return BlockEntityMachine.this.settingIndestructableBlock ? 1 : 0;
                case 21: return BlockEntityMachine.this.settingAlternateScore      ? 1 : 0;
                case 22: return BlockEntityMachine.this.settingAlternateColor;
                case 23: return BlockEntityMachine.this.settingRule1;
                case 24: return BlockEntityMachine.this.settingRule2;
                case 25: return BlockEntityMachine.this.settingRule3;
                case 26: return BlockEntityMachine.this.settingRule4;
                case 27: return BlockEntityMachine.this.settingRule5;
                default:
                    return 0;
            }
        }
        public void set(int index, int value) {
            switch(index) {
                case  0: BlockEntityMachine.this.storageToken               = value;      break;
                case  1: BlockEntityMachine.this.storageReward              = value;      break;
                case  2: BlockEntityMachine.this.bettingLow                 = value;      break;
                case  3: BlockEntityMachine.this.bettingHigh                = value;      break;
                case  4: BlockEntityMachine.this.rewardScore1               = value;      break;
                case  5: BlockEntityMachine.this.rewardScore2               = value;      break;
                case  6: BlockEntityMachine.this.rewardScore3               = value;      break;
                case  7: BlockEntityMachine.this.rewardAmount1              = value;      break;
                case  8: BlockEntityMachine.this.rewardAmount2              = value;      break;
                case  9: BlockEntityMachine.this.rewardAmount3              = value;      break;
                case 10: BlockEntityMachine.this.prizeMode1                 = value == 1; break;
                case 11: BlockEntityMachine.this.prizeMode2                 = value == 1; break;
                case 12: BlockEntityMachine.this.prizeMode3                 = value == 1; break;
                case 13: BlockEntityMachine.this.transferTokenIN            = value == 1; break;
                case 14: BlockEntityMachine.this.transferTokenOUT           = value == 1; break;
                case 15: BlockEntityMachine.this.transferRewardIN           = value == 1; break;
                case 16: BlockEntityMachine.this.transferRewardOUT          = value == 1; break;
                case 17: BlockEntityMachine.this.settingInfiniteToken       = value == 1; break;
                case 18: BlockEntityMachine.this.settingInfiniteReward      = value == 1; break;
                case 19: BlockEntityMachine.this.settingDropItemsOnBreak    = value == 1; break;
                case 20: BlockEntityMachine.this.settingIndestructableBlock = value == 1; break;
                case 21: BlockEntityMachine.this.settingAlternateScore      = value == 1; break;
                case 22: BlockEntityMachine.this.settingAlternateColor      = value;      break;
                case 23: BlockEntityMachine.this.settingRule1      = value;      break;
                case 24: BlockEntityMachine.this.settingRule2      = value;      break;
                case 25: BlockEntityMachine.this.settingRule3      = value;      break;
                case 26: BlockEntityMachine.this.settingRule4      = value;      break;
                case 27: BlockEntityMachine.this.settingRule5      = value;      break;
            }
        }
        public int getCount() {
            return 28;
        }
    };





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** 0 - KEY, 1 - MODULE, 2 - TOKEN, 3 - STORAGE-TOKEN, 4 - STORAGE_PRIZE **/
    public BlockEntityMachine(BlockEntityType<?> tileEntityTypeIn, BlockPos blockpos, BlockState blockstate, DyeColor color, int tableID) {
        super(tileEntityTypeIn, blockpos, blockstate, 5, new LogicDummy(tableID));
        this.color = color;
        this.tableID = tableID;
    }





    //----------------------------------------SERVER_TICK----------------------------------------//

    public static void serverTick(Level level, BlockPos pos, BlockState state, BlockEntityMachine BE) {
        BE.changeLogic();
        // Token Transfer
        if(BE.transferTokenIN) {
            if(BE.getTokenIO().getCount() > 0 && (BE.storageToken == 0 || BE.isTokenBET(BE.getTokenIO()))) {
                if(BE.isTokenBET(ItemStack.EMPTY)){ BE.setTokenBET(BE.getTokenIO()); }
                BE.storageToken += BE.getTokenIO().getCount();
                BE.inventory.set(2, ItemStack.EMPTY);
            }
        } else if(BE.transferTokenOUT) {
            if(BE.storageToken > 0 && (BE.isTokenBET(BE.getTokenIO()) || BE.getTokenIO().isEmpty())) {
                if(BE.getTokenIO().isEmpty()) {
                    int count = Math.min(BE.storageToken, 64);
                    BE.inventory.set(2, new ItemStack(BE.getTokenBET(), count));
                    BE.storageToken-=count;
                }else if(BE.getTokenIO().getCount() < 64) {
                    int count = Math.min(BE.storageToken, 64 - BE.getTokenIO().getCount());
                    BE.getTokenIO().grow(count);
                    BE.storageToken-=count;
                }
                if(BE.storageToken == 0) {
                    BE.setTokenBET(ItemStack.EMPTY);
                }
            }
        }
        // Reward Transfer
        else if(BE.transferRewardIN) {
            if(BE.inventory.get(2).getCount() > 0 && (BE.storageReward == 0 || BE.isTokenREW(BE.inventory.get(2)))) {
                if(BE.getTokenREW() == Item.byBlock(Blocks.AIR)) BE.setTokenREW(BE.inventory.get(2));
                int count = BE.inventory.get(2).getCount();
                BE.inventory.get(2).shrink(count);
                BE.storageReward+=count;
                if(BE.inventory.get(2).getCount() <= 0) BE.inventory.set(2, new ItemStack(Blocks.AIR));
            }
        } else if(BE.transferRewardOUT) {
            if(BE.storageReward > 0 && (BE.isTokenREW(BE.inventory.get(2)) || BE.inventory.get(2).isEmpty())) {
                if(BE.inventory.get(2).isEmpty()) {
                    int count = Math.min(BE.storageReward, 64);
                    BE.inventory.set(2, new ItemStack(BE.getTokenStackREW().getItem(), count));
                    BE.storageReward-=count;
                }else if(BE.inventory.get(2).getCount() < 64) {
                    int count = Math.min(BE.storageReward, 64 - BE.inventory.get(2).getCount());
                    BE.inventory.get(2).grow(count);
                    BE.storageReward-=count;
                }
                if(BE.storageReward == 0) {
                    BE.setTokenREW(new ItemStack(Blocks.AIR));
                }
            }
        }
        BE.setChanged();
        if(BE.logic.jingle > 0){
            BE.playSound();
        }
        if(BE.logic.turnstate > 1 && BE.logic.turnstate < 6){
            BE.logic.update();
        }
    }





    //----------------------------------------NETWORK----------------------------------------//

    // ...





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load(CompoundTag nbt){
        super.load(nbt);
    
        storageToken               = nbt.getInt("storage_token");
        storageReward              = nbt.getInt("storage_reward");
        bettingLow                 = nbt.getInt("betting_low");
        bettingHigh                = nbt.getInt("betting_high");
        rewardScore1               = nbt.getInt("reward_score_1");
        rewardScore2               = nbt.getInt("reward_score_2");
        rewardScore3               = nbt.getInt("reward_score_3");
        rewardAmount1              = nbt.getInt("reward_amount_1");
        rewardAmount2              = nbt.getInt("reward_amount_2");
        rewardAmount3              = nbt.getInt("reward_amount_3");
        prizeMode1                 = nbt.getBoolean("prize_mode_1");
        prizeMode2                 = nbt.getBoolean("prize_mode_2");
        prizeMode3                 = nbt.getBoolean("prize_mode_3");
        transferTokenIN            = nbt.getBoolean("transfer_token_in");
        transferTokenOUT           = nbt.getBoolean("transfer_token_out");
        transferRewardIN           = nbt.getBoolean("transfer_reward_in");
        transferRewardOUT          = nbt.getBoolean("transfer_reward_out");
        settingInfiniteToken       = nbt.getBoolean("setting_infinite_token");
        settingInfiniteReward      = nbt.getBoolean("setting_infinite_reward");
        settingDropItemsOnBreak    = nbt.getBoolean("setting_drop_items_on_break");
        settingIndestructableBlock = nbt.getBoolean("setting_indestructable_block");
        settingAlternateScore      = nbt.getBoolean("setting_alternate_score");
        settingAlternateColor      = nbt.getInt("setting_alternate_color");
        settingRule1      = nbt.getInt("setting_rule_1");
        settingRule2      = nbt.getInt("setting_rule_2");
        settingRule3      = nbt.getInt("setting_rule_3");
        settingRule4      = nbt.getInt("setting_rule_4");
        settingRule5      = nbt.getInt("setting_rule_5");

        this.inventory = NonNullList.withSize(5, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory);
        lastModule = getModule();
        logic = setLogic();
        logic.load(nbt);
    }

    public void saveAdditional(CompoundTag compound){
        super.saveAdditional(compound);
    
        compound.putInt("storage_token",   storageToken);
        compound.putInt("storage_reward",  storageReward);
        compound.putInt("betting_low",     bettingLow);
        compound.putInt("betting_high",    bettingHigh);
        compound.putInt("reward_score_1",  rewardScore1);
        compound.putInt("reward_score_2",  rewardScore2);
        compound.putInt("reward_score_3",  rewardScore3);
        compound.putInt("reward_amount_1", rewardAmount1);
        compound.putInt("reward_amount_2", rewardAmount2);
        compound.putInt("reward_amount_3", rewardAmount3);
        compound.putBoolean("prize_mode_1", prizeMode1);
        compound.putBoolean("prize_mode_2", prizeMode2);
        compound.putBoolean("prize_mode_3", prizeMode3);
        compound.putBoolean("transfer_token_in",   transferTokenIN);
        compound.putBoolean("transfer_token_out",  transferTokenOUT);
        compound.putBoolean("transfer_reward_in",  transferRewardIN);
        compound.putBoolean("transfer_reward_out", transferRewardOUT);
        compound.putBoolean("setting_infinite_token",       settingInfiniteToken);
        compound.putBoolean("setting_infinite_reward",      settingInfiniteReward);
        compound.putBoolean("setting_drop_items_on_break",  settingDropItemsOnBreak);
        compound.putBoolean("setting_indestructable_block", settingIndestructableBlock);
        compound.putBoolean("setting_alternate_score", settingAlternateScore);
        compound.putInt("setting_alternate_color",          settingAlternateColor);
        compound.putInt("setting_rule_1",          settingRule1);
        compound.putInt("setting_rule_2",          settingRule2);
        compound.putInt("setting_rule_3",          settingRule3);
        compound.putInt("setting_rule_4",          settingRule4);
        compound.putInt("setting_rule_5",          settingRule5);

        ContainerHelper.saveAllItems(compound, this.inventory);
        logic.save(compound);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public void changeLogic(){
        if(lastModule != getModule()){
            settingRule1 = 0;
            settingRule2 = 0;
            settingRule3 = 0;
            settingRule4 = 0;
            settingRule5 = 0;
            lastModule = getModule();
            logic = setLogic();
            if(!level.isClientSide()){
                CasinoPacketHandler.sendToChunk(new MessageInventoryClient(inventory, storageToken, storageReward, worldPosition), level.getChunkAt(worldPosition));
            }
            if(level.getBlockState(worldPosition).getBlock() instanceof BlockArcade) {
                if(level.isClientSide()){
                    CasinoPacketHandler.sendToServer(new MessageModuleServer(worldPosition));
                }
            }
        }
    }





    //----------------------------------------GET/SET----------------------------------------//

    public Item getKey(){
        return inventory.get(0).getItem();
    }
    public Item getModule() {
        return inventory.get(1).getItem();
    }
    public ItemStack getTokenIO() {
        return inventory.get(2);
    }

    public Item getTokenBET() {
        return inventory.get(3).getItem();
    }
    public boolean isTokenBET(ItemStack itemstack) {
        return itemstack.getItem() == inventory.get(3).getItem();
    }
    public ItemStack getTokenStackBET() {
        return inventory.get(3);
    }
    public void setTokenBET(ItemStack itemstack) {
        inventory.set(3, new ItemStack(itemstack.getItem(), 1));
    }

    public Item getTokenREW() {
        return inventory.get(4).getItem();
    }
    public boolean isTokenREW(ItemStack itemstack) {
        return itemstack.getItem() == inventory.get(4).getItem();
    }
    public ItemStack getTokenStackREW() {
        return inventory.get(4);
    }
    public void setTokenREW(ItemStack itemstack) {
        inventory.set(4, new ItemStack(itemstack.getItem(), 1));
    }

    private LogicModule setLogic(){
        if(this instanceof BlockEntityArcade){
            if(getModule() == CasinoKeeper.RULEBOOK_6_1.get()) return new Logic61(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_5_1.get()) return new Logic51(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_6_2.get()) return new Logic62(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_5_2.get()) return new Logic52(tableID);
        }
        if(this instanceof BlockEntityCardTableBase || this instanceof BlockEntityCardTableWide){
            if(getModule() == CasinoKeeper.RULEBOOK_2_1.get()) return new Logic21(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_2_2.get()) return new Logic22(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_1_1.get()) return new Logic11(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_3_1.get()) return new Logic31(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_3_2.get()) return new Logic32(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_3_3.get()) return new Logic33(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_4_1.get()) return new Logic41(tableID);
            if(getModule() == CasinoKeeper.RULEBOOK_4_2.get()) return new Logic42(tableID);
        }
        if(this instanceof BlockEntitySlotMachine){
            return new LogicSlotGame(tableID, getModule());
        }
        return new LogicDummy(tableID);
    }

    private void playSound(){
        if(!level.isClientSide()){
            level.playSound(null, worldPosition, getSound(logic.jingle), NEUTRAL, 0.75f, 1);
        }
        logic.jingle = 0;
    }

    private SoundEvent getSound(int index){
        switch(index){
            case  0: default:
            case  1: return CasinoKeeper.SOUND_CARD_PLACE.get();
            case  2: return CasinoKeeper.SOUND_CARD_SHOVE.get();
            case  3: return CasinoKeeper.SOUND_CHIP.get();
            case  4: return CasinoKeeper.SOUND_DICE.get();
            case  5: return CasinoKeeper.SOUND_IMPACT.get();
            case  6: return CasinoKeeper.SOUND_MENU.get();
            case  7: return CasinoKeeper.SOUND_PAUSE_OFF.get();
            case  8: return CasinoKeeper.SOUND_PAUSE_ON.get();
            case  9: return CasinoKeeper.SOUND_PICKUP.get();
            case 10: return CasinoKeeper.SOUND_REWARD.get();
            case 11: return CasinoKeeper.SOUND_ROULETTE.get();
            case 12: return CasinoKeeper.SOUND_TETRIS.get();
        }
    }
    
    public void sendRulesToLogic(){
        int[] values = new int[]{settingRule1, settingRule2, settingRule3, settingRule4, settingRule5};
        logic.setupRuleSet(values);
    }
    
    @Override
    public ContainerData getIntArray() {
        return casinoData;
    }



}
