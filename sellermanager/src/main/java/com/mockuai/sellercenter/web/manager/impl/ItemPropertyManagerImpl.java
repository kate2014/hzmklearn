package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemPropertyClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemPropertyManager;

public class ItemPropertyManagerImpl implements ItemPropertyManager{

	private ItemPropertyClient itemPropertyClient;
	
	public ItemPropertyClient getItemPropertyClient() {
		return itemPropertyClient;
	}

	public void setItemPropertyClient(ItemPropertyClient itemPropertyClient) {
		this.itemPropertyClient = itemPropertyClient;
	}

	public List<ItemPropertyTmplDTO> queryItemPropertyTmpl(ItemPropertyTmplQTO qto,Boolean needPropertyValue)
			throws ServiceException {
		Response<List<ItemPropertyTmplDTO>> response = null;
		response =this.itemPropertyClient.queryItemPropertyTmpl(qto, needPropertyValue, GlobalConstants.TEST_APP_KEY);
		System.out.println("********queryItemPropertyTmpl****************");
		System.out.println("code: " + response.getCode());
		System.out.println("message: " + response.getMessage());
		System.out.println("module: " + response.getModule());
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode= Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public List<SkuPropertyTmplDTO> querySkuPropertyTmpl(SkuPropertyTmplQTO qto,Boolean needPropertyValue)
			throws ServiceException {
		Response<List<SkuPropertyTmplDTO>> response = null;
		response =this.itemPropertyClient.querySkuPropertyTmpl(qto,needPropertyValue, GlobalConstants.TEST_APP_KEY);
		System.out.println("********querySkuPropertyTmpl****************");
		System.out.println("code: " + response.getCode());
		System.out.println("message: " + response.getMessage());
		System.out.println("module: " + response.getModule());
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode= Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
}
