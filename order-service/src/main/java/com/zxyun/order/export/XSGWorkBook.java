package com.zxyun.order.export;

import java.util.ArrayList;
import java.util.List;

/**
 * @des: 工作簿
 * @Author: given
 * @Date 2019/9/6 18:29
 */
public class XSGWorkBook {
    private String fileName;

    private List<XSGSheet> xsgSheets = new ArrayList<>();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<XSGSheet> getXsgSheets() {
        return xsgSheets;
    }

    public void addXsgSheet (XSGSheet xsgSheet) {
        xsgSheets.add(xsgSheet);
    }
}
