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
 * Created by wanghailong on 15-8-10.
 */
public class PageViewTest {
    private String requestPath1="/data/uv/query.do";
    private String requestPath2="/data/newVisitor/count.do";
    private String requestPath3="/data/itemTop/query.do";

    @Ignore
    public void test_pageView_success(){
        String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("seller_id", "9527"));
        params.add(new BasicNameValuePair("start_date","2015-1-10"));
        params.add(new BasicNameValuePair("end_date","2015-10-10"));

        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);
    }

    @Ignore
    public void test_newVisitor_success(){
        String reqUrl = TestConstant.REQ_PREFIX+requestPath2;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("seller_id", "9527"));
        params.add(new BasicNameValuePair("start_date","2015-8-1"));
        params.add(new BasicNameValuePair("end_date","2015-8-10"));

        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);
    }

    @Test
    public void test_itemTop_success(){
        String reqUrl = TestConstant.REQ_PREFIX+requestPath3;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("seller_id", "9527"));
//        params.add(new BasicNameValuePair("start_date","2015-8-2"));
//        params.add(new BasicNameValuePair("end_date","2015-8-10"));

        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);
    }
}
