package com.mockuai.usercenter.core.service.action.userauth;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.UserAuthType;
import com.mockuai.usercenter.common.dto.EnterpriseAuthExtendDTO;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;
import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.EnterpriseAuthExtendManager;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 8/7/15.
 */
@Service
public class QueryUserAuthInfoAction implements Action {

    @Resource
    private UserAuthInfoManager userAuthInfoManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {

        UserRequest userRequest = context.getRequest();
        UserAuthInfoQTO userAuthInfoQTO = (UserAuthInfoQTO) userRequest.getParam("userAuthInfoQTO");
        String bizCode = (String)context.get("bizCode");

        //入参校验
        try {
            userAuthInfoQTO.setBizCode(bizCode);
            List<UserAuthInfoDO> authInfoDOs = userAuthInfoManager.queryAuthInfo(userAuthInfoQTO);

            return new UserResponse(ModelUtil.convertToUserAuthInfoDTOList(authInfoDOs));
        } catch (UserException e){
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_AUTH_INFO.getActionName();
    }

}
