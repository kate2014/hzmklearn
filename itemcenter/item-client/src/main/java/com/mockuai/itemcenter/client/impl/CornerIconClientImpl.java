package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.CornerIconClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;

public class CornerIconClientImpl implements CornerIconClient {

	@Resource
	private ItemService itemService;
	
	public Response<CornerIconDTO> addCornerIcon(CornerIconDTO cornerIconDTO) {
		Request request = new BaseRequest();
		request.setParam("cornerIconDTO", cornerIconDTO);
		request.setCommand(ActionEnum.ADD_CORNER_ICON.getActionName());
		return itemService.execute(request);
	}

	public Response<Boolean> deleteCornerIcon(Long id, Long sellerId) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("sellerId", sellerId);
		request.setCommand(ActionEnum.DELETE_CORNER_ICON.getActionName());
		return itemService.execute(request);
	}

	public Response<List<CornerIconDTO>> queryCornerIcon(
			CornerIconQTO cornerIconQTO) {
		Request request = new BaseRequest();
		request.setParam("cornerIconQTO", cornerIconQTO);
		request.setCommand(ActionEnum.QUERY_CORNER_ICON.getActionName());
		return itemService.execute(request);
	}
	
	public Response<CornerIconDTO> getCornerIcon(
			Long id) {
		Request request = new BaseRequest();
		request.setParam("cornerIconId", id);
		request.setCommand(ActionEnum.GET_CORNER_ICON.getActionName());
		return itemService.execute(request);
	}
	
}
