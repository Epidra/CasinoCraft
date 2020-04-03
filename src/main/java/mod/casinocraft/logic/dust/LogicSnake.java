package mod.casinocraft.logic.dust;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Entity;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogicSnake extends LogicBase {

    public Entity octanom_head = new Entity(1, new Vector2(0, 0), new Vector2(0, 0));
    public List<Entity> octanom_tail = new ArrayList<Entity>();

    public int temp_player; // Player Input
    public int temp_auto;   // automatic movement

    public int pointer;

    public Vector2 point = new Vector2(-1, -1);

    public boolean active_move_tail;



    //--------------------CONSTRUCTOR--------------------

    public LogicSnake(){
        super(true, 0, "a_snake");
    }



    //--------------------BASIC--------------------

    public void start2(){
        active_move_tail = true;
        pointer = -1;
        temp_player = 0;
        temp_auto = 0;
        Command_Spawn_Point();
        octanom_tail.clear();
    }

    public void actionTouch(int action){
        temp_player = action;
    }

    public void updateMotion(){

    }

    public void updateLogic(){
        if(turnstate >= 2 && turnstate < 4) {
            octanom_head.Update();
            if(active_move_tail) {
                for(Entity tail : octanom_tail) { tail.Update(); }
            }
            Command_Move();
            Command_Collision();
        }
    }

    public void load2(CompoundNBT compound){
        octanom_head = loadEntity(    compound, 0);
        octanom_tail = loadEntityList(compound, 1);
        temp_player = compound.getInt("tempplayer");
        temp_auto = compound.getInt("tempauto");
        pointer = compound.getInt("pointer");
        point.set(compound.getInt("pointx"), compound.getInt("pointy"));
        active_move_tail = compound.getBoolean("activemovetail");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveEntity(    compound, 0, octanom_head);
        saveEntityList(compound, 1, octanom_tail);
        compound.putInt("tempplayer", temp_player);
        compound.putInt("temp_auto", temp_auto);
        compound.putInt("pointer", pointer);
        compound.putInt("pointx", point.X);
        compound.putInt("pointy", point.Y);
        compound.putBoolean("active_move_tail", active_move_tail);
        return compound;
    }



    //--------------------CUSTOM--------------------

    private void Command_Move() {
        if(octanom_head.Get_Pos().matches(octanom_head.Get_Next())) {
            Vector2 position = new Vector2(octanom_head.Get_Pos());
            // current moving direction
            // Cancels out moving in opposite direction (and running into own tail)
            //if(octanom_head.Get_Vel().Y < 0) { temp = 1; if(temp_movement == 2) temp_movement = 0; }
            //if(octanom_head.Get_Vel().Y > 0) { temp = 2; if(temp_movement == 1) temp_movement = 0; }
            //if(octanom_head.Get_Vel().X < 0) { temp = 3; if(temp_movement == 4) temp_movement = 0; }
            //if(octanom_head.Get_Vel().X > 0) { temp = 4; if(temp_movement == 3) temp_movement = 0; }

            // Updates Velocity
            //if(temp_movement == 1) { octanom_head.Set_InMotion( 0, -1); }
            //if(temp_movement == 2) { octanom_head.Set_InMotion( 0,  1); }
            //if(temp_movement == 3) { octanom_head.Set_InMotion(-1,  0); }
            //if(temp_movement == 4) { octanom_head.Set_InMotion( 1,  0); }

            // Updates Next()
            if(temp_player == 1 && octanom_head.Get_Next().Y !=   0) { octanom_head.Set_InMotion( 0, -Speed()); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 1 && octanom_head.Get_Next().Y ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 2 && octanom_head.Get_Next().Y != 240) { octanom_head.Set_InMotion( 0,  Speed()); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 2 && octanom_head.Get_Next().Y == 240) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 3 && octanom_head.Get_Next().X !=   0) { octanom_head.Set_InMotion(-Speed(),  0); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 3 && octanom_head.Get_Next().X ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 4 && octanom_head.Get_Next().X != 240-32) { octanom_head.Set_InMotion( Speed(),  0); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 4 && octanom_head.Get_Next().X == 240-32) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_auto == 1 && octanom_head.Get_Next().Y !=   0) { octanom_head.Set_InMotion( 0, -Speed());  }
            else if(temp_auto == 1 && octanom_head.Get_Next().Y ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 2 && octanom_head.Get_Next().Y != 240) { octanom_head.Set_InMotion( 0,  Speed());  }
            else if(temp_auto == 2 && octanom_head.Get_Next().Y == 240) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 3 && octanom_head.Get_Next().X !=   0) { octanom_head.Set_InMotion(-Speed(),  0);  }
            else if(temp_auto == 3 && octanom_head.Get_Next().X ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 4 && octanom_head.Get_Next().X != 240-32) { octanom_head.Set_InMotion( Speed(),  0);  }
            else if(temp_auto == 4 && octanom_head.Get_Next().X == 240-32) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            //if(active_move_tail)
            for(Entity tail : octanom_tail) {
                if(position.X > tail.Get_Pos().X) { tail.Set_Pos(position.X - 16, position.Y     ); tail.Set_Vel( Speed(),  0); }
                else if(position.X < tail.Get_Pos().X) { tail.Set_Pos(position.X + 16, position.Y     ); tail.Set_Vel(-Speed(),  0); }
                else if(position.Y > tail.Get_Pos().Y) { tail.Set_Pos(position.X     , position.Y - 16); tail.Set_Vel( 0,  Speed()); }
                else if(position.Y < tail.Get_Pos().Y) { tail.Set_Pos(position.X     , position.Y + 16); tail.Set_Vel( 0, -Speed()); }
                position.set(tail.Get_Pos());
            }
            active_move_tail = !octanom_head.Get_Vel().matches(0, 0);
        }
    }

    private void Command_Collision() {
        for(int i = 0; i < 5; i++) {
            if(octanom_head.Get_Pos().matches(point.X*16, point.Y*16)) {
                scorePoint++;
                pointer = i;
                Command_Spawn_Point();
                Command_Spawn_Tail();
            }
        }
        if(octanom_tail.size() > 1) {
            for(Entity tail : octanom_tail) {
                if(octanom_head.Get_Pos().matches(tail.Get_Pos())) {
                    turnstate = 4;
                }
            }
        }
    }

    private int Speed(){
        return 4;
    }

    private void Command_Spawn_Point() {
        boolean temp_break = false;
        int x = 0;
        int y = 0;
        for(int i = 0; i < 1; i++) {
            if(pointer == i || (pointer == -1 && i < 1)) {
                int b = 0;
                temp_break = false;
                while(!temp_break) {
                    b++;
                    boolean temp_internal = false;
                    x = RANDOM.nextInt(12)+1;
                    y = RANDOM.nextInt(12)+1;
                    if(b < 10) {
                        if(octanom_head.Get_Next() != new Vector2(x, y)) {
                            for(Entity tail : octanom_tail) {
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
                        if(octanom_head.Get_Next() != new Vector2(x, y)) {
                            temp_break = true;
                        }
                    }
                    point = new Vector2(x, y);
                }
            }
        }
    }

    private void Command_Spawn_Tail() {
        Vector2 pos = new Vector2(octanom_head.Get_Pos()/*.offset(octanom_head.Get_Vel().X*-16, octanom_head.Get_Vel().Y*-16)*/);
        int i = 0;
        for(Entity tail : octanom_tail) {
            if(i + 1 == octanom_tail.size()) {
                pos.set(tail.Get_Pos()/*.offset(tail.Get_Vel().X*16, tail.Get_Vel().Y*16)*/);
            }
            i++;
        }
        octanom_tail.add(new Entity(0, pos, pos));
    }

}
