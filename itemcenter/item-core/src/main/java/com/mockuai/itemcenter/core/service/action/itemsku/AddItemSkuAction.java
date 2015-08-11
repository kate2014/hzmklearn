package com.mockuai.itemcenter.core.service.action.itemsku;

import java.util.List;

import javax.annotation.Resource;

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
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品销售属性(ItemSku) Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemSkuAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(AddItemSkuAction.class);
	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	private SkuPropertyManager skuPropertyManager;

	@Resource
	TransactionTemplate transactionTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public ItemResponse doTransaction(final RequestContext context) throws ItemException {
		ItemResponse response = (ItemResponse)transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					ItemResponse response = null;
					ItemRequest request = context.getRequest();
					// 验证DTO是否为空
					if (request.getParam("itemSkuDTO") == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemSkuDTO is null");
					}
					if (request.getParam("skuPropertyDTOList") == null) {
						return ResponseUtil
								.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyDTOList is null");
					}

					// 1.新增ItemSku记录
					ItemSkuDTO itemSkuDTO = (ItemSkuDTO) request.getParam("itemSkuDTO");
					List<SkuPropertyDTO> skuPropertyDTOList = (List<SkuPropertyDTO>) request
							.getParam("skuPropertyDTOList");

					itemSkuDTO = itemSkuManager.addItemSku(itemSkuDTO);// 新增加的itemSkuDO
					Long skuId = itemSkuDTO.getId();
					Long sellerId = itemSkuDTO.getSellerId();
					
					// 表sku_property新增字段item_id字段 & item_sku表需要关联item_id －－  updated by cwr
					Long itemId = itemSkuDTO.getItemId(); 

					// 2.新增SkuProperty列表
					List<SkuPropertyDTO> list = skuPropertyManager
							.addSkuProperty(itemId,skuId, sellerId, skuPropertyDTOList);
					// 3.将SkuProperty列表中的val值以 "17g-蓝色-XXL"的形式更新到ItemSku表中
					itemSkuManager.updateItemSkuCodeValue(skuId, sellerId, skuPropertyDTOList);

					itemSkuDTO.setSkuPropertyDTOList(list);
					response = ResponseUtil.getSuccessResponse(itemSkuDTO);
					return response;
				} catch (ItemException e) {
					status.setRollbackOnly();
					log.error(e.getMessage());
					return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
				}
			}
		});
		return response;

	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ITEM_SKU.getActionName();
	}
}
