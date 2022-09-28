package mod.casinocraft.util;

import mod.lucky77.util.Vector2;

public class Button {

    public Vector2 position;
    private Vector2 mapOFF;
    private Vector2 mapON;
    private int size;
    public boolean isActive;
    public boolean isSwitched;
    public boolean isHighlighted;
    private CommandFunction command;
    private ActivatorFunction activator;
    private int toggleID;
    private int timer;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public Button(Vector2 position, Vector2 mapOFF, Vector2 mapON, int size, int toggleID, ActivatorFunction activator, CommandFunction command){
        this.position  = position;
        this.mapOFF    = mapOFF;
        this.mapON     = mapON;
        this.size      = size;
        this.toggleID  = toggleID;
        this.activator = activator;
        this.command   = command;
        isActive       = true;
        isSwitched     = false;
        timer          = 0;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public void collideWithMouse(int left, int top, double mouseX, double mouseY){
        if(isActive){
            if(left + position.X < mouseX && mouseX < left + position.X + width()){
                if(top + position.Y < mouseY && mouseY < top + position.Y + height()){
                    command.run();
                    if(toggleID == 0){
                        isSwitched = !isSwitched;
                    } if(toggleID > 0){
                        isSwitched = true;
                        timer = toggleID;
                    }
                }
            }
        }
    }

    public void update(int left, int top, double mouseX, double mouseY){
        isActive = activator.run();
        if(isSwitched){
            if(toggleID > 0){
                timer--;
                if(timer == 0){
                    isSwitched = false;
                }
            }
        }
        if(isActive){
            isHighlighted = false;
            if(left + position.X < mouseX && mouseX < left + position.X + width()){
                if(top + position.Y < mouseY && mouseY < top + position.Y + height()){
                    isHighlighted = true;
                }
            }
        }
    }





    //----------------------------------------GET_SET----------------------------------------//

    public Vector2 map(){
        if(isSwitched){
            return mapON;
        } return mapOFF;
    }

    public Vector2 highlight(){
        if(size == 0) return ButtonMap.LIGHT_MICRO;
        if(size == 1) return ButtonMap.LIGHT_SMALL;
        if(size == 2) return ButtonMap.LIGHT_BASIC;
        if(size == 3) return ButtonMap.LIGHT_LARGE;
        return ButtonMap.LIGHT_MICRO;

    }

    public int width(){
        if(size == 0) return 14;
        if(size == 1) return 22;
        if(size == 2) return 39;
        if(size == 3) return 78;
        return 0;
    }

    public int height(){
        if(size == 0) return 14;
        if(size == 1) return 22;
        if(size == 2) return 22;
        if(size == 3) return 22;
        return 0;
    }

}
