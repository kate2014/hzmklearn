package com.mockuai.itemcenter.mop.api.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.LimitEntity;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.mop.api.domain.*;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
* Created by zengzhangqiang on 4/26/15.
*/
public class MopApiUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static List<MopCategoryDTO> genMopCategoryDTOList(List<ItemCategoryDTO> itemCategoryDTOs){
        List<MopCategoryDTO> mopCategoryDTOs = new ArrayList<MopCategoryDTO>();
        for(ItemCategoryDTO itemCategoryDTO: itemCategoryDTOs){
            mopCategoryDTOs.add(genMopCategoryDTO(itemCategoryDTO));
        }

        return mopCategoryDTOs;
    }

    public static MopCategoryDTO genMopCategoryDTO(ItemCategoryDTO itemCategoryDTO){
        MopCategoryDTO mopCategoryDTO = new MopCategoryDTO();
        mopCategoryDTO.setId(itemCategoryDTO.getId());
        mopCategoryDTO.setCateName(itemCategoryDTO.getCateName());
        mopCategoryDTO.setCateLevel(itemCategoryDTO.getCateLevel());
        mopCategoryDTO.setParentId(itemCategoryDTO.getParentId());
        mopCategoryDTO.setTopId(itemCategoryDTO.getTopId());
        mopCategoryDTO.setSort(itemCategoryDTO.getSort());
        return mopCategoryDTO;
    }

    public static CartItemUidDTO parseCartItemUid(String cartItemUid){
        CartItemUidDTO cartItemUidDTO = new CartItemUidDTO();
        String[] strs = cartItemUid.split("_");
        if(strs.length != 2){
            //TODO error handle
        }

        long userId = Long.parseLong(strs[0]);
        long cartItemId = Long.parseLong(strs[1]);
        cartItemUidDTO.setUserId(userId);
        cartItemUidDTO.setCartItemId(cartItemId);

        return cartItemUidDTO;
    }

    public static SkuUidDTO parseSkuUid(String skuUid){
        SkuUidDTO skuUidDTO = new SkuUidDTO();
        String[] strs = skuUid.split("_");
        if(strs.length != 2){
            //TODO error handle
        }

        long sellerId = Long.parseLong(strs[0]);
        long skuId = Long.parseLong(strs[1]);
        skuUidDTO.setSellerId(sellerId);
        skuUidDTO.setSkuId(skuId);

        return skuUidDTO;
    }

    public static String genItemUid(long itemId, long sellerId){
        return ""+sellerId+"_"+itemId;
    }

    public static String genSkuUid(long itemSkuId, long sellerId){
        return ""+sellerId+"_"+itemSkuId;
    }

    public static String genItemImageUid(long itemImageId, long sellerId){
        return ""+sellerId+"_"+itemImageId;
    }

    public static String genOrderUid(long orderId, long sellerId, long buyerId){
        return ""+sellerId+"_"+buyerId+"_"+orderId;
    }

    public static ItemUidDTO parseItemUid(String itemUid){
        if(StringUtils.isBlank(itemUid)){
            return null;
        }
        try{
            ItemUidDTO itemUidDTO = new ItemUidDTO();
            String[] strs = itemUid.split("_");
            if(strs.length != 2){
                return null;
            }

            long sellerId = Long.parseLong(strs[0]);
            long itemId = Long.parseLong(strs[1]);
            itemUidDTO.setSellerId(sellerId);
            itemUidDTO.setItemId(itemId);

            return itemUidDTO;
        }catch(Exception e){
            //TODO error handle
        }
        return null;
    }

    public static OrderUidDTO parseOrderUid(String orderUid){
        OrderUidDTO orderUidDTO = new OrderUidDTO();
        String[] strs = orderUid.split("_");
        if(strs.length != 3){
            //TODO error handle
        }

        long sellerId = Long.parseLong(strs[0]);
        long buyerId = Long.parseLong(strs[1]);
        long orderId = Long.parseLong(strs[2]);

        orderUidDTO.setSellerId(sellerId);
        orderUidDTO.setUserId(buyerId);
        orderUidDTO.setOrderId(orderId);
        return orderUidDTO;
    }

    public static String genCommentUid(long commentId, long sellerId, long userId){
        return ""+sellerId+"_"+userId+"_"+commentId;
    }

    public static String genSkuPropertyUid(Long sellerId, Long skuPropertyId){
        if(skuPropertyId == null){
            return null;
        }
        return ""+sellerId+"_"+skuPropertyId;
    }


    public static <T> MopResponse<T> transferResp(Response<T> itemResp){
        //TODO refactor the code
        if(ResponseCode.SUCCESS.getCode() != itemResp.getCode()){
            //错误码转换
            return new MopResponse<T>(itemResp.getCode(), itemResp.getMessage());
        }else{
            Object data = null;
            if(itemResp.getModule()==null || itemResp.getModule() instanceof Boolean){
                data = null;
            }else if(itemResp.getModule() instanceof ItemDTO){
                data = genMopItem((ItemDTO)itemResp.getModule());
            }else if(itemResp.getModule() instanceof List){
                //TODO list中的数据转换
                data = itemResp.getModule();
            }
            return new MopResponse(data);
        }
    }

    public static boolean isSuccess(Response itemResp){
        return ResponseCode.SUCCESS.getCode() == itemResp.getCode();
    }

    public static <T> MopResponse getResponseByData(T data){
        return new MopResponse(data);
    }

    public static MopResponse getResponseByCode(MopRespCode mopRespCode){
        return new MopResponse(mopRespCode);
    }



    public static List<MopItemDTO> genMopItemListForSearchDTO(List<ItemSearchDTO> itemSearchDTOs){
        List<MopItemDTO> mopItemDTOs = new ArrayList<MopItemDTO>();
        for(ItemSearchDTO itemSearchDTO: itemSearchDTOs){
            mopItemDTOs.add(genMopItem(itemSearchDTO));
        }
        return mopItemDTOs;
    }

    public static List<MopItemDTO> genMopItemListForGroupDTO(List<GroupItemDTO> groupItemDTOs){
        List<MopItemDTO> mopItemDTOs = new ArrayList<MopItemDTO>();
        for(GroupItemDTO groupItemDTO: groupItemDTOs){
            mopItemDTOs.add(genMopItem(groupItemDTO));
        }
        return mopItemDTOs;
    }

    public static List<MopItemDTO> genMopItemList(List<ItemDTO> itemDTOs){
        List<MopItemDTO> mopItemDTOs = new ArrayList<MopItemDTO>();
        for(ItemDTO itemDTO: itemDTOs){
            mopItemDTOs.add(genMopItem(itemDTO));
        }
        return mopItemDTOs;
    }

    public static MopItemDTO genMopItem(ItemSearchDTO itemSearchDTO){
        MopItemDTO mopItemDTO = new MopItemDTO();
        mopItemDTO.setItemBrand(genMopItemBrand(itemSearchDTO));
        mopItemDTO.setItemName(itemSearchDTO.getItemName());
        mopItemDTO.setItemUid(itemSearchDTO.getItemUid());
        mopItemDTO.setIconUrl(itemSearchDTO.getIconUrl());
        mopItemDTO.setCategoryId(itemSearchDTO.getCategoryId());
        mopItemDTO.setSellerId(itemSearchDTO.getSellerId());
        mopItemDTO.setMarketPrice(itemSearchDTO.getMarketPrice());
        mopItemDTO.setPromotionPrice(itemSearchDTO.getPromotionPrice());
        mopItemDTO.setWirelessPrice(itemSearchDTO.getWirelessPrice());
        mopItemDTO.setCategoryName(itemSearchDTO.getCategoryName());
        mopItemDTO.setCornerIconUrl(itemSearchDTO.getCornerIconUrl());
        mopItemDTO.setSupplyBase(itemSearchDTO.getSupplyBase());
        return mopItemDTO;
    }

    public static MopItemDTO genMopItem(GroupItemDTO groupItemDTO){
        MopItemDTO mopItemDTO = new MopItemDTO();

        MopItemBrandDTO mopItemBrandDTO = new MopItemBrandDTO();
        mopItemBrandDTO.setId(groupItemDTO.getBrandId());
        mopItemDTO.setItemBrand(mopItemBrandDTO);

        mopItemDTO.setItemName(groupItemDTO.getItemName());
        mopItemDTO.setItemUid(groupItemDTO.getItemUid());
        mopItemDTO.setIconUrl(groupItemDTO.getIconUrl());
        mopItemDTO.setCategoryId(groupItemDTO.getCategoryId());
        mopItemDTO.setSellerId(groupItemDTO.getSellerId());
        mopItemDTO.setMarketPrice(groupItemDTO.getMarketPrice());
        mopItemDTO.setPromotionPrice(groupItemDTO.getPromotionPrice());
        mopItemDTO.setWirelessPrice(groupItemDTO.getWirelessPrice());
        mopItemDTO.setCategoryName(groupItemDTO.getCategoryName());
        return mopItemDTO;
    }

    public static MopItemBrandDTO genMopItemBrand(SellerBrandDTO sellerBrandDTO){
        if(sellerBrandDTO == null){
            return null;
        }
        MopItemBrandDTO mopItemBrandDTO = new MopItemBrandDTO();
        mopItemBrandDTO.setId(sellerBrandDTO.getId());
        mopItemBrandDTO.setLogoUrl(sellerBrandDTO.getLogo());
        mopItemBrandDTO.setName(sellerBrandDTO.getBrandName());
        mopItemBrandDTO.setEnName(sellerBrandDTO.getBrandEnName());
        return mopItemBrandDTO;
    }

    public static MopItemBrandDTO genMopItemBrand(ItemSearchDTO itemSearchDTO){
        MopItemBrandDTO mopItemBrandDTO = new MopItemBrandDTO();
        mopItemBrandDTO.setId(itemSearchDTO.getBrandId());
        return mopItemBrandDTO;
    }

    public static MopItemDTO genMopItem(ItemDTO itemDTO){
        MopItemDTO mopItemDTO = new MopItemDTO();
        mopItemDTO.setItemUid(MopApiUtil.genItemUid(itemDTO.getId(), itemDTO.getSellerId()));
        mopItemDTO.setItemName(itemDTO.getItemName());
        mopItemDTO.setSellerId(itemDTO.getSellerId());
        mopItemDTO.setCategoryId(itemDTO.getCategoryId().longValue());
        mopItemDTO.setItemType(itemDTO.getItemType());
        mopItemDTO.setIconUrl(itemDTO.getIconUrl());
        //TODO descUrl值设置
        mopItemDTO.setItemDesc(itemDTO.getItemDesc());
        mopItemDTO.setMarketPrice(itemDTO.getMarketPrice());
        mopItemDTO.setPromotionPrice(itemDTO.getPromotionPrice());
        mopItemDTO.setWirelessPrice(itemDTO.getWirelessPrice());
        if(itemDTO.getSaleBegin() != null){
            mopItemDTO.setSaleBegin(dateFormat.format(itemDTO.getSaleBegin()));
            // 当前之间转化成秒;
            long nowTime = System.currentTimeMillis() / 1000;
            // 起售时间转化成秒;
            long saleBeginTime = itemDTO.getSaleBegin().getTime() / 1000;
            if(saleBeginTime > nowTime) {
                mopItemDTO.setSalesRemainTime(0L);
            } else {
                mopItemDTO.setSalesRemainTime(saleBeginTime - nowTime);
            }
        }

        if(itemDTO.getSaleEnd() != null){
            mopItemDTO.setSaleEnd(dateFormat.format(itemDTO.getSaleEnd()));
        }
        mopItemDTO.setDeliveryType(itemDTO.getDeliveryType());
        mopItemDTO.setItemBrand(genMopItemBrand(itemDTO.getItemBrandDTO()));
        mopItemDTO.setItemSkuList(genMopItemSkuList(itemDTO.getItemSkuDTOList()));
        mopItemDTO.setItemImageList(genMopItemImageList(itemDTO.getItemImageDTOList()));

        // 限购;
        if(itemDTO.getBuyLimit() == null || itemDTO.getBuyLimit().size() == 0) {
            mopItemDTO.setLimitBuyCount(0);
        }
        long currentMillis = System.currentTimeMillis();

        if(itemDTO.getBuyLimit() != null) {
            for(LimitEntity limitEntity: itemDTO.getBuyLimit() ) {
                long beginTime = limitEntity.getBeginTime().getTime();
                long endTime = limitEntity.getEndTime().getTime();
                if(currentMillis >= beginTime && beginTime <= endTime) {
                    mopItemDTO.setLimitBuyCount(limitEntity.getLimitCount());
                }
            }
        }

		// 根据item_id查询所有的sku属性时候 有重复  比如：sku1 和 sku2 都有红色这个属性 结果红色会出现2次 导致app端会出现重复显示
        List<SkuPropertyDTO> skuPropertyList = itemDTO.getSkuPropertyList();
        List<SkuPropertyDTO> returnPropertyList = new ArrayList<SkuPropertyDTO>();
		Set<Long> valueIdList = new HashSet<Long>();
		if(skuPropertyList !=null){
			for(SkuPropertyDTO item : skuPropertyList){
				if(!valueIdList.contains(item.getPropertyValueId())){
					valueIdList.add(item.getPropertyValueId());
					returnPropertyList.add(item);
				}
			}
		}
		
        mopItemDTO.setSkuPropertyList(genMopSkuPropertyList(returnPropertyList));
        mopItemDTO.setItemPropertyList(genMopItemPropertyList(itemDTO.getItemPropertyList()));

        //FIXME 兼容saleMinNum和saleMaxNum不存在的情况
        if(itemDTO.getSaleMinNum() != null){
            mopItemDTO.setSaleMinNum(itemDTO.getSaleMinNum().longValue());
        }else{
            mopItemDTO.setSaleMinNum(0L);
        }

        if(itemDTO.getSaleMaxNum() != null){
            mopItemDTO.setSaleMaxNum(itemDTO.getSaleMaxNum().longValue());
        }else{
            mopItemDTO.setSaleMaxNum(0L);
        }
        mopItemDTO.setStatus(itemDTO.getItemStatus());
        //TODO bizProperty值设置
        return mopItemDTO;
    }



    public static List<MopItemSkuDTO> genMopItemSkuList(List<ItemSkuDTO> itemSkuList){
        if(itemSkuList == null){
            return null;
        }

        List<MopItemSkuDTO> mopItemSkuDTOs = new ArrayList<MopItemSkuDTO>();
        for(ItemSkuDTO itemSkuDTO: itemSkuList){
            MopItemSkuDTO mopItemSkuDTO = genMopItemSku(itemSkuDTO);
            mopItemSkuDTOs.add(mopItemSkuDTO);
        }
        return mopItemSkuDTOs;
    }

    public static MopItemSkuDTO genMopItemSku(ItemSkuDTO itemSku){
        MopItemSkuDTO mopItemSkuDTO = new MopItemSkuDTO();
        mopItemSkuDTO.setSkuUid(genSkuUid(itemSku.getId(), itemSku.getSellerId()));
        mopItemSkuDTO.setSkuCode(itemSku.getSkuCode());
        mopItemSkuDTO.setItemUid(genItemUid(itemSku.getItemId(), itemSku.getSellerId()));
        mopItemSkuDTO.setBarCode(itemSku.getBarCode());
        mopItemSkuDTO.setCostPrice(itemSku.getCostPrice());
        //mopItemSkuDTO.setMaterialCode(itemSku.getMaterialCode());
        mopItemSkuDTO.setImageUrl(itemSku.getImageUrl());
        mopItemSkuDTO.setMarketPrice(itemSku.getMarketPrice());
        mopItemSkuDTO.setPromotionPrice(itemSku.getPromotionPrice());
        mopItemSkuDTO.setWirelessPrice(itemSku.getWirelessPrice());
        mopItemSkuDTO.setStockNum(itemSku.getStockNum());
        mopItemSkuDTO.setSoldNum(itemSku.getSoldNum());
        mopItemSkuDTO.setSkuPropertyList(genMopSkuPropertyList(itemSku.getSkuPropertyDTOList()));
        return mopItemSkuDTO;
    }

    public static List<MopSkuPropertyDTO> genMopSkuPropertyList(List<SkuPropertyDTO> skuPropertyDTOList){
        if(skuPropertyDTOList == null){
            return null;
        }

        List<MopSkuPropertyDTO> mopSkuPropertyDTOs = new ArrayList<MopSkuPropertyDTO>();
        for(SkuPropertyDTO skuPropertyDTO: skuPropertyDTOList){
            MopSkuPropertyDTO mopSkuPropertyDTO = genMopSkuProperty(skuPropertyDTO);
            mopSkuPropertyDTOs.add(mopSkuPropertyDTO);
        }

        return mopSkuPropertyDTOs;
    }

    public static MopSkuPropertyDTO genMopSkuProperty(SkuPropertyDTO skuProperty){
        MopSkuPropertyDTO mopSkuPropertyDTO = new MopSkuPropertyDTO();
        //TODO code值的设置
        mopSkuPropertyDTO.setName(skuProperty.getName());
        mopSkuPropertyDTO.setValue(skuProperty.getValue());
        mopSkuPropertyDTO.setValueType(skuProperty.getValueType());
        mopSkuPropertyDTO.setSort(skuProperty.getSort());
        mopSkuPropertyDTO.setSkuPropertyUid(genSkuPropertyUid(skuProperty.getSellerId(), skuProperty.getId()));

        return mopSkuPropertyDTO;
    }

    public static List<MopItemPropertyDTO> genMopItemPropertyList(List<ItemPropertyDTO> itemPropertyList){
        if(itemPropertyList == null){
            return null;
        }

        List<MopItemPropertyDTO> mopItemPropertyDTOs = new ArrayList<MopItemPropertyDTO>();
        for(ItemPropertyDTO itemPropertyDTO: itemPropertyList){
        	// 根据bizMark字段来判断是否需要需要显示给用户看   updated by cwr 
        	if(isItemPropertyShow2User(itemPropertyDTO.getBizMark())){
        		MopItemPropertyDTO mopItemPropertyDTO = genMopItemProperty(itemPropertyDTO);
                mopItemPropertyDTOs.add(mopItemPropertyDTO);
        	}
        }

        return mopItemPropertyDTOs;
    }

    public static MopItemPropertyDTO genMopItemProperty(ItemPropertyDTO itemProperty){
        MopItemPropertyDTO mopItemPropertyDTO = new MopItemPropertyDTO();
        //TODO code值的设置
        System.out.println("itemPropetyCode: " +itemProperty.getCode());
        mopItemPropertyDTO.setCode(itemProperty.getCode());
        mopItemPropertyDTO.setName(itemProperty.getName());
        mopItemPropertyDTO.setValue(itemProperty.getValue());
        mopItemPropertyDTO.setValueType(itemProperty.getValueType());
        mopItemPropertyDTO.setSort(itemProperty.getSort());
        return mopItemPropertyDTO;
    }

    public static List<MopItemImageDTO> genMopItemImageList(List<ItemImageDTO> itemImageList){
        if(itemImageList == null){
            return null;
        }

        List<MopItemImageDTO> mopItemImageList = new ArrayList<MopItemImageDTO>();
        for(ItemImageDTO itemImageDTO: itemImageList){
            MopItemImageDTO mopItemImageDTO = genMopItemImage(itemImageDTO);
            mopItemImageList.add(mopItemImageDTO);
        }

        return mopItemImageList;
    }

    public static MopItemImageDTO genMopItemImage(ItemImageDTO itemImage){
        MopItemImageDTO mopItemImageDTO = new MopItemImageDTO();
        mopItemImageDTO.setImageName(itemImage.getImageName());
        mopItemImageDTO.setImageType(itemImage.getImageType());
        mopItemImageDTO.setImageUrl(itemImage.getImageUrl());
        mopItemImageDTO.setItemUid(genItemUid(itemImage.getItemId(), itemImage.getSellerId()));
        mopItemImageDTO.setItemImageUid(genItemImageUid(itemImage.getId(), itemImage.getSellerId()));
        if(itemImage.getPropertyValueId()!=null || itemImage.getPropertyValueId().longValue()>0){
            mopItemImageDTO.setSkuPropertyUid(genSkuPropertyUid(itemImage.getSellerId(), itemImage.getPropertyValueId()));
        }

        return mopItemImageDTO;
    }

    public static List<MopItemCommentDTO> genMopItemCommentList(List<ItemCommentDTO> itemCommentDTOs){
        List<MopItemCommentDTO> mopItemCommentDTOs = new ArrayList<MopItemCommentDTO>();
        for(ItemCommentDTO itemCommentDTO: itemCommentDTOs){
            mopItemCommentDTOs.add(genMopItemComment(itemCommentDTO));
        }
        return mopItemCommentDTOs;
    }

    public static MopItemCommentDTO genMopItemComment(ItemCommentDTO itemCommentDTO){
        MopItemCommentDTO mopItemCommentDTO = new MopItemCommentDTO();
        mopItemCommentDTO.setItemUid(genItemUid(itemCommentDTO.getItemId(), itemCommentDTO.getSellerId()));
        mopItemCommentDTO.setOrderUid(genOrderUid(itemCommentDTO.getOrderId(),
                itemCommentDTO.getSellerId(), itemCommentDTO.getUserId()));
        mopItemCommentDTO.setCommentUid(genCommentUid(itemCommentDTO.getId(), itemCommentDTO.getSellerId(),
                itemCommentDTO.getUserId()));
        mopItemCommentDTO.setTitle(itemCommentDTO.getTitle());
        mopItemCommentDTO.setContent(itemCommentDTO.getContent());
        mopItemCommentDTO.setCommentTime(dateFormat.format(itemCommentDTO.getCommentTime()));
        mopItemCommentDTO.setScore(itemCommentDTO.getScore());
        mopItemCommentDTO.setUserId(itemCommentDTO.getUserId());
        mopItemCommentDTO.setUserName(itemCommentDTO.getUserName());
        //填充评论图片列表
        mopItemCommentDTO.setCommentImageList(genMopCommentImageDTOList(itemCommentDTO.getCommentImageDTOs()));
        // sku_code;
        mopItemCommentDTO.setSkuCode(itemCommentDTO.getSkuCode());
//        //填充评论图片列表
//        mopItemCommentDTO.setCommentImageList(genMopCommentImageDTOList(itemCommentDTO.getCommentImageDTOs()));
        return mopItemCommentDTO;
    }

    public static List<MopCommentImageDTO> genMopCommentImageDTOList(List<CommentImageDTO> commentImageDTOs){
        if(commentImageDTOs == null){
            return null;
        }

        List<MopCommentImageDTO> mopCommentImageDTOs = new CopyOnWriteArrayList<MopCommentImageDTO>();
        for(CommentImageDTO commentImageDTO: commentImageDTOs){
            mopCommentImageDTOs.add(genMopCommentImage(commentImageDTO));
        }

        return mopCommentImageDTOs;
    }

    public static MopCommentImageDTO genMopCommentImage(CommentImageDTO commentImageDTO){
        if(commentImageDTO == null){
            return null;
        }

        MopCommentImageDTO mopCommentImageDTO = new MopCommentImageDTO();
        mopCommentImageDTO.setImageUrl(commentImageDTO.getImageUrl());
        return mopCommentImageDTO;
    }

    public static List<ItemCommentDTO> genItemCommentList(List<MopItemCommentDTO> mopItemCommentDTOs){
        List<ItemCommentDTO> itemCommentDTOs = new ArrayList<ItemCommentDTO>();
        for(MopItemCommentDTO mopItemCommentDTO: mopItemCommentDTOs){
            itemCommentDTOs.add(genItemComment(mopItemCommentDTO));
        }
        return itemCommentDTOs;
    }

    public static ItemCommentDTO genItemComment(MopItemCommentDTO mopItemCommentDTO){

        ItemUidDTO itemUidDTO = parseItemUid(mopItemCommentDTO.getItemUid());
        OrderUidDTO orderUidDTO = parseOrderUid(mopItemCommentDTO.getOrderUid());
        SkuUidDTO skuUidDTO = parseSkuUid(mopItemCommentDTO.getSkuUid());

        ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
        itemCommentDTO.setItemId(itemUidDTO.getItemId());
        itemCommentDTO.setSellerId(itemUidDTO.getSellerId());
        itemCommentDTO.setUserName(mopItemCommentDTO.getUserName());
        itemCommentDTO.setUserId(orderUidDTO.getUserId());
        itemCommentDTO.setSkuId(skuUidDTO.getSkuId());
        itemCommentDTO.setOrderId(orderUidDTO.getOrderId());
        itemCommentDTO.setTitle(mopItemCommentDTO.getTitle());
        itemCommentDTO.setContent(mopItemCommentDTO.getContent());
        itemCommentDTO.setScore(mopItemCommentDTO.getScore());
        //添加商品评论图片列表
        itemCommentDTO.setCommentImageDTOs(genCommentImageList(mopItemCommentDTO.getCommentImageList()));
        return itemCommentDTO;
    }

    public static List<CommentImageDTO> genCommentImageList(List<MopCommentImageDTO> mopCommentImageDTOs){
        if(mopCommentImageDTOs == null){
            return null;
        }

        List<CommentImageDTO> commentImageDTOs = new CopyOnWriteArrayList<CommentImageDTO>();
        for(MopCommentImageDTO mopCommentImageDTO: mopCommentImageDTOs){
            CommentImageDTO commentImageDTO = genCommentImage(mopCommentImageDTO);
            commentImageDTOs.add(commentImageDTO);
        }

        return commentImageDTOs;
    }

    public static CommentImageDTO genCommentImage(MopCommentImageDTO mopCommentImageDTO){
        if(mopCommentImageDTO == null){
            return null;
        }

        CommentImageDTO commentImageDTO = new CommentImageDTO();
        commentImageDTO.setImageUrl(mopCommentImageDTO.getImageUrl());
        return commentImageDTO;
    }
    
    /**
     * 根据标记为来判断该字段是否是显示给用户
     * @param bizMark
     * @return
     */
    public static boolean isItemPropertyShow2User(Long bizMark){
    	if(bizMark == null){// 如果是该字段没有配置 保险起见不给买家查看
    		return false;
    	}
    	//1 表示只显示给卖家  2 表示该字段可以显示给买家
    	if(bizMark.longValue() == 2L){
    		return true;
    	}
    	return false;
    }
    
}
