package com.mockuai.itemcenter.core.service.action.brand;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SellerBrandManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

@Service
public class GetSellerBrandAction implements Action{
	private static final Logger log = LoggerFactory.getLogger(GetSellerBrandAction.class);
	
	@Resource
	private SellerBrandManager sellerBrandManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("id") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "brandId is null");
		}
//		if(request.getParam("supplierId") == null){
//			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "supplierId is null");
//		}
		
		Long id = (Long)request.getParam("id");
		Long supplierId = (Long) request.getParam("supplierId");
		SellerBrandDTO dto =null;
		try {
			 dto = this.sellerBrandManager.getSellerBrand(id, supplierId);
			 if(dto !=null){
				 response = ResponseUtil.getSuccessResponse(dto); 
			 }else{
				response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
			 }
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SELLER_BRAND.getActionName();
	}
}
