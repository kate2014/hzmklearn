package com.mockuai.sellercenter.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.ServiceResponseHandler;
import com.mockuai.sellercenter.web.common.SessionManager;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.dto.param.ItemPropertyParam;
import com.mockuai.sellercenter.web.dto.param.ItemPropertyParam.PropValue;
import com.mockuai.sellercenter.web.manager.ItemPropertyManager;
import com.mockuai.sellercenter.web.util.RequestUtils;
import com.mockuai.sellercenter.web.util.ResponseUtils;

@Controller
public class PropertyAction extends BaseValidator {

	@Resource
	private ItemPropertyManager itemPropertyManager;
	
	private final String[] NOT_NULL_FIELDS = {
			"category_id"
	};
	
	/**
	 * 根据类目属性来查找其对应的属性 及其默认的属性值
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/property/query.do",produces="application/json; charset=utf-8")
	@ResponseBody
	public String queryProperty(HttpServletRequest request){
		String callback = request.getParameter("callback");

		if(!StringUtils.isBlank(this.notNullValidate(NOT_NULL_FIELDS, request))){
			ApiResponse  apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.P_E_PARAM_ISNULL);
			return ResponseUtils.toJsonStr(apiResponse);
		}		
		Long userId = SessionManager.getLoginUserId(request.getSession());
		Long categoryId =null;
		try {
			categoryId = RequestUtils.getLong(request, "category_id",true);
		} catch (Exception e) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_INVALID);
		}
			
		List<ItemPropertyTmplDTO> itemPropertyList= null;
		ItemPropertyTmplQTO itemPropertyTmplQTO = new ItemPropertyTmplQTO();
		itemPropertyTmplQTO.setCategoryId(categoryId);
		
		List<SkuPropertyTmplDTO> skuPropertyList = null;
		SkuPropertyTmplQTO skuPropertyTmplQTO = new SkuPropertyTmplQTO();
		skuPropertyTmplQTO.setCategoryId(categoryId);
		
		Boolean needPropertyValue = new Boolean(true);
		
		try {
			itemPropertyList = this.itemPropertyManager.queryItemPropertyTmpl(
					itemPropertyTmplQTO, needPropertyValue);
			skuPropertyList = this.itemPropertyManager.querySkuPropertyTmpl(
					skuPropertyTmplQTO, needPropertyValue);
		} catch (ServiceException e) {
			return ServiceResponseHandler.serviceExceptionHandler(e);
		}
		//普通属性
		List<ItemPropertyParam> returnList =new ArrayList<ItemPropertyParam>();
		for(ItemPropertyTmplDTO item: itemPropertyList){
			ItemPropertyParam param = new ItemPropertyParam();
			param.setCid(String.valueOf(item.getCategoryId()));
			param.setId(String.valueOf(item.getId()));
			param.setIsSku("0");
			param.setMust(String.valueOf(item.getMust()));
			param.setName(item.getName());
			this.handleFieldType(param, item);
			returnList.add(param);
		}
		// sku 属性
		for(SkuPropertyTmplDTO item: skuPropertyList){
			ItemPropertyParam param = new ItemPropertyParam();
			param.setCid(String.valueOf(item.getCategoryId()));
			param.setId(String.valueOf(item.getId()));
			param.setIsSku("1");
			param.setMust(String.valueOf(item.getMust()));
			param.setName(item.getName());
			System.out.println("fieldType: " + item.getFieldType() + " " + item.getName());
			this.handleSkuFieldType(param, item);
			System.out.println("aftersetvalue: " + param.getMulti() + " " + param.getUserDefined());
			returnList.add(param);
		}
		
		if(StringUtils.isBlank(callback)){
			return ResponseUtils.getSuccessApiResponseStr(returnList);
		}else{
			return callback + "(" + ResponseUtils.getSuccessApiResponseStr(returnList) + ")"; 
		}
		
	}
	
	/**
	 * 字段类型的处理 前端要区分是输入框 下拉框 还是复选框
	 * @param param
	 * @param item
	 */
	private void handleFieldType(ItemPropertyParam param,ItemPropertyTmplDTO item){
		PropValue propValue = new PropValue();
		List<String> vids = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		List<ItemPropertyValueDTO> valueList =null;
		if(item.getFieldType() == null){
			param.setMulti("0");
			param.setUserDefined("1");
			propValue.setValue(values);
			propValue.setVid(vids);
			param.setPropValues(propValue);
			return;
		}
		int fieldType = item.getFieldType().intValue();
		switch(fieldType){
			case 1:  //  是输入框 无需值列表
				param.setMulti("0");
				param.setUserDefined("1");
				break;
			case 2:  //下拉列表
				param.setMulti("1");
				param.setUserDefined("0");
				valueList = item.getPropertyValues();
				if(!CollectionUtils.isEmpty(valueList)){
					for(ItemPropertyValueDTO valueItem : valueList){
						vids.add(String.valueOf(valueItem.getId()));
						values.add(valueItem.getValue());
					}
				}
				break;
			case 3://复选框
				param.setMulti("0");
				param.setUserDefined("0");
				valueList = item.getPropertyValues();
				if(!CollectionUtils.isEmpty(valueList)){
					for(ItemPropertyValueDTO valueItem : valueList){
						vids.add(String.valueOf(valueItem.getId()));
						values.add(valueItem.getValue());
					 }
				break;
			}
			default :
				param.setMulti("0");
				param.setUserDefined("1");
				break;
		}
		propValue.setValue(values);
		propValue.setVid(vids);
		param.setPropValues(propValue);
		
	}
	
	/** 
	 * 处理sku属性的字段类型
	 * 根据后端的字段类型做转换为前端兼容的格式
	 */
	private void handleSkuFieldType(ItemPropertyParam param,SkuPropertyTmplDTO item){
		PropValue propValue = new PropValue();
		List<String> vids = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		List<SkuPropertyValueDTO> valueList =null;
		if(item.getFieldType() == null){
			param.setMulti("0");
			param.setUserDefined("1");
			propValue.setValue(values);
			propValue.setVid(vids);
			param.setPropValues(propValue);
			return;
		}
		int fieldType = item.getFieldType().intValue();
		switch(fieldType){
			case 1:  //  是输入框 无需值列表
				param.setMulti("0");
				param.setUserDefined("1");
				break;
			case 2:  //下拉列表
				param.setMulti("1");
				param.setUserDefined("0");
				valueList = item.getPropertyValues();
				if(!CollectionUtils.isEmpty(valueList)){
					for(SkuPropertyValueDTO valueItem : valueList){
						vids.add(String.valueOf(valueItem.getId()));
						values.add(valueItem.getValue());
					}
				}
				break;
			case 3://复选框
				param.setMulti("0");
				param.setUserDefined("0");
				valueList = item.getPropertyValues();
				if(!CollectionUtils.isEmpty(valueList)){
					for(SkuPropertyValueDTO valueItem : valueList){
						vids.add(String.valueOf(valueItem.getId()));
						values.add(valueItem.getValue());
					 }
				break;
			}
			default :
				param.setMulti("0");
				param.setUserDefined("1");
				break;
		}
		propValue.setValue(values);
		propValue.setVid(vids);
		param.setPropValues(propValue);
	}
	
}
