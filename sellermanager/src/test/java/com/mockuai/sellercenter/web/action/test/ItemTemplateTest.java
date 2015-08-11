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
public class ItemTemplateTest {
    private String requestPath = "/item/template/add.do";

    @Test
    public void test_addItemTemplate_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("template_name", "ceshi_template_name"));
        params.add(new BasicNameValuePair("name", "ceshi_name"));
        params.add(new BasicNameValuePair("sub_name", "ceshi_sub_name"));
        params.add(new BasicNameValuePair("cate_id", "1"));
        params.add(new BasicNameValuePair("brief", "ceshi_brief"));
        params.add(new BasicNameValuePair("description", "ceshi_description"));
        params.add(new BasicNameValuePair("brand_id", "1"));
        params.add(new BasicNameValuePair("user_min_bought", "1"));
        params.add(new BasicNameValuePair("user_max_bought", "100"));
        params.add(new BasicNameValuePair("corner_icon", "11"));
        params.add(new BasicNameValuePair("delivery_type", "4"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }
}
