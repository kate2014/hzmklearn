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
public class ItemBrandTest {
    private String requestPath1 = "/item/brand/get.do";
//    private String requestPath2 = "/item/brand/query.do";
    private String requestPath3 = "/item/brand/delete.do";
    private String requestPath4 = "/item/brand/add.do";
    private String requestPath5 = "/item/brand/update.do";

    @Test
    public void test_getItemBrand_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("brand_id", "23"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }

//    @Test
//    public void test_quaryItemBrand_success() {
//        String reqUrl = TestConstant.REQ_PREFIX+requestPath2;
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//    }

    @Test
    public void test_deleteItemBrand_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath3;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("brand_id", "23"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }

    @Test
    public void test_addItemBrand_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath4;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("brand_name", "ceshiname"));
        params.add(new BasicNameValuePair("brand_en_name", "ceshien"));
        params.add(new BasicNameValuePair("category_class", "ceshiclass"));
        params.add(new BasicNameValuePair("logo", "ceshilogo"));
        params.add(new BasicNameValuePair("brand_desc", "ceshidesc"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }

    @Test
    public void test_updateItemBrand_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath4;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("brand_id", "22"));
        params.add(new BasicNameValuePair("brand_name", "ceshi_name"));
        params.add(new BasicNameValuePair("brand_en_name", "ceshi_en"));
        params.add(new BasicNameValuePair("category_class", "ceshi_class"));
        params.add(new BasicNameValuePair("logo", "ceshi_logo"));
        params.add(new BasicNameValuePair("brand_desc", "ceshi_desc"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);
    }
}
