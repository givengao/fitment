package com.zxyun.order.export.covert;

/**
 * @des:
 * @Author: given
 * @Date 2019/9/9 7:55
 */
public interface Covert<S,D> {
    D covert (S s);
}
