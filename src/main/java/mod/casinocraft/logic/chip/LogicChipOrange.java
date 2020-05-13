package mod.casinocraft.logic.chip;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Ship;
import mod.shared.util.Vector2;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class LogicChipOrange extends LogicBase {   // Snake

    public Ship octanom_head = new Ship(1, new Vector2(0, 0), new Vector2(0, 0));
    public List<Ship> octanom_tail = new ArrayList<Ship>();
    public int temp_player; // Player Input
    public int temp_auto;   // automatic movement
    public Vector2 point = new Vector2(-1, -1);
    public boolean active_move_tail;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicChipOrange(int tableID){
        super(tableID);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        active_move_tail = true;
        temp_player = 0;
        temp_auto = 0;
        commandSpawnPoint();
        octanom_tail.clear();
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        temp_player = action;
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion(){

    }

    public void updateLogic(){
        if(turnstate >= 2 && turnstate < 4) {
            octanom_head.update();
            if(active_move_tail) {
                for(Ship tail : octanom_tail) { tail.update(); }
            }
            commandMove();
            commandCollision();
        }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(NBTTagCompound compound){
        octanom_head = loadEntity(    compound, 0);
        octanom_tail = loadEntityList(compound, 1);
        temp_player = compound.getInteger("tempplayer");
        temp_auto = compound.getInteger("tempauto");
        point.set(compound.getInteger("pointx"), compound.getInteger("pointy"));
        active_move_tail = compound.getBoolean("activemovetail");
    }

    public NBTTagCompound save2(NBTTagCompound compound){
        saveEntity(    compound, 0, octanom_head);
        saveEntityList(compound, 1, octanom_tail);
        compound.setInteger("tempplayer", temp_player);
        compound.setInteger("temp_auto", temp_auto);
        compound.setInteger("pointx", point.X);
        compound.setInteger("pointy", point.Y);
        compound.setBoolean("active_move_tail", active_move_tail);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void commandMove() {
        if(octanom_head.Get_Pos().matches(octanom_head.Get_Next())) {
            Vector2 position = new Vector2(octanom_head.Get_Pos());

            // Updates Next()
            if(temp_player == 1 && octanom_head.Get_Next().Y !=   0) { octanom_head.Set_InMotion( 0, -speed()); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 1 && octanom_head.Get_Next().Y ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 2 && octanom_head.Get_Next().Y != 224) { octanom_head.Set_InMotion( 0,  speed()); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 2 && octanom_head.Get_Next().Y == 224) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 3 && octanom_head.Get_Next().X !=   0) { octanom_head.Set_InMotion(-speed(),  0); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 3 && octanom_head.Get_Next().X ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 4 && octanom_head.Get_Next().X != 176) { octanom_head.Set_InMotion( speed(),  0); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 4 && octanom_head.Get_Next().X == 176) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_auto == 1 && octanom_head.Get_Next().Y !=   0) { octanom_head.Set_InMotion( 0, -speed());  }
            else if(temp_auto == 1 && octanom_head.Get_Next().Y ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 2 && octanom_head.Get_Next().Y != 224) { octanom_head.Set_InMotion( 0,  speed());  }
            else if(temp_auto == 2 && octanom_head.Get_Next().Y == 224) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 3 && octanom_head.Get_Next().X !=   0) { octanom_head.Set_InMotion(-speed(),  0);  }
            else if(temp_auto == 3 && octanom_head.Get_Next().X ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 4 && octanom_head.Get_Next().X != 176) { octanom_head.Set_InMotion( speed(),  0);  }
            else if(temp_auto == 4 && octanom_head.Get_Next().X == 176) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            for(Ship tail : octanom_tail) {
                if(position.X > tail.Get_Pos().X) { tail.Set_Pos(position.X - 16, position.Y     ); tail.Set_Vel( speed(),  0); }
                else if(position.X < tail.Get_Pos().X) { tail.Set_Pos(position.X + 16, position.Y     ); tail.Set_Vel(-speed(),  0); }
                else if(position.Y > tail.Get_Pos().Y) { tail.Set_Pos(position.X     , position.Y - 16); tail.Set_Vel( 0,  speed()); }
                else if(position.Y < tail.Get_Pos().Y) { tail.Set_Pos(position.X     , position.Y + 16); tail.Set_Vel( 0, -speed()); }
                position.set(tail.Get_Pos());
            }
            active_move_tail = !octanom_head.Get_Vel().matches(0, 0);
        }
    }

    private void commandCollision() {
        for(int i = 0; i < 5; i++) {
            if(octanom_head.Get_Pos().matches(point.X*16, point.Y*16)) {
                scorePoint++;
                commandSpawnPoint();
                commandSpawnTail();
            }
        }
        if(octanom_tail.size() > 1) {
            for(Ship tail : octanom_tail) {
                if(octanom_head.Get_Pos().matches(tail.Get_Pos())) {
                    turnstate = 4;
                }
            }
        }
    }

    private int speed(){
        return 4;
    }

    private void commandSpawnPoint() {
        boolean temp_break = false;
        int x = 0;
        int y = 0;
        int b = 0;
        while(!temp_break) {
            b++;
            boolean temp_internal = false;
            x = RANDOM.nextInt(10)+1;
            y = RANDOM.nextInt(13)+1;
            if(b < 10) {
                if(!octanom_head.Get_Next().matches(x, y)) {
                    for(Ship tail : octanom_tail) {
                        if(tail.Get_Grid().matches(x, y)) {
                            temp_internal = true;
                            break;
                        }
                    }
                    //for(int j = 0; j < 5; j++) {
                    if(point.matches(x, y))
                        temp_internal = true;
                    //}
                    if(!temp_internal) {
                        temp_break = true;
                    }
                }
            } else {
                if(!octanom_head.Get_Next().matches(x, y)) {
                    temp_break = true;
                }
            }
            point = new Vector2(x, y);
        }
    }

    private void commandSpawnTail() {
        Vector2 pos = new Vector2(octanom_head.Get_Pos()/*.offset(octanom_head.Get_Vel().X*-16, octanom_head.Get_Vel().Y*-16)*/);
        int i = 0;
        for(Ship tail : octanom_tail) {
            if(i + 1 == octanom_tail.size()) {
                pos.set(tail.Get_Pos()/*.offset(tail.Get_Vel().X*16, tail.Get_Vel().Y*16)*/);
            }
            i++;
        }
        octanom_tail.add(new Ship(0, pos, pos));
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 26;
    }

}
