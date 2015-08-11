package com.mockuai.usercenter.core.service.action.consignee;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;

@Service
public class UpdateConsigneeAction extends TransAction {

	@Resource
	private UserConsigneeManager userConsigneeManager;

	@Override
	public String getName() {
		return ActionEnum.UPDATE_CONSIGNEE.getActionName();
	}

	@Override
	protected UserResponse doTransaction(RequestContext context)
			throws UserException {
		UserRequest userRequest = context.getRequest();
		UserConsigneeDTO userConsigneeDto = (UserConsigneeDTO) userRequest
				.getParam("consigneeDTO");

		if (null == userConsigneeDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"consigneeDTO is null");
		}

		// 如果新增的地址为默认地址，则将该用户的所有地址改为非默认
		if (userConsigneeDto.getIsDefault() != null && userConsigneeDto.getIsDefault() == 1) {
			userConsigneeManager.updateUserDefaultConsignee(userConsigneeDto.getUserId());
		}

		// 修改用户的收货地址
		userConsigneeManager.updateConsignee(userConsigneeDto);
		return new UserResponse(true);
	}
}
