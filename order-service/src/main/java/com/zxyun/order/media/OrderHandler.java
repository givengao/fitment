package com.zxyun.order.media;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @des: 订单处理器
 * @Author: given
 * @Date 2019/8/21 13:51
 */
public interface OrderHandler<T extends OrderBaseContext> {

    Logger LOGGER = LoggerFactory.getLogger(OrderHandler.class);

    /**
     * 线程池用于异步执行任务
     */
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 执行
     * @param context
     */
    void execute(final T context);

    /**
     * 回滚
     * @param context
     */
    default void unExecute(final T context) {

    }

    /**
     * 忽略异常
     * @param context
     * @return
     */
    default boolean ignoreEx(final T context) {
        return false;
    }
}
