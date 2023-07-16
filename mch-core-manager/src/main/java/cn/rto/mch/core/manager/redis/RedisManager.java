package cn.rto.mch.core.manager.redis;

import cn.rto.mch.core.manager.common.SystemException;
import cn.rto.mch.core.manager.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * ClassName: RedisManager
 * Description: TODO
 * Author: guanjieye
 * Date: 2023/07/17
 */
@Component
public class RedisManager {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 左侧放入
     *
     * @param key
     * @param obj
     * @return
     */
    public Long leftPush(String key, Object obj) {
        try {
            final byte[] redisKey = stringRedisTemplate.getStringSerializer().serialize(key);
            final byte[] redisValue = stringRedisTemplate.getStringSerializer().serialize(JsonUtil.toJSONString(obj));
            return stringRedisTemplate.execute((RedisConnection connection) -> connection.lPush(redisKey, redisValue));
        } catch (Exception e) {
            throw new SystemException("redis leftPush operation error", e);
        }
    }

    /**
     * 右侧弹出1个
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T rightPopOne(String key, Class<T> clazz) {
        try {
            final byte[] redisKey = stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = stringRedisTemplate.execute((RedisConnection connection) -> connection.rPop(redisKey));
            if (null != redisValue && redisValue.length > 0) {
                return JsonUtil.parseObject(stringRedisTemplate.getStringSerializer().deserialize(redisValue), clazz);
            }
            return null;
        } catch (Exception e) {
            throw new SystemException("redis get operation error", e);
        }
    }

    /**
     * 右侧弹出多个
     *
     * @param key
     * @param clazz
     * @param count
     * @param <T>
     * @return
     */
    public <T> List<T> rightPopList(String key, Class<T> clazz, int count) {
        try {
            stringRedisTemplate.setEnableTransactionSupport(true);
            final byte[] redisKey = stringRedisTemplate.getStringSerializer().serialize(key);
            List<byte[]> byteValList = stringRedisTemplate.execute((RedisConnection connection) -> {
                connection.multi();
                List<byte[]> tempList = connection.lRange(redisKey, -1 * count, -1);
                if (!CollectionUtils.isEmpty(tempList)) {
                    connection.lTrim(redisKey, 0, tempList.size() * -1);
                }
                connection.exec();
                return tempList;
            });
            if (!CollectionUtils.isEmpty(byteValList)) {
                List<T> ret = new ArrayList<>();
                for (byte[] bytes : byteValList) {
                    ret.add(JsonUtil.parseObject(stringRedisTemplate.getStringSerializer().deserialize(bytes), clazz));
                }
                return ret;
            }
            return null;
        } catch (Exception e) {
            throw new SystemException("redis rightPopList operation error", e);
        }
    }

    public Boolean insertIfAbsent(Object obj, String key, long timeout, TimeUnit unit) throws SystemException {
        return this.insertIfAbsent(JsonUtil.toJSONString(obj), key, timeout, unit);
    }

