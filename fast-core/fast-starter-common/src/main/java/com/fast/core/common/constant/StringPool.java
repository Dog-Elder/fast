package com.fast.core.common.constant;

import cn.hutool.core.text.StrPool;

/**
 * 静态 String 池
 *
 * @author panzhipeng
 * @since 2023-02-21 23:36
 */

/**
 * 静态String池，提供常用字符串常量。
 * 实现StrPool接口以保持一致性。
 */
public class StringPool implements StrPool {
    public static final String AMPERSAND = "&";
    public static final String AND = "and";
    public static final String OR = "or";
    // 省略已在StrPool接口中定义的常量
    public static final String EMPTY_JSON = "{}"; // 无需重复，已在接口中定义
    public static final String EQUALS = "=";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String UNKNOWN = "unknown";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String CODE = "code";
    public static final String LOGIC_AND = "&&";
    public static final String LOGIC_OR = "||";

    // 示例：使用枚举管理编码字符串
    public enum Charset {
        UTF_8("UTF-8"),
        GBK("GBK"),
        ISO_8859_1("ISO-8859-1");

        private final String charset;

        Charset(String charset) {
            this.charset = charset;
        }

        @Override
        public String toString() {
            return this.charset;
        }
    }
}
