package mod.casinocraft.util;

import mod.shared.util.Vector2;

public class Dice {
	
	public int number;
    public int color;
    public int posX;
    public int posY;
    public int shiftX;
    public int shiftY;
    public int rotation;
    public boolean direction;

    public Dice(int _number, int _color) {
        number = _number;
        color = _color;
        posX = 0;
        posY = 0;
        shiftX = 0;
        shiftY = 0;
        rotation = 0;
        direction = false;
    }

    public void Set_Up(int _shiftX, int _shiftY, boolean leftOrRight) {
        shiftX = _shiftX;
        shiftY = _shiftY;
        direction = leftOrRight;
    }

    public Vector2 Get_Pos() {
        return new Vector2(posX, posY);
    }

    public void Update(int shift, int randNumber) {
        posX   += (shiftX / 30);
        posY   += (shiftY / 30);
        shiftX -= (shiftX / 30);
        shiftY -= (shiftY / 30);
        number = randNumber;
        if(direction) {
            rotation -= shift * 10;
        } else {
            rotation += shift * 10;
        }
    }

    public void Reset() {
        posX = 0;
        posY = 0;
        shiftX = 0;
        shiftY = 0;
        rotation = 0;
        direction = false;
    }
	
}