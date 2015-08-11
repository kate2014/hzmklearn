package com.mockuai.sellercenter.web.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemCategoryManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;

@Controller
public class ItemCategoryAction extends BaseValidator{
	
	@Resource
	private ItemCategoryManager itemCategoryManager;
	
	/**
	 * 非空字段
	 */
	private String[] NOT_NULL_FIELDS = {
			"parent_id"
	};
	
	/**
	 * 查询子类目列表 第一级别则是0
	 * @param request
	 * @return
	 */
	public String querySubCategory(HttpServletRequest request){
		String callback = request.getParameter("callback");
		
		//String bizCode = SessionManager.getLoginUserBizCode(request.getSession());
		Long parentId = null;
		
		try {
			parentId = RequestUtils.getLong(request, "parent_id", false);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		List<ItemCategoryDTO> list= null;
		try {
			list = this.itemCategoryManager
					.querySubCategory(parentId);
		} catch (ServiceException e) {
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(list);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(list) + ")"; 
		}
		
	}
	
	@RequestMapping(value="/category/query.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String queryCategory(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long parentId = null;
		try {
			parentId = RequestUtils.getLong(request, "parent_id", false);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		//String bizCode = SessionManager.getLoginUserBizCode(request.getSession());
		List<ItemCategoryDTO> list= null;
		ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
		
		//itemCategoryQTO.setBizCode(bizCode);
		itemCategoryQTO.setParentId(parentId);
		try {
			list = this.itemCategoryManager.queryCategory(itemCategoryQTO);
					
		} catch (ServiceException e) {
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(list);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(list) + ")"; 
		}
	}

	@RequestMapping(value="/category/leaf/query.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryLeafCategory(HttpServletRequest request){
		String callback = request.getParameter("callback");
		List<ItemCategoryDTO> list= null;
		try {
			list = this.itemCategoryManager.queryLeafCategory();

		} catch (ServiceException e) {
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		}

		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(list);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(list) + ")";
		}

	}
	
}
