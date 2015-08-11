package com.mockuai.shopplatform.core.api.impl;

import com.mockuai.shopplatform.api.Request;
import com.mockuai.shopplatform.api.Response;
import com.mockuai.shopplatform.api.ShopService;
import com.mockuai.shopplatform.core.service.RequestDispatcher;

import javax.annotation.Resource;

/**
 * @author ziqi
 */
public class ShopServiceImpl implements ShopService {

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
