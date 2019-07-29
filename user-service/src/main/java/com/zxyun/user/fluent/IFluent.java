package com.zxyun.user.fluent;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @des: 流式处理
 * @Author: given
 * @Date 2019/7/29 13:48
 */
public interface IFluent<E> extends Iterable<E> {

    /**
     * 过滤迭代器中的元素
     * @param predicate
     * @return
     */
    IFluent<E> filter (Predicate<? super E> predicate);

    /**
     * 返回迭代器的第一个元素
     * @return
     */
    Optional<E> first ();

    /**
     * 返回迭代器中前几个元素
     * @param count 从前往后数的个数
     * @return
     */
    IFluent<E> first (int count);

    /**
     * 返回迭代器的最后一个元素
     * @return
     */
    Optional<E> last ();

    /**
     * 返回迭代器中后几个元素
     * @param count 从后往前数的个数
     * @return
     */
    IFluent<E> last (int count);



    /**
     * 将此Iterable转换为包含类型为T的对象的新变量
     * @param function
     * @param <T>
     * @return
     */
    <T> IFluent<T> map (Function<? super E, ? extends T> function);

    /**
     * 去重
     * @return
     */
    IFluent<E> distinct ();

    /**
     * 将此Iterable转换为包含的对象的新List集合
     * @return
     */
    List<E> toList ();

    /**
     * 转换成map, key, value自定义
     * @param keyMapper
     * @param valueMapper
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Map<K, V> toMap (Function<? super E, ? extends K> keyMapper,
                            Function<? super E, ? extends V> valueMapper);

    /**
     * 转换成map,key自定义
     * @param keyMapper
     * @param <K>
     * @return
     */
    <K> Map<K, E> toMap (Function<? super E, ? extends K> keyMapper);

    /**
     * 将迭代器集合进行分组 key -> value 一对多
     * @param classifier
     * @param <K>
     * @return
     */
    <K> Map<K,List<E>> group (Function<? super E, ? extends K> classifier);

    /**
     * 将迭代器集合进行分组 key -> value 一对多
     * @param classifier
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Map<K,List<V>> group (Function<? super E, ? extends K> classifier,
                                 Function<? super E, ? extends V> valueMapper);

    /**
     * 排序
     * @param comparator
     * @return
     */
    IFluent<E> sorted (Comparator<? super E> comparator);

    /**
     * 集合复制
     * @param iterable
     * @param <V>
     * @return
     */
    static <V> List<V> copyToList(Iterable<V> iterable) {
        List<V> copy = new ArrayList<>();
        Iterator<V> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            copy.add(iterator.next());
        }
        return copy;
    }
}
