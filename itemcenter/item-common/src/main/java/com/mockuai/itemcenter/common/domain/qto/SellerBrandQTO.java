package com.mockuai.itemcenter.common.domain.qto;

import java.io.Serializable;

import com.mockuai.itemcenter.common.page.PageInfo;

public class SellerBrandQTO extends PageInfo implements Serializable {
	
	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandEnName() {
		return brandEnName;
	}

	public void setBrandEnName(String brandEnName) {
		this.brandEnName = brandEnName;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}


	private Long id;
	
	private Integer status;
	
	private Long sellerId;
	
    private String brandName;
    
	private String brandEnName;
    
	private Long[] ids;
	
}
