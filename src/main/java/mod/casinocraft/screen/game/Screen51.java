package mod.casinocraft.screen.game;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.game.Logic51;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class Screen51 extends ScreenCasino {   //  Tetris
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Screen51(MenuCasino container, Inventory player, Component name) {
		super(container, player, name);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public Logic51 logic(){
		return (Logic51) menu.logic();
	}
	
	protected void createGameButtons(){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
	
	protected void interact(double mouseX, double mouseY, int mouseButton){
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
		drawFontInvert(matrix, "" + logic().scorePoint, 216, 16);
		drawFontInvert(matrix, "" + logic().scoreLives, 216, 36);
		drawFontInvert(matrix, "" + logic().scoreLevel, 216, 56);
	}
	
	protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
		if(logic().ruleLargeField()){
			RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_TETRIS);
			blit(matrix, leftPos, topPos, 0, 0, 156, 256);
			blit(matrix, leftPos + 156, topPos, 156, 0, 100, logic().ruleHoldButton() ? 256 : 160);
		}
		if(!logic().ruleLargeField()){
			RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_COLUMNS);
			blit(matrix, leftPos, topPos, 0, 0, 156, 256);
			blit(matrix, leftPos + 156, topPos, 156, 0, 100, logic().ruleHoldButton() ? 256 : 160);
		}
		if(logic().ruleLargeField()){
			for(int y = 0; y < 20; y++){
				for(int x = 0; x < 10; x++){
					if(logic().grid[x][y] != -1) drawDigiSmall(matrix, 32 + 12*x, 8 + 12*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y));
				}
			}
		}
		if(!logic().ruleLargeField()){
			for(int y = 0; y < 14; y++){
				for(int x = 0; x < 6; x++){
					if(logic().grid[x][y+6] != -1 && logic().ruleGameMode() == 0) drawDigi(      matrix, 44 + 16*x, 16 + 16*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y+6), 0);
					if(logic().grid[x][y+6] != -1 && logic().ruleGameMode() != 0) drawDigiSymbol(matrix, 44 + 16*x, 16 + 16*y, logic().turnstate >= 4 ? 8 : tetroColor(x, y+6)   );
				}
			}
		}
		if(logic().ruleLargeField()){
			if(logic().rulePieces() == 4){
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[0].X, 8 + 12*logic().tetromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[1].X, 8 + 12*logic().tetromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[2].X, 8 + 12*logic().tetromino[2].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[3].X, 8 + 12*logic().tetromino[3].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
			}
			if(logic().rulePieces() == 3){
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[0].X, 8 + 12*logic().tetromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[1].X, 8 + 12*logic().tetromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[2].X, 8 + 12*logic().tetromino[2].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
			}
			if(logic().rulePieces() == 2){
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[0].X, 8 + 12*logic().tetromino[0].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
				drawDigiSmall(matrix, 32 + 12*logic().tetromino[1].X, 8 + 12*logic().tetromino[1].Y, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
			}
		}
		if(!logic().ruleLargeField()){
			if(logic().rulePieces() == 4){
				if(logic().tetromino[0].X < 6 && logic().tetromino[0].Y > 5) drawDigi(matrix, 44 + 16*logic().tetromino[0].X, 16 + 16*logic().tetromino[0].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[0], 0);
				if(logic().tetromino[1].X < 6 && logic().tetromino[1].Y > 5) drawDigi(matrix, 44 + 16*logic().tetromino[1].X, 16 + 16*logic().tetromino[1].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[0], 0);
				if(logic().tetromino[2].X < 6 && logic().tetromino[2].Y > 5) drawDigi(matrix, 44 + 16*logic().tetromino[2].X, 16 + 16*logic().tetromino[2].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[0], 0);
				if(logic().tetromino[3].X < 6 && logic().tetromino[3].Y > 5) drawDigi(matrix, 44 + 16*logic().tetromino[3].X, 16 + 16*logic().tetromino[3].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[0], 0);
			}
			if(logic().rulePieces() == 3){
				if(logic().tetromino[0].X < 6 && logic().tetromino[0].Y > 5) drawDigiSymbol(matrix, 44 + 16*logic().tetromino[0].X, 16 + 16*logic().tetromino[0].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
				if(logic().tetromino[1].X < 6 && logic().tetromino[1].Y > 5) drawDigiSymbol(matrix, 44 + 16*logic().tetromino[1].X, 16 + 16*logic().tetromino[1].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[1]);
				if(logic().tetromino[2].X < 6 && logic().tetromino[2].Y > 5) drawDigiSymbol(matrix, 44 + 16*logic().tetromino[2].X, 16 + 16*logic().tetromino[2].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[2]);
			}
			if(logic().rulePieces() == 2){
				if(logic().tetromino[0].X < 6 && logic().tetromino[0].Y > 5) drawDigiSymbol(matrix, 44 + 16*logic().tetromino[0].X, 16 + 16*logic().tetromino[0].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[0]);
				if(logic().tetromino[1].X < 6 && logic().tetromino[1].Y > 5) drawDigiSymbol(matrix, 44 + 16*logic().tetromino[1].X, 16 + 16*logic().tetromino[1].Y - 16*6, logic().turnstate >= 4 ? 8 : logic().container_now[1]);
			}
		}
		
		if(logic().rulePieces() == 4) drawTetromino(matrix, logic().turnstate >= 4 ? 8 : logic().container_next[0], 156, 80+6-2);
		if(logic().rulePieces() == 3) drawTrimino(  matrix, logic().turnstate >= 4 ? 8 : logic().container_next[0], logic().turnstate >= 4 ?  8 : logic().container_next[1], logic().turnstate >= 4 ?  8 : logic().container_next[2], 156+16+8, 80+6-2+16-8);
		if(logic().rulePieces() == 2) drawDomino(   matrix, logic().turnstate >= 4 ? 8 : logic().container_next[0], logic().turnstate >= 4 ?  8 : logic().container_next[1], 156+16+8, 80+6-2+16);
		if(logic().ruleHoldButton() && logic().container_hold[0] > -1){
			if(logic().rulePieces() == 4) drawTetromino(matrix, logic().turnstate >= 4 ? 8 : logic().container_hold[0], 156, 164+6+2);
			if(logic().rulePieces() == 3) drawTrimino(matrix, logic().turnstate >= 4 ? -1 : logic().container_hold[0], logic().turnstate >= 4 ? -1 : logic().container_hold[1], logic().turnstate >= 4 ? -1 : logic().container_hold[2], 156+16+8, 164+6+2+16-8);
			if(logic().rulePieces() == 2) drawDomino(matrix, logic().turnstate >= 4 ? -1 : logic().container_hold[0], logic().turnstate >= 4 ? -1 : logic().container_hold[1], 156+16+8, 164+6+2+16);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private int tetroColor(int x, int y){
		return logic().inLine(x, y) && (logic().alpha/75)%2==0 ? logic().grid[x][y] + 8 : logic().grid[x][y];
	}
	
	private void drawTetromino(PoseStack matrix, int mino, int x, int y) {
		if(mino == 0) { drawTetromino(matrix, mino, x, y, 24,  0, 24, 16, 24, 32, 24, 48); } // I
		if(mino == 1) { drawTetromino(matrix, mino, x, y, 16, 16, 32, 16, 16, 32, 32, 32); } // O
		if(mino == 2) { drawTetromino(matrix, mino, x, y, 24, 16, 40, 16, 24, 32,  8, 32); } // S
		if(mino == 3) { drawTetromino(matrix, mino, x, y, 24, 16,  8, 16, 24, 32, 40, 32); } // Z
		if(mino == 4) { drawTetromino(matrix, mino, x, y, 16,  8, 16, 24, 16, 40, 32, 40); } // L
		if(mino == 5) { drawTetromino(matrix, mino, x, y, 32,  8, 32, 24, 32, 40, 16, 40); } // J
		if(mino == 6) { drawTetromino(matrix, mino, x, y,  8, 16, 24, 16, 40, 16, 24, 32); } // T
	}
	
	private void drawTetromino(PoseStack matrix, int mino, int x, int y, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
		drawDigi(matrix, x + x1, y + y1, mino, 0);
		drawDigi(matrix, x + x2, y + y2, mino, 0);
		drawDigi(matrix, x + x3, y + y3, mino, 0);
		drawDigi(matrix, x + x4, y + y4, mino, 0);
	}
	
	private void drawTrimino(PoseStack matrix, int mino0, int mino1, int mino2, int x, int y) {
		drawDigiSymbol(matrix, x, y     , logic().turnstate >= 4 ? 8 : mino0);
		drawDigiSymbol(matrix, x, y + 16, logic().turnstate >= 4 ? 8 : mino1);
		drawDigiSymbol(matrix, x, y + 32, logic().turnstate >= 4 ? 8 : mino2);
	}
	
	private void drawDomino(PoseStack matrix, int mino0, int mino1, int x, int y) {
		drawDigiSymbol(matrix, x, y     , logic().turnstate >= 4 ? 8 : mino1);
		drawDigiSymbol(matrix, x, y + 16, logic().turnstate >= 4 ? 8 : mino0);
	}
	
	
	
}