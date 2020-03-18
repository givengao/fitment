package com.zxyun.order.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @des:
 * @Author: given
 * @Date 2020/3/18 14:53
 */
public class SignatureUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
    public static final String SIGN_TAG = "sign";
    public static final String KEY_TAG = "key";

    public SignatureUtil() {
    }

    public static String generatePackage(Map<String, String> map, String paternerKey) {
        String sign = generateSign(map, paternerKey);
        Map<String, String> tmap = MapUtil.order(map);
        String s2 = MapUtil.mapJoin(tmap, false, true);
        return s2 + "&" + "sign" + "=" + sign;
    }

    public static String generateSign(Map<String, String> map, String paternerKey) {
        Map<String, String> tmap = MapUtil.order(map);
        if (tmap.containsKey("sign")) {
            tmap.remove("sign");
        }

        String str = MapUtil.mapJoin(tmap, false, false);
        String md5Str = str + "&" + "key" + "=" + paternerKey;
        logger.debug("md5Str={}", md5Str);
        return DigestUtils.md5Hex(md5Str).toUpperCase();
    }

    public static String generateSign(Map<String, String> map, String signKey, String signVal, String addKstring, String addVstring) {
        Map<String, String> tmap = MapUtil.order(map);
        if (tmap.containsKey("sign")) {
            tmap.remove("sign");
        }

        String str = MapUtil.mapJoin(tmap, false, false, addKstring, addVstring);
        str = str + addVstring + (StringUtils.isBlank(signKey) ? "" : signKey + addKstring) + signVal;
        return DigestUtils.md5Hex(str).toUpperCase();
    }

    public static String generateEventMessageSignature(String token, String timestamp, String nonce) {
        String[] array = new String[]{token, timestamp, nonce};
        Arrays.sort(array);
        String s = StringUtils.join(array, "");
        return DigestUtils.sha1Hex(s);
    }

    public static boolean validateSign(Map<String, String> map, String key) {
        String localSign = generateSign(map, key);
        String remote = (String)map.get("sign");
        logger.debug("localSign={}, remote={}", localSign, remote);
        return remote == null ? false : remote.equals(localSign);
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap();
        map.put("bookDateStart", "2020-02-28");
        map.put("companyId", "693");
        map.put("bookDateEnd", "2020-02-28");
        map.put("productIds", "545,27");
        map.put("nonceStr", "14un8dsosqpn");
        map.put("timestamp", "1570704767190");
        map.put("appId", "747095637344257");
        System.out.println(generateSign(map, "vo6Q3icrDwL"));
    }
}
