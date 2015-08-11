package com.mockuai.sellercenter.web.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ParamException;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.ItemBrandManager;
import com.mockuai.sellercenter.web.manager.ItemManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;

@Controller
public class ItemBrandAction extends BaseValidator{
	
	@Resource
	private  ItemBrandManager itemBrandManager;
	
/*	
	public String addBrand(HttpServletRequest request,HttpServletResponse response){
		if(!StringUtils.isEmpty(this.notNullValidate(NOT_NULL_FIELDS, request))){
			ApiResponse  apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.P_E_PARAM_ISNULL);
			return ResponseUtils.toJsonStr(apiResponse);
		}
		
		int authorizeType = Integer.valueOf(request.getParameter("authorize_type"));
		int brandId= Integer.valueOf(request.getParameter("brand_id"));
		String certClass = request.getParameter("cert_class");
		String otherQualification  = request.getParameter("other_qualification");
		String tradeMarkLicense  = request.getParameter("trade_mark_license");
		String beginTime = request.getParameter("begin_time");
		String endTime = request.getParameter("end_time");
		
		BrandDTO brand = new BrandDTO();
		brand.setAuthorizeType(authorizeType);
		brand.setBrandId(brandId);
		brand.setCertClass(certClass);
		brand.setTradeMarkLicense(tradeMarkLicense);
		brand.setOtherQualification(otherQualification);
		brand.setBeginTime(beginTime);
		brand.setEndTime(endTime);
		
		//UserDetail userDetail = (UserDetail)request.getSession().getAttribute(GlobalConstants.USER_SESSION_KEY);
		//Long userId = userDetail.getUserId();
		Long userId=(long) 12;
		brand.setUserId(userId);
		try {
			boolean result = this.itemBrandManager.addBrand(brand);
		} catch (ServiceException e) {
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		}
		ApiResponse<Boolean> apiResponse =  ResponseUtils.getSuccessApiResponse(true);
		return ResponseUtils.toJsonStr(apiResponse);
	}
	
	@RequestMapping("/delete_brand.do") 
	@ResponseBody
	private String deleteBrand(HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isEmpty(request.getParameter("brand_id"))){
			ApiResponse  apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.P_E_PARAM_ISNULL);
			return ResponseUtils.toJsonStr(apiResponse);
		}
		HttpSession session = request.getSession();
		//UserDetail user = (UserDetail)session.getAttribute(GlobalConstants.USER_SESSION_KEY);
		//Long userId = user.getUserId();
		Long userId=(long) 12;
		System.out.println(userId);
		Long brandId = Long.valueOf(request.getParameter("brand_id"));
		try {
			//根据用户id和品牌id删除
			this.brandManager.deleteBrand(userId, brandId);
		} catch (ServiceException e) {
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		}
		ApiResponse<Boolean>apiResponse =  ResponseUtils.getSuccessApiResponse(true);
		return ResponseUtils.toJsonStr(apiResponse);
	}
	
	@RequestMapping("/brand_list.do") 
	@ResponseBody
	public String getBrandList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String callback = request.getParameter("callback");
		HttpSession session = request.getSession();
		//UserDetail user = (UserDetail)session.getAttribute(GlobalConstants.USER_SESSION_KEY);
		//Long userId = user.getUserId();
		Long userId=(long) 12;
		
		BrandDTO brand =new BrandDTO();
		brand.setId(10);
		brand.setBsId(733);
		brand.setName("童年时光");
		brand.seteName("CHILDLIFE");
		brand.setStatus(1);
		brand.setSort(0);
		brand.setLogo("http://ssdsdsd");
		brand.setCreateTime("2015-03-04 12:33:20");
		brand.setUpdateTime("2015-03-05 12:33:20");
		brand.setUserId(171);
		brand.setCertClass("童装，童鞋");
		
		ApiResponse<BrandDTO> resp =  ResponseUtils.getSuccessApiResponse(brand);
		
		if(callback == null){
			return ResponseUtils.toJsonStr(resp);
		}else{
			return callback + "(" + ResponseUtils.toJsonStr(resp)  +")";
		}
	}*/
	
