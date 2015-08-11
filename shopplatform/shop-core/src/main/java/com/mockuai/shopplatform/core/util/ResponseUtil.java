package com.mockuai.shopplatform.core.util;

import com.mockuai.shopplatform.api.ShopResponse;
import com.mockuai.shopplatform.constant.ResponseCode;

public class ResponseUtil {

	public static <T> ShopResponse<T> getSuccessResponse(T model) {
		ShopResponse res = new ShopResponse(model);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static <T> ShopResponse<T> getSuccessResponse(T model, long totalCount) {
		ShopResponse res = new ShopResponse(model, totalCount);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static ShopResponse getErrorResponse(ResponseCode responseCode) {
		return new ShopResponse(responseCode);
	}

	public static ShopResponse getErrorResponse(ResponseCode responseCode, String message) {
		return new ShopResponse(responseCode, message);
	}

	public static ShopResponse getErrorResponse(int code, String message) {
		return new ShopResponse(code, message);
	}

}
