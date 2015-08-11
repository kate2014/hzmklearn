package com.mockuai.sellercenter.web.action.test;

import com.mockuai.sellercenter.web.action.test.constant.TestConstant;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.util.HttpUtil;
import com.mockuai.sellercenter.web.util.JsonUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;

/**
 * Created by wanghailong on 15-7-25.
 */
public class CornerIconTest {
    private String requestPath1 = "/item/icon/add.do";
    private String requestPath2 = "/item/icon/delete.do";
//    private String requestPath3 = "/item/icon/query.do";

    @Test
    public void test_addItemIcon_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("icon_url", "[131231]"));
        params.add(new BasicNameValuePair("icon_name", ""));
        params.add(new BasicNameValuePair("icon_desc", "swe"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }

    @Test
    public void test_deleteItemIcon_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath2;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("icon_id", "22"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }

//    @Test
//    public void test_quaryItemIcon_success() {
//        String reqUrl = TestConstant.REQ_PREFIX+requestPath3;
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//
//    }



}
