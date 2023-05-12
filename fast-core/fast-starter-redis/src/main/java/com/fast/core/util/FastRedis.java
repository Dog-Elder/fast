package com.fast.core.util;

import com.fast.core.common.constant.RedisConstant;
import com.fast.core.common.exception.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Redis 工具类，提供常用的 Redis 操作方法封装
 */
@Component
@RequiredArgsConstructor
public class FastRedis {

    private final StringRedisTemplate redisTemplate;

    /**
     * 存储字符串类型的数据到 Redis 中
     *
     * @param key   Redis 键
     * @param value Redis 值
     */
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取 Redis 中指定键的字符串类型数据
     *
     * @param key Redis 键
     * @return Redis 值
     */
    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 存储对象类型的数据到 Redis 中
     *
     * @param key   Redis 键
     * @param value Redis 值
     */
    public <T> void setObject(String key, T value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 Redis 中指定键的对象类型数据
     *
     * @param key   Redis 键
     * @param clazz 对象类型
     * @param <T>   对象泛型类型
     * @return Redis 中指定键对应的对象类型数据
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String jsonValue = getString(key);
        if (jsonValue == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonValue, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 存储字符串类型的数据到 Redis 中，指定过期时间
     *
     * @param key    Redis 键
     * @param value  Redis 值
     * @param expire 过期时间，单位为秒
     */
    public void setStringWithExpire(String key, String value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire, SECONDS);
    }

    /**
     * 设置 Redis 中指定键的过期时间
     *
     * @param key    Redis 键
     * @param expire 过期时间，单位为秒
     * @return 是否设置成功
     */
    public boolean expire(String key, long expire) {
        return redisTemplate.expire(key, expire, SECONDS);
    }

    /**
     * 删除Redis中指定键的键值对
     *
     * @param key Redis键
     * @return 是否删除成功
     */
    public boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 模糊查询所有符合给定pattern的key集合
     *
     * @param pattern 匹配的模式，例如："user:*"
     * @return key集合
     */
    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 存储对象类型的数据到Redis中
     *
     * @param key    Redis键
     * @param value  Redis值，对象类型
     * @param expire 过期时间，单位为秒
     */
    public <T> void setObject(String key, T value, long expire) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(value), expire, SECONDS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储哈希类型的数据到Redis中
     *
     * @param key     Redis键
     * @param hashKey 哈希键
     * @param value   哈希值
     */
    public void setHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取Redis中存储的哈希类型的数据
     *
     * @param key     Redis键
     * @param hashKey 哈希键
     * @return Redis值，哈希值
     */
    public String getHash(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取Redis中存储的哈希类型的所有数据
     *
     * @param key Redis键
     * @return Redis值，哈希值的map集合
     */
    public Map<Object, Object> getAllHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 将Redis中存储的哈希类型的数据删除
     *
     * @param key     Redis键
     * @param hashKey 哈希键
     * @return 是否删除成功
     */
    public boolean deleteHash(String key, String hashKey) {
        return redisTemplate.opsForHash().delete(key, hashKey) > 0;
    }

    /**
     * 尝试3次获取锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean retryTheLock(String lockKey, String requestId, long expireTime) {
        boolean lock = false;
        int i = 0;
        // 重复三次获取锁
        for (i = 0; i < 3; i++) {
            lock = acquiringALock(lockKey, requestId, expireTime);
            if (lock) {
                break;
            } else {
                try {
                    // 一秒后重试获取锁
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (i == 3) {
            throw new CustomException("网络繁忙，请稍候重试");
        }
        return true;
    }

    /**
     * 重试获取锁
     *
     * @param lockKey       锁
     * @param requestId     请求标识
     * @param expireTime    超期时间
     * @param retryInterval 重试间隔 retryInterval<=0 无需等待(1秒=1000)
     * @return 是否获取成功
     */
    public boolean retryTheLockLasting(String lockKey, String requestId, long expireTime, long retryInterval) {
        boolean lock = false;
        while (true) {
            lock = acquiringALock(lockKey, requestId, expireTime);
            if (lock) {
                break;
            }
            if (retryInterval > 0) {
                try {
                    // 重试获取锁
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean acquiringALock(String lockKey, String requestId, long expireTime) {
        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            Boolean success = ops.setIfAbsent(lockKey, requestId);
            if (success) {
                redisTemplate.expire(lockKey, expireTime, TimeUnit.MILLISECONDS);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new CustomException("尝试获取分布式锁异常:key=" + lockKey + "value=" + requestId + " cause=" + e.getMessage());
        }
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseTheLock(String lockKey, String requestId) {
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
            if (RedisConstant.RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new CustomException("释放分布式锁方法异常:key=" + lockKey + "value=" + requestId + " cause=" + e.getMessage());
        }
    }


}
