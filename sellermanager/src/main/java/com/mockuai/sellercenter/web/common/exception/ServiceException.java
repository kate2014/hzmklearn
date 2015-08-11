package com.mockuai.sellercenter.web.common.exception;


public class ServiceException extends Exception {

	//private ResponseEnum responseEnum;
	private int code ;
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getServiceMessage() {
		return serviceMessage;
	}

	public void setServiceMessage(String serviceMessage) {
		this.serviceMessage = serviceMessage;
	}

	private String serviceMessage;

	public ServiceException() {
		super();
	}

	public ServiceException(Throwable e) {
		super(e);
	}

	public ServiceException(int code,String serviceMessage){
		this.code = code;
		this.serviceMessage = serviceMessage;
	}

}
