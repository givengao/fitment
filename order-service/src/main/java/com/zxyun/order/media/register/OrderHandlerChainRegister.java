package com.zxyun.order.media.register;

import com.zxyun.order.media.OrderBaseContext;
import com.zxyun.order.media.OrderHandler;
import com.zxyun.order.media.create.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @des: 订单业务处理链注册
 * @Author: given
 * @Date 2019/9/11 17:23
 */
@Component
public class OrderHandlerChainRegister implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderHandlerChainRegister.class);

    /**
     * 创建订单处理链
     */
    public static final List<OrderHandler<? extends OrderBaseContext>> CREATE_ORDER = new ArrayList<>();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        synchronized (this) {
            //注册创建订单处理链
            registerCreateOrderChain();
        }
    }

    /**
     * 创建订单处理器注册
     */
    protected void registerCreateOrderChain () {
        CREATE_ORDER.add(applicationContext.getBean(CreateOrderValidateHandler.class));
        CREATE_ORDER.add(applicationContext.getBean(CreateOrderBusCheckHandler.class));
        CREATE_ORDER.add(applicationContext.getBean(CreateOrderCalHandler.class));
        CREATE_ORDER.add(applicationContext.getBean(CreateOrderPersistHandler.class));
        CREATE_ORDER.add(applicationContext.getBean(CreteOrderPushHandler.class));

        LOGGER.info("#####################(新预定)创建订单调用链顺序########################");
        int i = 0;
        for (OrderHandler orderHandler : CREATE_ORDER) {
            LOGGER.info("{}、{}",++i,orderHandler.getClass().getName());
        }
        LOGGER.info("##########################################################");
    }
}
