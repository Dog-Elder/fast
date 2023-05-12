package com.fast.core.common.util;

import org.apache.commons.logging.LogFactory;

import java.util.function.Supplier;

/**
 * 打印log日志
 */
public class LogUtil {
    /**
     * 传入类名，打印Throwable异常信息
     */
    public static void err(Class<?> clazz, Throwable e) {
        LogFactory.getLog(clazz).error(e.getMessage(), e);
    }

    /**
     * desc 等价于：err(String.format(format, args))
     **/
    public static void err(String format, Object... args) {
        err(String.format(format, args));
    }

    /**
     * desc 等价于：info(String.format(format, args))
     **/
    public static void info(String format, Object... args) {
        if (com.fast.core.common.util.Util.isIdea()) {
            info(String.format(format, args));
        }
    }

    public static void warn(String msg) {
        LogFactory.getLog(new CurrentClassGetter().getCurrentClass()).warn(msg);
    }

    /**
     * 传入类名，打印Throwable异常信息
     */
    public static void info(Class<?> clazz, Throwable e) {
        if (com.fast.core.common.util.Util.isIdea()) {
            LogFactory.getLog(clazz).info(e.getMessage(), e);
        }
    }

    public static void info(String msg, Throwable e) {
        if (com.fast.core.common.util.Util.isIdea()) {
            LogFactory.getLog(new CurrentClassGetter().getCurrentClass()).info(msg, e);
        }
    }


    /**
     * 打印Throwable异常信息
     */
    public static void info(Throwable e) {
        if (com.fast.core.common.util.Util.isIdea()) {
            LogFactory.getLog(new CurrentClassGetter().getCurrentClass()).info(e.getMessage(), e);
        }
    }

    /**
     * 打印自定义信息
     */
    public static void info(String msg) {
        if (com.fast.core.common.util.Util.isIdea()) {
            LogFactory.getLog(new CurrentClassGetter().getCurrentClass()).info(msg);
        }
    }

    /**
     * desc 打印传入的lambda表达式结果
     **/
    public static void info(Supplier<String> function) {
        if (Util.isIdea()) {
            info(function.get());
        }
    }


    /**
     * 打印Throwable异常信息
     */
    public static void err(Throwable e) {
        LogFactory.getLog(new CurrentClassGetter().getCurrentClass()).error(e.getMessage(), e);
    }


    private static class CurrentClassGetter extends SecurityManager {
        public Class getCurrentClass() {
            Class[] arr = getClassContext();
            int i = 2;
            for (i = 2; i < arr.length; i++) {
                if (arr[i] != LogUtil.class) {
                    break;
                }
            }
            return arr[i];
        }
    }

    /**
     * 打印自定义信息，并且会返回该信息
     */
    public static String err(String msg) {
        LogFactory.getLog(new CurrentClassGetter().getCurrentClass()).error(msg);
        return msg;
    }

    public static void err(String msg, Throwable e) {
        LogFactory.getLog(new CurrentClassGetter().getCurrentClass()).error(msg, e);
    }

    /**
     * 打印消息并终止程序
     */
    public static void errThenExit(String msg) {
        err(msg);
        System.exit(0);
    }

    /**
     * 打印异常并终止程序
     */
    public static void errThenExit(Throwable throwable) {
        err(throwable);
        System.exit(0);
    }

    public static void errThenExit(String msg, Throwable e) {
        err(msg, e);
        System.exit(0);
    }

}
