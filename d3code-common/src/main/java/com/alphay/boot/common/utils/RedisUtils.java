package com.alphay.boot.common.utils;

import cn.hutool.json.JSONUtil;
import com.alphay.boot.common.utils.spring.SpringUtils;
import com.alphay.boot.common.config.RedisConfig;
import org.apache.ibatis.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Set;

/**
 * @author Nottyjay
 * @date 2019/11/27
 */
public class RedisUtils {
  private static final Logger LOG = LoggerFactory.getLogger(RedisUtils.class);

  private static RedisConfig redisConfig = SpringUtils.getBean(RedisConfig.class);

  private RedisUtils() {
    // 防止被外部程序初始化
  }

  /**
   * 获取一个Redis连接
   *
   * @return
   */
  public static Jedis getJedis() {
    if (redisConfig == null) {
      redisConfig = SpringUtils.getBean(RedisConfig.class);
      if (redisConfig == null) {
        LOG.error("Redis config error");
        return null;
      }
    }
    return getJedis(redisConfig.getIndex());
  }

  /**
   * 获取一个指定db的Redis连接
   *
   * @param index
   * @return
   */
  public static Jedis getJedis(int index) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Get Redis DB Index is [{}]", index);
    }
    return RedisPoolFactory.getJedis(index);
  }

  /**
   * 关闭一个Redis连接
   *
   * @param jedis
   */
  public static void closeJedis(final Jedis jedis) {
    if (jedis != null) {
      RedisPoolFactory.returnResource(jedis);
    }
  }

  public static Long batchDel(String pre_str) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      Set<String> set = jedis.keys(pre_str + "*");
      return del(set.toArray(new String[0]));
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return null;
  }

  /**
   * 向List头部追加记录
   *
   * @param key
   * @param values
   * @return 记录总数
   */
  public static Long rpush(String key, String... values) {
    Jedis jedis = null;
    long count = 0;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      count = jedis.rpush(key, values);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return count;
  }

  /**
   * 向List尾部追加记录
   *
   * @param key
   * @param value
   * @return
   */
  public static Long lpush(String key, String value) {
    Jedis jedis = null;
    Long lpush = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      lpush = jedis.lpush(key, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return lpush;
  }

  /**
   * 获取指定范围的记录，可以做为分页使用
   *
   * @param key
   * @param start
   * @param end
   * @return List
   */
  public static List<String> lrange(String key, long start, long end) {
    Jedis jedis = null;
    List<String> list = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      list = jedis.lrange(key, start, end);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return list;
  }

  /**
   * List长度
   *
   * @param key
   * @return 长度
   */
  public static Long llen(String key) {
    Jedis jedis = null;
    long count = 0;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      count = jedis.llen(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return count;
  }

  /**
   * 算是删除吧，只保留start与end之间的记录
   *
   * @param key
   * @param start 记录的开始位置(0表示第一条记录)
   * @param end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
   * @return 执行状态码
   */
  public static String ltrim(String key, int start, int end) {
    Jedis jedis = null;
    String str = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      str = jedis.ltrim(key, start, end);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return str;
  }

  /**
   * 将List中的第一条记录移出List
   *
   * @param key
   * @return 移出的记录
   */
  public static String lpop(String key) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      value = jedis.lpop(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return value;
  }

  /**
   * 将List中最后第一条记录移出List
   *
   * @param key
   * @return 移出的记录
   */
  public static String rpop(String key) {
    Jedis jedis = null;
    String value = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      value = jedis.rpop(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return value;
  }

  /**
   * @param key
   * @param field
   * @param value
   * @return
   */
  public static boolean hset(String key, String field, String value) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.hasText(field, "Cache field can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return false;
      }
      jedis.hset(key, field, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return false;
  }

  /**
   * @param key
   * @param field
   * @param value
   * @return
   */
  public static boolean hset(String key, String field, Object value) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.hasText(field, "Cache field can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return false;
      }
      jedis.hset(key, field, JSONUtil.toJsonStr(value));
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return false;
  }

  public static String hget(String key, String field) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.hasText(field, "Cache field can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      return jedis.hget(key, field);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return null;
  }

  public static <T> T hget(String key, String field, Class<T> clazz) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.hasText(field, "Cache field can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      String result = jedis.hget(key, field);
      return JSONUtil.toBean(result, clazz);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return null;
  }

  /**
   * 插入一个新缓存 当key已存在时，覆盖原有key的value
   *
   * @param key 缓存key
   * @param value 缓存内容
   */
  public static boolean set(String key, String value) {
    Assert.hasText(key, "Cache key can't be null");
    return set(key, value, null);
  }

  /**
   * 插入一个新缓存 当key已存在时，覆盖原有key的value。
   *
   * @param key 缓存key
   * @param value 缓存内容
   * @param expiredTime 缓存失效时间
   */
  public static boolean set(String key, String value, Integer expiredTime) {
    Assert.hasText(key, "Cache key can't be null");

    Jedis jedis = null;

    try {
      jedis = getJedis();
      if (jedis == null) {
        return false;
      }
      String result = null;
      if (expiredTime == null) {
        result = jedis.set(key, value);
      } else {
        result = jedis.setex(key, expiredTime, value);
      }

      if (result != null && "ok".equals(result.toLowerCase())) {
        return true;
      }
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }

    return false;
  }

  /**
   * 获取缓存中的信息
   *
   * @param key
   * @return
   */
  public static String get(String key) {
    Assert.hasText(key, "Cache key can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      return jedis.get(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }

    return null;
  }

  /**
   * 插入一个新缓存 当key已存在时，覆盖原有key的value。
   *
   * @param key 缓存key
   * @param member 缓存字段
   * @param score 得分
   */
  public static Long zadd(String key, String member, double score) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.hasText(member, "Cache member can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return 0l;
      }
      return jedis.zadd(key, score, member);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return 0l;
  }

  /**
   * 返回指定排名区间的成员
   *
   * @param key
   * @param start
   * @param stop
   * @return
   */
  public static Set<String> zrange(String key, long start, long stop) {
    Assert.hasText(key, "Cache key can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      return jedis.zrange(key, start, stop);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return null;
  }

  /**
   * 返回指定成员的得分
   *
   * @param key
   * @param member
   * @return
   */
  public static Double zscore(String key, String member) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.hasText(member, "Cache member can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return 0.0;
      }
      return jedis.zscore(key, member);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return 0.0;
  }

  public static Double zincrby(String key, String member, double increment) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.hasText(member, "Cache member can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return 0.0;
      }
      return jedis.zincrby(key, increment, member);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return 0.0;
  }

  public static Long zrem(String key, String member) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.hasText(member, "Cache member can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return 0l;
      }
      return jedis.zrem(key, member);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
    return 0l;
  }

  /**
   * 设置缓存过期时间
   *
   * @param key 缓存key
   * @param expiredTime 缓存过期时间
   * @return
   */
  public static boolean expire(String key, Integer expiredTime) {
    Assert.hasText(key, "Cache key can't be null");
    Assert.notNull(expiredTime, "Cache expire time can't be null");

    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return false;
      }
      return jedis.expire(key, expiredTime) == 1 ? true : false;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }

    return false;
  }

  /**
   * 检查缓存是否存在
   *
   * @param key 缓存key
   * @return
   */
  public static boolean exists(String key) {
    Assert.hasText(key, "Cache key can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return false;
      }
      return jedis.exists(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }

    return false;
  }

  /**
   * 删除缓存
   *
   * @param keys 需要删除的缓存key数组
   * @return
   */
  public static Long del(String... keys) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("delete cache keys:{}", JSONUtil.toJsonStr(keys));
    }
    Assert.notNull(keys, "Cache key can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      return jedis.del(keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }

    return null;
  }

  public static Long hdel(String key, String field) {
    Assert.notNull(key, "Cache key can't be null");
    Assert.notNull(field, "Cache field can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {
        return null;
      }
      return jedis.hdel(key, field);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }

    return null;
  }

  public static void publish(String channel, Object message) {
    Assert.notNull(channel, "Channel field can't be null");
    Assert.notNull(message, "Message can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {}

      publish(channel, JSONUtil.toJsonStr(message));
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
  }

  public static void publish(String channel, String message) {
    Assert.notNull(channel, "Channel field can't be null");
    Assert.notNull(message, "Message can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {}

      jedis.publish(channel, message);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
  }

  public static void subscribe(JedisPubSub subscribe, String channel) {
    Assert.notNull(subscribe, "Subscribe can't be null");
    Assert.notNull(channel, "Channel field can't be null");
    Jedis jedis = null;
    try {
      jedis = getJedis();
      if (jedis == null) {}

      jedis.subscribe(subscribe, channel);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } finally {
      closeJedis(jedis);
    }
  }

  /**
   * Redis连接池
   *
   * @author JerryG
   */
  static class RedisPoolFactory {

    private static JedisPool jedisPool = null;

    static {
      JedisPoolConfig config = new JedisPoolConfig();
      config.setMaxIdle(redisConfig.getMaxIdle());
      config.setMinIdle(redisConfig.getMinIdle());
      config.setMaxTotal(redisConfig.getMaxTotal());
      config.setMaxWaitMillis(redisConfig.getMaxWait());
      config.setTestOnBorrow(true);
      if ("".equals(redisConfig.getPassword())) {
        redisConfig.setPassword(null);
      }
      jedisPool =
          new JedisPool(
              config,
              redisConfig.getHost(),
              redisConfig.getPort(),
              redisConfig.getTimeout(),
              redisConfig.getPassword());
    }

    /**
     * 根据db下标获取
     *
     * @param index
     * @return
     */
    public static synchronized Jedis getJedis(int index) {
      if (jedisPool == null) {
        throw new CacheException("Jedis实例获取失败，连接池为空。");
      }

      Jedis jedis = jedisPool.getResource();
      jedis.select(index);

      return jedis;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
      if (jedis != null) {
        jedis.close();
      }
    }
  }
}
