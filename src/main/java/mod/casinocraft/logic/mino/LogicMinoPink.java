package mod.casinocraft.logic.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CHIP;

public class LogicMinoPink extends LogicModule {   // FanTan

    public List<Card> chips = new ArrayList<>();
    private int max_chips;
    private int alpha;
    private boolean chips_up;




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
        if(action == -1){
            if(selector.X > -1){
                grid[selector.X][selector.Y] = activePlayer+1;
                selector.set(-1, -1);
                spin();
            }
        } else {
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
            if(timeout == CasinoKeeper.config_timeout.get()){
                spin();
            }
        }
        if(turnstate == 3) {
            if(chips_up) {
                chips.add(new Card(32 + RANDOM.nextInt(192), 64 + RANDOM.nextInt(160)));
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

    public void load2(CompoundTag compound){
        chips = loadCardList(compound, 0);
        max_chips = compound.getInt("maxchips");
        alpha = compound.getInt("alpha");
        chips_up = compound.getBoolean("chipsup");
    }

    public CompoundTag save2(CompoundTag compound){
        saveCardList(compound, 0, chips);
        compound.putInt("maxchips", max_chips);
        compound.putInt("alpha", alpha);
        compound.putBoolean("chipsup", chips_up);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void spin(){
        turnstate = 3;
        max_chips = 40 + RANDOM.nextInt(60);
        chips_up = true;
    }

    private void result() {
        turnstate = 4;
        hand = "Chips: " + chips.size();
        for(int i = 0; i < 4; i++){
            if(grid[i][0] > 0){
                reward[grid[i][0]-1] = chips.size() == i+1 ? 4 : 0;
            }
        }

    }




    //----------------------------------------SUPPORT----------------------------------------//

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
