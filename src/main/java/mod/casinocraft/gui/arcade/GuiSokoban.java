package mod.casinocraft.gui.arcade;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Entity;
import mod.casinocraft.util.MapRoom;
import mod.shared.util.Vector2;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GuiSokoban extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSokoban(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_SOKOBAN);
        tc.gridI = new     int[16][12];
        tc.gridB = new boolean[16][12];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_UP)    { actionTouch(0); }
        if(keyCode == KEY_DOWN)  { actionTouch(1); }
        if(keyCode == KEY_LEFT)  { actionTouch(2); }
        if(keyCode == KEY_RIGHT) { actionTouch(3); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ARCADEDUMMY);
        this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background

        if(tc.turnstate == 1) {
            if(intro < 256 - 80) {
                intro = 0;
                tc.turnstate = 2;
            }
        }

        if(tc.turnstate >= 2) {
            if(tc.turnstate == 5) GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int x = 1; x < 15; x++){
                for(int y = 0; y < 12; y++){
                    //if(tc.getValue(x + y*16) == 1) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 32, 64, 16, 16);
                    //if(tc.getValue(x + y*16) == 2) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16, 48, 64, 16, 16);
                    if(getFlag (x + y*16)     ) this.drawTexturedModalRect(guiLeft + x*16, guiTop + y*16,  16*7, 216, 16, 16);
                }
            }
            for(Entity e : getEntityList(0)){ this.drawTexturedModalRect(guiLeft + e.Get_Pos().X, guiTop + e.Get_Pos().Y,  16*14, 216, 16, 16); }
            this.drawTexturedModalRect(guiLeft + getEntity(0).Get_Pos().X, guiTop + getEntity(0).Get_Pos().Y, 0, getEntity(0).Get_LookDirection()*16+66, 16, 16);
            for(Entity e : getEntityList(1)){ this.drawTexturedModalRect(guiLeft + e.Get_Pos().X, guiTop + e.Get_Pos().Y, 16*10, 216, 16, 16); }
            if(tc.turnstate == 5) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }



    //--------------------CUSTOM--------------------



    MapRoom MP = new MapRoom();

    Entity octanom;
    List<Entity> crate = new ArrayList<Entity>();
    List<Entity> cross = new ArrayList<Entity>();

    boolean moving;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntitySokoban(){
   //    super(null, null);
   //}

   //public TileEntitySokoban(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new     int[16][12];
   //    gridB = new boolean[16][12];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_SOKOBAN.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SOKOBAN.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SOKOBAN.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        tc.gridB = new boolean[16][12];
        tc.gridI = new int[16][12];
        octanom = new Entity(1, new Vector2(16 * 15, 16 * 15), new Vector2(16 * 15, 16 * 15));
        crate.clear();
        cross.clear();
        Load_Map();
    }

    public void actionTouch(int action){
        Command_Move(action);
    }

    public void update(){
        if(moving) {
            boolean swittch = true;
            for(Entity c : crate) {
                if(c.Get_Pos().X == c.Get_Next().X * 16 && c.Get_Pos().Y == c.Get_Next().Y * 16) {
                    c.Set_InMotion(0, 0);
                } else {
                    swittch = false;
                }
            }
            if(swittch) {
                moving = false;
            }
        } else {
            // Input
        }
        if(tc.turnstate >= 2 && tc.turnstate < 4) {
            octanom.Update();
            boolean win = true;
            for(Entity e : crate) {
                e.Update();
                boolean hp1 = true;
                for(Entity c : cross) {
                    if(c.Get_Pos().matches(e.Get_Pos())) {
                        hp1 = false;
                    }
                }
                if(hp1) { e.Set_HP(1); win = false; } else { e.Set_HP(2); }
            }
            for(Entity e : cross) {
                e.Update();
            }
            if(win && tc.turnstate < 4) {
                tc.scorePoint = crate.size() * 500;
                tc.turnstate = 4;
            }
            //Command_Move();
        }
    }



    //--------------------GETTER--------------------

    public Entity getEntity(int index){
        return octanom;
    }

    public List<Entity> getEntityList(int index){
        if(index == 0) return crate;
        return cross;
    }

    public int getValue(int index){
        return tc.gridI[index%16][index/16];
    }

    public boolean getFlag(int index){
        return tc.gridB[index%16][index/16];
    }

    public String getString(int index){
        return "";
    }



    //--------------------CUSTOM--------------------

    private void Load_Map() {
        List<String> list = MP.LoadSokoban(tc.rand);
        int y = 0;
        for(String s : list) {
            for(int x = 0; x < 16; x++) {
                char c = s.charAt(x);
                if(c != ' ') tc.gridI[x][y] = ConvertGrid(x, y);
                switch(c) {
                    case ' ': break;
                    case 'X': tc.gridB[x][y] = true; break;
                    case 'O': octanom = new Entity(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y));  break;
                    case 'M': cross.add(new Entity(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y))); break;
                    case 'C': crate.add(new Entity(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y))); break;
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

    private void Command_Move(int direction) {
        if(!octanom.isMoving()){
            int x = 0;
            int y = 0;
            if(direction == 0){ x =  0; y = -1; }
            if(direction == 1){ x =  0; y =  1; }
            if(direction == 2){ x = -1; y =  0; }
            if(direction == 3){ x =  1; y =  0; }
            if(!tc.gridB[octanom.Get_Grid().X + x][octanom.Get_Grid().Y + y]) { // Free space
                boolean blockedO = false;
                for(Entity c : crate) {
                    if(c.Get_Grid().X == octanom.Get_Grid().X + x && c.Get_Grid().Y == octanom.Get_Grid().Y + y) {
                        blockedO = true;
                        if(!tc.gridB[octanom.Get_Grid().X + x*2][octanom.Get_Grid().Y + y*2]) {
                            boolean blockedC = false;
                            for(Entity c2 : crate) {
                                if(c.Get_Grid().X == octanom.Get_Grid().X + x*2 && c.Get_Grid().Y == octanom.Get_Grid().Y + y*2) {
                                    blockedC = true;
                                    // crate blocked by crate
                                }
                            }
                            if(!blockedC) {
                                moving = true;
                                c.Set_InMotion(x*2, y*2);
                            }
                        }
                    }
                }
                if(!blockedO)
                    octanom.Set_InMotion(x*2, y*2);
            }
        }
    }

}
