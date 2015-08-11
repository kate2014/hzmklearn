package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemCategoryClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;

public class ItemCategoryClientImpl implements  ItemCategoryClient{

	@Resource
	private ItemService itemService;
	
	public Response<List<ItemCategoryDTO>> queryItemCategory(ItemCategoryQTO itemCategoryQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemCategoryQTO", itemCategoryQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemCategoryDTO>> queryItemLeafCategory() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM_LEAF_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	public Response<ItemCategoryDTO> getItemCategory(Long categoryId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("categoryId", categoryId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	@Override
	public Response<ItemCategoryDTO> addItemCategory(ItemCategoryDTO itemCategoryDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	@Override
	public Response<Void> deleteItemCategory(Long categoryId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("categoryId", categoryId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DELETE_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}

	@Override
	public Response<Void> updateItemCategory(ItemCategoryDTO itemCategoryDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemCategoryDTO", itemCategoryDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_ITEM_CATEGORY.getActionName());
		return itemService.execute(request);
	}
}
