package com.zxyun.user.export;

import java.util.List;

/**
 * @des:
 * @Author: given
 * @Date 2019/9/6 18:21
 */
public class XSGSheet<T> {

    /**
     * 工作表名称
     */
    private String title;

    /**
     * 工作表所有数据
     */
    private List<? super T> data;

    /**
     * 通用的单元格配置信息
     */
    private List<XSGCell<? super T>> xsgCells;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<? super T> getData() {
        return data;
    }

    public void setData(List<? super T> data) {
        this.data = data;
    }

    public List<XSGCell<? super T>> getXsgCells() {
        return xsgCells;
    }

    public void setXsgCells(List<XSGCell<? super T>> xsgCells) {
        this.xsgCells = xsgCells;
    }
}
