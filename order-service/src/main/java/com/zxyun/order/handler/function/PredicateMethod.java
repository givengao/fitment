package com.zxyun.order.handler.function;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 17:01
 */
public interface PredicateMethod<T> extends MethodFunction {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t);
}
