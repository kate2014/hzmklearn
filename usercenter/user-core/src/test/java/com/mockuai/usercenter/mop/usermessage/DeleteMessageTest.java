package com.mockuai.usercenter.mop.usermessage;

import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.usercenter.mop.ActionHolderMock;
import com.mockuai.usercenter.mop.RequestMock;
import com.mockuai.usercenter.mop.api.action.DeleteMessageAction;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 5/17/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeleteMessageTest {
    @Resource
    private ActionHolderMock actionHolderMock;

    @Test
    public void test_deleteMessage(){
        DeleteMessageAction deleteMessageAction =
                (DeleteMessageAction) actionHolderMock.getAction("/user/message/delete");
        RequestMock requestMock = new RequestMock();
        requestMock.setParam("id","2");
        requestMock.setAttribute("user_id", 1L);
        MopResponse mopResponse = deleteMessageAction.execute(requestMock);
        Assert.assertEquals(MopRespCode.REQUEST_SUCESS.getCode(), mopResponse.getCode());
        System.out.println("response>>>>>>>>>>>>>>>>"+JsonUtil.toJson(mopResponse));
    }
}
