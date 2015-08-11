package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

/**
 * 商品品牌DTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */

public class ItemBrandDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5343469723935399479L;

	private Long id; // 主键

	private String cname;// 品牌中文名

	private String ename;// 品牌英文名

	private Integer brandStatus; // 品牌状态

	private String logoUrl; // logo的URL

	private String country; // 品牌所属国家

	private String countryIcon; // 国家图片地址

	private String buildTime; // 品牌创建时间

	private String initiator; // 创始人

	private String background; // 品牌背景

	private String headAddress; // 总部地址

	private Integer sort; // 排序字段

	private String descs; // 品牌描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname == null ? null : cname.trim();
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename == null ? null : ename.trim();
	}

	public Integer getBrandStatus() {
		return brandStatus;
	}

	public void setBrandStatus(Integer brandStatus) {
		this.brandStatus = brandStatus;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl == null ? null : logoUrl.trim();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public String getCountryIcon() {
		return countryIcon;
	}

	public void setCountryIcon(String countryIcon) {
		this.countryIcon = countryIcon == null ? null : countryIcon.trim();
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime == null ? null : buildTime.trim();
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator == null ? null : initiator.trim();
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background == null ? null : background.trim();
	}

	public String getHeadAddress() {
		return headAddress;
	}

	public void setHeadAddress(String headAddress) {
		this.headAddress = headAddress == null ? null : headAddress.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs == null ? null : descs.trim();
	}

	@Override
	public String toString() {
		return "ItemBrandDTO [id=" + id + ", cname=" + cname + ", ename=" + ename + "]";
	}

}
