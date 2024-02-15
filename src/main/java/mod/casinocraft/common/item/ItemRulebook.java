package mod.casinocraft.common.item;

import mod.lucky77.item.ItemBook;

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
		super(_colorID);
		moduleID = _moduleID;
		ruleSet = _ruleSet;
		hasGambling = _hasGambling;
		hasHighscore = _hasHighscore;
		coloringType = _coloringType;
		isArcadeGame = _isArcadeGame;
		// this.addPage("TITLE", "this is the page body that can be bigger than one line", "", -1, "");
		// this.addPage("LOREM IPSUM", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita", "", -1, "");
		// this.addPage("           ", "kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", "", -1, "");
		// this.addPage("           ", "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.", "", -1, "");
		// this.addPage("", "textures/gui/static.png", 0, MODID);
		// this.addPage("", "textures/gui/static.png", 1, MODID);
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
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		
		if(id == 21){ // Black Jack
			this.addPage("BLACK JACK", "", "textures/gui/icons_casino.png", 4, MODID);
			this.addPage("", "Rule Set: /b /b --- /b --- /b --- /b ---", "", -1, "");
		}
		if(id == 22){ // Poker
			this.addPage("POKER", "", "textures/gui/icons_casino.png", 4, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		
		if(id == 31){ // Solitaire
			this.addPage("SOLITAIRE", "", "textures/gui/icons_casino.png", 5, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		if(id == 32){ // Pyramid
			this.addPage("PYRAMID", "", "textures/gui/icons_casino.png", 5, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		if(id == 33){ // Mau Mau
			this.addPage("MAU MAU", "", "textures/gui/icons_casino.png", 5, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		
		if(id == 41){ // Minesweeper
			this.addPage("MINESWEEPER", "", "textures/gui/icons_casino.png", 1, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		if(id == 42){ // Ishido
			this.addPage("ISHIDO", "", "textures/gui/icons_casino.png", 1, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		
		if(id == 51){ // Tetris
			this.addPage("TETRIS", "", "textures/gui/icons_casino.png", 3, MODID);
			this.addPage("", "Rule Set:/b/b Gameplay/b Field Size/b Piece Size/b HOLD Button", "", -1, "");
		}
		if(id == 52){ // 2048
			this.addPage("2048", "", "textures/gui/icons_casino.png", 3, MODID);
			this.addPage("", "Rule Set:/b/b Gameplay/b Movement", "", -1, "");
		}
		
		if(id == 61){ // Snake
			this.addPage("SNAKE", "", "textures/gui/icons_casino.png", 2, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		if(id == 62){ // Sokoban
			this.addPage("SOKOBAN", "", "textures/gui/icons_casino.png", 2, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		
		if(id == 70){ // Slot Machine
			this.addPage("SLOT MACHINE", "", "", -1, MODID);
			this.addPage("", "Rule Set:/b/b ---/b ---/b ---/b ---", "", -1, "");
		}
		
	}
	
	
	
}
