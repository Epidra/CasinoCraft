package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class LogicFanTan extends LogicBase {

    public List<Card> chips = new ArrayList<>();

    int max_chips;
    int alpha;
    boolean chips_up;

    public LogicFanTan(int table) {
        super(false, table, "c_fantan");
    }

    @Override
    public void actionTouch(int action) {
        if(action == -1){
            turnstate = 3;
            max_chips = 40 + RANDOM.nextInt(60);
            chips_up = true;
        } else {
            selector.X = action;
        }
    }

    @Override
    public void updateMotion() {

    }

    @Override
    public void updateLogic() {
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
                        Result();
                    }
                }
            }
        }
    }

    @Override
    public void start2() {
        chips.clear();
    }

    @Override
    public void load2(CompoundNBT compound){
        chips = loadCardList(compound, 0);
        max_chips = compound.getInt("maxchips");
        alpha = compound.getInt("alpha");
        chips_up = compound.getBoolean("chipsup");
    }

    @Override
    public CompoundNBT save2(CompoundNBT compound){
        saveCardList(compound, 0, chips);
        compound.putInt("maxchips", max_chips);
        compound.putInt("alpha", alpha);
        compound.putBoolean("chipsup", chips_up);
        return compound;
    }

    private void Result() {
        turnstate = 4;
        hand = "Chips: " + chips.size();
        reward = chips.size() == selector.X ? 2 : 0;
    }

}




