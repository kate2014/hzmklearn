package com.mockuai.shopplatform.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopplatform.api.BaseRequest;
import com.mockuai.shopplatform.api.Response;
import com.mockuai.shopplatform.api.ShopResponse;
import com.mockuai.shopplatform.constant.ActionEnum;
import com.mockuai.shopplatform.constant.ResponseCode;
import com.mockuai.shopplatform.domain.dto.ShopDTO;

/**
 * Created by luliang on 15/7/27.
 */
public class GetShop extends BaseAction {

    public MopResponse execute(Request request) {
        Long sellerId = (Long) request.getParam("seller_id");
        // 一个用户开一个店;
        if(sellerId == null) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "sellerId is invalid");
        }
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setSellerId(sellerId);
        com.mockuai.shopplatform.api.Request shopRequest = new BaseRequest();
        shopRequest.setParam("shopDTO", shopDTO);
        shopRequest.setCommand(ActionEnum.GET_SHOP.getActionName());
        Response response = this.getShopService().execute(shopRequest);
        if(response.getCode() == ResponseCode.SUCCESS.getCode()) {
            return new MopResponse(response.getModule());
        } else {
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/shop/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
