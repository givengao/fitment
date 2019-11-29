package com.zxyun.order.handler.function;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 16:38
 */
public interface ConsumerMethod<T> extends MethodFunction {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);
}
