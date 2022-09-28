package mod.casinocraft.logic.chip;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Ship;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundTag;
import java.util.ArrayList;
import java.util.List;
import static mod.casinocraft.util.KeyMap.*;
import static mod.casinocraft.util.SoundMap.SOUND_IMPACT;
import static mod.casinocraft.util.SoundMap.SOUND_TETRIS;

public class LogicChipOrange extends LogicModule {   // Snake

    public Ship octanom_head = new Ship(1, new Vector2(0, 0), new Vector2(0, 0));
    public List<Ship> octanom_tail = new ArrayList<Ship>();
    public int temp_player; // Player Input
    public int temp_auto;   // automatic movement
    public Vector2 point = new Vector2(0, 0);
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

    public void updateMotion(){

    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        octanom_head = loadEntity(    compound, 0);
        octanom_tail = loadEntityList(compound, 1);
        temp_player  = compound.getInt("tempplayer");
        temp_auto    = compound.getInt("temp_auto");
        point.set(compound.getInt("pointx"), compound.getInt("pointy"));
        active_move_tail = compound.getBoolean("active_move_tail");
    }

    public CompoundTag save2(CompoundTag compound){
        saveEntity(    compound, 0, octanom_head);
        saveEntityList(compound, 1, octanom_tail);
        compound.putInt("tempplayer", temp_player);
        compound.putInt("temp_auto",  temp_auto);
        compound.putInt("pointx", point.X);
        compound.putInt("pointy", point.Y);
        compound.putBoolean("active_move_tail", active_move_tail);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void commandMove() {
        if(octanom_head.getPos().matches(octanom_head.getNext())) {
            Vector2 position = new Vector2(octanom_head.getPos());

            // Updates Next()
                 if(temp_player == KEY_UP    && octanom_head.getNext().Y !=   0) { octanom_head.setInMotion( 0, -speed()); temp_auto = 1; temp_player = 0; }
            else if(temp_player == KEY_UP    && octanom_head.getNext().Y ==   0) { octanom_head.setInMotion( 0,        0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == KEY_DOWN  && octanom_head.getNext().Y != 224) { octanom_head.setInMotion( 0,  speed()); temp_auto = 2; temp_player = 0; }
            else if(temp_player == KEY_DOWN  && octanom_head.getNext().Y == 224) { octanom_head.setInMotion( 0,        0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == KEY_LEFT  && octanom_head.getNext().X !=   0) { octanom_head.setInMotion(-speed(),  0); temp_auto = 3; temp_player = 0; }
            else if(temp_player == KEY_LEFT  && octanom_head.getNext().X ==   0) { octanom_head.setInMotion( 0,        0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == KEY_RIGHT && octanom_head.getNext().X != 176) { octanom_head.setInMotion( speed(),  0); temp_auto = 4; temp_player = 0; }
            else if(temp_player == KEY_RIGHT && octanom_head.getNext().X == 176) { octanom_head.setInMotion( 0,        0); temp_auto = 0; temp_player = 0; }
            else if(temp_auto == 1 && octanom_head.getNext().Y !=   0) { octanom_head.setInMotion( 0, -speed());  }
            else if(temp_auto == 1 && octanom_head.getNext().Y ==   0) { octanom_head.setInMotion( 0,        0); temp_auto = 0; }
            else if(temp_auto == 2 && octanom_head.getNext().Y != 224) { octanom_head.setInMotion( 0,  speed());                }
            else if(temp_auto == 2 && octanom_head.getNext().Y == 224) { octanom_head.setInMotion( 0,        0); temp_auto = 0; }
            else if(temp_auto == 3 && octanom_head.getNext().X !=   0) { octanom_head.setInMotion(-speed(),  0);                }
            else if(temp_auto == 3 && octanom_head.getNext().X ==   0) { octanom_head.setInMotion( 0,        0); temp_auto = 0; }
            else if(temp_auto == 4 && octanom_head.getNext().X != 176) { octanom_head.setInMotion( speed(),  0);                }
            else if(temp_auto == 4 && octanom_head.getNext().X == 176) { octanom_head.setInMotion( 0,        0); temp_auto = 0; }
            for(Ship tail : octanom_tail) {
                     if(position.X > tail.getPos().X) { tail.setPos(position.X - 16, position.Y     ); tail.setVel( speed(),  0); }
                else if(position.X < tail.getPos().X) { tail.setPos(position.X + 16, position.Y     ); tail.setVel(-speed(),  0); }
                else if(position.Y > tail.getPos().Y) { tail.setPos(position.X     , position.Y - 16); tail.setVel( 0,  speed()); }
                else if(position.Y < tail.getPos().Y) { tail.setPos(position.X     , position.Y + 16); tail.setVel( 0, -speed()); }
                position.set(tail.getPos());
            }
            active_move_tail = !octanom_head.getVel().matches(0, 0);
        }
    }

    private void commandCollision() {
        for(int i = 0; i < 5; i++) {
            if(octanom_head.getPos().matches(point.X*16, point.Y*16)) {
                scorePoint++;
                commandSpawnPoint();
                commandSpawnTail();
                setJingle(SOUND_TETRIS);
            }
        }
        if(octanom_tail.size() > 1) {
            for(Ship tail : octanom_tail) {
                if(octanom_head.getPos().matches(tail.getPos())) {
                    turnstate = 4;
                    setJingle(SOUND_IMPACT);
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
                if(!octanom_head.getNext().matches(x, y)) {
                    for(Ship tail : octanom_tail) {
                        if(tail.getGrid().matches(x, y)) {
                            temp_internal = true;
                            break;
                        }
                    }
                    if(point.matches(x, y))
                        temp_internal = true;
                    if(!temp_internal) {
                        temp_break = true;
                    }
                }
            } else {
                if(!octanom_head.getNext().matches(x, y)) {
                    temp_break = true;
                }
            }
            point = new Vector2(x, y);
        }
    }

    private void commandSpawnTail() {
        Vector2 pos = new Vector2(octanom_head.getPos());
        int i = 0;
        for(Ship tail : octanom_tail) {
            if(i + 1 == octanom_tail.size()) {
                pos.set(tail.getPos());
            }
            i++;
        }
        octanom_tail.add(new Ship(0, pos, pos));
    }





    //----------------------------------------BASIC----------------------------------------//

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