	@RequestMapping(value="/item/brand/get.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String getSellerBrand(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		Long brandId;
		try {
			brandId = RequestUtils.getLong(request, "brand_id", true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}
		
		SellerBrandDTO sellerBrand;
		try {
			sellerBrand = this.itemBrandManager.getSellerBrand(brandId, supplierId);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(sellerBrand);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(sellerBrand) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/brand/query.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String querySellerBrand(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		
		SellerBrandQTO sellerBrandQTO = new SellerBrandQTO();
		sellerBrandQTO.setSellerId(supplierId);
		List<SellerBrandDTO> brandList = null;
		try {
			brandList = this.itemBrandManager
					.querySellerBrand(sellerBrandQTO);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(brandList);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(brandList) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/brand/delete.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String deleteSellerBrand(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		Long brandId=null;
		
		try {
			brandId = RequestUtils.getLong(request, "brand_id", true);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		}

		Boolean result= null;
		
		try {
			result = this.itemBrandManager.deleteSellerBrand(brandId,supplierId);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/brand/add.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String addSellerBrand(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		
		Integer brandId;
		String brandName=null,brandEnName=null,categoryClass=null,logo=null,brandDesc=null;
		try {
			brandName = RequestUtils.getString(request, "brand_name",true);
			brandEnName = RequestUtils.getString(request, "brand_en_name",false);
			categoryClass = RequestUtils.getString(request, "category_class",false);
			logo = RequestUtils.getString(request, "logo",true);
			brandDesc = RequestUtils.getString(request, "brand_desc",false);
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		} catch(Exception e){
			
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
		
		SellerBrandDTO dto = new SellerBrandDTO();
		dto.setLogo(logo);
		dto.setBrandDesc(brandDesc);
		dto.setBrandName(brandName);
		dto.setBrandEnName(brandEnName);
		dto.setCategoryClass(categoryClass);
		//TODO  logo 的url格式验证
		// dto.getLogo()
		
		dto.setSellerId(supplierId);
		//TODO 字段定义 暂时没有对于品牌的状态控制
		dto.setStatus(1); 
		
		SellerBrandDTO result = null;
		try {
			result = this.itemBrandManager.addSellerBrand(dto);
		} catch (ServiceException e) {
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
	@RequestMapping(value="/item/brand/update.do",produces="application/json; charset=utf-8") 
	@ResponseBody
	public String updateSellerBrand(HttpServletRequest request){
		String callback = request.getParameter("callback");
		Long supplierId = SessionManager.getLoginUserId(request.getSession());
		Long brandId;
		String brandName=null,brandEnName=null,categoryClass=null,logo=null,brandDesc=null;
		try {
			brandId = RequestUtils.getLong(request, "brand_id", true);
			brandName = RequestUtils.getString(request, "brand_name",true);
			brandEnName = RequestUtils.getString(request, "brand_en_name",false);
			categoryClass = RequestUtils.getString(request, "category_class",false);
			logo = RequestUtils.getString(request, "logo",true);
			brandDesc = RequestUtils.getString(request, "brand_desc",false);
			
		} catch (ParamException e) {
			return ResponseUtils.getFailApiResponseStr(e);
		} catch(Exception e){
			
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
		
		SellerBrandDTO dto = new SellerBrandDTO();
		dto.setId(brandId);
		dto.setLogo(logo);
		dto.setBrandDesc(brandDesc);
		dto.setBrandName(brandName);
		dto.setBrandEnName(brandEnName);
		dto.setCategoryClass(categoryClass);
		
		dto.setSellerId(supplierId);
		
		Boolean result = null;
		try {
			result = this.itemBrandManager.updateSellerBrand(dto);
		} catch (ServiceException e) {
			ApiResponse<Boolean> apiResponse = ResponseUtils.getFailApiResponse(e.getCode(),e.getServiceMessage());
			return ResponseUtils.toJsonStr(apiResponse);
		}
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(result);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(result) + ")"; 
		}
	}
	
}
