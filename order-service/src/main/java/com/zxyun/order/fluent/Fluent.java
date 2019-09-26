package com.zxyun.order.fluent;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @des: 流式处理
 * @Author: given
 * @Date 2019/7/29 15:23
 */
public class Fluent<E> implements IFluent<E> {

    private final Iterable<E> iterable;

    public Fluent(Iterable<E> iterable) {
        this.iterable = iterable;
    }

    public static <E> Fluent<E> from(Iterable<E> iterable) {
        List<E> copy = IFluent.copyToList(iterable);

        return new Fluent<>(copy);
    }

    @Override
    public IFluent<E> filter(Predicate<? super E> predicate) {
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            E nextElement = iterator.next();

            if (!predicate.test(nextElement)) {
                iterator.remove();
            }
        }

        return this;
    }

    @Override
    public Optional<E> first() {
        Iterator<E> iterator = first(1).iterator();

        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    @Override
    public IFluent<E> first(int count) {
        Iterator<E> iterator = iterator();

        int currentCount = 0;

        while (iterator.hasNext()) {
            iterator.next();
            if (currentCount >= count) {
                iterator.remove();
            }

            currentCount++;
        }

        return this;
    }

    @Override
    public Optional<E> last() {
        Iterator<E> iterator = last(1).iterator();

        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    @Override
    public IFluent<E> last(int count) {
        int elementsCount = getRemainingElementsCount();

        Iterator<E> iterator = iterator();
        int currentIndex = 0;

        while (iterator.hasNext()) {
            iterator.next();
            if (currentIndex < elementsCount - count) {
                iterator.remove();
            }
            currentIndex++;
        }

        return this;
    }

    @Override
    public <T> IFluent<T> map(Function<? super E,? extends T> function) {
        List<T> temporaryList = new ArrayList<>();
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            temporaryList.add(function.apply(iterator.next()));
        }

        return from(temporaryList);
    }

    @Override
    public IFluent<E> distinct() {
        List<E> exists = new ArrayList<>();
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (exists.contains(next)) {
                iterator.remove();
            }

            exists.add(next);
        }

        return this;
    }

    @Override
    public List<E> toList() {
        List<E> copy = IFluent.copyToList(iterable);

        return copy;
    }

    @Override
    public <K, V> Map<K,V> toMap(Function<? super E,? extends K> keyMapper, Function<? super E,? extends V> valueMapper) {
        Map<K,V> toMap = new HashMap<>();

        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            K key = keyMapper.apply(next);

            V value = valueMapper.apply(next);

            toMap.putIfAbsent(key, value);
        }

        return toMap;
    }

    @Override
    public <K> Map<K,E> toMap(Function<? super E,? extends K> keyMapper) {
        return toMap(keyMapper, e -> e);
    }

    @Override
    public <K> Map<K,List<E>> group(Function<? super E,? extends K> classifier) {

        return group(classifier, e -> e);
    }

    @Override
    public <K, V> Map<K,List<V>> group(Function<? super E,? extends K> classifier, Function<? super E,? extends V> valueMapper) {
        Map<K,List<V>> listMap = new HashMap<>();
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            K key = classifier.apply(next);

            V value = valueMapper.apply(next);

            List<V> vs = listMap.get(key);

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

    @Override
    public IFluent<E> sorted(Comparator<? super E> comparator) {
        List<E> copy = IFluent.copyToList(iterable);

        return from(copy.stream().sorted(comparator).collect(Collectors.toList()));
    }

    @Override
    public Iterator<E> iterator() {
        return iterable.iterator();
    }

    /**
     * @return the count of remaining objects of the current Iterable
     */
    public final int getRemainingElementsCount() {
        int counter = 0;
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            iterator.next();

            counter++;
        }

        return counter;
    }
}
