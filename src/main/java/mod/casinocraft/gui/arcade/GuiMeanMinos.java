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

public class GuiMeanMinos extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiMeanMinos(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_MEANMINOS);
        tc.gridI = new int[6][15];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_UP)    { actionTouch(0); }
        if(keyCode == KEY_DOWN)  { actionTouch(1); }
        if(keyCode == KEY_LEFT)  { actionTouch(2); }
        if(keyCode == KEY_RIGHT) { actionTouch(3); }
        if(keyCode == KEY_M)     { actionTouch(4); }
        if(keyCode == KEY_N)     { actionTouch(5); }
        if(keyCode == KEY_ENTER) { actionTouch(6); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 2) {
            this.fontRenderer.drawString("" + tc.scorePoint, 8+16*11 + 4-24, 8+16*0 + 4+8, 9999999);
            this.fontRenderer.drawString("" + tc.scoreLives, 8+16*11 + 4-24, 8+16*1 + 4+8, 9999999);
            this.fontRenderer.drawString("" + tc.scoreLevel, 8+16*11 + 4-24, 8+16*2 + 4+8, 9999999);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MEANMINOS);
        this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background

        if(tc.turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 15; y++){
                for(int x = 0; x < 6; x++){
                    //drawMino(tc.getValue(y * 10 + x), x, y);
                    if(getValue(y * 6 + x) != -1) this.drawTexturedModalRect(guiLeft+16 + 8+16 + 16*x, guiTop + 8 + 16*y, tc.turnstate >= 4 ? 16*8 : 16*getValue(y * 6 + x), 216, 16, 16);
                }
            }

            this.drawTexturedModalRect(guiLeft+8+16+16 + 16*getValue(200), guiTop+8 + 16*getValue(201), tc.turnstate >= 4 ? 16*8 : 16*getValue(210), 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+8+16+16 + 16*getValue(202), guiTop+8 + 16*getValue(203), tc.turnstate >= 4 ? 16*8 : 16*getValue(211), 216, 16, 16);

            if(getValue(212) > -1) drawTetromino(getValue(212), getValue(213), 8+16*11-24-4, 8+16*4+8);
            if(getValue(214) > -1) drawTetromino(getValue(214), getValue(215), 8+16*11-24-4, 8+16*9+8);

            //this.drawTexturedModalRect(guiLeft+256-20, guiTop+256-20, 0, 32, 16, 16);

            //if(tc.turnstate == 2) tc.update();
        }
    }



    //--------------------CUSTOM--------------------

    private void drawTetromino(int mino0, int mino1, int x, int y) {
        this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino1, 216, 16, 16);
        this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino0, 216, 16, 16);
    }


    public boolean active_hold;

    public int[] container_next    = new int[2];
    public int[] container_hold    = new int[2];
    public int[] container_current = new int[2];

    public double time_last;
    public double time_break;
    public int timer;

    public Vector2[] domino = new Vector2[2];

    public List<Vector2> clear = new ArrayList<Vector2>();

    public int alpha;



    //--------------------CONSTRUCTOR--------------------

    //public TileEntityMeanMinos(){
    //    super(null, null);
    //}
//
    //public TileEntityMeanMinos(TileEntityBoard te, BlockPos bp){
    //    super(te, bp);
    //    gridI = new int[6][15];
    //}
//
    //@Override
    //public String getGuiID() {
    //    return CasinoKeeper.GUIID_MEANMINOS.toString();
    //}
//
    //@Override
    //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
    //    return new ContainerCasino(playerInventory, this.board);
    //}
//
    //@Override
    //public ITextComponent getName() {
    //    return new TextComponentTranslation(CasinoKeeper.GUIID_MEANMINOS.toString(), new Object[0]);
    //}
