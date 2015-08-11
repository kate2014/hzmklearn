package com.mockuai.sellercenter.web.action.test;

import com.mockuai.sellercenter.web.action.test.constant.TestConstant;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.util.HttpUtil;
import com.mockuai.sellercenter.web.util.JsonUtil;
import junit.framework.Assert;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghailong on 15-7-25.
 */
public class OrderTest {
    private String requestPath = "/order/cancel.do";

    @Test
    public void test_cancelOrder_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("order_id", "121"));
        params.add(new BasicNameValuePair("user_id", "85"));
        params.add(new BasicNameValuePair("cancel_reason", "sssss"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }
}
