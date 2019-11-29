package com.zxyun.order.handler.invoke;

import com.zxyun.order.handler.function.PredicateMethod;
import com.zxyun.order.handler.register.MethodRegister;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 17:02
 */
public class PredicateMethodInvoker<T> extends MethodInvoker<PredicateMethod<T>> {

    public PredicateMethodInvoker(MethodRegister<PredicateMethod<T>> methodRegister) {
        super(methodRegister);
    }

    /**
     * 同步执行
     * @param key
     * @param t
     * @return
     */
    public boolean invoke (String key, T t) {
        if (key == null) {
            return false;
        }
        PredicateMethod<T> predicateMethod = getMethod(key);
        if (predicateMethod != null) {
            return predicateMethod.test(t);
        }
        return false;
    }

    /**
     * 异步执行
     * @param key
     * @param t
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean syncInvoke (String key, T t) throws ExecutionException, InterruptedException {
        Future<Boolean> feature = executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return PredicateMethodInvoker.this.invoke(key, t);
            }
        });

        return feature.get().booleanValue();
    }
}
