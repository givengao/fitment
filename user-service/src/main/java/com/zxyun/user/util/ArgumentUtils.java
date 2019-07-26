package com.zxyun.user.util;

import java.util.*;

/**
 * @des: 集合常用判断
 * @Author: given
 * @Date 2019/7/12 10:08
 */
public class ArgumentUtils {

    /**
     * 判断空
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty (Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断空
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> boolean isNotEmpty (Collection<T> collection) {
        return !isEmpty(collection);
    }

    public static <T> List<T> emptyList () {
        return (List<T>) Collections.emptyList();
    }

    public static <K, V> Map<K, V> emptyMap () {
        return (Map<K, V>)Collections.emptyMap();
    }

    public static <T> Set<T> emptySet () {
        return (Set<T>)Collections.emptySet();
    }


//    public static <T> List<List<T>> partition(List<T> source, int n) {
//        return ListUtils.partition(source, n);
//    }

    /**
     * 对集合进行切割
     * @param source 切割的集合
     * @param n 每段切割大小
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> partition(List<T> source, int n) {

        List<List<T>> result = new ArrayList<>();

        //(先计算出余数)
        int remainder = source.size() % n;

        //然后是商
        int number = source.size() / n;

        //偏移量
        int offset = 0;

        for (int i = 0; i < n; i++) {
            List<T> value;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
}
