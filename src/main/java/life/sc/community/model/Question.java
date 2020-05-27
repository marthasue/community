package life.sc.community.model;

import lombok.Data;

import java.util.Date;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer commentCount;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
}
