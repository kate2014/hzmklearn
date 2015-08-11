package com.mockuai.usercenter.core.api.impl;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.service.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserService;
import com.mockuai.usercenter.core.service.RequestDispatcher;




public class UserServiceImpl implements UserService{

	@Resource
	private RequestDispatcher requestDispatcher;

	public UserResponse<UserDTO> addUser(UserDTO userDTO) {
		UserRequest request = new UserRequest();
		request.setCommand(ActionEnum.ADD_USER.getActionName());
		request.setParam("userDTO", userDTO);
		UserResponse<UserDTO> response = requestDispatcher.dispatch(request);
		return response;
	}

	public UserResponse<Void> deleteUser(String appkey, long userId) {
		UserRequest request = new UserRequest();
		request.setCommand(ActionEnum.DELETE_USER.getActionName());
		request.setParam("appkey", appkey);
		request.setParam("userId", userId);
		UserResponse<Void> response = requestDispatcher.dispatch(request);
		return response;
	}

	public UserResponse<UserDTO> getUserById(String appkey, long userId) {
		UserRequest request = new UserRequest();
		request.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
		request.setParam("appkey", appkey);
		request.setParam("userId", userId);
		UserResponse<UserDTO> response = requestDispatcher.dispatch(request);
		return response;
	}

	public UserResponse<UserDTO> getUserByMobile(String appkey, String mobile) {
		UserRequest request = new UserRequest();
		request.setCommand(ActionEnum.GET_USER_BY_MOBILE.getActionName());
		request.setParam("appkey", appkey);
		request.setParam("mobile", mobile);
		UserResponse<UserDTO> response = requestDispatcher.dispatch(request);
		return response;
	}

	public UserResponse<UserDTO> getUserByEmail(String appkey, String email) {
		UserRequest request = new UserRequest();
		request.setCommand(ActionEnum.GET_USER_BY_EMAIL.getActionName());
		request.setParam("appkey", appkey);
		request.setParam("email", email);
		UserResponse<UserDTO> response = requestDispatcher.dispatch(request);
		return response;
	}

	public UserResponse<Void> updatePwd(String appkey, long userId, String newPwd) {
		UserRequest request = new UserRequest();
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		request.setParam("appkey", appkey);
		request.setParam("userId", userId);
		request.setParam("newPwd", newPwd);
		UserResponse<Void> response = requestDispatcher.dispatch(request);
		return response;
	}
}
