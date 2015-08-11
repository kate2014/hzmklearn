package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

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

@Service
public class UserLoginAction implements Action {
	private final static Logger log = LoggerFactory.getLogger(UserLoginAction.class);

	@Resource
	private UserManager userManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {
		// TODO Auto-generated method stub

		UserRequest userRequest = context.getRequest();
		String loginName = (String) userRequest.getParam("loginName");// 登陆的用户名/邮箱/手机号
		String loginPwd = (String) userRequest.getParam("loginPwd");// 登陆的密码

		if (loginName == null) {
			log.error("login name is null when login");
		}

		if (loginPwd == null) {
			log.error("login password is null when login");
		}

		UserDTO userDto = userManager.userLogin(loginName, loginPwd);

		return new UserResponse(userDto);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.USER_LOGIN.getActionName();
	}

}
