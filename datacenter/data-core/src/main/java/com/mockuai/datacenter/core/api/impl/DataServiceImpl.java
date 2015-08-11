package com.mockuai.datacenter.core.api.impl;

import com.mockuai.datacenter.common.api.DataService;
import com.mockuai.datacenter.common.api.Request;
import com.mockuai.datacenter.common.api.Response;
import com.mockuai.datacenter.core.service.RequestDispatcher;

import javax.annotation.Resource;

/**
 * Created by wanghailong on 15-8-7.
 */
public class DataServiceImpl implements DataService {

	@Resource
	private RequestDispatcher requestDispatcher;

	/**
	 * 服务端接口执行
	 **/
	@Override
	public Response execute(Request request) {

		Response response = requestDispatcher.dispatch(new RequestAdapter(request));
		return response;
	}
}
