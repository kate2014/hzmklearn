package com.mockuai.datacenter.core.util;

import com.mockuai.datacenter.common.api.DataResponse;
import com.mockuai.datacenter.common.constant.ResponseCode;

public class ResponseUtil {

	public static <T> DataResponse<T> getSuccessResponse(T model) {
		DataResponse res = new DataResponse(model);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static <T> DataResponse<T> getSuccessResponse(T model, long totalCount) {
		DataResponse res = new DataResponse(model, totalCount);
		res.setCode(ResponseCode.SUCCESS.getCode());
		res.setMessage(ResponseCode.SUCCESS.getComment());
		return res;
	}

	public static DataResponse getErrorResponse(ResponseCode responseCode) {
		return new DataResponse(responseCode);
	}

	public static DataResponse getErrorResponse(ResponseCode responseCode, String message) {
		return new DataResponse(responseCode, message);
	}

	public static DataResponse getErrorResponse(int code, String message) {
		return new DataResponse(code, message);
	}

}
