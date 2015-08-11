package com.mockuai.itemcenter.core.service.action.skuproperty;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 删除商品属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteSkuPropertyAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(DeleteSkuPropertyAction.class);
	@Resource
	private SkuPropertyManager skuPropertyManager;

	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	TransactionTemplate transactionTemplate;


	public ItemResponse doTransaction(final RequestContext context) throws ItemException {
		ItemResponse response = (ItemResponse)transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					ItemResponse response = null;
					ItemRequest request = context.getRequest();
					// 验证ID
					if (request.getLong("ID") == null) {
						return ResponseUtil
								.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyID is missing");
					}
					// 验证sellerId
					if (request.getLong("sellerId") == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
					}
					Long skuPropertyId = request.getLong("ID");// Sku属性ID
					Long sellerId = request.getLong("sellerId");// 供应商ID
					
					// 获取skuId
					SkuPropertyDTO skuPropertyDTO = skuPropertyManager.getSkuProperty(skuPropertyId, sellerId);
					Long skuId = skuPropertyDTO.getSkuId();
					
					skuPropertyManager.deleteSkuProperty(skuPropertyId, sellerId);
					
					// 根据skuId和sellerId去查找sku_property表，然后将里面的属性拼成code_value更新到item_sku表中
					// 比如将 红色,17码,50g拼接成 "红色-17码-50g"
					SkuPropertyQTO skuPropertyQTO = new SkuPropertyQTO();
					skuPropertyQTO.setSkuId(skuId);
					skuPropertyQTO.setSellerId(sellerId);
					List<SkuPropertyDTO> codeValuePropertyDTOList = skuPropertyManager
							.querySkuProperty(skuPropertyQTO);
					itemSkuManager.updateItemSkuCodeValue(skuId, sellerId, codeValuePropertyDTOList);

					response = ResponseUtil.getSuccessResponse(true);
					return response;
				} catch (ItemException e) {
					status.setRollbackOnly();
					log.error(e.toString());
					return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
				}
			}
		});
		return response;

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_SKU_PROPERTY.getActionName();
	}
}
