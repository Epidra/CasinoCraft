package mod.casinocraft.logic.chip;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.MapRoom;
import mod.casinocraft.util.Ship;
import mod.casinocraft.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class LogicChipPink extends LogicBase {   // Sokoban

    public Ship octanom = new Ship(1, new Vector2(16 * 15, 16 * 15), new Vector2(16 * 15, 16 * 15));
    public List<Ship> crate = new ArrayList<Ship>();
    public List<Ship> cross = new ArrayList<Ship>();
    public boolean moving;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicChipPink(int tableID){
        super(tableID, 12, 12);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        crate.clear();
        cross.clear();
        loadMap();
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == -1){
            turnstate = 4;
        } else {
            commandMove(action);
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion(){

    }

    public void updateLogic(){
        if(moving) {
            boolean swittch = true;
            for(Ship c : crate) {
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
            octanom.update();
            boolean win = true;
            for(Ship e : crate) {
                e.update();
                boolean hp1 = true;
                for(Ship c : cross) {
                    if(c.Get_Pos().matches(e.Get_Pos())) {
                        hp1 = false;
                    }
                }
                if(hp1) { e.Set_HP(1); win = false; } else { e.Set_HP(2); }
            }
            for(Ship e : cross) {
                e.update();
            }
            if(win && turnstate < 4) {
                scorePoint = crate.size() * 500;
                turnstate = 4;
            }
            //Command_Move();
        }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        octanom = loadEntity(compound, 0);
        crate = (loadEntityList(compound, 1));
        cross = (loadEntityList(compound, 2));

        moving = compound.getBoolean("moving");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveEntity(    compound, 0, octanom);
        saveEntityList(compound, 1, crate);
        saveEntityList(compound, 2, cross);

        compound.putBoolean("moving", moving);

        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void loadMap() {
        List<String> list = MapRoom.loadSokoban(RANDOM);
        int y = 0;
        for(String s : list) {
            for(int x = 0; x < 12; x++) {
                char c = s.charAt(x);
                switch(c) {
                    case ' ': break;
                    case 'X': grid[x][y] = 1; break;
                    case 'O': octanom = new Ship(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y));  break;
                    case 'M': cross.add(new Ship(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y))); break;
                    case 'C': crate.add(new Ship(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y))); break;
                }
            }
            y++;
        }
    }

    private void commandMove(int direction) {
        if(!octanom.isMoving()){
            int x = 0;
            int y = 0;
            if(direction == 0){ x =  0; y = -1; }
            if(direction == 1){ x =  0; y =  1; }
            if(direction == 2){ x = -1; y =  0; }
            if(direction == 3){ x =  1; y =  0; }
            if(grid[octanom.Get_Grid().X + x][octanom.Get_Grid().Y + y] == 0) { // Free space
                boolean blockedO = false;
                for(Ship c : crate) {
                    if(c.Get_Grid().X == octanom.Get_Grid().X + x && c.Get_Grid().Y == octanom.Get_Grid().Y + y) {
                        blockedO = true;
                        if(grid[octanom.Get_Grid().X + x*2][octanom.Get_Grid().Y + y*2] == 0) {
                            boolean blockedC = false;
                            for(Ship c2 : crate) {
                                if(c.Get_Grid().X == octanom.Get_Grid().X + x*2 && c.Get_Grid().Y == octanom.Get_Grid().Y + y*2) {
                                    blockedC = true;
                                    // crate blocked by crate
                                }
                            }
                            if(!blockedC) {
                                moving = true;
                                c.Set_InMotion(x*4, y*4);
                            }
                        }
                    }
                }
                if(!blockedO)
                    octanom.Set_InMotion(x*4, y*4);
            }
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 27;
    }

}
