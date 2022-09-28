package mod.casinocraft.util;

import mod.lucky77.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ButtonSet {

    private List<Button> buttons = new ArrayList<>();
    private int index = -1;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ButtonSet(){

    }





    //----------------------------------------ADD_BUTTON----------------------------------------//

    public void addButton(Vector2 position, Vector2 map, ActivatorFunction activator, CommandFunction command){
        buttons.add(new Button(position, map, map, 3, -1, activator, command));
    }

    public void addButton(Vector2 position, Vector2 map, int size, ActivatorFunction activator, CommandFunction command){
        buttons.add(new Button(position, map, map, size, -1, activator, command));
    }

    public void addButton(Vector2 position, Vector2 mapOFF, Vector2 mapON, int size, int toggleID, ActivatorFunction activator, CommandFunction command){
        buttons.add(new Button(position, mapOFF, mapON, size, toggleID, activator, command));
    }





    //----------------------------------------SUPPORT----------------------------------------//

    public void update(int left, int top, double mouseX, double mouseY){
        for(Button b : buttons){
            b.update(left, top, mouseX, mouseY);
        }
    }

    public void interact(int left, int top, double mouseX, double mouseY){
        for(Button b : buttons){
            b.collideWithMouse(left, top, mouseX, mouseY);
        }
    }

    public void releaseToggle(){
        for(Button b : buttons){
            b.isSwitched = false;
        }
    }





    //----------------------------------------GET-SET----------------------------------------//

    public boolean next(){
        index++;
        if(index >= buttons.size()){
            index = -1;
            return false;
        }
        return true;
    }

    public Vector2 pos(){
        return buttons.get(index).position;
    }

    public Vector2 map(){
        return buttons.get(index).map();
    }

    public int sizeX(){
        return buttons.get(index).width();
    }

    public int sizeY(){
        return buttons.get(index).height();
    }

    public Vector2 highlight(){
        return buttons.get(index).highlight();
    }

    public boolean visible(){
        return buttons.get(index).isActive;
    }

    public boolean isHighlighted(){
        return visible() && buttons.get(index).isHighlighted;
    }

}
