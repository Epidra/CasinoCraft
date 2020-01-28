package mod.casinocraft.logic;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.Dice;
import mod.casinocraft.util.Entity;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class LogicBase {

    public final Random RANDOM = new Random();

    // Difficulty still needed ???
    public int difficulty = 2; // 1 - easy, 2 - normal, 3 - hard

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

    // NBT Trigger      useful ???
    private boolean nbtHasCards     = false;
    private boolean nbtHasGrid      = false;
    private boolean nbtHasHighscore = false;

    // Highscore
    public int scorePoints[] = new int[18];
    public String scoreName[] = new String[18];
    public int scoreLast = 18; // Position of newest entry in highscore, used for highlighting

    // Grid
    private Vector2 gridSize;
    private int [][] grid = new int[4][4];

    // Boolean
    private List<Boolean> listBOOL = new ArrayList<Boolean>();

    // Integer
    private int cardSize;
    private List<Integer> listINT = new ArrayList<Integer>();

    // Dice
    private List<Vector2> listVECTOR = new ArrayList<Vector2>();

    // Cards
    private List<Card>[] listCARD = new ArrayList[1];

    // Dice
    private List<Dice> listDICE = new ArrayList<Dice>();

    // Entities
    private List<Entity> listENTITY = new ArrayList<Entity>();

    // Basic Game Stuff (Savable)
    /** 0 - bet, 1 - unused, 2 - turn player, 3 - turn computer, 4 - game over, 5 - result **/
    private int turnstate = 0;
    private int scoreCurrent = -1;
    private int scoreLevel  = -1;
    private int scoreLives  = -1;
    private String hand = "NULL";
    private int reward = -1;
    public  Vector2 selector = new Vector2(0,0);
    // Need some encpsulation for network



    //--------------------CONSTRUCTOR--------------------

    public LogicBase(){
        this(false, false, false, 1, 1, 1);
    }

    public LogicBase(boolean hasCards, boolean hasGrid, boolean hasHighscore, int gridsizeX, int gridsizeY, int cardsize){

        nbtHasCards = hasCards;
        nbtHasGrid = hasGrid;
        nbtHasHighscore = hasHighscore;

        if(hasCards){
            for(int i = 0; i < cardsize; i++){
                listCARD[i] = new ArrayList<Card>();
            }
        }

        if(hasGrid){
            gridSize = new Vector2(gridsizeX, gridsizeY);
            grid = new int[gridsizeX][gridsizeY];
            for(int y = 0; y < gridsizeY; y++){
                for(int x = 0; x < gridsizeX; x++){
                    grid[x][y] = 0;
                }
            }
        }

        if(hasHighscore){
            //for(int i = 0; i <18; i++) {
            //    scorePoints[i] = 0;
            //    scoreName[i] = "empty";
            //}
            for(int i = 17; i >= 0; i--) {
                scorePoints[i] = (18-i) * 5;
                scoreName[i] = getScorename(RANDOM.nextInt(24));
            }
        }
    }



    //--------------------BASIC--------------------

    public void start(int diff){ // Why ???
        difficulty = diff;
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
        scoreCurrent = 0;
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

    public void load(NBTTagCompound compound){
        { // Basic
            turnstate = compound.getInt("turnstate");
            scoreCurrent = compound.getInt("scorecurrent");
            scoreLevel = compound.getInt("scorelevel");
            scoreLives = compound.getInt("scorelives");
            hand = compound.getString("hand");
            reward = compound.getInt("reward");
            selector.set(compound.getInt("selectorx"), compound.getInt("selectory"));
        }
        { // Highscore
            if(nbtHasHighscore){
                for(int i = 0; i < 18; i++) {
                    scorePoints[i] = compound.getInt("points" + i);
                    scoreName[i]   = compound.getString("name" + i);
                }
            }
        }
        { // Integer
            int intSize = compound.getInt("integersize");
            for(int i = 0; i < intSize; i++){
                if(listINT.size() < i + 1){
                    listINT.add(compound.getInt("integer" + i + "z"));
                } else {
                    listINT.set(i, compound.getInt("integer" + i + "z"));
                }
            }
        }
        { // Boolean
            int boolSize = compound.getInt("booleansize");
            for(int i = 0; i < boolSize; i++){
                if(listBOOL.size() < i + 1){
                    listBOOL.add(compound.getBoolean("boolean" + i + "b"));
                } else {
                    listBOOL.set(i, compound.getBoolean("boolean" + i + "b"));
                }
            }
        }
        { // Cards
            if(nbtHasCards){
                for(int pos = 0; pos < cardSize; pos++){
                    int cardSize = compound.getInt("cardsize" + pos + "stack");
                    for(int i = 0; i < cardSize; i++){
                        if(listCARD[pos].size() < i + 1){
                            listCARD[pos].add(
                                    new Card(compound.getInt("card" + pos + "#" + i + "x"),
                                            compound.getInt("card" + pos + "#" + i + "y"),
                                            compound.getBoolean("card" + pos + "#" + i + "z")));
                        } else {
                            listCARD[pos].set(i,
                                    new Card(compound.getInt("card" + pos + "#" + i + "x"),
                                            compound.getInt("card" + pos + "#" + i + "y"),
                                            compound.getBoolean("card" + pos + "#" + i + "z")));
                        }
                    }
                }
            }
        }
        { // Dice
            int diceSize = compound.getInt("dicesize");
            for(int i = 0; i < diceSize; i++){
                if(listDICE.size() < i + 1){
                    listDICE.add(
                            new Dice(
                                compound.getInt("dice" + i + "x"),
                                compound.getInt("dice" + i + "y")));
                } else {
                    listDICE.set(i,
                            new Dice(
                                    compound.getInt("dice" + i + "x"),
                                    compound.getInt("dice" + i + "y")));
                }
            }
        }
        { // Grid
            if(nbtHasGrid){
                for(int y = 0; y < gridSize.Y; y++){
                    for(int x = 0; x < gridSize.X; x++){
                        grid[x][y] = compound.getInt("grid" + x + "#" + y + "z");
                    }
                }
            }

        }
        { // Entity
            int entitySize = compound.getInt("entitysize");
            int intcounter = 0;
            for(int i = 0; i < entitySize; i++){
                if(listENTITY.size() < i + 1){
                    listENTITY.add(
                            new Entity(
                                    compound.getInt("entity" + i + "a"),
                                    new Vector2(
                                            compound.getInt("entity" + i + "x"),
                                            compound.getInt("entity" + i + "y")),
                                    new Vector2(
                                            compound.getInt("entity" + i + "u"),
                                            compound.getInt("entity" + i + "v"))));
                } else {
                    listENTITY.add(
                            new Entity(
                                    compound.getInt("entity" + i + "a"),
                                    new Vector2(
                                            compound.getInt("entity" + i + "x"),
                                            compound.getInt("entity" + i + "y")),
                                    new Vector2(
                                            compound.getInt("entity" + i + "u"),
                                            compound.getInt("entity" + i + "v"))));
                }
            }
        }
    }

    public NBTTagCompound save(NBTTagCompound compound){
        { // Basic
            compound.setInt("turnstate", turnstate);
            compound.setInt("scorecurrent", scoreCurrent);
            compound.setInt("scorelevel", scoreLevel);
            compound.setInt("scorelives", scoreLives);
            compound.setString("hand", hand);
            compound.setInt("reward", reward);
            compound.setInt("selectorx", selector.X);
            compound.setInt("selectory", selector.Y);
        }
        { // Highscore
            if(nbtHasHighscore){
                for(int i = 0; i < 18; i++) {
                    compound.setInt("points" + i, scorePoints[i]);
                    compound.setString("name" + i, scoreName[i]);
                }
            }
        }
        { // Integer
            int intcounter = 0;
            compound.setInt("integersize", listINT.size());
            if(listINT.size() > 0){
                for (int i : listINT) {
                    compound.setInt("integer" + intcounter++ + "z", i);
                }
            }
        }
        { // Boolean
            int boolcounter = 0;
            compound.setInt("booleansize", listBOOL.size());
            if(listINT.size() > 0){
                for (boolean b : listBOOL) {
                    compound.setBoolean("boolean" + boolcounter++ + "b", b);
                }
            }
        }
        { // Cards
            if(nbtHasCards){
                for(int pos = 0; pos < cardSize; pos++){
                    int cardcounter = 0;
                    compound.setInt("cardsize" + pos + "stack", listCARD[pos].size());
                    if(listCARD[pos].size() > 0){
                        for (Card c : listCARD[pos]) {
                            compound.setInt("card" + pos + "#" + cardcounter + "x", c.number);
                            compound.setInt("card" + pos + "#" + cardcounter + "y", c.suit);
                            compound.setBoolean("card" + pos + "#" + cardcounter + "z", c.hidden);
                            cardcounter++;
                        }
                    }
                }
            }
        }
        { // Dice
            compound.setInt("dicesize",listDICE.size());
            int dicecounter = 0;
            if(listDICE.size() > 0){
                for (Dice d : listDICE) {
                    compound.setInt("dice" + dicecounter + "x", d.number);
                    compound.setInt("dice" + dicecounter + "y", d.color);
                    dicecounter++;
                }
            }
        }
        { // Grid
            if(nbtHasGrid){
                for(int y = 0; y < gridSize.Y; y++){
                    for(int x = 0; x < gridSize.X; x++){
                        compound.setInt("grid" + x + "#" + y + "z", grid[x][y]);
                    }
                }
            }

        }
        { // Entity
            compound.setInt("entitysize", listENTITY.size());
            int entitySize = compound.getInt("entitysize");
            int entitycounter = 0;
            if(listENTITY.size() > 0){
                for(Entity e : listENTITY){
                    compound.setInt("entity" + entitycounter + "a", e.ai);
                    compound.setInt("entity" + entitycounter + "x", e.Get_Pos().X);
                    compound.setInt("entity" + entitycounter + "y", e.Get_Pos().Y);
                    compound.setInt("entity" + entitycounter + "u", e.Get_Vel().X);
                    compound.setInt("entity" + entitycounter + "v", e.Get_Vel().Y);
                    entitycounter++;
                }
            }
        }
        return compound;
    }



    //--------------------OVERRIDES--------------------

    public abstract void updateMotion();
    public abstract void updateLogic();
    public abstract void start2();



    //--------------------GETTER--------------------


    public int  getINT(int index){
        if(index >= listINT.size()){
            CasinoCraft.LOGGER.debug("GET Integer List: index greater than size");
            return 0;
        }
        return listINT.get(index);
    }
    public void setINT(int index, int value){
        while(index >= listINT.size()){
            CasinoCraft.LOGGER.debug("SET Integer List: Missing value, adding 0 at postion" + listINT.size());
            listINT.add(0);
        }
        listINT.set(index, value);
    }
    public void updINT(int index, int value){ // update INT
        while(index >= listINT.size()){
            CasinoCraft.LOGGER.debug("SET Integer List: Missing value, adding 0 at postion" + listINT.size());
            listINT.add(0);
        }
        listINT.set(index, listINT.get(index) + value);
    }
    public void rplINT(int indexTo, int indexFrom){ // replace INT
        while(indexTo >= listINT.size()){
            CasinoCraft.LOGGER.debug("SET Integer List: Missing value, adding 0 at postion" + listINT.size());
            listINT.add(0);
        }
        while(indexFrom >= listINT.size()){
            CasinoCraft.LOGGER.debug("SET Integer List: Missing value, adding 0 at postion" + listINT.size());
            listINT.add(0);
        }
        listINT.set(indexTo, listINT.get(indexFrom));
    }

    public boolean getBOL(int index){
        if(index >= listBOOL.size()){
            CasinoCraft.LOGGER.debug("GET Boolean List: index greater than size");
            return false;
        }
        return listBOOL.get(index);
    }
    public void    setBOL(int index, boolean value){
        while(index >= listBOOL.size()){
            CasinoCraft.LOGGER.debug("SET Boolean List: Missing value, adding false at postion" + listBOOL.size());
            listBOOL.add(false);
        }
        listBOOL.set(index, value);
    }
    public void tglBOL(int index){
        listBOOL.set(index, !listBOOL.get(index));
    }

    public Vector2 getVEC(int index){
        if(index >= listVECTOR.size()){
            CasinoCraft.LOGGER.debug("GET Vector List: index greater than size");
            return new Vector2(0, 0);
        }
        return listVECTOR.get(index);
    }
    public void    setVEC(int index, Vector2 value){
        while(index >= listVECTOR.size()){
            CasinoCraft.LOGGER.debug("SET Vector List: Missing value, adding (0,0) at postion" + listVECTOR.size());
            listVECTOR.add(new Vector2(0,0));
        }
        listVECTOR.set(index, value);
    }
    public void    setVEC(int index, int x, int y){
        while(index >= listVECTOR.size()){
            CasinoCraft.LOGGER.debug("SET Vector List: Missing value, adding (0,0) at postion" + listVECTOR.size());
            listVECTOR.add(new Vector2(0,0));
        }
        listVECTOR.get(index).set(x, y);
    }
    public void    updVEC(int index, Vector2 value){
        while(index >= listVECTOR.size()){
            CasinoCraft.LOGGER.debug("SET Vector List: Missing value, adding (0,0) at postion" + listVECTOR.size());
            listVECTOR.add(new Vector2(0,0));
        }
        listVECTOR.get(index).add(value);
    }
    public void    updVEC(int index, int x, int y){
        while(index >= listVECTOR.size()){
            CasinoCraft.LOGGER.debug("SET Vector List: Missing value, adding (0,0) at postion" + listVECTOR.size());
            listVECTOR.add(new Vector2(0,0));
        }
        listVECTOR.get(index).add(x, y);
    }

    public Dice getDIE(int index){
        if(index >= listDICE.size()){
            CasinoCraft.LOGGER.debug("GET Dice List: index greater than size");
            return new Dice(0,0);
        }
        return listDICE.get(index);
    }
    public void setDIE(int index, Dice value){
        while(index >= listDICE.size()){
            CasinoCraft.LOGGER.debug("SET Integer List: Missing value, adding 0 at postion" + listDICE.size());
            listDICE.add(new Dice(0,0));
        }
        listDICE.set(index, value);
    }

    public Card getCRD(int stack, int index){
        if(index >= listCARD[stack].size()){
            CasinoCraft.LOGGER.debug("GET Card List: index greater than size");
            return new Card(0,0);
        }
        return listCARD[stack].get(index);
    }
    public void setCRD(int stack, int index, Card value){
        while(index >= listCARD[stack].size()){
            CasinoCraft.LOGGER.debug("SET Card List: Missing value, adding 0 at postion" + listCARD[stack].size());
            listCARD[stack].add(new Card(0,0));
        }
        listCARD[stack].set(index, value);
    }
    public void addCRD(int stack, Card value){
        listCARD[stack].add(value);
    }
    public void hdnCRD(int stack, int index){ // toggles hidden card
        listCARD[stack].get(index).hidden = !listCARD[stack].get(index).hidden;
    }
    public void rmvCRD(int stack, int index){ // remove card
        listCARD[stack].remove(index);
    }

    public List<Card> getSTK(int index){
        return listCARD[index];
    }
    public void       setSTK(int index, List<Card> value){
        listCARD[index] = value;
    }
    public void       clrSTK(int index){ // clears an enire Card Stack
        listCARD[index].clear();
    }

    public Entity getENT(int index){
        if(index >= listENTITY.size()){
            CasinoCraft.LOGGER.debug("GET Entity List: index greater than size");
            return new Entity(0,new Vector2(0,0), new Vector2(0,0));
        }
        return listENTITY.get(index);
    }
    public void   setENT(int index, Entity value){
        while(index >= listENTITY.size()){
            CasinoCraft.LOGGER.debug("SET Entity List: Missing value, adding 0 at postion" + listENTITY.size());
            listENTITY.add(new Entity(0,new Vector2(0,0), new Vector2(0,0)));
        }
        listENTITY.set(index, value);
    }

    public boolean getFLG(int x, int y){ // is a Flag set on the grid
        return grid[x][y] >= 1000;
    }
    public boolean getFLG(Vector2 vec){ // is a Flag set on the grid
        return grid[vec.X][vec.Y] >= 1000;
    }
    public void    setFLG(int x, int y, boolean flag){
        int temp = flag ? 1000 : 0;
        grid[x][y] = (grid[x][y] % 1000) + temp;
    }
    public void    setFLG(Vector2 vec, boolean flag){
        int temp = flag ? 1000 : 0;
        grid[vec.X][vec.Y] = (grid[vec.X][vec.Y] % 1000) + temp;
    }
    public int     getGRD(int x, int y){
        return grid[x][y] % 1000;
    }
    public int     getGRD(Vector2 vec){
        return grid[vec.X][vec.Y] % 1000;
    }
    public void    setGRD(int x, int y, int value){
        int flag = grid[x][y] >= 1000 ? 1000 : 0;
        grid[x][y] = value + flag;
    }
    public void    setGRD(Vector2 vec, int value){
        int flag = grid[vec.X][vec.Y] >= 1000 ? 1000 : 0;
        grid[vec.X][vec.Y] = value + flag;
    }
    public void    updGRD(int x, int y, int value){
        int flag = grid[x][y] >= 1000 ? 1000 : 0;
        int temp = grid[x][y] % 1000;
        grid[x][y] = temp + value + flag;
    }

    public void turnstate(int value){
        turnstate = (turnstate + value) % 6;
    }
    public int  turnstate(){
        return turnstate;
    }

    public void scoreScore(int value){
        scoreCurrent += value;
    }
    public int  scoreScore(){
        return scoreCurrent;
    }

    public void scoreLevel(int value){
        scoreLevel += value;
    }
    public int  scoreLevel(){
        return scoreLevel;
    }

    public void scoreLives(int value){
        scoreLives += value;
    }
    public int  scoreLives(){
        return scoreLives;
    }

    public String hand(){
        return hand;
    }
    public void   hand(String value){
        hand = value;
    }

    public int  reward(){
        return reward;
    }
    public void reward(int value){
        reward = value;
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
            if(points > scorePoints[i]) {
                pos = i;
            }
        }
        if(pos == 17) {
            scorePoints[17] = points;
            scoreName[17] = name;
            scoreLast = 17;
        }
        if(pos < 17) {
            for(int i = 16; i >= pos; i--) {
                scorePoints[i+1] = scorePoints[i];
                scoreName[i+1] = scoreName[i];
            }
            scorePoints[pos] = points;
            scoreName[pos] = name;
        }
        scoreLast = pos;
    }


}
