package com.mockuai.shopplatform.core.service.action.shop;

import com.mockuai.shopplatform.api.ShopResponse;
import com.mockuai.shopplatform.constant.ActionEnum;
import com.mockuai.shopplatform.constant.ResponseCode;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.core.manager.ShopItemGroupManager;
import com.mockuai.shopplatform.core.manager.ShopManager;
import com.mockuai.shopplatform.core.service.RequestContext;
import com.mockuai.shopplatform.core.service.ShopRequest;
import com.mockuai.shopplatform.core.service.action.Action;
import com.mockuai.shopplatform.core.util.ResponseUtil;
import com.mockuai.shopplatform.domain.dto.ShopDTO;
import com.mockuai.shopplatform.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopplatform.domain.qto.ShopItemGroupQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 添加分组;
 * Created by luliang on 15/7/28.
 */
@Service
public class AddShopItemGroupAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddShopItemGroupAction.class);
    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Resource
    private ShopManager shopManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse response = null;
        ShopRequest request = context.getRequest();
        ShopItemGroupDTO shopItemGroupDTO = (ShopItemGroupDTO)request.getParam("shopItemGroupDTO");
        if(shopItemGroupDTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopItemGroupDTO is null");
        }
        Long sellerId = shopItemGroupDTO.getSellerId();
        if(sellerId !=  null) {
            ShopDTO shopDTO = new ShopDTO();
            shopDTO.setSellerId(shopItemGroupDTO.getSellerId());
            // 去反查shopId,设计的字段填充进去;
            shopDTO = shopManager.getShop(shopDTO);
            shopItemGroupDTO.setShopId(shopDTO.getId());
        }
        try {
            Long id = shopItemGroupManager.addShopItemGroup(shopItemGroupDTO);
            shopItemGroupDTO.setId(id);
            response = ResponseUtil.getSuccessResponse(shopItemGroupDTO);
            return response;
        } catch (ShopException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_SHOP_ITEM_GROUP.getActionName();
    }
}
