package com.flinkinfo.monitordata.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 应用缓存
 *
 * @author jimmy
 */
//@Component
public class AppCache
{
    @Resource(name = "redisTemplate")
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    /**
     * 保存键值
     *
     * @param key
     * @param value
     */
    public void put(final String key, final String value)
    {
        redisTemplate.execute(new RedisCallback<Object>()
        {

            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException
            {
                redisConnection.set(redisTemplate.getStringSerializer().serialize(key),
                        redisTemplate.getStringSerializer().serialize(value));
                return null;
            }
        });
    }

    /**
     * 获取值
     *
     * @param id 键
     * @return
     */
    public String get(final String id)
    {
        return redisTemplate.execute(new RedisCallback<String>()
        {
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException
            {
                byte[] key = redisTemplate.getStringSerializer().serialize(id);
                if (redisConnection.exists(key))
                {
                    byte[] value = redisConnection.get(key);
                    String content = redisTemplate.getStringSerializer().deserialize(value);
                    return content;
                }
                return null;
            }
        });
    }
}
