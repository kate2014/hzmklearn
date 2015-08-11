package com.mockuai.shopplatform.core.filter.impl;


import com.mockuai.shopplatform.api.ShopResponse;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.core.filter.Filter;
import com.mockuai.shopplatform.core.service.RequestContext;

public class ParamCheckFilter implements Filter {

	@Override
	public boolean isAccept(RequestContext ctx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ShopResponse before(RequestContext ctx) throws ShopException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShopResponse after(RequestContext ctx) throws ShopException {
		// TODO Auto-generated method stub
		return null;
	}
}
