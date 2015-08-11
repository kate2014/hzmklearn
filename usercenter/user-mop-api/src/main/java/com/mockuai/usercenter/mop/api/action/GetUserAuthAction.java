package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

import java.util.*;

public class GetUserAuthAction extends BaseAction {

    public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("userId", userId);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.GET_USER_AUTH_INFO.getActionName());

        Response<UserAuthInfoDTO> userResp = this.getUserDispatchService().execute(userReq);

        if(userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }else{
            UserAuthInfoDTO userAuthInfoDTO = userResp.getModule();
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("auth_info", MopApiUtil.genMopAuthInfo(userAuthInfoDTO));
            return new MopResponse(data);
        }
    }

    public String getName() {
        return "/user/auth/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }


}
