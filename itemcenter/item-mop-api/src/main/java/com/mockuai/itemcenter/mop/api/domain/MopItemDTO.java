package com.mockuai.itemcenter.mop.api.domain;

import java.util.List;

/**
 * Created by zengzhangqiang on 4/24/15.
 */
public class MopItemDTO {
    private String itemUid;
    private String itemName;
    private Long sellerId;
    private Long categoryId;
    private Integer itemType;
    private String iconUrl;
    private String itemDesc;
    private Long marketPrice;
    private Long promotionPrice;
    private Long wirelessPrice;
    private Integer deliveryType;
    private List<PropertyDTO> bizPropertyList;
    private MopItemBrandDTO itemBrand;
    private List<MopItemImageDTO> itemImageList;
    private List<MopItemSkuDTO> itemSkuList;
    private List<MopSkuPropertyDTO> skuPropertyList;
    private List<MopItemPropertyDTO> itemPropertyList;
    private String saleBegin;
    private String saleEnd;
    private Long saleMinNum;
    private Long saleMaxNum;
    private Integer status;
    // 销售剩下的时间,秒
    private Long salesRemainTime;
    private String categoryName;
    /**
     * 角标url
     */
    private String cornerIconUrl;
    /**
     * 货源地
     */
    private String supplyBase;
    // 限购数量;
    private Integer limitBuyCount;

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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
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

    public List<MopItemImageDTO> getItemImageList() {
        return itemImageList;
    }

    public void setItemImageList(List<MopItemImageDTO> itemImageList) {
        this.itemImageList = itemImageList;
    }

    public List<MopItemSkuDTO> getItemSkuList() {
        return itemSkuList;
    }

    public void setItemSkuList(List<MopItemSkuDTO> itemSkuList) {
        this.itemSkuList = itemSkuList;
    }

    public List<MopSkuPropertyDTO> getSkuPropertyList() {
        return skuPropertyList;
    }

    public void setSkuPropertyList(List<MopSkuPropertyDTO> skuPropertyList) {
        this.skuPropertyList = skuPropertyList;
    }

    public String getSaleBegin() {
        return saleBegin;
    }

    public void setSaleBegin(String saleBegin) {
        this.saleBegin = saleBegin;
    }

    public String getSaleEnd() {
        return saleEnd;
    }

    public void setSaleEnd(String saleEnd) {
        this.saleEnd = saleEnd;
    }

    public MopItemBrandDTO getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(MopItemBrandDTO itemBrand) {
        this.itemBrand = itemBrand;
    }

    public List<MopItemPropertyDTO> getItemPropertyList() {
        return itemPropertyList;
    }

    public void setItemPropertyList(List<MopItemPropertyDTO> itemPropertyList) {
        this.itemPropertyList = itemPropertyList;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public List<PropertyDTO> getBizPropertyList() {
        return bizPropertyList;
    }

    public void setBizPropertyList(List<PropertyDTO> bizPropertyList) {
        this.bizPropertyList = bizPropertyList;
    }

    public Long getSaleMinNum() {
        return saleMinNum;
    }

    public void setSaleMinNum(Long saleMinNum) {
        this.saleMinNum = saleMinNum;
    }

    public Long getSaleMaxNum() {
        return saleMaxNum;
    }

    public void setSaleMaxNum(Long saleMaxNum) {
        this.saleMaxNum = saleMaxNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSalesRemainTime() {
        return salesRemainTime;
    }

    public void setSalesRemainTime(Long salesRemainTime) {
        this.salesRemainTime = salesRemainTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCornerIconUrl() {
        return cornerIconUrl;
    }

    public void setCornerIconUrl(String cornerIconUrl) {
        this.cornerIconUrl = cornerIconUrl;
    }

    public String getSupplyBase() {
        return supplyBase;
    }

    public void setSupplyBase(String supplyBase) {
        this.supplyBase = supplyBase;
    }

    public Integer getLimitBuyCount() {
        return limitBuyCount;
    }

    public void setLimitBuyCount(Integer limitBuyCount) {
        this.limitBuyCount = limitBuyCount;
    }
}
