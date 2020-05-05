package life.sc.community.model;

import lombok.Data;

@Data
public class Comment {
    private Long commentId;
    private Long questionId;
    private Integer userId;
    private String content;
    private Long gmtCreate;
}
