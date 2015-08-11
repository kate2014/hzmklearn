package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemTemplateClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;

public class ItemTemplateClientImpl implements ItemTemplateClient{

	@Resource
	private ItemService itemService;

	public Response<ItemTemplateDTO> addItemTemplate(ItemTemplateDTO itemTemplateDTO) {
		Request request = new BaseRequest();
		request.setParam("itemTemplateDTO", itemTemplateDTO);
		request.setCommand(ActionEnum.ADD_ITEM_TEMPLATE.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> updateItemTemplate(ItemTemplateDTO itemTemplateDTO) {
		Request request = new BaseRequest();
		request.setParam("itemTemplateDTO", itemTemplateDTO);
		request.setCommand(ActionEnum.UPDATE_ITEM_TEMPLATE.getActionName());
		return itemService.execute(request);
	}

	public Response<List<ItemTemplateDTO>> queryItemTemplate(
			ItemTemplateQTO itemTemplateQTO) {
		Request request = new BaseRequest();
		request.setParam("itemTemplateQTO", itemTemplateQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_TEMPLATE.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> deleteItemTemplate(Long itemTemplateId,Long supplierId) {
		Request request = new BaseRequest();
		request.setParam("itemTemplateId", itemTemplateId);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.DELETE_ITEM_TEMPLATE.getActionName());
		return itemService.execute(request);
	}

	public Response<ItemTemplateDTO> getItemTemplate(
		Long itemTemplateId,Long supplierId) {
		Request request = new BaseRequest();
		request.setParam("itemTemplateId", itemTemplateId);
		request.setParam("supplierId", supplierId);
		request.setCommand(ActionEnum.GET_ITEM_TEMPLATE.getActionName());
		return itemService.execute(request);
	}
}
