package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;

public class ItemSkuClientImpl implements ItemSkuClient {

	@Resource
	private ItemService itemService;

	public Response<ItemSkuDTO> getItemSku(Long itemSkuId, Long sellerId) {
		Request request = new BaseRequest();
		request.setParam("ID", itemSkuId);
		request.setParam("sellerId", sellerId);
		request.setCommand(ActionEnum.GET_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemSkuDTO>> queryItemSku(Long itemId, Long sellerId) {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setItemId(itemId);
		itemSkuQTO.setSellerId(sellerId);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemSkuDTO>> queryItemSku(List<Long> skuIdList, Long sellerId) {
		Request request = new BaseRequest();
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setIdList(skuIdList);
		itemSkuQTO.setSellerId(sellerId);
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemSkuDTO>> queryItemSku(ItemSkuQTO itemSkuQTO) {
		Request request = new BaseRequest();
		request.setParam("itemSkuQTO", itemSkuQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
		return itemService.execute(request);
	}
	
}
