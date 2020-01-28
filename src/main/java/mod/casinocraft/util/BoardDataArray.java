package mod.casinocraft.util;

import mod.casinocraft.logic.LogicBase;

public interface BoardDataArray {

    LogicBase get();

    Object get(int index);

    void set(int index, int value);

    int size();

}