package com.mockuai.usercenter.core.service.action.userauth;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.UserAuthType;
import com.mockuai.usercenter.common.dto.EnterpriseAuthExtendDTO;
import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import com.mockuai.usercenter.core.manager.EnterpriseAuthExtendManager;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class GetUserAuthInfoAction implements Action {

	@Resource
	private UserAuthInfoManager userAuthInfoManager;

	@Resource
	private EnterpriseAuthExtendManager enterpriseAuthExtendManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();
		Long userId = (Long) userRequest.getParam("userId");
		Integer authType = (Integer) userRequest.getParam("authType");//认证类型

		try {
			//如果认证类型没传，则默认查询买家认证信息
			if(authType == null){
				authType = UserAuthType.BUYER_AUTH.getValue();
			}
			UserAuthInfoDO authInfoDO = userAuthInfoManager
					.getAuthInfoByUserId(userId, authType);

			UserAuthInfoDTO userAuthInfoDTO = ModelUtil.convertToUserAuthInfoDTO(authInfoDO);

			//如果是企业认证，则需要填充企业认证扩展信息
			if(authInfoDO!=null && authInfoDO.getType()!=null
					&& authInfoDO.getType()==UserAuthType.ENTERPRISE_AUTH.getValue()){
				EnterpriseAuthExtendDO enterpriseAuthExtendDO =
						enterpriseAuthExtendManager.getEnterpriseAuthExtend(authInfoDO.getUserId());

				EnterpriseAuthExtendDTO enterpriseAuthExtendDTO =
						ModelUtil.convertToEnterpriseAuthExtendDTO(enterpriseAuthExtendDO);
				userAuthInfoDTO.setEnterpriseAuthExtendDTO(enterpriseAuthExtendDTO);
			}
			return new UserResponse(userAuthInfoDTO);
		} catch (UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.GET_USER_AUTH_INFO.getActionName();
	}

}
