package com.zxyun.order.handler.function;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 16:48
 */
public interface SupplierMethod<T> extends MethodFunction {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get();
}
