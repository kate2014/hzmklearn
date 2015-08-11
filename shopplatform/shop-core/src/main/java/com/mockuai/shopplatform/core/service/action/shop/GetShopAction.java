package com.mockuai.shopplatform.core.service.action.shop;

import com.mockuai.shopplatform.api.ShopResponse;
import com.mockuai.shopplatform.constant.ActionEnum;
import com.mockuai.shopplatform.constant.ResponseCode;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.core.manager.ShopImageManager;
import com.mockuai.shopplatform.core.manager.ShopItemGroupManager;
import com.mockuai.shopplatform.core.manager.ShopManager;
import com.mockuai.shopplatform.core.service.RequestContext;
import com.mockuai.shopplatform.core.service.ShopRequest;
import com.mockuai.shopplatform.core.service.action.Action;
import com.mockuai.shopplatform.core.util.ResponseUtil;
import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.domain.dto.ShopImageDTO;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ziqi.
 */
@Service
public class GetShopAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetShopAction.class);

    @Resource
    private ShopManager shopManager;
    @Resource
    private ShopImageManager shopImageManager;
    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        ShopDTO shopDTO = (ShopDTO) shopRequest.getParam("shopDTO");
        if(shopDTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopDTO is null");
        }

        try {

            shopDTO = shopManager.getShop(shopDTO);
            ShopImageDTO shopImageDTO = new ShopImageDTO();
            // img
            shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopIconId(), shopDTO.getSellerId());
            shopDTO.setShopIconUrl(shopImageDTO.getImageUrl());

            shopImageDTO = shopImageManager.getShopImage(shopDTO.getShopBannerImageId(), shopDTO.getSellerId());
            shopDTO.setShopBannerImageUrl(shopImageDTO.getImageUrl());

            // group item;
//            ShopItemGroupQTO shopItemGroupQTO = new ShopItemGroupQTO();
//            shopItemGroupQTO.setSellerId(shopDTO.getSellerId());
//            shopItemGroupQTO.setShopId(shopDTO.getId());
//            List<ShopItemGroupDTO> shopItemGroupDTOList = shopItemGroupManager.queryShopItemGroup(shopItemGroupQTO);
//            shopDTO.setShopItemGroupDTOList(shopItemGroupDTOList);
        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
        shopResponse = ResponseUtil.getSuccessResponse(shopDTO);
        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP.getActionName();
    }
}
