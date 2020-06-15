package life.sc.community.async;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import life.sc.community.util.JedisAdapter;
import life.sc.community.util.RedisKeyUtil;
import life.sc.community.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 事件的入口
 * 由EventProducer将Event保存到队列中
 * 为什么使用Redis来保存EventModel???
 */
@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    //将事件保存到队列中
    //就是保存到Redis中
    public boolean fireEvent(EventModel eventModel){
        try{
            //先将EventModel事件模型转化成JSON
            String json = JSON.toJSONString(eventModel);
            //String json = JSONObject.toJSONString(eventModel);
            //String key = RedisKeyUtil.getEventQueueKey();
            String key = "eventqueue";
            System.out.println("key = " + key);
            System.out.println("json = " + json);
            jedisAdapter.lpush(key,json);//将事件序列化之后存储到redis中
            jedisAdapter.lpush("name","1");
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
