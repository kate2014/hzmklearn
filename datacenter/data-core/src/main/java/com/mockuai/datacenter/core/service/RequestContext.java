package com.mockuai.datacenter.core.service;


import com.mockuai.datacenter.common.api.DataResponse;

/**
 * 当前请求的上下文容器
 * 
 * @author wujin.zzq
 */
public class RequestContext extends Context {
	private static final long serialVersionUID = 1L;

	/**
	 * 系统级别的上下文容器
	 */
	private AppContext appContext;

	/**
	 * 当前请求的请求对象
	 */
	private DataRequest request;

	/**
	 * 返回的response对象
	 */
	@SuppressWarnings("rawtypes")
	private DataResponse response;

	/**
	 * @param appContext
	 * @param request
	 */
	public RequestContext(AppContext appContext, DataRequest request) {
		this.appContext = appContext;
		this.request = request;
	}

	/**
	 * @return the appContext
	 */
	public AppContext getAppContext() {
		return appContext;
	}

	/**
	 * @return the request
	 */
	public DataRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	@SuppressWarnings("rawtypes")
	public DataResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	@SuppressWarnings("rawtypes")
	public void setResponse(DataResponse response) {
		this.response = response;
	}
}
