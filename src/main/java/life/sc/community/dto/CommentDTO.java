package life.sc.community.dto;

import life.sc.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long commentId;
    private Long parentId;
    private Integer type;
    private String content;
    private Long likeCount;
    private Long gmtCreate;
    private User user;
}
