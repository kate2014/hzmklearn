package com.mockuai.usercenter.common.qto;

import java.util.Date;

/**
 * Created by zengzhangqiang on 8/7/15.
 */
public class UserAuthInfoQTO extends BaseQTO{
    private Long id;
    private String bizCode;
    private Long userId;
    /**
     * 认证类型，1代表买家实名认证，2代表个人卖家实名认证，3代表企业实名认证。
     * 参考 {@link com.mockuai.usercenter.common.constant.UserAuthType}
     */
    private Integer type;

    /**
     * 认证状态
     */
    private Integer status;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
