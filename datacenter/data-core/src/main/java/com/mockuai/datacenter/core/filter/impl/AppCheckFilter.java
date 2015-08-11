package com.mockuai.datacenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.datacenter.common.api.DataResponse;
import com.mockuai.datacenter.common.constant.ResponseCode;
import com.mockuai.datacenter.core.exception.DataException;
import com.mockuai.datacenter.core.filter.Filter;
import com.mockuai.datacenter.core.manager.AppManager;
import com.mockuai.datacenter.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;


public class AppCheckFilter implements Filter {

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public DataResponse before(RequestContext ctx) throws DataException {
		String appKey = (String)ctx.getRequest().getParam("appKey");
		if(StringUtils.isBlank(appKey)){
			return new DataResponse(ResponseCode.PARAM_E_MISSING, "appKey is null");
		}

		AppManager appManager = (AppManager)ctx.getAppContext().getBean("appManager");
		AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
		if(appInfoDTO == null){
			return new DataResponse(ResponseCode.PARAM_E_INVALID, "appKey is invalid");
		}

		//将app所属的bizCode塞到context中
		ctx.put("bizCode", appInfoDTO.getBizCode());

		return new DataResponse(ResponseCode.SUCCESS);
	}

	@Override
	public DataResponse after(RequestContext ctx) throws DataException {
		return new DataResponse(ResponseCode.SUCCESS);
	}
}
