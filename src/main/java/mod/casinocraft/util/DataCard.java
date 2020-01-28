package mod.casinocraft.util;

public abstract class DataCard {
    private Card lastKnownValue;

    public static DataCard create(final ICardArray array, final int index){
        return new DataCard(){
            public Card get(){
                return array.get(index);
            }
            public void set(Card newValue){
                array.set(index, newValue);
            }
        };
    }

    public static DataCard create(final Card[] array, final int index){
        return new DataCard(){
            public Card get(){
                return array[index];
            }
            public void set(Card newValue){
                array[index] = newValue;
            }
        };
    }

    public static DataCard single(){
        return new DataCard(){
            private Card value;

            public Card get(){
                return this.value;
            }

            public void set(Card newValue){
                this.value = newValue;
            }
        };
    }

    public abstract Card get();

    public abstract void set(Card newValue);

    public boolean isDirty(){
        Card value = this.get();
        boolean flag = value != this.lastKnownValue;
        this.lastKnownValue = value;
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