package life.sc.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long questionId;
    private String content;
    private Integer type;
}
