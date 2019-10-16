package com.zxyun.order.media.create;

import com.zxyun.order.media.OrderHandler;
import org.springframework.stereotype.Component;

/**
 * @des: 订单相关数据持久化
 * @Author: given
 * @Date 2019/9/26 15:26
 */
@Component
public class CreateOrderPersistHandler implements OrderHandler<CreateOrderContext> {

    @Override
    public void execute(CreateOrderContext context) {

    }
}
