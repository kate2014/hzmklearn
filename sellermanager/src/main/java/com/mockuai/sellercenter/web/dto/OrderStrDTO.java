package com.mockuai.sellercenter.web.dto;

import java.util.Date;
import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderInvoiceDTO;
import com.mockuai.tradecenter.common.domain.OrderPaymentDTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.common.domain.UserConsigneeDTO;

public class OrderStrDTO {


	private Long id;

	/**
	 * 应用标志码
	 */
	private String bizCode;

	/**
	 * 订单流水
	 */
	private String orderSn;

	/**
	 * 订单类型
	 */
	private Integer type;

	/**
	 * 买家ID
	 */
	private Long userId;

	/**
	 * 卖家ID
	 */
	private Long sellerId;

	/**
	 * 商品总价
	 */
	private Long totalPrice;

	/**
	 * 运费
	 */
	private Long deliveryFee;

	/**
	 * 订单总金额（商品总价＋总运费）
	 */
	private Long totalAmount;

	/**
	 * 订单总优惠金额
	 */
	private Long discountAmount;

	/**
	 * 订单支付金额
	 */
	private Long payAmount;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 配送方式ID
	 */
	private Integer deliveryId;

	/**
	 * 支付方式ID
	 */
	private Integer paymentId;

	/**
	 * 是否需要发票，0代表不需要，1代表需要
	 */
	private Integer invoiceMark;

	/**
	 * 优惠券使用标记(二进制方式存储)，0代表未使用优惠券，1代表使用了优惠券
	 */
	private Long couponMark;

	/**
	 * 删除标记
	 */
	private Integer deleteMark;


	/**
	 * 买家备注
	 */
	private String userMemo;

	/**
	 * 卖家备注
	 */
	private String sellerMemo;

	/**
	 * 管理员备注
	 */
	private String adminMemo;

	/**
	 * 订单附带信息，由业务接入方自己控制和使用其中的数据，平台只负责透传
	 */
	private String attachInfo;

	/**
	 * 下单时间
	 */
	private Date orderTime;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 发货时间
	 */
	private Date consignTime;
	/**
	 * 确认收货时间
	 */
	private Date receiptTime;

	/**
	 * 订单来源类型，1代表来自购物车，2代表来自立即购买
	 */
	private Integer sourceType;

	/**
	 * 下单明细列表
	 */
	private List<OrderItemDTO> orderItems;
	/**
	 * 订单收货地址信息
	 */
	private OrderConsigneeDTO orderConsigneeDTO;

	/**
	 * 订单支付信息
	 */
	private OrderPaymentDTO orderPaymentDTO;
	/**
	 * 订单发票信息
	 */
	private OrderInvoiceDTO orderInvoiceDTO;
	/**
	 * 订单物流信息
	 */
	private List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs;
	/**
	 * 订单优惠信息
	 */
	private List<OrderDiscountInfoDTO> orderDiscountInfoDTOs;
	/**
	 * 订单使用优惠券信息列表
	 */
	private List<UsedCouponDTO> usedCouponDTOs;
	/**
	 * 订单使用虚拟财富信息列表
	 */
	private List<UsedWealthDTO> usedWealthDTOs;
	
	/**
	 * 收货人
	 */
	private String consignee;
	
	
	
	/**
	 * 实际支付金额 、（元）
	 */
	private String totalAmountStr;
	
	private String orderTimeStr;
	
	private String orderStatusDesc;
	
	private String paymentTypeDesc;
	
	/**
     * 0 代表 不是加星。 1 代表是加星
     */
    private Integer asteriskMark;
	
	
	
    private UserConsigneeDTO userConsigneeDTO;

    /**
     * 卖家取消订单原因
     */
    private String cancelReason;
    
    /**
     * 浮动价格
     */
    private Long floatingPrice;
    
    

    public Long getFloatingPrice() {
		return floatingPrice;
	}

	public void setFloatingPrice(Long floatingPrice) {
		this.floatingPrice = floatingPrice;
	}

    public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	

	public UserConsigneeDTO getUserConsigneeDTO() {
		return userConsigneeDTO;
	}

	public void setUserConsigneeDTO(UserConsigneeDTO userConsigneeDTO) {
		this.userConsigneeDTO = userConsigneeDTO;
	}

	public Integer getAsteriskMark() {
		return asteriskMark;
	}

	public void setAsteriskMark(Integer asteriskMark) {
		this.asteriskMark = asteriskMark;
	}

	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	public String getPaymentTypeDesc() {
		return paymentTypeDesc;
	}

	public void setPaymentTypeDesc(String paymentTypeDesc) {
		this.paymentTypeDesc = paymentTypeDesc;
	}

	public String getOrderTimeStr() {
		return orderTimeStr;
	}

	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}

	public String getTotalAmountStr() {
		return totalAmountStr;
	}

	public void setTotalAmountStr(String totalAmountStr) {
		this.totalAmountStr = totalAmountStr;
	}


	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Long deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(Integer invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public Long getCouponMark() {
		return couponMark;
	}

	public void setCouponMark(Long couponMark) {
		this.couponMark = couponMark;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	public String getUserMemo() {
		return userMemo;
	}

	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	public String getAdminMemo() {
		return adminMemo;
	}

	public void setAdminMemo(String adminMemo) {
		this.adminMemo = adminMemo;
	}

	public String getAttachInfo() {
		return attachInfo;
	}

	public void setAttachInfo(String attachInfo) {
		this.attachInfo = attachInfo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getConsignTime() {
		return consignTime;
	}

	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}

	public Date getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderConsigneeDTO getOrderConsigneeDTO() {
		return orderConsigneeDTO;
	}

	public void setOrderConsigneeDTO(OrderConsigneeDTO orderConsigneeDTO) {
		this.orderConsigneeDTO = orderConsigneeDTO;
	}

	public OrderPaymentDTO getOrderPaymentDTO() {
		return orderPaymentDTO;
	}

	public void setOrderPaymentDTO(OrderPaymentDTO orderPaymentDTO) {
		this.orderPaymentDTO = orderPaymentDTO;
	}

	public OrderInvoiceDTO getOrderInvoiceDTO() {
		return orderInvoiceDTO;
	}

	public void setOrderInvoiceDTO(OrderInvoiceDTO orderInvoiceDTO) {
		this.orderInvoiceDTO = orderInvoiceDTO;
	}

	public List<OrderDeliveryInfoDTO> getOrderDeliveryInfoDTOs() {
		return orderDeliveryInfoDTOs;
	}

	public void setOrderDeliveryInfoDTOs(List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs) {
		this.orderDeliveryInfoDTOs = orderDeliveryInfoDTOs;
	}

	public List<OrderDiscountInfoDTO> getOrderDiscountInfoDTOs() {
		return orderDiscountInfoDTOs;
	}

	public void setOrderDiscountInfoDTOs(List<OrderDiscountInfoDTO> orderDiscountInfoDTOs) {
		this.orderDiscountInfoDTOs = orderDiscountInfoDTOs;
	}

	public List<UsedCouponDTO> getUsedCouponDTOs() {
		return usedCouponDTOs;
	}

	public void setUsedCouponDTOs(List<UsedCouponDTO> usedCouponDTOs) {
		this.usedCouponDTOs = usedCouponDTOs;
	}

	public List<UsedWealthDTO> getUsedWealthDTOs() {
		return usedWealthDTOs;
	}

	public void setUsedWealthDTOs(List<UsedWealthDTO> usedWealthDTOs) {
		this.usedWealthDTOs = usedWealthDTOs;
	}

}