//
    //@Nullable
    //@Override
    //public ITextComponent getCustomName() {
    //    return new TextComponentTranslation(CasinoKeeper.GUIID_MEANMINOS.toString(), new Object[0]);
    //}



    //--------------------BASIC--------------------

    public void start2(){
        active_hold = true;
        container_next[0] = Domino_Roll();
        container_next[1] = Domino_Roll();
        container_hold[0] = -1;
        container_hold[1] = -1;
        container_current[0] = Domino_Roll();
        container_current[1] = Domino_Roll();
        Domino_Create();
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
        if(action == 0){ Domino_Drop();         } // UP
        if(action == 1){ Domino_Fall();         } // DOWN
        if(action == 2){ Command_Strafe(true);  } // LEFT
        if(action == 3){ Command_Strafe(false); } // RIGHT
        if(action == 4){ Command_Turn(true);    } // ROTATE LEFT
        if(action == 5){ Command_Turn(false);   } // ROTATE RIGHT
        if(action == 6){ Command_Hold();        } // HOLD
    }

    public void update(){
        timer+=15;
        if(alpha == 255) {
            if(timer > time_last + time_break && tc.turnstate == 2) {
                Domino_Fall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 255;
                for(int y = 0; y < 15; y++) {
                    for(int x = 0; x < 10; x++) {
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

        if(index == 200) return domino[0].X;
        if(index == 201) return domino[0].Y;
        if(index == 202) return domino[1].X;
        if(index == 203) return domino[1].Y;
        if(index == 210) return tc.turnstate >= 4 ?  8 : container_current[0];
        if(index == 211) return tc.turnstate >= 4 ?  8 : container_current[1];
        if(index == 212) return tc.turnstate >= 4 ?  8 : container_next[0];
        if(index == 213) return tc.turnstate >= 4 ?  8 : container_next[1];
        if(index == 214) return tc.turnstate >= 4 ? -1 : container_hold[0];
        if(index == 215) return tc.turnstate >= 4 ? -1 : container_hold[1];

        if(index == -1) return tc.scorePoint;
        if(index == -2) return tc.scoreLives;
        if(index == -3) return tc.scoreLevel;
        return inLine(index%6, index/6) && (alpha/75)%2==0 ? 7 : tc.gridI[index % 6][index / 6];
        //return tc.gridI[index % 10][index / 10];
    }



    //--------------------CUSTOM--------------------

    private boolean inLine(int x, int y){
        for(Vector2 v : clear) {
            if(v.matches(x, y)) return true;
        }
        return false;
    }

    private void Domino_Drop() {
        int tempPoint = tc.scorePoint;
        while(tc.scorePoint == tempPoint) {
            Domino_Fall();
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
                    while(y + temp + 1 < 15 && tc.gridI[x][y + temp + 1] == -1) {
                        temp++;
                    }
                    if(temp != 0) {
                        tc.gridI[x][y + temp] = tc.gridI[x][y];
                        tc.gridI[x][y] = -1;
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
            if(domino[0].X > 0) {
                if(domino[1].X > 0) {
                    if(tc.gridI[domino[0].X + dir][domino[0].Y] == -1) {
                        if(tc.gridI[domino[1].X + dir][domino[1].Y] == -1) {
                            domino[0].X = domino[0].X + dir;
                            domino[1].X = domino[1].X + dir;
                        }
                    }
                }
            }
        } else {
            dir = 1;
            if(domino[0].X < 5) {
                if(domino[1].X < 5) {
                    if(tc.gridI[domino[0].X + dir][domino[0].Y] == -1) {
                        if(tc.gridI[domino[1].X + dir][domino[1].Y] == -1) {
                            domino[0].X = domino[0].X + dir;
                            domino[1].X = domino[1].X + dir;
                        }
                    }
                }
            }
        }
    }

    public void Command_Turn(boolean totheleft) {

        int pos = 0; // Position of the rotatable Mino
        if(domino[0].Y > domino[1].Y) pos = 1; // Up
        if(domino[0].X > domino[1].X) pos = 2; // Left
        if(domino[0].Y < domino[1].Y) pos = 3; // Down
        if(domino[0].X < domino[1].X) pos = 4; // Right



        if(totheleft) {
            if(pos == 1) { if(domino[0].X - 1 >=  0 && tc.gridI[domino[0].X - 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X - 1, domino[0].Y    ); } }
            if(pos == 2) { if(domino[0].Y + 1 <= 14 && tc.gridI[domino[0].X][domino[0].Y + 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y + 1); } }
            if(pos == 3) { if(domino[0].X + 1 <=  5 && tc.gridI[domino[0].X + 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X + 1, domino[0].Y    ); } }
            if(pos == 4) { if(domino[0].Y - 1 >=  0 && tc.gridI[domino[0].X][domino[0].Y - 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y - 1); } }
        } else {
            if(pos == 1) { if(domino[0].X + 1 <=  5 && tc.gridI[domino[0].X + 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X + 1, domino[0].Y    ); } }
            if(pos == 2) { if(domino[0].Y - 1 >=  0 && tc.gridI[domino[0].X][domino[0].Y - 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y - 1); } }
            if(pos == 3) { if(domino[0].X - 1 >=  0 && tc.gridI[domino[0].X - 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X - 1, domino[0].Y    ); } }
            if(pos == 4) { if(domino[0].Y + 1 <= 14 && tc.gridI[domino[0].X][domino[0].Y + 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y + 1); } }
        }

    }

    public void Command_Hold() {
        if(active_hold) {
            active_hold = false;
            if(container_hold[0] == -1) {
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_current[0] = container_next[0];
                container_current[1] = container_next[1];
                container_next[0] = Domino_Roll();
                container_next[1] = Domino_Roll();
            } else {
                int[] temp = new int[2];
                temp[0] = container_hold[0];
                temp[1] = container_hold[1];
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_current[0] = temp[0];
                container_current[1] = temp[1];
            }
            Domino_Create();
        }
    }

    private int Domino_Roll() {
        return tc.rand.nextInt(7);
    }

    private void Domino_Create() {
        domino[0] = new Vector2(2, 1);
        domino[1] = new Vector2(2, 0);
    }

    public void Domino_Fall() {
        if(domino[0].Y < 14 && domino[1].Y < 14) {
            if(tc.gridI[domino[0].X][domino[0].Y + 1] == -1 && tc.gridI[domino[1].X][domino[1].Y + 1] == -1) {
                domino[0].Y = domino[0].Y + 1;
                domino[1].Y = domino[1].Y + 1;
            } else {
                Domino_Place();
            }
        } else {
            Domino_Place();
        }
    }

    private void Domino_Place() {
        active_hold = true;
        tc.gridI[domino[0].X][domino[0].Y] = container_current[0];
        tc.gridI[domino[1].X][domino[1].Y] = container_current[1];
        tc.scorePoint += 2 * 2;
        if(domino[1].Y == 0) tc.turnstate = 4;
        container_current[0] = container_next[0];
        container_current[1] = container_next[1];
        container_next[0] = Domino_Roll();
        container_next[1] = Domino_Roll();
        Domino_Create();
        Check_Field();
    }

    List<Vector2> clear_temp = new ArrayList<Vector2>();

    private void Check_Field() {
        int points = 0;
        int bonus = 0;
        clear_temp.clear();
        for(int y = 14; y >= 0; y--) {
            for(int x = 0; x < 6; x++) {
                if(!IsCleared(x, y)) {
                    Pathfinder(x, y);
                    if(clear_temp.size() >= 4) {
                        points += (clear_temp.size() * 10);
                        bonus++;
                        clear.addAll(clear_temp);
                    }
                    clear_temp.clear();
                }
            }
        }

        if(points > 0) {
            alpha -= 5;
            tc.scorePoint += (points * 2 * bonus * (tc.scoreLevel + 1));
            tc.scoreLives++;
            if(tc.scoreLives > (1 + tc.scoreLevel) * 10) {
                tc.scoreLevel++;
                time_break -= (time_break / 10);
            }
            //Command_Collapse();
        }
    }

    private void Pathfinder(int x, int y) {
        clear_temp.add(new Vector2(x, y));
        if(y - 1 >=  0 && tc.gridI[x][y] != -1 && tc.gridI[x][y] == tc.gridI[x    ][y - 1] && !IsClearedTemp(x    , y - 1)) Pathfinder(x    , y - 1);
        if(y + 1 <= 14 && tc.gridI[x][y] != -1 && tc.gridI[x][y] == tc.gridI[x    ][y + 1] && !IsClearedTemp(x    , y + 1)) Pathfinder(x    , y + 1);
        if(x - 1 >=  0 && tc.gridI[x][y] != -1 && tc.gridI[x][y] == tc.gridI[x - 1][y    ] && !IsClearedTemp(x - 1, y    )) Pathfinder(x - 1, y    );
        if(x + 1 <=  5 && tc.gridI[x][y] != -1 && tc.gridI[x][y] == tc.gridI[x + 1][y    ] && !IsClearedTemp(x + 1, y    )) Pathfinder(x + 1, y    );
    }

    private boolean IsClearedTemp(int x, int y) {
        for(int i = 0; i < clear_temp.size(); i++){
            if(clear_temp.get(i).X == x && clear_temp.get(i).Y == y) return true;
        }
        return false;
    }

}
