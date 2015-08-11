package com.mockuai.usercenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.*;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserInfoDTO;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.qto.UserQTO;

public class UserClientImpl implements UserClient {

	@Resource
	private UserDispatchService userDispatchService;

	public Response<Boolean> activeUser(Long userId, String appKey) {
		Request request = new BaseRequest();

		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ACTIVE_USER.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Void> deletedUser(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.DELETE_USER.getActionName());
		Response<Void> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> freezeUser(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.FREEZE_USER.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserDTO> getUserByEmail(String email, String appKey) {
		Request request = new BaseRequest();
		request.setParam("email", email);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_USER_BY_EMAIL.getActionName());
		Response<UserDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserDTO> getUserById(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
		Response<UserDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserDTO> getUserByMobile(String mobile, String appKey) {
		Request request = new BaseRequest();
		request.setParam("mobile", mobile);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_USER_BY_MOBILE.getActionName());
		Response<UserDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserDTO> getUserByName(String name, String appKey) {
		Request request = new BaseRequest();
		request.setParam("name", name);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_USER_BY_NAME.getActionName());
		Response<UserDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserDTO> addUser(UserDTO userDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userDTO", userDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_USER.getActionName());
		Response<UserDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserDTO> addOpenUser(UserDTO userDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userDTO", userDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_OPEN_USER.getActionName());
		Response<UserDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> moveUserIntoRecycle(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.MOVE_USER.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> restoreUser(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.RESTORE_USER.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> setUserRole(Long userId, Byte role, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("role", role);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.SET_ROLE.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> updateEmail(Long userId, String email, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("email", email);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_EMAIL.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> updateMobile(Long userId, String mobile, String appKey) {
		Request request = new BaseRequest();

		request.setParam("userId", userId);
		request.setParam("mobile", mobile);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_MOBILE.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Void> updatePwd(Long userId, String newPwd, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("newPwd", newPwd);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
		Response<Void> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> updateHeadImg(Long userId, String headImg, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("headImg", headImg);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_HEADIMG.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Boolean> updateInvitationCode(Long userId, String invitationCode, String appKey)
	{
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("invitationCode", invitationCode);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_INVITATION_CODE.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
	}

	public Response<String> generateInvitationCode(String appKey)
	{
		Request request = new BaseRequest();
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GENERATE_INVITATION_CODE.getActionName());
		Response<String> response = userDispatchService.execute(request);
		return response;
	}

	public Response<List<UserDTO>> queryUser(UserQTO userQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userQTO", userQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		Response<List<UserDTO>> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserDTO> apiUserLogin(UserInfoDTO userInfoDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userInfoDto", userInfoDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.API_USER_LOGIN.getActionName());
		Response<UserDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserDTO> userLogin(String loginName, String loginPwd, String appKey) {
		Request request = new BaseRequest();
		request.setParam("loginName", loginName);
		request.setParam("loginPwd", loginPwd);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.USER_LOGIN.getActionName());
		Response<UserDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserOpenInfoDTO> getUserOpenInfo(Integer openType, String openUid, String appKey) {
		Request request = new BaseRequest();
		request.setParam("openType", openType);
		request.setParam("openUid", openUid);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_USER_OPEN_INFO.getActionName());
		Response<UserOpenInfoDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserOpenInfoDTO> addUserOpenInfo(UserOpenInfoDTO userOpenInfoDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userOpenInfoDTO", userOpenInfoDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_USER_OPEN_INFO.getActionName());
		Response<UserOpenInfoDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Void> bingUserOpenInfo(Integer openType, String openUid,
										   String mobile, String password, String invitationCode, String appKey) {
		Request request = new BaseRequest();
		request.setParam("openType", openType);
		request.setParam("openUid", openUid);
		request.setParam("mobile", mobile);
		request.setParam("password", password);
		request.setParam("invitationCode", invitationCode);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.BIND_USER_OPEN_INFO.getActionName());
		Response<Void> response = userDispatchService.execute(request);
		return response;
	}
}
