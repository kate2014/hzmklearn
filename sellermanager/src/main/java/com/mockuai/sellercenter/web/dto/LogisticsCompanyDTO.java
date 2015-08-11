package com.mockuai.sellercenter.web.dto;
/**
 * 物流公司
 * @author hzmk
 *
 */
public class LogisticsCompanyDTO {

	
	private int code;
	
	private String name;
	
	public LogisticsCompanyDTO(int code,String name){
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
