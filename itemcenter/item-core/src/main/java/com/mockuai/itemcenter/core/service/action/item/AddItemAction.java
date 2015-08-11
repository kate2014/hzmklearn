package com.mockuai.itemcenter.core.service.action.item;

import java.util.*;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.LimitEntity;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.action.TransAction;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(AddItemAction.class);
	@Resource
	private ItemManager itemManager;

	@Resource
	private ItemSkuManager itemSkuManager;
	
	@Resource
	private ItemPropertyManager itemPropertyManager; // updated by cwr

	@Resource
	private SkuPropertyManager skuPropertyManager;

	@Resource
	private ItemImageManager itemImageManager;

	@Resource
	private ItemSearchManager itemSearchManager;
	
	@Resource
	private CompositeItemManager compositeItemManager;

	@Resource
	private ItemBuyLimitManager itemBuyLimitManager;
	
	@Resource 
	private ItemPropertyTmplManager itemPropertyTmplManager;
	
	@Resource
	TransactionTemplate transactionTemplate;

	public ItemResponse doTransaction(final RequestContext context) throws ItemException {
		//@SuppressWarnings("rawtypes")
		
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("itemDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
		}
		ItemDTO itemDTO = (ItemDTO) request.getParam("itemDTO");

		//上传商品描述内容

		// 1.首先添加item返回itemId
		ItemDTO retItemDTO = itemManager.addItem(itemDTO);// 新增加的itemDO


		Long itemId = retItemDTO.getId();
		Long sellerId = retItemDTO.getSellerId();
		
		//写入属性具体值的时候需要同时写入bizMark 和 propretyCode  需要先查询出来
		ItemPropertyTmplQTO itemPropertyTmplQTO= new ItemPropertyTmplQTO();
		//根据该商品的所属的类目来查找属性列表 该属性有一些基本的基本配置比如： bizMark 和 propertyCode
		itemPropertyTmplQTO.setCategoryId(itemDTO.getCategoryId());
		Map<Long,ItemPropertyTmplDO> propertyTmplMap = new HashMap<Long,ItemPropertyTmplDO>();
		List<ItemPropertyTmplDO> itemPropertyTmplList = this.itemPropertyTmplManager.queryItemPropertyTmpl(itemPropertyTmplQTO);
		if(itemPropertyTmplList != null){
			for(ItemPropertyTmplDO item : itemPropertyTmplList){
				propertyTmplMap.put(item.getId(),item);
			}
		}

		//普通属性的处理  非sku属性的处理  --  updated by cwr 
		List<ItemPropertyDTO> itemPropertyList = itemDTO.getItemPropertyList();
		if(!CollectionUtils.isEmpty(itemPropertyList)){
			for(ItemPropertyDTO itemProperty : itemPropertyList){
				itemProperty.setItemId(itemId);
				itemProperty.setSellerId(sellerId);
				
				ItemPropertyTmplDO  itemPropertyTmplDO = propertyTmplMap.get(itemProperty.getItemPropertyTmplId());
				itemProperty.setBizMark(itemPropertyTmplDO == null ? null : itemPropertyTmplDO.getBizMark());
				itemProperty.setCode(itemPropertyTmplDO == null ? null : itemPropertyTmplDO.getCode());
				itemProperty.setValueType(itemPropertyTmplDO ==null ? null: itemPropertyTmplDO.getValueType());
				//TODO 根据字段定义的valueType来判断必须是数字  有些字段必须是数字 比如重量字段必须是数字
				
				// TODO 后续考虑使用批量写入
				ItemPropertyDTO itemProperty2 = this.itemPropertyManager.addItemProperty(itemProperty);
			}
		}

		// 返回的ItemSkuDTO列表
		List<ItemSkuDTO> retItemSkuDTOList = new ArrayList<ItemSkuDTO>();
		List<ItemSkuDTO> itemSkuDTOList = itemDTO.getItemSkuDTOList();
		for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
			itemSkuDTO.setItemId(itemId);
			itemSkuDTO.setSellerId(sellerId);
			// 2.添加ItemSkuList
			ItemSkuDTO retitemSkuDTO = itemSkuManager.addItemSku(itemSkuDTO);
			Long skuId = retitemSkuDTO.getId();
			
			List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
			//2.5将code_value值更新到item_sku表中
			itemSkuManager.updateItemSkuCodeValue(skuId, sellerId, skuPropertyDTOList);
			// 3.添加SkuPropertyList
			List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
					.addSkuProperty(itemId,skuId, sellerId, skuPropertyDTOList);
			retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
			retItemSkuDTOList.add(retitemSkuDTO);
			
		}
		System.out.println("add itemSku success");

		// 4.添加副图列表
		List<ItemImageDTO> itemImageDTOList = itemDTO.getItemImageDTOList();
		List<ItemImageDTO> retItemImageDTOList = itemImageManager
				.addItemImage(itemId, sellerId, itemImageDTOList);

		retItemDTO.setItemSkuDTOList(retItemSkuDTOList);
		retItemDTO.setItemImageDTOList(retItemImageDTOList);
		response = ResponseUtil.getSuccessResponse(retItemDTO);

		// 设置限购;
		if(itemDTO.getBuyLimit() != null) {
			for(LimitEntity entity: itemDTO.getBuyLimit()) {
				ItemBuyLimitDTO itemBuyLimitDTO = new ItemBuyLimitDTO();
				itemBuyLimitDTO.setSellerId(sellerId);
				itemBuyLimitDTO.setItemId(itemId);
				itemBuyLimitDTO.setDeleteMark(0);
				itemBuyLimitDTO.setBeginTime(entity.getBeginTime());
				itemBuyLimitDTO.setEndTime(entity.getEndTime());
				itemBuyLimitDTO.setBizCode(itemDTO.getBizCode());
				itemBuyLimitDTO.setBuyCount(entity.getLimitCount());
				itemBuyLimitManager.addItemBuyLimit(itemBuyLimitDTO);
			}
		}

		return response;
		


	}

	private ItemSkuDTO getCheapestItemSku(List<ItemSkuDTO> itemSkuDTOs){
		if(itemSkuDTOs == null){
			return null;
		}

		Map<Long, ItemSkuDTO> itemPriceMap = new HashMap<Long, ItemSkuDTO>();
		for(ItemSkuDTO itemSkuDTO: itemSkuDTOs){
			itemPriceMap.put(itemSkuDTO.getPromotionPrice(), itemSkuDTO);
		}
		//TODO
		return null;
	}
	
	@Override
	public String getName() {
		return ActionEnum.ADD_ITEM.getActionName();
	}
}
