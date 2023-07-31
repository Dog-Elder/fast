package com.fast.common.constant.cache;


/**
 * @Description:常量
 * @Author: 黄嘉浩
 * @Date: 2018/9/3 16:03
 **/
public class CacheConstant {

    /**
     * Redis
     **/
    // redis过期时间，以秒为单位，一分钟
    public final static int EXRP_MINUTE = 60;

    // key过渡时间，以秒为单位,10秒
    public final static int EXRP_SECOND = 10;

    // redis过期时间，以秒为单位，一小时
    public final static int EXRP_HOUR = 60 * 60;

    //  redis过期时间，以秒为单位，一天
    public final static int EXRP_DAY = 60 * 60 * 24;

    /**
     * 框架统一前缀
     **/
    private static final String FAST = "fast:";

    /**
     * 后台缓存前缀
     **/
    public static final String MANAGE = CacheConstant.FAST + "manage:";

    /**
     * 系统配置前缀
     **/
    static final String SYS = CacheConstant.FAST + "sys:";


    /**
     * 管理端用户前缀
     **/
    public static final String MANAGE_USER = CacheConstant.MANAGE + "user:";

    /**
     * 用户
     **/
    public interface User {
        String INFO = CacheConstant.MANAGE_USER + "{}:info::{}";
    }

    /**
     * 对应值集相关缓存常量
     **/
    public interface SetValue {
        // 列表
        String SET_VALUE = CacheConstant.SYS + "set:value";
        String SET_STATE = CacheConstant.SYS + "set:state::{}";

    }

    /**
     * 锁前缀
     **/
    public interface SysLock {
        // 编码锁前缀
        String LOCK_CODE = CacheConstant.SYS + "lock:code::{}::{}";
    }

    /**
     * 对应编码缓存常量
     **/
    public interface SysSetRule {
        String CODE = "code:";
        // 编码集状态  v: Y:开启 N:关闭
        String CODE_STATUS = CacheConstant.SYS + SysSetRule.CODE + "set:status::{}::{}";
        // 编码集不存在状态
        String CODE_STATUS_NO_EXIST = CacheConstant.SYS + SysSetRule.CODE + "set:status:not::{}::{}";
        // 编码集已经使用(指的业务中已经开始生成字段)
        String CODE_USE_OPEN = CacheConstant.SYS + SysSetRule.CODE + "set:use::{}::{}";
        // 编码段数据 sys:code:rule:规则代码:编码值
        String CODE_RULE = CacheConstant.SYS + SysSetRule.CODE + "rule::{}::{}";
        // 编码序列自增值
        String CODE_NUMBER = CacheConstant.SYS + SysSetRule.CODE + "rule:number::{}::{}";
    }

}
