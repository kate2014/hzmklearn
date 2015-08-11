package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemPropertyClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;

public class ItemPropertyClientImpl implements ItemPropertyClient {

	@Resource
	private ItemService itemService;
	
	public Response<List<ItemPropertyTmplDTO>> queryItemPropertyTmpl(
			ItemPropertyTmplQTO qto,Boolean needPropertyValue, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemPropertyTmplQTO", qto);
		request.setParam("needPropertyValue",needPropertyValue);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.QUERY_ITEM_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	public Response<List<SkuPropertyTmplDTO>> querySkuPropertyTmpl(
			SkuPropertyTmplQTO qto, Boolean needPropertyValue, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuPropertyTmplQTO", qto);
		request.setParam("needPropertyValue",needPropertyValue);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.QUERY_SKU_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	@Override
	public Response<Long> addItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemPropertyTmplDTO", itemPropertyTmplDTO);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.ADD_ITEM_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	@Override
	public Response<Void> deleteItemPropertyTmpl(Long itemPropertyTmplId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("itemPropertyTmplId", itemPropertyTmplId);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.DELETE_ITEM_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	@Override
	public Response<Long> addSkuPropertyTmpl(SkuPropertyTmplDTO skuPropertyTmplDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuPropertyTmplDTO", skuPropertyTmplDTO);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}

	@Override
	public Response<Void> deleteSkuPropertyTmpl(Long skuPropertyTmplId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("skuPropertyTmplId", skuPropertyTmplId);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.DELETE_SKU_PROPERTY_TMPL.getActionName());
		return itemService.execute(request);
	}
}
