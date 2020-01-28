package mod.casinocraft.logic;

import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;

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

    public void start(int diff){ // Why ???
        //difficulty = diff;
        if(turnstate == 0) {
            turnstate = 2;
            start();
        } else {
            if(turnstate >= 5){
                turnstate = 1;
                start();
            } else {
                turnstate = 0;
            }
        }
        start();
    }

    public void start(){
        turnstate = 2;
        scorePoint = 0;
        scoreLevel  = 0;
        scoreLives  = 0;
        hand   = "empty";
        reward = 0;
        selector.set(0,0);
        start2();
    }

    public void update(){
        updateMotion();
        updateLogic();
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
        //{ // Cards
        //    if(nbtHasCards){
        //        for(int pos = 0; pos < cardSize; pos++){
        //            int cardSize = compound.getInt("cardsize" + pos + "stack");
        //            for(int i = 0; i < cardSize; i++){
        //                if(listCARD[pos].size() < i + 1){
        //                    listCARD[pos].add(
        //                            new Card(compound.getInt("card" + pos + "#" + i + "x"),
        //                                    compound.getInt("card" + pos + "#" + i + "y"),
        //                                    compound.getBoolean("card" + pos + "#" + i + "z")));
        //                } else {
        //                    listCARD[pos].set(i,
        //                            new Card(compound.getInt("card" + pos + "#" + i + "x"),
        //                                    compound.getInt("card" + pos + "#" + i + "y"),
        //                                    compound.getBoolean("card" + pos + "#" + i + "z")));
        //                }
        //            }
        //        }
        //    }
        //}
        //{ // Dice
        //    int diceSize = compound.getInt("dicesize");
        //    for(int i = 0; i < diceSize; i++){
        //        if(listDICE.size() < i + 1){
        //            listDICE.add(
        //                    new Dice(
        //                            compound.getInt("dice" + i + "x"),
        //                            compound.getInt("dice" + i + "y")));
        //        } else {
        //            listDICE.set(i,
        //                    new Dice(
        //                            compound.getInt("dice" + i + "x"),
        //                            compound.getInt("dice" + i + "y")));
        //        }
        //    }
        //}
        //{ // Grid
        //    if(nbtHasGrid){
        //        for(int y = 0; y < gridSize.Y; y++){
        //            for(int x = 0; x < gridSize.X; x++){
        //                grid[x][y] = compound.getInt("grid" + x + "#" + y + "z");
        //            }
        //        }
        //    }
//
        //}
        //{ // Entity
        //    int entitySize = compound.getInt("entitysize");
        //    int intcounter = 0;
        //    for(int i = 0; i < entitySize; i++){
        //        if(listENTITY.size() < i + 1){
        //            listENTITY.add(
        //                    new Entity(
        //                            compound.getInt("entity" + i + "a"),
        //                            new Vector2(
        //                                    compound.getInt("entity" + i + "x"),
        //                                    compound.getInt("entity" + i + "y")),
        //                            new Vector2(
        //                                    compound.getInt("entity" + i + "u"),
        //                                    compound.getInt("entity" + i + "v"))));
        //        } else {
        //            listENTITY.add(
        //                    new Entity(
        //                            compound.getInt("entity" + i + "a"),
        //                            new Vector2(
        //                                    compound.getInt("entity" + i + "x"),
        //                                    compound.getInt("entity" + i + "y")),
        //                            new Vector2(
        //                                    compound.getInt("entity" + i + "u"),
        //                                    compound.getInt("entity" + i + "v"))));
        //        }
        //    }
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
        }
        { // Highscore
            if(hasHighscore){
                for(int i = 0; i < 18; i++) {
                    compound.putInt("points" + i, scoreHigh[i]);
                    compound.putString("name" + i, scoreName[i]);
                }
            }
        }
        //{ // Cards
        //    if(nbtHasCards){
        //        for(int pos = 0; pos < cardSize; pos++){
        //            int cardcounter = 0;
        //            compound.setInt("cardsize" + pos + "stack", listCARD[pos].size());
        //            if(listCARD[pos].size() > 0){
        //                for (Card c : listCARD[pos]) {
        //                    compound.setInt("card" + pos + "#" + cardcounter + "x", c.number);
        //                    compound.setInt("card" + pos + "#" + cardcounter + "y", c.suit);
        //                    compound.setBoolean("card" + pos + "#" + cardcounter + "z", c.hidden);
        //                    cardcounter++;
        //                }
        //            }
        //        }
        //    }
        //}
        //{ // Dice
        //    compound.setInt("dicesize",listDICE.size());
        //    int dicecounter = 0;
        //    if(listDICE.size() > 0){
        //        for (Dice d : listDICE) {
        //            compound.setInt("dice" + dicecounter + "x", d.number);
        //            compound.setInt("dice" + dicecounter + "y", d.color);
        //            dicecounter++;
        //        }
        //    }
        //}
        //{ // Grid
        //    if(nbtHasGrid){
        //        for(int y = 0; y < gridSize.Y; y++){
        //            for(int x = 0; x < gridSize.X; x++){
        //                compound.setInt("grid" + x + "#" + y + "z", grid[x][y]);
        //            }
        //        }
        //    }
//
        //}
        //{ // Entity
        //    compound.setInt("entitysize", listENTITY.size());
        //    int entitySize = compound.getInt("entitysize");
        //    int entitycounter = 0;
        //    if(listENTITY.size() > 0){
        //        for(Entity e : listENTITY){
        //            compound.setInt("entity" + entitycounter + "a", e.ai);
        //            compound.setInt("entity" + entitycounter + "x", e.Get_Pos().X);
        //            compound.setInt("entity" + entitycounter + "y", e.Get_Pos().Y);
        //            compound.setInt("entity" + entitycounter + "u", e.Get_Vel().X);
        //            compound.setInt("entity" + entitycounter + "v", e.Get_Vel().Y);
        //            entitycounter++;
        //        }
        //    }
        //}
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



    //--------------------OVERRIDES--------------------

    public abstract void updateMotion();
    public abstract void updateLogic();
    public abstract void start2();

    public boolean hasHighscore(){
        return hasHighscore;
    }

    public String getName(){
        return name;
    }
}
