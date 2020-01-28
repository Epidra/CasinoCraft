package mod.shared.util;

public class Vector2 {

    /** Horizontal Value */
    public int X;
    /** Vertical Value */
    public int Y;



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Contructor */
    public Vector2(int x, int y){
        this.X = x;
        this.Y = y;
    }

    /** Secondary Constructor, useful for re-saving a Vector2 */
    public Vector2(Vector2 v) {
        X = v.X;
        Y = v.Y;
    }



    //----------------------------------------FUNCTION----------------------------------------//

    /** Sets the Vector with the given Values */
    public void set(int x, int y) {
        X = x;
        Y = y;
    }

    /** Sets the Vector with the Values of an existing Vector */
    public void set(Vector2 v) {
        X = v.X;
        Y = v.Y;
    }

    /** Adds the given Values to the Vector */
    public void add(int x, int y) {
        X += x;
        Y += y;
    }

    /** Adds the Values of an existing Vector to this Vector */
    public void add(Vector2 v) {
        X += v.X;
        Y += v.Y;
    }

    /** Returns a new Vector from the added Values of this Vector and the given Values */
    public Vector2 offset(int x, int y){
        return new Vector2(X + x, Y + y);
    }

    /** Returns a new Vector from the added Values of this Vector and an existing Vector */
    public Vector2 offset(Vector2 v){
        return new Vector2(X + v.X, Y + v.Y);
    }

    /** Returns if this Vector is identical to the given Values */
    public boolean matches(int x, int y) {
        return X == x && Y == y;
    }

    /** Returns if this Vector is identical to an existing Vecotr */
    public boolean matches(Vector2 v) {
        return X == v.X && Y == v.Y;
    }

}
