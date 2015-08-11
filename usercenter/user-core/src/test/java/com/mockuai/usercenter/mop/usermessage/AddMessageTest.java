package com.mockuai.usercenter.mop.usermessage;

import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.mop.ActionHolderMock;
import com.mockuai.usercenter.mop.RequestMock;
import com.mockuai.usercenter.mop.api.action.AddMessageAction;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddMessageTest {
    @Resource
    private ActionHolderMock actionHolderMock;

    @Test
    public void test_addMessage(){
        AddMessageAction addMessageAction =
                (AddMessageAction) actionHolderMock.getAction("/user/message/add");
        RequestMock requestMock = new RequestMock();
        UserMessageDTO messageDO = new UserMessageDTO();
        messageDO.setReceiverId(8L);
        //messageDO.setSenderId(3);
        messageDO.setContent("http_test");
        messageDO.setTitle("http_title");
        messageDO.setSendType(1);
        messageDO.setStatus(0);

        String message = JsonUtil.toJson(messageDO);
        requestMock.setParam("message", message);

        MopResponse mopResponse = addMessageAction.execute(requestMock);
        Assert.assertEquals(MopRespCode.REQUEST_SUCESS.getCode(), mopResponse.getCode());
        System.out.println("response>>>>>>>>>>>>>>>>" + JsonUtil.toJson(mopResponse));
    }
}
