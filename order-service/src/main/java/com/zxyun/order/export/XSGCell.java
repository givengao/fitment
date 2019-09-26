package com.zxyun.order.export;

import java.util.function.Function;

/**
 * @des: 单元格属性格式配置
 * @Author: given
 * @Date 2019/9/6 17:42
 */
public class XSGCell<T> {
    /**
     * 单元格头名称
     */
    private String headerName;

    /**
     * 单元格取值函数
     */
    private Function<? super T, String> bodyFunction;

    /**
     * 单元格对其格式
     */
    private String align;

    public XSGCell(String headerName, Function<? super T,String> bodyFunction, String align) {
        this.headerName = headerName;
        this.bodyFunction = bodyFunction;
        this.align = align;
    }

    public XSGCell(String headerName, Function<? super T,String> bodyFunction) {
        this.headerName = headerName;
        this.bodyFunction = bodyFunction;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public Function<? super T,String> getBodyFunction() {
        return bodyFunction;
    }

    public void setBodyFunction(Function<? super T,String> bodyFunction) {
        this.bodyFunction = bodyFunction;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }
}
