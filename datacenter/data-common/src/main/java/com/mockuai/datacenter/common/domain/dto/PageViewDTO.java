package com.mockuai.datacenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wanghailong on 15-8-5.
 */
public class PageViewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String bizCode;
    private Long sellerId;
    private Long vid;
    private Long VisitType;
    private String visitorIp;
    private Long userId;
    private String visitorArea;
    private Integer deviceType;
    private Date visitTime;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;
    private Long resultHour;
    private Date resultDate;
    private Integer num;

    public Long getResultHour() {
        return resultHour;
    }

    public void setResultHour(Long resultHour) {
        this.resultHour = resultHour;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getVisitType() {
        return VisitType;
    }

    public void setVisitType(Long visitType) {
        VisitType = visitType;
    }

    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVisitorArea() {
        return visitorArea;
    }

    public void setVisitorArea(String visitorArea) {
        this.visitorArea = visitorArea;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
