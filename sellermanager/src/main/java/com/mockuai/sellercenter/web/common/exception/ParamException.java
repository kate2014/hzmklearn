package com.mockuai.sellercenter.web.common.exception;

public class ParamException extends Exception {
	//异常的字段
	private String field;
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public ParamException(String field,String msg){
		super(msg);
		this.field = field;
	}
	
}
