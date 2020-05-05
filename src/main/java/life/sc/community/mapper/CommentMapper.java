package life.sc.community.mapper;

import life.sc.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {

    @Insert("insert into comment (question_id,user_id,content,gmt_create) values(#{questionId},#{userId},#{content},#{gmtCreate})")
    void insert(Comment comment);

    @Select("select * from comment where question_id = #{questionId}")
    List<Comment> getCommentsByQuestionId(Integer questionId);
    //List<Comment> getCommentsByQuestionId(@Param("questionId") Integer questionId);
}