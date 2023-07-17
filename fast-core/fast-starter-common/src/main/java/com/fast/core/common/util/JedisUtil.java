package com.fast.core.common.util;// package com.fast.core.common.util;
// 
// 
// import com.fast.core.common.exception.CustomException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationContext;
// import org.springframework.stereotype.Component;
// import redis.clients.jedis.Jedis;
// import redis.clients.jedis.JedisPool;
// import redis.clients.jedis.params.SetParams;
// 
// import java.util.Collections;
// import java.util.List;
// import java.util.Set;
// 
///**
//  * redis工具类
//  *
//  * @author 黄嘉浩
//  */
// @Component
////@Slf4j
// public class JedisUtil {
// 
//     private static ApplicationContext applicationContext = null;
// 
//     public JedisUtil() {
//     }
// 
// 
// 
//     /**
//      * 静态注入JedisPool连接池
//      * 本来是正常注入JedisUtil，可以在Controller和Service层使用，但是重写Shiro的CustomCache无法注入JedisUtil
//      * 现在改为静态注入JedisPool连接池，JedisUtil直接调用静态方法即可
//      * https:// blog.csdn.net/W_Z_W_888/article/details/79979103
//      */
//     private static JedisPool jedisPool;
// 
//     @Autowired
//     public void setJedisPool(JedisPool jedisPool) {
//         JedisUtil.jedisPool = jedisPool;
//     }
// 
//     /**
//      * 获取Jedis实例
//      *
//      * @param
//      * @return redis.clients.jedis.Jedis
//      * @author dog_E
//      * @date 2018/9/4 15:47
//      */
////    public static synchronized Jedis getResource() {
////        try {
////            if (jedisPool != null) {
////                return jedisPool.getResource();
////            } else {
////                return null;
////            }
////        } catch (Exception e) {
////            throw new CustomException("获取Jedis资源异常:" + e.getMessage());
////        }
////    }
// 
//     /**
//      * 释放Jedis资源
//      *
//      * @param
//      * @return void
//      * @author dog_E
//      * @date 2018/9/5 9:16
//      */
//     public static void closePool() {
//         try {
//             jedisPool.close();
//         } catch (Exception e) {
//             throw new CustomException("释放Jedis资源异常:" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 获取redis键值-object
//      *
//      * @param key
//      * @return java.lang.Object
//      * @author dog_E
//      * @date 2018/9/4 15:47
//      */
//     public static Object getObject(String key) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             String bytes = jedis.get(key);
//             if (SUtil.isNotBlank(bytes)) {
//                 return bytes;
//             }
//         } catch (Exception e) {
//             throw new CustomException("获取Redis键值getObject方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//         return null;
//     }
// 
//     /**
//      * 设置redis键值-object
//      *
//      * @param key
//      * @param value
//      * @return java.lang.String
//      * @author dog_E
//      * @date 2018/9/4 15:49
//      */
//     public static String setObject(String key, Object value) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.set(key.getBytes(), SerializableUtil.serializable(value));
//         } catch (Exception e) {
//             throw new CustomException("设置Redis键值setObject方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 设置redis键值-object-expiretime
//      *
//      * @param key
//      * @param value
//      * @param expiretime
//      * @return java.lang.String
//      * @author dog_E
//      * @date 2018/9/4 15:50
//      */
//     public static String setObject(String key, Object value, int expiretime) {
//         String result;
//         try (Jedis jedis = jedisPool.getResource()) {
//             result = jedis.set(key, String.valueOf(value));
//             if (RedisConstant.OK.equals(result)) {
//                 jedis.expire(key.getBytes(), expiretime);
//             }
//             return result;
//         } catch (Exception e) {
//             throw new CustomException("设置Redis键值setObject方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 获取redis键值-Json
//      *
//      * @param key
//      * @return java.lang.Object
//      * @author dog_E
//      * @date 2018/9/4 15:47
//      */
//     public static String getJson(String key) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.get(key);
//         } catch (Exception e) {
//             throw new CustomException("获取Redis键值getJson方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 设置redis键值-Json
//      *
//      * @param key
//      * @param value
//      * @return java.lang.String
//      * @author dog_E
//      * @date 2018/9/4 15:49
//      */
//     public static String setJson(String key, String value) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.set(key, value);
//         } catch (Exception e) {
//             throw new CustomException("设置Redis键值setJson方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 设置redis键值-Json-expiretime
//      *
//      * @param key        键
//      * @param value      json
//      * @param expiretime 到期时间
//      * @return java.lang.String
//      * @author dog_E
//      * @date 2018/9/4 15:50
//      */
//     public static String setJson(String key, String value, int expiretime) {
//         String result;
//         try (Jedis jedis = jedisPool.getResource()) {
//             result = jedis.set(key, value);
//             if (RedisConstant.OK.equals(result)) {
//                 jedis.expire(key, expiretime);
//             }
//             return result;
//         } catch (Exception e) {
//             throw new CustomException("设置Redis键值setJson方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * <b>可以作为获取唯一id的方法</b><br/>
//      * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
//      * @param key String
//      * @param number long要减去的值
//      * @return long 相加后的值
//      **/
//     public static long incrBy(String key, long number) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.incrBy(key, number);
//         } catch (Exception e) {
//             throw new CustomException("加/减Redis的键incrBy方法异常:key=" + key + " number=" + number + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 删除key
//      *
//      * @param key
//      * @return java.lang.Long
//      * @author dog_E
//      * @date 2018/9/4 15:50
//      */
//     public static Long delKey(String key) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.del(key.getBytes());
//         } catch (Exception e) {
//             throw new CustomException("删除Redis的键delKey方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//     }
// 
// 
//     /**
//      * key是否存在
//      *
//      * @param key
//      * @return java.lang.Boolean
//      * @author dog_E
//      * @date 2018/9/4 15:51
//      */
//     public static Boolean exists(String key) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.exists(key);
//         } catch (Exception e) {
//             throw new CustomException("查询Redis的键是否存在exists方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 模糊查询获取key集合(keys的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用)
//      *
//      * @param key
//      * @return java.util.Set<java.lang.String>
//      * @author dog_E
//      * @date 2018/9/6 9:43
//      */
//     public static Set<String> keysS(String key) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.keys(key);
//         } catch (Exception e) {
//             throw new CustomException("模糊查询Redis的键集合keysS方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 模糊查询获取key集合(keys的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用)
//      *
//      * @param key
//      * @return java.util.Set<java.lang.String>
//      * @author dog_E
//      * @date 2018/9/6 9:43
//      */
//     public static Set<byte[]> keysB(String key) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.keys(key.getBytes());
//         } catch (Exception e) {
//             throw new CustomException("模糊查询Redis的键集合keysB方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//     }
// 
// 
//     /**
//      * redis做hash的添加
//      */
//     public static boolean hset(String key, String field, String value) {
//         if (SUtil.isBlank(key) || SUtil.isBlank(field)) {
//             return false;
//         }
//         try (Jedis jedis = jedisPool.getResource()) {
//             // If the field already exists, and the HSET just produced an update of the value, 0 is returned,
//             // otherwise if a new field is created 1 is returned.
//             Long statusCode = jedis.hset(key, field, value);
//             if (statusCode > -1) {
//                 return true;
//             }
//         } catch (Exception e) {
//             throw new CustomException("模糊查询Redis的键集合keysB方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//         return false;
//     }
// 
//     /**
//      * @param key:   key
//      * @param field: map中key
//      * @Description: 获取hsah中field的value
//      * @Author: 黄嘉浩
//      * @Date: 2020/11/18 14:09
//      * @return: map中value
//      **/
//     public static String hget(String key, String field) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.hget(key, field);
//         } catch (Exception e) {
//             throw new CustomException("获取hsah中field的value方法异常:key=" + key + "field=" + field + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 获取hsah中所有的value
//      *
//      * @param key: key
//      * @Date: 2022/9/22 2:11
//      * @return: java.util.List<java.lang.String> map中所有value
//      **/
//     public static List<String> hvals(String key) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.hvals(key);
//         } catch (Exception e) {
//             throw new CustomException("获取hsah中field的value方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//     }
// 
// 
//     /**
//      * @param key:   key
//      * @param field: map中key
//      * @param value: 可以是正数 也可以是负数
//      * @Description: hash 值 增或加
//      * @Author: 黄嘉浩
//      * @Date: 2020/11/19 14:35
//      * @return: java.lang.Long
//      **/
//     public static Long hincrBy(String key, String field, long value) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.hincrBy(key, field, value);
//         } catch (Exception e) {
//             throw new CustomException("获取hsah中field的value方法异常:key=" + key + "field=" + field + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * @param key:   key
//      * @param fields : map中key
//      * @Description: hash 值删除
//      * @Author: 黄嘉浩
//      **/
//     public static Long hdel(String key, String... fields) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             return jedis.hdel(key, fields);
//         } catch (Exception e) {
//             throw new CustomException("获取hsah中field的value方法异常:key=" + key + "field=" + fields + " cause=" + e.getMessage());
//         }
//     }
// 
// 
//     /**
//      * 获取过期剩余时间
//      *
//      * @param key
//      * @return java.lang.String
//      * @author dog_E
//      * @date 2018/9/11 16:26
//      */
//     public static Long ttl(String key) {
//         Long result = -2L;
//         try (Jedis jedis = jedisPool.getResource()) {
//             result = jedis.ttl(key);
//             return result;
//         } catch (Exception e) {
//             throw new CustomException("获取Redis键过期剩余时间ttl方法异常:key=" + key + " cause=" + e.getMessage());
//         }
//     }
// 
// 
//     // 以下是分布式锁中的内容
// 
// 
//    /**
//     * 尝试3次获取锁
//     *
//     * @Date: 2022/9/25 23:34
//     * @param lockKey    锁
//     * @param requestId  请求标识
//     * @param expireTime 超期时间
//     * @return: boolean
//     **/
//    public static boolean retryTheLock(String lockKey, String requestId, long expireTime){
//        boolean lock = false;
//        int i = 0;
//        // 重复三次获取锁
//        for (i = 0; i < 3; i++) {
//            lock =acquiringALock(lockKey, requestId,expireTime);
//            if (lock) {
//                break;
//            } else {
//                try {
//                    // 一秒后重试获取锁
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (i == 3) {
//            throw new CustomException("网络繁忙，请稍候重试");
//        }
//        return true;
//    }
// 
//    /**
//     * 重试获取锁
//     *
//     * @Date: 2022/9/25 23:34
//     * @param lockKey    锁
//     * @param requestId  请求标识
//     * @param expireTime 超期时间
//     * @param retryInterval 重试间隔 retryInterval<=0 无需等待(1秒=1000)
//     * @return: boolean
//     **/
//    public static boolean retryTheLockLasting(String lockKey, String requestId, long expireTime,long retryInterval){
//        boolean lock = false;
//        while (true){
//            lock = acquiringALock(lockKey, requestId,expireTime);
//            if (lock) {
//                break;
//            }
//            if (retryInterval > 0){
//                try {
//                    // 重试获取锁
//                    Thread.sleep(retryInterval);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return true;
//    }
// 
// 
//     /**
//      * 尝试获取分布式锁
//      * 参考:https:// blog.csdn.net/qq_37960603/article/details/109460342?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522166411920316782395326252%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=166411920316782395326252&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~hot_rank-2-109460342-null-null.142^v50^control,201^v3^control_1&utm_term=jedis%E5%AE%9E%E7%8E%B0%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81&spm=1018.2226.3001.4187
//      *
//      * @param lockKey    锁
//      * @param requestId  请求标识
//      * @param expireTime 超期时间
//      * @return 是否获取成功
//      */
//     public static boolean acquiringALock(String lockKey, String requestId, long expireTime) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             Object result = jedis.set(lockKey, requestId, SetParams.setParams().nx().px(expireTime));
//             if (RedisConstant.LOCK_SUCCESS.equals(result)) {
//                 return true;
//             }
//             return false;
//         } catch (Exception e) {
//             throw new CustomException("尝试获取分布式锁异常:key=" + lockKey + "value=" + requestId + " cause=" + e.getMessage());
//         }
//     }
// 
//     /**
//      * 释放分布式锁
//      *
//      * @param lockKey   锁
//      * @param requestId 请求标识
//      * @return 是否释放成功
//      */
//     public static boolean releaseTheLock(String lockKey, String requestId) {
//         try (Jedis jedis = jedisPool.getResource()) {
//             String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//             Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
//             if (RedisConstant.RELEASE_SUCCESS.equals(result)) {
//                 return true;
//             }
//             return false;
//         } catch (Exception e) {
//             throw new CustomException("释放分布式锁方法异常:key=" + lockKey + "value=" + requestId  + " cause=" + e.getMessage());
//         }
//     }
// 
// 
// }
