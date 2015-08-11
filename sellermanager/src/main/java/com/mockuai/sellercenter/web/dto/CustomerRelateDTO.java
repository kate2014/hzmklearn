package com.mockuai.sellercenter.web.dto;

/**
 * 客户关系DTO
 * @author hzmk
 *
 */
public class CustomerRelateDTO {
	
	private String userId;
	
	private String imgUrl;
	
	private String userName;
	
	private String mobile;
	
	private String joinTime;

	private Long finishedOrderNum;
	
	private String finishedOrderTotalAmt;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public Long getFinishedOrderNum() {
		return finishedOrderNum;
	}

	public void setFinishedOrderNum(Long finishedOrderNum) {
		this.finishedOrderNum = finishedOrderNum;
	}

	public String getFinishedOrderTotalAmt() {
		return finishedOrderTotalAmt;
	}

	public void setFinishedOrderTotalAmt(String finishedOrderTotalAmt) {
		this.finishedOrderTotalAmt = finishedOrderTotalAmt;
	}
	
	
	
}
