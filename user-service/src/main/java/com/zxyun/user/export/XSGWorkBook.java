package com.zxyun.user.export;

import java.util.ArrayList;
import java.util.List;

/**
 * @des: 工作簿
 * @Author: given
 * @Date 2019/9/6 18:29
 */
public class XSGWorkBook {
    private String title;

    private List<XSGSheet> xsgSheets = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<XSGSheet> getXsgSheets() {
        return xsgSheets;
    }

    public void addXsgSheet (XSGSheet xsgSheet) {
        xsgSheets.add(xsgSheet);
    }
}
