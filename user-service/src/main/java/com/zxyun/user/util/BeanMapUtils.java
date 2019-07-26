package com.zxyun.user.util;

import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BeanMapUtils {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BeanMapUtils.class);

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj, String[] excludeFields) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);

                if (excludeFields != null && Arrays.stream(excludeFields).anyMatch(e -> field.getName().equals(e))) {
                    continue;
                }

                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception ex) {
            logger.error("object convert to map fail, className:{}, cause:{}",obj.getClass().getName(), ex);
        }
        return map;
    }

    /**
     * Map转成实体对象
     *
     * @param map   实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();

                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception ex) {
            logger.error("map convert to Object fail, className:{}, cause:{}",obj.getClass().getName(), ex);
        }
        return obj;
    }
}
