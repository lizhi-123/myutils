package study.index_enum;

public enum IndexEnum {
    TEMPERATURE(1),PRESSURE(2);


    private int index;

    IndexEnum(int index){
        this.index = index;
    }
    public int index(){
        return index;
    }
}
