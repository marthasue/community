package life.sc.community.dto;

import life.sc.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long commentId;
    private Long questionId;
    private Integer userId;
    private String content;
    private Long gmtCreate;
    private User user;
}
