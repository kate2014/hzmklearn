package com.mockuai.shopplatform.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.shopplatform.api.ShopResponse;
import com.mockuai.shopplatform.constant.ResponseCode;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.core.filter.Filter;
import com.mockuai.shopplatform.core.manager.AppManager;
import com.mockuai.shopplatform.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;


public class AppCheckFilter implements Filter {

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public ShopResponse before(RequestContext ctx) throws ShopException {
		String appKey = (String)ctx.getRequest().getParam("appKey");
		if(StringUtils.isBlank(appKey)){
			return new ShopResponse(ResponseCode.PARAM_E_MISSING, "appKey is null");
		}

		AppManager appManager = (AppManager)ctx.getAppContext().getBean("appManager");
		AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
		if(appInfoDTO == null){
			return new ShopResponse(ResponseCode.PARAM_E_INVALID, "appKey is invalid");
		}

		//将app所属的bizCode塞到context中
		ctx.put("bizCode", appInfoDTO.getBizCode());

		return new ShopResponse(ResponseCode.SUCCESS);
	}

	@Override
	public ShopResponse after(RequestContext ctx) throws ShopException {
		return new ShopResponse(ResponseCode.SUCCESS);
	}
}
