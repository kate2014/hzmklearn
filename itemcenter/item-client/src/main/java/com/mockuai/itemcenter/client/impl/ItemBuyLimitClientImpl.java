package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemBuyLimitClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/17.
 */
public class ItemBuyLimitClientImpl implements ItemBuyLimitClient {

    @Resource
    private ItemService itemService;

    public Response<Integer> getItemBuyLimit(Long itemId, Long sellerId) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("sellerId", sellerId);
        request.setCommand(ActionEnum.GET_ITEM_BUY_LIMIT.getActionName());
        return itemService.execute(request);
    }
}
