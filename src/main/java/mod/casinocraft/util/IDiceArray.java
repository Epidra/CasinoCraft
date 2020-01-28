package mod.casinocraft.util;

public interface IDiceArray {
    Dice get(int index);

    void set(int index, Object value);

    int size();
}