package com.mockuai.shopplatform.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.shopplatform.api.BaseRequest;
import com.mockuai.shopplatform.api.Response;
import com.mockuai.shopplatform.constant.ActionEnum;
import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.mop.api.util.JsonUtil;

/**
 * Created by luliang on 15/7/27.
 */
public class AddShop extends BaseAction {

    public MopResponse execute(Request request) {
        String shopStr = (String)request.getParam("shop");
        ShopDTO shopDTO = JsonUtil.parseJson(shopStr, ShopDTO.class);
        Long userId = (Long)request.getAttribute("user_id");
        shopDTO.setSellerId(userId);
        com.mockuai.shopplatform.api.Request shopRequest = new BaseRequest();
        shopRequest.setCommand(ActionEnum.ADD_SHOP.getActionName());
        shopRequest.setParam("shopDTO", shopDTO);
        Response<ShopDTO> shopResponse = this.getShopService().execute(shopRequest);

        return new MopResponse(shopResponse.getCode(), shopResponse.getMessage());
    }

    public String getName() {
        return "/shop/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
