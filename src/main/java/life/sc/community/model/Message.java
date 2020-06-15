package life.sc.community.model;

import lombok.Data;

@Data
public class Message {
    private Integer fromId;
    private Integer toId;
    private Long gmtCreate;
    private String content;
}
