package mod.casinocraft.gui.arcade;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
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

public class Gui2048 extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public Gui2048(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_2048);
        tc.gridI = new     int[4][4];
        tc.gridB = new boolean[4][4];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(!getFlag(17)) {
            if(keyCode == KEY_UP)    { actionTouch(0); }
            if(keyCode == KEY_DOWN)  { actionTouch(1); }
            if(keyCode == KEY_LEFT)  { actionTouch(2); }
            if(keyCode == KEY_RIGHT) { actionTouch(3); }
        }
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

        if(tc.turnstate >= 2){
            if(tc.turnstate == 5) GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_2048);
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){
                    //drawMino(tileCasino.getValue(y * 4 + x), x, y);
                    if(getValue(y*4+x) != 0){
                        int shiftX = getFlag(y*4+x) ? getValue(17) == 4 ? getValue(16) : getValue(17) == 3 ? -getValue(16) : 0 : 0;
                        int shiftY = getFlag(y*4+x) ? getValue(17) == 2 ? getValue(16) : getValue(17) == 1 ? -getValue(16) : 0 : 0;
                        this.drawTexturedModalRect(guiLeft + 48*x+32 + shiftX, guiTop + 48*y+16 + shiftY, Get_Spritesheet(true, getValue(y*4+x))*48, Get_Spritesheet(false, getValue(y*4+x))*48, 48, 48);
                    }
                }
            }
            if(tc.turnstate == 5) GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }



    //--------------------CUSTOM--------------------

    private int Get_Spritesheet(boolean x, int id) {
        if(x) {
            if(id == 1) return 0;
            if(id == 2) return 1;
            if(id == 3) return 2;
            if(id == 4) return 3;
            if(id == 5) return 0;
            if(id == 6) return 1;
            if(id == 7) return 2;
            if(id == 8) return 3;
            if(id == 9) return 0;
            if(id == 10) return 1;
            if(id == 11) return 2;
            if(id == 12) return 3;
        } else {
            if(id == 1) return 0;
            if(id == 2) return 0;
            if(id == 3) return 0;
            if(id == 4) return 0;
            if(id == 5) return 1;
            if(id == 6) return 1;
            if(id == 7) return 1;
            if(id == 8) return 1;
            if(id == 9) return 2;
            if(id == 10) return 2;
            if(id == 11) return 2;
            if(id == 12) return 2;
        }
        return 0;
    }


    boolean placing;
    boolean active_timer;
    int timer;
    int direction; // 0 - null, 1 - up, 2 - down, 3 - left, 4 - right



    //--------------------CONSTRUCTOR--------------------

   //public TileEntity2048(){
   //    super(null, null);
   //}

   //public TileEntity2048(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new     int[4][4];
   //    gridB = new boolean[4][4];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_2048.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_2048.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_2048.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                tc.gridI[x][y] = 0;
                tc.gridB[x][y] = false;
            }
        }
        placing = false;
        tc.gridI[0][0] = tc.rand.nextInt(1) + 1;
        tc.gridI[3][0] = tc.rand.nextInt(1) + 1;
        tc.gridI[0][3] = tc.rand.nextInt(1) + 1;
        tc.gridI[3][3] = tc.rand.nextInt(1) + 1;
        active_timer = false;
        timer = 0;
        direction = 0;
    }

    public void actionTouch(int action){
        if(action == 0) { Move(1); }
        if(action == 1) { Move(2); }
        if(action == 2) { Move(3); }
        if(action == 3) { Move(4); }
    }

    public void update(){
        if(active_timer) {
            timer = timer + 6;
            if(timer == 48) {
                active_timer = false;
                timer = 0;
                Change();
                Move(direction);
                placing = true;
            }
        } else {
            if(placing) {
                Place();
                placing = false;
            }
            direction = 0;
        }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        if(index == 16) return timer;
        if(index == 17) return direction;
        return tc.gridI[index%4][index/4];
    }

    public boolean getFlag(int index){
        if(index == 16) return placing;
        if(index == 17) return active_timer;
        return tc.gridB[index%4][index/4];
    }



    //--------------------CUSTOM--------------------

    private void Move(int s) {
        if(s == 1) { // up
            for(int y = 1; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(tc.gridI[x][y] != 0) {
                        if(tc.gridI[x][y - 1] == 0 || tc.gridI[x][y - 1] == tc.gridI[x][y]) {
                            tc.gridB[x][y] = true;
                            active_timer = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 2) { // down
            for(int y = 2; y > -1; y--) {
                for(int x = 3; x > -1; x--) {
                    if(tc.gridI[x][y] != 0) {
                        if(tc.gridI[x][y + 1] == 0 || tc.gridI[x][y + 1] == tc.gridI[x][y]) {
                            tc.gridB[x][y] = true;
                            active_timer = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 3) { // left
            for(int x = 1; x < 4; x++) {
                for(int y = 0; y < 4; y++) {
                    if(tc.gridI[x][y] != 0) {
                        if(tc.gridI[x - 1][y] == 0 || tc.gridI[x - 1][y] == tc.gridI[x][y]) {
                            tc.gridB[x][y] = true;
                            active_timer = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 4) { // right
            for(int x = 2; x > -1; x--) {
                for(int y = 3; y > -1; y--) {
                    if(tc.gridI[x][y] != 0) {
                        if(tc.gridI[x + 1][y] == 0 || tc.gridI[x + 1][y] == tc.gridI[x][y]) {
                            tc.gridB[x][y] = true;
                            active_timer = true;
                            direction = s;
                        }
                    }
                }
            }
        }
    }

    private void Change() {
        if(direction == 1) { // up
            for(int y = 1; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(tc.gridB[x][y]) {
                        if(tc.gridI[x][y - 1] == 0) {
                            tc.gridI[x][y - 1] = tc.gridI[x][y];
                        } else {
                            tc.gridI[x][y - 1]++;
                            Add_Points(tc.gridI[x][y - 1]);
                        }
                        tc.gridI[x][y] = 0;
                    }
                }
            }
        }
        if(direction == 2) { // down
            for(int y = 2; y > -1; y--) {
                for(int x = 3; x > -1; x--) {
                    if(tc.gridB[x][y]) {
                        if(tc.gridI[x][y + 1] == 0) {
                            tc.gridI[x][y + 1] = tc.gridI[x][y];
                        } else {
                            tc.gridI[x][y + 1]++;
                            Add_Points(tc.gridI[x][y + 1]);
                        }
                        tc.gridI[x][y] = 0;
                    }
                }
            }
        }
        if(direction == 3) { // left
            for(int x = 1; x < 4; x++) {
                for(int y = 0; y < 4; y++) {
                    if(tc.gridB[x][y]) {
                        if(tc.gridI[x - 1][y] == 0) {
                            tc.gridI[x - 1][y] = tc.gridI[x][y];
                        } else {
                            tc.gridI[x - 1][y]++;
                            Add_Points(tc.gridI[x - 1][y]);
                        }
                        tc.gridI[x][y] = 0;
                    }
                }
            }
        }
        if(direction == 4) { // right
            for(int x = 2; x > -1; x--) {
                for(int y = 3; y > -1; y--) {
                    if(tc.gridB[x][y]) {
                        if(tc.gridI[x + 1][y] == 0) {
                            tc.gridI[x + 1][y] = tc.gridI[x][y];
                        } else {
                            tc.gridI[x + 1][y]++;
                            Add_Points(tc.gridI[x + 1][y]);
                        }
                        tc.gridI[x][y] = 0;
                    }
                }
            }
        }
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                tc.gridB[x][y] = false;
            }
        }
    }

    private void Place() {
        for(int i = 0; i < 24; i++) {
            int x = tc.rand.nextInt(4);
            int y = tc.rand.nextInt(4);
            if(tc.gridI[x][y] == 0) {
                tc.gridI[x][y] = 1;
                break;
            }
        }
        Check();
    }

    private void Check() {
        boolean b = false;
        for(int y = 1; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                if(tc.gridI[x][y] != 0) {
                    if(tc.gridI[x][y - 1] == 0 || tc.gridI[x][y - 1] == tc.gridI[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int y = 2; y > -1; y--) {
            for(int x = 3; x > -1; x--) {
                if(tc.gridI[x][y] != 0) {
                    if(tc.gridI[x][y + 1] == 0 || tc.gridI[x][y + 1] == tc.gridI[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int x = 1; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(tc.gridI[x][y] != 0) {
                    if(tc.gridI[x - 1][y] == 0 || tc.gridI[x - 1][y] == tc.gridI[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int x = 2; x > -1; x--) {
            for(int y = 3; y > -1; y--) {
                if(tc.gridI[x][y] != 0) {
                    if(tc.gridI[x + 1][y] == 0 || tc.gridI[x + 1][y] == tc.gridI[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        if(!b) {
            tc.turnstate = 4;
        }
    }

    private int Get_Direction(boolean horizontal, int x, int y) {
        if(direction == 0)
            return 0;
        if( horizontal && direction == 3) if(tc.gridB[x][y]) return -timer; // left
        if( horizontal && direction == 4) if(tc.gridB[x][y]) return  timer; // right
        if(!horizontal && direction == 1) if(tc.gridB[x][y]) return -timer; // up
        if(!horizontal && direction == 2) if(tc.gridB[x][y]) return  timer; // down
        return 0;
    }

    private void Add_Points(int i) {
        if(i ==  1) tc.scorePoint +=     2;
        if(i ==  2) tc.scorePoint +=     4;
        if(i ==  3) tc.scorePoint +=     8;
        if(i ==  4) tc.scorePoint +=    16;
        if(i ==  5) tc.scorePoint +=    32;
        if(i ==  6) tc.scorePoint +=    64;
        if(i ==  7) tc.scorePoint +=   128;
        if(i ==  8) tc.scorePoint +=   256;
        if(i ==  9) tc.scorePoint +=   512;
        if(i == 10) tc.scorePoint +=  1024;
        if(i == 11) tc.scorePoint +=  2048;
        if(i == 12) tc.scorePoint +=  4096;
        if(i == 13) tc.scorePoint +=  8192;
        if(i == 14) tc.scorePoint += 16384;
        if(i == 15) tc.scorePoint += 32768;
        if(i == 16) tc.scorePoint += 65536;
    }

}
