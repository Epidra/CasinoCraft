package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CHIP;

public class LogicMinoLightBlue extends LogicBase {   // Ishido

    public List<Card> reserve = new ArrayList<Card>();




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoLightBlue(int tableID) {
        super(tableID, 12, 8);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {
        reserve = fillReserve();
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 12; x++){
                if(tableID == 1){
                    if((x < 2 || x > 9)){
                        grid[x][y] = -2;
                    } else {
                        grid[x][y] = -1;
                    }
                } else {
                    grid[x][y] = -1;
                }
            }
        }
        grid[5][3] = takeNextMino();
        grid[6][4] = takeNextMino();
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {
        place(action);
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {

    }

    public void updateMotion() {

    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        reserve.addAll(loadCardList(compound, 0));
    }

    public CompoundNBT save2(CompoundNBT compound){
        saveCardList(compound, 0, reserve);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void place(int action){
        int x = action % 12;
        int y = action / 12;
        int connections = canPlace(x, y);
        if(connections > 0){
            setJingle(SOUND_CHIP);
            switch(connections){
                case 1: scorePoint += 1;  break;
                case 2: scorePoint += 2;  break;
                case 3: scorePoint += 4;  break;
                case 4: scorePoint += 16; break;
            }
            grid[x][y] = takeNextMino();
            checkForGameOver();
        }
    }

    private List<Card> fillReserve(){
        List<Card> stack = new ArrayList<Card>();
        List<Card> deck  = new ArrayList<Card>();

        for(int z = 0; z < 2; z++){
            for(int y = 0; y < 6; y++) {
                for(int x = 0; x < 6; x++) {
                    stack.add(new Card(x, y));
                }
            }
        }

        while(stack.size() > 1) {
            int r = RANDOM.nextInt(stack.size() - 1);
            deck.add(stack.get(r));
            stack.remove(r);
        }
        deck.add(stack.get(0));

        return deck;
    }

    private int takeNextMino(){
        int x = reserve.get(0).number;
        int y = reserve.get(0).suit;
        reserve.remove(0);
        return x + y * 6;
    }

    public int showNextMino(){
        if(reserve.size() == 0) return -1;
        int x = reserve.get(0).number;
        int y = reserve.get(0).suit;
        return x + y * 6;
    }

    private void checkForGameOver(){
        if(reserve.size() == 0){
            turnstate = 4;
        } else {
            boolean placable = false;
            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 12; x++){
                    //if(grid[x][y] >= 0){
                        if(canPlace(x, y) > 0){
                            placable = true;
                        }
                    //}
                }
            }
            if(!placable){
                turnstate = 4;
            }
        }
    }

    private int canPlace(int x, int y){
        if(grid[x][y] >= 0){
            return 0;
        }
        int connections = 0;
        int empty = 0;
        int edge = 0;
        boolean noCon = false;
        if(x - 1 >=  0){ if(grid[x-1][y  ] <= -1){ empty++; } else if(grid[x-1][y  ] % 6 == reserve.get(0).number || grid[x-1][y  ] / 6 == reserve.get(0).suit){ connections++; } else { noCon = true; } } else { edge++; }
        if(x + 1 <  12){ if(grid[x+1][y  ] <= -1){ empty++; } else if(grid[x+1][y  ] % 6 == reserve.get(0).number || grid[x+1][y  ] / 6 == reserve.get(0).suit){ connections++; } else { noCon = true; } } else { edge++; }
        if(y - 1 >=  0){ if(grid[x  ][y-1] <= -1){ empty++; } else if(grid[x  ][y-1] % 6 == reserve.get(0).number || grid[x  ][y-1] / 6 == reserve.get(0).suit){ connections++; } else { noCon = true; } } else { edge++; }
        if(y + 1 <   8){ if(grid[x  ][y+1] <= -1){ empty++; } else if(grid[x  ][y+1] % 6 == reserve.get(0).number || grid[x  ][y+1] / 6 == reserve.get(0).suit){ connections++; } else { noCon = true; } } else { edge++; }
        return noCon ? 0 : empty + edge == 4 ? 1 : connections;
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 38;
    }

}
