package com.fast.core.common.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrFormatter;
import com.fast.core.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.fast.core.common.constant.Constants._N;
import static com.fast.core.common.constant.Constants._Y;


/**
 * 字符串工具类
 *
 * @author Dog_Elder
 */
public class SUtil extends StringUtils {
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }


    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return com.fast.core.common.util.Util.isNull(str) || SUtil.NULLSTR.equals(str.trim());
    }

    /**
     * 判断一个字符串是否为空串
     *
     * @param str String
     * @param msg String 返回的消息
     */
    public static void isEmpty(String str,String msg) {
        if (com.fast.core.common.util.Util.isNull(str) || SUtil.NULLSTR.equals(str.trim())) {
            throw new ServiceException(msg);
        }
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !SUtil.isEmpty(str);
    }


    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return Util.isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return SUtil.NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return SUtil.NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return SUtil.NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return SUtil.NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (CUtil.isEmpty(params) || SUtil.isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 字符串转set
     *
     * @param str 字符串
     * @param sep 分隔符
     * @return set集合
     */
    public static final Set<String> strToSet(String str, String sep) {
        return new HashSet<>(SUtil.strToList(str, sep, true, true));
    }

    /**
     * 字符串转List
     *
     * @param str 字符串
     * @param sep 分隔符
     * @return set集合
     */
    public static final List<String> strToList(String str, String sep) {
        return SUtil.strToList(str, sep, true, true);
    }

    /**
     * 字符串转list
     *
     * @param str         字符串
     * @param sep         分隔符
     * @param filterBlank 过滤纯空白
     * @param trim        去掉首尾空白
     * @return list集合
     */
    private static final List<String> strToList(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<>();
        if (SUtil.isEmpty(str)) {
            return list;
        }

        //  过滤空白字符串
        if (filterBlank && SUtil.isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && SUtil.isBlank(string)) {
                continue;
            }
            if (trim) {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        //  前置字符是否大写
        boolean preCharIsUpperCase = true;
        //  当前字符是否大写
        boolean curreCharIsUpperCase = true;
        //  下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SUtil.SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SUtil.SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(SUtil.trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        //  快速检查
        if (name == null || name.isEmpty()) {
            //  没必要转换
            return "";
        } else if (!name.contains("_")) {
            //  不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        //  用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            //  跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            //  首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SUtil.SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 语句驼峰式大小写
     *
     * @param input 输入
     * @return {@link String}
     */
    public static String statementToCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder(words[0].toLowerCase());

        for (int i = 1; i < words.length; i++) {
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1).toLowerCase());
        }

        return result.toString();
    }


    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * 字符分割并转为String集合
     * "1,2,3"转换为String集合
     *
     * @param regex "1,2,3"
     * @return 字符串集合
     */
    public static List<String> splitToStrList(String regex) {
        String[] split = regex.split(",");
        return Arrays.asList(split);
    }
    /**
     * 字符分割并转为String集合 删除空格
     * "1,2,3"转换为String集合
     *
     * @param regex "1,2,3"
     * @return 字符串集合
     */
    public static List<String> splitToStrListRemoveBlank(String regex) {
        String[] split = regex.replaceAll(" +","").split(",");
        return Arrays.asList(split);
    }

    /**
     * 判断target是否包含search  忽略大小写
     * 1、若二者都为空（null或空字符串），则返回true
     * 2、若search为空则返回true
     * 3、用原生containsIgnoreCase判断
     */
    public static boolean contains(String target, String search) {
        if (CharSequenceUtil.isBlank(target)) {
            return CharSequenceUtil.isBlank(search);
        } else {
            if (CharSequenceUtil.isBlank(search)) {
                return true;
            }
            return CharSequenceUtil.containsIgnoreCase(target, search);
        }
    }

    /**
     * stream.filter 专用
     * 一.如果查询参数(search)为空串或null 直接返回true
     * 二.判断target是否包含search  忽略大小写
     */
    public static boolean filterContains(String target, String search) {
        if (CharSequenceUtil.isBlank(search)) {
            return true;
        }
        return SUtil.contains(target, search);
    }

    /**
     * 判断字符串是否等于大写Y
     */
    public static boolean isY(String target) {
        return _Y.equals(target);
    }

    /**
     * 判断字符串是否等于大写N
     */
    public static boolean isN(String target) {
        return _N.equals(target);
    }

    /**
     * 首字母大写
     **/
    static String capitalized(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }


    /**
     * 数字 前面补0
     * 例如
     * length=3 number=1   return 001
     * length=3 number=11   return 011
     *
     * @Date: 2022/9/27 23:40
     * @param number: 数字
     * @param length: 0位数+数字长度的总长度
     * @return: java.lang.String
     **/
    public static String zeroPr(long number,long length) {
        return String.format("%0"+length+"d", number);
    }

}