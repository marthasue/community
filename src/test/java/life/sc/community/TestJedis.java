package life.sc.community;

import com.alibaba.fastjson.JSON;

import life.sc.community.async.EventModel;
import life.sc.community.async.EventType;
import life.sc.community.model.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public class TestJedis {
    public static final Logger logger = LoggerFactory.getLogger(TestJedis.class);
    // Jedispool
    JedisCommands jedisCommands;
    JedisPool jedisPool;
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    String ip = "127.0.0.1";
    int port = 6379;
    int timeout = 2000;
    String password = "123456";

    public TestJedis() {
        // 初始化jedis
        // 设置配置
        jedisPoolConfig.setMaxTotal(1024);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxWaitMillis(100);
        jedisPoolConfig.setTestOnBorrow(false);//jedis 第一次启动时，会报错
        jedisPoolConfig.setTestOnReturn(true);
        // 初始化JedisPool
        jedisPool = new JedisPool(jedisPoolConfig, ip, port, timeout,password);
        //
        Jedis jedis = jedisPool.getResource();

        EventModel eventModel = new EventModel();
        eventModel.setType(EventType.LIKE);
        eventModel.setEntityType(EntityType.ENTITY_COMMENT);
        eventModel.setActorId(14);
        eventModel.setEntityId(42L);
        eventModel.setEntityOwnerId(14);
        String value = JSON.toJSONString(eventModel);
        String key = "test";
        jedis.lpush(key,value);

        System.out.println("put key = " + key);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                List<String> events = jedis.brpop(0,key);
                for(String message : events) {
                    //第一次返回键值会返回相应的key值
                    if (message.equals(key)) {
                        continue;
                    }
                    //将EventModel对象反序列化
                    System.out.println("message = " + message);
                    EventModel m = (EventModel) JSON.parseObject(message, EventModel.class);
                    System.out.println("m.getActorId() = " + m.getActorId());
                    System.out.println("m.getType() = " + m.getType());
                    System.out.println("m.getEntityOwnerId() = " + m.getEntityOwnerId());
                }

            }
        });
        thread.start();

        jedisCommands = jedis;
    }

    public void setValue(String key, String value) {
        this.jedisCommands.set(key, value);
    }

    public String getValue(String key) {
        return this.jedisCommands.get(key);
    }

    public static void main(String[] args) {
        TestJedis testJedis = new TestJedis();
//        testJedis.setValue("testJedisKey", "testJedisValue");
//        logger.info("get value from redis:{}",testJedis.getValue("testJedisKey"));
    }

}
