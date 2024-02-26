package mod.casinocraft.item;

import mod.lucky77.item.ItemBook;
import net.minecraft.world.item.CreativeModeTab;

import static mod.casinocraft.CasinoCraft.MODID;

public class ItemRulebook extends ItemBook {
    
    private final int     moduleID;
    private final int[]   ruleSet;
    private final boolean hasGambling;
    private final boolean hasHighscore;
    private final boolean isArcadeGame;
    int coloringType;
    
    
    
    
    
    // ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
    
    /** Default Constructor */
    public ItemRulebook(int _colorID, int _moduleID, boolean _isArcadeGame, int[] _ruleSet, boolean _hasGambling, boolean _hasHighscore, int _coloringType){
        super(CreativeModeTab.TAB_MISC, _colorID);
        moduleID = _moduleID;
        ruleSet = _ruleSet;
        hasGambling = _hasGambling;
        hasHighscore = _hasHighscore;
        coloringType = _coloringType;
        isArcadeGame = _isArcadeGame;
        createPages(_moduleID);
    }
    
    
    
    
    
    // ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
    
    public int getModuleID(){
        return moduleID;
    }
    
    public boolean hasHighscore(){
        return hasHighscore;
    }
    
    public boolean hasGambling(){
        return hasGambling;
    }
    
    public int getColoringType(){
        return coloringType;
    }
    
    public int[] getRuleSet(){
        return ruleSet;
    }
    
    public boolean isArcadeGame(){
        return isArcadeGame;
    }
    
