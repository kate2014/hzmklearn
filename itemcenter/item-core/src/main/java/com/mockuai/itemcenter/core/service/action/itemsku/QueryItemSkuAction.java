package com.mockuai.itemcenter.core.service.action.itemsku;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品销售属性(ItemSku)列表 Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryItemSkuAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemSkuAction.class);
	@Resource
	private ItemSkuManager itemSkuManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("itemSkuQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemSkuQTO is null");
		}
		ItemSkuQTO itemSkuQTO = (ItemSkuQTO) request.getParam("itemSkuQTO");

		try {
			List<ItemSkuDTO> ItemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);
			response = ResponseUtil.getSuccessResponse(ItemSkuDTOList, itemSkuQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_SKU.getActionName();
	}
}
