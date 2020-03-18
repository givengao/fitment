package com.zxyun.order.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @des:
 * @Author: given
 * @Date 2020/3/18 14:57
 */
public class CodeUtils {
    public CodeUtils() {
    }

    public static long strToLong(String str) {
        String s = "";

        for(int i = 0; i < str.length(); ++i) {
            int ch = str.charAt(i);
            s = s + Integer.toHexString(ch);
        }

        return Long.parseLong(s, 16);
    }

    public static String longToStr(Long l) throws UnsupportedEncodingException {
        String str = Long.toHexString(l);
        byte[] baKeyword = new byte[str.length() / 2];

        for(int i = 0; i < baKeyword.length; ++i) {
            baKeyword[i] = (byte)(255 & Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
        }

        return new String(baKeyword, "UTF-8");
    }

    public static String encodeBase64(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    public static byte[] decodeBase64(byte[] data) {
        return Base64.decodeBase64(data);
    }

    public static void main(String[] args) throws Exception {
        String source = "password";
        String key = "A1B2C3D4E5F60708";
        System.out.println("原文: " + source);
        String encryptData = CodeUtils.DES.encryptHex(source, key);
        System.out.println("加密后: " + encryptData);
        String decryptData = CodeUtils.DES.decryptHex(encryptData, key);
        System.out.println("解密后: " + decryptData);
        long l = strToLong("1.3.4.35");
        System.out.println(l);
        String str = longToStr(l);
        System.out.println(str);
    }

    public static class SHA {
        public SHA() {
        }

        public static byte[] hmacSha1(String key, String valueToDigest) {
            return (new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key)).hmac(valueToDigest);
        }

        public static byte[] hmacSha256(String key, String valueToDigest) {
            return (new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key)).hmac(valueToDigest);
        }
    }

    public static class MD5 {
        public MD5() {
        }

        public static byte[] md5(byte[] data) {
            return DigestUtils.md5(data);
        }

        public static byte[] md5(InputStream data) throws IOException {
            return DigestUtils.md5(data);
        }

        public static byte[] md5(String data) {
            return DigestUtils.md5(data);
        }

        public static String md5Hex(byte[] data) {
            return DigestUtils.md5Hex(data);
        }

        public static String md5Hex(InputStream data) throws IOException {
            return DigestUtils.md5Hex(data);
        }

        public static String md5Hex(String data) {
            return DigestUtils.md5Hex(data);
        }

        public static byte[] hmacMd5(String key, String valueToDigest) {
            return (new HmacUtils(HmacAlgorithms.HMAC_MD5, key)).hmac(valueToDigest);
        }
    }

    public static class DES {
        public static final String KEY_ALGORITHM = "DES";
        public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

        public DES() {
        }

        private static SecretKey keyGenerator(String keyStr) throws Exception {
            byte[] input = HexString2Bytes(keyStr);
            DESKeySpec desKey = new DESKeySpec(input);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            return secretKey;
        }

        public static byte[] HexString2Bytes(String hexstr) {
            byte[] b = new byte[hexstr.length() / 2];
            int j = 0;

            for(int i = 0; i < b.length; ++i) {
                char c0 = hexstr.charAt(j++);
                char c1 = hexstr.charAt(j++);
                b[i] = (byte)(parse(c0) << 4 | parse(c1));
            }

            return b;
        }

        private static int parse(char c) {
            if (c >= 'a') {
                return c - 97 + 10 & 15;
            } else {
                return c >= 'A' ? c - 65 + 10 & 15 : c - 48 & 15;
            }
        }

        public static String encryptHex(String data, String key) throws Exception {
            return new String(encrypt(data, key), "UTF-8");
        }

        public static String encryptHex(byte[] data, String key) throws Exception {
            return new String(encrypt(data, key), "UTF-8");
        }

        public static byte[] encrypt(String data, String key) throws Exception {
            return encrypt(data.getBytes("UTF-8"), key);
        }

        public static byte[] encrypt(byte[] data, String key) throws Exception {
            Key deskey = keyGenerator(key);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            SecureRandom random = new SecureRandom();
            cipher.init(1, deskey, random);
            byte[] results = cipher.doFinal(data);
            return Base64.encodeBase64(results);
        }

        public static String decryptHex(String data, String key) throws Exception {
            return new String(decrypt(data, key), "UTF-8");
        }

        public static String decryptHex(byte[] data, String key) throws Exception {
            return new String(decrypt(data, key), "UTF-8");
        }

        public static byte[] decrypt(String data, String key) throws Exception {
            return decrypt(data.getBytes("UTF-8"), key);
        }

        public static byte[] decrypt(byte[] data, String key) throws Exception {
            Key deskey = keyGenerator(key);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(2, deskey);
            return cipher.doFinal(Base64.decodeBase64(data));
        }
    }
}
