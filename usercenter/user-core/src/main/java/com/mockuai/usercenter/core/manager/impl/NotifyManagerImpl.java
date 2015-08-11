package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.core.manager.NotifyManager;
import com.mockuai.usercenter.core.manager.ThreadPoolManager;
import com.mockuai.usercenter.core.util.HttpUtil;
import com.mockuai.usercenter.core.util.ModelUtil;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 7/2/15.
 */
@Service
public class NotifyManagerImpl implements NotifyManager {
    private static final Logger notifyLogger = LoggerFactory.getLogger("notifyLogger");

    @Resource
    private ThreadPoolManager threadPoolManager;

    public void notifyBindUserMsg(final String mobile, final int openType, final String openUid){
        //TODO 待重构成使用异步任务框架
        threadPoolManager.execute(new Runnable() {
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("topic", "user-bind-mobile-msg"));
                Map<String,Object> data = new HashMap<String, Object>();
                data.put("mobile", mobile);
                data.put("open_type", openType);
                data.put("open_uid", openUid);
                params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));
                try{
                    String response = HttpUtil.post("http://121.40.98.204:8082/YDX-ERP/message/notify", params);
                    notifyLogger.info("mobile:{}, openType:{}, openUid:{}, response:{}",
                            mobile, openType, openUid, response);
                }catch(Exception e){
                    notifyLogger.error("mobile:{}, openType:{}, openUid:{}", mobile, openType, openUid, e);
                }
            }
        });

    }

    public void notifyAddUserMsg(final long userId){
        //TODO 待重构成使用异步任务框架
        threadPoolManager.execute(new Runnable() {
            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("topic", "user-add-msg"));
                Map<String,Object> data = new HashMap<String, Object>();
                data.put("user_id", userId);
                params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));
                try{
                    String response = HttpUtil.post("http://121.40.98.204:8082/YDX-ERP/message/notify", params);
                    notifyLogger.info("userId:{}, response:{}", userId, response);
                }catch(Exception e){
                    notifyLogger.info("userId:{}", userId, e);

                }
            }
        });
    }
}
