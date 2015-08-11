package com.mockuai.datacenter.core.filter.impl;


import com.mockuai.datacenter.common.api.DataResponse;
import com.mockuai.datacenter.core.exception.DataException;
import com.mockuai.datacenter.core.filter.Filter;
import com.mockuai.datacenter.core.service.RequestContext;

public class ParamCheckFilter implements Filter {

	@Override
	public boolean isAccept(RequestContext ctx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DataResponse before(RequestContext ctx) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResponse after(RequestContext ctx) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}
}
