package life.sc.community.mapper;

import life.sc.community.dto.CommentDTO;
import life.sc.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CommentMapper {

    @Insert("insert into comment (parent_id,user_id,content,gmt_create,type,like_count) values(#{parentId},#{userId},#{content},#{gmtCreate},#{type},#{likeCount})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{parentId} and type=#{type} order by gmt_create desc")
    List<Comment> getCommentsByEntityId(@Param("parentId") Long id, @Param("type") Integer type);

    @Select("select count(parent_id) from comment where parent_id=#{parentId} and type=#{type}")
    int getCommentCount(@Param("parentId") Long id,@Param("type") Integer type);

    @Update("update comment set like_count =#{likeCount} where comment_id =#{commentId}")
    void updateLikeCount(@Param("commentId") Long id,@Param("likeCount") Long likeCount);
}
