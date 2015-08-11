package com.mockuai.usercenter.core.service.action.useropeninfo;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.NotifyManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.manager.UserOpenInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.service.action.TransAction;
import com.mockuai.usercenter.core.util.HttpUtil;
import com.mockuai.usercenter.core.util.ModelUtil;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
@Service
public class BindUserOpenInfo extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(BindUserOpenInfo.class);

    @Resource
    private UserOpenInfoManager userOpenInfoManager;
    @Resource
    private UserManager userManager;

    @Resource
    private NotifyManager notifyManager;

    @Override
    public UserResponse doTransaction(RequestContext context) throws UserException {
        UserRequest userRequest = context.getRequest();
        Integer openType = (Integer)userRequest.getParam("openType");
        String openUid = (String)userRequest.getParam("openUid");
        String mobile = (String)userRequest.getParam("mobile");
        String password = (String)userRequest.getParam("password");
        String invitationCode = (String)userRequest.getParam("invitationCode");
        String bizCode = (String)context.get("bizCode");
        UserDTO userDTO = userManager.getUserByMobile(mobile);

        UserOpenInfoDO userOpenInfoDO = userOpenInfoManager.getUserOpenInfo(openType, openUid, bizCode);
        if(userOpenInfoDO == null){
            return new UserResponse(ResponseCode.B_OPEN_ACCOUNT_NOT_EXIST);
        }
        //该手机号未被绑定，则绑定到该第三方开放账户所属的用户账户上
        if(userDTO == null){
            try{
                //更新用户名、手机号和密码
                UserDTO updateUser = new UserDTO();
                updateUser.setMobile(mobile);
                updateUser.setName(mobile);
                updateUser.setPassword(password);
                updateUser.setId(userOpenInfoDO.getUserId());
                int opNum = userManager.updateUser(updateUser);
                if(opNum != 1){
                    //TODO error handle
                }
            }catch(Exception e){
                log.error("",e);

                //TODO 这里为了临时解决问题先这么处理，后续充分测试后，可以把CATCH体里的代码去掉
                //更新原开放账户的手机号和密码
                int opNum = userManager.updateMobile(userOpenInfoDO.getUserId(), mobile);
                if(opNum != 1){
                    //TODO error handle
                    return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                }
                opNum = userManager.updatePwd(userOpenInfoDO.getUserId(), password);
                if(opNum != 1){
                    //TODO error handle
                    return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                }
            }
        }else{
            if(userDTO.getPassword().equals(password)){
                //已经绑定过了，则提示错误
                if(userDTO.getId().longValue() == userOpenInfoDO.getUserId().longValue()){
                    log.error("duplicate bind, openUid:{}, mobile:{}", openUid, mobile);
                    return new UserResponse(ResponseCode.B_BIND_DUPLICATE);
                }

                //如果该手机号已经被其他人绑定了，则提示错误
                UserOpenInfoDO getResult = userOpenInfoManager.getUserOpenInfoByUserId(openType, userDTO.getId(), bizCode);
                if(getResult!=null && getResult.getUserId().longValue()!=userOpenInfoDO.getUserId().longValue()){
                    log.error("mobile has bind by other, openUid:{}, mobile:{}", openUid, mobile);
                    return new UserResponse(ResponseCode.B_MOBILE_HAS_BINDED_BY_OTHER_PERSON);
                }

                //如果目前第三方账户上面绑定的用户是正常的用户（比如洋东西老数据中通过邮箱注册的用户），则提示错误。（需要让技术人员介入处理）
                UserDTO boundUser = userManager.getUserById(userOpenInfoDO.getUserId());
                if(boundUser!=null && boundUser.getType()!=2 ){
                    log.error("bind conflict, openUid:{}, mobile:{}", openUid, mobile);
                    return new UserResponse(ResponseCode.B_BIND_CONFLICT);
                }

                //TODO 将第三方开放信息关联到已有账户
                Long userId = userOpenInfoDO.getUserId();
                int opNum = userOpenInfoManager.updateUserId(userOpenInfoDO.getId(),
                        userOpenInfoDO.getUserId(), userDTO.getId(), bizCode);
                if(opNum != 1){
                    //TODO error handle
                    log.error("update error, openInfoId="+userOpenInfoDO.getId());
                    return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                }

                //删除原先第三方开放账户所关联的账户
                opNum = userManager.deleteUser(userId);
                if(opNum != 1){
                    //TODO error handle
                    log.error("delete error, userId="+userId);
                    return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                }
            }else{
                //TODO 提示密码错误，绑定失败
                return new UserResponse(ResponseCode.B_PASSWORD_ERROR);
            }
        }

        //如果邀请码不为空，则设置邀请人
        if(StringUtils.isBlank(invitationCode) == false){
            UserDTO getResult = userManager.getUserByMobile(mobile);
            if(getResult != null){
                Long inviterId = getResult.getInviterId();
                if (inviterId!=null && inviterId>0) {//邀请人已经存在
                    //TODO 确认如果邀请人已经存在的业务逻辑？？？
                }else{//邀请人不存在，则为其设置邀请人
                    //设置邀请人ID
                    UserDTO inviterDTO = userManager.getUserByInvitationCode(invitationCode);
                    if(inviterDTO == null){//邀请人不存在，则提示错误
                        throw new UserException(ResponseCode.B_INVITATION_CODE_INVALID,
                                "invitation code error");
                    }

                    UserDTO updateUser = new UserDTO();
                    updateUser.setId(getResult.getId());
                    updateUser.setInviterId(inviterDTO.getId());
                    int opNum = userManager.updateUser(updateUser);
                    if(opNum != 1){
                        //TODO error handle
                    }
                }
            }else{
                //TODO error handle
            }
        }


        //绑定成功，则发送绑定成功消息 (TODO 保证事务性，异步任务化)
        userOpenInfoDO = userOpenInfoManager.getUserOpenInfo(openType, openUid, bizCode);
        notifyManager.notifyBindUserMsg(mobile, openType, openUid);

        return new UserResponse<Void>(ResponseCode.REQUEST_SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.BIND_USER_OPEN_INFO.getActionName();
    }

//    private void notifyBindUserMsg(String mobile, int openType, String openUid){
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("topic", "user-bind-mobile-msg"));
//        Map<String,Object> data = new HashMap<String, Object>();
//        data.put("mobile", mobile);
//        data.put("open_type", openType);
//        data.put("open_uid", openUid);
//        params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));
//
//        //TODO 确保一定成功
//        String response = HttpUtil.post("http://121.40.98.204:8082/YDX-ERP/message/notify", params);
//    }

}