    private void createPages(int id){
        if(id == 11){ // Roulette
            this.addPage("ROULETTE", "", "textures/gui/icons_casino.png", 0, MODID);
            this.addPage("book.casinocraft.head_11_0", "book.casinocraft.page_11_0"); // Content
            this.addPage("book.casinocraft.head_11_1", "book.casinocraft.page_11_1"); // Goal
        }
        
        if(id == 21){ // Black Jack
            this.addPage("BLACK JACK", "", "textures/gui/icons_casino.png", 4, MODID);
            this.addPage("book.casinocraft.head_21_0", "book.casinocraft.page_21_0"); // Content
            this.addPage("book.casinocraft.head_21_1", "book.casinocraft.page_21_1"); // Goal
        }
        if(id == 22){ // Poker
            this.addPage("POKER", "", "textures/gui/icons_casino.png", 4, MODID);
            this.addPage("book.casinocraft.head_22_0", "book.casinocraft.page_22_0"); // Content
            this.addPage("book.casinocraft.head_22_1", "book.casinocraft.page_22_1"); // Goal
        }
        
        if(id == 31){ // Solitaire
            this.addPage("SOLITAIRE", "", "textures/gui/icons_casino.png", 5, MODID);
            this.addPage("book.casinocraft.head_31_0", "book.casinocraft.page_31_0"); // Content
            this.addPage("book.casinocraft.head_31_1", "book.casinocraft.page_31_1"); // Goal
            this.addPage("book.casinocraft.head_31_2", "book.casinocraft.page_31_2"); // Rule: Suits
            this.addPage("book.casinocraft.head_31_3", "book.casinocraft.page_31_3"); // Rule: Sorting
            this.addPage("book.casinocraft.head_31_4", "book.casinocraft.page_31_4"); // Rule: Reserve Cards
            this.addPage("book.casinocraft.head_31_5", "book.casinocraft.page_31_5"); // Rule: Placement
        }
        if(id == 32){ // Pyramid
            this.addPage("PYRAMID", "", "textures/gui/icons_casino.png", 5, MODID);
            this.addPage("book.casinocraft.head_32_0", "book.casinocraft.page_32_0"); // Content
            this.addPage("book.casinocraft.head_32_1", "book.casinocraft.page_32_1"); // Goal
            this.addPage("book.casinocraft.head_32_2", "book.casinocraft.page_32_2"); // Game Mode A
            this.addPage("book.casinocraft.head_32_3", "book.casinocraft.page_32_3"); // Game Mode B
            this.addPage("book.casinocraft.head_32_4", "book.casinocraft.page_32_4"); // Rule: Pyramids
            this.addPage("book.casinocraft.head_32_5", "book.casinocraft.page_32_5"); // Rule: Lives
        }
        if(id == 33){ // Mau Mau
            this.addPage("MAU MAU", "", "textures/gui/icons_casino.png", 5, MODID);
            this.addPage("book.casinocraft.head_33_0", "book.casinocraft.page_33_0"); // Content
            this.addPage("book.casinocraft.head_33_1", "book.casinocraft.page_33_1"); // Goal
        }
        
        if(id == 41){ // Minesweeper
            this.addPage("MINESWEEPER", "", "textures/gui/icons_casino.png", 1, MODID);
            this.addPage("book.casinocraft.head_41_0", "book.casinocraft.page_41_0"); // Content
            this.addPage("book.casinocraft.head_41_1", "book.casinocraft.page_41_1"); // Goal
        }
        if(id == 42){ // Ishido
            this.addPage("ISHIDO", "", "textures/gui/icons_casino.png", 1, MODID);
            this.addPage("book.casinocraft.head_42_0", "book.casinocraft.page_42_0"); // Content
            this.addPage("book.casinocraft.head_42_1", "book.casinocraft.page_42_1"); // Goal
        }
        
        if(id == 51){ // Tetris
            this.addPage("TETRIS", "", "textures/gui/icons_casino.png", 3, MODID);
            this.addPage("book.casinocraft.head_51_0", "book.casinocraft.page_51_0"); // Content
            this.addPage("book.casinocraft.head_51_1", "book.casinocraft.page_51_1"); // Goal
            this.addPage("book.casinocraft.head_51_2", "book.casinocraft.page_51_2"); // Game Mode A
            this.addPage("book.casinocraft.head_51_3", "book.casinocraft.page_51_3"); // Game Mode B
            this.addPage("book.casinocraft.head_51_4", "book.casinocraft.page_51_4"); // Game Mode C
            this.addPage("book.casinocraft.head_51_5", "book.casinocraft.page_51_5"); // Rule: Grid Size
            this.addPage("book.casinocraft.head_51_6", "book.casinocraft.page_51_6"); // Rule: Pieces
            this.addPage("book.casinocraft.head_51_7", "book.casinocraft.page_51_7"); // Rule: HOLD Button
        }
        if(id == 52){ // 2048
            this.addPage("2048", "", "textures/gui/icons_casino.png", 3, MODID);
            this.addPage("book.casinocraft.head_52_0", "book.casinocraft.page_52_0"); // Content
            this.addPage("book.casinocraft.head_52_1", "book.casinocraft.page_52_1"); // Goal
            this.addPage("book.casinocraft.head_52_2", "book.casinocraft.page_52_2"); // Rule: UNDO Button
        }
        
        if(id == 61){ // Snake
            this.addPage("SNAKE", "", "textures/gui/icons_casino.png", 2, MODID);
            this.addPage("book.casinocraft.head_61_0", "book.casinocraft.page_61_0"); // Content
            this.addPage("book.casinocraft.head_61_1", "book.casinocraft.page_61_1"); // Goal
        }
        if(id == 62){ // Sokoban
            this.addPage("SOKOBAN", "", "textures/gui/icons_casino.png", 2, MODID);
            this.addPage("book.casinocraft.head_62_0", "book.casinocraft.page_62_0"); // Content
            this.addPage("book.casinocraft.head_62_1", "book.casinocraft.page_62_1"); // Goal
        }
        
        if(id == 70){ // Slot Machine
            this.addPage("SLOT MACHINE", "", "", -1, MODID);
            this.addPage("book.casinocraft.head_70_0", "book.casinocraft.page_70_0"); // Content
            this.addPage("book.casinocraft.head_70_1", "book.casinocraft.page_70_1"); // Goal
        }
        
    }
    
    
    
}
