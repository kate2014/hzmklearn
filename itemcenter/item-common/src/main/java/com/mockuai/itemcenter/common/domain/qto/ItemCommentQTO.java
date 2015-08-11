package com.mockuai.itemcenter.common.domain.qto;

import com.mockuai.itemcenter.common.page.PageInfo;

/**
 * 商品评论QTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */

public class ItemCommentQTO extends PageInfo {
	private Long id; // 商品评论主键ID

	private Long itemId; // 商品ID

	private Long sellerId; // 卖家ID

	private Long orderId; // 订单ID

	private Long score; //商品评分

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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

}
