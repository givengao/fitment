package com.zxyun.order.handler;

/**
 * 1.校验。。。
 * 2.检查。。。
 * 3.计算。。。
 * 4.入库。。。
 * 5.结果分发。。。
 * @param <T>
 */
public interface OrderHandler<T extends OrderContext> {

    /**
     * 执行
     * @param t
     */
    void execute (T t);

    /**
     * 回滚
     * @param t
     */
    void rollBack (T t);

    /**
     * 最终执行
     * @param t
     */
    void executeFinal (T t);

    /**
     * 是否忽略异常
     * @return
     */
    default boolean ignoreEx () {
        return false;
    }

    /**
     * 业务回调
     * @param t
     */
    void callBack (T t);

    /**
     * 对异常有特殊处理
     * @param t
     */
    void callAfterException (T t);
}
