package com.zxyun.order.media.create;

import com.zxyun.order.media.OrderHandler;
import org.springframework.stereotype.Component;

/**
 * @des: 关联业务处理
 * @Author: given
 * @Date 2019/9/26 15:21
 */
@Component
public class CreateOrderBusCheckHandler implements OrderHandler<CreateOrderContext> {

    @Override
    public void execute(CreateOrderContext context) {

    }
}
