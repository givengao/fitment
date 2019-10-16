package com.zxyun.order.media.create;


import com.zxyun.order.media.OrderHandler;
import org.springframework.stereotype.Component;

/**
 * @des: 订单计算相关（包含各种活动金额计算及其优惠分摊等）
 * @Author: given
 * @Date 2019/9/26 15:23
 */
@Component
public class CreateOrderCalHandler implements OrderHandler<CreateOrderContext> {
    @Override
    public void execute(CreateOrderContext context) {

    }
}
