package com.mockuai.usercenter.core.api.impl;

import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.service.RequestDispatcher;

import javax.annotation.Resource;

/**
 * Created by idoud on 4/24/15.
 */
public class UserDispatchServiceImpl implements UserDispatchService {
    @Resource
    private RequestDispatcher requestDispatcher;

    public UserResponse execute(Request request) {
        return requestDispatcher.dispatch(new RequestAdapter(request));
    }
}
