package com.fast.core.common.util;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * Java 8 stream filter封装
 *
 * @author 黄嘉浩
 * @date 2023-08-18 20:03
 **/
public class FilterUtil {
    /**
     * 字符串比较全比较
     * 一.如果查询参数(search)为空串或null 直接返回true
     * 二.判断target是否等于search
     **/
    public static boolean eq(String target, String search) {
        return SUtil.isBlank(search) || SUtil.equals(target, search);
    }

    /**
     * 模糊查询
     * 一.如果查询参数(search)为空串或null 直接返回true
     * 二.判断target是否包含search  忽略大小写
     */
    public static boolean contains(String target, String search) {
        if (CharSequenceUtil.isBlank(search)) {
            return true;
        }
        return SUtil.contains(target, search);
    }
}
