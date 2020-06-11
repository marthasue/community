package life.sc.community.model;

import lombok.Data;

@Data
public class Comment {
    private Long commentId;
    private Long questionId;
    private Integer entityType;
    private Integer userId;
    private String content;
    private Long gmtCreate;
    private Long likeCount;
}
