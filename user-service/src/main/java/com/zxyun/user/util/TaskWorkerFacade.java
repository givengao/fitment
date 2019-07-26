package com.zxyun.user.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @des:定时任务执行操作
 * @Author: given
 * @Date 2019/7/15 17:57
 */
public abstract class TaskWorkerFacade<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskWorkerFacade.class);

    private TaskEnum taskEnum;

    public TaskWorkerFacade(TaskEnum taskEnum) {
        this.taskEnum = taskEnum;
    }

    private Map<TaskEnum, Consumer<Collection<T>>> consumerMap = new ConcurrentHashMap<>();

    {
        consumerMap.put(TaskEnum.BATCH, getBatchConsumer());
        consumerMap.put(TaskEnum.LOOP, getLoopConsumer());
    }

    protected enum TaskEnum {
        BATCH,
        LOOP,
        ;
    }

    /**
     * 查询源数据
     * @return
     */
    protected abstract Collection<T> queryData ();

    /**
     * 单次处理
     * @param collection
     */
    protected abstract void doTask (Collection<T> collection);

    /**
     * 批次处理
     * @param t
     */
    protected abstract void doTask (T t);

    /**
     * 获取源数据执行对应业务
     */
    public void action () {
        Long start = System.currentTimeMillis();

        LOGGER.info("task {} do start", this.getClass().getSimpleName());

        Collection<T> collection = queryData();

        if (ArgumentUtils.isEmpty(collection)) {
            LOGGER.info("task {} query data is empty", this.getClass().getSimpleName());
        }

        Consumer consumer = consumerMap.get(taskEnum);

        consumer.accept(collection);

        LOGGER.info("task {} do end, size:{}, time consume:{}", this.getClass().getSimpleName(), collection.size(),
                System.currentTimeMillis() - start);
    }

    /**
     * 批次处理执行对象
     * @return
     */
    private Consumer<Collection<T>> getLoopConsumer () {
        return (data) -> {
            Iterator<T> iterator = data.iterator();

            while (iterator.hasNext()) {
                doTask(iterator.next());
            }
        };
    }

    /**
     * 单次处理执行对象
     * @return
     */
    private Consumer<Collection<T>> getBatchConsumer () {
        return (data) -> {
            doTask(data);
        };
    }
}
