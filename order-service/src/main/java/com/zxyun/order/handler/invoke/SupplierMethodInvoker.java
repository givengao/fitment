package com.zxyun.order.handler.invoke;

import com.zxyun.order.handler.function.SupplierMethod;
import com.zxyun.order.handler.register.MethodRegister;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 16:50
 */
public class SupplierMethodInvoker<T> extends MethodInvoker<SupplierMethod<T>> {

    public SupplierMethodInvoker(MethodRegister<SupplierMethod<T>> methodRegister) {
        super(methodRegister);
    }

    /**
     * 同步执行
     * @param key
     * @return
     */
    public T invoke (String key) {
        if (key == null) {
            return null;
        }
        SupplierMethod<T> supplierMethod = getMethod(key);

        if (supplierMethod != null) {
            return supplierMethod.get();
        }
        return null;
    }

    /**
     * 异步执行
     * @param key
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public T syncInvoke (String key) throws ExecutionException, InterruptedException {
        Future<T> feature = executorService.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return SupplierMethodInvoker.this.invoke(key);
            }
        });

        return feature.get();
    }
}
