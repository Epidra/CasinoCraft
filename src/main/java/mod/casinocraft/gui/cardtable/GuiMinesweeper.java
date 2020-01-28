package mod.casinocraft.gui.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
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
import java.util.ArrayList;
import java.util.List;

public class GuiMinesweeper extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiMinesweeper(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_MINESWEEPER);
        tc.gridI = new     int[26][14];
        tc.gridB = new boolean[26][14];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 14; y++){
                for(int x = 0; x < 26; x++){
                    if(mouseRect(16-96 + 16*x, 16 + 16*y, 16, 16, mouseX, mouseY)){ actionTouch(x + y*26); }
                }
            }
        }
        if(tc.turnstate == 3 && mouseButton == 0){
            if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ actionTouch(-1); }
            if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ actionTouch(-2); }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(table == 1) {
            this.fontRenderer.drawString("POINTS",           25, 25, 0);
            this.fontRenderer.drawString("POINTS",           24, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scorePoint, 35, 35, 0);
            this.fontRenderer.drawString("" + tc.scorePoint, 34, 34, 16777215);
            this.fontRenderer.drawString("BOMBS",            205, 25, 0);
            this.fontRenderer.drawString("BOMBS",            205, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scoreLevel*tc.difficulty, 215, 35, 0);
            this.fontRenderer.drawString("" + tc.scoreLevel*tc.difficulty, 214, 34, 16777215);
        } else {
            this.fontRenderer.drawString("POINTS",           24-76-15, 25, 0);
            this.fontRenderer.drawString("POINTS",           24-76-16, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scorePoint, 34-76-15, 35, 0);
            this.fontRenderer.drawString("" + tc.scorePoint, 34-76-16, 34, 16777215);
            this.fontRenderer.drawString("BOMBS",            204+76+17, 25, 0);
            this.fontRenderer.drawString("BOMBS",            204+76+16, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scoreLevel*tc.difficulty, 214+76+17, 35, 0);
            this.fontRenderer.drawString("" + tc.scoreLevel*tc.difficulty, 214+76+16, 34, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        for(int y = 0; y < 14; y++){
            for(int x = 0; x < 26; x++){
                int i = getValue(x + y*26);
                if(getFlag(x + y*26)){
                    this.drawTexturedModalRect(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 112, 216, 16, 16);
                } else {
                    if(i == 9) {
                        this.drawTexturedModalRect(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 192, 216, 16, 16);
                    } else if(i == 10) {
                        this.drawTexturedModalRect(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 208, 216, 16, 16);
                    } else if(i > 0){
                        GlStateManager.color4f(1.0F-(i/10f), 1.0F, 1.0F, 0.5F);
                        this.drawTexturedModalRect(guiLeft-96 + 16 + 16*x, guiTop + 16 + 16*y, 240, 80-16+16*i, 16, 16);
                        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
            }
        }

        if(tc.turnstate == 3){
            drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }
    }



    //--------------------CUSTOM--------------------




    List<Vector2> FieldList = new ArrayList<Vector2>();

    //boolean[][] grid_cover = new boolean[14][14];
    boolean[][] grid_flag  = new boolean[30][14];
    //    int[][] grid_base  = new     int[14][14];

    int bombs;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityMinesweeper(){
   //    super(null, null);
   //}

   //public TileEntityMinesweeper(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new     int[26][14];
   //    gridB = new boolean[26][14];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_MINESWEEPER.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_MINESWEEPER.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_MINESWEEPER.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        bombs = 0;
        tc.selector = new Vector2(5, 5);
        FieldList.clear();
        tc.scoreLevel = 1;
        Create_Field();
    }

    public void Restart() {
        tc.turnstate = 2;
        FieldList.clear();
        tc.scoreLevel++;
        Create_Field();
    }

    public void actionTouch(int action){
        if(action == -1) {
            Restart();
        } else if(action == -2) {
            tc.turnstate = 4;
        } else {
            tc.selector = new Vector2(action%26, action/26);
            Command_Grid_Enter();
        }
    }

    public void update(){

    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        return tc.gridI[index%26][index/26];
    }

    public boolean getFlag(int index){
        return tc.gridB[index%26][index/26];
    }



    //--------------------CUSTOM--------------------

    private void Create_Field() {
        for(int i = 0; i < 14; i++) {
            for(int j = 0; j < 26; j++) {
                tc.gridI[j][i] = 0;
                tc.gridB[j][i] = false;
                grid_flag[j][i] = false;
            }
        }
        bombs = tc.scoreLevel*tc.difficulty;
        int max = tc.difficulty == 2 ? 14*14 : 14*26;
        max -= max/4;
        if(bombs > max) bombs = max;
        for(int i = 0; i < bombs; i++) {
            int x = tc.rand.nextInt(tc.difficulty == 2 ? 14 : 26) + (tc.difficulty == 2 ? 6 : 0);
            int y = tc.rand.nextInt(14);
            if(tc.gridI[x][y] != 9) {
                tc.gridI[x][y] = 9;
            } else {
                i--;
            }
        }
        for(int y = 0; y < 14; y++) {
            for(int x = tc.difficulty == 2 ? 6 : 0; x < (tc.difficulty == 2 ? 20 : 26); x++) {
                tc.gridB[x][y] = true;
                if(tc.gridI[x][y] != 9) {
                    int count = 0;
                    if(x >  0 && y >  0) if(tc.gridI[x - 1][y - 1] == 9) count++; // -X -Y
                    if(          y >  0) if(tc.gridI[x    ][y - 1] == 9) count++; //    -Y
                    if(x < 25 && y >  0) if(tc.gridI[x + 1][y - 1] == 9) count++; // +X -Y
                    if(x < 25          ) if(tc.gridI[x + 1][y    ] == 9) count++; // +X
                    if(x < 25 && y < 13) if(tc.gridI[x + 1][y + 1] == 9) count++; // +X +Y
                    if(          y < 13) if(tc.gridI[x    ][y + 1] == 9) count++; //    +Y
                    if(x >  0 && y < 13) if(tc.gridI[x - 1][y + 1] == 9) count++; // -X +Y
                    if(x >  0          ) if(tc.gridI[x - 1][y    ] == 9) count++; // -X
                    tc.gridI[x][y] = count;
                }
            }
        }
        if(tc.difficulty == 2) {
            for(int y = 0; y < 6; y++) {
                for(int x = 0; x < 6; x++) {

                }
            }
        }
    }

    private void Command_Grid_Enter() {
        if(tc.gridB[tc.selector.X][tc.selector.Y]) {
            //if(active_flag) {
            //    if(grid_flag[(int)tc.selector.X][(int)tc.selector.Y]) {
            //        grid_flag[(int)tc.selector.X][(int)tc.selector.Y] = false;
            //    } else {
            //        grid_flag[(int)tc.selector.X][(int)tc.selector.Y] = true;
            //    }
            //} else {
            tc.gridB[tc.selector.X][tc.selector.Y] = false;
            if(tc.gridI[tc.selector.X][tc.selector.Y] == 9) {
                tc.gridI[tc.selector.X][tc.selector.Y] = 10;
                Uncover_Bombs();
                tc.turnstate = 4;
                tc.scorePoint /= 2;
            } else {
                if(tc.gridI[tc.selector.X][tc.selector.Y] == 0) {
                    FieldList.add(tc.selector);
                    Uncover_Tiles();
                }
                boolean temp = false;
                for(int i = 0; i < 14; i++) {
                    for(int j = 0; j < 26; j++) {
                        if(tc.gridI[j][i] != 9) {
                            if(tc.gridB[j][i]) {
                                temp = true;
                            }
                        }
                    }
                }
                if(!temp) {
                    tc.turnstate = 3;
                    tc.scorePoint += bombs;
                }
            }
            //}
        }
    }

    private void Uncover_Tiles() {
        while(FieldList.size() > 0) {
            boolean temp0 = false;
            for(Vector2 v : FieldList) {
                if(v.X >  0 && v.Y >  0) { if(tc.gridB[v.X - 1][v.Y - 1] && !grid_flag[v.X - 1][v.Y - 1]) { tc.gridB[v.X - 1][v.Y - 1] = false; if(tc.gridI[v.X - 1][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y - 1)); temp0 = true; break; } } } } } // -X -Y
                if(            v.Y >  0) { if(tc.gridB[v.X    ][v.Y - 1] && !grid_flag[v.X    ][v.Y - 1]) { tc.gridB[v.X    ][v.Y - 1] = false; if(tc.gridI[v.X    ][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y - 1)); temp0 = true; break; } } } } } //    -Y
                if(v.X < 25 && v.Y >  0) { if(tc.gridB[v.X + 1][v.Y - 1] && !grid_flag[v.X + 1][v.Y - 1]) { tc.gridB[v.X + 1][v.Y - 1] = false; if(tc.gridI[v.X + 1][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y - 1)); temp0 = true; break; } } } } } // +X -Y
                if(v.X < 25            ) { if(tc.gridB[v.X + 1][v.Y    ] && !grid_flag[v.X + 1][v.Y    ]) { tc.gridB[v.X + 1][v.Y    ] = false; if(tc.gridI[v.X + 1][v.Y    ] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y    )); temp0 = true; break; } } } } } // +X
                if(v.X < 25 && v.Y < 13) { if(tc.gridB[v.X + 1][v.Y + 1] && !grid_flag[v.X + 1][v.Y + 1]) { tc.gridB[v.X + 1][v.Y + 1] = false; if(tc.gridI[v.X + 1][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y + 1)); temp0 = true; break; } } } } } // +X +Y
                if(            v.Y < 13) { if(tc.gridB[v.X    ][v.Y + 1] && !grid_flag[v.X    ][v.Y + 1]) { tc.gridB[v.X    ][v.Y + 1] = false; if(tc.gridI[v.X    ][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y + 1)); temp0 = true; break; } } } } } //    +Y
                if(v.X >  0 && v.Y < 13) { if(tc.gridB[v.X - 1][v.Y + 1] && !grid_flag[v.X - 1][v.Y + 1]) { tc.gridB[v.X - 1][v.Y + 1] = false; if(tc.gridI[v.X - 1][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y + 1)); temp0 = true; break; } } } } } // -X +Y
                if(v.X >  0            ) { if(tc.gridB[v.X - 1][v.Y    ] && !grid_flag[v.X - 1][v.Y    ]) { tc.gridB[v.X - 1][v.Y    ] = false; if(tc.gridI[v.X - 1][v.Y    ] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y    )); temp0 = true; break; } } } } } // -X
                break;
            }
            if(!temp0) FieldList.remove(0);
        }
    }

    private void Uncover_Bombs() {
        for(int y = 0; y < 14; y++) {
            for(int x = 0; x < 26; x++) {
                if(tc.gridI[x][y] == 9) {
                    tc.gridB[x][y] = false;
                }
            }
        }
    }

}
