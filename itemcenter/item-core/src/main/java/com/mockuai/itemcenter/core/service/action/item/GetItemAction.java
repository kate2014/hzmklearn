package com.mockuai.itemcenter.core.service.action.item;

import java.util.*;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.LimitEntity;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查看商品Action
 * 
 * @author chen.huang
 *
 */

@Service
public class GetItemAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetItemAction.class);
	@Resource
	private ItemManager itemManager;

	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	private ItemImageManager itemImageManager;
	
	@Resource
	private SkuPropertyManager skuPropertyManager;
	
	@Resource
	private ItemPropertyManager itemPropertyManager;

	@Resource
	private SellerBrandManager sellerBrandManager;

	@Resource
	private ItemBuyLimitManager itemBuyLimitManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemDTO itemDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("id") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemID is missing");
		}
		if (request.getLong("supplierId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long itemId = request.getLong("id");// 商品品牌ID
		Long sellerId = request.getLong("supplierId");// 供应商ID

		// 如果是需要详细信息时候  需要找到普通属性和销售属性值 －－ updated by cwr  
		if(request.getParam("needDetail") !=null && ((Boolean)request.getParam("needDetail"))){
			try {
				itemDTO = itemManager.getItem(itemId, sellerId);
				ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
				itemSkuQTO.setItemId(itemId);
				itemSkuQTO.setSellerId(sellerId);
				// 获取ItemSku列表
				List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);
				
				// 根据itemId查找该商品下的所有的基本属性
				ItemPropertyQTO itemPropertyQTO = new ItemPropertyQTO();
				itemPropertyQTO.setItemId(itemId);
				itemPropertyQTO.setSellerId(sellerId);
				itemPropertyQTO.setNeedPaging(null); //不需要分页
				List<ItemPropertyDTO> itemPropertyList = this.itemPropertyManager.queryItemProperty(itemPropertyQTO);

				//TODO 根据appkey判断应用类型，并根据property的bizMark来控制是否返回数据（有些字段在面向买家的应用是不返回的）

				itemDTO.setItemPropertyList(itemPropertyList);
				
				// 根据itemId查找该商品下的所有的销售属性值
				//q
				SkuPropertyQTO skuPropertyQTO =new SkuPropertyQTO();
				skuPropertyQTO.setSellerId(sellerId);
				skuPropertyQTO.setItemId(itemId);
				skuPropertyQTO.setNeedPaging(null);//不需要分页
				List<SkuPropertyDTO> skuPropertyList = this.skuPropertyManager.querySkuProperty(skuPropertyQTO);

				// 根据item_id查询所有的sku属性时候 有重复  比如：sku1 和 sku2 都有尺码L这个属性 结果会有重复 需要去除重复
				//去重操作 本段代码修改移到item-mop层修改 不然会导致商家中心sku部分出问题
				
//				List<SkuPropertyDTO> returnPropertyList = new ArrayList<SkuPropertyDTO>();
//				Set<Long> valueIdList = new HashSet<Long>();
				
//				if(skuPropertyList !=null){
//					for(SkuPropertyDTO item : skuPropertyList){
//						if(!valueIdList.contains(item.getVid())){
//							valueIdList.add(item.getVid());
//							returnPropertyList.add(item);
//						}
//					}
//				}
				itemDTO.setSkuPropertyList(skuPropertyList);
				
				// 获取副图列表
				ItemImageQTO itemImageQTO = new ItemImageQTO();
				itemImageQTO.setItemId(itemId);
				itemImageQTO.setSellerId(sellerId);
				List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

				//过滤商品图片，将商品副图和SKU图片分开，这里只提取商品副图，过滤掉商品SKU图片
//				List<ItemImageDTO> itemExtraImageList = getItemExtraImageList(itemImageDTOList);

				//往商品SKU中填充商品sku图片
				fillItemSkuImage(itemImageDTOList, itemSkuDTOList);

				itemDTO.setItemImageDTOList(itemImageDTOList);
				itemDTO.setItemSkuDTOList(itemSkuDTOList);
				//TODO 商品价格填充逻辑重构
				if(itemSkuDTOList!=null && itemSkuDTOList.isEmpty()==false){
					ItemSkuDTO itemSkuDTO = itemSkuDTOList.get(0);
					itemDTO.setMarketPrice(itemSkuDTO.getMarketPrice());
					itemDTO.setPromotionPrice(itemSkuDTO.getPromotionPrice());
					itemDTO.setWirelessPrice(itemSkuDTO.getWirelessPrice());
				}
				
			} catch (ItemException e) {
				response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
				log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
				return response;
			}
		}else{ //不需要查找普通属性和销售属性值的时候
			try {
				itemDTO = itemManager.getItem(itemId, sellerId);
				ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
				itemSkuQTO.setItemId(itemId);
				itemSkuQTO.setSellerId(sellerId);
				// 获取ItemSku列表
				List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);
				// 获取副图列表
				ItemImageQTO itemImageQTO = new ItemImageQTO();
				itemImageQTO.setItemId(itemId);
				itemImageQTO.setSellerId(sellerId);
				List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

				//过滤商品图片，将商品副图和SKU图片分开，这里只提取商品副图，过滤掉商品SKU图片
//				List<ItemImageDTO> itemExtraImageList = getItemExtraImageList(itemImageDTOList);

				//往商品SKU中填充商品sku图片
				fillItemSkuImage(itemImageDTOList, itemSkuDTOList);

				itemDTO.setItemImageDTOList(itemImageDTOList);
				itemDTO.setItemSkuDTOList(itemSkuDTOList);
			} catch (ItemException e) {
				response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
				log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
				return response;
			}
		}

		//获取商品品牌信息
		Long itemBrandId = itemDTO.getItemBrandId();
		if(itemBrandId != null){

			SellerBrandDTO sellerBrand = sellerBrandManager.getSellerBrand(itemBrandId, sellerId);
			// fixbug.
			if(sellerBrand == null) {
				ItemException e = ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "brand not exists for the item.");
				response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
				log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
				return response;
			}
			itemDTO.setItemBrandDTO(sellerBrand);
			//TODO 这里暂时返回兼容itemProperty中的品牌属性，后续需要考虑在商家中心来插入，平台不感知该属性 add by zengzhangqiang on 2015-06-11
			if(itemDTO.getItemPropertyList() == null){
				itemDTO.setItemPropertyList(new ArrayList<ItemPropertyDTO>());
			}
			ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
			itemPropertyDTO.setCode("IC_APP_P_ITEM_000001");
			itemPropertyDTO.setName("品牌");
			itemPropertyDTO.setValue(sellerBrand.getBrandName());
			itemPropertyDTO.setValueType(1);
			itemDTO.getItemPropertyList().add(itemPropertyDTO);
		}
		// 判断商品限购;
		List<ItemBuyLimitDTO> itemBuyLimitDTOs = itemBuyLimitManager.queryItemBuyLimitRecord(sellerId, itemId);
		if(itemBuyLimitDTOs != null) {
			List<LimitEntity> limitEntities = new ArrayList<LimitEntity>();
			for(ItemBuyLimitDTO itemBuyLimitDTO: itemBuyLimitDTOs) {
				LimitEntity limitEntity = new LimitEntity();
				limitEntity.setLimitCount(itemBuyLimitDTO.getBuyCount());
				limitEntity.setBeginTime(itemBuyLimitDTO.getBeginTime());
				limitEntity.setEndTime(itemBuyLimitDTO.getEndTime());
				limitEntities.add(limitEntity);
			}
			itemDTO.setBuyLimit(limitEntities);
		}
		//TODO 判断商品收藏状态
		
		response = ResponseUtil.getSuccessResponse(itemDTO);
		return response;
		
		
	}

	private List<ItemImageDTO> getItemExtraImageList(List<ItemImageDTO> itemImageDTOs){
		if(itemImageDTOs==null || itemImageDTOs.isEmpty()){
			return Collections.EMPTY_LIST;
		}

		List<ItemImageDTO> itemExtraImageList = new ArrayList<ItemImageDTO>();
		Map<Long, ItemImageDTO> skuImageMap = new HashMap<Long, ItemImageDTO>();
		for(ItemImageDTO itemImageDTO: itemImageDTOs){
			if(itemImageDTO.getPropertyValueId()==null || itemImageDTO.getPropertyValueId().longValue()<=0){
				//商品副图
				itemExtraImageList.add(itemImageDTO);
			}
		}

		return itemExtraImageList;
	}

	private void fillItemSkuImage(List<ItemImageDTO> itemImageDTOs,
													 List<ItemSkuDTO> itemSkuDTOList){
		if(itemImageDTOs==null || itemSkuDTOList==null){
			return;
		}
		Map<Long, ItemImageDTO> skuImageMap = new HashMap<Long, ItemImageDTO>();
		for(ItemImageDTO itemImageDTO: itemImageDTOs){
			if(itemImageDTO.getPropertyValueId()!=null && itemImageDTO.getPropertyValueId().longValue()>0){
				//商品SKU图片
				skuImageMap.put(itemImageDTO.getPropertyValueId(), itemImageDTO);
			}
		}

		//FIXME 商品sku图片与商品sku是根据商品sku属性上的propertyValueId进行关联的
		for(ItemSkuDTO itemSkuDTO: itemSkuDTOList){
			if(itemSkuDTO.getSkuPropertyDTOList() != null){
				for(SkuPropertyDTO skuPropertyDTO: itemSkuDTO.getSkuPropertyDTOList()){
					if(skuPropertyDTO.getPropertyValueId()!=null
							&& skuImageMap.containsKey(skuPropertyDTO.getPropertyValueId())){
						itemSkuDTO.setImageUrl(skuImageMap.get(skuPropertyDTO.getPropertyValueId()).getImageUrl());
					}
				}
			}
		}

		return;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ITEM.getActionName();
	}
}
