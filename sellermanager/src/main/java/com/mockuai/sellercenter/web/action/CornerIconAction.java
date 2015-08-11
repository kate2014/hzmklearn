package com.mockuai.sellercenter.web.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.CornerIconManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;

@Controller
public class CornerIconAction {
	
	@Resource
	private CornerIconManager cornerIconManager;
		
	@RequestMapping(value="/item/icon/add.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String addCornerIcon(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long sellerId = SessionManager.getLoginUserId(request.getSession());
		String iconUrl,iconName,iconDesc;
		try {
			iconUrl = RequestUtils.getString(request, "icon_url", true);
			iconName = RequestUtils.getString(request, "icon_name",
					false);
			iconDesc = RequestUtils.getString(request, "icon_desc",
					false);
		} catch (ParamException e) {
			String result = ResponseUtils.getFailApiResponseStr(e);
			return StringUtils.isBlank(callback)? result : callback +"(" + result + ")";
		}
		
		CornerIconDTO cornerIconDTO =new CornerIconDTO();
		cornerIconDTO.setIconDesc(iconDesc);
		cornerIconDTO.setIconName(iconName);
		cornerIconDTO.setIconUrl(iconUrl);
		cornerIconDTO.setSellerId(sellerId);
		CornerIconDTO result =null;
		try{
			result = this.cornerIconManager.addCornerIcon(cornerIconDTO);
		}catch(ServiceException e){
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			String errorMsg = ResponseUtils.toJsonStr(apiResponse);
			return StringUtils.isBlank(callback)? errorMsg : callback +"(" + errorMsg + ")";
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/icon/delete.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String deleteCornerIcon(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long sellerId = SessionManager.getLoginUserId(request.getSession());
		Long id;
		try {
			id = RequestUtils.getLong(request, "icon_id", true);
		} catch (ParamException e) {
			String result = ResponseUtils.getFailApiResponseStr(e);
			return StringUtils.isBlank(callback)? result : callback +"(" + result + ")";
		}
		
		Boolean result =null;
		try{
			result = this.cornerIconManager.deleteCornerIcon(id, sellerId);
		}catch(ServiceException e){
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			String errorMsg = ResponseUtils.toJsonStr(apiResponse);
			return StringUtils.isBlank(callback)? errorMsg : callback +"(" + errorMsg + ")";
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/icon/query.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryCornerIcon(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long sellerId = SessionManager.getLoginUserId(request.getSession());
		
		List<CornerIconDTO> result =null;
		CornerIconQTO cornerIconQTO = new CornerIconQTO();
		//TODO 是否是根据bizCode来查询
		cornerIconQTO.setSellerId(sellerId);
		try{
			result = this.cornerIconManager.queryCornerIcon(cornerIconQTO);
		}catch(ServiceException e){
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			String errorMsg =  ResponseUtils.toJsonStr(apiResponse);
			return StringUtils.isBlank(callback)? errorMsg : callback +"(" + errorMsg + ")";
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
}
