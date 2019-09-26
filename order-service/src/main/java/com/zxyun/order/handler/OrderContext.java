package com.zxyun.order.handler;

public class OrderContext <T> {

    private final static OrderContext DEFAULT_CONTEXT = new OrderContext() {
        public Long getTimeOut() {
            return 0L;
        }

        public String getBusinessName() {
            return "null";
        }
    };

    private T data;

    private Long TimeOut;

    private String businessName;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(Long timeOut) {
        TimeOut = timeOut;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
