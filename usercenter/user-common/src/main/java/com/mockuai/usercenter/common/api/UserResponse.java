package com.mockuai.usercenter.common.api;

import java.util.Collection;

import com.mockuai.usercenter.common.constant.ResponseCode;
import org.apache.commons.lang.StringUtils;

public class UserResponse<T> implements Response<T> {
	private static final long serialVersionUID = 8295766534182699773L;

	private T module;
	private final int code;
	private final String message;
	private long totalCount = 0;

	public UserResponse(ResponseCode responseCode) {
		this.code = responseCode.getValue();
		this.message = responseCode.getDesc();
	}

	public UserResponse(ResponseCode responseCode, String message) {
		this.code = responseCode.getValue();
		if(StringUtils.isBlank(message)){
			this.message = responseCode.getDesc();
		}else{
			this.message = message;
		}

	}


	public UserResponse(T module) {
		this.module = module;
		if (module != null) {
			if (module instanceof Collection) {
				totalCount = ((Collection) module).size();
			} else {
				totalCount = 1;
			}
		}
		this.code = ResponseCode.REQUEST_SUCCESS.getValue();
		this.message = ResponseCode.REQUEST_SUCCESS.getDesc();
	}

	public UserResponse(T module, Long totalCount) {
		this.module = module;
		this.code = ResponseCode.REQUEST_SUCCESS.getValue();
		this.message = ResponseCode.REQUEST_SUCCESS.getDesc();
		this.totalCount = totalCount;
	}

	public T getModule() {
		return module;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setModule(T module) {
		this.module = module;
	}

	@Override
	public boolean isSuccess() {
		return this.code == ResponseCode.REQUEST_SUCCESS.getValue();
	}

	public int getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}
