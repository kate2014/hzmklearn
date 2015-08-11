package com.mockuai.itemcenter.core.service.action.brand;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.SellerBrandManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询商品品牌列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteSellerBrandAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QuerySellerBrandAction.class);
	
	@Resource
	private SellerBrandManager sellerBrandManager;
	
	@Resource
	private ItemManager itemManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("id") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerBrandID is null");
		}
//		if(request.getParam("supplierId") == null){
//			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "supplierId is null");
//		}
			
		Long id = (Long)request.getParam("id");
		Long supplierId = (Long) request.getParam("supplierId");
		ItemQTO itemQTO = new ItemQTO();
		
		//根据品牌id和供应商判断改品牌下是否有商品存在 如果是有商品存在就不能删除改品牌
		itemQTO.setItemBrandId(id);
		itemQTO.setSellerId(supplierId);
		Boolean itemExist = this.itemManager.isItemExist(itemQTO);
		if(itemExist){
			return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DB_DELETE,"改品牌下有商品存在，不能删除!");
		}
		
		int result = 0;
		result = sellerBrandManager.deleteSellerBrand(id, supplierId);
		if(result > 0){
			response = ResponseUtil.getSuccessResponse(true);
		}else{
			response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
		}
		return response;
		
//		try {
//			
//		} catch (ItemException e) {
//			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
//			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
//			return response;
//		}
	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_SELLER_BRAND.getActionName();
	}
}
