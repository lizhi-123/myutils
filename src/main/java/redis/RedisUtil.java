package redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    //   #最大空闲连接数, 默认8个
    int rMaxIdle = 8;
    //#连接超时时间(毫秒)
    int TIMEOUT = 5000;
    // 在borrow一个事例时是否提前进行validate操作
    boolean BORROW = true;

    public void testJedis() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxIdle(rMaxIdle);
//        config.setTestOnBorrow(BORROW);
//        JedisPool pool = new JedisPool(config, "127.0.0.1", "3306", TIMEOUT, AUTH, 0);

    }
}
