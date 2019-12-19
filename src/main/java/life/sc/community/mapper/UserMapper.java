package life.sc.community.mapper;

import life.sc.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//@Repository
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
    //@Insert("insert into user (name,token,account_id) values(#{name},#{token},#{account_id})")
    void insert(User user);
}
