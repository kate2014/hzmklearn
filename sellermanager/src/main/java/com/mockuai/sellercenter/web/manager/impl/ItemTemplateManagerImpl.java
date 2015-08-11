package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.itemcenter.client.ItemTemplateClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemTemplateManager;

public class ItemTemplateManagerImpl implements ItemTemplateManager {

	public ItemTemplateClient getItemTemplateClient() {
		return itemTemplateClient;
	}

	public void setItemTemplateClient(ItemTemplateClient itemTemplateClient) {
		this.itemTemplateClient = itemTemplateClient;
	}

	private ItemTemplateClient itemTemplateClient;
	
	public Boolean deleteItemTemplate(Long templateId, Long supplierId) throws ServiceException {
		Response<Boolean> response = null;
		response = this.itemTemplateClient.deleteItemTemplate(templateId,supplierId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public ItemTemplateDTO addItemTemplate(ItemTemplateDTO itemDTO)  throws ServiceException{
		Response<ItemTemplateDTO> response = null;
		response = this.itemTemplateClient.addItemTemplate(itemDTO);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Boolean updateItemTemplate(ItemTemplateDTO itemDto)
			throws ServiceException {
		Response<Boolean> response = null;
		response = this.itemTemplateClient.updateItemTemplate(itemDto);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public ItemTemplateDTO getItemTemplate(Long templateId, Long supplierId)
			throws ServiceException {
		Response<ItemTemplateDTO> response = null;
		response = this.itemTemplateClient.getItemTemplate(templateId, supplierId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Response<List<ItemTemplateDTO>> queryItemTemplate(
			ItemTemplateQTO itemTemplateQTO) throws ServiceException {
		Response<List<ItemTemplateDTO>> response = null;
		response = this.itemTemplateClient.queryItemTemplate(itemTemplateQTO);
		/*
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
		*/
		if(response.getCode() != GlobalConstants.SERVICE_PROCESS_SUCCESS)
		{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
		return response;
	}
}
