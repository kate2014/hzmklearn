package com.mockuai.datacenter.core.util;

import com.mockuai.datacenter.common.constant.ResponseCode;
import com.mockuai.datacenter.core.exception.DataException;

public class ExceptionUtil {

	public static DataException getException(ResponseCode responseCode,
			String message) {
		return new DataException(responseCode, message);
	}
}
