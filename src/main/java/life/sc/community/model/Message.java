package life.sc.community.model;

import lombok.Data;

@Data
public class Message {
    private Integer fromId;
    private Integer toId;
    private Integer type;
    private Integer outerId;
    private Long gmtCreate;
    private String content;
}
