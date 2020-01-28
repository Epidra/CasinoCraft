package mod.casinocraft.util;

public interface IEntityArray {
    Entity get(int index);

    void set(int index, Object value);

    int size();
}