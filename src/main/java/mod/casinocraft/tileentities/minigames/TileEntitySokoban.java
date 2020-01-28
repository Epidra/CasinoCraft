package mod.casinocraft.tileentities.minigames;

import java.util.ArrayList;
import java.util.List;

import mod.shared.util.Vector2;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Entity;
import mod.casinocraft.util.MapRoom;
import net.minecraft.util.math.BlockPos;

public class TileEntitySokoban extends TileEntityCasino {
	
	MapRoom MP = new MapRoom();

    Entity octanom;
    List<Entity> crate = new ArrayList<Entity>();
    List<Entity> cross = new ArrayList<Entity>();

    boolean moving;
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntitySokoban(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new     int[16][12];
        gridB = new boolean[16][12];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
        gridB = new boolean[16][12];
        gridI = new int[16][12];
        octanom = new Entity(1, new Vector2(16 * 15, 16 * 15), new Vector2(16 * 15, 16 * 15));
        crate.clear();
        cross.clear();
        Load_Map();
	}
    
    public void actionTouch(int action){
    	Command_Move(action);
    }
    
    public void update(){
		if(moving) {
            boolean swittch = true;
            for(Entity c : crate) {
                if(c.Get_Pos().X == c.Get_Next().X * 16 && c.Get_Pos().Y == c.Get_Next().Y * 16) {
                    c.Set_InMotion(0, 0);
                } else {
                    swittch = false;
                }
            }
            if(swittch) {
                moving = false;
            }
        } else {
            // Input
        }
		if(turnstate >= 2 && turnstate < 4) {
            octanom.Update();
            boolean win = true;
            for(Entity e : crate) {
            	e.Update();
                boolean hp1 = true;
                for(Entity c : cross) {
                    if(c.Get_Pos().matches(e.Get_Pos())) {
                        hp1 = false;
                    }
                }
                if(hp1) { e.Set_HP(1); win = false; } else { e.Set_HP(2); }
            }
            for(Entity e : cross) {
                e.Update();
            }
            if(win && turnstate < 4) {
                scorePoints = crate.size() * 500;
                turnstate = 4;
            }
            //Command_Move();
        }
	}
	
    
    
  //--------------------GETTER--------------------
    
    public Entity getEntity(int index){
		return octanom;
	}
	
	public List<Entity> getEntityList(int index){
		if(index == 0) return crate;
		return cross;
	}
	
	public int getValue(int index){
		return gridI[index%16][index/16];
	}
	
	public boolean getFlag(int index){
		return gridB[index%16][index/16];
	}
	
	public String getString(int index){
		return "";
	}
    
	
	
	//--------------------CUSTOM--------------------
	
	private void Load_Map() {
        List<String> list = MP.LoadSokoban(rand);
        int y = 0;
        for(String s : list) {
            for(int x = 0; x < 16; x++) {
            	char c = s.charAt(x);
                if(c != ' ') gridI[x][y] = ConvertGrid(x, y);
                switch(c) {
                    case ' ': break;
                    case 'X': gridB[x][y] = true; break;
                    case 'O': octanom = new Entity(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y));  break;
                    case 'M': cross.add(new Entity(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y))); break;
                    case 'C': crate.add(new Entity(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y))); break;
                }
            }
            y++;
        }
    }

    private int ConvertGrid(int x, int y) {
        if(y % 2 == 0) if(x % 2 == 0) return 1;
        if(y % 2 == 0) if(x % 2 != 0) return 2;
        if(y % 2 != 0) if(x % 2 == 0) return 2;
        if(y % 2 != 0) if(x % 2 != 0) return 1;
        return 0;
    }
	
    private void Command_Move(int direction) {
    	if(!octanom.isMoving()){
    		int x = 0;
    		int y = 0;
    		if(direction == 0){ x =  0; y = -1; }
    		if(direction == 1){ x =  0; y =  1; }
    		if(direction == 2){ x = -1; y =  0; }
    		if(direction == 3){ x =  1; y =  0; }
    		if(!gridB[octanom.Get_Grid().X + x][octanom.Get_Grid().Y + y]) { // Free space
    			boolean blockedO = false;
                for(Entity c : crate) {
                    if(c.Get_Grid().X == octanom.Get_Grid().X + x && c.Get_Grid().Y == octanom.Get_Grid().Y + y) {
                    	blockedO = true;
                        if(!gridB[octanom.Get_Grid().X + x*2][octanom.Get_Grid().Y + y*2]) {
                        	boolean blockedC = false; 
                            for(Entity c2 : crate) {
                            	if(c.Get_Grid().X == octanom.Get_Grid().X + x*2 && c.Get_Grid().Y == octanom.Get_Grid().Y + y*2) {
                            		blockedC = true;
                            		// crate blocked by crate
                                }
                            }
                            if(!blockedC) {
                                moving = true;
                                c.Set_InMotion(x*2, y*2);
                            }
                        }
                    }
                }
                if(!blockedO)
                	octanom.Set_InMotion(x*2, y*2);
            }
    	}
    }
    
}