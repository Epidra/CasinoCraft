package mod.casinocraft.util;

public abstract class DataDice {
    private Dice lastKnownValue;

    public static DataDice create(final IDiceArray array, final int index){
        return new DataDice(){
            public Dice get(){
                return array.get(index);
            }
            public void set(Dice newValue){
                array.set(index, newValue);
            }
        };
    }

    public static DataDice create(final Dice[] array, final int index){
        return new DataDice(){
            public Dice get(){
                return array[index];
            }
            public void set(Dice newValue){
                array[index] = newValue;
            }
        };
    }

    public static DataDice single(){
        return new DataDice(){
            private Dice value;

            public Dice get(){
                return this.value;
            }

            public void set(Dice newValue){
                this.value = newValue;
            }
        };
    }

    public abstract Dice get();

    public abstract void set(Dice newValue);

    public boolean isDirty(){
        Dice o = this.get();
        boolean flag = o != this.lastKnownValue;
        this.lastKnownValue = o;
        return flag;
    }

}

/*
public abstract class IntReferenceHolder {

   public abstract int get();

   public abstract void set(int p_221494_1_);

   public boolean isDirty() {
      int i = this.get();
      boolean flag = i != this.lastKnownValue;
      this.lastKnownValue = i;
      return flag;
   }
}
 */