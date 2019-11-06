package com.zxyun.order.event;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @des: 事件总线
 * @Author: given
 * @Date 2019/11/4 11:30
 */
public class EventBus {
    private static final Logger LOGGER           = LoggerFactory.getLogger(EventBus.class);

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    /**
     * 某中事件的订阅者
     */
    private final static ConcurrentMap<Class<? extends Event>,CopyOnWriteArraySet<Subscriber>> SUBSCRIBER_MAP = new ConcurrentHashMap<Class<? extends Event>, CopyOnWriteArraySet<Subscriber>>();

    /**
     * 注册一个订阅者
     *
     * @param eventClass 事件类型
     * @param subscriber 订阅者
     */
    public static void register(Class<? extends Event> eventClass, Subscriber subscriber) {
        CopyOnWriteArraySet<Subscriber> set = SUBSCRIBER_MAP.get(eventClass);
        if (set == null) {
            set = new CopyOnWriteArraySet<Subscriber>();
            CopyOnWriteArraySet<Subscriber> old = SUBSCRIBER_MAP.putIfAbsent(eventClass, set);
            if (old != null) {
                set = old;
            }
        }
        set.add(subscriber);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Register subscriber: {} of event: {}.", subscriber, eventClass);
        }
    }

    /**
     * 反注册一个订阅者
     *
     * @param eventClass 事件类型
     * @param subscriber 订阅者
     */
    public static void unRegister(Class<? extends Event> eventClass, Subscriber subscriber) {
        CopyOnWriteArraySet<Subscriber> set = SUBSCRIBER_MAP.get(eventClass);
        if (set != null) {
            set.remove(subscriber);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("UnRegister subscriber: {} of event: {}.", subscriber, eventClass);
            }
        }
    }

    public void post (final Event event) {
        CopyOnWriteArraySet<Subscriber> subscribers = SUBSCRIBER_MAP.get(event.getClass());
        if (CollectionUtils.isNotEmpty(subscribers)) {
            for (final Subscriber subscriber : subscribers) {
                if (subscriber.isSync()) {
                    handleEvent(subscriber, event);
                } else { // 异步
                    final ExecutorService finalExecutorService = executorService;
                    try {
                        finalExecutorService.execute(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            handleEvent(subscriber, event);
                                        } finally {

                                        }
                                    }
                                });
                    } catch (RejectedExecutionException e) {
                        LOGGER.warn("This queue is full when post event to async execute, queue size is " +
                                        ", please optimize this async thread pool of eventbus.");
                    }
                }
            }
        }
    }

    private static void handleEvent(final Subscriber subscriber, final Event event) {
        try {
            subscriber.onEvent(event);
        } catch (Throwable e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Handle " + event.getClass() + " error", e);
            }
        }
    }
}
