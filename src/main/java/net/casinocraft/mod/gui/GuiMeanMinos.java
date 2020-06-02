package net.casinocraft.mod.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerArcade;
import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerColumns;
import net.casinocraft.mod.container.ContainerMeanMinos;
import net.casinocraft.mod.tileentity.TileEntityArcade;
import net.casinocraft.mod.tileentity.TileEntityBaccarat;
import net.casinocraft.mod.tileentity.TileEntityColumns;
import net.casinocraft.mod.tileentity.TileEntityMeanMinos;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMeanMinos extends GuiContainer{
	
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityMeanMinos tileFurnace;
    
    private ResourceLocation textureGround = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/Arcade.png");
	private ResourceLocation textureExtra  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/MinoSmall.png");
    
    public GuiMeanMinos(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerMeanMinos(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = (TileEntityMeanMinos) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	if(guiLeft+256-32 < mouseX && mouseX < guiLeft+256 && guiTop+256-32 < mouseY && mouseY < guiTop+256){
				tileFurnace.start();
			}
        }
    }
    
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
        
    	if(keyCode == Keyboard.KEY_LEFT) { tileFurnace.Command_Strafe("left"); }
    	if(keyCode == Keyboard.KEY_RIGHT){ tileFurnace.Command_Strafe("right"); }
    	if(keyCode == Keyboard.KEY_DOWN) { tileFurnace.Domino_Fall(); }
    	if(keyCode == Keyboard.KEY_M)    { tileFurnace.Command_Turn("left"); }
    	if(keyCode == Keyboard.KEY_N)    { tileFurnace.Command_Turn("right"); }
    	
    	if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)){
            this.mc.thePlayer.closeScreen();
        }
        
        
        
        this.checkHotbarKeys(keyCode);
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	this.fontRendererObj.drawString("" + tileFurnace.score_points, 5, 10, 9999999);
    	this.fontRendererObj.drawString("" + tileFurnace.score_lives,  5, 20, 9999999);
    	this.fontRendererObj.drawString("" + tileFurnace.score_level,  5, 30, 9999999);
    }
    
    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(textureGround);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize); // Background
        
        this.mc.getTextureManager().bindTexture(textureExtra);
        for(int y = 0; y < 15; y++){
        	for(int x = 0; x < 10; x++){
        		if(tileFurnace.grid_color[x][y] != null){
        			if(tileFurnace.grid_color[x][y].compareTo("empty") != 0){
            			this.drawTexturedModalRect(guiLeft+8+45 + 16*x, guiTop+3 + 16*y, MinoPos(tileFurnace.grid_color[x][y], true), MinoPos(tileFurnace.grid_color[x][y], false), 16, 16);
            		}
        		}
        	}
        }
        
        if(tileFurnace.grid_color[0][0] != null){
        	this.drawTexturedModalRect(guiLeft+8+45 + 16*tileFurnace.tetromino[0].X, guiTop+3 + 16*tileFurnace.tetromino[0].Y, MinoPos(tileFurnace.container_current[0], true), MinoPos(tileFurnace.container_current[0], false), 16, 16);
        	this.drawTexturedModalRect(guiLeft+8+45 + 16*tileFurnace.tetromino[1].X, guiTop+3 + 16*tileFurnace.tetromino[1].Y, MinoPos(tileFurnace.container_current[1], true), MinoPos(tileFurnace.container_current[1], false), 16, 16);
        }
        
        this.drawTexturedModalRect(guiLeft+256-32, guiTop+256-32, 0, 96/2, 16, 16);
        
        if(tileFurnace.grid_color[0][0] != null) tileFurnace.update();
    }
    
    private int MinoPos(String mino, boolean xy){
    	if(mino.compareTo("fire")    == 0){ if(xy) return 16; else return  0; }
    	if(mino.compareTo("air")     == 0){ if(xy) return 32; else return  0; }
    	if(mino.compareTo("thunder") == 0){ if(xy) return 48; else return  0; }
    	if(mino.compareTo("water")   == 0){ if(xy) return 64; else return  0; }
    	if(mino.compareTo("ice")     == 0){ if(xy) return 80; else return  0; }
    	if(mino.compareTo("earth")   == 0){ if(xy) return  0; else return 64; }
    	if(mino.compareTo("metal")   == 0){ if(xy) return 16; else return 64; }
    	if(mino.compareTo("nature")  == 0){ if(xy) return 32; else return 64; }
    	if(mino.compareTo("light")   == 0){ if(xy) return 48; else return 64; }
    	if(mino.compareTo("dark")    == 0){ if(xy) return 64; else return 64; }
    	return 0;
    }
}