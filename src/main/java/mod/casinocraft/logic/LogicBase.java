package mod.casinocraft.logic;

import mod.casinocraft.util.Card;
import mod.casinocraft.util.Dice;
import mod.casinocraft.util.Entity;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class LogicBase {

    public final Random RANDOM = new Random();
    public int scorePoint = -1;
    public int scoreLevel  = -1;
    public int scoreLives  = -1;

    public String hand = "NULL";
    public int reward = -1;

    public Vector2 selector = new Vector2(0,0);

    /** 0 - bet, 1 - unused, 2 - turn player, 3 - turn computer, 4 - game over, 5 - result **/
    public int turnstate;

    public final boolean hasHighscore;

    public final int table;

    private final String name;



    // COMMAND INPUT, replace number with keyboard values
    public final static int COMMAND_UP = 0;
    public final static int COMMAND_DOWN = 1;
    public final static int COMMAND_LEFT = 2;
    public final static int COMMAND_RIGHT = 3;
    public final static int COMMAND_ENTER = 4;
    public final static int COMMAND_SPACE = 5;
    public final static int COMMAND_CONTROL = 6;
    public final static int COMMAND_ROTLEFT = 7;
    public final static int COMMAND_ROTRIGHT = 8;

    // Highscore
    public int    scoreHigh[] = new int[18];
    public String scoreName[] = new String[18];
    public int    scoreLast = 18; // Position of newest entry in highscore, used for highlighting

    public boolean pause = false;

    public LogicBase(boolean hasHighscore, String name){
        this.hasHighscore = hasHighscore;
        this.table = 0;
        this.name = name;
    }

    public LogicBase(boolean hasHighsore, int table, String name){
        this.hasHighscore = hasHighsore;
        this.table = table;
        this.name = name;
    }

    public Object get(int index){
        return null;
    }

    public void set(int index, int value){
        if(index == 0) turnstate = value;
        if(index == 1) start(value);
        if(index == 2) actionTouch(value);
    }

    public int size(){ // ???
        return 1;
    }

    public abstract void actionTouch(int action);




    //--------------------BASIC--------------------

    public void start(int seed){ // Why ???
        RANDOM.setSeed(seed);
        //difficulty = diff;
        if(turnstate == 0) {
            if(table == 0){
                turnstate = 1;
            } else {
                turnstate = 2;
            }
            //turnstate = 2;
            //start();
        } else {
            if(turnstate >= 5){
                turnstate = 1;
                //start();
            } else {
                turnstate = 0;
            }
        }
        //start();
        //turnstate = 2;
        scorePoint = 0;
        scoreLevel  = 0;
        scoreLives  = 0;
        hand   = "empty";
        reward = 0;
        selector.set(0,0);
        start2();
    }

    //public void start(){ // ???
    //    //turnstate = 2;
    //    scorePoint = 0;
    //    scoreLevel  = 0;
    //    scoreLives  = 0;
    //    hand   = "empty";
    //    reward = 0;
    //    selector.set(0,0);
    //    start2();
    //}

    public void update(){
        if(!pause){
            updateMotion();
            updateLogic();
        }
    }

    public void load(CompoundNBT compound){
        { // Basic
            turnstate = compound.getInt("turnstate");
            scorePoint = compound.getInt("scorepoint");
            scoreLevel = compound.getInt("scorelevel");
            scoreLives = compound.getInt("scorelives");
            hand = compound.getString("hand");
            reward = compound.getInt("reward");
            selector.set(compound.getInt("selectorx"), compound.getInt("selectory"));
        }
        { // Highscore
            if(hasHighscore){
                for(int i = 0; i < 18; i++) {
                    scoreHigh[i] = compound.getInt("points" + i);
                    scoreName[i] = compound.getString("name" + i);
                }
            }
        }

        load2(compound);

//
        //}

    }

    public CompoundNBT save(CompoundNBT compound){
        { // Basic
            compound.putInt("turnstate", turnstate);
            compound.putInt("scorepoint", scorePoint);
            compound.putInt("scorelevel", scoreLevel);
            compound.putInt("scorelives", scoreLives);
            compound.putString("hand", hand);
            compound.putInt("reward", reward);
            compound.putInt("selectorx", selector.X);
            compound.putInt("selectory", selector.Y);
            compound.putBoolean("pause", pause);
        }
        { // Highscore
            if(hasHighscore){
                for(int i = 0; i < 18; i++) {
                    compound.putInt("points" + i, scoreHigh[i]);
                    compound.putString("name" + i, scoreName[i]);
                }
            }
        }

        save2(compound);



//
        //}

        return compound;
    }

    public Card[] loadCard(CompoundNBT compound, int index){
        int size = compound.getInt("cardsize" + index + "stack");
        Card[] array = new Card[size];
        for(int pos = 0; pos < size; pos++){
            array[pos] = new Card(compound.getInt("card" + index + "#" + pos + "x"),
                    compound.getInt("card" + index + "#" + pos + "y"),
                    compound.getBoolean("card" + index + "#" + pos + "z"));
        }
        return array;
    }

    public Dice[] loadDice(CompoundNBT compound){
        int size = compound.getInt("dicesize");
        Dice[] array = new Dice[size];
        for(int pos = 0; pos < size; pos++){
            array[pos] = new Dice(
                    compound.getInt("dice" + pos + "x"),
                    compound.getInt("dice" + pos + "y"));
        }
        return array;
    }

    public int[][] loadGrid(CompoundNBT compound, int sizeX, int sizeY){
        int[][] array = new int[sizeX][sizeY];
        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                array[x][y] = compound.getInt("grid" + x + "#" + y + "z");
            }
        }
        return array;
    }

    public boolean[][] loadGridB(CompoundNBT compound, int sizeX, int sizeY){
        boolean[][] array = new boolean[sizeX][sizeY];
        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                array[x][y] = compound.getBoolean("gridb" + x + "#" + y + "z");
            }
        }
        return array;
    }

    public Entity[] loadEntity(CompoundNBT compound, int index){
        int size = compound.getInt("entitysize" + index);
        Entity[] array = new Entity[size];
        for(int pos = 0; pos < size; pos++){
            array[pos] = new Entity(
                    compound.getInt("entity" + index + "#" + pos + "a"),
                    new Vector2(
                            compound.getInt("entity" + index + "#" + pos + "x"),
                            compound.getInt("entity" + index + "#" + pos + "y")),
                    new Vector2(
                            compound.getInt("entity" + index + "#" + pos + "u"),
                            compound.getInt("entity" + index + "#" + pos + "v")));
        }
        return array;
    }

    public CompoundNBT saveCards(CompoundNBT compound, int index, Card[] array){
        compound.putInt("cardsize" + index + "stack", array.length);
        for(int pos = 0; pos < array.length; pos++){
            compound.putInt("card" + index + "#" + pos + "x", array[pos].number);
            compound.putInt("card" + index + "#" + pos + "y", array[pos].suit);
            compound.putBoolean("card" + index + "#" + pos + "z", array[pos].hidden);
        }
        return compound;
    }

    public CompoundNBT saveDice(CompoundNBT compound, Dice[] array){
        compound.putInt("dicesize", array.length);
        for(int pos = 0; pos < array.length; pos++){
            compound.putInt("dice" + pos + "x", array[pos].number);
            compound.putInt("dice" + pos + "y", array[pos].color);
        }
        return compound;
    }

    public CompoundNBT saveGrid(CompoundNBT compound, int sizeX, int sizeY, int[][] array){
        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                compound.putInt("grid" + x + "#" + y + "z", array[x][y]);
            }
        }
        return compound;
    }

    public CompoundNBT saveGridB(CompoundNBT compound, int sizeX, int sizeY, boolean[][] array){
        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                compound.putBoolean("gridb" + x + "#" + y + "z", array[x][y]);
            }
        }
        return compound;
    }

    public CompoundNBT saveEntity(CompoundNBT compound, int index, Entity[] array){
        compound.putInt("entitysize" + index, array.length);
        for(int pos = 0; pos < array.length; pos++){
            compound.putInt("entity" + index + "#" + pos + "a", array[pos].ai);
            compound.putInt("entity" + index + "#" + pos + "x", array[pos].Get_Pos().X);
            compound.putInt("entity" + index + "#" + pos + "y", array[pos].Get_Pos().Y);
            compound.putInt("entity" + index + "#" + pos + "u", array[pos].Get_Vel().X);
            compound.putInt("entity" + index + "#" + pos + "v", array[pos].Get_Vel().Y);
        }
        return compound;
    }

    //--------------------CUSTOM--------------------

    private String getScorename(int r) {
        switch(r) {
            case  0: return "Ruby";
            case  1: return "Weiss";
            case  2: return "Blake";
            case  3: return "Yang";
            case  4: return "Jaune";
            case  5: return "Nora";
            case  6: return "Pyrrha";
            case  7: return "Ren";
            case  8: return "Sun";
            case  9: return "Neptune";
            case 10: return "Flynt";
            case 11: return "Neon";
            case 12: return "Cinder";
            case 13: return "Mercury";
            case 14: return "Emerald";
            case 15: return "Summer";
            case 16: return "Taiyang";
            case 17: return "Qrow";
            case 18: return "Raven";
            case 19: return "Winter";
            case 20: return "Coco";
            case 21: return "Fox";
            case 22: return "Velvet";
            case 23: return "Yatsuhashi";
        }
        return "Zwei";
    }

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

    public void setupHighscore() {
        scoreLast = 18;
        for(int i = 17; i >= 0; i--) {
            scoreHigh[i] = (18-i) * 5;
            scoreName[i] = getScorename(RANDOM.nextInt(24));
        }
        /**CasinoPacketHandler.INSTANCE.sendToServer(new ServerScoreMessage(getScoreToken(), scoreName, scorePoints, getPos()));*/
    }



    public void actionPause(){
        pause = !pause;
    }



    //--------------------OVERRIDES--------------------

    public abstract void updateMotion();
    public abstract void updateLogic();
    public abstract void start2();
    public abstract void load2(CompoundNBT compound);
    public abstract CompoundNBT save2(CompoundNBT compound);

    public boolean hasHighscore(){
        return hasHighscore;
    }

    public String getName(){
        return name;
    }
}
