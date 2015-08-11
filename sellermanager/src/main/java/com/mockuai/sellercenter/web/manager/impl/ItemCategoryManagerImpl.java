package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.itemcenter.client.ItemCategoryClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemCategoryManager;

public class ItemCategoryManagerImpl implements ItemCategoryManager {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ItemCategoryClient getItemCategoryClient() {
		return itemCategoryClient;
	}

	public void setItemCategoryClient(ItemCategoryClient itemCategoryClient) {
		this.itemCategoryClient = itemCategoryClient;
	}

	private ItemCategoryClient itemCategoryClient;
	
	public List<ItemCategoryDTO> querySubCategory(Long parentId) throws ServiceException{
		Response<List<ItemCategoryDTO>> response =null;
		ItemCategoryQTO qto = new ItemCategoryQTO();
		qto.setParentId(parentId);
		response = this.itemCategoryClient.queryItemCategory(qto, GlobalConstants.TEST_APP_KEY);
		logger.debug("******************");
		logger.debug("querySubCategory : " + response.getCode());
		logger.debug("message : " + response.getMessage());
		logger.debug("module : " + response.getModule());
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int code = Integer.valueOf(response.getCode());
			throw new ServiceException(code,response.getMessage());
		}
	}

	public List<ItemCategoryDTO> queryCategory(ItemCategoryQTO itemCategoryQTO) throws ServiceException{
		Response<List<ItemCategoryDTO>> response =null;
		response = this.itemCategoryClient.queryItemCategory(itemCategoryQTO,GlobalConstants.TEST_APP_KEY);
		logger.debug("******************");
		logger.debug("querySubCategory : " + response.getCode());
		logger.debug("message : " + response.getMessage());
		logger.debug("module : " + response.getModule());
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int code = Integer.valueOf(response.getCode());
			throw new ServiceException(code,response.getMessage());
		}
	}

	public List<ItemCategoryDTO> queryLeafCategory() throws ServiceException {
		Response<List<ItemCategoryDTO>> response =null;
		response = this.itemCategoryClient.queryItemLeafCategory();
		logger.debug("******************");
		logger.debug("queryLeafCategory : " + response.getCode());
		logger.debug("message : " + response.getMessage());
		logger.debug("module : " + response.getModule());
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int code = Integer.valueOf(response.getCode());
			throw new ServiceException(code,response.getMessage());
		}
	}

	public ItemCategoryDTO getItemCategory(Long categoryId)
			throws ServiceException {
		Response<ItemCategoryDTO> response = null;
		response = this.itemCategoryClient.getItemCategory(categoryId, GlobalConstants.TEST_APP_KEY);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			throw new ServiceException(response.getCode(),response.getMessage());
		}
		
	}
	
}
