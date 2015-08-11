package com.mockuai.itemcenter.core.service.action.itemsku;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 更新商品销售属性(ItemSku) Action
 * 
 * @author chen.huang
 *
 */

@Service
public class DecreaseItemSkuStockAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DecreaseItemSkuStockAction.class);
	@Resource
	private ItemSkuManager itemSkuManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getLong("skuId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemSkuID is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		if (request.getLong("decreasedNumber") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "increasedNumber is missing");
		}
		long skuId = request.getLong("skuId");
		long sellerId = request.getLong("sellerId");
		long decreasedNumber = request.getLong("decreasedNumber");
		try {
			itemSkuManager.decreaseItemSkuStock(skuId, sellerId, decreasedNumber);
			response = ResponseUtil.getSuccessResponse(true);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName();
	}
}
