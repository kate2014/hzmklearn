package com.mockuai.usercenter.core.service.action.userauth;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.constant.UserAuthStatus;
import com.mockuai.usercenter.common.constant.UserAuthType;
import com.mockuai.usercenter.common.dto.EnterpriseAuthExtendDTO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import com.mockuai.usercenter.core.manager.EnterpriseAuthExtendManager;
import com.mockuai.usercenter.core.service.action.TransAction;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

/**
 * 原先的添加用户认证信息和修改用户认证信息改为同一个接口
 * */
@Service
public class AddUserAuthInfoAction extends TransAction {

	@Resource
	private UserAuthInfoManager userAuthInfoManager;

	@Resource
	private EnterpriseAuthExtendManager enterpriseAuthExtendManager;

	@Override
	protected UserResponse doTransaction(RequestContext context) throws UserException {
		UserRequest userRequest = context.getRequest();
		UserAuthInfoDTO authInfoDTO = (UserAuthInfoDTO) userRequest.getParam("userAuthInfoDTO");// 用户实名认证的真实消息
		String bizCode = (String)context.get("bizCode");

		if(authInfoDTO == null){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "authInfoDTO is null");
		}
		authInfoDTO.setBizCode(bizCode);

		//如果未设置认证类型，则认证类型默认置为买家个人认证
		if(authInfoDTO.getType() == null){
			authInfoDTO.setType(UserAuthType.BUYER_AUTH.getValue());
		}

		//入参检查
		if(authInfoDTO.getType().intValue() == UserAuthType.SELLER_AUTH.getValue()){
			if(StringUtils.isBlank(authInfoDTO.getIdcardHoldImg())){
				return new UserResponse(ResponseCode.P_PARAM_NULL, "idcard hold img is null");
			}
		}else if(authInfoDTO.getType().intValue() == UserAuthType.ENTERPRISE_AUTH.getValue()){
			if(authInfoDTO.getEnterpriseAuthExtendDTO() == null){
				return new UserResponse(ResponseCode.P_PARAM_NULL, "enterprise auth extend info is null");
			}
		}

		try {

			UserAuthInfoDO userAuthInfoDO = ModelUtil.convertToUserAuthInfoDO(authInfoDTO);
			//默认设置为待认证状态
			userAuthInfoDO.setStatus(UserAuthStatus.AUTH_WAIT.getValue());

			//添加认证信息
			Long authInfoId = userAuthInfoManager.addUserAuthInfo(userAuthInfoDO);

			//如果是卖家企业认证，则需要添加企业认证扩展信息
			if(authInfoDTO.getType().intValue() == UserAuthType.ENTERPRISE_AUTH.getValue()){
				EnterpriseAuthExtendDTO enterpriseAuthExtendDTO = authInfoDTO.getEnterpriseAuthExtendDTO();
				enterpriseAuthExtendDTO.setBizCode(bizCode);
				enterpriseAuthExtendDTO.setUserId(authInfoDTO.getUserId());
				enterpriseAuthExtendManager.addEnterpriseAuthExtend(
						ModelUtil.convertToEnterpriseAuthExtendDO(enterpriseAuthExtendDTO));
			}
			return new UserResponse(authInfoId);
		} catch (UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_USER_AUTH_INFO.getActionName();
	}

}
