package com.zxyun.order.handler.invoke;

import com.zxyun.order.handler.function.MethodFunction;
import com.zxyun.order.handler.register.MethodRegister;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 16:26
 */
public abstract class MethodInvoker<T extends MethodFunction> {

    protected MethodRegister<T> methodRegister;

    protected ExecutorService executorService = Executors.newFixedThreadPool(10);

    public MethodInvoker(MethodRegister methodRegister) {
        this.methodRegister = methodRegister;
    }

    protected <T> T getMethod (String key) {
        return (T)methodRegister.getMethod(key);
    }
}
