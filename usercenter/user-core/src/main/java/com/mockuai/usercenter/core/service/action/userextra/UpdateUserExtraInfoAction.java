//package com.mockuai.usercenter.core.service.action.userextra;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.manager.UserExtraInfoManage;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
//@Service
//public class UpdateUserExtraInfoAction implements Action {
//
//	@Resource
//	private UserExtraInfoManage userExtraInfoManage;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//		UserRequest userRequest = context.getRequest();
//
//		UserExtraInfoDTO userExtraDto = (UserExtraInfoDTO) userRequest
//				.getParam("userExtraDTO");
//
//		userExtraInfoManage.updateUserExtraInfo(userExtraDto);
//
//		return new UserResponse(true);
//	}
//
//	@Override
//	public String getName() {
//
//		return ActionEnum.UPDATE_USER_EXTRA_INFO.getActionName();
//	}
//
//}
