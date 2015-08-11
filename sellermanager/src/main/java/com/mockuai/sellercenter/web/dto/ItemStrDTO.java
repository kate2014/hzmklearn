package com.mockuai.sellercenter.web.dto;

import java.util.List;

public class ItemStrDTO {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getUserMinBought() {
		return userMinBought;
	}

	public void setUserMinBought(String userMinBought) {
		this.userMinBought = userMinBought;
	}

	public String getUserMaxBought() {
		return userMaxBought;
	}

	public void setUserMaxBought(String userMaxBought) {
		this.userMaxBought = userMaxBought;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GalleryStrDTO> getGallery() {
		return gallery;
	}

	public void setGallery(List<GalleryStrDTO> gallery) {
		this.gallery = gallery;
	}

	public List<SkuPropStrDTO> getSkuProps() {
		return skuProps;
	}

	public void setSkuProps(List<SkuPropStrDTO> skuProps) {
		this.skuProps = skuProps;
	}

	public List<SkuStrDTO> getSkus() {
		return skus;
	}

	public void setSkus(List<SkuStrDTO> skus) {
		this.skus = skus;
	}
	
	public List<PropStrDTO> getProps() {
		return props;
	}

	public void setProps(List<PropStrDTO> props) {
		this.props = props;
	}

	private String name;
	
	private String subName;
	
	private String cateId;
	
	private String brief;
	
	private String description;
	
	private String brandId;
	
	private String userMinBought;
	
	private String userMaxBought;
	
	private String id;
	
	private List<GalleryStrDTO> gallery;
	
	private List<SkuPropStrDTO> skuProps;
	
	private List<SkuStrDTO> skus;
	
	private List<PropStrDTO> props;

	@Override
	public String toString() {
		return "ItemStrDTO [name=" + name + ", subName=" + subName
				+ ", cateId=" + cateId + ", brief=" + brief + ", description="
				+ description + ", brandId=" + brandId + ", userMinBought="
				+ userMinBought + ", userMaxBought=" + userMaxBought + ", id="
				+ id + ", gallery=" + gallery + ", skuProps=" + skuProps
				+ ", skus=" + skus + ", props=" + props + "]";
	}
	
}
