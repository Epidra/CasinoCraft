package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.NBTTagCompound;

public class LogicMinoBrown extends LogicBase {   // -----

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoBrown(int tableID) {
        super(tableID);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2() {

    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action) {

    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic() {

    }

    public void updateMotion() {

    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(NBTTagCompound compound){

    }

    public NBTTagCompound save2(NBTTagCompound compound){
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 34;
    }

}

//class TicTacToe : Ghost {
//
//        Vector2 selector;
//
//        int winline;
//
//        int result;
//
//        Mark[,] grid = new Mark[3, 3];
//
//        int marks_placed;
//
//        float alpha;
//
//public TicTacToe(string _id, ShopKeeper _Shopkeeper, FileManager _Filemanager, JukeBox _Jukebox, Vector2 _screensize) : base(_id, _Shopkeeper, _Filemanager, _Jukebox, _screensize) {
//        id = _id;
//        SK = _Shopkeeper;
//        FM = _Filemanager;
//        JK = _Jukebox;
//        screensize = _screensize;
//        random = new Random();
//        }
//
//protected override void Start2() {
//        grid[0, 0] = Mark.EMPTY; grid[1, 0] = Mark.EMPTY; grid[2, 0] = Mark.EMPTY;
//        grid[0, 1] = Mark.EMPTY; grid[1, 1] = Mark.EMPTY; grid[2, 1] = Mark.EMPTY;
//        grid[0, 2] = Mark.EMPTY; grid[1, 2] = Mark.EMPTY; grid[2, 2] = Mark.EMPTY;
//        marks_placed = 0;
//        winline = 0;
//        selector = new Vector2(1, 1);
//        result = 0;
//        alpha = 0.01f;
//        }
//
//public void Restart() {
//        turnstate = Turnstate.TURN_PLAYER;
//        grid[0, 0] = Mark.EMPTY; grid[1, 0] = Mark.EMPTY; grid[2, 0] = Mark.EMPTY;
//        grid[0, 1] = Mark.EMPTY; grid[1, 1] = Mark.EMPTY; grid[2, 1] = Mark.EMPTY;
//        grid[0, 2] = Mark.EMPTY; grid[1, 2] = Mark.EMPTY; grid[2, 2] = Mark.EMPTY;
//        marks_placed = 0;
//        winline = 0;
//        selector = new Vector2(1, 1);
//        result = 0;
//        }
//
//protected override ENUM.Command Update_Controls() {
//        if(alpha == 1) {
//        if(ButtonPressed(KeyMap.KEY.arrow_up)) { if(selector.Y > 0) selector.Y--; }
//        if(ButtonPressed(KeyMap.KEY.arrow_down)) { if(selector.Y < 2) selector.Y++; }
//        if(ButtonPressed(KeyMap.KEY.arrow_left)) { if(selector.X > 0) selector.X--; }
//        if(ButtonPressed(KeyMap.KEY.arrow_right)) { if(selector.X < 2) selector.X++; }
//        if(ButtonPressed(KeyMap.KEY.button_ok)) {
//        if(pressed_event_touch) {
//        for(int x = 0; x < 3; x++) {
//        for(int y = 0; y < 3; y++) {
//        if(Collision_Button(false, Get_Box(SK.Position_TicTacToe() + new Vector2(SK.texture_static_tictactoe_cross.Width * x, SK.texture_static_tictactoe_cross.Height * y), SK.texture_static_tictactoe_cross))) {
//        selector = new Vector2(x, y);
//        }
//        }
//        }
//        }
//        if(grid[(int)selector.X, (int)selector.Y] == Mark.EMPTY) {
//        grid[(int)selector.X, (int)selector.Y] = Mark.CROSS;
//        turnstate = Turnstate.TURN_COMPUTER;
//        marks_placed++;
//        Check();
//        }
//        }
//        } else {
//        alpha -= 0.02f;
//        if(alpha <= 0) {
//        alpha = 1.00f;
//        Restart();
//        }
//        }
//        return ENUM.Command.VOID;
//        }
//
//protected override ENUM.Command Update_Logic(GameTime gameTime) {
//        if(marks_placed == 9 && turnstate < Turnstate.GAMEOVER) {
//        winline = 0;
//        result = 2;
//        turnstate = Turnstate.GAMEOVER;
//        }
//
//        if(result == 1 && alpha == 1) {
//        score_points += 100;
//        JK.Play_Sound(ENUM.Sound.CLEARD);
//        alpha -= 0.02f;
//        }
//
//        if(result == 2 && turnstate < Turnstate.GAMEOVER) {
//        turnstate = Turnstate.GAMEOVER;
//        }
//
//        if(turnstate == Turnstate.TURN_COMPUTER && !active_pause && result == 0) {
//        Command_Turn_Computer();
//        }
//        return ENUM.Command.VOID;
//        }
//
//protected override void Draw_Animation() {
//
//        spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(10, 230), null, Color.White, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
//        spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(10, 230 * 2), null, Color.White, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
//
//        spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(230, 10), null, Color.White, -1.5708f, new Vector2(700, 0), 1, SpriteEffects.None, 0);
//        spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(230 * 2, 10), null, Color.White, -1.5708f, new Vector2(700, 0), 1, SpriteEffects.None, 0);
//
//        for(int x = 0; x < 3; x++) {
//        for(int y = 0; y < 3; y++) {
//        if(grid[x, y] == Mark.CROSS) spriteBatch.Draw(SK.texture_static_tictactoe_cross, SK.Position_TicTacToe() + new Vector2(SK.texture_static_tictactoe_cross.Width * x, SK.texture_static_tictactoe_cross.Height * y), Color.White * alpha);
//        if(grid[x, y] == Mark.CIRCLE) spriteBatch.Draw(SK.texture_static_tictactoe_circle, SK.Position_TicTacToe() + new Vector2(SK.texture_static_tictactoe_circle.Width * x, SK.texture_static_tictactoe_circle.Height * y), Color.White * alpha);
//        }
//        }
//
//        if(turnstate < Turnstate.GAMEOVER) {
//        spriteBatch.Draw(SK.texture_static_tictactoe_cross, SK.Position_TicTacToe() + new Vector2(SK.texture_static_tictactoe_cross.Width * selector.X, SK.texture_static_tictactoe_cross.Height * selector.Y), Color.Yellow * 0.50f);
//        }
//
//        if(winline == 1) spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(100, 100), null, Color.Gray * alpha, 1.5708f / 2, new Vector2(0, 0), 1, SpriteEffects.None, 0);
//        if(winline == 2) spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(100, 240 * 3 - 100), null, Color.Gray * alpha, -1.5708f / 2, new Vector2(0, 0), 1, SpriteEffects.None, 0);
//        if(winline == 3) spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(10, 110), null, Color.Gray * alpha, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
//        if(winline == 4) spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(10, 110 + 240), null, Color.Gray * alpha, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
//        if(winline == 5) spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(10, 110 + 240 * 2), null, Color.Gray * alpha, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
//        if(winline == 6) spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(110, 10), null, Color.Gray * alpha, -1.5708f, new Vector2(700, 0), 1, SpriteEffects.None, 0);
//        if(winline == 7) spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(110 + 240, 10), null, Color.Gray * alpha, -1.5708f, new Vector2(700, 0), 1, SpriteEffects.None, 0);
//        if(winline == 8) spriteBatch.Draw(SK.texture_static_tictactoe_line, SK.Position_TicTacToe() + new Vector2(110 + 240 * 2, 10), null, Color.Gray * alpha, -1.5708f, new Vector2(700, 0), 1, SpriteEffects.None, 0);
//        }
//
//private void Check() {
//        if(grid[0, 0] == Mark.CROSS && grid[0, 1] == Mark.CROSS && grid[0, 2] == Mark.CROSS) { winline = 6; result = 1; }
//        if(grid[1, 0] == Mark.CROSS && grid[1, 1] == Mark.CROSS && grid[1, 2] == Mark.CROSS) { winline = 7; result = 1; }
//        if(grid[2, 0] == Mark.CROSS && grid[2, 1] == Mark.CROSS && grid[2, 2] == Mark.CROSS) { winline = 8; result = 1; }
//        if(grid[0, 0] == Mark.CROSS && grid[1, 1] == Mark.CROSS && grid[2, 2] == Mark.CROSS) { winline = 1; result = 1; }
//        if(grid[2, 0] == Mark.CROSS && grid[1, 1] == Mark.CROSS && grid[0, 2] == Mark.CROSS) { winline = 2; result = 1; }
//        if(grid[0, 0] == Mark.CROSS && grid[1, 0] == Mark.CROSS && grid[2, 0] == Mark.CROSS) { winline = 3; result = 1; }
//        if(grid[0, 1] == Mark.CROSS && grid[1, 1] == Mark.CROSS && grid[2, 1] == Mark.CROSS) { winline = 4; result = 1; }
//        if(grid[0, 2] == Mark.CROSS && grid[1, 2] == Mark.CROSS && grid[2, 2] == Mark.CROSS) { winline = 5; result = 1; }
//
//        if(grid[0, 0] == Mark.CIRCLE && grid[0, 1] == Mark.CIRCLE && grid[0, 2] == Mark.CIRCLE) { winline = 6; if(turnstate < Turnstate.GAMEOVER) result = 2; }
//        if(grid[1, 0] == Mark.CIRCLE && grid[1, 1] == Mark.CIRCLE && grid[1, 2] == Mark.CIRCLE) { winline = 7; if(turnstate < Turnstate.GAMEOVER) result = 2; }
//        if(grid[2, 0] == Mark.CIRCLE && grid[2, 1] == Mark.CIRCLE && grid[2, 2] == Mark.CIRCLE) { winline = 8; if(turnstate < Turnstate.GAMEOVER) result = 2; }
//        if(grid[0, 0] == Mark.CIRCLE && grid[1, 1] == Mark.CIRCLE && grid[2, 2] == Mark.CIRCLE) { winline = 1; if(turnstate < Turnstate.GAMEOVER) result = 2; }
//        if(grid[2, 0] == Mark.CIRCLE && grid[1, 1] == Mark.CIRCLE && grid[0, 2] == Mark.CIRCLE) { winline = 2; if(turnstate < Turnstate.GAMEOVER) result = 2; }
//        if(grid[0, 0] == Mark.CIRCLE && grid[1, 0] == Mark.CIRCLE && grid[2, 0] == Mark.CIRCLE) { winline = 3; if(turnstate < Turnstate.GAMEOVER) result = 2; }
//        if(grid[0, 1] == Mark.CIRCLE && grid[1, 1] == Mark.CIRCLE && grid[2, 1] == Mark.CIRCLE) { winline = 4; if(turnstate < Turnstate.GAMEOVER) result = 2; }
//        if(grid[0, 2] == Mark.CIRCLE && grid[1, 2] == Mark.CIRCLE && grid[2, 2] == Mark.CIRCLE) { winline = 5; if(turnstate < Turnstate.GAMEOVER) result = 2; }
//        }
//
//private void Command_Turn_Computer() {
//        if(turnstate == Turnstate.TURN_COMPUTER) {
//        marks_placed++;
//        if(grid[1, 1] == Mark.EMPTY) {
//        grid[1, 1] = Mark.CIRCLE;
//        turnstate = Turnstate.TURN_PLAYER;
//        } else
//
//        if(grid[0, 0] == Mark.CIRCLE && grid[0, 1] == Mark.CIRCLE && grid[0, 2] == Mark.EMPTY) {
//        /*[X][X][ ]*/
//        grid[0, 2] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.CIRCLE && grid[0, 1] == Mark.EMPTY && grid[0, 2] == Mark.CIRCLE) {
//        /*[X][ ][X]*/
//        grid[0, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.EMPTY && grid[0, 1] == Mark.CIRCLE && grid[0, 2] == Mark.CIRCLE) {
//        /*[ ][X][X]*/
//        grid[0, 0] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//
//        if(grid[1, 0] == Mark.CIRCLE && grid[1, 1] == Mark.CIRCLE && grid[1, 2] == Mark.EMPTY) {
//        /*[ ][ ][ ]*/
//        grid[1, 2] = Mark.CIRCLE;
//        /*[X][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[1, 0] == Mark.CIRCLE && grid[1, 1] == Mark.EMPTY && grid[1, 2] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[1, 1] = Mark.CIRCLE;
//        /*[X][ ][X]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[1, 0] == Mark.EMPTY && grid[1, 1] == Mark.CIRCLE && grid[1, 2] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[1, 0] = Mark.CIRCLE;
//        /*[ ][X][X]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//
//        if(grid[2, 0] == Mark.CIRCLE && grid[2, 1] == Mark.CIRCLE && grid[2, 2] == Mark.EMPTY) {
//        /*[ ][ ][ ]*/
//        grid[2, 2] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][X][ ]*/
//        } else
//        if(grid[2, 0] == Mark.CIRCLE && grid[2, 1] == Mark.EMPTY && grid[2, 2] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[2, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][X]*/
//        } else
//        if(grid[2, 0] == Mark.EMPTY && grid[2, 1] == Mark.CIRCLE && grid[2, 2] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[2, 0] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][X][X]*/
//        } else
//
//        if(grid[0, 0] == Mark.CIRCLE && grid[1, 1] == Mark.CIRCLE && grid[2, 2] == Mark.EMPTY) {
//        /*[X][ ][ ]*/
//        grid[2, 2] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.CIRCLE && grid[1, 1] == Mark.EMPTY && grid[2, 2] == Mark.CIRCLE) {
//        /*[X][ ][ ]*/
//        grid[1, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][X]*/
//        } else
//        if(grid[0, 0] == Mark.EMPTY && grid[1, 1] == Mark.CIRCLE && grid[2, 2] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[0, 0] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][X]*/
//        } else
//
//        if(grid[0, 2] == Mark.CIRCLE && grid[1, 1] == Mark.CIRCLE && grid[2, 0] == Mark.EMPTY) {
//        /*[ ][ ][X]*/
//        grid[2, 0] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 2] == Mark.CIRCLE && grid[1, 1] == Mark.EMPTY && grid[2, 0] == Mark.CIRCLE) {
//        /*[ ][ ][X]*/
//        grid[1, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][ ]*/
//        } else
//        if(grid[0, 2] == Mark.EMPTY && grid[1, 1] == Mark.CIRCLE && grid[2, 0] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[0, 2] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][ ]*/
//        } else
//
//        if(grid[0, 0] == Mark.CIRCLE && grid[1, 0] == Mark.CIRCLE && grid[2, 0] == Mark.EMPTY) {
//        /*[X][ ][ ]*/
//        grid[2, 0] = Mark.CIRCLE;
//        /*[X][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.CIRCLE && grid[1, 0] == Mark.EMPTY && grid[2, 0] == Mark.CIRCLE) {
//        /*[X][ ][ ]*/
//        grid[1, 0] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.EMPTY && grid[1, 0] == Mark.CIRCLE && grid[2, 0] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[0, 0] = Mark.CIRCLE;
//        /*[X][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][ ]*/
//        } else
//
//        if(grid[0, 1] == Mark.CIRCLE && grid[1, 1] == Mark.CIRCLE && grid[2, 1] == Mark.EMPTY) {
//        /*[ ][X][ ]*/
//        grid[2, 1] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 1] == Mark.CIRCLE && grid[1, 1] == Mark.EMPTY && grid[2, 1] == Mark.CIRCLE) {
//        /*[ ][X][ ]*/
//        grid[1, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][X][ ]*/
//        } else
//        if(grid[0, 1] == Mark.EMPTY && grid[1, 1] == Mark.CIRCLE && grid[2, 1] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[0, 1] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][X][ ]*/
//        } else
//
//        if(grid[0, 2] == Mark.CIRCLE && grid[1, 2] == Mark.CIRCLE && grid[2, 2] == Mark.EMPTY) {
//        /*[ ][ ][X]*/
//        grid[2, 2] = Mark.CIRCLE;
//        /*[ ][ ][X]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 2] == Mark.CIRCLE && grid[1, 2] == Mark.EMPTY && grid[2, 2] == Mark.CIRCLE) {
//        /*[ ][ ][X]*/
//        grid[1, 2] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][X]*/
//        } else
//        if(grid[0, 2] == Mark.EMPTY && grid[1, 2] == Mark.CIRCLE && grid[2, 2] == Mark.CIRCLE) {
//        /*[ ][ ][ ]*/
//        grid[0, 2] = Mark.CIRCLE;
//        /*[ ][ ][X]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][X]*/
//        } else
//
//
//        if(grid[0, 0] == Mark.CROSS && grid[0, 1] == Mark.CROSS && grid[0, 2] == Mark.EMPTY) {
//        /*[X][X][ ]*/
//        grid[0, 2] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.CROSS && grid[0, 1] == Mark.EMPTY && grid[0, 2] == Mark.CROSS) {
//        /*[X][ ][X]*/
//        grid[0, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.EMPTY && grid[0, 1] == Mark.CROSS && grid[0, 2] == Mark.CROSS) {
//        /*[ ][X][X]*/
//        grid[0, 0] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//
//        if(grid[1, 0] == Mark.CROSS && grid[1, 1] == Mark.CROSS && grid[1, 2] == Mark.EMPTY) {
//        /*[ ][ ][ ]*/
//        grid[1, 2] = Mark.CIRCLE;
//        /*[X][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[1, 0] == Mark.CROSS && grid[1, 1] == Mark.EMPTY && grid[1, 2] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[1, 1] = Mark.CIRCLE;
//        /*[X][ ][X]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[1, 0] == Mark.EMPTY && grid[1, 1] == Mark.CROSS && grid[1, 2] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[1, 0] = Mark.CIRCLE;
//        /*[ ][X][X]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//
//        if(grid[2, 0] == Mark.CROSS && grid[2, 1] == Mark.CROSS && grid[2, 2] == Mark.EMPTY) {
//        /*[ ][ ][ ]*/
//        grid[2, 2] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][X][ ]*/
//        } else
//        if(grid[2, 0] == Mark.CROSS && grid[2, 1] == Mark.EMPTY && grid[2, 2] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[2, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][X]*/
//        } else
//        if(grid[2, 0] == Mark.EMPTY && grid[2, 1] == Mark.CROSS && grid[2, 2] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[2, 0] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][X][X]*/
//        } else
//
//        if(grid[0, 0] == Mark.CROSS && grid[1, 1] == Mark.CROSS && grid[2, 2] == Mark.EMPTY) {
//        /*[X][ ][ ]*/
//        grid[2, 2] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.CROSS && grid[1, 1] == Mark.EMPTY && grid[2, 2] == Mark.CROSS) {
//        /*[X][ ][ ]*/
//        grid[1, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][X]*/
//        } else
//        if(grid[0, 0] == Mark.EMPTY && grid[1, 1] == Mark.CROSS && grid[2, 2] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[0, 0] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][X]*/
//        } else
//
//        if(grid[0, 2] == Mark.CROSS && grid[1, 1] == Mark.CROSS && grid[2, 0] == Mark.EMPTY) {
//        /*[ ][ ][X]*/
//        grid[2, 0] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 2] == Mark.CROSS && grid[1, 1] == Mark.EMPTY && grid[2, 0] == Mark.CROSS) {
//        /*[ ][ ][X]*/
//        grid[1, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][ ]*/
//        } else
//        if(grid[0, 2] == Mark.EMPTY && grid[1, 1] == Mark.CROSS && grid[2, 0] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[0, 2] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][ ]*/
//        } else
//
//        if(grid[0, 0] == Mark.CROSS && grid[1, 0] == Mark.CROSS && grid[2, 0] == Mark.EMPTY) {
//        /*[X][ ][ ]*/
//        grid[2, 0] = Mark.CIRCLE;
//        /*[X][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.CROSS && grid[1, 0] == Mark.EMPTY && grid[2, 0] == Mark.CROSS) {
//        /*[X][ ][ ]*/
//        grid[1, 0] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][ ]*/
//        } else
//        if(grid[0, 0] == Mark.EMPTY && grid[1, 0] == Mark.CROSS && grid[2, 0] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[0, 0] = Mark.CIRCLE;
//        /*[X][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[X][ ][ ]*/
//        } else
//
//        if(grid[0, 1] == Mark.CROSS && grid[1, 1] == Mark.CROSS && grid[2, 1] == Mark.EMPTY) {
//        /*[ ][X][ ]*/
//        grid[2, 1] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 1] == Mark.CROSS && grid[1, 1] == Mark.EMPTY && grid[2, 1] == Mark.CROSS) {
//        /*[ ][X][ ]*/
//        grid[1, 1] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][X][ ]*/
//        } else
//        if(grid[0, 1] == Mark.EMPTY && grid[1, 1] == Mark.CROSS && grid[2, 1] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[0, 1] = Mark.CIRCLE;
//        /*[ ][X][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][X][ ]*/
//        } else
//
//        if(grid[0, 2] == Mark.CROSS && grid[1, 2] == Mark.CROSS && grid[2, 2] == Mark.EMPTY) {
//        /*[ ][ ][X]*/
//        grid[2, 2] = Mark.CIRCLE;
//        /*[ ][ ][X]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][ ]*/
//        } else
//        if(grid[0, 2] == Mark.CROSS && grid[1, 2] == Mark.EMPTY && grid[2, 2] == Mark.CROSS) {
//        /*[ ][ ][X]*/
//        grid[1, 2] = Mark.CIRCLE;
//        /*[ ][ ][ ]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][X]*/
//        } else
//        if(grid[0, 2] == Mark.EMPTY && grid[1, 2] == Mark.CROSS && grid[2, 2] == Mark.CROSS) {
//        /*[ ][ ][ ]*/
//        grid[0, 2] = Mark.CIRCLE;
//        /*[ ][ ][X]*/
//        turnstate = Turnstate.TURN_PLAYER;
//        /*[ ][ ][X]*/
//        } else
//
//        if(turnstate == Turnstate.TURN_COMPUTER) {
//        bool tempbreak = false;
//        while(!tempbreak) {
//        int temprand = random.Next(8);
//        switch(temprand) {
//        case 0: if(grid[0, 0] == Mark.EMPTY) { grid[0, 0] = Mark.CIRCLE; turnstate = Turnstate.TURN_PLAYER; tempbreak = true; } break;
//        case 1: if(grid[0, 2] == Mark.EMPTY) { grid[0, 2] = Mark.CIRCLE; turnstate = Turnstate.TURN_PLAYER; tempbreak = true; } break;
//        case 2: if(grid[2, 0] == Mark.EMPTY) { grid[2, 0] = Mark.CIRCLE; turnstate = Turnstate.TURN_PLAYER; tempbreak = true; } break;
//        case 3: if(grid[2, 2] == Mark.EMPTY) { grid[2, 2] = Mark.CIRCLE; turnstate = Turnstate.TURN_PLAYER; tempbreak = true; } break;
//        case 4: if(grid[1, 0] == Mark.EMPTY) { grid[1, 0] = Mark.CIRCLE; turnstate = Turnstate.TURN_PLAYER; tempbreak = true; } break;
//        case 5: if(grid[1, 2] == Mark.EMPTY) { grid[1, 2] = Mark.CIRCLE; turnstate = Turnstate.TURN_PLAYER; tempbreak = true; } break;
//        case 6: if(grid[0, 1] == Mark.EMPTY) { grid[0, 1] = Mark.CIRCLE; turnstate = Turnstate.TURN_PLAYER; tempbreak = true; } break;
//        case 7: if(grid[2, 1] == Mark.EMPTY) { grid[2, 1] = Mark.CIRCLE; turnstate = Turnstate.TURN_PLAYER; tempbreak = true; } break;
//        }
//        }
//        }
//        Check();
//        }
//        }
//
//private enum Mark { EMPTY, CROSS, CIRCLE };
//
//    }
