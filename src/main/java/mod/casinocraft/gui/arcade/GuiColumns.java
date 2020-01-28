package mod.casinocraft.gui.arcade;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.util.Vector2;
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

public class GuiColumns extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiColumns(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_COLUMNS);
        tc.gridI = new int[6][15];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(tc.turnstate == 2) {
            if(keyCode == KEY_UP)    { actionTouch(0); }
            if(keyCode == KEY_DOWN)  { actionTouch(1); }
            if(keyCode == KEY_LEFT)  { actionTouch(2); }
            if(keyCode == KEY_RIGHT) { actionTouch(3); }
            if(keyCode == KEY_M)     { actionTouch(4); }
            if(keyCode == KEY_N)     { actionTouch(5); }
            if(keyCode == KEY_ENTER) { actionTouch(6); }
        }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 2) {
            this.fontRenderer.drawString("" + tc.scorePoint, 8+16*11 + 4-24, 8+16*0 + 4+8, 9999999);
            this.fontRenderer.drawString("" + tc.scoreLives,  8+16*11 + 4-24, 8+16*1 + 4+8, 9999999);
            this.fontRenderer.drawString("" + tc.scoreLevel,  8+16*11 + 4-24, 8+16*2 + 4+8, 9999999);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_COLUMNS);
        this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background

        if(tc.turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 15; y++){
                for(int x = 0; x < 6; x++){
                    //drawMino(tc.getValue(y * 6 + x), x, y);
                    if(getValue(y * 6 + x) != -1) this.drawTexturedModalRect(guiLeft+16 + 8+16 + 16*x, guiTop + 8 + 16*y, tc.turnstate >= 4 ? 16*8 : 16*getValue(y * 6 + x), 216, 16, 16);
                }
            }

            this.drawTexturedModalRect(guiLeft+8 + 16*getValue(200)+16+16, guiTop+8 + 16*getValue(201), tc.turnstate >= 4 ? 16*8 : 16*getValue(210), 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+8 + 16*getValue(202)+16+16, guiTop+8 + 16*getValue(203), tc.turnstate >= 4 ? 16*8 : 16*getValue(211), 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+8 + 16*getValue(204)+16+16, guiTop+8 + 16*getValue(205), tc.turnstate >= 4 ? 16*8 : 16*getValue(212), 216, 16, 16);

            if(getValue(213) > -1) drawTetromino(getValue(213), getValue(214), getValue(215), 8+16*11-24-4, 8+8+8+16*4);
            if(getValue(216) > -1) drawTetromino(getValue(216), getValue(217), getValue(218), 8+16*11-24-4, 8+8+8+16*9);

            //this.drawTexturedModalRect(guiLeft+256-20, guiTop+256-20, 0, 32, 16, 16);

            //if(tc.turnstate == 2) tc.update();
        }
    }



    //--------------------CUSTOM--------------------

    private void drawTetromino(int mino0, int mino1, int mino2, int x, int y) {
        this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y +  0  , tc.turnstate >= 4 ? 16*8 : 16*mino0, 216, 16, 16);
        this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino1, 216, 16, 16);
        this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino2, 216, 16, 16);
    }





    public boolean active_hold;

    public int[] container_next    = new int[3];
    public int[] container_hold    = new int[3];
    public int[] container_current = new int[3];

    public double time_last;
    public double time_break;
    public int timer;

    public Vector2[] tromino = new Vector2[3];

    public List<Vector2> clear = new ArrayList<Vector2>();

    public int alpha;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityColumns(){
   //    super(null, null);
   //}

   //public TileEntityColumns(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new int[6][15];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_COLUMNS.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_COLUMNS.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_COLUMNS.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        active_hold = true;
        container_next[0] = Column_Roll();
        container_next[1] = Column_Roll();
        container_next[2] = Column_Roll();
        container_hold[0] = -1;
        container_hold[1] = -1;
        container_hold[2] = -1;
        container_current[0] = Column_Roll();
        container_current[1] = Column_Roll();
        container_current[2] = Column_Roll();
        Column_Create();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 15; j++) {
                tc.gridI[i][j] = -1;
            }
        }
        time_break = 500;
        time_last = 0;
        clear.clear();
        alpha = 255;
    }

    public void actionTouch(int action){
        if(action == 0){ Column_Drop();         } // UP
        if(action == 1){ Column_Fall();         } // DOWN
        if(action == 2){ Command_Strafe(true);  } // LEFT
        if(action == 3){ Command_Strafe(false); } // RIGHT
        if(action == 4){ Command_Cycle(true);   } // ROTATE LEFT
        if(action == 5){ Command_Cycle(false);  } // ROTATE RIGHT
        if(action == 6){ Command_Hold();        } // HOLD
    }

    public void update(){
        timer+=15;
        if(alpha == 255) {
            if(timer > time_last + time_break - tc.scoreLevel * 5 && tc.turnstate == 2) {
                Column_Fall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 255;
                for(int y = 0; y < 15; y++) {
                    for(int x = 0; x < 6; x++) {
                        if(IsCleared(x, y)) {
                            tc.gridI[x][y] = -1;
                        }
                    }
                }
                Command_Collapse();
            }
        }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){

        if(index == 200) return tromino[0].X;
        if(index == 201) return tromino[0].Y;
        if(index == 202) return tromino[1].X;
        if(index == 203) return tromino[1].Y;
        if(index == 204) return tromino[2].X;
        if(index == 205) return tromino[2].Y;
        if(index == 210) return tc.turnstate >= 4 ?  8 : container_current[0];
        if(index == 211) return tc.turnstate >= 4 ?  8 : container_current[1];
        if(index == 212) return tc.turnstate >= 4 ?  8 : container_current[2];
        if(index == 213) return tc.turnstate >= 4 ?  8 : container_next[0];
        if(index == 214) return tc.turnstate >= 4 ?  8 : container_next[1];
        if(index == 215) return tc.turnstate >= 4 ?  8 : container_next[2];
        if(index == 216) return tc.turnstate >= 4 ? -1 : container_hold[0];
        if(index == 217) return tc.turnstate >= 4 ? -1 : container_hold[1];
        if(index == 218) return tc.turnstate >= 4 ? -1 : container_hold[2];

        if(index == -1) return tc.scorePoint;
        if(index == -2) return tc.scoreLives;
        if(index == -3) return tc.scoreLevel;
        return inLine(index%6, index/6) && (alpha/75)%2==0 ? 7 : tc.gridI[index % 6][index / 6];
        //return gridI[index % 10][index / 10];
    }



    //--------------------CUSTOM--------------------

    private boolean inLine(int x, int y){
        for(Vector2 v : clear) {
            if(v.matches(x, y)) return true;
        }
        return false;
    }

    public void Column_Drop() {
        int tempPoint = tc.scorePoint;
        while(tc.scorePoint == tempPoint) {
            Column_Fall();
        }
    }

    private boolean IsCleared(int x, int y) {
        for(int i = 0; i < clear.size(); i++){
            if(clear.get(i).X == x && clear.get(i).Y == y) return true;
        }
        return false;
    }

    private void Command_Collapse() {
        // Gravity after match found and cleared
        int temp = 0;
        for(int y = 13; y >= 0; y--) {
            for(int x = 0; x < 6; x++) {
                if(tc.gridI[x][y] != -1) {
                    temp = 0;
                    if(tc.gridI[x][y + 1] == -1) {
                        temp++;
                        if(y+2 < 15 && tc.gridI[x][y + 2] == -1) {
                            temp++;
                            if(y+3 < 15 && tc.gridI[x][y + 3] == -1) {
                                temp++;
                            }
                        }
                    }
                    if(temp != 0) {
                        tc.gridI[x][y + temp] = tc.gridI[x][y];
                        tc.gridI[x][y       ] = -1;
                    }
                }
            }
        }
        clear.clear();
        alpha = 255;
        Check_Field();
    }

    public void Command_Strafe(boolean totheleft) {
        int dir = 0;
        if(totheleft) {
            dir = -1;
            if(tromino[0].X > 0) {
                if(tromino[1].X > 0) {
                    if(tromino[2].X > 0) {
                        if(tc.gridI[tromino[0].X + dir][tromino[0].Y] == -1) {
                            if(tc.gridI[tromino[1].X + dir][tromino[1].Y] == -1) {
                                if(tc.gridI[tromino[2].X + dir][tromino[2].Y] == -1) {
                                    tromino[0].X = tromino[0].X + dir;
                                    tromino[1].X = tromino[1].X + dir;
                                    tromino[2].X = tromino[2].X + dir;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            dir = 1;
            if(tromino[0].X < 5) {
                if(tromino[1].X < 5) {
                    if(tromino[2].X < 5) {
                        if(tc.gridI[tromino[0].X + dir][tromino[0].Y] == -1) {
                            if(tc.gridI[tromino[1].X + dir][tromino[1].Y] == -1) {
                                if(tc.gridI[tromino[2].X + dir][tromino[2].Y] == -1) {
                                    tromino[0].X = tromino[0].X + dir;
                                    tromino[1].X = tromino[1].X + dir;
                                    tromino[2].X = tromino[2].X + dir;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void Command_Cycle(boolean totheleft) {
        if(totheleft) {
            int temp = container_current[0];
            container_current[0] = container_current[1];
            container_current[1] = container_current[2];
            container_current[2] = temp;
        } else {
            int temp = container_current[2];
            container_current[2] = container_current[1];
            container_current[1] = container_current[0];
            container_current[0] = temp;
        }

    }

    public void Command_Hold() {
        if(active_hold) {
            active_hold = false;
            if(container_hold[0] == -1) {
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_hold[2] = container_current[2];
                container_current[0] = container_next[0];
                container_current[1] = container_next[1];
                container_current[2] = container_next[2];
                container_next[0] = Column_Roll();
                container_next[1] = Column_Roll();
                container_next[2] = Column_Roll();
            } else {
                int[] temp = new int[3];
                temp[0] = container_hold[0];
                temp[1] = container_hold[1];
                temp[2] = container_hold[2];
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_hold[2] = container_current[2];
                container_current[0] = temp[0];
                container_current[1] = temp[1];
                container_current[2] = temp[2];
            }
            Column_Create();
        }
    }

    private int Column_Roll() {
        return tc.rand.nextInt(7);
    }

    private void Column_Create() {
        tromino[0] = new Vector2(2, 0);
        tromino[1] = new Vector2(2, 1);
        tromino[2] = new Vector2(2, 2);
    }

    public void Column_Fall() {
        if(tromino[0].Y < 14 && tromino[1].Y < 14 && tromino[2].Y < 14) {
            if(tc.gridI[tromino[0].X][tromino[0].Y + 1] == -1 && tc.gridI[tromino[1].X][tromino[1].Y + 1] == -1 && tc.gridI[tromino[2].X][tromino[2].Y + 1] == -1) {
                tromino[0].Y = tromino[0].Y + 1;
                tromino[1].Y = tromino[1].Y + 1;
                tromino[2].Y = tromino[2].Y + 1;
            } else {
                Column_Place();
            }
        } else {
            Column_Place();
        }
    }

    private void Column_Place() {
        active_hold = true;
        tc.gridI[tromino[0].X][tromino[0].Y] = container_current[0];
        tc.gridI[tromino[1].X][tromino[1].Y] = container_current[1];
        tc.gridI[tromino[2].X][tromino[2].Y] = container_current[2];
        tc.scorePoint = tc.scorePoint + 2 * 2;
        if(tromino[0].Y == 0) tc.turnstate = 4;
        container_current[0] = container_next[0];
        container_current[1] = container_next[1];
        container_current[2] = container_next[2];
        container_next[0] = Column_Roll();
        container_next[1] = Column_Roll();
        container_next[2] = Column_Roll();
        Column_Create();
        Check_Field();
    }

    private void Check_Field() {

        int points = 0;
        int bonus = 0;

        for(int y = 14; y >= 0; y--) {
            for(int x = 0; x < 6; x++) {
                if(x < 4 && tc.gridI[x][y] != -1 && tc.gridI[x][y] == tc.gridI[x + 1][y] && tc.gridI[x][y] == tc.gridI[x + 2][y]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y)); clear.add(new Vector2(x + 2, y));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(y > 1 && tc.gridI[x][y] != -1 && tc.gridI[x][y] == tc.gridI[x][y - 1] && tc.gridI[x][y] == tc.gridI[x][y - 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x, y - 1)); clear.add(new Vector2(x, y - 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(x < 4 && y > 1 && tc.gridI[x][y] != -1 && tc.gridI[x][y] == tc.gridI[x + 1][y - 1] && tc.gridI[x][y] == tc.gridI[x + 2][y - 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y - 1)); clear.add(new Vector2(x + 2, y - 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(x < 4 && y < 13 && tc.gridI[x][y] != -1 && tc.gridI[x][y] == tc.gridI[x + 1][y + 1] && tc.gridI[x][y] == tc.gridI[x + 2][y + 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y + 1)); clear.add(new Vector2(x + 2, y + 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
            }
        }

        if(points > 0) {
            alpha -= 5;
            tc.scorePoint += (points * 2 * (tc.scoreLevel + 1));
            tc.scoreLives++;
            if(tc.scoreLives > (1 + tc.scoreLevel) * 10) {
                tc.scoreLevel++;
                time_break -= (time_break / 10);
            }
            //Command_Collapse();
        }
    }

}
