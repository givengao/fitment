package com.zxyun.order.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.*;

/**
 * @des:
 * @Author: given
 * @Date 2020/3/18 14:55
 */
public class MapUtil {
    public MapUtil() {
    }

    public static Map<String, String> order(Map<String, String> map) {
        List<Map.Entry<String, String>> infoIds = new ArrayList(map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return ((String)o1.getKey()).toString().compareTo((String)o2.getKey());
            }
        });
        Map<String, String> tempMap = new LinkedHashMap(map.size());

        for(int i = 0; i < infoIds.size(); ++i) {
            Map.Entry<String, String> item = (Map.Entry)infoIds.get(i);
            tempMap.put(item.getKey(), item.getValue());
        }

        return tempMap;
    }

    public static Map<String, String> objectToMap(Object object, String... ignore) {
        Map<String, String> tempMap = null;

        for(Class clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    tempMap = new LinkedHashMap(fields.length);
                    Field[] var5 = fields;
                    int var6 = fields.length;

                    for(int var7 = 0; var7 < var6; ++var7) {
                        Field field = var5[var7];
                        field.setAccessible(true);
                        if (!Modifier.isStatic(field.getModifiers())) {
                            boolean ig = false;
                            if (ignore != null && ignore.length > 0) {
                                String[] var10 = ignore;
                                int var11 = ignore.length;

                                for(int var12 = 0; var12 < var11; ++var12) {
                                    String i = var10[var12];
                                    if (i.equals(field.getName())) {
                                        ig = true;
                                        break;
                                    }
                                }
                            }

                            if (!ig) {
                                Object o = null;

                                try {
                                    o = field.get(object);
                                } catch (IllegalArgumentException var14) {
                                    var14.printStackTrace();
                                } catch (IllegalAccessException var15) {
                                    var15.printStackTrace();
                                }

                                tempMap.put(field.getName(), o == null ? "" : o.toString());
                            }
                        }
                    }
                }
            } catch (Exception var16) {
                var16.printStackTrace();
            }
        }

        return tempMap;
    }

    public static Map<String, String> objectToMapNoNullValue(Object object, String... ignore) {
        Map<String, String> tempMap = null;

        for(Class clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    tempMap = new LinkedHashMap(fields.length);
                    Field[] var5 = fields;
                    int var6 = fields.length;

                    for(int var7 = 0; var7 < var6; ++var7) {
                        Field field = var5[var7];
                        field.setAccessible(true);
                        if (!Modifier.isStatic(field.getModifiers())) {
                            boolean ig = false;
                            if (ignore != null && ignore.length > 0) {
                                String[] var10 = ignore;
                                int var11 = ignore.length;

                                for(int var12 = 0; var12 < var11; ++var12) {
                                    String i = var10[var12];
                                    if (i.equals(field.getName())) {
                                        ig = true;
                                        break;
                                    }
                                }
                            }

                            if (!ig) {
                                Object o = null;

                                try {
                                    o = field.get(object);
                                } catch (IllegalArgumentException var14) {
                                    var14.printStackTrace();
                                } catch (IllegalAccessException var15) {
                                    var15.printStackTrace();
                                }

                                if (o != null) {
                                    tempMap.put(field.getName(), o.toString());
                                }
                            }
                        }
                    }
                }
            } catch (Exception var16) {
                var16.printStackTrace();
            }
        }

        return tempMap;
    }

    public static HashMap<String, Object> objectToObjectMap(Object object, String... ignore) {
        if (object == null) {
            return null;
        } else {
            HashMap<String, Object> tempMap = new LinkedHashMap();

            for(Class clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
                try {
                    Field[] fields = clazz.getDeclaredFields();
                    if (fields != null && fields.length > 0) {
                        Field[] var5 = fields;
                        int var6 = fields.length;

                        for(int var7 = 0; var7 < var6; ++var7) {
                            Field field = var5[var7];
                            field.setAccessible(true);
                            if (!Modifier.isStatic(field.getModifiers())) {
                                boolean ig = false;
                                if (ignore != null && ignore.length > 0) {
                                    String[] var10 = ignore;
                                    int var11 = ignore.length;

                                    for(int var12 = 0; var12 < var11; ++var12) {
                                        String i = var10[var12];
                                        if (i.equals(field.getName())) {
                                            ig = true;
                                            break;
                                        }
                                    }
                                }

                                if (!ig) {
                                    Object o = null;

                                    try {
                                        o = field.get(object);
                                    } catch (Exception var14) {
                                        var14.printStackTrace();
                                    }

                                    if (o != null) {
                                        tempMap.put(field.getName(), o);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception var15) {
                    var15.printStackTrace();
                }
            }

            return tempMap;
        }
    }

    public static String mapJoin(Map<String, String> map, boolean keyLower, boolean valueUrlencode, boolean isbase64, String addKstring, String addVstring) {
        int addVstringLen = 0;
        if (null == addKstring) {
            addKstring = "";
        }

        if (null == addVstring) {
            addVstring = "";
        } else {
            addVstringLen = addVstring.length();
        }

        StringBuilder stringBuilder = new StringBuilder();
        Iterator var8 = map.keySet().iterator();

        while(true) {
            String key;
            do {
                do {
                    if (!var8.hasNext()) {
                        if (stringBuilder.length() > 0 && addVstringLen > 0) {
                            stringBuilder.deleteCharAt(stringBuilder.length() - addVstringLen);
                        }

                        return stringBuilder.toString();
                    }

                    key = (String)var8.next();
                } while(map.get(key) == null);
            } while("".equals(map.get(key)));

            try {
                String temp = key.endsWith("_") && key.length() > 1 ? key.substring(0, key.length() - 1) : key;
                stringBuilder.append(keyLower ? temp.toLowerCase() : temp).append(addKstring).append(isbase64 ? CodeUtils.encodeBase64(((String)map.get(key)).getBytes()) : (valueUrlencode ? URLEncoder.encode((String)map.get(key), "utf-8").replace("+", "%20") : (String)map.get(key))).append(addVstring);
            } catch (UnsupportedEncodingException var11) {
                var11.printStackTrace();
            }
        }
    }

    public static String mapJoin(Map<String, String> map, boolean keyLower, boolean valueUrlencode) {
        return mapJoin(map, keyLower, valueUrlencode, "=", "&");
    }

    public static String mapJoin(Map<String, String> map, boolean keyLower, boolean valueUrlencode, String addKstring, String addVstring) {
        return mapJoin(map, keyLower, valueUrlencode, false, addKstring, addVstring);
    }

    public static String mapOrderAndJoin(Map<String, String> map, boolean keyLower, boolean valueUrlencode) {
        return mapJoin(order(map), keyLower, valueUrlencode);
    }

    public static String mapOrderAndJoin(Map<String, String> map, boolean keyLower, boolean valueUrlencode, String addKstring, String addVstring) {
        return mapJoin(order(map), keyLower, valueUrlencode, addKstring, addVstring);
    }
}
