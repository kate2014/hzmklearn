package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.core.util.HttpUtil;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 7/2/15.
 */
public interface NotifyManager {

    public void notifyBindUserMsg(String mobile, int openType, String openUid);

    public void notifyAddUserMsg(long userId);
}

