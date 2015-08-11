package com.mockuai.itemcenter.core.service.action.collection;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCollectionManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

import java.util.List;

/**
 * 删除商品收藏Action
 */
@Service
public class DeleteItemCollectionAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemCollectionAction.class);
	@Resource
	private ItemCollectionManager itemCollectionManager;

	public ItemResponse doTransaction(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getParam("itemIdList") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemIdList is null");
		}

		if (request.getLong("userId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "user_id is null");
		}

		List<Long> itemIdList = (List<Long>)request.getParam("itemIdList");
		Long userId = request.getLong("userId");
		try {
			//TODO 待重构成批量删除操作
			for(Long itemId: itemIdList){
				Boolean numOfDeleted = itemCollectionManager.deleteItemCollectionByItemId(itemId,userId);
			}
			response = ResponseUtil.getSuccessResponse(true);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEM_COLLECTION.getActionName();
	}
}
