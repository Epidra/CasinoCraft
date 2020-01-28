package mod.casinocraft.gui.cardtable;

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

public class GuiSudoku extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSudoku(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_SUDOKU);
        tc.gridI = new int[9][9];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 9; x++) {
                    if(mouseRect(20 + 24*x, 20 + 24*y, 24, 24, mouseX, mouseY)){ actionTouch(100 + y*9 + x); }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_1) { actionTouch(1); }
        if(keyCode == KEY_2) { actionTouch(2); }
        if(keyCode == KEY_3) { actionTouch(3); }
        if(keyCode == KEY_4) { actionTouch(4); }
        if(keyCode == KEY_5) { actionTouch(5); }
        if(keyCode == KEY_6) { actionTouch(6); }
        if(keyCode == KEY_7) { actionTouch(7); }
        if(keyCode == KEY_8) { actionTouch(8); }
        if(keyCode == KEY_9) { actionTouch(9); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SUDOKU);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);

        this.drawTexturedModalRect(guiLeft+20 + 24*getValue(-1), guiTop+20 + 24*getValue(-2), 0, 232, 24, 24);

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(getValue(y*9+x+100) != 0) {
                    if(getValue(y*9+x) > 0) {
                        GlStateManager.color4f(0.25F, 0.25F, 0.25F, 1.0F);
                        this.drawTexturedModalRect(guiLeft+20+4 + 24*x, guiTop+20+4 + 24*y, 240, 64 + 16*getValue(y*9+x), 16, 16);
                        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                } else {
                    if(getValue(y*9+x) > 0) {
                        this.drawTexturedModalRect(guiLeft+20+4 + 24*x, guiTop+20+4 + 24*y, 240, 64 + 16*getValue(y*9+x), 16, 16);
                    }
                }
            }
        }
    }



    //--------------------CUSTOM--------------------




    //int[][] grid        = new int[9][9];
    int[][] grid_mirror = new int[9][9];

    boolean match;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntitySudoku(){
   //    super(null, null);
   //}

   //public TileEntitySudoku(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new int[9][9];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_SUDOKU.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SUDOKU.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SUDOKU.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        match = false;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                tc.gridI[x][y] = 0;
            }
        }

        int r = tc.rand.nextInt(9)+1;
        tc.gridI[4][4] = r;
        grid_mirror[4][4] = r;
        Generate_Square(5 - tc.difficulty, 3, 0);
        Generate_Square(5 - tc.difficulty, 0, 3);
        Generate_Square(5 - tc.difficulty, 6, 3);
        Generate_Square(5 - tc.difficulty, 3, 6);

        Generate_Square(5 - tc.difficulty, 0, 0);
        Generate_Square(5 - tc.difficulty, 6, 0);
        Generate_Square(5 - tc.difficulty, 0, 6);
        Generate_Square(5 - tc.difficulty, 6, 6);
    }

    public void actionTouch(int action){
        if(action >= 100){
            int i = action - 100;
            tc.selector.set(i%9, i/9);
        } else {
            tc.gridI[tc.selector.X][tc.selector.Y] = action;
            Check();
        }
    }

    public void update(){
        if(match && tc.turnstate < 4) {
            tc.turnstate = 4;
        }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        if(index == -1) return tc.selector.X;
        if(index == -2) return tc.selector.Y;
        if(index >= 100){ int i = index - 100;
            return grid_mirror[i%9][i/9];
        } else {
            return tc.gridI[index%9][index/9];
        }
    }



    //--------------------CUSTOM--------------------

    private void Generate_Square(int count, int xi, int yi) {
        int index = 0;
        while(index < count) {
            int x = tc.rand.nextInt(3);
            int y = tc.rand.nextInt(3);
            int i = tc.rand.nextInt(9)+1;
            if(tc.gridI[0][yi + y] != i && tc.gridI[1][yi + y] != i && tc.gridI[2][yi + y] != i && tc.gridI[3][yi + y] != i && tc.gridI[4][yi + y] != i && tc.gridI[5][yi + y] != i && tc.gridI[6][yi + y] != i && tc.gridI[7][yi + y] != i && tc.gridI[8][yi + y] != i) {
                if(tc.gridI[xi + x][0] != i && tc.gridI[xi + x][1] != i && tc.gridI[xi + x][2] != i && tc.gridI[xi + x][3] != i && tc.gridI[xi + x][4] != i && tc.gridI[xi + x][5] != i && tc.gridI[xi + x][6] != i && tc.gridI[xi + x][7] != i && tc.gridI[xi + x][8] != i) {
                    if(tc.gridI[xi + 0][yi + 0] != i && tc.gridI[xi + 1][yi + 0] != i && tc.gridI[xi + 2][yi + 0] != i && tc.gridI[xi + 0][yi + 1] != i && tc.gridI[xi + 1][yi + 1] != i && tc.gridI[xi + 2][yi + 1] != i && tc.gridI[xi + 0][yi + 2] != i && tc.gridI[xi + 1][yi + 2] != i && tc.gridI[xi + 2][yi + 2] != i) {
                        tc.gridI[xi + x][yi + y] = i;
                        grid_mirror[xi + x][yi + y] = i;
                        index++;
                    }
                }
            }
        }
    }

    private void Check() {
        boolean[] match_vert = new boolean[9];
        boolean[] match_hori = new boolean[9];
        boolean[] match_cube = new boolean[9];
        int[] n = new int[10];
        for(int i = 0; i < 9; i++) { n[i] = 0; }

        for(int y = 0; y < 9; y++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int x = 0; x < 9; x++) {
                n[tc.gridI[x][y]]++;
            }
            match_vert[y] = n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1;
        }

        for(int x = 0; x < 9; x++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int y = 0; y < 9; y++) {
                n[tc.gridI[x][y]]++;
            }
            match_hori[x] = n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1;
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                for(int i = 0; i < 9; i++) { n[i] = 0; }
                for(int yi = y * 3; yi < y * 3 + 3; yi++) {
                    for(int xi = x * 3; xi < x * 3 + 3; xi++) {
                        n[tc.gridI[xi][yi]]++;
                    }
                }
                match_cube[y * 3 + x] = false;
                if(n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1) match_hori[y * 3 + x] = true;
            }
        }

        match = true;
        for(int i = 0; i < 9; i++) {
            if(!match_vert[i]) match = false;
            if(!match_hori[i]) match = false;
            if(!match_cube[i]) match = false;
        }
    }

}
