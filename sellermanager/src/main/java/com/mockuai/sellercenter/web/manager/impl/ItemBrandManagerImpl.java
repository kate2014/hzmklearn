package com.mockuai.sellercenter.web.manager.impl;

import java.util.List;

import com.mockuai.itemcenter.client.ItemBrandClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemBrandManager;

public class ItemBrandManagerImpl implements ItemBrandManager{
	
	public ItemBrandClient getItemBrandClient() {
		return itemBrandClient;
	}

	public void setItemBrandClient(ItemBrandClient itemBrandClient) {
		this.itemBrandClient = itemBrandClient;
	}

	private ItemBrandClient itemBrandClient;

	public List<SellerBrandDTO> querySellerBrand(SellerBrandQTO sellerBrandQTO)throws ServiceException {
		Response<List<SellerBrandDTO>> response = null;
		response =this.itemBrandClient.querySellerBrand(sellerBrandQTO);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			throw new ServiceException(response.getCode(),response.getMessage());
		}
	}
	
	public Boolean deleteSellerBrand(Long id,Long supplierId)throws ServiceException{
		Response<Boolean> response = null;
		response =this.itemBrandClient.deleteSellerBrand(id, supplierId);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			throw new ServiceException(response.getCode(),response.getMessage());
		}
	}
	
	public SellerBrandDTO addSellerBrand(SellerBrandDTO sellerBrandDTO)throws ServiceException{
		Response<SellerBrandDTO> response = null;
		response =this.itemBrandClient.addSellerBrand(sellerBrandDTO);
		if(response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS){
			return response.getModule();
		}else{
			throw new ServiceException(response.getCode(),response.getMessage());
		}
	}

	public SellerBrandDTO getSellerBrand(Long id, Long supplierId)
			throws ServiceException {
		Response<SellerBrandDTO> response =null;
		response = this.itemBrandClient.getSellerBrand(id, supplierId);
		if (response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
			return response.getModule();
		} else {
			throw new ServiceException(response.getCode(),
					response.getMessage());
		}
	}

	public Boolean updateSellerBrand(SellerBrandDTO sellerBrandDTO)
			throws ServiceException {
		Response<Boolean> response = null;
		response = this.itemBrandClient.updateSellerBrand(sellerBrandDTO);
		if (response.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
			return response.getModule();
		} else {
			throw new ServiceException(response.getCode(),
					response.getMessage());
		}
	}
}
