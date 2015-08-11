package com.mockuai.usercenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.usercenter.client.ConsigneeClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.*;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;

public class ConsigneeClientImpl implements ConsigneeClient {

	@Resource
	private UserDispatchService userDispatchService;

	public Response<UserConsigneeDTO> addConsignee(UserConsigneeDTO userConsigneeDTO, String appKey) {
		Request request = new BaseRequest();

		request.setParam("consigneeDTO", userConsigneeDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());

		Response<UserConsigneeDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> deleteConsignee(Long userId, Long consigneeId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("consigneeId", consigneeId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DELETE_CONSIGNEE.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<List<UserConsigneeDTO>> queryConsignee(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_CONSIGNEE.getActionName());
		Response<List<UserConsigneeDTO>> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> setDefConsignee(Long consigneeId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("consigneeId", consigneeId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.SET_DEFAULT_CONSIGNEE.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> updateConsignee(UserConsigneeDTO userConsigneeDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("consigneeDTO", userConsigneeDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());

		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserConsigneeDTO> getConsigneeById(Long userId, Long consigneeId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("consigneeId", consigneeId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_CONSIGNEE_BY_ID.getActionName());
		Response<UserConsigneeDTO> response = userDispatchService.execute(request);
		return response;
	}

}
