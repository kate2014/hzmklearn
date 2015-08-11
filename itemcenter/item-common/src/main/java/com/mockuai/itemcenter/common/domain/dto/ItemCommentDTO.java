package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品评论DTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */
public class ItemCommentDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4051055219641294698L;

	private Long id; // 主键

	private Long userId; // 用户ID

	private String userName;// 用户昵称

	private Long orderId;// 订单ID

	private Long itemId; // 商品ID

	private Long skuId;// 前端只能传skuId,不改表结构添加评论的适合查询出skuCode;

	private String skuCode; // 订单商品sku

	private Long sellerId;// 卖家ID

	private String title;// 评论标题

	private String content;// 评论内容

	private Integer score; // 用户打分

	private Date commentTime;//评论时间

	private Date replyTime;// 回复时间

	private Long replyUserId; // 回复者用户ID

	private String replyContent;// 回复内容
	
	private String bizCode;

	/**
	 * 评论图片列表
	 */
	private List<CommentImageDTO> commentImageDTOs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public Long getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public List<CommentImageDTO> getCommentImageDTOs() {
		return commentImageDTOs;
	}

	public void setCommentImageDTOs(List<CommentImageDTO> commentImageDTOs) {
		this.commentImageDTOs = commentImageDTOs;
	}
}