package mod.casinocraft.util;

import mod.shared.util.Vector2;

public class Entity {

    Vector2 position = new Vector2(0, 0); // The actual Position of the Entity
    Vector2 next     = new Vector2(0, 0); // Position its moving to
    Vector2 velocity = new Vector2(0, 0); // The Speed it moves with each Tick

    int lookDirection = 0;

    int hp = 0;
    public int ai = 0;

    public Entity(int _ai, Vector2 _position, Vector2 _next) {
        ai = _ai;
        position = _position;
        velocity = new Vector2(0, 0);
        next = _next;
    }

    public Entity(int _ai, Vector2 _position, Vector2 _velocity, Vector2 _next) {
        ai = _ai;
        position = _position;
        velocity = _velocity;
        next = _next;
        if(velocity.X < 0) lookDirection = 1;
        if(velocity.X > 0) lookDirection = 3;
        if(velocity.Y < 0) lookDirection = 2;
        if(velocity.Y > 0) lookDirection = 0;
    }

    public void Update() { // Default
        Update(false);
    }

    public void Update(boolean powered_up) { // Only for Octanom
        if(ai == 0){
            position.add(velocity);
        } else {
            if(!velocity.matches(0, 0)){
                position.add(velocity);
                if(velocity.X < 0 && position.X <= next.X){ velocity.set(0, 0); position.set(next); }
                if(velocity.X > 0 && position.X >= next.X){ velocity.set(0, 0); position.set(next); }
                if(velocity.Y < 0 && position.Y <= next.Y){ velocity.set(0, 0); position.set(next); }
                if(velocity.Y > 0 && position.Y >= next.Y){ velocity.set(0, 0); position.set(next); }
            }
        }
    }

    public Vector2 Get_Pos() { return position; }
    public Vector2 Get_Vel() { return velocity; }
    public Vector2 Get_Next(){ return next; }
    public int     Get_HP()  { return hp; }
    public Vector2 Get_Grid(){ return new Vector2(position.X / 16, position.Y / 16); }
    public int Get_LookDirection() { return lookDirection; }

    public void Set_Pos(int x, int y) { position.set(x, y); }
    public void Set_HP(int i) { hp = i; }
    public void Set_Next(Vector2 i) { next.set(i); }
    public void Change_HP(int i) { hp = hp + i; }
    public void Set_InMotion(int x, int y) { // ???
        velocity.set(x, y);
        if(velocity.X < 0) lookDirection = 1;
        if(velocity.X > 0) lookDirection = 3;
        if(velocity.Y < 0) lookDirection = 2;
        if(velocity.Y > 0) lookDirection = 0;
        if(velocity.Y < 0) next.Y-=16;
        if(velocity.Y > 0) next.Y+=16;
        if(velocity.X < 0) next.X-=16;
        if(velocity.X > 0) next.X+=16;
    }
    public boolean isMoving(){
        return velocity.X != 0 || velocity.Y != 0;
    }

    public void Set_Vel(int x, int y) {
        velocity.set(x, y);

    }

}
