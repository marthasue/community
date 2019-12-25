package life.sc.community.dto;

import life.sc.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer comment;
    private Integer creator;
    private Integer view_count;
    private Integer like_count;
    private String tag;
    private User user;
}
