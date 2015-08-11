package com.mockuai.datacenter.common.domain.dto;

import java.io.Serializable;

public class DataDTO implements Serializable{
	private String ip;
	private Integer appType;
	private Long userId;
	private Long vid;
	private Long visitType;
	private Long sellerId;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public Long getVisitType() {
		return visitType;
	}

	public void setVisitType(Long visitType) {
		this.visitType = visitType;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	@Override
	public String toString() {
		return "ip: " + ip + ",userId: " + userId + ",visitType: "
				+ visitType + ",sellerId: " + sellerId + ",vid: " + vid
				+ ",appType: " + appType;
	}
}
