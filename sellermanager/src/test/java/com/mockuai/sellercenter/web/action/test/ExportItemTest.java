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
 * Created by wanghailong on 15-7-27.
 */
public class ExportItemTest {
    private String requestPath="/item/export.do";

    @Test
    public void test_exportItem_success(){
        String reqUrl = TestConstant.REQ_PREFIX+requestPath;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", "item_name"));
        params.add(new BasicNameValuePair("item_status", "4"));
        params.add(new BasicNameValuePair("brand_key", "1"));
        params.add(new BasicNameValuePair("bar_code", "bizCode"));
        params.add(new BasicNameValuePair("category_id", "419"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);
    }
}
