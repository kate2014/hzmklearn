package com.mockuai.sellercenter.web.action;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.dts.client.OrderExportClient;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import com.mockuai.tradecenter.common.domain.OrderQTO;
@Controller
public class OrderExportAction {

	protected final Log log = LogFactory.getLog(this.getClass());
	
	private String[] ACCEPT_DATE_FORMAT = { "yyyy-MM-dd" };
	
	@Resource
	private OrderExportClient orderExportClient;
	
	protected String appKey = "3bc25302234640259fadea047cb7c7d3";

	private com.mockuai.tradecenter.common.domain.OrderQTO initQuery(HttpServletRequest request) throws ParamException, UnsupportedEncodingException{

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
		
		if(null ==pageSize ){
			pageSize = 500;
		}

		order.setOffset(((currentPage - 1) * pageSize));
		order.setCount(pageSize);

		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		order.setSellerId(supplierId);

		orderStatus = RequestUtils.getInt(request, "order_status", false);
		order.setOrderStatus(orderStatus);

		paymentId = RequestUtils.getInt(request, "payment_id", false);
		order.setPaymentId(paymentId);

		if(!StringUtils.isBlank(request.getParameter("order_sn"))){
			order.setOrderSn(request.getParameter("order_sn"));
		}
		if (!StringUtils.isBlank(request.getParameter("start_time"))) {
			orderTimeStart = RequestUtils.getFormatDate(request, "start_time", ACCEPT_DATE_FORMAT);
			order.setOrderTimeStart(orderTimeStart);
		}
		if (!StringUtils.isBlank(request.getParameter("end_time"))) {
			orderTimeEnd = RequestUtils.getFormatDate(request, "end_time", ACCEPT_DATE_FORMAT);
			order.setOrderTimeEnd(orderTimeEnd);
		}
		if (!StringUtils.isBlank(request.getParameter("consignee_mobile"))) {
			consigneeMobile = request.getParameter("consigneeMobile");
			order.setConsigneeMobile(consigneeMobile);
		}
		if(!StringUtils.isBlank(request.getParameter("consignee"))){
			consignee = request.getParameter("consignee");
			order.setConsignee(new String(consignee.getBytes("ISO-8859-1"),"utf-8"));
		}
		if(!StringUtils.isBlank(request.getParameter("asterisk_mark"))){
			order.setAsteriskMark(Integer.parseInt(request.getParameter("asterisk_mark")));
		}

		return order;
	}

	@RequestMapping(value = "/order/downloadOrders.do")
	@ResponseBody
	public String downOrders(HttpServletRequest request, ModelMap model) {
		
		com.mockuai.dts.common.domain.OrderExportQTO exportQuery = new com.mockuai.dts.common.domain.OrderExportQTO();
		try {

			String callback = request.getParameter("callback");
			
			try {
				 OrderQTO query = initQuery(request);
				
				 BeanUtils.copyProperties(query, exportQuery);
			} catch (ParamException e) {
				return ResponseUtils.getFailApiResponseStr(e);
			} catch(Exception e){
				log.error("parse param error : " ,e);
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
			}
	
			com.mockuai.dts.common.api.action.Response<Boolean> exportRes = orderExportClient.exportOrders(exportQuery);
			boolean result = false;
			if(exportRes.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
				result = exportRes.getModule();
			}
			if(StringUtils.isBlank(callback)){
				return ResponseUtils.getSuccessApiResponseStr(result);
			}else{
				return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
			}

		} 
		
		catch(Exception e){
			
			log.error("OrderExportAction downOrders error",e);
			
			ApiResponse apiResponse = ResponseUtils.getFailApiResponse(20000,e.getMessage());
			return  ResponseUtils.toJsonStr(apiResponse);
		}

	}

}
