package mod.casinocraft.gui.arcade;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Entity;
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

public class GuiSnake extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSnake(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_SNAKE);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_UP)    { actionTouch(1); }
        if(keyCode == KEY_DOWN)  { actionTouch(2); }
        if(keyCode == KEY_LEFT)  { actionTouch(3); }
        if(keyCode == KEY_RIGHT) { actionTouch(4); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 2) {
            this.fontRenderer.drawString("" + tc.scorePoint, 16+16, 16+200+16, 9999999);
        }
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
            this.drawTexturedModalRect(guiLeft+16 + getVector(0).X*16, guiTop + getVector(0).Y*16, 128, 0+66, 16, 16);
            this.drawTexturedModalRect(guiLeft+16 + getEntity(0).Get_Pos().X, guiTop + getEntity(0).Get_Pos().Y, 0, getEntity(0).Get_LookDirection()*16+66, 16, 16);
            for(Entity tail : getEntityList(0)){
                this.drawTexturedModalRect(guiLeft+16 + tail.Get_Pos().X, guiTop + tail.Get_Pos().Y, 16, 66, 16, 16);
            }
            if(tc.turnstate == 5) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }



    //--------------------CUSTOM--------------------



    Entity octanom_head;
    List<Entity> octanom_tail = new ArrayList<Entity>();

    int temp_player; // Player Input
    int temp_auto;   // automatic movement

    int pointer;

    Vector2 point;

    boolean active_move_tail;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntitySnake(){
   //    super(null, null);
   //}

   //public TileEntitySnake(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_SNAKE.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SNAKE.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SNAKE.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        active_move_tail = true;
        pointer = -1;
        temp_player = 0;
        temp_auto = 0;
        octanom_head = new Entity(1, new Vector2(0, 0), new Vector2(0, 0));
        point = new Vector2(-1, -1);
        Command_Spawn_Point();
        octanom_tail.clear();
    }

    public void actionTouch(int action){
        temp_player = action;
    }

    public void update(){
        if(tc.turnstate >= 2 && tc.turnstate < 4) {
            octanom_head.Update();
            if(active_move_tail) {
                for(Entity tail : octanom_tail) { tail.Update(); }
            }
            Command_Move();
            Command_Collision();
        }
    }



    //--------------------GETTER--------------------

    public Vector2 getVector(int index){
        return point;
    }

    public Entity getEntity(int index){
        return octanom_head;
    }

    public List<Entity> getEntityList(int index){
        return octanom_tail;
    }



    //--------------------CUSTOM--------------------

    private void Command_Move() {
        if(octanom_head.Get_Pos().matches(octanom_head.Get_Next())) {
            Vector2 position = new Vector2(octanom_head.Get_Pos());
            // current moving direction
            // Cancels out moving in opposite direction (and running into own tail)
            //if(octanom_head.Get_Vel().Y < 0) { temp = 1; if(temp_movement == 2) temp_movement = 0; }
            //if(octanom_head.Get_Vel().Y > 0) { temp = 2; if(temp_movement == 1) temp_movement = 0; }
            //if(octanom_head.Get_Vel().X < 0) { temp = 3; if(temp_movement == 4) temp_movement = 0; }
            //if(octanom_head.Get_Vel().X > 0) { temp = 4; if(temp_movement == 3) temp_movement = 0; }

            // Updates Velocity
            //if(temp_movement == 1) { octanom_head.Set_InMotion( 0, -1); }
            //if(temp_movement == 2) { octanom_head.Set_InMotion( 0,  1); }
            //if(temp_movement == 3) { octanom_head.Set_InMotion(-1,  0); }
            //if(temp_movement == 4) { octanom_head.Set_InMotion( 1,  0); }

            // Updates Next()
            if(temp_player == 1 && octanom_head.Get_Next().Y !=   0) { octanom_head.Set_InMotion( 0, -Speed()); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 1 && octanom_head.Get_Next().Y ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 2 && octanom_head.Get_Next().Y != 240) { octanom_head.Set_InMotion( 0,  Speed()); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 2 && octanom_head.Get_Next().Y == 240) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 3 && octanom_head.Get_Next().X !=   0) { octanom_head.Set_InMotion(-Speed(),  0); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 3 && octanom_head.Get_Next().X ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_player == 4 && octanom_head.Get_Next().X != 240-32) { octanom_head.Set_InMotion( Speed(),  0); temp_auto = temp_player; temp_player = 0; }
            else if(temp_player == 4 && octanom_head.Get_Next().X == 240-32) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0; temp_player = 0; }
            else if(temp_auto == 1 && octanom_head.Get_Next().Y !=   0) { octanom_head.Set_InMotion( 0, -Speed());  }
            else if(temp_auto == 1 && octanom_head.Get_Next().Y ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 2 && octanom_head.Get_Next().Y != 240) { octanom_head.Set_InMotion( 0,  Speed());  }
            else if(temp_auto == 2 && octanom_head.Get_Next().Y == 240) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 3 && octanom_head.Get_Next().X !=   0) { octanom_head.Set_InMotion(-Speed(),  0);  }
            else if(temp_auto == 3 && octanom_head.Get_Next().X ==   0) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            else if(temp_auto == 4 && octanom_head.Get_Next().X != 240-32) { octanom_head.Set_InMotion( Speed(),  0);  }
            else if(temp_auto == 4 && octanom_head.Get_Next().X == 240-32) { octanom_head.Set_InMotion( 0,  0); temp_auto = 0;  }
            //if(active_move_tail)
            for(Entity tail : octanom_tail) {
                if(position.X > tail.Get_Pos().X) { tail.Set_Pos(position.X - 16, position.Y     ); tail.Set_Vel( Speed(),  0); }
                else if(position.X < tail.Get_Pos().X) { tail.Set_Pos(position.X + 16, position.Y     ); tail.Set_Vel(-Speed(),  0); }
                else if(position.Y > tail.Get_Pos().Y) { tail.Set_Pos(position.X     , position.Y - 16); tail.Set_Vel( 0,  Speed()); }
                else if(position.Y < tail.Get_Pos().Y) { tail.Set_Pos(position.X     , position.Y + 16); tail.Set_Vel( 0, -Speed()); }
                position.set(tail.Get_Pos());
            }
            active_move_tail = !octanom_head.Get_Vel().matches(0, 0);
        }
    }

    private void Command_Collision() {
        for(int i = 0; i < 5; i++) {
            if(octanom_head.Get_Pos().matches(point.X*16, point.Y*16)) {
                tc.scorePoint += 1 * tc.difficulty;
                pointer = i;
                Command_Spawn_Point();
                Command_Spawn_Tail();
            }
        }
        if(octanom_tail.size() > 1) {
            for(Entity tail : octanom_tail) {
                if(octanom_head.Get_Pos().matches(tail.Get_Pos())) {
                    tc.turnstate = 4;
                }
            }
        }
    }

    private int Speed(){
        return 2;
    }

    private void Command_Spawn_Point() {
        boolean temp_break = false;
        int x = 0;
        int y = 0;
        for(int i = 0; i < 1; i++) {
            if(pointer == i || (pointer == -1 && i < 1)) {
                int b = 0;
                temp_break = false;
                while(!temp_break) {
                    b++;
                    boolean temp_internal = false;
                    x = tc.rand.nextInt(12)+1;
                    y = tc.rand.nextInt(12)+1;
                    if(b < 10) {
                        if(octanom_head.Get_Next() != new Vector2(x, y)) {
                            for(Entity tail : octanom_tail) {
                                if(tail.Get_Grid().matches(x, y)) {
                                    temp_internal = true;
                                    break;
                                }
                            }
                            //for(int j = 0; j < 5; j++) {
                            if(point.matches(x, y))
                                temp_internal = true;
                            //}
                            if(!temp_internal) {
                                temp_break = true;
                            }
                        }
                    } else {
                        if(octanom_head.Get_Next() != new Vector2(x, y)) {
                            temp_break = true;
                        }
                    }
                    point = new Vector2(x, y);
                }
            }
        }
    }

    private void Command_Spawn_Tail() {
        Vector2 pos = new Vector2(octanom_head.Get_Pos()/*.offset(octanom_head.Get_Vel().X*-16, octanom_head.Get_Vel().Y*-16)*/);
        int i = 0;
        for(Entity tail : octanom_tail) {
            if(i + 1 == octanom_tail.size()) {
                pos.set(tail.Get_Pos()/*.offset(tail.Get_Vel().X*16, tail.Get_Vel().Y*16)*/);
            }
            i++;
        }
        octanom_tail.add(new Entity(0, pos, pos));
    }

}
