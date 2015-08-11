package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemManager;

public class ItemManagerImpl implements ItemManager {
	private String skuUrl;
	private ItemClient itemClient;
	
	//@Resource
	//TODO private ItemImageClient itemImageClient;
	//@Resource
	//TODO private ItemPropertyClient itemPropertyClient;
	//@Resource
	//TODO private SkuPropertyClient skuPropertyClient;
	
	public ItemClient getItemClient() {
		return itemClient;
	}

	public void setItemClient(ItemClient itemClient) {
		this.itemClient = itemClient;
	}

	public Response<List<ItemDTO>> queryItem(ItemQTO itemQTO)throws ServiceException{
		Response<List<ItemDTO>> response = this.itemClient.queryItem(itemQTO);
		/*if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			System.out.println("totalCount: " + response.getTotalCount());
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}*/
		
		if(response.getCode() != GlobalConstants.SERVICE_PROCESS_SUCCESS){
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
		return response;
	}
	
	public Boolean addItem(ItemDTO item)throws ServiceException{
		Response<ItemDTO> response = null;
		response = this.itemClient.addItem(item);
		System.out.println("************************");
		System.out.println("add item response: " + response.getCode());
		System.out.println("module: " + response.getModule());
		System.out.println("message: " + response.getMessage());
		
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule() != null;
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
	public ItemDTO getItem(Long userId,Long itemId)throws ServiceException{
		Response<ItemDTO> response = null;
		Boolean needDetail =new Boolean(true); //需要查询下面的所有的属性 销售属性图片等信息
		response = this.itemClient.getItem(itemId, userId, needDetail);
		System.out.println("************************");
		System.out.println("get item response: " + response.getCode());
		System.out.println("module: " + response.getModule());
		System.out.println("message: " + response.getMessage());
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
	public Boolean updateItem(ItemDTO itemDTO)throws ServiceException{
		Response<Boolean> response = null;
		//response = this.itemClient.updateItem(itemDTO);
		Boolean updateDetail= new Boolean(true);//需要更新下面的明细信息
		response = this.itemClient.updateItem(itemDTO, updateDetail);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
	public Boolean deleteItem(Long itemId,Long supplierId)throws ServiceException{
		Response<Boolean> response = null;
		response = this.itemClient.deleteItem(itemId, supplierId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Boolean withdraw(Long itemId, Long supplierId)	
			throws ServiceException {
		Response<Boolean> response = null;
		response = this.itemClient.withdrawItem(itemId, supplierId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Boolean upItem(Long itemId, Long supplierId) throws ServiceException {
		Response<Boolean> response = null;
		response = this.itemClient.upItem(itemId, supplierId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Boolean preSaleItem(Long itemId, Long supplierId) throws ServiceException {
		Response<Boolean> response = null;
		response = this.itemClient.preSaleItem(itemId, supplierId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Boolean addItemInGroup(Long sellerId, Long itemId, Long groupId) throws ServiceException {
		Response<Boolean> response = null;
		response = this.itemClient.addGroupItem(groupId, sellerId, itemId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public Boolean removeItemFromGroup(Long sellerId, Long itemId) throws ServiceException {
		Response<Boolean> response = null;
		response = this.itemClient.removeGroupItem(sellerId, itemId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}

	public void setSkuUrl(String skuUrl) {
		this.skuUrl = skuUrl;
	}

	public String genSkuUrl(long skuId, long sellerId){
		return skuUrl+"?sku_uid="+sellerId+"_"+skuId;
	}


	/*
	public List<ItemImageDTO> queryItemImages(ItemImageQTO qto)throws ServiceException{
		Response<List<ItemImageDTO>> response= null;
		response = this.itemClient.queryItems(qto);
		if(response.getCode().equals("10000")){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
	public List<SkuPropertyDTO> querySkuProperty(SkuPropertyQTO qto)throws ServiceException{
		Response<List<SkuPropertyDTO>> response= null;
		if(response.getCode().equals("10000")){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}
	
	public List<ItemPropertyDTO> queryItemProperty(ItemPropertyQTO qto)throws ServiceException{
		Response<List<ItemPropertyDTO>> response= null;
		if(response.getCode().equals("10000")){
			return response.getModule();
		}else{
			int errorCode = Integer.valueOf(response.getCode());
			throw new ServiceException(errorCode,response.getMessage());
		}
	}*/
	
}
