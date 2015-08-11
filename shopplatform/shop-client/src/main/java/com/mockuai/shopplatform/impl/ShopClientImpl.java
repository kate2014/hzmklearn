package com.mockuai.shopplatform.impl;

import com.mockuai.shopplatform.ShopClient;
import com.mockuai.shopplatform.api.BaseRequest;
import com.mockuai.shopplatform.api.Request;
import com.mockuai.shopplatform.api.Response;
import com.mockuai.shopplatform.api.ShopService;
import com.mockuai.shopplatform.constant.ActionEnum;
import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;
import com.mockuai.shopplatform.domain.qto.ShopQTO;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ziqi.
 */
public class ShopClientImpl implements ShopClient{

    @Resource
    private ShopService shopService;

    public Response<ShopDTO> addShop(ShopDTO shopDTO) {
        Request request = new BaseRequest();
        request.setParam("shopDTO", shopDTO);
        request.setCommand(ActionEnum.ADD_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<List<ShopDTO>> queryShop(ShopQTO shopQTO) {
        Request request = new BaseRequest();
        request.setParam("shopQTO", shopQTO);
        request.setCommand(ActionEnum.QUERY_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<ShopDTO> getShop(ShopDTO shopDTO) {
        Request request = new BaseRequest();
        request.setParam("shopDTO", shopDTO);
        request.setCommand(ActionEnum.GET_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> updateShop(ShopDTO shopDTO) {
        Request request = new BaseRequest();
        request.setParam("shopDTO", shopDTO);
        request.setCommand(ActionEnum.UPDATE_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<ShopItemGroupDTO> addShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) {
        Request request = new BaseRequest();
        request.setParam("shopItemGroupDTO", shopItemGroupDTO);
        request.setCommand(ActionEnum.ADD_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> updateShopItemGroup(ShopItemGroupDTO shopItemGroupDTO) {
        Request request = new BaseRequest();
        request.setParam("shopItemGroupDTO", shopItemGroupDTO);
        request.setCommand(ActionEnum.UPDATE_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<ShopItemGroupDTO> getShopItemGroup(Long sellerId, Long groupId) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("groupId", groupId);
        request.setCommand(ActionEnum.GET_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> deleteShopItemGroup(Long id, Long sellerId) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("sellerId", sellerId);
        request.setCommand(ActionEnum.DELETE_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<List<ShopItemGroupDTO>> queryShopItemGroup(ShopItemGroupQTO shopItemGroupQTO) {
        Request request = new BaseRequest();
        request.setParam("shopItemGroupQTO", shopItemGroupQTO);
        request.setCommand(ActionEnum.QUERY_SHOP_ITEM_GROUP.getActionName());
        return shopService.execute(request);
    }

    public Response<Integer> getShopStatus(Long sellerId) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setCommand(ActionEnum.GET_SHOP_STATUS.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> freezeShop(Long sellerId) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setCommand(ActionEnum.FREEZE_SHOP.getActionName());
        return shopService.execute(request);
    }

    public Response<Boolean> thawShop(Long sellerId) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setCommand(ActionEnum.THAW_SHOP.getActionName());
        return shopService.execute(request);
    }
}
