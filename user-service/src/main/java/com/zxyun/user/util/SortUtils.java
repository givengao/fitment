package com.zxyun.user.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @des: 排序算法
 * @Author: given
 * @Date 2019/7/26 14:19
 */
public class SortUtils {

    /**
     * 冒泡排序
     * @param list
     * @param comparator
     * @param <V>
     * @return
     */
    public static <V> List<V> bubbleSort (List<? extends V> list, Comparator<? super V> comparator) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        V temp = null;
        int size = list.size();
        V[] vs = (V[]) list.toArray();

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (comparator.compare(vs[j], vs[j + 1]) > 0) {
                    temp = vs[j];
                    vs[j] = vs[j + 1];
                    vs[j + 1] = temp;
                }
            }
        }

        return Arrays.asList(vs);
    }

    /**
     * 选择排序
     * @param list
     * @param comparator
     * @param <V>
     * @return
     */
    public static <V> List<V> selectSort (List<? extends V> list, Comparator<? super V> comparator) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        V temp = null;
        int size = list.size();
        V[] vs = (V[]) list.toArray();

        for (int i = 0; i < size - 1; i++) {
            // 记录每一次循环最小值的位置
            int pos = i;
            for (int j = i + 1; j < size; j++) {
                if (comparator.compare(vs[pos], vs[j]) > 0) {
                    pos = j;
                }
            }

            // 最小的数与第i个位置的数交换
            temp = vs[i];
            vs[i] = vs[pos];
            vs[pos] = temp;
        }

        return Arrays.asList(vs);
    }

    /**
     * 插入排序
     * @param list
     * @param comparator
     * @param <V>
     * @return
     */
    public static <V> List<V> insertSort (List<? extends V> list, Comparator<? super V> comparator) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        V temp = null;
        int size = list.size();
        V[] vs = (V[]) list.toArray();

        for (int i = 1; i < size; i++) {
            temp = vs[i];
            for (int j = 0; j < i; j++) {
                if (comparator.compare(temp, vs[j]) < 0) {
                    for (int k = i; k > j; k--) {
                        vs[k] = vs[k - 1];
                    }
                    vs[j] = temp;
                    break;
                }
            }
        }

        return Arrays.asList(vs);
    }

    /**
     * 适用大数据量排序
     * @param list
     * @param comparator
     * @param <V>
     * @return
     */
    public static <V> List<V> streamSort (List<? extends V> list, Comparator<? super V> comparator) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        return list.stream().sorted(comparator).collect(Collectors.toList());
    }



    public static void main (String[] args) {

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            integers.add((int)(1+Math.random()*(100-1+1)));
        }

        long start = System.currentTimeMillis();
        AggregationUtils.sort(integers, Comparator.comparingInt(Integer::intValue)).forEach(e -> System.out.println(e + "\n"));

        System.out.println("------------------\n");

        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}
