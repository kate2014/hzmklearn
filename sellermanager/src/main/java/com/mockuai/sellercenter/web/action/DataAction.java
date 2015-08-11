package com.mockuai.sellercenter.web.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.DataManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.dataCenter.DataDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;
import com.mockuai.tradecenter.common.util.DateUtil;

@Controller
public class DataAction   extends BaseValidator {
	@Resource
	private DataManager dataManager;
	
	protected String appKey = "3bc25302234640259fadea047cb7c7d3";
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String[] ACCEPT_DATE_FORMAT= {"yyyy-MM-dd"};
	
	/**
	 * 卖家查询用户中心的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/data/query.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryData(HttpServletRequest request){

		DataQTO orderQTO = new DataQTO();
		
		String callback = request.getParameter("callback");
		long sellerId = SessionManager.getLoginUserId(request.getSession());
		
		orderQTO.setSellerId(sellerId);
		
		try {
			if(!StringUtils.isBlank(request.getParameter("start_time"))){
				Date orderTimeStart = RequestUtils.getFormatDate(request, "start_time",ACCEPT_DATE_FORMAT);
				orderQTO.setTimeStart(orderTimeStart);
			}
			if(!StringUtils.isBlank(request.getParameter("end_time"))){
				Date orderTimeEnd = RequestUtils.getFormatDate(request, "end_time",ACCEPT_DATE_FORMAT);
				orderQTO.setTimeEnd(DateUtil.getRelativeDate(orderTimeEnd,1));
			}
		} catch (ParamException e) {
			logger.error("queryData ParamException error", e);
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		DataDTO result;
		try {
			result = this.dataManager.getData(orderQTO, appKey).getModule();
		} catch (ServiceException e) {
			logger.error("DataAction.queryData ServiceException error", e);
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}catch(Exception e){
			logger.error("DataAction.queryData exception error", e);
			return  ResponseUtils.toJsonStr(ResponseUtils.toJsonStr(ResponseUtils.getFailApiResponse(ResponseEnum.S_E_SERVICE_ERROR.getCode(),e.getMessage())));
		}
		if(result==null){
			result = new DataDTO();
			result.setPaidOrderCount(0);
			result.setPaidUserCount(0);
			result.setPriceOfUserAverage(0);
			result.setTotalAmount(0);
			result.setTotalOrderCount(0);
			result.setTotalUserCount(0);
		}
			
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	/**
	 * 得到昨天，一周内，全部的数据信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/data/getData.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String getData(HttpServletRequest request){
		String callback = request.getParameter("callback");
		long sellerId = SessionManager.getLoginUserId(request.getSession());
		
		DataQTO orderQTO_yesterday = new DataQTO();
		DataQTO orderQTO_7day = new DataQTO();
		DataQTO orderQTO_all = new DataQTO();
		List<DataDTO> result = new ArrayList<DataDTO>();
		
		orderQTO_yesterday.setSellerId(sellerId);
		orderQTO_7day.setSellerId(sellerId);
		orderQTO_all.setSellerId(sellerId);
		
		try {
			Date today = DateUtil.getToday().getTime();
			Date yesterday = DateUtil.getRelativeDate(today, -1);
			Date dayBefore7 = DateUtil.getRelativeDate(today, -7);
			Date tomorrow = DateUtil.getRelativeDate(today, 1);
			
			orderQTO_yesterday.setTimeStart(yesterday);
			orderQTO_yesterday.setTimeEnd(today);
			orderQTO_7day.setTimeStart(dayBefore7);
			orderQTO_7day.setTimeEnd(tomorrow);
		} catch (ParseException e) {
			 ResponseUtils.toJsonStr(ResponseUtils.toJsonStr(ResponseUtils.getFailApiResponse(ResponseEnum.P_E_PARAM_INVALID.getCode(),e.getMessage())));
		}
		
		DataDTO result_yesterday;
		DataDTO result_7day ;
		DataDTO result_all;
		try {
			result_yesterday = this.dataManager.getData(orderQTO_yesterday, appKey).getModule();
			
			result_7day = this.dataManager.getData(orderQTO_7day, appKey).getModule();
			
			result_all =  this.dataManager.getData(orderQTO_all, appKey).getModule();
			
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		 
		
		DataDTO result_null = new DataDTO();
		result_null.setPaidOrderCount(0);
		result_null.setPaidUserCount(0);
		result_null.setPriceOfUserAverage(0);
		result_null.setTotalAmount(0);
		result_null.setTotalOrderCount(0);
		result_null.setTotalUserCount(0);
		
		if(result_yesterday==null)
			result.add(result_null);
		else
			result.add(result_yesterday);
		if(result_7day==null)
			result.add(result_null);
		else
			result.add(result_7day);
		if(result_all==null)
			result.add(result_null);
		else
			result.add(result_all);
		
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	/**
	 * 查询销量前10的商品信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/data/queryTOP10.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryTOP10Item(HttpServletRequest request){
		String callback = request.getParameter("callback");
		long sellerId = SessionManager.getLoginUserId(request.getSession());
		
		DataQTO orderQTO = new DataQTO();
		orderQTO.setSellerId(sellerId);
		
		try {
			if(!StringUtils.isBlank(request.getParameter("start_time"))){
				Date orderTimeStart = RequestUtils.getFormatDate(request, "start_time",ACCEPT_DATE_FORMAT);
				orderQTO.setTimeStart(orderTimeStart);
			}
			if(!StringUtils.isBlank(request.getParameter("end_time"))){
				Date orderTimeEnd = RequestUtils.getFormatDate(request, "end_time",ACCEPT_DATE_FORMAT);
				orderQTO.setTimeEnd(DateUtil.getRelativeDate(orderTimeEnd,1));
			}
		} catch (ParamException e) {
			logger.error("queryTOP10Item", e);
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		Response<List<SalesVolumeDTO>> result;
		try {
			result = this.dataManager.queryTOP10Item(orderQTO, appKey);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
}
