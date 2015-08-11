package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.NotifyManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.util.HttpUtil;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加来自第三方开放平台登陆的用户，此场景，则暂时不校验手机号和邮箱
 * */
@Service
public class AddOpenUserAction implements Action {

	@Resource
	private UserManager userManager;

	@Resource
	private NotifyManager notifyManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();
		UserDTO userDto = (UserDTO) userRequest.getParam("userDTO");
		String bizCode = (String)context.get("bizCode");

		if(userDto == null){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "userDto is null");
		}


		userDto.setBizCode(bizCode);
		try{
			userDto = userManager.addOpenUser(userDto);
			//推送开放账号新增消息 (TODO 保证事务性，异步任务化)
			notifyManager.notifyAddUserMsg(userDto.getId());
			return new UserResponse(userDto);
		}catch(UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_OPEN_USER.getActionName();
	}

}
