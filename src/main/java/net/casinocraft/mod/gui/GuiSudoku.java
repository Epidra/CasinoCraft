package net.casinocraft.mod.gui;

import java.io.IOException;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerSudoku;
import net.casinocraft.mod.tileentity.TileEntitySudoku;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSudoku extends GuiContainer{
	
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntitySudoku tileFurnace;
    
    private ResourceLocation textureGroundBlack     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableBlack.png");
	private ResourceLocation textureGroundBlue      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableBlue.png");
	private ResourceLocation textureGroundBrown     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableBrown.png");
	private ResourceLocation textureGroundCyan      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableCyan.png");
	private ResourceLocation textureGroundGray      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableGray.png");
	private ResourceLocation textureGroundGreen     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableGreen.png");
	private ResourceLocation textureGroundLightBlue = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableLightBlue.png");
	private ResourceLocation textureGroundLime      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableLime.png");
	private ResourceLocation textureGroundMagenta   = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableMagenta.png");
	private ResourceLocation textureGroundOrange    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableOrange.png");
	private ResourceLocation textureGroundPink      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTablePink.png");
	private ResourceLocation textureGroundPurple    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTablePurple.png");
	private ResourceLocation textureGroundRed       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableRed.png");
	private ResourceLocation textureGroundSilver    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableSilver.png");
	private ResourceLocation textureGroundWhite     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableWhite.png");
	private ResourceLocation textureGroundYellow    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableYellow.png");
	
	private ResourceLocation textureExtra  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/MysticSquare.png");
    
    public GuiSudoku(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerSudoku(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = (TileEntitySudoku) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	
        }
        /*if(ButtonPressed(GhostKey.button_function_P1) == GhostState.pressed) { if(!active_gameover) pressed_response = true; }
        if(ButtonPressed(GhostKey.button_ok_P1) == GhostState.pressed) { if(active_selection) { Insert(); } else { Select(); } pressed_response = true; }
        if(ButtonPressed(GhostKey.arrow_up_P1) == GhostState.pressed) { if(!active_selection) { if(selector_grid.Y > 0) selector_grid.Y--; } else { } pressed_response = true; }
        if(ButtonPressed(GhostKey.arrow_down_P1) == GhostState.pressed) { if(!active_selection) { if(selector_grid.Y < 8) selector_grid.Y++; } else { } pressed_response = true; }
        if(ButtonPressed(GhostKey.arrow_left_P1) == GhostState.pressed) { if(!active_selection) { if(selector_grid.X > 0) selector_grid.X--; } else { if(selector_number > 0) selector_number--; } pressed_response = true; }
        if(ButtonPressed(GhostKey.arrow_right_P1) == GhostState.pressed) { if(!active_selection) { if(selector_grid.X < 8) selector_grid.X++; } else { if(selector_number < 8) selector_number++; } pressed_response = true; }
        if(pressed_event_touch) {
            pressed_response = true;
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 9; x++) {
                    if(Collision_Button(false, new Rectangle((int)SK.Position_Sudoku_Grid().X + 64 * x, (int)SK.Position_Sudoku_Grid().Y + 64 * y, 64, 64))) {
                        if(selector_grid == new Vector2(x, y)) {
                            if(!active_selection) { Select(); }
                        } else {
                            selector_grid = new Vector2(x, y);
                        }
                    }
                }
            }
            for(int x = 0; x < 9; x++) {
                if(Collision_Button(false, new Rectangle((int)SK.Position_Sudoku_Selection().X + 64 * x, (int)SK.Position_Sudoku_Selection().Y, 64, 64))) {
                    if(selector_number == x) {
                        if(active_selection) { Insert(); }
                    } else {
                        selector_number = x;
                    }
                }
            }
        }
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(Collision_Button(true, new Rectangle((int)SK.Position_Sudoku_Grid().X + 64 * x, (int)SK.Position_Sudoku_Grid().Y + 64 * y, 64, 64))) { if(!active_selection) selector_grid = new Vector2(x, y); }
            }
        }
        for(int x = 0; x < 9; x++) {
            if(Collision_Button(true, new Rectangle((int)SK.Position_Sudoku_Selection().X + 64 * x, (int)SK.Position_Sudoku_Selection().Y, 64, 64))) { if(active_selection) selector_number = x; }
        }*/
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        
    }
    
    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(getBackground());
        int i = (this.width  - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize); // Background
    }
    
    /*public override void Draw2() {
        spriteBatch.Draw(SK.texture_background_sudoku, SK.Position_Sudoku_Grid(), Color.White);
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(grid_mirror[x, y] != 0) {
                    if(grid[x, y] != 0) spriteBatch.Draw(SK.texture_spritesheet_sudoku, SK.Position_Sudoku_Grid() + new Vector2(x * 64, y * 64), new Rectangle(1 + Get_Number(true, grid[x, y]) * 65, 1 + Get_Number(false, grid[x, y]) * 65, 64, 64), Color.Black, 0.0f, new Vector2(0, 0), 1, SpriteEffects.None, 0.0f);
                } else {
                    if(grid[x, y] != 0) spriteBatch.Draw(SK.texture_spritesheet_sudoku, SK.Position_Sudoku_Grid() + new Vector2(x * 64, y * 64), new Rectangle(1 + Get_Number(true, grid[x, y]) * 65, 1 + Get_Number(false, grid[x, y]) * 65, 64, 64), Color.White, 0.0f, new Vector2(0, 0), 1, SpriteEffects.None, 0.0f);
                }
            }
        }

        if(active_selection) {
            spriteBatch.Draw(SK.texture_spritesheet_minos_64x, SK.Position_Sudoku_Grid() + new Vector2(selector_grid.X * 64, selector_grid.Y * 64), new Rectangle(0, 0, 64, 64), Color.Azure * 1.00f);
        } else {
            spriteBatch.Draw(SK.texture_spritesheet_minos_64x, SK.Position_Sudoku_Grid() + new Vector2(selector_grid.X * 64, selector_grid.Y * 64), null, Color.Azure * 0.50f);
        }

        for(int x = 0; x < 9; x++) {
            spriteBatch.Draw(SK.texture_spritesheet_sudoku, SK.Position_Sudoku_Selection() + new Vector2(x * 64, 0), new Rectangle(1 + Get_Number(true, x + 1) * 65, 1 + Get_Number(false, x + 1) * 65, 64, 64), Color.White);
        }
        if(active_selection) {
            spriteBatch.Draw(SK.texture_spritesheet_minos_64x, SK.Position_Sudoku_Selection() + new Vector2(selector_number * 64, 0), new Rectangle(0, 0, 64, 64), Color.Blue * 0.50f);
        }
    }*/

    private int Get_Number(boolean xy, int i) {
        if(xy) {
            if(i == 1) return 0;
            if(i == 2) return 1;
            if(i == 3) return 2;
            if(i == 4) return 0;
            if(i == 5) return 1;
            if(i == 6) return 2;
            if(i == 7) return 0;
            if(i == 8) return 1;
            if(i == 9) return 2;
        } else {
            if(i == 1) return 0;
            if(i == 2) return 0;
            if(i == 3) return 0;
            if(i == 4) return 1;
            if(i == 5) return 1;
            if(i == 6) return 1;
            if(i == 7) return 2;
            if(i == 8) return 2;
            if(i == 9) return 2;
        }
        return i;
    }
    
    private ResourceLocation getBackground(){
    	if(tileFurnace.color == EnumDyeColor.BLACK)      return textureGroundBlack;
    	if(tileFurnace.color == EnumDyeColor.BLUE)       return textureGroundBlue;
    	if(tileFurnace.color == EnumDyeColor.BROWN)      return textureGroundBrown;
    	if(tileFurnace.color == EnumDyeColor.CYAN)       return textureGroundCyan;
    	if(tileFurnace.color == EnumDyeColor.GRAY)       return textureGroundGray;
    	if(tileFurnace.color == EnumDyeColor.GREEN)      return textureGroundGreen;
    	if(tileFurnace.color == EnumDyeColor.LIGHT_BLUE) return textureGroundLightBlue;
    	if(tileFurnace.color == EnumDyeColor.LIME)       return textureGroundLime;
    	if(tileFurnace.color == EnumDyeColor.MAGENTA)    return textureGroundMagenta;
    	if(tileFurnace.color == EnumDyeColor.ORANGE)     return textureGroundOrange;
    	if(tileFurnace.color == EnumDyeColor.PINK)       return textureGroundPink;
    	if(tileFurnace.color == EnumDyeColor.PURPLE)     return textureGroundPurple;
    	if(tileFurnace.color == EnumDyeColor.RED)        return textureGroundRed;
    	if(tileFurnace.color == EnumDyeColor.SILVER)     return textureGroundSilver;
    	if(tileFurnace.color == EnumDyeColor.WHITE)      return textureGroundWhite;
    	if(tileFurnace.color == EnumDyeColor.YELLOW)     return textureGroundYellow;
    	
    	return textureGroundGray;
    }
}