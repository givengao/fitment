package com.zxyun.order.media.create;


import com.zxyun.order.media.OrderHandler;
import org.springframework.stereotype.Component;

/**
 * @des: 创建订单消息推送
 * @Author: given
 * @Date 2019/9/26 15:34
 */
@Component
public class CreteOrderPushHandler implements OrderHandler<CreateOrderContext> {
    @Override
    public void execute(CreateOrderContext context) {

    }
}
