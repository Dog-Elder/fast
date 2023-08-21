package com.fast.core.common.util;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * 谷歌身份验证
 * https://blog.csdn.net/qq_46122292/article/details/127629244
 *
 * @author 黄嘉浩
 * @Description: 谷歌身份验证器工具类
 * @date 2023/08/21
 */
public class GoogleAuthenticator {

    /**
     * 密钥长度
     */
    private static final int SECRET_SIZE = 10;
    /**
     * 种子
     */
    private static final String SEED = "g8GjEvTbW5oVSV7avL47357438readsadaASDsadsasdSADsAdASdfsafdsafZAFDSADSADsaDsad124ewdsadSdasyetredLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";
    /**
     * 随机数算法
     */
    private static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
    /**
     * 窗口大小 时钟漂移容忍 默认 0
     */
    private static final int WINDOW_SIZE = 0;

    /**
     * 窗口大小
     */
    private int windowSize = WINDOW_SIZE;

    /**
     * 谷歌身份验证
     */
    public GoogleAuthenticator() {
    }

    /**
     * 谷歌身份验证
     *
     * @param windowSize 窗口大小
     */
    public GoogleAuthenticator(int windowSize) {
        if (windowSize >= 1 && windowSize <= 17) {
            this.windowSize = windowSize;
        }
    }

    /**
     * 生成密钥
     *
     * @return {@link String}
     */
    public String generateSecretKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
            sr.setSeed(Base64.decodeBase64(SEED.getBytes()));
            byte[] buffer = sr.generateSeed(SECRET_SIZE);
            Base32 codec = new Base32();
            byte[] encodedKey = codec.encode(buffer);
            return new String(encodedKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成二维码信息
     *
     * @param label  标签
     * @param user   用户
     * @param secret 秘密
     * @return {@link String}
     */
    public String generateQRBarcode(String label, String user, String secret) {
        String format = "otpauth://totp/%s:%s?secret=%s";
        return String.format(format, label, user, secret);
    }

    /**
     * 生成qrbarcode
     *
     * @param user   用户
     * @param secret 秘密
     * @return {@link String}
     */
    public String generateQRBarcode(String user, String secret) {
        String format = "otpauth://totp/%s?secret=%s";
        return String.format(format, user, secret);
    }

    /**
     * 验证代码
     *
     * @param secret      秘密
     * @param code        代码
     * @param timeSeconds 时间秒
     * @return boolean
     */
    public boolean verifyCode(String secret, long code, long timeSeconds) {
        if (secret == null || secret.isEmpty()) {
            return false;
        }

        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);

        long currentTimeSeconds = timeSeconds / 30L;

        for (int i = -windowSize; i <= windowSize; i++) {
            long hash;
            try {
                hash = calculateCode(decodedKey, currentTimeSeconds + i);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            if (hash == code) {
                return true;
            }
        }

        return false;
    }

    /**
     * 计算代码
     *
     * @param key         关键
     * @param timeSeconds 时间秒
     * @return int
     * @throws NoSuchAlgorithmException 没有这样算法异常
     * @throws InvalidKeyException      无效关键例外
     */
    private int calculateCode(byte[] key, long timeSeconds) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = timeSeconds;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }

    /**
     * 生成 密钥与 二维码
     **/
//    public static void main(String[] args) {
//        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
//        String secretKey = googleAuthenticator.generateSecretKey();
//        String code = googleAuthenticator.generateQRBarcode("user", secretKey);
//        System.out.println("code = " + code);
//        //生成二维码  300 表示二维码的大小  D:\img\aa\qrcode.jpg 表示为二维码的生成路径
//        QrCodeUtil.generate(code, 300, 300, FileUtil.file("D:\\img\\aa\\qrcode.jpg"));
//    }

//    /**
//     * 主要
//     *
//     * @param args arg游戏
//     */
//    public static void main(String[] args) {
//        String code = "721416";
//        long time = System.currentTimeMillis() / 1000L;
//        GoogleAuthenticator g = new GoogleAuthenticator();
//        boolean result = g.verifyCode("ESSG362DXJ4VCJBQ", Long.valueOf(code), time);
//        System.out.println("验证码是否正确: " + result);
//    }
}