package mod.casinocraft.logic.other;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.CompoundNBT;

public class LogicSlotGame extends LogicBase {

    public LogicSlotGame() {
        super(false, "x_slotgame");
    }

    @Override
    public void actionTouch(int action) {

    }

    @Override
    public void updateMotion() {

    }

    @Override
    public void updateLogic() {

    }

    @Override
    public void start2() {

    }

    @Override
    public void load2(CompoundNBT compound){

    }

    @Override
    public CompoundNBT save2(CompoundNBT compound){
        return compound;
    }
}

//public class LogicSlotMachine extends LogicBase {
//
//    int slotID = -1; // to define what wheels are displayed
//    int multiplier;
//    int wheel; // What wheel will be halted next
//
//    int[] wheelPos   = new int[3];
//    int[] wheelSpeed = new int[3];
//
//    float[] lines = new float[5];
//
//
//
//    //--------------------CONSTRUCTOR--------------------
//
//    public LogicSlotMachine(){
//        super(false, false, false, false, 9, 3);
//    }
//
//
//
//    //--------------------BASIC--------------------
//
//    public void start2(){
//        if(slotID == -1) {
//            slotID = rand.nextInt(6);
//        } else {
//            slotID = (slotID + 1) % 6;
//        }
//        Fill_Grid();
//        wheelPos[0] = rand.nextInt(9) * 100;
//        wheelPos[1] = rand.nextInt(9) * 100;
//        wheelPos[2] = rand.nextInt(9) * 100;
//        wheelSpeed[0] = 1 + rand.nextInt(5);
//        wheelSpeed[1] = 1 + rand.nextInt(5);
//        wheelSpeed[2] = 1 + rand.nextInt(5);
//        multiplier = 1;
//        wheel = 0;
//        lines[0] = 0.00f;
//        lines[1] = 0.00f;
//        lines[2] = 0.00f;
//        lines[3] = 0.00f;
//        lines[4] = 0.00f;
//    }
//
//    public void actionTouch(int action){
//        if(action == -1){
//            if(turnstate == 2){
//                turnstate = 3;
//            } else if(turnstate == 3){
//                Spin();
//            }
//        }
//    }
//
//    public void update(){
//        if(turnstate == 3) {
//            for(int i = 0; i < 3; i++) {
//                if(wheel <= i) {
//                    wheelPos[i] = (wheelPos[i] + wheelSpeed[i]) % 900;
//                } else if(wheelSpeed[i] != 0){
//
//                    if(wheelPos[i] % 100 >= 35 && wheelPos[i] % 100 <= 65) {
//                        wheelPos[i] = (wheelPos[i] / 100) * 100 + 50;
//                        wheelSpeed[i] = 0;
//                    } else {
//                        wheelPos[i] = (wheelPos[i] + (wheelSpeed[i] / 2)) % 900;
//                    }
//                }
//            }
//        }
//        if(turnstate == 3 && wheelSpeed[2] == 0) {
//            Result();
//        }
//        if(turnstate == 4) {
//            //GameOver(gameTime.TotalGameTime.TotalSeconds);
//        }
//    }
//
//    @Override
//    public void update2() {
//
//    }
//
//
//    //--------------------GETTER--------------------
//
//    public int getValue(int index){
//        if(index == -1) return wheelPos[0];
//        if(index == -2) return wheelPos[1];
//        if(index == -3) return wheelPos[2];
//        return gridI[index%9][index/9];
//    }
//
//
//
//    //--------------------CUSTOM--------------------
//
//    private void Fill_Grid() {
//        for(int y = 0; y < 3; y++) {
//            int[] gridtemp = Get_Grid((slotID + y) % 9);
//            for(int x = 0; x < 9; x++) {
//                gridI[x][y] = gridtemp[x];
//            }
//        }
//    }
//
//    private int[] Get_Grid(int id) {
//        switch(id) {
//            case 0: return new int[] { 1, 0, 5, 4, 3, 2, 0, 5, 4 };
//            case 1: return new int[] { 1, 2, 0, 5, 4, 3, 2, 0, 5 };
//            case 2: return new int[] { 1, 3, 2, 0, 5, 4, 3, 2, 0 };
//            case 3: return new int[] { 1, 4, 3, 2, 0, 5, 4, 3, 2 };
//            case 4: return new int[] { 1, 5, 4, 3, 2, 0, 5, 4, 3 };
//            case 5: return new int[] { 1, 0, 5, 4, 3, 2, 0, 5, 4 };
//            case 6: return new int[] { 1, 2, 0, 5, 4, 3, 2, 0, 5 };
//            case 7: return new int[] { 1, 3, 2, 0, 5, 4, 3, 2, 0 };
//            case 8: return new int[] { 1, 4, 3, 2, 0, 5, 4, 3, 2 };
//            case 9: return new int[] { 1, 5, 4, 3, 2, 0, 5, 4, 3 };
//        }
//        return null;
//    }
//
//    private void Spin() {
//        if(wheel < 3) {
//            wheel++;
//        }
//    }
//
//    private void Result() {
//        if(multiplier >= 1) {
//            if(grid[(wheelPos[0] / 100 + 2) % 9][0] == 0) { coins_plus += (bet / multiplier); lines[0] = 0.15f; }
//            if(grid[(wheelPos[1] / 100 + 2) % 9][1] == 0) { coins_plus += (bet / multiplier); lines[0] = 0.15f; }
//            if(grid[(wheelPos[2] / 100 + 2) % 9][2] == 0) { coins_plus += (bet / multiplier); lines[0] = 0.15f; }
//            if(grid[(wheelPos[0] / 100 + 2) % 9][0] == grid[(wheelPos[1] / 100 + 2) % 9, 1] && grid[(wheelPos[0] / 100 + 2) % 9, 0] == grid[(wheelPos[2] / 100 + 2) % 9, 2]) {
//                coins_plus += grid[(wheelPos[0] / 100 + 2) % 9, 0] == 1 ? 7 : (bet * grid[(wheelPos[0] / 100 + 2) % 9, 0]);
//                lines[0] = 0.65f;
//            }
//        }
//        if(multiplier >= 2) {
//            if(grid[(wheelPos[0] / 100 + 1) % 9][0] == 0) { coins_plus += (bet / multiplier); lines[1] = 0.15f; }
//            if(grid[(wheelPos[1] / 100 + 1) % 9][1] == 0) { coins_plus += (bet / multiplier); lines[1] = 0.15f; }
//            if(grid[(wheelPos[2] / 100 + 1) % 9][2] == 0) { coins_plus += (bet / multiplier); lines[1] = 0.15f; }
//            if(grid[(wheelPos[0] / 100 + 1) % 9][0] == grid[(wheelPos[1] / 100 + 1) % 9, 1] && grid[(wheelPos[0] / 100 + 1) % 9, 0] == grid[(wheelPos[2] / 100 + 1) % 9, 2]) {
//                coins_plus += grid[(wheelPos[0] / 100 + 1) % 9, 0] == 1 ? 7 : (bet * grid[(wheelPos[0] / 100 + 1) % 9, 0]);
//                lines[1] = 0.65f;
//            }
//        }
//        if(multiplier >= 3) {
//            if(grid[(wheelPos[0] / 100 + 3) % 9][0] == 0) { coins_plus += (bet / multiplier); lines[2] = 0.15f; }
//            if(grid[(wheelPos[1] / 100 + 3) % 9][1] == 0) { coins_plus += (bet / multiplier); lines[2] = 0.15f; }
//            if(grid[(wheelPos[2] / 100 + 3) % 9][2] == 0) { coins_plus += (bet / multiplier); lines[2] = 0.15f; }
//            if(grid[(wheelPos[0] / 100 + 3) % 9][0] == grid[(wheelPos[1] / 100 + 3) % 9, 1] && grid[(wheelPos[0] / 100 + 3) % 9, 0] == grid[(wheelPos[2] / 100 + 3) % 9, 2]) {
//                coins_plus += grid[(wheelPos[0] / 100 + 3) % 9, 0] == 1 ? 7 : (bet * grid[(wheelPos[0] / 100 + 3) % 9, 0]);
//                lines[2] = 0.65f;
//            }
//        }
//        if(multiplier >= 4) {
//            if(grid[(wheelPos[0] / 100 + 1) % 9][0] == 0) { coins_plus += (bet / multiplier); lines[3] = 0.15f; }
//            if(grid[(wheelPos[1] / 100 + 2) % 9][1] == 0) { coins_plus += (bet / multiplier); lines[3] = 0.15f; }
//            if(grid[(wheelPos[2] / 100 + 3) % 9][2] == 0) { coins_plus += (bet / multiplier); lines[3] = 0.15f; }
//            if(grid[(wheelPos[0] / 100 + 1) % 9][0] == grid[(wheelPos[1] / 100 + 2) % 9, 1] && grid[(wheelPos[0] / 100 + 1) % 9, 0] == grid[(wheelPos[2] / 100 + 3) % 9, 2]) {
//                coins_plus += grid[(wheelPos[0] / 100 + 1) % 9, 0] == 1 ? 7 : (bet * grid[(wheelPos[0] / 100 + 1) % 9, 0]);
//                lines[3] = 0.65f;
//            }
//        }
//        if(multiplier >= 5) {
//            if(grid[(wheelPos[0] / 100 + 3) % 9][0] == 0) { coins_plus += (bet / multiplier); lines[4] = 0.15f; }
//            if(grid[(wheelPos[1] / 100 + 2) % 9][1] == 0) { coins_plus += (bet / multiplier); lines[4] = 0.15f; }
//            if(grid[(wheelPos[2] / 100 + 1) % 9][2] == 0) { coins_plus += (bet / multiplier); lines[4] = 0.15f; }
//            if(grid[(wheelPos[0] / 100 + 3) % 9][0] == grid[(wheelPos[1] / 100 + 2) % 9, 1] && grid[(wheelPos[0] / 100 + 3) % 9, 0] == grid[(wheelPos[2] / 100 + 1) % 9, 2]) {
//                coins_plus += grid[(wheelPos[0] / 100 + 2) % 9, 0] == 1 ? 7 : (bet * grid[(wheelPos[0] / 100 + 2) % 9, 0]);
//                lines[4] = 0.65f;
//            }
//        }
//        turnstate = 4;
//    }
//
//}
