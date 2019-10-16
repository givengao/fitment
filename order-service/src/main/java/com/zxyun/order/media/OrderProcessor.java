package com.zxyun.order.media;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @des: 订单处理入口
 * @Author: given
 * @Date 2019/8/21 13:53
 */
@Component
public class OrderProcessor {

    /**
     * 处理
     * @param handlers 处理类集合
     * @param context 处理上下文
     * @param function 个性化处理函数(得到链路执行的最终结果做自己的特殊处理)
     * @param <T>
     * @param <R>
     * @return
     */
    public <T extends OrderBaseContext, R> R process (List<? extends OrderHandler<? extends OrderBaseContext>> handlers, T context, Function<T, ? extends R> function) {
        if (CollectionUtils.isEmpty(handlers)) {
            return null;
        }

        List<OrderHandler> unExecutes = new ArrayList<>();
        int index = context.getAndIncrement();
        int size = handlers.size();
        do {
            OrderHandler handler = handlers.get(index);
            try {
                //执行过滤器
                handler.execute(context);
                //当有业务异常时直接中断链路执行
                if (!context.isSuccess()) {
                    if (!handler.ignoreEx(context)) {
                        unExecutes.add(handler);
                        break;
                    }
                }
            } catch(Exception ex) {
                if (!handler.ignoreEx(context)) {
                    unExecutes.add(handler);
                    throw ex;
                }
            } finally {
                //回滚操作
                if (!CollectionUtils.isEmpty(unExecutes)) {
                    for (OrderHandler unHandler : unExecutes) {
                        unHandler.unExecute(context);
                    }
                }
            }
            index = context.getAndIncrement();
        } while (index < size);

        //返回最后一次执行的结果
        if (function != null) {
            return function.apply(context);
        }

        return null;
    }

    /**
     * 处理(需要做实物回滚，相关业务处理需要抛出异常)
     * @param handlers
     * @param context
     */
    public void process (List<? extends OrderHandler<? extends OrderBaseContext>> handlers, OrderBaseContext context) {
        process(handlers, context, null);
    }
}
