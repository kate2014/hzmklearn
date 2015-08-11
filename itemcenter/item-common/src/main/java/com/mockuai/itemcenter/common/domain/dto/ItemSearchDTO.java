package com.mockuai.itemcenter.common.domain.dto;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class ItemSearchDTO implements Serializable{
    @Field("item_uid")
    private String itemUid;
    @Field("biz_code")
    private String bizCode;
    @Field("item_name")
    private String itemName;
    @Field("seller_id")
    private Long sellerId;
    @Field("category_id")
    private Long categoryId;
    @Field("brand_id")
    private Long brandId;
    @Field("icon_url")
    private String iconUrl;
    @Field("market_price")
    private Long marketPrice;
    @Field("promotion_price")
    private Long promotionPrice;
    @Field("wireless_price")
    private Long wirelessPrice;
    @Field("sale_begin")
    private Date saleBegin;
    // 商品类目名称条形码
    @Field("category_name")
    private String categoryName;
    @Field("bar_code")
    private String barCode;
    /**
     * 货源地
     */
    @Field("supply_base")
    private String supplyBase;

    /**
     * 角标id
     */
    @Field("corner_icon_id")
    private Long cornerIconId;

    /**
     * 角标url，搜索引擎中不存放该字段，在查询时获取商品列表时，再填充
     */
    private String cornerIconUrl;

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSupplyBase() {
        return supplyBase;
    }

    public void setSupplyBase(String supplyBase) {
        this.supplyBase = supplyBase;
    }

    public Long getCornerIconId() {
        return cornerIconId;
    }

    public void setCornerIconId(Long cornerIconId) {
        this.cornerIconId = cornerIconId;
    }

    public String getCornerIconUrl() {
        return cornerIconUrl;
    }

    public void setCornerIconUrl(String cornerIconUrl) {
        this.cornerIconUrl = cornerIconUrl;
    }
}
