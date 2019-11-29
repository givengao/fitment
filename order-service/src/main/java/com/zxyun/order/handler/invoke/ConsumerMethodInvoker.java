package com.zxyun.order.handler.invoke;

import com.zxyun.order.handler.function.ConsumerMethod;
import com.zxyun.order.handler.register.MethodRegister;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 16:41
 */
public class ConsumerMethodInvoker<T> extends MethodInvoker<ConsumerMethod<T>> {

    public ConsumerMethodInvoker(MethodRegister<ConsumerMethod<T>> methodRegister) {
        super(methodRegister);
    }

    /**
     * 同步执行
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void invoke (String key, T t) {
        if (key == null) {
            return;
        }
        ConsumerMethod<T> consumerMethod = getMethod(key);

        if (consumerMethod != null) {
            consumerMethod.accept(t);
        }
    }

    /**
     * 异步执行
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void syncInvoke (String key, T t) {
        executorService.execute(() -> invoke(key, t));
    }
}
