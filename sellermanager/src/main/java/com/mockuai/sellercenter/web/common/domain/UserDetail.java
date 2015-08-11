package com.mockuai.sellercenter.web.common.domain;

/**
 * 保存到session中的用户信息
 * @author cwr
 */
public class UserDetail {
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private Long userId;
	
	private String userName;
	
	private String bizCode;
	
}
