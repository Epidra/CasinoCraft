package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.SoundMap;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CHIP;

public class LogicMinoPink extends LogicModule {   // FanTan

    public List<Card> chips = new ArrayList<>();
    private int max_chips;
    private int alpha;
    private boolean chips_up;
    public boolean hasPlaced = false;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoPink(int tableID) {
        super(tableID, 4, 1);
    }





    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {
        chips.clear();
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {
        timeout = 0;
        if(action == -3){ // WAIT
            activePlayer++;
            hasPlaced = false;
            if(activePlayer >= getFirstFreePlayerSlot()){
                turnstate = 3;
                spin();
            }
        }
        else if(action == -2) { // ANOTHER
            hasPlaced = false;
            boolean temp = false;
            for(int y = 0; y < grid[0].length; y++){
                for(int x = 0; x < grid.length; x++){
                    if(grid[x][y] == 0){
                        selector.set(x, y);
                        temp = true;
                        break;
                    }
                }
                if(temp) break;
            }
        } else if(action == -1){ // PLACE
            hasPlaced = true;
            if(selector.X > -1){
                grid[selector.X][selector.Y] = activePlayer+1;
                selector.set(-1, -1);
            }
        } else if(action >= 0) { // place on field
            if(grid[action][0] == 0){
                selector.set(action, 0);
                setJingle(SOUND_CHIP);
            }
        }
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {
        if(turnstate == 2){
            timeout++;
            if(timeout == timeoutMAX){
                if(!hasPlaced) command(-1);
                command(-3);
            }
        }
        if(turnstate == 3) {
            if(chips_up) {
                chips.add(new Card((tableID == 1 ? 32 : -32) + RANDOM.nextInt(tableID == 1 ? 192 : 320), 64 + RANDOM.nextInt(160)));
                if(chips.size() == max_chips) {
                    chips_up = false;
                    alpha = 100;
                }
            } else {
                alpha -= 20;
                if(alpha <= 0) {
                    alpha = 100;
                    chips.remove(chips.size() - 1);
                    chips.remove(chips.size() - 1);
                    chips.remove(chips.size() - 1);
                    chips.remove(chips.size() - 1);
                    if(chips.size() <= 4) {
                        result();
                    }
                }
            }
        }
    }

    public void updateMotion() {

    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        chips     = loadCardList(compound, 0);
        max_chips = compound.getInt("maxchips");
        alpha     = compound.getInt("alpha");
        chips_up  = compound.getBoolean("chipsup");
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveCardList(compound, 0,      chips);
        compound.putInt("maxchips",    max_chips);
        compound.putInt("alpha",       alpha);
        compound.putBoolean("chipsup", chips_up);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void spin(){
        max_chips = 40 + RANDOM.nextInt(60);
        chips_up = true;
    }

    private void result() {
        turnstate = 4;
        hand = "Chips: " + chips.size();
        for(int i = 0; i < 4; i++){
            if(grid[i][0] > 0){
                reward[grid[i][0]-1] += chips.size() == i+1 ? 4 : 0;
                grid[i][0] = chips.size() == i+1 ? grid[i][0] : -1;
            }
        }
        if(reward[0] > 0 || reward[1] > 0 || reward[2] > 0 || reward[3] > 0 || reward[4] > 0 || reward[5] > 0) setJingle(SoundMap.SOUND_REWARD);

    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return true;
    }

    public int getID(){
        return 43;
    }



}
