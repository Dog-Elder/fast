package com.fast.core.common.util;

import com.fast.core.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @program: xxxxx
 * @description: 工具类
 * @author: 黄嘉浩
 * @create: 2021-06-02 15:20
 **/
@Slf4j
public class Util {

    /**
     * 是否是win系统
     */
    public static boolean isWin;

    /**
     * 是否是win系统
     */
    public static boolean isIdea;

    static {
        // 是否是win系统
        isWin = File.separatorChar == '\\';
        log.info("是否是win系统:{}",isWin);
        isIdea = System.getProperty("java.class.path").contains("idea_rt.jar");
        log.info("是否是IDEA环境:{}",isIdea);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @param msg    String 消息
     */
    public static void isNull(Object object, String msg) {
        if (object == null) {
            throw new ServiceException(msg);
        }
    }

    /**
     * * 是True
     *
     */
    public static void isTrue(boolean target,String msg) {
        if (target) {
            throw new ServiceException(msg);
        }
    }

    /**
     * * 非True
     *
     */
    public static void isNotTrue(boolean target,String msg) {
        isTrue(!target,msg);
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !Util.isNull(object);
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static void isNotNull(Object object, String msg) {
        if (!Util.isNull(object)) {
            throw new ServiceException(msg);
        }
    }


    /**
     * @Description: 检查是否是IDEA环境
     * @Author: Dog_Elder
     * @Date: 10:57
     * @return: boolean
     **/
    public static boolean isIdea() {
        return isIdea;
    }
}
