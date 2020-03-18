package com.zxyun.order.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @des: List聚合操作相关
 * @Author: given
 * @Date 2019/7/25 10:20
 */
public class AggregationUtils {

    /**
     * 将list集合进行分组 key -> value 一对多
     * @param list 源数据
     * @param classifier 指定生成对应key数据函数
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K,List<V>> groupToMapList (List<? extends V> list,
                                                        Function<? super V, ? extends K> classifier) {
        return toMapList(list, classifier, e -> e);
    }

    /**
     * 将list集合进行分组并且生成指定value数据 key -> value 一对多
     * @param list 源数据
     * @param keyMapper 指定生成对应key数据函数
     * @param valueMapper 指定生成对应value数据函数
     * @param <K>
     * @param <V>
     * @param <U>
     * @return
     */
    public static <K, V, U> Map<K,List<U>> toMapList (List<? extends V> list,
                                                      Function<? super V, ? extends K> keyMapper,
                                                      Function<? super V, ? extends U> valueMapper) {
        if (list == null || list.isEmpty()) {
            return new HashMap<>();
        }

        Map<K,List<U>> listMap = new HashMap<>();

        for (V v : list) {

            if (v == null) {
                continue;
            }

            K key = keyMapper.apply(v);

            U value = valueMapper.apply(v);

            List<U> vs = listMap.get(key);

            if (vs != null && !vs.isEmpty()) {
                vs.add(value);
            } else {
                vs = new ArrayList<>();
                vs.add(value);
            }

            listMap.put(key, vs);
        }

        return listMap;
    }

    /**
     * 将list集合进行分组并且生成指定value数据 key -> value 一对一
     * @param list 源数据
     * @param keyMapper 指定生成对应key数据函数
     * @param valueMapper 指定生成对应value数据函数
     * @param <K>
     * @param <V>
     * @param <U>
     * @return
     */
    public static <K, V, U> Map<K,U> toMap (List<? extends V> list,
                                            Function<? super V, ? extends K> keyMapper,
                                            Function<? super V, ? extends U> valueMapper) {
        if (list == null || list.isEmpty()) {
            return new HashMap<>();
        }

        Map<K,U> toMap = new HashMap<>();

        for (V v : list) {
            K key = keyMapper.apply(v);

            U value = valueMapper.apply(v);

            toMap.putIfAbsent(key, value);
        }

        return toMap;
    }

    /**
     * 将list集合进行分组 key -> value 一对一
     * @param list 源数据
     * @param keyMapper 指定生成对应key数据函数
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K,V> toMap (List<? extends V> list,
                                         Function<? super V, ? extends K> keyMapper) {
        return toMap(list, keyMapper, e -> e);
    }

    /**
     * 根据对应规则过滤指定集合数据并生成对应值集合
     * @param list 源数据
     * @param mapper 指定生成对应数据函数
     * @param predicate 过滤规则
     * @param <U>
     * @param <V>
     * @return
     */
    public static <U, V> List<U> toList(List<? extends V> list,
                                        Function<? super V, ? extends U> mapper,
                                        Predicate<? super V> predicate) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        List<U> toList = new ArrayList<>();

        for (V v : list) {
            if (v == null) {
                continue;
            }

            U u = mapper.apply(v);

            if (u == null) {
                continue;
            }

            if (predicate == null) {
                toList.add(u);
            } else {
                if (predicate.test(v)) {
                    toList.add(u);
                }
            }
        }

        return toList;
    }

    /**
     * 指定集合数据并生成对应值集合
     * @param list 源数据
     * @param mapper 指定生成对应数据函数
     * @param <U>
     * @param <V>
     * @return
     */
    public static <U, V> List<U> toList(List<? extends V> list,
                                        Function<? super V, ? extends U> mapper) {
        return toList(list, mapper, null);
    }

    /**
     * 根据规则过滤集合
     * @param list 源数据
     * @param predicate 过滤规则
     * @param <V>
     * @return
     */
    public static <V> List<V> filter(List<? extends V> list,
                                     Predicate<? super V> predicate) {
        return toList(list, e -> e, predicate);
    }

    /**
     * 生成对应值集合并且保证值不重复
     * @param list 源数据
     * @param mapper 指定生成对应数据函数
     * @param <U>
     * @param <V>
     * @return
     */
    public static <U, V> List<U> toDistinctList (List<? extends V> list,
                                                 Function<? super V, ? extends U> mapper) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        List<U> toList = new ArrayList<>();

        for (V v : list) {
            if (v == null) {
                continue;
            }

            U u = mapper.apply(v);

            if (u == null) {
                continue;
            }

            if (!toList.contains(u)) {
                toList.add(u);
            }
        }

        return toList;
    }

    /**
     * 集合排序获得对应值得集合
     * @param list
     * @param mapper
     * @param comparator
     * @param <U>
     * @param <V>
     * @return
     */
    public static <U, V> List<U> sort (List<? extends V> list,
                                       Function<? super V, ? extends U> mapper,
                                       Comparator<? super U> comparator) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        return list.stream().map(mapper).sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 集合排序获取本身数据集合
     * @param list
     * @param comparator
     * @param <V>
     * @return
     */
    public static <V> List<V> sort (List<? extends V> list,
                                    Comparator<? super V> comparator) {
        return sort(list, e -> e, comparator);
    }

    /**
     * 不同对象间根据key关联匹配填充值
     * @param source 来源
     * @param dest 目标
     * @param sKeyMapper key关联取值函数
     * @param dKeyMapper key关联取值函数
     * @param biConsumer 最终匹配处理
     * @param <S>
     * @param <D>
     * @param <K>
     */
    public static <S, D, K> void match (List<? extends S> source, List<? extends D> dest,
                                        Function<? super S, ? extends K> sKeyMapper,
                                        Function<? super D, ? extends K> dKeyMapper,
                                        BiConsumer<? super S, ? super D> biConsumer) {
        if (isEmpty(source) || isEmpty(dest) || sKeyMapper == null || dKeyMapper == null || biConsumer == null) {
            return;
        }
        Map<K, S> ksMap = toMap(source, sKeyMapper);
        for (D d : dest) {
            K key = dKeyMapper.apply(d);
            S s = ksMap.get(key);
            if (s != null) {
                biConsumer.accept(s, d);
            }
        }
    }

    /**
     * 不同对象间根据key关联匹配填充值
     * @param sourceSupplier 取来源数据函数
     * @param destSupplier 取目标数据函数
     * @param sKeyMapper key关联取值函数
     * @param dKeyMapper key关联取值函数
     * @param biConsumer 最终匹配处理
     * @param <S>
     * @param <D>
     * @param <K>
     */
    public static <S, D, K> void match (Supplier<List<? extends S>> sourceSupplier,
                                        Supplier<List<? extends D>> destSupplier,
                                        Function<? super S, ? extends K> sKeyMapper,
                                        Function<? super D, ? extends K> dKeyMapper,
                                        BiConsumer<? super S, ? super D> biConsumer) {
        List<? extends S> source = sourceSupplier.get();
        List<? extends D> dest = destSupplier.get();
        match(source, dest, sKeyMapper, dKeyMapper, biConsumer);
    }


    private static boolean isEmpty (Collection collection) {
        return (collection == null || collection.isEmpty());
    }
}
