package com.mockuai.usercenter.mop.usermessage;

import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.usercenter.common.qto.UserMessageQTO;
import com.mockuai.usercenter.mop.ActionHolderMock;
import com.mockuai.usercenter.mop.RequestMock;
import com.mockuai.usercenter.mop.api.action.QueryMessageAction;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
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
public class QueryMessageTest {
    @Resource
    private ActionHolderMock actionHolderMock;

    @Test
    public void test_queryMessage(){
        QueryMessageAction queryMessageAction =
                (QueryMessageAction) actionHolderMock.getAction("/user/message/list");
        RequestMock requestMock = new RequestMock();

        UserMessageQTO userMessageQto = new UserMessageQTO();
        userMessageQto.setReceiverId(1L);
        userMessageQto.setPageNum(1L);
        userMessageQto.setPageSize(3);

        String message = JsonUtil.toJson(userMessageQto);
        requestMock.setParam("userMessageQTO", message);

        MopResponse mopResponse = queryMessageAction.execute(requestMock);
        //Assert.assertEquals(MopRespCode.REQUEST_SUCESS.getCode(), mopResponse.getCode());
        System.out.println("response>>>>>>>>>>>>>>>>"+JsonUtil.toJson(mopResponse));
    }
}
