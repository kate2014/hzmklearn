package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luliang on 15/7/17.
 */
public class LimitEntity implements Serializable {

    private Integer limitCount;
    private Date beginTime;
    private Date endTime;

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
