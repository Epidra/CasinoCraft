package mod.casinocraft.util;

public abstract class DataEntity {
    private Entity lastKnownValue;

    public static DataEntity create(final IEntityArray array, final int index){
        return new DataEntity(){
            public Entity get(){
                return array.get(index);
            }
            public void set(Entity newValue){
                array.set(index, newValue);
            }
        };
    }

    public static DataEntity create(final Entity[] array, final int index){
        return new DataEntity(){
            public Entity get(){
                return array[index];
            }
            public void set(Entity newValue){
                array[index] = newValue;
            }
        };
    }

    public static DataEntity single(){
        return new DataEntity(){
            private Entity value;

            public Entity get(){
                return this.value;
            }

            public void set(Entity newValue){
                this.value = newValue;
            }
        };
    }

    public abstract Entity get();

    public abstract void set(Entity newValue);

    public boolean isDirty(){
        Entity o = this.get();
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