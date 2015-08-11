package com.mockuai.sellercenter.web.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.LogisticsCompanyDTO;
import com.mockuai.sellercenter.web.dto.OrderStrDTO;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.dto.SkuPropertyDTO;
import com.mockuai.sellercenter.web.manager.ItemSkuManager;
import com.mockuai.sellercenter.web.manager.OrderManager;
import com.mockuai.sellercenter.web.util.DozerBeanService;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.util.DateUtil;

@Controller
public class OrderAction extends BaseValidator {

	private String[] ACCEPT_DATE_FORMAT = { "yyyy-MM-dd" };

	@Resource
	private OrderManager orderManager;

	@Resource
	private ItemSkuManager itemSkuManager;

	private String appKey = GlobalConstants.DEFAULT_APP_KEY;

	@Resource
	private DozerBeanService dozerBeanService;

	private com.mockuai.tradecenter.common.domain.OrderQTO initQuery(HttpServletRequest request)
			throws ParamException, UnsupportedEncodingException {

		com.mockuai.tradecenter.common.domain.OrderQTO order = new OrderQTO();

		Integer orderStatus = null;

		Date orderTimeStart = null, orderTimeEnd = null;
		Integer paymentId = null;
		String consigneeMobile = null, consignee = null;

		Integer currentPage, pageSize = null;

		currentPage = RequestUtils.getInt(request, "current_page", false);
		pageSize = RequestUtils.getInt(request, "page_size", false);

		if (null == currentPage) {
			currentPage = 1;
		}

		order.setOffset(((currentPage - 1) * pageSize));
		order.setCount(pageSize);

		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		order.setSellerId(supplierId);

		orderStatus = RequestUtils.getInt(request, "order_status", false);
		order.setOrderStatus(orderStatus);

		paymentId = RequestUtils.getInt(request, "payment_id", false);
		order.setPaymentId(paymentId);

		if (!StringUtils.isBlank(request.getParameter("order_sn"))) {
			order.setOrderSn(request.getParameter("order_sn"));
		}
		if (!StringUtils.isBlank(request.getParameter("start_time"))) {
			orderTimeStart = RequestUtils.getFormatDate(request, "start_time", ACCEPT_DATE_FORMAT);
			order.setOrderTimeStart(orderTimeStart);
		}
		if (!StringUtils.isBlank(request.getParameter("end_time"))) {
			orderTimeEnd = RequestUtils.getFormatDate(request, "end_time", ACCEPT_DATE_FORMAT);

			order.setOrderTimeEnd(DateUtil.getRelativeDate(orderTimeEnd, 1));
		}
		if (!StringUtils.isBlank(request.getParameter("consignee_mobile"))) {
			consigneeMobile = request.getParameter("consignee_mobile");
			order.setConsigneeMobile(consigneeMobile);
		}
		if (!StringUtils.isBlank(request.getParameter("consignee"))) {
			consignee = request.getParameter("consignee");
			order.setConsignee(new String(consignee.getBytes("ISO-8859-1"), "utf-8"));
		}
		if (!StringUtils.isBlank(request.getParameter("asterisk_mark"))) {
			order.setAsteriskMark(Integer.parseInt(request.getParameter("asterisk_mark")));
		}

		return order;
	}

