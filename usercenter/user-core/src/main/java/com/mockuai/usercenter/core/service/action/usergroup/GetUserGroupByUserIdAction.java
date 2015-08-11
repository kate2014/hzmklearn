//package com.mockuai.usercenter.core.service.action.usergroup;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.common.dto.UserGroupDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.manager.UserGroupManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
//@Service
//public class GetUserGroupByUserIdAction implements Action {
//
//	@Resource
//	private UserGroupManager userGroupManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//
//		UserRequest userRequest = context.getRequest();
//		Long userId = (Long) userRequest.getParam("userId");
//
//		UserGroupDTO userGroupDto = userGroupManager
//				.getUserGroupByUserId(userId);
//		return new UserResponse(userGroupDto);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.GET_USER_GROUP.getActionName();
//	}
//
//}
