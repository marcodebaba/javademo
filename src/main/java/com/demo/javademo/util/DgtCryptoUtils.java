package com.demo.javademo.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;
import java.util.UUID;

/**
 * 加密/解密服务的实现类
 *
 * @author Allen Wang
 */
@Slf4j
public class DgtCryptoUtils {

    public static String toMD5(String src) {
        return toHashValue(src, "MD5");
    }

    public static String toSHA256(String src) {
        return toHashValue(src, "SHA-256");
    }

    private static String toHashValue(String src, String hashAlgo) {
        byte[] data = src.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgo);
            byte[] digest = md.digest(data);
            return DgtStringUtils.toHexStr(digest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 采用AES方式加密 (算法AES/模式CBC/补码方式PKCS5Padding), 密钥以及向量从应用配置中获取
     *
     * @param src 源字符串
     * @return 加密后的字符串
     */
    public static String encryptAesCbc(String src) {
        Properties prop = PreloadUtils.getConfig();
        String key = prop.getProperty("crypto.aes.key");
        String iv = prop.getProperty("crypto.aes.iv");
        return encryptAesCbc(src, key, iv);
    }

    /**
     * 采用AES方式加密 (算法AES/模式CBC/补码方式PKCS5Padding)
     *
     * @param src 源字符串
     * @param key 密钥
     * @param iv  向量，使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 加密后的字符串
     */
    public static String encryptAesCbc(String src, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivp = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivp);

            byte[] encryptedBytes = cipher.doFinal(src.getBytes("utf-8"));

            // 此处使用BASE64做转码功能，同时能起到2次加密的作用
            String encrypted = Base64.getEncoder().encodeToString(encryptedBytes);
            return encrypted;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * 采用AES方式解密 (算法AES/模式CBC/补码方式PKCS5Padding), 密钥以及向量从应用配置中获取
     *
     * @param encrypted 加密后的字符串
     * @return 解密后的字符串
     */
    public static String decryptAesCbc(String encrypted) {
        Properties prop = PreloadUtils.getConfig();
        // String key = prop.getProperty("aes.key");
        // String iv = prop.getProperty("aes.iv");
        String key = prop.getProperty("crypto.aes.key");
        String iv = prop.getProperty("crypto.aes.iv");
        return decryptAesCbc(encrypted, key, iv);
    }

    /**
     * 采用AES方式解密 (算法AES/模式CBC/补码方式PKCS5Padding)
     *
     * @param encrypted 加密后的字符串
     * @param key       密钥
     * @param iv        向量，使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 解密后的字符串
     */
    public static String decryptAesCbc(String encrypted, String key, String iv) {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 算法/模式/补码方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivp = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivp);

            // 先用base64解码
            byte[] decodeBytes = Base64.getDecoder().decode(encrypted);
            byte[] srcBytes = cipher.doFinal(decodeBytes);

            String srcStr = new String(srcBytes, "utf-8");
            return srcStr;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * 加密
     *
     * @param keyStr
     * @param data
     * @param type
     * @param iv
     * @return
     */
    public static byte[] encrypt(String keyStr, byte[] data, String type, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(type);

            byte[] raw = keyStr.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new SecurityException("加密失败：" + e.getMessage(), e);
        }

    }

    /**
     * 解密
     *
     * @param keyStr
     * @param encData
     * @param type
     * @param iv
     * @return
     */
    public static byte[] decrypt(String keyStr, byte[] encData, String type, String iv) {
        try {
            byte[] raw = keyStr.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 算法/模式/补码方式
            Cipher cipher = Cipher.getInstance(type);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
            return cipher.doFinal(encData);
        } catch (Exception e) {
            throw new SecurityException("解密失败：" + e.getMessage(), e);
        }
    }

    /**
     * 生成sign
     *
     * @param content
     * @return
     */
    public static String createSign(String content, String salt) throws Exception {
        String sign = null;
        try {
            //sign = DigestUtils.md5Hex(MD5_KEY + content);
            sign = getSHA256StrJava(salt+content);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("生成sign失败"+e.getMessage());
        }
        return sign;
    }

    /**
     * 　　* 利用java原生的摘要实现SHA256加密
     * 　　* @param str 加密后的报文
     * 　　* @return
     *
     */
    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return encodeStr;
    }

    /**
     * 　　* 将byte转为16进制
     * 　　* @param bytes
     * 　　* @return
     *
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append('0');
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        String requestId = UUID.randomUUID().toString();
        long timestamp = 1655906291;
        String shaContent = DgtCryptoUtils.toSHA256("salt" + "partnerCode"
                + requestId + timestamp).toUpperCase();
        System.out.println(shaContent);
    }
}
