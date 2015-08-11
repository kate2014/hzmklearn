package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemBrandClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemBrandDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;

public class ItemBrandClientImpl implements ItemBrandClient {

	@Resource
	private ItemService itemService;
	
//	public Response<ItemBrandDTO> getBrand(Long id, Long suppierId) {
//		Request request = new BaseRequest();
//		request.setParam("id", 22L);
//		request.setParam("suppierId", 1L);
//		request.setCommand(ActionEnum.GET_ITEMBRAND.getActionName());
//		return itemService.execute(request);
//	}

	public Response<List<ItemBrandDTO>> queryBrand(ItemBrandQTO itemBrandQTO) {
		Request request = new BaseRequest();
		request.setParam("itemBrandQTO", itemBrandQTO);
		request.setCommand(ActionEnum.QUERY_ITEMBRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<ItemBrandDTO> addItemBrand(ItemBrandDTO itemBrandDTO) {
		Request request = new BaseRequest();
		request.setParam("itemBrandDTO", itemBrandDTO);
		request.setCommand(ActionEnum.QUERY_ITEMBRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<List<SellerBrandDTO>> querySellerBrand(SellerBrandQTO sellerBrandQTO){
		Request request = new BaseRequest();
		request.setParam("sellerBrandQTO", sellerBrandQTO);
		request.setCommand(ActionEnum.QUERY_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<SellerBrandDTO> addSellerBrand(SellerBrandDTO sellerBrandDTO) {
		Request request = new BaseRequest();
		request.setParam("sellerBrandDTO", sellerBrandDTO);
		request.setCommand(ActionEnum.ADD_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> deleteSellerBrand(Long id, Long supplierId) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.DELETE_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> deleteItemBrand(Long id) {
		Request request = new BaseRequest();
		request.setParam("ID", id);
		request.setCommand(ActionEnum.DELETE_ITEMBRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<SellerBrandDTO> getSellerBrand(Long id, Long supplierId) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.GET_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> updateSellerBrand(SellerBrandDTO sellerBrandDTO) {
		Request request = new BaseRequest();
		request.setParam("sellerBrandDTO", sellerBrandDTO);
		request.setCommand(ActionEnum.UPDATE_SELLER_BRAND.getActionName());
		return itemService.execute(request);
	}
}
