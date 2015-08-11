package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

public class CompositeItemDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1981971233706332664L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getSubSkuId() {
		return subSkuId;
	}

	public void setSubSkuId(Long subSkuId) {
		this.subSkuId = subSkuId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	private String id;
	
	private Long itemId;
	
	private Long skuId;//
	
	private Long subSkuId;//该组合商品关联的具体的规格商品id
	
	private Integer num;//数量
	
	private Long supplierId;//供应商id
	
	private String bizCode;//
	
}
