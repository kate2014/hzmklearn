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
import java.util.concurrent.CopyOnWriteArrayList;

import junit.framework.Assert;


/**
 * Created by zensgzhangqiang on 7/8/15.
 */
public class UserMessageTest {
    private String requestPath1 = "/user/message/add.do";
    private String requestPath2 = "/user/message/queryGlobalMessage.do";
    private String requestPath3 = "/user/message/queryUserMessage.do";
    private String requestPath4 = "/user/feedback/queryFeedback.do";
//    @Test
//    public void test_addmessage_success(){// content is null
//        String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        //List<String> list = new ArrayList<String>();
//        params.add(new BasicNameValuePair("user_ids", "[131231]"));
//        params.add(new BasicNameValuePair("content", ""));
//        params.add(new BasicNameValuePair("send_type", "swe"));
//        params.add(new BasicNameValuePair("title", "ww"));
//        String response = HttpUtil.post(reqUrl, params);
//        System.out.println("response:"+response);
//        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
//        Assert.assertTrue(resp.getCode() == 10000);
//
//    }

//    @Test
//    public void test_addmessage_success1(){//send_type is null
//        String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        //List<String> list = new ArrayList<String>();
//        params.add(new BasicNameValuePair("user_ids", "[131231]"));
//        params.add(new BasicNameValuePair("content", "weq"));
//        params.add(new BasicNameValuePair("send_type", ""));
//        params.add(new BasicNameValuePair("title", "weq"));
//        String response = HttpUtil.post(reqUrl, params);
//        System.out.println("response:"+response);
//        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
//        Assert.assertTrue(resp.getCode() == 10000);
//    }
//@Test
//public void test_addmessage_success2(){//title is null
//    String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
//    List<NameValuePair> params = new ArrayList<NameValuePair>();
//    //List<String> list = new ArrayList<String>();
//    params.add(new BasicNameValuePair("user_ids", "[131231]"));
//    params.add(new BasicNameValuePair("content", "weq"));
//    params.add(new BasicNameValuePair("send_type", "1"));
//    params.add(new BasicNameValuePair("title", ""));
//    String response = HttpUtil.post(reqUrl, params);
//    System.out.println("response:"+response);
//    ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
//    Assert.assertTrue(resp.getCode() == 10000);
//
//}
//
//@Test
//public void test_addmessage_success3(){//id is wrong
//    String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
//    List<NameValuePair> params = new ArrayList<NameValuePair>();
//    //List<String> list = new ArrayList<String>();
//    params.add(new BasicNameValuePair("user_ids", "[85,91]"));
//    params.add(new BasicNameValuePair("content", "weq"));
//    params.add(new BasicNameValuePair("send_type", "1"));
//    params.add(new BasicNameValuePair("title", "sas"));
//    String response = HttpUtil.post(reqUrl, params);
//    System.out.println("response:"+response);
//    ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
//    Assert.assertTrue(resp.getCode() == 10000);
//
//}
//@Test
//public void test_addmessage_success3(){//global message
//    String reqUrl = TestConstant.REQ_PREFIX+requestPath1;
//    List<NameValuePair> params = new ArrayList<NameValuePair>();
//    //List<String> list = new ArrayList<String>();
//    params.add(new BasicNameValuePair("user_ids", "[85,91]"));
//    params.add(new BasicNameValuePair("content", "weq"));
//    params.add(new BasicNameValuePair("send_type", "1"));
//    params.add(new BasicNameValuePair("title", "oswfdhwfh"));
//    String response = HttpUtil.post(reqUrl, params);
//    System.out.println("response:"+response);
//    ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
//    Assert.assertTrue(resp.getCode() == 10000);
//
//}

//    @Test
//    public void test_queryGlobalmessage_success(){
//        String reqUrl = TestConstant.REQ_PREFIX+requestPath2;
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("current_page",null));
//        params.add(new BasicNameValuePair("page_size",null));
//        String response = HttpUtil.post(reqUrl, params);
//        System.out.println("response:"+response);
//        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
//        Assert.assertTrue(resp.getCode()==10000);
//        //done
//    }

//    @Test
//    public void testQueryUserMessage() {
//        String reqUrl = TestConstant.REQ_PREFIX + requestPath3;
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("current_page", "3"));
//        params.add(new BasicNameValuePair("page_size", "5"));
//        String response = HttpUtil.post(reqUrl, params);
//        System.out.println("response:" + response);
////        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
//     //   Assert.assertTrue(resp.getCode() == 10000);
//        //dones
//    }

    @Test
    public void testQueryFeedBackMessageSuccess(){
        String reqUrl = TestConstant.REQ_PREFIX+requestPath4;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("start_time","2015-1-10"));
        params.add(new BasicNameValuePair("end_time","2015-10-10"));
        params.add(new BasicNameValuePair("current_page", "3"));
        params.add(new BasicNameValuePair("page_size", "4"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:"+response);
//        ApiResponse resp = JsonUtil.parseJson(response, ApiResponse.class);
 //       Assert.assertTrue(resp.getCode()==10000);
        //done
    }

}
