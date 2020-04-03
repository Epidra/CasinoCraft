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

    public int[][] grid = new int[1][1];

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
    public final static int COMMAND_ROTLEFT = 4;
    public final static int COMMAND_ROTRIGHT = 5;
    public final static int COMMAND_CONTROL = 6;
    public final static int COMMAND_ENTER = 7;
    public final static int COMMAND_SPACE = 8;

    // Highscore
    public int    scoreHigh[] = new int[18];
    public String scoreName[] = new String[18];
    public int    scoreLast = 18; // Position of newest entry in highscore, used for highlighting

    public boolean pause = false;

    public LogicBase(boolean hasHighsore, int table, String name){
        this(hasHighsore, table, name, 0, 0);
    }

    public LogicBase(boolean hasHighsore, int table, String name, int gridX, int gridY){
        this.hasHighscore = hasHighsore;
        this.table = table;
        this.name = name;
        if(hasHighscore) setupHighscore();
        if(gridX > 1 && gridY > 1){
            grid = new int[gridX][gridY];
        }
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
        ResetGrid();
        start2();
    }

    protected void ResetGrid(){
        if(grid.length > 1){
            for(int i = 0; i < grid.length; i++){
                for(int j = 0; j < grid[0].length; j++){
                    grid[i][j] = 0;
                }
            }
        }
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
            int[] baseValues = compound.getIntArray("basevalues");
            turnstate  = baseValues[0];
            scorePoint = baseValues[1];
            scoreLevel = baseValues[2];
            scoreLives = baseValues[3];
            reward     = baseValues[4];
            selector.set(baseValues[5], baseValues[6]);
            hand = compound.getString("hand");
            pause = compound.getBoolean("pause");
        }
        { // Grid
            if(grid.length > 1){
                int[] array = compound.getIntArray("grid");
                for(int y = 0; y < grid[0].length; y++){
                    for(int x = 0; x < grid.length; x++){
                        grid[x][y] = array[y*grid.length + x];
                    }
                }
            }
        }
        { // Highscore
            if(hasHighscore){
                for(int i = 0; i < 18; i++) {
                    scoreHigh[i] = compound.getInt("points" + i);
                    scoreName[i] = compound.getString("name" + i);
                }
            }
        }

        if(turnstate >= 2 && turnstate <= 5){
            load2(compound);
        }

//
        //}

    }

    public CompoundNBT save(CompoundNBT compound){
        { // Basic
            compound.putIntArray("basevalues", new int[]{
                    turnstate,
                    scorePoint,
                    scoreLevel,
                    scoreLives,
                    reward,
                    selector.X,
                    selector.Y
            });
            compound.putString("hand", hand);
            compound.putBoolean("pause", pause);
        }
        { // Grid
            if(grid.length > 1){
                int[] array = new int[grid.length * grid[0].length];
                for(int y = 0; y < grid[0].length; y++){
                    for(int x = 0; x < grid.length; x++){
                        array[y*grid.length + x] = grid[x][y];
                    }
                }
                compound.putIntArray("grid", array);
            }
        }
        { // Highscore
            if(hasHighscore){
                for(int i = 0; i < 18; i++) {
                    compound.putInt("points" + i, scoreHigh[i]);
                    compound.putString("name" + i, scoreName[i]);
                }
            }
        }

        if(turnstate >= 2 && turnstate <= 5){
            save2(compound);
        }



//
        //}

        return compound;
    }





    public Card[] loadCardArray(CompoundNBT compound, int index){
        int[] array = compound.getIntArray("cardstack" + index);
        Card[] cards = new Card[array.length / 3];
        for(int i = 0; i < array.length; i += 3){
            cards[i/3] = new Card(array[i], array[i+1], array[i+2] == 1);
        }
        return cards;
    }

    public List<Card> loadCardList(CompoundNBT compound, int index){
        int[] array = compound.getIntArray("cardstack" + index);
        List<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < array.length; i += 3){
            cards.add(new Card(array[i], array[i+1], array[i+2] == 1));
        }
        return cards;
    }

    public Dice[] loadDice(CompoundNBT compound){
        int[] array = compound.getIntArray("diceset");
        Dice[] dice = new Dice[array.length / 2];
        for(int i = 0; i < array.length; i += 2){
            dice[i/2] = new Dice(array[i], array[i+1]);
        }
        return dice;
    }

    public Entity loadEntity(CompoundNBT compound, int index){
        int[] array = compound.getIntArray("entity" + index);
        return new Entity(array[0], new Vector2(array[1], array[2]), new Vector2(array[3], array[4]));
    }

    public List<Entity> loadEntityList(CompoundNBT compound, int index){
        int[] array = compound.getIntArray("entitylist" + index);
        List<Entity> list = new ArrayList<Entity>();
        for(int i = 0; i < array.length; i += 5){
            list.add(new Entity( array[i], new Vector2(array[i+1], array[i+2]), new Vector2(array[i+3], array[i+4])));
        }
        return list;
    }




    public CompoundNBT saveCardArray(CompoundNBT compound, int index, Card[] cards){
        int[] array = new int[cards.length * 3];
        for(int pos = 0; pos < cards.length; pos++){
            array[pos*3    ] = cards[pos].number;
            array[pos*3 + 1] = cards[pos].suit;
            array[pos*3 + 2] = cards[pos].hidden ? 1 : 0;
        }
        compound.putIntArray("cardstack" + index, array);
        return compound;
    }

    public CompoundNBT saveCardList(CompoundNBT compound, int index, List<Card> cards){
        int[] array = new int[cards.size() * 3];
        for(int pos = 0; pos < cards.size(); pos++){
            array[pos*3    ] = cards.get(pos).number;
            array[pos*3 + 1] = cards.get(pos).suit;
            array[pos*3 + 2] = cards.get(pos).hidden ? 1 : 0;
        }
        compound.putIntArray("cardstack" + index, array);
        return compound;
    }

    public CompoundNBT saveDice(CompoundNBT compound, Dice[] dice){
        int[] array = new int[dice.length * 2];
        for(int pos = 0; pos < dice.length; pos++){
            array[pos*2    ] = dice[pos].number;
            array[pos*2 + 1] = dice[pos].color;
        }
        compound.putIntArray("diceset", array);
        return compound;
    }

    public CompoundNBT saveEntity(CompoundNBT compound, int index, Entity ent){
        compound.putIntArray("entity" + index, new int[]{ent.ai, ent.Get_Pos().X, ent.Get_Pos().Y, ent.Get_Next().X, ent.Get_Next().Y});
        return compound;
    }

    public CompoundNBT saveEntityList(CompoundNBT compound, int index, List<Entity> list){
        int[] array = new int[list.size() * 5];
        for(int pos = 0; pos < list.size(); pos++){
            array[pos*5    ] = list.get(pos).ai;
            array[pos*5 + 1] = list.get(pos).Get_Pos().X;
            array[pos*5 + 2] = list.get(pos).Get_Pos().Y;
            array[pos*5 + 3] = list.get(pos).Get_Next().X;
            array[pos*5 + 4] = list.get(pos).Get_Next().Y;
        }
        compound.putIntArray("entitylist" + index, array);
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
            scoreHigh[i] = 0;
            scoreName[i] = "--------";
            //scoreHigh[i] = (18-i) * 5;
            //scoreName[i] = getScorename(RANDOM.nextInt(24));
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
