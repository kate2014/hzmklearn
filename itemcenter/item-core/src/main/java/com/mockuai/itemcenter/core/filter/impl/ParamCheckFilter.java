package com.mockuai.itemcenter.core.filter.impl;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.filter.Filter;
import com.mockuai.itemcenter.core.service.RequestContext;

public class ParamCheckFilter implements Filter{

	@Override
	public boolean isAccept(RequestContext ctx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean before(RequestContext ctx) throws ItemException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean after(RequestContext ctx) throws ItemException {
		// TODO Auto-generated method stub
		return false;
	}
}
