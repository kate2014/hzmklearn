package com.mockuai.shopplatform.core.service.action.shop;

import com.mockuai.shopplatform.api.ShopResponse;
import com.mockuai.shopplatform.constant.ActionEnum;
import com.mockuai.shopplatform.constant.ResponseCode;
import com.mockuai.shopplatform.core.exception.ShopException;
import com.mockuai.shopplatform.core.manager.ShopItemGroupManager;
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
 * Created by luliang on 15/7/31.
 */
@Service
public class QueryShopItemGroupAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(QueryShopItemGroupAction.class);

    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {

        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        ShopItemGroupQTO shopItemGroupQTO = (ShopItemGroupQTO) shopRequest.getParam("shopItemGroupQTO");
        if(shopItemGroupQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopItemGroupQTO is null");
        }

        List<ShopItemGroupDTO> shopItemGroupDTOList = null;
        try {
            shopItemGroupDTOList = shopItemGroupManager.queryShopItemGroup(shopItemGroupQTO);
        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
        shopResponse = ResponseUtil.getSuccessResponse(shopItemGroupDTOList, shopItemGroupQTO.getTotalCount());
        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SHOP_ITEM_GROUP.getActionName();
    }
}
