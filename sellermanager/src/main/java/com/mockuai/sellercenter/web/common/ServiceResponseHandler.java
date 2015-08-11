package com.mockuai.sellercenter.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.util.ResponseUtils;

public class ServiceResponseHandler {
	
	private static Logger logger = LoggerFactory.getLogger(ServiceResponseHandler.class);
	
	/**
	 * 如果是服务端没有返回成功，统一处理异常情况部分信息不显示给用户，可能屏蔽部分信息部显示给用户
	 * @param e
	 * @return
	 */
	public static String serviceExceptionHandler(ServiceException e){
		logger.error("service error: " + e.getCode() +  " " + e.getMessage());
		ApiResponse apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
		return  ResponseUtils.toJsonStr(apiResponse);
	}
	
}
