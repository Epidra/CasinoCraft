package mod.casinocraft.logic;

import mod.casinocraft.util.Card;
import mod.casinocraft.util.Dice;
import mod.casinocraft.util.Ship;
import mod.shared.util.Vector2;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class LogicBase {

    public final Random RANDOM = new Random();
    public int scorePoint = -1;
    public int scoreLevel  = -1;
    public int scoreLives  = -1;
    public String hand = "NULL";
    public String[] currentPlayer = new String[]{"void", "void", "void", "void", "void", "void"};
    public int[] reward = new int[]{0, 0, 0, 0, 0, 0};
    public int[][] grid = new int[1][1];
    public Vector2 selector = new Vector2(0,0);
    /** 0 - bet, 1 - unused, 2 - turn player, 3 - turn computer, 4 - game over, 5 - result **/
    public int turnstate;
    public final int tableID;
    public int[]    scoreHigh = new int[18];
    public String[] scoreName = new String[18];
    public int    scoreLast = 18; // Position of newest entry in highscore, used for highlighting
    public boolean pause = false;
    public int frame = 0;
    public int activePlayer = 0;
    public int timeout = 0;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Constructor without Grid **/
    public LogicBase(int table){
        this(table, 1, 1);
    }

    /** Default Constructor **/
    public LogicBase(int tableID, int gridX, int gridY){
        this.tableID = tableID;
        if(hasHighscore()) setupHighscore();
        grid = new int[gridX][gridY];
    }




    //----------------------------------------START----------------------------------------//

    public void start(int seed){
        RANDOM.setSeed(seed);
        if(turnstate == 0) {
            if(tableID == 0){
                turnstate = 1;
            } else {
                turnstate = 2;
            }
        } else {
            if(turnstate >= 5){
                turnstate = 1;
            } else {
                turnstate = 0;
            }
        }
        scorePoint = 0;
        scoreLevel  = 0;
        scoreLives  = 0;
        hand   = "empty";
        reward = new int[]{0, 0, 0, 0, 0, 0};
        selector.set(0,0);
        activePlayer = 0;
        timeout = 0;
        resetGrid();
        start2();
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void update(){
        if(!pause){
            frame = (frame + 1) % 48;
            updateMotion();
            updateLogic();
        }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load(NBTTagCompound compound){
        { // Basic
            int[] baseValues = compound.getIntArray("basevalues");
            turnstate  = baseValues[0];
            scorePoint = baseValues[1];
            scoreLevel = baseValues[2];
            scoreLives = baseValues[3];
            selector.set(baseValues[4], baseValues[5]);
            activePlayer = baseValues[6];
            hand = compound.getString("hand");
            pause = compound.getBoolean("pause");
            currentPlayer[0] = compound.getString("currentplayer0");
            currentPlayer[1] = compound.getString("currentplayer1");
            currentPlayer[2] = compound.getString("currentplayer2");
            currentPlayer[3] = compound.getString("currentplayer3");
            currentPlayer[4] = compound.getString("currentplayer4");
            currentPlayer[5] = compound.getString("currentplayer5");
        }
        { // Grid
            int[] array = compound.getIntArray("grid");
            for(int y = 0; y < grid[0].length; y++){
                for(int x = 0; x < grid.length; x++){
                    grid[x][y] = array[y*grid.length + x];
                }
            }
        }
        { // Highscore
            if(hasHighscore()){
                for(int i = 0; i < 18; i++) {
                    scoreHigh[i] = compound.getInteger("points" + i);
                    scoreName[i] = compound.getString("name" + i);
                }
            }
        }
        if(turnstate >= 2 && turnstate <= 5){
            load2(compound);
        }
    }

    public NBTTagCompound save(NBTTagCompound compound){
        { // Basic
            compound.setIntArray("basevalues", new int[]{
                    turnstate,
                    scorePoint,
                    scoreLevel,
                    scoreLives,
                    selector.X,
                    selector.Y,
                    activePlayer
            });
            compound.setString("hand", hand);
            compound.setBoolean("pause", pause);
            compound.setString("currentplayer0", currentPlayer[0]);
            compound.setString("currentplayer1", currentPlayer[1]);
            compound.setString("currentplayer2", currentPlayer[2]);
            compound.setString("currentplayer3", currentPlayer[3]);
            compound.setString("currentplayer4", currentPlayer[4]);
            compound.setString("currentplayer5", currentPlayer[5]);
        }
        { // Grid
            int[] array = new int[grid.length * grid[0].length];
            for(int y = 0; y < grid[0].length; y++){
                for(int x = 0; x < grid.length; x++){
                    array[y*grid.length + x] = grid[x][y];
                }
            }
            compound.setIntArray("grid", array);
        }
        { // Highscore
            if(hasHighscore()){
                for(int i = 0; i < 18; i++) {
                    compound.setInteger("points" + i, scoreHigh[i]);
                    compound.setString("name" + i, scoreName[i]);
                }
            }
        }

        if(turnstate >= 2 && turnstate <= 5){
            save2(compound);
        }
        return compound;
    }




    //----------------------------------------LOAD_HELPER----------------------------------------//

    protected Card[] loadCardArray(NBTTagCompound compound, int index){
        int[] array = compound.getIntArray("cardstack" + index);
        Card[] cards = new Card[array.length / 3];
        for(int i = 0; i < array.length; i += 3){
            cards[i/3] = new Card(array[i], array[i+1], array[i+2] == 1);
        }
        return cards;
    }

    protected List<Card> loadCardList(NBTTagCompound compound, int index){
        int[] array = compound.getIntArray("cardstack" + index);
        List<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < array.length; i += 3){
            cards.add(new Card(array[i], array[i+1], array[i+2] == 1));
        }
        return cards;
    }

    protected Dice[] loadDice(NBTTagCompound compound){
        int[] array = compound.getIntArray("diceset");
        Dice[] dice = new Dice[array.length / 2];
        for(int i = 0; i < array.length; i += 2){
            dice[i/2] = new Dice(array[i], array[i+1]);
        }
        return dice;
    }

    protected Ship loadEntity(NBTTagCompound compound, int index){
        int[] array = compound.getIntArray("entity" + index);
        return new Ship(array[0], new Vector2(array[1], array[2]), new Vector2(array[3], array[4]), new Vector2(array[5], array[6]));
    }

    protected List<Ship> loadEntityList(NBTTagCompound compound, int index){
        int[] array = compound.getIntArray("entitylist" + index);
        List<Ship> list = new ArrayList<Ship>();
        for(int i = 0; i < array.length; i += 7){
            list.add(new Ship( array[i], new Vector2(array[i+1], array[i+2]), new Vector2(array[i+3], array[i+4]), new Vector2(array[i+5], array[i+6])));
        }
        return list;
    }




    //----------------------------------------SAVE_HELPER----------------------------------------//

    protected NBTTagCompound saveCardArray(NBTTagCompound compound, int index, Card[] cards){
        int[] array = new int[cards.length * 3];
        for(int pos = 0; pos < cards.length; pos++){
            array[pos*3    ] = cards[pos].number;
            array[pos*3 + 1] = cards[pos].suit;
            array[pos*3 + 2] = cards[pos].hidden ? 1 : 0;
        }
        compound.setIntArray("cardstack" + index, array);
        return compound;
    }

    protected NBTTagCompound saveCardList(NBTTagCompound compound, int index, List<Card> cards){
        int[] array = new int[cards.size() * 3];
        for(int pos = 0; pos < cards.size(); pos++){
            array[pos*3    ] = cards.get(pos).number;
            array[pos*3 + 1] = cards.get(pos).suit;
            array[pos*3 + 2] = cards.get(pos).hidden ? 1 : 0;
        }
        compound.setIntArray("cardstack" + index, array);
        return compound;
    }

    protected NBTTagCompound saveDice(NBTTagCompound compound, Dice[] dice){
        int[] array = new int[dice.length * 2];
        for(int pos = 0; pos < dice.length; pos++){
            array[pos*2    ] = dice[pos].number;
            array[pos*2 + 1] = dice[pos].color;
        }
        compound.setIntArray("diceset", array);
        return compound;
    }

    protected NBTTagCompound saveEntity(NBTTagCompound compound, int index, Ship ent){
        compound.setIntArray("entity" + index, new int[]{ent.ai, ent.Get_Pos().X, ent.Get_Pos().Y, ent.Get_Next().X, ent.Get_Next().Y, ent.Get_Vel().X, ent.Get_Vel().Y});
        return compound;
    }

    protected NBTTagCompound saveEntityList(NBTTagCompound compound, int index, List<Ship> list){
        int[] array = new int[list.size() * 7];
        for(int pos = 0; pos < list.size(); pos++){
            array[pos*7    ] = list.get(pos).ai;
            array[pos*7 + 1] = list.get(pos).Get_Pos().X;
            array[pos*7 + 2] = list.get(pos).Get_Pos().Y;
            array[pos*7 + 3] = list.get(pos).Get_Next().X;
            array[pos*7 + 4] = list.get(pos).Get_Next().Y;
            array[pos*7 + 5] = list.get(pos).Get_Vel().X;
            array[pos*7 + 6] = list.get(pos).Get_Vel().Y;
        }
        compound.setIntArray("entitylist" + index, array);
        return compound;
    }




    //----------------------------------------HIGHSCORE----------------------------------------//

    public void addScore(String name, int points) {
        int pos = 18;
        for(int i = 17; i >= 0; i--) {
            if(points > scoreHigh[i]) {
                pos = i;
            }
        }
        if(pos == 17) {
            scoreHigh[17] = points;
            scoreName[17] = name;
            scoreLast = 17;
        }
        if(pos < 17) {
            for(int i = 16; i >= pos; i--) {
                scoreHigh[i+1] = scoreHigh[i];
                scoreName[i+1] = scoreName[i];
            }
            scoreHigh[pos] = points;
            scoreName[pos] = name;
        }
        scoreLast = pos;
    }

    private void setupHighscore() {
        scoreLast = 18;
        for(int i = 17; i >= 0; i--) {
            scoreHigh[i] = 0;
            scoreName[i] = "--------";
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected void resetGrid(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j] = 0;
            }
        }
    }

    public void addPlayer(String newPlayer){
        for(int i = 0; i < 6; i++){
            if(currentPlayer[i].matches("void")){
                currentPlayer[i] = newPlayer;
                break;
            }
        }
    }

    public boolean hasFreePlayerSlots(){
        if(isMultiplayer()){
            for(int i = 0; i < (tableID == 1 ? 4 : 6); i++){
                if(currentPlayer[i].matches("void")){
                    return true;
                }
            }
        } else {
            return currentPlayer[0].matches("void");
        }
        return false;
    }

    public int getFirstFreePlayerSlot(){
        if(isMultiplayer()){
            for(int i = 0; i < (tableID == 1 ? 4 : 6); i++){
                if(currentPlayer[i].matches("void")){
                    return i;
                }
            }
        } else {
            return 0;
        }
        return 6;
    }

    public void removePlayer(String oldPlayer){
        for(int i = 0; i < 6; i++){
            if(currentPlayer[i].matches(oldPlayer)){
                currentPlayer[i] = "void";
                break;
            }
        }
    }

    public void resetPlayers(){
        for(int i = 0; i < 6; i++){
            currentPlayer[i] = "void";
        }
    }



    //----------------------------------------OVERRIDE----------------------------------------//

    public abstract void command(int action);
    public abstract void updateMotion();
    public abstract void updateLogic();
    public abstract void start2();
    public abstract void load2(NBTTagCompound compound);
    public abstract NBTTagCompound save2(NBTTagCompound compound);

    public abstract boolean hasHighscore();
    public abstract boolean isMultiplayer();
    public abstract int getID();

}
