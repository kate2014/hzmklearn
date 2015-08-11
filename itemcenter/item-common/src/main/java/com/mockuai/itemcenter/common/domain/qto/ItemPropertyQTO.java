package com.mockuai.itemcenter.common.domain.qto;

import java.io.Serializable;

import com.mockuai.itemcenter.common.page.PageInfo;

public class ItemPropertyQTO extends PageInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7776448986632934256L;


	private Long id;

	private Long itemId;// 商品ID

	private Long sellerId;// 供应商ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

}