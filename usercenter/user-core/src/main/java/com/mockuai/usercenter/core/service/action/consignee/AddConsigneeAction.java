package com.mockuai.usercenter.core.service.action.consignee;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
public class
		AddConsigneeAction extends TransAction {

	private static final int MAX_CONSIGNEE = 20;
	@Resource
	private UserConsigneeManager userConsigneeManager;

	@Override
	public String getName() {
		return ActionEnum.ADD_CONSIGNEE.getActionName();
	}

	@Override
	protected UserResponse doTransaction(RequestContext context)
			throws UserException {

		UserRequest userRequest = context.getRequest();
		UserConsigneeDTO userConsigneeDto = (UserConsigneeDTO) userRequest
				.getParam("consigneeDTO");
		String bizCode = (String)context.get("bizCode");

		if(userConsigneeDto == null){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "userConsigneeDto is null");
		}


		userConsigneeDto.setBizCode(bizCode);

		Long userId = userConsigneeDto.getUserId();
		// 查询当前用户的总的收货地址数量，如果大余20条，则不能插入
		Integer totalCount = userConsigneeManager.getConsigneeCountByUserId(userId);
		if (totalCount >= MAX_CONSIGNEE) {
			throw new UserException(ResponseCode.B_ADD_ERROR,
					"user consignee can't greater than 20");
		}

		// 如果新增的地址为默认地址，则将该用户的默认地址改为非默认
		if (userConsigneeDto.getIsDefault() != null && userConsigneeDto.getIsDefault() == 1) {
			userConsigneeManager.updateUserDefaultConsignee(userId);
		}

		//TODO 行政区域CODE检查，townCode可以不填，其他CODE都必填，而且必须有效
		if(StringUtils.isBlank(userConsigneeDto.getCountryCode())
				|| StringUtils.isBlank(userConsigneeDto.getProvinceCode())
				|| StringUtils.isBlank(userConsigneeDto.getCityCode())
				|| StringUtils.isBlank(userConsigneeDto.getAreaCode())){
			throw new UserException(ResponseCode.P_PARAM_NULL, "region code is null");
		}

		// 添加收货地址
		userConsigneeDto = userConsigneeManager.addConsignee(userConsigneeDto);
		return new UserResponse(userConsigneeDto);
	}

}