	public void queryItemSku(List<OrderStrDTO> orders) throws ServiceException {

		for (OrderStrDTO o : orders) {
			List<com.mockuai.sellercenter.web.dto.OrderItemDTO> orderItems = o.getOrderItems();
			for (com.mockuai.sellercenter.web.dto.OrderItemDTO orderItem : orderItems) {

				ItemSkuQTO query = new ItemSkuQTO();

				query.setId(orderItem.getItemSkuId());

				List<ItemSkuDTO> list = itemSkuManager.queryItemSku(query);

				ItemSkuDTO itemSUK = list.get(0);

				List<SkuPropertyDTO> skuPropertyList = dozerBeanService.coverList(itemSUK.getSkuPropertyDTOList(),
						com.mockuai.sellercenter.web.dto.SkuPropertyDTO.class);

				orderItem.setSkuPropertyList(skuPropertyList);

				com.mockuai.tradecenter.common.domain.ItemSkuDTO sku = new com.mockuai.tradecenter.common.domain.ItemSkuDTO();

				BeanUtils.copyProperties(itemSUK, sku);
				orderItem.setSku(sku);

			}
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/order/get.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getOrder(HttpServletRequest request) {
		String callback = request.getParameter("callback");

		try {
			Long orderId = RequestUtils.getLong(request, "order_id", true);
			Long userId = RequestUtils.getLong(request, "user_id", true);

			Response<OrderDTO> serviceResponse = null;

			serviceResponse = this.orderManager.getOrder(orderId, userId, appKey);
			List<OrderStrDTO> responseList = new ArrayList<OrderStrDTO>();
			OrderStrDTO orderStrDTO = dozerBeanService.cover(serviceResponse.getModule(), OrderStrDTO.class);

			responseList.add(orderStrDTO);
			queryItemSku(responseList);

			if (StringUtils.isBlank(callback)) {
				return ResponseUtils.getSuccessApiResponseStr(orderStrDTO);
			} else {
				return callback + "(" + ResponseUtils.getSuccessApiResponseStr(orderStrDTO) + ")";
			}
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		} catch (Exception e) {
			ApiResponse apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.S_E_SERVICE_ERROR.getCode(),
					e.getMessage());
			return ResponseUtils.toJsonStr(apiResponse);

		}
	}

	/**
	 * 筛选订单
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/order/query.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryOrder(HttpServletRequest request) {

		String callback = request.getParameter("callback");

		try {

			OrderQTO query = initQuery(request);

			Response<List<OrderDTO>> serviceResponse = null;

			serviceResponse = this.orderManager.queryOrder(query, appKey);
			List<OrderDTO> orderList = serviceResponse.getModule();
			List<OrderStrDTO> responseList = new ArrayList<OrderStrDTO>();
			if (null != orderList && orderList.size() > 0) {

				for (OrderDTO order : orderList) {
					OrderStrDTO orderStrDTO = dozerBeanService.cover(order,
							com.mockuai.sellercenter.web.dto.OrderStrDTO.class);
					responseList.add(orderStrDTO);
				}
				queryItemSku(responseList);

			}

			PageDTO<List<OrderStrDTO>> pageInfo = new PageDTO<List<OrderStrDTO>>();
			pageInfo.setData(responseList);
			pageInfo.setTotalCount(serviceResponse.getTotalCount());
			if (StringUtils.isBlank(callback)) {
				return ResponseUtils.getSuccessApiResponseStr(pageInfo);
			} else {
				return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageInfo) + ")";
			}

		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		} catch (Exception e) {
			ApiResponse apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.S_E_SERVICE_ERROR.getCode(),
					e.getMessage());
			return ResponseUtils.toJsonStr(apiResponse);

		}
	}

	@RequestMapping(value = "/order/queryLogisticsCompany.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryLogisticsCompany(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		List<LogisticsCompanyDTO> logisticsCompanyList = new ArrayList<LogisticsCompanyDTO>();
		logisticsCompanyList.add(new LogisticsCompanyDTO(1, "申通E物流"));
		logisticsCompanyList.add(new LogisticsCompanyDTO(2, "圆通速递"));
		logisticsCompanyList.add(new LogisticsCompanyDTO(3, "中通速递"));
		if (StringUtils.isBlank(callback)) {
			return ResponseUtils.getSuccessApiResponseStr(logisticsCompanyList);
		} else {
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(logisticsCompanyList) + ")";
		}
	}

	@RequestMapping(value = "/order/updateMemo.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateMemo(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		try {
			String memo = RequestUtils.getString(request, "memo", true);
			// order.setConsignee(new
			// String(consignee.getBytes("ISO-8859-1"),"utf-8"));
			// memo = new String(memo.getBytes("ISO-8859-1"),"utf-8");

			Long orderId = RequestUtils.getLong(request, "order_id", true);
			Long userId = RequestUtils.getLong(request, "user_id", true);

			boolean result = this.orderManager.updateOrderSellerMemo(orderId, userId, memo, appKey);

			if (StringUtils.isBlank(callback)) {
				return ResponseUtils.getSuccessApiResponseStr(result);
			} else {
				return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
			}

		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		} catch (Exception e) {
			ApiResponse apiResponse = ResponseUtils.getFailApiResponse(20000, e.getMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		}
	}

	@RequestMapping(value = "/order/updateAsteriskMark.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateAsteriskMark(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		long orderId, userId;

		try {
			orderId = RequestUtils.getLong(request, "order_id", true);
			userId = RequestUtils.getLong(request, "user_id", true);
			// 0 不加星 1 加星
			String asteriskMark = RequestUtils.getString(request, "asterisk_marks", true);

			boolean result;
			result = this.orderManager.updateAsteriskMark(orderId, userId, asteriskMark, appKey);

			if (StringUtils.isBlank(callback)) {
				return ResponseUtils.getSuccessApiResponseStr(result);
			} else {
				return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
			}

		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		} catch (Exception e) {
			return ResponseUtils.toJsonStr(
					ResponseUtils.getFailApiResponse(ResponseEnum.S_E_SERVICE_ERROR.getCode(), e.getMessage()));
		}

	}

	@RequestMapping(value = "/order/cancel.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String cancelOrder(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		long orderId, userId;
		String cancelReason = null;
		try {
			orderId = RequestUtils.getLong(request, "order_id", true);
			userId = RequestUtils.getLong(request, "user_id", true);
			cancelReason = RequestUtils.getString(request, "cancel_reason", true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}

		boolean result;
		try {
			result = this.orderManager.cancelOrder(orderId, userId, cancelReason, appKey);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}

		if (StringUtils.isBlank(callback)) {
			return ResponseUtils.getSuccessApiResponseStr(result);
		} else {
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
		}
	}

	@RequestMapping(value = "/order/update_price.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateOrderPrice(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		long orderId, userId, freight, floatingPrice;

		try {
			orderId = RequestUtils.getLong(request, "order_id", true);
			userId = RequestUtils.getLong(request, "user_id", true);

			// freight = RequestUtils.getLong(request,"freight",false);
			floatingPrice = RequestUtils.getLong(request, "floating_price", true);

		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}

		boolean result;
		try {
			result = this.orderManager.updateOrderPrice(orderId, userId, 0l, floatingPrice, appKey);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		if (StringUtils.isBlank(callback)) {
			return ResponseUtils.getSuccessApiResponseStr(result);
		} else {
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
		}
	}

	/**
	 * 订单收货
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/order/delivery.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deliveryGoods(HttpServletRequest request) {
		String callback = request.getParameter("callback");
		OrderDeliveryInfoDTO delivery = null;
		List<OrderDeliveryInfoDTO> deliveryList = new ArrayList<OrderDeliveryInfoDTO>();
		long orderId = 0, userId = 0;
		Integer deliveryType;

		try {
			orderId = RequestUtils.getLong(request, "order_id", true);
			userId = RequestUtils.getLong(request, "user_id", true);

			String needDelivery = RequestUtils.getString(request, "need_delivery", true);

			if (needDelivery.equalsIgnoreCase("y")) {
				// 需要物流
				delivery = new OrderDeliveryInfoDTO();
				String deliveryCompany;
				deliveryCompany = RequestUtils.getString(request, "delivery_company", true);
				String diliveryCode = RequestUtils.getString(request, "delivery_code", true);
				delivery.setDeliveryCode(diliveryCode);
				delivery.setOrderId(orderId);
				delivery.setUserId(userId);

				delivery.setDeliveryCompany(deliveryCompany);
				delivery.setDeliveryFee(0l);
				delivery.setDeliveryType(2);

				deliveryList.add(delivery);
			}

		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}

		boolean result;
		try {
			result = this.orderManager.deliveryGoods(orderId, userId, deliveryList, appKey);

			if (result) {

				OrderDTO order = this.orderManager.getOrder(orderId, userId, appKey).getModule();

				if (null != order) {
					return ResponseUtils.getSuccessApiResponseStr(order);
				}

			} else {
				return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
			}
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}

		return null;
	}

}