    public Boolean insertIfAbsent(String obj, String key, long timeout, TimeUnit unit) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = this.stringRedisTemplate.getStringSerializer().serialize(obj);
            Boolean result = (Boolean) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return timeout > 0L ? connection.set(redisKey, redisValue, Expiration.from(timeout, unit), RedisStringCommands.SetOption.ifAbsent()) : connection.setNX(redisKey, redisValue);
            });
            return result;
        } catch (Exception var9) {
            throw new SystemException("redis set operation error", var9);
        }
    }

    public Boolean insert(Object obj, String key, long second) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = this.stringRedisTemplate.getStringSerializer().serialize(this.objToString(obj));
            Boolean result = (Boolean) this.stringRedisTemplate.execute((RedisCallback<Boolean>) (connection) -> {
                if (second > 0L) {
                    connection.setEx(redisKey, second, redisValue);
                } else {
                    connection.set(redisKey, redisValue);
                }

                return Boolean.TRUE;
            });
            return result;
        } catch (Exception var8) {
            throw new SystemException("redis set operation error", var8);
        }
    }

    public Boolean insert(Object obj, String key) throws SystemException {
        return this.insert(obj, key, 0L);
    }

    public Boolean delete(String key) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            Long result = (Long) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.del(new byte[][]{redisKey});
            });
            return result > 0L;
        } catch (Exception var4) {
            throw new SystemException("redis delete operation error", var4);
        }
    }

    public String getString(String key) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = (byte[]) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.get(redisKey);
            });
            return null != redisValue && redisValue.length > 0 ? (String) this.stringRedisTemplate.getStringSerializer().deserialize(redisValue) : null;
        } catch (Exception var4) {
            throw new SystemException("redis getString operation error", var4);
        }
    }

    public <T> T get(String key, Class<T> clazz) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = (byte[]) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.get(redisKey);
            });
            return null != redisValue && redisValue.length > 0 ? JsonUtil.parseObject((String) this.stringRedisTemplate.getStringSerializer().deserialize(redisValue), clazz) : null;
        } catch (Exception var5) {
            throw new SystemException("redis get operation error", var5);
        }
    }

    public <T> List<T> getList(String key, Class<T> clazz) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = (byte[]) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.get(redisKey);
            });
            return null != redisValue && redisValue.length > 0 ? JsonUtil.parseList((String) this.stringRedisTemplate.getStringSerializer().deserialize(redisValue), clazz) : null;
        } catch (Exception var5) {
            throw new SystemException("redis list operation error", var5);
        }
    }

    public Boolean sAdd(String key, Object obj) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = this.stringRedisTemplate.getStringSerializer().serialize(JsonUtil.toJSONString(obj));
            Long result = (Long) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.sAdd(redisKey, new byte[][]{redisValue});
            });
            return result > 0L;
        } catch (Exception var6) {
            throw new SystemException("redis sAdd operation error", var6);
        }
    }

    public Boolean sRem(String key, Object obj) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = this.stringRedisTemplate.getStringSerializer().serialize(JsonUtil.toJSONString(obj));
            Long result = (Long) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.sRem(redisKey, new byte[][]{redisValue});
            });
            return result > 0L;
        } catch (Exception var6) {
            throw new SystemException("redis sRem operation error", var6);
        }
    }

    public Boolean zAdd(String key, Object obj, double score) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = this.stringRedisTemplate.getStringSerializer().serialize(JsonUtil.toJSONString(obj));
            return (Boolean) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.zAdd(redisKey, score, redisValue);
            });
        } catch (Exception var7) {
            throw new SystemException("redis zAdd operation error", var7);
        }
    }

    public Boolean zRem(String key, Object obj) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = this.stringRedisTemplate.getStringSerializer().serialize(JsonUtil.toJSONString(obj));
            Long result = (Long) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.zRem(redisKey, new byte[][]{redisValue});
            });
            return result > 0L;
        } catch (Exception var6) {
            throw new SystemException("redis zRem operation error", var6);
        }
    }

    public <T> List<T> zrangeByScore(String key, double min, double max, Class<T> clazz) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            Set<byte[]> bytes = (Set) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.zRangeByScore(redisKey, min, max);
            });
            return null != bytes && !bytes.isEmpty() ? (List) bytes.stream().map((b) -> {
                return JsonUtil.parseObject((String) this.stringRedisTemplate.getStringSerializer().deserialize(b), clazz);
            }).collect(Collectors.toList()) : null;
        } catch (Exception var9) {
            throw new SystemException("redis zRangeByScore operation error", var9);
        }
    }

    public Boolean hSet(String key, String field, Object obj) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisValue = this.stringRedisTemplate.getStringSerializer().serialize(JsonUtil.toJSONString(obj));
            byte[] redisField = this.stringRedisTemplate.getStringSerializer().serialize(field);
            return (Boolean) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.hSet(redisKey, redisField, redisValue);
            });
        } catch (Exception var7) {
            throw new SystemException("redis hSet operation error", var7);
        }
    }

    public <T> T hGet(String key, String field, Class<T> clazz) {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisField = this.stringRedisTemplate.getStringSerializer().serialize(field);
            byte[] bytes = (byte[]) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.hGet(redisKey, redisField);
            });
            return bytes != null && bytes.length > 0 ? JsonUtil.parseObject((String) this.stringRedisTemplate.getStringSerializer().deserialize(bytes), clazz) : null;
        } catch (Exception var7) {
            throw new SystemException("redis hGet operation error", var7);
        }
    }

    public <T> List<T> hVals(String keyEnum, Class<T> clazz) {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(keyEnum);
            List<byte[]> hVals = (List) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.hVals(redisKey);
            });
            if (hVals != null && hVals.size() > 0) {
                List<T> list = new ArrayList();
                Iterator var6 = hVals.iterator();

                while (var6.hasNext()) {
                    byte[] b = (byte[]) var6.next();
                    list.add(JsonUtil.parseObject((String) this.stringRedisTemplate.getStringSerializer().deserialize(b), clazz));
                }

                return list;
            } else {
                return null;
            }
        } catch (Exception var8) {
            throw new SystemException("redis hVals operation error", var8);
        }
    }

    public Boolean hDelete(String key, String field) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            byte[] redisField = this.stringRedisTemplate.getStringSerializer().serialize(field);
            Long result = (Long) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.hDel(redisKey, new byte[][]{redisField});
            });
            return result > 0L;
        } catch (Exception var6) {
            throw new SystemException("redis hDel operation error", var6);
        }
    }

    public void publish(String channel, Object message) throws SystemException {
        try {
            this.stringRedisTemplate.convertAndSend(channel, message);
        } catch (Exception var4) {
            throw new SystemException("redis publish operation error", var4);
        }
    }

    public Long increment(String key, Long value) throws SystemException {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(key);
            return (Long) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return value > 0L ? connection.incrBy(redisKey, value) : connection.incr(redisKey);
            });
        } catch (Exception var4) {
            throw new SystemException("redis increment operation error", var4);
        }
    }

    public <T> List<T> hVals(String keyEnum, Class<T> clazz, Class innerGeneric) {
        try {
            byte[] redisKey = this.stringRedisTemplate.getStringSerializer().serialize(keyEnum);
            List<byte[]> hVals = (List) this.stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
                return connection.hVals(redisKey);
            });
            if (hVals != null && hVals.size() > 0) {
                List<T> list = new ArrayList();
                Iterator var7 = hVals.iterator();

                while (var7.hasNext()) {
                    byte[] b = (byte[]) var7.next();
                    list.add(JsonUtil.parseObject((String) this.stringRedisTemplate.getStringSerializer().deserialize(b), clazz, innerGeneric));
                }

                return list;
            } else {
                return null;
            }
        } catch (Exception var9) {
            throw new SystemException("redis hVals operation error", var9);
        }
    }

    public void setTemplate(StringRedisTemplate template) {
        this.stringRedisTemplate = template;
    }

    private String objToString(Object obj) {
        return obj instanceof String ? (String) obj : JsonUtil.toJSONString(obj);
    }
}
