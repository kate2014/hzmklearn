package com.mockuai.sellercenter.web.manager;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;

public interface OrderManager {
	// private OrderClient orderClient;
	/**
	 * 获取订单
	 * @param orderId
	 * @param userId
	 * @param appkey
	 * @return
	 * @throws ServiceException
	 */
	public Response<OrderDTO> getOrder(Long orderId,Long userId,String appkey)throws ServiceException;
	
	/**
	 * 查询底层的待发货的订单
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public Integer pendingDelivery(Long userId)throws ServiceException;
	
	
	/**
	 * 查询订单
	 * @param query
	 * @param appKey
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<OrderDTO>> queryOrder(OrderQTO query,String appKey)throws ServiceException;
	
	/**
	 * 修改订单价格
	 * @param orderId
	 * @param userId
	 * @param freight
	 * @param floatingPrice
	 * @return
	 */
	Boolean updateOrderPrice(long orderId,long userId,long freight,long floatingPrice,String appKey)throws ServiceException;
	
	/**
	 * 取消订单
	 * @param orderId
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Boolean cancelOrder(long orderId,long userId,String cancelReason,String appKey)throws ServiceException;
	
	/**
	 * 发货
	 * @param orderId
	 * @param userId
	 * @param list
	 * @param appkEY
	 * @return
	 */
	Boolean deliveryGoods(long orderId,long userId,List<OrderDeliveryInfoDTO> list,String appkEY)throws ServiceException;
	
	
	/**
	 * 导出excel
	 */
//	public void downReportXls(HttpServletResponse response, OrderQTO query,String appkey)throws ServiceException;
	
	Boolean updateOrderSellerMemo(long orderId,long userId,String memo,String appkey)throws ServiceException;
	
	Boolean updateAsteriskMark(long orderId,long userId,String asteriskMark,String appkey)throws ServiceException;
	
}
