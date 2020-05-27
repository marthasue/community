package life.sc.community.mapper;

import life.sc.community.dto.QuestionDTO;
import life.sc.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long id);

    @Select("select * from question where date_sub(CURDATE(), INTERVAL 7 DAY)<= DATE(gmt_create)")
    List<Question> findLastWeekQuestion();

    @Update("update question set comment_count = comment_count+1 where id = #{id}")
    void increCommentCountById(@Param("id") Long id);

    @Update("update question set view_count = view_count+1 where id = #{id}")
    void increViewCount(@Param("id") Long questionId);
}
