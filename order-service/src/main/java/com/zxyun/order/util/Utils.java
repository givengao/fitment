
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.zxyun.order.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public final class Utils {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[0-9]{10}$");
    private static final Pattern VALIDATE_PATTERN = Pattern.compile("^[0-9]{6}$");
    private static final Pattern COOKCODE_PATTERN = Pattern.compile("^[1-4]{4}$");
    private static final Pattern IMEI_PATTERN = Pattern.compile("^[0-9]{15}$");
    private static final Pattern PRODUCT_SN_PATTERN = Pattern.compile("^[0-9]{20}$");
    private static final Pattern HH_MM_TIME = Pattern.compile("^(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$");

    public Utils() {
    }

    public static String getValidateCode() {
        return createSN(6);
    }

    public static String createSN(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder("");

        for(int i = 0; i < length; ++i) {
            builder.append(String.valueOf(random.nextInt(10)));
        }

        return builder.toString();
    }

    public static List<String> removeDupli(List<String> keyList) {
        Hashtable<String, String> table = new Hashtable();
        if (keyList != null && !keyList.isEmpty()) {
            Iterator var2 = keyList.iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                table.put(key, key);
            }

            List<String> newKeyList = new ArrayList();
            Set<String> keys = table.keySet();
            Iterator var4 = keys.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                newKeyList.add(key);
            }

            return newKeyList;
        } else {
            return new ArrayList();
        }
    }

    public static String createOrderSN() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + createSN(9) + createSN(9);
    }

    public static String toTakeNumber(long takeNumber) {
        String temp = String.valueOf(takeNumber);
        int length = temp.length();
        StringBuilder builder = new StringBuilder("");

        for(int i = 0; i < 4 - length; ++i) {
            builder.append("0");
        }

        builder.append(temp);
        return builder.toString();
    }

    public static String formatFloat(float value) {
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        return decimalFormat.format((double)value);
    }

    public static void main(String[] args) {
        System.out.println(createSN(6));
    }
}
