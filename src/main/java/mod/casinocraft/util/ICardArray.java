package mod.casinocraft.util;

public interface ICardArray {
    Card get(int index);

    void set(int index, Object value);

    int size();
}