package life.sc.community.async;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EventModel {
    //之前定义的事件类型
    private EventType type;
    //触发者的id
    private Integer actorId;
    //entityId和entityType共同组成了所触发的事件
    private Long entityId;
    private Integer entityType;
    //该事件的拥有者
    private Integer entityOwnerId;
    //需要传输的额外信息
    private Map<String, String> exts = new HashMap<>();

    public EventModel(EventType type){
        this.type = type;
    }
}
