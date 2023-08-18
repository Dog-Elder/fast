package com.fast.common.constant.cache;


import com.fast.core.annotation.Cache;

/**
 * 缓存常量
 *
 * @Author: 黄嘉浩
 * @Date: 2018/9/3 16:03
 **/
public class CacheConstant {

    /**
     * 框架统一前缀
     **/
    private static final String FAST = "fast:";

    /**
     * 后台缓存前缀
     **/
    public static final String MANAGE = CacheConstant.FAST + "manage:";

    /**
     * 系统前缀
     **/
    static final String SYS = CacheConstant.FAST + "sys:";

    /**
     * 除非结果为空
     **/
    public final static String UNLESS_RESULT_EQ_NULL = "#result == null";

    /**
     * 除非结果为空或空集合
     **/
    public final static String UNLESS_RESULT_EQ_NULL_OR_ZERO = "#result == null || #result.size()==0";

    /**
     * 时间 (秒)
     **/
    final static int ONE_MINUTE = 60;
    final static int TEN_SECONDS = 10;
    final static int ONE_HOUR = 60 * 60;
    public final static int ONE_DAY = 60 * 60 * 24;

    /**
     * 常用于注解 {@link Cache#value()}使用
     **/
    public interface TTL {
        String TTL = "@ttl=";
        // 一分钟
        String ONE_MINUTE = TTL + CacheConstant.ONE_MINUTE;
        // 10秒
        String TEN_SECONDS = TTL + CacheConstant.TEN_SECONDS;
        // 一小时
        String ONE_HOUR = TTL + CacheConstant.ONE_HOUR;
        // 一天
        String ONE_DAY = TTL + CacheConstant.ONE_DAY;
    }


    /**
     * 参数配置
     **/
    public interface Config {
        String CONFIG = "config:";
        String MANAGE_CONFIG = MANAGE + CONFIG;
        // 通过Config key获取value
        String VALUE = MANAGE_CONFIG + "value" + TTL.ONE_MINUTE;
    }

    /**
     * 系统菜单
     **/
    public interface SysMenu {
        String MENU = "menu:";
        String MANAGE_MENU = MANAGE + MENU;
        String ALL = MANAGE_MENU + "all";
        String tree = MANAGE_MENU + "tree";
    }

    /**
     * 用户
     **/
    public interface User {
        String USER = "user:";
        String MANAGE_USER = MANAGE + USER;
        String INFO = MANAGE_USER + "{}:info::{}";
    }

    /**
     * 角色
     **/
    public interface Role {
        String ROLE = "role:";
        String MANAGE_ROLE = MANAGE + ROLE;
        String ALL = MANAGE_ROLE + "all";
    }

    /**
     * 角色与菜单
     **/
    public interface RoleMenu {
        String ROLE_MENU = "role_menu:";
        String MANAGE_ROLE_MENU = MANAGE + ROLE_MENU;
        String ROLE_MENU_ALL = MANAGE_ROLE_MENU + "all";
    }


    /**
     * 用户与角色
     **/
    public interface UserRole {
        String USER_ROLE = "user_role:";
        String MANAGE_USER_ROLE = MANAGE + USER_ROLE;
        String USER_ROLE_ALL = MANAGE_USER_ROLE + "all";
    }


    /**
     * 值集
     **/
    public interface SetValue {
        // 列表
        String SET_VALUE = CacheConstant.SYS + "set:value";
        String SET_STATE = CacheConstant.SYS + "set:state::{}";
    }

    /**
     * 分布式锁
     **/
    public interface SysLock {
        // 编码锁前缀
        String LOCK_CODE = CacheConstant.SYS + "lock:code::{}::{}";
    }

    /**
     * 编码
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
