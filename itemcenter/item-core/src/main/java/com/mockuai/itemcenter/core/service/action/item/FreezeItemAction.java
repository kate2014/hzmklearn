package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/28.
 */
@Service
public class FreezeItemAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(FreezeItemAction.class);
    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSearchManager itemSearchManager;

    @Override
    public ItemResponse doTransaction(RequestContext context) throws ItemException {
        try {
            ItemResponse response = null;
            ItemRequest request = context.getRequest();
            // 验证ID
            if (request.getLong("itemId") == null) {
                return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemId is missing");
            }
            if (request.getLong("supplierId") == null) {
                return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "supplierId is missing");
            }
            Long itemId = request.getLong("itemId");// 商品品牌ID
            Long sellerId = request.getLong("supplierId");// 供应商ID

            int result = itemManager.freezeItem(itemId, sellerId);
            if(result > 0){
                response = ResponseUtil.getSuccessResponse(true);
            }else{
                response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
            }
            //删除商品在搜索引擎中的索引
            itemSearchManager.deleteItemIndex(itemId, sellerId);

            return response;

        } catch (ItemException e) {
            log.error(e.toString());
            return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.FREEZE_ITEM_ACTION.getActionName();
    }
}
