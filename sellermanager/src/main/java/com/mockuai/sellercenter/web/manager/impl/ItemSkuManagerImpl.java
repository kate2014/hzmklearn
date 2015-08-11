package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemSkuManager;

public class ItemSkuManagerImpl implements ItemSkuManager {
	
	public ItemSkuClient getItemSkuClient() {
		return itemSkuClient;
	}

	public void setItemSkuClient(ItemSkuClient itemSkuClient) {
		this.itemSkuClient = itemSkuClient;
	}

	private ItemSkuClient itemSkuClient;

	public List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO)
			throws ServiceException {
		Response<List<ItemSkuDTO>> response =null;
		response = this.itemSkuClient.queryItemSku(itemSkuQTO);
		if (response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
			return response.getModule();
		} else {
			throw new ServiceException(response.getCode(),
					response.getMessage());
		}
	}
	
//	private ItemSkuClient itemSkuClient;
//	
//	private SkuPropertyClient skuPropertyClient;
//	
//	public List<ItemSkuDTO> querySkuItem(Long itemId,Long supplierId) throws ServiceException{
//		Response<List<ItemSkuDTO>> response =null;
//		response = this.itemSkuClient.queryItemSku(itemId, supplierId);
//		if (response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
//			return response.getModule();
//		} else {
//			throw new ServiceException(response.getCode(),
//					response.getMessage());
//		}
//	}
//	
//	public List<SkuPropertyDTO> querySkuProeprty(Long itemId,Long supplierId))throws ServiceException{
//		Response<List<SkuPropertyDTO>> response =null;
//		SkuPropertyQTO skuPropertyQTO =new  SkuPropertyQTO();
//		skuPropertyQTO.setItemId(itemId);
//		skuPropertyQTO.setSellerId(supplierId);
//		
//		response = this.skuPropertyClient.querySkuProperyt(skuPropertyQTO);
//		
//		if (response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
//			return response.getModule();
//		} else {
//			throw new ServiceException(response.getCode(),
//					response.getMessage());
//		}
//	}
	
	
}
