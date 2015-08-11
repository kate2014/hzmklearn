package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

public class SendSmsDTO extends BaseDTO implements Serializable {

	private String userName;

	private String password;

	private String mobile;

	private String content;

	private Integer productId;

	private Date dsTime;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Date getDsTime() {
		return dsTime;
	}

	public void setDsTime(Date dsTime) {
		this.dsTime = dsTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
