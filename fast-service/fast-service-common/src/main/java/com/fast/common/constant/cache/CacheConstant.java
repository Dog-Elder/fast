package com.fast.common.constant.cache;


/**
 * @Description:常量
 * @Author: @Dog_Elder
 * @Date: 2018/9/3 16:03
 **/
public class CacheConstant {

    /**
     * Redis
     **/
    //redis过期时间，以秒为单位，一分钟
    public final static int EXRP_MINUTE = 60;

    //key过渡时间，以秒为单位,10秒
    public final static int EXRP_SECOND = 10;

    //redis过期时间，以秒为单位，一小时
    public final static int EXRP_HOUR = 60 * 60;

    // redis过期时间，以秒为单位，一天
    public final static int EXRP_DAY = 60 * 60 * 24;

    /**
     * 后台缓存前缀
     **/
    public static final String MANAGE = "manage:";

    public static final String SYS = "sys:";

    /**
     * 对应值集相关缓存常量
     **/
    public interface SetValue {
        //列表
        String _IN = SYS + "set:value";
    }

    /**
     * 锁前缀
     **/
    public interface SysLock {
        //编码锁前缀
        String _CODE_IN = SYS + "lock:code:{}:{}";
    }

    /**
     * 对应编码缓存常量
     **/
    public interface SysSetRule {
        String CODE = "code:";
        //编码集状态  v: Y:开启 N:关闭
        String _STATUS = SYS + CODE + "set:status:{}:{}";
        //编码集已经使用(指的业务中已经开始生成字段)
        String _USE_OPEN = SYS + CODE + "set:use:{}:{}";
        //编码段数据 sys:code:rule:规则代码:编码值
        String _IN = SYS + CODE + "rule:{}:{}";
        //编码序列自增值
        String _NUMBER = SYS + CODE + "rule:number:{}:{}";
    }

}
