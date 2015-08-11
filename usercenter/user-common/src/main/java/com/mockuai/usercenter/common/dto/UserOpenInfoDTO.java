package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
public class UserOpenInfoDTO implements Serializable{
    private Long id;
    private String bizCode;
    private Long userId;
    /**
     * 第三方开放平台用户昵称
     */
    private String name;
    /**
     * 第三方开放平台类型，1代表微信
     */
    private Integer openType;
    /**
     * 第三方开放平台openid
     */
    private String openId;
    /**
     * 第三方开放平台用户全局唯一ID
     */
    private String openUid;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenUid() {
        return openUid;
    }

    public void setOpenUid(String openUid) {
        this.openUid = openUid;
    }
}
