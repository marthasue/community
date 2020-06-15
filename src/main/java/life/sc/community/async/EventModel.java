package life.sc.community.async;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


public class EventModel {
    //触发者的id
    private Integer actorId;
    //entityId和entityType共同组成了所触发的事件
    private Long entityId;
    private Integer entityType;
    //该事件的拥有者
    private Integer entityOwnerId;

    //之前定义的事件类型
    private EventType type;
    //需要传输的额外信息
//    private Map<String, String> exts = new HashMap<>();
    public EventModel(){

    }

    public EventModel(EventType type){
        this.type = type;
    }

//    public void setExts(String key, String value) {
//        exts.put(key,value);
//    }
//    public String getExts(String key){
//        return exts.get(key);
//    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Integer getEntityOwnerId() {
        return entityOwnerId;
    }

    public void setEntityOwnerId(Integer entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

//    public Map<String, String> getExts() {
//        return exts;
//    }
//
//    public void setExts(Map<String, String> exts) {
//        this.exts = exts;
//    }
}
