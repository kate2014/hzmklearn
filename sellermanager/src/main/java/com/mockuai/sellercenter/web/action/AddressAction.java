package com.mockuai.sellercenter.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.sellercenter.web.common.ApiResponse;
import com.mockuai.sellercenter.web.common.BaseValidator;
import com.mockuai.sellercenter.web.common.constant.ResponseEnum;
import com.mockuai.sellercenter.web.common.exception.ServiceException;
import com.mockuai.sellercenter.web.manager.AddressManager;
import com.mockuai.sellercenter.web.util.ResponseUtils;

/**
 * 根据附地址id获取子级别地址
 * @author cwr
 */
@Controller
public class AddressAction extends BaseValidator {
	
	private final String[] NOT_NULL_FIELDS = {
		"address_id"
	};
	
	private AddressManager addressManager;

	public String getSubAddress(HttpServletRequest request){
		
		if(!StringUtils.isEmpty(this.notNullValidate(NOT_NULL_FIELDS, request))){
			ApiResponse  apiResponse = ResponseUtils.getFailApiResponse(ResponseEnum.P_E_PARAM_ISNULL);
			return ResponseUtils.toJsonStr(apiResponse);
		}
		Integer addressId = Integer.valueOf(request.getParameter("address_id"));
		
		List<RegionDTO> list = null;
		try {
			list = this.addressManager.getSubAddress(addressId);
		} catch (ServiceException e) {
			ApiResponse apiResponse = ResponseUtils.getFailApiResponse(e.getCode(), e.getServiceMessage()); 
			return ResponseUtils.toJsonStr(apiResponse);
		}
		ApiResponse apiResponse = ResponseUtils.getSuccessApiResponse(list);
		return ResponseUtils.toJsonStr(apiResponse);
	}
}
