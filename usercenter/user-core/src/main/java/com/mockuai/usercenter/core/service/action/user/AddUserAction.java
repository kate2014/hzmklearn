package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.manager.NotifyManager;
import com.mockuai.usercenter.core.util.HttpUtil;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加用户接口
 * */
@Service
public class AddUserAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddUserAction.class);

	@Resource
	private UserManager userManager;

	@Resource
	private NotifyManager notifyManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest userRequest = context.getRequest();
		UserDTO userDTO = (UserDTO) userRequest.getParam("userDTO");
		String bizCode = (String)context.get("bizCode");

		//TODO 入参校验
		if(userDTO == null){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "userDTO is null");
		}

		userDTO.setBizCode(bizCode);
		try{
			// 判断邀请码是否为空，如果不为空，判断是否存在指定的用户
			String invitationCode = userDTO.getInvitationCode();
			if (StringUtils.isBlank(invitationCode) == false) {
				UserDTO invUser = userManager.getUserByInvitationCode(invitationCode);
				if (invUser == null) {
					throw new UserException(ResponseCode.B_INVITATION_CODE_INVALID,
							"invitation code error");
				}
				userDTO.setInviterId(invUser.getId());
			} else {
				log.warn("invitation code is empty when add user with biz code = ", userDTO.getBizCode());
				userDTO.setInviterId(0L);
			}

			userDTO = userManager.addUser(userDTO);

			//添加成功，推送新增用户消息（TODO 保证事务性，异步任务化）
			notifyManager.notifyAddUserMsg(userDTO.getId());

			return new UserResponse(userDTO);
		}catch(UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.ADD_USER.getActionName();
	}

}
