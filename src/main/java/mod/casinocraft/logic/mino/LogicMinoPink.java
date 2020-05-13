package mod.casinocraft.logic.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class LogicMinoPink extends LogicBase {   // FanTan

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
            }
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion() {

    }

    public void updateLogic() {
        if(turnstate == 2){
            timeout++;
            if(timeout == CasinoKeeper.config_timeout){
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




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(NBTTagCompound compound){
        chips = loadCardList(compound, 0);
        max_chips = compound.getInteger("maxchips");
        alpha = compound.getInteger("alpha");
        chips_up = compound.getBoolean("chipsup");
    }

    public NBTTagCompound save2(NBTTagCompound compound){
        saveCardList(compound, 0, chips);
        compound.setInteger("maxchips", max_chips);
        compound.setInteger("alpha", alpha);
        compound.setBoolean("chipsup", chips_up);
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
