package com.zxyun.order.media.create;

import com.zxyun.order.media.OrderBaseContext;

/**
 * @des: 创建订单上下文
 * @Author: given
 * @Date 2019/9/26 14:57
 */
public class CreateOrderContext extends OrderBaseContext<CreateOrderRequestParam> {

    public CreateOrderContext(CreateOrderRequestParam requestParam) {
        super(requestParam);
    }

    /**订单类型：false现货，true预订*/
    public static final String ORDER_TYPE = "orderType";
}
