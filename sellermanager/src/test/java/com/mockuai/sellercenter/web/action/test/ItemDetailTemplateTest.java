package com.mockuai.sellercenter.web.action.test;

import com.mockuai.sellercenter.web.action.test.constant.TestConstant;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.util.HttpUtil;
import com.mockuai.sellercenter.web.util.JsonUtil;
import junit.framework.Assert;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghailong on 15-7-25.
 */
public class ItemDetailTemplateTest {
    private String requestPath1 = "/item/detail/template/add.do";
    private String requestPath2 = "/item/detail/template/query.do";
    private String requestPath3 = "/item/detail/template/get.do";

    @Ignore
    public void test_addDetailTemplate_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("template_name", "ceshi_template_name"));
        params.add(new BasicNameValuePair("header_html", "ceshi_header_html"));
        params.add(new BasicNameValuePair("header_tms_id", "ceshi_header_tms_id"));
        params.add(new BasicNameValuePair("footer_html", "ceshi_footer_html"));
        params.add(new BasicNameValuePair("footer_tms_id", "ceshi_footer_tms_id"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }

    @Ignore
    public void test_quaryDetailTemplate_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath2;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("page", "1"));
        params.add(new BasicNameValuePair("page_size", "10"));

        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }

    @Test
    public void test_getDetailTemplate_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath3;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", "4"));

        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }
}
