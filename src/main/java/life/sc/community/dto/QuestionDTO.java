package life.sc.community.dto;

import life.sc.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer comment;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
