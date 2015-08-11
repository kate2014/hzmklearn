package com.mockuai.shopplatform.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopplatform.api.BaseRequest;
import com.mockuai.shopplatform.api.Response;
import com.mockuai.shopplatform.constant.ActionEnum;
import com.mockuai.shopplatform.constant.ResponseCode;
import com.mockuai.shopplatform.domain.qto.ShopQTO;

/**
 * 搜索店铺,简单做根据名字搜索结果不排序;
 *
 * Created by luliang on 15/7/27.
 */
public class QueryShop extends BaseAction {

    public MopResponse execute(Request request) {
        String keyword = (String)request.getParam("keyword");
        ShopQTO shopQTO = new ShopQTO();
        shopQTO.setShopName(keyword);
        com.mockuai.shopplatform.api.Request shopRequest = new BaseRequest();
        shopRequest.setParam("shopQTO", shopQTO);
        shopRequest.setCommand(ActionEnum.QUERY_SHOP.getActionName());
        Response response = this.getShopService().execute(shopRequest);
        if(response.getCode() == ResponseCode.SUCCESS.getCode()) {
            return new MopResponse(response.getModule());
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/shop/query";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
