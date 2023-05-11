package com.fast.core.common.util;

import java.util.Random;

/**
 * @description: UUID位数工具类
 **/
public class UUIDDigitUtil {

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateString(int length) // 参数为返回随机数的长度
    {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }
}
