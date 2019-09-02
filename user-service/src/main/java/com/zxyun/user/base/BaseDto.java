package com.zxyun.user.base;

/**
 * @des: 入参基类
 * @Author: given
 * @Date 2019/8/4 21:23
 */
public class BaseDto {
    private Long companyId;

    private Long userId;

    private String userName;

    private Integer group;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
