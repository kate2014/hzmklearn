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
 *
 * 测试:找不到对应id
 */
public class ExportTaskTest {
    private String requestPath1 = "/item/export/task/query.do";
    private String requestPath2 = "/item/export/task/delete.do";

    @Test
    public void test_queryItemExportTask_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("page", "23"));
        params.add(new BasicNameValuePair("page_size", "9527"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }

    @Test
    public void test_deleteItemExportTask_success() {
        String reqUrl = TestConstant.REQ_PREFIX+requestPath2;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", "2"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
        Assert.assertTrue(resp.getCode() == 10000);

    }
}
