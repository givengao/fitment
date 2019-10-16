package com.zxyun.order.media;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @des: 订单相关处理上下文
 * @Author: given
 * @Date 2019/9/26 14:51
 */
public class OrderBaseContext<T> {

    /**过滤器索引*/
    private final ThreadLocal<AtomicInteger> filterIndex = new ThreadLocal<AtomicInteger>() {
        protected AtomicInteger initialValue() {
            return new AtomicInteger(0); 	//索引默认从零开始
        };
    };

    /**保存上下文信息*/
    private Map<String,Object> attributeMap = new ConcurrentHashMap<String,Object>();

    private T requestParam;

    /**
     * 业务异常信息
     */
    private List<OrderExVo> exVoList;

    /**
     * 是否执行成功
     */
    private Boolean isSuccess = true;

    public OrderBaseContext(T requestParam) {
        this.requestParam = requestParam;
    }

    public int getAndIncrement() {
        return filterIndex.get().getAndIncrement();
    }

    public T getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(T requestParam) {
        this.requestParam = requestParam;
    }

    /**
     * 设置信息
     * @param key
     * @return
     */
    public <T> T getAttribute(String key) {
        return (T)attributeMap.get(key);
    }

    /**
     * 获取 信息
     * @param key
     * @param value
     */
    public void setAttribute(String key,Object value) {
        if(key != null && value != null) {
            attributeMap.put(key, value);
        }
    }

    /**
     * 设置异常信息
     * @param code
     * @param msg
     */
    public final void setException(Integer code, String msg) {
        this.setExVo(OrderBaseContext.OrderExVo.error(code, msg));
    }

    public List<OrderExVo> getExVoList() {
        return exVoList;
    }

    private final void setExVo (OrderExVo exVo) {
        if (exVoList == null) {
            exVoList = new ArrayList<>();
        }
        isSuccess = false;
        this.exVoList.add(exVo);
    }

    public boolean isSuccess () {
        return isSuccess == null ? false : isSuccess.booleanValue();
    }

    public String errorMsg () {
        String errorMsg = "链路处理出现业务异常:";
        if (CollectionUtils.isNotEmpty(exVoList)) {
            errorMsg = errorMsg + exVoList.stream().map(e -> e.getMsg()).filter(e -> e != null).collect(Collectors.joining("|"));
        } else {
            errorMsg = errorMsg + "未知";
        }
        return errorMsg;
    }

    /**
     * 错误信息相关（避免抛出业务异常）
     */
    public static class OrderExVo {

        private int code;

        private String msg;

        public OrderExVo(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static OrderExVo error ( Integer code, String msg) {
            return new OrderExVo(code, msg);
        }
    }
}
