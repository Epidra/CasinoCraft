package mod.casinocraft.util;

public class Ship {

    Vector2 position = new Vector2(0, 0); // The actual Position of the Entity
    Vector2 next     = new Vector2(0, 0); // Position its moving to
    Vector2 velocity = new Vector2(0, 0); // The Speed it moves with each Tick
    int lookDirection = 0;
    int hp = 0;
    public int ai = 0;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public Ship(int _ai, Vector2 _position, Vector2 _next) {
        ai = _ai;
        position = _position;
        velocity = new Vector2(0, 0);
        next = _next;
    }

    public Ship(int _ai, Vector2 _position, Vector2 _velocity, Vector2 _next) {
        ai = _ai;
        position = _position;
        velocity = _velocity;
        next = _next;
        if(velocity.X < 0) lookDirection = 2;
        if(velocity.X > 0) lookDirection = 3;
        if(velocity.Y < 0) lookDirection = 0;
        if(velocity.Y > 0) lookDirection = 1;
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void update() {
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




    //----------------------------------------SUPPORT----------------------------------------//

    public Vector2 getPos() { return position; }
    public Vector2 getVel() { return velocity; }
    public Vector2 getNext(){ return next; }
    public int getHP()  { return hp; }
    public Vector2 getGrid(){ return new Vector2(position.X / 16, position.Y / 16); }
    public int getLookDirection() { return lookDirection; }

    public void setPos(int x, int y) { position.set(x, y); }
    public void setHP(int i) { hp = i; }
    public void setNext(Vector2 i) { next.set(i); }
    public void changeHP(int i) { hp = hp + i; }
    public void setInMotion(int x, int y) { // ???
        velocity.set(x, y);
        if(velocity.X < 0) lookDirection = 2;
        if(velocity.X > 0) lookDirection = 3;
        if(velocity.Y < 0) lookDirection = 0;
        if(velocity.Y > 0) lookDirection = 1;
        if(velocity.Y < 0) next.Y-=16;
        if(velocity.Y > 0) next.Y+=16;
        if(velocity.X < 0) next.X-=16;
        if(velocity.X > 0) next.X+=16;
    }
    public boolean isMoving(){
        return velocity.X != 0 || velocity.Y != 0;
    }

    public void setVel(int x, int y) {
        velocity.set(x, y);
    }

}
