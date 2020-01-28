package mod.casinocraft.tileentities;

import java.util.List;
import java.util.Random;

import mod.shared.util.Vector2;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.Dice;
import mod.casinocraft.util.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityCasino extends TileEntity implements IInventory {
	
	public EnumDyeColor color;
	
	public Random rand = new Random();
	public int difficulty = 2; // 1 - easy, 2 - normal, 3 - hard
	public int scorePoints = -1;
	public int scoreLevel  = -1;
	public int scoreLives  = -1;
	
	public int    [][] gridI = new     int[4][4];
    public boolean[][] gridB = new boolean[4][4];
    
    public String hand = "NULL";
    public int reward = -1;
    
    public Vector2 selector = new Vector2(0,0);
	
	/** 0 - bet, 1 - unused, 2 - turn player, 3 - turn computer, 4 - game over, 5 - result **/
    public int turnstate;
    public TileEntityBoard board;
    
    /** Basic Constructor **/
	public TileEntityCasino(){
		
	}
	
	/** Advanced Constructor **/
	public TileEntityCasino(TileEntityBoard te, BlockPos bp){
		this.color = te.color;
		board = te;
		this.setPos(bp);
		turnstate = 0;
	}
	
	public boolean hasToken() {
		return board.bet_storage > 0;
	}
	
	public Item getToken() {
		return board.getToken();
	}
	public ItemStack getTokenStack() {
		return board.getTokenStack();
	}
	public int getBetStorage() {
		return board.bet_storage;
	}
	public int getBetLow() {
		return board.bet_low;
	}
	public int getBetHigh() {
		return board.bet_high;
	}
	
	
	
	
	
	public void start(){
        turnstate = 2;
        scorePoints = 0;
        scoreLevel  = 0;
        scoreLives  = 0;
        hand   = "empty";
        reward = 0;
        selector.set(0,0);
        start2();
	}
	
	public void start2(){
		
	}
	
	public void actionTouch(int action){
		
    }
	
	public void actionStart(int diff) {
		difficulty = diff;
		if(turnstate == 0) {
			turnstate = 2;
			start();
		} else {
			if(turnstate >= 5 && !hasToken()){
				turnstate = 1;
				start();
			} else {
				turnstate = 0;
			}
		}
	}
	
	public void update(){
		
	}
	
	
	
	// Getter Stuff
	public Vector2      getVector    (int index){ return  null; }
	public Dice         getDice      (int index){ return  null; }
	public Card         getCard      (int index){ return  null; }
	public List<Card>   getCardStack (int index){ return  null; }
	public Entity       getEntity    (int index){ return  null; }
	public List<Entity> getEntityList(int index){ return  null; }
	public int          getValue     (int index){ return     0; }
	public boolean      getFlag      (int index){ return false; }
	public String       getString    (int index){ return    ""; }
	
	
	
	// Basic Stuff
	@Override public String    getName() { return null; }
	@Override public boolean   hasCustomName() { return false; }
	@Override public int       getSizeInventory() { return 0; }
	@Override public ItemStack getStackInSlot(int index) { return null; }
	@Override public ItemStack decrStackSize(int index, int count) { return null; }
	@Override public ItemStack removeStackFromSlot(int index) { return null; }
	@Override public void      setInventorySlotContents(int index, ItemStack stack) { }
	@Override public int       getInventoryStackLimit() { return 0; }
	@Override public void      openInventory(EntityPlayer player) { }
	@Override public void      closeInventory(EntityPlayer player) { }
	@Override public boolean   isItemValidForSlot(int index, ItemStack stack) { return false; }
	@Override public int       getField(int id) { return 0; }
	@Override public void      setField(int id, int value) { }
	@Override public int       getFieldCount() { return 0; }
	@Override public void      clear() { }
	@Override public boolean   isEmpty() { return false; }
	@Override public boolean   isUsableByPlayer(EntityPlayer player) { return true; }
	
}
