package com.fast.core.common.util;


import com.fast.core.common.exception.ServiceException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Md5Util {
    /**
     * 生成含有随机盐的密码
     */
    public static String generatePassWord(String srcPassword) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        srcPassword = md5Hex(srcPassword + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = srcPassword.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = srcPassword.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 校验密码是否正确
     */
    public static boolean verifyPassword(String srcPass, String md5Pass) {
        try {
            if (srcPass.length() > md5Pass.length()) {
                throw new ServiceException("参数异位");
            }
            if (com.fast.core.common.util.SUtil.isEmpty(srcPass) || SUtil.isEmpty(md5Pass)) {
                throw new ServiceException("密码错误");
            }
            char[] cs1 = new char[32];
            char[] cs2 = new char[16];
            for (int i = 0; i < 48; i += 3) {
                cs1[i / 3 * 2] = md5Pass.charAt(i);
                cs1[i / 3 * 2 + 1] = md5Pass.charAt(i + 2);
                cs2[i / 3] = md5Pass.charAt(i + 1);
            }
            String salt = new String(cs2);
            return md5Hex(srcPass + salt).equals(new String(cs1));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        return md5Hex(src, "UTF-8");
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src, String charset) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes(charset));
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 签名
     * 暂定为所有有值的参数 按key排序然后和秘钥一起拼接成k1=v1&k2=v2&...kn=vn&key=秘钥
     */
    public static String sign(Map<String, String> param, String key) {
        TreeMap<String, String> map = new TreeMap<>(param);
        StringBuilder str = new StringBuilder();
        map.forEach((k, v) -> {
            if (StringUtils.isNotBlank(v)) {
                str.append(k).append("=").append(v).append("&");
            }
        });
        str.append("key=").append(key);
        return Md5Util.md5Hex(str.toString()).toLowerCase();
    }

}
