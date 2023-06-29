package com.fast.core.common.util.sql;


import com.fast.core.common.util.SUtil;

/**
 * sql操作工具类
 * 
 * @author 黄嘉浩
 */
public class SqlUtil
{
    /**
     * 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value)
    {
        if (SUtil.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            return SUtil.EMPTY;
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }
}
