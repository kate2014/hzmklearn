package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.itemcenter.client.ItemPropertyClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.PropertyManager;

public class PropertyManagerImpl implements PropertyManager{
	
	private final String SUCCESS_CODE = "200";
	
	//@Resource
	private ItemPropertyClient itemPropertyClient;

	public List<SkuPropertyTmplDTO> querySkuPropertyTemplate(String bizCode,
			Long categoryId) throws ServiceException {
		Response<List<SkuPropertyTmplDTO>> response = null;
		SkuPropertyTmplQTO qto = new SkuPropertyTmplQTO();
		qto.setCategoryId(categoryId);
		//skuPropertyQTO.setBizCode(bizCode);
		response = this.itemPropertyClient.querySkuPropertyTmpl(qto,new Boolean(true), GlobalConstants.TEST_APP_KEY);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public List<ItemPropertyTmplDTO> queryItemPropertyTemplate(String bizCode,
			Long categoryId) throws ServiceException {
		Response<List<ItemPropertyTmplDTO>> response =null;
		ItemPropertyTmplQTO qto = new ItemPropertyTmplQTO();
		qto.setCategoryId(categoryId);
		response = this.itemPropertyClient.queryItemPropertyTmpl(qto, new Boolean(true), GlobalConstants.TEST_APP_KEY);
		//qto.setBizCode(bizCode);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
}
