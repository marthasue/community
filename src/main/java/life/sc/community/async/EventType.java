package life.sc.community.async;

/**
 * 事件
 */
public enum EventType {

    LIKE(0),
    COMMENT(1);

    private int value;
    EventType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
