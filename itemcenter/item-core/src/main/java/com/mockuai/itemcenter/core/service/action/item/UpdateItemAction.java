package com.mockuai.itemcenter.core.service.action.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.LimitEntity;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.manager.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
 * 更新商品Action
 * 
 * @author chen.huang
 *
 */
@Service
public class UpdateItemAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateItemAction.class);
	@Resource
	private ItemManager itemManager;
	
	@Resource 
	private SkuPropertyManager skuPropertyManager;
	
	@Resource
	private ItemPropertyManager itemPropertyManager;
	
	@Resource
	private ItemImageManager itemImageManager;
	
	@Resource
	private ItemSkuManager itemSkuManager;
	
	@Resource 
	private ItemPropertyTmplManager itemPropertyTmplManager;
	
	@Resource
	private CompositeItemManager compositeItemManager;

	@Resource
	private ItemSearchManager itemSearchManager;

	@Resource
	private ItemBuyLimitManager itemBuyLimitManager;
	
	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("itemDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
		}
		ItemDTO itemDTO = (ItemDTO) request.getParam("itemDTO");
		
		// 如果需要修改所有的商品下面的属性 销售属性 及其 图片等 需要先删除 在写入  -- updated by cwr
		Long itemId = itemDTO.getId();
		Long supplierId = itemDTO.getSellerId();

		//判断指定商品是否存在
		ItemDTO itemResult = itemManager.getItem(itemId, supplierId);
		if(itemResult == null){
			//TODO error handle
		}

		// 添加新的纪录;
		if(itemDTO.getBuyLimit() != null) {
			// 限购;
			itemBuyLimitManager.deleteItemBuyLimit(supplierId, itemId); // 删除原有的限购

			for(LimitEntity entity: itemDTO.getBuyLimit()) {
				ItemBuyLimitDTO itemBuyLimitDTO = new ItemBuyLimitDTO();
				itemBuyLimitDTO.setSellerId(supplierId);
				itemBuyLimitDTO.setItemId(itemId);
				itemBuyLimitDTO.setDeleteMark(0);
				itemBuyLimitDTO.setBeginTime(entity.getBeginTime());
				itemBuyLimitDTO.setEndTime(entity.getEndTime());
				itemBuyLimitDTO.setBizCode(itemDTO.getBizCode());
				itemBuyLimitDTO.setBuyCount(entity.getLimitCount());
				itemBuyLimitManager.addItemBuyLimit(itemBuyLimitDTO);
			}
		}

		
		//TODO 商品业务状态检查

		if(request.getParam("updateDetail")!= null && (Boolean)(request.getParam("updateDetail"))){
			// 先更改item表
			Boolean isSuccessfullyUpdated = itemManager.updateItem(itemDTO);
			//删除item下面的图片 基本属性 销售属性 item_sku 在写入
			int imageRows = this.itemImageManager.deleteItemImageListByItemId(itemId, supplierId);
			int itemPropertyRows = this.itemPropertyManager.deleteByItemId(itemId, supplierId);
			int skuPropertyRows =this.skuPropertyManager.deleteByItemId(itemId, supplierId);
//			int itemSkuRows = this.itemSkuManager.deleteByItemId(itemId, supplierId);

			//TODO 商品索引更新逻辑异步化;索引更新与商品更新的事务性保证
			//更新商品索引
			itemResult = itemManager.getItem(itemId, supplierId);//查询更新完毕之后的商品信息
			if(itemResult.getDeleteMark().intValue()!=1 && itemResult.getItemStatus().intValue()==4){
				itemSearchManager.setItemIndex(itemResult);
			}

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
			
			// 在写入
			List<ItemPropertyDTO> itemPropertyList = itemDTO.getItemPropertyList();
			if(!CollectionUtils.isEmpty(itemPropertyList)){
				for(ItemPropertyDTO itemProperty : itemPropertyList){
					itemProperty.setItemId(itemId);
					itemProperty.setSellerId(supplierId);
					
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
			boolean isFirst = true;

			//查询该商品的sku列表
			ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
			itemSkuQTO.setItemId(itemId);
			itemSkuQTO.setSellerId(supplierId);
			List<ItemSkuDTO> itemOldSkuList = itemSkuManager.queryItemSku(itemSkuQTO);//该商品原有的sku列表
			if(itemOldSkuList == null){
				//TODO error handle
			}

			/**
			 * 处理sku的增删改查
			 * （1）如果待更新sku不带id，则认为是新增sku;
			 * （2）如果待更新sku带id，则认为是需要更新的sku;
			 * （3）如果原有sku中，有sku不存在于待更新sku列表中，则删除该原有sku
			 */
			for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
				if(itemSkuDTO.getId() == null){//新增的sku
					itemSkuDTO.setItemId(itemId);
					itemSkuDTO.setSellerId(supplierId);
					// 2.添加ItemSkuList
					ItemSkuDTO retitemSkuDTO = this.itemSkuManager.addItemSku(itemSkuDTO);
					Long skuId = retitemSkuDTO.getId();

				/* 组合商品部分暂时不需要
				if(itemSkuDTO.getIsComposite() != null && itemSkuDTO.getIsComposite()){
					if(isFirst){
						isFirst = false;//只需要在第一次时候删除
						int compositeItemRow = this.compositeItemManager.deleteCompositeItemByItemId(itemId, supplierId);
					}
					List<CompositeItemDTO> compositeItems = itemSkuDTO.getCompositeItemList();
					if(compositeItems != null){
						for(CompositeItemDTO compositeItem : compositeItems){
							compositeItem.setSkuId(skuId);//关联新写入的sku
							compositeItem.setItemId(itemId);//关联item
							compositeItem.setSupplierId(supplierId);
							//TODO
							//compositeItem.setBizCode(item.getBizCode());
							Long dbId = this.compositeItemManager.addCompositeItem(compositeItem);
						}
					}
				}else{
					List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
					//2.5将code_value值更新到item_sku表中
					itemSkuManager.updateItemSkuCodeValue(skuId, supplierId, skuPropertyDTOList);
					// 3.添加SkuPropertyList
					List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
							.addSkuProperty(itemId,skuId, supplierId, skuPropertyDTOList);
					retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
					retItemSkuDTOList.add(retitemSkuDTO);
				}*/

					List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
					//2.5将code_value值更新到item_sku表中
					itemSkuManager.updateItemSkuCodeValue(skuId, supplierId, skuPropertyDTOList);
					// 3.添加SkuPropertyList
					List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
							.addSkuProperty(itemId,skuId, supplierId, skuPropertyDTOList);
					retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
					retItemSkuDTOList.add(retitemSkuDTO);
				}else{//需要更新的sku
					itemSkuDTO.setItemId(itemId);
					itemSkuDTO.setSellerId(supplierId);
					itemSkuManager.updateItemSku(itemSkuDTO);

					Long skuId = itemSkuDTO.getId();

				/* 组合商品部分暂时不需要
				if(itemSkuDTO.getIsComposite() != null && itemSkuDTO.getIsComposite()){
					if(isFirst){
						isFirst = false;//只需要在第一次时候删除
						int compositeItemRow = this.compositeItemManager.deleteCompositeItemByItemId(itemId, supplierId);
					}
					List<CompositeItemDTO> compositeItems = itemSkuDTO.getCompositeItemList();
					if(compositeItems != null){
						for(CompositeItemDTO compositeItem : compositeItems){
							compositeItem.setSkuId(skuId);//关联新写入的sku
							compositeItem.setItemId(itemId);//关联item
							compositeItem.setSupplierId(supplierId);
							//TODO
							//compositeItem.setBizCode(item.getBizCode());
							Long dbId = this.compositeItemManager.addCompositeItem(compositeItem);
						}
					}
				}else{
					List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
					//2.5将code_value值更新到item_sku表中
					itemSkuManager.updateItemSkuCodeValue(skuId, supplierId, skuPropertyDTOList);
					// 3.添加SkuPropertyList
					List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
							.addSkuProperty(itemId,skuId, supplierId, skuPropertyDTOList);
					retitemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
					retItemSkuDTOList.add(retitemSkuDTO);
				}*/

					List<SkuPropertyDTO> skuPropertyDTOList = itemSkuDTO.getSkuPropertyDTOList();
					//2.5将code_value值更新到item_sku表中
					itemSkuManager.updateItemSkuCodeValue(skuId, supplierId, skuPropertyDTOList);
					// 3.添加SkuPropertyList
					List<SkuPropertyDTO> retSkuPropertyDTOList = skuPropertyManager
							.addSkuProperty(itemId,skuId, supplierId, skuPropertyDTOList);
					itemSkuDTO.setSkuPropertyDTOList(retSkuPropertyDTOList);
					retItemSkuDTOList.add(itemSkuDTO);
				}
			}

			//处理sku删除逻辑
			List<ItemSkuDTO> deleteItemSkuList = new ArrayList<ItemSkuDTO>();
			for(ItemSkuDTO itemSkuDTO: itemOldSkuList){
				boolean needDelete = true;
				for(ItemSkuDTO updateItemSkuDTO: itemSkuDTOList){
					if(updateItemSkuDTO.getId()!=null
							&& updateItemSkuDTO.getId().longValue()==itemSkuDTO.getId().longValue()){
						needDelete = false;
						break;
					}
				}

				//TODO 待重构成批量删除
				if(needDelete){
					boolean deleteResult = itemSkuManager.deleteItemSku(itemSkuDTO.getId(), itemSkuDTO.getSellerId());
					if(deleteResult == false){
						//TODO error handle;
					}
				}
			}

			// 4.添加副图列表
			List<ItemImageDTO> itemImageDTOList = itemDTO.getItemImageDTOList();
			List<ItemImageDTO> retItemImageDTOList = itemImageManager
					.addItemImage(itemId, supplierId, itemImageDTOList);

			response = ResponseUtil.getSuccessResponse(true);
			return response;
			
		}else{
			try {
				Boolean isSuccessfullyUpdated = itemManager.updateItem(itemDTO);
				response = ResponseUtil.getSuccessResponse(isSuccessfullyUpdated);
				return response;
			} catch (ItemException e) {
				response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
				log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
				return response;
			}
		}
	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_ITEM.getActionName();
	}
}
