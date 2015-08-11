package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 7/6/15.
 */
@Service
public class UpdateInviterIdAction implements Action
{
    private static final Logger log = LoggerFactory.getLogger(UpdateInviterIdAction.class);
    @Resource
    private UserManager userManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {

        UserRequest request = context.getRequest();
        Long inviterId = (Long) request.getParam("inviterId");
        Long userId = (Long) request.getParam("userId");

        //入参检查
        if(userId == null){
            log.error("use id is null when update inviter id");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "userId is null");
        }

        if(inviterId == null){
            log.error("invite id is null when update inviter id");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "inviterId is null");
        }

        //校验指定的user信息
        UserDTO userResult = userManager.getUserById(userId);
        if(userResult == null){
            log.error("user result is null when update inviter id");
            return new UserResponse(ResponseCode.B_ACCOUNT_NOT_EXIST);
        }

        //如果待更新的邀请人id与原邀请人id相等，则直接返回true
        if(userResult.getInviterId()!=null
                && userResult.getInviterId().longValue()==inviterId.longValue()){
            return new UserResponse(ResponseCode.REQUEST_SUCCESS);
        }

        //校验指定的inviter信息
        UserDTO inviter = userManager.getUserById(inviterId);
        if(inviter == null){
            log.error("inviter is null when update inviter id");
            return new UserResponse(ResponseCode.B_INVITER_NOT_EXIST);
        }


        UserDTO updateUserDTO = new UserDTO();
        updateUserDTO.setId(userId);
        updateUserDTO.setInviterId(inviterId);
        int opNum = userManager.updateUser(updateUserDTO);
        if(opNum != 1){
            log.error("op number != 1");
            return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        return new UserResponse(ResponseCode.REQUEST_SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_INVITER_ID.getActionName();
    }
}
