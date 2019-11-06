package com.zxyun.order.event;

/**
 * @des: 事件订阅者
 * @Author: given
 * @Date 2019/11/4 11:32
 */
public abstract class Subscriber {
    /**
     * 接到事件是否同步执行
     */
    protected boolean sync = true;

    /**
     * 事件订阅者
     */
    protected Subscriber() {
    }

    /**
     * 事件订阅者
     *
     * @param sync 是否同步
     */
    protected Subscriber(boolean sync) {
        this.sync = sync;
    }

    /**
     * 是否同步
     *
     * @return 是否同步
     */
    public boolean isSync() {
        return sync;
    }

    /**
     * 事件处理，请处理异常
     *
     * @param event 事件
     */
    public abstract void onEvent(Event event);
}
