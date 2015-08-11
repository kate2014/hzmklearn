package com.mockuai.itemcenter.common.domain.dto;

import com.mockuai.itemcenter.common.domain.dto.LimitEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ItemDTO implements Serializable {
	@Override
	public String toString() {
		return "ItemDTO [id=" + id + ", itemName=" + itemName + ", sellerId="
				+ sellerId + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5099121530065857622L;
	
	private Long id;
	
	private String itemUid;

	private String itemName;// 商品名称

	private String itemBrief;

	private Long sellerId;// 供应商ID

	private Long score; //商品评分

	private Long itemBrandId; // 商品品牌ID

	private Integer itemType; // 商品类型：1代表实体商品，2代表虚拟商品

	private String iconUrl; // 商品主图URL

	private Long categoryId; // 商品所属类目ID

	private Long groupId; // 商品分组ID;

	private Long costPrice;//成本价

	private Long marketPrice;// 市场价

	private Long promotionPrice;// 促销价

	private Long wirelessPrice;// 无线价

	private Date saleBegin;// 售卖开始时间

	private Date saleEnd;// 售卖结束时间

	private Integer saleMinNum;// 最小售卖数

	private Integer saleMaxNum;// 最大售卖数

	private Integer itemStatus;// 商品状态

	private Integer deleteMark;

	private Date gmtCreated;

	private Date gmtModified;

	private String itemDesc;

	private List<ItemSkuDTO> itemSkuDTOList;

	private List<ItemImageDTO> itemImageDTOList;
	
	private List<ItemPropertyDTO> itemPropertyList;//普通属性的列表
	private List<SkuPropertyDTO> skuPropertyList;
	
	private String categoryName;//类目的中文名称
	
	private String brandName;//品牌名称
	
	private String createTime; //特定格式的时间
	
	private String statusName;//状态名称
	
	private Long cornerIconId;//角标score
	
	private String bizCode;

	private SellerBrandDTO itemBrandDTO;
	
	private Integer deliveryType;//发货方式

	private List<CompositeItemDTO> compositeItem;//组合商品的列表

	private List<LimitEntity> buyLimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemUid() {
		return itemUid;
	}

	public void setItemUid(String itemUid) {
		this.itemUid = itemUid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemBrief() {
		return itemBrief;
	}

	public void setItemBrief(String itemBrief) {
		this.itemBrief = itemBrief;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getItemBrandId() {
		return itemBrandId;
	}

	public void setItemBrandId(Long itemBrandId) {
		this.itemBrandId = itemBrandId;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Long costPrice) {
		this.costPrice = costPrice;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Long getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Long promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Long getWirelessPrice() {
		return wirelessPrice;
	}

	public void setWirelessPrice(Long wirelessPrice) {
		this.wirelessPrice = wirelessPrice;
	}

	public Date getSaleBegin() {
		return saleBegin;
	}

	public void setSaleBegin(Date saleBegin) {
		this.saleBegin = saleBegin;
	}

	public Date getSaleEnd() {
		return saleEnd;
	}

	public void setSaleEnd(Date saleEnd) {
		this.saleEnd = saleEnd;
	}

	public Integer getSaleMinNum() {
		return saleMinNum;
	}

	public void setSaleMinNum(Integer saleMinNum) {
		this.saleMinNum = saleMinNum;
	}

	public Integer getSaleMaxNum() {
		return saleMaxNum;
	}

	public void setSaleMaxNum(Integer saleMaxNum) {
		this.saleMaxNum = saleMaxNum;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public List<ItemSkuDTO> getItemSkuDTOList() {
		return itemSkuDTOList;
	}

	public void setItemSkuDTOList(List<ItemSkuDTO> itemSkuDTOList) {
		this.itemSkuDTOList = itemSkuDTOList;
	}

	public List<ItemImageDTO> getItemImageDTOList() {
		return itemImageDTOList;
	}

	public void setItemImageDTOList(List<ItemImageDTO> itemImageDTOList) {
		this.itemImageDTOList = itemImageDTOList;
	}

	public List<ItemPropertyDTO> getItemPropertyList() {
		return itemPropertyList;
	}

	public void setItemPropertyList(List<ItemPropertyDTO> itemPropertyList) {
		this.itemPropertyList = itemPropertyList;
	}

	public List<SkuPropertyDTO> getSkuPropertyList() {
		return skuPropertyList;
	}

	public void setSkuPropertyList(List<SkuPropertyDTO> skuPropertyList) {
		this.skuPropertyList = skuPropertyList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getCornerIconId() {
		return cornerIconId;
	}

	public void setCornerIconId(Long cornerIconId) {
		this.cornerIconId = cornerIconId;
	}

	public SellerBrandDTO getItemBrandDTO() {
		return itemBrandDTO;
	}

	public void setItemBrandDTO(SellerBrandDTO itemBrandDTO) {
		this.itemBrandDTO = itemBrandDTO;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public List<CompositeItemDTO> getCompositeItem() {
		return compositeItem;
	}

	public void setCompositeItem(List<CompositeItemDTO> compositeItem) {
		this.compositeItem = compositeItem;
	}

	public List<LimitEntity> getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(List<LimitEntity> buyLimit) {
		this.buyLimit = buyLimit;
	}

}