package com.mockuai.sellercenter.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.dts.client.SellerUserExportClient;
import com.mockuai.dts.common.domain.SellerUserExportQTO;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.GlobalConstants;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.CustomerRelateDTO;
import com.mockuai.sellercenter.web.dto.PageDTO;
import com.mockuai.sellercenter.web.manager.UserManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;


/**
 * 客户关系 控制类
 * @author hzmk
 *
 */
@Controller
public class CustomerRelationAction {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private UserManager userManager;
	
	@Resource
	private SellerUserExportClient sellerUserExportClient;
	
	
	protected String appKey = "3bc25302234640259fadea047cb7c7d3";
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="customerRelate/query.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	protected String query(HttpServletRequest request){
		String callback = request.getParameter("callback");
		try{
			String keyword = RequestUtils.getString(request,"keyword",false);
			SellerUserQTO query = new SellerUserQTO();
			if(StringUtils.isNotBlank(keyword)){
				query.setKeyword(keyword);
			}
			
			Integer currentPage,pageSize = null;
			
			currentPage = RequestUtils.getInt(request, "current_page",false);
			pageSize = RequestUtils.getInt(request, "page_size",false);
			
			if(null==currentPage){
				currentPage = 1;
			}
			
			if(null==pageSize){
				pageSize = 20;
			}
			query.setPageSize(pageSize);
			query.setStartRow((long) ((currentPage-1)*pageSize));
			
			Response<List<SellerUserRelateDTO>> serviceResponse = (Response<List<SellerUserRelateDTO>>) userManager.querySellerUserRelate(query, appKey);
			
			List<SellerUserRelateDTO> sellerUserRelateDTOList = serviceResponse.getModule();
			
			List<CustomerRelateDTO> responseList = new ArrayList<CustomerRelateDTO>();
			
			if(null!=sellerUserRelateDTOList&&sellerUserRelateDTOList.size()>0){
				for(SellerUserRelateDTO sellerUserRelateDTO:sellerUserRelateDTOList){
					CustomerRelateDTO customerRelateDTO = new CustomerRelateDTO();
					customerRelateDTO.setUserId(sellerUserRelateDTO.getUserId()+"");
					customerRelateDTO.setUserName(sellerUserRelateDTO.getUserName());
					customerRelateDTO.setMobile(sellerUserRelateDTO.getMobile());
					customerRelateDTO.setFinishedOrderNum(sellerUserRelateDTO.getFinishedOrderNum());
					customerRelateDTO.setFinishedOrderTotalAmt(MoneyUtil.getMoneyStr(sellerUserRelateDTO.getFinishedOrderAmount()));
					customerRelateDTO.setJoinTime(DateUtil.getFormatDate(sellerUserRelateDTO.getRelateTimes(),"yyyy-MM-dd HH:mm:ss.S"));
					customerRelateDTO.setImgUrl(sellerUserRelateDTO.getImgUrl());
					responseList.add(customerRelateDTO);
				}
			}
			
			
			PageDTO<List<CustomerRelateDTO>> pageInfo =new PageDTO<List<CustomerRelateDTO>>();
			pageInfo.setData(responseList);
			pageInfo.setTotalCount(serviceResponse.getTotalCount());
			if(StringUtils.isBlank(callback)){
				return ResponseUtils.getSuccessApiResponseStr(pageInfo); 
			}else{
				return callback + "(" + ResponseUtils.getSuccessApiResponseStr(pageInfo) + ")"; 
			}
				
			}catch (ServiceException e) {
				return ServiceResponseHandler.serviceExceptionHandler(e);
			} 
			catch (Exception e) {
				logger.error("CustomerRelationAction.query error",e);
				ApiResponse apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.S_E_SERVICE_ERROR.getCode(),e.getMessage());
				return  ResponseUtils.toJsonStr(apiResponse);
		}
	}
	
	@RequestMapping(value = "customerRelate/download.do")
	@ResponseBody
	public String download(HttpServletRequest request, ModelMap model) {
		String callback = request.getParameter("callback");
		SellerUserExportQTO exportQuery = new SellerUserExportQTO();
		String keyword;
		try {
			keyword = RequestUtils.getString(request,"keyword",false);
			if(StringUtils.isNotBlank(keyword)){
				exportQuery.setKeyword(keyword);
			}
			Long supplierId = SessionManager.getLoginUserId(request.getSession());	
			exportQuery.setSellerId(supplierId);
			
			Integer currentPage,pageSize = null;
			
			currentPage = RequestUtils.getInt(request, "current_page",false);
			pageSize = RequestUtils.getInt(request, "page_size",false);
			
			if(null==currentPage){
				currentPage = 1;
			}
			
			if(null==pageSize){
				pageSize = 20;
			}
			
			exportQuery.setOffset(currentPage);
			exportQuery.setPageSize(pageSize);
			
			com.mockuai.dts.common.api.action.Response<Boolean> exportRes = sellerUserExportClient.exportDatas(exportQuery);
			boolean result = false;
			if(exportRes.getCode() == GlobalConstants.SERVICE_PROCESS_SUCCESS) {
				result = exportRes.getModule();
				if(StringUtils.isBlank(callback)){
					return ResponseUtils.getSuccessApiResponseStr(result);
				}else{
					return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")";
				}
			}else{
				return ResponseUtils.toJsonStr(ResponseUtils.getFailApiResponse(exportRes.getCode(),exportRes.getMessage()));
			}
			
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}catch (Exception e) {
			logger.error("CustomerRelationAction.query error",e);
			ApiResponse apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.S_E_SERVICE_ERROR.getCode(),e.getMessage());
			return  ResponseUtils.toJsonStr(apiResponse);
	}
	}
}
