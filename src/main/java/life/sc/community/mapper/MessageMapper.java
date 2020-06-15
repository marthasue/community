package life.sc.community.mapper;

import life.sc.community.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {

    @Insert("insert into message (from_id,to_id,gmt_create,content) values(#{fromId},#{toId},#{gmtCreate},#{content})")
    void addMessage(Message message);
}
