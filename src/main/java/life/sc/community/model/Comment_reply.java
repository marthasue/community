package life.sc.community.model;

import lombok.Data;

@Data
public class Comment_reply {
    private Long commentId;
    private Long userId;
    private Long replyId;
    private String replyContent;
    private Long gmtCreate;
}
