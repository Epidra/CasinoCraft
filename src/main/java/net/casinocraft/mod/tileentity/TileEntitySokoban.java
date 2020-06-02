package net.casinocraft.mod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerSokoban;
import net.casinocraft.mod.util.Card;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntitySokoban extends TileEntityCasino {
	
	//MapRoom MP = new MapRoom();

    //boolean[][] grid_main   = new boolean[16][12];
    //int [][] grid_field  = new int [16][12];

    //Entity octanom;
    //List<Entity> crate = new List<Entity>();
    //List<Entity> cross = new List<Entity>();

    //string temp_movement;

    //bool moving;
	
	public EnumDyeColor color;
	
	public TileEntitySokoban(){
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerSokoban(playerInventory, this);
    }
	
	
	public void start(){
		
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
}

/*
using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Input.Touch;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Media;

namespace GameCenter {
    class Sokoban : Ghost {

        

        public Sokoban(string _id, ContentManager _content, ShopKeeper _Shopkeeper, FileManager _Filemanager, JukeBox _Jukebox, float screenX, float screenY) : base(_id, _content, _Shopkeeper, _Filemanager, _Jukebox, screenX, screenY) {
            id = _id;
            CM = _content;
            SK = _Shopkeeper;
            FM = _Filemanager;
            JK = _Jukebox;
            screensize_width = (int)SK.screensize_main.X;
            screensize_height = (int)SK.screensize_main.Y;
            screensize_width_scale = screenX;
            screensize_height_scale = screenY;
            random = new Random();
        }

        public override void Start2() {
            temp_movement = "empty";
            grid_main = new bool[16, 12];
            grid_field = new int[16, 12];
            octanom = new Entity(1, new Vector2(32 * 15, 32 * 15), new Vector2(0, 0));
            crate.RemoveRange(0, crate.Count);
            cross.RemoveRange(0, cross.Count);
            Load_Map();
        }

        private void Load_Map() {
            List<string> list = MP.sokoban[random.Next(MP.sokoban.Length)];
            int y = 0;
            foreach(string s in list) {
                for(int x = 0; x < 16; x++) {
                    if(s.Substring(x, 1) != " ") grid_field[x, y] = ConvertGrid(x, y);
                    switch(s.Substring(x, 1)) {
                        case " ": break;
                        case "X": grid_main[x, y] = true; break;
                        case "O": octanom = new Entity(1, new Vector2(32 * x, 32 * y), new Vector2(x, y));  break;
                        case "M": cross.Add(new Entity(1, new Vector2(32 * x, 32 * y), new Vector2(x, y))); break;
                        case "C": crate.Add(new Entity(1, new Vector2(32 * x, 32 * y), new Vector2(x, y))); break;
                    }
                }
                y++;
            }
        }

        private int ConvertGrid(int x, int y) {
            if(y % 2 == 0) if(x % 2 == 0) return 1;
            if(y % 2 == 0) if(x % 2 != 0) return 2;
            if(y % 2 != 0) if(x % 2 == 0) return 2;
            if(y % 2 != 0) if(x % 2 != 0) return 1;
            return 0;
        }

        public override string Update2() {
            if(moving) {
                bool swittch = true;
                foreach(Entity c in crate) {
                    if(c.Get_Pos() == c.Get_Next() * 32) {
                        c.Set_Vel(0, 0);
                    } else {
                        swittch = false;
                    }
                }
                if(swittch) {
                    moving = false;
                }
            } else {
                if(ButtonPressed(GhostKey.button_function_P1) == GhostState.pressed) { pressed_response = true; }
                if(ButtonPressed(GhostKey.button_ok_P1) == GhostState.pressed)    { pressed_response = true; }
                if(ButtonPressed(GhostKey.arrow_up_P1) == GhostState.pressed)     { temp_movement = "Up";    pressed_response = true; }
                if(ButtonPressed(GhostKey.arrow_down_P1) == GhostState.pressed)   { temp_movement = "Down";  pressed_response = true; }
                if(ButtonPressed(GhostKey.arrow_left_P1) == GhostState.pressed)   { temp_movement = "Left";  pressed_response = true; }
                if(ButtonPressed(GhostKey.arrow_right_P1) == GhostState.pressed)  { temp_movement = "Right"; pressed_response = true; }
            }
            return "void";
        }

        public override string Update3(GameTime gameTime) {
            if(!active_pause && !active_gameover) {
                octanom.Update();
                bool win = true;
                foreach(Entity e in crate) {
                    e.Update();
                    bool hp1 = true;
                    foreach(Entity c in cross) {
                        if(c.Get_Pos() == e.Get_Pos()) {
                            hp1 = false;
                        }
                    }
                    if(hp1) { e.Set_HP(1); win = false; } else { e.Set_HP(2); }
                }
                foreach(Entity e in cross) {
                    e.Update();
                }
                if(win && !active_gameover) {
                    JK.Noise("Cleared");
                    score_points = crate.Count * 5;
                    GameOver(gameTime.TotalGameTime.TotalSeconds);
                }
                Command_Move(gameTime);
            }
            return "void";
        }

        public override void Draw2() {
            for(int x = 0; x < 16; x++) {
                for(int y = 0; y < 12; y++) {
                    if(grid_field[x, y] == 1) spriteBatch.Draw(SK.texture_background_grid32,     SK.Position_DisplayEdge() + SK.Position_Grid32() + new Vector2(6 * 32, 5 * 32) + new Vector2(32 * x, 32 * y), new Rectangle( 0, 0, 32, 32), Color.WhiteSmoke, 0.0f, new Vector2(0, 0), 1, SpriteEffects.None, 0.0f);
                    if(grid_field[x, y] == 2) spriteBatch.Draw(SK.texture_background_grid32,     SK.Position_DisplayEdge() + SK.Position_Grid32() + new Vector2(6 * 32, 5 * 32) + new Vector2(32 * x, 32 * y), new Rectangle(32, 0, 32, 32), Color.WhiteSmoke, 0.0f, new Vector2(0, 0), 1, SpriteEffects.None, 0.0f);
                    if(grid_main [x, y])      spriteBatch.Draw(SK.texture_spritesheet_octagames, SK.Position_DisplayEdge() + SK.Position_Grid32() + new Vector2(6 * 32, 5 * 32) + new Vector2(32 * x, 32 * y), new Rectangle(32, 0, 32, 32), Color.WhiteSmoke, 0.0f, new Vector2(0, 0), 1, SpriteEffects.None, 0.0f);
                }
            }
            foreach(Entity c in crate) { spriteBatch.Draw(SK.texture_spritesheet_octagames,    SK.Position_DisplayEdge() + SK.Position_Grid32() + new Vector2(6 * 32, 5 * 32) + c.      Get_GridPos(), new Rectangle( 0,                               32, 32, 32), Color.White, 0.0f, new Vector2(0, 0), 1, SpriteEffects.None, 0.0f); }
            foreach(Entity c in cross) { spriteBatch.Draw(SK.texture_spritesheet_octagames,    SK.Position_DisplayEdge() + SK.Position_Grid32() + new Vector2(6 * 32, 5 * 32) + c.      Get_GridPos(), new Rectangle(32,                               32, 32, 32), Color.White, 0.0f, new Vector2(0, 0), 1, SpriteEffects.None, 0.0f); }
                                         spriteBatch.Draw(SK.texture_spritesheet_octanom_head, SK.Position_DisplayEdge() + SK.Position_Grid32() + new Vector2(6 * 32, 5 * 32) + octanom.Get_GridPos(), new Rectangle( 0, octanom.Get_LookDirection() * 32, 32, 32), Color.White, 0.0f, new Vector2(0, 0), 1, SpriteEffects.None, 0.0f);
        }

        private void Command_Move(GameTime gameTime) {
            if(octanom.Get_Pos() == octanom.Get_Next() * 32) {
                octanom.Set_Vel(0, 0);
                bool swittch = true;

                if(temp_movement == "Up" && !grid_main[(int)octanom.Get_Next().X, (int)octanom.Get_Next().Y - 1]) {
                    octanom.Set_Next(new Vector2(octanom.Get_Next().X, octanom.Get_Next().Y - 1));
                    foreach(Entity c in crate) {
                        if(c.Get_Pos() == octanom.Get_Next() * 32) {
                            octanom.Set_Next(new Vector2(octanom.Get_Next().X, octanom.Get_Next().Y + 1));
                            swittch = false;
                            if(!grid_main[(int)octanom.Get_Next().X, (int)octanom.Get_Next().Y - 2]) {
                                bool switch2 = true;
                                foreach(Entity c2 in crate) {
                                    if(c2.Get_Pos() == new Vector2(octanom.Get_Next().X, octanom.Get_Next().Y - 2) * 32) {
                                        switch2 = false;
                                    }
                                }
                                if(switch2) {
                                    moving = true;
                                    c.Set_Next(new Vector2(octanom.Get_Next().X, octanom.Get_Next().Y - 2));
                                    c.Set_Vel(0, -2);
                                }
                            }
                        }
                    }
                    if(swittch) { octanom.Set_Vel(0, -2); }
                } else
                if(temp_movement == "Down" && !grid_main[(int)octanom.Get_Next().X, (int)octanom.Get_Next().Y + 1]) {
                    octanom.Set_Next(new Vector2(octanom.Get_Next().X, octanom.Get_Next().Y + 1));
                    foreach(Entity c in crate) {
                        if(c.Get_Pos() == octanom.Get_Next() * 32) {
                            octanom.Set_Next(new Vector2(octanom.Get_Next().X, octanom.Get_Next().Y - 1));
                            swittch = false;
                            if(!grid_main[(int)octanom.Get_Next().X, (int)octanom.Get_Next().Y + 2]) {
                                bool switch2 = true;
                                foreach(Entity c2 in crate) {
                                    if(c2.Get_Pos() == new Vector2(octanom.Get_Next().X, octanom.Get_Next().Y + 2) * 32) {
                                        switch2 = false;
                                    }
                                }
                                if(switch2) {
                                    moving = true;
                                    c.Set_Next(new Vector2(octanom.Get_Next().X, octanom.Get_Next().Y + 2));
                                    c.Set_Vel(0, 2);
                                }
                            }
                        }
                    }
                    if(swittch) { octanom.Set_Vel(0, 2); }
                } else
                if(temp_movement == "Left" && !grid_main[(int)octanom.Get_Next().X - 1, (int)octanom.Get_Next().Y]) {
                    octanom.Set_Next(new Vector2(octanom.Get_Next().X - 1, octanom.Get_Next().Y));
                    foreach(Entity c in crate) {
                        if(c.Get_Pos() == octanom.Get_Next() * 32) {
                            octanom.Set_Next(new Vector2(octanom.Get_Next().X + 1, octanom.Get_Next().Y));
                            swittch = false;
                            if(!grid_main[(int)octanom.Get_Next().X - 2, (int)octanom.Get_Next().Y]) {
                                bool switch2 = true;
                                foreach(Entity c2 in crate) {
                                    if(c2.Get_Pos() == new Vector2(octanom.Get_Next().X - 2, octanom.Get_Next().Y) * 32) {
                                        switch2 = false;
                                    }
                                }
                                if(switch2) {
                                    moving = true;
                                    c.Set_Next(new Vector2(octanom.Get_Next().X - 2, octanom.Get_Next().Y));
                                    c.Set_Vel(-2, 0);
                                }
                            }
                        }
                    }
                    if(swittch) { octanom.Set_Vel(-2, 0); }
                } else
                if(temp_movement == "Right" && !grid_main[(int)octanom.Get_Next().X + 1, (int)octanom.Get_Next().Y]) {
                    octanom.Set_Next(new Vector2(octanom.Get_Next().X + 1, octanom.Get_Next().Y));
                    foreach(Entity c in crate) {
                        if(c.Get_Pos() == octanom.Get_Next() * 32) {
                            octanom.Set_Next(new Vector2(octanom.Get_Next().X - 1, octanom.Get_Next().Y));
                            swittch = false;
                            if(!grid_main[(int)octanom.Get_Next().X + 2, (int)octanom.Get_Next().Y]) {
                                bool switch2 = true;
                                foreach(Entity c2 in crate) {
                                    if(c2.Get_Pos() == new Vector2(octanom.Get_Next().X + 2, octanom.Get_Next().Y) * 32) {
                                        switch2 = false;
                                    }
                                }
                                if(switch2) {
                                    moving = true;
                                    c.Set_Next(new Vector2(octanom.Get_Next().X + 2, octanom.Get_Next().Y));
                                    c.Set_Vel(2, 0);
                                }
                            }
                        }
                    }
                    if(swittch) { octanom.Set_Vel(2, 0); }
                }
                temp_movement = "null";
            }
        }
        
    }
}*/
