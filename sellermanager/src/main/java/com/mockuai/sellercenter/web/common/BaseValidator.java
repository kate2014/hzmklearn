package com.mockuai.sellercenter.web.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.mockuai.sellercenter.web.common.constant.ResponseEnum;

public class BaseValidator {
	
	/**
	 * 非空字段验证
	 * @param fields
	 * @param request
	 * @return
	 */
	public String notNullValidate(String[] fields,HttpServletRequest request){
		if(fields == null){
			return null;
		}
		boolean isValid = true;
		for(String field : fields){
			if(StringUtils.isBlank(request.getParameter(field))){
				return ResponseEnum.P_E_PARAM_ISNULL.getMsg();
			}
		}
		return null;
	}
	
}
